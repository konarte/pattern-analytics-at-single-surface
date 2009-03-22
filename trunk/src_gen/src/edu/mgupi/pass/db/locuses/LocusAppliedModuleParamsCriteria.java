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

public class LocusAppliedModuleParamsCriteria extends AbstractORMCriteria {
	public final IntegerExpression ID;
	public final StringExpression name;
	public final StringExpression value;
	
	public LocusAppliedModuleParamsCriteria(Criteria criteria) {
		super(criteria);
		ID = new IntegerExpression("ID", this);
		name = new StringExpression("name", this);
		value = new StringExpression("value", this);
	}
	
	public LocusAppliedModuleParamsCriteria(PersistentSession session) {
		this(session.createCriteria(LocusAppliedModuleParams.class));
	}
	
	public LocusAppliedModuleParamsCriteria() throws PersistentException {
		this(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession());
	}
	
	public LocusAppliedModuleParams uniqueLocusAppliedModuleParams() {
		return (LocusAppliedModuleParams) super.uniqueResult();
	}
	
	public LocusAppliedModuleParams[] listLocusAppliedModuleParams() {
		return (LocusAppliedModuleParams[]) super.list().toArray(new LocusAppliedModuleParams[super.list().size()]);
	}
}

