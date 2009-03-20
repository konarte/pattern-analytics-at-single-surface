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

import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class LocusModuleParamsCriteria extends AbstractORMCriteria {
	public final IntegerExpression idModuleParam;
	public final StringExpression paramName;
	public final ByteArrayExpression paramData;
	public final IntegerExpression paramType;
	
	public LocusModuleParamsCriteria(Criteria criteria) {
		super(criteria);
		idModuleParam = new IntegerExpression("idModuleParam", this);
		paramName = new StringExpression("paramName", this);
		paramData = new ByteArrayExpression("paramData", this);
		paramType = new IntegerExpression("paramType", this);
	}
	
	public LocusModuleParamsCriteria(PersistentSession session) {
		this(session.createCriteria(LocusModuleParams.class));
	}
	
	public LocusModuleParamsCriteria() throws PersistentException {
		this(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession());
	}
	
	public LocusModuleParams uniqueLocusModuleParams() {
		return (LocusModuleParams) super.uniqueResult();
	}
	
	public LocusModuleParams[] listLocusModuleParams() {
		return (LocusModuleParams[]) super.list().toArray(new LocusModuleParams[super.list().size()]);
	}
}

