package edu.mgupi.pass.face.gui.forms;

import java.awt.Frame;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;

import edu.mgupi.pass.db.surfaces.SurfaceTypes;
import edu.mgupi.pass.db.surfaces.SurfaceTypesFactory;
import edu.mgupi.pass.face.gui.template.AbstractEditorTableModel;
import edu.mgupi.pass.face.gui.template.CommonEditorTableModel;
import edu.mgupi.pass.face.gui.template.TableViewerTemplate;

public class SurfaceTypesTable extends TableViewerTemplate<SurfaceTypes> {

	/**
	 * Default constructor.
	 * 
	 * @param owner
	 * 
	 */
	public SurfaceTypesTable(Frame owner) {
		super(owner, "surfaceTypesTable", Messages.getString("SurfaceTypesTable.title"));
	}

	private CommonEditorTableModel<SurfaceTypes> tableModel = null;

	@Override
	protected CommonEditorTableModel<SurfaceTypes> getTableModelImpl(JTable owner) {
		if (tableModel == null) {
			tableModel = new CommonEditorTableModel<SurfaceTypes>(owner, SurfaceTypesRecord.class) {

				@Override
				protected SurfaceTypes createInstanceImpl() {
					return SurfaceTypesFactory.createSurfaceTypes();
				}

				@Override
				protected String[] getColumns() {
					return new String[] { Messages.getString("SurfaceTypesTable.head.id"),
							Messages.getString("SurfaceTypesTable.head.class"),
							Messages.getString("SurfaceTypesTable.head.name"),
							Messages.getString("SurfaceTypesTable.head.material") };
				}

				@Override
				protected List<SurfaceTypes> getDataImpl() throws Exception {
					return Arrays.asList(SurfaceTypesFactory.listSurfaceTypesByQuery(null, null));
				}

				@Override
				public Object getValueAt(int rowIndex, int columnIndex) {
					SurfaceTypes surface = super.getRowAt(rowIndex);
					switch (columnIndex) {
					case 0:
						return surface.getIdSurfaceType();
					case 1:
						return surface.getSurfaceClass().getName();
					case 2:
						return surface.getName();
					case 3:
						return "-";
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
		model.setHorizontalAlignMode(3, JLabel.CENTER);
		model.setColumnWidth(1, 200, 200, 1);
	}

}
