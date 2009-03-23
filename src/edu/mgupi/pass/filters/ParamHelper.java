package edu.mgupi.pass.filters;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

public class ParamHelper {

	public static Param getParameter(String name, Collection<Param> params) throws NoSuchParamException {
		return searchParameter(name, params, true);
	}

	public static Param getParameter(String name, IFilter filter) throws NoSuchParamException {
		return searchParameter(name, filter, true);
	}

	public static Param searchParameter(String name, IFilter filter) throws NoSuchParamException {
		return searchParameter(name, filter, false);
	}

	protected static Param searchParameter(String name, IFilter filter, boolean mandatory) throws NoSuchParamException {
		if (name == null) {
			throw new IllegalArgumentException("Internal error. Name is null.");
		}

		if (filter == null) {
			return null;
		}
		return searchParameter(name, filter.getParams(), mandatory);
	}

	protected static Param searchParameter(String name, Collection<Param> paramList, boolean mandatory)
			throws NoSuchParamException {
		if (name == null) {
			throw new IllegalArgumentException("Internal error. Name is null.");
		}
		if (paramList == null) {
			return null;
		}

		for (Param param : paramList) {
			if (name.equals(param.getName())) {
				return param;
			}
		}

		if (mandatory) {
			throw new NoSuchParamException("Unable to find parameter '" + name + "' into parameters list '" + paramList
					+ "'. This parameter is required.");
		}

		return null;
	}

	protected static Object getParameterM(String key, Map<String, Object> params) throws NoSuchParamException {
		if (key == null) {
			throw new IllegalArgumentException("Internal error. Key is null.");
		}
		if (params == null) {
			throw new IllegalArgumentException("Internal error. Params is null.");
		}

		Object value = params.get(key);
		if (value == null) {
			throw new NoSuchParamException("Internal error. Unable to find value by requested key '" + key + "'. Map '"
					+ params + "' does not contains this required parameter.");
		}
		return value;
	}

	protected static Map<String, Object> convertParamsToValues(Collection<Param> parameters) {
		Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
		if (parameters == null) {
			return paramMap;
		}
		Collection<Param> paramList = parameters;
		if (paramList == null) {
			return paramMap;
		}

		for (Param param : paramList) {
			paramMap.put(param.getName(), param.getStringValue());
		}

		return paramMap;
	}

	protected static Map<String, Object> convertParamsToValues(IFilter filter) {
		return convertParamsToValues(filter == null ? null : filter.getParams());
	}

	protected static void updateFilterFromMap(Collection<Param> paramList, Map<String, String> map)
			throws NumberFormatException, IllegalParameterValueException {
		if (paramList == null) {
			return;
		}

		for (Param param : paramList) {
			String value = map.get(param.getName());
			System.out.println("Attempt to set value '" + (value == null ? "<null>" : value) + "' for parameter '"
					+ param.getName());
			param.setStringValue(value);
		}

	}

	protected static void updateFilterFromMap(IFilter filter, Map<String, String> map) throws NumberFormatException,
			IllegalParameterValueException {
		if (filter == null) {
			return;
		}
		updateFilterFromMap(filter.getParams(), map);
	}

	public static String convertParamsToJSON(IFilter filter) {
		if (filter == null) {
			throw new IllegalArgumentException("Internal error. Filter is null.");
		}

		return JSONValue.toJSONString(convertParamsToValues(filter));
	}

	public static String convertParamsToJSON(Collection<Param> params) {
		return JSONValue.toJSONString(convertParamsToValues(params));
	}

	@SuppressWarnings("unchecked")
	public static void fillParametersFromJSON(IFilter filter, String value) throws ParseException,
			NumberFormatException, IllegalParameterValueException {
		if (filter == null) {
			throw new IllegalArgumentException("Internal error. Filter is null.");
		}
		if (value == null) {
			throw new IllegalArgumentException("Internal error. Value is null.");
		}

		updateFilterFromMap(filter, (Map<String, String>) JSONValue.parseWithException(value));
	}

	@SuppressWarnings("unchecked")
	public static void fillParametersFromJSON(Collection<Param> params, String value) throws ParseException,
			NumberFormatException, IllegalParameterValueException {

		if (value == null) {
			throw new IllegalArgumentException("Internal error. Value is null.");
		}

		if (params == null) {
			return;
		}

		updateFilterFromMap(params, (Map<String, String>) JSONValue.parseWithException(value));
	}

}
