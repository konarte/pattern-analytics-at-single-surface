package edu.mgupi.pass.face;

import static org.junit.Assert.assertTrue;

import javax.swing.JFrame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.util.Config;

public class MainFramePreCacheTest {

	private MainFrame frame;

	@Before
	public void setUp() throws Exception {
		Config.getInstance().setReadOnly();

		frame = (MainFrame) AppHelper.getInstance().openWindow(MainFrame.class);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	@After
	public void tearDown() throws Exception {
		if (frame != null) {
			frame.setVisible(false);
			frame.dispose();
			frame = null;
		}
	}

	@Test
	public void testPreCache() throws Exception {
		Thread.sleep(200);
		assertTrue(frame.isVisible());
	}

}
