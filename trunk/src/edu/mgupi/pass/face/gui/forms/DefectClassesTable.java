package edu.mgupi.pass.face.gui.forms;

import java.awt.Frame;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;

import edu.mgupi.pass.db.defects.DefectClasses;
import edu.mgupi.pass.db.defects.DefectClassesFactory;
import edu.mgupi.pass.face.gui.template.AbstractEditorTableModel;
import edu.mgupi.pass.face.gui.template.CommonEditorTableModel;
import edu.mgupi.pass.face.gui.template.TableViewerTemplate;

public class DefectClassesTable extends TableViewerTemplate<DefectClasses> {

	/**
	 * Default constructor.
	 * 
	 * @param owner
	 */
	public DefectClassesTable(Frame owner) {
		super(owner, "defectClassesTable", Messages.getString("DefectClassesTable.title"));
	}

	private CommonEditorTableModel<DefectClasses> tableModel = null;

	@Override
	protected CommonEditorTableModel<DefectClasses> getTableModelImpl(JTable owner) {
		if (tableModel == null) {
			tableModel = new CommonEditorTableModel<DefectClasses>(owner, DefectClassesRecord.class) {

				@Override
				protected DefectClasses createInstanceImpl() {
					return DefectClassesFactory.createDefectClasses();
				}

				@Override
				protected String[] getColumns() {
					return new String[] { Messages.getString("DefectClassesTable.head.id"),
							Messages.getString("DefectClassesTable.head.name") };
				}

				@Override
				protected List<DefectClasses> getDataImpl() throws Exception {
					return Arrays.asList(DefectClassesFactory.listDefectClassesByQuery(null, null));
				}

				@Override
				public Object getValueAt(int rowIndex, int columnIndex) {
					DefectClasses defect = super.getRowAt(rowIndex);
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
