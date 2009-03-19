package edu.mgupi.pass.filters;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.sources.TestSourceImpl;

public class TestFilterTest {

	private TestFilter filter = null;

	@Before
	public void setUp() throws Exception {
		filter = new TestFilter();
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

	private void convertImage(BufferedImage image) throws IOException, NoSuchParamException {
		BufferedImage newImage = filter.convert(image);
		
		new File("tmp").mkdir();

		ImageWriter writer = ImageIO.getImageWritersByFormatName("JPG").next();
		writer.setOutput(ImageIO.createImageOutputStream(new File("tmp/test-filter.jpg")));

		ImageWriteParam iwp = writer.getDefaultWriteParam();
		iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		iwp.setCompressionQuality(0.90f);

		writer.write(null, new IIOImage(newImage, null, null), iwp);
		writer.dispose();

	}

	@Test
	public void testProcess() throws Exception {
		TestSourceImpl source = new TestSourceImpl();
		source.init();
		try {

			BufferedImage image = source.getSingleSource().getSourceImage();
			filter.onAttachToImage(image);
			try {
				this.convertImage(image);
			} finally {
				filter.onDetachFromImage(image);
			}
		} finally {

			source.close();
		}
	}
}
