package edu.mgupi.pass.modules;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import org.orm.PersistentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.db.locuses.LFilters;
import edu.mgupi.pass.db.locuses.LFiltersFactory;
import edu.mgupi.pass.db.locuses.LModules;
import edu.mgupi.pass.db.locuses.LModulesFactory;
import edu.mgupi.pass.db.locuses.LocusAppliedFilters;
import edu.mgupi.pass.db.locuses.LocusAppliedFiltersFactory;
import edu.mgupi.pass.db.locuses.LocusAppliedModule;
import edu.mgupi.pass.db.locuses.LocusAppliedModuleFactory;
import edu.mgupi.pass.db.locuses.LocusModuleData;
import edu.mgupi.pass.db.locuses.LocusSources;
import edu.mgupi.pass.db.locuses.LocusSourcesFactory;
import edu.mgupi.pass.db.locuses.Locuses;
import edu.mgupi.pass.db.locuses.LocusesFactory;
import edu.mgupi.pass.filters.FilterChainsaw;
import edu.mgupi.pass.filters.FilterException;
import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.filters.IllegalParameterValueException;
import edu.mgupi.pass.filters.NoSuchParamException;
import edu.mgupi.pass.filters.service.HistogramFilter;
import edu.mgupi.pass.filters.service.ResizeFilter;
import edu.mgupi.pass.sources.SourceStore;
import edu.mgupi.pass.util.CacheIFactory;
import edu.mgupi.pass.util.CacheInitiable;
import edu.mgupi.pass.util.Const;
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

		histogramFilter = new HistogramFilter();

		thumbFilters = new FilterChainsaw();
		ResizeFilter resize = (ResizeFilter) thumbFilters.appendFilter(ResizeFilter.class);
		resize.getWIDTH().setValue(Const.THUMB_WIDTH);
		resize.getHEIGHT().setValue(Const.THUMB_HEIGHT);

		histoFilters = new FilterChainsaw();
		//histoFilters.appendFilter(GrayScaleFilter.class);
		histogramFilter = (HistogramFilter) histoFilters.appendFilter(HistogramFilter.class);
	}

	private IModule module;
	private FilterChainsaw processingFilters;
	private FilterChainsaw preProcessingFilters;

	// private FilterChainsaw

	public void close() throws PersistentException {

		// ;)
		this.finishProcessing();

		this.cachedModules = null;
		this.module = null;

		if (this.processingFilters != null) {
			this.processingFilters.close();
			this.processingFilters = null;
		}
		if (this.preProcessingFilters != null) {
			this.preProcessingFilters.close();
			this.preProcessingFilters = null;
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

	private CacheInitiable<IModule> cachedModules = CacheIFactory.getSingleInstanceModules();
		//new CacheInitiable<IModule>(MODE.SINGLE_INSTANCE);

	public IModule setModule(Class<? extends IModule> moduleClass) throws InstantiationException,
			IllegalAccessException, IOException, ModuleException, PersistentException {
		if (moduleClass == null) {
			throw new IllegalArgumentException("Internal error. moduleClass must be not null.");
		}

		if (this.module != null && moduleClass == this.module.getClass()) {
			logger.debug("ModuleProcessor.registerModule class {} already set", moduleClass);
			return this.module;

		}
		logger.debug("Registering module as class {}", moduleClass);

		IModule instance = cachedModules.getInstance(moduleClass);

		this.module = instance;
		this.updateModule();

		return this.module;
	}

	public void updateModule() throws InstantiationException, IllegalAccessException, IOException, ModuleException,
			PersistentException {
		if (lastLocus != null) {

			logger.debug("Do analyze after set new module");

			// Reset temporary image!
			// Because some modules does not provide it :)

			lastLocus.setProcessed(false);
			for (LocusModuleData param : lastLocus.getModule().getData()) {
				param.delete();
			}
			lastLocus.getModule().getData().clear();
			ANAZYLE.start();
			try {
				this.module.analyze(lastProcessedImage, lastLocus);
			} finally {
				ANAZYLE.stop();
			}
			lastLocus.setProcessed(true);

			logger.debug("Caching parameters for instance {} : {}", this.module.getName(), lastLocus.getModule()
					.getData());

		}
	}

	public void setChainsaw(FilterChainsaw processingFilters) {
		logger.debug("Set main filter chain {}", processingFilters);
		this.processingFilters = processingFilters;
	}

	public void setPreprocessingChainsaw(FilterChainsaw preProcessingFilters) {
		logger.debug("Set pre-processing filter chain {}", preProcessingFilters);
		this.preProcessingFilters = preProcessingFilters;
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
	private Secundomer PREPROCESSING = SecundomerList.registerSecundomer("Pre-process image by special filter chain");
	private Secundomer FILTERING = SecundomerList.registerSecundomer("Filter image by common filter chain");
	private Secundomer STORE_FILTERED_IMAGE = SecundomerList.registerSecundomer("Saving filtered image as raw");
	private Secundomer FILTERING_THUMB = SecundomerList.registerSecundomer("Filter image by internal thumb chain");
	private Secundomer FILTERING_HISTO = SecundomerList.registerSecundomer("Filter image by internal histogram chain");
	private Secundomer ANAZYLE = SecundomerList.registerSecundomer("Analyze image by registered module");

	private Locuses lastLocus = null;

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

		FILTERING_HISTO.start();
		try {
			histoFilters.attachImage(image);
			this.lastHistogramImage = histoFilters.filterSaw();
			locus.setHistogram(histogramFilter.getLastHistogramChannel());
		} finally {
			FILTERING_HISTO.stop();
		}

		if (this.preProcessingFilters != null) {
			PREPROCESSING.start();
			try {
				this.preProcessingFilters.attachImage(image);
				logger.debug("Pre-processing image, cause main filter chain is setup");
				image = this.preProcessingFilters.filterSaw();
			} finally {
				PREPROCESSING.stop();
			}
		}

		if (this.processingFilters != null) {
			FILTERING.start();
			try {
				this.processingFilters.attachImage(image);
				logger.debug("Filtering image, cause main filter chain is setup");
				image = this.processingFilters.filterSaw();
			} finally {
				FILTERING.stop();
			}
		}

		logger.debug("Now we building histograms and thumb-image");
		this.lastProcessedImage = image;

		//		STORE_FILTERED_IMAGE.start();
		//		try {
		//			this.lastProcessedImage = image;
		//		} finally {
		//			STORE_FILTERED_IMAGE.stop();
		//		}

		FILTERING_THUMB.start();
		try {
			thumbFilters.attachImage(image);
			this.lastThumbImage = thumbFilters.filterSaw();

		} finally {
			FILTERING_THUMB.stop();
		}

		// There is histo, before

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
		logger.debug("Caching parameters for instance {} : {}", this.module.getName(), locus.getModule().getData());

		return locus;
	}

	public void applyProcessed() throws IOException {

		if (lastLocus == null) {
			throw new IllegalStateException("Internal error when applying processed module. Locus does not saved.");
		}

		STORE_FILTERED_IMAGE.start();

		try {

			lastLocus.setFilteredImage(ModuleHelper.convertImageToRaw(this.lastProcessedImage));
			lastLocus.setThumbImage(ModuleHelper.convertImageToRaw(this.lastThumbImage));

			ModuleHelper.finalyzeParams(lastLocus);
		} finally {
			STORE_FILTERED_IMAGE.stop();
		}
	}

	public void finishProcessing() throws PersistentException {
		lastLocus = null;
		lastProcessedImage = null;
		lastThumbImage = null;
		lastHistogramImage = null;

		if (processingFilters != null) {
			processingFilters.detachImage();
		}
		if (preProcessingFilters != null) {
			preProcessingFilters.detachImage();
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
			LocusAppliedModule module = LocusAppliedModuleFactory.createLocusAppliedModule();
			module.setModule(lModule);
			locus.setModule(module);

		} finally {
			LOCUS_DATA.stop();
		}

		FILTERS_DATA.start();
		try {
			if (this.processingFilters == null) {
				return; //
			}

			List<LocusAppliedFilters> lFilters = locus.getFilters();

			for (IFilter filter : this.processingFilters.getFilters()) {
				LocusAppliedFilters locusFilter = LocusAppliedFiltersFactory.createLocusAppliedFilters();
				//locusFilter.setOptions(ParamHelper.convertParamsToJSON(filter));

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
		return this.processingFilters;
	}

	public FilterChainsaw getPreProcessingFilters() {
		return this.preProcessingFilters;
	}

	public FilterChainsaw getThumbFilters() {
		return thumbFilters;
	}

	public FilterChainsaw getHistoFilters() {
		return histoFilters;
	}

}