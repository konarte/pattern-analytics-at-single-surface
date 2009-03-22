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

public class LocusModuleDataCriteria extends AbstractORMCriteria {
	public final IntegerExpression idModuleParam;
	public final StringExpression paramName;
	public final ByteArrayExpression paramData;
	public final IntegerExpression dataType;
	
	public LocusModuleDataCriteria(Criteria criteria) {
		super(criteria);
		idModuleParam = new IntegerExpression("idModuleParam", this);
		paramName = new StringExpression("paramName", this);
		paramData = new ByteArrayExpression("paramData", this);
		dataType = new IntegerExpression("dataType", this);
	}
	
	public LocusModuleDataCriteria(PersistentSession session) {
		this(session.createCriteria(LocusModuleData.class));
	}
	
	public LocusModuleDataCriteria() throws PersistentException {
		this(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession());
	}
	
	public LocusModuleData uniqueLocusModuleData() {
		return (LocusModuleData) super.uniqueResult();
	}
	
	public LocusModuleData[] listLocusModuleData() {
		return (LocusModuleData[]) super.list().toArray(new LocusModuleData[super.list().size()]);
	}
}

