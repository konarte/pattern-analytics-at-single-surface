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
package edu.mgupi.pass.db.locuses;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class LocusModuleDataDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression idModuleParam;
	public final StringExpression paramName;
	public final ByteArrayExpression paramData;
	public final IntegerExpression dataType;
	
	public LocusModuleDataDetachedCriteria() {
		super(edu.mgupi.pass.db.locuses.LocusModuleData.class, edu.mgupi.pass.db.locuses.LocusModuleDataCriteria.class);
		idModuleParam = new IntegerExpression("idModuleParam", this.getDetachedCriteria());
		paramName = new StringExpression("paramName", this.getDetachedCriteria());
		paramData = new ByteArrayExpression("paramData", this.getDetachedCriteria());
		dataType = new IntegerExpression("dataType", this.getDetachedCriteria());
	}
	
	public LocusModuleDataDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, edu.mgupi.pass.db.locuses.LocusModuleDataCriteria.class);
		idModuleParam = new IntegerExpression("idModuleParam", this.getDetachedCriteria());
		paramName = new StringExpression("paramName", this.getDetachedCriteria());
		paramData = new ByteArrayExpression("paramData", this.getDetachedCriteria());
		dataType = new IntegerExpression("dataType", this.getDetachedCriteria());
	}
	
	public LocusModuleData uniqueLocusModuleData(PersistentSession session) {
		return (LocusModuleData) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public LocusModuleData[] listLocusModuleData(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (LocusModuleData[]) list.toArray(new LocusModuleData[list.size()]);
	}
}

