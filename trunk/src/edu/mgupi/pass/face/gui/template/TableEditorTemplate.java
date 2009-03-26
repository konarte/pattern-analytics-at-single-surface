package edu.mgupi.pass.face.gui.template;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.util.Utils;

public abstract class TableEditorTemplate extends JDialog implements ActionListener {

	private final static Logger logger = LoggerFactory.getLogger(TableEditorTemplate.class);

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;

	private static enum TableActions {
		add, remove, save, cancel
	};

	private void registerAction(AbstractButton button, TableActions action) {
		button.setName(action.name());
		button.setActionCommand(action.name());
		Utils.addCheckedListener(button, this);
	}

	/**
	 * @param owner
	 */
	public TableEditorTemplate(Frame owner) {
		super(owner);
		initialize();
	}
	
	protected abstract AbstractEditorTableModel getTableModel();

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(374, 258);
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

	private AbstractDialogAdapter getDialogAdapter() {
		if (dialogAdapter != null) {
			return dialogAdapter;
		}

		dialogAdapter = new AbstractDialogAdapter(this) {

			@Override
			protected void cancelImpl() throws Exception {
				// TODO Auto-generated method stub

			}

			@Override
			protected void openDialogImpl() throws Exception {
				// TODO Auto-generated method stub

			}

			@Override
			protected boolean saveImpl() throws Exception {
				// TODO Auto-generated method stub
				return false;
			}

		};

		return dialogAdapter;
	}

	public boolean opendDialog() {
		return getDialogAdapter().openDialog();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command == null) {
			return;
		}

		TableActions action = null;
		try {
			action = TableActions.valueOf(e.getActionCommand());
		} catch (IllegalArgumentException iae) {
			logger.debug("Received unknown action: " + e.getActionCommand());
			return;
		}

		if (action == TableActions.add) {

		} else if (action == TableActions.remove) {
			
			//jTableData.removeRowSelectionInterval(index0, index1)
		} else if (action == TableActions.save) {
			getDialogAdapter().save();
		} else if (action == TableActions.cancel) {
			getDialogAdapter().cancel();
		} else {
			JOptionPane.showMessageDialog(this, "Internal error. Unknown action: " + action, "Internal error",
					JOptionPane.ERROR_MESSAGE);
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
			jTableData = new JTableReadOnly(getTableModel());
			jTableData.setName("data");
			
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
			jButtonSave.setText("Сохранить");
			registerAction(jButtonSave, TableActions.save);
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
			jButtonCancel.setText("Отменить");
			registerAction(jButtonCancel, TableActions.cancel);
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
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = -1;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = -1;
			jPanelTable = new JPanel();
			jPanelTable.setLayout(new GridBagLayout());
			jPanelTable.add(getJScrollPaneData(), gridBagConstraints);
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
			jButtonAdd.setText("+");
			registerAction(jButtonAdd, TableActions.add);
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
			jButtonRemove.setText("-");
			registerAction(jButtonRemove, TableActions.remove);
		}
		return jButtonRemove;
	}

} //  @jve:decl-index=0:visual-constraint="10,10"
