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
	public final static String PARAM_CURRENT_BACKGROUND = "imageBackground";

	public final static String PARAM_LOOK_AND_FEEL = "lookAndFeel";
	public final static String PARAM_FILTER_DELETE_MODE = "filterDeleteMode";

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

	public static enum SourceMode {
		center("Разместить в центре"), left_top("Разместить слева сверху"), scale("Отмасштабировать");

		private String title;

		private SourceMode(String title) {
			this.title = title;
		}

		public String toString() {
			return title;
		}
	};

	public static enum DeletionMode {
		confirm("Требовать подтверждение удаления каждого фильтра"), no_confirm("Удалять без подтверждения"), confirm_only_users(
				"Требовать подтверждения только у пользователей");

		private String title;

		private DeletionMode(String title) {
			this.title = title;
		}

		public String toString() {
			return title;
		}
	}

	public SourceMode getCurrentSourceMode() {
		final SourceMode default_ = SourceMode.left_top;
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
		final DeletionMode default_ = DeletionMode.confirm;
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
	//
	//	static class ComplexParameter<E> {
	//		//private Object[] values;
	//		private Enum<? extends E> enum_;
	//		private Enum<? extends E> default_;
	//
	//		private String[] visual;
	//
	//		public ComplexParameter(Enum enum_, String visual[], Enum<? extends E> default_) {
	//			//this.values = values;
	//			this.enum_ = enum_;
	//			this.visual = visual;
	//			this.default_ = default_;
	//
	//			this.confirmValue(default_);
	//		}
	//
	//		public Object confirmValue(Enum<? extends E> enum_) {
	//			if (enum_ == null) {
	//				return default_;
	//			}
	//			return enum_;
	//
	//			//			if (enum_.equals(other))
	//			//			for (Object obj : this.values) {
	//			//				if (obj.equals(value)) {
	//			//					return value;
	//			//				}
	//			//			}
	//			//			return default_;
	//		}
	//
	//		public String[] getVisualValues() {
	//			return this.visual;
	//		}
	//
	//		public Enum<? extends E> getDefaultValue() {
	//			return default_;
	//		}
	//	}
}