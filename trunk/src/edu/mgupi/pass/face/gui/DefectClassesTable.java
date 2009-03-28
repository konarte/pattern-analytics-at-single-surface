package edu.mgupi.pass.face.gui;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;

import edu.mgupi.pass.db.defects.DefectClasses;
import edu.mgupi.pass.db.defects.DefectClassesFactory;
import edu.mgupi.pass.face.gui.template.AbstractEditorTableModel;
import edu.mgupi.pass.face.gui.template.CommonEditorTableModel;
import edu.mgupi.pass.face.gui.template.TableEditorTemplate;

public class DefectClassesTable extends TableEditorTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DefectClassesTable(Frame owner) {
		super(owner, "defectClassesTable", "Классы дефектов");
	}

	private AbstractEditorTableModel tableModel = null;

	@Override
	protected AbstractEditorTableModel getTableModelImpl(JTable owner) {
		if (tableModel == null) {
			tableModel = new CommonEditorTableModel<DefectClasses>(owner, DefectClassesRecord.class) {
				private static final long serialVersionUID = 1L;

				@Override
				protected DefectClasses createInstanceImpl() {
					return DefectClassesFactory.createDefectClasses();
				}

				@Override
				protected String[] getColumns() {
					return new String[] { "ID", "Класс дефекта" };
				}

				@Override
				protected List<DefectClasses> getDataImpl() throws Exception {
					List<DefectClasses> classes = new ArrayList<DefectClasses>();
					classes.addAll(Arrays.asList(DefectClassesFactory.listDefectClassesByQuery(null, null)));
					return classes;
				}

				@Override
				public Object getValueAt(int rowIndex, int columnIndex) {
					DefectClasses defect = data.get(rowIndex);
					return columnIndex == 0 ? defect.getIdDefectClass() : defect.getName();
				}
			};
		}
		return tableModel;
	}

	@Override
	protected void tablePostInit(JTable owner) {
		AbstractEditorTableModel model = (AbstractEditorTableModel) owner.getModel();
		model.setHorizontalAlignMode(0, JLabel.CENTER);
		model.setColumnWidth(1, 200);
	}

} //  @jve:decl-index=0:visual-constraint="10,10"
