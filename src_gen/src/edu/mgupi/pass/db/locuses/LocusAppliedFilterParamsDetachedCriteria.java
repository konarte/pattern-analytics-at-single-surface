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

public class LocusAppliedFilterParamsDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression ID;
	public final StringExpression name;
	public final StringExpression value;
	
	public LocusAppliedFilterParamsDetachedCriteria() {
		super(edu.mgupi.pass.db.locuses.LocusAppliedFilterParams.class, edu.mgupi.pass.db.locuses.LocusAppliedFilterParamsCriteria.class);
		ID = new IntegerExpression("ID", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		value = new StringExpression("value", this.getDetachedCriteria());
	}
	
	public LocusAppliedFilterParamsDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, edu.mgupi.pass.db.locuses.LocusAppliedFilterParamsCriteria.class);
		ID = new IntegerExpression("ID", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		value = new StringExpression("value", this.getDetachedCriteria());
	}
	
	public LocusAppliedFilterParams uniqueLocusAppliedFilterParams(PersistentSession session) {
		return (LocusAppliedFilterParams) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public LocusAppliedFilterParams[] listLocusAppliedFilterParams(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (LocusAppliedFilterParams[]) list.toArray(new LocusAppliedFilterParams[list.size()]);
	}
}

