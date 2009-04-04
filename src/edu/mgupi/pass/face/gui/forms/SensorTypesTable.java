/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)SensorTypesTable.java 1.0 04.04.2009
 */

package edu.mgupi.pass.face.gui.forms;

import java.awt.Frame;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;

import edu.mgupi.pass.db.sensors.SensorTypes;
import edu.mgupi.pass.db.sensors.SensorTypesFactory;
import edu.mgupi.pass.face.gui.template.AbstractEditorTableModel;
import edu.mgupi.pass.face.gui.template.CommonEditorTableModel;
import edu.mgupi.pass.face.gui.template.TableViewerTemplate;

public class SensorTypesTable extends TableViewerTemplate<SensorTypes> {

	/**
	 * Default constructor.
	 * 
	 * @param owner
	 * 
	 */
	public SensorTypesTable(Frame owner) {
		super(owner, "defectTypesTable", Messages.getString("SensorTypesTable.title"));
	}

	private CommonEditorTableModel<SensorTypes> tableModel = null;

	private final static String YES = Messages.getString("SensorTypesTable.head.yes");
	private final static String NO = Messages.getString("SensorTypesTable.head.no");

	@Override
	protected CommonEditorTableModel<SensorTypes> getTableModelImpl(JTable owner) {
		if (tableModel == null) {
			tableModel = new CommonEditorTableModel<SensorTypes>(owner, SensorTypesRecord.class) {

				@Override
				protected SensorTypes createInstanceImpl() {
					return SensorTypesFactory.createSensorTypes();
				}

				@Override
				protected String[] getColumns() {
					return new String[] { Messages.getString("SensorTypesTable.head.id"),
							Messages.getString("SensorTypesTable.head.class"),
							Messages.getString("SensorTypesTable.head.name"),
							Messages.getString("SensorTypesTable.head.image") };
				}

				@Override
				protected List<SensorTypes> getDataImpl() throws Exception {
					return Arrays.asList(SensorTypesFactory.listSensorTypesByQuery(null, null));
				}

				@Override
				public Object getValueAt(int rowIndex, int columnIndex) {
					SensorTypes defect = super.getRowAt(rowIndex);
					switch (columnIndex) {
					case 0:
						return defect.getIdSensorType();
					case 1:
						return defect.getSensorClass().getName();
					case 2:
						return defect.getName();
					case 3:
						return defect.getSensorImage() == null ? NO : YES;
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
		model.setColumnWidth(1, 100, 100, 1);

	}

}
