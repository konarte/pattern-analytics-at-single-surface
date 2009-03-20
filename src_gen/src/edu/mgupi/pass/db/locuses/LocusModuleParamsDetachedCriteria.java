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

public class LocusModuleParamsDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression idModuleParam;
	public final StringExpression paramName;
	public final ByteArrayExpression paramData;
	public final IntegerExpression paramType;
	
	public LocusModuleParamsDetachedCriteria() {
		super(edu.mgupi.pass.db.locuses.LocusModuleParams.class, edu.mgupi.pass.db.locuses.LocusModuleParamsCriteria.class);
		idModuleParam = new IntegerExpression("idModuleParam", this.getDetachedCriteria());
		paramName = new StringExpression("paramName", this.getDetachedCriteria());
		paramData = new ByteArrayExpression("paramData", this.getDetachedCriteria());
		paramType = new IntegerExpression("paramType", this.getDetachedCriteria());
	}
	
	public LocusModuleParamsDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, edu.mgupi.pass.db.locuses.LocusModuleParamsCriteria.class);
		idModuleParam = new IntegerExpression("idModuleParam", this.getDetachedCriteria());
		paramName = new StringExpression("paramName", this.getDetachedCriteria());
		paramData = new ByteArrayExpression("paramData", this.getDetachedCriteria());
		paramType = new IntegerExpression("paramType", this.getDetachedCriteria());
	}
	
	public LocusModuleParams uniqueLocusModuleParams(PersistentSession session) {
		return (LocusModuleParams) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public LocusModuleParams[] listLocusModuleParams(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (LocusModuleParams[]) list.toArray(new LocusModuleParams[list.size()]);
	}
}

