package edu.mgupi.pass.sources;

import java.util.Collection;

/**
 * Common interface for source modules (that provides images for us)
 * 
 * @author raidan
 */

public interface ISource {
	/**
	 * Init method for Source interface. All method for preparing (caching) must
	 * be place here.
	 */
	void init();

	/**
	 * Return collection of source images to process. Can be multiple ^_^. Note,
	 * that we do not need any parameters to input (cause this is not our case).
	 * 
	 * @return collection of SourceStore objects
	 */
	Collection<SourceStore> getSources();

	/**
	 * Final method for close all prepared connections/hardware calls/etc.
	 */
	void done();
}
