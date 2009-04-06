package edu.mgupi.pass.face.gui.template;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import org.hibernate.Criteria;
import org.orm.PersistentTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.db.surfaces.PassPersistentManager;
import edu.mgupi.pass.face.gui.AppHelper;
import edu.mgupi.pass.face.gui.IWindowCloseable;
import edu.mgupi.pass.util.Const;
import edu.mgupi.pass.util.IRefreshable;
import edu.mgupi.pass.util.Secundomer;
import edu.mgupi.pass.util.SecundomerList;
import edu.mgupi.pass.util.Utils;

public abstract class RecordEditorTemplate<T> extends JDialogControlled implements
		IWindowCloseable, IRefreshable, DocumentListener {

	private final static Logger logger = LoggerFactory.getLogger(RecordEditorTemplate.class); //  @jve:decl-index=0:

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
		this(owner, name, title, false);
	}

	private boolean readOnly = false;

	/**
	 * Default constructor
	 * 
	 * @param owner
	 * @param name
	 * @param title
	 * @param readOnly
	 */
	public RecordEditorTemplate(Frame owner, String name, String title, boolean readOnly) {
		super(owner, true);
		this.readOnly = readOnly;
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

	private Map<JComboBox, String> requiredComboMap = new LinkedHashMap<JComboBox, String>();
	private Map<JTextComponent, String> requiredMap = new LinkedHashMap<JTextComponent, String>();
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

					if (readOnly) {
						return false;
					}

					logger.trace("Do save for {}.", workObject);

					logger.trace("Checking for allowedImpl...");
					if (!isSaveAllowed(workObject)) {
						return false;
					}

					// We do not need to commit\rollback
					logger.trace("Saving in transaction {}.", PassPersistentManager.instance()
							.getSession().getTransaction());
					putFormToObjectImpl(workObject);

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

		boolean retOK = false;
		if (this.loadRecord(source, isAdd)) {
			retOK = getDialogAdapter().openDialog();
		}

		if (retOK) {
			return this.workObject;
		}

		return null;
	}

	public boolean loadRecord(T source, boolean isAdd) throws Exception {
		if (source == null) {
			throw new IllegalArgumentException("Internal error. Source must be not null.");
		}
		if (jPanelData == null) {
			throw new IllegalStateException(
					"Internal error. jPanelData is null but must be created calling 'setFormPanelData'.");
		}

		if (jPanelData.getBorder() != null && jPanelData.getBorder() instanceof TitledBorder) {
			TitledBorder border = ((TitledBorder) jPanelData.getBorder());
			if (this.readOnly) {
				border.setTitle(Messages.getString("RecordEditorTemplate.view"));
			} else {
				border.setTitle(isAdd ? Messages.getString("RecordEditorTemplate.create")
						: Messages.getString("RecordEditorTemplate.edit"));
			}

		}

		this.workObject = source;
		logger.trace(isAdd ? "Add record {}." : "Edit record {}.", this.workObject);
		return this.loadFromObject(workObject);
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
				//				key.getKey().refresh();
				if (key.getKey().refresh() == 0) {
					secundomerLoad.stop();
					AppHelper.showErrorDialog(this, Messages.getString(
							"RecordEditorTemplate.err.noValuesInSelect", key.getValue()));
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

		logger.trace("Checking for required fields...");
		for (Map.Entry<JTextComponent, String> comp : requiredMap.entrySet()) {
			String text = comp.getKey().getText();
			if (text == null || text.isEmpty()) {
				AppHelper.showFieldRequiredDialog(this, comp.getValue());
				return false;
			}
			int valueLen = text.length();
			if (valueLen > Const.MAXIMUM_STRING_COLUMN_WIDTH) {
				AppHelper.showFormattedErrorDialog(this, Messages.getString(
						"RecordEditorTemplate.err.rowTooBig", comp.getValue(), valueLen, Utils
								.getPluralForm(valueLen), Const.MAXIMUM_STRING_COLUMN_WIDTH));
				return false;
			}
		}

		for (Map.Entry<JComboBox, String> comp : requiredComboMap.entrySet()) {
			Object value = comp.getKey().getSelectedItem();
			if (value == null) {
				AppHelper.showFieldRequiredDialog(RecordEditorTemplate.this, comp.getValue());
				return false;
			}
		}

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
							"RecordEditorTemplate.err.duplicateRowInsert", newValue,
							this.requiredMap.get(uniqueComponent)));
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
				buffer.append(Messages.getString("RecordEditorTemplate.err.rowsDeleteDenied",
						objects.size(), foundObjects.size(), Utils.getPluralForm(foundObjects
								.size())));
				buffer.append("<ul>");
				for (Object obj : foundObjects) {
					buffer.append("<li>").append(getDenyDeletionMessage(obj));
				}
				buffer.append("</ul></html>");

				secundomerCheckDelete.stop();
				logger.error(buffer.toString());
				AppHelper.showFormattedErrorDialog(this, buffer.toString());
			}

			return false;
		} finally {
			secundomerCheckDelete.stop();
		}
	}

	protected abstract Criteria getMultipleDeleteCriteria(Collection<T> objects) throws Exception;

	protected abstract String getDenyDeletionMessage(Object foundObject);

	protected abstract boolean loadFormFromObjectImpl(T object) throws Exception;

	protected abstract void putFormToObjectImpl(T object) throws Exception;

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

	protected void setFormPanel(JPanel panel) {
		if (jPanelData == null) {
			jPanelData = panel;
			jPanelData.setBorder(BorderFactory.createTitledBorder(this.readOnly ? Messages
					.getString("RecordEditorTemplate.view") : Messages
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

	private JLabel puComponentPair(JPanel place, String label, JComponent component,
			boolean alignComponentWidth, int componentX) {
		JLabel jLabel = new JLabel(label + ":");
		place.add(jLabel, AppHelper.getJBCForm(0, gridY));
		place.add(component, AppHelper.getJBCForm(componentX, gridY, alignComponentWidth));

		gridY++;
		return jLabel;
	}

	protected JLabel putComponentPair(JPanel place, String label, JLabel component) {
		return puComponentPair(place, label, component, true, 1);
	}

	protected JLabel putComponentPair(JPanel place, String label, JComboBox component) {
		if (component instanceof IRefreshable) {
			refresheableMap.put((IRefreshable) component, label);
			this.requiredComboMap.put(component, label);
		}

		if (readOnly) {
			component.addActionListener(this);
			component.setVisible(false);

			JLabel roLabel = new JLabel();
			if (component.getRenderer() instanceof BasicComboBoxRenderer) {
				roLabel.setText(((BasicComboBoxRenderer) component.getRenderer()).getText());
			} else {
				roLabel.setText(String.valueOf(component.getSelectedItem()));
			}
			place.add(roLabel, AppHelper.getJBCForm(1, gridY, true));

			comboSet.put(component, roLabel);
		}

		return puComponentPair(place, label, component, false, readOnly ? 2 : 1);
	}

	protected JLabel putComponentPair(JPanel place, String label, JTextComponent component) {

		if (readOnly) {
			component.getDocument().addDocumentListener(this);
			component.setVisible(false);

			JLabel roLabel = new JLabel(component.getText());
			place.add(roLabel, AppHelper.getJBCForm(1, gridY, true));

			textSet.put(component.getDocument(), roLabel);
		}

		return puComponentPair(place, label, component, true, readOnly ? 2 : 1);
	}

	private Map<JComboBox, JLabel> comboSet = new HashMap<JComboBox, JLabel>();
	private Map<Document, JLabel> textSet = new HashMap<Document, JLabel>();

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		JLabel label = comboSet.get(e.getSource());
		if (label != null) {
			JComboBox component = (JComboBox) e.getSource();
			if (component.getRenderer() instanceof BasicComboBoxRenderer) {
				label.setText(((BasicComboBoxRenderer) component.getRenderer()).getText());
			} else {
				label.setText(String.valueOf(component.getSelectedItem()));
			}
		}
	}

	public void insertUpdate(DocumentEvent e) {
		this.updateTexImpl(e.getDocument());
	}

	public void removeUpdate(DocumentEvent e) {
		this.updateTexImpl(e.getDocument());
	}

	public void changedUpdate(DocumentEvent e) {
		this.updateTexImpl(e.getDocument());
	}

	private void updateTexImpl(Document source) {
		JLabel label = textSet.get(source);
		if (label != null) {
			try {
				label.setText(source.getText(0, source.getLength()));
			} catch (BadLocationException e) {
				logger.error("Error when updating text in readonly mode", e);
			}
		}
	}
} //  @jve:decl-index=0:visual-constraint="10,10"
