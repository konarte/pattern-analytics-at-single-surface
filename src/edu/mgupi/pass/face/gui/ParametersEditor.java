package edu.mgupi.pass.face.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.face.gui.template.AbstractDialogAdapter;
import edu.mgupi.pass.face.gui.template.ParametersEditorPanel;
import edu.mgupi.pass.filters.Param;

public class ParametersEditor extends JDialog implements ActionListener {

	Logger logger = LoggerFactory.getLogger(ParametersEditor.class);

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private ParametersEditorPanel jPanelParams = null;
	private JPanel jPanelButtons = null;
	private JButton jButtonOK = null;
	private JButton jButtonCancel = null;
	private JButton jButtonRetoreDefaults = null;

	/**
	 * This is the default constructor
	 * 
	 * @param owner
	 */
	public ParametersEditor(Frame owner) {
		super(owner, true);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(400, 300);
		this.setMinimumSize(new Dimension(400, 200));
		this.setResizable(false);
		this.setName("parametersEditorDialog");
		this.setContentPane(getJContentPane());
		this.setTitle(Messages.getString("ParametersEditor.title"));

	}

	private AbstractDialogAdapter myDialogAdapter = null; //  @jve:decl-index=0:

	private AbstractDialogAdapter getDialogAdapter() {
		if (myDialogAdapter == null) {
			myDialogAdapter = new AbstractDialogAdapter(this) {

				@Override
				protected void cancelImpl() throws Exception {
					jPanelParams.resetParameterValues();
				}

				@Override
				protected void openDialogImpl() throws Exception {
				}

				@Override
				protected boolean saveImpl() throws Exception {
					jPanelParams.saveModelData();
					return true;
				}
			};
		}
		return myDialogAdapter;
	}

	protected void setParametersImpl(String name, Collection<Param> parameters) throws Exception {
		if (parameters == null) {
			throw new IllegalArgumentException("Internal error. 'parameters' must be not null.");
		}
		this.setTitle(name);
		jPanelParams.setModelData(parameters);
		this.pack();
		this.setLocationRelativeTo(this.getOwner());
	}

	public boolean openDialog(String name, Collection<Param> parameters) {
		try {
			this.setParametersImpl(name, parameters);
		} catch (Throwable t) {
			AppHelper.showExceptionDialog(this, Messages
					.getString("ParametersEditor.err.loadParameters"), t);
			return false;
		}

		return getDialogAdapter().openDialog();
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
			jContentPane.add(getJPanelParams(), BorderLayout.NORTH);
			jContentPane.add(getJPanelButtons(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanelParams
	 * 
	 * @return javax.swing.JPanel
	 */
	private ParametersEditorPanel getJPanelParams() {
		if (jPanelParams == null) {
			jPanelParams = new ParametersEditorPanel();
		}
		return jPanelParams;
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
			jPanelButtons.add(getJButtonRetoreDefaults(), null);
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
	 * This method initializes jButtonRetoreDefaults
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonRetoreDefaults() {
		if (jButtonRetoreDefaults == null) {
			jButtonRetoreDefaults = new JButton();
			jButtonRetoreDefaults.setText(Messages.getString("ParametersEditor.restoreDefaults"));
			jButtonRetoreDefaults.setName("restoreDefaults");
			jButtonRetoreDefaults.setActionCommand("restoreDefaults");
			jButtonRetoreDefaults.addActionListener(this);
		}
		return jButtonRetoreDefaults;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command == null) {
			return;
		}

		if (command.equals("restoreDefaults")) {
			if (JOptionPane.showConfirmDialog(this, Messages
					.getString("ParametersEditor.confirm.restoreDefaults"), Messages
					.getString("ParametersEditor.title.restore"), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

				try {
					jPanelParams.restoreDefaults();
				} catch (Throwable t) {
					AppHelper.showExceptionDialog(this, Messages
							.getString("ParametersEditor.err.restore"), t);
				}
			}

		}
	}

} //  @jve:decl-index=0:visual-constraint="10,10"
