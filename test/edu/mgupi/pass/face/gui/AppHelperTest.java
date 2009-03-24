package edu.mgupi.pass.face.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Window;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.util.WaitCondition;
import edu.mgupi.pass.util.SwingHelper;
import edu.mgupi.pass.util.WorkSet;

public class AppHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		AppHelper.reset();
	}

	@Test
	public void testInstance() {
		AppHelper helper = AppHelper.getInstance();
		assertTrue(helper == AppHelper.getInstance());
	}

	private SplashWindow splash = null;
	private SplashWindow splash2 = null;

	@Test
	public void testCreateWindow() throws Exception {
		splash = null;
		splash2 = null;

		SwingHelper.addWorkAndWaitThis(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				splash = (SplashWindow) AppHelper.getInstance().registerAdditionalWindow(SplashWindow.class);
				splash2 = (SplashWindow) AppHelper.getInstance().registerAdditionalWindow(SplashWindow.class);
			}
		}, new WaitCondition() {
			@Override
			public boolean keepWorking() {
				return splash == null || splash2 == null;
			}
		});

		assertNotNull(splash);
		assertFalse(splash.isVisible());
		assertNull(AppHelper.getInstance().searchWindow(SplashWindow.class));

		assertNotNull(splash2);
		assertFalse(splash2.isVisible());
		assertFalse(splash == splash2);

		splash.dispose();
		splash2.dispose();
	}

	@Test
	public void testOpenWindow() throws Exception {
		splash = null;

		SwingHelper.addWorkAndWaitThis(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				splash = (SplashWindow) AppHelper.getInstance().getFrameImpl(SplashWindow.class);
				splash.setVisible(true);

			}
		}, new WaitCondition() {
			@Override
			public boolean keepWorking() {
				return splash == null || !splash.isVisible();
			}
		});

		assertNotNull(splash);
		assertNotNull(AppHelper.getInstance().searchWindow(SplashWindow.class));

		assertTrue(splash == AppHelper.getInstance().getFrameImpl(SplashWindow.class));

		splash.setVisible(false);
		splash.dispose();

	}

	@Test
	public void testOpenWindowFrame() throws Exception {
		splash = null;

		assertNull(AppHelper.getInstance().searchWindow(SplashWindow.class));
		SwingHelper.addWorkAndWaitThis(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				splash = (SplashWindow) AppHelper.getInstance().getFrameImpl(SplashWindow.class);
				splash.setVisible(true);

			}
		}, new WaitCondition() {
			@Override
			public boolean keepWorking() {
				return splash == null || !splash.isVisible();
			}
		});
		assertNotNull(splash);
		assertTrue(splash == AppHelper.getInstance().getFrameImpl(SplashWindow.class));

		assertNull(AppHelper.getInstance().searchWindow(AboutDialog.class));

		SwingHelper.addWorkAndWaitThis(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				AppHelper.getInstance().getDialogImpl(AboutDialog.class).setVisible(true);

			}
		}, new WaitCondition() {
			@Override
			public boolean keepWorking() {
				return AppHelper.getInstance().searchWindow(AboutDialog.class) == null
						|| !AppHelper.getInstance().searchWindow(AboutDialog.class).isVisible();
			}
		});

		Window about = AppHelper.getInstance().searchWindow(AboutDialog.class);
		assertNotNull(about);

		about.setVisible(false);
		about.dispose();

		splash.setVisible(false);
		splash.dispose();

	}

	@Test
	public void testOpenWindowFrameIncorrect() throws Exception {
		splash = null;
		final SplashWindow mySplash = (SplashWindow) AppHelper.getInstance().registerAdditionalWindow(
				SplashWindow.class);
		assertNotNull(mySplash);

		//		try {
		SwingHelper.addWorkAndWaitForTheEnd(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				splash = (SplashWindow) AppHelper.getInstance().getWindowImpl(SplashWindow.class, true);
				splash.setVisible(true);
			}
		});
		assertNotNull(splash);
		assertFalse(splash == mySplash);
		//			fail("No exception thrown!");
		//		} catch (RuntimeException e) {
		//			if (e.getCause() != null && e.getCause().getClass() == NoSuchMethodException.class) {
		//				System.out.println("Received expected exception: " + e.getCause());
		//			} else {
		//				throw e;
		//			}
		//		}

		//		try {

		SwingTestHelper.showMeBackground(AppHelper.getInstance().getDialogImpl(AboutDialog.class));
		AboutDialog about = (AboutDialog) AppHelper.getInstance().searchWindow(AboutDialog.class);
		assertNotNull(about);
		about.setVisible(false);
		about.dispose();

		//			fail("No exception thrown!");
		//		} catch (RuntimeException e) {
		//			if (e.getCause() != null && e.getCause().getClass() == InstantiationException.class) {
		//				System.out.println("Received expected exception: " + e.getCause());
		//			} else {
		//				throw e;
		//			}
		//		}

		mySplash.setVisible(false);
		mySplash.dispose();

		splash.setVisible(false);
		splash.dispose();

	}

	private MyFrame commonWindow = null;
	private MySplash mySplash = null;
	private MyFrame registeredWindow = null;
	private MyFrame registeredComp1 = null;
	private MyFrame registeredComp2 = null;

	@Test
	public void testUpdateUIComplex() throws Exception {
		commonWindow = null;
		mySplash = null;
		registeredWindow = null;
		registeredComp1 = null;
		registeredComp2 = null;

		final LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
		assertTrue(looks.length > 0);
		SwingHelper.addWorkAndWaitForTheEnd(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				AppHelper.getInstance().updateUI(looks[0].getClassName());
			}
		});

		SwingHelper.addWorkAndWaitThis(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				commonWindow = (MyFrame) AppHelper.getInstance().getFrameImpl(MyFrame.class);
				mySplash = (MySplash) AppHelper.getInstance().getFrameImpl(MySplash.class);
				commonWindow.setVisible(true);
				mySplash.setVisible(true);
			}
		}, new WaitCondition() {
			@Override
			public boolean keepWorking() {
				return commonWindow == null || mySplash == null || !commonWindow.isVisible() || !mySplash.isVisible();
			}
		});

		assertEquals(0, commonWindow.repaintCount);
		assertEquals(0, mySplash.repaintCount);

		SwingHelper.addWorkAndWaitForTheEnd(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				AppHelper.getInstance().updateUI(looks[1].getClassName());
			}
		});

		assertEquals(1, commonWindow.repaintCount);
		assertEquals(1, mySplash.repaintCount);

		SwingHelper.addWorkAndWaitThis(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				registeredWindow = (MyFrame) AppHelper.getInstance().registerAdditionalWindow(MyFrame.class);
				registeredComp1 = (MyFrame) AppHelper.getInstance().registerAdditionalComponent(new MyFrame());
				registeredComp2 = (MyFrame) AppHelper.getInstance().registerAdditionalComponent(new MyFrame());
			}
		}, new WaitCondition() {
			@Override
			public boolean keepWorking() {
				return registeredComp1 == null || registeredComp2 == null || registeredWindow == null;
			}
		});

		assertEquals(0, registeredWindow.repaintCount);
		assertEquals(0, registeredComp1.repaintCount);
		assertEquals(0, registeredComp2.repaintCount);

		SwingHelper.addWorkAndWaitForTheEnd(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				AppHelper.getInstance().updateUI(looks[0].getClassName());
			}
		});

		assertEquals(2, commonWindow.repaintCount);
		assertEquals(2, mySplash.repaintCount);
		assertEquals(1, registeredWindow.repaintCount);
		assertEquals(1, registeredComp1.repaintCount);
		assertEquals(1, registeredComp2.repaintCount);

		AppHelper.getInstance().unregisterAdditionalComponent(registeredComp1);

		SwingHelper.addWorkAndWaitForTheEnd(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				AppHelper.getInstance().updateUI(looks[1].getClassName());
			}
		});

		assertEquals(3, commonWindow.repaintCount);
		assertEquals(3, mySplash.repaintCount);
		assertEquals(2, registeredWindow.repaintCount);
		assertEquals(1, registeredComp1.repaintCount);
		assertEquals(2, registeredComp2.repaintCount);

		AppHelper.reset();
		SwingHelper.addWorkAndWaitForTheEnd(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				AppHelper.getInstance().updateUI(looks[0].getClassName());
			}
		});
		assertEquals(3, commonWindow.repaintCount);
		assertEquals(3, mySplash.repaintCount);
		assertEquals(2, registeredWindow.repaintCount);
		assertEquals(1, registeredComp1.repaintCount);
		assertEquals(2, registeredComp2.repaintCount);

		commonWindow.setVisible(false);
		commonWindow.dispose();

		mySplash.setVisible(false);
		mySplash.dispose();

		registeredWindow.setVisible(false);
		registeredWindow.dispose();

		registeredComp1.setVisible(false);
		registeredComp1.dispose();

		registeredComp2.setVisible(false);
		registeredComp2.dispose();

	}

	@Test
	public void testShowExceptionDialog() throws Exception {
		AppHelper.getInstance().updateUI(UIManager.getSystemLookAndFeelClassName());

		//		final MyFrame testFrame = (MyFrame) AppHelper.getInstance().registerAdditionalWindow(MyFrame.class);
		//		assertNotNull(testFrame);

		try {
			SwingHelper.addWorkAndWaitForTheEnd(new WorkSet() {
				@Override
				public void workImpl() throws Exception {
					AppHelper.showExceptionDialog("Fuck you fucking fuck. Wait 5 sec...", new Exception(
							"I'm dead already :(", new RuntimeException("Stars will show me the way...")));
				}
			});
			fail("No exception thrown!");
		} catch (RuntimeException e) {
			if (e.getCause() == null) {
				System.out.println("Received expected exception: " + e);
				SwingTestHelper.closeAllWindows();
			} else {
				throw e;
			}
		}

		//		testFrame.dispose();
		//		testFrame.setVisible(false);

	}

	static class MyFrame extends JFrame {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MyFrame() {
			super();
			this.setBounds(100, 100, 500, 300);
		}

		protected int repaintCount = 0;

		public void repaint() {
			super.repaint();
			this.repaintCount++;
		}
	}

	static class MySplash extends SplashWindow {
		public MySplash() throws IOException {
			super();
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		protected int repaintCount = 0;

		public void repaint() {
			super.repaint();
			this.repaintCount++;
		}
	}

}
