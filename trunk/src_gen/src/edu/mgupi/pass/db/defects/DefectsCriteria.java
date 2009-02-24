/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: Raidan Flk
 * License Type: Evaluation
 */
package edu.mgupi.pass.db.defects;

import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class DefectsCriteria extends AbstractORMCriteria {
	public final IntegerExpression idDefect;
	public final FloatExpression beddingDepth;
	public final FloatExpression depth;
	public final FloatExpression width;
	public final FloatExpression length;
	
	public DefectsCriteria(Criteria criteria) {
		super(criteria);
		idDefect = new IntegerExpression("idDefect", this);
		beddingDepth = new FloatExpression("beddingDepth", this);
		depth = new FloatExpression("depth", this);
		width = new FloatExpression("width", this);
		length = new FloatExpression("length", this);
	}
	
	public DefectsCriteria(PersistentSession session) {
		this(session.createCriteria(Defects.class));
	}
	
	public DefectsCriteria() throws PersistentException {
		this(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession());
	}
	
	public DefectTypesCriteria createDefectTypeCriteria() {
		return new DefectTypesCriteria(createCriteria("defectType"));
	}
	
	public Defects uniqueDefects() {
		return (Defects) super.uniqueResult();
	}
	
	public Defects[] listDefects() {
		return (Defects[]) super.list().toArray(new Defects[super.list().size()]);
	}
}

