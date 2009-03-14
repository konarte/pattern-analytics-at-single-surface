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

public class DefectClassesDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression idDefectClass;
	public final StringExpression name;
	
	public DefectClassesDetachedCriteria() {
		super(edu.mgupi.pass.db.defects.DefectClasses.class, edu.mgupi.pass.db.defects.DefectClassesCriteria.class);
		idDefectClass = new IntegerExpression("idDefectClass", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
	}
	
	public DefectClassesDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, edu.mgupi.pass.db.defects.DefectClassesCriteria.class);
		idDefectClass = new IntegerExpression("idDefectClass", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
	}
	
	public DefectClasses uniqueDefectClasses(PersistentSession session) {
		return (DefectClasses) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public DefectClasses[] listDefectClasses(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (DefectClasses[]) list.toArray(new DefectClasses[list.size()]);
	}
}

