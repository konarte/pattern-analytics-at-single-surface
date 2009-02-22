package edu.mgupi.pass.filters.java;

import static org.junit.Assert.assertNotNull;

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

public class ColorSpaceFilterTest {
	private final static Logger logger = LoggerFactory.getLogger(ColorSpaceFilterTest.class);

	private ColorSpaceFilter filter = null;

	@Before
	public void setUp() throws Exception {
		filter = new ColorSpaceFilter();
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
		System.out.println(params);
	}

	@Test
	public void testDone() {
		//
		for (String type : ImageIO.getReaderFormatNames()) {
			logger.debug("Supported type: " + type);
		}
	}

	private void convertImage(BufferedImage image, Map<String, Object> paramMap, int space, String name)
			throws IOException, NoSuchParamException {
		paramMap.put("ColorMode", space);
		BufferedImage newImage = filter.convert(image, null, paramMap);
		logger.info("Image converted to " + name + " SUCCESSFULLY");

		ImageIO.write(newImage, "JPG", new File("tmp/" + name + ".jpg"));
	}

	@Test
	public void testProcess() throws Exception {
		TestSourceImpl source = new TestSourceImpl();
		source.init();
		try {

			BufferedImage image = source.getSingleSource().getMainImage();
			Collection<Param> params = filter.getParams();
			Map<String, Object> paramMap = ParamHelper.convertParamsToValues(params);

			Exception savedE = null;

			ImageIO.write(image, "JPG", new File("tmp/ORIGINAL.jpg"));

			Param param = params.iterator().next();
			for (int i = 0; i < param.getAllowed_values().length; i++) {
				try {
					this.convertImage(image, paramMap, (Integer) param.getAllowed_values()[i],
							param.getVisual_values()[i]);
				} catch (Exception e) {
					new Exception("Error on converting to ColorSpace " + param.getVisual_values()[i], e)
							.printStackTrace();
					savedE = e;
				}
			}
			if (savedE != null) {
				throw savedE;
			}
		} finally {
			source.done();
		}
	}
}
