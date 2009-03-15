package edu.mgupi.pass.face;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class AboutDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JButton jButtonOK = null;
	private JPanel jPanel1 = null;
	private JLabel jLabelProgramTitle = null;
	private JLabel jLabelVersion = null;

	/**
	 * @param owner
	 */
	public AboutDialog(Frame owner) {
		super(owner, true);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("О программе...");
		this.setContentPane(getJContentPane());
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
			jContentPane.add(getJPanel(), BorderLayout.SOUTH);
			jContentPane.add(getJPanel1(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.add(getJButtonOK(), new GridBagConstraints());
		}
		return jPanel;
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
					AboutDialog.this.dispose();
				}
			});
			jButtonOK.setHorizontalAlignment(SwingConstants.CENTER);
			jButtonOK.setText("OK");
			jButtonOK.setMnemonic(KeyEvent.VK_UNDEFINED);
		}
		return jButtonOK;
	}

	private String VERSION = ResourceBundle.getBundle("app").getString("version"); // @jve:decl-index=0:
	private String BUILD = ResourceBundle.getBundle("mybuild").getString("build.number"); // @jve:decl-index=0:
	private JLabel jLabelAuthor = null;

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.insets = new Insets(15, 0, 0, 0);
			gridBagConstraints11.gridy = 2;
			jLabelAuthor = new JLabel();
			jLabelAuthor.setText("Author: raidan");
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.weightx = 0.0D;
			gridBagConstraints1.gridx = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 0.0D;
			gridBagConstraints.gridx = 0;
			jLabelVersion = new JLabel();
			jLabelVersion.setText("Version " + VERSION + ", build " + BUILD);
			jLabelProgramTitle = new JLabel();
			jLabelProgramTitle.setText("Pattern Analytics at Single Surface");
			jLabelProgramTitle.setFont(new Font("Dialog", Font.BOLD, 12));
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.setEnabled(true);
			jPanel1.add(jLabelProgramTitle, gridBagConstraints);
			jPanel1.add(jLabelVersion, gridBagConstraints1);
			jPanel1.add(jLabelAuthor, gridBagConstraints11);
		}
		return jPanel1;
	}

}
