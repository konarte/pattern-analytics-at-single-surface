package edu.mgupi.pass.face;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.face.template.ParametersEditorPanel;
import edu.mgupi.pass.filters.IllegalParameterValueException;
import edu.mgupi.pass.filters.Param;

public class ParametersEditor extends JDialog {

	Logger logger = LoggerFactory.getLogger(ParametersEditor.class);

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private ParametersEditorPanel jPanelParams = null;
	private JPanel jPanelButtons = null;
	private JButton jButtonOK = null;
	private JButton jButtonCancel = null;

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
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				ParametersEditor.this.cancelImpl();
			}
		});
		this.setSize(400, 200);
		this.setMinimumSize(new Dimension(400, 200));
		this.setResizable(false);
		this.setName("frameEditModuleParameters");
		this.setContentPane(getJContentPane());
		this.setTitle("Редактирование параметров модуля");
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
			jButtonOK.setAction(new AbstractAction() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					ParametersEditor.this.save();
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

			Action cancelAction = new AbstractAction() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					ParametersEditor.this.cancelImpl();
				}
			};

			jButtonCancel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
					KeyStroke.getKeyStroke((char) KeyEvent.VK_ESCAPE), "cancel");
			jButtonCancel.getActionMap().put("cancel", cancelAction);
			jButtonCancel.setAction(cancelAction);
			jButtonCancel.setName("cancel");
			jButtonCancel.setText("Отмена");
		}
		return jButtonCancel;
	}

	public void setParameters(String name, Collection<Param> parameters) {
		if (parameters == null) {
			throw new IllegalArgumentException("Internal error. 'parameters' must be not null.");
		}
		this.setTitle(name);
		jPanelParams.setParameters(parameters);
		//Dimension buttonsDim = ((FlowLayout) jPanelButtons.getLayout()).preferredLayoutSize(jPanelButtons);
		//jPanelParams.fixParentBounds(this, 0, buttonsDim.height, true);
		this.pack();
		this.setLocationRelativeTo(this.getOwner());
	}

	private boolean ok = false;

	private JButton jButtonRetoreDefaults = null;

	public boolean open(String name, Collection<Param> parameters) {
		this.setParameters(name, parameters);
		this.setVisible(true);
		return ok;
	}

	private void cancelImpl() {
		ok = false;
		jPanelParams.resetParameterValues();
		this.setVisible(false);
	}

	private void save() {
		try {
			ParametersEditor.this.saveImpl();
		} catch (Throwable t) {
			logger.error("Error when saving parameters for module", t);
			AppHelper.showExceptionDialog(ParametersEditor.this, "Error when applying module parameters.", t);
		}
	}

	private void saveImpl() throws IllegalParameterValueException {
		ok = false;

		jPanelParams.saveParameterValues();

		ok = true;
		this.setVisible(false);

	}

	private void restoreDefaults() {

		if (JOptionPane.showConfirmDialog(this,
				"Вы уверены, что хотите восстановить все значения параметров по-умолчанию?", "Восстановление значений",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

			try {
				jPanelParams.restoreDefaults();
			} catch (Throwable t) {
				logger.error("Error when restoring defaults for module parameters", t);
				AppHelper.showExceptionDialog(ParametersEditor.this,
						"Unexpected error when restoring defaults for module parameters.", t);
			}
		}
	}

	/**
	 * This method initializes jButtonRetoreDefaults
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonRetoreDefaults() {
		if (jButtonRetoreDefaults == null) {
			jButtonRetoreDefaults = new JButton();
			jButtonRetoreDefaults.setAction(new AbstractAction() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					ParametersEditor.this.restoreDefaults();
				}
			});
			jButtonRetoreDefaults.setText("Восстановить");
			jButtonRetoreDefaults.setName("restoreDefaults");
		}
		return jButtonRetoreDefaults;
	}

} //  @jve:decl-index=0:visual-constraint="10,10"
