package edu.mgupi.pass.face.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.db.DBHelper;
import edu.mgupi.pass.db.PasswordNotEqualsException;
import edu.mgupi.pass.db.UserNotFoundException;
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
public class SettingsWindow extends JDialog implements ActionListener {

	private final static Logger logger = LoggerFactory.getLogger(SettingsWindow.class); // @jve:decl-index=0:

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanelButtons = null;
	private JButton jButtonOK = null;
	private JButton jButtonCancel = null;
	private JPanel jPanelSettings = null;

	//	private JPanel jPanelLocale = null;

	//	private JLabel jLabelLocale = null;
	//	private JPanel jPanelLocalePlace = null;

	//	private JComboBox jComboBoxLocale = null;

	/**
	 * Default constructor
	 * 
	 * @param owner
	 */
	public SettingsWindow(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setBounds(300, 200, 600, 400);
		this.setName("settingsDialog");
		this.setModal(true);
		this.setTitle(Messages.getString("SettingsWindow.title"));
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
				//SettingsWindow.this.resetControls();
			}

			@Override
			protected void openDialogImpl() throws Exception {
				SettingsWindow.this.resetControls();

				/*
				 * Reloading window size.
				 */
				pack();
				setMinimumSize(new Dimension(getWidth(), getHeight()));
			}

			@Override
			protected boolean saveImpl() throws Exception {
				//				boolean needSaveCommon = false;
				boolean needRestartProcessing = false;

				// LaF apply
				if (Config.getInstance().setLookAndFeel(lafs.get(jComboBoxLaF.getSelectedItem()))) {
					logger.debug("LaF changed. Save common.");
					//					needSaveCommon = true;
				}

				// Do not be scare :)
				// I use class-map for enums 
				if (Config.getInstance().setRowsDeleteMode(
						(DeletionMode) getValue(DeletionMode.class))) {
					logger.debug("Deletion mode changed. Save common.");
					//					needSaveCommon = true;
				}

				if (Config.getInstance().setTransactionMode(
						(TestTransactionMode) getValue(TestTransactionMode.class))) {
					logger.debug("Transaction mode changed. Save common.");
					//					needSaveCommon = true;
				}

				if (Config.getInstance().setDeletionCheckModeMode(
						(DeletionCheckMode) getValue(DeletionCheckMode.class))) {
					logger.debug("Deletion check mode changed. Save common.");
					//					needSaveCommon = true;
				}

				if (Config.getInstance().setCurrentLocale(
						(SupportedLocale) getValue(SupportedLocale.class))) {
					logger.debug("Current locale changed. Save common.");

					//					needSaveCommon = true;

					boolean restart = false;
					if (Application.isRestartAvailable()) {
						restart = JOptionPane.showConfirmDialog(SettingsWindow.this, Messages
								.getString("SettingsWindow.confirm.localeReload"), Messages
								.getString("SettingsWindow.title.localeReload"),
								JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
						if (restart) {
							Application.enqueRestart();
						}
					} else {
						JOptionPane.showMessageDialog(SettingsWindow.this, Messages
								.getString("SettingsWindow.confirm.localeReloadManual"), Messages
								.getString("SettingsWindow.title.localeReload"),
								JOptionPane.INFORMATION_MESSAGE);
					}

				}

				if (Config.getInstance().setURL(jURL.getText())) {
					logger.debug("URL changed. Save common.");
					//					needSaveCommon = true;
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

				//if (this.needSaveCommon) {
				logger.debug("Saving settings...");
				Config.getInstance().saveCommonConfig();
				//}

				return needRestartProcessing;

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

		this.jURL.setText(Config.getInstance().getURL());
		this.jLogin.setText(Config.getInstance().getLogin());
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
			GridBagConstraints jbc = AppHelper.getJBCBorderPanel(0, true);
			jbc.fill = GridBagConstraints.BOTH;
			jPanelSettings = new JPanel();
			jPanelSettings.setLayout(new GridBagLayout());
			jPanelSettings.add(getJTabbedPaneSettings(), jbc);
		}
		return jPanelSettings;
	}

	private JTabbedPane jTabbedPaneSettings = null;

	/**
	 * This method initializes jTabbedPaneSettings
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPaneSettings() {
		if (jTabbedPaneSettings == null) {
			jTabbedPaneSettings = new JTabbedPane();
			jTabbedPaneSettings.setName("settingsPane");
			jTabbedPaneSettings.addTab(Messages.getString("SettingsWindow.currentSettings"), null,
					getJPanelSettingsCurrent());
			jTabbedPaneSettings.addTab(Messages.getString("SettingsWindow.commonSettings"), null,
					getJPanelSettingsCommon());
			jTabbedPaneSettings.addTab(Messages.getString("SettingsWindow.connectSettings"), null,
					getJPanelSettingsConnect());
		}
		return jTabbedPaneSettings;
	}

	/*
	 * 
	 * -------------------------------------------------------------------------
	 * 
	 * CURRENT
	 * 
	 * -------------------------------------------------------------------------
	 */

	private JPanel jPanelSettingsCurrent = null;

	/**
	 * This method initializes jPanelSettingsCurrent
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSettingsCurrent() {
		if (jPanelSettingsCurrent == null) {
			jPanelSettingsCurrent = new JPanel();
			jPanelSettingsCurrent.setLayout(new GridBagLayout());
			jPanelSettingsCurrent.setName("settingsCurrent");
			jPanelSettingsCurrent.add(getJPanelSourceMode(), AppHelper.getJBCBorderPanel(0, true));
		}
		return jPanelSettingsCurrent;
	}

	private JPanel jPanelSourceMode = null;

	/**
	 * This method initializes jPanelSourceMode
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSourceMode() {
		if (jPanelSourceMode == null) {
			jPanelSourceMode = new JPanel();
			jPanelSourceMode.setLayout(new GridBagLayout());
			jPanelSourceMode.setBorder(BorderFactory.createTitledBorder(Messages
					.getString("SettingsWindow.sourceImageBorder")));
			jPanelSourceMode.add(getJPanelSourceModePlace(), AppHelper.getJBCInBorderPanel());
		}
		return jPanelSourceMode;
	}

	private JPanel jPanelSourceModePlace = null;
	private JLabel jLabelBackgroundShow = null;
	private JLabel jLabelBackground = null;
	private JLabel jLabelSourceMode = null;

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
			jLabelBackground.setText(Messages.getString("SettingsWindow.source.backgroundImage"));

			jLabelSourceMode = new JLabel();
			jLabelSourceMode.setText(Messages.getString("SettingsWindow.source.imageLoadMode"));

			jPanelSourceModePlace = new JPanel();
			jPanelSourceModePlace.setLayout(new GridBagLayout());
			jPanelSourceModePlace.add(jLabelSourceMode, AppHelper.getJBCForm(0, 0));
			jPanelSourceModePlace.add(getJComboBoxSourceMode(), AppHelper
					.getJBCForm(1, 0, 2, false));
			jPanelSourceModePlace.add(jLabelBackground, AppHelper.getJBCForm(0, 1));
			jPanelSourceModePlace.add(getJButtonBackground(), AppHelper.getJBCForm(1, 1));
			jPanelSourceModePlace.add(jLabelBackgroundShow, AppHelper.getJBCForm(2, 1, true));
		}
		return jPanelSourceModePlace;
	}

	private JComboBox jComboBoxSourceMode = null;

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

	private JButton jButtonBackground = null;

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
					Color newBack = JColorChooser.showDialog(SettingsWindow.this, Messages
							.getString("SettingsWindow.title.backgroundSelect"),
							SettingsWindow.this.newBackground);
					if (newBack != null) {
						SettingsWindow.this.newBackground = newBack;
						jLabelBackgroundShow.setBackground(newBack);
					}
				}
			});
			jButtonBackground.setText("...");
			jButtonBackground.setName("backgroundColor");
		}
		return jButtonBackground;
	}

	/*
	 * 
	 * -------------------------------------------------------------------------
	 * 
	 * COMMON
	 * 
	 * -------------------------------------------------------------------------
	 */

	private JPanel jPanelSettingsCommon = null;

	/**
	 * This method initializes jPanelSettingsCommon
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSettingsCommon() {
		if (jPanelSettingsCommon == null) {
			jPanelSettingsCommon = new JPanel();
			jPanelSettingsCommon.setLayout(new GridBagLayout());
			jPanelSettingsCommon.setName("settingsCommon");
			jPanelSettingsCommon.add(getJPanelInterface(), AppHelper.getJBCBorderPanel(0, false));
			jPanelSettingsCommon
					.add(getJPanelDeletionMode(), AppHelper.getJBCBorderPanel(1, false));
			jPanelSettingsCommon.add(getJPanelTransactionMode(), AppHelper.getJBCBorderPanel(2,
					false));
			jPanelSettingsCommon.add(getJPanelDeletionCheckMode(), AppHelper.getJBCBorderPanel(3,
					true));
		}
		return jPanelSettingsCommon;
	}

	/*
	 * 
	 * -------------------------------------------------------------------------
	 * 
	 * COMMON.INTERFACE
	 * 
	 * -------------------------------------------------------------------------
	 */

	private JPanel jPanelInterface = null;

	/**
	 * This method initializes jPanelLaF
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelInterface() {
		if (jPanelInterface == null) {

			jPanelInterface = new JPanel();
			jPanelInterface.setLayout(new GridBagLayout());
			jPanelInterface.setBorder(BorderFactory.createTitledBorder(Messages
					.getString("SettingsWindow.intefaceBorder")));
			jPanelInterface.setName("jPanelLaF");
			jPanelInterface.add(getJPanelInterfacePlace(), AppHelper.getJBCInBorderPanel());
		}
		return jPanelInterface;
	}

	private JPanel jPanelInterfacePlace = null;
	private JLabel jLabelLaF = null;

	/**
	 * This method initializes jPanelLaFPlace
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelInterfacePlace() {
		if (jPanelInterfacePlace == null) {
			jLabelLaF = new JLabel();
			jLabelLaF.setText(Messages.getString("SettingsWindow.laf"));

			jPanelInterfacePlace = new JPanel();
			jPanelInterfacePlace.setLayout(new GridBagLayout());
			jPanelInterfacePlace.add(jLabelLaF, AppHelper.getJBCForm(0, 0));
			jPanelInterfacePlace.add(getJComboBoxLaF(), AppHelper.getJBCForm(1, 0));

			setUpComboBox(SupportedLocale.values(), Messages.getString("SettingsWindow.locale"),
					jPanelInterfacePlace, 1);
		}
		return jPanelInterfacePlace;
	}

	private JComboBox jComboBoxLaF = null;
	private Map<String, String> lafs = new LinkedHashMap<String, String>(); // @jve:decl-index=0:

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

	/*
	 * 
	 * -------------------------------------------------------------------------
	 * 
	 * COMMON.DELETION_MODE
	 * 
	 * -------------------------------------------------------------------------
	 */

	private JPanel jPanelDeletionMode = null;

	private JPanel jPanelDeletionModePlace = null;

	/**
	 * This method initializes jPanelFilterEdit
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelDeletionMode() {
		if (jPanelDeletionMode == null) {

			jPanelDeletionMode = new JPanel();
			jPanelDeletionMode.setLayout(new GridBagLayout());
			jPanelDeletionMode.setBorder(BorderFactory.createTitledBorder(Messages
					.getString("SettingsWindow.deletionMode")));

			jPanelDeletionModePlace = new JPanel();
			setUpRadioButtons(DeletionMode.values(), jPanelDeletionModePlace);

			jPanelDeletionMode.add(jPanelDeletionModePlace, AppHelper.getJBCInBorderPanel());

		}
		return jPanelDeletionMode;
	}

	/*
	 * 
	 * -------------------------------------------------------------------------
	 * 
	 * COMMON.TRANSACTION_MODE
	 * 
	 * -------------------------------------------------------------------------
	 */

	private JPanel jPanelTransactionMode = null;
	private JPanel jPanelTransactionModePlace = null;

	/**
	 * This method initializes jPanelTransactionMode
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelTransactionMode() {
		if (jPanelTransactionMode == null) {
			jPanelTransactionMode = new JPanel();
			jPanelTransactionMode.setVisible(false);
			jPanelTransactionMode.setLayout(new GridBagLayout());
			jPanelTransactionMode.setBorder(BorderFactory.createTitledBorder(Messages
					.getString("SettingsWindow.transMode")));

			jPanelTransactionModePlace = new JPanel();
			setUpRadioButtons(TestTransactionMode.values(), jPanelTransactionModePlace);

			jPanelTransactionMode.add(jPanelTransactionModePlace, AppHelper.getJBCInBorderPanel());
		}
		return jPanelTransactionMode;
	}

	/*
	 * 
	 * -------------------------------------------------------------------------
	 * 
	 * COMMON.DELETION_CHECK_MODE
	 * 
	 * -------------------------------------------------------------------------
	 */
	private JPanel jPanelDeletionCheckMode = null;

	private JPanel jPanelDeletionCheckModePlace = null;

	/**
	 * This method initializes jPanelDeletionCheckMode
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelDeletionCheckMode() {
		if (jPanelDeletionCheckMode == null) {
			jPanelDeletionCheckMode = new JPanel();
			jPanelDeletionCheckMode.setLayout(new GridBagLayout());
			jPanelDeletionCheckMode.setBorder(BorderFactory.createTitledBorder(Messages
					.getString("SettingsWindow.deletionCheckMode")));

			jPanelDeletionCheckModePlace = new JPanel();
			setUpRadioButtons(DeletionCheckMode.values(), jPanelDeletionCheckModePlace);

			jPanelDeletionCheckMode.add(jPanelDeletionCheckModePlace, AppHelper
					.getJBCInBorderPanel());
		}
		return jPanelDeletionCheckMode;
	}

	/*
	 * 
	 * -------------------------------------------------------------------------
	 * 
	 * CONNECT
	 * 
	 * -------------------------------------------------------------------------
	 */

	private JPanel jPanelSettingsConnect = null;

	/**
	 * This method initializes jPanelSettingsCurrent
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSettingsConnect() {
		if (jPanelSettingsConnect == null) {
			jPanelSettingsConnect = new JPanel();
			jPanelSettingsConnect.setLayout(new GridBagLayout());
			jPanelSettingsConnect.setName("settingsConnect");
			jPanelSettingsConnect.add(getJPanelConnect(), AppHelper.getJBCBorderPanel(0, true));
		}
		return jPanelSettingsConnect;
	}

	private JPanel jPanelConnect = null;

	/**
	 * This method initializes jPanelSourceMode
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelConnect() {
		if (jPanelConnect == null) {
			jPanelConnect = new JPanel();
			jPanelConnect.setLayout(new GridBagLayout());
			jPanelConnect.setBorder(BorderFactory.createTitledBorder(Messages
					.getString("SettingsWindow.connectSettingsBorder")));
			jPanelConnect.add(getJPanelConnectPlace(), AppHelper.getJBCInBorderPanel());
		}
		return jPanelConnect;
	}

	private JPanel jPanelConnectPlace = null;

	private JLabel jLabelLogin = null;
	private JLabel jLabelPassword = null;
	private JLabel jLabelURL = null;

	private JLabel jLogin = null;
	private JTextField jURL = null;

	/**
	 * This method initializes jPanelSourceModePlace
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelConnectPlace() {
		if (jPanelConnectPlace == null) {

			jLabelLogin = new JLabel();
			jLabelLogin.setText(Messages.getString("SettingsWindow.conn.login"));

			jLabelPassword = new JLabel();
			jLabelPassword.setText(Messages.getString("SettingsWindow.conn.password"));

			jLabelURL = new JLabel();
			jLabelURL.setText(Messages.getString("SettingsWindow.conn.url"));

			jURL = new JTextField(30);
			jLogin = new JLabel();

			jPanelConnectPlace = new JPanel();
			jPanelConnectPlace.setLayout(new GridBagLayout());

			jPanelConnectPlace.add(jLabelURL, AppHelper.getJBCForm(0, 0));
			jPanelConnectPlace.add(jURL, AppHelper.getJBCForm(1, 0, 2, false));

			jPanelConnectPlace.add(jLabelLogin, AppHelper.getJBCForm(0, 1));
			jPanelConnectPlace.add(jLogin, AppHelper.getJBCForm(1, 1, 2, false));

			jPanelConnectPlace.add(jLabelPassword, AppHelper.getJBCForm(0, 2));
			jPanelConnectPlace.add(getJButtonPasswordChange(), AppHelper.getJBCForm(1, 2));
			jPanelConnectPlace.add(getJButtonPasswordForget(), AppHelper.getJBCForm(2, 2));

		}
		return jPanelConnectPlace;
	}

	private JButton jButtonPasswordChange;

	private JPanel jPanelChangePassword = null;
	private JPasswordField jOldPassword = null;
	private JPasswordField jNewPassword = null;
	private JPasswordField jNewPasswordCofirm = null;

	private JPanel getJPanelChangePassword() {
		if (jPanelChangePassword == null) {
			jPanelChangePassword = new JPanel();

			jPanelChangePassword.setLayout(new GridBagLayout());

			jOldPassword = new JPasswordField(20);
			jNewPassword = new JPasswordField(20);
			jNewPasswordCofirm = new JPasswordField(20);

			jPanelChangePassword.add(new JLabel(Messages
					.getString("SettingsWindow.conn.password.change.old")), AppHelper.getJBCForm(0,
					0));
			jPanelChangePassword.add(jOldPassword, AppHelper.getJBCForm(1, 0, true));

			jPanelChangePassword.add(new JLabel(Messages
					.getString("SettingsWindow.conn.password.change.new")), AppHelper.getJBCForm(0,
					1));
			jPanelChangePassword.add(jNewPassword, AppHelper.getJBCForm(1, 1, true));

			jPanelChangePassword.add(new JLabel(Messages
					.getString("SettingsWindow.conn.password.change.newConfirm")), AppHelper
					.getJBCForm(0, 2));
			jPanelChangePassword.add(jNewPasswordCofirm, AppHelper.getJBCForm(1, 2, true));

			jOldPassword.requestFocusInWindow();
		}

		jOldPassword.setText(Config.getInstance().getPassword());
		jNewPassword.setText("");
		jNewPasswordCofirm.setText("");

		return jPanelChangePassword;
	}

	private void changePassowrdImpl() {
		if (JOptionPane.showConfirmDialog(SettingsWindow.this, getJPanelChangePassword(), Messages
				.getString("SettingsWindow.title.password.change"), JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

			if (!Arrays.equals(jNewPassword.getPassword(), jNewPasswordCofirm.getPassword())) {
				AppHelper.showErrorDialog(SettingsWindow.this, Messages
						.getString("SettingsWindow.err.password.change.newNotEquals"));
				return;
			}

			try {
				DBHelper.changePassword(new String(jOldPassword.getPassword()), new String(
						jNewPassword.getPassword()));

				AppHelper.showInfoDialog(SettingsWindow.this, Messages
						.getString("SettingsWindow.password.change.OK"));

				if (Config.getInstance().hasSavedPassword()) {
					Config.getInstance().setPassword(new String(jNewPassword.getPassword()));

					/*
					 * Immediately save new password
					 */
					Config.getInstance().saveCommonConfig();
				}

			} catch (UserNotFoundException unfe) {
				AppHelper.showErrorDialog(SettingsWindow.this, Messages.getString(
						"SettingsWindow.err.password.change.userNotFound", unfe));
			} catch (PasswordNotEqualsException pnee) {
				AppHelper.showErrorDialog(SettingsWindow.this, Messages
						.getString("SettingsWindow.err.password.change.oldNotEquals"));
			} catch (Exception ex) {
				AppHelper.showExceptionDialog(SettingsWindow.this, Messages
						.getString("SettingsWindow.err.password.change"), ex);
			}
		}

	}

	private JButton getJButtonPasswordChange() {
		if (jButtonPasswordChange == null) {
			jButtonPasswordChange = new JButton();
			jButtonPasswordChange.addActionListener(this);

			jButtonPasswordChange
					.setText(Messages.getString("SettingsWindow.conn.password.change"));
			jButtonPasswordChange.setName("changePassword");
		}
		return jButtonPasswordChange;
	}

	private JButton jButtonPasswordForget;

	private void forgetPasswordImpl() {
		if (AppHelper.showConfirmDialog(this, Messages
				.getString("SettingsWindow.confirm.conn.password.forget"))) {
			try {
				Config.getInstance().setPassword(null);
				Config.getInstance().saveCommonConfig();

				jButtonPasswordForget.setEnabled(false);
			} catch (Exception ex) {
				AppHelper.showExceptionDialog(SettingsWindow.this, Messages
						.getString("SettingsWindow.err.password.forget"), ex);
			}
		}
	}

	private JButton getJButtonPasswordForget() {
		if (jButtonPasswordForget == null) {
			jButtonPasswordForget = new JButton();
			jButtonPasswordForget.addActionListener(this);

			jButtonPasswordForget
					.setText(Messages.getString("SettingsWindow.conn.password.forget"));
			jButtonPasswordForget.setName("forgetPassword");
			jButtonPasswordForget.setEnabled(Config.getInstance().hasSavedPassword());
		}
		return jButtonPasswordForget;
	}

	/*
	 * -------------------------------------------------------------------------
	 * -------------------------------------------------------------------------
	 * -------------------------------------------------------------------------
	 * -------------------------------------------------------------------------
	 * -------------------------------------------------------------------------
	 * -------------------------------------------------------------------------
	 * -------------------------------------------------------------------------
	 * -------------------------------------------------------------------------
	 */

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JComboBox) {
			JComboBox box = (JComboBox) e.getSource();
			Enum<?> enum_ = (Enum<?>) box.getSelectedItem();
			if (enum_ != null) {
				logger.debug("Set value " + enum_.name() + " for " + enum_.getClass());
				selectedValues.put(enum_.getClass(), enum_);
			}
		} else if (e.getSource() instanceof JRadioButton) {
			Enum<?> enum_ = this.cachedEnums.get(e.getSource());
			if (enum_ != null) {
				logger.debug("Set value " + enum_.name() + " for " + enum_.getClass());
				selectedValues.put(enum_.getClass(), enum_);
			}
		} else if (e.getSource() == jButtonPasswordChange) {
			this.changePassowrdImpl();
		} else if (e.getSource() == jButtonPasswordForget) {
			this.forgetPasswordImpl();
		}
	}

	private Map<JRadioButton, Enum<?>> cachedEnums = new HashMap<JRadioButton, Enum<?>>(); //  @jve:decl-index=0:
	private Map<Enum<?>, JRadioButton> cachedButtons = new HashMap<Enum<?>, JRadioButton>(); //  @jve:decl-index=0:

	@SuppressWarnings("unchecked")
	private Map<Class<? extends Enum>, JComboBox> cachedCombos = new HashMap<Class<? extends Enum>, JComboBox>(); //  @jve:decl-index=0:
	@SuppressWarnings("unchecked")
	private Map<Class<? extends Enum>, Enum<?>> selectedValues = new HashMap<Class<? extends Enum>, Enum<?>>(); //  @jve:decl-index=0:	

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
						"SettingsWindow.err.enumRecipient", value.getClass()));
			}
		}

	}

	@SuppressWarnings("unchecked")
	private Enum<?> getValue(Class<? extends Enum> clazz) {
		return selectedValues.get(clazz);
	}

	//
	//	@Override
	//	public void stateChanged(ChangeEvent e) {
	//		Enum<?> enum_ = this.cachedEnums.get(e.getSource());
	//		if (enum_ != null) {
	//			logger.debug("Set value " + enum_.name() + " for " + enum_.getClass());
	//			selectedValues.put(enum_.getClass(), enum_);
	//		}
	//	}

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
		for (Enum<?> mode : enumValues) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 1;
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.gridy = index;

			JRadioButton button = new JRadioButton();
			button.setText(mode.toString());
			button.setName(mode.name());
			button.setActionCommand(mode.getClass().getName());
			button.addActionListener(this);
			///button.addChangeListener(this);

			placePanel.add(button, gridBagConstraints);

			group.add(button);

			cachedEnums.put(button, mode);
			cachedButtons.put(mode, button);

			index++;
		}
	}

	private void setUpComboBox(Enum<?> enumValues[], String title, JPanel placePanel, int startY) {
		if (enumValues == null || enumValues.length == 0) {
			throw new IllegalArgumentException(
					"Internal error. 'enumValues' must be not null and not empty.");
		}
		if (placePanel == null) {
			throw new IllegalArgumentException("Internal error. 'placePanel' must be not null.");
		}

		if (placePanel.getLayout() == null) {
			placePanel.setLayout(new GridBagLayout());
		}
		placePanel.add(new JLabel(title), AppHelper.getJBCForm(0, startY));

		JComboBox comboBox = new JComboBox(enumValues);
		comboBox.addActionListener(this);
		placePanel.add(comboBox, AppHelper.getJBCForm(1, startY));

		cachedCombos.put(enumValues[0].getClass(), comboBox);

	}

} // @jve:decl-index=0:visual-constraint="10,10"
