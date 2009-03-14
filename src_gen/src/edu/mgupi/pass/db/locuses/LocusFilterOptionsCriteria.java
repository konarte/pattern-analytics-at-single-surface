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

public class LocusFilterOptionsCriteria extends AbstractORMCriteria {
	public final IntegerExpression idLocusFilter;
	public final StringExpression options;
	
	public LocusFilterOptionsCriteria(Criteria criteria) {
		super(criteria);
		idLocusFilter = new IntegerExpression("idLocusFilter", this);
		options = new StringExpression("options", this);
	}
	
	public LocusFilterOptionsCriteria(PersistentSession session) {
		this(session.createCriteria(LocusFilterOptions.class));
	}
	
	public LocusFilterOptionsCriteria() throws PersistentException {
		this(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession());
	}
	
	public LFiltersCriteria createFilterCriteria() {
		return new LFiltersCriteria(createCriteria("filter"));
	}
	
	public LocusFilterOptions uniqueLocusFilterOptions() {
		return (LocusFilterOptions) super.uniqueResult();
	}
	
	public LocusFilterOptions[] listLocusFilterOptions() {
		return (LocusFilterOptions[]) super.list().toArray(new LocusFilterOptions[super.list().size()]);
	}
}

