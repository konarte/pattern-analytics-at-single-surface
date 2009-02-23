package edu.mgupi.pass.filters.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.filters.NoSuchParamException;
import edu.mgupi.pass.filters.Param;
import edu.mgupi.pass.filters.ParamHelper;
import edu.mgupi.pass.sources.TestSourceImpl;

public class ResizeFilterTest {
	private final static Logger logger = LoggerFactory.getLogger(ResizeFilterTest.class);

	private ResizeFilter filter = null;

	@Before
	public void setUp() throws Exception {
		filter = new ResizeFilter();
	}

	@After
	public void tearDown() throws Exception {
		if (filter != null) {
			filter.done();
			filter = null;
		}
	}

	@Test
	public void testGetParams() {
		Collection<Param> params = filter.getParams();
		assertNotNull(params);
		assertTrue(params == filter.getParams());
		System.out.println(params);
	}

	@Test
	public void testDone() {
		//
		for (String type : ImageIO.getReaderFormatNames()) {
			logger.debug("Supported type: " + type);
		}
	}

	private Map<String, Object> paramMap;

	private void convertImage(BufferedImage image, int width, int height) throws IOException, NoSuchParamException {
		paramMap.put("Width", width);
		paramMap.put("Height", height);

		Param param = ParamHelper.getParameterL("Method", filter.getParams());
		for (int i = 0; i < param.getAllowed_values().length; i++) {
			paramMap.put(param.getName(), param.getAllowed_values()[i]);

			BufferedImage newImage = filter.convert(image, null, paramMap);

			ImageIO.write(newImage, "JPG", new File("tmp/resize-" + width + "-" + height + "-"
					+ param.getVisual_values()[i] + ".jpg"));
		}

	}

	@Test
	public void testProcess() throws Exception {
		TestSourceImpl source = new TestSourceImpl();
		source.init();
		try {

			BufferedImage image = source.getSingleSource().getMainImage();
			paramMap = ParamHelper.convertParamsToValues(filter.getParams());

			this.convertImage(image, 256, 256);
			this.convertImage(image, 1024, 1024);
		} finally {
			source.done();
		}
	}

}
