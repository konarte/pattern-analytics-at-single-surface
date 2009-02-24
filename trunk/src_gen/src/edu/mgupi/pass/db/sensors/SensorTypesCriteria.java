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

public class SensorTypesCriteria extends AbstractORMCriteria {
	public final IntegerExpression idSensorType;
	public final StringExpression name;
	public final ByteArrayExpression sensorImage;
	
	public SensorTypesCriteria(Criteria criteria) {
		super(criteria);
		idSensorType = new IntegerExpression("idSensorType", this);
		name = new StringExpression("name", this);
		sensorImage = new ByteArrayExpression("sensorImage", this);
	}
	
	public SensorTypesCriteria(PersistentSession session) {
		this(session.createCriteria(SensorTypes.class));
	}
	
	public SensorTypesCriteria() throws PersistentException {
		this(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession());
	}
	
	public SensorClassesCriteria createSensorClassCriteria() {
		return new SensorClassesCriteria(createCriteria("sensorClass"));
	}
	
	public SensorTypes uniqueSensorTypes() {
		return (SensorTypes) super.uniqueResult();
	}
	
	public SensorTypes[] listSensorTypes() {
		return (SensorTypes[]) super.list().toArray(new SensorTypes[super.list().size()]);
	}
}

