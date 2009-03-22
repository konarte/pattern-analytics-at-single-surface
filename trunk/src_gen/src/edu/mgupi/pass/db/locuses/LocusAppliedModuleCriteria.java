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

public class LocusAppliedModuleCriteria extends AbstractORMCriteria {
	public final IntegerExpression idLocusModule;
	
	public LocusAppliedModuleCriteria(Criteria criteria) {
		super(criteria);
		idLocusModule = new IntegerExpression("idLocusModule", this);
	}
	
	public LocusAppliedModuleCriteria(PersistentSession session) {
		this(session.createCriteria(LocusAppliedModule.class));
	}
	
	public LocusAppliedModuleCriteria() throws PersistentException {
		this(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession());
	}
	
	public LModulesCriteria createModuleCriteria() {
		return new LModulesCriteria(createCriteria("module"));
	}
	
	public LocusAppliedModuleParamsCriteria createParamsCriteria() {
		return new LocusAppliedModuleParamsCriteria(createCriteria("params"));
	}
	
	public LocusModuleDataCriteria createDataCriteria() {
		return new LocusModuleDataCriteria(createCriteria("data"));
	}
	
	public LocusAppliedModule uniqueLocusAppliedModule() {
		return (LocusAppliedModule) super.uniqueResult();
	}
	
	public LocusAppliedModule[] listLocusAppliedModule() {
		return (LocusAppliedModule[]) super.list().toArray(new LocusAppliedModule[super.list().size()]);
	}
}

