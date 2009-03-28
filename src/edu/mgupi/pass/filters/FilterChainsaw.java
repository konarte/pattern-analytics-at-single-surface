package edu.mgupi.pass.filters;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.orm.PersistentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.face.gui.AppDataStorage;
import edu.mgupi.pass.util.CacheIFactory;
import edu.mgupi.pass.util.CacheInitiable;
import edu.mgupi.pass.util.IInitiable;

/**
 * Class for chained operation with image. This is simple chain, where all
 * filters applied on image in turn, one after another.
 * 
 * No parallel or independent filtering!
 * 
 * @author raidan
 * 
 */
public class FilterChainsaw {

	private final static Logger logger = LoggerFactory.getLogger(FilterChainsaw.class);

	protected List<IFilter> filterList = new ArrayList<IFilter>();

	private CacheInitiable<IFilter> cacheInstance = null;
	protected boolean singleInstanceCaching = false;
	//	protected boolean doNotCleanCacheInstance = false;

	private BufferedImage sourceImage = null;
	private BufferedImage lastFilteredImage;

	public FilterChainsaw() {
		this(false);
	}

	public FilterChainsaw(boolean singleInstanceCaching) {
		this.singleInstanceCaching = singleInstanceCaching;
		this.cacheInstance = singleInstanceCaching ? CacheIFactory.getSingleInstanceFilters() : CacheIFactory
				.getFreeListFilters();
	}

	/**
	 * Normally, we do not need actually closing our chain. Cause these chains
	 * do not removing during all program execution (this is how I planned).
	 * 
	 * @see #reset()
	 */
	public void close() {
		this.reset();
		this.cacheInstance = null;
		this.filterList = null;

		//		if (cacheInstance != null) {
		//			if (doNotCleanCacheInstance) {
		//				cacheInstance.close();
		//			}
		//			cacheInstance = null;
		//		}

	}

	/**
	 * Method for reset chain of filters. If filter implements
	 * {@link IInitiable} interface -- we call its method
	 * {@link IInitiable#close()}.
	 * 
	 */
	public void reset() {
		logger.debug("Reset all registered filters");

		this.detachImage();
		filterList.clear();

		this.sourceImage = null;
		this.lastFilteredImage = null;
	}

	/**
	 * Append new filter (instantiating as class) to chain in the end. This
	 * method must be common usable in interface.
	 * 
	 * @param filterClass
	 *            {@link IFilter} class for instantiate. Must be not null.
	 * @return created instance
	 * @throws InstantiationException
	 *             standard exception for {@link Class#newInstance()} method.
	 * @throws IllegalAccessException
	 *             standard exception for {@link Class#newInstance()} method.
	 * @throws FilterException
	 * @throws PersistentException
	 * 
	 */
	public IFilter appendFilter(Class<? extends IFilter> filterClass) throws InstantiationException,
			IllegalAccessException, FilterException, PersistentException {
		return this.appendFilter(this.filterList.size(), filterClass);
	}

	public IFilter appendFilter(int index, Class<? extends IFilter> filterClass) throws InstantiationException,
			IllegalAccessException, FilterException, PersistentException {
		if (filterClass == null) {
			throw new IllegalArgumentException("Internal error. filterClass must be not null.");
		}

		logger.debug("Appending filter as class {}", filterClass);

		IFilter instance = null;
		int actualIndex = index;
		if (singleInstanceCaching) {
			int idx = this.searchFilterClassPos(filterClass);
			if (idx != -1) {
				instance = filterList.get(idx);
				filterList.remove(idx);
				actualIndex = filterList.size();
			}
		}

		if (instance == null) {
			instance = this.cacheInstance.getInstance(filterClass);
			AppDataStorage.getInstance().checkUsingFilter(instance);
		}

		// If we using single caching -- we don't add additional instance to filter list
		filterList.add(actualIndex, instance);

		if (this.sourceImage != null && instance instanceof IFilterAttachable) {
			((IFilterAttachable) instance).onAttachToImage(sourceImage);
		}

		return instance;
	}

	//	/**
	//	 * Append new filter to chain (in the end). If filter instance implements
	//	 * {@link IInitiable} interface we call its method {@link IInitiable#init()}
	//	 * .
	//	 * 
	//	 * @param filter
	//	 *            instance of {@link IFilter} class. Must be not null.
	//	 * 
	//	 */
	//	public void appendFilter(IFilter filter) {
	//
	//		if (filter == null) {
	//			throw new IllegalArgumentException("Internal error. filter must be not null.");
	//		}
	//
	//		logger.debug("Appending filter as instance {}", filter);
	//
	//		filterList.add(filter);
	//
	//		if (filter instanceof IInitiable) {
	//			((IInitiable) filter).init();
	//		}
	//
	//		if (this.sourceImage != null && filter instanceof IFilterAttachable) {
	//			((IFilterAttachable) filter).onAttachToImage(sourceImage);
	//		}
	//	}

	/**
	 * Remove filter from specified position. If filter implements
	 * {@link IInitiable} interface -- we call its method
	 * {@link IInitiable#close()}.
	 * 
	 * @param pos
	 *            we remove filter from this position.
	 * @return true is removing is success, false if no such index
	 * 
	 */
	public boolean removeFilter(int pos) {

		if (pos >= 0 && pos < filterList.size()) {
			IFilter filter = filterList.get(pos);
			logger.debug("Removing filter {}", filter);

			if (this.sourceImage != null && filter instanceof IFilterAttachable) {
				((IFilterAttachable) filter).onDetachFromImage(sourceImage);
			}

			this.cacheInstance.putDeleted(filter);
			filterList.remove(pos);
			return true;
		} else {
			logger.debug("FilterChainsaw.appendFilter, but pos {} is not in range", pos);
			return false;
		}
	}

	public void removeAllFilters() {
		while (this.removeFilter(0) == true)
			;
	}

	public void removeFilter(Class<? extends IFilter> filterClass) {
		if (!this.singleInstanceCaching) {
			throw new IllegalStateException(
					"Removing filters by class allow only if constructor parameter 'singleInstanceCaching' is true.");
		}
		this.removeFilter(this.searchFilterClassPos(filterClass));
	}

	private int searchFilterClassPos(Class<? extends IFilter> filterClass) {
		if (!this.singleInstanceCaching) {
			throw new IllegalStateException(
					"Removing filters by class allow only if constructor parameter 'singleInstanceCaching' is true.");
		}
		for (int pos = 0; pos < filterList.size(); pos++) {
			IFilter filter = filterList.get(pos);
			if (filter.getClass().equals(filterClass)) {
				return pos;
			}
		}
		return -1;
	}

	public IFilter searchFilterClass(Class<? extends IFilter> filterClass) {
		int pos = this.searchFilterClassPos(filterClass);
		if (pos != -1) {
			return this.filterList.get(pos);
		} else {
			return null;
		}
	}

	/**
	 * Get filter from specified position
	 * 
	 * @param pos
	 *            we get filter from this position.
	 * 
	 * @return filter instance for specified position.
	 */
	public IFilter getFilter(int pos) {
		if (pos >= 0 && pos < filterList.size()) {

			logger.trace("Return filter at index {}", pos);

			return filterList.get(pos);
		} else {
			logger.trace("Requsted position {} is not in range", pos);

			return null;
		}
	}

	public int getFilterCount() {
		return this.filterList.size();
	}

	/**
	 * Return current registered filters. Actually, I don't want to care about
	 * modifying given collection.
	 * 
	 * @return current collection of filters.
	 */
	public Collection<IFilter> getFilters() {
		return this.filterList;
	}

	/**
	 * Move filter up (i.e. up to the begin of processing chain)
	 * 
	 * @param pos
	 *            position will be move up.
	 * @return true is moved, false if not
	 */
	public boolean moveUp(int pos) {
		if (pos > 0 && pos < filterList.size()) {
			logger.trace("Moving index {} up", pos);

			IFilter old = filterList.get(pos - 1);
			filterList.set(pos - 1, filterList.get(pos));
			filterList.set(pos, old);

			return true;
		} else {
			logger.trace("Can't move up, position {} is not in range", pos);
			return false;
		}
	}

	/**
	 * Move filter down (up to the end of processing chain)
	 * 
	 * @param pos
	 *            position will be move down.
	 * @return true is moved, false if not
	 */
	public boolean moveDown(int pos) {
		if (pos >= 0 && pos < filterList.size() - 1) {
			logger.trace("Moving index {} down", pos);

			IFilter old = filterList.get(pos + 1);
			filterList.set(pos + 1, filterList.get(pos));
			filterList.set(pos, old);

			return true;
		} else {
			logger.trace("Can't move down, position {} is not in range", pos);
			return false;
		}
	}

	/**
	 * Start processing new image.
	 * 
	 * @param image
	 *            instance of BufferedImage. Must be not null.
	 */
	public void attachImage(BufferedImage image) {
		if (this.sourceImage != null) {
			throw new IllegalStateException("Internal error. Please, detach previous image.");
		}

		logger.debug("Attaching image {}", image);

		this.sourceImage = image;
		for (IFilter filter : this.filterList) {
			if (filter instanceof IFilterAttachable) {
				((IFilterAttachable) filter).onAttachToImage(image);
			}
		}
	}

	/**
	 * End processing image. Main idea -- we can do many filtering operations
	 * for every image (i.e. we can change parameters of filters, append new
	 * filters, etc.) to ensure all OK. This is reason for separated operation
	 * -- {@link #attachImage(BufferedImage)} for attach :),
	 * {@link #filterSaw()} for filtering and detachImage when we done
	 * processing this image.
	 */
	public void detachImage() {
		if (sourceImage != null) {
			logger.debug("Deataching image {}", sourceImage);

			for (IFilter filter : this.filterList) {
				if (filter instanceof IFilterAttachable) {
					((IFilterAttachable) filter).onDetachFromImage(sourceImage);
				}
			}
			sourceImage = null;
		}
	}

	/**
	 * Actual processing of image by prepared filter chain.
	 * 
	 * @return filtered image we registered in
	 *         {@link #attachImage(BufferedImage)}.
	 * 
	 * @throws FilterException
	 *             if any filter throws this exception.
	 */
	public BufferedImage filterSaw() throws FilterException {

		if (sourceImage == null) {
			throw new IllegalStateException("Internal error. Please, call attachImage first.");
		}

		logger.debug("Actual filtering");

		lastFilteredImage = sourceImage;
		BufferedImage dest = null;
		for (IFilter filter : this.filterList) {
			dest = filter.convert(lastFilteredImage);
			lastFilteredImage = dest;
		}
		return lastFilteredImage;
	}

	/**
	 * Return last filtered image we proceed by our chain
	 * 
	 * @return last filtered image (or null if we never called
	 *         {@link #filterSaw()})
	 */
	public BufferedImage getLastFilteredImage() {
		return this.lastFilteredImage;
	}

	/**
	 * Represent registered chain of filters as string (their names).
	 */
	public String toString() {
		StringBuilder buffer = new StringBuilder();

		int size = this.filterList.size();

		if (size == 0) {

			buffer.append("None");

		} else {

			for (int i = 0; i < size; i++) {
				IFilter filter = this.filterList.get(i);
				buffer.append("[").append(filter.toString()).append("]");
				if (i < size - 1) {
					buffer.append(" - ");
				}
			}
		}
		return buffer.toString();
	}

}
