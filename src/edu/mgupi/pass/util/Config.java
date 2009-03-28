package edu.mgupi.pass.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JCheckBox;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.FileConfiguration;
import org.orm.PersistentException;
import org.orm.PersistentManager.SessionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.face.gui.AppHelper;

/**
 * Config class. Using apache commons-config.
 * 
 * INIConfiguration mark as deprecated, but it works fine. I can't force to work
 * hierarchy configurators.
 * 
 * @author raidan
 * 
 */

public class Config {

	private final static Logger logger = LoggerFactory.getLogger(Config.class);

	private Config() {
		// singleton
	}

	private static Config instance;

	/**
	 * Singleton ^_^
	 * 
	 * @return instance of config class
	 */
	public static synchronized Config getInstance() {
		if (instance == null) {
			instance = new Config();
			instance.prepareConfig();
		}
		return instance;
	}

	public static synchronized void setDebugInstance() {
		getInstance().readOnly = true;
	}

	private boolean readOnly = false;

	private final static String DEFAULT_COMMON_CONFIG_NAME = "config.ini";

	private Configuration currentConfigInstance;
	private Configuration commonConfigInstance;
	private FileConfiguration configInstance;

	@SuppressWarnings("deprecation")
	protected void prepareConfig() {
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

			configInstance = new org.apache.commons.configuration.INIConfiguration(DEFAULT_COMMON_CONFIG_NAME);
			configInstance.setEncoding("UTF-8");

			currentConfigInstance = configInstance.subset("current");
			commonConfigInstance = configInstance.subset("common");

		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	private final static String PARAM_CURRENT_SOURCE_MODE = "sourceMode";
	private final static String PARAM_CURRENT_BACKGROUND = "imageBackground";

	private final static String PARAM_LOOK_AND_FEEL = "lookAndFeel";
	private final static String PARAM_ROWS_DELETE_MODE = "rowsDeleteMode";
	private final static String PARAM_TRANSACTION_MODE = "transactionMode";
	private final static String PARAM_DATA_DELETION_CHECK = "dataDeletionCheck";

	public static enum SourceMode {
		LEFT_TOP("Разместить слева сверху"), //
		CENTER("Разместить в центре"), //
		SCALE("Отмасштабировать и дополнить"), //
		SCALE_IF_LEFT_TOP("Масштаб. для большой картинки; иначе разместить слева сверху"), //
		SCALE_IF_CENTER("Масштаб. для большой картинки; иначе разместить в центре");

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
		CONFIRM("Требовать подтверждение удаления каждой записи"), //
		CONFIRM_MULTPLES("Требовать подтверждение только при удалении нескольких строк"), //
		/*
		 * CONFIRM_USERS_ONLY("Требовать подтверждения только у пользователей"),
		 */
		NO_CONFIRM("Удалять без подтверждения"); //

		private String title;

		private DeletionMode(String title) {
			this.title = title;
		}

		public String toString() {
			return title;
		}
	}

	/**
	 * 
	 * @author raidan
	 * 
	 */
	public static enum DeletionCheckMode {
		ACQUIRE_THEN_CHECK("Сначала спросить, потом проверить возможность удаления."), //
		CHECK_THEN_ACQUIRE("Сначала проверить возможность удаления, потом спросить."), //
		NO_CHECK("Не проверять возможность удаления (выдавать SQL-исключение при ошибке).");

		private String title;

		private DeletionCheckMode(String title) {
			this.title = title;
		}

		public String toString() {
			return title;
		}
	}

	/**
	 * Enumeration for transaction(session) modes.
	 * 
	 * @author raidan
	 * 
	 */
	public static enum TransactionMode {
		COMMIT_EVERY_ROW("'commit' на каждую вставку/удаление"), // 
		COMMIT_BULK("'commit' на весь табличный интерфейс");

		private String title;

		private TransactionMode(String title) {
			this.title = title;
		}

		public String toString() {
			return title;
		}
	}

	private SourceMode currentSourceMode = null;

	public SourceMode getCurrentSourceMode() {
		if (currentSourceMode != null) {
			return currentSourceMode;
		}
		final SourceMode default_ = SourceMode.LEFT_TOP;
		try {
			currentSourceMode = SourceMode.valueOf(this.currentConfigInstance.getString(PARAM_CURRENT_SOURCE_MODE,
					default_.name()));
			return currentSourceMode;
		} catch (IllegalArgumentException iae) {
			return default_;
		}
	}

	private Color currentColor = null;

	public Color getCurrentBackground() {
		if (currentColor != null) {
			return currentColor;
		}
		final Color default_ = Color.WHITE;
		currentColor = new Color(this.currentConfigInstance.getInt(PARAM_CURRENT_BACKGROUND, default_.getRGB()));
		return currentColor;
	}

	public String getLookAndFeel() {
		final String default_ = UIManager.getSystemLookAndFeelClassName();
		return this.commonConfigInstance.getString(PARAM_LOOK_AND_FEEL, default_);
	}

	private DeletionMode currentRowsDeletionMode = null;

	public DeletionMode getRowsDeleteMode() {
		if (currentRowsDeletionMode != null) {
			return currentRowsDeletionMode;
		}
		final DeletionMode default_ = DeletionMode.CONFIRM;
		try {
			currentRowsDeletionMode = DeletionMode.valueOf(this.commonConfigInstance.getString(PARAM_ROWS_DELETE_MODE,
					default_.name()));
			return currentRowsDeletionMode;
		} catch (IllegalArgumentException iae) {
			return default_;
		}
	}

	private TransactionMode currentTransactionMode = null;

	public TransactionMode getTransactionMode() throws PersistentException {
		if (currentTransactionMode != null) {
			return currentTransactionMode;
		}
		final TransactionMode default_ = TransactionMode.COMMIT_EVERY_ROW;
		try {
			currentTransactionMode = TransactionMode.valueOf(this.commonConfigInstance.getString(
					PARAM_TRANSACTION_MODE, default_.name()));

			AppHelper.getInstance().setDatabaseSessionType(
					currentTransactionMode == TransactionMode.COMMIT_BULK ? SessionType.APP_BASE
							: SessionType.THREAD_BASE);
			return currentTransactionMode;
		} catch (IllegalArgumentException iae) {
			return default_;
		}
	}

	private DeletionCheckMode currentDeletionCheckMode = null;

	public DeletionCheckMode getDeletionCheckMode() {
		if (currentDeletionCheckMode != null) {
			return currentDeletionCheckMode;
		}
		final DeletionCheckMode default_ = DeletionCheckMode.ACQUIRE_THEN_CHECK;
		try {
			currentDeletionCheckMode = DeletionCheckMode.valueOf(this.commonConfigInstance.getString(
					PARAM_DATA_DELETION_CHECK, default_.name()));
			return currentDeletionCheckMode;
		} catch (IllegalArgumentException iae) {
			return default_;
		}
	}

	public boolean setCurrentSourceMode(SourceMode value) {

		boolean res = this.setCurrentParameterImpl(PARAM_CURRENT_SOURCE_MODE, this.getCurrentSourceMode().name(), value
				.name());
		this.currentSourceMode = value;
		return res;
	}

	public boolean setCurrentBackground(Color value) {
		boolean res = this.setCurrentParameterImpl(PARAM_CURRENT_BACKGROUND, this.getCurrentBackground().getRGB(),
				value.getRGB());
		this.currentColor = value;
		return res;
	}

	public boolean setLookAndFeel(String value) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		if (this.setCommonParameterImpl(PARAM_LOOK_AND_FEEL, this.getLookAndFeel(), value)) {
			AppHelper.getInstance().updateUI(value);
			return true;
		} else {
			return false;
		}
	}

	public boolean setRowsDeleteMode(DeletionMode value) {

		boolean res = this
				.setCommonParameterImpl(PARAM_ROWS_DELETE_MODE, this.getRowsDeleteMode().name(), value.name());
		this.currentRowsDeletionMode = value;
		return res;
	}

	public boolean setTransactionMode(TransactionMode value) throws PersistentException {
		boolean res = this.setCommonParameterImpl(PARAM_TRANSACTION_MODE, this.getTransactionMode().name(), value
				.name());
		this.currentTransactionMode = value;
		if (res) {
			AppHelper.getInstance().setDatabaseSessionType(
					value == TransactionMode.COMMIT_BULK ? SessionType.APP_BASE : SessionType.THREAD_BASE);
			return true;
		} else {
			return false;
		}
	}

	public boolean setDeletionCheckModeMode(DeletionCheckMode value) {

		boolean res = this.setCommonParameterImpl(PARAM_DATA_DELETION_CHECK, this.getDeletionCheckMode().name(), value
				.name());
		this.currentDeletionCheckMode = value;
		return res;
	}

	//

	private boolean setParameterImpl(Configuration config, String paramName, Object oldValue, Object newValue) {
		//		if (readOnly) {
		//			return false;
		//		}
		//		logger.debug("OLD : " + oldValue.getClass() + ", NEW: " + newValue.getClass());
		if (Utils.equals(newValue, oldValue)) {
			return false;
		} else {
			config.setProperty(paramName, newValue);
			return true;
		}
	}

	private boolean setCurrentParameterImpl(String paramName, Object oldValue, Object newValue) {
		return this.setParameterImpl(this.currentConfigInstance, paramName, oldValue, newValue);
	}

	private boolean setCommonParameterImpl(String paramName, Object oldValue, Object newValue) {
		return this.setParameterImpl(this.commonConfigInstance, paramName, oldValue, newValue);
	}

	public void storeWindowPosition(Window window) {
		if (readOnly) {
			return;
		}

		if (window == null) {
			logger.error("Attemp to save empty window.");
			return;
		}

		logger.debug("Saving window position for window " + window.getName());

		Configuration config = this.configInstance.subset("window-" + window.getName());
		config.setProperty("x", window.getX());
		config.setProperty("y", window.getY());
		config.setProperty("width", window.getWidth());
		config.setProperty("height", window.getHeight());

		Component component = Utils.getChildNamed(window, "scaleButton");
		if (component != null && component instanceof JCheckBox) {
			config.setProperty("scaleButton", ((JCheckBox) component).isSelected());
		}
	}

	public void storeWindowCheckBoxes(Window window, JCheckBox... checkBoxes) {
		if (readOnly) {
			return;
		}

		if (window == null) {
			logger.error("Attemp to set empty window.");
			return;
		}

		Configuration config = this.configInstance.subset("window-" + window.getName() + "-checks");
		for (JCheckBox box : checkBoxes) {
			config.setProperty(box.getName(), box.isSelected());
		}

	}

	public void loadWindowPosition(Window window) {

		if (readOnly) {
			return;
		}

		if (window == null) {
			logger.error("Attemp to get empty window.");
			return;
		}

		logger.debug("Loading window position for window " + window.getName());

		Configuration config = this.configInstance.subset("window-" + window.getName());
		window.setBounds(config.getInt("x", window.getX()), config.getInt("y", window.getY()), config.getInt("width",
				window.getWidth()), config.getInt("height", window.getHeight()));

		Component component = Utils.getChildNamed(window, "scaleButton");
		if (component != null && component instanceof JCheckBox) {
			boolean selected = config.getBoolean("scaleButton", false);
			JCheckBox box = (JCheckBox) component;
			if (selected != box.isSelected()) {

				logger.debug("Click on checkBox " + box.getName());
				box.doClick();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void loadWindowCheckBoxes(Window window) {
		if (readOnly) {
			return;
		}

		if (window == null) {
			logger.error("Attemp to set empty window.");
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

					logger.debug("Click on additional checkBox " + box.getName());
					box.doClick();
				}
			}
		}

	}

	public void saveCommonConfig() throws ConfigurationException {
		if (readOnly) {
			return;
		}
		if (this.configInstance != null) {
			this.configInstance.save();
		}
	}

	public void saveCurrentConfig() throws ConfigurationException {
		this.saveCommonConfig();
	}

}