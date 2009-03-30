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
 * Class for store data, using in application.
 * 
 * Main idea -- is checking to add only registered in database module/filter.
 * 
 * @author raidan
 * 
 */
public class AppDataStorage {

	private AppDataStorage() {
		// singleton
	}

	private static AppDataStorage instance;

	/**
	 * Get instance of storage.
	 * 
	 * @return {@link AppDataStorage}
	 */
	public static synchronized AppDataStorage getInstance() {
		if (instance == null) {
			instance = new AppDataStorage();
		}
		return instance;
	}

	/**
	 * Special reset method.
	 */
	protected static void reset() {
		if (instance != null) {
			instance.moduleList = null;
			instance.filterList = null;
			instance = null;
		}
	}

	private LModules[] moduleList = null;
	private LFilters[] filterList = null;

	/**
	 * Check using module for use in application. Method will ensure that this
	 * module is registered in database.
	 * 
	 * @param moduleClass
	 *            class of checking module
	 * @throws PersistentException
	 * @throws ModuleException
	 */
	public void checkUsingModule(Class<? extends IModule> moduleClass) throws PersistentException, ModuleException {
		if (this.searchModuleByClass(moduleClass) == null) {
			throw new ModuleNotFoundException("Module '" + moduleClass.getName()
					+ "' is not registered. Unable to use.");
		}
	}

	public void checkUsingFilter(Class<? extends IFilter> filterClass) throws PersistentException, FilterException {
		if (this.searchFilterByClass(filterClass) == null) {
			throw new FilterNotFoundException("Filter '" + filterClass.getName()
					+ "' is not registered. Unable to use.");
		}
	}

	public LModules getModuleByClass(Class<? extends IModule> moduleClass) throws PersistentException, ModuleException {
		LModules module = this.searchModuleByClass(moduleClass);
		if (module == null) {
			throw new ModuleNotFoundException("Unable to find registered module '" + moduleClass.getName() + "'.");
		}
		return module;
	}

	public LModules searchModuleByClass(Class<? extends IModule> moduleClass) throws PersistentException {

		if (moduleClass == null) {
			throw new IllegalArgumentException("Internal error. 'moduleClass' must be not null.");
		}

		String className = moduleClass.getName();
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
			AppHelper.showExceptionDialog(null, "Error when loading modules list", t);
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

	public LFilters searchFilterByClass(Class<? extends IFilter> filterClass) throws PersistentException {

		if (filterClass == null) {
			throw new IllegalArgumentException("Internal error. 'filterClass' must be not null.");
		}

		String className = filterClass.getName();
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
			AppHelper.showExceptionDialog(null, "Error when loading filters list", t);
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
