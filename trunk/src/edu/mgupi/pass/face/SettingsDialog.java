package edu.mgupi.pass.face;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.TitledBorder;

import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.util.Config;
import edu.mgupi.pass.util.Const;

public class SettingsDialog extends JDialog {

	private final static Logger logger = LoggerFactory.getLogger(SettingsDialog.class); // @jve:decl-index=0:

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanelButtons = null;
	private JButton jButtonOK = null;
	private JButton jButtonCancel = null;
	private JPanel jPanelCommon = null;
	private JPanel jPanelLaF = null;
	private JComboBox jComboBoxLaF = null;
	private JLabel jLabelLaF = null;
	private MainFrame parentFrame = null;

	/**
	 * @param owner
	 */
	public SettingsDialog(Frame owner) {
		super(owner);
		parentFrame = (MainFrame) owner;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(418, 321);
		this.setName("settingsDialog");
		this.setModal(true);
		this.setTitle("Настройки");
		this.setContentPane(getJContentPane());

		// -------------------

		this.resetSettings();
	}

	public final static String SOURCE_MODE_CENTER = "Разместить в центре";
	public final static String SOURCE_MODE_LEFT_TOP = "Разместить слева сверху";
	public final static String SOURCE_MODE_SCALE = "Отмасштабировать";

	public final static String DEFAULT_SOURCE_MODE = SOURCE_MODE_LEFT_TOP;

	public final static int DEFAULT_BACKGROUND = Color.WHITE.getRGB(); // @jve:decl-index=0:

	private String currentSource = null;
	private Color currentBackground = null; // @jve:decl-index=0:
	private Color newBackground = null; // @jve:decl-index=0:

	private void resetSettings() {

		this.currentSource = Config.getInstance().getCurrentSourceMode(DEFAULT_SOURCE_MODE);
		this.currentBackground = new Color(Config.getInstance().getCurrentBackground(DEFAULT_BACKGROUND));

		jComboBoxLaF.setSelectedItem(UIManager.getLookAndFeel().getName());
		jComboBoxSourceMode.setSelectedItem(currentSource);
		newBackground = currentBackground;

		jLabelBackgroundShow.setBackground(currentBackground);
	}

	private boolean needSaveCommon = false;

	private void applySettings() throws Exception {

		needSaveCommon = false;

		// LaF apply
		String className = lafs.get(jComboBoxLaF.getSelectedItem());
		Config.getInstance().setLookAndFeel(className);
		if (!UIManager.getLookAndFeel().getClass().getName().equals(className)) {
			AppHelper.getInstance().updateUI(className);
			needSaveCommon = true;
		}

		boolean needRestartProcessing = false;

		String newSourceMode = (String) jComboBoxSourceMode.getSelectedItem();
		// setup new source mode anyway
		Config.getInstance().setCurrentSourceMode(newSourceMode);
		if (!newSourceMode.equals(currentSource)) {
			currentSource = newSourceMode;
			needRestartProcessing = true;
		}

		Config.getInstance().setCurrentBackground(newBackground.getRGB());
		if (!newBackground.equals(currentBackground)) {
			currentBackground = newBackground; 
			needRestartProcessing = true;
		}

		if (needRestartProcessing) {
			this.parentFrame.restartProcessingBySource();
		}

	}

	private void saveSettings() {

		try {
			this.applySettings();
		} catch (Exception e) {
			logger.error("Error when applying settings", e);
			JOptionPane.showMessageDialog(null, "Unexpected error when applying settings (" + e + ")",
					"Error when applying settings.", JOptionPane.ERROR_MESSAGE);
		}

		try {
			if (this.needSaveCommon) {
				logger.debug("Saving settings...");
				Config.getInstance().saveCommonConfig();
			}
		} catch (ConfigurationException e) {
			logger.error("Error when saving settings", e);
			JOptionPane.showMessageDialog(null, "Unexpected error when saving settings (" + e + ")",
					"Error when saving settings.", JOptionPane.ERROR_MESSAGE);
		}

		SettingsDialog.this.setVisible(false);
	}

	private void cancelSettings() {
		this.resetSettings();
		SettingsDialog.this.setVisible(false);
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
			jContentPane.add(getJPanelButtons(), BorderLayout.SOUTH);
			jContentPane.add(getJPanelCommon(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanelButtons
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelButtons() {
		if (jPanelButtons == null) {
			jPanelButtons = new JPanel();
			jPanelButtons.setLayout(new FlowLayout());
			jPanelButtons.add(getJButtonOK(), null);
			jPanelButtons.add(getJButtonCancel(), null);
		}
		return jPanelButtons;
	}

	/**
	 * This method initializes jButtonOK
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonOK() {
		if (jButtonOK == null) {
			jButtonOK = new JButton();
			jButtonOK.setAction(new AbstractAction() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					SettingsDialog.this.saveSettings();
				}
			});
			jButtonOK.setName("ok");
			jButtonOK.setText("OK");
		}
		return jButtonOK;
	}

	/**
	 * This method initializes jButtonCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonCancel() {
		if (jButtonCancel == null) {
			jButtonCancel = new JButton();
			jButtonCancel.setAction(new AbstractAction() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					SettingsDialog.this.cancelSettings();
				}
			});
			jButtonCancel.setName("cancel");
			jButtonCancel.setText("Отмена");
		}
		return jButtonCancel;
	}

	/**
	 * This method initializes jPanelCommon
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelCommon() {
		if (jPanelCommon == null) {
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.fill = GridBagConstraints.BOTH;
			gridBagConstraints6.weighty = 1.0;
			gridBagConstraints6.weightx = 1.0;
			jPanelCommon = new JPanel();
			jPanelCommon.setLayout(new GridBagLayout());
			jPanelCommon.add(getJTabbedPaneSettings(), gridBagConstraints6);
		}
		return jPanelCommon;
	}

	/**
	 * This method initializes jPanelLaF
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelLaF() {
		if (jPanelLaF == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints1.gridy = 0;
			jLabelLaF = new JLabel();
			jLabelLaF.setText("Стиль");
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.NONE;
			gridBagConstraints.gridx = 1;
			jPanelLaF = new JPanel();
			jPanelLaF.setLayout(new GridBagLayout());
			jPanelLaF.setBorder(BorderFactory.createTitledBorder(null, "Настройки интерфейса",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
					new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jPanelLaF.setName("jPanelLaF");
			jPanelLaF.add(jLabelLaF, gridBagConstraints1);
			jPanelLaF.add(getJComboBoxLaF(), gridBagConstraints);
		}
		return jPanelLaF;
	}

	private Map<String, String> lafs = new LinkedHashMap<String, String>(); // @jve:decl-index=0:

	private JPanel jPanelSourceMode = null;

	private JComboBox jComboBoxSourceMode = null;

	private JLabel jLabelSourceMode = null;

	/**
	 * This method initializes jComboBoxLaF
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBoxLaF() {
		if (jComboBoxLaF == null) {

			for (LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
				lafs.put(laf.getName(), laf.getClassName());
			}
			jComboBoxLaF = new JComboBox(lafs.keySet().toArray(new String[0]));
			jComboBoxLaF.setName("laf");

		}
		return jComboBoxLaF;
	}

	/**
	 * This method initializes jPanelSourceMode
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSourceMode() {
		if (jPanelSourceMode == null) {
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 2;
			gridBagConstraints10.fill = GridBagConstraints.NONE;
			gridBagConstraints10.weightx = 0.0D;
			gridBagConstraints10.anchor = GridBagConstraints.WEST;
			gridBagConstraints10.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints10.gridy = 1;
			jLabelBackgroundShow = new JLabel();
			jLabelBackgroundShow.setText("     ");
			jLabelBackgroundShow.setOpaque(true);
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = 1;
			gridBagConstraints9.anchor = GridBagConstraints.WEST;
			gridBagConstraints9.gridy = 1;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.gridy = 1;
			jLabelBackground = new JLabel();
			jLabelBackground.setText("Цвет фона");
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.gridy = 1;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints5.gridy = 0;
			jLabelSourceMode = new JLabel();
			jLabelSourceMode.setText("Режим загрузки");
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.gridwidth = 2;
			gridBagConstraints3.weightx = 1.0;
			jPanelSourceMode = new JPanel();
			jPanelSourceMode.setLayout(new GridBagLayout());
			jPanelSourceMode.setBorder(BorderFactory.createTitledBorder(null, "Исходное изображение -> "
					+ Const.MAIN_IMAGE_WIDTH + "x" + Const.MAIN_IMAGE_HEIGHT, TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jPanelSourceMode.add(jLabelSourceMode, gridBagConstraints5);
			jPanelSourceMode.add(getJComboBoxSourceMode(), gridBagConstraints3);
			jPanelSourceMode.add(jLabelBackground, gridBagConstraints7);
			jPanelSourceMode.add(getJButtonBackground(), gridBagConstraints9);
			jPanelSourceMode.add(jLabelBackgroundShow, gridBagConstraints10);
		}
		return jPanelSourceMode;
	}

	private JTabbedPane jTabbedPaneSettings = null;

	private JPanel jPanelSettingsCurrent = null;

	private JPanel jPanelSettingsCommon = null;

	private JLabel jLabelBackground = null;

	private JButton jButtonBackground = null;

	private JLabel jLabelBackgroundShow = null;

	/**
	 * This method initializes jComboBoxSourceMode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBoxSourceMode() {
		if (jComboBoxSourceMode == null) {
			jComboBoxSourceMode = new JComboBox(new String[] { SOURCE_MODE_CENTER, SOURCE_MODE_LEFT_TOP,
					SOURCE_MODE_SCALE });
		}
		return jComboBoxSourceMode;
	}

	/**
	 * This method initializes jTabbedPaneSettings
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPaneSettings() {
		if (jTabbedPaneSettings == null) {
			jTabbedPaneSettings = new JTabbedPane();
			jTabbedPaneSettings.addTab("Текущие настройки", null, getJPanelSettingsCurrent(), null);
			jTabbedPaneSettings.addTab("Общие настройки", null, getJPanelSettingsCommon(), null);
		}
		return jTabbedPaneSettings;
	}

	/**
	 * This method initializes jPanelSettingsCurrent
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSettingsCurrent() {
		if (jPanelSettingsCurrent == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.weighty = 1.0D;
			gridBagConstraints2.weightx = 1.0D;
			gridBagConstraints2.gridx = 0;
			jPanelSettingsCurrent = new JPanel();
			jPanelSettingsCurrent.setLayout(new GridBagLayout());
			jPanelSettingsCurrent.add(getJPanelSourceMode(), gridBagConstraints2);
		}
		return jPanelSettingsCurrent;
	}

	/**
	 * This method initializes jPanelSettingsCommon
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSettingsCommon() {
		if (jPanelSettingsCommon == null) {
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints4.gridy = -1;
			gridBagConstraints4.ipadx = 0;
			gridBagConstraints4.ipady = 0;
			gridBagConstraints4.weightx = 1.0D;
			gridBagConstraints4.weighty = 1.0D;
			gridBagConstraints4.gridx = -1;
			jPanelSettingsCommon = new JPanel();
			jPanelSettingsCommon.setLayout(new GridBagLayout());
			jPanelSettingsCommon.add(getJPanelLaF(), gridBagConstraints4);
		}
		return jPanelSettingsCommon;
	}

	/**
	 * This method initializes jButtonBackground
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonBackground() {
		if (jButtonBackground == null) {
			jButtonBackground = new JButton();

			jButtonBackground.setAction(new AbstractAction() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					Color newBack = JColorChooser.showDialog(SettingsDialog.this, "Выбор цвета для фона",
							SettingsDialog.this.newBackground);
					if (newBack != null) {
						SettingsDialog.this.newBackground = newBack;
						jLabelBackgroundShow.setBackground(newBack);
					}
				}
			});
			jButtonBackground.setText("...");
		}
		return jButtonBackground;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
