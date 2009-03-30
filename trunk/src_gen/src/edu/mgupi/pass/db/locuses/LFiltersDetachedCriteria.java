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

public class LFiltersDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression idLFilter;
	public final StringExpression name;
	public final StringExpression codename;
	public final BooleanExpression serviceFilter;
	
	public LFiltersDetachedCriteria() {
		super(edu.mgupi.pass.db.locuses.LFilters.class, edu.mgupi.pass.db.locuses.LFiltersCriteria.class);
		idLFilter = new IntegerExpression("idLFilter", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		codename = new StringExpression("codename", this.getDetachedCriteria());
		serviceFilter = new BooleanExpression("serviceFilter", this.getDetachedCriteria());
	}
	
	public LFiltersDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, edu.mgupi.pass.db.locuses.LFiltersCriteria.class);
		idLFilter = new IntegerExpression("idLFilter", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		codename = new StringExpression("codename", this.getDetachedCriteria());
		serviceFilter = new BooleanExpression("serviceFilter", this.getDetachedCriteria());
	}
	
	public LFilters uniqueLFilters(PersistentSession session) {
		return (LFilters) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public LFilters[] listLFilters(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (LFilters[]) list.toArray(new LFilters[list.size()]);
	}
}

