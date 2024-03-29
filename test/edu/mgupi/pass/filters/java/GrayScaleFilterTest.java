package edu.mgupi.pass.filters.java;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
import edu.mgupi.pass.inputs.TestInputImpl;

public class GrayScaleFilterTest {

	private GrayScaleFilter filter = null;

	@Before
	public void setUp() throws Exception {
		filter = new GrayScaleFilter();
	}

	@After
	public void tearDown() throws Exception {
		filter = null;
	}

	@Test
	public void testGetParams() {
		Collection<Param> params = filter.getParams();
		assertNull(params);
		assertTrue(params == filter.getParams());
	}

	@Test
	public void testDone() {
		//
	}

	private void convertImage(BufferedImage image) throws IOException, FilterException {
		BufferedImage newImage = filter.convert(image);

		new File("tmp").mkdir();

		ImageIO.write(newImage, "JPG", new File("tmp/grayscale.jpg"));
	}

	@Test
	public void testProcess() throws Exception {
		TestInputImpl source = new TestInputImpl();
		source.init();
		try {

			BufferedImage image = source.getSingleSource().getSourceImage();

			this.convertImage(image);
		} finally {
			source.close();
		}
	}
}
