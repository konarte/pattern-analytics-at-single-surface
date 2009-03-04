package edu.mgupi.pass.filters.service;

import static org.junit.Assert.assertNull;
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

import edu.mgupi.pass.filters.FilterException;
import edu.mgupi.pass.filters.Param;
import edu.mgupi.pass.filters.java.ColorSpaceFilter;
import edu.mgupi.pass.filters.java.GrayScaleFilter;
import edu.mgupi.pass.filters.java.InvertFilter;
import edu.mgupi.pass.filters.java.RescaleFilter;
import edu.mgupi.pass.sources.TestSourceImpl;

public class HistogramFilterTest {

	private HistogramFilter filter = null;

	@Before
	public void setUp() throws Exception {
		filter = new HistogramFilter();
	}

	@After
	public void tearDown() throws Exception {
		if (filter != null) {
			filter = null;
		}
	}

	@Test
	public void testGetParams() {
		Collection<Param> params = filter.getParams();
		assertNull(params);
		assertTrue(params == filter.getParams());
		System.out.println(params);
	}

	@Test
	public void testDone() {
		//
	}

	private void saveImage(BufferedImage image, String addInfo) throws IOException, FilterException {
		BufferedImage newImage = filter.convert(image);

		ImageIO.write(newImage, "JPG", new File("tmp/histogram-" + addInfo + ".jpg"));
	}

	@Test
	public void testProcess() throws Exception {
		TestSourceImpl source = new TestSourceImpl();
		source.init();
		try {
			BufferedImage image = source.getSingleSource().getSourceImage();
			this.saveImage(image, "Color");

			GrayScaleFilter grayscale = new GrayScaleFilter();
			BufferedImage image2 = grayscale.convert(image);
			this.saveImage(image2, "Grayscale");

			InvertFilter invert = new InvertFilter();
			BufferedImage image3 = invert.convert(image);
			this.saveImage(image3, "Invert");

			image3 = invert.convert(image2);
			this.saveImage(image3, "Grayscale to Invert");

			image3 = invert.convert(image);
			image2 = grayscale.convert(image3);

			this.saveImage(image2, "Invert to Grayscale");

			ColorSpaceFilter cfilter = new ColorSpaceFilter();

			cfilter.getCOLOR_MODE().setValue(ColorSpace.CS_LINEAR_RGB);
			image2 = cfilter.convert(image);
			this.saveImage(image2, "Linear RGB");

			cfilter.getCOLOR_MODE().setValue(ColorSpace.CS_GRAY);
			image3 = cfilter.convert(image2);
			this.saveImage(image3, "Linear RGB to CS_GRAY");

			// paramMap1.put("ColorMode", ColorSpace.CS_sRGB);
			// image2 = cfilter.convert(image, null, paramMap1);
			// this.convertImage(image2, paramMap1, "sRGB");

			cfilter.getCOLOR_MODE().setValue(ColorSpace.CS_GRAY);
			image2 = cfilter.convert(image);
			this.saveImage(image2, "CS_GRAY");

			RescaleFilter rfilter = new RescaleFilter();
			rfilter.getBRIGHTNESS().setValue(40);
			rfilter.getCONTRAST().setValue(100);
			image = rfilter.convert(image2);

			this.saveImage(image, "CS_GRAY 100-40");

		} finally {
			source.close();
		}
	}

}
