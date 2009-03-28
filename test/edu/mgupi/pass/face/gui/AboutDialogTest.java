package edu.mgupi.pass.face.gui;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.util.Utils;

public class AboutDialogTest {
	private AboutDialog dialog = null;

	@Before
	public void setUp() throws Exception {
		dialog = new AboutDialog(null);

	}

	@After
	public void tearDown() throws Exception {
		SwingTestHelper.closeAllWindows();
	}

	@Test
	public void testUI() throws Exception {

		SwingTestHelper.showMeBackground(this.dialog);

		JTable props = (JTable) Utils.getChildNamed(dialog, "properties");
		assertNotNull(props);
		TableModel model = props.getModel();
		int rows = model.getRowCount();

		assertTrue(rows > 0);

		JTable libs = (JTable) Utils.getChildNamed(dialog, "libraries");
		assertNotNull(libs);
		model = libs.getModel();
		rows = model.getRowCount();

		assertTrue(rows > 0);

		final JLabel authors = (JLabel) Utils.getChildNamed(dialog, "authors");
		assertNotNull(authors);

		// Seems there is no listeners to Desktop or DesktopPeer :(
		// Can't check success of opening link

		//		AWTEventListener listener = new AWTEventListener() {
		//			@Override
		//			public void eventDispatched(AWTEvent event) {
		//				System.out.println("EVENT RECEIVED: " + event);
		//
		//			}
		//		};
		//		Toolkit.getDefaultToolkit().addAWTEventListener(listener, Event.ACTION_EVENT);

		//		authors.dispatchEvent(new MouseEvent(authors, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(),
		//				MouseEvent.BUTTON1, 1, 1, 1, false));

		// OK button must close dialog!

		SwingTestHelper.clickCloseDialogButton(dialog, "cancel");

	}

}
