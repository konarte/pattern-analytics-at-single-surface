package edu.mgupi.pass.filters;

/**
 * Class for describe parameters can be processed in filters. This is only
 * describe! Filters will receive simple map of names and values on processing.
 * 
 * @author raidan
 * 
 */
public class Param {

	public Param(String name, String title, TYPES type, Object default_) {
		this.name = name;
		this.title = title;
		this.type = type;
		this.default_ = default_;
		this.value = this.default_;
	}

	public Param(String name, String title, TYPES type, Object default_, int lowBorder, int hiBorder) {
		this(name, title, type, default_);
		this.low_border = lowBorder;
		this.hi_border = hiBorder;
	}

	public Param(String name, String title, Object default_, Object[] allowedValues, String[] visualValues) {
		this(name, title, TYPES.LIST, default_);

		if (allowedValues != null && allowedValues.length > 0) {
			if (visualValues != null && visualValues.length > 0) {
				if (allowedValues.length != visualValues.length) {
					throw new IllegalArgumentException("visualValues count don't equals allowedValues. "
							+ "Please, set correct equation between allowed and visual elemenet.");
				}
			} else {
				// set visual same as allowed
				visualValues = new String[allowedValues.length];
				System.arraycopy(allowedValues, 0, visualValues, 0, allowedValues.length);
			}
		} else {
			throw new IllegalArgumentException("Incorrect construction call. Please, specify allowedValues.");
		}
		this.allowed_values = allowedValues;
		this.visual_values = visualValues;
	}

	public static enum TYPES {
		PX, LIST, STRING, INT
	};

	private String name;
	private String title;
	private TYPES type;
	private Object default_;
	private int low_border;
	private int hi_border;
	private Object[] allowed_values;
	private String[] visual_values;

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
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

	public int getHi_border() {
		return hi_border;
	}

	public Object[] getAllowed_values() {
		return allowed_values;
	}

	public String[] getVisual_values() {
		return visual_values;
	}

	public Object getValue() {
		return value;
	}

	private Object value;

	public void setValue(Object value) {
		this.value = value;
	}
}
