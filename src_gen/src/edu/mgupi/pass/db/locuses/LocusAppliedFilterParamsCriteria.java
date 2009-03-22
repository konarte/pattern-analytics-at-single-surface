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

public class LocusAppliedFilterParamsCriteria extends AbstractORMCriteria {
	public final IntegerExpression ID;
	public final StringExpression name;
	public final StringExpression value;
	
	public LocusAppliedFilterParamsCriteria(Criteria criteria) {
		super(criteria);
		ID = new IntegerExpression("ID", this);
		name = new StringExpression("name", this);
		value = new StringExpression("value", this);
	}
	
	public LocusAppliedFilterParamsCriteria(PersistentSession session) {
		this(session.createCriteria(LocusAppliedFilterParams.class));
	}
	
	public LocusAppliedFilterParamsCriteria() throws PersistentException {
		this(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession());
	}
	
	public LocusAppliedFilterParams uniqueLocusAppliedFilterParams() {
		return (LocusAppliedFilterParams) super.uniqueResult();
	}
	
	public LocusAppliedFilterParams[] listLocusAppliedFilterParams() {
		return (LocusAppliedFilterParams[]) super.list().toArray(new LocusAppliedFilterParams[super.list().size()]);
	}
}

