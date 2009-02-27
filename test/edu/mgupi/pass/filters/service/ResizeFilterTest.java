package edu.mgupi.pass.filters.service;

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

import edu.mgupi.pass.filters.Param;
import edu.mgupi.pass.filters.FilterException;
import edu.mgupi.pass.filters.ParamHelper;
import edu.mgupi.pass.sources.TestSourceImpl;

public class ResizeFilterTest {

	private ResizeFilter filter = null;

	@Before
	public void setUp() throws Exception {
		filter = new ResizeFilter();
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

	private void convertImage(BufferedImage image, int width, int height) throws IOException, FilterException {
		ParamHelper.getParameter("Width", filter).setValue(width);
		ParamHelper.getParameter("Height", filter).setValue(height);

		Param param = ParamHelper.getParameter("Method", filter);
		for (int i = 0; i < param.getAllowed_values().length; i++) {
			param.setValue(param.getAllowed_values()[i]);

			BufferedImage newImage = filter.convert(image);

			ImageIO.write(newImage, "JPG", new File("tmp/resize-" + width + "-" + height + "-"
					+ param.getVisual_values()[i] + ".jpg"));
		}

	}

	@Test
	public void testProcess() throws Exception {
		TestSourceImpl source = new TestSourceImpl();
		source.init();
		try {

			BufferedImage image = source.getSingleSource().getSourceImage();

			this.convertImage(image, 256, 256);
			this.convertImage(image, 1024, 1024);
		} finally {
			source.done();
		}
	}

}
