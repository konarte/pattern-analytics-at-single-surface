package edu.mgupi.pass.face.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
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
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

import edu.mgupi.pass.face.IProgress;
import edu.mgupi.pass.util.Config;
import edu.mgupi.pass.util.Const;
import edu.mgupi.pass.util.IRefreshable;
import edu.mgupi.pass.util.Utils;
import edu.mgupi.pass.util.Config.SupportedLocale;

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
		// singleton
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
	public static void reset() {
		if (instance != null) {

			for (Window window : instance.windowsCollection.values()) {
				if (window instanceof IWindowCloseable) {
					((IWindowCloseable) window).close();
				}
			}

			instance.windowsCollection.clear();
			instance.additionalWindows.clear();
			instance.components.clear();
			instance = null;
		}
		AppDataStorage.getInstance().reset();
	}

	public static void printCache() {
		if (instance != null) {
			try {
				instance.cachedLock.lock();
				instance.componentsLock.lock();

				for (Map.Entry<Class<? extends Window>, Window> entry : instance.windowsCollection
						.entrySet()) {
					logger.debug("-- Cached window instance of " + entry.getKey() + " = "
							+ entry.getValue().getName() + " (" + entry.getValue() + ")");
				}
				for (Window window : instance.additionalWindows) {
					logger.debug("-- Additional window instance " + window.getName() + " ("
							+ window + ")");
				}
				for (Component comp : instance.components) {
					logger.debug("-- Additional component " + comp.getName() + " (" + comp + ")");
				}
			} finally {
				instance.cachedLock.unlock();
				instance.componentsLock.unlock();
			}
		}
	}

	private Image iconImage;

	/**
	 * Set image icon for all creating windows.
	 * 
	 * @param iconImage
	 */
	public void setWindowsIcon(Image iconImage) {
		this.iconImage = iconImage;
	}

	/**
	 * Return image icon.
	 * 
	 * @return Image instance or null
	 * 
	 */
	public Image getWindowIcon() {
		return this.iconImage;
	}

	/**
	 * Set supported locale, change {@link Locale#setDefault(Locale)}. <br>
	 * 
	 * <b>Do not call this method when frames or dialogs are loaded!</b> <br>
	 * <b>The only way to safe change locale is call it before any
	 * initialization.</b>
	 * 
	 * @param locale
	 * @return true is locale successfully changed, false if it is unknown.
	 */
	public static boolean setLocale(SupportedLocale locale) {
		if (locale == SupportedLocale.ENGLISH) {
			Locale.setDefault(Locale.ENGLISH);
			return true;
		} else if (locale == SupportedLocale.RUSSIAN) {
			Locale.setDefault(Const.LOCALE_RU);
			return true;
		} else {
			logger.error("Unknown locale type {}. Using default {}.", locale, Locale.getDefault());
			return false;
		}
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
			throw new IllegalStateException(
					"Internal error. Progress interface does not setup yet.");
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
	 * Return count of components, registered by
	 * {@link #registerAdditionalComponent(Component)}
	 * 
	 * @return count of components
	 */
	public int getRegisteredComponentCount() {
		componentsLock.lock();
		try {
			return components.size();
		} finally {
			componentsLock.unlock();
		}
	}

	/**
	 * Register additional window (no caching and no return same instance after
	 * that)
	 * 
	 * @param parent
	 * 
	 * @param windowType
	 *            class of window
	 * 
	 * @return instance of {@link Window}
	 * @throws Exception
	 *             on any error
	 */
	public Window registerAdditionalWindow(Frame parent, Class<? extends Window> windowType)
			throws Exception {
		return this.getWindowImpl(parent, windowType, true);
	}

	/**
	 * Unregister additional window (remove from cache and dispose).
	 * 
	 * @param windowInstance
	 * @return true if windows was successfully unregistered and disposed
	 */
	public boolean unregisterAdditionalWindow(Window windowInstance) {
		cachedLock.lock();
		try {
			if (additionalWindows.remove(windowInstance)) {
				logger.trace("Removing instance of additional window {}.", windowInstance);
				windowInstance.setVisible(false);
				windowInstance.dispose();
				return true;
			}
			return false;
		} finally {
			cachedLock.unlock();
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
	 * 
	 * @see #getWindowImpl(Class, boolean)
	 */
	public Window getFrameImpl(Class<? extends Frame> windowType) throws Exception {
		return this.getWindowImpl(windowType, false);
	}

	/**
	 * Create new instance or return cached. If {@link Exception} was thrown --
	 * it will be shown in message box, but not throwing upper.
	 * 
	 * @param windowType
	 *            class of window
	 * @return instance of {@link Window} (the same as on previous call with
	 *         same windowType)
	 * 
	 * @see #getWindowImpl(Class, boolean)
	 */
	public Window getDialog(Class<? extends Dialog> windowType) {
		try {
			return this.getWindowImpl(windowType, false);
		} catch (Exception e) {
			AppHelper.showExceptionDialog(null, Messages.getString("AppHelper.err.windowCreate",
					windowType), e);
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
	 * @see #getWindowImpl(Class, boolean)
	 */
	public Window getDialogImpl(Class<? extends Dialog> windowType) throws Exception {
		return this.getWindowImpl(windowType, false);
	}

	private Lock cachedLock = new ReentrantLock();
	private volatile Map<Class<? extends Window>, Window> windowsCollection = new HashMap<Class<? extends Window>, Window>();
	private volatile Collection<Window> additionalWindows = new ArrayList<Window>();

	/**
	 * Implementation of get-o-create-window, create without parent.
	 * 
	 * @param windowType
	 *            class of creating window
	 * @param additionalWindow
	 *            true if instance must be registered as additional window (with
	 *            with flag application will be always create new instance of
	 *            given class)
	 * @return instance of {@link Window} (the same as on previous call with
	 *         same windowType)
	 * @throws Exception
	 *             on any error
	 * @see #getWindowImpl(Frame, Class, boolean)
	 */
	protected synchronized Window getWindowImpl(Class<? extends Window> windowType,
			boolean additionalWindow) throws Exception {
		return getWindowImpl(null, windowType, additionalWindow);
	}

	/**
	 * Implementation of get-o-create window.
	 * 
	 * @param parent
	 *            window, primary owner of this creating window
	 * @param windowType
	 *            class of creating window
	 * @param additionalWindow
	 *            true if instance must be registered as additional window (with
	 *            with flag application will be always create new instance of
	 *            given class)
	 * @return instance of {@link Window} (the same as on previous call with
	 *         same windowType)
	 * @throws Exception
	 *             on any error
	 */
	protected synchronized Window getWindowImpl(Frame parent, Class<? extends Window> windowType,
			boolean additionalWindow) throws Exception {

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
				window = constructor.newInstance((Frame) parent);

				final Window myWindow = window;
				window.addWindowListener(new WindowAdapter() {
					public void windowOpened(WindowEvent e) {
						myWindow.requestFocus();
					}
				});

				// This is Dialog, I guess
				window.setLocationRelativeTo(window.getOwner());

			}

			if (this.iconImage != null) {
				window.setIconImage(iconImage);
			}
			if (additionalWindow) {
				// Register in special collection
				logger.trace("Return force new instance of {} :: {}.", windowType, window);
				additionalWindows.add(window);
			} else {
				// Register in cache
				logger.trace("Cache instance of {} :: {}.", windowType, window);

				windowsCollection.put(windowType, window);
				Config.getInstance().loadWindowPosition(window);
			}

			return window;
		} finally {
			cachedLock.unlock();
		}
	}

	/**
	 * Return count of windows registered by
	 * {@link #registerAdditionalWindow(Frame, Class)} or
	 * {@link #getWindowImpl(Class, boolean)} with true flag
	 * 
	 * @return count of additional windows
	 */
	public int getAdditionalWindowsCount() {
		cachedLock.lock();
		try {
			return additionalWindows.size();
		} finally {
			cachedLock.unlock();
		}
	}

	/**
	 * Return count of cached windows registered by
	 * {@link #getWindowImpl(Class, boolean)} with false flag
	 * 
	 * @return count of cached windows
	 */
	public int getCachedWindowsCount() {
		cachedLock.lock();
		try {
			return windowsCollection.size();
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

				if (window instanceof IRefreshable) {
					((IRefreshable) window).refresh();
				}
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

	// do not use!
	//	protected static void setDatabaseTransactionMode(TransactionMode transactionMode) throws PersistentException {
	//		SessionType session = PassPersistentManager.instance().getSessionType();
	//		System.out.println("Warning. Skip settings session type. Current session type: "
	//				+ (session == SessionType.APP_BASE ? "app based" : (session == SessionType.THREAD_BASE ? "thread base"
	//						: "other")));
	//		//		SessionType sessionType = transactionMode == TransactionMode.COMMIT_BULK ? SessionType.ADVANCED_APP_BASE
	//		//				: SessionType.THREAD_BASE;
	//		//		if (PassPersistentManager.instance().getSessionType() != sessionType) {
	//		//
	//		//			logger.debug("Switching persistance type. Disposing persistance manager first...");
	//		//
	//		//			PassPersistentManager.instance().disposePersistentManager();
	//		//			PassPersistentManager.setSessionType(sessionType);
	//		//		}
	//	}

	/**
	 * Saving positions for all registered windows. Usually called when
	 * application done.
	 */
	public void saveWindowPositions() {
		componentsLock.lock();
		cachedLock.lock();
		try {

			logger.debug("Saving all registered windows position into config.");

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
	 * Show dialog with {@link Throwable} data. Automatically printing stack
	 * trace of error.
	 * 
	 * @param parent
	 *            window, where exception was thrown
	 * @param message
	 *            text for display at head of message.
	 * @param e
	 *            any {@link Throwable} instance
	 */
	public static void showExceptionDialog(Component parent, String message, Throwable e) {

		logger.error(message, e);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		e.printStackTrace(new PrintStream(out));

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JLabel("<html><h2>" + Utils.splitStingBySlices(message, 60, "<br>")
				+ "</h2><b>" + Utils.splitStingBySlices(e.toString(), 100, "<br>")
				+ "</b><hr></html>"), BorderLayout.NORTH);

		// Creating text area for big stack :)
		JTextArea area = new JTextArea(out.toString());
		area.setEditable(false);
		area.setFont(new Font(area.getFont().getName(), Font.PLAIN, 12));

		JScrollPane pane = new JScrollPane(area);
		pane.setPreferredSize(new Dimension(600, 300));
		panel.add(pane, BorderLayout.CENTER);

		JOptionPane.showMessageDialog(parent, panel, Messages.getString("AppHelper.title.error"),
				JOptionPane.ERROR_MESSAGE);
		try {
			out.close();
		} catch (IOException io) {
			// I can't imaging that happens :)
			logger.error("Unexpected error when closing stream " + out, io);
		}
	}

	/**
	 * 
	 * Show error dialog to user. Automatically print error message.
	 * 
	 * @param parent
	 *            window, where error was occurs
	 * @param message
	 *            error message
	 * @param title
	 *            title for message
	 */
	public static void showErrorDialog(Component parent, String message, String title) {
		logger.error(message);
		JOptionPane.showMessageDialog(parent, Utils.splitStingBySlices(message, 100, "\n"), title,
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Show error dialog to user. Use default title for message box.
	 * 
	 * @param parent
	 *            window, where error was occurs
	 * @param message
	 *            error message
	 * 
	 * @see #showErrorDialog(Component, String, String)
	 */
	public static void showErrorDialog(Component parent, String message) {
		showErrorDialog(parent, message, Messages.getString("AppHelper.title.error"));
	}

	/**
	 * Show information about required field.
	 * 
	 * @param parent
	 *            window, where error was occurs
	 * @param fieldName
	 *            visual name of field, required to fill
	 * @see #showErrorDialog(Component, String, String)
	 */
	public static void showFieldRequiredDialog(Component parent, String fieldName) {
		showErrorDialog(parent, Messages.getString("AppHelper.err.requiredField", fieldName),
				Messages.getString("AppHelper.requiredFieldTitle"));
	}

	/**
	 * 
	 * Show info dialog to user. Automatically print info message.
	 * 
	 * @param parent
	 *            window
	 * @param message
	 *            information message
	 */
	public static void showInfoDialog(Component parent, String message) {
		logger.info(message);
		JOptionPane.showMessageDialog(parent, Utils.splitStingBySlices(message, 100, "\n"),
				Messages.getString("AppHelper.title.info"), JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * 
	 * Show warning dialog to user. Automatically print warn message.
	 * 
	 * @param parent
	 *            window
	 * @param message
	 *            information message
	 */
	public static void showWarnDialog(Component parent, String message) {
		logger.warn(message);
		JOptionPane.showMessageDialog(parent, Utils.splitStingBySlices(message, 100, "\n"),
				Messages.getString("AppHelper.title.warn"), JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * 
	 * Show warning dialog to user. Automatically print warn message.
	 * 
	 * @param parent
	 *            window
	 * @param message
	 *            information message
	 * @return true if user confirm that, false if not
	 */
	public static boolean showConfirmDialog(Component parent, String message) {
		return JOptionPane.showConfirmDialog(parent, Utils.splitStingBySlices(message, 100, "\n"),
				Messages.getString("AppHelper.title.confirm"), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	}

	public static GridBagConstraints getJBCForm(int gridX, int gridY) {
		return getJBCForm(gridX, gridY, 1, false);
	}

	public static GridBagConstraints getJBCForm(int gridX, int gridY, boolean fillX) {
		return getJBCForm(gridX, gridY, 1, fillX);
	}

	public static GridBagConstraints getJBCForm(int gridX, int gridY, int gridWidth, boolean fillX) {
		GridBagConstraints jbc = new GridBagConstraints();

		jbc.gridx = gridX;
		jbc.gridy = gridY;

		jbc.gridwidth = gridWidth;

		jbc.anchor = (gridX == 0 && gridWidth == 1) ? GridBagConstraints.EAST
				: GridBagConstraints.WEST;
		jbc.insets = new Insets(0, 0, 5, 5);
		//jbc.weightx = gridX == 0 ? 0 : 1;
		jbc.weightx = (gridX > 0 || gridWidth > 1) && fillX ? 1 : 0;
		jbc.fill = gridX > 0 && fillX ? GridBagConstraints.HORIZONTAL : GridBagConstraints.NONE;

		return jbc;
	}

	public static GridBagConstraints getJBCBorderPanel(int gridY, boolean fillY) {
		GridBagConstraints jbc = new GridBagConstraints();
		jbc.anchor = GridBagConstraints.NORTHWEST;
		jbc.fill = GridBagConstraints.HORIZONTAL;
		jbc.weightx = 1;
		jbc.weighty = fillY ? 1 : 0;
		jbc.gridx = 0;
		jbc.gridy = gridY;
		return jbc;
	}

	public static GridBagConstraints getJBCInBorderPanel() {
		GridBagConstraints jbc = new GridBagConstraints();
		jbc.fill = GridBagConstraints.NONE;
		jbc.anchor = GridBagConstraints.WEST;
		jbc.weightx = 1;
		jbc.weighty = 1;
		jbc.gridx = 0;
		jbc.gridy = 0;
		jbc.insets = new Insets(0, 5, 0, 5);
		return jbc;
	}
}
