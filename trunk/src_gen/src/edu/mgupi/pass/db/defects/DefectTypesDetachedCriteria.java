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

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class DefectTypesDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression idDefectType;
	public final StringExpression name;
	public final ByteArrayExpression defectImage;
	
	public DefectTypesDetachedCriteria() {
		super(edu.mgupi.pass.db.defects.DefectTypes.class, edu.mgupi.pass.db.defects.DefectTypesCriteria.class);
		idDefectType = new IntegerExpression("idDefectType", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		defectImage = new ByteArrayExpression("defectImage", this.getDetachedCriteria());
	}
	
	public DefectTypesDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, edu.mgupi.pass.db.defects.DefectTypesCriteria.class);
		idDefectType = new IntegerExpression("idDefectType", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		defectImage = new ByteArrayExpression("defectImage", this.getDetachedCriteria());
	}
	
	public DefectClassesDetachedCriteria createDefectClassCriteria() {
		return new DefectClassesDetachedCriteria(createCriteria("defectClass"));
	}
	
	public DefectTypeOptionsDetachedCriteria createOptionsCriteria() {
		return new DefectTypeOptionsDetachedCriteria(createCriteria("options"));
	}
	
	public DefectTypes uniqueDefectTypes(PersistentSession session) {
		return (DefectTypes) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public DefectTypes[] listDefectTypes(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (DefectTypes[]) list.toArray(new DefectTypes[list.size()]);
	}
}

