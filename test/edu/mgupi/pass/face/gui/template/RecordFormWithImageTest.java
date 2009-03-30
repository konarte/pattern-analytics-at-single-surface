package edu.mgupi.pass.face.gui.template;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.UIManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.face.gui.AppHelper;
import edu.mgupi.pass.face.gui.SwingTestHelper;
import edu.mgupi.pass.inputs.TestInputImpl;

public class RecordFormWithImageTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		SwingTestHelper.closeAllWindows();
	}

	@Test
	public void voidTestView() throws Exception {

		AppHelper.getInstance().updateUI(UIManager.getSystemLookAndFeelClassName());

		JDialog myDialog = new JDialog((Frame) null, true);
		myDialog.setLocation(500, 200);
		myDialog.setSize(800, 600);
		JRootPane pane = myDialog.getRootPane();
		pane.setLayout(new BorderLayout());

		RecordFormWithImageTemplate tmp = new RecordFormWithImageTemplate(new JPanel());
		pane.add(new JLabel("HELLO!"), BorderLayout.CENTER);
		pane.add(tmp, BorderLayout.EAST);
		pane.add(new JLabel("OK"), BorderLayout.SOUTH);

		TestInputImpl inp = new TestInputImpl();
		inp.init();

		try {
			tmp.getImagePanel().setImage(inp.getSingleSource().getSourceImage());
		} finally {
			inp.close();

		}

		//myDialog.setVisible(true);
		SwingTestHelper.showMeBackground(myDialog);

	}

}
