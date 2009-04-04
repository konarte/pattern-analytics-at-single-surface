/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)SensorTypesRecord.java 1.0 04.04.2009
 */

package edu.mgupi.pass.face.gui.forms;

import java.awt.Frame;
import java.awt.GridBagLayout;
import java.util.Collection;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.hibernate.Criteria;

import edu.mgupi.pass.db.sensors.SensorClasses;
import edu.mgupi.pass.db.sensors.SensorTypes;
import edu.mgupi.pass.db.sensors.SensorTypesCriteria;
import edu.mgupi.pass.db.sensors.Sensors;
import edu.mgupi.pass.db.sensors.SensorsCriteria;
import edu.mgupi.pass.face.gui.template.RecordEditorTemplate;
import edu.mgupi.pass.face.gui.template.RecordFormWithImageTemplate;

public class SensorTypesRecord extends RecordEditorTemplate<SensorTypes> {

	public SensorTypesRecord(Frame owner) {
		super(owner, "defectTypesRecordDialog", Messages.getString("SensorTypesRecord.title"));
		super.setFormPanel(getFormPanel());
	}

	@Override
	protected String getDenyDeletionMessage(Object foundObject) {
		Sensors type = (Sensors) foundObject;
		return Messages.getString("SensorTypesRecord.exists", type.getName(), type.getSensorType()
				.getName());
	}

	@Override
	protected Criteria getMultipleDeleteCriteria(Collection<SensorTypes> objects) throws Exception {
		SensorsCriteria criteria = new SensorsCriteria();
		int[] in = new int[objects.size()];
		int idx = 0;
		for (SensorTypes def : objects) {
			in[idx++] = def.getIdSensorType();
		}
		criteria.createSensorTypeCriteria().idSensorType.in(in);
		return criteria;
	}

	@Override
	protected Criteria getUniqueCheckCriteria(SensorTypes object, String newValue) throws Exception {
		SensorTypesCriteria criteria = new SensorTypesCriteria();
		criteria.name.eq(newValue);
		if (object.getIdSensorType() != 0) {
			criteria.idSensorType.ne(object.getIdSensorType());
		}
		return criteria;
	}

	private String name = null;
	private byte[] image = null;
	private SensorClasses sensorClass = null;

	@Override
	protected boolean loadFormFromObjectImpl(SensorTypes object) throws Exception {
		this.name = object.getName();
		this.sensorClass = object.getSensorClass();
		this.image = object.getSensorImage();

		jLabelIDValue.setText(String.valueOf(object.getIdSensorType()));
		jName.setText(name);

		jClass.setValue(sensorClass);

		formPanel.setImageRaw(image);

		return true;
	}

	@Override
	protected void restoreObjectImpl(SensorTypes object) throws Exception {
		object.setName(this.name);
		object.setSensorImage(this.image);
		object.setSensorClass(this.sensorClass);
	}

	@Override
	protected void putFormToObjectImpl(SensorTypes object) throws Exception {
		object.setName(jName.getText());
		object.setSensorClass(jClass.getValue());
		object.setSensorImage(formPanel.getRawImage());
	}

	private JPanel jPanelPlace = null;
	private JLabel jLabelIDValue = null;
	private JTextField jName = null;
	private ComboBoxSensorClasses jClass = null;

	private RecordFormWithImageTemplate formPanel = null;

	protected RecordFormWithImageTemplate getFormPanel() {
		if (formPanel == null) {
			formPanel = new RecordFormWithImageTemplate(getJPanelPlace());
		}
		return formPanel;
	}

	/**
	 * This method initializes jPanelPlace
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelPlace() {
		if (jPanelPlace == null) {

			jPanelPlace = new JPanel();

			jPanelPlace.setLayout(new GridBagLayout());

			jLabelIDValue = new JLabel("0");
			jName = new JTextField();
			jClass = new ComboBoxSensorClasses();

			super.putComponentPair(jPanelPlace, Messages.getString("SensorTypesRecord.id"),
					jLabelIDValue);
			super.putComponentPair(jPanelPlace, Messages.getString("SensorTypesRecord.class"),
					jClass);
			super.putUniqueComponentPair(jPanelPlace, Messages.getString("SensorTypesRecord.name"),
					jName);
		}
		return jPanelPlace;
	}
} //  @jve:decl-index=0:visual-constraint="16,-1"
