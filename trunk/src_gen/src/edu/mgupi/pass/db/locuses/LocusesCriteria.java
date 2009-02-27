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
package edu.mgupi.pass.db.locuses;

import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class LocusesCriteria extends AbstractORMCriteria {
	public final IntegerExpression idLocus;
	public final StringExpression name;
	public final ByteArrayExpression thumbImage;
	public final ByteArrayExpression filteredImage;
	
	public LocusesCriteria(Criteria criteria) {
		super(criteria);
		idLocus = new IntegerExpression("idLocus", this);
		name = new StringExpression("name", this);
		thumbImage = new ByteArrayExpression("thumbImage", this);
		filteredImage = new ByteArrayExpression("filteredImage", this);
	}
	
	public LocusesCriteria(PersistentSession session) {
		this(session.createCriteria(Locuses.class));
	}
	
	public LocusesCriteria() throws PersistentException {
		this(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession());
	}
	
	public LModulesCriteria createModuleCriteria() {
		return new LModulesCriteria(createCriteria("module"));
	}
	
	public edu.mgupi.pass.db.surfaces.SurfacesCriteria createSurfaceCriteria() {
		return new edu.mgupi.pass.db.surfaces.SurfacesCriteria(createCriteria("surface"));
	}
	
	public edu.mgupi.pass.db.defects.DefectsCriteria createDefectCriteria() {
		return new edu.mgupi.pass.db.defects.DefectsCriteria(createCriteria("defect"));
	}
	
	public LocusSourcesCriteria createLocusSourceCriteria() {
		return new LocusSourcesCriteria(createCriteria("locusSource"));
	}
	
	public edu.mgupi.pass.db.sensors.SensorsCriteria createSensorCriteria() {
		return new edu.mgupi.pass.db.sensors.SensorsCriteria(createCriteria("sensor"));
	}
	
	public LocusModuleParamsCriteria createParamsCriteria() {
		return new LocusModuleParamsCriteria(createCriteria("params"));
	}
	
	public LocusFiltersCriteria createFiltersCriteria() {
		return new LocusFiltersCriteria(createCriteria("filters"));
	}
	
	public Locuses uniqueLocuses() {
		return (Locuses) super.uniqueResult();
	}
	
	public Locuses[] listLocuses() {
		return (Locuses[]) super.list().toArray(new Locuses[super.list().size()]);
	}
}

