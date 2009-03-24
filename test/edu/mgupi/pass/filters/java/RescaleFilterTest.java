package edu.mgupi.pass.filters.java;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.filters.FilterException;
import edu.mgupi.pass.filters.Param;
import edu.mgupi.pass.filters.Param.ParamType;
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
		try {
			params.add(new Param("1", "2", ParamType.INT, 0));
			fail("No exception throws!");
		} catch (UnsupportedOperationException e) {
			System.out.println("Received expected exception: " + e);
		}
	}

	@Test
	public void testDone() {
		//
	}

	private void convertImage(BufferedImage image, int brightness, int contrast, String name) throws IOException,
			FilterException {
		filter.getBRIGHTNESS().setValue(brightness);
		filter.getCONTRAST().setValue(contrast);

		BufferedImage newImage = filter.convert(image);

		new File("tmp").mkdir();

		ImageIO.write(newImage, "JPG", new File("tmp/" + name + "-" + contrast + "-" + brightness + ".jpg"));
	}

	@Test
	public void testProcess() throws Exception {
		TestSourceImpl source = new TestSourceImpl();
		source.init();
		try {

			BufferedImage image = source.getSingleSource().getSourceImage();

			this.convertImage(image, 40, 100, "color");
			this.convertImage(image, 0, 140, "color");

			ColorSpaceFilter cfilter = new ColorSpaceFilter();
			cfilter.getCOLOR_MODE().setValue(ColorSpace.CS_GRAY);

			BufferedImage image2 = cfilter.convert(image);
			this.convertImage(image2, 40, 100, "CS_GRAY");
		} finally {
			source.close();
		}
	}

}
