package edu.mgupi.pass.face;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import edu.mgupi.pass.util.Const;

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
		this.setSize(353, 299);
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
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
			jPanel.setLayout(new FlowLayout());
			jPanel.add(getJButtonOK(), null);
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
					AboutDialog.this.setVisible(false);
				}
			});
			jButtonOK.setHorizontalAlignment(SwingConstants.CENTER);
			jButtonOK.setText("OK");
			jButtonOK.setMnemonic(KeyEvent.VK_UNDEFINED);
		}
		return jButtonOK;
	}

	private JLabel jLabelAuthor = null;
	private JScrollPane jScrollPane = null;
	private JTextArea jTextArea = null;

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.fill = GridBagConstraints.BOTH;
			gridBagConstraints4.gridy = 2;
			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.weighty = 1.0;
			gridBagConstraints4.insets = new Insets(0, 15, 0, 15);
			gridBagConstraints4.gridx = 0;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.insets = new Insets(10, 0, 10, 0);
			gridBagConstraints11.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.weightx = 1.0D;
			gridBagConstraints11.anchor = GridBagConstraints.CENTER;
			gridBagConstraints11.gridwidth = 1;
			gridBagConstraints11.gridy = 3;
			jLabelAuthor = new JLabel();
			jLabelAuthor.setText("<html><hr><center>Design, code: raidan<br>Science contest: Konart</center></html>");
			jLabelAuthor.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelAuthor.setHorizontalTextPosition(SwingConstants.TRAILING);
			jLabelAuthor.setPreferredSize(new Dimension(200, 40));
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
			jLabelVersion.setText("Version " + Const.VERSION + ", build " + Const.BUILD);
			jLabelProgramTitle = new JLabel();
			jLabelProgramTitle.setText(Const.PROGRAM_NAME);
			jLabelProgramTitle.setFont(new Font("Dialog", Font.BOLD, 12));
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.setEnabled(true);
			jPanel1.add(jLabelProgramTitle, gridBagConstraints);
			jPanel1.add(jLabelVersion, gridBagConstraints1);
			jPanel1.add(jLabelAuthor, gridBagConstraints11);
			jPanel1.add(getJScrollPane(), gridBagConstraints4);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTextArea());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setEditable(false);
			jTextArea.setFont(new Font("Dialog", Font.PLAIN, 12));
			jTextArea.setText("Database connection: MySQL JDBC Type 4\n" + "User: normal\n" + "Free memory: 51 MB\n"
					+ "---------------------\n" + "Modules: 1\n" + "Filters: 18");
		}
		return jTextArea;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
