package edu.mgupi.pass.modules;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.db.locuses.LocusModuleData;
import edu.mgupi.pass.db.locuses.Locuses;
import edu.mgupi.pass.filters.Param;
import edu.mgupi.pass.filters.ParamTest;
import edu.mgupi.pass.filters.Param.TYPES;
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

	private Collection<Param> params = null;
	private Param TEST_PARAM1 = new Param("test", "Тестовый", TYPES.INT, 0);
	private Param TEST_PARAM2 = new Param("test2", "Тестовый2", TYPES.STRING, "Hello");

	public TestModule() {
		params = new ArrayList<Param>();
		params.add(TEST_PARAM1);
		params.add(TEST_PARAM2);
		ParamTest.fillParameters(params);
		params = Collections.unmodifiableCollection(params);
	}

	public String getName() {
		return "Warning. Тестовый модуль анализа";
	}

	@Override
	public Collection<Param> getParams() {
		return params;
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

	public void analyze(BufferedImage filteredImage, Locuses store) throws IOException, ModuleException {
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

		ModuleHelper.putParameterValue(store, "myParam1", imageParams);
		ModuleHelper.putParameterValue(store, "myParam2", filteredImage);

		BufferedImage dest = new BufferedImage(256, 256, filteredImage.getType());
		Graphics2D graphics2D = dest.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		graphics2D.drawImage(filteredImage, 0, 0, 256, 256, null);

		graphics2D.setFont(new Font("Courier", Font.PLAIN, 22));
		graphics2D.setColor(Color.BLACK);

		// graphics2D.setStroke(new BasicStroke(3.0f));

		String text = TEST_PARAM1.getName() + "(" + TEST_PARAM1.getValue() + ") \n " + TEST_PARAM2.getName() + "("
				+ TEST_PARAM2.getValue() + ")";
		graphics2D.setColor(Color.WHITE);
		graphics2D.fillRect(20, 35 - graphics2D.getFontMetrics().getHeight() + 5, graphics2D.getFontMetrics()
				.charsWidth(text.toCharArray(), 0, text.length()), graphics2D.getFontMetrics().getHeight());

		graphics2D.setColor(Color.BLACK);
		graphics2D.drawString(text, 20, 35);

		graphics2D.dispose();

		logger.debug("Saving temporary image to store: " + dest);
		ModuleHelper.putTemporaryModuleImage(store, dest);
	}

	public double compare(Locuses graph1, Locuses graph2) throws ModuleException {
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

		LocusModuleData param_g1 = ModuleHelper.getParameter(graph1, "myParam2");
		LocusModuleData param_g2 = ModuleHelper.getParameter(graph2, "myParam2");

		try {
			new File("tmp").mkdir();
			ImageIO.write((BufferedImage) ModuleHelper.getParameterValue(graph1, "myParam2", true), "PNG", new File(
					"tmp/G1-myParam1-imageRestored.png"));
			ImageIO.write((BufferedImage) ModuleHelper.getParameterValue(graph2, "myParam2", true), "PNG", new File(
					"tmp/G2-myParam1-imageRestored.png"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		if (Arrays.equals(param_g1.getParamData(), param_g2.getParamData())) {
			return 1;
		} else {
			return 0;
		}

		// return false;
	}

	public Param getTEST_PARAM1() {
		return TEST_PARAM1;
	}

	public Param getTEST_PARAM2() {
		return TEST_PARAM2;
	}

}
