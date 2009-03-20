package edu.mgupi.pass.face;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import edu.mgupi.pass.util.Config;
import edu.mgupi.pass.util.Const;

public class AboutDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JButton jButtonOK = null;
	private JPanel jPanel1 = null;
	private JLabel jLabelProgramTitle = null;

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
		this.setSize(462, 380);
		this.setName("aboutDialog");
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setTitle("О программе...");
		this.setContentPane(getJContentPane());
		Config.getInstance().getWindowPosition(this);
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
			jButtonOK.setName("ok");
			jButtonOK.setMnemonic(KeyEvent.VK_UNDEFINED);
		}
		return jButtonOK;
	}

	private JLabel jLabelAuthor = null;
	private JScrollPane jScrollPane = null;

	private JTable jTableSystemProperties = null;

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
			jLabelAuthor.setText("<html><br><hr>(c) raidan, Konart 2009</html>");
			jLabelAuthor.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelAuthor.setHorizontalTextPosition(SwingConstants.TRAILING);
			jLabelAuthor.setPreferredSize(new Dimension(200, 40));
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(0, 15, 5, 15);
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0D;
			gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints.gridx = 0;
			jLabelProgramTitle = new JLabel();
			jLabelProgramTitle.setText("<html><h3>" + Const.PROGRAM_NAME + "<br>" + Const.LAST_PROGRAM_NAME + "</h3>"
					+ "<b>Science content:</b> Konart<br><b>Code, design:</b> raidan</html>");

			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.setEnabled(true);
			jPanel1.add(jLabelProgramTitle, gridBagConstraints);
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
			jScrollPane.setViewportView(getJTableSystemProperties());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTableSystemProperties
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTableSystemProperties() {
		if (jTableSystemProperties == null) {

			Properties props = System.getProperties();
			String list[] = props.keySet().toArray(new String[0]);
			Arrays.sort(list);

			String cells[][] = new String[props.size()][2];

			for (int i = 0; i < list.length; i++) {
				cells[i][0] = list[i];
				cells[i][1] = props.getProperty(cells[i][0]);
			}

			jTableSystemProperties = new JTable(cells, new String[] { "Property", "Value" }) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			jTableSystemProperties.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jTableSystemProperties.setName("properties");
			jTableSystemProperties.setCellSelectionEnabled(true);

		}
		return jTableSystemProperties;
	}
} //  @jve:decl-index=0:visual-constraint="7,1"
