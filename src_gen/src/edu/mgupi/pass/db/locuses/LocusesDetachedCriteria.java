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

public class LocusesDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression idLocus;
	public final StringExpression name;
	public final ByteArrayExpression thumbImage;
	public final ByteArrayExpression filteredImage;
	public final StringExpression moduleOptions;
	
	public LocusesDetachedCriteria() {
		super(edu.mgupi.pass.db.locuses.Locuses.class, edu.mgupi.pass.db.locuses.LocusesCriteria.class);
		idLocus = new IntegerExpression("idLocus", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		thumbImage = new ByteArrayExpression("thumbImage", this.getDetachedCriteria());
		filteredImage = new ByteArrayExpression("filteredImage", this.getDetachedCriteria());
		moduleOptions = new StringExpression("moduleOptions", this.getDetachedCriteria());
	}
	
	public LocusesDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, edu.mgupi.pass.db.locuses.LocusesCriteria.class);
		idLocus = new IntegerExpression("idLocus", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		thumbImage = new ByteArrayExpression("thumbImage", this.getDetachedCriteria());
		filteredImage = new ByteArrayExpression("filteredImage", this.getDetachedCriteria());
		moduleOptions = new StringExpression("moduleOptions", this.getDetachedCriteria());
	}
	
	public LModulesDetachedCriteria createModuleCriteria() {
		return new LModulesDetachedCriteria(createCriteria("module"));
	}
	
	public edu.mgupi.pass.db.surfaces.SurfacesDetachedCriteria createSurfaceCriteria() {
		return new edu.mgupi.pass.db.surfaces.SurfacesDetachedCriteria(createCriteria("surface"));
	}
	
	public edu.mgupi.pass.db.defects.DefectsDetachedCriteria createDefectCriteria() {
		return new edu.mgupi.pass.db.defects.DefectsDetachedCriteria(createCriteria("defect"));
	}
	
	public LocusSourcesDetachedCriteria createLocusSourceCriteria() {
		return new LocusSourcesDetachedCriteria(createCriteria("locusSource"));
	}
	
	public edu.mgupi.pass.db.sensors.SensorsDetachedCriteria createSensorCriteria() {
		return new edu.mgupi.pass.db.sensors.SensorsDetachedCriteria(createCriteria("sensor"));
	}
	
	public LocusModuleParamsDetachedCriteria createParamsCriteria() {
		return new LocusModuleParamsDetachedCriteria(createCriteria("params"));
	}
	
	public LocusFilterOptionsDetachedCriteria createFiltersCriteria() {
		return new LocusFilterOptionsDetachedCriteria(createCriteria("filters"));
	}
	
	public Locuses uniqueLocuses(PersistentSession session) {
		return (Locuses) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public Locuses[] listLocuses(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (Locuses[]) list.toArray(new Locuses[list.size()]);
	}
}

