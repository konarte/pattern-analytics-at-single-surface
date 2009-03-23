package edu.mgupi.pass.face;

import java.awt.Frame;

import javax.swing.JTable;

import edu.mgupi.pass.db.locuses.LModules;
import edu.mgupi.pass.face.template.JTableReadOnly;

public class ListDialogModules extends ListDialogFilters {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ListDialogModules(Frame owner) {
		super(owner);
		this.setTitle("Список модулей");
	}

	protected JTable getTableDataImpl() {
		LModules modules[] = MainFrameDataStorage.getInstance().listLModulesIface();

		String cells[][] = new String[modules.length][2];
		for (int i = 0; i < modules.length; i++) {
			cells[i][0] = modules[i].getName();
			cells[i][1] = modules[i].getCodename();
		}

		JTableReadOnly table = new JTableReadOnly(cells, new String[] { "Название модуля", "Используемый класс" });
		return table;
	}

}
