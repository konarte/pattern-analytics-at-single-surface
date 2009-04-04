/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)ComboBoxSurfaces.java 1.0 04.04.2009
 */

package edu.mgupi.pass.face.gui.forms;

import org.orm.PersistentException;

import edu.mgupi.pass.db.surfaces.Surfaces;

public class ComboBoxSurfaces extends AbstractComboBox<Surfaces> {

	public ComboBoxSurfaces() {
		super(true);
	}

	@Override
	protected String getRenderedValue(Surfaces value) {
		return "ID: " + value.getIdSurface()
				+ (value.getSurfaceType() == null ? "" : ", " + value.getSurfaceType().getName());
	}

	@Override
	protected Surfaces[] getRowsImpl() throws PersistentException {
		return null;
	}

	@Override
	protected boolean objectEqualsImpl(Surfaces first, Surfaces second) {
		return first.getIdSurface() == second.getIdSurface();
	}

}
