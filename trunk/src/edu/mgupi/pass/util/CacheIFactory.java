package edu.mgupi.pass.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.modules.IModule;
import edu.mgupi.pass.util.CacheInitiable.MODE;

/**
 * Main idea in using CacheInitiable -- using single instance for store same
 * data.
 * 
 * @author raidan
 * 
 */
public class CacheIFactory {

	private static Lock fLFI = new ReentrantLock();
	private static CacheInitiable<IFilter> freeListFiltersInstance = null;

	/**
	 * Return instance of free-list {@link CacheInitiable} for {@link IFilter} .
	 * 
	 * @return instance of cache
	 */
	public static CacheInitiable<IFilter> getFreeListFilters() {
		fLFI.tryLock();
		try {
			if (freeListFiltersInstance == null) {
				freeListFiltersInstance = new CacheInitiable<IFilter>(MODE.FREE_LISTS);
			}
			return freeListFiltersInstance;
		} finally {
			fLFI.unlock();
		}
	}

	private static Lock sFI = new ReentrantLock();
	private static CacheInitiable<IFilter> singleFilterInstance = null;

	/**
	 * Return instance of single-instance {@link CacheInitiable} for
	 * {@link IFilter} .
	 * 
	 * @return instance of cache
	 */
	public static CacheInitiable<IFilter> getSingleInstanceFilters() {
		sFI.tryLock();
		try {
			if (singleFilterInstance == null) {
				singleFilterInstance = new CacheInitiable<IFilter>(MODE.SINGLE_INSTANCE);
			}
			return singleFilterInstance;
		} finally {
			sFI.unlock();
		}
	}

	private static Lock sMI = new ReentrantLock();
	private static CacheInitiable<IModule> singleModuleInstance = null;

	/**
	 * Return instance of single-instance {@link CacheInitiable} for
	 * {@link IModule} .
	 * 
	 * @return instance of cache
	 */
	public static CacheInitiable<IModule> getSingleInstanceModules() {
		sMI.tryLock();
		try {
			if (singleModuleInstance == null) {
				singleModuleInstance = new CacheInitiable<IModule>(MODE.SINGLE_INSTANCE);
			}
			return singleModuleInstance;
		} finally {
			sMI.unlock();
		}
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
