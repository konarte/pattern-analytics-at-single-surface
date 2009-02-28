package edu.mgupi.pass.modules;

import java.awt.image.BufferedImage;
import java.io.IOException;

import edu.mgupi.pass.db.locuses.Locuses;

/**
 * Module of image analyzing and processing.
 * 
 * @author raidan
 * 
 */
public interface IModule {

	/**
	 * First call of module. Only once after instantiating.
	 */
	void init();

	/**
	 * Return name of module.
	 * 
	 * @return human readable name.
	 */
	String getName();

	/**
	 * Call when we need to analyze image. This method must convert image to
	 * internal representation and parameters.
	 * 
	 * Module must return parameters.
	 * 
	 * This method always call locally.
	 * 
	 * @param filteredImage
	 * @param store
	 *            it is instance of {@link Locuses} class, filled with all
	 *            neccessary data (histogram, using filters, thumb image etc.).
	 *            You must use {@link Locuses#getParams()} for set (and store
	 *            into database) specified parameters.
	 * 
	 * @throws IOException
	 * @throws ModuleException
	 */
	void analyze(BufferedImage filteredImage, Locuses store) throws IOException, ModuleException;

	/**
	 * Method for comparing two different graphs. Method must return true if
	 * graphs alike and false if not. Be sure, you will receive objects
	 * processed by this module and all required parameters will be give!
	 * 
	 * This method can be called remotely, but you don't need to care about.
	 * 
	 * You must use {@link Locuses#getParams()} for get stored parameters (you
	 * know their names, you saved these parameters in
	 * {@link #analyze(BufferedImage, Locuses)} method).
	 * 
	 * @param graph1
	 *            first locus to compare.
	 * @param graph2
	 *            second locus to compare.
	 * @return true if these locuses are the same and false, if not. Criteria
	 *         for "the same or not" -- specific for every class.
	 * 
	 * @throws ModuleException
	 *             if anything goes wrong.
	 */
	boolean compare(Locuses graph1, Locuses graph2) throws ModuleException;

	/**
	 * Final call of method. Will call once on program exit.
	 */
	void done();
}
