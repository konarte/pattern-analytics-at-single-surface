/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)SensorClassesTable.java 1.0 04.04.2009
 */

package edu.mgupi.pass.face.gui.forms;

import java.awt.Frame;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;

import edu.mgupi.pass.db.sensors.SensorClasses;
import edu.mgupi.pass.db.sensors.SensorClassesFactory;
import edu.mgupi.pass.face.gui.template.AbstractEditorTableModel;
import edu.mgupi.pass.face.gui.template.CommonEditorTableModel;
import edu.mgupi.pass.face.gui.template.TableViewerTemplate;

public class SensorClassesTable extends TableViewerTemplate<SensorClasses> {

	/**
	 * Default constructor.
	 * 
	 * @param owner
	 */
	public SensorClassesTable(Frame owner) {
		super(owner, "sensorClassesTable", Messages.getString("SensorClassesTable.title"));
	}

	private CommonEditorTableModel<SensorClasses> tableModel = null;

	@Override
	protected CommonEditorTableModel<SensorClasses> getTableModelImpl(JTable owner) {
		if (tableModel == null) {
			tableModel = new CommonEditorTableModel<SensorClasses>(owner, SensorClassesRecord.class) {

				@Override
				protected SensorClasses createInstanceImpl() {
					return SensorClassesFactory.createSensorClasses();
				}

				@Override
				protected String[] getColumns() {
					return new String[] { Messages.getString("SensorClassesTable.head.id"),
							Messages.getString("SensorClassesTable.head.name") };
				}

				@Override
				protected List<SensorClasses> getDataImpl() throws Exception {
					return Arrays.asList(SensorClassesFactory.listSensorClassesByQuery(null, null));
				}

				@Override
				public Object getValueAt(int rowIndex, int columnIndex) {
					SensorClasses defect = super.getRowAt(rowIndex);
					return columnIndex == 0 ? defect.getIdSensorClass() : defect.getName();
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
