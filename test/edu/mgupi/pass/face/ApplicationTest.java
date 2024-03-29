package edu.mgupi.pass.face;

import java.awt.Window;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.face.gui.AppHelper;
import edu.mgupi.pass.face.gui.MainFrame;
import edu.mgupi.pass.face.gui.SwingTestHelper;
import edu.mgupi.pass.face.gui.WaitCondition;
import edu.mgupi.pass.util.Config;
import edu.mgupi.pass.util.Secundomer;
import edu.mgupi.pass.util.SecundomerList;

public class ApplicationTest {

	@Before
	public void setUp() throws Exception {
		Config.getInstance().setDebugVirualMode();
		Config.getInstance().setPassword("adesroot");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMain() throws Exception {
		Secundomer sec = SecundomerList.registerSecundomer("Application Load Time");

		sec.start();

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Application.main(null);
			}
		});
		t.start();
		
		SwingTestHelper.waitWhile(new WaitCondition() {
			public boolean keepWorking() {
				Window expectedWindow = AppHelper.getInstance().searchWindow(MainFrame.class);
				return expectedWindow == null || expectedWindow.isVisible() == false;
			}
		});
		sec.stop();
		SwingTestHelper.closeAllWindows();

	}
}
