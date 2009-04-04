package edu.mgupi.pass.modules;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.orm.PersistentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.db.locuses.LFilters;
import edu.mgupi.pass.db.locuses.LModules;
import edu.mgupi.pass.db.locuses.LocusAppliedFilters;
import edu.mgupi.pass.db.locuses.LocusAppliedFiltersFactory;
import edu.mgupi.pass.db.locuses.LocusAppliedModule;
import edu.mgupi.pass.db.locuses.LocusAppliedModuleFactory;
import edu.mgupi.pass.db.locuses.LocusModuleData;
import edu.mgupi.pass.db.locuses.LocusSources;
import edu.mgupi.pass.db.locuses.LocusSourcesFactory;
import edu.mgupi.pass.db.locuses.Locuses;
import edu.mgupi.pass.db.locuses.LocusesFactory;
import edu.mgupi.pass.face.gui.AppDataStorage;
import edu.mgupi.pass.filters.FilterChainsaw;
import edu.mgupi.pass.filters.FilterChainsawTransactional;
import edu.mgupi.pass.filters.FilterException;
import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.filters.ParamHelper;
import edu.mgupi.pass.filters.FilterChainsaw.SawMode;
import edu.mgupi.pass.filters.service.HistogramFilter;
import edu.mgupi.pass.filters.service.ResizeFilter;
import edu.mgupi.pass.inputs.InputStore;
import edu.mgupi.pass.util.AbstractCacheInitiable;
import edu.mgupi.pass.util.CacheIFactory;
import edu.mgupi.pass.util.Config;
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

	private FilterChainsaw thumbCS;
	private FilterChainsaw histoCS;

	private FilterChainsaw processingChainsaw;
	private FilterChainsaw preProcessingChainsaw;

	private HistogramFilter histogramFilter;

	//	private boolean keepIntegrity = false;

	//	/**
	//	 * Common constructor. We immediate create two chains -- for thumb and for
	//	 * histogram.
	//	 * 
	//	 * @throws Exception
	//	 */
	//	public ModuleProcessor() throws Exception {
	//		this(false);
	//	}
	//	 * @param keepIntegrity
	//	 *            if true, this module processor will be very carefully about
	//	 *            their data. For example, if you set up new {@link IModule} and
	//	 *            that going to failure -- module automatically revert
	//	 *            previously saved module.

	/**
	 * Common constructor. We immediate create two chains -- for thumb and for
	 * histogram.
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws FilterException
	 * @throws PersistentException
	 */
	public ModuleProcessor() throws PersistentException, FilterException, InstantiationException,
			IllegalAccessException {

		histogramFilter = new HistogramFilter();

		thumbCS = new FilterChainsaw();
		ResizeFilter resize = (ResizeFilter) thumbCS.appendFilter(ResizeFilter.class);
		resize.getWIDTH().setValue(Const.THUMB_WIDTH);
		resize.getHEIGHT().setValue(Const.THUMB_HEIGHT);

		histoCS = new FilterChainsaw();
		//histoFilters.appendFilter(GrayScaleFilter.class);
		histogramFilter = (HistogramFilter) histoCS.appendFilter(HistogramFilter.class);

		processingChainsaw = new FilterChainsaw(SawMode.USER_EDIT_CHAINSAW);
		preProcessingChainsaw = new FilterChainsaw(SawMode.SINGLE_INSTANCE);
	}

	private IModule module;

	public void close() {

		// ;)
		this.finishProcessing();

		this.cachedModules = null;
		this.module = null;

		if (this.processingChainsaw != null) {
			this.processingChainsaw.close();
			this.processingChainsaw = null;
		}
		if (this.preProcessingChainsaw != null) {
			this.preProcessingChainsaw.close();
			this.preProcessingChainsaw = null;
		}

		if (thumbCS != null) {
			thumbCS.close();
			thumbCS = null;
		}
		if (histoCS != null) {
			histoCS.close();
			histoCS = null;
		}

		logger.debug("ModuleProcessor closed");
	}

	private AbstractCacheInitiable<IModule> cachedModules = CacheIFactory
			.getSingleInstanceModules();

	/**
	 * Set module as main processing module.
	 * 
	 * @param moduleClass
	 *            class of module, we automatically instantiate this. Class must
	 *            implements {@link IModule} interface.
	 * @return instance of given class
	 * 
	 * @throws ModuleException
	 * @throws PersistentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws IOException
	 */
	public IModule setModule(Class<? extends IModule> moduleClass) throws PersistentException,
			ModuleException, InstantiationException, IllegalAccessException, IOException {
		if (moduleClass == null) {
			throw new IllegalArgumentException("Internal error. moduleClass must be not null.");
		}

		if (this.module != null && moduleClass == this.module.getClass()) {
			logger.debug("ModuleProcessor.registerModule class {} already set", moduleClass);
			return this.module;

		}
		logger.debug("Registering module as class {}", moduleClass);

		/*
		 * Check using this module in registered in database.
		 * 
		 * We always check it ;)
		 */
		AppDataStorage.getInstance().checkUsingModule(moduleClass);

		IModule newInstance = cachedModules.getInstance(moduleClass);

		this.module = newInstance;
		this.updateModule();

		return this.module;
	}

	/**
	 * Update current module -- clearing parameters and executing
	 * {@link IModule#analyze(BufferedImage, Locuses)}. On this step we don't
	 * rebuild histograms, thumbnails or applying filters to original source.
	 * 
	 * @throws IOException
	 * @throws ModuleException
	 * @throws PersistentException
	 */
	public void updateModule() throws IOException, ModuleException, PersistentException {
		if (lastLocus != null) {

			logger.debug("Do analyze after set new module");

			/*
			 * Reset all parameters in module.
			 */

			lastLocus.setProcessed(false);
			for (LocusModuleData param : lastLocus.getModule().getData()) {
				param.delete();
			}
			lastLocus.getModule().getData().clear();
			ANAZYLE.start();
			try {
				// do analyze
				this.module.analyze(lastProcessedImage, lastLocus);
			} finally {
				ANAZYLE.stop();
			}
			lastLocus.setProcessed(true);

			logger.debug("Caching parameters for instance {} : {}", this.module.getName(),
					lastLocus.getModule().getData());

		}
	}

	private BufferedImage lastProcessedImage;
	private BufferedImage lastThumbImage;
	private BufferedImage lastHistogramImage;

	/**
	 * Return last processed (filtered) image.
	 * 
	 * @return instance of image with applied pre-processing and processing
	 *         filters.
	 */
	public BufferedImage getLastProcessedImage() {
		return lastProcessedImage;
	}

	/**
	 * Return last thumbnail of image with applied pre-processing and processing
	 * filters.
	 * 
	 * @return instance of thumbnail
	 */
	public BufferedImage getLastThumbImage() {
		return lastThumbImage;
	}

	/**
	 * Return last histogram image of original source.
	 * 
	 * @return instance of histogram image
	 */
	public BufferedImage getLastHistogramImage() {
		return lastHistogramImage;
	}

	private Secundomer STORE_ORIGINAL_IMAGE = SecundomerList
			.registerSecundomer("Saving original image AS IS");
	private Secundomer PREPROCESSING = SecundomerList
			.registerSecundomer("Pre-process image by special filter chain");
	private Secundomer FILTERING = SecundomerList
			.registerSecundomer("Filter image by common filter chain");
	private Secundomer STORE_FILTERED_IMAGE = SecundomerList
			.registerSecundomer("Saving filtered image as raw");
	private Secundomer FILTERING_THUMB = SecundomerList
			.registerSecundomer("Filter image by internal thumb chain");
	private Secundomer FILTERING_HISTO = SecundomerList
			.registerSecundomer("Filter image by internal histogram chain");
	private Secundomer ANAZYLE = SecundomerList
			.registerSecundomer("Analyze image by registered module");

	private Locuses lastLocus = null;

	/**
	 * Start processing given source. Source image can be found in
	 * {@link InputStore#getSourceImage()}. <br>
	 * 
	 * Processing is a bunch of steps:
	 * <ol>
	 * <li>{@link #fillLocusData(Locuses)} loads from database required values
	 * for {@link Locuses} instance.
	 * 
	 * <li>Init {@link LocusSources}.
	 * 
	 * <li>Build histogram by source image.
	 * 
	 * <li>Apply pre-processing filters to image by
	 * {@link ModuleProcessor#getPreChainsaw()}.
	 * 
	 * <li>Apply processing filters to pre-processed image by
	 * {@link ModuleProcessor#getChainsaw()}.
	 * 
	 * <li>Creating thumbnail for that last image.
	 * 
	 * <li>Calling {@link IModule#analyze(BufferedImage, Locuses)} for that last
	 * image.
	 * 
	 * </ol>
	 * 
	 * @param source
	 *            is source for processing, must be not null
	 * @return created and filled instance of Locus
	 * 
	 * @throws FilterException
	 * @throws IOException
	 * @throws ModuleException
	 * @throws PersistentException
	 */
	public Locuses startProcessing(InputStore source) throws FilterException, IOException,
			ModuleException, PersistentException {
		if (this.module == null) {
			throw new IllegalStateException("Internal error. module must be not null");
		}
		if (source == null) {
			throw new IllegalArgumentException("Internal error. Store must be not null.");
		}

		logger.debug("Start processing for new store {}", source);

		logger.debug("Creating new locus");
		Locuses locus = LocusesFactory.createLocuses();
		locus.setName(source.getName());
		this.fillLocusData(locus);

		STORE_ORIGINAL_IMAGE.start();
		try {
			LocusSources lSource = LocusSourcesFactory.createLocusSources();
			lSource.setFilename(source.getName());
			lSource.setSourceImage(source.getFileData());
			// lSource.save();
			locus.setLocusSource(lSource);
		} finally {
			STORE_ORIGINAL_IMAGE.stop();
		}

		BufferedImage image = source.getSourceImage();

		FILTERING_HISTO.start();
		try {
			histoCS.attachImage(image);
			this.lastHistogramImage = histoCS.filterSaw();
			locus.setHistogram(histogramFilter.getLastHistogramChannel());
		} finally {
			FILTERING_HISTO.stop();
		}

		if (this.preProcessingChainsaw != null) {
			PREPROCESSING.start();
			try {
				this.preProcessingChainsaw.attachImage(image);
				logger.debug("Pre-processing image, cause main filter chain is setup");
				image = this.preProcessingChainsaw.filterSaw();
			} finally {
				PREPROCESSING.stop();
			}
		}

		FILTERING.start();
		try {
			this.processingChainsaw.attachImage(image);
			logger.debug("Filtering image, cause main filter chain is setup");
			image = this.processingChainsaw.filterSaw();
		} finally {
			FILTERING.stop();
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
			thumbCS.attachImage(image);
			this.lastThumbImage = thumbCS.filterSaw();

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
		logger.debug("Caching parameters for instance {} : {}", this.module.getName(), locus
				.getModule().getData());

		return locus;
	}

	/**
	 * Method must be called when you ready to save processed locus into
	 * database. <br>
	 * 
	 * This method filled all temporary parameters into last processed locus.
	 * 
	 * @throws IOException
	 */
	public void applyProcessed() throws IOException {

		if (lastLocus == null) {
			throw new IllegalStateException(
					"Internal error when applying processed module. Locus does not saved.");
		}

		STORE_FILTERED_IMAGE.start();

		try {

			lastLocus.setFilteredImage(ModuleHelper.convertImageToRaw(this.lastProcessedImage));
			lastLocus.setThumbImage(ModuleHelper.convertImageToRaw(this.lastThumbImage));

			ModuleHelper.finalyzeParams(this.lastLocus);

		} finally {
			STORE_FILTERED_IMAGE.stop();
		}
	}

	/**
	 * This method clear all saved values (thumbnail, histogram, etc.).
	 */
	public void finishProcessing() {
		lastLocus = null;
		lastProcessedImage = null;
		lastThumbImage = null;
		lastHistogramImage = null;

		processingChainsaw.detachImage();
		preProcessingChainsaw.detachImage();

		thumbCS.detachImage();
		histoCS.detachImage();

	}

	private Secundomer LOCUS_DATA = SecundomerList
			.registerSecundomer("Filling locus data from database");
	private Secundomer FILTERS_DATA = SecundomerList
			.registerSecundomer("Filling filters data from database");

	/**
	 * Filling locus by parameters -- what module we use and what filters we
	 * apply.
	 * 
	 * @param locus
	 * @throws PersistentException
	 * @throws FilterException
	 * @throws ModuleException
	 */
	private void fillLocusData(Locuses locus) throws PersistentException, FilterException,
			ModuleException {
		logger.debug("Filling data from database");

		LOCUS_DATA.start();
		try {

			//			String codename = module.getClass().getName();
			//			LModules lModule = LModulesFactory.loadLModulesByQuery("codename = '" + codename + "'", null);
			//			if (lModule == null) {
			//				throw new FilterException("Unexpected error. Unable to find properly registed module " + codename);
			//			}

			LModules lModule = AppDataStorage.getInstance().getModuleByClass(module.getClass());
			LocusAppliedModule module = LocusAppliedModuleFactory.createLocusAppliedModule();
			module.setModule(lModule);
			locus.setModule(module);

		} finally {
			LOCUS_DATA.stop();
		}

		FILTERS_DATA.start();
		try {
			if (this.processingChainsaw.getFilterCount() == 0) {
				return; //
			}

			List<LocusAppliedFilters> lFilters = locus.getFilters();

			for (IFilter filter : this.preProcessingChainsaw.getFilters()) {
				LocusAppliedFilters locusFilter = LocusAppliedFiltersFactory
						.createLocusAppliedFilters();
				LFilters lFilter = AppDataStorage.getInstance().getServiceFilterByClass(
						filter.getClass());

				locusFilter.setFilter(lFilter);

				lFilters.add(locusFilter);
			}

			for (IFilter filter : this.processingChainsaw.getFilters()) {
				LocusAppliedFilters locusFilter = LocusAppliedFiltersFactory
						.createLocusAppliedFilters();
				//locusFilter.setOptions(ParamHelper.convertParamsToJSON(filter));

				//				String codename = filter.getClass().getName();
				//				LFilters dbFilter = LFiltersFactory.loadLFiltersByQuery("codename = '" + codename + "'", null);
				//				if (dbFilter == null) {
				//					throw new FilterException("Unexpected error. Unable to find properly registed filter " + codename);
				//				}

				LFilters lFilter = AppDataStorage.getInstance().getFilterByClass(filter.getClass());

				locusFilter.setFilter(lFilter);
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

	public FilterChainsaw getChainsaw() {
		return this.processingChainsaw;
	}

	/**
	 * Return instance of pre-processing filter set (chainsaw).
	 * 
	 * <b>Do not forget! This chainsaw store only one instance of every using
	 * filter!</b>
	 * 
	 * @return instance of pre-processing filter set
	 */
	public FilterChainsaw getPreChainsaw() {
		return this.preProcessingChainsaw;
	}

	public FilterChainsaw getThumbFilters() {
		return thumbCS;
	}

	public FilterChainsaw getHistoFilters() {
		return histoCS;
	}

	private SimpleDateFormat fmt = new SimpleDateFormat("dd.MM.yyy HH:mm:ss");

	public void saveSettingsToFile(File file) throws FileNotFoundException,
			UnsupportedEncodingException {
		if (this.module == null) {
			throw new IllegalStateException("Internal error. Module does not registered yet.");
		}

		if (file == null) {
			throw new IllegalArgumentException(
					"Internal error. Input variable 'file' must be not null.");
		}

		logger.debug("Saving settings to file " + file.getAbsolutePath());

		PrintStream printer = new PrintStream(new FileOutputStream(file, false), false, Config
				.getInstance().getDefaultEncoding());
		try {
			printer.println("# " + Const.PROGRAM_NAME_FULL);
			printer.println("# Module Definiion File");
			printer.println("# " + file.getName());
			printer.println("# " + fmt.format(new Date()));
			printer.println();

			printer.print("module=" + this.module.getClass().getName() + "=");
			printer.print(ParamHelper.convertParamsToJSON(this.module.getParams()));
			printer.println();
			for (IFilter filter : this.processingChainsaw.getFilters()) {
				printer.print(filter.getClass().getName() + "=");
				printer.print(ParamHelper.convertParamsToJSON(filter));
				printer.println();
			}
		} finally {
			printer.close();
		}
	}

	@SuppressWarnings("unchecked")
	public void loadSettingsFromFile(File file) throws Exception {

		if (this.module == null) {
			throw new IllegalStateException("Internal error. Module does not registered yet.");
		}

		if (file == null) {
			throw new IllegalArgumentException(
					"Internal error. Input variable 'file' must be not null.");
		}

		logger.debug("Loading settings from file " + file.getAbsolutePath());

		FilterChainsawTransactional currentFilters = new FilterChainsawTransactional(
				this.processingChainsaw);
		IModule currentModule = this.module;

		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),
				Config.getInstance().getDefaultEncoding()));
		try {
			this.processingChainsaw.removeAllFilters();

			String line = "";
			boolean moduleSet = false;
			int idx = 0;

			while ((line = reader.readLine()) != null) {
				idx++;
				line = line.trim();
				if (line.isEmpty()) {
					continue; //
				}
				if (line.startsWith("#")) {
					continue; //
				}
				if (line.startsWith("module=")) {
					if (moduleSet) {
						throw new Exception("Line " + idx
								+ ". File contains more than one module. "
								+ this.module.getClass().getName() + " already set up.");
					}
					String class_ = line.substring("module=".length());
					int pos = class_.indexOf("=");
					if (pos < 0) {
						throw new Exception("Line " + idx
								+ ". Illegal line. Can't find '=' symbol after module definition.");
					}
					String className = class_.substring(0, pos);
					String params = class_.substring(pos + 1);

					this.setModule((Class<? extends IModule>) Class.forName(className));
					ParamHelper.fillParametersFromJSON(this.module.getParams(), params);

					moduleSet = true;
				} else {
					int pos = line.indexOf("=");
					if (pos < 0) {
						throw new Exception("Line " + idx
								+ ". Illegal line. Can't find '=' symbol.");
					}
					String filterName = line.substring(0, pos);
					String params = line.substring(pos + 1);

					IFilter filter = this.processingChainsaw
							.appendFilter((Class<? extends IFilter>) Class.forName(filterName));
					ParamHelper.fillParametersFromJSON(filter, params);

				}
			}
		} catch (Exception e) {
			// Restore filters!
			currentFilters.commitChanges();
			if (currentModule != null) {
				this.setModule(currentModule.getClass());
			}
			throw e;
		} finally {
			reader.close();
		}
	}
}
