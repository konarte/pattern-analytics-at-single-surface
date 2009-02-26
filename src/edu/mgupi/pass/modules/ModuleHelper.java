package edu.mgupi.pass.modules;

import java.util.Collection;

import edu.mgupi.pass.db.locuses.LocusModuleParams;
import edu.mgupi.pass.db.locuses.Locuses;

public class ModuleHelper {

	public static LocusModuleParams getParameter(String name, Locuses locus) throws ModuleParamException {
		if (locus == null) {
			return null;
		}
		return searchParameter(name, locus.getParams(), true);
	}

	//
	// public static LocusModuleParams getParameter(String name,
	// Collection<LocusModuleParams> paramList)
	// throws ModuleParamException {
	// return searchParameter(name, paramList, true);
	// }
	//
	// public static LocusModuleParams searchParameter(String name,
	// Collection<LocusModuleParams> paramList)
	// throws ModuleParamException {
	// return searchParameter(name, paramList, false);
	// }

	private static LocusModuleParams searchParameter(String name, Collection<LocusModuleParams> paramList,
			boolean mandatory) throws ModuleParamException {
		if (paramList == null) {
			return null;
		}

		if (name == null) {
			throw new IllegalArgumentException("Internal error. Name is null.");
		}
		for (LocusModuleParams param : paramList) {
			if (name.equals(param.getParamName())) {
				return param;
			}
		}

		if (mandatory) {
			throw new ModuleParamException("Unable to find module parameter '" + name + "' into parameters list '"
					+ paramList + "'. This parameter is required.");
		}

		return null;
	}

}
