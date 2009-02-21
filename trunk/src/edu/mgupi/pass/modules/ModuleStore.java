package edu.mgupi.pass.modules;

import java.util.HashMap;
import java.util.Map;

import edu.mgupi.pass.db.surfaces.Surfaces;

public class ModuleStore {

	private Surfaces surface;

	public Surfaces getSurface() {
		return surface;
	}

	public void setSurface(Surfaces surface) {
		this.surface = surface;
	}

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
