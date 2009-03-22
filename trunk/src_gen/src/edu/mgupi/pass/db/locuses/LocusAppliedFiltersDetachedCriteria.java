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

public class LocusAppliedFiltersDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression idLocusFilter;
	
	public LocusAppliedFiltersDetachedCriteria() {
		super(edu.mgupi.pass.db.locuses.LocusAppliedFilters.class, edu.mgupi.pass.db.locuses.LocusAppliedFiltersCriteria.class);
		idLocusFilter = new IntegerExpression("idLocusFilter", this.getDetachedCriteria());
	}
	
	public LocusAppliedFiltersDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, edu.mgupi.pass.db.locuses.LocusAppliedFiltersCriteria.class);
		idLocusFilter = new IntegerExpression("idLocusFilter", this.getDetachedCriteria());
	}
	
	public LFiltersDetachedCriteria createFilterCriteria() {
		return new LFiltersDetachedCriteria(createCriteria("filter"));
	}
	
	public LocusAppliedFilterParamsDetachedCriteria createParamsCriteria() {
		return new LocusAppliedFilterParamsDetachedCriteria(createCriteria("params"));
	}
	
	public LocusAppliedFilters uniqueLocusAppliedFilters(PersistentSession session) {
		return (LocusAppliedFilters) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public LocusAppliedFilters[] listLocusAppliedFilters(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (LocusAppliedFilters[]) list.toArray(new LocusAppliedFilters[list.size()]);
	}
}

