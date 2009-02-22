package edu.mgupi.pass.filters.service;

import static org.junit.Assert.assertNull;

import java.awt.color.ColorSpace;
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
import edu.mgupi.pass.filters.java.ColorSpaceFilter;
import edu.mgupi.pass.filters.java.RescaleFilter;
import edu.mgupi.pass.filters.java.SimpleSharpFilterTest;
import edu.mgupi.pass.filters.service.HistogramFilter;
import edu.mgupi.pass.sources.TestSourceImpl;

public class HistogramFilterTest {
	private final static Logger logger = LoggerFactory.getLogger(SimpleSharpFilterTest.class);

	private HistogramFilter filter = null;

	@Before
	public void setUp() throws Exception {
		filter = new HistogramFilter();
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

	private void convertImage(BufferedImage image, Map<String, Object> paramMap, String addInfo) throws IOException,
			NoSuchParamException {
		BufferedImage newImage = filter.convert(image, null, paramMap);

		ImageIO.write(newImage, "JPG", new File("tmp/histogram-" + addInfo + ".jpg"));
	}

	@Test
	public void testProcess() throws Exception {
		TestSourceImpl source = new TestSourceImpl();
		source.init();
		try {

			BufferedImage image = source.getSingleSource().getMainImage();
			Collection<Param> params = filter.getParams();
			Map<String, Object> paramMap = ParamHelper.convertParamsToValues(params);

			this.convertImage(image, paramMap, "Color");

			ColorSpaceFilter cfilter = new ColorSpaceFilter();
			Map<String, Object> paramMap1 = ParamHelper.convertParamsToValues(cfilter.getParams());
			paramMap1.put("ColorMode", ColorSpace.CS_LINEAR_RGB);
			BufferedImage image2 = cfilter.convert(image, null, paramMap1);
			this.convertImage(image2, paramMap1, "Linear RGB");
			paramMap1.put("ColorMode", ColorSpace.CS_GRAY);
			BufferedImage image3 = cfilter.convert(image2, null, paramMap1);
			this.convertImage(image3, paramMap1, "Linear RGB to Gray");

//			paramMap1.put("ColorMode", ColorSpace.CS_sRGB);
//			image2 = cfilter.convert(image, null, paramMap1);
//			this.convertImage(image2, paramMap1, "sRGB");

			paramMap1.put("ColorMode", ColorSpace.CS_GRAY);
			image2 = cfilter.convert(image, null, paramMap1);
			this.convertImage(image2, paramMap1, "Gray");

			RescaleFilter rfilter = new RescaleFilter();
			Map<String, Object> paramMap2 = ParamHelper.convertParamsToValues(rfilter.getParams());
			paramMap2.put("Brightness", 40);
			paramMap2.put("Contrast", 100);
			image = rfilter.convert(image2, null, paramMap2);

			this.convertImage(image, paramMap, "Gray 100-40");
		} finally {
			source.done();
		}
	}

}
