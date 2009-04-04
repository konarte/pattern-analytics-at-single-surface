/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)ComboBoxSensorClasses.java 1.0 04.04.2009
 */

package edu.mgupi.pass.face.gui.forms;

import org.orm.PersistentException;

import edu.mgupi.pass.db.sensors.SensorClasses;
import edu.mgupi.pass.db.sensors.SensorClassesFactory;

public class ComboBoxSensorClasses extends AbstractComboBox<SensorClasses> {

	public ComboBoxSensorClasses() {
		this(false);
	}

	public ComboBoxSensorClasses(boolean canBeEmpty) {
		super(canBeEmpty);
	}

	@Override
	protected SensorClasses[] getRowsImpl() throws PersistentException {
		return SensorClassesFactory.listSensorClassesByQuery(null, null);
	}

	@Override
	protected boolean objectEqualsImpl(SensorClasses first, SensorClasses second) {
		return first.getIdSensorClass() == second.getIdSensorClass();
	}

	@Override
	protected String getRenderedValue(SensorClasses value) {
		return value.getName();
	}
}
