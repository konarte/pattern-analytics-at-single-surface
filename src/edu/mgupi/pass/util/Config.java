package edu.mgupi.pass.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.FileConfiguration;
import org.apache.commons.configuration.INIConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	public String getCurrentSourceMode() {
		return this.currentConfigInstance.getString(PARAM_CURRENT_SOURCE_MODE);
	}

	public int getCurrentBackground() {
		return this.currentConfigInstance.getInt(PARAM_CURRENT_BACKGROUND);
	}

	public String getLookAndFeel() {
		return this.commonConfigInstance.getString(PARAM_LOOK_AND_FEEL);
	}

	public String getCurrentSourceMode(String default_) {
		return this.currentConfigInstance.getString(PARAM_CURRENT_SOURCE_MODE, default_);
	}

	public int getCurrentBackground(int default_) {
		return this.currentConfigInstance.getInt(PARAM_CURRENT_BACKGROUND, default_);
	}

	public String getLookAndFeel(String default_) {
		return this.commonConfigInstance.getString(PARAM_LOOK_AND_FEEL, default_);
	}

	public void setCurrentSourceMode(String value) {
		this.currentConfigInstance.setProperty(PARAM_CURRENT_SOURCE_MODE, value);
	}

	public void setCurrentBackground(int value) {
		this.currentConfigInstance.setProperty(PARAM_CURRENT_BACKGROUND, value);
	}

	public void setLookAndFeel(String value) {
		this.commonConfigInstance.setProperty(PARAM_LOOK_AND_FEEL, value);
	}

	// public Configuration getCurrentConfig() {
	// return this.currentConfigInstance;
	// }
	//
	// public Configuration getCommonConfig() {
	// return this.commonConfigInstance;
	// }

	public void saveCommonConfig() throws ConfigurationException {
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