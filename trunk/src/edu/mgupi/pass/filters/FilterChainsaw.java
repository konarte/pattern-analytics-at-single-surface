package edu.mgupi.pass.filters;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for chained operation with filters. Main principle is
 * 
 * @author raidan
 * 
 */
public class FilterChainsaw {
	private final static Logger logger = LoggerFactory.getLogger(FilterChainsaw.class);

	private List<IFilter> filterList = new ArrayList<IFilter>();

	public void done() {
		logger.debug("FilterChainsaw.done");

		this.reset();
	}

	/**
	 * Method for reset chain of filters
	 */
	public void reset() {
		logger.debug("FilterChainsaw.reset");

		this.detachImage();
		for (IFilter filter : filterList) {
			if (filter instanceof IFilterInitiable) {
				((IFilterInitiable) filter).done();
			}
		}
		filterList.clear();
	}

	/**
	 * Append new filter (instantiating as class) to chain in the end
	 * 
	 * @param filterClass
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @see #appendFilter(IFilter)
	 */
	public void appendFilter(Class<? extends IFilter> filterClass) throws InstantiationException,
			IllegalAccessException {
		if (filterClass == null) {
			throw new IllegalArgumentException("Internal error. filterClass must be not null.");
		}

		logger.debug("FilterChainsaw.appendFilter now appends filter as class {}", filterClass);
		this.appendFilter(filterClass.newInstance());
	}

	/**
	 * Append new filter to chain (in the end)
	 * 
	 * @param filter
	 */
	public void appendFilter(IFilter filter) {

		if (filter == null) {
			throw new IllegalArgumentException("Internal error. filter must be not null.");
		}

		logger.debug("FilterChainsaw.appendFilter now appends filter {}", filter);

		filterList.add(filter);

		if (filter instanceof IFilterInitiable) {
			((IFilterInitiable) filter).init();
		}

		if (this.image != null) {
			filter.onAttachToImage(image);
		}
	}

	/**
	 * Remove filter from specified position
	 * 
	 * @param pos
	 */
	public void removeFilter(int pos) {

		if (pos >= 0 && pos < filterList.size()) {
			IFilter filter = filterList.get(pos);
			logger.debug("FilterChainsaw.appendFilter now remove filter {}", filter);

			if (this.image != null) {
				filter.onDetachFromImage(image);
			}
			if (filter instanceof IFilterInitiable) {
				((IFilterInitiable) filter).done();
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
	 * @return
	 */
	public IFilter getFilter(int pos) {
		if (pos >= 0 && pos < filterList.size()) {

			logger.trace("FilterChainsaw.getFilter now get filter index {}", pos);

			return filterList.get(pos);
		} else {
			logger.trace("FilterChainsaw.getFilter, but pos {} is not in range", pos);

			return null;
		}
	}

	public Collection<IFilter> getFilters() {
		return this.filterList;
	}

	/**
	 * Move filter up (i.e. to the begin of processing chain)
	 * 
	 * @param pos
	 */
	public void moveUp(int pos) {
		if (pos > 0 && pos < filterList.size()) {
			logger.trace("FilterChainsaw.moveUp now move up index {}", pos);

			IFilter old = filterList.get(pos - 1);
			filterList.set(pos - 1, filterList.get(pos));
			filterList.set(pos, old);
		} else {
			logger.trace("FilterChainsaw.moveUp, but pos {} is not in range", pos);
		}
	}

	/**
	 * Move filter down (to the end of processing chain)
	 * 
	 * @param pos
	 */
	public void moveDown(int pos) {
		if (pos >= 0 && pos < filterList.size() - 1) {
			logger.trace("FilterChainsaw.moveDown now move down index {}", pos);

			IFilter old = filterList.get(pos + 1);
			filterList.set(pos + 1, filterList.get(pos));
			filterList.set(pos, old);
		} else {
			logger.trace("FilterChainsaw.moveDown, but pos {} is not in range", pos);
		}
	}

	private BufferedImage image = null;

	/**
	 * Start processing new image
	 * 
	 * @param image
	 */
	public void attachImage(BufferedImage image) {
		if (this.image != null) {
			throw new IllegalStateException("Internal error. Please, detach previous image.");
		}

		logger.debug("FilterChainsaw.attachImage now attach image {}", image);

		this.image = image;
		for (IFilter filter : this.filterList) {
			filter.onAttachToImage(image);
		}
	}

	/**
	 * End processing image
	 */
	public void detachImage() {
		if (image != null) {
			logger.debug("FilterChainsaw.detachImage now detach image {}", image);

			for (IFilter filter : this.filterList) {
				filter.onDetachFromImage(image);
			}
			image = null;
		}
	}

	private BufferedImage lastImage;
	/**
	 * Actual processing of image by prepared filter chain
	 * 
	 * @return
	 * @throws FilterException
	 */
	public BufferedImage filterSaw() throws FilterException {

		if (image == null) {
			throw new IllegalStateException("Internal error. Please, call attachImage first.");
		}

		logger.debug("FilterChainsaw.attachImage now SAW launch");

		lastImage = image;
		BufferedImage dest = null;
		for (IFilter filter : this.filterList) {
			dest = filter.convert(lastImage);
			lastImage = dest;
		}
		return lastImage;
	}
	
	public BufferedImage getLastFilteredImage() {
		return this.lastImage;		
	}

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
