package edu.mgupi.pass.face;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.util.Config;

public class SettingsDialogTest {

	private SettingsDialog dialog = null;

	@Before
	public void setUp() throws Exception {
		Config.getInstance().setReadOnly();
		dialog = (SettingsDialog) AppHelper.getInstance().getDialogImpl(SettingsDialog.class);
	}

	@After
	public void tearDown() throws Exception {
		if (dialog != null) {
			dialog.setVisible(false);
			dialog.dispose();
			dialog = null;
		}
	}

	@Test
	public void testOpenDialog() throws Exception {
		SwingTestHelper.showMeBackground(dialog);
	}

}
