package edu.mgupi.pass.face.gui;

import java.util.Arrays;
import java.util.Comparator;

import org.orm.PersistentException;

import edu.mgupi.pass.db.locuses.LFilters;
import edu.mgupi.pass.db.locuses.LFiltersFactory;
import edu.mgupi.pass.db.locuses.LModules;
import edu.mgupi.pass.db.locuses.LModulesFactory;
import edu.mgupi.pass.filters.FilterException;
import edu.mgupi.pass.filters.FilterNotFoundException;
import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.modules.IModule;
import edu.mgupi.pass.modules.ModuleException;
import edu.mgupi.pass.modules.ModuleNotFoundException;

/**
 * Class for store data, using in application
 * 
 * @author raidan
 * 
 */
public class AppDataStorage {

	private AppDataStorage() {
		//
	}

	private static AppDataStorage instance;

	public static synchronized AppDataStorage getInstance() {
		if (instance == null) {
			instance = new AppDataStorage();
		}
		return instance;
	}

	protected static synchronized void reset() {
		instance = null;
	}

	private LModules[] moduleList = null;
	private LFilters[] filterList = null;

	public void checkUsingModule(IModule moduleInstance) throws PersistentException, ModuleException {
		if (this.searchModuleByInstance(moduleInstance) == null) {
			throw new ModuleNotFoundException("Module '" + moduleInstance.getClass().getName() + "' ('"
					+ moduleInstance.getName() + "') is not registered. Unable to use.");
		}
	}

	public void checkUsingFilter(IFilter filterInstance) throws PersistentException, FilterException {
		if (this.searchFilterByInstance(filterInstance) == null) {
			throw new FilterNotFoundException("Filter '" + filterInstance.getClass().getName() + "' ('"
					+ filterInstance.getName() + "') is not registered. Unable to use.");
		}
	}

	public LModules getModuleByInstance(IModule moduleInstance) throws PersistentException, ModuleException {
		LModules module = this.searchModuleByInstance(moduleInstance);
		if (module == null) {

			throw new ModuleNotFoundException("Unable to find registered module '"
					+ moduleInstance.getClass().getName() + "' ('" + moduleInstance.getName() + "').");
		}
		return module;
	}

	public LModules searchModuleByInstance(IModule moduleInstance) throws PersistentException {

		if (moduleInstance == null) {
			throw new IllegalArgumentException("Internal error. 'moduleInstance' must be not null.");
		}

		String className = moduleInstance.getClass().getName();
		for (LModules module : this.listLModulesImpl()) {
			if (module.getCodename().equals(className)) {
				return module;
			}
		}
		return null;
	}

	public LModules[] listLModules() {
		try {
			return this.listLModulesImpl();
		} catch (Throwable t) {
			AppHelper.showExceptionDialog(null, "Error wgeb loading modules list", t);
			return null;
		}
	}

	public LModules[] listLModulesImpl() throws PersistentException {
		if (this.moduleList == null) {
			moduleList = LModulesFactory.listLModulesByQuery(null, null);
			if (moduleList == null || moduleList.length == 0) {
				throw new PersistentException(
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

	public LFilters searchFilterByInstance(IFilter filterInstance) throws PersistentException {

		if (filterInstance == null) {
			throw new IllegalArgumentException("Internal error. 'filterInstance' must be not null.");
		}

		String className = filterInstance.getClass().getName();
		for (LFilters module : this.listLFiltersImpl()) {
			if (module.getCodename().equals(className)) {
				return module;
			}
		}
		return null;
	}

	public LFilters[] listLFilters() {
		try {
			return this.listLFiltersImpl();
		} catch (Throwable t) {
			AppHelper.showExceptionDialog(null, "Error wgeb loading filters list", t);
			return null;
		}
	}

	public LFilters[] listLFiltersImpl() throws PersistentException {
		if (this.filterList == null) {
			filterList = LFiltersFactory.listLFiltersByQuery(null, null);
			if (filterList == null || filterList.length == 0) {
				throw new PersistentException(
						"No registered filters found. Unable to work. Please, fill table 'LFilters'.");
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
