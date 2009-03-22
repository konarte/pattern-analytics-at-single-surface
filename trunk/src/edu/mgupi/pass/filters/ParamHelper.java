package edu.mgupi.pass.filters;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONValue;

public class ParamHelper {

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
		Collection<Param> paramList = filter.getParams();
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

	protected static Map<String, Object> convertParamsToValues(IFilter filter) {
		Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
		if (filter == null) {
			return paramMap;
		}
		Collection<Param> paramList = filter.getParams();
		if (paramList == null) {
			return paramMap;
		}

		for (Param param : paramList) {
			paramMap.put(param.getName(), param.getValue());
		}

		return paramMap;
	}

	public static String convertParamsToJSON(IFilter filter) {
		if (filter == null) {
			throw new IllegalArgumentException("Internal error. Filter is null.");
		}

		return JSONValue.toJSONString(convertParamsToValues(filter));
	}
}
