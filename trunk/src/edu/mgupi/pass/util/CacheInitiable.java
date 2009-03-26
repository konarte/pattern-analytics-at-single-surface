package edu.mgupi.pass.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Special caching class for suppor Filters and Modules.
 * 
 * @author raidan
 * 
 * @param <E>
 * 
 */
public class CacheInitiable<E> {

	protected enum MODE {
		SINGLE_INSTANCE, FREE_LISTS
	};

	private MODE mode;

	protected CacheInitiable(MODE mode) {
		this.mode = mode;
	}

	private final static Logger logger = LoggerFactory.getLogger(CacheInitiable.class);

	private Map<Class<? extends E>, E> cachedValues = new HashMap<Class<? extends E>, E>();
	private Collection<E> givenInstances = new ArrayList<E>();
	private Map<Class<? extends E>, Set<E>> freeLists = new HashMap<Class<? extends E>, Set<E>>();

	private Lock lockFreeLists = new ReentrantLock();
	private Lock lockSingleInstance = new ReentrantLock();

	public E getInstance(Class<? extends E> moduleClass) throws InstantiationException, IllegalAccessException {
		if (mode == MODE.FREE_LISTS) {
			return this.getFreeImpl(moduleClass);
		} else {
			return this.getInstanceImpl(moduleClass);
		}
	}

	@SuppressWarnings("unchecked")
	public void putDeleted(E instance) {
		if (mode == MODE.FREE_LISTS) {
			lockFreeLists.lock();
			try {

				Set<E> values = freeLists.get(instance.getClass());
				if (values == null) {
					values = new LinkedHashSet<E>();
					freeLists.put((Class<? extends E>) instance.getClass(), values);
				}

				if (values.add(instance)) {
					logger.trace("ADD TO FREE LIST {}, {} now", instance.getClass(), values.size());
				}
			} finally {
				lockFreeLists.unlock();
			}
		}
	}

	private void closeInstance(E instance) {
		if (instance instanceof IInitiable) {
			((IInitiable) instance).close();
		}
	}

	protected void close() {
		logger.debug("CACHE DONE : " + this + " (" + this.mode + ")");
		logger.debug("Total cached single classes: " + cachedValues.size());
		for (E instance : cachedValues.values()) {
			this.closeInstance(instance);
		}
		cachedValues.clear();

		int totalFreeInstances = 0;

		logger.debug("Total given free-list instances: " + givenInstances.size());
		for (E instance : givenInstances) {
			this.closeInstance(instance);

			Set<E> values = freeLists.get(instance.getClass());
			if (values != null) {
				totalFreeInstances++;
				values.remove(instance);
			}
		}
		givenInstances.clear();

		logger.debug("Total given free-list classes: " + freeLists.size());
		for (Set<E> values : freeLists.values()) {
			for (E instance : values) {
				totalFreeInstances++;
				this.closeInstance(instance);
			}
		}

		freeLists.clear();
	}

	private E initInstance(Class<? extends E> moduleClass) throws InstantiationException, IllegalAccessException {
		E instance = moduleClass.newInstance();
		if (instance instanceof IInitiable) {
			((IInitiable) instance).init();
		}
		return instance;
	}

	private E getInstanceImpl(Class<? extends E> moduleClass) throws InstantiationException, IllegalAccessException {

		lockSingleInstance.lock();

		try {

			E instance = cachedValues.get(moduleClass);

			if (instance == null) {
				logger.trace("NEW INSTANCE {}", moduleClass);
				instance = this.initInstance(moduleClass);
				cachedValues.put(moduleClass, instance);

			} else {
				logger.trace("FROM CACHE {}", moduleClass);
			}
			return instance;
		} finally {
			lockSingleInstance.unlock();
		}
	}

	private E getFreeImpl(Class<? extends E> moduleClass) throws InstantiationException, IllegalAccessException {

		lockFreeLists.lock();

		try {

			Set<E> values = freeLists.get(moduleClass);
			if (values != null && values.size() > 0) {
				E value = values.iterator().next();
				values.remove(value);

				logger.trace("FROM FREE LIST {}, {} left", moduleClass, values.size());
				return value;
			}
			E instance = this.initInstance(moduleClass);
			givenInstances.add(instance);

			logger.trace("ADD TO GIVEN LIST {}, {} total", moduleClass, givenInstances.size());

			return instance;
		} finally {
			lockFreeLists.unlock();
		}
	}

}
