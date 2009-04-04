/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)SensorsRecord.java 1.0 04.04.2009
 */

package edu.mgupi.pass.face.gui.forms;

import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Collection;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.hibernate.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.db.sensors.SensorTypes;
import edu.mgupi.pass.db.sensors.Sensors;
import edu.mgupi.pass.face.gui.template.RecordEditorTemplate;
import edu.mgupi.pass.face.gui.template.RecordFormWithImageTemplate;

public class SensorsRecord extends RecordEditorTemplate<Sensors> {

	private final static Logger logger = LoggerFactory.getLogger(SensorsRecord.class);

	public SensorsRecord(Frame owner) {
		this(owner, "sensorsRecordTable", Messages.getString("SensorsRecord.title"), false);
	}

	public SensorsRecord(Frame owner, String name, String title, boolean readOnly) {
		super(owner, name, title, readOnly);
		super.setFormPanel(getFormPanel());
	}

	@Override
	protected String getDenyDeletionMessage(Object foundObject) {
		return null;
	}

	@Override
	protected Criteria getMultipleDeleteCriteria(Collection<Sensors> objects) throws Exception {
		return null;
	}

	@Override
	protected Criteria getUniqueCheckCriteria(Sensors object, String newValue) throws Exception {
		return null;
	}

	private SensorTypes type;
	private String name;

	@Override
	protected boolean loadFormFromObjectImpl(Sensors object) throws Exception {
		name = object.getName();
		type = object.getSensorType();

		jLabelID.setText(String.valueOf(object.getIdSensor()));
		if (type != null) {
			if (type.getSensorClass() != null) {
				jClass.setValue(type.getSensorClass());
			}
			jType.setValue(type);
		}
		jName.setText(name);

		return true;
	}

	@Override
	protected void restoreObjectImpl(Sensors object) throws Exception {
		object.setSensorType(type);
		object.setName(jName.getText());
	}

	@Override
	protected void putFormToObjectImpl(Sensors object) throws Exception {
		object.setSensorType(jType.getValue());
		object.setName(jName.getText());
	}

	private JLabel jLabelID;
	private JTextField jName;
	private ComboBoxSensorClasses jClass;
	private ComboBoxSensorTypes jType;

	private RecordFormWithImageTemplate formPanel = null;
	private JPanel jPanelPlace = null;

	protected RecordFormWithImageTemplate getFormPanel() {
		if (formPanel == null) {
			formPanel = new RecordFormWithImageTemplate(getJPanelPlace(), Messages
					.getString("SensorsRecord.imageBorder"), true);
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

			jLabelID = new JLabel("0");
			jClass = new ComboBoxSensorClasses();

			jType = new ComboBoxSensorTypes(jClass);
			jType.addActionListener(this);

			jName = new JTextField();

			super.putComponentPair(jPanelPlace, Messages.getString("SensorsRecord.id"), jLabelID);
			super.putComponentPair(jPanelPlace, Messages.getString("SensorsRecord.class"), jClass);
			super.putComponentPair(jPanelPlace, Messages.getString("SensorsRecord.type"), jType);
			super.putUniqueComponentPair(jPanelPlace, Messages.getString("SensorsRecord.name"), jName);

		}
		return jPanelPlace;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == jType) {

			SensorTypes selectedType = jType.getValue();
			if (selectedType != null) {
				try {
					this.formPanel.setImageRaw(selectedType.getSensorImage());
				} catch (IOException e1) {
					logger.error("Error when applying image", e1);
				}
			}
		}
	}

}
