package edu.mgupi.pass.face;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppHelper {

	private final static Logger logger = LoggerFactory.getLogger(AppHelper.class);

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

	private volatile Map<Class<? extends Window>, Window> windowsCollection = new HashMap<Class<? extends Window>, Window>();

	private synchronized Window getWindow(Class<? extends Window> windowType, Frame owner) {
		Window window = windowsCollection.get(windowType);

		if (window == null) {
			try {
				if (owner == null) {
					window = windowType.newInstance();
				} else {
					window = windowType.getConstructor(Frame.class).newInstance(owner);
				}
			} catch (Exception e) {
				logger.error("Error when creating window instance", e);
				JOptionPane.showMessageDialog(null, "Unexpected error when creating instance of '" + windowType
						+ "'. Please, consult with developers (" + e + ")", "Error when creating frame",
						JOptionPane.WARNING_MESSAGE);
			}
			windowsCollection.put(windowType, window);
		}
		return window;
	}

	public Window searchWindow(Class<? extends Window> windowType) {
		return windowsCollection.get(windowType);
	}

	public Window createWindow(Class<? extends Window> windowType) {
		return createWindow(windowType, null);
	}

	public Window createWindow(Class<? extends Window> windowType, Frame owner) {
		if (windowType == null) {
			throw new IllegalArgumentException("Internal error. 'windowType' must be not not null.");
		}

		Window frame = this.getWindow(windowType, owner);
		if (owner != null && (frame instanceof Dialog)) {
			frame.setLocationRelativeTo(owner);
		}
		return frame;
	}

	public Window openWindow(Class<? extends Window> windowType) {
		return this.openWindow(windowType, null);
	}

	public Window openWindow(Class<? extends Window> windowType, Frame owner) {
		Window frame = this.createWindow(windowType, owner);
		frame.setVisible(true);
		return frame;
	}

	public void updateUI(String className) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {

		// Updating all opened components
		UIManager.setLookAndFeel(className);
		for (Window window : windowsCollection.values()) {
			SwingUtilities.updateComponentTreeUI(window);
		}
		for (Component c : components) {
			SwingUtilities.updateComponentTreeUI(c);
		}

	}

	private Collection<Component> components = new ArrayList<Component>();

	public void registerAdditionalComponent(Component c) {
		components.add(c);
	}

	public void unregisterAdditionalComponent(Component c) {
		components.remove(c);
	}

	//
	// public Window closeWindow(Class<? extends Window> windowType) {
	// if (windowType == null) {
	// throw new
	// IllegalArgumentException("Internal error. 'windowType' must be not not null.");
	// }
	// return this.closeWindow(this.getWindow(windowType));
	// }
	//
	// public Window closeWindow(Window frame) {
	//
	// if (frame == null) {
	// throw new
	// IllegalArgumentException("Internal error. 'frame' must be not not null.");
	// }
	//
	// frame.setVisible(false);
	// return frame;
	// }
}
