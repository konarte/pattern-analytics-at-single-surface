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

public class DefectTypePropertiesDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression idDefectProperty;
	
	public DefectTypePropertiesDetachedCriteria() {
		super(edu.mgupi.pass.db.defects.DefectTypeProperties.class, edu.mgupi.pass.db.defects.DefectTypePropertiesCriteria.class);
		idDefectProperty = new IntegerExpression("idDefectProperty", this.getDetachedCriteria());
	}
	
	public DefectTypePropertiesDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, edu.mgupi.pass.db.defects.DefectTypePropertiesCriteria.class);
		idDefectProperty = new IntegerExpression("idDefectProperty", this.getDetachedCriteria());
	}
	
	public DefectTypeProperties uniqueDefectTypeProperties(PersistentSession session) {
		return (DefectTypeProperties) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public DefectTypeProperties[] listDefectTypeProperties(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (DefectTypeProperties[]) list.toArray(new DefectTypeProperties[list.size()]);
	}
}

