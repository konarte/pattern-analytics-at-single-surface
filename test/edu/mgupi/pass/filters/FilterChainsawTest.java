package edu.mgupi.pass.filters;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.filters.java.ColorSpaceFilter;
import edu.mgupi.pass.filters.java.RescaleFilter;
import edu.mgupi.pass.sources.TestSourceImpl;

/**
 * Test for common chainsaw
 * 
 * @author raidan
 * 
 */
public class FilterChainsawTest {

	private FilterChainsaw chainsaw = null;

	@Before
	public void setUp() {
		chainsaw = new FilterChainsaw();
	}

	@After
	public void tearDown() {
		if (chainsaw != null) {
			chainsaw.close();
			chainsaw = null;
		}
	}

	@Test
	public void testReset() throws InstantiationException, IllegalAccessException {
		chainsaw.appendFilter(TestFilter.class);
		chainsaw.appendFilter(TestFilter.class);

		assertNotNull(chainsaw.getFilter(0));

		chainsaw.reset();
		assertNull(chainsaw.getFilter(0));

		chainsaw.reset();
		assertNull(chainsaw.getFilter(0));

		chainsaw.appendFilter(new TestFilter());
		assertNotNull(chainsaw.getFilter(0));

		chainsaw.reset();
		assertNull(chainsaw.getFilter(0));
	}

	@Test
	public void testAppendFilter() throws InstantiationException, IllegalAccessException {
		TestFilter filter = new TestFilter();
		TestFilter filter2 = new TestFilter();

		chainsaw.appendFilter(filter);

		assertTrue(filter == chainsaw.getFilter(0));

		chainsaw.appendFilter(filter2);

		assertTrue(filter == chainsaw.getFilter(0));
		assertTrue(filter2 == chainsaw.getFilter(1));

		try {
			chainsaw.appendFilter((IFilter) null);
			fail("No IllegalArgumentException!");
		} catch (IllegalArgumentException iae) {
			System.out.println("Received expected exception: " + iae);
		}

		chainsaw.reset();

		chainsaw.appendFilter(TestFilter.class);
		chainsaw.appendFilter(ColorSpaceFilter.class);

		assertTrue(TestFilter.class == chainsaw.getFilter(0).getClass());
		assertTrue(ColorSpaceFilter.class == chainsaw.getFilter(1).getClass());

		try {
			chainsaw.appendFilter((Class<IFilter>) null);
			fail("No IllegalArgumentException!");
		} catch (IllegalArgumentException iae) {
			System.out.println("Received expected exception: " + iae);
		}
	}

	@Test
	public void testRemoveFilter() {
		TestFilter filter = new TestFilter();
		TestFilter filter2 = new TestFilter();

		chainsaw.appendFilter(filter);
		chainsaw.appendFilter(filter2);

		assertTrue(filter == chainsaw.getFilter(0));
		assertTrue(filter2 == chainsaw.getFilter(1));

		chainsaw.removeFilter(0);

		assertTrue(filter2 == chainsaw.getFilter(0));

		filter = new TestFilter();
		chainsaw.appendFilter(filter);
		assertTrue(filter2 == chainsaw.getFilter(0));
		assertTrue(filter == chainsaw.getFilter(1));

		chainsaw.removeFilter(5);

		assertTrue(filter2 == chainsaw.getFilter(0));
		assertTrue(filter == chainsaw.getFilter(1));

	}

	@Test
	public void testGetFilter() {
		TestFilter filter = new TestFilter();
		TestFilter filter2 = new TestFilter();

		chainsaw.appendFilter(filter);
		chainsaw.appendFilter(filter2);

		assertTrue(filter == chainsaw.getFilter(0));
		assertTrue(filter2 == chainsaw.getFilter(1));
		assertTrue(filter == chainsaw.getFilter(0));

		assertNull(chainsaw.getFilter(-1));
		assertNull(chainsaw.getFilter(5));
	}

	@Test
	public void testMoveUpAndDown() {
		chainsaw.moveUp(0);
		assertNull(chainsaw.getFilter(0));

		chainsaw.moveDown(0);
		assertNull(chainsaw.getFilter(0));

		TestFilter filter = new TestFilter();
		TestFilter filter2 = new TestFilter();
		TestFilter filter3 = new TestFilter();
		chainsaw.appendFilter(filter);
		chainsaw.appendFilter(filter2);
		chainsaw.appendFilter(filter3);

		assertTrue(filter == chainsaw.getFilter(0));
		chainsaw.moveUp(0);
		assertTrue(filter == chainsaw.getFilter(0));
		assertTrue(filter2 == chainsaw.getFilter(1));
		assertTrue(filter3 == chainsaw.getFilter(2));

		chainsaw.moveDown(0);
		assertTrue(filter2 == chainsaw.getFilter(0));
		assertTrue(filter == chainsaw.getFilter(1));
		assertTrue(filter3 == chainsaw.getFilter(2));

		chainsaw.moveDown(1);
		assertTrue(filter2 == chainsaw.getFilter(0));
		assertTrue(filter3 == chainsaw.getFilter(1));
		assertTrue(filter == chainsaw.getFilter(2));

		chainsaw.moveDown(2);
		assertTrue(filter2 == chainsaw.getFilter(0));
		assertTrue(filter3 == chainsaw.getFilter(1));
		assertTrue(filter == chainsaw.getFilter(2));

		chainsaw.moveUp(1);
		assertTrue(filter3 == chainsaw.getFilter(0));
		assertTrue(filter2 == chainsaw.getFilter(1));
		assertTrue(filter == chainsaw.getFilter(2));

		chainsaw.moveUp(2);
		assertTrue(filter3 == chainsaw.getFilter(0));
		assertTrue(filter == chainsaw.getFilter(1));
		assertTrue(filter2 == chainsaw.getFilter(2));

		chainsaw.moveUp(1);
		chainsaw.moveDown(1);
		assertTrue(filter == chainsaw.getFilter(0));
		assertTrue(filter2 == chainsaw.getFilter(1));
		assertTrue(filter3 == chainsaw.getFilter(2));
	}

	@Test
	public void testAttachAndDetach() throws FilterException, InstantiationException, IllegalAccessException {
		BufferedImage image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);

		chainsaw.appendFilter(TestFilter.class);
		chainsaw.appendFilter(TestFilter.class);

		chainsaw.attachImage(image);
		assertTrue(((TestFilter) chainsaw.getFilter(0)).isAttached());
		assertTrue(((TestFilter) chainsaw.getFilter(1)).isAttached());
		chainsaw.detachImage();
		assertFalse(((TestFilter) chainsaw.getFilter(0)).isAttached());
		assertFalse(((TestFilter) chainsaw.getFilter(1)).isAttached());

		chainsaw.attachImage(image);
		try {
			chainsaw.attachImage(image);
			fail("No IllegalStateException!");
		} catch (IllegalStateException ise) {
			System.out.println("Received expected exception: " + ise);
		}
		assertTrue(((TestFilter) chainsaw.getFilter(0)).isAttached());
		assertTrue(((TestFilter) chainsaw.getFilter(1)).isAttached());
		chainsaw.detachImage();
		chainsaw.detachImage();
		assertFalse(((TestFilter) chainsaw.getFilter(0)).isAttached());
		assertFalse(((TestFilter) chainsaw.getFilter(1)).isAttached());
		assertFalse(((TestFilter) chainsaw.getFilter(0)).isDone());
		assertFalse(((TestFilter) chainsaw.getFilter(1)).isDone());

		chainsaw.reset();
		chainsaw.attachImage(image);
		try {
			chainsaw.attachImage(image);
			fail("No IllegalStateException!");
		} catch (IllegalStateException ise) {
			System.out.println("Received expected exception: " + ise);
		}

		TestFilter filter = new TestFilter();
		TestFilter filter2 = new TestFilter();
		chainsaw.appendFilter(filter);
		assertTrue(((TestFilter) chainsaw.getFilter(0)).isAttached());

		chainsaw.appendFilter(filter2);
		assertTrue(((TestFilter) chainsaw.getFilter(1)).isAttached());

		try {
			chainsaw.attachImage(image);
			fail("No IllegalStateException!");
		} catch (IllegalStateException ise) {
			System.out.println("Received expected exception: " + ise);
		}

		chainsaw.removeFilter(0);
		assertFalse(filter.isAttached());
		assertTrue(filter.isDone());

		assertTrue(filter2.isAttached());
		assertFalse(filter2.isDone());

		chainsaw.removeFilter(0);
		assertFalse(filter2.isAttached());
		assertTrue(filter2.isDone());

		chainsaw.filterSaw();

		chainsaw.detachImage();

		try {
			chainsaw.filterSaw();
			fail("No IllegalStateException!");
		} catch (IllegalStateException ise) {
			System.out.println("Received expected exception: " + ise);
		}

	}

	private void convertImage(BufferedImage image, String addText) throws IOException, FilterException {
		BufferedImage newImage = chainsaw.filterSaw();

		new File("tmp").mkdir();

		ImageIO.write(newImage, "JPG", new File("tmp/saw" + chainsaw + addText + ".jpg"));

	}

	@Test
	public void testCommonFilterSaw() throws FilterException, IOException {
		TestSourceImpl source = new TestSourceImpl();
		source.init();
		try {

			BufferedImage image = source.getSingleSource().getSourceImage();
			chainsaw.attachImage(image);

			this.convertImage(image, "");

			ColorSpaceFilter color = new ColorSpaceFilter();
			color.getCOLOR_MODE().setValue(ColorSpace.CS_GRAY);
			chainsaw.appendFilter(color);

			RescaleFilter rescale = new RescaleFilter();
			rescale.getBRIGHTNESS().setValue(40);
			chainsaw.appendFilter(rescale);

			this.convertImage(image, "1");

			chainsaw.moveDown(0);
			this.convertImage(image, "");

			chainsaw.moveDown(0);
			this.convertImage(image, "2");

			chainsaw.detachImage();

		} finally {
			source.close();
		}
	}

}
