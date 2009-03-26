package edu.mgupi.pass.face.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.filters.Param;
import edu.mgupi.pass.filters.Param.ParamType;
import edu.mgupi.pass.util.WaitCondition;
import edu.mgupi.pass.util.WorkSet;

public class SwingTestHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		SwingTestHelper.closeAllWindows();
	}

	private volatile boolean keepWorking = false;

	@Test
	public void testAddWorkAndWaitThis() throws Exception {
		keepWorking = true;
		final long start = System.currentTimeMillis();
		SwingTestHelper.addWorkAndWaitThis(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				while (keepWorking) {
					Thread.sleep(100);
				}
			}
		}, new WaitCondition() {
			@Override
			public boolean keepWorking() {
				return (System.currentTimeMillis() - start) < 500;
			}
		});
		keepWorking = false;
		assertTrue(System.currentTimeMillis() - start >= 500);

		try {
			SwingTestHelper.addWorkAndWaitThis(null, null);
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}

		try {
			SwingTestHelper.addWorkAndWaitThis(new WorkSet() {
				@Override
				public void workImpl() throws Exception {
					//
				}
			}, null);
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}

		try {
			SwingTestHelper.addWorkAndWaitThis(null, new WaitCondition() {
				@Override
				public boolean keepWorking() {
					return false;
				}
			});
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}
	}

	@Test
	public void testAddWorkAndWaitForTheEnd() throws Exception {
		final long start = System.currentTimeMillis();
		SwingTestHelper.addWorkAndWaitForTheEnd(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				Thread.sleep(500);
			}
		});
		assertTrue(System.currentTimeMillis() - start >= 500);

		try {
			SwingTestHelper.addWorkAndWaitForTheEnd(null);
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}
	}

	@Test
	public void testWaitUntil() throws InterruptedException {
		final long start = System.currentTimeMillis();
		SwingTestHelper.waitUntil(new WaitCondition() {
			@Override
			public boolean keepWorking() {
				return (System.currentTimeMillis() - start) < 500;
			}
		});
		assertTrue(System.currentTimeMillis() - start >= 500);

		try {
			SwingTestHelper.waitUntil(null);
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}
	}

	@Test
	public void testShowMeBackground() throws Exception {
		JDialog myDialog = new JDialog((Frame) null, true);
		final long start = System.currentTimeMillis();

		SwingTestHelper.showMeBackground(myDialog);

		assertTrue(System.currentTimeMillis() - start < 5000);
		myDialog.setVisible(false);

		try {
			SwingTestHelper.showMeBackground(null);
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}
	}

	@Test
	public void testWaitMe() throws Exception {
		JDialog myDialog = new JDialog((Frame) null, true);
		SwingTestHelper.showMeBackground(myDialog);
		myDialog.setVisible(false);

		final long start = System.currentTimeMillis();

		SwingTestHelper.waitMe(myDialog);
		assertTrue(System.currentTimeMillis() - start < 5000);
		myDialog.setVisible(false);

		try {
			SwingTestHelper.waitMe(null);
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}
	}

	@Test
	public void testCloseAllWindows() throws Exception {
		JDialog myDialog = new JDialog((Frame) null, true);
		SwingTestHelper.showMeBackground(myDialog);

		assertTrue(myDialog.isVisible());

		SwingTestHelper.closeAllWindows();

		assertFalse(myDialog.isVisible());
	}

	@Test
	public void testAllChildrenClosed() throws Exception {
		JFrame myFrame = new JFrame();
		assertTrue(SwingTestHelper.allChildrenClosed(myFrame));

		JDialog myDialog = new JDialog(myFrame, true);
		SwingTestHelper.showMeBackground(myDialog);

		assertFalse(SwingTestHelper.allChildrenClosed(myFrame));
		SwingTestHelper.closeAllWindows();
		assertTrue(SwingTestHelper.allChildrenClosed(myFrame));

		try {
			SwingTestHelper.allChildrenClosed(null);
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}
	}

	@Test
	public void testSearchAnyOpenedWindow() throws Exception {
		JFrame myFrame = new JFrame();
		assertNull(SwingTestHelper.searchAnyOpenedWindow(myFrame));

		JDialog myDialog = new JDialog(myFrame, true);
		SwingTestHelper.showMeBackground(myDialog);

		assertTrue(myDialog == SwingTestHelper.searchAnyOpenedWindow(myFrame));
		SwingTestHelper.closeAllWindows();
		assertNull(SwingTestHelper.searchAnyOpenedWindow(myFrame));

		try {
			SwingTestHelper.searchAnyOpenedWindow(null);
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}
	}

	@Test
	public void testClickCloseDialogButton() throws Exception {
		JFrame myFrame = new JFrame();
		final JDialog myDialog = new JDialog(myFrame, true);

		JButton button = new JButton();
		button.setAction(new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				myDialog.setVisible(false);

			}
		});
		button.setText("OK");
		button.setName("OK");
		myDialog.getRootPane().add(button);

		SwingTestHelper.showMeBackground(myDialog);
		assertTrue(myDialog.isVisible() == true);

		SwingTestHelper.clickCloseDialogButton(myDialog, "OK");
		assertTrue(myDialog.isVisible() == false);

		try {
			SwingTestHelper.clickCloseDialogButton(null, "OK");
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}

		try {
			SwingTestHelper.clickCloseDialogButton(myDialog, null);
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}

		try {
			SwingTestHelper.clickCloseDialogButton(myDialog, "unknown");
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}

		try {
			// This is error!!!
			// myDialog already closed
			SwingTestHelper.clickCloseDialogButton(myDialog, "OK");
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}
	}

	@Test
	public void testOpenDialog() throws Exception {
		JFrame myFrame = new JFrame();

		JDialog myDialog = (JDialog) SwingTestHelper.openDialog(JDialog.class, true);
		assertTrue(myDialog.isVisible());
		assertEquals(0, myFrame.getOwnedWindows().length);
		myDialog.setVisible(false);

		myDialog = (JDialog) SwingTestHelper.openDialog(JDialog.class, false);
		assertTrue(myDialog.isVisible());
		myDialog.setVisible(false);

		try {
			// This is error!!!
			// myDialog initialized already
			myDialog = (JDialog) SwingTestHelper.openDialog(JDialog.class, true);
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}

		try {
			myDialog = (JDialog) SwingTestHelper.openDialog(null, true);
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}
	}

	private JDialog tempDialog = null;

	@Test
	public void testClickOpenDialogButtonWindowString() throws Exception {

		final JFrame myFrame = new JFrame();

		JButton button = new JButton();
		button.setAction(new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				tempDialog = new JDialog(myFrame, true);
				tempDialog.setVisible(true);

			}
		});
		button.setText("OK");
		button.setName("OK");
		myFrame.getRootPane().add(button);

		SwingTestHelper.clickOpenDialogButton(myFrame, "OK");

		assertNotNull(tempDialog);
		assertTrue(tempDialog.isVisible());

		try {
			SwingTestHelper.clickOpenDialogButton(null, null);
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}

		try {
			SwingTestHelper.clickOpenDialogButton(null, "OK");
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}

		try {
			SwingTestHelper.clickOpenDialogButton(myFrame, null);
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}

		try {
			SwingTestHelper.clickOpenDialogButton(myFrame, "unknown");
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}

	}

	@Test
	public void testClickOpenDialogButtonWindowStringClassOfQextendsWindow() throws Exception {
		final JFrame myFrame = new JFrame();

		JButton button = new JButton();
		button.setAction(new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// Main idea -- I must register window through AppHelper 
					final JDialog testDialog = (JDialog) AppHelper.getInstance().getDialogImpl(JDialog.class);
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							testDialog.setVisible(true);
						}
					});

					// But, I wan't to create additional methods, received parent frames 
					tempDialog = new JDialog(myFrame, true);
					tempDialog.setModal(true);
					tempDialog.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		button.setText("OK");
		button.setName("OK");
		myFrame.getRootPane().add(button);

		SwingTestHelper.clickOpenDialogButton(myFrame, "OK", JDialog.class);

		tempDialog = (JDialog) AppHelper.getInstance().searchWindow(JDialog.class);
		assertNotNull(tempDialog);
		assertTrue(tempDialog.isVisible());

		tempDialog.setVisible(false);
		tempDialog = null;

		SwingTestHelper.clickOpenDialogButton(null, "OK", JDialog.class);

		assertNull(tempDialog);
		tempDialog = (JDialog) AppHelper.getInstance().searchWindow(JDialog.class);
		assertNotNull(tempDialog);
		assertTrue(tempDialog.isVisible());

		tempDialog.setVisible(false);
		tempDialog = null;

		SwingTestHelper.clickOpenDialogButton(null, null, JDialog.class);

		assertNull(tempDialog);
		tempDialog = (JDialog) AppHelper.getInstance().searchWindow(JDialog.class);
		assertNotNull(tempDialog);
		assertTrue(tempDialog.isVisible());

		tempDialog.setVisible(false);
		tempDialog = null;

		SwingTestHelper.closeAllWindows();
		SwingTestHelper.clickOpenDialogButton(myFrame, "OK", null);
		assertNotNull(tempDialog);
		assertTrue(tempDialog.isVisible());

		try {
			SwingTestHelper.clickOpenDialogButton(null, null, null);
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}

		try {
			SwingTestHelper.clickOpenDialogButton(myFrame, null, null);
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}

	}

	private Color returnColor = null;

	@Test
	public void testClickColorChooserOK() throws Exception {
		final JFrame myFrame = new JFrame();

		returnColor = Color.YELLOW;

		JButton button = new JButton();
		button.setAction(new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				returnColor = JColorChooser.showDialog(myFrame, "Выбор цвета", Color.RED);

			}
		});
		button.setText("OK");
		button.setName("OK");
		myFrame.getRootPane().add(button);

		SwingTestHelper.clickOpenDialogButton(myFrame, "OK");
		SwingTestHelper.clickColorChooserOK(myFrame, Color.BLUE);
		assertEquals(Color.BLUE, returnColor);

		try {
			SwingTestHelper.clickColorChooserOK(null, null);
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}

		try {
			SwingTestHelper.clickColorChooserOK(null, Color.GREEN);
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}

		try {
			SwingTestHelper.clickColorChooserOK(myFrame, null);
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}

	}

	@Test
	public void testClickColorChooserCancel() throws Exception {
		final JFrame myFrame = new JFrame();

		returnColor = Color.YELLOW;

		JButton button = new JButton();
		button.setAction(new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				returnColor = JColorChooser.showDialog(myFrame, "Выбор цвета", Color.RED);

			}
		});
		button.setText("OK");
		button.setName("OK");
		myFrame.getRootPane().add(button);

		SwingTestHelper.clickOpenDialogButton(myFrame, "OK");
		SwingTestHelper.clickColorChooserCancel(myFrame);
		assertEquals(null, returnColor);

		try {
			SwingTestHelper.clickColorChooserCancel(null);
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}

	}

	private int returnType = 0;
	private File returnFile = null;

	@Test
	public void testClickFileChooserOK() throws Exception {
		final JFrame myFrame = new JFrame();

		returnFile = new File(".");
		returnType = 0;

		JButton button = new JButton();
		button.setAction(new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				returnType = chooser.showOpenDialog(myFrame);
				if (returnType == JFileChooser.APPROVE_OPTION) {
					returnFile = chooser.getSelectedFile();
				}

			}
		});
		button.setText("OK");
		button.setName("OK");
		myFrame.getRootPane().add(button);

		SwingTestHelper.clickOpenDialogButton(myFrame, "OK");
		SwingTestHelper.clickFileChooserOK(myFrame, new File("tmp/suslik.jpg"));

		assertEquals(returnType, JFileChooser.APPROVE_OPTION);
		assertNotNull(returnFile);
		assertEquals("suslik.jpg", returnFile.getName());

		try {
			SwingTestHelper.clickFileChooserOK(null, null);
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}

		try {
			SwingTestHelper.clickFileChooserOK(null, new File("."));
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}

		try {
			SwingTestHelper.clickFileChooserOK(myFrame, null);
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}
	}

	private int returnType2 = 0;
	private File returnFile2 = null;

	@Test
	public void testClickFileChooserCancel() throws Exception {
		final JFrame myFrame = new JFrame();

		returnFile2 = new File(".");
		returnType2 = 0;

		JButton button = new JButton();
		button.setAction(new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				returnType2 = chooser.showOpenDialog(myFrame);
				if (returnType2 == JFileChooser.APPROVE_OPTION) {
					returnFile2 = chooser.getSelectedFile();
				}

			}
		});
		button.setText("OK");
		button.setName("OK");
		myFrame.getRootPane().add(button);

		SwingTestHelper.clickOpenDialogButton(myFrame, "OK");
		SwingTestHelper.clickFileChooserCancel(myFrame);

		assertEquals(returnType2, JFileChooser.CANCEL_OPTION);
		assertNotNull(returnFile2);
		assertEquals(".", returnFile2.getName());

		try {
			SwingTestHelper.clickFileChooserCancel(null);
			fail("No exception thrown!");
		} catch (AssertionError e) {
			System.out.println("Received expected exception: " + e);
		}
	}

	@Test
	public void testGetButtonByActionCommand() {
		final JFrame myFrame = new JFrame();

		JButton button = new JButton();
		button.setText("OK");
		button.setActionCommand("cmd1");
		myFrame.getRootPane().add(button);

		JButton button2 = new JButton();
		button2.setText("OK");
		button2.setActionCommand("cmd2");
		myFrame.getRootPane().add(button2);

		JButton b = SwingTestHelper.getButtonByActionCommand(myFrame, "cmd1");
		assertNotNull(b);
		assertTrue(b == button);

		b = SwingTestHelper.getButtonByActionCommand(myFrame, "cmd2");
		assertNotNull(b);
		assertTrue(b == button2);

		assertNull(SwingTestHelper.getButtonByActionCommand(null, null));
		assertNull(SwingTestHelper.getButtonByActionCommand(myFrame, null));
		assertNull(SwingTestHelper.getButtonByActionCommand(null, "cmd2"));
		assertNull(SwingTestHelper.getButtonByActionCommand(myFrame, "cmd3"));

	}

	@Test
	public void testPrintChildHierarchy() {
		final JFrame myFrame = new JFrame();

		JButton button = new JButton();
		button.setText("OK");
		button.setActionCommand("cmd1");
		myFrame.getRootPane().add(button);

		JButton button2 = new JButton();
		button2.setText("OK");
		button2.setActionCommand("cmd2");
		myFrame.getRootPane().add(button2);

		SwingTestHelper.printChildHierarchy(myFrame);

		SwingTestHelper.printChildHierarchy(null);
	}

	@Test
	public void testGetReflectedFieldAccess() throws Exception {
		Param param = new Param("имя", "Титул", ParamType.INT, 18);

		assertEquals("имя", SwingTestHelper.getReflectedFieldAccess(param, "name"));
		assertEquals("Титул", SwingTestHelper.getReflectedFieldAccess(param, "title"));
		assertEquals(ParamType.INT, SwingTestHelper.getReflectedFieldAccess(param, "type"));
		assertEquals(18, SwingTestHelper.getReflectedFieldAccess(param, "default_"));

		try {
			SwingTestHelper.getReflectedFieldAccess(param, "unknown-type-fff");
			fail("No exception thrown!");
		} catch (NoSuchFieldException e) {
			System.out.println("Received expected exception: " + e);
		}

	}

}
