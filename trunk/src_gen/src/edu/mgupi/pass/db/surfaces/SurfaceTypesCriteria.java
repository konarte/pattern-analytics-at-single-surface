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

import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class SurfaceTypesCriteria extends AbstractORMCriteria {
	public final IntegerExpression idSurfaceType;
	public final StringExpression name;
	
	public SurfaceTypesCriteria(Criteria criteria) {
		super(criteria);
		idSurfaceType = new IntegerExpression("idSurfaceType", this);
		name = new StringExpression("name", this);
	}
	
	public SurfaceTypesCriteria(PersistentSession session) {
		this(session.createCriteria(SurfaceTypes.class));
	}
	
	public SurfaceTypesCriteria() throws PersistentException {
		this(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession());
	}
	
	public SurfaceClassesCriteria createSurfaceClassCriteria() {
		return new SurfaceClassesCriteria(createCriteria("surfaceClass"));
	}
	
	public MaterialsCriteria createSurfaceMaterialCriteria() {
		return new MaterialsCriteria(createCriteria("surfaceMaterial"));
	}
	
	public SurfaceTypes uniqueSurfaceTypes() {
		return (SurfaceTypes) super.uniqueResult();
	}
	
	public SurfaceTypes[] listSurfaceTypes() {
		return (SurfaceTypes[]) super.list().toArray(new SurfaceTypes[super.list().size()]);
	}
}

