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

public class SensorClassesCriteria extends AbstractORMCriteria {
	public final IntegerExpression idSensorClass;
	public final StringExpression name;
	
	public SensorClassesCriteria(Criteria criteria) {
		super(criteria);
		idSensorClass = new IntegerExpression("idSensorClass", this);
		name = new StringExpression("name", this);
	}
	
	public SensorClassesCriteria(PersistentSession session) {
		this(session.createCriteria(SensorClasses.class));
	}
	
	public SensorClassesCriteria() throws PersistentException {
		this(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession());
	}
	
	public SensorClasses uniqueSensorClasses() {
		return (SensorClasses) super.uniqueResult();
	}
	
	public SensorClasses[] listSensorClasses() {
		return (SensorClasses[]) super.list().toArray(new SensorClasses[super.list().size()]);
	}
}

