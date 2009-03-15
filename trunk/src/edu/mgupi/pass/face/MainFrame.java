package edu.mgupi.pass.face;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
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
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import edu.mgupi.pass.util.Const;

public class MainFrame extends JFrame implements ProgressInterface {

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

	/**
	 * This is the default constructor
	 */
	public MainFrame() {
		super();
		initialize();
	}

	private BufferedImage commonImage = null; // @jve:decl-index=0:

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setJMenuBar(getJmainMenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle("Pattern Analytics at Single Surface");
		this.setMinimumSize(new Dimension(800, 700));
		this.setBounds(new Rectangle(150, 150, 800, 700));

		try {
			this.commonImage = ImageIO.read(new File("test/suslik_list.jpg"));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ----------------
		MainFrame.this.switchScaleCheckBox();
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
			jContentPane.add(getJPanelStatus(), BorderLayout.SOUTH);
			jContentPane.add(getJPanelCommon(), BorderLayout.CENTER);
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
			jMenuItemOpen.setAction(new NoAction());
			jMenuItemOpen.setText("Открыть");
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
			jMenuItemSettings.setAction(new NoAction());
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
	private JButton jButtonTmp = null;
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
	private JPanel jPanelImage = null;

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
					AboutDialog about = new AboutDialog(MainFrame.this);
					about.setLocationRelativeTo(MainFrame.this);
					about.setVisible(true);
				}
			});
			jMenuItemAbout.setText("О программе...");
			jMenuItemAbout.setActionCommand("about");
		}
		return jMenuItemAbout;
	}

	private class NoAction extends AbstractAction {

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
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.weightx = 1.0D;
			gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
			jLabelTmp = new JLabel();
			jLabelTmp.setText("");
			jLabelTmp.setVisible(true);
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 3;
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
			jPanelStatus.add(getJCheckBoxScale(), gridBagConstraints2);
		}
		return jPanelStatus;
	}

	private boolean fitImageToWindowSize = false;

	/**
	 * This method initializes jCheckBoxScale
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxScale() {
		if (jCheckBoxScale == null) {
			jCheckBoxScale = new JCheckBox();
			jCheckBoxScale.setAction(new AbstractAction() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					MainFrame.this.switchScaleCheckBox();
				}
			});
			jCheckBoxScale.setText("Масштаб под размеры окна");
			jCheckBoxScale.setHorizontalAlignment(SwingConstants.LEADING);
			jCheckBoxScale.setMnemonic(KeyEvent.VK_UNDEFINED);
			jCheckBoxScale.setHorizontalTextPosition(SwingConstants.TRAILING);
		}
		return jCheckBoxScale;
	}

	private void switchScaleCheckBox() {
		MainFrame.this.fitImageToWindowSize = jCheckBoxScale.isSelected();
		if (MainFrame.this.fitImageToWindowSize) {

			jPanelImage.setPreferredSize(new Dimension((int) jScrollPaneImage.getVisibleRect().getWidth(),
					(int) jScrollPaneImage.getVisibleRect().getHeight()));
			jPanelImage.setBounds(jScrollPaneImage.getBounds());
			jScrollPaneImage.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			jScrollPaneImage.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		} else {
			int width = commonImage.getWidth();
			int height = commonImage.getHeight();

			width = Const.MAIN_IMAGE_WIDTH > width ? width : Const.MAIN_IMAGE_WIDTH;
			height = Const.MAIN_IMAGE_HEIGHT > height ? height : Const.MAIN_IMAGE_HEIGHT;

			jPanelImage.setPreferredSize(new Dimension(width, height));
			jPanelImage.setBounds(0, 0, width, height);
			jScrollPaneImage.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			jScrollPaneImage.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		}
		jPanelImage.repaint();
	}

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
	 * This method initializes jButtonTmp
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonTmp() {
		if (jButtonTmp == null) {
			jButtonTmp = new JButton();
			jButtonTmp.setAction(new AbstractAction() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {

					new Thread(new Runnable() {

						public void run() {
							jButtonTmp.setEnabled(false);
							MainFrame.this.startProgress(100);
							MainFrame.this.setProgress(15);
							try {
								Thread.sleep(1500);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							MainFrame.this.stopProgress();
							jButtonTmp.setEnabled(true);
						};
					}).start();

				}
			});
			jButtonTmp.setHorizontalAlignment(SwingConstants.CENTER);
			jButtonTmp.setText("Тест ProgressBar");
		}
		return jButtonTmp;
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
			jScrollPaneFilters.setPreferredSize(new Dimension(85, 100));
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
			jTextAreaSensors.setText("Название: вихревой датчик\nТип: многокатушечный\nЧуть-чуть о датчике");
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
			jTextAreaSurface.setText("Название: плоскость\nМатериал: сиськи\nЧуть-чуть о поверхности");
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
			jTextAreaDefect.setText("Название: поверхностный\nТип: окалина\nЧуть-чуть о дефекте");
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
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints22.gridy = 0;
			gridBagConstraints22.fill = GridBagConstraints.NONE;
			gridBagConstraints22.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints22.weightx = 1.0D;
			gridBagConstraints22.gridx = 0;
			jPanelLeftOthers = new JPanel();
			jPanelLeftOthers.setLayout(new GridBagLayout());
			jPanelLeftOthers.add(getJCheckBoxHistogram(), gridBagConstraints22);
			jPanelLeftOthers.add(getJButtonTmp(), gridBagConstraints24);
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
		}
		return jButtonProcess;
	}

	/**
	 * This method initializes jPanelImage
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelImage() {
		if (jPanelImage == null) {
			jPanelImage = new ImagePanel();
			// jPanelImage = new JPanel();
			jPanelImage.setLayout(new GridBagLayout());
			jPanelImage.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		}
		return jPanelImage;
	};

	class ImagePanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (commonImage != null) {

				if (fitImageToWindowSize) {
					/*
					 * Based of thumb maker by Marco Schmidt
					 */
					int thumbWidth = this.getWidth();
					int thumbHeight = this.getHeight();
					double thumbRatio = (double) thumbWidth / (double) thumbHeight;
					int imageWidth = commonImage.getWidth();
					int imageHeight = commonImage.getHeight();
					double imageRatio = (double) imageWidth / (double) imageHeight;
					if (thumbRatio < imageRatio) {
						thumbHeight = (int) (thumbWidth / imageRatio);
					} else {
						thumbWidth = (int) (thumbHeight * imageRatio);
					}

					g.drawImage(MainFrame.this.commonImage, 0, 0, thumbWidth, thumbHeight, null);

				} else {
					g.drawImage(MainFrame.this.commonImage, 0, 0, null);
				}
			}
		};
	}
}
