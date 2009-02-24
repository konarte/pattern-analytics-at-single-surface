package edu.mgupi.pass.filters;

import java.awt.image.BufferedImage;
import java.util.Collection;

/**
 * Interface for filters. At least we write out filter on high-speed language
 * like C/C++/Delphi -- we'll have this cover anyway. So that is the point we
 * have special class Param (to make easier work in interface).
 * 
 * @author raidan
 * 
 */
public interface IFilter {

	String getName();

	/**
	 * Return params for completing in this filter. Will be call only once, for
	 * first instantiation of class.
	 * 
	 * Every new image will be attached to this filter
	 * 
	 * @return
	 */
	Collection<Param> getParams();

	/**
	 * Method called just after loading image and processing by filters
	 * 
	 * @param source
	 */
	void onAttachToImage(BufferedImage source);

	/**
	 * Method called just after closing processing image
	 * 
	 * @param source
	 */
	void onDetachFromImage(BufferedImage source);

	/**
	 * Process images. This method will be call every time we need to filter
	 * image. Please, ensure that method as fast as it can ^_^
	 * 
	 * @param image
	 *            Image for processing with this filter
	 * @param params
	 *            Params (key is param name and value is entered value for this
	 *            parameter)
	 * 
	 */
	BufferedImage convert(BufferedImage source) throws ParamException;

}
