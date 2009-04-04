/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)SurfacesRecordView.java 1.0 04.04.2009
 */

package edu.mgupi.pass.face.gui.forms;

import java.awt.Frame;

public class SurfacesRecordView extends SurfacesRecord {

	public SurfacesRecordView(Frame owner) {
		super(owner, "surfaceRecordTableView", Messages.getString("SurfacesRecord.title"), true);
		super.setModal(false);
	}

}
