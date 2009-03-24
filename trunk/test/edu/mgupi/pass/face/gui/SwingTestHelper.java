package edu.mgupi.pass.face.gui;

import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import edu.mgupi.pass.util.WaitCondition;
import edu.mgupi.pass.util.WorkSet;

public class SwingTestHelper {

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
	public static void addWorkAndWaitThis(WorkSet actualWork, WaitCondition waitCondition) throws Exception {
		waitUntil(waitCondition, addWorkNoWait(actualWork));
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

		waitUntil(new WaitCondition() {
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
	public static void waitUntil(WaitCondition condition) throws InterruptedException {
		waitUntil(condition, null);
	}

	private static void waitUntil(WaitCondition condition, Throwable watchedException) throws InterruptedException {
		long time = System.currentTimeMillis();
		boolean timeOK = false;
		if (watchedException == null) {
			watchedException = new Exception();
		}
		while ((timeOK = ((System.currentTimeMillis() - time) < MAX_WAIT_TIME)) && condition.keepWorking()
				&& watchedException.getCause() == null) {
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

	public static void showMeBackground(final Window window) throws Exception {
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
		waitUntil(new WaitCondition() {

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
}
