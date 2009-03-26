package edu.mgupi.pass.face.gui.template;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.face.gui.AppHelper;
import edu.mgupi.pass.util.Config;
import edu.mgupi.pass.util.Utils;
import edu.mgupi.pass.util.Config.DeletionMode;

public abstract class AbstractEditorTableModel extends AbstractTableModel implements ActionListener,
		ListSelectionListener {

	private final static Logger logger = LoggerFactory.getLogger(AbstractEditorTableModel.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JTable owner = null;

	public AbstractEditorTableModel(JTable owner) {
		if (owner == null) {
			throw new IllegalArgumentException("Internal error. 'owner' must be not null.");
		}
		this.owner = owner;
		this.owner.getSelectionModel().addListSelectionListener(this);
		this.owner.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					AbstractEditorTableModel.this.editRow();
				}
			}

		});
	}

	public void setColumnWidth(int... width) {
		if (width == null) {
			return;
		}

		for (int i = 0; i < width.length; i++) {
			TableColumn column = this.owner.getColumn(this.owner.getColumnName(i));
			column.setPreferredWidth(width[i]);
		}
	}

	public void setHorizontalAlignMode(int column, int mode) {
		TableColumn col = this.owner.getColumn(this.owner.getColumnName(column));

		DefaultTableCellRenderer cellr = new DefaultTableCellRenderer();
		cellr.setHorizontalAlignment(mode);
		col.setCellRenderer(cellr);
	}

	private JButton editButton;

	public void registerEditRowButton(JButton button) {
		if (editButton != null) {
			throw new IllegalStateException("'editButton' already registered (" + editButton.getText() + ").");
		}
		this.editButton = button;
		button.setActionCommand("edit");
		button.setName("edit");
		Utils.addCheckedListener(button, this);
	}

	private JButton upButton;

	public void registerUpRowButton(JButton button) {
		if (upButton != null) {
			throw new IllegalStateException("'upButton' already registered (" + upButton.getText() + ").");
		}
		this.upButton = button;
		button.setActionCommand("up");
		button.setName("up");
		Utils.addCheckedListener(button, this);
	}

	private JButton downButton;

	public void registerDownRowButton(JButton button) {
		if (downButton != null) {
			throw new IllegalStateException("'downButton' button already registered (" + downButton.getText() + ").");
		}
		this.downButton = button;
		button.setActionCommand("down");
		button.setName("down");
		Utils.addCheckedListener(button, this);
	}

	private JButton addButton;

	public void registerAddRowButton(JButton button) {
		if (addButton != null) {
			throw new IllegalStateException("'addButton' button already registered (" + addButton.getText() + ").");
		}
		this.addButton = button;
		button.setActionCommand("add");
		button.setName("add");
		Utils.addCheckedListener(button, this);
	}

	private JButton deleteButton;

	public void registerDeleteRowButton(JButton button) {
		if (deleteButton != null) {
			throw new IllegalStateException("'deleteButton' button already registered (" + deleteButton.getText()
					+ ").");
		}
		this.deleteButton = button;
		button.setActionCommand("delete");
		button.setName("delete");
		Utils.addCheckedListener(button, this);
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command == null) {
			return;
		}

		if (command.equals("add")) {
			logger.debug("Execution 'addRow' command.");
			this.addRow();
		} else if (command.equals("delete")) {
			logger.debug("Execution 'deleteRow' command.");
			this.deleteRow();
		} else if (command.equals("up")) {
			logger.debug("Execution 'upRow' command.");
			this.upRow();
		} else if (command.equals("down")) {
			logger.debug("Execution 'downRow' command.");
			this.downRow();
		} else if (command.equals("edit")) {
			logger.debug("Execution 'editRow' command.");
			this.editRow();
		}
	}

	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			return;
		}

		int rowIdx = this.owner.getSelectedRow();

		if (rowIdx != -1) {
			lastRowSelected = rowIdx;
		}

		logger.debug("Selecting row " + rowIdx + " of " + e);

		try {
			this.rowSelectionImpl(rowIdx);
		} catch (Exception e1) {
			logger.error("Error when selecting row", e);
			AppHelper.showExceptionDialog("Ошибка при смене строки.", e1);
			return;
		}

		if (this.deleteButton != null) {
			this.deleteButton.setEnabled(this.getRowCount() > 0);
		}

		if (this.editButton != null) {
			this.editButton.setEnabled(this.getRowCount() > 0 && this.owner.getSelectedRowCount() == 1);
		}

		int selectedRows[] = this.owner.getSelectedRows();
		int len = selectedRows.length;
		if (this.upButton != null) {
			this.upButton.setEnabled((len > 0 ? selectedRows[0] : rowIdx) > 0);
		}
		if (this.downButton != null) {
			this.downButton.setEnabled((len > 0 ? selectedRows[len - 1] : rowIdx) < this.getRowCount() - 1);
		}
	}

	public void addRow() {
		int rowIdx = this.owner.getRowCount();

		logger.debug("Adding row " + rowIdx);

		try {
			if (this.addRowImpl(rowIdx)) {

				logger.debug("OK, success added");

				super.fireTableRowsInserted(rowIdx, rowIdx);

				this.owner.setRowSelectionInterval(rowIdx, rowIdx);
			}
		} catch (Exception e) {
			logger.error("Error when adding row", e);
			AppHelper.showExceptionDialog("Ошибка при добавлении новой строки.", e);
			return;
		}
	}

	public void deleteRow() {

		int rowIdx = this.owner.getSelectedRow();

		logger.debug("Removing row " + rowIdx);

		if (rowIdx < 0) {
			return;
		}
		try {
			int[] selectedRows = this.owner.getSelectedRows();
			if (this.checkDeleteRows(selectedRows.length > 1)) {

				if (this.deleteRowsImpl(selectedRows)) {

					logger.debug("OK, success delete");

					super.fireTableRowsDeleted(selectedRows[0], selectedRows[selectedRows.length - 1]);

					rowIdx = selectedRows[0];
					if (rowIdx >= this.getRowCount()) {
						rowIdx = this.getRowCount() - 1;
					}

					if (rowIdx >= 0) {
						this.owner.setRowSelectionInterval(rowIdx, rowIdx);
					} else {
						this.valueChanged(new ListSelectionEvent(this.owner, -1, -1, false));
					}

				}

			}
		} catch (Exception e) {
			logger.error("Error when removing row", e);
			AppHelper.showExceptionDialog("Ошибка при удалении строки.", e);
			return;
		}
	}

	protected boolean checkDeleteRows(boolean multiple) throws Exception {
		DeletionMode currentMode = Config.getInstance().getRowsDeleteMode();
		if (multiple) {
			if (currentMode != DeletionMode.NO_CONFIRM) {
				return JOptionPane.showConfirmDialog(this.owner.getTopLevelAncestor(),
						"Вы действительно хотите удалить все выделенные строки?",
						"Подтверждение множественного удаления", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
			}
		} else {
			if (currentMode != DeletionMode.NO_CONFIRM && currentMode != DeletionMode.CONFIRM_MULTPLES) {
				return JOptionPane.showConfirmDialog(this.owner.getTopLevelAncestor(),
						"Вы действительно хотите удалить выделенную строку?", "Подтверждение удаления",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
			}
		}
		return true;
	}

	public void editRow() {
		if (this.owner.getSelectedRowCount() != 1) {
			return;
		}
		int rowIdx = this.owner.getSelectedRow();

		logger.debug("Editing row " + rowIdx);

		try {
			if (this.editRowImpl(rowIdx)) {

				logger.debug("OK, success edit");

				super.fireTableRowsUpdated(rowIdx, rowIdx);
			}
		} catch (Exception e) {
			logger.error("Error when editing row", e);
			AppHelper.showExceptionDialog("Ошибка при редактировании новой строки.", e);
			return;
		}
	}

	public void upRow() {
		int rowIdx = this.owner.getSelectedRow();

		if (rowIdx < 0) {
			return;
		}

		logger.debug("Moving row " + rowIdx + " up");

		int[] selectedRows = this.owner.getSelectedRows();

		try {
			if (this.moveUpImpl(selectedRows)) {

				int len = selectedRows.length;
				int first = selectedRows[0];
				int last = selectedRows[len - 1];

				logger.debug("OK, success up");

				super.fireTableRowsUpdated(first - 1, last);

				if (this.upButton != null) {
					this.upButton.setEnabled(first - 1 > 0);
				}
				if (this.downButton != null) {
					this.downButton.setEnabled(last - 1 < this.getRowCount() - 1);
				}

				this.owner.setRowSelectionInterval(first - 1, last - 1);
			}
		} catch (Exception e) {
			logger.error("Error when upping row", e);
			AppHelper.showExceptionDialog("Ошибка при перемещении строки.", e);
			return;
		}
	}

	public void downRow() {
		int rowIdx = this.owner.getSelectedRow();

		if (rowIdx < 0) {
			return;
		}

		logger.debug("Moving row " + rowIdx + " down");

		int[] selectedRows = this.owner.getSelectedRows();
		try {
			if (this.moveDownImpl(selectedRows)) {

				int len = selectedRows.length;
				int first = selectedRows[0];
				int last = selectedRows[len - 1];

				logger.debug("OK, success down");

				super.fireTableRowsUpdated(first, last + 1);

				if (this.upButton != null) {
					this.upButton.setEnabled(first + 1 > 0);
				}
				if (this.downButton != null) {
					this.downButton.setEnabled(last + 1 < this.getRowCount() - 1);
				}

				this.owner.setRowSelectionInterval(first + 1, last + 1);
			}
		} catch (Exception e) {
			logger.error("Error when downing row", e);
			AppHelper.showExceptionDialog("Ошибка при перемещении строки.", e);
			return;
		}
	}

	private int lastRowCount = 0;
	private int lastRowSelected = -1;

	public void open() throws Exception {
		this.openImpl();

		int rowCount = this.getRowCount();

		if (rowCount != this.lastRowCount || this.lastRowSelected == -1) {
			lastRowSelected = 0;
		}

		logger.debug("Using lastRowSelected " + lastRowSelected + "/" + rowCount);

		if (lastRowSelected >= 0 && lastRowSelected < rowCount) {
			owner.setRowSelectionInterval(lastRowSelected, lastRowSelected);
		} else {
			valueChanged(new ListSelectionEvent(this.owner, -1, -1, false));
		}
	}

	public void close() throws Exception {
		lastRowCount = this.getRowCount();
		this.closeImpl();
	}

	protected abstract void openImpl() throws Exception;

	protected abstract void closeImpl() throws Exception;

	protected abstract void rowSelectionImpl(int rowIdx) throws Exception;

	protected abstract boolean addRowImpl(int rowIdx) throws Exception;

	protected abstract boolean deleteRowsImpl(int rowIdx[]) throws Exception;

	protected abstract boolean editRowImpl(int rowIdx) throws Exception;

	protected abstract boolean moveUpImpl(int[] rowIdx) throws Exception;

	protected abstract boolean moveDownImpl(int[] rowIdx) throws Exception;

	public abstract String getColumnName(int column);

}
