/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: Anonymous
 * License Type: Purchased
 */
package edu.mgupi.pass.db.sensors;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class SensorClassesDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression idSensorClass;
	public final StringExpression name;
	
	public SensorClassesDetachedCriteria() {
		super(edu.mgupi.pass.db.sensors.SensorClasses.class, edu.mgupi.pass.db.sensors.SensorClassesCriteria.class);
		idSensorClass = new IntegerExpression("idSensorClass", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
	}
	
	public SensorClassesDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, edu.mgupi.pass.db.sensors.SensorClassesCriteria.class);
		idSensorClass = new IntegerExpression("idSensorClass", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
	}
	
	public SensorClasses uniqueSensorClasses(PersistentSession session) {
		return (SensorClasses) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public SensorClasses[] listSensorClasses(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (SensorClasses[]) list.toArray(new SensorClasses[list.size()]);
	}
}

