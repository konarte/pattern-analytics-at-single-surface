package edu.mgupi.pass.util;

import java.awt.Component;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JCheckBox;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.FileConfiguration;
import org.apache.commons.configuration.INIConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.face.SwingHelper;

/**
 * Config class. Using apache config
 * 
 * @author raidan
 * 
 */

// Well I can't make HierarchicalINIConfiguration to work
@SuppressWarnings("deprecation")
public class Config {

	private final static Logger logger = LoggerFactory.getLogger(Config.class);

	private Config() {
		//
	}

	private static volatile Config instance;

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

	private boolean readOnly = false;

	public void setReadOnly() {
		readOnly = true;
	}

	private final static String DEFAULT_COMMON_CONFIG_NAME = "config.ini";

	private Configuration currentConfigInstance;
	private Configuration commonConfigInstance;
	private FileConfiguration configInstance;

	// protected void prepareConfig() {
	// this.prepareConfig(configName == null ? DEFAULT_CONFIG_NAME :
	// configName);
	// }

	protected void prepareConfig() {
		try {
			logger.trace("Loading config from file {}.", configName);

			File file = new File(DEFAULT_COMMON_CONFIG_NAME);
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException io) {
					throw new RuntimeException(io);
				}
			}

			configInstance = new INIConfiguration(DEFAULT_COMMON_CONFIG_NAME);
			configInstance.setEncoding("UTF-8");

			currentConfigInstance = configInstance.subset("current");
			commonConfigInstance = configInstance.subset("common");

			// currentConfigInstance = new PropertiesConfiguration();
			// Properties props =
			// this.commonConfigInstance.getProperties("current.");
			// for (Object key : props.keySet()) {
			// String sKey = (String) key;
			// this.currentConfigInstance.setProperty(sKey.substring("current.".length()),
			// this.commonConfigInstance
			// .getProperty(sKey));
			// }

		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	private String configName = null;

	public void setConfigName(String configName) {
		this.configName = configName;
		this.prepareConfig();
	}

	public void resetConfigName() {
		this.configName = null;
		this.prepareConfig();
	}

	public final static String PARAM_CURRENT_SOURCE_MODE = "sourceMode";
	public final static String PARAM_CURRENT_BACKGROUND = "background";

	public final static String PARAM_LOOK_AND_FEEL = "laf";

	//	public String getCurrentSourceMode() {
	//		return this.currentConfigInstance.getString(PARAM_CURRENT_SOURCE_MODE);
	//	}
	//
	//	public int getCurrentBackground() {
	//		return this.currentConfigInstance.getInt(PARAM_CURRENT_BACKGROUND);
	//	}
	//
	//	public String getLookAndFeel() {
	//		return this.commonConfigInstance.getString(PARAM_LOOK_AND_FEEL);
	//	}

	public String getCurrentSourceMode(String default_) {
		return readOnly ? default_ : this.currentConfigInstance.getString(PARAM_CURRENT_SOURCE_MODE, default_);
	}

	public int getCurrentBackground(int default_) {
		return readOnly ? default_ : this.currentConfigInstance.getInt(PARAM_CURRENT_BACKGROUND, default_);
	}

	public String getLookAndFeel(String default_) {
		return readOnly ? default_ : this.commonConfigInstance.getString(PARAM_LOOK_AND_FEEL, default_);
	}

	public void setCurrentSourceMode(String value) {
		if (readOnly) {
			return;
		}
		this.currentConfigInstance.setProperty(PARAM_CURRENT_SOURCE_MODE, value);
	}

	public void setCurrentBackground(int value) {
		if (readOnly) {
			return;
		}
		this.currentConfigInstance.setProperty(PARAM_CURRENT_BACKGROUND, value);
	}

	public void setLookAndFeel(String value) {
		if (readOnly) {
			return;
		}
		this.commonConfigInstance.setProperty(PARAM_LOOK_AND_FEEL, value);
	}

	public void setWindowPosition(Window window) {
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

	public void setWindowCheckBoxes(Window window, JCheckBox... checkBoxes) {
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

	public void getWindowPosition(Window window) {

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
	public void getWindowCheckBoxes(Window window) {
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

	// public Configuration getCurrentConfig() {
	// return this.currentConfigInstance;
	// }
	//
	// public Configuration getCommonConfig() {
	// return this.commonConfigInstance;
	// }

	public void saveCommonConfig() throws ConfigurationException {
		if (readOnly) {
			return;
		}
		if (this.configInstance != null) {
			this.configInstance.save();
		}
	}

	// @SuppressWarnings("unchecked")
	public void saveCurrentConfig() throws ConfigurationException {
		// Iterator<String> iter = this.currentConfigInstance.getKeys();
		// while (iter.hasNext()) {
		// String key = iter.next();
		// this.commonConfigInstance.setProperty("current." + key,
		// this.commonConfigInstance.getProperty(key));
		// }
		//
		this.saveCommonConfig();
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

}