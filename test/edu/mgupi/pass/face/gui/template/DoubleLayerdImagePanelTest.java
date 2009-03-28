package edu.mgupi.pass.face.gui.template;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.face.gui.SwingTestHelper;

public class DoubleLayerdImagePanelTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		SwingTestHelper.closeAllWindows();
	}

	@Test
	public void voidTestView() throws Exception {
		JDialog myDialog = new JDialog((Frame) null, true);
		myDialog.setLocation(500, 200);
		myDialog.setSize(800, 600);
		JRootPane pane = myDialog.getRootPane();
		pane.setLayout(new BorderLayout());

		pane.add(new RecordFormWithImageTemplate(myDialog, new JPanel()), BorderLayout.CENTER);
		pane.add(new JLabel("OK"), BorderLayout.SOUTH);

		//myDialog.setVisible(true);
		SwingTestHelper.showMeBackground(myDialog);
		
	}

}
