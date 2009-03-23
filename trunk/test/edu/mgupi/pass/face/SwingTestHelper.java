package edu.mgupi.pass.face;

import java.awt.Window;

public class SwingTestHelper {
	public static void showMeBackground(final Window window) throws Exception {
		SwingHelper.addWorkAndWaitThis(new WorkSet() {

			@Override
			public void workImpl() throws Exception {
				window.setVisible(true);
			}
		}, new ConditionSet() {

			@Override
			public boolean keepWorking() {
				return window.isVisible() == false;
			}
		});
	}

	public static void waitMe(final Window window) throws Exception {
		SwingHelper.waitUntil(new ConditionSet() {

			@Override
			public boolean keepWorking() {
				return window.isVisible() == true;
			}
		});
	}
}
