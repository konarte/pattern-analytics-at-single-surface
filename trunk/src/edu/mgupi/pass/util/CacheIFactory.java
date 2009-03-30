package edu.mgupi.pass.util;

import edu.mgupi.pass.filters.FilterChainsaw;
import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.modules.IModule;
import edu.mgupi.pass.modules.ModuleProcessor;

/**
 * Main idea in using CacheInitiable -- using single instance for store same
 * data. <br>
 * 
 * Contains 3 types of caches:
 * <ul>
 * <li>{@link #getFreeListFilters()} -- free lists for filters.
 * 
 * <li>{@link #getSingleInstanceFilters()} -- single instance list for filters,
 * used by pre-processing chainsaw in {@link ModuleProcessor}.
 * 
 * <li>{@link #getSingleInstanceModules()} -- single instance list for modules,
 * used by {@link ModuleProcessor}.
 * </ul>
 * 
 * @author raidan
 * 
 * @see AbstractCacheInitiable
 */
public class CacheIFactory {

	private static AbstractCacheInitiable<IFilter> freeListFiltersInstance = null;
	private static AbstractCacheInitiable<IFilter> singleFilterInstance = null;
	private static AbstractCacheInitiable<IModule> singleModuleInstance = null;

	/**
	 * Return instance of free-list {@link AbstractCacheInitiable} for
	 * {@link IFilter}, used by {@link FilterChainsaw}.
	 * 
	 * @return instance of cache
	 */
	public static synchronized AbstractCacheInitiable<IFilter> getFreeListFilters() {
		if (freeListFiltersInstance == null) {
			freeListFiltersInstance = new CacheFreeLists<IFilter>();
		}
		return freeListFiltersInstance;
	}

	/**
	 * Return instance of single-instance {@link AbstractCacheInitiable} for
	 * {@link IFilter}, used by {@link ModuleProcessor#getPreChainsaw()}.
	 * 
	 * @return instance of cache
	 */
	public static synchronized AbstractCacheInitiable<IFilter> getSingleInstanceFilters() {
		if (singleFilterInstance == null) {
			singleFilterInstance = new CacheSingleInstance<IFilter>();
		}
		return singleFilterInstance;
	}

	/**
	 * Return instance of single-instance {@link AbstractCacheInitiable} for
	 * {@link IModule}.
	 * 
	 * @return instance of cache
	 */
	public static synchronized AbstractCacheInitiable<IModule> getSingleInstanceModules() {
		if (singleModuleInstance == null) {
			singleModuleInstance = new CacheSingleInstance<IModule>();
		}
		return singleModuleInstance;
	}

	/**
	 * Special close and clear method for cache. All registered caches are
	 * cleaning and done!
	 */
	public static void close() {
		if (freeListFiltersInstance != null) {
			freeListFiltersInstance.close();
			freeListFiltersInstance = null;
		}
		if (singleFilterInstance != null) {
			singleFilterInstance.close();
		}
		if (singleModuleInstance != null) {
			singleModuleInstance.close();
		}
	}

}
