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

public class DefectTypeOptionsCriteria extends AbstractORMCriteria {
	public final IntegerExpression ID;
	public final StringExpression name;
	public final StringExpression value;
	
	public DefectTypeOptionsCriteria(Criteria criteria) {
		super(criteria);
		ID = new IntegerExpression("ID", this);
		name = new StringExpression("name", this);
		value = new StringExpression("value", this);
	}
	
	public DefectTypeOptionsCriteria(PersistentSession session) {
		this(session.createCriteria(DefectTypeOptions.class));
	}
	
	public DefectTypeOptionsCriteria() throws PersistentException {
		this(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession());
	}
	
	public DefectTypeOptions uniqueDefectTypeOptions() {
		return (DefectTypeOptions) super.uniqueResult();
	}
	
	public DefectTypeOptions[] listDefectTypeOptions() {
		return (DefectTypeOptions[]) super.list().toArray(new DefectTypeOptions[super.list().size()]);
	}
}

