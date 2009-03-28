package edu.mgupi.pass.face.gui.template;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.hibernate.Transaction;
import org.orm.PersistentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.db.surfaces.PassPersistentManager;
import edu.mgupi.pass.face.gui.AppHelper;
import edu.mgupi.pass.util.Config;
import edu.mgupi.pass.util.Config.TransactionMode;

public abstract class TableEditorTemplate extends JDialog {

	private final static Logger logger = LoggerFactory.getLogger(TableEditorTemplate.class); //  @jve:decl-index=0:

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;

	/**
	 * @param owner
	 * @param name 
	 * @param title 
	 */
	public TableEditorTemplate(Frame owner, String name, String title) {
		super(owner, true);
		setName(name);
		setTitle(title);
		initialize();
	}

	private AbstractEditorTableModel tableModel = null;

	private AbstractEditorTableModel getTableModel() {
		if (tableModel == null) {
			tableModel = this.getTableModelImpl(getJTableData());
		}
		return tableModel;
	}

	protected abstract AbstractEditorTableModel getTableModelImpl(JTable owner);

	protected abstract void tablePostInit(JTable owner);

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(587, 348);
		this.setTitle("Редактирование таблицы");
		this.setName("editorTemplateDialog");
		this.setContentPane(getJContentPane());
	}

	private AbstractDialogAdapter dialogAdapter = null; //  @jve:decl-index=0:
	private JPanel jPanelPlace = null;
	private JPanel jPanelButtons = null;
	private JScrollPane jScrollPaneData = null;
	private JTable jTableData = null;
	private JButton jButtonSave = null;
	private JButton jButtonCancel = null;
	private JPanel jPanelTable = null;
	private JPanel jPanelAddRemoveButtons = null;
	private JButton jButtonAdd = null;
	private JButton jButtonRemove = null;
	private JButton jButtonEdit = null;

	private AbstractDialogAdapter getDialogAdapter() {
		if (dialogAdapter != null) {
			return dialogAdapter;
		}

		dialogAdapter = new AbstractDialogAdapter(this, getTableModel()) {

			@Override
			protected void cancelImpl() throws Exception {
				if (Config.getInstance().getTransactionMode() == TransactionMode.COMMIT_BULK) {
					Transaction transaction = PassPersistentManager.instance().getSession().getTransaction();
					if (transaction.isActive()) {
						logger.trace("Rollback MAIN transaction {}.", transaction);
						transaction.rollback();
					}
				}
			}

			@Override
			protected void openDialogImpl() throws Exception {
				if (Config.getInstance().getTransactionMode() == TransactionMode.COMMIT_BULK) {
					PassPersistentManager.instance().getSession().beginTransaction();
					logger.trace("Begin MAIN transaction {}.", PassPersistentManager.instance().getSession()
							.getTransaction());
				}
			}

			@Override
			protected boolean saveImpl() throws Exception {
				if (Config.getInstance().getTransactionMode() == TransactionMode.COMMIT_BULK) {
					Transaction transaction = PassPersistentManager.instance().getSession().getTransaction();
					if (transaction.isActive()) {
						logger.trace("Commit MAIN transaction {}.", transaction);
						transaction.commit();
					}
					return true;
				}
				return false;
			}

		};

		return dialogAdapter;
	}

	public boolean openDialog() {
		TransactionMode mode = null;
		try {
			mode = Config.getInstance().getTransactionMode();
		} catch (PersistentException pe) {
			AppHelper.showExceptionDialog("Ошибка при получении текущего режима транзакций", pe);
		}
		if (mode == TransactionMode.COMMIT_BULK) {
			return getDialogAdapter().openDialog();
		} else {
			getDialogAdapter().showDialogCancelOnly();
			return false;
		}
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
			jContentPane.add(getJPanelPlace(), BorderLayout.CENTER);
			jContentPane.add(getJPanelButtons(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanelPlace
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelPlace() {
		if (jPanelPlace == null) {
			jPanelPlace = new JPanel();
			jPanelPlace.setLayout(new BorderLayout());
			jPanelPlace.add(getJPanelTable(), BorderLayout.CENTER);
			jPanelPlace.add(getJPanelAddRemoveButtons(), BorderLayout.SOUTH);
		}
		return jPanelPlace;
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
			jPanelButtons.add(getJButtonSave(), null);
			jPanelButtons.add(getJButtonCancel(), null);
		}
		return jPanelButtons;
	}

	/**
	 * This method initializes jScrollPaneData
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneData() {
		if (jScrollPaneData == null) {
			jScrollPaneData = new JScrollPane();
			jScrollPaneData.setViewportView(getJTableData());
		}
		return jScrollPaneData;
	}

	/**
	 * This method initializes jTableData
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTableData() {
		if (jTableData == null) {
			jTableData = new JTableReadOnly();
			jTableData.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			jTableData.setModel(getTableModel());
			jTableData.setName("data");
			this.tablePostInit(jTableData);
		}
		return jTableData;
	}

	/**
	 * This method initializes jButtonSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonSave() {
		if (jButtonSave == null) {
			jButtonSave = new JButton();
			jButtonSave.setText("OK");
			getDialogAdapter().registerOKButton(jButtonSave);
		}
		return jButtonSave;
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
	 * This method initializes jPanelTable
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelTable() {
		if (jPanelTable == null) {
			jPanelTable = new JPanel();
			jPanelTable.setLayout(new BorderLayout());
			jPanelTable.add(getJScrollPaneData(), BorderLayout.CENTER);
		}
		return jPanelTable;
	}

	/**
	 * This method initializes jPanelAddRemoveButtons
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelAddRemoveButtons() {
		if (jPanelAddRemoveButtons == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			jPanelAddRemoveButtons = new JPanel();
			jPanelAddRemoveButtons.setLayout(flowLayout);
			jPanelAddRemoveButtons.add(getJButtonAdd(), null);
			jPanelAddRemoveButtons.add(getJButtonRemove(), null);
			jPanelAddRemoveButtons.add(getJButtonEdit(), null);
		}
		return jPanelAddRemoveButtons;
	}

	/**
	 * This method initializes jButtonAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonAdd() {
		if (jButtonAdd == null) {
			jButtonAdd = new JButton();
			jButtonAdd.setText("Добавить");
			getTableModel().registerAddRowButton(jButtonAdd);
		}
		return jButtonAdd;
	}

	/**
	 * This method initializes jButtonRemove
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonRemove() {
		if (jButtonRemove == null) {
			jButtonRemove = new JButton();
			jButtonRemove.setText("Удалить");
			getTableModel().registerDeleteRowButton(jButtonRemove);
		}
		return jButtonRemove;
	}

	/**
	 * This method initializes jButtonEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonEdit() {
		if (jButtonEdit == null) {
			jButtonEdit = new JButton();
			jButtonEdit.setText("Редактировать");
			getTableModel().registerEditRowButton(jButtonEdit);
		}
		return jButtonEdit;
	}

} //  @jve:decl-index=0:visual-constraint="10,10"
