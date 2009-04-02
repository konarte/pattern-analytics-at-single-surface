/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)LoginWindow.java 1.0 02.04.2009
 */

package edu.mgupi.pass.face.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import edu.mgupi.pass.face.gui.template.AbstractDialogAdapter;
import edu.mgupi.pass.util.Config;

public class LoginWindow extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanelConnect = null;
	private JPanel jPanelButtons = null;
	private JButton jButtonOK = null;
	private JButton jButtonCancel = null;

	/**
	 * @param owner
	 */
	public LoginWindow(Frame owner) {
		super(owner, true);
		initialize();
	}

	public boolean openDialog() {
		return getDialogAdapter().openDialog();
	}

	private AbstractDialogAdapter dialogAdapter = null; //  @jve:decl-index=0:
	private JPanel jPanelConnectPlace = null;

	private AbstractDialogAdapter getDialogAdapter() {
		if (dialogAdapter == null) {
			dialogAdapter = new AbstractDialogAdapter(this, true) {

				@Override
				protected void cancelImpl() throws Exception {

				}

				@Override
				protected void openDialogImpl() throws Exception {
				}

				@Override
				protected boolean saveImpl() throws Exception {

					if (jLogin.getText().isEmpty()) {
						AppHelper.showWarnDialog(LoginWindow.this, Messages
								.getString("LoginWindow.loginRequired"));

						return false;
					}

					if (jURL.getText().isEmpty()) {
						AppHelper.showWarnDialog(LoginWindow.this, Messages
								.getString("LoginWindow.urlRequired"));

						return false;
					}

					Config.getInstance().setURL(jURL.getText());
					Config.getInstance().setLogin(jLogin.getText());

					if (jCheckBoxSavePassword.isSelected()) {
						Config.getInstance().setPassword(new String(jPassword.getPassword()));
					} else {
						Config.getInstance().setPassword(null);
					}

					Config.getInstance().saveCommonConfig();

					return true;
				}
			};

		}
		return dialogAdapter;
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setResizable(false);

		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(300, 200);
		this.setTitle(Messages.getString("LoginWindow.title"));
		this.setName("loginDialog");
		this.setContentPane(getJContentPane());
		this.pack();

		Point point = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		this.setLocation((int) point.getX() - this.getWidth() / 2, (int) point.getY()
				- this.getHeight() / 2);
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
			jContentPane.add(getJPanelConnect(), BorderLayout.CENTER);
			jContentPane.add(getJPanelButtons(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanelConnect
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelConnect() {
		if (jPanelConnect == null) {
			jPanelConnect = new JPanel();

			GridBagConstraints jbc = AppHelper.getJBCInBorderPanel();
			jbc.insets = new Insets(10, 10, 10, 0);

			jPanelConnect.setLayout(new GridBagLayout());
			jPanelConnect.add(getJPanelConnectPlace(), jbc);

			jbc = AppHelper.getJBCBorderPanel(1, true);
			jbc.insets = new Insets(0, 10, 10, 0);
			jPanelConnect.add(getJPanelAdditional(), jbc);

		}
		return jPanelConnect;
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

	public char[] getPassword() {
		return jPassword.getPassword();
	}

	private JLabel jLabelLogin;
	private JLabel jLabelPassword;

	private JTextField jLogin;
	private JPasswordField jPassword;

	private JCheckBox jCheckBoxAdditional;

	private JPanel jPanelAdditional;

	/**
	 * This method initializes jPanelConnectPlace
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelConnectPlace() {
		if (jPanelConnectPlace == null) {
			jPanelConnectPlace = new JPanel();
			jPanelConnectPlace.setLayout(new GridBagLayout());

			jLabelLogin = new JLabel();
			jLabelLogin.setText(Messages.getString("LoginWindow.login"));

			jLabelPassword = new JLabel();
			jLabelPassword.setText(Messages.getString("LoginWindow.password"));

			jLogin = new JTextField(30);
			jPassword = new JPasswordField(30);

			jLogin.setText(Config.getInstance().getLogin());

			jPanelConnectPlace.add(jLabelLogin, AppHelper.getJBCForm(0, 0));
			jPanelConnectPlace.add(jLogin, AppHelper.getJBCForm(1, 0));

			jPanelConnectPlace.add(jLabelPassword, AppHelper.getJBCForm(0, 1));
			jPanelConnectPlace.add(jPassword, AppHelper.getJBCForm(1, 1));

			jCheckBoxAdditional = new JCheckBox();
			jCheckBoxAdditional.setText(Messages.getString("LoginWindow.additional"));
			jCheckBoxAdditional.addActionListener(this);

			//			GridBagConstraints jbc = AppHelper.getJBCForm(1, 2, false);
			//			jbc.gridwidth = 2;
			//			jbc.gridx = 0;

			jPanelConnectPlace.add(jCheckBoxAdditional, AppHelper.getJBCForm(0, 2, 2, false));

		}
		return jPanelConnectPlace;
	}

	private JLabel jLabelURL;
	private JTextField jURL;
	private JCheckBox jCheckBoxSavePassword;

	private JPanel getJPanelAdditional() {
		if (jPanelAdditional == null) {
			jPanelAdditional = new JPanel();
			jPanelAdditional.setLayout(new GridBagLayout());
			jPanelAdditional.setVisible(false);

			jLabelURL = new JLabel();
			jLabelURL.setText(Messages.getString("LoginWindow.additional.url"));

			jURL = new JTextField(30);
			jURL.setText(Config.getInstance().getURL());

			jPanelAdditional.add(jLabelURL, AppHelper.getJBCForm(0, 0));
			jPanelAdditional.add(jURL, AppHelper.getJBCForm(1, 0, 1, true));

			jCheckBoxSavePassword = new JCheckBox();
			jCheckBoxSavePassword
					.setText(Messages.getString("LoginWindow.additional.savePassword"));

			jPanelAdditional.add(jCheckBoxSavePassword, AppHelper.getJBCForm(0, 1, 2, false));

		}
		return jPanelAdditional;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jCheckBoxAdditional) {
			jPanelAdditional.setVisible(jCheckBoxAdditional.isSelected());
			this.pack();
		}

	}

} //  @jve:decl-index=0:visual-constraint="10,10"
