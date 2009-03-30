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

import edu.mgupi.pass.filters.FilterChainsaw.SawMode;
import edu.mgupi.pass.filters.java.ColorSpaceFilter;
import edu.mgupi.pass.filters.java.RescaleFilter;
import edu.mgupi.pass.inputs.TestInputImpl;

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
	public void testReset() throws Exception {
		chainsaw.appendFilter(TestFilter.class);
		chainsaw.appendFilter(TestFilter.class);

		assertNotNull(chainsaw.getFilter(0));

		chainsaw.reset();
		assertNull(chainsaw.getFilter(0));

		chainsaw.reset();
		assertNull(chainsaw.getFilter(0));

		chainsaw.appendFilter(TestFilter.class);
		assertNotNull(chainsaw.getFilter(0));

		chainsaw.reset();
		assertNull(chainsaw.getFilter(0));
	}

	@Test
	public void testAppendFilter() throws Exception {
		TestFilter filter = (TestFilter) chainsaw.appendFilter(TestFilter.class);

		assertTrue(filter == chainsaw.getFilter(0));
		assertTrue(filter.isInit());
		assertFalse(filter.isDone());

		TestFilter filter2 = (TestFilter) chainsaw.appendFilter(TestFilter.class);
		assertTrue(filter == chainsaw.getFilter(0));
		assertTrue(filter2 == chainsaw.getFilter(1));

		TestMyFilter my = (TestMyFilter) chainsaw.appendFilter(TestMyFilter.class);
		assertTrue(my.isInit());
		assertFalse(my.isDone());

		try {
			chainsaw.appendFilter(null);
			fail("No IllegalArgumentException!");
		} catch (IllegalArgumentException iae) {
			System.out.println("Received expected exception: " + iae);
		}

		// This is OK, just if is not registered in database
		chainsaw.appendFilter(TestMyFilter.class);

		TestInputImpl source = new TestInputImpl();
		source.init();
		try {
			BufferedImage image = source.getSingleSource().getSourceImage();
			chainsaw.attachImage(image);
			chainsaw.filterSaw();
			chainsaw.detachImage();
		} finally {
			source.close();
		}

		chainsaw.reset();
		assertTrue(filter.isInit());
		assertFalse(filter.isDone());

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

		chainsaw.close();
		chainsaw = new FilterChainsaw(SawMode.USER_EDIT_CHAINSAW);
		try {
			chainsaw.appendFilter(TestMyFilter.class);
			fail("No FilterNotFoundException thrown!");
		} catch (FilterNotFoundException fne) {
			System.out.println("Received expected exception: " + fne);
		}
	}

	@Test
	public void testRemoveFilter() throws Exception {
		TestFilter filter = (TestFilter) chainsaw.appendFilter(TestFilter.class);
		TestFilter filter2 = (TestFilter) chainsaw.appendFilter(TestFilter.class);

		assertTrue(filter == chainsaw.getFilter(0));
		assertTrue(filter2 == chainsaw.getFilter(1));

		chainsaw.removeFilter(0);

		assertTrue(filter2 == chainsaw.getFilter(0));

		filter = (TestFilter) chainsaw.appendFilter(TestFilter.class);
		assertTrue(filter2 == chainsaw.getFilter(0));
		assertTrue(filter == chainsaw.getFilter(1));

		chainsaw.removeFilter(5);

		assertTrue(filter2 == chainsaw.getFilter(0));
		assertTrue(filter == chainsaw.getFilter(1));

	}

	@Test
	public void testGetFilter() throws Exception {
		TestFilter filter = (TestFilter) chainsaw.appendFilter(TestFilter.class);
		TestFilter filter2 = (TestFilter) chainsaw.appendFilter(TestFilter.class);

		assertTrue(filter == chainsaw.getFilter(0));
		assertTrue(filter2 == chainsaw.getFilter(1));
		assertTrue(filter == chainsaw.getFilter(0));

		assertNull(chainsaw.getFilter(-1));
		assertNull(chainsaw.getFilter(5));
	}

	@Test
	public void testMoveUpAndDown() throws Exception {
		chainsaw.moveUp(0);
		assertNull(chainsaw.getFilter(0));

		chainsaw.moveDown(0);
		assertNull(chainsaw.getFilter(0));

		TestFilter filter = (TestFilter) chainsaw.appendFilter(TestFilter.class);
		TestFilter filter2 = (TestFilter) chainsaw.appendFilter(TestFilter.class);
		TestFilter filter3 = (TestFilter) chainsaw.appendFilter(TestFilter.class);

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
	public void testAttachAndDetach() throws Exception {
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

		TestFilter filter = (TestFilter) chainsaw.appendFilter(TestFilter.class);

		assertTrue(((TestFilter) chainsaw.getFilter(0)).isAttached());

		TestFilter filter2 = (TestFilter) chainsaw.appendFilter(TestFilter.class);
		assertTrue(((TestFilter) chainsaw.getFilter(1)).isAttached());

		try {
			chainsaw.attachImage(image);
			fail("No IllegalStateException!");
		} catch (IllegalStateException ise) {
			System.out.println("Received expected exception: " + ise);
		}

		chainsaw.removeFilter(0);
		assertFalse(filter.isAttached());
		assertFalse(filter.isDone());

		assertTrue(filter2.isAttached());
		assertFalse(filter2.isDone());

		chainsaw.removeFilter(0);
		assertFalse(filter2.isAttached());
		assertFalse(filter2.isDone());

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
	public void testCommonFilterSaw() throws Exception {
		TestInputImpl source = new TestInputImpl();
		source.init();
		try {

			BufferedImage image = source.getSingleSource().getSourceImage();
			chainsaw.attachImage(image);

			this.convertImage(image, "");

			ColorSpaceFilter color = (ColorSpaceFilter) chainsaw.appendFilter(ColorSpaceFilter.class);
			color.getCOLOR_MODE().setValue(ColorSpace.CS_GRAY);

			RescaleFilter rescale = (RescaleFilter) chainsaw.appendFilter(RescaleFilter.class);
			rescale.getBRIGHTNESS().setValue(40);

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

	@Test
	public void testFilterSourceCaching() throws Exception {
		chainsaw.close();
		chainsaw = new FilterChainsaw(SawMode.SINGLE_INSTANCE);

		TestFilter filter = (TestFilter) chainsaw.appendFilter(TestFilter.class);
		TestFilter filter2 = (TestFilter) chainsaw.appendFilter(TestFilter.class);

		assertTrue(filter == filter2);
		assertTrue(chainsaw.getFilter(0) == filter);
		assertNull(chainsaw.getFilter(1));

		chainsaw.removeFilter(TestFilter.class);

		assertNull(chainsaw.getFilter(0));

		filter2 = (TestFilter) chainsaw.appendFilter(TestFilter.class);
		assertTrue(filter == filter2);

	}

}
