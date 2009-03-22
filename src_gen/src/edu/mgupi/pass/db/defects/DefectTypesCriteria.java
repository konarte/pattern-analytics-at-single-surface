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

public class DefectTypesCriteria extends AbstractORMCriteria {
	public final IntegerExpression idDefectType;
	public final StringExpression name;
	public final ByteArrayExpression defectImage;
	
	public DefectTypesCriteria(Criteria criteria) {
		super(criteria);
		idDefectType = new IntegerExpression("idDefectType", this);
		name = new StringExpression("name", this);
		defectImage = new ByteArrayExpression("defectImage", this);
	}
	
	public DefectTypesCriteria(PersistentSession session) {
		this(session.createCriteria(DefectTypes.class));
	}
	
	public DefectTypesCriteria() throws PersistentException {
		this(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession());
	}
	
	public DefectClassesCriteria createDefectClassCriteria() {
		return new DefectClassesCriteria(createCriteria("defectClass"));
	}
	
	public DefectTypeOptionsCriteria createOptionsCriteria() {
		return new DefectTypeOptionsCriteria(createCriteria("options"));
	}
	
	public DefectTypes uniqueDefectTypes() {
		return (DefectTypes) super.uniqueResult();
	}
	
	public DefectTypes[] listDefectTypes() {
		return (DefectTypes[]) super.list().toArray(new DefectTypes[super.list().size()]);
	}
}

