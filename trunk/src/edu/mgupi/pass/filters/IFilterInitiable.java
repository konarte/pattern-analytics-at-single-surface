package edu.mgupi.pass.filters;

/**
 * Interface for filters. Allow to create init and done methods.
 * 
 * @author raidan
 * 
 */
public interface IFilterInitiable {

	/**
	 * Method will be launched just after instantiating class (before calling
	 * {@link IFilter#getParams()}.
	 */
	public void init();

	/**
	 * Method will be launched after processing (when we destroy filter instance
	 * in {@link FilterChainsaw}).
	 */
	public void done();
}
