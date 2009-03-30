package edu.mgupi.pass.face.gui;

import java.util.Arrays;
import java.util.Comparator;

import org.orm.PersistentException;

import edu.mgupi.pass.db.locuses.LFilters;
import edu.mgupi.pass.db.locuses.LFiltersCriteria;
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
	protected void reset() {
		if (instance != null) {
			instance.modulesList = null;
			instance.filtersList = null;
			instance.serviceFiltersList = null;
			instance = null;
		}
	}

	private LModules[] modulesList = null;
	private LFilters[] filtersList = null;
	private LFilters[] serviceFiltersList = null;

	/**
	 * Check using module for use in application. Method will ensure that this
	 * module is registered in database.
	 * 
	 * @param moduleClass
	 *            class of checking module
	 * @throws PersistentException
	 * @throws ModuleException
	 */
	public void checkUsingModule(Class<? extends IModule> moduleClass) throws PersistentException,
			ModuleException {
		this.getModuleByClass(moduleClass);
	}

	public void checkUsingFilter(Class<? extends IFilter> filterClass) throws PersistentException,
			FilterException {
		this.getFilterByClass(filterClass);
	}

	public LModules getModuleByClass(Class<? extends IModule> moduleClass)
			throws PersistentException, ModuleException {
		LModules module = this.searchModuleByClass(moduleClass);
		if (module == null) {
			throw new ModuleNotFoundException(Messages
					.getString("AppDataStorage.err.moduleNotFound", moduleClass.getName()));
		}
		return module;
	}

	public LModules searchModuleByClass(Class<? extends IModule> moduleClass)
			throws PersistentException {

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
			AppHelper.showExceptionDialog(null, Messages
					.getString("AppDataStorage.err.loadingModuleList"), t);
			return null;
		}
	}

	public LModules[] listLModulesImpl() throws PersistentException {
		if (this.modulesList == null) {
			modulesList = LModulesFactory.listLModulesByQuery(null, null);
			if (modulesList == null || modulesList.length == 0) {
				throw new PersistentException(Messages
						.getString("AppDataStorage.err.noModulesFound"));
			}
			Arrays.sort(modulesList, new Comparator<LModules>() {
				@Override
				public int compare(LModules o1, LModules o2) {
					if (o1 != null && o2 != null) {
						return o1.getName().compareTo(o2.getName());
					}
					return 0;
				}
			});

		}
		return this.modulesList;
	}

	public LFilters searchFilterByClass(Class<? extends IFilter> filterClass)
			throws PersistentException {

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

	public LFilters getFilterByClass(Class<? extends IFilter> filterClass)
			throws PersistentException, FilterException {
		LFilters filter = this.searchFilterByClass(filterClass);
		if (filter == null) {
			throw new FilterNotFoundException(Messages
					.getString("AppDataStorage.err.filterNotFound", filterClass.getName()));
		}
		return filter;
	}

	public LFilters getServiceFilterByClass(Class<? extends IFilter> filterClass)
			throws PersistentException, FilterException {
		LFilters filter = this.searchFilterByClass(filterClass);
		if (filter == null) {
			String className = filterClass.getName();
			for (LFilters module : this.listServiceFilters()) {
				if (module.getCodename().equals(className)) {
					return module;
				}
			}
		}
		if (filter == null) {
			throw new FilterNotFoundException(Messages
					.getString("AppDataStorage.err.serviceFilterNotFound", filterClass.getName()));
		}
		return filter;
	}

	public LFilters[] listLFilters() {
		try {
			return this.listLFiltersImpl();
		} catch (Throwable t) {
			AppHelper.showExceptionDialog(null, Messages
					.getString("AppDataStorage.err.loadingFilterList"), t);
			return null;
		}
	}

	public LFilters[] listLFiltersImpl() throws PersistentException {
		if (this.filtersList == null) {
			LFiltersCriteria criteria = new LFiltersCriteria();
			criteria.serviceFilter.eq(false);

			filtersList = LFiltersFactory.listLFiltersByCriteria(criteria);
			if (filtersList == null || filtersList.length == 0) {
				throw new PersistentException(Messages
						.getString("AppDataStorage.err.noFiltersFound"));
			}

			Arrays.sort(filtersList, new Comparator<LFilters>() {
				@Override
				public int compare(LFilters o1, LFilters o2) {
					if (o1 != null && o2 != null) {
						return o1.getName().compareTo(o2.getName());
					}
					return 0;
				}
			});
		}
		return this.filtersList;

	}

	private LFilters[] listServiceFilters() throws PersistentException {
		if (this.serviceFiltersList == null) {
			LFiltersCriteria criteria = new LFiltersCriteria();
			criteria.serviceFilter.eq(true);

			serviceFiltersList = LFiltersFactory.listLFiltersByCriteria(criteria);
		}
		return serviceFiltersList;
	}

}
