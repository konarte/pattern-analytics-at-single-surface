package edu.mgupi.pass.face.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.face.gui.template.AbstractDialogAdapter;
import edu.mgupi.pass.util.Config;
import edu.mgupi.pass.util.Config.DeletionCheckMode;
import edu.mgupi.pass.util.Config.DeletionMode;
import edu.mgupi.pass.util.Config.SourceMode;
import edu.mgupi.pass.util.Config.TestTransactionMode;

/**
 * Settings dialog. Current (for loaded images) and common settings.
 * 
 * in the common page has one main idea. You must set up
 * <code>weighty = 1</code> to the bottom jPanel... and set
 * <code>weighty = 0</code> to all other.
 * 
 * @author raidan
 * 
 */
public class SettingsDialog extends JDialog implements ChangeListener {

	private final static Logger logger = LoggerFactory.getLogger(SettingsDialog.class); // @jve:decl-index=0:

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanelButtons = null;
	private JButton jButtonOK = null;
	private JButton jButtonCancel = null;
	private JPanel jPanelSettings = null;
	private JPanel jPanelLaF = null;
	private JComboBox jComboBoxLaF = null;
	private JLabel jLabelLaF = null;

	/**
	 * Default constructor
	 * 
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

		this.setName("settingsDialog");
		this.setModal(true);
		this.setTitle("Настройки");
		this.setContentPane(getJContentPane());
		this.setResizable(false);

		//Config.getInstance().getWindowPosition(this);

		// -------------------
	}

	/**
	 * Default method for open this dialog.
	 * 
	 * @return true is changed Current settings, false if Current settings are
	 *         not changed (Common settings does not affect to this)
	 */
	public boolean openDialog() {
		return getDialogAdapter().openDialog();
	}

	private AbstractDialogAdapter myDialogAdapter = null; //  @jve:decl-index=0:

	private AbstractDialogAdapter getDialogAdapter() {
		if (myDialogAdapter != null) {
			return myDialogAdapter;
		}
		myDialogAdapter = new AbstractDialogAdapter(this) {

			@Override
			protected void cancelImpl() throws Exception {
				//SettingsDialog.this.resetControls();
			}

			@Override
			protected void openDialogImpl() throws Exception {
				SettingsDialog.this.resetControls();
				pack();
				setMinimumSize(new Dimension(getWidth(), getHeight()));
			}

			private boolean needRestartProcessing = false;
			private boolean needSaveCommon = false;

			@Override
			protected boolean saveImpl() throws Exception {
				needSaveCommon = false;
				needRestartProcessing = false;

				// LaF apply
				if (Config.getInstance().setLookAndFeel(lafs.get(jComboBoxLaF.getSelectedItem()))) {
					logger.debug("LaF changed. Save common.");
					needSaveCommon = true;
				}

				// Do not be scare :)
				// I use class-map for enums 
				if (Config.getInstance().setRowsDeleteMode((DeletionMode) getValue(DeletionMode.CONFIRM))) {
					logger.debug("Deletion mode changed. Save common.");
					needSaveCommon = true;
				}

				if (Config.getInstance().setTransactionMode(
						(TestTransactionMode) getValue(TestTransactionMode.COMMIT_BULK))) {
					logger.debug("Transaction mode changed. Save common.");
					needSaveCommon = true;
				}

				if (Config.getInstance().setDeletionCheckModeMode(
						(DeletionCheckMode) getValue(DeletionCheckMode.ACQUIRE_THEN_CHECK))) {
					logger.debug("Deletion check mode changed. Save common.");
					needSaveCommon = true;
				}

				if (Config.getInstance().setCurrentSourceMode((SourceMode) jComboBoxSourceMode.getSelectedItem())) {
					logger.debug("Selection source mode changed. Restart process.");
					needRestartProcessing = true;
				}

				if (Config.getInstance().setCurrentBackground(newBackground)) {
					logger.debug("New background changed (to {}). Restart process.", newBackground);
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

	private Color newBackground = null; // @jve:decl-index=0:

	/**
	 * Reset current control by its values in {@link Config}
	 */
	protected void resetControls() throws Exception {

		SourceMode currentSource = Config.getInstance().getCurrentSourceMode();

		jComboBoxLaF.setSelectedItem(UIManager.getLookAndFeel().getName());
		jComboBoxSourceMode.setSelectedItem(currentSource);

		Color currentBackground = Config.getInstance().getCurrentBackground();
		newBackground = currentBackground;
		jLabelBackgroundShow.setBackground(currentBackground);

		this.setValue(Config.getInstance().getRowsDeleteMode());
		this.setValue(Config.getInstance().getTransactionMode());
		this.setValue(Config.getInstance().getDeletionCheckMode());
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
			jContentPane.add(getJPanelSettings(), BorderLayout.CENTER);
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
	 * This method initializes jPanelSettings
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSettings() {
		if (jPanelSettings == null) {
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.fill = GridBagConstraints.BOTH;
			gridBagConstraints6.weighty = 1.0;
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.gridy = 0;
			gridBagConstraints6.weightx = 1.0;
			jPanelSettings = new JPanel();
			jPanelSettings.setLayout(new GridBagLayout());
			jPanelSettings.add(getJTabbedPaneSettings(), gridBagConstraints6);
		}
		return jPanelSettings;
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
			jPanelLaF.setBorder(BorderFactory.createTitledBorder("Настройки интерфейса"));
			jPanelLaF.setName("jPanelLaF");
			jPanelLaF.add(getJPanelLaFPlace(), gridBagConstraints11);
		}
		return jPanelLaF;
	}

	private Map<String, String> lafs = new LinkedHashMap<String, String>(); // @jve:decl-index=0:

	private JPanel jPanelSourceModePlace = null;

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
	 * This method initializes jPanelSourceModePlace
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSourceModePlace() {
		if (jPanelSourceModePlace == null) {
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 2;
			gridBagConstraints10.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints10.weightx = 0.0D;
			gridBagConstraints10.anchor = GridBagConstraints.WEST;
			gridBagConstraints10.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints10.gridy = 1;
			jLabelBackgroundShow = new JLabel();
			jLabelBackgroundShow.setText(" ");
			jLabelBackgroundShow.setName("backgroundSample");
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
			jPanelSourceModePlace = new JPanel();
			jPanelSourceModePlace.setLayout(new GridBagLayout());
			jPanelSourceModePlace.add(jLabelSourceMode, gridBagConstraints5);
			jPanelSourceModePlace.add(getJComboBoxSourceMode(), gridBagConstraints3);
			jPanelSourceModePlace.add(jLabelBackground, gridBagConstraints7);
			jPanelSourceModePlace.add(getJButtonBackground(), gridBagConstraints9);
			jPanelSourceModePlace.add(jLabelBackgroundShow, gridBagConstraints10);
		}
		return jPanelSourceModePlace;
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
			jComboBoxSourceMode.setName("sourceMode");
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
			jTabbedPaneSettings.setName("settingsPane");
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
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.gridx = 0;
			gridBagConstraints14.weightx = 1.0D;
			gridBagConstraints14.weighty = 1.0D;
			gridBagConstraints14.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints14.anchor = GridBagConstraints.NORTH;
			gridBagConstraints14.gridy = 0;
			jPanelSettingsCurrent = new JPanel();
			jPanelSettingsCurrent.setLayout(new GridBagLayout());
			jPanelSettingsCurrent.setName("settingsCurrent");
			jPanelSettingsCurrent.add(getJPanelSourceMode(), gridBagConstraints14);
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
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.gridx = -1;
			gridBagConstraints18.gridy = -1;
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.gridx = 0;
			gridBagConstraints17.weighty = 1.0D;
			gridBagConstraints17.weightx = 1.0D;
			gridBagConstraints17.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints17.anchor = GridBagConstraints.NORTH;
			gridBagConstraints17.gridy = 3;
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.gridx = 0;
			gridBagConstraints15.anchor = GridBagConstraints.NORTH;
			gridBagConstraints15.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints15.weightx = 1.0D;
			gridBagConstraints15.weighty = 0.0D;
			gridBagConstraints15.gridy = 2;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.weightx = 1.0D;
			gridBagConstraints12.gridy = 1;
			gridBagConstraints12.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints12.weighty = 0.0D;
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
			jPanelSettingsCommon.setName("settingsCommon");
			jPanelSettingsCommon.add(getJPanelLaF(), gridBagConstraints4);
			jPanelSettingsCommon.add(getJPanelFilterEdit(), gridBagConstraints12);
			jPanelSettingsCommon.add(getJPanelTransactionMode(), gridBagConstraints15);
			jPanelSettingsCommon.add(getJPanelDeletionCheckMode(), gridBagConstraints17);
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

			// Shit-head Visual Editor shows me Color chooser dialog
			//  every time opening this page (if a use addActionListener for instance 
			//  instead of setAction)
			jButtonBackground.setAction(new AbstractAction() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
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
			jButtonBackground.setName("backgroundColor");
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
			jPanelFilterEdit.setBorder(BorderFactory.createTitledBorder("Редактирование данных"));
			jPanelFilterEdit.add(getJPanelFilterEditPlace(), gridBagConstraints13);

		}
		return jPanelFilterEdit;
	}

	private JPanel jPanelSourceMode = null;

	private JPanel jPanelTransactionMode = null;

	private JPanel jPanelTransactionModePlace = null;

	private Map<JLabel, JRadioButton> cachedLinks = new HashMap<JLabel, JRadioButton>(); //  @jve:decl-index=0:
	private Map<JRadioButton, Enum<?>> cachedEnums = new HashMap<JRadioButton, Enum<?>>(); //  @jve:decl-index=0:
	private Map<Enum<?>, JRadioButton> cachedButtons = new HashMap<Enum<?>, JRadioButton>(); //  @jve:decl-index=0:
	@SuppressWarnings("unchecked")
	private Map<Class<? extends Enum>, Enum<?>> selectedValues = new HashMap<Class<? extends Enum>, Enum<?>>(); //  @jve:decl-index=0:

	private JPanel jPanelDeletionCheckMode = null;

	private JPanel jPanelDeletionCheckModePlace = null;

	private void setValue(Enum<?> value) {
		selectedValues.put(value.getClass(), value);
		cachedButtons.get(value).setSelected(true);
	}

	private Enum<?> getValue(Enum<?> value) {
		return selectedValues.get(value.getClass());
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		Enum<?> enum_ = this.cachedEnums.get(e.getSource());
		if (enum_ != null) {
			selectedValues.put(enum_.getClass(), enum_);
		}
	}

	private MouseAdapter myAdapter = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			AbstractButton button = cachedLinks.get(e.getSource());
			if (button != null) {
				button.setSelected(true);
			}
		}
	};

	// Be care!!!
	// Use this method only for unique enums classes!!!
	private void setUpRadioButtons(Enum<?> enumValues[], JPanel placePanel) {
		ButtonGroup group = new ButtonGroup();
		int index = 0;
		for (final Enum<?> mode : enumValues) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = index;
			final JRadioButton button = new JRadioButton();
			cachedEnums.put(button, mode);

			button.setName(mode.name());
			button.setActionCommand(mode.getClass().getName());
			button.addChangeListener(this);

			placePanel.add(button, gridBagConstraints);

			gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 1;
			gridBagConstraints.gridy = index;
			gridBagConstraints.anchor = GridBagConstraints.WEST;

			JLabel label = new JLabel();
			cachedLinks.put(label, button);

			label.setText(mode.toString());
			label.addMouseListener(this.myAdapter);

			placePanel.add(label, gridBagConstraints);

			group.add(button);
			cachedButtons.put(mode, button);

			index++;
		}

	}

	/**
	 * This method initializes jPanelFilterEditPlace
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelFilterEditPlace() {
		if (jPanelFilterEditPlace == null) {
			jPanelFilterEditPlace = new JPanel();
			jPanelFilterEditPlace.setLayout(new GridBagLayout());
			setUpRadioButtons(DeletionMode.values(), jPanelFilterEditPlace);
		}
		return jPanelFilterEditPlace;
	}

	/**
	 * This method initializes jPanelSourceMode
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSourceMode() {
		if (jPanelSourceMode == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints2.gridy = -1;
			gridBagConstraints2.weightx = 1.0D;
			gridBagConstraints2.weighty = 1.0D;
			gridBagConstraints2.gridx = -1;
			jPanelSourceMode = new JPanel();
			jPanelSourceMode.setLayout(new GridBagLayout());
			jPanelSourceMode.setBorder(BorderFactory.createTitledBorder("Исходное изображение -> 1024x1024"));
			jPanelSourceMode.add(getJPanelSourceModePlace(), gridBagConstraints2);
		}
		return jPanelSourceMode;
	}

	/**
	 * This method initializes jPanelTransactionMode
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelTransactionMode() {
		if (jPanelTransactionMode == null) {
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints16.gridy = 0;
			gridBagConstraints16.weightx = 1.0D;
			gridBagConstraints16.gridx = 0;
			jPanelTransactionMode = new JPanel();
			jPanelTransactionMode.setVisible(false);
			jPanelTransactionMode.setLayout(new GridBagLayout());
			jPanelTransactionMode.setBorder(BorderFactory.createTitledBorder("Транзакционная модель"));
			jPanelTransactionMode.add(getJPanelTransactionModePlace(), gridBagConstraints16);
		}
		return jPanelTransactionMode;
	}

	/**
	 * This method initializes jPanelTransactionModePlace
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelTransactionModePlace() {
		if (jPanelTransactionModePlace == null) {
			jPanelTransactionModePlace = new JPanel();
			jPanelTransactionModePlace.setLayout(new GridBagLayout());
			setUpRadioButtons(TestTransactionMode.values(), jPanelTransactionModePlace);
		}
		return jPanelTransactionModePlace;
	}

	/**
	 * This method initializes jPanelDeletionCheckMode
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelDeletionCheckMode() {
		if (jPanelDeletionCheckMode == null) {
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.gridx = 0;
			gridBagConstraints19.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints19.weightx = 1.0D;
			gridBagConstraints19.gridy = 0;
			jPanelDeletionCheckMode = new JPanel();
			jPanelDeletionCheckMode.setLayout(new GridBagLayout());
			jPanelDeletionCheckMode.setBorder(BorderFactory.createTitledBorder("При удалении строки из БД"));
			jPanelDeletionCheckMode.add(getJPanelDeletionCheckModePlace(), gridBagConstraints19);
		}
		return jPanelDeletionCheckMode;
	}

	/**
	 * This method initializes jPanelDeletionCheckModePlace
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelDeletionCheckModePlace() {
		if (jPanelDeletionCheckModePlace == null) {
			jPanelDeletionCheckModePlace = new JPanel();
			jPanelDeletionCheckModePlace.setLayout(new GridBagLayout());
			setUpRadioButtons(DeletionCheckMode.values(), jPanelDeletionCheckModePlace);
		}
		return jPanelDeletionCheckModePlace;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
