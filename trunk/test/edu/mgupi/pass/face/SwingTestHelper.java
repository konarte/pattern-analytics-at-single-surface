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
public class SwingTestHelper {

	private final static Logger logger = LoggerFactory.getLogger(SwingTestHelper.class);

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

	/**
	 * Append work by {@link SwingUtilities#invokeLater(Runnable)}.
	 * 
	 * You can call this method and do anything else. Next calling
	 * {@link #addWork(WorkSet)} method will wait for this work too!
	 * 
	 * 
	 * @param actualWork
	 * @throws Exception
	 */
	public static void addWorkNoWait(final WorkSet actualWork) throws Exception {
		expectedWorkCount++;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					actualWork.workImpl();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					workCount++;
				}
			}
		});
	}

	private static boolean lastWorkDone = false;

	/**
	 * Append work as separate thread. No SwingUtilities using!
	 * 
	 * <b>Do not use for work with delayed GUI update!</b>
	 * 
	 * @param actualWork
	 * @throws Exception
	 */
	public static void addWorkSingleWait(final WorkSet actualWork) throws Exception {
		lastWorkDone = false;
		new Thread(new Runnable() {
			public void run() {
				try {
					actualWork.workImpl();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					lastWorkDone = true;
				}
			}
		}).start();

		waitUntil(new ConditionSet() {
			public boolean keepWorking() {
				return !lastWorkDone;
			}
		});
	}

	/**
	 * Use this method for any work that required delayed GUI update.
	 * 
	 * @param actualWork
	 * @throws Exception
	 * 
	 * @see #addWorkNoWait(WorkSet)
	 */
	public static void addWork(WorkSet actualWork) throws Exception {
		addWorkNoWait(actualWork);
		waitUntil(new ConditionSet() {
			public boolean keepWorking() {
				return workCount < expectedWorkCount;
			}
		});
	}

	private final static long MAX_WAIT_TIME = 5000;

	/**
	 * Special method for waiting given condition (but no more than
	 * {@value #MAX_WAIT_TIME}).
	 * 
	 * @param condition
	 * @throws InterruptedException
	 */
	public static void waitUntil(ConditionSet condition) throws InterruptedException {
		long time = System.currentTimeMillis();
		while ((System.currentTimeMillis() - time) < MAX_WAIT_TIME && condition.keepWorking()) {
			Thread.sleep(100);
		}
	}

}
