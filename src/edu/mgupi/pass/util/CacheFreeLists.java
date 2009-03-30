/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)CacheFreeLists.java 1.0 29.03.2009
 */

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

import edu.mgupi.pass.filters.FilterChainsaw;

/**
 * Implementation of {@link AbstractCacheInitiable}. <br>
 * 
 * Keeps cached instances of classes, store when instance does not required no
 * more and give it to next requester. Used by all other {@link FilterChainsaw}
 * instances.
 * 
 * @author raidan
 * 
 * @param <E>
 */
public class CacheFreeLists<E> extends AbstractCacheInitiable<E> {

	private final static Logger logger = LoggerFactory.getLogger(CacheFreeLists.class);

	private Lock lock = new ReentrantLock();
	private Collection<E> givenInstances = new ArrayList<E>();
	private Map<Class<? extends E>, Set<E>> freeLists = new HashMap<Class<? extends E>, Set<E>>();

	@Override
	protected void close() {
		logger.debug("----- CACHE DONE : {}.", this);

		int totalFreeInstances = 0;

		logger.debug("Total given free-list instances: {}.", givenInstances.size());
		for (E instance : givenInstances) {
			this.closeInstance(instance);

			Set<E> values = freeLists.get(instance.getClass());
			if (values != null) {
				totalFreeInstances++;
				values.remove(instance);
			}
		}
		givenInstances.clear();

		logger.debug("Total given free-list classes: {}.", freeLists.size());
		for (Set<E> values : freeLists.values()) {
			for (E instance : values) {
				totalFreeInstances++;
				this.closeInstance(instance);
			}
		}

		freeLists.clear();

	}

	@Override
	public E getInstance(Class<? extends E> moduleClass) throws InstantiationException,
			IllegalAccessException {
		lock.lock();

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
			lock.unlock();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void releaseInstance(E instance) {
		lock.lock();
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
			lock.unlock();
		}

	}

}
