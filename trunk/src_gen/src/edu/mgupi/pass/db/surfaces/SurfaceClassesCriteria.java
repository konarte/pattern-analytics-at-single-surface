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

public class SurfaceClassesCriteria extends AbstractORMCriteria {
	public final IntegerExpression idSurfaceType;
	public final StringExpression name;
	public final ByteArrayExpression surfaceImage;
	
	public SurfaceClassesCriteria(Criteria criteria) {
		super(criteria);
		idSurfaceType = new IntegerExpression("idSurfaceType", this);
		name = new StringExpression("name", this);
		surfaceImage = new ByteArrayExpression("surfaceImage", this);
	}
	
	public SurfaceClassesCriteria(PersistentSession session) {
		this(session.createCriteria(SurfaceClasses.class));
	}
	
	public SurfaceClassesCriteria() throws PersistentException {
		this(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession());
	}
	
	public SurfaceClasses uniqueSurfaceClasses() {
		return (SurfaceClasses) super.uniqueResult();
	}
	
	public SurfaceClasses[] listSurfaceClasses() {
		return (SurfaceClasses[]) super.list().toArray(new SurfaceClasses[super.list().size()]);
	}
}

