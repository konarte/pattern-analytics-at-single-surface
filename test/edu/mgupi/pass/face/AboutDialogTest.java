package edu.mgupi.pass.face;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AboutDialogTest {
	private AboutDialog dialog = null;

	@Before
	public void setUp() throws Exception {
		dialog = new AboutDialog(null);

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
	public void testUI() throws Exception {

		SwingTestHelper.showMeBackground(this.dialog);

		final JButton ok = (JButton) SwingHelper.getChildNamed(dialog, "ok");
		assertNotNull(ok);

		final JTable props = (JTable) SwingHelper.getChildNamed(dialog, "properties");
		assertNotNull(props);
		TableModel model = props.getModel();
		int rows = model.getRowCount();

		assertTrue(rows > 0);

		JTable libs = (JTable) SwingHelper.getChildNamed(dialog, "libraries");
		assertNotNull(libs);
		model = libs.getModel();
		rows = model.getRowCount();

		assertTrue(rows > 0);

	}

}
