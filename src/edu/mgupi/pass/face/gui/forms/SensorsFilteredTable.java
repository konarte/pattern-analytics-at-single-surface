/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)SensorsFilteredTable.java 1.0 04.04.2009
 */

package edu.mgupi.pass.face.gui.forms;

import java.awt.Frame;
import java.awt.GridBagLayout;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.orm.PersistentException;

import edu.mgupi.pass.db.sensors.Sensors;
import edu.mgupi.pass.db.sensors.SensorsCriteria;
import edu.mgupi.pass.db.sensors.SensorsFactory;
import edu.mgupi.pass.face.gui.AppHelper;
import edu.mgupi.pass.face.gui.template.AbstractEditorTableModel;
import edu.mgupi.pass.face.gui.template.CommonEditorTableModel;
import edu.mgupi.pass.face.gui.template.TableViewerTemplate;
import edu.mgupi.pass.util.IRefreshable;

public class SensorsFilteredTable extends TableViewerTemplate<Sensors> {

	/**
	 * Default constructor.
	 * 
	 * @param owner
	 * 
	 */
	public SensorsFilteredTable(Frame owner) {
		super(owner, "sensorsFilteredTable", Messages.getString("SensorsFilteredTable.title"));
		super.setFiltersPanel(getFiltersPanel());
	}

	public Sensors openWindow() {
		((IRefreshable) jClass).refresh();
		((IRefreshable) jType).refresh();

		super.getDialogAdapter().openDialog();
		return super.selectedObject;
	}

	private CommonEditorTableModel<Sensors> myTableMode = null;

	@Override
	protected CommonEditorTableModel<Sensors> getTableModelImpl(JTable owner) {
		if (myTableMode == null) {
			myTableMode = new CommonEditorTableModel<Sensors>(owner, SensorsRecord.class) {

				@Override
				protected Sensors createInstanceImpl() {
					return SensorsFactory.createSensors();
				}

				@Override
				protected String[] getColumns() {
					return new String[] { Messages.getString("SensorsFilteredTable.head.id"),
							Messages.getString("SensorsFilteredTable.head.type"),
							Messages.getString("SensorsFilteredTable.head.name") };
				}

				private SensorsCriteria lastCriteria = null;

				private SensorsCriteria getCriteria() throws PersistentException {
					if (lastCriteria == null) {
						lastCriteria = new SensorsCriteria();
					}
					return lastCriteria;
				}

				@Override
				protected List<Sensors> getDataImpl() throws Exception {
					lastCriteria = null;

					if (jType.getSelectedItem() != null) {
						getCriteria().createSensorTypeCriteria().idSensorType.eq(jType.getValue()
								.getIdSensorType());
					} else {
						if (jClass.getSelectedItem() != null) {
							getCriteria().createSensorTypeCriteria().createSensorClassCriteria().idSensorClass
									.eq(jClass.getValue().getIdSensorClass());
						}
					}
					if (!jName.getText().isEmpty()) {
						getCriteria().name.like("%" + jName.getText() + "%");
					}

					if (lastCriteria != null) {
						return Arrays.asList(SensorsFactory.listSensorsByCriteria(lastCriteria));
					}

					return Arrays.asList(SensorsFactory.listSensorsByQuery(null, null));
				}

				@Override
				public Object getValueAt(int rowIndex, int columnIndex) {
					Sensors sensor = super.getRowAt(rowIndex);
					switch (columnIndex) {
					case 0:
						return sensor.getIdSensor();
					case 1:
						return sensor.getSensorType().getName();
					case 2:
						return sensor.getName();
					default:
						return columnIndex;
					}
				}
			};
			myTableMode.setAddOnlyMode();
		}
		return myTableMode;
	}

	private ComboBoxSensorClasses jClass;
	private ComboBoxSensorTypes jType;
	private JTextField jName;

	private JPanel panelFilters;

	private JPanel getFiltersPanel() {
		if (panelFilters == null) {
			panelFilters = new JPanel();
			panelFilters.setLayout(new GridBagLayout());

			jClass = new ComboBoxSensorClasses(true);
			jType = new ComboBoxSensorTypes(jClass, true);
			jName = new JTextField(20);

			panelFilters.add(new JLabel(Messages.getString("SensorsRecord.class")), AppHelper
					.getJBCForm(0, 0));
			panelFilters.add(jClass, AppHelper.getJBCForm(1, 0));

			panelFilters.add(new JLabel(Messages.getString("SensorsRecord.type")), AppHelper
					.getJBCForm(0, 1));
			panelFilters.add(jType, AppHelper.getJBCForm(1, 1));

			panelFilters.add(new JLabel(Messages.getString("SensorsRecord.name")), AppHelper
					.getJBCForm(2, 0));
			panelFilters.add(jName, AppHelper.getJBCForm(3, 0));
		}
		return panelFilters;
	}

	@Override
	protected void tablePostInit(JTable owner) {
		AbstractEditorTableModel model = (AbstractEditorTableModel) owner.getModel();
		model.setHorizontalAlignMode(0, JLabel.CENTER);
		model.setColumnWidth(1, 100, 100);

	}

}
