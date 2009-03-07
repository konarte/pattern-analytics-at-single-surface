package edu.mgupi.pass.filters;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private List<IFilter> filterList = new ArrayList<IFilter>();

	/**
	 * Normally, we do not need actually closing our chain. Cause these chains
	 * do not removing during all program execution (this is how I planned).
	 * 
	 * @see #reset()
	 */
	public void close() {
		this.reset();
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
		for (IFilter filter : filterList) {
			if (filter instanceof IInitiable) {
				((IInitiable) filter).close();
			}
		}
		filterList.clear();
	}

	/**
	 * Append new filter (instantiating as class) to chain in the end. This
	 * method must be common usable in interface.
	 * 
	 * @param filterClass
	 *            {@link IFilter} class for instantiate. Must be not null.
	 * @throws InstantiationException
	 *             standard exception for {@link Class#newInstance()} method.
	 * @throws IllegalAccessException
	 *             standard exception for {@link Class#newInstance()} method.
	 * 
	 * @see #appendFilter(IFilter)
	 */
	public void appendFilter(Class<? extends IFilter> filterClass) throws InstantiationException,
			IllegalAccessException {
		if (filterClass == null) {
			throw new IllegalArgumentException("Internal error. filterClass must be not null.");
		}

		logger.debug("Appending filter as class {}", filterClass);
		this.appendFilter(filterClass.newInstance());
	}

	/**
	 * Append new filter to chain (in the end). If filter instance implements
	 * {@link IInitiable} interface we call its method {@link IInitiable#init()}
	 * .
	 * 
	 * @param filter
	 *            instance of {@link IFilter} class. Must be not null.
	 * 
	 */
	public void appendFilter(IFilter filter) {

		if (filter == null) {
			throw new IllegalArgumentException("Internal error. filter must be not null.");
		}

		logger.debug("Appending filter as instance {}", filter);

		filterList.add(filter);

		if (filter instanceof IInitiable) {
			((IInitiable) filter).init();
		}

		if (this.sourceImage != null && filter instanceof IFilterAttachable) {
			((IFilterAttachable) filter).onAttachToImage(sourceImage);
		}
	}

	/**
	 * Remove filter from specified position. If filter implements
	 * {@link IInitiable} interface -- we call its method
	 * {@link IInitiable#close()}.
	 * 
	 * @param pos
	 *            we remove filter from this position.
	 * 
	 */
	public void removeFilter(int pos) {

		if (pos >= 0 && pos < filterList.size()) {
			IFilter filter = filterList.get(pos);
			logger.debug("Removing filter {}", filter);

			if (this.sourceImage != null && filter instanceof IFilterAttachable) {
				((IFilterAttachable) filter).onDetachFromImage(sourceImage);
			}
			if (filter instanceof IInitiable) {
				((IInitiable) filter).close();
			}
			filterList.remove(pos);
		} else {
			logger.debug("FilterChainsaw.appendFilter, but pos {} is not in range", pos);
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
	 */
	public void moveUp(int pos) {
		if (pos > 0 && pos < filterList.size()) {
			logger.trace("Moving index {} up", pos);

			IFilter old = filterList.get(pos - 1);
			filterList.set(pos - 1, filterList.get(pos));
			filterList.set(pos, old);
		} else {
			logger.trace("Can't move up, position {} is not in range", pos);
		}
	}

	/**
	 * Move filter down (up to the end of processing chain)
	 * 
	 * @param pos
	 *            position will be move down.
	 */
	public void moveDown(int pos) {
		if (pos >= 0 && pos < filterList.size() - 1) {
			logger.trace("Moving index {} down", pos);

			IFilter old = filterList.get(pos + 1);
			filterList.set(pos + 1, filterList.get(pos));
			filterList.set(pos, old);
		} else {
			logger.trace("Can't move down, position {} is not in range", pos);
		}
	}

	private BufferedImage sourceImage = null;

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

	private BufferedImage lastFilteredImage;

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
