package edu.mgupi.pass.face;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.modules.ModuleProcessor;
import edu.mgupi.pass.modules.basic.SimpleMatrixModule;
import edu.mgupi.pass.sources.SourceStore;
import edu.mgupi.pass.sources.visual.SingleFilePick;
import edu.mgupi.pass.util.Const;

public class MainFrame extends JFrame implements ProgressInterface {

	private final static Logger logger = LoggerFactory.getLogger(MainFrame.class);

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
	private SingleFilePick singleFilePicker = null; // @jve:decl-index=0:
	private ImageFrameTemplate histogramFrame = null; // @jve:decl-index=0:visual-constraint="820,10"
	private ModuleProcessor mainModuleProcessor = null; // @jve:decl-index=0:

	/**
	 * This is the default constructor
	 * 
	 * @throws Exception
	 */
	public MainFrame() throws Exception {
		super();
		initialize();
	}

	private String basicWindowName = "Pattern Analytics at Single Surface v." + Const.VERSION + " b." + Const.BUILD; // @jve:decl-index=0:

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() throws Exception {
		this.setJMenuBar(getJmainMenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle(basicWindowName);
		this.setMinimumSize(new Dimension(600, 720));
		this.setBounds(new Rectangle(150, 150, 800, 720));

		// ----------------

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (singleFilePicker != null) {
					singleFilePicker.close();
				}
				logger.debug("Application terminating...");
			}
		});

		mainModuleProcessor = new ModuleProcessor();
		mainModuleProcessor.registerModule(SimpleMatrixModule.class);

		histogramFrame = (ImageFrameTemplate) AppHelper.getInstance().createWindow(ImageFrameTemplate.class);
		histogramFrame.registerControlCheckbox(this.jCheckBoxHistogram);
		histogramFrame.setTitle(mainModuleProcessor.getHistoFilters().toString());

		singleFilePicker = new SingleFilePick();
		singleFilePicker.init();

		// MainFrame.this.switchScaleCheckBox();

		// TestSourceImpl source = new TestSourceImpl();
		// source.init();
		// this.startProcessing(source.getSingleSource());
		// source.close();

		logger.debug("Main frame init done.");
	}

	private void startProcessing(SourceStore source) throws Exception {

		if (source == null) {
			logger.debug("Nothing to process...");
			// JOptionPane.showMessageDialog(null,
			// "Internal error. Received null source.", "Internal error",
			// JOptionPane.ERROR_MESSAGE);
			return;
		}

		logger.debug("Start loading source " + source.getName());

		this.setTitle(basicWindowName + " -- " + source.getName());

		// This is must be safe!!!!
		// I can stop wherever I want!
		mainModuleProcessor.finishProcessing();

		// Main cycle
		mainModuleProcessor.startProcessing(source);
		this.jLabelImageInfo.setText("" + mainModuleProcessor.getLastProcessedImage().getWidth() + "x"
				+ mainModuleProcessor.getLastProcessedImage().getHeight() + " "
				+ mainModuleProcessor.getLastProcessedImage().getColorModel().getPixelSize() + " bpp");
		// Locuses locus = mainModuleProcessor.startProcessing(source);
		this.jPanelImage.setImage(mainModuleProcessor.getLastProcessedImage());
		this.histogramFrame.setImage(mainModuleProcessor.getLastHistogramImage());
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
						MainFrame.this.startProcessing(MainFrame.this.singleFilePicker.getSingleSource());
					} catch (Exception e1) {
						logger.error("Error when picking new image for processing", e1);
						JOptionPane.showMessageDialog(null, "Error when opening image: " + e1, "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			jMenuItemOpen.setText("Открыть");
			jMenuItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
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
					MainFrame.this.dispose();

					// We can catch this situation on listening
					// WindowEvent.WINDOW_CLOSED event
				}
			});
			jMenuItemExit.setText("Выход");
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
	private JScrollPane jScrollPaneImage = null;
	private JPanel jPanelFilters = null;
	private JList jListFilters = null;
	private JButton jButtonFilterAdd = null;
	private JButton jButtonFilterDelete = null;
	private JScrollPane jScrollPaneFilters = null;
	private JPanel jPanelSensors = null;
	private JComboBox jComboBoxSensor = null;
	private JButton jButtonSensorSearch = null;
	private JTextArea jTextAreaSensors = null;
	private JCheckBox jCheckBoxSensor = null;
	private JPanel jPanelSurface = null;
	private JPanel jPanelDefect = null;
	private JComboBox jComboBoxSurface = null;
	private JButton jButtonSurface = null;
	private JTextArea jTextAreaSurface = null;
	private JCheckBox jCheckBoxSurface = null;
	private JComboBox jComboBoxDefect = null;
	private JButton jButtonDefect = null;
	private JTextArea jTextAreaDefect = null;
	private JCheckBox jCheckBoxDefect = null;
	private JCheckBox jCheckBoxHistogram = null;
	private JPanel jPanelLeftOthers = null;
	private JButton jButtonProcess = null;
	private ImagePanel jPanelImage = null;

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
					AppHelper.getInstance().openWindow(AboutDialog.class, MainFrame.this);
				}
			});
			jMenuItemAbout.setText("О программе...");
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

	/**
	 * This method initializes jCheckBoxScale
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxScale() {
		if (jCheckBoxScale == null) {
			jCheckBoxScale = new JCheckBox();
			if (jPanelImage != null) {
				jPanelImage.registerFitButton(jCheckBoxScale);
			} else {
				JOptionPane.showMessageDialog(null, "Internal error. Expected panelImage layout not initialized yet.",
						"Invalid layout programming", JOptionPane.ERROR_MESSAGE);
			}
			jCheckBoxScale.setText("Масштаб под размеры окна");
			jCheckBoxScale.setHorizontalAlignment(SwingConstants.LEADING);
			jCheckBoxScale.setMnemonic(KeyEvent.VK_UNDEFINED);
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
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = GridBagConstraints.BOTH;
			gridBagConstraints5.weighty = 1.0;
			gridBagConstraints5.weightx = 1.0;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.anchor = GridBagConstraints.NORTH;
			gridBagConstraints4.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints4.gridy = 0;
			jPanelCommon = new JPanel();
			jPanelCommon.setLayout(new GridBagLayout());
			jPanelCommon.add(getJPanelLeft(), gridBagConstraints4);
			jPanelCommon.add(getJScrollPaneImage(), gridBagConstraints5);
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
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = -1;
			gridBagConstraints6.gridy = -1;
			jPanelLeft = new JPanel();
			jPanelLeft.setLayout(new BoxLayout(getJPanelLeft(), BoxLayout.Y_AXIS));
			jPanelLeft.setPreferredSize(new Dimension(200, 200));
			jPanelLeft.setMinimumSize(new Dimension(200, 200));
			jPanelLeft.add(getJPanelModule(), null);
			jPanelLeft.add(getJPanelFilters(), null);
			jPanelLeft.add(getJPanelSensors(), null);
			jPanelLeft.add(getJPanelSurface(), null);
			jPanelLeft.add(getJPanelDefect(), null);
			jPanelLeft.add(getJPanelLeftOthers(), null);
		}
		return jPanelLeft;
	}

	/**
	 * This method initializes jScrollPaneImage
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneImage() {
		if (jScrollPaneImage == null) {
			jScrollPaneImage = new JScrollPane();
			jScrollPaneImage.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			jScrollPaneImage.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			jScrollPaneImage.setViewportView(getJPanelImage());
		}
		return jScrollPaneImage;
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
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = 1;
			gridBagConstraints9.gridy = 1;
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
			jPanelFilters.add(getJButtonFilterAdd(), gridBagConstraints8);
			jPanelFilters.add(getJButtonFilterDelete(), gridBagConstraints9);
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
			jListFilters = new JList(new String[] { "GrayScale Filter", "AutoSharp Filter", "Invert Filter" });
			jListFilters.setPreferredSize(new Dimension(93, 50));
		}
		return jListFilters;
	}

	/**
	 * This method initializes jButtonFilterAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonFilterAdd() {
		if (jButtonFilterAdd == null) {
			jButtonFilterAdd = new JButton();
			jButtonFilterAdd.setText("Добавить");
		}
		return jButtonFilterAdd;
	}

	/**
	 * This method initializes jButtonFilterDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonFilterDelete() {
		if (jButtonFilterDelete == null) {
			jButtonFilterDelete = new JButton();
			jButtonFilterDelete.setText("Удалить");
		}
		return jButtonFilterDelete;
	}

	/**
	 * This method initializes jScrollPaneFilters
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneFilters() {
		if (jScrollPaneFilters == null) {
			jScrollPaneFilters = new JScrollPane();
			jScrollPaneFilters.setPreferredSize(new Dimension(85, 120));
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
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = GridBagConstraints.BOTH;
			gridBagConstraints11.weighty = 1.0D;
			gridBagConstraints11.gridwidth = 2;
			gridBagConstraints11.gridy = 1;
			gridBagConstraints11.gridheight = 1;
			gridBagConstraints11.weightx = 1.0;
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
			jPanelSensors.add(getJTextAreaSensors(), gridBagConstraints11);
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
			jButtonSensorSearch.setText("Найти");
		}
		return jButtonSensorSearch;
	}

	/**
	 * This method initializes jTextAreaSensors
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextAreaSensors() {
		if (jTextAreaSensors == null) {
			jTextAreaSensors = new JTextArea();
			jTextAreaSensors.setText("Название: вихревой датчик\nЧуть-чуть о датчике");
			jTextAreaSensors.setEditable(false);
		}
		return jTextAreaSensors;
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
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.fill = GridBagConstraints.BOTH;
			gridBagConstraints16.gridy = 1;
			gridBagConstraints16.weightx = 1.0;
			gridBagConstraints16.weighty = 1.0;
			gridBagConstraints16.gridwidth = 2;
			gridBagConstraints16.gridx = 0;
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
			jPanelSurface.add(getJTextAreaSurface(), gridBagConstraints16);
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
			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.fill = GridBagConstraints.BOTH;
			gridBagConstraints20.gridy = 1;
			gridBagConstraints20.weightx = 1.0;
			gridBagConstraints20.weighty = 1.0;
			gridBagConstraints20.gridwidth = 2;
			gridBagConstraints20.gridx = 0;
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
			jPanelDefect.add(getJTextAreaDefect(), gridBagConstraints20);
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
			jButtonSurface.setText("Найти");
		}
		return jButtonSurface;
	}

	/**
	 * This method initializes jTextAreaSurface
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextAreaSurface() {
		if (jTextAreaSurface == null) {
			jTextAreaSurface = new JTextArea();
			jTextAreaSurface.setText("Название: плоскость\nЧуть-чуть о поверхности");
			jTextAreaSurface.setEditable(false);
		}
		return jTextAreaSurface;
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
			jButtonDefect.setText("Найти");
		}
		return jButtonDefect;
	}

	/**
	 * This method initializes jTextAreaDefect
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextAreaDefect() {
		if (jTextAreaDefect == null) {
			jTextAreaDefect = new JTextArea();
			jTextAreaDefect.setText("Название: поверхностный\nЧуть-чуть о дефекте");
			jTextAreaDefect.setEditable(false);
		}
		return jTextAreaDefect;
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
			jCheckBoxHistogram.setAction(new AbstractAction() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					histogramFrame.setVisible(jCheckBoxHistogram.isSelected());
				}
			});
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
			jButtonProcess.setText("Распознать...");
			jButtonProcess.setEnabled(false);
		}
		return jButtonProcess;
	}

	/**
	 * This method initializes jPanelImage
	 * 
	 * @return javax.swing.JPanel
	 */
	private ImagePanel getJPanelImage() {
		if (jPanelImage == null) {
			jPanelImage = new ImagePanel();
			jPanelImage.setLayout(new GridBagLayout());
			jPanelImage.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		}
		return jPanelImage;
	};

	/**
	 * This method initializes jPanelModule
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelModule() {
		if (jPanelModule == null) {
			GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
			gridBagConstraints26.gridx = 1;
			gridBagConstraints26.anchor = GridBagConstraints.WEST;
			gridBagConstraints26.gridy = 1;
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.anchor = GridBagConstraints.WEST;
			gridBagConstraints22.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints22.gridx = 0;
			gridBagConstraints22.gridy = 1;
			gridBagConstraints22.weightx = 1.0D;
			gridBagConstraints22.fill = GridBagConstraints.NONE;
			GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
			gridBagConstraints25.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints25.anchor = GridBagConstraints.WEST;
			gridBagConstraints25.gridwidth = 3;
			gridBagConstraints25.weightx = 1.0;
			jPanelModule = new JPanel();
			jPanelModule.setLayout(new GridBagLayout());
			jPanelModule.setBorder(BorderFactory.createTitledBorder(null, "Модуль анализа изображения",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
					new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jPanelModule.setMaximumSize(new Dimension(200, 50));
			jPanelModule.add(getJComboBoxModules(), gridBagConstraints25);
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
			jComboBoxModules = new JComboBox(new String[] { "Simple Module", "TestModule" });
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
			jCheckBoxModuleGraphic.setText("Модуль");
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
		// TODO Auto-generated method stub

	}

	public void printMessage(String message) {
		// TODO Auto-generated method stub

	}
}
