package edu.mgupi.pass.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.modules.IModule;
import edu.mgupi.pass.util.CacheInitiable.MODE;

public class CacheIFactory {

	private static Lock fLFI = new ReentrantLock();
	private static CacheInitiable<IFilter> freeListFiltersInstance = null;

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

	protected void finalize() throws Throwable {
		close();
	}

}
