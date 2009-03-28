package edu.mgupi.pass.face.gui.template;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.face.gui.AppHelper;
import edu.mgupi.pass.util.Config;
import edu.mgupi.pass.util.Config.DeletionCheckMode;

public abstract class CommonEditorTableModel extends AbstractEditorTableModel {
	private final static Logger logger = LoggerFactory.getLogger(CommonEditorTableModel.class);

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

			logger.trace("Received null data during open model.");

			data = new ArrayList();
		} else {
			if (logger.isTraceEnabled()) {
				logger.trace("Received data during open model: {}.", data);
			}
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
		Object added = dialog.addRecord(createInstanceImpl());
		if (added != null) {

			logger.trace("Successfully added. Received new object: {}.", added);

			data.add(rowIdx, added);
			return true;
		} else {

			logger.trace("Does not added. Return false.");

			return false;
		}
	}

	protected abstract Object createInstanceImpl();

	@Override
	protected boolean deleteRowsImpl(int rowIdx[]) throws Exception {

		if (data == null) {
			throw new IllegalStateException(
					"Internal error. Unable to add row, 'data' does not initialized yet. Maybe, you forget to 'openDialog'?");
		}

		logger.trace("Try to delete {} rows.", rowIdx.length);

		RecordEditorTemplate dialog = (RecordEditorTemplate) AppHelper.getInstance().getDialogImpl(recordEditorClass);

		Object[] rows = data.subList(rowIdx[0], rowIdx[rowIdx.length - 1] + 1).toArray();

		if (Config.getInstance().getDeletionCheckMode() == DeletionCheckMode.CHECK_THEN_ACQUIRE) {
			boolean canDelete = dialog.isDeleteAllowed(rows);
			if (!canDelete) {
				return false;
			} else {
				if (!super.checkDeleteRows(rowIdx.length > 1)) {
					return false;
				}
			}
		}

		boolean deleted = dialog.deleteRecords(
				Config.getInstance().getDeletionCheckMode() == DeletionCheckMode.ACQUIRE_THEN_CHECK, rows);
		if (deleted) {

			logger.trace("Successfully deleted. Removing rows.");

			for (@SuppressWarnings("unused")
			int idx : rowIdx) {
				data.remove(rowIdx[0]);
			}
			return true;
		} else {
			logger.trace("Does not deleted. Return false.");
			return false;
		}

	}

	protected boolean checkDeleteRows(boolean multiple) throws Exception {
		if (Config.getInstance().getDeletionCheckMode() == DeletionCheckMode.ACQUIRE_THEN_CHECK
				|| Config.getInstance().getDeletionCheckMode() == DeletionCheckMode.NO_CHECK) {
			return super.checkDeleteRows(multiple);
		} else {
			return true;
		}
	}

	@Override
	protected boolean editRowImpl(int rowIdx) throws Exception {

		if (data == null) {
			throw new IllegalStateException(
					"Internal error. Unable to add row, 'data' does not initialized yet. Maybe, you forget to 'openDialog'?");
		}

		RecordEditorTemplate dialog = (RecordEditorTemplate) AppHelper.getInstance().getDialogImpl(recordEditorClass);
		boolean edited = dialog.editRecord(data.get(rowIdx));
		if (edited) {
			logger.trace("Successfully edited. Edited object: {}.", edited);

			return true;
		} else {
			logger.trace("Does not edited. Return false.");
			return false;
		}

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
