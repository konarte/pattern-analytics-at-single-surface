package edu.mgupi.pass.face;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
		MainFrameDataStorage.reset();
	}

	private volatile Collection<Component> components = new ArrayList<Component>();

	public Component registerAdditionalComponent(Component c) {
		components.add(c);
		return c;
	}

	public void unregisterAdditionalComponent(Component c) {
		components.remove(c);
	}

	private volatile Map<Class<? extends Window>, Window> windowsCollection = new HashMap<Class<? extends Window>, Window>();
	private volatile Collection<Window> additionalWindows = new ArrayList<Window>();

	public Window registerAdditionalWindow(Class<? extends Window> windowType) {
		return this.getWindow(windowType, true);
	}

	protected Window getFrame(Class<? extends Frame> windowType) {
		return this.getWindow(windowType, false);
	}

	public Window getDialog(Class<? extends Dialog> windowType) {
		return this.getWindow(windowType, false);
	}

	public Window getDialogImpl(Class<? extends Dialog> windowType) throws Exception {
		return this.getWindowImpl(windowType, false);
	}

	private Window getWindow(Class<? extends Window> windowType, boolean additionalWindow) {
		try {
			return this.getWindowImpl(windowType, additionalWindow);
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

	protected Window getWindowImpl(Class<? extends Window> windowType, boolean additionalWindow) throws Exception {
		Window window = this.getWindow2(windowType, additionalWindow);
		if (window == null) {
			throw new IllegalStateException("Internal error. Window does not initialized.");
		}
//		if (!keepHidden) {
//			window.setVisible(true);
//		}
		return window;
	}

	private synchronized Window getWindow2(Class<? extends Window> windowType, boolean additionalWindow)
			throws Exception {
		Window window = windowsCollection.get(windowType);

		if (window == null || additionalWindow) {

			Constructor<? extends Window> constructor = null;
			try {
				constructor = windowType.getConstructor();
				window = constructor.newInstance();
			} catch (NoSuchMethodException me) {
				constructor = windowType.getConstructor(Frame.class);
				window = constructor.newInstance((Frame) null);

				final Window myWindow = window;
				window.addWindowListener(new WindowAdapter() {
					public void windowOpened(WindowEvent e) {
						myWindow.requestFocus();
					}
				});

				window.setLocationRelativeTo(window.getOwner());

			}
			if (additionalWindow) {
				logger.debug("Return force new instance of " + windowType + " :: " + window);
				additionalWindows.add(window);
			} else {
				windowsCollection.put(windowType, window);
				Config.getInstance().loadWindowPosition(window);
			}
		}

		return window;
	}

	public Window searchWindow(Class<? extends Window> windowType) {
		return windowsCollection.get(windowType);
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

	public void saveWindowPositions() {
		for (Window window : windowsCollection.values()) {
			Config.getInstance().storeWindowPosition(window);
		}
		for (Window window : additionalWindows) {
			Config.getInstance().storeWindowPosition(window);
		}
	}

	public static void showExceptionDialog(String message, Throwable e) {
		showExceptionDialog(null, message, e);
	}

	public static void showExceptionDialog(Component parent, String message, Throwable e) {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		e.printStackTrace(new PrintStream(out));

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JLabel("<html><h2>" + message + "</h2><b>" + e + "</b><hr></html>"), BorderLayout.NORTH);

		JTextArea area = new JTextArea(out.toString());
		area.setEditable(false);
		area.setFont(new Font(area.getFont().getName(), Font.PLAIN, 12));

		JScrollPane pane = new JScrollPane(area);
		pane.setPreferredSize(new Dimension(600, 300));
		panel.add(pane, BorderLayout.CENTER);

		JOptionPane.showMessageDialog(parent, panel, "Error", JOptionPane.ERROR_MESSAGE);

		try {
			out.close();
		} catch (IOException io) {
			logger.error("Error when closing stream", io);
		}
	}

	/***********************************************************************/
	/***********************************************************************/
	/***********************************************************************/
	/***********************************************************************/
	/***********************************************************************/
	/***********************************************************************/
	/***********************************************************************/
	/***********************************************************************/
	/***********************************************************************/
	/***********************************************************************/
	/***********************************************************************/
	/***********************************************************************/
	/***********************************************************************/
	/***********************************************************************/
	/***********************************************************************/
	/***********************************************************************/
	/***********************************************************************/
	/***********************************************************************/
	//
	//	private synchronized Window getWindow(Frame owner, Class<? extends Window> windowType, boolean force,
	//			boolean ignoreEmptyFrame) throws Exception {
	//		Window window = windowsCollection.get(windowType);
	//
	//		if (window == null || force) {
	//
	//			if (owner == null && !ignoreEmptyFrame) {
	//				window = windowType.newInstance();
	//			} else {
	//				window = windowType.getConstructor(Frame.class).newInstance(owner);
	//
	//				final Window myWindow = window;
	//				// For dialog windows we requested focus
	//				window.addWindowListener(new WindowAdapter() {
	//					public void windowOpened(WindowEvent e) {
	//						myWindow.requestFocus();
	//					}
	//				});
	//			}
	//
	//			if (force) {
	//				logger.debug("Return force new instance of " + windowType + " :: " + window);
	//				additionalWindows.add(window);
	//			} else {
	//				windowsCollection.put(windowType, window);
	//			}
	//		}
	//
	//		if (owner != null && (window instanceof Dialog)) {
	//			window.setLocationRelativeTo(owner);
	//		}
	//		return window;
	//	}
	//
	//	private Window createWindow(Frame owner, Class<? extends Window> windowType, boolean force, boolean ignoreEmptyFrame)
	//			throws Exception {
	//		if (windowType == null) {
	//			throw new IllegalArgumentException("Internal error. 'windowType' must be not not null.");
	//		}
	//
	//		return this.getWindow(owner, windowType, force, ignoreEmptyFrame);
	//	}
	//
	//	public Window searchWindow(Class<? extends Window> windowType) {
	//		return windowsCollection.get(windowType);
	//	}
	//
	//	//
	//	//	public Window openWindow(Class<? extends Window> windowType) {
	//	//		return this.openWindow(null, windowType);
	//	//	}
	//
	//	public Window openWindow(Frame owner, Class<? extends Window> windowType) {
	//		return this.openWindow(owner, windowType, true, false);
	//	}
	//
	//	public Window openWindowHidden(Frame owner, Class<? extends Window> windowType) {
	//		return this.openWindow(owner, windowType, false, false);
	//	}
	//
	//	public Window openWindowDialogHidden(Frame owner, Class<? extends Window> windowType) {
	//		return this.openWindow(owner, windowType, false, true);
	//	}
	//
	//	public Window registerAdditionalWindow(Class<? extends Window> windowType) throws Exception {
	//		return createWindow(null, windowType, true, false);
	//	}
	//
	//	private Window openWindow(Frame owner, Class<? extends Window> windowType, boolean show, boolean ignoreEmptyFrame) {
	//		try {
	//			return this.openWindowImpl(owner, windowType, show, ignoreEmptyFrame);
	//		} catch (Exception e) {
	//			logger.error("Error when creating window instance", e);
	//			AppHelper.showExceptionDialog("Unexpected error when creating instance of '" + windowType
	//					+ "'. Please, consult with developers.", e);
	//			return null;
	//		}
	//	}
	//
	//	protected Window openWindowImpl(Frame owner, Class<? extends Window> windowType, boolean show,
	//			boolean ignoreEmptyFrame) throws Exception {
	//		Window frame = this.createWindow(owner, windowType, false, ignoreEmptyFrame);
	//		if (show) {
	//			frame.setVisible(true);
	//		}
	//		return frame;
	//	}
}
