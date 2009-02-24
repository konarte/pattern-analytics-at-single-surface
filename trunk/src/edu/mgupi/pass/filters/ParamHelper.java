package edu.mgupi.pass.filters;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ParamHelper {
	public static Object getParameterM(String key, Map<String, Object> params) throws NoSuchParamException {
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

	public static Param getParameterL(String name, IFilter filter) throws NoSuchParamException {
		return searchParameter(name, filter.getParams(), true);
	}

	public static Param getParameterL(String name, Collection<Param> paramList) throws NoSuchParamException {
		return searchParameter(name, paramList, true);
	}

	public static Param searchParameterL(String name, Collection<Param> paramList) throws NoSuchParamException {
		return searchParameter(name, paramList, false);
	}

	private static Param searchParameter(String name, Collection<Param> paramList, boolean mandatory)
			throws NoSuchParamException {
		if (paramList == null) {
			return null;
		}

		if (name == null) {
			throw new IllegalArgumentException("Internal error. Name is null.");
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

	public static Map<String, Object> convertParamsToValues(Collection<Param> paramList) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (paramList == null) {
			return paramMap;
		}

		for (Param param : paramList) {
			paramMap.put(param.getName(), param.getValue());
		}

		return paramMap;
	}
}
