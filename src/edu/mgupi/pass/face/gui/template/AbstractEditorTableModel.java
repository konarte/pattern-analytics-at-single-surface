package edu.mgupi.pass.face.gui.template;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

public abstract class AbstractEditorTableModel extends AbstractTableModel implements ActionListener,
		ListSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable owner = null;

	public AbstractEditorTableModel(JTable owner) {
		if (owner == null) {
			throw new IllegalArgumentException("Internal error. 'owner' must be not null.");
		}
		this.owner = owner;
		this.owner.getSelectionModel().addListSelectionListener(this);
	}

	private JButton upButton;

	public void registerUpRowButton(JButton button) {
		this.upButton = button;
		button.setActionCommand("up");
		button.addActionListener(this);
	}

	private JButton downButton;

	public void registerDownRowButton(JButton button) {
		this.downButton = button;
		button.setActionCommand("down");
		button.addActionListener(this);
	}

	//private JButton addButton;
	public void registerAddRowButton(JButton button) {
		//this.addButton = button;
		button.setActionCommand("add");
		button.addActionListener(this);
	}

	private JButton deleteButton;

	public void registerDeleteRowButton(JButton button) {
		this.deleteButton = button;
		button.setActionCommand("delete");
		button.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command == null) {
			return;
		}

		if (command.equals("add")) {
			this.addRow();
		} else if (command.equals("delete")) {
			this.deleteRow();
		} else if (command.equals("up")) {
			this.upRow();
		} else if (command.equals("down")) {
			this.downRow();
		}
	}

	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			return;
		}

		int rowIdx = e.getFirstIndex();

		this.rowSelectionImpl(rowIdx);

		if (this.upButton != null) {
			this.upButton.setEnabled(rowIdx > 0);
		}
		if (this.downButton != null) {
			this.downButton.setEnabled(rowIdx < this.getRowCount() - 1);
		}
	}

	public void addRow() {
		int rowIdx = this.owner.getRowCount() + 1;
		if (this.addRowImpl(rowIdx)) {
			super.fireTableRowsInserted(rowIdx, rowIdx);

			this.owner.setRowSelectionInterval(rowIdx, rowIdx);
		}
	}

	public void deleteRow() {
		int rowIdx = this.owner.getSelectedRow();

		if (rowIdx < 0) {
			return;
		}

		if (JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить строку?", "Подтверждение удаления",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			if (this.deleteRowImpl(rowIdx)) {
				super.fireTableRowsDeleted(rowIdx, rowIdx);

				if (this.deleteButton != null) {
					this.deleteButton.setEnabled(this.getRowCount() > 0);
				}

				if (rowIdx >= this.getRowCount()) {
					rowIdx--;
				}

				this.owner.setRowSelectionInterval(rowIdx, rowIdx);

			}
		}
	}

	public void upRow() {
		int rowIdx = this.owner.getSelectedRow();
		if (this.moveUpImpl(rowIdx)) {
			super.fireTableRowsUpdated(rowIdx, rowIdx + 1);

			if (this.upButton != null) {
				this.upButton.setEnabled(rowIdx > 0);
			}
			if (this.downButton != null) {
				this.downButton.setEnabled(rowIdx < this.getRowCount() - 1);
			}

			this.owner.setRowSelectionInterval(rowIdx, rowIdx);
		}
	}

	public void downRow() {
		int rowIdx = this.owner.getSelectedRow();
		if (this.moveDownImpl(rowIdx)) {
			super.fireTableRowsUpdated(rowIdx, rowIdx - 1);

			if (this.upButton != null) {
				this.upButton.setEnabled(rowIdx > 0);
			}
			if (this.downButton != null) {
				this.downButton.setEnabled(rowIdx < this.getRowCount() - 1);
			}

			this.owner.setRowSelectionInterval(rowIdx, rowIdx);
		}
	}

	protected abstract void rowSelectionImpl(int rowIdx);

	protected abstract boolean addRowImpl(int rowIdx);

	protected abstract boolean deleteRowImpl(int rowIdx);

	protected abstract boolean moveUpImpl(int rowIdx);

	protected abstract boolean moveDownImpl(int rowIdx);

	protected abstract int returnMainColoumn();

}
