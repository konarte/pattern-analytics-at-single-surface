package edu.mgupi.pass.modules;

import java.util.HashMap;
import java.util.Map;

public class ModuleStore {

	private Map<String, Object> properties = new HashMap<String, Object>();

	/**
	 * Convert collection of internal options to Map (name of option, value of
	 * option)
	 * 
	 * @return
	 */
	public Map<String, Object> getProperties() {
		return properties;
	}

	/**
	 * Set's property to this object
	 * 
	 * @param name
	 * @param value
	 */
	public void setProperty(String name, Object value) {
		this.properties.put(name, value);
	}
}
