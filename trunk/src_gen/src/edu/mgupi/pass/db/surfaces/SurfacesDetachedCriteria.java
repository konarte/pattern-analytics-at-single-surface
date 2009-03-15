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

public class SurfacesDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression idSurface;
	public final FloatExpression height;
	public final FloatExpression width;
	public final FloatExpression length;
	public final BooleanExpression multiLayer;
	
	public SurfacesDetachedCriteria() {
		super(edu.mgupi.pass.db.surfaces.Surfaces.class, edu.mgupi.pass.db.surfaces.SurfacesCriteria.class);
		idSurface = new IntegerExpression("idSurface", this.getDetachedCriteria());
		height = new FloatExpression("height", this.getDetachedCriteria());
		width = new FloatExpression("width", this.getDetachedCriteria());
		length = new FloatExpression("length", this.getDetachedCriteria());
		multiLayer = new BooleanExpression("multiLayer", this.getDetachedCriteria());
	}
	
	public SurfacesDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, edu.mgupi.pass.db.surfaces.SurfacesCriteria.class);
		idSurface = new IntegerExpression("idSurface", this.getDetachedCriteria());
		height = new FloatExpression("height", this.getDetachedCriteria());
		width = new FloatExpression("width", this.getDetachedCriteria());
		length = new FloatExpression("length", this.getDetachedCriteria());
		multiLayer = new BooleanExpression("multiLayer", this.getDetachedCriteria());
	}
	
	public SurfaceTypesDetachedCriteria createSurfaceModeCriteria() {
		return new SurfaceTypesDetachedCriteria(createCriteria("surfaceMode"));
	}
	
	public Surfaces uniqueSurfaces(PersistentSession session) {
		return (Surfaces) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public Surfaces[] listSurfaces(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (Surfaces[]) list.toArray(new Surfaces[list.size()]);
	}
}

