package edu.mgupi.pass.face.gui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.io.File;
import java.lang.reflect.Field;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.SwingUtilities;

import edu.mgupi.pass.util.Utils;
import edu.mgupi.pass.util.WaitCondition;
import edu.mgupi.pass.util.WorkSet;

public class SwingTestHelper {

	//private final static Logger logger = LoggerFactory.getLogger(SwingTestHelper.class);

	private static volatile int expectedWorkCount = 0;
	private static volatile int workCount = 0;

	private static Throwable addWorkNoWait(final WorkSet actualWork) throws Exception {
		assertNotNull(actualWork);
		expectedWorkCount++;

		final Throwable outputException = new Throwable();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					actualWork.workImpl();
				} catch (Throwable e) {
					outputException.initCause(e);
				} finally {
					workCount++;
				}
			}
		});

		return outputException;
	}

	/**
	 * Append work by {@link SwingUtilities#invokeLater(Runnable)}.
	 * 
	 * Wait until given condition is true.
	 * 
	 * <b>Use if work interrupts execution and you want to wait work
	 * finished</b>
	 * 
	 * @param actualWork
	 * @param waitCondition
	 * 
	 * @throws Exception
	 */
	public static void addWorkAndWaitThis(WorkSet actualWork, WaitCondition waitCondition) throws Exception {
		waitWhile(waitCondition, addWorkNoWait(actualWork));
	}

	/**
	 * Use this method for any work that required delayed GUI update.
	 * 
	 * <b>Use if work does not interrupts execution!</b>
	 * 
	 * @param actualWork
	 * @throws Exception
	 * 
	 * @see #addWorkNoWait(WorkSet)
	 */
	public static void addWorkAndWaitForTheEnd(WorkSet actualWork) throws Exception {

		waitWhile(new WaitCondition() {
			public boolean keepWorking() {
				return workCount < expectedWorkCount;
			}
		}, addWorkNoWait(actualWork));
	}

	// Please, do not change this line.
	// I can't imagine that any normal operation in interface can be longer than 5 sec
	private final static long MAX_WAIT_TIME = 5000;

	/**
	 * Special method for waiting given condition (but no more than
	 * {@value #MAX_WAIT_TIME}).
	 * 
	 * @param condition
	 * @throws InterruptedException
	 */
	public static void waitWhile(WaitCondition condition) throws InterruptedException {
		waitWhile(condition, null);
	}

	private static void waitWhile(WaitCondition condition, Throwable watchedException) throws InterruptedException {

		assertNotNull(condition);

		long time = System.currentTimeMillis();
		boolean timeOK = false;
		if (watchedException == null) {
			watchedException = new Exception();
		}
		do {
			Thread.sleep(50);
		} while ((timeOK = ((System.currentTimeMillis() - time) < MAX_WAIT_TIME)) && condition.keepWorking()
				&& watchedException.getCause() == null);
		if (!timeOK) {
			throw new RuntimeException("Error. Wait interrupted after " + (System.currentTimeMillis() - time)
					+ " msec.");
		}
		if (watchedException.getCause() != null) {
			throw new RuntimeException(watchedException.getCause());
		}
	}

	public static void showMeBackground(final Window window) throws Exception {
		assertNotNull(window);
		addWorkAndWaitThis(new WorkSet() {

			@Override
			public void workImpl() throws Exception {
				window.setVisible(true);
			}
		}, new WaitCondition() {

			@Override
			public boolean keepWorking() {
				return window.isVisible() == false;
			}
		});
	}

	public static void waitMe(final Window window) throws Exception {
		assertNotNull(window);
		waitWhile(new WaitCondition() {

			@Override
			public boolean keepWorking() {
				return window.isVisible() == true;
			}
		});
	}

	public static void closeAllWindows() throws Exception {
		addWorkAndWaitForTheEnd(new WorkSet() {
			public void workImpl() throws Exception {
				for (Window window : JFrame.getWindows()) {
					if (window.isVisible()) {
						window.setVisible(false);
						window.dispose();
					}
				}
			}
		});
	}

	public static boolean allChildrenClosed(Window parent) {
		assertNotNull(parent);
		for (Window window : parent.getOwnedWindows()) {
			if (window.isVisible()) {
				return false;
			}
		}
		return true;
	}

	public static Window searchAnyOpenedWindow(Window parent) {
		assertNotNull(parent);
		for (Window window : parent.getOwnedWindows()) {
			if (window.isVisible()) {
				return window;
			}
		}
		return null;
	}

	public static void clickCloseDialogButton(final Window parent, final String buttonOrActionNameName)
			throws Exception {
		clickCloseDialogButton(parent, buttonOrActionNameName, false);
	}

	public static void clickCloseDialogButton(final Window parent, final String buttonOrActionNameName,
			final boolean expectErrors) throws Exception {
		assertNotNull(parent);
		assertTrue(parent.isVisible());
		AbstractButton button = (AbstractButton) Utils.getChildNamed(parent, buttonOrActionNameName);
		if (button == null) {
			button = SwingTestHelper.getButtonByActionCommand(parent, buttonOrActionNameName);
		}
		assertNotNull("Button with name or action '" + buttonOrActionNameName + "' does not exists on form '"
				+ parent.getName() + "'", button);
		assertTrue(button.isEnabled());
		assertTrue(button.isVisible());

		final AbstractButton foundButton = button;
		SwingTestHelper.addWorkAndWaitThis(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				foundButton.doClick();
			}
		}, new WaitCondition() {

			@Override
			public boolean keepWorking() {
				/*
				 * Workaround for errors
				 */
				return parent.isVisible() == true && (!expectErrors || allChildrenClosed(parent));
			}
		});
	}

	public static Window openDialog(final Class<? extends Window> expectedInstance, boolean mustNull) throws Exception {
		assertNotNull(expectedInstance);
		if (mustNull) {
			assertNull(AppHelper.getInstance().searchWindow(expectedInstance));
		}
		return clickOpenDialogButton(null, null, null, expectedInstance);
	}

	public static Window clickOpenDialogButton(final Window parent, final String buttonName) throws Exception {
		return clickOpenDialogButton(parent, null, buttonName, null);
	}

	public static Window clickOpenDialogButton(final Window parent, final Window expectedParent, final String buttonName)
			throws Exception {
		return clickOpenDialogButton(parent, expectedParent, buttonName, null);
	}

	public static Window clickOpenDialogButton(final Window parent, final Window expectedParent,
			final String buttonName, final Class<? extends Window> expectedInstance) throws Exception {

		final Window expectedParentImpl = expectedParent == null ? parent : expectedParent;

		if (expectedInstance != null) {
			Window expectedWindow = AppHelper.getInstance().searchWindow(expectedInstance);
			if (expectedWindow != null) {
				assertFalse(expectedWindow.isVisible());
			}
		} else {
			assertNotNull(parent);
			assertNotNull(buttonName);
			assertTrue(allChildrenClosed(expectedParentImpl));
		}

		SwingTestHelper.addWorkAndWaitThis(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				if (buttonName != null && parent != null) {
					AbstractButton button = (AbstractButton) Utils.getChildNamed(parent, buttonName);
					assertNotNull(button);
					button.doClick();
				} else {
					Window window = AppHelper.getInstance().getWindowImpl(expectedInstance, false);
					window.setVisible(true);
				}
			}
		}, new WaitCondition() {
			@Override
			public boolean keepWorking() {
				if (expectedInstance != null) {
					Window expectedWindow = AppHelper.getInstance().searchWindow(expectedInstance);
					return expectedWindow == null || !expectedWindow.isVisible();
				} else {
					return allChildrenClosed(expectedParentImpl);
				}

			}
		});

		if (expectedInstance != null) {
			Window expectedWindow = AppHelper.getInstance().searchWindow(expectedInstance);
			assertNotNull(expectedWindow);
			assertTrue(expectedWindow.isVisible());
			return expectedWindow;
		} else {
			assertFalse(allChildrenClosed(expectedParentImpl));
			return SwingTestHelper.searchAnyOpenedWindow(expectedParentImpl);
		}

	}

	public static void clickColorChooserOK(final Window parent, final Color color) throws Exception {
		assertFalse(allChildrenClosed(parent));
		assertNotNull(color);

		SwingTestHelper.addWorkAndWaitThis(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				Window chooserDialog = SwingTestHelper.searchAnyOpenedWindow(parent);
				assertNotNull(chooserDialog);

				JColorChooser chooser = (JColorChooser) SwingTestHelper.getReflectedFieldAccess(chooserDialog,
						"chooserPane");
				chooser.setColor(color);

				JButton button = SwingTestHelper.getButtonByActionCommand(chooserDialog, "OK");
				assertNotNull(button);
				button.doClick();
			}
		}, new WaitCondition() {
			@Override
			public boolean keepWorking() {
				return !allChildrenClosed(parent);
			}
		});
	}

	public static void clickColorChooserCancel(final Window parent) throws Exception {
		assertFalse(allChildrenClosed(parent));

		SwingTestHelper.addWorkAndWaitThis(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				Window chooserDialog = SwingTestHelper.searchAnyOpenedWindow(parent);
				assertNotNull(chooserDialog);

				JButton button = SwingTestHelper.getButtonByActionCommand(chooserDialog, "cancel");
				assertNotNull(button);
				button.doClick();
			}
		}, new WaitCondition() {
			@Override
			public boolean keepWorking() {
				return !allChildrenClosed(parent);
			}
		});
	}

	public static void clickFileChooserOK(final Window parent, final File file) throws Exception {
		assertFalse(allChildrenClosed(parent));

		SwingTestHelper.addWorkAndWaitThis(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				JDialog chooserDialog = (JDialog) SwingTestHelper.searchAnyOpenedWindow(parent);
				assertNotNull(chooserDialog);

				JFileChooser chooser = (JFileChooser) chooserDialog.getContentPane().getComponent(0);
				assertNotNull(chooser);
				chooser.setSelectedFile(file);
				chooser.approveSelection();
			}
		}, new WaitCondition() {
			@Override
			public boolean keepWorking() {
				return !allChildrenClosed(parent);
			}
		});
	}

	public static void clickFileChooserCancel(final Window parent) throws Exception {
		assertFalse(allChildrenClosed(parent));

		SwingTestHelper.addWorkAndWaitThis(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				JDialog chooserDialog = (JDialog) SwingTestHelper.searchAnyOpenedWindow(parent);
				assertNotNull(chooserDialog);

				JFileChooser chooser = (JFileChooser) chooserDialog.getContentPane().getComponent(0);
				assertNotNull(chooser);
				chooser.cancelSelection();
			}
		}, new WaitCondition() {
			@Override
			public boolean keepWorking() {
				return !allChildrenClosed(parent);
			}
		});
	}

	public static JButton getButtonByActionCommand(Component parent, String actionCommand) {

		if (parent == null) {
			return null;
		}
		if (actionCommand == null) {
			return null;
		}

		//		// Debug line
		//		if (logger.isTraceEnabled()) {
		//			logger.trace("Class: " + parent.getClass() + " Name: " + parent.getName());
		//		}

		if (parent instanceof JButton) {
			if (actionCommand.equals(((JButton) parent).getActionCommand())) {
				return (JButton) parent;
			}
		}

		if (parent instanceof Container) {
			Component[] children = (parent instanceof JMenu) ? ((JMenu) parent).getMenuComponents()
					: ((Container) parent).getComponents();

			for (int i = 0; i < children.length; ++i) {
				JButton child = getButtonByActionCommand(children[i], actionCommand);
				if (child != null) {
					return child;
				}
			}
		}

		return null;
	}

	public static void printChildHierarchy(Component parent) {
		printChildHierarchy(parent, 0);
	}

	private static void printChildHierarchy(Component parent, int level) {
		StringBuilder prefix = new StringBuilder("");
		for (int i = 0; i <= level; i++) {
			prefix.append(" ");
		}
		if (parent instanceof Container) {
			Component[] children = (parent instanceof JMenu) ? ((JMenu) parent).getMenuComponents()
					: ((Container) parent).getComponents();

			for (int i = 0; i < children.length; ++i) {
				Component child = children[i];
				System.out.println(prefix + " " + child.getName()
						+ (child instanceof AbstractButton ? " " + ((AbstractButton) child).getActionCommand() : "")
						+ " = " + child);
				printChildHierarchy(child, level + 1);
			}
		}
	}

	public static Object getReflectedFieldAccess(Object object, String name) throws SecurityException,
			NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field field = object.getClass().getDeclaredField(name);

		boolean returnNoAccess = false;
		if (!field.isAccessible()) {
			returnNoAccess = true;
			field.setAccessible(true);
		}

		try {
			return field.get(object);
		} finally {
			if (returnNoAccess) {
				field.setAccessible(false);
			}
		}
	}
}
