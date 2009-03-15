package edu.mgupi.pass.face;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AppHelper {
	private AppHelper() {
		//
	}

	private static AppHelper instance;

	public static synchronized AppHelper getInstance() {
		if (instance == null) {
			instance = new AppHelper();
		}
		return instance;
	}

	private Map<Class<? extends JFrame>, JFrame> framesCollection = new HashMap<Class<? extends JFrame>, JFrame>();

	private synchronized JFrame getFrame(Class<? extends JFrame> windowType) {
		JFrame frame = framesCollection.get(windowType);
		if (frame == null) {
			try {
				frame = windowType.newInstance();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Unexpected error when creating instance of '" + windowType
						+ "'. Please, consult with developers.", "Error when creating frame",
						JOptionPane.WARNING_MESSAGE);
			}
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			framesCollection.put(windowType, frame);
		}
		return frame;
	}

	public JFrame openFrame(Class<? extends JFrame> windowType) {

		if (windowType == null) {
			throw new IllegalArgumentException("Internal error. 'windowType' must be not not null.");
		}

		JFrame frame = this.getFrame(windowType);
		frame.setVisible(true);
		return frame;
	}
	
	public JFrame closeFrame(Class<? extends JFrame> windowType) {
		if (windowType == null) {
			throw new IllegalArgumentException("Internal error. 'windowType' must be not not null.");
		}
		return this.closeFrame(this.getFrame(windowType));
	}

	public JFrame closeFrame(JFrame frame) {

		if (frame == null) {
			throw new IllegalArgumentException("Internal error. 'frame' must be not not null.");
		}

		frame.setVisible(false);
		return frame;
	}
}
