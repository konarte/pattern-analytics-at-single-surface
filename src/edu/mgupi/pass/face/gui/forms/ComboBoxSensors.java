/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)ComboBoxSensors.java 1.0 04.04.2009
 */

package edu.mgupi.pass.face.gui.forms;

import org.orm.PersistentException;

import edu.mgupi.pass.db.sensors.Sensors;

public class ComboBoxSensors extends AbstractComboBox<Sensors> {

	public ComboBoxSensors() {
		super(true);
	}

	@Override
	protected String getRenderedValue(Sensors value) {
		return value.getName();
	}

	@Override
	protected Sensors[] getRowsImpl() throws PersistentException {
		return null;
	}

	@Override
	protected boolean objectEqualsImpl(Sensors first, Sensors second) {
		return first.getIdSensor() == second.getIdSensor();
	}

}
