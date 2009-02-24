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

public class LFiltersCriteria extends AbstractORMCriteria {
	public final IntegerExpression idModule;
	public final StringExpression name;
	public final StringExpression codename;
	
	public LFiltersCriteria(Criteria criteria) {
		super(criteria);
		idModule = new IntegerExpression("idModule", this);
		name = new StringExpression("name", this);
		codename = new StringExpression("codename", this);
	}
	
	public LFiltersCriteria(PersistentSession session) {
		this(session.createCriteria(LFilters.class));
	}
	
	public LFiltersCriteria() throws PersistentException {
		this(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession());
	}
	
	public LFilters uniqueLFilters() {
		return (LFilters) super.uniqueResult();
	}
	
	public LFilters[] listLFilters() {
		return (LFilters[]) super.list().toArray(new LFilters[super.list().size()]);
	}
}

