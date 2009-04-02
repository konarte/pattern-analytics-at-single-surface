package edu.mgupi.pass.face.gui.template;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

import org.hibernate.Criteria;
import org.orm.PersistentTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.db.surfaces.PassPersistentManager;
import edu.mgupi.pass.face.gui.AppHelper;
import edu.mgupi.pass.face.gui.IWindowCloseable;
import edu.mgupi.pass.util.IRefreshable;
import edu.mgupi.pass.util.Secundomer;
import edu.mgupi.pass.util.SecundomerList;
import edu.mgupi.pass.util.Utils;

public abstract class RecordEditorTemplate<T> extends JDialog implements IWindowCloseable,
		IRefreshable {

	private final static Logger logger = LoggerFactory.getLogger(RecordEditorTemplate.class); //  @jve:decl-index=0:

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanelData = null;
	private JPanel jPanelButtons = null;
	private JButton jButtonOK = null;
	private JButton jButtonCancel = null;

	/**
	 * Default constructor
	 * 
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
	 * @see IWindowCloseable#close()
	 */
	public void close() {
		if (jPanelData != null && jPanelData instanceof RecordFormWithImageTemplate) {
			((RecordFormWithImageTemplate) jPanelData).getImageControlAdapter().close();
		}
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(400, 250);
		this.setMinimumSize(new Dimension(400, 250));
		this.setContentPane(getJContentPane());
	}

	private Map<JTextComponent, String> requiredMap = new HashMap<JTextComponent, String>();
	private AbstractDialogAdapter dialogAdapter = null; //  @jve:decl-index=0:

	private Secundomer secundomerLoad = null;
	private Secundomer secundomerSave = null;
	private Secundomer secundomerCheckSave = null;
	private Secundomer secundomerRollback = null;
	private Secundomer secundomerDelete = null;
	private Secundomer secundomerCheckDelete = null;

	private AbstractDialogAdapter getDialogAdapter() {
		if (dialogAdapter == null) {
			this.secundomerLoad = SecundomerList.registerSecundomer("" + this.hashCode()
					+ " Loading object time for " + this.getTitle());
			this.secundomerSave = SecundomerList.registerSecundomer("" + this.hashCode()
					+ " Saving object time for " + this.getTitle());
			this.secundomerCheckSave = SecundomerList.registerSecundomer("" + this.hashCode()
					+ " Check allow save for " + this.getTitle());
			this.secundomerRollback = SecundomerList.registerSecundomer("" + this.hashCode()
					+ " Rollback time for " + this.getTitle());
			this.secundomerDelete = SecundomerList.registerSecundomer("" + this.hashCode()
					+ " Deleting object for " + this.getTitle());
			this.secundomerCheckDelete = SecundomerList.registerSecundomer("" + this.hashCode()
					+ " Check allow delete for " + this.getTitle());

			dialogAdapter = new AbstractDialogAdapter(this, true) {

				@Override
				protected void cancelImpl() throws Exception {
//					PassPersistentManager.instance().getSession().lock(workObject, LockMode.NONE);
					logger.trace("Do cancel for {}.", workObject);
				}

				@Override
				protected void openDialogImpl() throws Exception {
					/*
					 * No, we use optimistic locking ;)
					 */
//					PassPersistentManager.instance().getSession().lock(workObject, LockMode.WRITE);
				}

				@Override
				protected boolean saveImpl() throws Exception {

					logger.trace("Do save for {}.", workObject);

					logger.trace("Checking for required fields...");
					for (Map.Entry<JTextComponent, String> comp : requiredMap.entrySet()) {
						String text = comp.getKey().getText();
						if (text == null || text.isEmpty()) {
							AppHelper.showFieldRequiredDialog(RecordEditorTemplate.this, comp
									.getValue());
							return false;
						}
					}

					logger.trace("Checking for allowedImpl...");
					if (!isSaveAllowed(workObject)) {
						return false;
					}

					// We do not need to commit\rollback
					logger.trace("Saving in transaction {}.", PassPersistentManager.instance()
							.getSession().getTransaction());
					saveFormToObjectImpl(workObject);

					//					PersistentTransaction transaction = null;
					//					if (Config.getInstance().getTransactionMode() == TransactionMode.COMMIT_EVERY_ROW) {
					//						transaction = PassPersistentManager.instance().getSession().beginTransaction();
					//					}
					secundomerSave.start();
					try {
						PersistentTransaction transaction = PassPersistentManager.instance()
								.getSession().beginTransaction();
						try {

							saveObject(workObject);

							if (transaction != null) {
								transaction.commit();
							}
							//PassPersistentManager.instance().getSession().flush();
						} catch (Exception e) {
							secundomerSave.stop();
							secundomerRollback.start();
							restoreObjectImpl(workObject);
							if (transaction != null) {
								transaction.rollback();
							}
							throw e;
						}
					} finally {
						secundomerSave.stop();
						secundomerRollback.stop();
						PassPersistentManager.instance().getSession().close();
					}

					return true;

				}
			};
		}
		return dialogAdapter;
	}

	private T workObject = null; //  @jve:decl-index=0:

	public T addRecord(T source) throws Exception {
		return this.openRecordImpl(source, true);

	}

	public boolean editRecord(T source) throws Exception {
		return this.openRecordImpl(source, false) != null;
	}

	private T openRecordImpl(T source, boolean isAdd) throws Exception {
		if (source == null) {
			throw new IllegalArgumentException("Internal error. Source must be not null.");
		}

		if (jPanelData.getBorder() != null && jPanelData.getBorder() instanceof TitledBorder) {
			((TitledBorder) jPanelData.getBorder()).setTitle(isAdd ? Messages
					.getString("RecordEditorTemplate.title.create") : Messages
					.getString("RecordEditorTemplate.title.edit"));
		}

		this.workObject = source;
		logger.trace(isAdd ? "Add record {}." : "Edit record {}.", this.workObject);

		boolean retOK = false;
		if (loadFromObject(workObject)) {
			retOK = getDialogAdapter().openDialog();
		}

		if (retOK) {
			return this.workObject;
		} else {
			return null;
		}
	}

	public boolean deleteRecords(boolean checkForDeleteAllowed, Collection<T> source)
			throws Exception {
		if (source == null) {
			throw new IllegalArgumentException("Internal error. Source must be not null.");
		}

		if (logger.isTraceEnabled()) {
			logger.trace("Received {} rows for delete...", source);
		}
		if (source.size() == 0) {
			return false;
		}
		if (!checkForDeleteAllowed || isDeleteAllowed(source)) {
			logger.trace("Deleting in transaction {}.", PassPersistentManager.instance()
					.getSession().getTransaction());

			//			PersistentTransaction transaction = null;
			//			if (Config.getInstance().getTransactionMode() == TransactionMode.COMMIT_EVERY_ROW) {
			//				transaction = PassPersistentManager.instance().getSession().beginTransaction();
			//			}
			secundomerDelete.start();
			try {
				PersistentTransaction transaction = PassPersistentManager.instance().getSession()
						.beginTransaction();
				try {

					deleteObjects(source);
					if (transaction != null) {
						transaction.commit();
					}
					//PassPersistentManager.instance().getSession().flush();
				} catch (Exception e) {
					secundomerDelete.stop();
					secundomerRollback.start();
					if (transaction != null) {
						transaction.rollback();
					}
					throw e;
				}
			} finally {
				secundomerDelete.stop();
				secundomerRollback.stop();
				PassPersistentManager.instance().getSession().close();
			}
			return true;

		}
		return false;
	}

	private void saveObject(T object) throws Exception {
		PassPersistentManager.instance().getSession().saveOrUpdate(object);
	}

	private void deleteObjects(Collection<T> objects) throws Exception {
		for (T object : objects) {
			PassPersistentManager.instance().getSession().delete(object);
		}
	}

	private Map<IRefreshable, String> refresheableMap = new HashMap<IRefreshable, String>();
	private JTextComponent uniqueComponent = null;
	private String uniqueComponentValue = null;

	private boolean loadFromObject(T object) throws Exception {

		secundomerLoad.start();
		try {
			for (Map.Entry<IRefreshable, String> key : this.refresheableMap.entrySet()) {
				if (key.getKey().refresh() == 0) {
					secundomerLoad.stop();
					AppHelper.showErrorDialog(this, Messages.getString(
							"RecordEditorTemplate.noValuesInSelect", key.getValue()));
					return false;
				}
			}

			boolean ret = this.loadFormFromObjectImpl(object);

			uniqueComponentValue = null;
			if (uniqueComponent != null) {
				uniqueComponentValue = uniqueComponent.getText();
				uniqueComponent.requestFocusInWindow();
			}

			return ret;
		} finally {
			secundomerLoad.stop();
		}
	}

	private boolean isSaveAllowed(T object) throws Exception {
		if (uniqueComponent != null) {
			secundomerCheckSave.start();
			try {
				logger.trace("Checking saving allowed for {}.", uniqueComponent.getText());

				if (Utils.equals(uniqueComponent.getText(), uniqueComponentValue)) {

					logger.trace("There are equals. Return true.");

					return true;
				}

				String newValue = uniqueComponent.getText();

				Criteria criteria = this.getUniqueCheckCriteria(object, newValue);

				if (criteria == null) {
					return true;
				}

				Object foundObject = criteria.uniqueResult();
				if (foundObject != null) {
					secundomerCheckSave.stop();
					logger.trace("Found existing value in database.");
					AppHelper.showErrorDialog(this, Messages.getString(
							"RecordEditorTemplate.duplicateRowInsert", newValue, this.requiredMap
									.get(uniqueComponent)));
					return false;
				}
			} finally {
				secundomerCheckSave.stop();

			}

		} else {
			logger.trace("Skipping isSavedAllowed. No 'uniqueComponent' registered.");
		}

		return true;
	}

	protected abstract Criteria getUniqueCheckCriteria(T object, String newValue) throws Exception;

	public boolean isDeleteAllowed(Collection<T> objects) throws Exception {

		Criteria criteria = this.getMultipleDeleteCriteria(objects);

		if (criteria == null) {
			return true;
		}
		secundomerCheckDelete.start();

		try {

			criteria.setMaxResults(10);

			logger.trace("Checking allow deletions for {}.", objects);
			logger.trace("Received criteria: {}.", criteria);

			List<?> foundObjects = criteria.list();
			if (foundObjects.size() == 0) {
				logger.trace("No rows found.");
				return true;
			}

			logger.trace("Found {}.", foundObjects);
			if (foundObjects.size() >= 0) {
				StringBuilder buffer = new StringBuilder("<html>");
				buffer.append(Messages.getString("RecordEditorTemplate.rowsDeleteDenied", objects
						.size(), foundObjects.size(), Utils.getPluralForm(foundObjects.size())));
				buffer.append("<ul>");
				for (Object obj : foundObjects) {
					buffer.append("<li>").append(getDenyDeletionMessage(obj));
				}
				buffer.append("</ul></html>");

				secundomerCheckDelete.stop();
				logger.error(buffer.toString());
				JOptionPane.showMessageDialog(this, buffer.toString(), Messages
						.getString("RecordEditorTemplate.title.error"), JOptionPane.ERROR_MESSAGE);
			}

			return false;
		} finally {
			secundomerCheckDelete.stop();
		}
	}

	protected abstract Criteria getMultipleDeleteCriteria(Collection<T> objects) throws Exception;

	protected abstract String getDenyDeletionMessage(Object foundObject);

	protected abstract boolean loadFormFromObjectImpl(T object) throws Exception;

	protected abstract void saveFormToObjectImpl(T object) throws Exception;

	protected abstract void restoreObjectImpl(T object) throws Exception;

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
			jPanelData.setBorder(BorderFactory.createTitledBorder(Messages
					.getString("RecordEditorTemplate.edit")));
			jContentPane.add(panel, BorderLayout.CENTER);

			this.refresh();
		}
	}

	/**
	 * @see IRefreshable#refresh()
	 */
	public int refresh() {

		this.pack();
		this.setMinimumSize(new Dimension(this.getWidth(), this.getHeight()));

		return 0;
	}

	protected JPanel createDefaultFormPanel(JPanel panelPlace) {
		JPanel formPanel = new JPanel();
		GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
		gridBagConstraints4.gridx = 0;
		gridBagConstraints4.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints4.weightx = 1.0D;
		gridBagConstraints4.weighty = 1.0D;
		gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints4.gridy = 0;
		formPanel.setSize(300, 200);
		formPanel.setLayout(new GridBagLayout());
		formPanel.add(panelPlace, gridBagConstraints4);

		return formPanel;
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

	private int gridY = 0;

	protected int getNextComponentGridY() {
		return this.gridY++;
	}

	protected void putRequiredComponentPair(JPanel place, String label, JTextComponent component) {
		this.putComponentPair(place, label, component);
		this.requiredMap.put(component, label);
	}

	protected void putUniqueComponentPair(JPanel place, String label, JTextComponent component) {

		if (uniqueComponent != null) {
			throw new IllegalStateException("There is only one unique component available on form!");
		}

		this.putRequiredComponentPair(place, label, component);
		this.uniqueComponent = component;
		this.uniqueComponentValue = component.getText();
	}

	protected JLabel putComponentPair(JPanel place, String label, Component component) {

		JLabel jLabel = new JLabel(label + ":");
		place.add(jLabel, AppHelper.getJBCForm(0, gridY));
		place.add(component, AppHelper.getJBCForm(1, gridY, true));

		if (component instanceof JComboBox) {
			JComboBox combo = (JComboBox) component;
			if (combo.getModel() instanceof IRefreshable) {
				refresheableMap.put((IRefreshable) combo.getModel(), label);
			}
		}

		gridY++;

		return jLabel;
	}
} //  @jve:decl-index=0:visual-constraint="10,10"
