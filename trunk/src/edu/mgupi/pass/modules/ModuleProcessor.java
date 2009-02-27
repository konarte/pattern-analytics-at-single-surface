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
import edu.mgupi.pass.db.locuses.LocusFilters;
import edu.mgupi.pass.db.locuses.LocusFiltersFactory;
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

public class ModuleProcessor {
	private final static Logger logger = LoggerFactory.getLogger(ModuleProcessor.class);

	private FilterChainsaw thumbFilters;
	private FilterChainsaw histoFilters;

	private HistogramFilter histo;

	public ModuleProcessor() throws InstantiationException, IllegalAccessException, IllegalParameterValueException,
			NoSuchParamException {

		ResizeFilter resize = new ResizeFilter();
		ParamHelper.getParameter("Width", resize).setValue(Const.THUMB_WIDTH);
		ParamHelper.getParameter("Height", resize).setValue(Const.THUMB_HEIGHT);

		histo = new HistogramFilter();

		thumbFilters = new FilterChainsaw();
		thumbFilters.appendFilter(resize);

		histoFilters = new FilterChainsaw();
		histoFilters.appendFilter(GrayScaleFilter.class);
		histoFilters.appendFilter(histo);
	}

	private IModule module;
	private FilterChainsaw filters;

	// private FilterChainsaw

	public void done() {
		logger.debug("ModuleProcessor.done");

		this.reset();
	}

	public void reset() {
		logger.debug("ModuleProcessor.reset");

		if (this.module != null) {
			this.module.done();
			this.module = null;
		}

		if (this.filters != null) {
			this.filters.done();
			this.filters = null;
		}
	}

	public void registerModule(Class<? extends IModule> moduleClass) throws InstantiationException,
			IllegalAccessException {
		if (moduleClass == null) {
			throw new IllegalArgumentException("Internal error. moduleClass must be not null.");
		}

		if (this.module != null && moduleClass == this.module.getClass()) {
			logger.debug("ModuleProcessor.registerModule class {} already set", moduleClass);
			return;
		}

		logger.debug("ModuleProcessor.registerModule as class {}", moduleClass);
		this.reset();

		this.module = moduleClass.newInstance();
		this.module.init();
	}

	public void setChainsaw(FilterChainsaw filters) {
		logger.debug("ModuleProcessor.setChainsaw");
		this.filters = filters;
	}

	public Locuses startProcessing(SourceStore store) throws FilterException, IOException, ModuleException,
			PersistentException {
		if (this.module == null) {
			throw new IllegalStateException("Internal error. module must be not null");
		}
		logger.debug("ModuleProcessor.processSource for new store {}", store);

		logger.debug("ModuleProcessor.processSource now create new locus");
		Locuses locus = LocusesFactory.createLocuses();
		this.fillLocusData(locus);

		BufferedImage image = store.getSourceImage();
		if (this.filters != null) {
			this.filters.attachImage(image);
			logger.debug("ModuleProcessor.processSource now filter image");
			image = this.filters.filterSaw();
		}

		logger.debug("ModuleProcessor.processSource now analyze image by module {}", this.module);
		this.module.analyze(image, locus);

		locus.setFilteredImage(ModuleHelper.convertImageToPNGRaw(image));

		thumbFilters.attachImage(image);
		locus.setThumbImage(ModuleHelper.convertImageToPNGRaw(thumbFilters.filterSaw()));

		histoFilters.attachImage(image);
		histoFilters.filterSaw();
		store.setHistorgram(histo.getLastHistogramChannel());
		locus.setHistogram(histo.getLastHistogramChannel());

		locus.setProcessed(true);

		return locus;
	}

	public void finishProcessing() {
		if (filters != null) {
			filters.detachImage();
		}
		thumbFilters.detachImage();
		histoFilters.detachImage();
	}

	private void fillLocusData(Locuses locus) throws PersistentException, FilterException {
		logger.debug("ModuleProcessor.processSource now fill data");

		String codename = module.getClass().getCanonicalName();
		LModules module = LModulesFactory.loadLModulesByQuery("codename = '" + codename + "'", null);
		if (module == null) {
			throw new FilterException("Unexpected error. Unable to find properly registed module " + codename);
		}
		locus.setModule(module);

		List<LocusFilters> filters = locus.getFilters();
		if (this.filters != null) {
			for (IFilter filter : this.filters.getFilters()) {
				LocusFilters locusFilter = LocusFiltersFactory.createLocusFilters();
				locusFilter.setOptions(ParamHelper.convertParamsToJSON(filter));

				codename = filter.getClass().getCanonicalName();
				LFilters dbFilter = LFiltersFactory.loadLFiltersByQuery("codename = '" + codename + "'", null);
				if (dbFilter == null) {
					throw new FilterException("Unexpected error. Unable to find properly registed filter " + codename);
				}

				locusFilter.setFilter(dbFilter);

				filters.add(locusFilter);

			}
		}

	}

	public IModule getModule() {
		return this.module;
	}

	public FilterChainsaw getFilters() {
		return this.filters;
	}
}
