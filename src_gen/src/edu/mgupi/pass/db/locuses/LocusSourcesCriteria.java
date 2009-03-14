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

import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class LocusSourcesCriteria extends AbstractORMCriteria {
	public final IntegerExpression idLocusSource;
	public final StringExpression filename;
	public final ByteArrayExpression sourceImage;
	
	public LocusSourcesCriteria(Criteria criteria) {
		super(criteria);
		idLocusSource = new IntegerExpression("idLocusSource", this);
		filename = new StringExpression("filename", this);
		sourceImage = new ByteArrayExpression("sourceImage", this);
	}
	
	public LocusSourcesCriteria(PersistentSession session) {
		this(session.createCriteria(LocusSources.class));
	}
	
	public LocusSourcesCriteria() throws PersistentException {
		this(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession());
	}
	
	public LocusSources uniqueLocusSources() {
		return (LocusSources) super.uniqueResult();
	}
	
	public LocusSources[] listLocusSources() {
		return (LocusSources[]) super.list().toArray(new LocusSources[super.list().size()]);
	}
}

