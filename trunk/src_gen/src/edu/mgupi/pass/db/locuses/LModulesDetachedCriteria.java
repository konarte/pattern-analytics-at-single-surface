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

public class LModulesDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression idLModule;
	public final StringExpression name;
	public final StringExpression codename;
	
	public LModulesDetachedCriteria() {
		super(edu.mgupi.pass.db.locuses.LModules.class, edu.mgupi.pass.db.locuses.LModulesCriteria.class);
		idLModule = new IntegerExpression("idLModule", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		codename = new StringExpression("codename", this.getDetachedCriteria());
	}
	
	public LModulesDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, edu.mgupi.pass.db.locuses.LModulesCriteria.class);
		idLModule = new IntegerExpression("idLModule", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		codename = new StringExpression("codename", this.getDetachedCriteria());
	}
	
	public LModules uniqueLModules(PersistentSession session) {
		return (LModules) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public LModules[] listLModules(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (LModules[]) list.toArray(new LModules[list.size()]);
	}
}

