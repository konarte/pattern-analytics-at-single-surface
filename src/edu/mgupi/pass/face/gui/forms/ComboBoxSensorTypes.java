/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)ComboBoxSensorTypes.java 1.0 04.04.2009
 */

package edu.mgupi.pass.face.gui.forms;

import org.orm.PersistentException;

import edu.mgupi.pass.db.sensors.SensorClasses;
import edu.mgupi.pass.db.sensors.SensorTypes;
import edu.mgupi.pass.db.sensors.SensorTypesFactory;

public class ComboBoxSensorTypes extends AbstractDependableComboBox<SensorTypes, SensorClasses> {
	
	public ComboBoxSensorTypes(ComboBoxSensorClasses parent) {
		this(parent, false);
	}

	public ComboBoxSensorTypes(ComboBoxSensorClasses parent, boolean canBeEmpty) {
		super(parent, canBeEmpty);
	}

	@Override
	protected boolean acceptFilter(SensorTypes item, SensorClasses filter) {
		return item.getSensorClass() == null
				|| item.getSensorClass().getIdSensorClass() == filter.getIdSensorClass();
	}

	@Override
	protected SensorTypes[] getRowsImpl() throws PersistentException {
		return SensorTypesFactory.listSensorTypesByQuery(null, null);
	}

	@Override
	protected boolean objectEqualsImpl(SensorTypes first, SensorTypes second) {
		return first.getIdSensorType() == second.getIdSensorType();
	}

	@Override
	protected String getRenderedValue(SensorTypes value) {
		return value.getName();
	}

}
