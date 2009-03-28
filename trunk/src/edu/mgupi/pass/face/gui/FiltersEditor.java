package edu.mgupi.pass.face.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.face.gui.template.AbstractDialogAdapter;
import edu.mgupi.pass.face.gui.template.AbstractEditorTableModel;
import edu.mgupi.pass.face.gui.template.JTableReadOnly;
import edu.mgupi.pass.face.gui.template.ParametersEditorPanel;
import edu.mgupi.pass.filters.FilterChainsaw;
import edu.mgupi.pass.filters.FilterChainsawTransactional;
import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.filters.FilterChainsawTransactional.FilterStore;

public class FiltersEditor extends JDialog /* implements ActionListener */{
	private final static Logger logger = LoggerFactory.getLogger(FiltersEditor.class); //  @jve:decl-index=0:

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanelSelectFilters = null;
	private JPanel jPanelEditParameters = null;
	private ParametersEditorPanel jPanelParameters = null;
	private JPanel jPanelButtons = null;
	private JButton jButtonOK = null;
	private JButton jButtonCancel = null;

	/**
	 * @param owner
	 */
	public FiltersEditor(Frame owner) {
		super(owner, true);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {

		this.setSize(700, 500);
		this.setName("filtersEditorDialog");
		this.setMinimumSize(new Dimension(700, 500));
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setTitle("Настройка фильтров");
		this.setContentPane(getJContentPane());

	}

	public void setFilters(String title, FilterChainsaw chainsaw) throws Exception {
		if (chainsaw == null) {
			throw new IllegalArgumentException("Internal error. 'Processor.filters' must be not null.");
		}

		this.setTitle("Настройка фильтров " + title);
		getTableModel().setChainsaw(chainsaw);

	}

	private AbstractDialogAdapter myDialogAdapter = null; //  @jve:decl-index=0:

	private AbstractDialogAdapter getDialogAdapter() {
		if (myDialogAdapter != null) {
			return myDialogAdapter;
		}
		myDialogAdapter = new AbstractDialogAdapter(this, getTableModel()) {

			@Override
			protected void openDialogImpl() throws Exception {
			}

			@Override
			protected boolean saveImpl() throws Exception {
				jPanelParameters.saveModelData();
				logger.debug("Commiting changes...");

				tableModel.getChainsaw().commitChanges();
				return true;
			}

			@Override
			protected void cancelImpl() throws Exception {
			}

		};
		return myDialogAdapter;
	}

	public boolean openDialog(String title, FilterChainsaw filters) {
		try {
			this.setFilters(title, filters);
		} catch (Exception e) {
			AppHelper.showExceptionDialog("Ошибка при установке фильтров.", e);
			return false;
		}
		return getDialogAdapter().openDialog();
	}

	private JPanel jPanelControlButtons = null;

	private JButton jButtonAdd = null;

	private JButton jButtonRemove = null;

	private JButton jButtonUp = null;

	private JButton jButtonDown = null;

	private JPanel jPanelFilters = null;

	private JScrollPane jScrollPaneData = null;

	private JTable jTableData = null;

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridwidth = 2;
			gridBagConstraints4.weightx = 1.0D;
			gridBagConstraints4.gridy = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.weightx = 1.0D;
			gridBagConstraints1.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints1.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints1.weighty = 1.0D;
			gridBagConstraints1.gridy = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints.weighty = 1.0D;
			gridBagConstraints.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints.weightx = 0.0D;
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(getJPanelSelectFilters(), gridBagConstraints);
			jContentPane.add(getJPanelEditParameters(), gridBagConstraints1);
			jContentPane.add(getJPanelButtons(), gridBagConstraints4);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanelSelectFilters
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSelectFilters() {
		if (jPanelSelectFilters == null) {
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.anchor = GridBagConstraints.NORTH;
			gridBagConstraints6.weighty = 1.0D;
			gridBagConstraints6.weightx = 0.0D;
			gridBagConstraints6.fill = GridBagConstraints.BOTH;
			gridBagConstraints6.gridy = 0;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.anchor = GridBagConstraints.WEST;
			gridBagConstraints5.fill = GridBagConstraints.NONE;
			gridBagConstraints5.gridy = 1;
			jPanelSelectFilters = new JPanel();
			jPanelSelectFilters.setLayout(new GridBagLayout());
			jPanelSelectFilters.setBorder(BorderFactory.createTitledBorder(null, "Выбранные фильтры",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
					new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jPanelSelectFilters.add(getJPanelFilters(), gridBagConstraints6);
			jPanelSelectFilters.add(getJPanelControlButtons(), gridBagConstraints5);
		}
		return jPanelSelectFilters;
	}

	/**
	 * This method initializes jPanelEditParameters
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelEditParameters() {
		if (jPanelEditParameters == null) {
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.weightx = 1.0D;
			gridBagConstraints3.weighty = 1.0D;
			gridBagConstraints3.gridy = 0;
			jPanelEditParameters = new JPanel();
			jPanelEditParameters.setLayout(new GridBagLayout());
			jPanelEditParameters.setBorder(BorderFactory.createTitledBorder(null, "Редактирование значений фильтра",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
					new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jPanelEditParameters.add(getJPanelParameters(), gridBagConstraints3);
		}
		return jPanelEditParameters;
	}

	/**
	 * This method initializes jPanelParameters
	 * 
	 * @return javax.swing.JPanel
	 */
	private ParametersEditorPanel getJPanelParameters() {
		if (jPanelParameters == null) {
			jPanelParameters = new ParametersEditorPanel();
		}
		return jPanelParameters;
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

	/**
	 * This method initializes jPanelControlButtons
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelControlButtons() {
		if (jPanelControlButtons == null) {
			jPanelControlButtons = new JPanel();
			jPanelControlButtons.setLayout(new FlowLayout());
			jPanelControlButtons.add(getJButtonAdd(), null);
			jPanelControlButtons.add(getJButtonRemove(), null);
			jPanelControlButtons.add(getJButtonUp(), null);
			jPanelControlButtons.add(getJButtonDown(), null);
		}
		return jPanelControlButtons;
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
	 * This method initializes jButtonUp
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonUp() {
		if (jButtonUp == null) {
			jButtonUp = new JButton();
			jButtonUp.setText("Up");
			getTableModel().registerUpRowButton(jButtonUp);
		}
		return jButtonUp;
	}

	/**
	 * This method initializes jButtonDown
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonDown() {
		if (jButtonDown == null) {
			jButtonDown = new JButton();
			jButtonDown.setText("Down");
			getTableModel().registerDownRowButton(jButtonDown);
		}
		return jButtonDown;
	}

	/**
	 * This method initializes jPanelFilters
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelFilters() {
		if (jPanelFilters == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.anchor = GridBagConstraints.NORTH;
			gridBagConstraints2.gridx = -1;
			gridBagConstraints2.gridy = -1;
			gridBagConstraints2.weightx = 0.0D;
			gridBagConstraints2.weighty = 0.0D;
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			jPanelFilters = new JPanel();
			jPanelFilters.setLayout(new BorderLayout());
			jPanelFilters.add(getJScrollPaneData(), BorderLayout.CENTER);
		}
		return jPanelFilters;
	}

	/**
	 * This method initializes jScrollPaneData
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneData() {
		if (jScrollPaneData == null) {
			jScrollPaneData = new JScrollPane();
			jScrollPaneData.setPreferredSize(new Dimension(250, 400));
			jScrollPaneData.setViewportView(getJTableData());
		}
		return jScrollPaneData;
	}

	private final static String[] columns = new String[] { "№", "Фильтр" };

	/**
	 * This method initializes jTableData
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTableData() {
		if (jTableData == null) {
			jTableData = new JTableReadOnly();
			jTableData.setModel(getTableModel());
			jTableData.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

			getTableModel().setHorizontalAlignMode(0, JLabel.CENTER);
			getTableModel().setColumnWidth(1, 200);
		}
		return jTableData;
	}

	static class MyTableModel extends AbstractEditorTableModel {

		private static final long serialVersionUID = 1L;

		private FiltersEditor parent;

		public MyTableModel(JTable owner, FiltersEditor parent) {
			super(owner);
			this.parent = parent;
		}

		private FilterChainsawTransactional transactionalSaw = null;

		protected void setChainsaw(FilterChainsaw chainsaw) {
			this.transactionalSaw = new FilterChainsawTransactional(chainsaw);
			super.fireTableDataChanged();
		}

		protected FilterChainsawTransactional getChainsaw() {
			if (transactionalSaw == null) {
				throw new IllegalStateException("Internal error. Chainsaw is diappeared.");
			}
			return transactionalSaw;
		}

		protected void openImpl() throws Exception {
			//
		}

		protected void closeImpl() throws Exception {
			if (this.transactionalSaw != null) {
				this.transactionalSaw.close();
				this.transactionalSaw = null;
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		protected boolean addRowImpl(int rowIdx) throws Exception {
			LFiltersList list = (LFiltersList) AppHelper.getInstance().getDialogImpl(LFiltersList.class);
			String pickClass = list.openDialog();
			if (pickClass != null) {
				this.getChainsaw().appendFilter(rowIdx, (Class<IFilter>) Class.forName(pickClass));
				return true;
			}
			return false;
		}

		@Override
		protected boolean deleteRowsImpl(int rowIdx[]) throws Exception {
			for (@SuppressWarnings("unused")
			int idx : rowIdx) {
				this.getChainsaw().removeFilter(rowIdx[0]);
			}
			return true;
		}

		@Override
		protected boolean editRowImpl(int rowIdx) throws Exception {
			throw new IllegalStateException("Internal error. This method is prohibited.");
		}

		@Override
		protected boolean moveDownImpl(int[] rowIdx) throws Exception {
			for (int i = rowIdx.length - 1; i >= 0; i--) {
				this.getChainsaw().moveDown(rowIdx[i]);
			}
			return true;
		}

		@Override
		protected boolean moveUpImpl(int[] rowIdx) throws Exception {
			for (int i = 0; i < rowIdx.length; i++) {
				this.getChainsaw().moveUp(rowIdx[i]);
			}
			return true;
		}

		@Override
		protected void rowSelectionImpl(int rowIdx) throws Exception {

			if (rowIdx != -1) {
				FilterStore filter = (FilterStore) this.getChainsaw().getFilterStore(rowIdx);

				parent.jPanelParameters.saveModelData();

				((TitledBorder) parent.jPanelEditParameters.getBorder()).setTitle(filter.name + " - параметры");
				parent.jPanelParameters.setModelData(filter.parameters);
				parent.pack();

			} else {
				((TitledBorder) parent.jPanelEditParameters.getBorder()).setTitle("Фильтр не выбран");
				parent.jPanelParameters.setModelData(null);
			}

			parent.repaint();
		}

		@Override
		public int getColumnCount() {
			return columns.length;
		}

		@Override
		public int getRowCount() {
			return this.transactionalSaw == null ? 0 : this.transactionalSaw.getFilterCount();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return columnIndex == 0 ? String.valueOf(rowIndex + 1) : transactionalSaw.getFilterStore(rowIndex).name;
		}

		@Override
		public String getColumnName(int column) {
			return columns[column];
		}

	};

	private MyTableModel tableModel = null;

	private MyTableModel getTableModel() {
		if (tableModel == null) {
			tableModel = new MyTableModel(getJTableData(), this);
		}

		return tableModel;
	}

} //  @jve:decl-index=0:visual-constraint="10,10"
