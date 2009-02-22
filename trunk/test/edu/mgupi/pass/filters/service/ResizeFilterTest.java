package edu.mgupi.pass.filters.service;

import static org.junit.Assert.assertNull;

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
		assertNull(params);
		System.out.println(params);
	}

	@Test
	public void testDone() {
		//
		for (String type : ImageIO.getReaderFormatNames()) {
			logger.debug("Supported type: " + type);
		}
	}

	private void convertImage(BufferedImage image, Map<String, Object> paramMap, int width, int height)
			throws IOException, NoSuchParamException {
		paramMap.put("Width", width);
		paramMap.put("Height", height);
		BufferedImage newImage = filter.convert(image, null, paramMap);

		ImageIO.write(newImage, "JPG", new File("tmp/resize-" + width + "-" + height + ".jpg"));
	}

	@Test
	public void testProcess() throws Exception {
		TestSourceImpl source = new TestSourceImpl();
		source.init();
		try {

			BufferedImage image = source.getSingleSource().getMainImage();
			Collection<Param> params = filter.getParams();
			Map<String, Object> paramMap = ParamHelper.convertParamsToValues(params);

			this.convertImage(image, paramMap, 256, 256);
		} finally {
			source.done();
		}
	}

}
