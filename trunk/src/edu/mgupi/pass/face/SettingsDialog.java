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
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.face.template.AbstractDialogAdapter;
import edu.mgupi.pass.util.Config;
import edu.mgupi.pass.util.Const;
import edu.mgupi.pass.util.Config.DeletionMode;
import edu.mgupi.pass.util.Config.SourceMode;

public class SettingsDialog extends JDialog implements ActionListener {

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

	/**
	 * @param owner
	 */
	public SettingsDialog(Frame owner) {
		super(owner);
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

		//Config.getInstance().getWindowPosition(this);

		// -------------------

		this.resetSettings();
	}

	private AbstractDialogAdapter myDialogAdapter = null; //  @jve:decl-index=0:

	private AbstractDialogAdapter getDialogAdapter() {
		if (myDialogAdapter != null) {
			return myDialogAdapter;
		}
		myDialogAdapter = new AbstractDialogAdapter(this) {

			@Override
			protected void cancelImpl() throws Exception {
				SettingsDialog.this.resetSettings();
			}

			@Override
			protected void openDialogImpl() throws Exception {
				//
			}

			private boolean needRestartProcessing = false;
			private boolean needSaveCommon = false;

			@Override
			protected boolean saveImpl() throws Exception {
				needSaveCommon = false;
				needRestartProcessing = false;

				// LaF apply
				String className = lafs.get(jComboBoxLaF.getSelectedItem());
				Config.getInstance().setLookAndFeel(className);
				if (!UIManager.getLookAndFeel().getClass().getName().equals(className)) {
					AppHelper.getInstance().updateUI(className);
					logger.debug("LaF changed. Save common.");
					needSaveCommon = true;
				}

				if (Config.getInstance().setFilterDeleteConfirm(currentDeletionMode)) {
					logger.debug("Deletion mode changed. Save common.");
					needSaveCommon = true;
				}

				if (Config.getInstance().setCurrentSourceMode((SourceMode) jComboBoxSourceMode.getSelectedItem())) {
					logger.debug("Selection source mode changed. Restart process.");
					needRestartProcessing = true;
				}

				if (Config.getInstance().setCurrentBackground(newBackground.getRGB())) {
					logger.debug("New background changed (to {}). Restart process.", newBackground.getRGB());
					needRestartProcessing = true;
				}

				if (this.needSaveCommon) {
					logger.debug("Saving settings...");
					Config.getInstance().saveCommonConfig();
				}

				return this.needRestartProcessing;

			}
		};
		return myDialogAdapter;
	}

	public boolean openDialog() {
		return getDialogAdapter().openDialog();
	}

	private Color newBackground = null; // @jve:decl-index=0:
	private DeletionMode currentDeletionMode = null; //  @jve:decl-index=0:

	private void resetSettings() {

		SourceMode currentSource = Config.getInstance().getCurrentSourceMode();

		jComboBoxLaF.setSelectedItem(UIManager.getLookAndFeel().getName());
		jComboBoxSourceMode.setSelectedItem(currentSource);

		Color currentBackground = new Color(Config.getInstance().getCurrentBackground());
		newBackground = currentBackground;
		jLabelBackgroundShow.setBackground(currentBackground);

		currentDeletionMode = Config.getInstance().getFilterDeleteMode();
		this.cachedButtons.get(currentDeletionMode).setSelected(true);

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
			jContentPane.add(getJPanelButtons(), java.awt.BorderLayout.SOUTH);
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
			jButtonOK.setText("OK");
			getDialogAdapter().registerOKButton(jButtonOK);
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
			jButtonCancel.setText("cancel");
			getDialogAdapter().registerCancelButton(jButtonCancel);
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
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.fill = GridBagConstraints.NONE;
			gridBagConstraints11.anchor = GridBagConstraints.WEST;
			gridBagConstraints11.weightx = 1.0D;
			gridBagConstraints11.gridy = 0;
			jLabelLaF = new JLabel();
			jLabelLaF.setText("Стиль");
			jPanelLaF = new JPanel();
			jPanelLaF.setLayout(new GridBagLayout());
			jPanelLaF.setBorder(BorderFactory.createTitledBorder(null, "Настройки интерфейса",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
					new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jPanelLaF.setName("jPanelLaF");
			jPanelLaF.add(getJPanelLaFPlace(), gridBagConstraints11);
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
			gridBagConstraints10.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints10.weightx = 0.0D;
			gridBagConstraints10.anchor = GridBagConstraints.WEST;
			gridBagConstraints10.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints10.gridy = 1;
			jLabelBackgroundShow = new JLabel();
			jLabelBackgroundShow.setText(" ");
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

	private JPanel jPanelLaFPlace = null;

	private JPanel jPanelFilterEdit = null;

	private JPanel jPanelFilterEditPlace = null;

	/**
	 * This method initializes jComboBoxSourceMode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBoxSourceMode() {
		if (jComboBoxSourceMode == null) {
			jComboBoxSourceMode = new JComboBox(SourceMode.values());
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
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.weightx = 1.0D;
			gridBagConstraints12.gridy = 1;
			gridBagConstraints12.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints12.weighty = 1.0D;
			gridBagConstraints12.anchor = GridBagConstraints.NORTH;
			gridBagConstraints12.gridx = 0;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.anchor = GridBagConstraints.NORTH;
			gridBagConstraints4.gridy = 0;
			gridBagConstraints4.weightx = 1.0D;
			gridBagConstraints4.weighty = 0.0D;
			gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints4.gridx = 0;
			jPanelSettingsCommon = new JPanel();
			jPanelSettingsCommon.setLayout(new GridBagLayout());
			jPanelSettingsCommon.add(getJPanelLaF(), gridBagConstraints4);
			jPanelSettingsCommon.add(getJPanelFilterEdit(), gridBagConstraints12);
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
			jButtonBackground.setText("...");
			jButtonBackground.setName("backgroundColor");
			jButtonBackground.setActionCommand("backgroundColor");
		}
		return jButtonBackground;
	}

	/**
	 * This method initializes jPanelLaFPlace
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelLaFPlace() {
		if (jPanelLaFPlace == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.gridx = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.NONE;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridx = 1;
			jPanelLaFPlace = new JPanel();
			jPanelLaFPlace.setLayout(new GridBagLayout());
			jPanelLaFPlace.add(getJComboBoxLaF(), gridBagConstraints);
			jPanelLaFPlace.add(jLabelLaF, gridBagConstraints1);
		}
		return jPanelLaFPlace;
	}

	/**
	 * This method initializes jPanelFilterEdit
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelFilterEdit() {
		if (jPanelFilterEdit == null) {
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 0;
			gridBagConstraints13.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints13.weightx = 1.0D;
			gridBagConstraints13.gridy = 0;
			jPanelFilterEdit = new JPanel();
			jPanelFilterEdit.setLayout(new GridBagLayout());
			jPanelFilterEdit.setBorder(BorderFactory.createTitledBorder(null, "Редактирование фильтров",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
					new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jPanelFilterEdit.add(getJPanelFilterEditPlace(), gridBagConstraints13);

		}
		return jPanelFilterEdit;
	}

	private Map<DeletionMode, JRadioButton> cachedButtons = new HashMap<DeletionMode, JRadioButton>(); //  @jve:decl-index=0:

	/**
	 * This method initializes jPanelFilterEditPlace
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelFilterEditPlace() {
		if (jPanelFilterEditPlace == null) {
			jPanelFilterEditPlace = new JPanel();
			jPanelFilterEditPlace.setLayout(new GridBagLayout());

			ButtonGroup group = new ButtonGroup();
			int index = 0;
			for (final DeletionMode mode : DeletionMode.values()) {
				GridBagConstraints gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = index;
				final JRadioButton button = new JRadioButton();
				jPanelFilterEditPlace.add(button, gridBagConstraints);
				button.addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						currentDeletionMode = mode;
					}
				});

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 1;
				gridBagConstraints.gridy = index;
				gridBagConstraints.anchor = GridBagConstraints.WEST;
				JLabel label = new JLabel();
				label.setText(mode.toString());
				label.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						button.setSelected(true);
					}
				});

				jPanelFilterEditPlace.add(label, gridBagConstraints);

				group.add(button);
				cachedButtons.put(mode, button);

				index++;
			}

		}
		return jPanelFilterEditPlace;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command == null) {
			return;
		}
		if (command.equals("background")) {
			Color newBack = JColorChooser.showDialog(SettingsDialog.this, "Выбор цвета для фона", this.newBackground);
			if (newBack != null) {
				this.newBackground = newBack;
				jLabelBackgroundShow.setBackground(newBack);
			}
		}

	}
} // @jve:decl-index=0:visual-constraint="10,10"
