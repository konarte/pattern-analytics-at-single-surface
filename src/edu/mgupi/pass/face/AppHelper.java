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

import edu.mgupi.pass.util.Config;

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

	protected static synchronized void reset() {
		instance.windowsCollection.clear();
		instance.additionalWindows.clear();
		instance.components.clear();
		instance = null;
	}

	private volatile Map<Class<? extends Window>, Window> windowsCollection = new HashMap<Class<? extends Window>, Window>();
	private volatile Collection<Window> additionalWindows = new ArrayList<Window>();

	private synchronized Window getWindow(Class<? extends Window> windowType, Frame owner, boolean force) {
		Window window = windowsCollection.get(windowType);

		if (window == null || force) {
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
			if (force) {
				logger.debug("Return force new instance of " + windowType + " :: " + window);
				additionalWindows.add(window);
			} else {
				windowsCollection.put(windowType, window);
			}
		}

		if (owner != null && (window instanceof Dialog)) {
			window.setLocationRelativeTo(owner);
		}
		return window;
	}

	private Window createWindow(Class<? extends Window> windowType, Frame owner, boolean force) {
		if (windowType == null) {
			throw new IllegalArgumentException("Internal error. 'windowType' must be not not null.");
		}

		return this.getWindow(windowType, owner, force);
	}

	public Window searchWindow(Class<? extends Window> windowType) {
		return windowsCollection.get(windowType);
	}

	public Window createWindow(Class<? extends Window> windowType) {
		return createWindow(windowType, null, true);
	}

	public Window openWindow(Class<? extends Window> windowType) {
		return this.openWindow(windowType, null);
	}

	public Window openWindow(Class<? extends Window> windowType, Frame owner) {
		Window frame = this.createWindow(windowType, owner, false);
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
		for (Window window : additionalWindows) {
			SwingUtilities.updateComponentTreeUI(window);
		}
		for (Component c : components) {
			SwingUtilities.updateComponentTreeUI(c);
		}

	}

	private volatile Collection<Component> components = new ArrayList<Component>();

	public void registerAdditionalComponent(Component c) {
		components.add(c);
	}

	public void unregisterAdditionalComponent(Component c) {
		components.remove(c);
	}

	public void saveWindowPositions() {
		for (Window window : windowsCollection.values()) {
			Config.getInstance().setWindowPosition(window);
		}
		for (Window window : additionalWindows) {
			Config.getInstance().setWindowPosition(window);
		}
	}
}
