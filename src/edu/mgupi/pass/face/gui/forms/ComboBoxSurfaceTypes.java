/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)SurfaceTypesComboBox.java 1.0 04.04.2009
 */

package edu.mgupi.pass.face.gui.forms;

import org.orm.PersistentException;

import edu.mgupi.pass.db.surfaces.SurfaceClasses;
import edu.mgupi.pass.db.surfaces.SurfaceTypes;
import edu.mgupi.pass.db.surfaces.SurfaceTypesFactory;

public class ComboBoxSurfaceTypes extends AbstractDependableComboBox<SurfaceTypes, SurfaceClasses> {


	public ComboBoxSurfaceTypes(final ComboBoxSurfaceClasses classCombo) {
		this(classCombo, false);
	}

	public ComboBoxSurfaceTypes(final ComboBoxSurfaceClasses classCombo, boolean canBeEmpty) {
		super(classCombo, canBeEmpty);
	}

	@Override
	protected boolean acceptFilter(SurfaceTypes item, SurfaceClasses filter) {
		return item.getSurfaceClass() == null
				|| item.getSurfaceClass().getIdSurfaceClass() == filter.getIdSurfaceClass();
	}

	@Override
	protected SurfaceTypes[] getRowsImpl() throws PersistentException {
		return SurfaceTypesFactory.listSurfaceTypesByQuery(null, null);
	}

	@Override
	protected boolean objectEqualsImpl(SurfaceTypes first, SurfaceTypes second) {
		return first.getIdSurfaceType() == second.getIdSurfaceType();
	}

	@Override
	protected String getRenderedValue(SurfaceTypes value) {
		return value.getName();
	}
}