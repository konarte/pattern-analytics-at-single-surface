package edu.mgupi.pass.face;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
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

/**
 * Class for application support -- make easy change LookAndFeel, set window
 * states, keep cached (opened) windows, some dialogues.
 * 
 * @author raidan
 * 
 */
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
		if (instance != null) {
			instance.windowsCollection.clear();
			instance.additionalWindows.clear();
			instance.components.clear();
			instance = null;
		}
	}

	private volatile Map<Class<? extends Window>, Window> windowsCollection = new HashMap<Class<? extends Window>, Window>();
	private volatile Collection<Window> additionalWindows = new ArrayList<Window>();

	private synchronized Window getWindow(Class<? extends Window> windowType, Frame owner, boolean force)
			throws Exception {
		Window window = windowsCollection.get(windowType);

		if (window == null || force) {

			if (owner == null) {
				window = windowType.newInstance();
			} else {
				window = windowType.getConstructor(Frame.class).newInstance(owner);
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

	private Window createWindow(Class<? extends Window> windowType, Frame owner, boolean force) throws Exception {
		if (windowType == null) {
			throw new IllegalArgumentException("Internal error. 'windowType' must be not not null.");
		}

		return this.getWindow(windowType, owner, force);
	}

	public Window searchWindow(Class<? extends Window> windowType) {
		return windowsCollection.get(windowType);
	}

	public Window registerAdditionalWindow(Class<? extends Window> windowType) throws Exception {
		return createWindow(windowType, null, true);
	}

	public Window openWindow(Class<? extends Window> windowType) {
		return this.openWindow(windowType, null);
	}

	public Window openWindow(Class<? extends Window> windowType, Frame owner) {
		try {
			return this.openWindowImpl(windowType, owner);
		} catch (Exception e) {
			logger.error("Error when creating window instance", e);
			AppHelper.showExceptionDialog("Unexpected error when creating instance of '" + windowType
					+ "'. Please, consult with developers.", e);
			//			JOptionPane.showMessageDialog(null, "Unexpected error when creating instance of '" + windowType
			//					+ "'. Please, consult with developers (" + e + ")", "Error when creating frame",
			//					JOptionPane.WARNING_MESSAGE);
			return null;
		}
	}

	protected Window openWindowImpl(Class<? extends Window> windowType, Frame owner) throws Exception {
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

	public Component registerAdditionalComponent(Component c) {
		components.add(c);
		return c;
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

	public static void showExceptionDialog(String message, Throwable e) {
		showExceptionDialog(null, message, e);
	}

	public static void showExceptionDialog(Component parent, String message, Throwable e) {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		e.printStackTrace(new PrintStream(out));

		String stackTrace = out.toString().replaceAll("\n", "");

		//StringBuilder str = Utils.replaceAll(new StringBuilder(out.toString()), "\n", "");
		//String str = Utils.replaceAll(out.toString(), "\n", "");

		//		JOptionPane option = new JOptionPane(message, JOptionPane.ERROR_MESSAGE);
		//		JDialog dialog = option.createDialog("Error");
		//		JTextArea jtext = new JTextArea(out.toString());
		//		jtext.setPreferredSize(new Dimension(400, 300));
		//		jtext.setEditable(false);
		//		dialog.getContentPane().add(jtext, BorderLayout.SOUTH);
		//		dialog.pack();
		//		dialog.setVisible(true);
		//
		//		SwingHelper.printChildHierarchy(dialog);
		//
		//		dialog.dispose();

		JOptionPane.showMessageDialog(parent, "<html><h2>" + message + "</h2><b>" + e + "</b><hr><pre>" + stackTrace
				+ "</pre></html>", "Error", JOptionPane.ERROR_MESSAGE);

		try {
			out.close();
		} catch (IOException io) {
			logger.error("Error when closing stream", io);
		}
	}
}
