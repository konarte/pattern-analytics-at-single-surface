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

public class LocusAppliedModuleDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression idLocusModule;
	
	public LocusAppliedModuleDetachedCriteria() {
		super(edu.mgupi.pass.db.locuses.LocusAppliedModule.class, edu.mgupi.pass.db.locuses.LocusAppliedModuleCriteria.class);
		idLocusModule = new IntegerExpression("idLocusModule", this.getDetachedCriteria());
	}
	
	public LocusAppliedModuleDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, edu.mgupi.pass.db.locuses.LocusAppliedModuleCriteria.class);
		idLocusModule = new IntegerExpression("idLocusModule", this.getDetachedCriteria());
	}
	
	public LModulesDetachedCriteria createModuleCriteria() {
		return new LModulesDetachedCriteria(createCriteria("module"));
	}
	
	public LocusAppliedModuleParamsDetachedCriteria createParamsCriteria() {
		return new LocusAppliedModuleParamsDetachedCriteria(createCriteria("params"));
	}
	
	public LocusModuleDataDetachedCriteria createDataCriteria() {
		return new LocusModuleDataDetachedCriteria(createCriteria("data"));
	}
	
	public LocusAppliedModule uniqueLocusAppliedModule(PersistentSession session) {
		return (LocusAppliedModule) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public LocusAppliedModule[] listLocusAppliedModule(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (LocusAppliedModule[]) list.toArray(new LocusAppliedModule[list.size()]);
	}
}

