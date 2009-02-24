package edu.mgupi.pass.filters.java;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.filters.Param;
import edu.mgupi.pass.filters.ParamException;
import edu.mgupi.pass.filters.ParamHelper;
import edu.mgupi.pass.sources.TestSourceImpl;

public class RescaleFilterTest {

	private RescaleFilter filter = null;

	@Before
	public void setUp() throws Exception {
		filter = new RescaleFilter();
	}

	@After
	public void tearDown() throws Exception {
		filter = null;
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
	}

	private void convertImage(BufferedImage image, int brightness, int contrast, String name) throws IOException,
			ParamException {
		ParamHelper.getParameterL("Brightness", filter).setValue(brightness);
		ParamHelper.getParameterL("Contrast", filter).setValue(contrast);

		BufferedImage newImage = filter.convert(image);

		ImageIO.write(newImage, "JPG", new File("tmp/" + name + "-" + contrast + "-" + brightness + ".jpg"));
	}

	@Test
	public void testProcess() throws Exception {
		TestSourceImpl source = new TestSourceImpl();
		source.init();
		try {

			BufferedImage image = source.getSingleSource().getImage();

			this.convertImage(image, 40, 100, "color");
			this.convertImage(image, 0, 140, "color");

			ColorSpaceFilter cfilter = new ColorSpaceFilter();
			ParamHelper.getParameterL("ColorMode", cfilter).setValue(ColorSpace.CS_GRAY);

			BufferedImage image2 = cfilter.convert(image);
			this.convertImage(image2, 40, 100, "CS_GRAY");
		} finally {
			source.done();
		}
	}

}
