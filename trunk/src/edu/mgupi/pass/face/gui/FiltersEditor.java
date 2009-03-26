package edu.mgupi.pass.face.gui;

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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
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

import org.orm.PersistentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.face.gui.template.AbstractDialogAdapter;
import edu.mgupi.pass.face.gui.template.ParametersEditorPanel;
import edu.mgupi.pass.filters.FilterChainsaw;
import edu.mgupi.pass.filters.FilterChainsawTransaction;
import edu.mgupi.pass.filters.FilterException;
import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.filters.FilterChainsawTransaction.FilterStore;
import edu.mgupi.pass.util.Config;
import edu.mgupi.pass.util.Utils;
import edu.mgupi.pass.util.Config.DeletionMode;

public class FiltersEditor extends JDialog implements ActionListener {
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

	private static enum FilterActions {
		add, remove, up, down
	};

	private void registerAction(AbstractButton button, FilterActions action) {
		button.setName(action.name());
		button.setActionCommand(action.name());
		Utils.addCheckedListener(button, this);
	}

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
		filterListModel.setChainsaw(chainsaw);
		filterListModel.updateFiltersImpl();
		jListSelected.setSelectedIndex(0);
		jListSelected.ensureIndexIsVisible(0);

	}

	private AbstractDialogAdapter myDialogAdapter = null; //  @jve:decl-index=0:

	private AbstractDialogAdapter getDialogAdapter() {
		if (myDialogAdapter != null) {
			return myDialogAdapter;
		}
		myDialogAdapter = new AbstractDialogAdapter(this) {

			@Override
			protected void openDialogImpl() throws Exception {
				//
			}

			@Override
			protected boolean saveImpl() throws Exception {
				jPanelParameters.saveModelData();
				logger.debug("Commiting changes...");

				filterListModel.getChainsaw().commitChanges();
				filterListModel.closeChainsaw();

				return true;
			}

			@Override
			protected void cancelImpl() throws Exception {
				filterListModel.closeChainsaw();
			}

		};
		return myDialogAdapter;
	}

	public boolean openDialog(String title, FilterChainsaw filters) {
		try {
			this.setFilters(title, filters);
		} catch (Exception e) {
			logger.error("Error when setting filters", e);
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

	@SuppressWarnings("unchecked")
	private void addFilterImpl() throws Exception {

		LFiltersList list = (LFiltersList) AppHelper.getInstance().getDialogImpl(LFiltersList.class);
		String pickClass = list.openDialog();
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

	private void deleteFilterImpl() {
		int index = this.jListSelected.getSelectedIndex();
		if (index < 0) {
			return;
		}

		logger.debug("Attempt to remove (" + index + ") " + this.jListSelected.getSelectedValue());

		this.filterListModel.removeFilterImpl(index);

		if (index >= filterListModel.getSize()) {
			index--;
		}
		this.jListSelected.setSelectedIndex(index);

	}

	private FilterListModel filterListModel = null;

	static class FilterListModel extends AbstractListModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private FilterChainsawTransaction transactionalSaw = null;

		protected void setChainsaw(FilterChainsaw chainsaw) {
			if (this.transactionalSaw != null) {
				this.closeChainsaw();
			}
			this.transactionalSaw = new FilterChainsawTransaction(chainsaw);
		}

		protected FilterChainsawTransaction getChainsaw() {
			if (transactionalSaw == null) {
				throw new IllegalStateException("Internal error. Chainsaw is diappeared.");
			}
			return transactionalSaw;
		}

		protected void closeChainsaw() {
			if (this.transactionalSaw != null) {
				this.transactionalSaw.close();
				this.transactionalSaw = null;
			}
		}

		public void updateFiltersImpl() {
			super.fireContentsChanged(this, 0, this.getSize());
		}

		public void addFilterImpl(int index, Class<IFilter> filterClass) throws InstantiationException,
				IllegalAccessException, FilterException, PersistentException {
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
			return transactionalSaw == null ? null : transactionalSaw.getFilterStore(index);
		}

		@Override
		public int getSize() {
			return transactionalSaw == null ? 0 : transactionalSaw.getFilterCount();
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

				private void onSelectItemImpl(JList list) throws Exception {
					int index = list.getSelectedIndex();
					if (index != -1) {

						FilterStore filter = (FilterStore) list.getSelectedValue();

						logger.debug("Selecting filter {}", filter.name);

						jPanelParameters.saveModelData();

						((TitledBorder) jPanelEditParameters.getBorder()).setTitle(filter.name + " - параметры");
						jPanelParameters.setModelData(filter.parameters);
						FiltersEditor.this.pack();

						logger.trace("Finished.");

						jButtonRemove.setEnabled(true);

						jButtonUp.setEnabled(index > 0);
						jButtonDown.setEnabled(index < list.getModel().getSize() - 1);

					} else {
						logger.trace("Skip updating. No rows.");

						((TitledBorder) jPanelEditParameters.getBorder()).setTitle("Модуль не выбран");
						jPanelParameters.setModelData(null);

						jButtonUp.setEnabled(false);
						jButtonDown.setEnabled(false);
						jButtonRemove.setEnabled(false);
					}
					FiltersEditor.this.repaint();
				}

				@Override
				public void valueChanged(ListSelectionEvent e) {
					if (!e.getValueIsAdjusting()) {
						try {
							this.onSelectItemImpl((JList) e.getSource());
						} catch (Exception e1) {
							logger.error("Error when applying filter options", e);
							AppHelper.showExceptionDialog("Unexpected error when filter options.", e1);
						}
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
			registerAction(jButtonAdd, FilterActions.add);
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
			jButtonRemove.setEnabled(false);
			registerAction(jButtonRemove, FilterActions.remove);
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
			jButtonUp.setEnabled(false);
			registerAction(jButtonUp, FilterActions.up);
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
			jButtonDown.setEnabled(false);
			registerAction(jButtonDown, FilterActions.down);
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

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand() == null) {
			return;
		}
		FilterActions action = null;
		try {
			action = FilterActions.valueOf(event.getActionCommand());
		} catch (IllegalArgumentException iae) {
			logger.debug("Received unknown action: " + event.getActionCommand());
			return;
		}

		if (action == FilterActions.add) {
			try {
				this.addFilterImpl();
			} catch (Exception e) {
				logger.error("Error when adding new filter", e);
				AppHelper.showExceptionDialog("Unexpected error when adding filter.", e);
			}
		} else if (action == FilterActions.remove) {
			int index = this.jListSelected.getSelectedIndex();
			if (index < 0) {
				return;
			}

			if (Config.getInstance().getFilterDeleteMode() != DeletionMode.CONFIRM
					|| JOptionPane.showConfirmDialog(this, "Действительно удалить выбранный фильтр?",
							"Подтверждение удаления фильтра", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				this.deleteFilterImpl();
			}

		} else if (action == FilterActions.up) {
			int index = jListSelected.getSelectedIndex();
			if (filterListModel.moveUp(index)) {
				jListSelected.setSelectedIndex(index - 1);
				jListSelected.ensureIndexIsVisible(index - 1);
			}
		} else if (action == FilterActions.down) {
			int index = jListSelected.getSelectedIndex();
			if (filterListModel.moveDown(index)) {
				jListSelected.setSelectedIndex(index + 1);
				jListSelected.ensureIndexIsVisible(index + 1);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Internal error. Unknown action: " + action, "Internal error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

} //  @jve:decl-index=0:visual-constraint="10,10"
