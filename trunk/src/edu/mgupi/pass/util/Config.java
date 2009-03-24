package edu.mgupi.pass.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JCheckBox;
import javax.swing.UIManager;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.FileConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private final static String PARAM_FILTER_DELETE_MODE = "filterDeleteMode";

	public static enum SourceMode {
		CENTER("Разместить в центре"), LEFT_TOP("Разместить слева сверху"), SCALE("Отмасштабировать");

		private String title;

		private SourceMode(String title) {
			this.title = title;
		}

		public String toString() {
			return title;
		}
	};

	public static enum DeletionMode {
		CONFIRM("Требовать подтверждение удаления каждого фильтра"), NO_CONFIRM("Удалять без подтверждения"), //
		CONFIRM_USERS_ONLY("Требовать подтверждения только у пользователей");

		private String title;

		private DeletionMode(String title) {
			this.title = title;
		}

		public String toString() {
			return title;
		}
	}

	public SourceMode getCurrentSourceMode() {
		final SourceMode default_ = SourceMode.LEFT_TOP;
		try {
			return readOnly ? default_ : SourceMode.valueOf(this.currentConfigInstance.getString(
					PARAM_CURRENT_SOURCE_MODE, default_.name()));
		} catch (IllegalArgumentException iae) {
			return default_;
		}
	}

	public int getCurrentBackground() {
		final int default_ = Color.WHITE.getRGB();
		return readOnly ? default_ : this.currentConfigInstance.getInt(PARAM_CURRENT_BACKGROUND, default_);
	}

	public String getLookAndFeel() {
		final String default_ = UIManager.getSystemLookAndFeelClassName();
		return readOnly ? default_ : this.commonConfigInstance.getString(PARAM_LOOK_AND_FEEL, default_);
	}

	public DeletionMode getFilterDeleteMode() {
		final DeletionMode default_ = DeletionMode.CONFIRM;
		try {
			return readOnly ? default_ : DeletionMode.valueOf(this.commonConfigInstance.getString(
					PARAM_FILTER_DELETE_MODE, default_.name()));
		} catch (IllegalArgumentException iae) {
			return default_;
		}
	}

	public boolean setCurrentSourceMode(SourceMode value) {
		return this.setCurrentParameterImpl(PARAM_CURRENT_SOURCE_MODE, this.getCurrentSourceMode().name(),
				value == null ? null : value.name());
	}

	public boolean setCurrentBackground(int value) {
		return this.setCurrentParameterImpl(PARAM_CURRENT_BACKGROUND, this.getCurrentBackground(), value);
	}

	public boolean setLookAndFeel(String value) {
		return this.setCommonParameterImpl(PARAM_LOOK_AND_FEEL, this.getLookAndFeel(), value);
	}

	public boolean setFilterDeleteConfirm(DeletionMode value) {
		return this.setCommonParameterImpl(PARAM_FILTER_DELETE_MODE, this.getFilterDeleteMode().name(),
				value == null ? null : value.name());
	}

	private boolean setParameterImpl(Configuration config, String paramName, Object oldValue, Object newValue) {
		if (readOnly) {
			return false;
		}
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

		Component component = SwingHelper.getChildNamed(window, "scaleButton");
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

		Component component = SwingHelper.getChildNamed(window, "scaleButton");
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
			Component component = SwingHelper.getChildNamed(window, key);
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