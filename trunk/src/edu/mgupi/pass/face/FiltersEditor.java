package edu.mgupi.pass.face;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.face.template.ParametersEditorPanel;
import edu.mgupi.pass.filters.FilterChainsaw;
import edu.mgupi.pass.filters.FilterChainsawTransaction2;
import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.filters.FilterChainsawTransaction2.FilterStore;

public class FiltersEditor extends JDialog {
	private final static Logger logger = LoggerFactory.getLogger(FiltersEditor.class); //  @jve:decl-index=0:

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanelSelectFilters = null;
	private JPanel jPanelEditParameters = null;
	private JScrollPane jScrollPaneSelected = null;
	private ParametersEditorPanel jPanelParameters = null;
	private JList jListSelected = null;
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
		this.setMinimumSize(new Dimension(700, 500));
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setTitle("Настройка фильтров");
		this.setContentPane(getJContentPane());
	}

	private FilterListModel filterListModel = null;

	public void setFilters(String title, FilterChainsaw filters) {
		if (filters == null) {
			throw new IllegalArgumentException("Internal error. 'Processor.filters' must be not null.");
		}

		this.lastSelected = null;

		this.setTitle("Настройка фильтров " + title);
		filterListModel.setChainsaw(new FilterChainsawTransaction2(filters));
		filterListModel.updateFiltersImpl();
		jListSelected.setSelectedIndex(0);
		jListSelected.ensureIndexIsVisible(0);

	}

	private FilterStore lastSelected = null; //  @jve:decl-index=0:

	private void onSelectItem(JList list) {

		try {
			this.onSelectItemImpl(list);
		} catch (Exception e) {
			logger.error("Error when applying filter options", e);
			AppHelper.showExceptionDialog(this, "Unexpected error when filter options.", e);
		}
	}

	private void onSelectItemImpl(JList list) throws Exception {
		FilterStore filter = (FilterStore) list.getSelectedValue();
		if (filter != null) {

			logger.debug("Selecting filter {} and last selected is null ? {}", filter.name, lastSelected == null);

			if (lastSelected != null) {
				jPanelParameters.saveParameterValues();
			}
			lastSelected = filter;

			((TitledBorder) jPanelEditParameters.getBorder()).setTitle("Редактирование \"" + filter.name + "\"");
			jPanelParameters.setParameters(filter.parameters);
			this.pack();

			logger.trace("Finished.");
		} else {
			logger.trace("Skip updating. No rows.");

			((TitledBorder) jPanelEditParameters.getBorder()).setTitle("Модуль не выбран");
			jPanelParameters.setParameters(null);

		}
		this.repaint();
	}

	public boolean ok = false;

	public boolean open(String title, FilterChainsaw filters) {
		this.setFilters(title, filters);
		this.setVisible(true);
		return ok;
	}

	private JPanel jPanelControlButtons = null;

	private JButton jButtonAdd = null;

	private JButton jButtonRemove = null;

	private JButton jButtonUp = null;

	private JButton jButtonDown = null;

	private JPanel jPanelFilters = null;

	private void save() {
		try {
			this.saveImpl();
		} catch (Exception e) {
			logger.error("Error when applying filter options", e);
			AppHelper.showExceptionDialog(this, "Unexpected error when filter options.", e);
		}
	}

	private void saveImpl() throws Exception {
		ok = false;

		jPanelParameters.saveParameterValues();
		logger.debug("Commiting changes...");

		FilterChainsawTransaction2 chainsaw = this.filterListModel.getChainsaw();

		chainsaw.commitChanges();
		chainsaw.close();
		chainsaw = null;

		ok = true;
		this.setVisible(false);

	}

	private void cancelImpl() {
		ok = false;
		this.filterListModel.closeChainsaw();
		this.setVisible(false);
	}

	private void addFilter() {
		try {
			this.addFilterImpl();
		} catch (Exception e) {
			logger.error("Error when adding new filter", e);
			AppHelper.showExceptionDialog(this, "Unexpected error when adding filter.", e);
		}
	}

	@SuppressWarnings("unchecked")
	private void addFilterImpl() throws Exception {

		Window owner = this.getOwner();
		Frame parent = owner != null && owner instanceof Frame ? (Frame) owner : null;

		FilterListDialog list = (FilterListDialog) AppHelper.getInstance().openWindowDialogHidden(parent,
				FilterListDialog.class);
		String pickClass = list.open();
		if (pickClass != null) {

			//int index = jListSelected.getSelectedIndex() + 1; //get selected index
			int index = filterListModel.getSize();

			logger.debug("Attempt to open class " + pickClass + ", " + filterListModel.getSize()
					+ " items alredy opened, " + index + " position");

			this.filterListModel.addFilterImpl(index, (Class<IFilter>) Class.forName(pickClass));

			this.jListSelected.setSelectedIndex(index);
			this.jListSelected.ensureIndexIsVisible(index);

		}
	}

	private void deleteFilter() {
		int index = this.jListSelected.getSelectedIndex();
		if (index < 0) {
			return;
		}

		if (JOptionPane.showConfirmDialog(this, "Действительно удалить выбранный фильтр?",
				"Подтверждение удаления фильтра", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
			return;
		}

		logger.debug("Attempt to remove (" + index + ") " + this.jListSelected.getSelectedValue());

		this.filterListModel.removeFilterImpl(index);

		if (this.filterListModel.getSize() == 0) {
			this.onSelectItem(this.jListSelected);
		} else {
			if (index >= filterListModel.getSize()) {
				index--;
			}
			this.jListSelected.setSelectedIndex(index);
		}

	}

	static class FilterListModel extends AbstractListModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private FilterChainsawTransaction2 chainsaw = null;

		protected void setChainsaw(FilterChainsawTransaction2 chainsaw) {
			this.chainsaw = chainsaw;
		}

		protected FilterChainsawTransaction2 getChainsaw() {
			if (chainsaw == null) {
				throw new IllegalStateException("Internal error. Chainsaw is diappeared.");
			}
			return chainsaw;
		}

		protected void closeChainsaw() {
			if (this.chainsaw != null) {
				this.chainsaw.close();
				this.chainsaw = null;
			}
		}

		public void updateFiltersImpl() {
			super.fireContentsChanged(this, 0, this.getSize());
		}

		public void addFilterImpl(int index, Class<IFilter> filterClass) throws InstantiationException,
				IllegalAccessException {
			this.getChainsaw().appendFilter(index, filterClass);
			super.fireIntervalAdded(this, index, index);
		}

		public void removeFilterImpl(int index) {
			this.getChainsaw().removeFilter(index);
			super.fireIntervalRemoved(this, index, index);
		}

		public boolean moveUp(int index) {
			if (this.getChainsaw().moveUp(index)) {
				super.fireContentsChanged(this, index - 1, index);
				return true;
			} else {
				return false;
			}
		}

		public boolean moveDown(int index) {
			if (this.getChainsaw().moveDown(index)) {
				super.fireContentsChanged(this, index, index + 1);
				return true;
			} else {
				return false;
			}
		}

		@Override
		public Object getElementAt(int index) {
			return chainsaw == null ? null : chainsaw.getFilterStore(index);
		}

		@Override
		public int getSize() {
			return chainsaw == null ? 0 : chainsaw.getFilterCount();
		}
	}

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
			gridBagConstraints6.weightx = 1.0D;
			gridBagConstraints6.fill = GridBagConstraints.BOTH;
			gridBagConstraints6.gridy = 0;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.anchor = GridBagConstraints.WEST;
			gridBagConstraints5.fill = GridBagConstraints.NONE;
			gridBagConstraints5.gridy = 1;
			jPanelSelectFilters = new JPanel();
			jPanelSelectFilters.setLayout(new GridBagLayout());
			jPanelSelectFilters.setBorder(BorderFactory.createTitledBorder(null, "Управление фильтрами",
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
	 * This method initializes jScrollPaneSelected
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneSelected() {
		if (jScrollPaneSelected == null) {
			jScrollPaneSelected = new JScrollPane();
			jScrollPaneSelected.setPreferredSize(new Dimension(230, 120));
			jScrollPaneSelected.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			jScrollPaneSelected.setViewportView(getJListSelected());
		}
		return jScrollPaneSelected;
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

	private FilterListModel getFilterListModel() {
		if (filterListModel == null) {
			filterListModel = new FilterListModel();
		}
		return filterListModel;
	}

	/**
	 * This method initializes jListSelected
	 * 
	 * @return javax.swing.JList
	 */
	private JList getJListSelected() {
		if (jListSelected == null) {
			jListSelected = new JList(getFilterListModel());

			jListSelected.setCellRenderer(new DefaultListCellRenderer() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
						boolean cellHasFocus) {
					FilterStore store = ((FilterStore) value);
					if (store == null) {
						throw new RuntimeException("Unexpected error! Store = null.");
					}

					return super.getListCellRendererComponent(list, store.name, index, isSelected, cellHasFocus);
				}
			});

			jListSelected.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jListSelected.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					if (!e.getValueIsAdjusting()) {
						FiltersEditor.this.onSelectItem((JList) e.getSource());
					}
				}
			});

			jListSelected.setVisibleRowCount(10);

		}
		return jListSelected;
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
					FiltersEditor.this.save();
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
					FiltersEditor.this.cancelImpl();
				}
			});
			jButtonCancel.setName("cancel");
			jButtonCancel.setText("Отмена");
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
			jButtonAdd.setAction(new AbstractAction() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					FiltersEditor.this.addFilter();
				}
			});
			jButtonAdd.setName("");
			jButtonAdd.setText("Добавить");
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
			jButtonRemove.setAction(new AbstractAction() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					FiltersEditor.this.deleteFilter();
				}
			});
			jButtonRemove.setText("Удалить");
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
			jButtonUp.setAction(new AbstractAction() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					int index = jListSelected.getSelectedIndex();
					if (filterListModel.moveUp(index)) {
						jListSelected.setSelectedIndex(index - 1);
						jListSelected.ensureIndexIsVisible(index - 1);
					}
				}
			});
			jButtonUp.setText("Up");
			jButtonUp.setName("up");
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
			jButtonDown.setAction(new AbstractAction() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					int index = jListSelected.getSelectedIndex();
					if (filterListModel.moveDown(index)) {
						jListSelected.setSelectedIndex(index + 1);
						jListSelected.ensureIndexIsVisible(index + 1);
					}
				}
			});
			jButtonDown.setName("down");
			jButtonDown.setText("Down");
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
			jPanelFilters.add(getJScrollPaneSelected(), BorderLayout.CENTER);
		}
		return jPanelFilters;
	}

} //  @jve:decl-index=0:visual-constraint="10,10"
