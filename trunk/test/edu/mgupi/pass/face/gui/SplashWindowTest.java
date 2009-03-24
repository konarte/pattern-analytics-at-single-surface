package edu.mgupi.pass.face.gui;

import javax.swing.JFrame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SplashWindowTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
	public void testWindow() throws Exception {
		SplashWindow window = new SplashWindow();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		Thread.sleep(200);

		window.setSplashText("Изменение теста");
		Thread.sleep(500);

		window.setVisible(false);
		window.dispose();

	}

	@Test
	public void testWindowIterate() throws Exception {

		SplashWindow window = new SplashWindow("resources/splash/tooslow.jpg");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);

		Thread.sleep(5000);
		window.setVisible(false);
		window.dispose();
	}

}
