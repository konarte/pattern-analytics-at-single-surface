package edu.mgupi.pass.filters.java;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.filters.FilterException;
import edu.mgupi.pass.filters.Param;
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

	private void convertImage(BufferedImage image, int space, String name) throws IOException, FilterException {
		filter.getCOLOR_MODE().setValue(space);
		BufferedImage newImage = filter.convert(image);
		logger.info("Image converted to " + name + " SUCCESSFULLY (image type is " + newImage.getType() + ")");
		
		new File("tmp").mkdir();

		ImageIO.write(newImage, "JPG", new File("tmp/colorspace " + name + ".jpg"));
	}

	@Test
	public void testProcess() throws Exception {
		TestSourceImpl source = new TestSourceImpl();
		source.init();
		try {

			BufferedImage image = source.getSingleSource().getSourceImage();

			Exception savedE = null;
			
			new File("tmp").mkdir();

			ImageIO.write(image, "JPG", new File("tmp/ORIGINAL.jpg"));

			Param param = filter.getCOLOR_MODE();
			for (int i = 0; i < param.getAllowed_values().length; i++) {
				try {
					this.convertImage(image, (Integer) param.getAllowed_values()[i], param.getVisual_values()[i]);
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
			source.close();
		}
	}
}
