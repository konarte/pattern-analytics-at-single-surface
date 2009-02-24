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

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class DefectsDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression idDefect;
	public final FloatExpression beddingDepth;
	public final FloatExpression depth;
	public final FloatExpression width;
	public final FloatExpression length;
	
	public DefectsDetachedCriteria() {
		super(edu.mgupi.pass.db.defects.Defects.class, edu.mgupi.pass.db.defects.DefectsCriteria.class);
		idDefect = new IntegerExpression("idDefect", this.getDetachedCriteria());
		beddingDepth = new FloatExpression("beddingDepth", this.getDetachedCriteria());
		depth = new FloatExpression("depth", this.getDetachedCriteria());
		width = new FloatExpression("width", this.getDetachedCriteria());
		length = new FloatExpression("length", this.getDetachedCriteria());
	}
	
	public DefectsDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, edu.mgupi.pass.db.defects.DefectsCriteria.class);
		idDefect = new IntegerExpression("idDefect", this.getDetachedCriteria());
		beddingDepth = new FloatExpression("beddingDepth", this.getDetachedCriteria());
		depth = new FloatExpression("depth", this.getDetachedCriteria());
		width = new FloatExpression("width", this.getDetachedCriteria());
		length = new FloatExpression("length", this.getDetachedCriteria());
	}
	
	public DefectTypesDetachedCriteria createDefectTypeCriteria() {
		return new DefectTypesDetachedCriteria(createCriteria("defectType"));
	}
	
	public Defects uniqueDefects(PersistentSession session) {
		return (Defects) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public Defects[] listDefects(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (Defects[]) list.toArray(new Defects[list.size()]);
	}
}

