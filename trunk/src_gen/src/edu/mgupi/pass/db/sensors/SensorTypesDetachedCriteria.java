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

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class SensorTypesDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression idSensorType;
	public final StringExpression name;
	public final ByteArrayExpression sensorImage;
	
	public SensorTypesDetachedCriteria() {
		super(edu.mgupi.pass.db.sensors.SensorTypes.class, edu.mgupi.pass.db.sensors.SensorTypesCriteria.class);
		idSensorType = new IntegerExpression("idSensorType", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		sensorImage = new ByteArrayExpression("sensorImage", this.getDetachedCriteria());
	}
	
	public SensorTypesDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, edu.mgupi.pass.db.sensors.SensorTypesCriteria.class);
		idSensorType = new IntegerExpression("idSensorType", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		sensorImage = new ByteArrayExpression("sensorImage", this.getDetachedCriteria());
	}
	
	public SensorClassesDetachedCriteria createSensorClassCriteria() {
		return new SensorClassesDetachedCriteria(createCriteria("sensorClass"));
	}
	
	public SensorTypes uniqueSensorTypes(PersistentSession session) {
		return (SensorTypes) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public SensorTypes[] listSensorTypes(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (SensorTypes[]) list.toArray(new SensorTypes[list.size()]);
	}
}

