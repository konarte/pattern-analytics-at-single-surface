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

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class SurfaceTypesDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression idSurfaceMode;
	public final StringExpression name;
	
	public SurfaceTypesDetachedCriteria() {
		super(edu.mgupi.pass.db.surfaces.SurfaceTypes.class, edu.mgupi.pass.db.surfaces.SurfaceTypesCriteria.class);
		idSurfaceMode = new IntegerExpression("idSurfaceMode", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
	}
	
	public SurfaceTypesDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, edu.mgupi.pass.db.surfaces.SurfaceTypesCriteria.class);
		idSurfaceMode = new IntegerExpression("idSurfaceMode", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
	}
	
	public SurfaceClassesDetachedCriteria createSurfaceClassCriteria() {
		return new SurfaceClassesDetachedCriteria(createCriteria("surfaceClass"));
	}
	
	public MaterialsDetachedCriteria createSurfaceMaterialCriteria() {
		return new MaterialsDetachedCriteria(createCriteria("surfaceMaterial"));
	}
	
	public SurfaceTypes uniqueSurfaceTypes(PersistentSession session) {
		return (SurfaceTypes) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public SurfaceTypes[] listSurfaceTypes(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (SurfaceTypes[]) list.toArray(new SurfaceTypes[list.size()]);
	}
}

