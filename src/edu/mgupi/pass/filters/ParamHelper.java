package edu.mgupi.pass.filters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParamHelper {

	private final static Logger logger = LoggerFactory.getLogger(ParamHelper.class);

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

	protected static Map<String, Object> updateToMap(Collection<Param> parameters) {
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

	protected static void updateFromMap(Collection<Param> paramList, Map<String, String> map)
			throws NumberFormatException, IllegalParameterValueException {
		if (paramList == null) {
			return;
		}

		for (Param param : paramList) {
			String value = map.get(param.getName());

			logger.trace("Attempt to set value '{}' for parameter '{}'.", (value == null ? "<null>" : value), param);

			param.setStringValue(value);
		}

	}

	public static String convertParamsToJSON(IFilter filter) {
		if (filter == null) {
			throw new IllegalArgumentException("Internal error. Filter is null.");
		}
		return JSONValue.toJSONString(updateToMap(filter.getParams()));
	}

	public static void fillParametersFromJSON(IFilter filter, String value) throws ParseException,
			NumberFormatException, IllegalParameterValueException {
		if (filter == null) {
			throw new IllegalArgumentException("Internal error. Filter is null.");
		}
		fillParametersFromJSON(filter.getParams(), value);
	}

	public static String convertParamsToJSON(Collection<Param> params) {
		return JSONValue.toJSONString(updateToMap(params));
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

		updateFromMap(params, (Map<String, String>) JSONValue.parseWithException(value));
	}

	public static Collection<Param> cloneParameters(Collection<Param> sourceParameters)
			throws CloneNotSupportedException {
		Collection<Param> cloned = new ArrayList<Param>(sourceParameters.size());
		for (Param param : sourceParameters) {
			cloned.add((Param) param.clone());
		}
		return cloned;
	}

	public static void restoreParameterValues(Collection<Param> oldParameters, Collection<Param> newParameters)
			throws NoSuchParamException, IllegalParameterValueException {
		for (Param param : oldParameters) {
			Param storeParam = ParamHelper.getParameter(param.getName(), newParameters);
			logger.debug(param.getName() + " " + storeParam.getValue() + " -> " + param.getValue());
			param.setValue(storeParam.getValue());
		}
	}
}
