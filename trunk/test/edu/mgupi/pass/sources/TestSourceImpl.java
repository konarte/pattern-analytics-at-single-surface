package edu.mgupi.pass.sources;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSourceImpl implements ISource {

	private final static Logger logger = LoggerFactory.getLogger(TestSourceImpl.class);

	public void init() {
		logger.debug("Calling init method.");
	}

	public void done() {
		logger.debug("Calling done method.");
	}

	private String imagePath = "test/82675284.jpg";

	public SourceStore getSingleSource() throws IOException {
		logger.debug("Calling getSingleSource.");
		File file = new File(imagePath);
		return new SourceStore(file.getName(), ImageIO.read(file));
	}

	// private String imageDir = "test/multiple/*.jpg";
	//
	// @Override
	// public Collection<SourceStore> getSourcesList() throws IOException {
	// logger.debug("Calling getSourceList.");
	// Collection<SourceStore> sources = new ArrayList<SourceStore>();
	// for (File file : new File(imageDir).listFiles()) {
	// sources.add(new SourceStore(file.getName(), ImageIO.read(file)));
	// }
	// logger.debug("Found " + sources.size() + " images");
	// return sources;
	// }

}
