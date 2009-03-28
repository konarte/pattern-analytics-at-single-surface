package edu.mgupi.pass.face.gui;

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
		super(owner);
		initialize();
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setName("defectTypesTable");
		this.setTitle("Список типов дефектов");

	}

	private AbstractEditorTableModel tableModel = null;

	@Override
	protected AbstractEditorTableModel getTableModelImpl(JTable owner) {
		if (tableModel == null) {
			tableModel = new CommonEditorTableModel(owner, DefectTypesRecord.class) {
				private static final long serialVersionUID = 1L;

				@Override
				protected Object createInstanceImpl() {
					return DefectTypesFactory.createDefectTypes();
				}

				@Override
				protected String[] getColumns() {
					return new String[] { "ID", "Класс дефекта", "Тип дефекта", "Доп. опции", "Картинка" };
				}

				@SuppressWarnings("unchecked")
				@Override
				protected List getDataImpl() throws Exception {
					List<DefectTypes> classes = new ArrayList<DefectTypes>();
					classes.addAll(Arrays.asList(DefectTypesFactory.listDefectTypesByQuery(null, null)));
					return classes;
				}

				@Override
				public Object getValueAt(int rowIndex, int columnIndex) {
					DefectTypes defect = (DefectTypes) data.get(rowIndex);
					switch (columnIndex) {
					case 0:
						return defect.getIdDefectType();
					case 1:
						return defect.getDefectClass().getName();
					case 2:
						return defect.getName();
					case 3:
						return defect.getOptions().size() == 0 ? "Нет" : "Да";
					case 4:
						return defect.getDefectImage() == null ? "Нет" : "Да";
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
