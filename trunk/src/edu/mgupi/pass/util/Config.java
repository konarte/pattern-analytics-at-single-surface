package edu.mgupi.pass.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;

import javax.swing.JCheckBox;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.FileConfiguration;
import org.orm.PersistentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.face.Application;
import edu.mgupi.pass.face.gui.AppHelper;
import edu.mgupi.pass.face.gui.LoginWindow;
import edu.mgupi.pass.face.gui.MainFrame;
import edu.mgupi.pass.face.gui.SettingsWindow;
import edu.mgupi.pass.face.gui.template.ImagePanel;
import edu.mgupi.pass.modules.ModuleProcessor;

/**
 * Configuration class. Using <a
 * href="http://commons.apache.org/configuration/">Apache commons
 * configuration</a>. <br>
 * 
 * Current configuration separates on two part -- current and common.
 * 
 * <ul>
 * <li>Current configuration stores special pre-processing settings for
 * {@link ModuleProcessor#getPreChainsaw()}, sets up by {@link MainFrame}.
 * 
 * <li>Common configuration stores other options (Look and Feel, type of check
 * when we delete rows in tables, etc.).
 * </ul>
 * 
 * INIConfiguration mark as deprecated, but it works fine. I can't force to work
 * hierarchy configurators. <br>
 * 
 * @author raidan
 * 
 */

public class Config {

	private final static Logger logger = LoggerFactory.getLogger(Config.class);

	/**
	 * Enumeration for source pre-processing. This options tuned up
	 * {@link ModuleProcessor#getPreChainsaw()} by {@link MainFrame}.
	 * 
	 * @author raidan
	 * 
	 */
	public static enum SourceMode {
		LEFT_TOP(Messages.getString("Config.sourceMode.LEFT_TOP")), // 
		CENTER(Messages.getString("Config.sourceMode.CENTER")), // 
		SCALE(Messages.getString("Config.sourceMode.SCALE")), // 
		SCALE_IF_LEFT_TOP(Messages.getString("Config.sourceMode.SCALE_IF_LEFT_TOP",
				Const.MAIN_IMAGE_WIDTH, Const.MAIN_IMAGE_HEIGHT)), //
		SCALE_IF_CENTER(Messages.getString("Config.sourceMode.SCALE_IF_CENTER",
				Const.MAIN_IMAGE_WIDTH, Const.MAIN_IMAGE_HEIGHT));

		private String title;

		private SourceMode(String title) {
			this.title = title;
		}

		public String toString() {
			return title;
		}
	};

	/**
	 * Enumeration for deletion modes. You can easily add new options --
	 * settings will be tuned up automatically.
	 * 
	 * @author raidan
	 * 
	 */
	public static enum DeletionMode {
		CONFIRM(Messages.getString("Config.deletionMode.CONFIRM")), // 
		CONFIRM_MULTPLES(Messages.getString("Config.deletionMode.CONFIRM_MULTIPLIES")), // 
		/*
		 * CONFIRM_USERS_ONLY("Требовать подтверждения только у пользователей"),
		 */
		NO_CONFIRM(Messages.getString("Config.deletionMode.NO_CONFIRM")); // 

		private String title;

		private DeletionMode(String title) {
			this.title = title;
		}

		public String toString() {
			return title;
		}
	}

	/**
	 * Type of checks before delete data (from tables).
	 * 
	 * @author raidan
	 * 
	 */
	public static enum DeletionCheckMode {
		ACQUIRE_THEN_CHECK(Messages.getString("Config.delectionCheckMode.ACQUIRE_THEN_CHECK")), // 
		CHECK_THEN_ACQUIRE(Messages.getString("Config.deletionCheckMode.CHECK_THEN_ACQUIRE")), // 
		NO_CHECK(Messages.getString("Config.deletionCheckMode.NO_CHECK"));

		private String title;

		private DeletionCheckMode(String title) {
			this.title = title;
		}

		public String toString() {
			return title;
		}
	}

	/**
	 * Supported language packs and locales.
	 * 
	 * @author raidan
	 * 
	 * @see AppHelper#setLocale(SupportedLocale)
	 * 
	 */
	public static enum SupportedLocale {
		RUSSIAN("Русский"), ENGLISH("English");
		private String title;

		private SupportedLocale(String title) {
			this.title = title;
		}

		public String toString() {
			return title;
		}
	}

	/**
	 * Enumeration for transaction(session) modes.
	 * 
	 * WARNING! DO NOT USE THIS OPTION.
	 * 
	 * @author raidan
	 */
	public static enum TestTransactionMode {
		COMMIT_EVERY_ROW(Messages.getString("Config.testTM.COMMIT_EVERY_ROW")), //  
		COMMIT_BULK(Messages.getString("Config.testTM.COMMIT_BULK"));

		private String title;

		private TestTransactionMode(String title) {
			this.title = title;
		}

		public String toString() {
			return title;
		}
	}

	private Config() {
		// singleton
	}

	private static Config instance;

	/**
	 * Return instance of class (new on first call).
	 * 
	 * @return instance
	 */
	public static synchronized Config getInstance() {
		if (instance == null) {
			instance = new Config();
			instance.prepareConfig();
		}
		return instance;
	}

	public static synchronized void close() {
		if (instance == null) {
			instance = null;
		}
	}

	/**
	 * Special method for set up current instance as virtual, for debug. <br>
	 * 
	 * Virtual means, that we don't load properties from file and do not save
	 * it. All changed values will be store in memory and disappear after
	 * program end. <br>
	 * 
	 * Method must use by tests.
	 */
	public void setDebugVirualMode() {
		this.virualMode = true;
	}

	private boolean virualMode = false;
	private final static String DEFAULT_COMMON_CONFIG_NAME = "config.ini";
	private Configuration currentConfigInstance;
	private Configuration commonConfigInstance;
	private Configuration connectConfigInstance;
	private FileConfiguration configInstance;

	@SuppressWarnings("deprecation")
	private void prepareConfig() {
		try {
			logger.trace("Loading config from file {}.", DEFAULT_COMMON_CONFIG_NAME);

			File file = new File(DEFAULT_COMMON_CONFIG_NAME);
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException io) {
					throw new RuntimeException(io);
				}
			}

			configInstance = new org.apache.commons.configuration.INIConfiguration(
					DEFAULT_COMMON_CONFIG_NAME);
			configInstance.setEncoding("UTF-8");

			currentConfigInstance = configInstance.subset("current");
			commonConfigInstance = configInstance.subset("common");
			connectConfigInstance = configInstance.subset("connection");

		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	private final static String PARAM_CURRENT_SOURCE_MODE = "sourceMode";
	private final static String PARAM_CURRENT_BACKGROUND = "imageBackground";

	private final static String PARAM_LOOK_AND_FEEL = "lookAndFeel";
	private final static String PARAM_LOCALE = "locale";
	private final static String PARAM_ROWS_DELETE_MODE = "rowsDeleteMode";
	private final static String PARAM_TRANSACTION_MODE = "transactionMode";
	private final static String PARAM_DATA_DELETION_CHECK = "dataDeletionCheck";

	private final static String PARAM_CONNECTION_URL = "url";
	private final static String PARAM_CONNECTION_LOGIN = "login";
	private final static String PARAM_CONNECTION_PASSWORD = "password";

	private SourceMode currentSourceMode = null;

	/**
	 * Return current mode of source.
	 * 
	 * @return current {@link SourceMode}
	 */
	public SourceMode getCurrentSourceMode() {
		if (currentSourceMode != null) {
			return currentSourceMode;
		}
		final SourceMode default_ = SourceMode.LEFT_TOP;

		if (this.virualMode) {
			return default_;
		}

		try {
			currentSourceMode = SourceMode.valueOf(this.currentConfigInstance.getString(
					PARAM_CURRENT_SOURCE_MODE, default_.name()));
			return currentSourceMode;
		} catch (IllegalArgumentException iae) {
			return default_;
		}
	}

	private Color currentColor = null;

	/**
	 * Return color of background for preprocessing.
	 * 
	 * @return current {@link Color}
	 */
	public Color getCurrentBackground() {
		if (currentColor != null) {
			return currentColor;
		}
		final Color default_ = Color.WHITE;

		if (this.virualMode) {
			return default_;
		}

		currentColor = new Color(this.currentConfigInstance.getInt(PARAM_CURRENT_BACKGROUND,
				default_.getRGB()));
		return currentColor;
	}

	private String currentLaF = null;

	/**
	 * Return Look and Feel.
	 * 
	 * @return current {@link LookAndFeel} classname.
	 */
	public String getLookAndFeel() {

		if (currentLaF != null) {
			return currentLaF;
		}

		final String default_ = UIManager.getSystemLookAndFeelClassName();

		if (this.virualMode) {
			return default_;
		}

		return this.commonConfigInstance.getString(PARAM_LOOK_AND_FEEL, default_);
	}

	private DeletionMode currentRowsDeletionMode = null;

	/**
	 * Return current mode of rows deletion.
	 * 
	 * @return current {@link DeletionMode}
	 */
	public DeletionMode getRowsDeleteMode() {
		if (currentRowsDeletionMode != null) {
			return currentRowsDeletionMode;
		}
		final DeletionMode default_ = DeletionMode.CONFIRM;

		if (this.virualMode) {
			return default_;
		}

		try {
			currentRowsDeletionMode = DeletionMode.valueOf(this.commonConfigInstance.getString(
					PARAM_ROWS_DELETE_MODE, default_.name()));
			return currentRowsDeletionMode;
		} catch (IllegalArgumentException iae) {
			return default_;
		}
	}

	private TestTransactionMode currentTransactionMode = null;

	/**
	 * Return current transaction mode. <br>
	 * 
	 * <b>Do not use except tests! Hibernate does not provide work after
	 * exception!. </b>
	 * 
	 * @return current {@link TestTransactionMode}.
	 * @throws PersistentException
	 */
	public TestTransactionMode getTransactionMode() throws PersistentException {
		if (currentTransactionMode != null) {
			return currentTransactionMode;
		}
		final TestTransactionMode default_ = TestTransactionMode.COMMIT_EVERY_ROW;

		if (this.virualMode) {
			return default_;
		}

		try {
			currentTransactionMode = TestTransactionMode.valueOf(this.commonConfigInstance
					.getString(PARAM_TRANSACTION_MODE, default_.name()));

			//AppHelper.setDatabaseTransactionMode(currentTransactionMode);
			return currentTransactionMode;
		} catch (IllegalArgumentException iae) {
			return default_;
		}
	}

	private DeletionCheckMode currentDeletionCheckMode = null;

	/**
	 * Return current type of check when user delete row.
	 * 
	 * @return current {@link DeletionCheckMode}
	 */
	public DeletionCheckMode getDeletionCheckMode() {
		if (currentDeletionCheckMode != null) {
			return currentDeletionCheckMode;
		}
		final DeletionCheckMode default_ = DeletionCheckMode.ACQUIRE_THEN_CHECK;

		if (this.virualMode) {
			return default_;
		}

		try {
			currentDeletionCheckMode = DeletionCheckMode.valueOf(this.commonConfigInstance
					.getString(PARAM_DATA_DELETION_CHECK, default_.name()));
			return currentDeletionCheckMode;
		} catch (IllegalArgumentException iae) {
			return default_;
		}
	}

	private String defaultEncoding = "UTF-8";

	/**
	 * Return default encoding for all saved/loaded files.
	 * 
	 * @return encoding name
	 */
	public String getDefaultEncoding() {
		return defaultEncoding;
	}

	private SupportedLocale currentLocale = null;

	/**
	 * Return current locale from supported list. <br>
	 * 
	 * Usually used by {@link SettingsWindow} and {@link Application}.
	 * 
	 * @return locale
	 */
	public SupportedLocale getCurrentLocale() {
		if (currentLocale != null) {
			return currentLocale;
		}
		final SupportedLocale default_ = Locale.getDefault().equals(Const.LOCALE_RU) ? SupportedLocale.RUSSIAN
				: SupportedLocale.ENGLISH;

		if (this.virualMode) {
			return default_;
		}

		try {
			currentLocale = SupportedLocale.valueOf(this.commonConfigInstance.getString(
					PARAM_LOCALE, default_.name()));
			return currentLocale;
		} catch (IllegalArgumentException iae) {
			return default_;
		}
	}

	private String currentURL;

	/**
	 * Return URL for connect to database. <br>
	 * 
	 * @return URL string like "jdbc:mysql://localhost:3306/pass_db"
	 */
	public String getURL() {
		if (currentURL != null) {
			return currentURL;
		}
		final String default_ = "jdbc:mysql://localhost:3306/pass_db";

		if (this.virualMode) {
			return default_;
		}

		currentURL = this.connectConfigInstance.getString(PARAM_CONNECTION_URL, default_);
		return currentURL;
	}

	private String currentLogin;

	/**
	 * Return login (username) for connect to database. <br>
	 * 
	 * @return login
	 */
	public String getLogin() {
		if (currentLogin != null) {
			return currentLogin;
		}
		final String default_ = "pass";

		if (this.virualMode) {
			return default_;
		}

		currentLogin = this.connectConfigInstance.getString(PARAM_CONNECTION_LOGIN, default_);
		currentLogin = Utils.replaceAll(currentLogin, "'", "");
		currentLogin = Utils.replaceAll(currentLogin, ";", "");
		return currentLogin;
	}

	/**
	 * Return password state.
	 * 
	 * @return true is password saved in settings, false if not
	 */
	public boolean hasSavedPassword() {
		return currentPassword != null;
	}

	private String currentPassword;

	/**
	 * Return password for connect to database. <br>
	 * 
	 * @return password if user allow to save password in settings or null, if
	 *         password manual entered and don't saved
	 */
	public String getPassword() {
		if (currentPassword != null) {
			return currentPassword;
		}
		final String default_ = null;

		if (this.virualMode) {
			return default_;
		}

		currentPassword = this.connectConfigInstance.getString(PARAM_CONNECTION_PASSWORD, default_);
		return currentPassword;
	}

	/**
	 * Set new source mode.
	 * 
	 * @param value
	 *            new {@link SourceMode}
	 * @return true if values changed, false if this is equals to previous
	 */
	public boolean setCurrentSourceMode(SourceMode value) {

		boolean res = this.setCurrentParameterImpl(PARAM_CURRENT_SOURCE_MODE, this
				.getCurrentSourceMode().name(), value.name());
		this.currentSourceMode = value;
		return res;
	}

	/**
	 * Set new background color.
	 * 
	 * @param value
	 *            new {@link Color}
	 * @return true if values changed, false if this is equals to previous
	 */
	public boolean setCurrentBackground(Color value) {
		boolean res = this.setCurrentParameterImpl(PARAM_CURRENT_BACKGROUND, this
				.getCurrentBackground().getRGB(), value.getRGB());
		this.currentColor = value;
		return res;
	}

	/**
	 * Set new Look and Feel.
	 * 
	 * @param value
	 *            new {@link LookAndFeel} classname
	 * @return true if values changed, false if this is equals to previous
	 * 
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws UnsupportedLookAndFeelException
	 */
	public boolean setLookAndFeel(String value) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		boolean res = this
				.setCommonParameterImpl(PARAM_LOOK_AND_FEEL, this.getLookAndFeel(), value);

		currentLaF = value;
		if (res || !UIManager.getLookAndFeel().getClass().getName().equals(value)) {
			AppHelper.getInstance().updateUI(value);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Set new rows deletion mode.
	 * 
	 * @param value
	 *            new {@link DeletionMode}
	 * @return true if values changed, false if this is equals to previous
	 */
	public boolean setRowsDeleteMode(DeletionMode value) {
		boolean res = this.setCommonParameterImpl(PARAM_ROWS_DELETE_MODE, this.getRowsDeleteMode()
				.name(), value.name());
		this.currentRowsDeletionMode = value;
		return res;
	}

	/**
	 * Set new test transaction mode. <br>
	 * 
	 * <b>Warning! This method does not affects no more!</b>
	 * 
	 * @param value
	 *            new {@link TestTransactionMode}
	 * @return true if values changed, false if this is equals to previous
	 * @throws PersistentException
	 */
	public boolean setTransactionMode(TestTransactionMode value) throws PersistentException {
		boolean res = this.setCommonParameterImpl(PARAM_TRANSACTION_MODE, this.getTransactionMode()
				.name(), value.name());
		this.currentTransactionMode = value;
		return res;
		//		if (res) {
		//			//AppHelper.setDatabaseTransactionMode(value);
		//			return true;
		//		} else {
		//			return false;
		//		}
	}

	/**
	 * Set new check mode for rows deletions.
	 * 
	 * @param value
	 *            new {@link DeletionCheckMode}
	 * @return true if values changed, false if this is equals to previous
	 */
	public boolean setDeletionCheckModeMode(DeletionCheckMode value) {

		boolean res = this.setCommonParameterImpl(PARAM_DATA_DELETION_CHECK, this
				.getDeletionCheckMode().name(), value.name());
		this.currentDeletionCheckMode = value;
		return res;
	}

	/**
	 * Set new {@link Locale}. Do not forget, that we must restart application
	 * for apply changes. <br>
	 * 
	 * <b>This method does not change locale in runtime, this is too
	 * dangerous.</b>
	 * 
	 * @param value
	 *            new {@link SupportedLocale}
	 * @return true if values changed, false if this is equals to previous
	 * @see AppHelper#setLocale(SupportedLocale)
	 */
	public boolean setCurrentLocale(SupportedLocale value) {
		boolean res = this.setCommonParameterImpl(PARAM_LOCALE, this.getCurrentLocale().name(),
				value.name());
		this.currentLocale = value;
		return res;
	}

	/**
	 * Set new URL for connection to database.
	 * 
	 * @param value
	 *            new URL string like "jdbc:mysql://localhost:3306/pass_db"
	 * @return true if values changed, false if this is equals to previous
	 */
	public boolean setURL(String value) {
		if (value != null) {
			value = value.trim();
		}
		boolean res = this.setConnectParameterImpl(PARAM_CONNECTION_URL, this.currentURL, value);
		this.currentURL = value;
		return res;
	}

	/**
	 * Set new login for connection to database. <br>
	 * 
	 * Actually, sets up only in login window.
	 * 
	 * @param value
	 *            new login
	 * @return true if values changed, false if this is equals to previous
	 * 
	 * @see LoginWindow
	 */
	public boolean setLogin(String value) {
		if (value != null) {
			value = value.trim();
		}
		value = Utils.replaceAll(value, "'", "");
		value = Utils.replaceAll(value, ";", "");
		boolean res = this
				.setConnectParameterImpl(PARAM_CONNECTION_LOGIN, this.currentLogin, value);
		this.currentLogin = value;
		return res;
	}

	/**
	 * Set new password for connection to database. <br>
	 * 
	 * Actually, sets up only in login window.
	 * 
	 * @param value
	 *            new password
	 * @return true if values changed, false if this is equals to previous
	 * 
	 * @see LoginWindow
	 */
	public boolean setPassword(String value) {
		boolean res = this.setConnectParameterImpl(PARAM_CONNECTION_PASSWORD, this.currentPassword,
				value);
		this.currentPassword = value;
		return res;
	}

	private boolean setCurrentParameterImpl(String paramName, Object oldValue, Object newValue) {
		return this.setParameterImpl(this.currentConfigInstance, paramName, oldValue, newValue);
	}

	private boolean setCommonParameterImpl(String paramName, Object oldValue, Object newValue) {
		return this.setParameterImpl(this.commonConfigInstance, paramName, oldValue, newValue);
	}

	private boolean setConnectParameterImpl(String paramName, Object oldValue, Object newValue) {
		return this.setParameterImpl(this.connectConfigInstance, paramName, oldValue, newValue);
	}

	private boolean setParameterImpl(Configuration config, String paramName, Object oldValue,
			Object newValue) {
		if (newValue == null) {
			config.clearProperty(paramName);
		} else {
			config.setProperty(paramName, newValue);
		}
		return !Utils.equals(newValue, oldValue);
	}

	/**
	 * Default name for scale-button. That check-box controls window fit.
	 * 
	 * @see ImagePanel
	 */
	public final static String DEFAULT_SCALE_BUTTON_NAME = "scaleButton";

	/**
	 * Save position and size of given window to the config. <br>
	 * 
	 * If this window contains special check-box with name
	 * {@value #DEFAULT_SCALE_BUTTON_NAME} -- we store its
	 * {@link JCheckBox#isSelected()} state. <br>
	 * 
	 * Window defines by it's {@link Window#getName()}.
	 * 
	 * @param window
	 *            instance of saving window
	 */
	public void storeWindowPosition(Window window) {
		if (virualMode) {
			return;
		}

		if (window == null) {
			logger.error("Attemp to save empty window.");
			return;
		}

		logger.trace("Saving window position for window {}.", window.getName());

		Configuration config = this.configInstance.subset("window-" + window.getName());
		config.setProperty("x", window.getX());
		config.setProperty("y", window.getY());
		config.setProperty("width", window.getWidth());
		config.setProperty("height", window.getHeight());

		Component component = Utils.getChildNamed(window, DEFAULT_SCALE_BUTTON_NAME);
		if (component != null && component instanceof JCheckBox) {
			JCheckBox box = (JCheckBox) component;

			logger.trace("Save additional checkBox {}.", box.getName());
			config.setProperty(DEFAULT_SCALE_BUTTON_NAME, box.isSelected());
		}
	}

	/**
	 * Save {@link JCheckBox#isSelected()} states of all given check-boxes for
	 * this window. <br>
	 * 
	 * Window defines by it's {@link Window#getName()}.
	 * 
	 * @param window
	 *            instance of window that contains given check-boxes
	 * @param checkBoxes
	 */
	public void storeWindowCheckBoxes(Window window, JCheckBox... checkBoxes) {
		if (virualMode) {
			return;
		}

		if (window == null) {
			logger.error("Attemp to save empty window.");
			return;
		}

		Configuration config = this.configInstance.subset("window-" + window.getName() + "-checks");
		for (JCheckBox box : checkBoxes) {

			logger.trace("Save scale checkBox {}.", box.getName());
			config.setProperty(box.getName(), box.isSelected());
		}

	}

	/**
	 * Load and immediately set position and size of this window. <br>
	 * 
	 * If this window contains special check-box with name
	 * {@value #DEFAULT_SCALE_BUTTON_NAME} -- we load its state and call
	 * {@link JCheckBox#doClick()} if state differ than existing button have. <br>
	 * 
	 * Window defines by it's {@link Window#getName()}.
	 * 
	 * @param window
	 */
	public void loadWindowPosition(Window window) {

		if (virualMode) {
			return;
		}

		if (window == null) {
			logger.error("Attemp load get empty window.");
			return;
		}

		logger.trace("Loading window position for window {}.", window.getName());

		Configuration config = this.configInstance.subset("window-" + window.getName());
		window.setBounds(config.getInt("x", window.getX()), config.getInt("y", window.getY()),
				config.getInt("width", window.getWidth()), config.getInt("height", window
						.getHeight()));

		Component component = Utils.getChildNamed(window, DEFAULT_SCALE_BUTTON_NAME);
		if (component != null && component instanceof JCheckBox) {
			boolean selected = config.getBoolean(DEFAULT_SCALE_BUTTON_NAME, false);
			JCheckBox box = (JCheckBox) component;
			if (selected != box.isSelected()) {

				logger.trace("Click on scale checkBox {}.", box.getName());
				box.doClick();
			}
		}
	}

	/**
	 * Load {@link JCheckBox#isSelected()} states of all found check-boxes for
	 * this window. <br>
	 * 
	 * If state if load check-box does not equals to found in config -- we call
	 * {@link JCheckBox#doClick()}. <br>
	 * 
	 * Window defines by it's {@link Window#getName()}.
	 * 
	 * @param window
	 *            instance of window that contains check-boxes we can set
	 */
	@SuppressWarnings("unchecked")
	public void loadWindowCheckBoxes(Window window) {
		if (virualMode) {
			return;
		}

		if (window == null) {
			logger.error("Attemp to load empty window.");
			return;
		}

		Configuration config = this.configInstance.subset("window-" + window.getName() + "-checks");
		Iterator<String> iter = config.getKeys();
		while (iter.hasNext()) {
			String key = iter.next();
			Component component = Utils.getChildNamed(window, key);
			if (component != null && component instanceof JCheckBox) {
				boolean selected = config.getBoolean(key);
				JCheckBox box = (JCheckBox) component;
				if (selected != box.isSelected()) {

					logger.trace("Click on additional checkBox {}.", box.getName());
					box.doClick();
				}
			}
		}

	}

	/**
	 * Actual saving common configuration. If you don't call this method -- you
	 * loose all changed properties.
	 * 
	 * @throws ConfigurationException
	 */
	public void saveCommonConfig() throws ConfigurationException {
		if (virualMode) {
			return;
		}

		logger.debug("Saving common configuration.");

		if (this.configInstance != null) {
			this.configInstance.save();
		}
	}

	/**
	 * Actual saving current configuration. If you don't call this method -- you
	 * loose all changed properties.
	 * 
	 * @throws ConfigurationException
	 */
	public void saveCurrentConfig() throws ConfigurationException {

		logger.debug("Saving current configuration.");

		this.saveCommonConfig();
	}

}