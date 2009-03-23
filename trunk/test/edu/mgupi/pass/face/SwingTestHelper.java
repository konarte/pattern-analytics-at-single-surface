package edu.mgupi.pass.face;

import java.awt.Window;

import javax.swing.JFrame;

import edu.mgupi.pass.util.WaitCondition;
import edu.mgupi.pass.util.SwingHelper;
import edu.mgupi.pass.util.WorkSet;

public class SwingTestHelper {
	public static void showMeBackground(final Window window) throws Exception {
		SwingHelper.addWorkAndWaitThis(new WorkSet() {

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
		SwingHelper.waitUntil(new WaitCondition() {

			@Override
			public boolean keepWorking() {
				return window.isVisible() == true;
			}
		});
	}

	public static void closeAllWindows() {
		for (Window window : JFrame.getWindows()) {
			if (window.isVisible()) {
				window.setVisible(false);
				window.dispose();
			}
		}
	}
}
