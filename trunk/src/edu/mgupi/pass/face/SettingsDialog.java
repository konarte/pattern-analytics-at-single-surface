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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.TitledBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SettingsDialog extends JDialog {

	private final static Logger logger = LoggerFactory.getLogger(SettingsDialog.class);

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
		this.setModal(true);
		this.setTitle("Настройки");
		this.setContentPane(getJContentPane());

		// -------------------

		this.resetSettings();
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

	private void applySettings() throws Exception {
		// LaF apply
		String className = lafs.get(jComboBoxLaF.getSelectedItem());
		if (!UIManager.getLookAndFeel().getClass().getName().equals(className)) {
			AppHelper.getInstance().updateUI(className);
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

		SettingsDialog.this.setVisible(false);
	}

	private void resetSettings() {
		jComboBoxLaF.setSelectedItem(UIManager.getLookAndFeel().getName());
	}

	private void cancelSettings() {
		this.resetSettings();
		SettingsDialog.this.setVisible(false);
	}

	/**
	 * This method initializes jPanelCommon
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelCommon() {
		if (jPanelCommon == null) {
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.ipadx = 0;
			gridBagConstraints4.ipady = 0;
			gridBagConstraints4.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints4.weighty = 1.0D;
			gridBagConstraints4.weightx = 1.0D;
			gridBagConstraints4.gridy = 1;
			jPanelCommon = new JPanel();
			jPanelCommon.setLayout(new GridBagLayout());
			jPanelCommon.add(getJPanelLaF(), gridBagConstraints4);
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
			jPanelLaF.add(getJComboBoxLaF(), gridBagConstraints);
			jPanelLaF.add(jLabelLaF, gridBagConstraints1);
		}
		return jPanelLaF;
	}

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

} // @jve:decl-index=0:visual-constraint="10,10"
