package edu.mgupi.pass.face.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.util.Const;
import edu.mgupi.pass.util.Utils;

public class SplashWindowTest {

	private SplashWindow window = null;

	@Before
	public void setUp() throws Exception {
		window = new SplashWindow();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@After
	public void tearDown() throws Exception {
		SwingTestHelper.closeAllWindows();
	}

	@Test
	public void testWindow() throws Exception {

		SwingTestHelper.showMeBackground(window);

		JLabel label = (JLabel) Utils.getChildNamed(window, "program");
		assertNotNull(label);
		assertEquals(Const.PROGRAM_NAME_FIRST, label.getText());

		label = (JLabel) Utils.getChildNamed(window, "version");
		assertNotNull(label);
		assertEquals(Const.PROGRAM_NAME_LAST, label.getText());

		label = (JLabel) Utils.getChildNamed(window, "title");
		assertNotNull(label);

		window.setSplashText("Изменение текста");

		assertEquals("Изменение текста", label.getText());

	}

	@Test
	public void testWindowIterate() throws Exception {
		SwingTestHelper.showMeBackground(window);

		//Thread.sleep(5000);
	}

}
