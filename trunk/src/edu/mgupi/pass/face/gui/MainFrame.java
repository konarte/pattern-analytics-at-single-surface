package edu.mgupi.pass.face.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.text.MessageFormat;
import java.util.Collection;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.db.locuses.LModules;
import edu.mgupi.pass.db.locuses.Locuses;
import edu.mgupi.pass.face.Application;
import edu.mgupi.pass.face.IProgress;
import edu.mgupi.pass.face.gui.template.ImageFrameTemplate;
import edu.mgupi.pass.face.gui.template.ImagePanel;
import edu.mgupi.pass.filters.FilterChainsaw;
import edu.mgupi.pass.filters.FilterChainsawTransactional;
import edu.mgupi.pass.filters.FilterNotFoundException;
import edu.mgupi.pass.filters.IFilter;
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
import edu.mgupi.pass.util.Utils;
import edu.mgupi.pass.util.Config.SourceMode;

public class MainFrame extends JFrame implements IProgress, ActionListener {

	private final static Logger logger = LoggerFactory.getLogger(MainFrame.class); // @jve:decl-index=0:

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JMenuBar jmainMenuBar = null;
	private JMenu jMenuFile = null;
	private JMenuItem jMenuItemOpen = null;
	private JMenuItem jMenuItemOpenMass = null;
	private JMenuItem jMenuItemSettings = null;
	private JMenuItem jMenuItemExit = null;
	private JMenu jMenuDatabase = null;
	private JMenuItem jMenuItemMaterials = null;

	// ------------
	protected SingleFilePick singleFilePicker = null; // @jve:decl-index=0:
	protected ImageFrameTemplate histogramFrame = null;
	protected ImageFrameTemplate moduleFrame = null;
	protected ModuleProcessor mainModuleProcessor = null; // @jve:decl-index=0:

	protected static enum Actions {
		NOT_IMPLEMENTED, // 
		OPEN, CLOSE, OPEN_MASS, OPEN_FILTER_SET, SAVE_FILTER_SET, SETTINGS, EXIT, //
		FILTER_LIST, MODULE_LIST, //
		DEFECT_CLASSES, DEFECT_TYPES, //
		SURFACE_CLASSES, SURFACE_TYPES, //
		HELP, ABOUT, //

		// buttons
		EDIT_FILTERS, EDIT_MODULE_PARAMS

	};

	private void registerAction(AbstractButton button, Actions action) {
		button.setName(action.name());
		button.setActionCommand(action.name());
		Utils.addCheckedListener(button, this);
	}

	/**
	 * This is the default constructor
	 * 
	 * @throws Exception
	 */
	public MainFrame() throws Exception {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() throws Exception {

		// Initializing interface

		// Ensure that connection is OK
		AppDataStorage.getInstance().listLFiltersImpl();
		AppDataStorage.getInstance().listLModulesImpl();

		this.setJMenuBar(getJmainMenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle(Const.PROGRAM_NAME_FULL);
		this.setName("mainFrame"); //$NON-NLS-1$
		this.setMinimumSize(new Dimension(600, 620));
		this.setBounds(new Rectangle(150, 150, 800, 620));
		//Config.getInstance().getWindowPosition(this);

		// ----------------

		this.initImpl();
	}

	public void preCache() throws Exception {
		AppHelper.getInstance().getDialogImpl(SettingsDialog.class);
		AppHelper.getInstance().getDialogImpl(AboutDialog.class);
		AppHelper.getInstance().getDialogImpl(ParametersEditor.class);
		AppHelper.getInstance().getDialogImpl(FiltersEditor.class);
		AppHelper.getInstance().getDialogImpl(LFiltersList.class);
		AppHelper.getInstance().getDialogImpl(LModulesList.class);

		AppHelper.getInstance().getDialogImpl(DefectClassesTable.class);
		AppHelper.getInstance().getDialogImpl(DefectClassesRecord.class);

		AppHelper.getInstance().getDialogImpl(DefectTypesTable.class);
		AppHelper.getInstance().getDialogImpl(DefectTypesRecord.class);

		AppHelper.getInstance().getDialogImpl(SurfaceClassesTable.class);
		AppHelper.getInstance().getDialogImpl(SurfaceClassesRecord.class);

		AppHelper.getInstance().getDialogImpl(SurfaceTypesTable.class);
		AppHelper.getInstance().getDialogImpl(SurfaceTypesRecord.class);

		this.getfSChooser();
	}

	private void initImpl() throws Exception {

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				MainFrame.this.closeImpl();
			}
		});

		AppHelper.getInstance().setIProgressInstance(this);
		this.clearMessage();

		mainModuleProcessor = new ModuleProcessor();
		this.filtersModel.setChainsaw(mainModuleProcessor.getChainsaw());

		histogramFrame = (ImageFrameTemplate) AppHelper.getInstance().registerAdditionalWindow(
				this, ImageFrameTemplate.class);
		histogramFrame.registerControlCheckbox(this.jCheckBoxHistogram);
		histogramFrame.setTitle(mainModuleProcessor.getHistoFilters().toString());
		histogramFrame.setName("histogramWindow"); //$NON-NLS-1$
		histogramFrame.setLocation(100, 200);
		Config.getInstance().loadWindowPosition(histogramFrame);

		moduleFrame = (ImageFrameTemplate) AppHelper.getInstance().registerAdditionalWindow(this,
				ImageFrameTemplate.class);
		moduleFrame.registerControlCheckbox(this.jCheckBoxModuleGraphic);
		moduleFrame.setTitle(Messages.getString("MainFrame.moduleNotSelected")); //$NON-NLS-1$
		moduleFrame.setName("moduleFrameWindow"); //$NON-NLS-1$
		moduleFrame.setLocation(100, 200 + histogramFrame.getHeight() + 20);
		Config.getInstance().loadWindowPosition(moduleFrame);

		Config.getInstance().loadWindowCheckBoxes(this);

		singleFilePicker = new SingleFilePick();
		singleFilePicker.init();

		jComboBoxModules.setSelectedIndex(0);
		//LModules item = (LModules) jComboBoxModules.getSelectedItem();
		//this.setModule((Class<IModule>) Class.forName(item.getCodename()));

		logger.debug("Main frame init done."); //$NON-NLS-1$
	}

	private void closeImpl() {
		this.printMessage(Messages.getString("MainFrame.mess.closingApp")); //$NON-NLS-1$

		logger.debug("Application terminating..."); //$NON-NLS-1$

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
				logger.error("Error when closing main module", e); //$NON-NLS-1$
			}
			mainModuleProcessor = null;
		}

		// preprocessingCache.close();

		try {
			logger.debug("Saving current config..."); //$NON-NLS-1$
			AppHelper.getInstance().saveWindowPositions();
			Config.getInstance().storeWindowCheckBoxes(MainFrame.this, jCheckBoxHistogram,
					jCheckBoxModuleGraphic);
			Config.getInstance().saveCurrentConfig();
		} catch (ConfigurationException e) {
			logger.error("Error when saving current config", e); //$NON-NLS-1$
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

	private String originalImageInfo = ""; //  @jve:decl-index=0: //$NON-NLS-1$
	private String filteredImageInfo = ""; //$NON-NLS-1$

	// private CacheInitiable<IFilter> preprocessingCache = new
	// CacheInitiable<IFilter>(); // @jve:decl-index=0:

	protected void startProcessingImpl(InputStore source) throws Exception {

		if (source == null) {
			logger.debug("Nothing to process..."); //$NON-NLS-1$
			// JOptionPane.showMessageDialog(null,
			// "Internal error. Received null source.", "Internal error",
			// JOptionPane.ERROR_MESSAGE);
			return;
		}

		this.closeProcessing();

		logger.debug("Start loading source " + source.getName()); //$NON-NLS-1$

		this.setTitle(Const.PROGRAM_NAME_FULL + " -- " + source.getName()); //$NON-NLS-1$
		this.processStarted = true;

		this.applySourcePreProcessor(source.getSourceImage());

		// Main cycle

		currentSource = source;
		currentLocus = mainModuleProcessor.startProcessing(source);

		BufferedImage sourceImage = source.getSourceImage();
		BufferedImage filteredImage = mainModuleProcessor.getLastProcessedImage();

		this.originalImageInfo = "" //  //$NON-NLS-1$
				+ sourceImage.getWidth() //
				+ "x" //  //$NON-NLS-1$
				+ sourceImage.getHeight() //
				+ MessageFormat.format(", {0} bpp", sourceImage.getColorModel().getPixelSize()); //$NON-NLS-1$

		double percentRate = 100;
		ResizeFilter resize = (ResizeFilter) mainModuleProcessor.getPreChainsaw()
				.searchFilterClass(ResizeFilter.class);
		if (resize != null) {
			percentRate = resize.getLastThumbRate();
		}
		this.filteredImageInfo = "" // //$NON-NLS-1$
				+ filteredImage.getWidth() //
				+ "x" // //$NON-NLS-1$
				+ filteredImage.getHeight() //
				+ MessageFormat.format(", {0} bpp, {1,number,percent}", filteredImage //$NON-NLS-1$
						.getColorModel().getPixelSize(), percentRate);

		jLabelImageInfo.setText(jTabbedPaneImages.getSelectedIndex() == 0 ? this.originalImageInfo
				: this.filteredImageInfo);

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
		originalImageInfo = ""; //$NON-NLS-1$
		filteredImageInfo = ""; //$NON-NLS-1$
		jLabelImageInfo.setText(""); //$NON-NLS-1$
		this.jPanelImageFiltered.setImage(null);
		this.jPanelImageSource.setImage(null);
		this.histogramFrame.setImage(null);
		this.mainModuleProcessor.finishProcessing();
		this.moduleFrame.setImage(null);

		this.processStarted = false;
	}

	protected void restartProcessingBySource() {
		MainFrame.this.printMessage(Messages.getString("MainFrame.mess.updateBySource")); //$NON-NLS-1$
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				try {
					MainFrame.this.restartProcessingBySourceImpl();
				} catch (Throwable t) {
					MainFrame.this.closeProcessing();
					AppHelper.showExceptionDialog(MainFrame.this, Messages
							.getString("MainFrame.err.updateBySource"), t); //$NON-NLS-1$
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
			resizer.getINTERPOLATION_METHOD().setValue("bicubic"); //$NON-NLS-1$

			PlaceImageFilter place = (PlaceImageFilter) preProcessing
					.appendFilter(PlaceImageFilter.class);
			place.getWIDTH().setValue(Const.MAIN_IMAGE_WIDTH);
			place.getHEIGHT().setValue(Const.MAIN_IMAGE_HEIGHT);

			place.getBACKGROUND().setValue(background);
			place.getPLACE().setValue("center"); //$NON-NLS-1$
		}

	}

	protected void restartProcessingByModuleParams(final Collection<Param> savedParameters) {
		MainFrame.this.printMessage(Messages.getString("MainFrame.mess.updateByModuleParams")); //$NON-NLS-1$
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				try {
					MainFrame.this.restartProcessingByModuleParamsImpl();
				} catch (Throwable t) {

					String messageOnRestore = ""; //$NON-NLS-1$
					if (savedParameters != null) {
						try {
							logger.info("Restoring module parameters..."); //$NON-NLS-1$
							ParamHelper.restoreParameterValues(mainModuleProcessor.getModule()
									.getParams(), savedParameters);
							MainFrame.this.restartProcessingByModuleParamsImpl();
						} catch (Throwable t1) {
							logger.error("Error when restoring module parameters", t1); //$NON-NLS-1$
							messageOnRestore = Messages.getString(
									"MainFrame.err.updateByModulesParams.restore", t1.getMessage()); //$NON-NLS-1$
							MainFrame.this.closeProcessing();
						}
					}

					AppHelper.showExceptionDialog(MainFrame.this, Messages.getString(
							"MainFrame.err.updateByModuleParams", messageOnRestore), t); //$NON-NLS-1$
				} finally {
					MainFrame.this.clearMessage();
				}

			}
		});
	}

	protected void restartProcessingByModuleParamsImpl() throws Exception {
		MainFrame.this.mainModuleProcessor.updateModule();
		MainFrame.this.applyModuleProcessing();
	}

	protected void setModule(Class<? extends IModule> newModule) throws Exception {

		if (newModule == null) {
			throw new IllegalArgumentException(
					"Internal error. Parameter 'newModule' must be not null."); //$NON-NLS-1$
		}

		logger.debug("Applying module {}", newModule); //$NON-NLS-1$

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
		logger.debug("Restarting process by filters..."); //$NON-NLS-1$
		MainFrame.this.printMessage(Messages.getString("MainFrame.mess.updateByFilters")); //$NON-NLS-1$

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				try {
					MainFrame.this.restartProcessingByFiltersImpl();
				} catch (Throwable t) {
					String messageOnRestore = ""; //$NON-NLS-1$
					if (oldFilters != null) {
						try {
							oldFilters.commitChanges();
							MainFrame.this.restartProcessingByFiltersImpl();
						} catch (Throwable t1) {
							logger.error("Error when restoring filters", t1); //$NON-NLS-1$
							messageOnRestore = Messages.getString(
									"MainFrame.err.updateByFilters.restore", t1.getMessage()); //$NON-NLS-1$
							MainFrame.this.closeProcessing();
						}
					}

					AppHelper.showExceptionDialog(MainFrame.this, Messages.getString(
							"MainFrame.err.updateByFilters", messageOnRestore), t); //$NON-NLS-1$
				} finally {
					oldFilters.close();
					MainFrame.this.clearMessage();
				}
			}
		});
	}

	protected void restartProcessingByFiltersImpl() throws Exception {
		this.filtersModel.updateFiltersImpl();
		this.startProcessingImpl(this.currentSource);
	}

	protected void loadNewSetting(final File newFile) {

		logger.debug("Loading new settings file..."); //$NON-NLS-1$

		MainFrame.this.printMessage(Messages.getString("MainFrame.mess.loadNewSettings")); //$NON-NLS-1$

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				IModule currentModule = mainModuleProcessor.getModule();
				FilterChainsawTransactional currentFilters = new FilterChainsawTransactional(
						mainModuleProcessor.getChainsaw());

				boolean isOK = false;
				try {
					mainModuleProcessor.loadSettingsFromFile(newFile);
					isOK = true;
				} catch (FilterNotFoundException fe) {
					AppHelper.showExceptionDialog(MainFrame.this, Messages.getString(
							"MainFrame.err.loadNewSettings.invalidFilter", newFile.getName()), fe); //$NON-NLS-1$
				} catch (ModuleNotFoundException me) {
					AppHelper.showExceptionDialog(MainFrame.this, Messages.getString(
							"MainFrame.err.loadNewSettings.invalidModule", newFile.getName()), me); //$NON-NLS-1$
				} catch (Throwable t) {
					AppHelper.showExceptionDialog(MainFrame.this, Messages.getString(
							"MainFrame.err.loadNewSettings.other", newFile.getName()), t); //$NON-NLS-1$
				}

				if (isOK) {

					try {
						restartProcessingByFiltersImpl();
						jComboBoxModules.setSelectedItem(AppDataStorage.getInstance()
								.getModuleByClass(
										MainFrame.this.mainModuleProcessor.getModule().getClass()));
					} catch (Throwable t) {
						String messageOnRestore = ""; //$NON-NLS-1$
						if (currentModule != null) {
							try {
								MainFrame.this.setModule(currentModule.getClass());
							} catch (Throwable t1) {
								logger.error("Error when restoring module", t1); //$NON-NLS-1$
								messageOnRestore = Messages.getString(
										"MainFrame.err.loadNewSettings.restore.module", //$NON-NLS-1$ 
										t1.getMessage());
								MainFrame.this.closeProcessing();
							}
						}
						if (currentFilters != null) {
							try {
								currentFilters.commitChanges();
								MainFrame.this.restartProcessingByFiltersImpl();
							} catch (Throwable t1) {
								logger.error("Error when restoring filters", t1); //$NON-NLS-1$

								messageOnRestore = Messages.getString(
										"MainFrame.err.loadNewSettings.restore.filters", //$NON-NLS-1$ 
										t1.getMessage());
								MainFrame.this.closeProcessing();
							}
						}

						AppHelper.showExceptionDialog(MainFrame.this, Messages.getString(
								"MainFrame.err.loadNewSettings", messageOnRestore), t); //$NON-NLS-1$ 
					} finally {
						if (currentFilters != null) {
							currentFilters.close();
						}
					}
				}

				MainFrame.this.clearMessage();
			}
		});

	}

	public void clearMessage() {
		this.printMessage(""); //$NON-NLS-1$
	}

	public void printMessage(final String message) {
		jStatus.setText(message);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanelCommon(), BorderLayout.CENTER);
			jContentPane.add(getJPanelStatus(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jmainMenuBar
	 * 
	 * @return javax.swing.JMenuBar
	 */
	private JMenuBar getJmainMenuBar() {
		if (jmainMenuBar == null) {
			jmainMenuBar = new JMenuBar();
			jmainMenuBar.add(getJMenuFile());
			jmainMenuBar.add(getJMenuDatabase());
			jmainMenuBar.add(getJMenuHelp());
		}
		return jmainMenuBar;
	}

	/**
	 * This method initializes jMenuFile
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getJMenuFile() {
		if (jMenuFile == null) {
			jMenuFile = new JMenu();
			jMenuFile.setText(Messages.getString("MainFrame.menu.file")); //$NON-NLS-1$
			jMenuFile.add(getJMenuItemOpen());
			jMenuFile.add(getJMenuItemClose());
			jMenuFile.addSeparator();
			jMenuFile.add(getJMenuItemOpenMass());
			jMenuFile.addSeparator();
			jMenuFile.add(getJMenuItemOpenFilterSet());
			jMenuFile.add(getJMenuItemSaveFilterSet());
			jMenuFile.addSeparator();

			jMenuFile.add(getJMenuItemSettings());
			jMenuFile.addSeparator();
			jMenuFile.add(getJMenuItemExit());
		}
		return jMenuFile;
	}

	/**
	 * This method initializes jMenuItemOpen
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemOpen() {
		if (jMenuItemOpen == null) {
			jMenuItemOpen = new JMenuItem();
			jMenuItemOpen.setText(Messages.getString("MainFrame.menu.open")); //$NON-NLS-1$
			jMenuItemOpen.setAccelerator(KeyStroke
					.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
			registerAction(jMenuItemOpen, Actions.OPEN);
		}
		return jMenuItemOpen;
	}

	/**
	 * This method initializes jMenuItemOpenMass
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemOpenMass() {
		if (jMenuItemOpenMass == null) {
			jMenuItemOpenMass = new JMenuItem();
			jMenuItemOpenMass.setText(Messages.getString("MainFrame.menu.openmass")); //$NON-NLS-1$
			registerAction(jMenuItemOpenMass, Actions.NOT_IMPLEMENTED);
		}
		return jMenuItemOpenMass;
	}

	/**
	 * This method initializes jMenuItemSettings
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemSettings() {
		if (jMenuItemSettings == null) {
			jMenuItemSettings = new JMenuItem();
			jMenuItemSettings.setText(Messages.getString("MainFrame.menu.settings")); //$NON-NLS-1$
			registerAction(jMenuItemSettings, Actions.SETTINGS);
		}
		return jMenuItemSettings;
	}

	/**
	 * This method initializes jMenuItemExit
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemExit() {
		if (jMenuItemExit == null) {
			jMenuItemExit = new JMenuItem();
			jMenuItemExit.setText(Messages.getString("MainFrame.menu.exit")); //$NON-NLS-1$
			registerAction(jMenuItemExit, Actions.EXIT);
		}
		return jMenuItemExit;
	}

	/**
	 * This method initializes jMenuDatabase
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getJMenuDatabase() {
		if (jMenuDatabase == null) {
			jMenuDatabase = new JMenu();
			jMenuDatabase.setText(Messages.getString("MainFrame.menu.storage")); //$NON-NLS-1$
			jMenuDatabase.add(getJMenuItemAvailableFilters());
			jMenuDatabase.add(getJMenuItemAvailableModules());
			jMenuDatabase.addSeparator();

			jMenuDatabase.add(getJMenuItemDefectClasses());
			jMenuDatabase.add(getJMenuItemDefectTypes());
			jMenuDatabase.addSeparator();

			jMenuDatabase.add(getJMenuItemSurfaceClasses());
			jMenuDatabase.add(getJMenuItemSurfaceTypes());
			jMenuDatabase.addSeparator();

			jMenuDatabase.add(getJMenuItemMaterials());
		}
		return jMenuDatabase;
	}

	/**
	 * This method initializes jMenuItemMaterials
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemMaterials() {
		if (jMenuItemMaterials == null) {
			jMenuItemMaterials = new JMenuItem();
			jMenuItemMaterials.setText(Messages.getString("MainFrame.menu.materials")); //$NON-NLS-1$
			this.registerAction(jMenuItemMaterials, Actions.NOT_IMPLEMENTED);
		}
		return jMenuItemMaterials;
	}

	private JMenu jMenuHelp = null;
	private JMenuItem jMenuItemHelp = null;
	private JMenuItem jMenuItemAbout = null;
	private JPanel jPanelStatus = null;
	private JLabel jStatus = null;
	private JCheckBox jCheckBoxScale = null;
	private JProgressBar jProgressBarMain = null;
	private JLabel jLabelTmp = null;
	private JPanel jPanelCommon = null;
	private JPanel jPanelLeft = null;
	private JScrollPane jScrollPaneImageSource = null;
	private JPanel jPanelFilters = null;
	private JList jListFilters = null;
	private JButton jButtonFiltersChange = null;
	private JScrollPane jScrollPaneFilters = null;
	private JPanel jPanelSensors = null;
	private JComboBox jComboBoxSensor = null;
	private JButton jButtonSensorSearch = null;
	private JCheckBox jCheckBoxSensor = null;
	private JPanel jPanelSurface = null;
	private JComboBox jComboBoxSurface = null;
	private JButton jButtonSurface = null;
	private JCheckBox jCheckBoxSurface = null;
	private JCheckBox jCheckBoxHistogram = null;
	private JPanel jPanelLeftOthers = null;
	private JButton jButtonTeach = null;
	private ImagePanel jPanelImageSource = null;

	/**
	 * This method initializes jMenuHelp
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getJMenuHelp() {
		if (jMenuHelp == null) {
			jMenuHelp = new JMenu();
			jMenuHelp.setText(Messages.getString("MainFrame.menu.help")); //$NON-NLS-1$
			jMenuHelp.add(getJMenuItemHelp());
			jMenuHelp.addSeparator();
			jMenuHelp.add(getJMenuItemAbout());
			jMenuHelp.addSeparator();
			jMenuHelp.add(getJMenuTest());
		}
		return jMenuHelp;
	}

	/**
	 * This method initializes jMenuItemHelp
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemHelp() {
		if (jMenuItemHelp == null) {
			jMenuItemHelp = new JMenuItem();
			jMenuItemHelp.setText(Messages.getString("MainFrame.menu.helpImpl")); //$NON-NLS-1$
			jMenuItemHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
			registerAction(jMenuItemHelp, Actions.HELP);
		}
		return jMenuItemHelp;
	}

	/**
	 * This method initializes jMenuItemAbout
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemAbout() {
		if (jMenuItemAbout == null) {
			jMenuItemAbout = new JMenuItem();
			jMenuItemAbout.setText(Messages.getString("MainFrame.menu.about")); //$NON-NLS-1$
			registerAction(jMenuItemAbout, Actions.ABOUT);
		}
		return jMenuItemAbout;
	}

	/**
	 * This method initializes jPanelStatus
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelStatus() {
		if (jPanelStatus == null) {
			GridBagConstraints gridBagConstraints110 = new GridBagConstraints();
			gridBagConstraints110.gridx = 3;
			gridBagConstraints110.insets = new Insets(0, 15, 0, 15);
			gridBagConstraints110.gridy = 0;
			jLabelImageInfo = new JLabel();
			jLabelImageInfo.setText(""); //$NON-NLS-1$
			jLabelImageInfo.setName("labelImageInfo"); //$NON-NLS-1$

			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.weightx = 1.0D;
			gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
			jLabelTmp = new JLabel();
			jLabelTmp.setText(""); //$NON-NLS-1$
			jLabelTmp.setVisible(true);
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 4;
			gridBagConstraints2.fill = GridBagConstraints.NONE;
			gridBagConstraints2.anchor = GridBagConstraints.EAST;

			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 2;
			gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints1.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints1.weightx = 1.0D;

			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.insets = new Insets(0, 5, 0, 5);
			jStatus = new JLabel();
			jStatus.setText("Текст для примера!"); //$NON-NLS-1$

			jStatus.setFont(new Font("Dialog", Font.BOLD, 12)); //$NON-NLS-1$
			jPanelStatus = new JPanel();
			jPanelStatus.setLayout(new GridBagLayout());
			jPanelStatus.add(jStatus, gridBagConstraints);
			jPanelStatus.add(jLabelTmp, gridBagConstraints3);
			jPanelStatus.add(getJProgressBarMain(), gridBagConstraints1);
			jPanelStatus.add(jLabelImageInfo, gridBagConstraints110);
			jPanelStatus.add(getJCheckBoxScale(), gridBagConstraints2);
		}
		return jPanelStatus;
	}

	private JPanel jPanelModule = null;
	private JComboBox jComboBoxModules = null;
	private JCheckBox jCheckBoxModuleGraphic = null;

	private JMenu jMenuTest = null;

	private JMenuItem jMenuItemProgress = null;

	private JLabel jLabelImageInfo = null;

	private JTabbedPane jTabbedPaneImages = null;

	private JScrollPane jScrollPaneImageFiltered = null;

	private ImagePanel jPanelImageFiltered = null;

	private JButton jButtonModuleParams = null;

	private JButton jButtonCompare = null;

	/**
	 * This method initializes jCheckBoxScale
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxScale() {
		if (jCheckBoxScale == null) {
			jCheckBoxScale = new JCheckBox();
			if (jPanelImageSource != null && jPanelImageFiltered != null) {
				jPanelImageSource.registerFitButton(jCheckBoxScale);
				jPanelImageFiltered.registerFitButton(jCheckBoxScale);
			} else {
				AppHelper.showErrorDialog(this, Messages.getString("MainFrame.err.notInitialized")); //$NON-NLS-1$
			}
			jCheckBoxScale.setText(Messages.getString("MainFrame.scaleButton")); //$NON-NLS-1$
			jCheckBoxScale.setHorizontalAlignment(SwingConstants.LEADING);
			jCheckBoxScale.setMnemonic(KeyEvent.VK_UNDEFINED);
			jCheckBoxScale.setName(Config.DEFAULT_SCALE_BUTTON_NAME);
			jCheckBoxScale.setHorizontalTextPosition(SwingConstants.TRAILING);
		}
		return jCheckBoxScale;
	}

	// private void switchScaleCheckBox() {
	// jPanelImage.setFitMode(jCheckBoxScale.isSelected());
	// }

	/**
	 * This method initializes jProgressBarMain
	 * 
	 * @return javax.swing.JProgressBar
	 */
	private JProgressBar getJProgressBarMain() {
		if (jProgressBarMain == null) {
			jProgressBarMain = new JProgressBar();
			jProgressBarMain.setValue(15);
			jProgressBarMain.setStringPainted(true);
			jProgressBarMain.setVisible(false);
		}
		return jProgressBarMain;
	}

	public void setProgress(int value) {
		this.jProgressBarMain.setValue(value);
	}

	public void startProgress(int max) {
		this.jProgressBarMain.setValue(0);
		this.jProgressBarMain.setMaximum(max);

		this.jLabelTmp.setVisible(false);
		this.jProgressBarMain.setVisible(true);
	}

	public void stopProgress() {
		this.jProgressBarMain.setVisible(false);
		this.jLabelTmp.setVisible(true);
	}

	/**
	 * This method initializes jPanelCommon
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelCommon() {
		if (jPanelCommon == null) {
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.fill = GridBagConstraints.BOTH;
			gridBagConstraints9.weighty = 1.0;
			gridBagConstraints9.weightx = 1.0;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.anchor = GridBagConstraints.NORTH;
			gridBagConstraints4.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints4.gridy = 0;
			jPanelCommon = new JPanel();
			jPanelCommon.setLayout(new GridBagLayout());
			jPanelCommon.add(getJPanelLeft(), gridBagConstraints4);
			jPanelCommon.add(getJTabbedPaneImages(), gridBagConstraints9);
		}
		return jPanelCommon;
	}

	/**
	 * This method initializes jPanelLeft
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelLeft() {
		if (jPanelLeft == null) {
			GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
			gridBagConstraints27.gridy = 6;
			gridBagConstraints27.weighty = 0.0D;
			gridBagConstraints27.weightx = 1.0D;
			gridBagConstraints27.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints27.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints27.gridx = 0;
			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.gridx = 0;
			gridBagConstraints20.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints20.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints20.weightx = 1.0D;
			gridBagConstraints20.gridy = 4;
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.gridx = 0;
			gridBagConstraints16.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints16.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints16.gridy = 3;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints11.fill = GridBagConstraints.BOTH;
			gridBagConstraints11.weightx = 1.0D;
			gridBagConstraints11.weighty = 1.0D;
			gridBagConstraints11.gridy = 1;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridy = 2;
			gridBagConstraints5.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints5.weightx = 1.0D;
			gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.gridx = 0;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = -1;
			gridBagConstraints6.gridy = -1;
			jPanelLeft = new JPanel();
			jPanelLeft.setLayout(new GridBagLayout());
			jPanelLeft.setPreferredSize(new Dimension(200, 200));
			jPanelLeft.setMinimumSize(new Dimension(200, 200));
			jPanelLeft.add(getJPanelFilters(), gridBagConstraints11);
			jPanelLeft.add(getJPanelModule(), gridBagConstraints5);
			jPanelLeft.add(getJPanelSensors(), gridBagConstraints16);
			jPanelLeft.add(getJPanelSurface(), gridBagConstraints20);
			jPanelLeft.add(getJPanelLeftOthers(), gridBagConstraints27);
		}
		return jPanelLeft;
	}

	/**
	 * This method initializes jScrollPaneImageSource
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneImageSource() {
		if (jScrollPaneImageSource == null) {
			jScrollPaneImageSource = new JScrollPane();
			jScrollPaneImageSource
					.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			jScrollPaneImageSource
					.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			jScrollPaneImageSource.setViewportView(getJPanelImageSource());
		}
		return jScrollPaneImageSource;
	}

	/**
	 * This method initializes jPanelFilters
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelFilters() {
		if (jPanelFilters == null) {
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.fill = GridBagConstraints.BOTH;
			gridBagConstraints10.weighty = 1.0D;
			gridBagConstraints10.gridx = 0;
			gridBagConstraints10.gridy = 0;
			gridBagConstraints10.gridwidth = 3;
			gridBagConstraints10.gridheight = 1;
			gridBagConstraints10.weightx = 1.0D;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridy = 1;
			gridBagConstraints8.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints8.gridx = 0;
			jPanelFilters = new JPanel();
			jPanelFilters.setLayout(new GridBagLayout());
			jPanelFilters.setBorder(BorderFactory.createTitledBorder(Messages
					.getString("MainFrame.filters"))); //$NON-NLS-1$
			jPanelFilters.setPreferredSize(new Dimension(183, 120));
			jPanelFilters.add(getJScrollPaneFilters(), gridBagConstraints10);
			jPanelFilters.add(getJButtonFiltersChange(), gridBagConstraints8);
		}
		return jPanelFilters;
	}

	protected FiltersModel filtersModel = null;

	private JMenuItem jMenuItemAvailableFilters = null;

	private JMenuItem jMenuItemAvailableModules = null;

	private JMenuItem jMenuItemOpenFilterSet = null;

	private JMenuItem jMenuItemSaveFilterSet = null;

	private JMenuItem jMenuItemClose = null;

	private JMenuItem jMenuItemDefectClasses = null;

	private JMenuItem jMenuItemDefectTypes = null;

	private JMenuItem jMenuItemSurfaceClasses = null;

	private JMenuItem jMenuItemSurfaceTypes = null;

	static class FiltersModel extends AbstractListModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private FilterChainsaw chainsaw = null;

		protected void setChainsaw(FilterChainsaw chainsaw) {
			this.chainsaw = chainsaw;
		}

		protected FilterChainsaw getChainsaw() {
			if (chainsaw == null) {
				throw new IllegalStateException("Internal error. Chainsaw is diappeared."); //$NON-NLS-1$
			}
			return chainsaw;
		}

		protected void updateFiltersImpl() {
			super.fireContentsChanged(this, 0, getChainsaw().getFilterCount());
		}

		@Override
		public Object getElementAt(int index) {
			return chainsaw == null ? null : chainsaw.getFilter(index);
		}

		@Override
		public int getSize() {
			return chainsaw == null ? 0 : chainsaw.getFilterCount();
		}

	}

	private FiltersModel getFiltersModel() {
		if (filtersModel == null) {
			filtersModel = new FiltersModel();
		}
		return filtersModel;
	}

	/**
	 * This method initializes jListFilters
	 * 
	 * @return javax.swing.JList
	 */
	private JList getJListFilters() {
		if (jListFilters == null) {
			jListFilters = new JList(getFiltersModel());
			jListFilters.setName("filtersList"); //$NON-NLS-1$
			jListFilters.setCellRenderer(new DefaultListCellRenderer() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public Component getListCellRendererComponent(JList list, Object value, int index,
						boolean isSelected, boolean cellHasFocus) {
					return super.getListCellRendererComponent(list, ((IFilter) value).getName(),
							index, isSelected, cellHasFocus);
				}
			});

		}
		return jListFilters;
	}

	/**
	 * This method initializes jButtonFiltersChange
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonFiltersChange() {
		if (jButtonFiltersChange == null) {
			jButtonFiltersChange = new JButton();
			jButtonFiltersChange.setText(Messages.getString("MainFrame.filtersChange")); //$NON-NLS-1$
			this.registerAction(jButtonFiltersChange, Actions.EDIT_FILTERS);
		}
		return jButtonFiltersChange;
	}

	/**
	 * This method initializes jScrollPaneFilters
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneFilters() {
		if (jScrollPaneFilters == null) {
			jScrollPaneFilters = new JScrollPane();
			jScrollPaneFilters.setViewportView(getJListFilters());
		}
		return jScrollPaneFilters;
	}

	/**
	 * This method initializes jPanelSensors
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSensors() {
		if (jPanelSensors == null) {
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 0;
			gridBagConstraints13.gridwidth = 2;
			gridBagConstraints13.anchor = GridBagConstraints.WEST;
			gridBagConstraints13.gridy = 2;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 1;
			gridBagConstraints12.gridy = 0;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.gridy = 0;
			gridBagConstraints7.weightx = 1.0;
			jPanelSensors = new JPanel();
			jPanelSensors.setLayout(new GridBagLayout());
			jPanelSensors.setBorder(BorderFactory.createTitledBorder(Messages
					.getString("MainFrame.sensor"))); //$NON-NLS-1$
			jPanelSensors.add(getJComboBoxSensor(), gridBagConstraints7);
			jPanelSensors.add(getJButtonSensorSearch(), gridBagConstraints12);
			jPanelSensors.add(getJCheckBoxSensor(), gridBagConstraints13);
		}
		return jPanelSensors;
	}

	/**
	 * This method initializes jComboBoxSensor
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBoxSensor() {
		if (jComboBoxSensor == null) {
			jComboBoxSensor = new JComboBox(new String[] { Messages
					.getString("MainFrame.resentUsedSensors") }); //$NON-NLS-1$
		}
		return jComboBoxSensor;
	}

	/**
	 * This method initializes jButtonSensorSearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonSensorSearch() {
		if (jButtonSensorSearch == null) {
			jButtonSensorSearch = new JButton();
			jButtonSensorSearch.setText(Messages.getString("MainFrame.find")); //$NON-NLS-1$
			this.registerAction(jButtonSensorSearch, Actions.NOT_IMPLEMENTED);
		}
		return jButtonSensorSearch;
	}

	/**
	 * This method initializes jCheckBoxSensor
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxSensor() {
		if (jCheckBoxSensor == null) {
			jCheckBoxSensor = new JCheckBox();
			jCheckBoxSensor.setText(Messages.getString("MainFrame.details")); //$NON-NLS-1$
		}
		return jCheckBoxSensor;
	}

	/**
	 * This method initializes jPanelSurface
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSurface() {
		if (jPanelSurface == null) {
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.gridx = 0;
			gridBagConstraints17.gridwidth = 2;
			gridBagConstraints17.anchor = GridBagConstraints.WEST;
			gridBagConstraints17.gridy = 2;
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.gridx = 1;
			gridBagConstraints15.gridy = 0;
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints14.gridy = 0;
			gridBagConstraints14.weightx = 1.0;
			gridBagConstraints14.gridx = 0;
			jPanelSurface = new JPanel();
			jPanelSurface.setLayout(new GridBagLayout());
			jPanelSurface.setBorder(BorderFactory.createTitledBorder(Messages
					.getString("MainFrame.surface"))); //$NON-NLS-1$
			jPanelSurface.add(getJComboBoxSurface(), gridBagConstraints14);
			jPanelSurface.add(getJButtonSurface(), gridBagConstraints15);
			jPanelSurface.add(getJCheckBoxSurface(), gridBagConstraints17);
		}
		return jPanelSurface;
	}

	/**
	 * This method initializes jComboBoxSurface
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBoxSurface() {
		if (jComboBoxSurface == null) {
			jComboBoxSurface = new JComboBox(new String[] { Messages
					.getString("MainFrame.resentUsedSurfaces") }); //$NON-NLS-1$
		}
		return jComboBoxSurface;
	}

	/**
	 * This method initializes jButtonSurface
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonSurface() {
		if (jButtonSurface == null) {
			jButtonSurface = new JButton();
			jButtonSurface.setText(Messages.getString("MainFrame.find")); //$NON-NLS-1$
			registerAction(jButtonSurface, Actions.NOT_IMPLEMENTED);
		}
		return jButtonSurface;
	}

	/**
	 * This method initializes jCheckBoxSurface
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxSurface() {
		if (jCheckBoxSurface == null) {
			jCheckBoxSurface = new JCheckBox();
			jCheckBoxSurface.setText(Messages.getString("MainFrame.details")); //$NON-NLS-1$
		}
		return jCheckBoxSurface;
	}

	/**
	 * This method initializes jCheckBoxHistogram
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxHistogram() {
		if (jCheckBoxHistogram == null) {
			jCheckBoxHistogram = new JCheckBox();
			jCheckBoxHistogram.setName("histogram"); //$NON-NLS-1$
			jCheckBoxHistogram.setText(Messages.getString("MainFrame.histogram")); //$NON-NLS-1$
		}
		return jCheckBoxHistogram;
	}

	/**
	 * This method initializes jPanelLeftOthers
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelLeftOthers() {
		if (jPanelLeftOthers == null) {
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.gridx = 0;
			gridBagConstraints18.insets = new Insets(5, 0, 5, 0);
			gridBagConstraints18.gridy = 2;
			GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
			gridBagConstraints24.gridx = 0;
			gridBagConstraints24.fill = GridBagConstraints.NONE;
			gridBagConstraints24.insets = new Insets(5, 0, 0, 0);
			gridBagConstraints24.anchor = GridBagConstraints.NORTH;
			gridBagConstraints24.gridy = 1;
			jPanelLeftOthers = new JPanel();
			jPanelLeftOthers.setLayout(new GridBagLayout());
			jPanelLeftOthers.add(getJButtonTeach(), gridBagConstraints24);
			jPanelLeftOthers.add(getJButtonCompare(), gridBagConstraints18);
		}
		return jPanelLeftOthers;
	}

	/**
	 * This method initializes jButtonTeach
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonTeach() {
		if (jButtonTeach == null) {
			jButtonTeach = new JButton();
			jButtonTeach.setText(Messages.getString("MainFrame.newDefect")); //$NON-NLS-1$
			jButtonTeach.setEnabled(false);
			registerAction(jButtonTeach, Actions.NOT_IMPLEMENTED);
		}
		return jButtonTeach;
	}

	/**
	 * This method initializes jPanelImageSource
	 * 
	 * @return javax.swing.JPanel
	 */
	private ImagePanel getJPanelImageSource() {
		if (jPanelImageSource == null) {
			jPanelImageSource = new ImagePanel();
			jPanelImageSource.setLayout(new GridBagLayout());
		}
		return jPanelImageSource;
	};

	/**
	 * This method initializes jPanelModule
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelModule() {
		if (jPanelModule == null) {
			GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
			gridBagConstraints28.gridx = 0;
			gridBagConstraints28.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints28.gridy = 1;
			GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
			gridBagConstraints26.gridx = 0;
			gridBagConstraints26.anchor = GridBagConstraints.WEST;
			gridBagConstraints26.gridy = 3;
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.anchor = GridBagConstraints.WEST;
			gridBagConstraints22.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints22.gridx = 0;
			gridBagConstraints22.gridy = 2;
			gridBagConstraints22.weightx = 1.0D;
			gridBagConstraints22.fill = GridBagConstraints.NONE;
			GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
			gridBagConstraints25.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints25.anchor = GridBagConstraints.WEST;
			gridBagConstraints25.gridwidth = 1;
			gridBagConstraints25.gridx = 0;
			gridBagConstraints25.gridy = 0;
			gridBagConstraints25.weightx = 1.0;
			jPanelModule = new JPanel();
			jPanelModule.setLayout(new GridBagLayout());
			jPanelModule.setBorder(BorderFactory.createTitledBorder(Messages
					.getString("MainFrame.module"))); //$NON-NLS-1$
			jPanelModule.setMaximumSize(new Dimension(200, 50));
			jPanelModule.add(getJComboBoxModules(), gridBagConstraints25);
			jPanelModule.add(getJButtonModuleParams(), gridBagConstraints28);
			jPanelModule.add(getJCheckBoxHistogram(), gridBagConstraints22);
			jPanelModule.add(getJCheckBoxModuleGraphic(), gridBagConstraints26);
		}
		return jPanelModule;
	}

	/**
	 * This method initializes jComboBoxModules
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBoxModules() {
		if (jComboBoxModules == null) {
			jComboBoxModules = new JComboBox(AppDataStorage.getInstance().listLModules());
			jComboBoxModules.setName("modules"); //$NON-NLS-1$
			jComboBoxModules.addActionListener(new ActionListener() {

				@SuppressWarnings("unchecked")
				@Override
				public void actionPerformed(ActionEvent e) {

					final LModules item = (LModules) jComboBoxModules.getSelectedItem();
					if (item == null) {
						return;
					}

					final IModule currentModule = MainFrame.this.mainModuleProcessor.getModule();
					if (currentModule != null
							&& currentModule.getClass().getName().equals(item.getCodename())) {
						logger.trace("Do not check module, it's already checked."); //$NON-NLS-1$
						return;
					}

					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							IModule currentModule = mainModuleProcessor.getModule();
							try {
								MainFrame.this.setModule((Class<IModule>) Class.forName(item
										.getCodename()));
							} catch (Throwable t) {
								String messageOnRestore = ""; //$NON-NLS-1$
								if (currentModule != null) {
									try {
										MainFrame.this.setModule(currentModule.getClass());
									} catch (Throwable t1) {
										logger.error("Error when restoring module", t1); //$NON-NLS-1$
										messageOnRestore = Messages.getString(
												"MainFrame.err.setModule.restore", t1.getMessage()); //$NON-NLS-1$
										MainFrame.this.closeProcessing();
									}
								}
								String name = item.getCodename() + " (" + item.getName() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
								AppHelper.showExceptionDialog(MainFrame.this, Messages.getString(
										"MainFrame.err.setModule", name, messageOnRestore), t); //$NON-NLS-1$
							}
						}
					});

				}
			});

			jComboBoxModules.setRenderer(new BasicComboBoxRenderer() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public Component getListCellRendererComponent(JList list, Object value, int index,
						boolean isSelected, boolean cellHasFocus) {
					return super.getListCellRendererComponent(list, ((LModules) value).getName(),
							index, isSelected, cellHasFocus);
				}
			});
		}
		return jComboBoxModules;
	}

	/**
	 * This method initializes jCheckBoxModuleGraphic
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxModuleGraphic() {
		if (jCheckBoxModuleGraphic == null) {
			jCheckBoxModuleGraphic = new JCheckBox();
			jCheckBoxModuleGraphic.setText(Messages.getString("MainFrame.moduleImage")); //$NON-NLS-1$
			jCheckBoxModuleGraphic.setName("moduleImage"); //$NON-NLS-1$
		}
		return jCheckBoxModuleGraphic;
	}

	/**
	 * This method initializes jMenuTest
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getJMenuTest() {
		if (jMenuTest == null) {
			jMenuTest = new JMenu();
			jMenuTest.setText("Тест"); //$NON-NLS-1$
			jMenuTest.add(getJMenuItemProgress());
		}
		return jMenuTest;
	}

	/**
	 * This method initializes jMenuItemProgress
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemProgress() {
		if (jMenuItemProgress == null) {
			jMenuItemProgress = new JMenuItem();
			jMenuItemProgress.setAction(new AbstractAction() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					new Thread(new Runnable() {

						public void run() {
							MainFrame.this.startProgress(100);
							MainFrame.this.setProgress(15);
							try {
								Thread.sleep(1500);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
							MainFrame.this.stopProgress();
						};
					}).start();
				}
			});
			jMenuItemProgress.setText("Тест ProgressBar"); //$NON-NLS-1$
		}
		return jMenuItemProgress;
	}

	/**
	 * This method initializes jTabbedPaneImages
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPaneImages() {
		if (jTabbedPaneImages == null) {
			jTabbedPaneImages = new JTabbedPane();
			jTabbedPaneImages.addTab(Messages.getString("MainFrame.image.source"), null, //$NON-NLS-1$
					getJScrollPaneImageSource(), null);
			jTabbedPaneImages.addTab(Messages.getString("MainFrame.image.filtered"), null, //$NON-NLS-1$
					getJScrollPaneImageFiltered(), null);
			jTabbedPaneImages.setSelectedIndex(1);
			jTabbedPaneImages.setName("tabbedPaneImages"); //$NON-NLS-1$
			jTabbedPaneImages.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					jLabelImageInfo
							.setText(jTabbedPaneImages.getSelectedIndex() == 0 ? MainFrame.this.originalImageInfo
									: MainFrame.this.filteredImageInfo);

				}
			});
		}
		return jTabbedPaneImages;
	}

	/**
	 * This method initializes jScrollPaneImageFiltered
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneImageFiltered() {
		if (jScrollPaneImageFiltered == null) {
			jScrollPaneImageFiltered = new JScrollPane();
			jScrollPaneImageFiltered
					.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			jScrollPaneImageFiltered.setViewportView(getJPanelImageFiltered());
			jScrollPaneImageFiltered
					.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		}
		return jScrollPaneImageFiltered;
	}

	/**
	 * This method initializes jPanelImageFiltered
	 * 
	 * @return javax.swing.JPanel
	 */
	private ImagePanel getJPanelImageFiltered() {
		if (jPanelImageFiltered == null) {
			jPanelImageFiltered = new ImagePanel();
			jPanelImageFiltered.setLayout(new GridBagLayout());
		}
		return jPanelImageFiltered;
	}

	/**
	 * This method initializes jButtonModuleParams
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonModuleParams() {
		if (jButtonModuleParams == null) {
			jButtonModuleParams = new JButton();
			jButtonModuleParams.setText(Messages.getString("MainFrame.moduleParameters")); //$NON-NLS-1$
			this.registerAction(jButtonModuleParams, Actions.EDIT_MODULE_PARAMS);
		}
		return jButtonModuleParams;
	}

	/**
	 * This method initializes jButtonCompare
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonCompare() {
		if (jButtonCompare == null) {
			jButtonCompare = new JButton();
			jButtonCompare.setText(Messages.getString("MainFrame.processDefects")); //$NON-NLS-1$
			jButtonCompare.setEnabled(false);
			registerAction(jButtonCompare, Actions.NOT_IMPLEMENTED);
		}
		return jButtonCompare;
	}

	/**
	 * This method initializes jMenuItemAvailableFilters
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemAvailableFilters() {
		if (jMenuItemAvailableFilters == null) {
			jMenuItemAvailableFilters = new JMenuItem();
			jMenuItemAvailableFilters.setText(Messages.getString("MainFrame.menu.filters")); //$NON-NLS-1$
			registerAction(jMenuItemAvailableFilters, Actions.FILTER_LIST);
		}
		return jMenuItemAvailableFilters;
	}

	/**
	 * This method initializes jMenuItemAvailableModules
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemAvailableModules() {
		if (jMenuItemAvailableModules == null) {
			jMenuItemAvailableModules = new JMenuItem();
			jMenuItemAvailableModules.setText(Messages.getString("MainFrame.menu.modules")); //$NON-NLS-1$
			registerAction(jMenuItemAvailableModules, Actions.MODULE_LIST);
		}
		return jMenuItemAvailableModules;
	}

	/**
	 * This method initializes jMenuItemOpenFilterSet
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemOpenFilterSet() {
		if (jMenuItemOpenFilterSet == null) {
			jMenuItemOpenFilterSet = new JMenuItem();
			jMenuItemOpenFilterSet.setText(Messages.getString("MainFrame.menu.loadFilterSet")); //$NON-NLS-1$
			registerAction(jMenuItemOpenFilterSet, Actions.OPEN_FILTER_SET);
		}
		return jMenuItemOpenFilterSet;
	}

	/**
	 * This method initializes jMenuItemSaveFilterSet
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemSaveFilterSet() {
		if (jMenuItemSaveFilterSet == null) {
			jMenuItemSaveFilterSet = new JMenuItem();
			jMenuItemSaveFilterSet.setText(Messages.getString("MainFrame.menu.saveFilterSet")); //$NON-NLS-1$
			registerAction(jMenuItemSaveFilterSet, Actions.SAVE_FILTER_SET);
		}
		return jMenuItemSaveFilterSet;
	}

	/**
	 * This method initializes jMenuItemClose
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemClose() {
		if (jMenuItemClose == null) {
			jMenuItemClose = new JMenuItem();
			jMenuItemClose.setText(Messages.getString("MainFrame.menu.close")); //$NON-NLS-1$
			jMenuItemClose.setEnabled(false);
			registerAction(jMenuItemClose, Actions.CLOSE);
		}
		return jMenuItemClose;
	}

	private JFileChooser fSChooser = null;

	private JFileChooser getfSChooser() {
		if (fSChooser == null) {
			fSChooser = new JFileChooser();

			fSChooser.setCurrentDirectory(new File(".")); //$NON-NLS-1$
			fSChooser.setMultiSelectionEnabled(false);
			fSChooser.addChoosableFileFilter(new FileNameExtensionFilter(Messages
					.getString("MainFrame.filterSet.filter"), //$NON-NLS-1$
					"settings")); //$NON-NLS-1$

			AppHelper.getInstance().registerAdditionalComponent(fSChooser);

		}
		fSChooser.repaint();
		return fSChooser;
	}

	/**
	 * This method initializes jMenuItemDefectClasses
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemDefectClasses() {
		if (jMenuItemDefectClasses == null) {
			jMenuItemDefectClasses = new JMenuItem();
			jMenuItemDefectClasses.setMnemonic(KeyEvent.VK_UNDEFINED);
			jMenuItemDefectClasses.setText(Messages.getString("MainFrame.menu.defectClasses")); //$NON-NLS-1$
			registerAction(jMenuItemDefectClasses, Actions.DEFECT_CLASSES);
		}
		return jMenuItemDefectClasses;
	}

	/**
	 * This method initializes jMenuItemDefectTypes
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemDefectTypes() {
		if (jMenuItemDefectTypes == null) {
			jMenuItemDefectTypes = new JMenuItem();
			jMenuItemDefectTypes.setText(Messages.getString("MainFrame.menu.defectTypes")); //$NON-NLS-1$
			registerAction(jMenuItemDefectTypes, Actions.DEFECT_TYPES);
		}
		return jMenuItemDefectTypes;
	}

	/**
	 * This method initializes jMenuItemSurfaceClasses
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemSurfaceClasses() {
		if (jMenuItemSurfaceClasses == null) {
			jMenuItemSurfaceClasses = new JMenuItem();
			jMenuItemSurfaceClasses.setText(Messages.getString("MainFrame.menu.surfaceClasses")); //$NON-NLS-1$
			registerAction(jMenuItemSurfaceClasses, Actions.SURFACE_CLASSES);
		}
		return jMenuItemSurfaceClasses;
	}

	/**
	 * This method initializes jMenuItemSurfaceTypes
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemSurfaceTypes() {
		if (jMenuItemSurfaceTypes == null) {
			jMenuItemSurfaceTypes = new JMenuItem();
			jMenuItemSurfaceTypes.setText(Messages.getString("MainFrame.menu.surfaceType")); //$NON-NLS-1$
			registerAction(jMenuItemSurfaceTypes, Actions.SURFACE_TYPES);
		}
		return jMenuItemSurfaceTypes;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();

		if (command == null) {
			return;
		}

		Actions action = null;
		try {
			action = Actions.valueOf(command);
		} catch (IllegalArgumentException iae) {
			logger.debug("Unknown command received: " + command); //$NON-NLS-1$
			return;
		}

		if (action == Actions.OPEN) {
			// Open new image
			try {
				this.printMessage(Messages.getString("MainFrame.mess.openNewImage")); //$NON-NLS-1$
				this.startProcessingImpl(this.singleFilePicker.getSingleSource());
				this.jMenuItemClose.setEnabled(true);
			} catch (Throwable t) {
				this.closeProcessing();
				AppHelper.showExceptionDialog(this, Messages
						.getString("MainFrame.err.openNewImage"), t); //$NON-NLS-1$
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
									"MainFrame.confirm.reWriteFilterSet", //$NON-NLS-1$  
									newFile.getName()), Messages
									.getString("MainFrame.title.rewriteFilterSet"), //$NON-NLS-2$
									JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
						logger.debug("Actual saving file " + newFile.getAbsolutePath()); //$NON-NLS-1$
						this.mainModuleProcessor.saveSettingsToFile(newFile);
					}
				} catch (Throwable t) {
					AppHelper.showExceptionDialog(this,

					Messages.getString("MainFrame.err.rewriteFilterSet", newFile.getName()), t); //$NON-NLS-1$
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
			SettingsDialog settings = (SettingsDialog) AppHelper.getInstance().getDialog(
					SettingsDialog.class);
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
			LFiltersList filterList = (LFiltersList) AppHelper.getInstance().getDialog(
					LFiltersList.class);
			if (filterList != null) {
				filterList.showDialogCancelOnly();
			}
		} else if (action == Actions.MODULE_LIST) {
			// View all registered modules
			LModulesList modulesList = (LModulesList) AppHelper.getInstance().getDialog(
					LModulesList.class);
			if (modulesList != null) {
				modulesList.showDialogCancelOnly();
			}
		} else if (action == Actions.DEFECT_CLASSES) {
			//
			DefectClassesTable defectClasses = (DefectClassesTable) AppHelper.getInstance()
					.getDialog(DefectClassesTable.class);
			if (defectClasses != null) {
				defectClasses.openDialog();
			}
		} else if (action == Actions.DEFECT_TYPES) {
			//
			DefectTypesTable defectTypes = (DefectTypesTable) AppHelper.getInstance().getDialog(
					DefectTypesTable.class);
			if (defectTypes != null) {
				defectTypes.openDialog();
			}
		} else if (action == Actions.SURFACE_CLASSES) {
			//
			SurfaceClassesTable surfaceClasses = (SurfaceClassesTable) AppHelper.getInstance()
					.getDialog(SurfaceClassesTable.class);
			if (surfaceClasses != null) {
				surfaceClasses.openDialog();
			}
		} else if (action == Actions.SURFACE_TYPES) {
			//
			SurfaceTypesTable surfaceTypes = (SurfaceTypesTable) AppHelper.getInstance().getDialog(
					SurfaceTypesTable.class);
			if (surfaceTypes != null) {
				surfaceTypes.openDialog();
			}
		} else if (action == Actions.HELP) {
			// View help
			try {
				Desktop.getDesktop().browse(new URI(Const.WEB_HELP_PAGE));
			} catch (Exception e1) {
				AppHelper.showExceptionDialog(this, Messages.getString("MainFrame.err.openWebLink", //$NON-NLS-1$
						Const.WEB_HELP_PAGE), e1);
			}
		} else if (action == Actions.ABOUT) {
			// View about
			AboutDialog about = (AboutDialog) AppHelper.getInstance().getDialog(AboutDialog.class);
			if (about != null) {
				about.showDialog();
			}
		} else if (action == Actions.NOT_IMPLEMENTED) {
			// Not implemented yet. Sorry.
			AppHelper.showErrorDialog(this, Messages.getString("MainFrame.err.notImplemented")); //$NON-NLS-1$
		} else if (action == Actions.EDIT_FILTERS) {
			//
			FilterChainsawTransactional currentFilters = new FilterChainsawTransactional(
					mainModuleProcessor.getChainsaw());

			FiltersEditor editor = (FiltersEditor) AppHelper.getInstance().getDialog(
					FiltersEditor.class);
			if (editor != null
					&& editor
							.openDialog(
									Messages.getString("MainFrame.title.mainFilters"), mainModuleProcessor.getChainsaw())) { //$NON-NLS-1$
				this.restartProcessingByFilters(currentFilters);
			}
		} else if (action == Actions.EDIT_MODULE_PARAMS) {

			Collection<Param> currentParameters = null;
			try {
				currentParameters = ParamHelper.cloneParameters(mainModuleProcessor.getModule()
						.getParams());
			} catch (CloneNotSupportedException cnse) {
				AppHelper.showExceptionDialog(this, Messages
						.getString("MainFrame.err.prepareError"), cnse); //$NON-NLS-1$
				return;
			}

			ParametersEditor editor = (ParametersEditor) AppHelper.getInstance().getDialog(
					ParametersEditor.class);
			if (editor != null
					&& editor.openDialog(mainModuleProcessor.getModule().getName(),
							mainModuleProcessor.getModule().getParams())) {
				this.restartProcessingByModuleParams(currentParameters);

			}
		} else {
			AppHelper.showErrorDialog(this, Messages.getString(
					"MainFrame.err.unknownAction", action)); //$NON-NLS-1$
		}

	}
}
