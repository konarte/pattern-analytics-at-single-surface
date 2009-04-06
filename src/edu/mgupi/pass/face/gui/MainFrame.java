package edu.mgupi.pass.face.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.MessageFormat;
import java.util.Collection;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.db.locuses.LModules;
import edu.mgupi.pass.db.locuses.Locuses;
import edu.mgupi.pass.db.sensors.Sensors;
import edu.mgupi.pass.db.surfaces.Surfaces;
import edu.mgupi.pass.face.Application;
import edu.mgupi.pass.face.gui.forms.AbstractComboBox;
import edu.mgupi.pass.face.gui.forms.DefectClassesRecord;
import edu.mgupi.pass.face.gui.forms.DefectClassesTable;
import edu.mgupi.pass.face.gui.forms.DefectTypesRecord;
import edu.mgupi.pass.face.gui.forms.DefectTypesTable;
import edu.mgupi.pass.face.gui.forms.SensorClassesRecord;
import edu.mgupi.pass.face.gui.forms.SensorClassesTable;
import edu.mgupi.pass.face.gui.forms.SensorTypesRecord;
import edu.mgupi.pass.face.gui.forms.SensorTypesTable;
import edu.mgupi.pass.face.gui.forms.SensorsFilteredTable;
import edu.mgupi.pass.face.gui.forms.SensorsRecord;
import edu.mgupi.pass.face.gui.forms.SensorsRecordView;
import edu.mgupi.pass.face.gui.forms.SurfaceClassesRecord;
import edu.mgupi.pass.face.gui.forms.SurfaceClassesTable;
import edu.mgupi.pass.face.gui.forms.SurfaceTypesRecord;
import edu.mgupi.pass.face.gui.forms.SurfaceTypesTable;
import edu.mgupi.pass.face.gui.forms.SurfacesFilteredTable;
import edu.mgupi.pass.face.gui.forms.SurfacesRecord;
import edu.mgupi.pass.face.gui.forms.SurfacesRecordView;
import edu.mgupi.pass.face.gui.template.ImageFrameTemplate;
import edu.mgupi.pass.face.gui.template.TableViewerTemplate;
import edu.mgupi.pass.filters.FilterChainsaw;
import edu.mgupi.pass.filters.FilterChainsawTransactional;
import edu.mgupi.pass.filters.FilterNotFoundException;
import edu.mgupi.pass.filters.Param;
import edu.mgupi.pass.filters.ParamHelper;
import edu.mgupi.pass.filters.service.PlaceImageFilter;
import edu.mgupi.pass.filters.service.ResizeFilter;
import edu.mgupi.pass.inputs.InputStore;
import edu.mgupi.pass.inputs.gui.SingleFilePick;
import edu.mgupi.pass.modules.IModule;
import edu.mgupi.pass.modules.ModuleHelper;
import edu.mgupi.pass.modules.ModuleNotFoundException;
import edu.mgupi.pass.modules.ModuleProcessor;
import edu.mgupi.pass.util.Config;
import edu.mgupi.pass.util.Const;
import edu.mgupi.pass.util.Config.SourceMode;

public class MainFrame extends MainFrameDesign {

	private final static Logger logger = LoggerFactory.getLogger(MainFrame.class); // @jve:decl-index=0:

	// ------------
	protected SingleFilePick singleFilePicker = null; // @jve:decl-index=0:
	protected ImageFrameTemplate histogramFrame = null;
	protected ImageFrameTemplate moduleFrame = null;
	protected SurfacesRecordView surfaceView = null;
	protected SensorsRecordView sensorView = null;
	protected ModuleProcessor mainModuleProcessor = null; // @jve:decl-index=0:
	private Surfaces selectedSurface = null;
	private Sensors selectedSensor = null;

	/**
	 * This is the default constructor
	 * 
	 * @throws Exception
	 */
	public MainFrame() throws Exception {
		super();
		this.initImpl();
	}

	public void preCache() throws Exception {
		AppHelper.getInstance().getDialogImpl(this, SettingsWindow.class);
		AppHelper.getInstance().getDialogImpl(this, AboutDialog.class);
		AppHelper.getInstance().getDialogImpl(this, ParametersEditor.class);
		AppHelper.getInstance().getDialogImpl(this, FiltersEditor.class);
		AppHelper.getInstance().getDialogImpl(this, LFiltersList.class);
		AppHelper.getInstance().getDialogImpl(this, LModulesList.class);

		/*
		 * Forms precaching
		 */
		AppHelper.getInstance().getDialogImpl(this, DefectClassesTable.class);
		AppHelper.getInstance().getDialogImpl(this, DefectClassesRecord.class);

		AppHelper.getInstance().getDialogImpl(this, DefectTypesTable.class);
		AppHelper.getInstance().getDialogImpl(this, DefectTypesRecord.class);

		AppHelper.getInstance().getDialogImpl(this, SurfaceClassesTable.class);
		AppHelper.getInstance().getDialogImpl(this, SurfaceClassesRecord.class);

		AppHelper.getInstance().getDialogImpl(this, SurfaceTypesTable.class);
		AppHelper.getInstance().getDialogImpl(this, SurfaceTypesRecord.class);

		AppHelper.getInstance().getDialogImpl(this, SurfacesFilteredTable.class);
		AppHelper.getInstance().getDialogImpl(this, SurfacesRecord.class);
		AppHelper.getInstance().getDialogImpl(this, SurfacesRecordView.class);

		AppHelper.getInstance().getDialogImpl(this, SensorClassesTable.class);
		AppHelper.getInstance().getDialogImpl(this, SensorClassesRecord.class);

		AppHelper.getInstance().getDialogImpl(this, SensorTypesTable.class);
		AppHelper.getInstance().getDialogImpl(this, SensorTypesRecord.class);

		AppHelper.getInstance().getDialogImpl(this, SensorsFilteredTable.class);
		AppHelper.getInstance().getDialogImpl(this, SensorsRecord.class);
		AppHelper.getInstance().getDialogImpl(this, SensorsRecordView.class);

		this.getfSChooser();
	}

	private void initImpl() throws Exception {

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				MainFrame.this.closeImpl();
			}
		});

		AppHelper.getInstance().setIProgressInstance(this);

		mainModuleProcessor = new ModuleProcessor();
		this.filtersModel.setChainsaw(mainModuleProcessor.getChainsaw());

		histogramFrame = (ImageFrameTemplate) AppHelper.getInstance().registerAdditionalWindow(
				this, ImageFrameTemplate.class);
		histogramFrame.registerControlCheckbox(this.jCheckBoxHistogram);
		histogramFrame.setTitle(mainModuleProcessor.getHistoFilters().toString());
		histogramFrame.setName("histogramWindow");
		histogramFrame.setLocation(100, 200);
		Config.getInstance().loadWindowPosition(histogramFrame);

		moduleFrame = (ImageFrameTemplate) AppHelper.getInstance().registerAdditionalWindow(this,
				ImageFrameTemplate.class);
		moduleFrame.registerControlCheckbox(this.jCheckBoxModuleGraphic);
		moduleFrame.setTitle(Messages.getString("MainFrame.moduleNotSelected"));
		moduleFrame.setName("moduleFrameWindow");
		moduleFrame.setLocation(100, 200 + histogramFrame.getHeight() + 20);
		Config.getInstance().loadWindowPosition(moduleFrame);

		surfaceView = (SurfacesRecordView) AppHelper.getInstance().getDialogImpl(this,
				SurfacesRecordView.class);
		surfaceView.registerControlCheckbox(this.jCheckBoxSurface);

		sensorView = (SensorsRecordView) AppHelper.getInstance().getDialogImpl(this,
				SensorsRecordView.class);
		sensorView.registerControlCheckbox(this.jCheckBoxSensor);

		Config.getInstance().loadWindowCheckBoxes(this);

		singleFilePicker = new SingleFilePick();
		singleFilePicker.init();

		jComboBoxModules.setSelectedIndex(0);
		//LModules item = (LModules) jComboBoxModules.getSelectedItem();
		//this.setModule((Class<IModule>) Class.forName(item.getCodename()));

		logger.debug("Main frame init done.");
	}

	private void closeImpl() {
		this.printMessage(Messages.getString("MainFrame.mess.closingApp"));

		logger.debug("Application terminating...");

		if (singleFilePicker != null) {
			singleFilePicker.close();
			singleFilePicker = null;
		}
		if (fSChooser != null) {
			this.fSChooser.removeAll();
			this.fSChooser = null;
		}

		this.closeProcessing();

		if (mainModuleProcessor != null) {
			try {
				this.mainModuleProcessor.close();
			} catch (Exception e) {
				logger.error("Error when closing main module", e);
			}
			mainModuleProcessor = null;
		}

		// preprocessingCache.close();

		try {
			logger.debug("Saving current config...");
			AppHelper.getInstance().saveWindowPositions();
			Config.getInstance().storeWindowCheckBoxes(MainFrame.this, jCheckBoxHistogram,
					jCheckBoxModuleGraphic, jCheckBoxSurface, jCheckBoxSensor);
			Config.getInstance().saveCurrentConfig();
		} catch (ConfigurationException e) {
			logger.error("Error when saving current config", e);
		}

		AppHelper.printCache();
		AppHelper.reset();

		this.clearMessage();
	}

	protected boolean isProcessStarted() {
		return this.processStarted;
	}

	private boolean processStarted = false;
	private InputStore currentSource = null; //  @jve:decl-index=0:
	private Locuses currentLocus = null; // @jve:decl-index=0:

	private String originalImageInfo = ""; //  @jve:decl-index=0: 
	private String filteredImageInfo = "";

	// private CacheInitiable<IFilter> preprocessingCache = new
	// CacheInitiable<IFilter>(); // @jve:decl-index=0:

	protected void startProcessingImpl(InputStore source) throws Exception {

		if (source == null) {
			logger.debug("Nothing to process...");
			// JOptionPane.showMessageDialog(null,
			// "Internal error. Received null source.", "Internal error",
			// JOptionPane.ERROR_MESSAGE);
			return;
		}

		this.closeProcessing();

		logger.debug("Start loading source " + source.getName());

		this.setTitle(Const.PROGRAM_NAME_FULL + " -- " + source.getName());
		this.processStarted = true;

		this.applySourcePreProcessor(source.getSourceImage());

		// Main cycle

		currentSource = source;
		currentLocus = mainModuleProcessor.startProcessing(source);

		BufferedImage sourceImage = source.getSourceImage();
		BufferedImage filteredImage = mainModuleProcessor.getLastProcessedImage();

		this.originalImageInfo = "" //  
				+ sourceImage.getWidth() //
				+ "x" //  
				+ sourceImage.getHeight() //
				+ MessageFormat.format(", {0} bpp", sourceImage.getColorModel().getPixelSize());

		double percentRate = 100;
		ResizeFilter resize = (ResizeFilter) mainModuleProcessor.getPreChainsaw()
				.searchFilterClass(ResizeFilter.class);
		if (resize != null) {
			percentRate = resize.getLastThumbRate();
		}
		this.filteredImageInfo = "" // 
				+ filteredImage.getWidth() //
				+ "x" // 
				+ filteredImage.getHeight() //
				+ MessageFormat.format(", {0} bpp, {1,number,percent}", filteredImage
						.getColorModel().getPixelSize(), percentRate);

		jLabelImageInfo.setText(jTabbedPaneImages.getSelectedIndex() == 0 ? this.originalImageInfo
				: this.filteredImageInfo);
		jButtonTeach.setEnabled(true);

		// Locuses locus = mainModuleProcessor.startProcessing(source);
		// REQUIRED SQUARE BASE!
		this.jPanelImageFiltered.setImage(filteredImage/* , true */);
		this.jPanelImageSource.setImage(sourceImage);
		this.histogramFrame.setImage(mainModuleProcessor.getLastHistogramImage());

		this.applyModuleProcessing();

	}

	protected void closeProcessing() {
		this.setTitle(Const.PROGRAM_NAME_FULL);
		currentLocus = null;
		currentSource = null;
		originalImageInfo = "";
		filteredImageInfo = "";
		jLabelImageInfo.setText("");
		this.jPanelImageFiltered.setImage(null);
		this.jPanelImageSource.setImage(null);
		this.histogramFrame.setImage(null);
		this.mainModuleProcessor.finishProcessing();
		this.moduleFrame.setImage(null);
		jButtonTeach.setEnabled(false);
		this.processStarted = false;
	}

	protected void restartProcessingBySource() {
		MainFrame.this.printMessage(Messages.getString("MainFrame.mess.updateBySource"));
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				try {
					MainFrame.this.restartProcessingBySourceImpl();
				} catch (Throwable t) {
					MainFrame.this.closeProcessing();
					AppHelper.showExceptionDialog(MainFrame.this, Messages
							.getString("MainFrame.err.updateBySource"), t);
				} finally {
					MainFrame.this.clearMessage();
				}

			}
		});
	}

	protected void restartProcessingBySourceImpl() throws Exception {
		this.startProcessingImpl(this.currentSource);
	}

	private void applySourcePreProcessor(BufferedImage newImage) throws Exception {

		// Скорее всего, наилучшим выходом будет использование автоматического масштабирования,
		// когда мы приводим изображение к размеру 1024x1024, а потом закрашиваем 
		//   участки фоновым цветом :)

		if (newImage == null) {
			return;
		}

		FilterChainsaw preProcessing = mainModuleProcessor.getPreChainsaw();

		SourceMode sourceScaleMode = Config.getInstance().getCurrentSourceMode();
		Color background = Config.getInstance().getCurrentBackground();

		String placePosition = sourceScaleMode == SourceMode.CENTER
				|| sourceScaleMode == SourceMode.SCALE_IF_CENTER ? PlaceImageFilter.PLACE_CENTER
				: PlaceImageFilter.PLACE_TOP_LEFT;

		boolean actionPlaceMode = false;
		if (sourceScaleMode == SourceMode.CENTER || sourceScaleMode == SourceMode.LEFT_TOP) {
			actionPlaceMode = true;
		} else if (sourceScaleMode == SourceMode.SCALE) {
			actionPlaceMode = false;
		} else {
			if (newImage.getWidth() > Const.MAIN_IMAGE_WIDTH
					|| newImage.getHeight() > Const.MAIN_IMAGE_HEIGHT) {
				actionPlaceMode = false;
			} else {
				actionPlaceMode = true;
			}
		}

		if (actionPlaceMode) {

			preProcessing.removeFilter(ResizeFilter.class);

			PlaceImageFilter placeImage = (PlaceImageFilter) preProcessing
					.appendFilter(PlaceImageFilter.class);
			placeImage.getWIDTH().setValue(Const.MAIN_IMAGE_WIDTH);
			placeImage.getHEIGHT().setValue(Const.MAIN_IMAGE_HEIGHT);

			placeImage.getBACKGROUND().setValue(background);
			placeImage.getPLACE().setValue(placePosition);

		} else {

			ResizeFilter resizer = (ResizeFilter) preProcessing.appendFilter(ResizeFilter.class);
			resizer.getWIDTH().setValue(Const.MAIN_IMAGE_WIDTH);
			resizer.getHEIGHT().setValue(Const.MAIN_IMAGE_HEIGHT);
			resizer.getINTERPOLATION_METHOD().setValue("bicubic");

			PlaceImageFilter place = (PlaceImageFilter) preProcessing
					.appendFilter(PlaceImageFilter.class);
			place.getWIDTH().setValue(Const.MAIN_IMAGE_WIDTH);
			place.getHEIGHT().setValue(Const.MAIN_IMAGE_HEIGHT);

			place.getBACKGROUND().setValue(background);
			place.getPLACE().setValue("center");
		}

	}

	protected void restartProcessingByModuleParams(final Collection<Param> savedParameters) {
		this.printMessage(Messages.getString("MainFrame.mess.updateByModuleParams"));

		try {
			MainFrame.this.restartProcessingByModuleParamsImpl();
		} catch (Throwable t) {

			String messageOnRestore = "";
			if (savedParameters != null) {
				try {
					logger.info("Restoring module parameters...");
					ParamHelper.restoreParameterValues(mainModuleProcessor.getModule().getParams(),
							savedParameters);
					MainFrame.this.restartProcessingByModuleParamsImpl();
				} catch (Throwable t1) {
					logger.error("Error when restoring module parameters", t1);
					messageOnRestore = Messages.getString(
							"MainFrame.err.updateByModulesParams.restore", t1.getMessage());
					MainFrame.this.closeProcessing();
				}
			}

			AppHelper.showExceptionDialog(MainFrame.this, Messages.getString(
					"MainFrame.err.updateByModuleParams", messageOnRestore), t);
		} finally {
			this.clearMessage();
		}

	}

	protected void restartProcessingByModuleParamsImpl() throws Exception {
		MainFrame.this.mainModuleProcessor.updateModule();
		MainFrame.this.applyModuleProcessing();
	}

	protected void setModule(Class<? extends IModule> newModule) throws Exception {

		if (newModule == null) {
			throw new IllegalArgumentException(
					"Internal error. Parameter 'newModule' must be not null.");
		}

		logger.debug("Applying module {}", newModule);

		this.mainModuleProcessor.setModule(newModule);
		this.moduleFrame.setTitle(this.mainModuleProcessor.getModule().getName());
		Collection<Param> params = mainModuleProcessor.getModule().getParams();
		jButtonModuleParams.setEnabled(params != null && params.size() > 0);

		jComboBoxModules.setSelectedItem(AppDataStorage.getInstance().getModuleByClass(newModule));

		this.applyModuleProcessing();
	}

	private void applyModuleProcessing() throws Exception {

		if (this.processStarted) {
			this.moduleFrame.setImage(ModuleHelper.getTemporaryModuleImage(this.currentLocus));
		}
	}

	protected void restartProcessingByFilters(final FilterChainsawTransactional oldFilters) {
		logger.debug("Restarting process by filters...");
		this.printMessage(Messages.getString("MainFrame.mess.updateByFilters"));

		try {
			MainFrame.this.restartProcessingByFiltersImpl();
		} catch (Throwable t) {
			String messageOnRestore = "";
			if (oldFilters != null) {
				try {
					oldFilters.commitChanges();
					MainFrame.this.restartProcessingByFiltersImpl();
				} catch (Throwable t1) {
					logger.error("Error when restoring filters", t1);
					messageOnRestore = Messages.getString("MainFrame.err.updateByFilters.restore",
							t1.getMessage());
					MainFrame.this.closeProcessing();
				}
			}

			AppHelper.showExceptionDialog(MainFrame.this, Messages.getString(
					"MainFrame.err.updateByFilters", messageOnRestore), t);
		} finally {
			if (oldFilters != null) {
				oldFilters.close();
			}
			this.clearMessage();
		}
	}

	protected void restartProcessingByFiltersImpl() throws Exception {
		this.filtersModel.updateFiltersImpl();
		this.startProcessingImpl(this.currentSource);
	}

	protected void loadNewSetting(final File newFile) {

		logger.debug("Loading new settings file...");

		this.printMessage(Messages.getString("MainFrame.mess.loadNewSettings"));

		IModule currentModule = mainModuleProcessor.getModule();
		FilterChainsawTransactional currentFilters = new FilterChainsawTransactional(
				mainModuleProcessor.getChainsaw());

		boolean isOK = false;
		try {
			mainModuleProcessor.loadSettingsFromFile(newFile);
			isOK = true;
		} catch (FilterNotFoundException fe) {
			AppHelper.showExceptionDialog(MainFrame.this, Messages.getString(
					"MainFrame.err.loadNewSettings.invalidFilter", newFile.getName()), fe);
		} catch (ModuleNotFoundException me) {
			AppHelper.showExceptionDialog(MainFrame.this, Messages.getString(
					"MainFrame.err.loadNewSettings.invalidModule", newFile.getName()), me);
		} catch (Throwable t) {
			AppHelper.showExceptionDialog(MainFrame.this, Messages.getString(
					"MainFrame.err.loadNewSettings.other", newFile.getName()), t);
		}

		try {
			if (isOK) {
				restartProcessingByFiltersImpl();
				jComboBoxModules.setSelectedItem(AppDataStorage.getInstance().getModuleByClass(
						MainFrame.this.mainModuleProcessor.getModule().getClass()));
			}
		} catch (Throwable t) {
			String messageOnRestore = "";
			if (currentModule != null) {
				try {
					MainFrame.this.setModule(currentModule.getClass());
				} catch (Throwable t1) {
					logger.error("Error when restoring module", t1);
					messageOnRestore = Messages.getString(
							"MainFrame.err.loadNewSettings.restore.module", t1.getMessage());
					MainFrame.this.closeProcessing();
				}
			}
			try {
				currentFilters.commitChanges();
				MainFrame.this.restartProcessingByFiltersImpl();
			} catch (Throwable t1) {
				logger.error("Error when restoring filters", t1);

				messageOnRestore = Messages.getString(
						"MainFrame.err.loadNewSettings.restore.filters", t1.getMessage());
				MainFrame.this.closeProcessing();
			}

			AppHelper.showExceptionDialog(MainFrame.this, Messages.getString(
					"MainFrame.err.loadNewSettings", messageOnRestore), t);
		} finally {
			currentFilters.close();
			this.clearMessage();
		}

	}

	private <T> T appendObjectToCombo(AbstractComboBox<T> box, T newValue) {
		if (newValue == null) {
			return null;
		}
		if (box.searchValue(newValue) == -1) {
			box.getMyModel().addElement(newValue);
		}

		box.setValue(newValue);
		return newValue;
	}

	private boolean updateSelectedSensor() {
		try {
			if (selectedSensor != null) {
				sensorView.loadRecord(selectedSensor, false);
			}
			return true;
		} catch (Throwable t) {
			AppHelper.showExceptionDialog(MainFrame.this, Messages
					.getString("MainFrame.err.updateSelectedSensor"), t);
		}
		return false;
	}

	private boolean updateSelectedSurface() {
		try {
			if (selectedSurface != null) {
				surfaceView.loadRecord(selectedSurface, false);
			}
			return true;
		} catch (Throwable t) {
			AppHelper.showExceptionDialog(MainFrame.this, Messages
					.getString("MainFrame.err.updateSelectedSurface"), t);
		}
		return false;
	}

	private boolean openWindow(Class<? extends TableViewerTemplate<?>> clazz) {
		TableViewerTemplate<?> instance = (TableViewerTemplate<?>) AppHelper.getInstance()
				.getDialog(this, clazz);
		if (instance != null) {
			instance.showWindow();
			if (updateSelectedSensor()) {
				updateSelectedSurface();
			}

			return true;
		}
		return false;
	}

	private JFileChooser fSChooser = null;

	private JFileChooser getfSChooser() {
		if (fSChooser == null) {
			fSChooser = new JFileChooser();

			fSChooser.setCurrentDirectory(new File("."));
			fSChooser.setMultiSelectionEnabled(false);
			fSChooser.addChoosableFileFilter(new FileNameExtensionFilter(Messages
					.getString("MainFrame.filterSet.filter"), "settings"));

			AppHelper.getInstance().registerAdditionalComponent(fSChooser);

		}
		fSChooser.repaint();
		return fSChooser;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == jComboBoxSurface) {
			this.selectedSurface = jComboBoxSurface.getValue();
			this.updateSelectedSurface();
			return;
		}
		if (e.getSource() == jComboBoxSensor) {
			this.selectedSensor = jComboBoxSensor.getValue();
			this.updateSelectedSensor();
			return;
		}

		if (e.getSource() == jComboBoxModules) {
			final LModules item = (LModules) jComboBoxModules.getSelectedItem();
			if (item == null) {
				return;
			}

			final IModule currentModule = MainFrame.this.mainModuleProcessor.getModule();
			if (currentModule != null
					&& currentModule.getClass().getName().equals(item.getCodename())) {
				logger.trace("Do not check module, it's already checked.");
				return;
			}

			SwingUtilities.invokeLater(new Runnable() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					IModule currentModule = mainModuleProcessor.getModule();
					try {
						MainFrame.this
								.setModule((Class<IModule>) Class.forName(item.getCodename()));
					} catch (Throwable t) {
						String messageOnRestore = "";
						if (currentModule != null) {
							try {
								MainFrame.this.setModule(currentModule.getClass());
							} catch (Throwable t1) {
								logger.error("Error when restoring module", t1);
								messageOnRestore = Messages.getString(
										"MainFrame.err.setModule.restore", t1.getMessage());
								MainFrame.this.closeProcessing();
							}
						}
						String name = item.getCodename() + " (" + item.getName() + ")";
						AppHelper.showExceptionDialog(MainFrame.this, Messages.getString(
								"MainFrame.err.setModule", name, messageOnRestore), t);
					}
				}
			});

			return;
		}

		String command = e.getActionCommand();

		if (command == null) {
			return;
		}

		Actions action = null;
		try {
			action = Actions.valueOf(command);
		} catch (IllegalArgumentException iae) {
			logger.debug("Unknown command received: " + command);
			return;
		}

		if (action == Actions.OPEN) {
			// Open new image
			try {
				this.printMessage(Messages.getString("MainFrame.mess.openNewImage"));
				this.startProcessingImpl(this.singleFilePicker.getSingleSource());
				this.jMenuItemClose.setEnabled(true);
			} catch (Throwable t) {
				this.closeProcessing();
				AppHelper.showExceptionDialog(this, Messages
						.getString("MainFrame.err.openNewImage"), t);
			} finally {
				this.clearMessage();
			}
		} else if (action == Actions.CLOSE) {
			this.closeProcessing();
			this.jMenuItemClose.setEnabled(false);
		} else if (action == Actions.SAVE_FILTER_SET) {
			JFileChooser chooser = getfSChooser();
			int ret = chooser.showSaveDialog(this);
			if (ret == JFileChooser.APPROVE_OPTION) {
				File newFile = chooser.getSelectedFile();
				try {
					if (!newFile.exists()
							|| JOptionPane.showConfirmDialog(this, Messages.getString(
									"MainFrame.confirm.reWriteFilterSet", newFile.getName()),
									Messages.getString("MainFrame.title.rewriteFilterSet"),
									JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
						logger.debug("Actual saving file " + newFile.getAbsolutePath());
						this.mainModuleProcessor.saveSettingsToFile(newFile);
					}
				} catch (Throwable t) {
					AppHelper.showExceptionDialog(this,

					Messages.getString("MainFrame.err.rewriteFilterSet", newFile.getName()), t);
				}
			}

		} else if (action == Actions.OPEN_FILTER_SET) {
			JFileChooser chooser = getfSChooser();
			int ret = chooser.showOpenDialog(this);
			if (ret == JFileChooser.APPROVE_OPTION) {
				this.loadNewSetting(chooser.getSelectedFile());
			}
		} else if (action == Actions.SETTINGS) {
			// Edit settings
			SettingsWindow settings = (SettingsWindow) AppHelper.getInstance().getDialog(this,
					SettingsWindow.class);
			if (settings != null) {
				if (settings.openDialog()) {
					this.restartProcessingBySource();
				}
				// RESTART NOW
				if (Application.isRestartRequired()) {
					this.closeImpl();
					System.exit(0);
				}
			}
		} else if (action == Actions.EXIT) {
			// Do exit
			this.closeImpl();
			System.exit(0);
		} else if (action == Actions.FILTER_LIST) {
			// View all registered filters
			LFiltersList filterList = (LFiltersList) AppHelper.getInstance().getDialog(this,
					LFiltersList.class);
			if (filterList != null) {
				filterList.showDialogCancelOnly();
			}
		} else if (action == Actions.MODULE_LIST) {
			// View all registered modules
			LModulesList modulesList = (LModulesList) AppHelper.getInstance().getDialog(this,
					LModulesList.class);
			if (modulesList != null) {
				modulesList.showDialogCancelOnly();
			}
		} else if (action == Actions.DEFECT_CLASSES) {
			//
			this.openWindow(DefectClassesTable.class);
		} else if (action == Actions.DEFECT_TYPES) {
			//
			this.openWindow(DefectTypesTable.class);
		} else if (action == Actions.SURFACE_CLASSES) {
			//
			this.openWindow(SurfaceClassesTable.class);
		} else if (action == Actions.SURFACE_TYPES) {
			//
			this.openWindow(SurfaceTypesTable.class);
		} else if (action == Actions.SURFACES) {
			SurfacesFilteredTable surfaces = (SurfacesFilteredTable) AppHelper.getInstance()
					.getDialog(this, SurfacesFilteredTable.class);
			if (surfaces != null) {
				Surfaces surface = surfaces.openWindow();
				if (surface != null) {
					this.appendObjectToCombo(jComboBoxSurface, surface);
					this.selectedSurface = surface;
				}
			}
		} else if (action == Actions.SENSOR_CLASSES) {
			//
			this.openWindow(SensorClassesTable.class);
		} else if (action == Actions.SENSOR_TYPES) {
			//
			this.openWindow(SensorTypesTable.class);
		} else if (action == Actions.SENSORS) {
			SensorsFilteredTable sensors = (SensorsFilteredTable) AppHelper.getInstance()
					.getDialog(this, SensorsFilteredTable.class);
			if (sensors != null) {
				Sensors sensor = sensors.openWindow();
				if (sensor != null) {
					this.appendObjectToCombo(jComboBoxSensor, sensor);
					this.selectedSensor = sensor;
				}
			}
		} else if (action == Actions.HELP) {
			// View help
			AppHelper.openLink(Const.WEB_HELP_PAGE);
		} else if (action == Actions.ABOUT) {
			// View about
			AboutDialog about = (AboutDialog) AppHelper.getInstance().getDialog(this,
					AboutDialog.class);
			if (about != null) {
				about.showDialog();
			}
		} else if (action == Actions.NOT_IMPLEMENTED) {
			// Not implemented yet. Sorry.
			AppHelper.showErrorDialog(this, Messages.getString("MainFrame.err.notImplemented"));
		} else if (action == Actions.EDIT_FILTERS) {
			//
			FilterChainsawTransactional currentFilters = new FilterChainsawTransactional(
					mainModuleProcessor.getChainsaw());

			FiltersEditor editor = (FiltersEditor) AppHelper.getInstance().getDialog(this,
					FiltersEditor.class);
			if (editor != null
					&& editor.openDialog(Messages.getString("MainFrame.title.mainFilters"),
							mainModuleProcessor.getChainsaw())) {
				this.restartProcessingByFilters(currentFilters);
			}
		} else if (action == Actions.EDIT_MODULE_PARAMS) {

			Collection<Param> currentParameters = null;
			try {
				currentParameters = ParamHelper.cloneParameters(mainModuleProcessor.getModule()
						.getParams());
			} catch (CloneNotSupportedException cnse) {
				AppHelper.showExceptionDialog(this, Messages
						.getString("MainFrame.err.prepareError"), cnse);
				return;
			}

			ParametersEditor editor = (ParametersEditor) AppHelper.getInstance().getDialog(this,
					ParametersEditor.class);
			if (editor != null
					&& editor.openDialog(mainModuleProcessor.getModule().getName(),
							mainModuleProcessor.getModule().getParams())) {
				this.restartProcessingByModuleParams(currentParameters);

			}
		} else if (action == Actions.CREATE_DEFECT) {
			if (this.selectedSensor == null) {
				AppHelper.showErrorDialog(this, Messages
						.getString("MainFrame.err.noSensorSelected"));
				return;
			}
			if (this.selectedSurface == null) {
				AppHelper.showErrorDialog(this, Messages
						.getString("MainFrame.err.noSurfaceSelected"));
				return;
			}

			AppHelper.showInfoDialog(this, "OK!");
		} else {
			AppHelper.showErrorDialog(this, Messages.getString("MainFrame.err.unknownAction",
					action));
		}

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == jTabbedPaneImages) {
			jLabelImageInfo
					.setText(jTabbedPaneImages.getSelectedIndex() == 0 ? MainFrame.this.originalImageInfo
							: MainFrame.this.filteredImageInfo);
		}

	}
}
