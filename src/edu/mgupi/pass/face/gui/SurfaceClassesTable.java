package edu.mgupi.pass.face.gui;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;

import edu.mgupi.pass.db.surfaces.SurfaceClasses;
import edu.mgupi.pass.db.surfaces.SurfaceClassesFactory;
import edu.mgupi.pass.face.gui.template.AbstractEditorTableModel;
import edu.mgupi.pass.face.gui.template.CommonEditorTableModel;
import edu.mgupi.pass.face.gui.template.TableEditorTemplate;

public class SurfaceClassesTable extends TableEditorTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SurfaceClassesTable(Frame owner) {
		super(owner, "surfaceClassesTable", Messages.getString("SurfaceClassesTable.title"));
	}

	private AbstractEditorTableModel tableModel = null;

	private final static String YES = Messages.getString("SurfaceClassesTable.yes");
	private final static String NO = Messages.getString("SurfaceClassesTable.no");

	@Override
	protected AbstractEditorTableModel getTableModelImpl(JTable owner) {
		if (tableModel == null) {
			tableModel = new CommonEditorTableModel<SurfaceClasses>(owner,
					SurfaceClassesRecord.class) {
				private static final long serialVersionUID = 1L;

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
					List<SurfaceClasses> classes = new ArrayList<SurfaceClasses>();
					classes.addAll(Arrays.asList(SurfaceClassesFactory.listSurfaceClassesByQuery(
							null, null)));
					return classes;
				}

				@Override
				public Object getValueAt(int rowIndex, int columnIndex) {
					SurfaceClasses surface = data.get(rowIndex);
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
