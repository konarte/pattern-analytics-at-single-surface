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

import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class LocusFiltersCriteria extends AbstractORMCriteria {
	public final IntegerExpression idLocusFilter;
	public final StringExpression options;
	
	public LocusFiltersCriteria(Criteria criteria) {
		super(criteria);
		idLocusFilter = new IntegerExpression("idLocusFilter", this);
		options = new StringExpression("options", this);
	}
	
	public LocusFiltersCriteria(PersistentSession session) {
		this(session.createCriteria(LocusFilters.class));
	}
	
	public LocusFiltersCriteria() throws PersistentException {
		this(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession());
	}
	
	public LFiltersCriteria createFilterCriteria() {
		return new LFiltersCriteria(createCriteria("filter"));
	}
	
	public LocusFilters uniqueLocusFilters() {
		return (LocusFilters) super.uniqueResult();
	}
	
	public LocusFilters[] listLocusFilters() {
		return (LocusFilters[]) super.list().toArray(new LocusFilters[super.list().size()]);
	}
}

