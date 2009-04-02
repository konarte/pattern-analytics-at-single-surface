/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)SurfaceFilteredTable.java 1.0 31.03.2009
 */

package edu.mgupi.pass.face.gui.forms;

import java.awt.Frame;

import javax.swing.JTable;

import edu.mgupi.pass.face.gui.template.AbstractEditorTableModel;
import edu.mgupi.pass.face.gui.template.TableEditorTemplate;

public class SurfaceFilteredTable extends TableEditorTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SurfaceFilteredTable(Frame owner) {
		super(owner, "surfaceFilteredTable", "Поиск поверхностей");
	}


	@Override
	protected AbstractEditorTableModel getTableModelImpl(JTable owner) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void tablePostInit(JTable owner) {
		// TODO Auto-generated method stub

	}

}
