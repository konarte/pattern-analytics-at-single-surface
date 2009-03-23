package edu.mgupi.pass.face.template;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;

public class JTableReadOnly extends JTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JTableReadOnly(TableModel dm) {
		super(dm);
		this.setConfig();
	}

	public JTableReadOnly(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		this.setConfig();
	}

	private void setConfig() {
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setCellSelectionEnabled(false);
		this.setRowSelectionAllowed(true);
	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}
}