package edu.mgupi.pass.face.gui;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;

import edu.mgupi.pass.db.surfaces.SurfaceTypes;
import edu.mgupi.pass.db.surfaces.SurfaceTypesFactory;
import edu.mgupi.pass.face.gui.template.AbstractEditorTableModel;
import edu.mgupi.pass.face.gui.template.CommonEditorTableModel;
import edu.mgupi.pass.face.gui.template.TableEditorTemplate;

public class SurfaceTypesTable extends TableEditorTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SurfaceTypesTable(Frame owner) {
		super(owner, "surfaceTypesTable", "Список типов поверхностей");
	}


	private AbstractEditorTableModel tableModel = null;

	@Override
	protected AbstractEditorTableModel getTableModelImpl(JTable owner) {
		if (tableModel == null) {
			tableModel = new CommonEditorTableModel(owner, SurfaceClassesRecord.class) {
				private static final long serialVersionUID = 1L;

				@Override
				protected Object createInstanceImpl() {
					return SurfaceTypesFactory.createSurfaceTypes();
				}

				@Override
				protected String[] getColumns() {
					return new String[] { "ID", "Тип поверхности", "Материал" };
				}

				@SuppressWarnings("unchecked")
				@Override
				protected List getDataImpl() throws Exception {
					List<SurfaceTypes> types = new ArrayList<SurfaceTypes>();
					types.addAll(Arrays.asList(SurfaceTypesFactory.listSurfaceTypesByQuery(null, null)));
					return types;
				}

				@Override
				public Object getValueAt(int rowIndex, int columnIndex) {
					SurfaceTypes surface = (SurfaceTypes) data.get(rowIndex);
					switch (columnIndex) {
					case 0:
						return surface.getIdSurfaceType();
					case 1:
						return surface.getName();
					case 2:
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
		model.setHorizontalAlignMode(2, JLabel.CENTER);
		model.setColumnWidth(1, 200, 1);
	}

}
