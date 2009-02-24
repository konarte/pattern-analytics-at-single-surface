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
package edu.mgupi.pass.db.surfaces;

import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class MaterialsCriteria extends AbstractORMCriteria {
	public final IntegerExpression idSurfaceMaterial;
	public final StringExpression name;
	public final FloatExpression electricalConduction;
	public final FloatExpression magneticConductivity;
	
	public MaterialsCriteria(Criteria criteria) {
		super(criteria);
		idSurfaceMaterial = new IntegerExpression("idSurfaceMaterial", this);
		name = new StringExpression("name", this);
		electricalConduction = new FloatExpression("electricalConduction", this);
		magneticConductivity = new FloatExpression("magneticConductivity", this);
	}
	
	public MaterialsCriteria(PersistentSession session) {
		this(session.createCriteria(Materials.class));
	}
	
	public MaterialsCriteria() throws PersistentException {
		this(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession());
	}
	
	public edu.mgupi.pass.db.sensors.SensorsCriteria createSensorCriteria() {
		return new edu.mgupi.pass.db.sensors.SensorsCriteria(createCriteria("sensor"));
	}
	
	public Materials uniqueMaterials() {
		return (Materials) super.uniqueResult();
	}
	
	public Materials[] listMaterials() {
		return (Materials[]) super.list().toArray(new Materials[super.list().size()]);
	}
}

