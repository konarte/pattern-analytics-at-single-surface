package edu.mgupi.pass.face;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.face.gui.AppHelper;
import edu.mgupi.pass.face.gui.MainFrame;
import edu.mgupi.pass.face.gui.SwingTestHelper;
import edu.mgupi.pass.util.Secundomer;
import edu.mgupi.pass.util.SecundomerList;
import edu.mgupi.pass.util.WaitCondition;
import edu.mgupi.pass.util.WorkSet;

public class ApplicationTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMain() throws Exception {
		Secundomer sec = SecundomerList.registerSecundomer("Application Load Time");

		sec.start();
		SwingTestHelper.addWorkAndWaitThis(new WorkSet() {
			public void workImpl() throws Exception {
				Application.main(null);
			}
		}, new WaitCondition() {
			public boolean keepWorking() {
				return AppHelper.getInstance().searchWindow(MainFrame.class) == null;
			}
		});
		sec.stop();
		SwingTestHelper.closeAllWindows();


	}
}
