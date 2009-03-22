package edu.mgupi.pass.modules;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;

import edu.mgupi.pass.db.locuses.LocusAppliedModule;
import edu.mgupi.pass.db.locuses.Locuses;
import edu.mgupi.pass.filters.Param;

/**
 * Module of image analyzing and processing.
 * 
 * @author raidan
 * 
 */
public interface IModule {

	/**
	 * Return name of module.
	 * 
	 * @return human readable name.
	 */
	String getName();

	/**
	 * Return collection of parameters, using this module
	 * 
	 * @return collection of parameters of null
	 */
	Collection<Param> getParams();

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
	 *            neccessary data (histogram, using filters, thumb image etc.)
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
	 * You must use {@link Locuses#getModule()} and
	 * {@link LocusAppliedModule#getData()} for get stored parameters (you know
	 * their names, you saved these parameters in
	 * {@link #analyze(BufferedImage, Locuses)} method).
	 * 
	 * @param graph1
	 *            first locus to compare.
	 * @param graph2
	 *            second locus to compare.
	 * @return range from 0 to 1, where 0 -- locuses are not the same at any
	 *         kind of criteria, 1 -- there are the same (other numbers is a
	 *         measure of alike)
	 * @throws IOException
	 * 
	 * @throws ModuleException
	 *             if anything goes wrong.
	 */
	double compare(Locuses graph1, Locuses graph2) throws IOException, ModuleException;

}
