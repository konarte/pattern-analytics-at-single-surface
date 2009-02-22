package edu.mgupi.pass.modules;

import java.awt.Image;

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
	 * Call when we need to analyze image. This method must convert image to
	 * internal representation and parameters.
	 * 
	 * Module must return parameters.
	 * 
	 * This method always call locally.
	 * 
	 * @param filteredImage
	 * @param store
	 */
	void analyze(Image filteredImage, Locuses store);

	/**
	 * Method for comparing two different graphs. Method must return true if
	 * graphs alike and false if not. Be sure, you will receive objects
	 * processed by this module and all required parameters will be give!
	 * 
	 * This method can be called remotely, but you don't need to care about.
	 * 
	 * @param graph1
	 * @param graph2
	 * @return
	 */
	boolean compare(Locuses graph1, Locuses graph2);

	/**
	 * Final call of method. Will call once on program exit.
	 */
	void done();
}
