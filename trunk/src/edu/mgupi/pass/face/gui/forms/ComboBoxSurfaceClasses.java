package edu.mgupi.pass.face.gui.forms;

import org.orm.PersistentException;

import edu.mgupi.pass.db.surfaces.SurfaceClasses;
import edu.mgupi.pass.db.surfaces.SurfaceClassesFactory;

public class ComboBoxSurfaceClasses extends AbstractComboBox<SurfaceClasses> {

	public ComboBoxSurfaceClasses() {
		this(false);
	}

	public ComboBoxSurfaceClasses(boolean canBeEmpty) {
		super(canBeEmpty);
	}

	@Override
	protected SurfaceClasses[] getRowsImpl() throws PersistentException {
		return SurfaceClassesFactory.listSurfaceClassesByQuery(null, null);
	}

	@Override
	protected boolean objectEqualsImpl(SurfaceClasses first, SurfaceClasses second) {
		return first.getIdSurfaceClass() == second.getIdSurfaceClass();
	}

	@Override
	protected String getRenderedValue(SurfaceClasses value) {
		return value.getName();
	}
}