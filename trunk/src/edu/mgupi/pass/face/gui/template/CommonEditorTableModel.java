package edu.mgupi.pass.face.gui.template;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JTable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.face.gui.AppHelper;
import edu.mgupi.pass.util.Config;
import edu.mgupi.pass.util.Config.DeletionCheckMode;

public abstract class CommonEditorTableModel<T> extends AbstractEditorTableModel {
	private final static Logger logger = LoggerFactory.getLogger(CommonEditorTableModel.class);

	private Class<? extends RecordEditorTemplate<T>> recordEditorClass = null;

	public CommonEditorTableModel(JTable owner,
			Class<? extends RecordEditorTemplate<T>> recordEditorClass) {
		super(owner);
		if (recordEditorClass == null) {
			throw new IllegalArgumentException("Internal error. 'editDialog' must be not null.");
		}
		this.recordEditorClass = recordEditorClass;
	}

	private String[] columns = getColumns();
	private List<T> data = new ArrayList<T>();

	@Override
	protected void onOpenImpl() throws Exception {
		data.clear();
		List<T> implData = this.getDataImpl();
		if (implData != null) {
			logger.trace("Received data during open model: {} rows.", data.size());
			data.addAll(implData);
		} else {
			logger.trace("Received null data during open model.");
		}
		super.fireTableDataChanged();
	}

	public T getRowAt(int index) {
		return data.get(index);
	}

	protected abstract List<T> getDataImpl() throws Exception;

	protected abstract String[] getColumns();

	@Override
	protected void onCloseImpl() throws Exception {
		data.clear();
		super.fireTableDataChanged();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected boolean addRowImpl(int rowIdx) throws Exception {

		if (data == null) {
			throw new IllegalStateException(
					"Internal error. Unable to add row, 'data' does not initialized yet. Maybe, you forget to 'openDialog'?");
		}

		RecordEditorTemplate<T> dialog = (RecordEditorTemplate<T>) AppHelper.getInstance()
				.getDialogImpl(null, recordEditorClass);
		T added = dialog.addRecord(createInstanceImpl());
		if (added != null) {

			logger.trace("Successfully added. Received new object: {}.", added);

			data.add(rowIdx, added);
			return true;
		}

		logger.trace("Does not added. Return false.");

		return false;

	}

	protected abstract T createInstanceImpl();

	@SuppressWarnings("unchecked")
	@Override
	protected boolean deleteRowsImpl(int rowIdx[]) throws Exception {

		if (data == null) {
			throw new IllegalStateException(
					"Internal error. Unable to add row, 'data' does not initialized yet. Maybe, you forget to 'openDialog'?");
		}

		logger.trace("Try to delete {} rows.", rowIdx.length);

		RecordEditorTemplate<T> dialog = (RecordEditorTemplate<T>) AppHelper.getInstance()
				.getDialogImpl(null, recordEditorClass);

		Collection<T> rows = data.subList(rowIdx[0], rowIdx[rowIdx.length - 1] + 1);
		boolean deleted = dialog
				.deleteRecords(
						Config.getInstance().getDeletionCheckMode() == DeletionCheckMode.ACQUIRE_THEN_CHECK,
						rows);
		if (deleted) {

			logger.trace("Successfully deleted. Removing rows from model.");
			for (int i = 0; i < rowIdx.length; i++) {
				data.remove(rowIdx[0]);
			}
			// Unexpected situation on 'removeAll(rows)' :(
			// ConcurrentModification thrown
			//data.removeAll(rows);
			return true;
		}

		logger.trace("Does not deleted. Return false.");
		return false;

	}

	@Override
	@SuppressWarnings("unchecked")
	protected boolean allowDeleteRows(int rows[]) throws Exception {
		boolean allow = true;
		if (Config.getInstance().getDeletionCheckMode() == DeletionCheckMode.ACQUIRE_THEN_CHECK
				|| Config.getInstance().getDeletionCheckMode() == DeletionCheckMode.NO_CHECK) {
			allow = super.allowDeleteRows(rows);
		}

		if (allow && Config.getInstance().getDeletionCheckMode() != DeletionCheckMode.NO_CHECK) {
			Collection<T> rowList = data.subList(rows[0], rows[rows.length - 1] + 1);
			RecordEditorTemplate<T> dialog = (RecordEditorTemplate<T>) AppHelper.getInstance()
					.getDialogImpl(null, recordEditorClass);

			allow = dialog.isDeleteAllowed(rowList);

			if (allow
					&& Config.getInstance().getDeletionCheckMode() == DeletionCheckMode.CHECK_THEN_ACQUIRE) {
				allow = super.allowDeleteRows(rows);
			}
		}
		return allow;

	}

	@SuppressWarnings("unchecked")
	@Override
	protected boolean editRowImpl(int rowIdx) throws Exception {

		if (data == null) {
			throw new IllegalStateException(
					"Internal error. Unable to add row, 'data' does not initialized yet. Maybe, you forget to 'openDialog'?");
		}

		RecordEditorTemplate<T> dialog = (RecordEditorTemplate<T>) AppHelper.getInstance()
				.getDialogImpl(null, recordEditorClass);
		boolean edited = dialog.editRecord(data.get(rowIdx));
		if (edited) {
			logger.trace("Successfully edited. Edited object: {}.", edited);

			return true;
		}

		logger.trace("Does not edited. Return false.");
		return false;

	}

	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	@Override
	protected boolean moveRowsDownImpl(int[] rowIdx) throws Exception {
		return false;
	}

	@Override
	protected boolean moveRowsUpImpl(int[] rowIdx) throws Exception {
		return false;
	}

	@Override
	protected void onRowSelectionImpl(int rowIdx) throws Exception {
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
