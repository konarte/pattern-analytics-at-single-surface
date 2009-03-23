package edu.mgupi.pass.face;

import java.util.Arrays;
import java.util.Comparator;

import edu.mgupi.pass.db.locuses.LFilters;
import edu.mgupi.pass.db.locuses.LFiltersFactory;
import edu.mgupi.pass.db.locuses.LModules;
import edu.mgupi.pass.db.locuses.LModulesFactory;

public class MainFrameDataStorage {

	private MainFrameDataStorage() {
		//
	}

	private static MainFrameDataStorage instance;

	public static synchronized MainFrameDataStorage getInstance() {
		if (instance == null) {
			instance = new MainFrameDataStorage();
		}
		return instance;
	}

	protected static synchronized void reset() {
		instance = null;
	}

	private LModules[] moduleList = null;
	private LFilters[] filterList = null;

	public LModules[] listLModulesIface() {
		try {
			return this.listLModules();
		} catch (Throwable t) {
			AppHelper.showExceptionDialog("Error wgeb loading modules list", t);
			return null;
		}
	}

	public LModules[] listLModules() throws Exception {
		if (this.moduleList == null) {
			moduleList = LModulesFactory.listLModulesByQuery(null, null);
			if (moduleList == null || moduleList.length == 0) {
				throw new Exception(
						"No registered analyze modules found. Unable to work. Please, fill table 'LModules'.");
			}
			Arrays.sort(moduleList, new Comparator<LModules>() {
				@Override
				public int compare(LModules o1, LModules o2) {
					if (o1 != null && o2 != null) {
						return o1.getName().compareTo(o2.getName());
					}
					return 0;
				}
			});

		}
		return this.moduleList;
	}

	public LFilters[] listLFiltersIface() {
		try {
			return this.listLFilters();
		} catch (Throwable t) {
			AppHelper.showExceptionDialog("Error wgeb loading filters list", t);
			return null;
		}
	}

	public LFilters[] listLFilters() throws Exception {
		if (this.filterList == null) {
			filterList = LFiltersFactory.listLFiltersByQuery(null, null);
			if (filterList == null || filterList.length == 0) {
				throw new Exception("No registered filters found. Unable to work. Please, fill table 'LFilters'.");
			}

			Arrays.sort(filterList, new Comparator<LFilters>() {
				@Override
				public int compare(LFilters o1, LFilters o2) {
					if (o1 != null && o2 != null) {
						return o1.getName().compareTo(o2.getName());
					}
					return 0;
				}
			});
		}
		return this.filterList;

	}

}
