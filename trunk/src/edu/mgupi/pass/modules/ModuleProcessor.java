package edu.mgupi.pass.modules;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.orm.PersistentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.db.locuses.LFilters;
import edu.mgupi.pass.db.locuses.LFiltersFactory;
import edu.mgupi.pass.db.locuses.LModules;
import edu.mgupi.pass.db.locuses.LModulesFactory;
import edu.mgupi.pass.db.locuses.LocusFilterOptions;
import edu.mgupi.pass.db.locuses.LocusFilterOptionsFactory;
import edu.mgupi.pass.db.locuses.LocusModuleParams;
import edu.mgupi.pass.db.locuses.LocusSources;
import edu.mgupi.pass.db.locuses.LocusSourcesFactory;
import edu.mgupi.pass.db.locuses.Locuses;
import edu.mgupi.pass.db.locuses.LocusesFactory;
import edu.mgupi.pass.filters.FilterChainsaw;
import edu.mgupi.pass.filters.FilterException;
import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.filters.IllegalParameterValueException;
import edu.mgupi.pass.filters.NoSuchParamException;
import edu.mgupi.pass.filters.ParamHelper;
import edu.mgupi.pass.filters.java.GrayScaleFilter;
import edu.mgupi.pass.filters.service.HistogramFilter;
import edu.mgupi.pass.filters.service.ResizeFilter;
import edu.mgupi.pass.sources.SourceStore;
import edu.mgupi.pass.util.Const;
import edu.mgupi.pass.util.IInitiable;
import edu.mgupi.pass.util.Secundomer;
import edu.mgupi.pass.util.SecundomerList;

/**
 * Main module processor.
 * 
 * @author raidan
 * 
 */
public class ModuleProcessor {
	private final static Logger logger = LoggerFactory.getLogger(ModuleProcessor.class);

	private FilterChainsaw thumbFilters;
	private FilterChainsaw histoFilters;

	private HistogramFilter histogramFilter;

	/**
	 * Common constructor. We immediate create two chains -- for thumb and for
	 * histogram.
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalParameterValueException
	 * @throws NoSuchParamException
	 */
	public ModuleProcessor() throws InstantiationException, IllegalAccessException, IllegalParameterValueException,
			NoSuchParamException {

		ResizeFilter resize = new ResizeFilter();
		resize.getWIDTH().setValue(Const.THUMB_WIDTH);
		resize.getHEIGHT().setValue(Const.THUMB_HEIGHT);

		histogramFilter = new HistogramFilter();

		thumbFilters = new FilterChainsaw();
		thumbFilters.appendFilter(resize);

		histoFilters = new FilterChainsaw();
		histoFilters.appendFilter(GrayScaleFilter.class);
		histoFilters.appendFilter(histogramFilter);
	}

	private IModule module;
	private FilterChainsaw filters;

	// private FilterChainsaw

	public void close() throws PersistentException {

		// ;)
		this.finishProcessing();

		for (IModule module : cachedModules.values()) {
			if (module instanceof IInitiable) {
				((IInitiable) module).close();
			}
		}
		this.module = null;
		this.cachedModules.clear();

		if (this.filters != null) {
			this.filters.close();
			this.filters = null;
		}

		if (thumbFilters != null) {
			thumbFilters.close();
			thumbFilters = null;
		}
		if (histoFilters != null) {
			histoFilters.close();
			histoFilters = null;
		}

		logger.debug("ModuleProcessor closed");
	}

	//
	// public void reset() {
	// logger.debug("Reset");
	//
	//		
	// }

	private Map<Class<? extends IModule>, IModule> cachedModules = new HashMap<Class<? extends IModule>, IModule>();

	public void setModule(Class<? extends IModule> moduleClass) throws InstantiationException, IllegalAccessException,
			IOException, ModuleException {
		if (moduleClass == null) {
			throw new IllegalArgumentException("Internal error. moduleClass must be not null.");
		}

		if (this.module != null && moduleClass == this.module.getClass()) {
			logger.debug("ModuleProcessor.registerModule class {} already set", moduleClass);
			return;

		}
		logger.debug("Registering module as class {}", moduleClass);

		IModule instance = cachedModules.get(moduleClass);
		if (instance == null) {
			logger.debug("Creating new instance");
			instance = moduleClass.newInstance();
			if (instance instanceof IInitiable) {
				((IInitiable) instance).init();
			}
			cachedModules.put(moduleClass, instance);
		} else {
			logger.debug("Using cached instance");
		}

		this.module = instance;
		if (lastLocus != null) {

			Set<LocusModuleParams> params = this.cachedInstanceParams.get(instance);
			if (params != null) {

				logger
						.debug("Skip analyzing. Using cached values " + params + " for instance "
								+ this.module.getName());
				lastLocus.setParams(params);

			} else {
				logger.debug("Do analyze after set new module");

				// Reset temporary image!
				// Because some modules does not provide it :)

				lastLocus.setProcessed(false);
				lastLocus.setParams(new HashSet<LocusModuleParams>());
				this.module.analyze(lastProcessedImage, lastLocus);
				lastLocus.setProcessed(true);

				logger.debug("Caching parameters for instance {} : {}", this.module.getName(), lastLocus.getParams());
				this.cachedInstanceParams.put(this.module, lastLocus.getParams());

			}

		}
	}

	public void setChainsaw(FilterChainsaw filters) {
		logger.debug("Set main filter chain {}", filters);
		this.filters = filters;
	}

	private BufferedImage lastProcessedImage;
	private BufferedImage lastThumbImage;
	private BufferedImage lastHistogramImage;

	public BufferedImage getLastProcessedImage() {
		return lastProcessedImage;
	}

	public BufferedImage getLastThumbImage() {
		return lastThumbImage;
	}

	public BufferedImage getLastHistogramImage() {
		return lastHistogramImage;
	}

	private Secundomer STORE_ORIGINAL_IMAGE = SecundomerList.registerSecundomer("Saving original image AS IS");
	private Secundomer FILTERING = SecundomerList.registerSecundomer("Filter image by common filter chain");
	private Secundomer STORE_FILTERED_IMAGE = SecundomerList.registerSecundomer("Saving filtered image as PNG");
	private Secundomer FILTERING_THUMB = SecundomerList.registerSecundomer("Filter image by internal thumb chain");
	private Secundomer FILTERING_HISTO = SecundomerList.registerSecundomer("Filter image by internal histogram chain");
	private Secundomer ANAZYLE = SecundomerList.registerSecundomer("Analyze image by registered module");

	private Locuses lastLocus = null;
	private Map<IModule, Set<LocusModuleParams>> cachedInstanceParams = new HashMap<IModule, Set<LocusModuleParams>>();

	public Locuses startProcessing(SourceStore store) throws FilterException, IOException, ModuleException,
			PersistentException {
		if (this.module == null) {
			throw new IllegalStateException("Internal error. module must be not null");
		}
		if (store == null) {
			throw new IllegalArgumentException("Internal error. Store must be not null.");
		}

		logger.debug("Start processing for new store {}", store);

		logger.debug("Creating new locus");
		Locuses locus = LocusesFactory.createLocuses();
		locus.setName(store.getName());
		this.fillLocusData(locus);

		STORE_ORIGINAL_IMAGE.start();
		try {
			LocusSources lSource = LocusSourcesFactory.createLocusSources();
			lSource.setFilename(store.getName());
			lSource.setSourceImage(store.getFileData());
			// lSource.save();
			locus.setLocusSource(lSource);
		} finally {
			STORE_ORIGINAL_IMAGE.stop();
		}

		BufferedImage image = store.getSourceImage();
		if (this.filters != null) {
			FILTERING.start();
			try {
				this.filters.attachImage(image);
				logger.debug("Filtering image, cause main filter chain is setup");
				image = this.filters.filterSaw();
			} finally {
				FILTERING.stop();
			}
		}

		logger.debug("Now we building histograms and thumb-image");
		STORE_FILTERED_IMAGE.start();
		try {
			this.lastProcessedImage = image;
			locus.setFilteredImage(ModuleHelper.convertImageToPNGRaw(image));
		} finally {
			STORE_FILTERED_IMAGE.stop();
		}

		FILTERING_THUMB.start();
		try {
			thumbFilters.attachImage(image);
			this.lastThumbImage = thumbFilters.filterSaw();
			locus.setThumbImage(ModuleHelper.convertImageToPNGRaw(this.lastThumbImage));
		} finally {
			FILTERING_THUMB.stop();
		}

		FILTERING_HISTO.start();
		try {
			histoFilters.attachImage(image);
			this.lastHistogramImage = histoFilters.filterSaw();
			store.setHistogram(histogramFilter.getLastHistogramChannel());
			locus.setHistogram(histogramFilter.getLastHistogramChannel());
		} finally {
			FILTERING_HISTO.stop();
		}

		logger.debug("Time to analyze image by registered module {}", this.module);
		ANAZYLE.start();
		try {
			this.module.analyze(image, locus);
		} finally {
			ANAZYLE.stop();
		}

		locus.setProcessed(true);
		lastLocus = locus;

		// Cache params (for switched modules)
		logger.debug("Caching parameters for instance {} : {}", this.module.getName(), locus.getParams());
		cachedInstanceParams.put(this.module, locus.getParams());

		return locus;
	}

	public void finishProcessing() throws PersistentException {
		lastLocus = null;
		lastProcessedImage = null;
		lastThumbImage = null;
		lastHistogramImage = null;

		for (Set<LocusModuleParams> params : cachedInstanceParams.values()) {
			for (LocusModuleParams param : params) {
				param.delete();
			}
		}

		cachedInstanceParams.clear();

		if (filters != null) {
			filters.detachImage();
		}
		thumbFilters.detachImage();
		histoFilters.detachImage();

	}

	private Secundomer LOCUS_DATA = SecundomerList.registerSecundomer("Filling locus data from database");
	private Secundomer FILTERS_DATA = SecundomerList.registerSecundomer("Filling filters data from database");

	private void fillLocusData(Locuses locus) throws PersistentException, FilterException {
		logger.debug("Filling data from database");

		LOCUS_DATA.start();
		try {

			String codename = module.getClass().getCanonicalName();
			LModules lModule = LModulesFactory.loadLModulesByQuery("codename = '" + codename + "'", null);
			if (lModule == null) {
				throw new FilterException("Unexpected error. Unable to find properly registed module " + codename);
			}
			locus.setModule(lModule);
		} finally {
			LOCUS_DATA.stop();
		}

		FILTERS_DATA.start();
		try {
			if (this.filters == null) {
				return; //
			}

			List<LocusFilterOptions> lFilters = locus.getFilters();

			for (IFilter filter : this.filters.getFilters()) {
				LocusFilterOptions locusFilter = LocusFilterOptionsFactory.createLocusFilterOptions();
				locusFilter.setOptions(ParamHelper.convertParamsToJSON(filter));

				String codename = filter.getClass().getCanonicalName();
				LFilters dbFilter = LFiltersFactory.loadLFiltersByQuery("codename = '" + codename + "'", null);
				if (dbFilter == null) {
					throw new FilterException("Unexpected error. Unable to find properly registed filter " + codename);
				}

				locusFilter.setFilter(dbFilter);
				// locusFilter.save();

				lFilters.add(locusFilter);
			}
		} finally {
			FILTERS_DATA.stop();
		}
	}

	public IModule getModule() {
		return this.module;
	}

	public FilterChainsaw getFilters() {
		return this.filters;
	}

	public FilterChainsaw getThumbFilters() {
		return thumbFilters;
	}

	public FilterChainsaw getHistoFilters() {
		return histoFilters;
	}

}
