package edu.mgupi.pass.face.gui.template;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.db.surfaces.PassPersistentManager;
import edu.mgupi.pass.face.gui.AppHelper;

public abstract class RecordEditorTemplate extends JDialog {

	private final static Logger logger = LoggerFactory.getLogger(RecordEditorTemplate.class); //  @jve:decl-index=0:

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanelData = null;
	private JPanel jPanelButtons = null;
	private JButton jButtonOK = null;
	private JButton jButtonCancel = null;

	/**
	 * @param owner
	 * @param name
	 * @param title
	 */
	public RecordEditorTemplate(Frame owner, String name, String title) {
		super(owner, true);
		this.setName(name);
		this.setTitle(title);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(400, 250);
		this.setContentPane(getJContentPane());
	}

	public void close() {
		if (jPanelData != null && jPanelData instanceof RecordFormWithImageTemplate) {
			((RecordFormWithImageTemplate) jPanelData).getImageControlAdapter().close();
		}
	}

	private AbstractDialogAdapter dialogAdapter = null; //  @jve:decl-index=0:

	private AbstractDialogAdapter getDialogAdapter() {
		if (dialogAdapter == null) {
			dialogAdapter = new AbstractDialogAdapter(this, true) {

				@Override
				protected void cancelImpl() throws Exception {
					logger.debug("Do cancel for " + workObject);
				}

				@Override
				protected void openDialogImpl() throws Exception {
					// Do nothing
				}

				private Map<JTextComponent, JLabel> requiredMap = null;

				@Override
				protected boolean saveImpl() throws Exception {

					logger.debug("Do save for " + workObject);

					logger.debug("Checking for required fields...");

					if (requiredMap == null) {
						requiredMap = new HashMap<JTextComponent, JLabel>();
						setRequiredFields(requiredMap);
					}
					for (Map.Entry<JTextComponent, JLabel> comp : requiredMap.entrySet()) {
						String text = comp.getKey().getText();
						if (text == null || text.isEmpty()) {
							AppHelper.showFieldRequiredDialog(comp.getValue().getText());
							return false;
						}
					}

					logger.debug("Checking for allowedImpl...");
					if (!isSaveAllowed(workObject)) {
						return false;
					}

					// We do not need to commit\rollback
					logger.debug("Saving in transaction "
							+ PassPersistentManager.instance().getSession().getTransaction());
					saveFormToObjectImpl(workObject);
					try {
						saveObject(workObject);
					} catch (Exception e) {
						restoreObjectImpl(workObject);
						throw e;
					}

					return true;

				}
			};
		}
		return dialogAdapter;
	}

	private Object workObject = null; //  @jve:decl-index=0:

	public Object addRecord(Object source) throws Exception {
		if (source == null) {
			throw new IllegalArgumentException("Internal error. Source must be not null.");
		}

		if (jPanelData.getBorder() != null && jPanelData.getBorder() instanceof TitledBorder) {
			((TitledBorder) jPanelData.getBorder()).setTitle("Создание новой записи");
		}

		this.workObject = source;
		logger.debug("Add record " + this.workObject);

		boolean retOK = false;
		if (loadFormFromObject(workObject)) {
			retOK = getDialogAdapter().openDialog();
		}

		if (retOK) {
			return this.workObject;
		} else {
			return null;
		}
	}

	public boolean editRecord(Object source) throws Exception {
		if (source == null) {
			throw new IllegalArgumentException("Internal error. Source must be not null.");
		}

		if (jPanelData.getBorder() != null && jPanelData.getBorder() instanceof TitledBorder) {
			((TitledBorder) jPanelData.getBorder()).setTitle("Редактирование записи");
		}

		this.workObject = source;
		logger.debug("Edit record " + this.workObject);

		logger.debug("Do load for " + workObject);
		if (loadFormFromObject(workObject)) {
			return getDialogAdapter().openDialog();
		} else {
			return false;
		}
	}

	public boolean deleteRecords(boolean checkForDeleteAllowed, Object source[]) throws Exception {
		if (source == null) {
			throw new IllegalArgumentException("Internal error. Source must be not null.");
		}

		logger.debug("Received " + source.length + " rows for delete...");
		if (source.length == 0) {
			return false;
		}
		if (!checkForDeleteAllowed || isDeleteAllowed(source)) {
			logger.debug("Deleting in transaction " + PassPersistentManager.instance().getSession().getTransaction());
			deleteObjects(source);
			return true;

		}
		return false;
	}

	protected void saveObject(Object object) throws Exception {
		PassPersistentManager.instance().saveObject(object);
	}

	protected void deleteObjects(Object objects[]) throws Exception {
		for (Object object : objects) {
			PassPersistentManager.instance().deleteObject(object);
		}
	}

	protected abstract boolean isSaveAllowed(Object object) throws Exception;

	public abstract boolean isDeleteAllowed(Object objects[]) throws Exception;

	protected abstract boolean loadFormFromObject(Object object) throws Exception;

	protected abstract void saveFormToObjectImpl(Object object) throws Exception;

	protected abstract void restoreObjectImpl(Object object) throws Exception;

	protected abstract void setRequiredFields(Map<JTextComponent, JLabel> map);

	//	protected abstract JPanel getFormPanelImpl();

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
		}
		return jContentPane;
	}

	protected void setFormPanelData(JPanel panel) {
		if (jPanelData == null) {
			jPanelData = panel;
			jPanelData.setBorder(BorderFactory.createTitledBorder(null, "Редактирование",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
					new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jContentPane.add(panel, BorderLayout.CENTER);
		}
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

	private int gridy = 0;

	protected void putComponentPair(JPanel place, String label, Component component) {

		GridBagConstraints gridBagConstraintsComp = new GridBagConstraints();
		gridBagConstraintsComp.gridx = 1;
		gridBagConstraintsComp.anchor = GridBagConstraints.WEST;
		gridBagConstraintsComp.insets = new Insets(0, 0, 0, 5);
		gridBagConstraintsComp.gridy = gridy;
		gridBagConstraintsComp.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsComp.weightx = 1.0D;

		GridBagConstraints gridBagConstraintsLab = new GridBagConstraints();
		gridBagConstraintsLab.gridx = 0;
		gridBagConstraintsLab.anchor = GridBagConstraints.EAST;
		gridBagConstraintsLab.fill = GridBagConstraints.NONE;
		gridBagConstraintsLab.weightx = 0.0D;
		gridBagConstraintsLab.insets = new Insets(5, 5, 5, 5);
		gridBagConstraintsLab.gridy = gridy;

		place.add(new JLabel(label), gridBagConstraintsLab);
		place.add(component, gridBagConstraintsComp);

		gridy++;
	}

} //  @jve:decl-index=0:visual-constraint="10,10"
