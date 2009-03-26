package edu.mgupi.pass.face.gui.template;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import edu.mgupi.pass.face.gui.AppHelper;

public abstract class CommonEditorTableModel extends AbstractEditorTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4443849886815253825L;

	private Class<? extends RecordEditorTemplate> recordEditorClass = null;

	public CommonEditorTableModel(JTable owner, Class<? extends RecordEditorTemplate> recordEditorClass) {
		super(owner);
		if (recordEditorClass == null) {
			throw new IllegalArgumentException("Internal error. 'editDialog' must be not null.");
		}
		this.recordEditorClass = recordEditorClass;
	}

	protected String[] columns = getColumns();
	@SuppressWarnings("unchecked")
	protected List data = null;

	@SuppressWarnings("unchecked")
	@Override
	protected void openImpl() throws Exception {
		data = this.getDataImpl();
		if (data == null) {
			data = new ArrayList();
		}
	}

	@SuppressWarnings("unchecked")
	protected abstract List getDataImpl() throws Exception;

	protected abstract String[] getColumns();

	@Override
	protected void closeImpl() throws Exception {
		data = null;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected boolean addRowImpl(int rowIdx) throws Exception {

		if (data == null) {
			throw new IllegalStateException(
					"Internal error. Unable to add row, 'data' does not initialized yet. Maybe, you forget to 'openDialog'?");
		}

		RecordEditorTemplate dialog = (RecordEditorTemplate) AppHelper.getInstance().getDialogImpl(recordEditorClass);
		Object saved = dialog.editRecord(createInstanceImpl());
		if (saved != null) {
			data.add(rowIdx, saved);
			return true;
		}
		return false;
	}

	protected abstract Object createInstanceImpl();

	@Override
	protected boolean deleteRowsImpl(int rowIdx[]) throws Exception {

		if (data == null) {
			throw new IllegalStateException(
					"Internal error. Unable to add row, 'data' does not initialized yet. Maybe, you forget to 'openDialog'?");
		}

		RecordEditorTemplate dialog = (RecordEditorTemplate) AppHelper.getInstance().getDialogImpl(recordEditorClass);
		System.out.println("Try to delete " + rowIdx.length);
		boolean deleted = dialog.deleteRecords(data.subList(rowIdx[0], rowIdx[rowIdx.length - 1] + 1).toArray());
		if (deleted) {

			for (@SuppressWarnings("unused")
			int idx : rowIdx) {
				data.remove(rowIdx[0]);
			}
			return true;
		}
		return false;
	}

	@Override
	protected boolean editRowImpl(int rowIdx) throws Exception {

		if (data == null) {
			throw new IllegalStateException(
					"Internal error. Unable to add row, 'data' does not initialized yet. Maybe, you forget to 'openDialog'?");
		}

		RecordEditorTemplate dialog = (RecordEditorTemplate) AppHelper.getInstance().getDialogImpl(recordEditorClass);
		Object edited = dialog.editRecord(data.get(rowIdx));
		if (edited != null) {
			return true;
		}
		return false;
	}

	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	@Override
	protected boolean moveDownImpl(int[] rowIdx) throws Exception {
		return false;
	}

	@Override
	protected boolean moveUpImpl(int[] rowIdx) throws Exception {
		return false;
	}

	@Override
	protected void rowSelectionImpl(int rowIdx) throws Exception {
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return data == null ? 0 : data.size();
	}

}
