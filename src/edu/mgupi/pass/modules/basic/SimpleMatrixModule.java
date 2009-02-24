package edu.mgupi.pass.modules.basic;

import java.awt.Image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.db.locuses.Locuses;
import edu.mgupi.pass.modules.IModule;

public class SimpleMatrixModule implements IModule {

	private final static Logger logger = LoggerFactory.getLogger(SimpleMatrixModule.class);

	public void init() {
		logger.debug("SimpleMatrixModule.init");
	}

	public void done() {
		logger.debug("SimpleMatrixModule.done");
	}

	public void analyze(Image filteredImage, Locuses store) {
		// TODO Auto-generated method stub

	}

	public boolean compare(Locuses graph1, Locuses graph2) {
		// TODO Auto-generated method stub
		return false;
	}

}
