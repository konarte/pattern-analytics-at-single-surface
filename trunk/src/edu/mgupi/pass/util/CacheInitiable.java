package edu.mgupi.pass.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheInitiable<E> {

	private final static Logger logger = LoggerFactory.getLogger(CacheInitiable.class);

	private Map<Class<? extends E>, E> cachedValues = new HashMap<Class<? extends E>, E>();

	public E getInstance(Class<? extends E> moduleClass) throws InstantiationException, IllegalAccessException {
		E instance = cachedValues.get(moduleClass);
		if (instance == null) {
			logger.debug("Creating new instance");
			instance = moduleClass.newInstance();
			if (instance instanceof IInitiable) {
				((IInitiable) instance).init();
			}
			cachedValues.put(moduleClass, instance);
		} else {
			logger.debug("Using cached instance");
		}
		return instance;
	}
// Обязательно реализовать кэширование!
// Пригодится для FilterChainsaw
// Основная идея -- запоминание "удаленного" фильтра и его повторное использование
//  для этого мы имеем списки экземпляров свободных фильтров :)
//
//	private Map<Class<? extends E>, List<E>> instances = new HashMap<Class<? extends E>, List<E>>();
//
//	public E getFirstFree(Class<? extends E> moduleClass) throws InstantiationException, IllegalAccessException {
//		List<E> values = instances.get(moduleClass);
//		if (values != null && values.size() > 0) {
//			E value = values.get(values.size() - 1);
//			values.remove(values.size() - 1);
//			return value;
//		}
//		return null;
//	}
//
//	@SuppressWarnings("unchecked")
//	public void putInstance(E instance) {
//		List<E> values = instances.get(instance.getClass());
//		if (values == null) {
//			values = new ArrayList<E>();
//			instances.put((Class<? extends E>) instance.getClass(), values);
//		}
//		values.add(instance);
//	}

	public void close() {
		for (E instance : cachedValues.values()) {
			if (instance instanceof IInitiable) {
				((IInitiable) instance).close();
			}
		}
		cachedValues.clear();
		
//		for (Collection<E> values : instances.values()) {
//			for (E instance : values) {
//				if (instance instanceof IInitiable) {
//					((IInitiable) instance).close();
//				}
//			}
//		}

//		 instances.clear();
	}

}
