package edu.mgupi.pass.modules;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.db.locuses.LocusModuleParams;
import edu.mgupi.pass.db.locuses.LocusModuleParamsFactory;
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

	public void analyze(BufferedImage filteredImage, Locuses store) throws IOException {
		if (!init) {
			throw new IllegalStateException("Internal error. Please, call init first.");
		}
		if (filteredImage == null) {
			throw new IllegalArgumentException("Internal error. filteredImage must be not null.");
		}
		if (store == null) {
			throw new IllegalArgumentException("Internal error. store must be not null.");
		}

		String imageParams = "" + filteredImage.getWidth() + "x" + filteredImage.getHeight() + " : "
				+ filteredImage.getType();

		LocusModuleParams param = LocusModuleParamsFactory.createLocusModuleParams();
		param.setParamName("myParam1");
		param.setParamData(imageParams.getBytes());
		store.getParams().add(param);

		param = LocusModuleParamsFactory.createLocusModuleParams();
		param.setParamName("myParam2");

		byte[] imageData = (byte[]) filteredImage.getData().getDataElements(0, 0, filteredImage.getWidth(),
				filteredImage.getHeight(), null);

		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		GZIPOutputStream zipOut = new GZIPOutputStream(byteStream);
		zipOut.write(imageData);

		param.setParamData(byteStream.toByteArray());
		store.getParams().add(param);

		zipOut.close();
		byteStream.close();
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

		java.util.Set<LocusModuleParams> params = graph1.getParams();
		for (LocusModuleParams param : params) {
			System.out.println("G1: " + param.getParamName() + " = " + param.getParamData());
		}

		params = graph2.getParams();
		for (LocusModuleParams param : params) {
			System.out.println("G2: " + param.getParamName() + " = " + param.getParamData());
		}

		return false;
	}

}
