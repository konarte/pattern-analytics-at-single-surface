package edu.mgupi.pass.filters;

/**
 * Class for describe parameters can be processed in filters. This is only
 * describe! Filters will receive simple map of names and values on processing.
 * 
 * @author raidan
 * 
 */
public class Param implements Cloneable {

	public Param(String name, String title, TYPES type, Object default_) {
		this.name = name;
		this.title = title;
		this.type = type;
		this.default_ = default_;
		try {
			this.setValue(default_);
		} catch (IllegalParameterValueException iv) {
			throw new IllegalArgumentException(iv);
		}
	}

	public Param(String name, String title, TYPES type, Object default_, int lowBorder, int hiBorder) {
		this(name, title, type, default_);

		if (hiBorder < lowBorder) {
			throw new IllegalArgumentException("Incorrect using of hiBorder (" + hiBorder + ") and lowBorder ("
					+ lowBorder + "). lowBorder must be smaller.");
		}

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
		LIST, STRING, INT, DOUBLE, COLOR
	};

	private String name;
	private String title;
	private TYPES type;
	private Object default_;
	private int low_border = Integer.MIN_VALUE;
	private int hi_border = Integer.MAX_VALUE;
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

	public void setDefault_(Object default_) {
		this.default_ = default_;
	}

	private Object value;

	public void setValue(Object value) throws IllegalParameterValueException {

		if (value == null) {
			this.value = value;
			return;
		}

		if (this.allowed_values != null) {

			boolean found = false;
			for (Object allow : this.getAllowed_values()) {
				if (value.equals(allow)) {
					found = true;
					break;
				}
			}

			if (!found) {
				throw new IllegalParameterValueException("Parameter " + this + " attempt to use incorrect value "
						+ value + ". This is value not acceptable by allowed_parameters.");
			}
		}

		if (this.type == TYPES.DOUBLE) {
			if (value instanceof Long) {
				value = ((Long) value).doubleValue();
			} else {
				if (!(value instanceof Double)) {
					throw new IllegalParameterValueException("Parameter " + this
							+ " attempt to set up incorrect value " + value + " (" + value.getClass()
							+ "). This value must be a double.");
				}
			}
		}

		if (this.type == TYPES.INT) {
			if (value instanceof Long) {
				value = ((Long) value).intValue();
			} else {
				if (!(value instanceof Integer)) {
					throw new IllegalParameterValueException("Parameter " + this
							+ " attempt to set up incorrect value " + value + ". This value must be an integer.");
				}
			}

			int newValue = (Integer) value;
			if (newValue < this.low_border) {
				throw new IllegalParameterValueException("Parameter " + this + " attempt to set up incorrect value "
						+ value + ". This value must be greater that lowBorder " + this.low_border + ".");
			}

			if (newValue > this.hi_border) {
				throw new IllegalParameterValueException("Parameter " + this + " attempt to set up incorrect value "
						+ value + ". This value must be smaller that hiBorder " + this.hi_border + ".");
			}
		}

		if (this.type == TYPES.STRING) {
			if (!(value instanceof String)) {
				throw new IllegalParameterValueException("Parameter " + this + " attempt to set up incorrect value "
						+ value + ". This value must be a string.");
			}
		}

		this.value = value;
	}

	public void resetValue() throws IllegalParameterValueException {
		if (this.default_ != null) {
			this.setValue(this.default_);
		}
	}

	public String toString() {
		return this.title + " (" + this.name + ")";
	}

	public Object clone() throws CloneNotSupportedException {
		Param cloned = (Param) super.clone();

		return cloned;
	}
}
