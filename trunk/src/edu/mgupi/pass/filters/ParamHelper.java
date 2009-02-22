package edu.mgupi.pass.filters;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ParamHelper {
	public static Object getParameter(String key, Map<String, Object> params) throws NoSuchParamException {
		if (key == null) {
			throw new IllegalArgumentException("Internal error. Key is null.");
		}
		if (key == null) {
			throw new IllegalArgumentException("Internal error. Params is null.");
		}

		Object value = params.get(key);
		if (value == null) {
			throw new NoSuchParamException("Internal error. Unable to find value by requested key '" + key + "'");
		}
		return value;
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
