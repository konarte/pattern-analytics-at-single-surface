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

public class RescaleFilterTest {

	private final static Logger logger = LoggerFactory.getLogger(RescaleFilterTest.class);

	private RescaleFilter filter = null;

	@Before
	public void setUp() throws Exception {
		filter = new RescaleFilter();
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

	private void convertImage(BufferedImage image, Map<String, Object> paramMap, int brightness, int contrast,
			String name) throws IOException, NoSuchParamException {
		paramMap.put("Brightness", brightness);
		paramMap.put("Contrast", contrast);
		BufferedImage newImage = filter.convert(image, null, paramMap);

		ImageIO.write(newImage, "JPG", new File("tmp/" + name + "-" + contrast + "-" + brightness + ".jpg"));
	}

	@Test
	public void testProcess() throws Exception {
		TestSourceImpl source = new TestSourceImpl();
		source.init();
		try {

			BufferedImage image = source.getSingleSource().getMainImage();
			Collection<Param> params = filter.getParams();
			Map<String, Object> paramMap = ParamHelper.convertParamsToValues(params);

			this.convertImage(image, paramMap, 40, 100, "color");
			this.convertImage(image, paramMap, 0, 140, "color");

			ColorSpaceFilter cfilter = new ColorSpaceFilter();

			BufferedImage image2 = cfilter.convert(image, null, ParamHelper.convertParamsToValues(cfilter.getParams()));
			this.convertImage(image2, paramMap, 40, 100, "gray");
		} finally {
			source.done();
		}
	}

}
