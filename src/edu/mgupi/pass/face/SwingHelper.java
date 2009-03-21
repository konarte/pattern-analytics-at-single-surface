package edu.mgupi.pass.face;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JMenu;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class for Swing testing. Base idea is delayed execution (into another
 * thread) and waiting until its done.
 * 
 * @author raidan
 * 
 */
public class SwingHelper {

	private final static Logger logger = LoggerFactory.getLogger(SwingHelper.class);

	/**
	 * Method (c) Ichiro Suzuki
	 * 
	 * @param parent
	 * @param name
	 * @return found Component with this name or null, if component with this
	 *         name is not found
	 */
	public static Component getChildNamed(Component parent, String name) {

		// Debug line
		if (logger.isTraceEnabled()) {
			logger.trace("Class: " + parent.getClass() + " Name: " + parent.getName());
		}

		if (name.equals(parent.getName())) {
			return parent;
		}

		if (parent instanceof Container) {
			Component[] children = (parent instanceof JMenu) ? ((JMenu) parent).getMenuComponents()
					: ((Container) parent).getComponents();

			for (int i = 0; i < children.length; ++i) {
				Component child = getChildNamed(children[i], name);
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
				System.out.println(prefix + " " + children[i].getName() + " = " + children[i]);
				printChildHierarchy(children[i], level + 1);
			}
		}
	}

	//
	// public static Object getReflectedFieldAccess(Object object, String name)
	// throws SecurityException,
	// NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
	// Field field = object.getClass().getDeclaredField(name);
	//
	// boolean returnNoAccess = false;
	// if (!field.isAccessible()) {
	// returnNoAccess = true;
	// field.setAccessible(true);
	// }
	//
	// try {
	// return field.get(object);
	// } finally {
	// if (returnNoAccess) {
	// field.setAccessible(false);
	// }
	// }
	//
	// }

	private static volatile int expectedWorkCount = 0;
	private static volatile int workCount = 0;

	private static Throwable addWorkNoWait(final WorkSet actualWork) throws Exception {
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
	public static void addWorkAndWaitThis(WorkSet actualWork, ConditionSet waitCondition) throws Exception {
		waitUntil(waitCondition, addWorkNoWait(actualWork));
	}

	//	private static boolean lastWorkDone = false;

	//
	//	/**
	//	 * Append work as separate thread. No SwingUtilities using!
	//	 * 
	//	 * <b>Do not use for work with delayed GUI update!</b>
	//	 * 
	//	 * @param actualWork
	//	 * @throws Exception
	//	 */
	//	public static void addWorkSingleWait(final WorkSet actualWork) throws Exception {
	//		lastWorkDone = false;
	//		new Thread(new Runnable() {
	//			public void run() {
	//				try {
	//					actualWork.workImpl();
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				} finally {
	//					lastWorkDone = true;
	//				}
	//			}
	//		}).start();
	//
	//		waitUntil(new ConditionSet() {
	//			public boolean keepWorking() {
	//				return !lastWorkDone;
	//			}
	//		});
	//	}

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

		waitUntil(new ConditionSet() {
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
	public static void waitUntil(ConditionSet condition) throws InterruptedException {
		waitUntil(condition, null);
	}

	private static void waitUntil(ConditionSet condition, Throwable watchedException) throws InterruptedException {
		long time = System.currentTimeMillis();
		boolean timeOK = false;
		while ((timeOK = ((System.currentTimeMillis() - time) < MAX_WAIT_TIME)) && condition.keepWorking()) {
			Thread.sleep(100);
		}
		if (!timeOK) {
			throw new RuntimeException("Error. Wait interrupted after " + (System.currentTimeMillis() - time)
					+ " msec.");
		}
		if (watchedException.getCause() != null) {
			throw new RuntimeException(watchedException.getCause());
		}
	}
}
