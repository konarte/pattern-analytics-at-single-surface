package edu.mgupi.pass.face.gui;

import java.awt.Frame;

import javax.swing.JTable;

import edu.mgupi.pass.db.locuses.LModules;
import edu.mgupi.pass.face.gui.template.JTableReadOnly;

public class LModulesList extends LFiltersList {
	/**
	 * Default constructor.
	 * 
	 * @param owner
	 * 
	 */
	public LModulesList(Frame owner) {
		super(owner);
		this.setTitle(Messages.getString("LModulesList.title"));
		this.setName("lModulesList");
	}

	@Override
	protected JTable getTableDataImpl() {
		LModules modules[] = AppDataStorage.getInstance().listLModules();

		String cells[][] = new String[modules.length][2];
		for (int i = 0; i < modules.length; i++) {
			cells[i][0] = modules[i].getName();
			cells[i][1] = modules[i].getCodename();
		}

		JTableReadOnly table = new JTableReadOnly(cells, new String[] {
				Messages.getString("LModulesList.name"), Messages.getString("LModulesList.class") });
		return table;
	}

}
