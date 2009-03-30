package edu.mgupi.pass.face.gui.template;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
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

/**
 * Model for editing tables -- such a table show list of rows, where user can
 * add row ({@link #addRow()}), edit current row ({@link #editRow()}), delete
 * row ({@link #deleteRows()}, move rows up ( {@link #moveRowsUp()}, move rows
 * down ({@link #moveRowsDown()}, locate changed item event (
 * {@link #valueChanged(ListSelectionEvent)}.
 * 
 * You can register 5 types of buttons, that provides events:
 * {@link #registerAddRowButton(JButton)},
 * {@link #registerDeleteRowButton(JButton)},
 * {@link #registerEditRowButton(JButton)},
 * {@link #registerMoveRowsDownButton(JButton)},
 * {@link #registerMoveRowsUpButton(JButton)}.
 * 
 * Not all this methods are required to use, for example moving rows up and down
 * is not required when you edit data, loaded from database.
 * 
 * You must implement few ...Impl methods.
 * 
 * @author raidan
 * 
 */
public abstract class AbstractEditorTableModel extends AbstractTableModel implements
		ActionListener, ListSelectionListener {

	private final static Logger logger = LoggerFactory.getLogger(AbstractEditorTableModel.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JTable owner = null;

	/**
	 * Main constructor.
	 * 
	 * @param owner
	 *            instance of table, where you attach this model
	 */
	public AbstractEditorTableModel(JTable owner) {
		if (owner == null) {
			throw new IllegalArgumentException("Internal error. 'owner' must be not null.");
		}

		logger.debug("Initialize editor table model {}.", this);

		this.owner = owner;

		// Set up interval selection mode
		this.owner.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		// see #valueChanged
		this.owner.getSelectionModel().addListSelectionListener(this);

		/*
		 * Provide support for double-clicking on row.
		 * 
		 * If you do double-click -- we call 'edit' method.
		 */
		this.owner.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					AbstractEditorTableModel.this.editRow();
				}
			}

		});
	}

	/**
	 * Help method for set up preferred column with to specified columns.
	 * 
	 * @param width
	 *            width value for every column; this array will use for simple
	 *            iteration through all columns
	 */
	public void setColumnWidth(int... width) {
		if (width == null) {
			return;
		}

		for (int i = 0; i < width.length; i++) {
			TableColumn column = this.owner.getColumn(this.owner.getColumnName(i));
			column.setPreferredWidth(width[i]);
		}
	}

	/**
	 * Help method for set up align mode for specified column
	 * 
	 * @param column
	 *            column number you wish to set up new align mode
	 * @param mode
	 *            alignment mode, see {@link JLabel#setHorizontalAlignment(int)}
	 * 
	 */
	public void setHorizontalAlignMode(int column, int mode) {
		TableColumn col = this.owner.getColumn(this.owner.getColumnName(column));

		DefaultTableCellRenderer cellr = new DefaultTableCellRenderer();
		cellr.setHorizontalAlignment(mode);
		col.setCellRenderer(cellr);
	}

	private JButton editButton;

	/**
	 * Register 'edit' button on dialog. Clicking this button will provide
	 * 'edit' event.
	 * 
	 * @param button
	 *            instance of button, required
	 * 
	 * @see #editRow()
	 */
	public void registerEditRowButton(JButton button) {
		if (editButton != null) {
			throw new IllegalStateException("'editButton' already registered ("
					+ editButton.getText() + ").");
		}

		if (button == null) {
			throw new IllegalStateException("Internal error. Parameter 'button' must be not null.");
		}

		this.editButton = button;
		button.setActionCommand("edit");
		button.setName("edit");
		Utils.addCheckedListener(button, this);
	}

	private JButton upButton;

	/**
	 * Register 'MoveRowsUp' button on dialog. Clicking this button will provide
	 * 'up' event.
	 * 
	 * This is optional method.
	 * 
	 * @param button
	 *            instance of button, required
	 * 
	 * @see #moveRowsUp()
	 */
	public void registerMoveRowsUpButton(JButton button) {
		if (upButton != null) {
			throw new IllegalStateException("'upButton' already registered (" + upButton.getText()
					+ ").");
		}

		if (button == null) {
			throw new IllegalStateException("Internal error. Parameter 'button' must be not null.");
		}

		this.upButton = button;
		button.setActionCommand("up");
		button.setName("up");
		Utils.addCheckedListener(button, this);
	}

	private JButton downButton;

	/**
	 * Register 'MoveRowsDown' button on dialog. Clicking this button will
	 * provide 'down' event.
	 * 
	 * This is optional method.
	 * 
	 * @param button
	 *            instance of button, required
	 * 
	 * @see #moveRowsDown()
	 */
	public void registerMoveRowsDownButton(JButton button) {
		if (downButton != null) {
			throw new IllegalStateException("'downButton' button already registered ("
					+ downButton.getText() + ").");
		}

		if (button == null) {
			throw new IllegalStateException("Internal error. Parameter 'button' must be not null.");
		}

		this.downButton = button;
		button.setActionCommand("down");
		button.setName("down");
		Utils.addCheckedListener(button, this);
	}

	private JButton addButton;

	/**
	 * Register 'AddRow' button on dialog. Clicking this button will provide
	 * 'add' event.
	 * 
	 * @param button
	 *            instance of button, required
	 * 
	 * @see #addRow()
	 */
	public void registerAddRowButton(JButton button) {
		if (addButton != null) {
			throw new IllegalStateException("'addButton' button already registered ("
					+ addButton.getText() + ").");
		}

		if (button == null) {
			throw new IllegalStateException("Internal error. Parameter 'button' must be not null.");
		}

		this.addButton = button;
		button.setActionCommand("add");
		button.setName("add");
		Utils.addCheckedListener(button, this);
	}

	private JButton deleteButton;

	/**
	 * Register 'DeleteRow' button on dialog. Clicking this button will provide
	 * 'delete' event.
	 * 
	 * @param button
	 *            instance of button, required
	 * 
	 * @see #deleteRows()
	 */
	public void registerDeleteRowButton(JButton button) {
		if (deleteButton != null) {
			throw new IllegalStateException("'deleteButton' button already registered ("
					+ deleteButton.getText() + ").");
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

		/*
		 * Process event from buttons
		 */
		if (command.equals("add")) {
			logger.trace("Execution 'addRow' command.");
			this.addRow();
		} else if (command.equals("delete")) {
			logger.trace("Execution 'deleteRow' command.");
			this.deleteRows();
		} else if (command.equals("up")) {
			logger.trace("Execution 'upRow' command.");
			this.moveRowsUp();
		} else if (command.equals("down")) {
			logger.trace("Execution 'downRow' command.");
			this.moveRowsDown();
		} else if (command.equals("edit")) {
			logger.trace("Execution 'editRow' command.");
			this.editRow();
		}
	}

	/**
	 * @see #onRowSelectionImpl(int)
	 */
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			return;
		}
		/*
		 * Select only non-adjusting rows (adjusting happens during continues
		 * selection of rows)
		 */

		int rowIdx = this.owner.getSelectedRow();

		if (rowIdx != -1) {
			lastRowSelected = rowIdx;
		}

		if (logger.isTraceEnabled()) {
			logger.trace("Selecting row {} for {}.", rowIdx, e);
		}

		try {
			this.onRowSelectionImpl(rowIdx);
		} catch (Throwable t) {
			AppHelper.showExceptionDialog(this.owner, Messages
					.getString("AbstractEditorTableModel.err.selectRow"), t);
			return;
		}

		/*
		 * Set up our buttons for enable states
		 */

		if (this.deleteButton != null) {
			this.deleteButton.setEnabled(this.getRowCount() > 0);
		}

		if (this.editButton != null) {
			this.editButton.setEnabled(this.getRowCount() > 0
					&& this.owner.getSelectedRowCount() == 1);
		}

		/*
		 * Check up and down buttons for all selection ranges
		 */
		int selectedRows[] = this.owner.getSelectedRows();
		int len = selectedRows.length;
		if (this.upButton != null) {
			this.upButton.setEnabled((len > 0 ? selectedRows[0] : rowIdx) > 0);
		}
		if (this.downButton != null) {
			this.downButton.setEnabled((len > 0 ? selectedRows[len - 1] : rowIdx) < this
					.getRowCount() - 1);
		}
	}

	/**
	 * Method called when user pressed 'AddRow' button.
	 * 
	 * @see #addRowImpl(int)
	 */
	public void addRow() {
		int currentRow = this.owner.getRowCount();

		logger.trace("Adding row {}. ", currentRow);

		try {
			if (this.addRowImpl(currentRow)) {

				logger.trace("OK, successfully added");

				super.fireTableRowsInserted(currentRow, currentRow);

				this.owner.setRowSelectionInterval(currentRow, currentRow);
			}
		} catch (Throwable t) {
			AppHelper.showExceptionDialog(this.owner, Messages
					.getString("AbstractEditorTableModel.err.addRow"), t);
			return;
		}
	}

	/**
	 * Method called when user pressed 'DeleteRow' button.
	 * 
	 * If user selected more than one row -- all of them will be removed.
	 * 
	 * @see #allowDeleteRows(int[])
	 * @see #deleteRowsImpl(int[])
	 */
	public void deleteRows() {

		int currentRow = this.owner.getSelectedRow();

		logger.trace("Removing row {}.", currentRow);

		if (currentRow < 0) {
			return;
		}
		try {
			// get range 
			int[] selectedRows = this.owner.getSelectedRows();

			// check for deletion
			if (this.allowDeleteRows(selectedRows)) {

				// delete now
				if (this.deleteRowsImpl(selectedRows)) { // #1

					logger.trace("OK, successfully delete");

					super.fireTableRowsDeleted(selectedRows[0],
							selectedRows[selectedRows.length - 1]);

					currentRow = selectedRows[0];
					if (currentRow >= this.getRowCount()) {
						currentRow = this.getRowCount() - 1;
					}

					if (currentRow >= 0) {
						// Go to the top of removed rows and mark previous
						this.owner.setRowSelectionInterval(currentRow, currentRow);
					} else {
						// Well, we removed all rows
						this.valueChanged(new ListSelectionEvent(this.owner, -1, -1, false));
					}

					/*
					 * States of buttons (up, down, remove, edit) will be
					 * updated in #valueChanged method.
					 */

				} // #1 if

			}
		} catch (Throwable t) {
			AppHelper.showExceptionDialog(this.owner, Messages
					.getString("AbstractEditorTableModel.err.deleteRow"), t);
			return;
		}
	}

	/**
	 * Method must check rows and return decision -- can we delete any selected
	 * rows or not.
	 * 
	 * @param selectedRows
	 *            rows we want to delete
	 * @return true if we can delete and false if can't
	 * @throws Exception
	 *             on any error
	 */
	protected boolean allowDeleteRows(int selectedRows[]) throws Exception {
		DeletionMode currentMode = Config.getInstance().getRowsDeleteMode();
		if (selectedRows.length > 1) {
			if (currentMode != DeletionMode.NO_CONFIRM) {
				return JOptionPane.showConfirmDialog(this.owner.getTopLevelAncestor(), Utils
						.formatSimplePlurals(Messages
								.getString("AbstractEditorTableModel.confirm.deleteMultiple"),
								selectedRows.length), Messages
						.getString("AbstractEditorTableModel.title.confirmDelete"),
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

			}
		} else {
			if (currentMode != DeletionMode.NO_CONFIRM
					&& currentMode != DeletionMode.CONFIRM_MULTPLES) {
				return JOptionPane.showConfirmDialog(this.owner.getTopLevelAncestor(), Messages
						.getString("AbstractEditorTableModel.confirm.deleteSingle"), Messages
						.getString("AbstractEditorTableModel.title.confirmDelete"),
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
			}
		}
		return true;
	}

	/**
	 * Method called when user pressed 'EditRow' button.
	 * 
	 * @see #editRowImpl(int)
	 */
	public void editRow() {
		if (this.owner.getSelectedRowCount() != 1) {
			return;
		}
		int currentRow = this.owner.getSelectedRow();

		logger.trace("Editing row {}.", currentRow);

		try {
			if (this.editRowImpl(currentRow)) {

				logger.trace("OK, successfully edit");

				super.fireTableRowsUpdated(currentRow, currentRow);
			}
		} catch (Throwable t) {
			AppHelper.showExceptionDialog(this.owner, Messages
					.getString("AbstractEditorTableModel.err.editRow"), t);
			return;
		}
	}

	/**
	 * Method called when user pressed 'MoveRowsUp' button.
	 * 
	 * @see #moveRowsUpImpl(int[])
	 */
	public void moveRowsUp() {
		int[] selectedRows = this.owner.getSelectedRows();

		if (selectedRows == null || selectedRows.length == 0) {
			logger.trace("Unable to move rows up. Selected rows are empty.");
			return;
		}

		int len = selectedRows.length;
		int first = selectedRows[0];
		int last = selectedRows[len - 1];

		// before do moving -- we must check 
		if (first <= 0) {
			logger.trace("Unable to move row up. First row is {}.", first);
			return;
		}

		if (logger.isTraceEnabled()) {
			logger.trace("Moving rows {} up.", Arrays.toString(selectedRows));
		}

		try {
			if (this.moveRowsUpImpl(selectedRows)) {

				logger.trace("OK, successfully moved up.");

				super.fireTableRowsUpdated(first - 1, last);

				this.owner.setRowSelectionInterval(first - 1, last - 1);
			}
		} catch (Throwable t) {
			AppHelper.showExceptionDialog(this.owner, Messages
					.getString("AbstractEditorTableModel.err.moveUp"), t);
			return;
		}
	}

	/**
	 * Method called when user pressed 'MoveRowsDown' button.
	 * 
	 * @see #moveRowsDownImpl(int[])
	 */
	public void moveRowsDown() {
		int[] selectedRows = this.owner.getSelectedRows();

		if (selectedRows == null || selectedRows.length == 0) {
			logger.trace("Unable to move rows down. Selected rows are empty.");
			return;
		}

		int len = selectedRows.length;
		int first = selectedRows[0];
		int last = selectedRows[len - 1];

		int totalRows = this.getRowCount();
		if (last >= totalRows - 1) {
			logger.trace("Unable to move row down. Last row is {} of {}.", last, totalRows);
			return;
		}

		if (logger.isTraceEnabled()) {
			logger.trace("Moving rows {} down.", Arrays.toString(selectedRows));
		}
		try {
			if (this.moveRowsDownImpl(selectedRows)) {

				logger.trace("OK, success down");

				super.fireTableRowsUpdated(first, last + 1);

				this.owner.setRowSelectionInterval(first + 1, last + 1);
			}
		} catch (Throwable t) {
			AppHelper.showExceptionDialog(this.owner, Messages
					.getString("AbstractEditorTableModel.err.moveDown"), t);
			return;
		}
	}

	private int lastRowCount = 0;
	private int lastRowSelected = -1;

	/**
	 * Method open will be called by {@link AbstractDialogAdapter}, if you
	 * attach to its constructor instance of this class.
	 * 
	 * Method open retrieves data from database, do some stuff.
	 * 
	 * We use them to select previously selected row :)
	 * 
	 * @throws Exception
	 * @see #onOpenImpl()
	 */
	public void onOpenWindow() throws Exception {
		this.onOpenImpl();

		int rowCount = this.getRowCount();

		if (rowCount != this.lastRowCount || this.lastRowSelected == -1) {
			lastRowSelected = 0;
		}

		logger.trace("Opening model. Using lastRowSelected {} of {}.", lastRowSelected, rowCount);

		if (lastRowSelected >= 0 && lastRowSelected < rowCount) {
			owner.setRowSelectionInterval(lastRowSelected, lastRowSelected);
		} else {
			valueChanged(new ListSelectionEvent(this.owner, -1, -1, false));
		}
	}

	/**
	 * Method called by {@link AbstractDialogAdapter}, when parent interface is
	 * done job (pressed 'Save' or 'Cancel').
	 * 
	 * @throws Exception
	 * @see #onCloseImpl()
	 */
	public void onCloseWindow() throws Exception {

		// We use lastRowCount for reopen interface on previously selected row
		lastRowCount = this.getRowCount();
		this.onCloseImpl();
	}

	/**
	 * This method must implement action on open window (loading data, for
	 * example)
	 * 
	 * @throws Exception
	 */
	protected abstract void onOpenImpl() throws Exception;

	/**
	 * This method must clear all loaded data. Calling on window hiding.
	 * 
	 * @throws Exception
	 */
	protected abstract void onCloseImpl() throws Exception;

	/**
	 * Actions on selection row.
	 * 
	 * @param selectedRow
	 * @throws Exception
	 */
	protected abstract void onRowSelectionImpl(int selectedRow) throws Exception;

	/**
	 * Adding new row.
	 * 
	 * @param newRow
	 *            position where you need to insert new row
	 * @return true if row successfully added, false otherwise
	 * @throws Exception
	 */
	protected abstract boolean addRowImpl(int newRow) throws Exception;

	/**
	 * Deleting selected rows.
	 * 
	 * @param selectedRows
	 * @return true if rows successfully deleted, false otherwise
	 * @throws Exception
	 */
	protected abstract boolean deleteRowsImpl(int selectedRows[]) throws Exception;

	/**
	 * Modifying existing row.
	 * 
	 * @param editRow
	 *            row number where you must edit
	 * @return true is row was modified, false otherwise
	 * @throws Exception
	 */
	protected abstract boolean editRowImpl(int editRow) throws Exception;

	/**
	 * Moving rows up.
	 * 
	 * @param selectedRows
	 * @return true if rows was moved up, false otherwise
	 * @throws Exception
	 */
	protected abstract boolean moveRowsUpImpl(int[] selectedRows) throws Exception;

	/**
	 * Moving rows down.
	 * 
	 * @param selectedRows
	 * @return true if rows was moved down, false otherwise
	 * @throws Exception
	 */
	protected abstract boolean moveRowsDownImpl(int[] selectedRows) throws Exception;

	// Standard method for displaying column on position
	public abstract String getColumnName(int column);

}
