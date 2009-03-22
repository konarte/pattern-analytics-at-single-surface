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

public class LocusAppliedFiltersCriteria extends AbstractORMCriteria {
	public final IntegerExpression idLocusFilter;
	
	public LocusAppliedFiltersCriteria(Criteria criteria) {
		super(criteria);
		idLocusFilter = new IntegerExpression("idLocusFilter", this);
	}
	
	public LocusAppliedFiltersCriteria(PersistentSession session) {
		this(session.createCriteria(LocusAppliedFilters.class));
	}
	
	public LocusAppliedFiltersCriteria() throws PersistentException {
		this(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession());
	}
	
	public LFiltersCriteria createFilterCriteria() {
		return new LFiltersCriteria(createCriteria("filter"));
	}
	
	public LocusAppliedFilterParamsCriteria createParamsCriteria() {
		return new LocusAppliedFilterParamsCriteria(createCriteria("params"));
	}
	
	public LocusAppliedFilters uniqueLocusAppliedFilters() {
		return (LocusAppliedFilters) super.uniqueResult();
	}
	
	public LocusAppliedFilters[] listLocusAppliedFilters() {
		return (LocusAppliedFilters[]) super.list().toArray(new LocusAppliedFilters[super.list().size()]);
	}
}

