package edu.mgupi.pass.filters;

import java.awt.image.BufferedImage;
import java.util.Collection;

/**
 * Interface for filters. At least we write out filter on high-speed language
 * like C/C++/Delphi -- we'll have this cover anyway. So that is the point we
 * have special class {@link Param} (to make easier work in interface).
 * 
 * @author raidan
 * 
 */
public interface IFilter {

	/**
	 * Return name of filter.
	 * 
	 * @return human readable name.
	 */
	String getName();

	/**
	 * Return parameters for completing in this filter. Will be call only once,
	 * for first instantiation of class.
	 * 
	 * @return collection of {@link Param} instance.
	 */
	Collection<Param> getParams();

	/**
	 * Process images. This method will be call every time we need to filter
	 * image. Please, ensure that method as fast as it can ^_^
	 * 
	 * @param source
	 *            Image for processing with this filter.
	 * 
	 * @return filtered image (not the same instance!)
	 * 
	 * @throws FilterException
	 *             when anything goes wrong.
	 */
	BufferedImage convert(BufferedImage source) throws FilterException;

}
