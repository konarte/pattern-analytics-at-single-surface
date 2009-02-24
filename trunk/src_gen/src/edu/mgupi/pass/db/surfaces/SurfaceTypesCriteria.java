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

public class SurfaceTypesCriteria extends AbstractORMCriteria {
	public final IntegerExpression idSurfaceMode;
	public final StringExpression name;
	
	public SurfaceTypesCriteria(Criteria criteria) {
		super(criteria);
		idSurfaceMode = new IntegerExpression("idSurfaceMode", this);
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

