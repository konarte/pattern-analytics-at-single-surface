package edu.mgupi.pass.filters;

import java.awt.image.BufferedImage;

/**
 * Interface for filters. Allow to use onAttach and onDetach events.
 * 
 * @author raidan
 * 
 */
public interface IFilterAttachable {
	/**
	 * Method called just after loading image and processing by filters.
	 * 
	 * @param source
	 *            image we'll proceed.
	 */
	void onAttachToImage(BufferedImage source);

	/**
	 * Method called just after closing processing image.
	 * 
	 * @param source
	 */
	void onDetachFromImage(BufferedImage source);
}
