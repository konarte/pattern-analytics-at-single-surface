package edu.mgupi.pass.face.gui.forms;

import java.awt.Frame;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;

import edu.mgupi.pass.db.surfaces.SurfaceClasses;
import edu.mgupi.pass.db.surfaces.SurfaceClassesFactory;
import edu.mgupi.pass.face.gui.template.AbstractEditorTableModel;
import edu.mgupi.pass.face.gui.template.CommonEditorTableModel;
import edu.mgupi.pass.face.gui.template.TableViewerTemplate;

public class SurfaceClassesTable extends TableViewerTemplate<SurfaceClasses> {

	/**
	 * Default constructor.
	 * 
	 * @param owner
	 * 
	 */
	public SurfaceClassesTable(Frame owner) {
		super(owner, "surfaceClassesTable", Messages.getString("SurfaceClassesTable.title"));
	}

	private CommonEditorTableModel<SurfaceClasses> tableModel = null;

	private final static String YES = Messages.getString("SurfaceClassesTable.yes");
	private final static String NO = Messages.getString("SurfaceClassesTable.no");

	@Override
	protected CommonEditorTableModel<SurfaceClasses> getTableModelImpl(JTable owner) {
		if (tableModel == null) {
			tableModel = new CommonEditorTableModel<SurfaceClasses>(owner,
					SurfaceClassesRecord.class) {

				@Override
				protected SurfaceClasses createInstanceImpl() {
					return SurfaceClassesFactory.createSurfaceClasses();
				}

				@Override
				protected String[] getColumns() {
					return new String[] { Messages.getString("SurfaceClassesTable.head.id"),
							Messages.getString("SurfaceClassesTable.head.name"),
							Messages.getString("SurfaceClassesTable.head.image") };
				}

				@Override
				protected List<SurfaceClasses> getDataImpl() throws Exception {
					return Arrays.asList(SurfaceClassesFactory
							.listSurfaceClassesByQuery(null, null));
				}

				@Override
				public Object getValueAt(int rowIndex, int columnIndex) {
					SurfaceClasses surface = super.getRowAt(rowIndex);
					switch (columnIndex) {
					case 0:
						return surface.getIdSurfaceClass();
					case 1:
						return surface.getName();
					case 2:
						return surface.getSurfaceImage() == null ? NO : YES;
					default:
						return columnIndex;
					}
				}
			};
		}
		return tableModel;
	}

	@Override
	protected void tablePostInit(JTable owner) {
		AbstractEditorTableModel model = (AbstractEditorTableModel) owner.getModel();
		model.setHorizontalAlignMode(0, JLabel.CENTER);
		model.setHorizontalAlignMode(2, JLabel.CENTER);
		model.setColumnWidth(1, 200, 1);
	}

}
