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

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class NameMappingDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression ID;
	public final IntegerExpression nameType;
	public final StringExpression name;
	public final StringExpression title;
	
	public NameMappingDetachedCriteria() {
		super(edu.mgupi.pass.db.NameMapping.class, edu.mgupi.pass.db.NameMappingCriteria.class);
		ID = new IntegerExpression("ID", this.getDetachedCriteria());
		nameType = new IntegerExpression("nameType", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		title = new StringExpression("title", this.getDetachedCriteria());
	}
	
	public NameMappingDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, edu.mgupi.pass.db.NameMappingCriteria.class);
		ID = new IntegerExpression("ID", this.getDetachedCriteria());
		nameType = new IntegerExpression("nameType", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		title = new StringExpression("title", this.getDetachedCriteria());
	}
	
	public NameMapping uniqueNameMapping(PersistentSession session) {
		return (NameMapping) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public NameMapping[] listNameMapping(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (NameMapping[]) list.toArray(new NameMapping[list.size()]);
	}
}

