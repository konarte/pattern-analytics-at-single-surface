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
package edu.mgupi.pass.db.locuses;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class LocusFiltersDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression idLocusFilter;
	public final StringExpression options;
	
	public LocusFiltersDetachedCriteria() {
		super(edu.mgupi.pass.db.locuses.LocusFilters.class, edu.mgupi.pass.db.locuses.LocusFiltersCriteria.class);
		idLocusFilter = new IntegerExpression("idLocusFilter", this.getDetachedCriteria());
		options = new StringExpression("options", this.getDetachedCriteria());
	}
	
	public LocusFiltersDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, edu.mgupi.pass.db.locuses.LocusFiltersCriteria.class);
		idLocusFilter = new IntegerExpression("idLocusFilter", this.getDetachedCriteria());
		options = new StringExpression("options", this.getDetachedCriteria());
	}
	
	public LFiltersDetachedCriteria createFilterCriteria() {
		return new LFiltersDetachedCriteria(createCriteria("filter"));
	}
	
	public LocusFilters uniqueLocusFilters(PersistentSession session) {
		return (LocusFilters) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public LocusFilters[] listLocusFilters(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (LocusFilters[]) list.toArray(new LocusFilters[list.size()]);
	}
}

