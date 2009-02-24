package edu.mgupi.pass.sources;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSourceImpl implements ISource {

	private final static Logger logger = LoggerFactory.getLogger(TestSourceImpl.class);

	private boolean init = false;

	protected void finalize() throws Throwable {
		if (!done) {
			System.err.println("Finalize not called");
			System.exit(1);
		}
	}

	public void init() {
		if (init) {
			throw new IllegalStateException("Internal error. Init already called.");
		}
		logger.debug("TestSourceImpl. Calling init method.");
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
		logger.debug("TestSourceImpl. Calling done method.");
		done = true;
	}

	private String imagePath = "test/82675284.jpg";

	public SourceStore getSingleSource() throws IOException {
		if (!init) {
			throw new IllegalStateException("Internal error. Please, call init first.");
		}
		logger.debug("TestSourceImpl. Calling getSingleSource.");
		File file = new File(imagePath);
		return new SourceStore(file.getName(), ImageIO.read(file));
	}

	// private String imageDir = "test/multiple/*.jpg";
	//
	// @Override
	// public Collection<SourceStore> getSourcesList() throws IOException {
	// logger.debug("TestSourceImpl. Calling getSourceList.");
	// Collection<SourceStore> sources = new ArrayList<SourceStore>();
	// for (File file : new File(imageDir).listFiles()) {
	// sources.add(new SourceStore(file.getName(), ImageIO.read(file)));
	// }
	// logger.debug("Found " + sources.size() + " images");
	// return sources;
	// }

}
