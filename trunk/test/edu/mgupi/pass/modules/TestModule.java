package edu.mgupi.pass.modules;

import java.awt.Image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.db.locuses.Locuses;

public class TestModule implements IModule {

	private final static Logger logger = LoggerFactory.getLogger(TestModule.class);

	private boolean init = false;

	public void init() {
		logger.debug("TestModule.init");
		if (init) {
			throw new IllegalStateException("Internal error. Init already called.");
		}
		init = true;
	}

	private boolean done = false;

	public void done() {
		if (!init) {
			throw new IllegalStateException("Internal error. Please, call init first.");
		}
		if (done) {
			throw new IllegalStateException("Internal error. Done already called.");
		}
		logger.debug("TestModule.done");
		done = true;
	}

	public void analyze(Image filteredImage, Locuses store) {
		if (!init) {
			throw new IllegalStateException("Internal error. Please, call init first.");
		}
		if (filteredImage == null) {
			throw new IllegalArgumentException("Internal error. filteredImage must be not null.");
		}
		if (store == null) {
			throw new IllegalArgumentException("Internal error. store must be not null.");
		}

	}

	public boolean compare(Locuses graph1, Locuses graph2) {
		if (!init) {
			throw new IllegalStateException("Internal error. Please, call init first.");
		}
		if (graph1 == null) {
			throw new IllegalArgumentException("Internal error. graph1 must be not null.");
		}
		if (graph2 == null) {
			throw new IllegalArgumentException("Internal error. graph2 must be not null.");
		}

		return false;
	}

}
