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
package edu.mgupi.pass.db.surfaces;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class MaterialsDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression idSurfaceMaterial;
	public final StringExpression name;
	public final FloatExpression electricalConduction;
	public final FloatExpression magneticConductivity;
	
	public MaterialsDetachedCriteria() {
		super(edu.mgupi.pass.db.surfaces.Materials.class, edu.mgupi.pass.db.surfaces.MaterialsCriteria.class);
		idSurfaceMaterial = new IntegerExpression("idSurfaceMaterial", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		electricalConduction = new FloatExpression("electricalConduction", this.getDetachedCriteria());
		magneticConductivity = new FloatExpression("magneticConductivity", this.getDetachedCriteria());
	}
	
	public MaterialsDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, edu.mgupi.pass.db.surfaces.MaterialsCriteria.class);
		idSurfaceMaterial = new IntegerExpression("idSurfaceMaterial", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		electricalConduction = new FloatExpression("electricalConduction", this.getDetachedCriteria());
		magneticConductivity = new FloatExpression("magneticConductivity", this.getDetachedCriteria());
	}
	
	public Materials uniqueMaterials(PersistentSession session) {
		return (Materials) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public Materials[] listMaterials(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (Materials[]) list.toArray(new Materials[list.size()]);
	}
}

