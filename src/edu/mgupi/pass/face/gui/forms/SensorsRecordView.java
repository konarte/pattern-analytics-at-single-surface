/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)SensorsRecordsView.java 1.0 04.04.2009
 */

package edu.mgupi.pass.face.gui.forms;

import java.awt.Frame;

public class SensorsRecordView extends SensorsRecord {
	public SensorsRecordView(Frame owner) {
		super(owner, "sensorsRecordTableView", Messages.getString("SensorsRecord.title"), true);
		super.setModal(false);
	}
}
