package edu.mgupi.pass.face.gui;

import java.awt.Frame;

import javax.swing.JTable;

import edu.mgupi.pass.db.locuses.LModules;
import edu.mgupi.pass.face.gui.template.JTableReadOnly;

public class LModulesList extends LFiltersList {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LModulesList(Frame owner) {
		super(owner);
		this.setTitle("Список модулей");
		this.setName("lModulesList");
	}

	protected JTable getTableDataImpl() {
		LModules modules[] = AppDataStorage.getInstance().listLModules();

		String cells[][] = new String[modules.length][2];
		for (int i = 0; i < modules.length; i++) {
			cells[i][0] = modules[i].getName();
			cells[i][1] = modules[i].getCodename();
		}

		JTableReadOnly table = new JTableReadOnly(cells, new String[] { "Название модуля", "Используемый класс" });
		return table;
	}

}
