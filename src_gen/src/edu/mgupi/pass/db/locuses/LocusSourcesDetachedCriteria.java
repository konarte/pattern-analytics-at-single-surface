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
package edu.mgupi.pass.db.locuses;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class LocusSourcesDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression idLocusSource;
	public final StringExpression filename;
	public final ByteArrayExpression sourceImage;
	
	public LocusSourcesDetachedCriteria() {
		super(edu.mgupi.pass.db.locuses.LocusSources.class, edu.mgupi.pass.db.locuses.LocusSourcesCriteria.class);
		idLocusSource = new IntegerExpression("idLocusSource", this.getDetachedCriteria());
		filename = new StringExpression("filename", this.getDetachedCriteria());
		sourceImage = new ByteArrayExpression("sourceImage", this.getDetachedCriteria());
	}
	
	public LocusSourcesDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, edu.mgupi.pass.db.locuses.LocusSourcesCriteria.class);
		idLocusSource = new IntegerExpression("idLocusSource", this.getDetachedCriteria());
		filename = new StringExpression("filename", this.getDetachedCriteria());
		sourceImage = new ByteArrayExpression("sourceImage", this.getDetachedCriteria());
	}
	
	public LocusSources uniqueLocusSources(PersistentSession session) {
		return (LocusSources) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public LocusSources[] listLocusSources(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (LocusSources[]) list.toArray(new LocusSources[list.size()]);
	}
}

