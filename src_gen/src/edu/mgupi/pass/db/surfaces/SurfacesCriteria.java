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

public class SurfacesCriteria extends AbstractORMCriteria {
	public final IntegerExpression idSurface;
	public final FloatExpression height;
	public final FloatExpression width;
	public final FloatExpression length;
	public final BooleanExpression multiLayer;
	
	public SurfacesCriteria(Criteria criteria) {
		super(criteria);
		idSurface = new IntegerExpression("idSurface", this);
		height = new FloatExpression("height", this);
		width = new FloatExpression("width", this);
		length = new FloatExpression("length", this);
		multiLayer = new BooleanExpression("multiLayer", this);
	}
	
	public SurfacesCriteria(PersistentSession session) {
		this(session.createCriteria(Surfaces.class));
	}
	
	public SurfacesCriteria() throws PersistentException {
		this(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession());
	}
	
	public SurfaceTypesCriteria createSurfaceTypeCriteria() {
		return new SurfaceTypesCriteria(createCriteria("surfaceType"));
	}
	
	public Surfaces uniqueSurfaces() {
		return (Surfaces) super.uniqueResult();
	}
	
	public Surfaces[] listSurfaces() {
		return (Surfaces[]) super.list().toArray(new Surfaces[super.list().size()]);
	}
}

