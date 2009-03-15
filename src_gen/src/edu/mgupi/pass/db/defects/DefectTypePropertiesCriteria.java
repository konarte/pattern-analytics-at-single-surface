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

public class DefectTypePropertiesCriteria extends AbstractORMCriteria {
	public final IntegerExpression idDefectProperty;
	
	public DefectTypePropertiesCriteria(Criteria criteria) {
		super(criteria);
		idDefectProperty = new IntegerExpression("idDefectProperty", this);
	}
	
	public DefectTypePropertiesCriteria(PersistentSession session) {
		this(session.createCriteria(DefectTypeProperties.class));
	}
	
	public DefectTypePropertiesCriteria() throws PersistentException {
		this(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession());
	}
	
	public DefectTypeProperties uniqueDefectTypeProperties() {
		return (DefectTypeProperties) super.uniqueResult();
	}
	
	public DefectTypeProperties[] listDefectTypeProperties() {
		return (DefectTypeProperties[]) super.list().toArray(new DefectTypeProperties[super.list().size()]);
	}
}

