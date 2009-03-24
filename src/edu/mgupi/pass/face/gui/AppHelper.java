package edu.mgupi.pass.face.gui;

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
import java.util.concurrent.locks.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.util.Config;
import edu.mgupi.pass.util.IProgress;

/**
 * Class for application support -- make easy change LookAndFeel, set window
 * states, keep cached (but hidden) windows, some dialogues.
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

	/**
	 * Get instance of helper. This is singleton.
	 * 
	 * @return {@link AppHelper}
	 */
	public static synchronized AppHelper getInstance() {
		if (instance == null) {
			instance = new AppHelper();
		}
		return instance;
	}

	/**
	 * Special method for reset all cached data.
	 */
	protected static synchronized void reset() {
		if (instance != null) {
			instance.windowsCollection.clear();
			instance.additionalWindows.clear();
			instance.components.clear();
			instance = null;
		}
		MainFrameDataStorage.reset();
	}

	private IProgress progressInterface;

	/**
	 * Registering visual interface (for progress bar)
	 * 
	 * @param progressInterface
	 */
	public void setIProgressInstance(IProgress progressInterface) {
		this.progressInterface = progressInterface;
	}

	/**
	 * Return registered visual interface
	 * 
	 * @return current instance of IProgress
	 */
	public IProgress getProgressInstance() {
		if (this.progressInterface == null) {
			throw new IllegalStateException("Internal error. Progress interface does not setup yet.");
		}
		return this.progressInterface;
	}

	private Lock componentsLock = new ReentrantLock();
	private Collection<Component> components = new ArrayList<Component>();

	/**
	 * Register additional component. This will helps on changing LookAndFeel.
	 * 
	 * @param c
	 *            any component instance
	 * @return as received
	 * @see #updateUI(String)
	 */
	public Component registerAdditionalComponent(Component c) {
		componentsLock.lock();
		try {
			components.add(c);
			return c;
		} finally {
			componentsLock.unlock();
		}
	}

	/**
	 * Unregister component. Applying LookAndFeel does not changes this
	 * component.
	 * 
	 * @param c
	 *            any component
	 * @see #updateUI(String)
	 */
	public void unregisterAdditionalComponent(Component c) {
		componentsLock.lock();
		try {
			components.remove(c);
		} finally {
			componentsLock.unlock();
		}
	}

	/**
	 * Register additional window (no caching and no return same instance after
	 * that)
	 * 
	 * @param windowType
	 *            class of window
	 * 
	 * @return instance of {@link Window}
	 * @throws Exception
	 *             on any error
	 */
	public Window registerAdditionalWindow(Class<? extends Window> windowType) throws Exception {
		return this.getWindowImpl(windowType, true);
	}

	/**
	 * Create new instance of given class or return the same instance (if this
	 * method already called for given windowType).
	 * 
	 * @param windowType
	 *            class of window
	 * @return instance of {@link Window} (the same as on previous call with
	 *         same windowType)
	 * @throws Exception
	 *             on any error
	 */
	public Window getFrameImpl(Class<? extends Frame> windowType) throws Exception {
		return this.getWindowImpl(windowType, false);
	}

	/**
	 * Create new instance or return cached. If {@link Exception} was thrown --
	 * it will be shown in messagebox, but not throwing.
	 * 
	 * @param windowType
	 * @return instance of {@link Window} (the same as on previous call with
	 *         same windowType)
	 */
	public Window getDialog(Class<? extends Dialog> windowType) {
		try {
			return this.getWindowImpl(windowType, false);
		} catch (Exception e) {
			logger.error("Error when creating window instance", e);
			AppHelper.showExceptionDialog("Unexpected error when creating instance of '" + windowType
					+ "'. Please, consult with developers.", e);
			return null;
		}
	}

	/**
	 * Create new instance of given class or return the same instance (if this
	 * method already called for given windowType).
	 * 
	 * @param windowType
	 *            class of window
	 * @return instance of {@link Window} (the same as on previous call with
	 *         same windowType)
	 * @throws Exception
	 *             on any error
	 */
	public Window getDialogImpl(Class<? extends Dialog> windowType) throws Exception {
		return this.getWindowImpl(windowType, false);
	}

	private Lock cachedLock = new ReentrantLock();
	private volatile Map<Class<? extends Window>, Window> windowsCollection = new HashMap<Class<? extends Window>, Window>();
	private volatile Collection<Window> additionalWindows = new ArrayList<Window>();

	/**
	 * Implementation
	 * 
	 * @param windowType
	 * @param additionalWindow
	 * @return
	 * @throws Exception
	 *             on any error
	 */
	protected synchronized Window getWindowImpl(Class<? extends Window> windowType, boolean additionalWindow)
			throws Exception {

		cachedLock.lock();
		try {

			Window window = windowsCollection.get(windowType);

			// Check for return (if this window cached) 
			if (window != null && !additionalWindow) {
				return window;
			}

			// Using default constructor
			Constructor<? extends Window> constructor = null;
			try {
				constructor = windowType.getConstructor();
				window = constructor.newInstance();
			} catch (NoSuchMethodException me) {

				// Using constructor with Frame, but give them shit :) 
				constructor = windowType.getConstructor(Frame.class);
				window = constructor.newInstance((Frame) null);

				final Window myWindow = window;
				window.addWindowListener(new WindowAdapter() {
					public void windowOpened(WindowEvent e) {
						myWindow.requestFocus();
					}
				});

				// This is Dialog, I guess
				window.setLocationRelativeTo(window.getOwner());

			}
			if (additionalWindow) {
				// Register in special collection
				logger.debug("Return force new instance of " + windowType + " :: " + window);
				additionalWindows.add(window);
			} else {
				// Register in cache
				windowsCollection.put(windowType, window);
				Config.getInstance().loadWindowPosition(window);
			}

			return window;
		} finally {
			cachedLock.unlock();
		}
	}

	/**
	 * Search window in registered cache. Cached filled by methods
	 * {@link #getDialog(Class)}, {@link #getDialogImpl(Class)},
	 * {@link #getFrameImpl(Class)}.
	 * 
	 * @param windowType
	 *            searching class
	 * @return instance of {@link Window} or null, if not registered yet.
	 */
	public Window searchWindow(Class<? extends Window> windowType) {
		cachedLock.lock();
		try {
			return windowsCollection.get(windowType);
		} finally {
			cachedLock.unlock();
		}
	}

	/**
	 * Apply this LookAndFeel to all registered windows -- cached, additional
	 * and components.
	 * 
	 * @param className
	 *            full classname for {@link LookAndFeel} class
	 * 
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws UnsupportedLookAndFeelException
	 */
	public void updateUI(String className) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {

		componentsLock.lock();
		cachedLock.lock();
		try {

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
		} finally {
			cachedLock.unlock();
			componentsLock.unlock();
		}

	}

	/**
	 * 
	 */
	public void saveWindowPositions() {
		componentsLock.lock();
		cachedLock.lock();
		try {
			for (Window window : windowsCollection.values()) {
				Config.getInstance().storeWindowPosition(window);
			}
			for (Window window : additionalWindows) {
				Config.getInstance().storeWindowPosition(window);
			}
		} finally {
			cachedLock.unlock();
			componentsLock.unlock();
		}
	}

	/**
	 * Show dialog with {@link Throwable} data
	 * 
	 * @param message
	 *            text for display at head of message.
	 * @param e
	 *            any {@link Throwable} instance
	 */
	public static void showExceptionDialog(String message, Throwable e) {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		e.printStackTrace(new PrintStream(out));

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JLabel("<html><h2>" + message + "</h2><b>" + e + "</b><hr></html>"), BorderLayout.NORTH);

		// Creating text area for big stack :)
		JTextArea area = new JTextArea(out.toString());
		area.setEditable(false);
		area.setFont(new Font(area.getFont().getName(), Font.PLAIN, 12));

		JScrollPane pane = new JScrollPane(area);
		pane.setPreferredSize(new Dimension(600, 300));
		panel.add(pane, BorderLayout.CENTER);

		JOptionPane.showMessageDialog(null, panel, "Error", JOptionPane.ERROR_MESSAGE);

		try {
			out.close();
		} catch (IOException io) {
			// I can't imaging that happens :)
			logger.error("Unexpected error when closing stream " + out, io);
		}
	}

}
