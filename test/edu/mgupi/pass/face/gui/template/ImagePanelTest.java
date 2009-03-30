/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)ImagePanelTest.java 1.0 29.03.2009
 */

package edu.mgupi.pass.face.gui.template;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JRootPane;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.face.gui.SwingTestHelper;
import edu.mgupi.pass.inputs.TestInputImpl;

public class ImagePanelTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		SwingTestHelper.closeAllWindows();
	}

	@Test
	public void testImagePanelIntIntBooleanBooleanString() throws Exception {
		JFrame parent = new JFrame();
		JRootPane root = parent.getRootPane();
		root.setLayout(new BoxLayout(root, BoxLayout.X_AXIS));
		parent.setBounds(200, 300, 500, 400);

		ImagePanel img = new ImagePanel(256, 256, true, "Изображение");
		root.add(img);

		TestInputImpl input = new TestInputImpl();
		input.init();
		try {
			img.setImage(input.getSingleSource().getSourceImage());

			parent.setVisible(true);
			//Thread.sleep(5000);
		} finally {
			input.close();
		}
	}

}
