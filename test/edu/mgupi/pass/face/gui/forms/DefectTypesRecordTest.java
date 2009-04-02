package edu.mgupi.pass.face.gui.forms;

import java.io.File;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.face.gui.AppHelper;
import edu.mgupi.pass.face.gui.SwingTestHelper;
import edu.mgupi.pass.face.gui.forms.DefectTypesRecord;
import edu.mgupi.pass.util.Config;

public class DefectTypesRecordTest {

	@Before
	public void setUp() throws Exception {
		Config.getInstance().setDebugVirualMode();
	}

	@After
	public void tearDown() throws Exception {
		SwingTestHelper.closeAllWindows();
	}

	@Test
	public void testOpen() throws Exception {

		DefectTypesRecord dtf = (DefectTypesRecord) AppHelper.getInstance().getDialogImpl(
				DefectTypesRecord.class);
		AppHelper.getInstance().updateUI(javax.swing.UIManager.getSystemLookAndFeelClassName());
		//		AppHelper.getInstance().updateUI(com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel.class.getName());
		//		AppHelper.getInstance().updateUI(com.sun.java.swing.plaf.motif.MotifLookAndFeel.class.getName());

		dtf.getFormPanel().getImagePanel().setImage(ImageIO.read(new File("test/suslik_list.jpg")));
		//		dtf.setVisible(true);

		SwingTestHelper.showMeBackground(new DefectTypesRecord(null));
		//DefectTypesRecord.addRecord(DefectTypesFactory.createDefectTypes());
		//assertTrue(rec == rec.rec);

	}
}
