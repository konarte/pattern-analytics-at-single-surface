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
package edu.mgupi.pass.db.defects;

import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class DefectClassesCriteria extends AbstractORMCriteria {
	public final IntegerExpression idDefectClass;
	public final StringExpression name;
	
	public DefectClassesCriteria(Criteria criteria) {
		super(criteria);
		idDefectClass = new IntegerExpression("idDefectClass", this);
		name = new StringExpression("name", this);
	}
	
	public DefectClassesCriteria(PersistentSession session) {
		this(session.createCriteria(DefectClasses.class));
	}
	
	public DefectClassesCriteria() throws PersistentException {
		this(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession());
	}
	
	public DefectClasses uniqueDefectClasses() {
		return (DefectClasses) super.uniqueResult();
	}
	
	public DefectClasses[] listDefectClasses() {
		return (DefectClasses[]) super.list().toArray(new DefectClasses[super.list().size()]);
	}
}

