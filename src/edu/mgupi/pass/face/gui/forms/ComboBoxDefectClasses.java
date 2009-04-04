package edu.mgupi.pass.face.gui.forms;

import org.orm.PersistentException;

import edu.mgupi.pass.db.defects.DefectClasses;
import edu.mgupi.pass.db.defects.DefectClassesFactory;

public class ComboBoxDefectClasses extends AbstractComboBox<DefectClasses> {

	public ComboBoxDefectClasses() {
		super(false);
	}

	@Override
	protected DefectClasses[] getRowsImpl() throws PersistentException {
		return DefectClassesFactory.listDefectClassesByQuery(null, null);
	}

	@Override
	protected boolean objectEqualsImpl(DefectClasses first, DefectClasses second) {
		return first.getIdDefectClass() == second.getIdDefectClass();
	}

	@Override
	protected String getRenderedValue(DefectClasses value) {
		return value.getName();
	}
}
