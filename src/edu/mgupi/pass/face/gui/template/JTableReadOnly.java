package edu.mgupi.pass.face.gui.template;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 * Simple helper class for tables. Show non-editable table, selecting only rows.
 * 
 * @author raidan
 * 
 */
public class JTableReadOnly extends JTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor with data.
	 * 
	 * @see javax.swing.JTable
	 */
	public JTableReadOnly() {
		super();
		this.setConfig();
	}

	/**
	 * Default constructor with data.
	 * 
	 * @param rowData
	 *            data
	 * @param columnNames
	 *            column names
	 * 
	 * @see javax.swing.JTable
	 */
	public JTableReadOnly(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		this.setConfig();
	}

	private void setConfig() {
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setCellSelectionEnabled(false);
		this.setRowSelectionAllowed(true);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}