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
import java.awt.event.ActionListener;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.face.Application;
import edu.mgupi.pass.face.gui.template.AbstractDialogAdapter;
import edu.mgupi.pass.util.Config;
import edu.mgupi.pass.util.Config.DeletionCheckMode;
import edu.mgupi.pass.util.Config.DeletionMode;
import edu.mgupi.pass.util.Config.SourceMode;
import edu.mgupi.pass.util.Config.SupportedLocale;
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
public class SettingsDialog extends JDialog implements ChangeListener, ActionListener {

	private final static Logger logger = LoggerFactory.getLogger(SettingsDialog.class); // @jve:decl-index=0:

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanelButtons = null;
	private JButton jButtonOK = null;
	private JButton jButtonCancel = null;
	private JPanel jPanelSettings = null;
	private JPanel jPanelLaF = null;
	private JPanel jPanelLocale = null;
	private JComboBox jComboBoxLaF = null;
	private JLabel jLabelLaF = null;
	//	private JLabel jLabelLocale = null;
	private JPanel jPanelLocalePlace = null;

	//	private JComboBox jComboBoxLocale = null;

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
		this.setLocation(300, 200);
		this.setName("settingsDialog");
		this.setModal(true);
		this.setTitle(Messages.getString("SettingsDialog.title"));
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

				/*
				 * Reloading window size.
				 */
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
				if (Config.getInstance().setRowsDeleteMode(
						(DeletionMode) getValue(DeletionMode.class))) {
					logger.debug("Deletion mode changed. Save common.");
					needSaveCommon = true;
				}

				if (Config.getInstance().setTransactionMode(
						(TestTransactionMode) getValue(TestTransactionMode.class))) {
					logger.debug("Transaction mode changed. Save common.");
					needSaveCommon = true;
				}

				if (Config.getInstance().setDeletionCheckModeMode(
						(DeletionCheckMode) getValue(DeletionCheckMode.class))) {
					logger.debug("Deletion check mode changed. Save common.");
					needSaveCommon = true;
				}

				if (Config.getInstance().setCurrentLocale(
						(SupportedLocale) getValue(SupportedLocale.class))) {
					logger.debug("Current locale changed. Save common.");

					needSaveCommon = true;

					boolean restart = false;
					if (Application.isRestartAvailable()) {
						restart = JOptionPane.showConfirmDialog(SettingsDialog.this, Messages
								.getString("SettingsDialog.confirm.localeReload"), Messages
								.getString("SettingsDialog.title.localeReload"),
								JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
						if (restart) {
							Application.enqueRestart();
						}
					} else {
						JOptionPane.showMessageDialog(SettingsDialog.this, Messages
								.getString("SettingsDialog.confirm.localeReloadManual"), Messages
								.getString("SettingsDialog.title.localeReload"),
								JOptionPane.INFORMATION_MESSAGE);
					}

				}

				if (Config.getInstance().setCurrentSourceMode(
						(SourceMode) jComboBoxSourceMode.getSelectedItem())) {
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

		String currentLaF = Config.getInstance().getLookAndFeel();
		for (Map.Entry<String, String> key : this.lafs.entrySet()) {
			if (key.getValue().equals(currentLaF)) {
				jComboBoxLaF.setSelectedItem(key.getKey());
			}
		}

		jComboBoxSourceMode.setSelectedItem(currentSource);

		Color currentBackground = Config.getInstance().getCurrentBackground();
		newBackground = currentBackground;
		jLabelBackgroundShow.setBackground(currentBackground);

		this.setValue(Config.getInstance().getRowsDeleteMode());
		this.setValue(Config.getInstance().getTransactionMode());
		this.setValue(Config.getInstance().getDeletionCheckMode());
		this.setValue(Config.getInstance().getCurrentLocale());
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
	private JPanel getJPanelLocale() {
		if (jPanelLocale == null) {
			GridBagConstraints jbcLocale = new GridBagConstraints();
			jbcLocale.fill = GridBagConstraints.NONE;
			jbcLocale.anchor = GridBagConstraints.WEST;
			jbcLocale.weightx = 1.0D;
			jbcLocale.gridx = 0;
			jbcLocale.gridy = 0;
			jbcLocale.insets = new Insets(0, 5, 0, 5);

			jPanelLocale = new JPanel();
			jPanelLocale.setLayout(new GridBagLayout());
			jPanelLocale.setBorder(BorderFactory.createTitledBorder(Messages
					.getString("SettingsDialog.localeBorder")));
			jPanelLocale.setName("jPanelLocale");
			jPanelLocale.add(getJPanelLocalePlace(), jbcLocale);
		}
		return jPanelLocale;
	}

	/**
	 * This method initializes jPanelLaF
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelLaF() {
		if (jPanelLaF == null) {
			GridBagConstraints jbcLaf = new GridBagConstraints();
			jbcLaf.fill = GridBagConstraints.NONE;
			jbcLaf.anchor = GridBagConstraints.WEST;
			jbcLaf.weightx = 1.0D;
			jbcLaf.gridx = 0;
			jbcLaf.gridy = 0;
			jbcLaf.insets = new Insets(0, 5, 0, 5);

			jLabelLaF = new JLabel();
			jLabelLaF.setText(Messages.getString("SettingsDialog.laf"));
			jPanelLaF = new JPanel();
			jPanelLaF.setLayout(new GridBagLayout());
			jPanelLaF.setBorder(BorderFactory.createTitledBorder(Messages
					.getString("SettingsDialog.lafBorder")));
			jPanelLaF.setName("jPanelLaF");
			jPanelLaF.add(getJPanelLaFPlace(), jbcLaf);
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

			jLabelBackgroundShow = new JLabel();
			jLabelBackgroundShow.setText(" ");
			jLabelBackgroundShow.setName("backgroundSample");
			jLabelBackgroundShow.setOpaque(true);

			jLabelBackground = new JLabel();
			jLabelBackground.setText(Messages.getString("SettingsDialog.backgroundImage"));

			jLabelSourceMode = new JLabel();
			jLabelSourceMode.setText(Messages.getString("SettingsDialog.imageLoadMode"));

			GridBagConstraints jbcSourceMode = new GridBagConstraints();
			jbcSourceMode.gridx = 0;
			jbcSourceMode.insets = new Insets(0, 5, 0, 5);
			jbcSourceMode.anchor = GridBagConstraints.EAST;
			jbcSourceMode.gridy = 0;

			GridBagConstraints jbcComboSourceMode = new GridBagConstraints();
			jbcComboSourceMode.fill = GridBagConstraints.VERTICAL;
			jbcComboSourceMode.gridx = 1;
			jbcComboSourceMode.gridy = 0;
			jbcComboSourceMode.gridwidth = 2;
			jbcComboSourceMode.weightx = 1.0;

			GridBagConstraints jbcBackground = new GridBagConstraints();
			jbcBackground.gridx = 0;
			jbcBackground.gridy = 1;
			jbcBackground.anchor = GridBagConstraints.EAST;
			jbcBackground.insets = new Insets(0, 5, 0, 5);

			GridBagConstraints jbcButtonBackground = new GridBagConstraints();
			jbcButtonBackground.gridx = 1;
			jbcButtonBackground.anchor = GridBagConstraints.WEST;
			jbcButtonBackground.gridy = 1;

			GridBagConstraints jbcLabelBackgroundShow = new GridBagConstraints();
			jbcLabelBackgroundShow.gridx = 2;
			jbcLabelBackgroundShow.fill = GridBagConstraints.HORIZONTAL;
			jbcLabelBackgroundShow.weightx = 0.0D;
			jbcLabelBackgroundShow.anchor = GridBagConstraints.WEST;
			jbcLabelBackgroundShow.insets = new Insets(0, 5, 0, 5);
			jbcLabelBackgroundShow.gridy = 1;

			jPanelSourceModePlace = new JPanel();
			jPanelSourceModePlace.setLayout(new GridBagLayout());
			jPanelSourceModePlace.add(jLabelSourceMode, jbcSourceMode);
			jPanelSourceModePlace.add(getJComboBoxSourceMode(), jbcComboSourceMode);
			jPanelSourceModePlace.add(jLabelBackground, jbcBackground);
			jPanelSourceModePlace.add(getJButtonBackground(), jbcButtonBackground);
			jPanelSourceModePlace.add(jLabelBackgroundShow, jbcLabelBackgroundShow);
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

	private JPanel jPanelDeletionMode = null;

	private JPanel jPanelDeletionModePlace = null;

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
			jTabbedPaneSettings.addTab(Messages.getString("SettingsDialog.currentSettings"), null,
					getJPanelSettingsCurrent(), null);
			jTabbedPaneSettings.addTab(Messages.getString("SettingsDialog.commonSettings"), null,
					getJPanelSettingsCommon(), null);
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
			GridBagConstraints jbcLaf = new GridBagConstraints();
			jbcLaf.anchor = GridBagConstraints.NORTH;
			jbcLaf.weightx = 1.0D;
			jbcLaf.weighty = 0.0D;
			jbcLaf.fill = GridBagConstraints.HORIZONTAL;
			jbcLaf.gridx = 0;
			jbcLaf.gridy = 0;

			GridBagConstraints jbcLocale = new GridBagConstraints();
			jbcLocale.anchor = GridBagConstraints.NORTH;
			jbcLocale.fill = GridBagConstraints.HORIZONTAL;
			jbcLocale.weightx = 1.0D;
			jbcLocale.weighty = 0.0D;
			jbcLocale.gridx = 0;
			jbcLocale.gridy = 1;

			GridBagConstraints jbcDeletioMode = new GridBagConstraints();
			jbcDeletioMode.weightx = 1.0D;
			jbcDeletioMode.fill = GridBagConstraints.HORIZONTAL;
			jbcDeletioMode.weighty = 0.0D;
			jbcDeletioMode.anchor = GridBagConstraints.NORTH;
			jbcDeletioMode.gridx = 0;
			jbcDeletioMode.gridy = 2;

			GridBagConstraints jbcTransactionMode = new GridBagConstraints();
			jbcTransactionMode.anchor = GridBagConstraints.NORTH;
			jbcTransactionMode.fill = GridBagConstraints.HORIZONTAL;
			jbcTransactionMode.weightx = 1.0D;
			jbcTransactionMode.weighty = 0.0D;
			jbcTransactionMode.gridx = 0;
			jbcTransactionMode.gridy = 3;

			GridBagConstraints jbcDeletionCheckMode = new GridBagConstraints();
			jbcDeletionCheckMode.weighty = 1.0D;
			jbcDeletionCheckMode.weightx = 1.0D;
			jbcDeletionCheckMode.fill = GridBagConstraints.HORIZONTAL;
			jbcDeletionCheckMode.anchor = GridBagConstraints.NORTH;
			jbcDeletionCheckMode.gridx = 0;
			jbcDeletionCheckMode.gridy = 4;

			jPanelSettingsCommon = new JPanel();
			jPanelSettingsCommon.setLayout(new GridBagLayout());
			jPanelSettingsCommon.setName("settingsCommon");
			jPanelSettingsCommon.add(getJPanelLaF(), jbcLaf);
			jPanelSettingsCommon.add(getJPanelLocale(), jbcLocale);
			jPanelSettingsCommon.add(getJPanelDeletionMode(), jbcDeletioMode);
			jPanelSettingsCommon.add(getJPanelTransactionMode(), jbcTransactionMode);
			jPanelSettingsCommon.add(getJPanelDeletionCheckMode(), jbcDeletionCheckMode);
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

			/*
			 * / Shit-head Visual Editor shows me Color chooser dialog every
			 * time opening this page (if a use addActionListener for instance
			 * instead of setAction)
			 */
			jButtonBackground.setAction(new AbstractAction() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					Color newBack = JColorChooser.showDialog(SettingsDialog.this, Messages
							.getString("SettingsDialog.title.backgroundSelect"),
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
	 * This method initializes jPanelLaFPlace
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelLocalePlace() {
		if (jPanelLocalePlace == null) {
			jPanelLocalePlace = new JPanel();
			//setUpRadioButtons(SupportedLocale.values(), jPanelLocalePlace);
			setUpComboBox(SupportedLocale.values(), Messages.getString("SettingsDialog.locale"),
					jPanelLocalePlace);
			//			GridBagConstraints jbcLabel = new GridBagConstraints();
			//			jbcLabel.insets = new Insets(0, 0, 0, 5);
			//			jbcLabel.gridy = 0;
			//			jbcLabel.gridx = 0;
			//			GridBagConstraints jbcCombo = new GridBagConstraints();
			//			jbcCombo.fill = GridBagConstraints.NONE;
			//			jbcCombo.gridy = 0;
			//			jbcCombo.gridx = 1;
			//
			//			jLabelLocale = new JLabel();
			//			jLabelLocale.setText(Messages.getString("SettingsDialog.locale")); 
			//
			//			for (LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
			//				lafs.put(laf.getName(), laf.getClassName());
			//			}
			//
			//			jComboBoxLocale = new JComboBox(lafs.keySet().toArray(new String[0]));
			//			jComboBoxLocale.setName("laf"); 
			//
			//			jPanelLocalePlace = new JPanel();
			//			jPanelLocalePlace.setLayout(new GridBagLayout());
			//			jPanelLocalePlace.add(jComboBoxLocale, jbcCombo);
			//			jPanelLocalePlace.add(jLabelLocale, jbcLabel);
		}
		return jPanelLocalePlace;
	}

	/**
	 * This method initializes jPanelFilterEdit
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelDeletionMode() {
		if (jPanelDeletionMode == null) {
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 0;
			gridBagConstraints13.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints13.weightx = 1.0D;
			gridBagConstraints13.gridy = 0;
			jPanelDeletionMode = new JPanel();
			jPanelDeletionMode.setLayout(new GridBagLayout());
			jPanelDeletionMode.setBorder(BorderFactory.createTitledBorder(Messages
					.getString("SettingsDialog.deletionMode")));
			jPanelDeletionMode.add(getJPanelDeletionModePlace(), gridBagConstraints13);

		}
		return jPanelDeletionMode;
	}

	private JPanel jPanelSourceMode = null;

	private JPanel jPanelTransactionMode = null;

	private JPanel jPanelTransactionModePlace = null;

	private Map<JLabel, JRadioButton> cachedLinks = new HashMap<JLabel, JRadioButton>(); //  @jve:decl-index=0:
	private Map<JRadioButton, Enum<?>> cachedEnums = new HashMap<JRadioButton, Enum<?>>(); //  @jve:decl-index=0:
	private Map<Enum<?>, JRadioButton> cachedButtons = new HashMap<Enum<?>, JRadioButton>(); //  @jve:decl-index=0:

	@SuppressWarnings("unchecked")
	private Map<Class<? extends Enum>, JComboBox> cachedCombos = new HashMap<Class<? extends Enum>, JComboBox>(); //  @jve:decl-index=0:
	@SuppressWarnings("unchecked")
	private Map<Class<? extends Enum>, Enum<?>> selectedValues = new HashMap<Class<? extends Enum>, Enum<?>>(); //  @jve:decl-index=0:	

	private JPanel jPanelDeletionCheckMode = null;

	private JPanel jPanelDeletionCheckModePlace = null;

	private void setValue(Enum<?> value) {
		selectedValues.put(value.getClass(), value);

		JRadioButton radio = cachedButtons.get(value);
		if (radio != null) {
			radio.setSelected(true);
		} else {
			JComboBox combo = cachedCombos.get(value.getClass());
			if (combo != null) {
				combo.setSelectedItem(value);
			} else {
				AppHelper.showErrorDialog(this, Messages.getString(
						"SettingsDialog.err.enumRecipient", value.getClass()));
			}
		}

	}

	@SuppressWarnings("unchecked")
	private Enum<?> getValue(Class<? extends Enum> clazz) {
		return selectedValues.get(clazz);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		Enum<?> enum_ = this.cachedEnums.get(e.getSource());
		if (enum_ != null) {
			selectedValues.put(enum_.getClass(), enum_);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JComboBox) {
			JComboBox box = (JComboBox) e.getSource();
			Enum<?> enum_ = (Enum<?>) box.getSelectedItem();
			if (enum_ != null) {
				selectedValues.put(enum_.getClass(), enum_);
			}
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
		if (enumValues == null) {
			throw new IllegalArgumentException("Internal error. 'enumValues' must be not null.");
		}
		if (placePanel == null) {
			throw new IllegalArgumentException("Internal error. 'placePanel' must be not null.");
		}

		placePanel.setLayout(new GridBagLayout());
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

	private void setUpComboBox(Enum<?> enumValues[], String title, JPanel placePanel) {
		if (enumValues == null || enumValues.length == 0) {
			throw new IllegalArgumentException(
					"Internal error. 'enumValues' must be not null and not empty.");
		}
		if (placePanel == null) {
			throw new IllegalArgumentException("Internal error. 'placePanel' must be not null.");
		}

		placePanel.setLayout(new GridBagLayout());

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 0, 0, 5);
		placePanel.add(new JLabel(title), gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.anchor = GridBagConstraints.WEST;

		JComboBox comboBox = new JComboBox(enumValues);
		comboBox.addActionListener(this);
		placePanel.add(comboBox, gridBagConstraints);

		cachedCombos.put(enumValues[0].getClass(), comboBox);

	}

	/**
	 * This method initializes jPanelFilterEditPlace
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelDeletionModePlace() {
		if (jPanelDeletionModePlace == null) {
			jPanelDeletionModePlace = new JPanel();
			setUpRadioButtons(DeletionMode.values(), jPanelDeletionModePlace);
		}
		return jPanelDeletionModePlace;
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
			jPanelSourceMode.setBorder(BorderFactory.createTitledBorder(Messages
					.getString("SettingsDialog.sourceImage")));
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
			jPanelTransactionMode.setBorder(BorderFactory.createTitledBorder(Messages
					.getString("SettingsDialog.transMode")));
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
			jPanelDeletionCheckMode.setBorder(BorderFactory.createTitledBorder(Messages
					.getString("SettingsDialog.deletionCheckMode")));
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
			setUpRadioButtons(DeletionCheckMode.values(), jPanelDeletionCheckModePlace);
		}
		return jPanelDeletionCheckModePlace;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
