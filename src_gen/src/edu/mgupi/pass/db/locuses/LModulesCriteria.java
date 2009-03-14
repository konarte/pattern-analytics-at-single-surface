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

public class LModulesCriteria extends AbstractORMCriteria {
	public final IntegerExpression idLModule;
	public final StringExpression name;
	public final StringExpression codename;
	
	public LModulesCriteria(Criteria criteria) {
		super(criteria);
		idLModule = new IntegerExpression("idLModule", this);
		name = new StringExpression("name", this);
		codename = new StringExpression("codename", this);
	}
	
	public LModulesCriteria(PersistentSession session) {
		this(session.createCriteria(LModules.class));
	}
	
	public LModulesCriteria() throws PersistentException {
		this(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession());
	}
	
	public LModules uniqueLModules() {
		return (LModules) super.uniqueResult();
	}
	
	public LModules[] listLModules() {
		return (LModules[]) super.list().toArray(new LModules[super.list().size()]);
	}
}

