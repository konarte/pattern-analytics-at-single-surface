package edu.mgupi.pass.filters;

import java.awt.Image;
import java.util.Collection;
import java.util.Map;

/**
 * Interface for filters. At least we write out filter on high-speed language
 * like C/C++/Delphi -- we'll have this cover anyway. So that is the point we
 * have special class Param (to make easier work in interface).
 * 
 * @author raidan
 * 
 */
public interface IFilter {

	/**
	 * Return params for completing in this filter. Will be call only once, for
	 * first instantiation of class.
	 * 
	 * @return
	 */
	Collection<Param> getParams();

	/**
	 * Process images. This method will be call every time we need to filter
	 * image. Please, ensure that method as fast as it can ^_^
	 * 
	 * @param image
	 *            Image for processing with this filter
	 * @param params
	 *            Params (key is param name and value is entered value for this
	 *            parameter)
	 */
	void process(Image image, Map<String, Object> params);

	/**
	 * Final method for close all prepared connections/hardware calls/etc. Will
	 * be called only once on program exit.
	 */
	void done();
}
