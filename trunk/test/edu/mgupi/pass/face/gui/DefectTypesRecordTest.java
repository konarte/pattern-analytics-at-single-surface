package edu.mgupi.pass.face.gui;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DefectTypesRecordTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		SwingTestHelper.closeAllWindows();
	}

	@Test
	public void testOpen() throws Exception {
		//new DefectTypesRecord(null).setVisible(true);
		SwingTestHelper.showMeBackground(new DefectTypesRecord(null));
		//DefectTypesRecord.addRecord(DefectTypesFactory.createDefectTypes());
		//assertTrue(rec == rec.rec);

	}
}
