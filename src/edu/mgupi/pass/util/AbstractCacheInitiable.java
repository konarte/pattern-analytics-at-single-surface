package edu.mgupi.pass.util;

/**
 * Special caching class for support Filters and Modules. <br>
 * 
 * You must operate with this cache in two methods:
 * <ul>
 * <li> {@link #getInstance(Class)} return instance of class (new, created, or
 * from free-lists, depends on implementation).
 * 
 * <li> {@link #releaseInstance(Object)} release instance of used classes and
 * move it to free lists or not, depends on implementation.
 * </ul>
 * 
 * @author raidan
 * 
 * @param <E>
 *            any class, but if this class implements {@link IInitiable}, that
 *            we will be call {@link IInitiable#init()} and
 *            {@link IInitiable#close()} methods when create instance or close
 *            it (if this is not be forgotten in implementations ;). <br>
 *            Be carefully, you do not need call {@link IInitiable#close()}
 *            method by yourself anyway.
 * 
 */
public abstract class AbstractCacheInitiable<E> {

	/**
	 * Create new instance of cache.
	 * 
	 */
	protected AbstractCacheInitiable() {
		//
	}

	/**
	 * Return instance of specified class. Depends on implementation, we do
	 * different job.
	 * 
	 * @param moduleClass
	 *            class definition
	 * @return instance, depends of current mode
	 * @throws InstantiationException
	 *             standard exception of {@link Class#newInstance()} method.
	 * @throws IllegalAccessException
	 *             standard exception of {@link Class#newInstance()} method.
	 */
	public abstract E getInstance(Class<? extends E> moduleClass) throws InstantiationException, IllegalAccessException;

	/**
	 * Mark cached instance as deleted (it keeps in special 'free-lists', given
	 * next requester with same class). <br>
	 * 
	 * Work only if current mode = FREE_LISTS. If not, do nothing.
	 * 
	 * @param instance
	 *            you do need more
	 */
	public abstract void releaseInstance(E instance);

	/**
	 * Special method for closing cache. All classes with {@link IInitiable}
	 * interface will be closed by {@link IInitiable#close()} method.
	 */
	protected abstract void close();

	/**
	 * Closing instance, call {@link IInitiable#close()} if this instance
	 * implements it.
	 */
	protected void closeInstance(E instance) {
		if (instance instanceof IInitiable) {
			((IInitiable) instance).close();
		}
	}

	/**
	 * Initiating instance, call {@link IInitiable#init()} if this instance
	 * implements it.
	 * 
	 * @param moduleClass
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	protected E initInstance(Class<? extends E> moduleClass) throws InstantiationException, IllegalAccessException {
		E instance = moduleClass.newInstance();
		if (instance instanceof IInitiable) {
			((IInitiable) instance).init();
		}
		return instance;
	}

}
