package edu.mgupi.pass.face.gui.template;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.Insets;
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
import edu.mgupi.pass.util.IRefreshable;
import edu.mgupi.pass.util.Utils;

public abstract class RecordEditorTemplate<T> extends JDialog {

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

	private Map<JTextComponent, String> requiredMap = new HashMap<JTextComponent, String>();
	private AbstractDialogAdapter dialogAdapter = null; //  @jve:decl-index=0:

	private AbstractDialogAdapter getDialogAdapter() {
		if (dialogAdapter == null) {
			dialogAdapter = new AbstractDialogAdapter(this, true) {

				@Override
				protected void cancelImpl() throws Exception {
					logger.trace("Do cancel for {}.", workObject);
				}

				@Override
				protected void openDialogImpl() throws Exception {
					// Do nothing
				}

				@Override
				protected boolean saveImpl() throws Exception {

					logger.trace("Do save for {}.", workObject);

					logger.trace("Checking for required fields...");
					for (Map.Entry<JTextComponent, String> comp : requiredMap.entrySet()) {
						String text = comp.getKey().getText();
						if (text == null || text.isEmpty()) {
							AppHelper.showFieldRequiredDialog(RecordEditorTemplate.this, comp.getValue());
							return false;
						}
					}

					logger.trace("Checking for allowedImpl...");
					if (!isSaveAllowed(workObject)) {
						return false;
					}

					// We do not need to commit\rollback
					logger.trace("Saving in transaction {}.", PassPersistentManager.instance().getSession()
							.getTransaction());
					saveFormToObjectImpl(workObject);

					//					PersistentTransaction transaction = null;
					//					if (Config.getInstance().getTransactionMode() == TransactionMode.COMMIT_EVERY_ROW) {
					//						transaction = PassPersistentManager.instance().getSession().beginTransaction();
					//					}
					PersistentTransaction transaction = PassPersistentManager.instance().getSession()
							.beginTransaction();
					try {
						saveObject(workObject);
						if (transaction != null) {
							transaction.commit();
						}
						PassPersistentManager.instance().getSession().flush();
					} catch (Exception e) {
						restoreObjectImpl(workObject);
						if (transaction != null) {
							transaction.rollback();
						}
						throw e;
					} finally {
						PassPersistentManager.instance().getSession().close();
					}

					return true;

				}
			};
		}
		return dialogAdapter;
	}

	private T workObject = null; //  @jve:decl-index=0:
	private boolean isAdd = false;

	public T addRecord(T source) throws Exception {
		if (source == null) {
			throw new IllegalArgumentException("Internal error. Source must be not null.");
		}

		if (jPanelData.getBorder() != null && jPanelData.getBorder() instanceof TitledBorder) {
			((TitledBorder) jPanelData.getBorder()).setTitle("Создание новой записи");
		}

		isAdd = true;
		this.workObject = source;
		logger.trace("Add record {}.", this.workObject);

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

	public boolean editRecord(T source) throws Exception {
		if (source == null) {
			throw new IllegalArgumentException("Internal error. Source must be not null.");
		}

		if (jPanelData.getBorder() != null && jPanelData.getBorder() instanceof TitledBorder) {
			((TitledBorder) jPanelData.getBorder()).setTitle("Редактирование записи");
		}

		isAdd = false;
		this.workObject = source;
		logger.trace("Edit record " + this.workObject);

		if (loadFromObject(workObject)) {
			return getDialogAdapter().openDialog();
		} else {
			return false;
		}
	}

	public boolean deleteRecords(boolean checkForDeleteAllowed, Collection<T> source) throws Exception {
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
			logger.trace("Deleting in transaction {}.", PassPersistentManager.instance().getSession().getTransaction());

			//			PersistentTransaction transaction = null;
			//			if (Config.getInstance().getTransactionMode() == TransactionMode.COMMIT_EVERY_ROW) {
			//				transaction = PassPersistentManager.instance().getSession().beginTransaction();
			//			}
			PersistentTransaction transaction = PassPersistentManager.instance().getSession().beginTransaction();

			try {

				deleteObjects(source);
				if (transaction != null) {
					transaction.commit();
				}
				PassPersistentManager.instance().getSession().flush();
			} catch (Exception e) {
				if (transaction != null) {
					transaction.rollback();
				}
				throw e;
			} finally {
				PassPersistentManager.instance().getSession().close();
			}
			return true;

		}
		return false;
	}

	private void saveObject(T object) throws Exception {
		//PassPersistentManager.instance().getSession().saveOrUpdate(object);
		if (isAdd) {
			PassPersistentManager.instance().getSession().save(object);
		} else {
			PassPersistentManager.instance().getSession().update(object);
		}
	}

	private void deleteObjects(Collection<T> objects) throws Exception {
		//		try {
		for (T object : objects) {
			PassPersistentManager.instance().getSession().delete(object);
		}
		//		} catch (Exception e) {
		//			for (T object : objects) {
		//				PassPersistentManager.instance().getSession().refresh(object);
		//			}
		//			throw e;
		//		}
	}

	private Map<IRefreshable, String> refresheableMap = new HashMap<IRefreshable, String>();
	private JTextComponent uniqueComponent = null;
	private String uniqueComponentValue = null;

	//private Map<JTextComponent, String> cachedValues = new HashMap<JTextComponent, String>();

	private boolean loadFromObject(T object) throws Exception {

		for (Map.Entry<IRefreshable, String> key : this.refresheableMap.entrySet()) {
			if (key.getKey().refresh() == 0) {
				AppHelper.showErrorDialog(this, "Зписок значений в поле '" + key.getValue()
						+ "' пуст. Необходимо заполнить данные в родительской таблице.");
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
	}

	private boolean isSaveAllowed(T object) throws Exception {
		if (uniqueComponent != null) {
			logger.trace("Checking saving allowed for {}.", uniqueComponent.getText());

			if (Utils.equals(uniqueComponent.getText(), uniqueComponentValue)) {

				logger.trace("There are equals. Return true.");

				return true;
			}

			String newValue = uniqueComponent.getText();

			Criteria criteria = this.getSaveAllowCriteria(object, newValue);

			if (criteria == null) {
				return true;
			}

			Object foundObject = criteria.uniqueResult();
			if (foundObject != null) {
				logger.trace("Found existing value in database.");
				AppHelper.showErrorDialog(this, "Значение '" + newValue + "' для поля '"
						+ this.requiredMap.get(uniqueComponent) + "' уже существует в базе данных.");
				return false;
			}

		} else {
			logger.trace("Skipping isSavedAllowed. No 'uniqueComponent' registered.");
		}

		return true;
	}

	protected abstract Criteria getSaveAllowCriteria(T object, String newValue) throws Exception;

	public boolean isDeleteAllowed(Collection<T> objects) throws Exception {
		Criteria criteria = this.getMultipleDeleteCriteria(objects);

		if (criteria == null) {
			return true;
		}

		criteria.setMaxResults(10);

		logger.trace("Checking allow deletions for {}.", objects);
		logger.trace("Received criteria: {}.", criteria);

		List<?> foundObjects = criteria.list();
		if (foundObjects.size() == 0) {
			logger.trace("No rows found.");
			return true;
		}

		logger.trace("Found {}.", foundObjects);
		if (foundObjects.size() == 1) {
			AppHelper.showErrorDialog(this, "Удаление строки запрещено: "
					+ this.getDenyDeletionMessage(foundObjects.get(0)));
		} else {
			StringBuilder buffer = new StringBuilder("<html>При удалении строк");
			buffer.append(objects.size() == 1 ? "и" : "");
			buffer.append(" возникли следующие ошибки");
			if (foundObjects.size() > 10) {
				buffer.append(" (первые десять)");
			}
			buffer.append(":<ul>");
			for (Object obj : foundObjects) {
				buffer.append("<li>").append(getDenyDeletionMessage(obj));
			}
			buffer.append("</ul></html>");

			logger.error(buffer.toString());
			JOptionPane.showMessageDialog(this, buffer.toString(), "Ошибка", JOptionPane.ERROR_MESSAGE);
		}

		return false;
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

		JLabel jLabel = new JLabel(label + ":");
		place.add(jLabel, gridBagConstraintsLab);
		place.add(component, gridBagConstraintsComp);

		if (component instanceof JComboBox) {
			JComboBox combo = (JComboBox) component;
			if (combo.getModel() instanceof IRefreshable) {
				refresheableMap.put((IRefreshable) combo.getModel(), label);
			}
		}

		gridy++;

		return jLabel;
	}
} //  @jve:decl-index=0:visual-constraint="10,10"
