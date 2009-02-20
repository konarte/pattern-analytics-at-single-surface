package edu.mgupi.pass.filters;

/**
 * Class for describe parameters can be processed in filters. This is only
 * describe! Filters will receive simple map of names and values on processing.
 * 
 * @author raidan
 * 
 */
public class Param {

	public Param(String name, TYPES type, Object default_) {
		this.name = name;
		this.type = type;
		this.default_ = default_;
	}

	public static enum TYPES {
		PX, LIST, STRING
	};

	private String name;
	private TYPES type;
	private Object default_;
	private int low_border;
	private int up_border;
	private Object[] allowed_values;

	public String getName() {
		return name;
	}

	public TYPES getType() {
		return type;
	}

	public Object getDefault_() {
		return default_;
	}

	public int getLow_border() {
		return low_border;
	}

	public int getUp_border() {
		return up_border;
	}

	public Object[] getAllowed_values() {
		return allowed_values;
	}

	public Object getValue() {
		return value;
	}

	private Object value;
}
