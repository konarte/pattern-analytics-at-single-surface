package edu.mgupi.pass.modules.basic;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.db.locuses.Locuses;
import edu.mgupi.pass.modules.IModule;

public class SimpleMatrixModule implements IModule {

	private final static Logger logger = LoggerFactory.getLogger(SimpleMatrixModule.class);

	public void init() {
		logger.debug("SMM initiated");
	}

	public void close() {
		logger.debug("SMM closed");
	}

	public void analyze(BufferedImage filteredImage, Locuses store) throws IOException {
		// TODO Auto-generated method stub
	}

	public float compare(Locuses graph1, Locuses graph2) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getName() {
		return "Анализ матриц";
	}

}
