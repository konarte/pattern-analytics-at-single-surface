package edu.mgupi.pass.face.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Component;
import java.awt.Window;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AppHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		AppHelper.reset();
		SwingTestHelper.closeAllWindows();
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

		SwingTestHelper.addWorkAndWaitThis(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				splash = (SplashWindow) AppHelper.getInstance().registerAdditionalWindow(null,
						SplashWindow.class);
				splash2 = (SplashWindow) AppHelper.getInstance().registerAdditionalWindow(null,
						SplashWindow.class);

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

		assertTrue(AppHelper.getInstance().unregisterAdditionalWindow(splash));

		//splash.dispose();
		splash2.dispose();
	}

	@Test
	public void testOpenWindow() throws Exception {
		splash = null;
		splash = (SplashWindow) SwingTestHelper.openDialog(SplashWindow.class, true);

		assertNotNull(splash);
		assertNotNull(AppHelper.getInstance().searchWindow(SplashWindow.class));

		assertTrue(splash == AppHelper.getInstance().getFrameImpl(null, SplashWindow.class));

		splash.setVisible(false);
		splash.dispose();

	}

	@Test
	public void testOpenWindowFrame() throws Exception {
		splash = null;

		splash = (SplashWindow) SwingTestHelper.openDialog(SplashWindow.class, true);

		assertNotNull(splash);
		assertTrue(splash == AppHelper.getInstance().getFrameImpl(null, SplashWindow.class));

		Window about = SwingTestHelper.openDialog(AboutDialog.class, true);
		assertNotNull(about);

		about.setVisible(false);
		about.dispose();

		splash.setVisible(false);
		splash.dispose();

	}

	@Test
	public void testOpenWindowFrameMore() throws Exception {
		splash = null;
		final SplashWindow mySplash = (SplashWindow) AppHelper.getInstance()
				.registerAdditionalWindow(null, SplashWindow.class);
		assertNotNull(mySplash);

		splash = (SplashWindow) SwingTestHelper.openDialog(SplashWindow.class, true);
		assertNotNull(splash);
		assertFalse(splash == mySplash);

		SwingTestHelper.showMeBackground(AppHelper.getInstance().getDialogImpl(null,
				AboutDialog.class));
		AboutDialog about = (AboutDialog) AppHelper.getInstance().searchWindow(AboutDialog.class);
		assertNotNull(about);
		about.setVisible(false);
		about.dispose();

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
		SwingTestHelper.addWorkAndWaitForTheEnd(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				AppHelper.getInstance().updateUI(looks[0].getClassName());
			}
		});

		commonWindow = (MyFrame) SwingTestHelper.openDialog(MyFrame.class, true);
		mySplash = (MySplash) SwingTestHelper.openDialog(MySplash.class, true);

		assertEquals(0, commonWindow.repaintCount);
		assertEquals(0, mySplash.repaintCount);

		SwingTestHelper.addWorkAndWaitForTheEnd(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				AppHelper.getInstance().updateUI(looks[1].getClassName());
			}
		});

		assertEquals(1, commonWindow.repaintCount);
		assertEquals(1, mySplash.repaintCount);

		SwingTestHelper.addWorkAndWaitThis(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				registeredWindow = (MyFrame) AppHelper.getInstance().registerAdditionalWindow(
						commonWindow, MyFrame.class);
				registeredComp1 = (MyFrame) AppHelper.getInstance().registerAdditionalComponent(
						new MyFrame());
				registeredComp2 = (MyFrame) AppHelper.getInstance().registerAdditionalComponent(
						new MyFrame());
			}
		}, new WaitCondition() {
			@Override
			public boolean keepWorking() {
				return registeredComp1 == null || registeredComp2 == null
						|| registeredWindow == null;
			}
		});

		assertEquals(0, registeredWindow.repaintCount);
		assertEquals(0, registeredComp1.repaintCount);
		assertEquals(0, registeredComp2.repaintCount);

		SwingTestHelper.addWorkAndWaitForTheEnd(new WorkSet() {
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

		SwingTestHelper.addWorkAndWaitForTheEnd(new WorkSet() {
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
		SwingTestHelper.addWorkAndWaitForTheEnd(new WorkSet() {
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
			SwingTestHelper.addWorkAndWaitForTheEnd(new WorkSet() {
				@Override
				public void workImpl() throws Exception {
					AppHelper
							.showExceptionDialog(
									null,
									"Принцип восприятия непредвзято создает паллиативный интеллект, условно. "
											+ "Концепция ментально оспособляет закон внешнего мира. "
											+ "Сомнение раскладывает на элементы неоднозначный структурализм. Wait 5 sec...",
									new Exception(
											"Много много много разного такого разного такого супер-текста. "
													+ "Количество текста не поддается осмыслению. А нам надо проверить, "
													+ "чтобы экран не разосрался вширь.",
											new RuntimeException("Stars will show me the way...")));
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

	}

	@Test
	public void testShowErrorDialog() throws Exception {
		AppHelper.getInstance().updateUI(UIManager.getSystemLookAndFeelClassName());

		//		final MyFrame testFrame = (MyFrame) AppHelper.getInstance().registerAdditionalWindow(MyFrame.class);
		//		assertNotNull(testFrame);

		try {
			SwingTestHelper.addWorkAndWaitForTheEnd(new WorkSet() {
				@Override
				public void workImpl() throws Exception {
					AppHelper
							.showErrorDialog(
									null,
									"Принцип восприятия непредвзято создает паллиативный интеллект, условно. "
											+ "Концепция ментально оспособляет закон внешнего мира. "
											+ "Сомнение раскладывает на элементы неоднозначный структурализм. Wait 5 sec...");
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

	}

	private Random rand = new Random();

	@Test
	public void testThreadedComponents() throws Exception {
		Thread threads[] = new Thread[100];
		for (int i = 0; i < threads.length; i++) {
			final int index = i;
			threads[i] = new Thread(new Runnable() {
				public void run() {
					Collection<Component> componentList = new ArrayList<Component>();
					for (int idx = 0; idx < 10; idx++) {
						Component comp = new Component() {

						};
						componentList.add(comp);
						AppHelper.getInstance().registerAdditionalComponent(comp);
						try {
							Thread.sleep(rand.nextInt(100));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					for (Component comp : componentList) {
						AppHelper.getInstance().unregisterAdditionalComponent(comp);
						try {
							Thread.sleep(rand.nextInt(100));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.println(new Date() + " Thread " + index + " done");
				}
			});

		}
		for (Thread t : threads) {
			t.start();
		}

		SwingTestHelper.waitWhile(new WaitCondition() {
			@Override
			public boolean keepWorking() {
				// We must remove all components before done
				return AppHelper.getInstance().getRegisteredComponentCount() > 0;
			}
		});

	}

	@Test
	public void testThreadedWindows() throws Exception {
		final int TOTAL_COUNT = 100;
		final int INTERNAL_COUNT = 10;

		Thread threads[] = new Thread[TOTAL_COUNT];
		for (int i = 0; i < threads.length; i++) {
			final int index = i;
			threads[i] = new Thread(new Runnable() {
				public void run() {
					for (int idx = 0; idx < INTERNAL_COUNT; idx++) {
						try {
							AppHelper.getInstance().getWindowImpl(null, Window.class, idx % 2 == 0);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						try {
							Thread.sleep(rand.nextInt(100));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.println(new Date() + " Thread " + index + " done");
				}
			});

		}
		for (Thread t : threads) {
			t.start();
		}

		SwingTestHelper.waitWhile(new WaitCondition() {
			@Override
			public boolean keepWorking() {
				// We must remove all components before done
				return AppHelper.getInstance().getCachedWindowsCount() != 1
						|| AppHelper.getInstance().getAdditionalWindowsCount() != TOTAL_COUNT
								* (INTERNAL_COUNT / 2);
			}
		});

	}

	static class MyFrame extends JFrame {
		/**
		 * 
		 */

		public MyFrame() {
			super();
			this.setBounds(100, 100, 500, 300);
		}

		protected int repaintCount = 0;

		@Override
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

		protected int repaintCount = 0;

		@Override
		public void repaint() {
			super.repaint();
			this.repaintCount++;
		}
	}

}
