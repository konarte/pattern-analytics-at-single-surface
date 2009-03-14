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

public class LocusFilterOptionsDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression idLocusFilter;
	public final StringExpression options;
	
	public LocusFilterOptionsDetachedCriteria() {
		super(edu.mgupi.pass.db.locuses.LocusFilterOptions.class, edu.mgupi.pass.db.locuses.LocusFilterOptionsCriteria.class);
		idLocusFilter = new IntegerExpression("idLocusFilter", this.getDetachedCriteria());
		options = new StringExpression("options", this.getDetachedCriteria());
	}
	
	public LocusFilterOptionsDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, edu.mgupi.pass.db.locuses.LocusFilterOptionsCriteria.class);
		idLocusFilter = new IntegerExpression("idLocusFilter", this.getDetachedCriteria());
		options = new StringExpression("options", this.getDetachedCriteria());
	}
	
	public LFiltersDetachedCriteria createFilterCriteria() {
		return new LFiltersDetachedCriteria(createCriteria("filter"));
	}
	
	public LocusFilterOptions uniqueLocusFilterOptions(PersistentSession session) {
		return (LocusFilterOptions) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public LocusFilterOptions[] listLocusFilterOptions(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (LocusFilterOptions[]) list.toArray(new LocusFilterOptions[list.size()]);
	}
}

