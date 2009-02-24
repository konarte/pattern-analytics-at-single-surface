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

public class SurfaceClassesDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression idSurfaceType;
	public final StringExpression name;
	public final ByteArrayExpression surfaceImage;
	
	public SurfaceClassesDetachedCriteria() {
		super(edu.mgupi.pass.db.surfaces.SurfaceClasses.class, edu.mgupi.pass.db.surfaces.SurfaceClassesCriteria.class);
		idSurfaceType = new IntegerExpression("idSurfaceType", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		surfaceImage = new ByteArrayExpression("surfaceImage", this.getDetachedCriteria());
	}
	
	public SurfaceClassesDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, edu.mgupi.pass.db.surfaces.SurfaceClassesCriteria.class);
		idSurfaceType = new IntegerExpression("idSurfaceType", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		surfaceImage = new ByteArrayExpression("surfaceImage", this.getDetachedCriteria());
	}
	
	public SurfaceClasses uniqueSurfaceClasses(PersistentSession session) {
		return (SurfaceClasses) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public SurfaceClasses[] listSurfaceClasses(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (SurfaceClasses[]) list.toArray(new SurfaceClasses[list.size()]);
	}
}

