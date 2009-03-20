package edu.mgupi.pass.face;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.db.locuses.LFilters;
import edu.mgupi.pass.db.locuses.LFiltersFactory;
import edu.mgupi.pass.db.locuses.LModules;
import edu.mgupi.pass.db.locuses.LModulesFactory;
import edu.mgupi.pass.db.locuses.Locuses;
import edu.mgupi.pass.filters.FilterChainsaw;
import edu.mgupi.pass.filters.java.GrayScaleFilter;
import edu.mgupi.pass.filters.service.PlaceImageFilter;
import edu.mgupi.pass.filters.service.ResizeFilter;
import edu.mgupi.pass.modules.IModule;
import edu.mgupi.pass.modules.ModuleHelper;
import edu.mgupi.pass.modules.ModuleProcessor;
import edu.mgupi.pass.sources.SourceStore;
import edu.mgupi.pass.sources.visual.SingleFilePick;
import edu.mgupi.pass.util.Config;
import edu.mgupi.pass.util.Const;
import edu.mgupi.pass.util.IProgress;

public class MainFrame extends JFrame implements IProgress {

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
		// Loading model data
		this.loadModelImpl();

		// Initializing interface

		this.setJMenuBar(getJmainMenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle(Const.FULL_PROGRAM_NAME);
		this.setName("mainFrame");
		this.setMinimumSize(new Dimension(600, 720));
		this.setBounds(new Rectangle(150, 150, 800, 720));
		Config.getInstance().getWindowPosition(this);

		// ----------------

		this.initImpl();
	}

	protected LModules[] moduleList = null;
	protected LFilters[] filterList = null;

	private void loadModelImpl() throws Exception {
		moduleList = LModulesFactory.listLModulesByQuery(null, null);
		if (moduleList == null || moduleList.length == 0) {
			throw new Exception("No registered analyze modules found. Unable to work. Please, fill table 'LModules'.");
		}

		filterList = LFiltersFactory.listLFiltersByQuery(null, null);
		if (filterList == null || filterList.length == 0) {
			throw new Exception("No registered filters found. Unable to work. Please, fill table 'LFilters'.");
		}

		Arrays.sort(moduleList, new Comparator<LModules>() {
			@Override
			public int compare(LModules o1, LModules o2) {
				if (o1 != null && o2 != null) {
					return o1.getName().compareTo(o2.getName());
				}
				return 0;
			}
		});

		Arrays.sort(filterList, new Comparator<LFilters>() {
			@Override
			public int compare(LFilters o1, LFilters o2) {
				if (o1 != null && o2 != null) {
					return o1.getName().compareTo(o2.getName());
				}
				return 0;
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void initImpl() throws Exception {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				MainFrame.this.closeImpl();
			}
		});

		Config.getInstance().setIProgressInstance(this);

		mainModuleProcessor = new ModuleProcessor();

		histogramFrame = (ImageFrameTemplate) AppHelper.getInstance().createWindow(ImageFrameTemplate.class);
		histogramFrame.registerControlCheckbox(this.jCheckBoxHistogram);
		histogramFrame.setTitle(mainModuleProcessor.getHistoFilters().toString());
		histogramFrame.setName("histogramWindow");
		Config.getInstance().getWindowPosition(histogramFrame);

		moduleFrame = (ImageFrameTemplate) AppHelper.getInstance().createWindow(ImageFrameTemplate.class);
		moduleFrame.registerControlCheckbox(this.jCheckBoxModuleGraphic);
		moduleFrame.setTitle("Модуль не выбран");
		moduleFrame.setName("moduleFrameWindow");
		Config.getInstance().getWindowPosition(moduleFrame);

		Config.getInstance().getWindowCheckBoxes(this);

		singleFilePicker = new SingleFilePick();
		singleFilePicker.init();

		LModules item = (LModules) jComboBoxModules.getSelectedItem();
		MainFrame.this.setModule((Class<IModule>) Class.forName(item.getCodename()));

		mainModuleProcessor.setPreprocessingChainsaw(new FilterChainsaw(true));
		this.applySourcePreProcessor();

		FilterChainsaw saw = new FilterChainsaw();
		saw.appendFilter(GrayScaleFilter.class);
		mainModuleProcessor.setChainsaw(saw);

		logger.debug("Main frame init done.");
		this.clearMessage();
	}

	private void closeImpl() {
		this.printMessage("Закрытие приложения");

		logger.debug("Application terminating...");

		if (singleFilePicker != null) {
			singleFilePicker.close();
		}
		this.currentSource = null;
		this.currentLocus = null;

		if (mainModuleProcessor != null) {
			try {
				this.mainModuleProcessor.close();
			} catch (Exception e) {
				logger.error("Error when closing main module", e);
				JOptionPane.showMessageDialog(null, "Error when closing main module: " + e, "Closing error",
						JOptionPane.ERROR_MESSAGE);
			}
			mainModuleProcessor = null;
		}

		// preprocessingCache.close();

		try {
			logger.debug("Saving current config...");
			AppHelper.getInstance().saveWindowPositions();
			Config.getInstance().setWindowCheckBoxes(MainFrame.this, jCheckBoxHistogram, jCheckBoxModuleGraphic);
			Config.getInstance().saveCurrentConfig();
		} catch (ConfigurationException e) {
			logger.error("Error when saving current config", e);
			JOptionPane.showMessageDialog(null, "Error when saving current config: " + e, "Config saving error",
					JOptionPane.ERROR_MESSAGE);
		}

		AppHelper.reset();
		this.clearMessage();
	}

	private SourceStore currentSource = null;
	private Locuses currentLocus = null; // @jve:decl-index=0:

	private String originalImageInfo = "";
	private String filteredImageInfo = "";

	// private CacheInitiable<IFilter> preprocessingCache = new
	// CacheInitiable<IFilter>(); // @jve:decl-index=0:

	protected void startProcessing(SourceStore source) throws Exception {

		if (source == null) {
			logger.debug("Nothing to process...");
			// JOptionPane.showMessageDialog(null,
			// "Internal error. Received null source.", "Internal error",
			// JOptionPane.ERROR_MESSAGE);
			return;
		}

		this.printMessage("Загрузка годографа...");

		logger.debug("Start loading source " + source.getName());

		this.setTitle(Const.FULL_PROGRAM_NAME + " -- " + source.getName());

		// This is must be safe!!!!
		// I can stop wherever I want!
		mainModuleProcessor.finishProcessing();
		currentLocus = null;
		currentSource = source;

		// Main cycle
		currentLocus = mainModuleProcessor.startProcessing(source);

		BufferedImage sourceImage = source.getSourceImage();
		BufferedImage filteredImage = mainModuleProcessor.getLastProcessedImage();
		originalImageInfo = "" + sourceImage.getWidth() + "x" + sourceImage.getHeight() + " "
				+ sourceImage.getColorModel().getPixelSize() + " bpp";

		filteredImageInfo = "" + filteredImage.getWidth() + "x" + filteredImage.getHeight() + " "
				+ filteredImage.getColorModel().getPixelSize() + " bpp";

		jLabelImageInfo.setText(jTabbedPaneImages.getSelectedIndex() == 0 ? this.originalImageInfo
				: this.filteredImageInfo);

		// Locuses locus = mainModuleProcessor.startProcessing(source);
		// REQUIRED SQUARE BASE!
		this.jPanelImageFiltered.setImage(filteredImage, true);
		this.jPanelImageSource.setImage(sourceImage);
		this.histogramFrame.setImage(mainModuleProcessor.getLastHistogramImage());
		this.moduleFrame.setImage(ModuleHelper.getTemporaryModuleImage(this.currentLocus));

		this.clearMessage();

	}

	private void applySourcePreProcessor() throws Exception {

		// Скорее всего, наилучшим выходом будет использование автоматического масштабирования,
		// когда мы приводим изображение к размеру 1024x1024, а потом закрашиваем 
		//   участки фоновым цветом :)

		this.printMessage("Применение фильтров-препроцессинга...");

		FilterChainsaw preProcessing = mainModuleProcessor.getPreProcessingFilters();

		String sourceScaleMode = Config.getInstance().getCurrentSourceMode(SettingsDialog.DEFAULT_SOURCE_MODE);
		int background = Config.getInstance().getCurrentBackground(SettingsDialog.DEFAULT_BACKGROUND);

		if (sourceScaleMode.equals(SettingsDialog.SOURCE_MODE_CENTER)
				|| sourceScaleMode.equals(SettingsDialog.SOURCE_MODE_LEFT_TOP)) {

			preProcessing.removeFilter(ResizeFilter.class);

			PlaceImageFilter place = (PlaceImageFilter) preProcessing.appendFilter(PlaceImageFilter.class);
			place.getWIDTH().setValue(Const.MAIN_IMAGE_WIDTH);
			place.getHEIGHT().setValue(Const.MAIN_IMAGE_HEIGHT);

			place.getBACKGROUND().setValue(new Color(background));
			place.getPLACE().setValue(sourceScaleMode.equals(SettingsDialog.SOURCE_MODE_CENTER) ? "center" : "topleft");

		} else {
			preProcessing.removeFilter(PlaceImageFilter.class);

			ResizeFilter resizer = (ResizeFilter) preProcessing.appendFilter(ResizeFilter.class);
			resizer.getWIDTH().setValue(Const.MAIN_IMAGE_WIDTH);
			resizer.getHEIGHT().setValue(Const.MAIN_IMAGE_HEIGHT);
			resizer.getINTERPOLATION_METHOD().setValue(RenderingHints.VALUE_INTERPOLATION_BICUBIC);

		}

		this.clearMessage();
	}

	protected void restartProcessingBySource() throws Exception {
		this.applySourcePreProcessor();
		this.startProcessing(this.currentSource);
	}

	protected void setModule(Class<? extends IModule> newModule) throws Exception {

		if (newModule == null) {
			throw new IllegalArgumentException("Internal error. Parameter 'newModule' must be not null.");
		}

		this.printMessage("Подключение нового модуля анализа");

		logger.debug("Applying module {}", newModule);

		this.mainModuleProcessor.setModule(newModule);
		this.moduleFrame.setTitle(this.mainModuleProcessor.getModule().getName());

		if (currentLocus != null) {
			this.moduleFrame.setImage(ModuleHelper.getTemporaryModuleImage(this.currentLocus));
		} else {
			this.moduleFrame.setImage(null);
		}

		this.clearMessage();
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
			jMenuFile.setText("Файл");
			jMenuFile.add(getJMenuItemOpen());
			jMenuFile.add(getJMenuItemOpenMass());
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
			jMenuItemOpen.setAction(new AbstractAction() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					try {
						MainFrame.this.printMessage("Открытие нового годографа");
						MainFrame.this.startProcessing(MainFrame.this.singleFilePicker.getSingleSource());
					} catch (Exception e1) {
						logger.error("Error when picking new image for processing", e1);
						JOptionPane.showMessageDialog(null, "Error when opening image: " + e1, "Error",
								JOptionPane.ERROR_MESSAGE);
					} finally {
						MainFrame.this.clearMessage();
					}
				}
			});
			jMenuItemOpen.setText("Открыть");
			jMenuItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
			jMenuItemOpen.setName("open");
			jMenuItemOpen.setActionCommand("open");
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
			jMenuItemOpenMass.setAction(new NoAction());
			jMenuItemOpenMass.setText("Массовая загрузка");
			jMenuItemOpenMass.setName("openmass");
			jMenuItemOpenMass.setActionCommand("openMass");
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
			jMenuItemSettings.setAction(new AbstractAction() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					AppHelper.getInstance().openWindow(SettingsDialog.class, MainFrame.this);
				}
			});
			jMenuItemSettings.setText("Настройки...");
			jMenuItemSettings.setName("settings");
			jMenuItemSettings.setActionCommand("settings");
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
			jMenuItemExit.setAction(new AbstractAction() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					// When this frame closed -- we totally exit the application

					MainFrame.this.closeImpl();
					System.exit(0);
				}
			});
			jMenuItemExit.setText("Выход");
			jMenuItemExit.setName("exit");
			jMenuItemExit.setActionCommand("exit");
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
			jMenuDatabase.setText("Хранилище");
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
			jMenuItemMaterials.setAction(new NoAction());
			jMenuItemMaterials.setText("Материалы");
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
	private JPanel jPanelDefect = null;
	private JComboBox jComboBoxSurface = null;
	private JButton jButtonSurface = null;
	private JCheckBox jCheckBoxSurface = null;
	private JComboBox jComboBoxDefect = null;
	private JButton jButtonDefect = null;
	private JCheckBox jCheckBoxDefect = null;
	private JCheckBox jCheckBoxHistogram = null;
	private JPanel jPanelLeftOthers = null;
	private JButton jButtonProcess = null;
	private ImagePanel jPanelImageSource = null;

	/**
	 * This method initializes jMenuHelp
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getJMenuHelp() {
		if (jMenuHelp == null) {
			jMenuHelp = new JMenu();
			jMenuHelp.setText("Помощь");
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
			jMenuItemHelp.setAction(new NoAction());
			jMenuItemHelp.setText("Помощь");
			jMenuItemHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
			jMenuItemHelp.setName("help");
			jMenuItemHelp.setActionCommand("help");
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
			jMenuItemAbout.setAction(new AbstractAction() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					//					JOptionPane.showMessageDialog(null, "<html><h2>" + Const.FULL_PROGRAM_NAME + "</h2>"
					//							+ "<b>Science content:</b> Konart<br><b>Code, design:</b> raidan"
					//							+ "<br><hr>(c) raidan, Konart 2009" + "</html>", "О программе...",
					//							JOptionPane.INFORMATION_MESSAGE);

					AppHelper.getInstance().openWindow(AboutDialog.class, MainFrame.this);
				}
			});
			jMenuItemAbout.setText("О программе...");
			jMenuItemAbout.setName("about");
			jMenuItemAbout.setActionCommand("about");
		}
		return jMenuItemAbout;
	}

	public static class NoAction extends AbstractAction {

		/**
	 *
	 */
		private static final long serialVersionUID = 1L; // @jve:decl-index=0:

		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "Not implemented yet.", "Info", JOptionPane.INFORMATION_MESSAGE);
		}
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
			jLabelImageInfo.setText("");
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.weightx = 1.0D;
			gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
			jLabelTmp = new JLabel();
			jLabelTmp.setText("");
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
			jStatus.setText("Текст для примера!");

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
				jPanelImageFiltered.registerFitButton(jCheckBoxScale, jPanelImageSource);
			} else {
				JOptionPane.showMessageDialog(null, "Internal error. Expected panelImage layout not initialized yet.",
						"Invalid layout programming", JOptionPane.ERROR_MESSAGE);
			}
			jCheckBoxScale.setText("Масштаб под размеры окна");
			jCheckBoxScale.setHorizontalAlignment(SwingConstants.LEADING);
			jCheckBoxScale.setMnemonic(KeyEvent.VK_UNDEFINED);
			jCheckBoxScale.setName("scaleButton");
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
			GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
			gridBagConstraints23.gridx = 0;
			gridBagConstraints23.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints23.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints23.weightx = 1.0D;
			gridBagConstraints23.gridy = 5;
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
			gridBagConstraints11.gridy = 2;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridy = 1;
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
			jPanelLeft.add(getJPanelModule(), gridBagConstraints5);
			jPanelLeft.add(getJPanelFilters(), gridBagConstraints11);
			jPanelLeft.add(getJPanelSensors(), gridBagConstraints16);
			jPanelLeft.add(getJPanelSurface(), gridBagConstraints20);
			jPanelLeft.add(getJPanelDefect(), gridBagConstraints23);
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
			jScrollPaneImageSource.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			jScrollPaneImageSource.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
			jPanelFilters.setBorder(BorderFactory.createTitledBorder(null, "Фильтры",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
					new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jPanelFilters.setPreferredSize(new Dimension(183, 120));
			jPanelFilters.add(getJScrollPaneFilters(), gridBagConstraints10);
			jPanelFilters.add(getJButtonFiltersChange(), gridBagConstraints8);
		}
		return jPanelFilters;
	}

	/**
	 * This method initializes jListFilters
	 * 
	 * @return javax.swing.JList
	 */
	private JList getJListFilters() {
		if (jListFilters == null) {
			jListFilters = new JList(filterList);
			jListFilters.setCellRenderer(new DefaultListCellRenderer() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
						boolean cellHasFocus) {
					return super.getListCellRendererComponent(list, ((LFilters) value).getName(), index, isSelected,
							cellHasFocus);
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
			jButtonFiltersChange.setAction(new NoAction());
			jButtonFiltersChange.setText("Изменить");
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
			jPanelSensors.setBorder(BorderFactory.createTitledBorder(null, "Датчик",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
					new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
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
			jComboBoxSensor = new JComboBox(new String[] { "Недавно выбранные датчики" });
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
			jButtonSensorSearch.setAction(new NoAction());
			jButtonSensorSearch.setText("Найти");
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
			jCheckBoxSensor.setText("Окно подробностей");
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
			jPanelSurface.setBorder(BorderFactory.createTitledBorder(null, "Поверхность",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
					new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jPanelSurface.add(getJComboBoxSurface(), gridBagConstraints14);
			jPanelSurface.add(getJButtonSurface(), gridBagConstraints15);
			jPanelSurface.add(getJCheckBoxSurface(), gridBagConstraints17);
		}
		return jPanelSurface;
	}

	/**
	 * This method initializes jPanelDefect
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelDefect() {
		if (jPanelDefect == null) {
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridx = 0;
			gridBagConstraints21.anchor = GridBagConstraints.WEST;
			gridBagConstraints21.gridwidth = 2;
			gridBagConstraints21.gridy = 2;
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.gridx = 1;
			gridBagConstraints19.gridy = 0;
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints18.gridy = 0;
			gridBagConstraints18.weightx = 1.0;
			gridBagConstraints18.gridx = 0;
			jPanelDefect = new JPanel();
			jPanelDefect.setLayout(new GridBagLayout());
			jPanelDefect.setBorder(BorderFactory.createTitledBorder(null, "Дефект", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jPanelDefect.add(getJComboBoxDefect(), gridBagConstraints18);
			jPanelDefect.add(getJButtonDefect(), gridBagConstraints19);
			jPanelDefect.add(getJCheckBoxDefect(), gridBagConstraints21);
		}
		return jPanelDefect;
	}

	/**
	 * This method initializes jComboBoxSurface
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBoxSurface() {
		if (jComboBoxSurface == null) {
			jComboBoxSurface = new JComboBox(new String[] { "Недавно выбранные поверхности" });
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
			jButtonSurface.setAction(new NoAction());
			jButtonSurface.setText("Найти");
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
			jCheckBoxSurface.setText("Окно подробностей");
		}
		return jCheckBoxSurface;
	}

	/**
	 * This method initializes jComboBoxDefect
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBoxDefect() {
		if (jComboBoxDefect == null) {
			jComboBoxDefect = new JComboBox(new String[] { "Недавно выбранные дефекты" });
		}
		return jComboBoxDefect;
	}

	/**
	 * This method initializes jButtonDefect
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonDefect() {
		if (jButtonDefect == null) {
			jButtonDefect = new JButton();
			jButtonDefect.setAction(new NoAction());
			jButtonDefect.setText("Найти");
		}
		return jButtonDefect;
	}

	/**
	 * This method initializes jCheckBoxDefect
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxDefect() {
		if (jCheckBoxDefect == null) {
			jCheckBoxDefect = new JCheckBox();
			jCheckBoxDefect.setText("Окно подробностей");
		}
		return jCheckBoxDefect;
	}

	/**
	 * This method initializes jCheckBoxHistogram
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxHistogram() {
		if (jCheckBoxHistogram == null) {
			jCheckBoxHistogram = new JCheckBox();
			jCheckBoxHistogram.setName("histogram");
			jCheckBoxHistogram.setText("Гистограмма");
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
			GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
			gridBagConstraints24.gridx = 0;
			gridBagConstraints24.fill = GridBagConstraints.NONE;
			gridBagConstraints24.insets = new Insets(15, 15, 15, 15);
			gridBagConstraints24.anchor = GridBagConstraints.NORTH;
			gridBagConstraints24.gridy = 1;
			jPanelLeftOthers = new JPanel();
			jPanelLeftOthers.setLayout(new GridBagLayout());
			jPanelLeftOthers.add(getJButtonProcess(), gridBagConstraints24);
		}
		return jPanelLeftOthers;
	}

	/**
	 * This method initializes jButtonProcess
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonProcess() {
		if (jButtonProcess == null) {
			jButtonProcess = new JButton();
			jButtonProcess.setText("Сравниить...");
			jButtonProcess.setEnabled(false);
		}
		return jButtonProcess;
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
			jPanelModule.setBorder(BorderFactory.createTitledBorder(null, "Модуль анализа изображения",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
					new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
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
			jComboBoxModules = new JComboBox(moduleList);
			jComboBoxModules.setName("module");
			jComboBoxModules.addActionListener(new ActionListener() {

				@SuppressWarnings("unchecked")
				@Override
				public void actionPerformed(ActionEvent e) {
					LModules item = (LModules) jComboBoxModules.getSelectedItem();
					try {
						MainFrame.this.setModule((Class<IModule>) Class.forName(item.getCodename()));
					} catch (Exception e1) {
						String name = item.getCodename() + " (" + item.getName() + ")";
						logger.error("Error when select module " + name, e1);
						JOptionPane.showMessageDialog(null, "Ошибка при подключении модуля " + name + ": " + e1,
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			});

			jComboBoxModules.setRenderer(new BasicComboBoxRenderer() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
						boolean cellHasFocus) {
					return super.getListCellRendererComponent(list, ((LModules) value).getName(), index, isSelected,
							cellHasFocus);
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
			jCheckBoxModuleGraphic.setText("График модуля");
			jCheckBoxModuleGraphic.setName("moduleImage");
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
			jMenuTest.setText("Тест");
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
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							MainFrame.this.stopProgress();
						};
					}).start();
				}
			});
			jMenuItemProgress.setText("Тест ProgressBar");
		}
		return jMenuItemProgress;
	}

	public void clearMessage() {
		jStatus.setText("");
	}

	public void printMessage(String message) {
		jStatus.setText(message);
	}

	/**
	 * This method initializes jTabbedPaneImages
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPaneImages() {
		if (jTabbedPaneImages == null) {
			jTabbedPaneImages = new JTabbedPane();
			jTabbedPaneImages.addTab("Исходное", null, getJScrollPaneImageSource(), null);
			jTabbedPaneImages.addTab("Отфильтрованное", null, getJScrollPaneImageFiltered(), null);
			jTabbedPaneImages.setSelectedIndex(1);
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
			jScrollPaneImageFiltered.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			jScrollPaneImageFiltered.setViewportView(getJPanelImageFiltered());
			jScrollPaneImageFiltered.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
			jButtonModuleParams.setText("Параметры модуля");
			jButtonModuleParams.setName("moduleParams");
		}
		return jButtonModuleParams;
	}
}
