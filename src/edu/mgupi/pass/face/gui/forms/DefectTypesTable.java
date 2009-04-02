package edu.mgupi.pass.face.gui.forms;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;

import edu.mgupi.pass.db.defects.DefectTypes;
import edu.mgupi.pass.db.defects.DefectTypesFactory;
import edu.mgupi.pass.face.gui.template.AbstractEditorTableModel;
import edu.mgupi.pass.face.gui.template.CommonEditorTableModel;
import edu.mgupi.pass.face.gui.template.TableEditorTemplate;

public class DefectTypesTable extends TableEditorTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DefectTypesTable(Frame owner) {
		super(owner, "defectTypesTable", Messages.getString("DefectTypesTable.title"));
	}

	private AbstractEditorTableModel tableModel = null;

	private final static String YES = Messages.getString("DefectTypesTable.yes");
	private final static String NO = Messages.getString("DefectTypesTable.no");

	@Override
	protected AbstractEditorTableModel getTableModelImpl(JTable owner) {
		if (tableModel == null) {
			tableModel = new CommonEditorTableModel<DefectTypes>(owner, DefectTypesRecord.class) {
				private static final long serialVersionUID = 1L;

				@Override
				protected DefectTypes createInstanceImpl() {
					return DefectTypesFactory.createDefectTypes();
				}

				@Override
				protected String[] getColumns() {
					return new String[] { Messages.getString("DefectTypesTable.id"),
							Messages.getString("DefectTypesTable.class"),
							Messages.getString("DefectTypesTable.name"),
							Messages.getString("DefectTypesTable.options"),
							Messages.getString("DefectTypesTable.image") };
				}

				@Override
				protected List<DefectTypes> getDataImpl() throws Exception {
					List<DefectTypes> classes = new ArrayList<DefectTypes>();
					classes.addAll(Arrays.asList(DefectTypesFactory.listDefectTypesByQuery(null,
							null)));
					return classes;
				}

				@Override
				public Object getValueAt(int rowIndex, int columnIndex) {
					DefectTypes defect = data.get(rowIndex);
					switch (columnIndex) {
					case 0:
						return defect.getIdDefectType();
					case 1:
						return defect.getDefectClass().getName();
					case 2:
						return defect.getName();
					case 3:
						return defect.getOptions().size() == 0 ? NO : YES;
					case 4:
						return defect.getDefectImage() == null ? NO : YES;
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
		model.setHorizontalAlignMode(4, JLabel.CENTER);
		model.setColumnWidth(1, 100, 100, 1, 1);

	}

}
