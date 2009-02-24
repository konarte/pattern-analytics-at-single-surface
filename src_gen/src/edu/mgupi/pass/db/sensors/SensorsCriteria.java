/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: Raidan Flk
 * License Type: Evaluation
 */
package edu.mgupi.pass.db.sensors;

import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class SensorsCriteria extends AbstractORMCriteria {
	public final IntegerExpression idSensor;
	
	public SensorsCriteria(Criteria criteria) {
		super(criteria);
		idSensor = new IntegerExpression("idSensor", this);
	}
	
	public SensorsCriteria(PersistentSession session) {
		this(session.createCriteria(Sensors.class));
	}
	
	public SensorsCriteria() throws PersistentException {
		this(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession());
	}
	
	public SensorTypesCriteria createSensorTypeCriteria() {
		return new SensorTypesCriteria(createCriteria("sensorType"));
	}
	
	public edu.mgupi.pass.db.surfaces.MaterialsCriteria createMpathMaterialCriteria() {
		return new edu.mgupi.pass.db.surfaces.MaterialsCriteria(createCriteria("mpathMaterial"));
	}
	
	public Sensors uniqueSensors() {
		return (Sensors) super.uniqueResult();
	}
	
	public Sensors[] listSensors() {
		return (Sensors[]) super.list().toArray(new Sensors[super.list().size()]);
	}
}

