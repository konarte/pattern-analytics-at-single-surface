package edu.mgupi.pass.modules;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.db.locuses.LocusModuleParams;
import edu.mgupi.pass.db.locuses.LocusModuleParamsFactory;
import edu.mgupi.pass.db.locuses.Locuses;
import edu.mgupi.pass.util.IInitiable;

/**
 * Test for module interface.
 * 
 * @author raidan
 * 
 */
public class TestModule implements IModule, IInitiable {

	protected void finalize() throws Throwable {
		logger.debug("Checking properly finalyzed method");
		if (!close) {
			throw new RuntimeException("Method close not called!");
		}

	}

	private final static Logger logger = LoggerFactory.getLogger(TestModule.class);

	private boolean init = false;

	public void init() {
		if (init) {
			throw new IllegalStateException("Internal error. Init already called.");
		}
		init = true;
		logger.debug("Module initiated");
	}

	private boolean close = false;

	public void close() {
		if (!init) {
			throw new IllegalStateException("Internal error. Please, call init first.");
		}
		if (close) {
			throw new IllegalStateException("Internal error. Done already called.");
		}
		close = true;
		logger.debug("Module closed");
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
		if (store.getProcessed()) {
			throw new IllegalArgumentException(
					"Internal error. Store was already processed! Attemt to reuse store object!");
		}

		String imageParams = "" + filteredImage.getWidth() + "x" + filteredImage.getHeight() + " : "
				+ filteredImage.getType();

		LocusModuleParams param = LocusModuleParamsFactory.createLocusModuleParams();
		param.setParamName("myParam1");
		param.setParamData(imageParams.getBytes());
		store.getParams().add(param);

		param = LocusModuleParamsFactory.createLocusModuleParams();
		param.setParamName("myParam2");
		param.setParamData(ModuleHelper.convertImageToPNGRaw(filteredImage));
		store.getParams().add(param);

		// ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

		// ImageIO.write(filteredImage, "PNG", byteStream);
		// param.setParamData(byteStream.toByteArray());

		// byteStream.close();

		// ImageWriter writer =
		// ImageIO.getImageWritersByFormatName("JPG").next();
		// writer.setOutput(ImageIO.createImageOutputStream(byteStream));
		//
		// ImageWriteParam iwp = writer.getDefaultWriteParam();
		// iwp.setProgressiveMode(ImageWriteParam.MODE_DEFAULT );
		// iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		// iwp.setCompressionQuality(0.95f);
		//
		// writer.write(null, new IIOImage(filteredImage, null, null), iwp);
		// writer.dispose();

		// byte[] imageData = (byte[])
		// filteredImage.getData().getDataElements(0, 0,
		// filteredImage.getWidth(),
		// filteredImage.getHeight(), null);
		// GZIPOutputStream zipOut = new GZIPOutputStream(byteStream);
		// zipOut.write(imageData);
		// zipOut.close();

		store.setProcessed(true);
	}

	public boolean compare(Locuses graph1, Locuses graph2) throws ModuleException {
		if (!init) {
			throw new IllegalStateException("Internal error. Please, call init first.");
		}
		if (graph1 == null) {
			throw new IllegalArgumentException("Internal error. graph1 must be not null.");
		}
		if (graph2 == null) {
			throw new IllegalArgumentException("Internal error. graph2 must be not null.");
		}
		if (!graph1.getProcessed()) {
			throw new IllegalArgumentException("Internal error. graph1 was not marked as processed!");
		}
		if (!graph2.getProcessed()) {
			throw new IllegalArgumentException("Internal error. graph2 was not marked as processed!");
		}

		LocusModuleParams param_g1 = ModuleHelper.getParameter("myParam2", graph1);
		LocusModuleParams param_g2 = ModuleHelper.getParameter("myParam2", graph2);

		try {
			ImageIO.write(ModuleHelper.covertPNGRawToImage(param_g1.getParamData()), "PNG", new File(
					"tmp/G1-myParam1-imageRestored.png"));
			ImageIO.write(ModuleHelper.covertPNGRawToImage(param_g2.getParamData()), "PNG", new File(
					"tmp/G2-myParam1-imageRestored.png"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		if (Arrays.equals(param_g1.getParamData(), param_g2.getParamData())) {
			return true;
		} else {
			return false;
		}

		// java.util.Set<LocusModuleParams> params = graph1.getParams();
		// for (LocusModuleParams param : params) {
		// if (param.getParamName().equals("myParam1")) {
		// System.out.println("G1: " + param.getParamName() + " = " + new
		// String(param.getParamData()));
		// } else if (param.getParamName().equals("myParam2")) {
		// try {
		// ImageIO.write(ImageIO.read(new
		// ByteArrayInputStream(param.getParamData())), "PNG", new File(
		// "tmp/G1-myParam1-imageRestored.png"));
		//		
		// } catch (IOException e) {
		// throw new RuntimeException(e);
		// }
		// }
		// }
		//
		// params = graph2.getParams();
		// for (LocusModuleParams param : params) {
		// if (param.getParamName().equals("myParam1")) {
		// System.out.println("G2: " + param.getParamName() + " = " +
		// param.getParamData());
		// } else {
		//
		// }
		// }

		// return false;
	}

	public String getName() {
		return "Тестовый модуль анализа";
	}

}
