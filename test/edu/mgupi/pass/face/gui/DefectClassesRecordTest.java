package edu.mgupi.pass.face.gui;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DefectClassesRecordTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		SwingTestHelper.closeAllWindows();
	}

	@Test
	public void testStart() throws Exception {
		//new DefectClassesRecord(null).setVisible(true);
		//		DefectClassesRecord def = new DefectClassesRecord(null);
		//		def.setVisible(true);
		//		def.addRecord(DefectClassesFactory.createDefectClasses());

		SwingTestHelper.showMeBackground(new DefectClassesRecord(null));
	}

}
