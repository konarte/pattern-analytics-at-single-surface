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

public class SensorsDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression idSensor;
	
	public SensorsDetachedCriteria() {
		super(edu.mgupi.pass.db.sensors.Sensors.class, edu.mgupi.pass.db.sensors.SensorsCriteria.class);
		idSensor = new IntegerExpression("idSensor", this.getDetachedCriteria());
	}
	
	public SensorsDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, edu.mgupi.pass.db.sensors.SensorsCriteria.class);
		idSensor = new IntegerExpression("idSensor", this.getDetachedCriteria());
	}
	
	public SensorTypesDetachedCriteria createSensorTypeCriteria() {
		return new SensorTypesDetachedCriteria(createCriteria("sensorType"));
	}
	
	public edu.mgupi.pass.db.surfaces.MaterialsDetachedCriteria createMpathMaterialCriteria() {
		return new edu.mgupi.pass.db.surfaces.MaterialsDetachedCriteria(createCriteria("mpathMaterial"));
	}
	
	public Sensors uniqueSensors(PersistentSession session) {
		return (Sensors) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public Sensors[] listSensors(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (Sensors[]) list.toArray(new Sensors[list.size()]);
	}
}

