/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)CacheSingleInstance.java 1.0 29.03.2009
 */

package edu.mgupi.pass.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.modules.ModuleProcessor;

/**
 * 
 * Implementation of {@link AbstractCacheInitiable}. <br>
 * 
 * Used when we require only one instance per cache -- that used only once! This
 * is how works pre-processing {@link ModuleProcessor#getPreChainsaw()}.
 * 
 * @author raidan
 * 
 * @param <E>
 */

public class CacheSingleInstance<E> extends AbstractCacheInitiable<E> {

	private final static Logger logger = LoggerFactory.getLogger(CacheSingleInstance.class);

	private Lock lock = new ReentrantLock();
	private Map<Class<? extends E>, E> cachedValues = new HashMap<Class<? extends E>, E>();

	@Override
	protected void close() {

		logger.debug("----- CACHE SINGLE INSTANCE DONE : {}.", this);

		logger.debug("Total cached single classes: {}", cachedValues.size());

		for (E instance : cachedValues.values()) {
			super.closeInstance(instance);
		}
		cachedValues.clear();

	}

	@Override
	public E getInstance(Class<? extends E> moduleClass) throws InstantiationException,
			IllegalAccessException {
		lock.lock();

		try {
			E instance = cachedValues.get(moduleClass);

			if (instance == null) {
				logger.trace("NEW INSTANCE {}", moduleClass);

				instance = super.initInstance(moduleClass);
				cachedValues.put(moduleClass, instance);

			} else {
				logger.trace("FROM CACHE {}", moduleClass);
			}
			return instance;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void releaseInstance(E instance) {
		/*
		 * Do nothing.
		 * 
		 * This type of cache does not provide any work on releasing.
		 */
	}

}
