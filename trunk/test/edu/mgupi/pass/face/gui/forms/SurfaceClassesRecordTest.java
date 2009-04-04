package edu.mgupi.pass.face.gui.forms;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.face.gui.SwingTestHelper;

public class SurfaceClassesRecordTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		SwingTestHelper.closeAllWindows();
	}

	@Test
	public void testOpen() throws Exception {
		SwingTestHelper.showMeBackground(new SurfaceClassesRecord(null));
		//new SurfaceClassesRecord(null).addRecord(SurfaceClassesFactory.createSurfaceClasses());
	}

}
