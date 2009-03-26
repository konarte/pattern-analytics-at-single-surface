package edu.mgupi.pass.face;

import java.awt.Window;

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
				Window expectedWindow = AppHelper.getInstance().searchWindow(MainFrame.class);
				return expectedWindow == null || expectedWindow.isVisible() == false;
			}
		});
		sec.stop();
		SwingTestHelper.closeAllWindows();

	}
}
