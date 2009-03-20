package edu.mgupi.pass.face;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.swing.JButton;
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

import edu.mgupi.pass.modules.TestModule;
import edu.mgupi.pass.modules.TestModule2;
import edu.mgupi.pass.modules.basic.SimpleMatrixModule;
import edu.mgupi.pass.sources.TestSourceImpl;

public class MainFrameTest {

	private final static Logger logger = LoggerFactory.getLogger(MainFrameTest.class);

	private MainFrame frame;
	private TestSourceImpl source;

	@Before
	public void setUp() throws Exception {
		frame = (MainFrame) AppHelper.getInstance().openWindow(MainFrame.class);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		source = new TestSourceImpl();
		source.init();

	}

	@After
	public void tearDown() throws Exception {
		if (source != null) {
			source.close();
			source = null;
		}
		if (frame != null) {
			SwingTestHelper.addWork(new WorkSet() {
				public void workImpl() throws Exception {
					frame.setVisible(false);
					frame.dispose();
				}
			});

			frame = null;
		}
		AppHelper.reset();
	}

	@Test
	public void testLaFCycling() throws Exception {
		for (final LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
			SwingTestHelper.addWork(new WorkSet() {
				public void workImpl() throws Exception {
					logger.debug("Applying class " + laf.getClassName());
					AppHelper.getInstance().updateUI(laf.getClassName());
				}
			});

		}
	}

	@Test
	public void testOpenImage() throws Exception {
		SwingTestHelper.addWork(new WorkSet() {
			public void workImpl() throws Exception {
				AppHelper.getInstance().updateUI(UIManager.getCrossPlatformLookAndFeelClassName());
			}
		});

		JMenuItem open = (JMenuItem) SwingTestHelper.getChildNamed(frame, "open");
		assertNotNull(open);

		this.frame.startProcessing(source.getSingleSource());

		final JCheckBox fitButton = (JCheckBox) SwingTestHelper.getChildNamed(frame, "fitButton");
		assertNotNull(fitButton);
		fitButton.setSelected(false);

		// Fit image to window size
		SwingTestHelper.addWork(new WorkSet() {
			public void workImpl() throws Exception {
				fitButton.doClick();
			}
		});

		SwingTestHelper.addWork(new WorkSet() {
			public void workImpl() throws Exception {
				frame.setBounds(100, 200, 1024, 768);
			}
		});

		SwingTestHelper.addWork(new WorkSet() {
			public void workImpl() throws Exception {
				frame.setBounds(100, 200, 600, 600);
			}
		});

		// Return default style
		SwingTestHelper.addWork(new WorkSet() {
			public void workImpl() throws Exception {
				fitButton.doClick();
			}
		});

		SwingTestHelper.addWork(new WorkSet() {
			public void workImpl() throws Exception {
				frame.setBounds(100, 200, 1024, 768);
			}
		});

		SwingTestHelper.addWork(new WorkSet() {
			public void workImpl() throws Exception {
				frame.setBounds(100, 200, 600, 600);
			}
		});

		//
		// this.addWork(new WorkSet() {
		// public void work() throws Exception {
		//
		// addWorkNoWait(new WorkSet() {
		// public void work() throws Exception {
		// open.doClick();
		// }
		//
		// });
		//
		// System.out.println("Dispatched!!!!!!!!!!!!");
		//
		// final SingleFilePick pick = (SingleFilePick)
		// SwingTestHelper.getReflectedAccess(frame,
		// "singleFilePicker");
		// assertNotNull(pick);
		//
		// final JFileChooser chooser = (JFileChooser)
		// SwingTestHelper.getReflectedAccess(pick, "chooser");
		// assertNotNull(chooser);
		//				
		// chooser.dispatchEvent(new KeyEvent(chooser, KeyEvent.KEY_RELEASED,
		// System.currentTimeMillis(), 0,
		// KeyEvent.VK_UNDEFINED, (char) 27, KeyEvent.KEY_LOCATION_STANDARD));
		//
		// }
		// });
	}

	@Test
	public void testSettings() throws Exception {

		final JMenuItem settings = (JMenuItem) SwingTestHelper.getChildNamed(frame, "settings");
		assertNotNull(settings);

		SwingTestHelper.addWorkNoWait(new WorkSet() {
			public void workImpl() throws Exception {
				settings.doClick();
			}
		});

		SwingTestHelper.waitUntil(new ConditionSet() {
			public boolean keepWorking() {
				return AppHelper.getInstance().searchWindow(SettingsDialog.class) == null;
			}
		});
		assertNotNull(AppHelper.getInstance().searchWindow(SettingsDialog.class));
		final JButton cancel = (JButton) SwingTestHelper.getChildNamed(AppHelper.getInstance().searchWindow(
				SettingsDialog.class), "cancel");
		assertNotNull(cancel);

		SwingTestHelper.addWork(new WorkSet() {
			public void workImpl() throws Exception {
				cancel.doClick();
			}
		});
	}

	@Test
	public void testMainHelpfulWindows() throws Exception {

		assertFalse(this.frame.histogramFrame.hasImage());
		this.frame.startProcessing(source.getSingleSource());
		assertTrue(this.frame.histogramFrame.hasImage());

		final JCheckBox histogram = (JCheckBox) SwingTestHelper.getChildNamed(frame, "histogram");
		assertNotNull(histogram);
		histogram.setSelected(false);

		SwingTestHelper.addWork(new WorkSet() {
			public void workImpl() throws Exception {
				histogram.doClick();
			}
		});

		assertNotNull(frame.histogramFrame);
		assertTrue(frame.histogramFrame.isVisible());

		SwingTestHelper.addWork(new WorkSet() {
			public void workImpl() throws Exception {
				frame.histogramFrame.setLocation(300, 400);
			}
		});

		final JCheckBox moduleImage = (JCheckBox) SwingTestHelper.getChildNamed(frame, "moduleImage");
		assertNotNull(moduleImage);
		moduleImage.setSelected(false);

		SwingTestHelper.addWork(new WorkSet() {
			public void workImpl() throws Exception {
				moduleImage.doClick();
			}
		});

		assertNotNull(frame.moduleFrame);
		assertTrue(frame.moduleFrame.isVisible());

		SwingTestHelper.addWork(new WorkSet() {
			public void workImpl() throws Exception {
				frame.moduleFrame.setLocation(300, 600);
			}
		});

	}

	@Test
	public void testChangeModulesPostModule() throws Exception {
		assertFalse(this.frame.moduleFrame.hasImage());
		this.frame.startProcessing(source.getSingleSource());

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

		this.frame.startProcessing(source.getSingleSource());
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

		this.frame.startProcessing(source.getSingleSource());
		assertFalse(this.frame.moduleFrame.hasImage());

	}
}
