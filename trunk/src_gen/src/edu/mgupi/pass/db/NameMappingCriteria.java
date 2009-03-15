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
package edu.mgupi.pass.db;

import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class NameMappingCriteria extends AbstractORMCriteria {
	public final IntegerExpression ID;
	public final IntegerExpression nameType;
	public final StringExpression name;
	public final StringExpression title;
	
	public NameMappingCriteria(Criteria criteria) {
		super(criteria);
		ID = new IntegerExpression("ID", this);
		nameType = new IntegerExpression("nameType", this);
		name = new StringExpression("name", this);
		title = new StringExpression("title", this);
	}
	
	public NameMappingCriteria(PersistentSession session) {
		this(session.createCriteria(NameMapping.class));
	}
	
	public NameMappingCriteria() throws PersistentException {
		this(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession());
	}
	
	public NameMapping uniqueNameMapping() {
		return (NameMapping) super.uniqueResult();
	}
	
	public NameMapping[] listNameMapping() {
		return (NameMapping[]) super.list().toArray(new NameMapping[super.list().size()]);
	}
}

