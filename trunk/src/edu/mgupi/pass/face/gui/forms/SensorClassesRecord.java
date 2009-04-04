/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)SensorClassesRecord.java 1.0 04.04.2009
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
import edu.mgupi.pass.db.sensors.SensorClassesCriteria;
import edu.mgupi.pass.db.sensors.SensorTypes;
import edu.mgupi.pass.db.sensors.SensorTypesCriteria;
import edu.mgupi.pass.face.gui.template.RecordEditorTemplate;

public class SensorClassesRecord extends RecordEditorTemplate<SensorClasses> {

	public SensorClassesRecord(Frame owner) {
		super(owner, "sensorClassesRecordDialog", Messages.getString("SensorClassesTable.title"));
		super.setFormPanel(getFormPanel());
	}

	@Override
	protected String getDenyDeletionMessage(Object foundObject) {
		SensorTypes type = (SensorTypes) foundObject;
		return Messages.getString("SensorClassesTable.exists", type.getName(), type
				.getSensorClass().getName());
	}

	@Override
	protected Criteria getMultipleDeleteCriteria(Collection<SensorClasses> objects)
			throws Exception {
		SensorTypesCriteria criteria = new SensorTypesCriteria();
		int[] in = new int[objects.size()];
		int idx = 0;
		for (SensorClasses def : objects) {
			in[idx++] = def.getIdSensorClass();
		}
		criteria.createSensorClassCriteria().idSensorClass.in(in);
		return criteria;
	}

	@Override
	protected Criteria getUniqueCheckCriteria(SensorClasses object, String newValue)
			throws Exception {
		SensorClassesCriteria criteria = new SensorClassesCriteria();
		criteria.name.eq(newValue);
		if (object.getIdSensorClass() != 0) {
			criteria.idSensorClass.ne(object.getIdSensorClass());
		}
		return criteria;
	}

	private String sensorClass = null;

	@Override
	protected boolean loadFormFromObjectImpl(SensorClasses object) throws Exception {
		this.sensorClass = object.getName();

		jLabelIDValue.setText(String.valueOf(object.getIdSensorClass()));
		jTextFieldNameValue.setText(object.getName());

		return true;
	}

	@Override
	protected void restoreObjectImpl(SensorClasses object) throws Exception {
		object.setName(sensorClass);
	}

	@Override
	protected void putFormToObjectImpl(SensorClasses object) throws Exception {
		object.setName(jTextFieldNameValue.getText());
	}

	private JPanel jPanelPlace = null;
	private JLabel jLabelIDValue = null;
	private JTextField jTextFieldNameValue = null;

	private JPanel formPanel = null;

	protected JPanel getFormPanel() {
		if (formPanel == null) {
			formPanel = super.createDefaultFormPanel(getJPanelPlace());
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
			jTextFieldNameValue = new JTextField();

			super.putComponentPair(jPanelPlace, Messages.getString("SensorClassesTable.id"),
					jLabelIDValue);
			super.putUniqueComponentPair(jPanelPlace,
					Messages.getString("SensorClassesTable.name"), jTextFieldNameValue);
		}
		return jPanelPlace;
	}

}
