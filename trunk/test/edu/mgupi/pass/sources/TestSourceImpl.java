package edu.mgupi.pass.sources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSourceImpl implements ISource {

	private final static Logger logger = LoggerFactory.getLogger(TestSourceImpl.class);

	private boolean init = false;

	protected void finalize() throws Throwable {
		if (!done) {
			logger.error("Close not called. Terminating application immediately.");
			System.exit(1);
		}
	}

	public void init() {
		if (init) {
			throw new IllegalStateException("Internal error. Init already called.");
		}
		init = true;
		logger.debug("Source initiated");
	}

	private boolean done = false;

	public void close() {
		if (!init) {
			throw new IllegalStateException("Internal error. Please, call init first.");
		}
		if (done) {
			throw new IllegalStateException("Internal error. Done already called.");
		}
		done = true;
		logger.debug("Source closed");
	}

	private String imagePath = "test/suslik_list.jpg";
	//private String imagePath = "test/suslik.jpg";

	public SourceStore getSingleSource() throws IOException {
		if (!init) {
			throw new IllegalStateException("Internal error. Please, call init first.");
		}
		logger.debug("Return new source from {}", imagePath);
		
		File file = new File(imagePath);

		FileInputStream input = new FileInputStream(file);
		byte buffer[] = new byte[(int) input.getChannel().size()];
		try {
			input.read(buffer);
		} finally {
			input.close();
		}

		return new SourceStore(file.getName(), ImageIO.read(file), buffer);
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
