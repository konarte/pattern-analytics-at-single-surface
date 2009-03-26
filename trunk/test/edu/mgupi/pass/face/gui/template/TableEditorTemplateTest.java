package edu.mgupi.pass.face.gui.template;

import javax.swing.JDialog;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.face.gui.AppHelper;
import edu.mgupi.pass.face.gui.SwingTestHelper;

public class TableEditorTemplateTest {

	private TableEditorTemplate editor = null;

	@Before
	public void setUp() throws Exception {
		editor = (TableEditorTemplate) AppHelper.getInstance().getDialogImpl(TableEditorTemplate.class);
		editor.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
	}

	@After
	public void tearDown() throws Exception {
		SwingTestHelper.closeAllWindows();
	}

	@Test
	public void testOpendDialog() throws Exception {
		editor.setVisible(true);
		//SwingTestHelper.showMeBackground(editor);

		SwingTestHelper.waitMe(editor);

	}

}
