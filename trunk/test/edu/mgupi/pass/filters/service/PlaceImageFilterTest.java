package edu.mgupi.pass.filters.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.awt.Color;
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
import edu.mgupi.pass.inputs.TestInputImpl;

public class PlaceImageFilterTest {

	private PlaceImageFilter filter = null;

	@Before
	public void setUp() throws Exception {
		filter = new PlaceImageFilter();
	}

	@After
	public void tearDown() throws Exception {
		filter = null;
	}

	@Test
	public void testGetParams() {
		Collection<Param> params = filter.getParams();
		assertNotNull(params);
		try {
			params.add(new Param("1", "2", ParamType.INT, 0));
			fail("No exception throws!");
		} catch (UnsupportedOperationException e) {
			System.out.println("Received expected exception: " + e);
		}
	}

	private void convertImage(BufferedImage image, int width, int height, Color background) throws IOException,
			FilterException {
		filter.getWIDTH().setValue(width);
		filter.getHEIGHT().setValue(height);
		filter.getBACKGROUND().setValue(background);

		new File("tmp").mkdir();

		Param param = filter.getPLACE();
		for (int i = 0; i < param.getAllowed_values().length; i++) {
			param.setValue(param.getAllowed_values()[i]);

			BufferedImage newImage = filter.convert(image);

			ImageIO.write(newImage, "JPG", new File("tmp/place-" + width + "-" + height + "-"
					+ param.getVisual_values()[i] + "-" + background + ".jpg"));
		}

	}

	@Test
	public void testConvert() throws Exception {
		TestInputImpl source = new TestInputImpl();
		source.init();
		try {

			BufferedImage image = source.getSingleSource().getSourceImage();

			this.convertImage(image, 1024, 1024, Color.WHITE);
			this.convertImage(image, 1024, 1024, Color.BLUE);
		} finally {
			source.close();
		}
	}

}
