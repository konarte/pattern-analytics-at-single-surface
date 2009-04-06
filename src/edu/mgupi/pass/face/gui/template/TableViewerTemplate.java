package edu.mgupi.pass.face.gui.template;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import edu.mgupi.pass.util.Utils;

public abstract class TableViewerTemplate<T> extends JDialog {

	//private final static Logger logger = LoggerFactory.getLogger(TableEditorTemplate.class); //  @jve:decl-index=0:

	private JPanel jContentPane = null;

	/**
	 * @param owner
	 * @param name
	 * @param title
	 */
	public TableViewerTemplate(Frame owner, String name, String title) {
		super(owner, true);
		setName(name);
		setTitle(title);
		initialize();
	}

	private CommonEditorTableModel<T> tableModel = null;

	protected CommonEditorTableModel<T> getTableModel() {
		if (tableModel == null) {
			tableModel = this.getTableModelImpl(getJTableData());
		}
		return tableModel;
	}

	protected abstract CommonEditorTableModel<T> getTableModelImpl(JTable owner);

	protected abstract void tablePostInit(JTable owner);

	protected void setFiltersPanel(JPanel panelData) {

		if (panelData != null && this.jPanelFilters == null) {
			getJPanelFilters().add(panelData, BorderLayout.WEST);
			jContentPane.add(getJPanelFilters(), BorderLayout.NORTH);
		}
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(400, 350);
		this.setMinimumSize(new Dimension(400, 250));
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
	private JPanel jPanelFilters = null;
	private JButton jButtonSearchFiltered = null;
	private JPanel jPanelFiltersButtons = null;
	private JButton jButtonResetFiltered = null;

	protected T selectedObject = null;
	private JPanel jPanelRowCount = null;
	private JLabel jLabelRowCount = null;

	protected AbstractDialogAdapter getDialogAdapter() {
		if (dialogAdapter == null) {

			dialogAdapter = new AbstractDialogAdapter(this, getTableModel()) {

				@Override
				protected void cancelImpl() throws Exception {
					selectedObject = null;
				}

				@Override
				protected void openDialogImpl() throws Exception {
				}

				@Override
				protected boolean saveImpl() throws Exception {

					selectedObject = null;

					int row = jTableData.getSelectedRow();
					if (row != -1) {
						selectedObject = tableModel.getRowAt(row);
					}

					return false;
				}

			};
		}

		return dialogAdapter;
	}

	public boolean showWindow() {
		getDialogAdapter().showDialogCancelOnly();
		return false;
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
			jTableData.setModel(getTableModel());
			jTableData.setName("data");
			jTableData.setRowSorter(new TableRowSorter<TableModel>(getTableModel()));
			jTableData.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						getDialogAdapter().save();
					}
				}
			});
			jTableData.getModel().addTableModelListener(new TableModelListener() {
				@Override
				public void tableChanged(TableModelEvent e) {
					if (jLabelRowCount != null) {
						jLabelRowCount.setText(Utils.formatSimplePlurals(Messages
								.getString("TableViewerTemplate.rowCount"), jTableData
								.getRowCount()));
					}
				}
			});

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
			jPanelTable.add(getJPanelRowCount(), BorderLayout.SOUTH);
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
			jButtonAdd.setText(Messages.getString("TableViewerTemplate.add"));
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
			jButtonRemove.setText(Messages.getString("TableViewerTemplate.delete"));
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
			jButtonEdit.setText(Messages.getString("TableViewerTemplate.edit"));
			getTableModel().registerEditRowButton(jButtonEdit);
		}
		return jButtonEdit;
	}

	/**
	 * This method initializes jPanelFilters
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelFilters() {
		if (jPanelFilters == null) {
			jPanelFilters = new JPanel();
			jPanelFilters.setLayout(new BorderLayout());
			jPanelFilters.setBorder(BorderFactory.createTitledBorder(Messages
					.getString("TableViewerTemplate.filtersBorder")));

			jPanelFilters.add(getJPanelFiltersButtons(), BorderLayout.SOUTH);
		}
		return jPanelFilters;
	}

	/**
	 * This method initializes jPanelFiltersButtons
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelFiltersButtons() {
		if (jPanelFiltersButtons == null) {
			jPanelFiltersButtons = new JPanel();
			jPanelFiltersButtons.setLayout(new FlowLayout(FlowLayout.LEFT));
			jPanelFiltersButtons.add(getJButtonSearchFiltered());
			jPanelFiltersButtons.add(getJButtonResetFiltered(), null);
		}
		return jPanelFiltersButtons;
	}

	/**
	 * This method initializes jButtonSearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonSearchFiltered() {
		if (jButtonSearchFiltered == null) {
			jButtonSearchFiltered = new JButton();
			jButtonSearchFiltered.setText(Messages.getString("TableViewerTemplate.filterDo"));
			getTableModel().registerSearchRowsButton(jButtonSearchFiltered);
		}
		return jButtonSearchFiltered;
	}

	/**
	 * This method initializes jButtonResetFiltered
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonResetFiltered() {
		if (jButtonResetFiltered == null) {
			jButtonResetFiltered = new JButton();
			jButtonResetFiltered.setText(Messages.getString("TableViewerTemplate.filterReset"));
			getTableModel().registerResetSearchButton(jButtonResetFiltered);
		}
		return jButtonResetFiltered;
	}

	/**
	 * This method initializes jPanelRowCount
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelRowCount() {
		if (jPanelRowCount == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setAlignment(FlowLayout.RIGHT);
			jLabelRowCount = new JLabel();
			jLabelRowCount.setName("labelRowCount");
			jLabelRowCount.setText(Utils.formatSimplePlurals(Messages
					.getString("TableViewerTemplate.rowCount"), 0));
			jLabelRowCount.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jPanelRowCount = new JPanel();
			jPanelRowCount.setLayout(flowLayout1);
			jPanelRowCount.add(jLabelRowCount, null);
		}
		return jPanelRowCount;
	}

} //  @jve:decl-index=0:visual-constraint="10,10"
