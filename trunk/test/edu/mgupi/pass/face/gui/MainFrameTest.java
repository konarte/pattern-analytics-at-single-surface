package edu.mgupi.pass.face.gui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.inputs.TestInputImpl;
import edu.mgupi.pass.modules.TestModule;
import edu.mgupi.pass.modules.TestModule2;
import edu.mgupi.pass.modules.basic.SimpleMatrixModule;
import edu.mgupi.pass.util.Config;
import edu.mgupi.pass.util.Utils;
import edu.mgupi.pass.util.WaitCondition;
import edu.mgupi.pass.util.WorkSet;

public class MainFrameTest {

	private final static Logger logger = LoggerFactory.getLogger(MainFrameTest.class);

	private MainFrame frame;
	private TestInputImpl source;

	@Before
	public void setUp() throws Exception {
		Config.setDebugInstance();

		frame = (MainFrame) AppHelper.getInstance().getFrameImpl(MainFrame.class);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		source = new TestInputImpl();
		source.init();

	}

	@After
	public void tearDown() throws Exception {
		if (source != null) {
			source.close();
			source = null;
		}
		SwingTestHelper.closeAllWindows();
		AppHelper.reset();
	}

	@Test
	public void testLaFCycling() throws Exception {
		for (final LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
			SwingTestHelper.addWorkAndWaitForTheEnd(new WorkSet() {
				public void workImpl() throws Exception {
					logger.debug("Applying class " + laf.getClassName());
					AppHelper.getInstance().updateUI(laf.getClassName());
				}
			});

		}
	}

	@Test
	public void testOpenImage() throws Exception {
		SwingTestHelper.addWorkAndWaitForTheEnd(new WorkSet() {
			public void workImpl() throws Exception {
				AppHelper.getInstance().updateUI(UIManager.getCrossPlatformLookAndFeelClassName());
			}
		});

		JMenuItem open = (JMenuItem) Utils.getChildNamed(frame, MainFrame.Actions.OPEN.name());
		assertNotNull(open);

		SwingTestHelper.clickOpenDialogButton(frame, MainFrame.Actions.OPEN.name());
		SwingTestHelper.clickFileChooserCancel(frame);

		SwingTestHelper.clickOpenDialogButton(frame, MainFrame.Actions.OPEN.name());

		assertFalse(frame.isProcessStarted());
		SwingTestHelper.clickFileChooserOK(frame, new File("test/suslik.jpg"));

		// Frame must start!
		// If not -- check fileChooser
		SwingTestHelper.waitUntil(new WaitCondition() {
			@Override
			public boolean keepWorking() {
				return frame.isProcessStarted() == false;
			}
		});

		final JCheckBox scaleButton = (JCheckBox) Utils.getChildNamed(frame, "scaleButton");
		assertNotNull(scaleButton);
		assertFalse(scaleButton.isSelected());

		// Fit image to window size
		SwingTestHelper.addWorkAndWaitForTheEnd(new WorkSet() {
			public void workImpl() throws Exception {
				scaleButton.doClick();
			}
		});

		SwingTestHelper.addWorkAndWaitForTheEnd(new WorkSet() {
			public void workImpl() throws Exception {
				frame.setBounds(100, 200, 1024, 768);
			}
		});

		SwingTestHelper.addWorkAndWaitForTheEnd(new WorkSet() {
			public void workImpl() throws Exception {
				frame.setBounds(100, 200, 600, 600);
			}
		});

		// Return default style
		SwingTestHelper.addWorkAndWaitForTheEnd(new WorkSet() {
			public void workImpl() throws Exception {
				scaleButton.doClick();
			}
		});

		SwingTestHelper.addWorkAndWaitForTheEnd(new WorkSet() {
			public void workImpl() throws Exception {
				frame.setBounds(100, 200, 1024, 768);
			}
		});

		SwingTestHelper.addWorkAndWaitForTheEnd(new WorkSet() {
			public void workImpl() throws Exception {
				frame.setBounds(100, 200, 600, 600);
			}
		});

	}

	@Test
	public void testCloseImage() throws Exception {
		final JMenuItem close = (JMenuItem) Utils.getChildNamed(frame, MainFrame.Actions.CLOSE.name());
		assertNotNull(close);

		assertFalse(frame.isProcessStarted());
		SwingTestHelper.addWorkAndWaitForTheEnd(new WorkSet() {
			public void workImpl() throws Exception {
				close.doClick();
			}
		});
		assertFalse(frame.isProcessStarted());

		SwingTestHelper.clickOpenDialogButton(frame, MainFrame.Actions.OPEN.name());
		SwingTestHelper.clickFileChooserOK(frame, new File("test/suslik.jpg"));

		// Frame must start!
		// If not -- check fileChooser
		SwingTestHelper.waitUntil(new WaitCondition() {
			@Override
			public boolean keepWorking() {
				return frame.isProcessStarted() == false;
			}
		});

		SwingTestHelper.addWorkAndWaitForTheEnd(new WorkSet() {
			public void workImpl() throws Exception {
				close.doClick();
			}
		});

		assertFalse(frame.isProcessStarted());
	}

	@Test
	public void testSettings() throws Exception {

		SwingTestHelper.clickOpenDialogButton(frame, MainFrame.Actions.SETTINGS.name(), SettingsDialog.class);

		SwingTestHelper.clickCloseDialogButton((SettingsDialog) AppHelper.getInstance().searchWindow(
				SettingsDialog.class), "cancel");

	}

	@Test
	public void testMainHelpfulWindows() throws Exception {

		assertFalse(this.frame.histogramFrame.hasImage());
		this.frame.startProcessingImpl(source.getSingleSource());
		assertTrue(this.frame.histogramFrame.hasImage());

		final JCheckBox histogram = (JCheckBox) Utils.getChildNamed(frame, "histogram");
		assertNotNull(histogram);
		assertFalse(histogram.isSelected());

		SwingTestHelper.addWorkAndWaitForTheEnd(new WorkSet() {
			public void workImpl() throws Exception {
				histogram.doClick();
			}
		});

		assertNotNull(frame.histogramFrame);
		assertTrue(frame.histogramFrame.isVisible());

		SwingTestHelper.addWorkAndWaitForTheEnd(new WorkSet() {
			public void workImpl() throws Exception {
				frame.histogramFrame.setLocation(300, 400);
			}
		});

		final JCheckBox moduleImage = (JCheckBox) Utils.getChildNamed(frame, "moduleImage");
		assertNotNull(moduleImage);
		assertFalse(moduleImage.isSelected());

		SwingTestHelper.addWorkAndWaitForTheEnd(new WorkSet() {
			public void workImpl() throws Exception {
				moduleImage.doClick();
			}
		});

		assertNotNull(frame.moduleFrame);
		assertTrue(frame.moduleFrame.isVisible());

		SwingTestHelper.addWorkAndWaitForTheEnd(new WorkSet() {
			public void workImpl() throws Exception {
				frame.moduleFrame.setLocation(300, 600);
			}
		});

		SwingTestHelper.addWorkAndWaitForTheEnd(new WorkSet() {
			public void workImpl() throws Exception {
				histogram.doClick();
				moduleImage.doClick();
			}
		});

		assertFalse(frame.histogramFrame.isVisible());
		assertFalse(frame.moduleFrame.isVisible());

	}

	@Test
	public void testChangeModulesPostModule() throws Exception {
		assertFalse(this.frame.moduleFrame.hasImage());
		this.frame.startProcessingImpl(source.getSingleSource());

		frame.setModule(SimpleMatrixModule.class);
		assertTrue(this.frame.moduleFrame.hasImage());

		this.frame.setModule(TestModule.class);
		assertTrue(this.frame.moduleFrame.hasImage());

		this.frame.setModule(TestModule2.class);
		assertFalse(this.frame.moduleFrame.hasImage());

		this.frame.setModule(TestModule.class);
		assertTrue(this.frame.moduleFrame.hasImage());

		this.frame.setModule(TestModule2.class);
		assertFalse(this.frame.moduleFrame.hasImage());

	}

	@Test
	public void testChangeModulesPreModule() throws Exception {
		this.frame.setModule(SimpleMatrixModule.class);
		assertFalse(this.frame.moduleFrame.hasImage());

		this.frame.setModule(TestModule.class);
		assertFalse(this.frame.moduleFrame.hasImage());

		this.frame.startProcessingImpl(source.getSingleSource());
		assertTrue(this.frame.moduleFrame.hasImage());

	}

	@Test
	public void testChangeModulesPreModule2() throws Exception {

		this.frame.setModule(TestModule.class);
		assertFalse(this.frame.moduleFrame.hasImage());

		this.frame.setModule(SimpleMatrixModule.class);
		assertFalse(this.frame.moduleFrame.hasImage());

		this.frame.setModule(TestModule2.class);
		assertFalse(this.frame.moduleFrame.hasImage());

		this.frame.startProcessingImpl(source.getSingleSource());
		assertFalse(this.frame.moduleFrame.hasImage());

	}
}
