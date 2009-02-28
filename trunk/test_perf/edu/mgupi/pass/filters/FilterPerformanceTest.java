package edu.mgupi.pass.filters;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.filters.java.ColorSpaceFilter;
import edu.mgupi.pass.filters.java.GrayScaleFilter;
import edu.mgupi.pass.filters.java.InvertFilter;
import edu.mgupi.pass.filters.java.RescaleFilter;
import edu.mgupi.pass.filters.java.SimpleSharpFilter;
import edu.mgupi.pass.filters.java.SimpleSmoothFilter;
import edu.mgupi.pass.filters.service.HistogramFilter;
import edu.mgupi.pass.filters.service.ResizeFilter;
import edu.mgupi.pass.sources.TestSourceImpl;
import edu.mgupi.pass.util.Secundomer;
import edu.mgupi.pass.util.SecundomerList;

/**
 * Intel Core 2 Duo E6750 (2.66 GHz), image 425x640, internal Eclipse 3.4 JUnit
 * 4 runner, Java 1.6.0_12
 * 
 * <pre>
 * == RESULT ==
 * edu.mgupi.pass.filters.java.ColorSpaceFilter. Total: 4406 msec (100), avg = 44.06 msec/call
 * edu.mgupi.pass.filters.java.GrayScaleFilter. Total: 187 msec (100), avg = 1.87 msec/call
 * edu.mgupi.pass.filters.java.InvertFilter. Total: 797 msec (100), avg = 7.97 msec/call
 * edu.mgupi.pass.filters.java.RescaleFilter. Total: 4062 msec (100), avg = 40.62 msec/call
 * edu.mgupi.pass.filters.java.SimpleSharpFilter. Total: 1640 msec (100), avg = 16.4 msec/call
 * edu.mgupi.pass.filters.java.SimpleSmoothFilter. Total: 1625 msec (100), avg = 16.25 msec/call
 * edu.mgupi.pass.filters.service.HistogramFilter. Total: 1296 msec (100), avg = 12.96 msec/call
 * edu.mgupi.pass.filters.service.ResizeFilter. Total: 3156 msec (100), avg = 31.56 msec/call
 * edu.mgupi.pass.filters.java.GrayScaleFilter CHAINSAW. Total: 156 msec (100), avg = 1.56 msec/call
 * edu.mgupi.pass.filters.java.GrayScaleFilter CHAINSAW EVERYIMAGE. Total: 1875 msec (100), avg = 18.75 msec/call
 * edu.mgupi.pass.filters.java.GrayScaleFilter EVERYIMAGE. Total: 1828 msec (100), avg = 18.28 msec/call
 * edu.mgupi.pass.filters.java.InvertFilter EVERYIMAGE. Total: 2250 msec (100), avg = 22.5 msec/call
 * edu.mgupi.pass.filters.java.SimpleSharpFilter EVERYIMAGE. Total: 3063 msec (100), avg = 30.63 msec/call
 * </pre>
 * 
 * 
 * Intel Core 2 Duo E6750 (2.66 GHz), image 800x721, internal Eclipse 3.4 JUnit
 * 4 runner, Java 1.6.0_12
 * 
 * <pre>
 * == RESULT == 
 * edu.mgupi.pass.filters.java.ColorSpaceFilter. Total: 5016 msec (100), avg = 50.16 msec/call
 * edu.mgupi.pass.filters.java.GrayScaleFilter. Total: 625 msec (100), avg = 6.25 msec/call
 * edu.mgupi.pass.filters.java.InvertFilter. Total: 2156 msec (100), avg = 21.56 msec/call
 * edu.mgupi.pass.filters.java.RescaleFilter. Total: 6516 msec (100), avg = 65.16 msec/call
 * edu.mgupi.pass.filters.java.SimpleSharpFilter. Total: 3078 msec (100), avg = 30.78 msec/call
 * edu.mgupi.pass.filters.java.SimpleSmoothFilter. Total: 3000 msec (100), avg = 30.0 msec/call
 * edu.mgupi.pass.filters.service.HistogramFilter. Total: 2719 msec (100), avg = 27.19 msec/call
 * edu.mgupi.pass.filters.service.ResizeFilter. Total: 4063 msec (100), avg = 40.63 msec/call
 * edu.mgupi.pass.filters.java.GrayScaleFilter CHAINSAW. Total: 390 msec (100), avg = 3.9 msec/call
 * edu.mgupi.pass.filters.java.GrayScaleFilter CHAINSAW EVERYIMAGE. Total: 3344 msec (100), avg = 33.44 msec/call
 * edu.mgupi.pass.filters.java.GrayScaleFilter EVERYIMAGE. Total: 3343 msec (100), avg = 33.43 msec/call
 * edu.mgupi.pass.filters.java.InvertFilter EVERYIMAGE. Total: 4390 msec (100), avg = 43.9 msec/call
 * edu.mgupi.pass.filters.java.SimpleSharpFilter EVERYIMAGE. Total: 6218 msec (100), avg = 62.18 msec/call
 * </pre>
 * 
 * @author raidan
 * 
 */
public class FilterPerformanceTest {

	TestSourceImpl source;

	@Before
	public void setUp() throws Exception {
		source = new TestSourceImpl();
		source.init();
	}

	@After
	public void tearDown() throws Exception {
		if (source != null) {
			source.done();
			source = null;
		}
	}

	private int CNT = 100;

	// private Collection<Secundomer> results = new ArrayList<Secundomer>();

	private void testImpl(IFilter filter) throws IOException, FilterException {
		this.testImpl(filter, false);
	}

	BufferedImage image2;

	private void testImpl(IFilter filter, boolean readEveryImage) throws IOException, FilterException {
		BufferedImage image = source.getSingleSource().getSourceImage();

		Secundomer sec = SecundomerList.registerSecundomer(filter.getClass().getName()
				+ (readEveryImage ? " EVERYIMAGE" : ""));
		for (int i = 0; i < CNT; i++) {
			sec.start();
			if (readEveryImage) {
				image = source.getSingleSource().getSourceImage();
			}
			image2 = filter.convert(image);
			sec.stop();
		}
	}

	public void testColorSpaceFilter() throws IOException, FilterException {
		ColorSpaceFilter filter = new ColorSpaceFilter();
		filter.getCOLOR_MODE().setValue(ColorSpace.CS_GRAY);
		this.testImpl(filter);
	}

	public void testGrayScaleFilter() throws IOException, FilterException {
		GrayScaleFilter filter = new GrayScaleFilter();
		this.testImpl(filter);
	}

	public void testGrayScaleFilterEveryImage() throws IOException, FilterException {
		GrayScaleFilter filter = new GrayScaleFilter();
		this.testImpl(filter, true);
	}

	public void testInvertFilter() throws IOException, FilterException {
		InvertFilter filter = new InvertFilter();
		this.testImpl(filter);
	}

	public void testInvertFilterEveryImage() throws IOException, FilterException {
		InvertFilter filter = new InvertFilter();
		this.testImpl(filter, true);
	}

	public void testRescaleFilter() throws IOException, FilterException {
		RescaleFilter filter = new RescaleFilter();
		filter.getBRIGHTNESS().setValue(40);
		this.testImpl(filter);
	}

	public void testSimpleSharpFilter() throws IOException, FilterException {
		SimpleSharpFilter filter = new SimpleSharpFilter();
		this.testImpl(filter);
	}

	public void testSimpleSharpFilterEveryImage() throws IOException, FilterException {
		SimpleSharpFilter filter = new SimpleSharpFilter();
		this.testImpl(filter, true);
	}

	public void testSimpleSmoothFilter() throws IOException, FilterException {
		SimpleSmoothFilter filter = new SimpleSmoothFilter();
		this.testImpl(filter);
	}

	public void testHistogramFilter() throws IOException, FilterException {
		HistogramFilter filter = new HistogramFilter();
		this.testImpl(filter);
	}

	public void testResizeFilter() throws IOException, FilterException {
		ResizeFilter filter = new ResizeFilter();
		filter.getWIDTH().setValue(1024);
		filter.getHEIGHT().setValue(768);

		this.testImpl(filter);
	}

	public void testGrayScaleFilterChainsaw(boolean readEveryImage) throws IOException, FilterException {
		FilterChainsaw saw = new FilterChainsaw();

		GrayScaleFilter filter = new GrayScaleFilter();
		BufferedImage image = source.getSingleSource().getSourceImage();

		saw.appendFilter(filter);

		if (!readEveryImage) {
			saw.attachImage(image);
		}

		Secundomer sec = SecundomerList.registerSecundomer(filter.getClass().getName() + " CHAINSAW"
				+ (readEveryImage ? " EVERYIMAGE" : ""));
		for (int i = 0; i < CNT; i++) {
			sec.start();
			if (readEveryImage) {
				image = source.getSingleSource().getSourceImage();
				saw.attachImage(image);
			}
			image2 = saw.filterSaw();
			if (readEveryImage) {
				saw.detachImage();
			}
			sec.stop();
		}

		if (!readEveryImage) {
			saw.detachImage();
		}
	}

	@Test
	public void testAll() throws IOException, FilterException {
		this.testColorSpaceFilter();
		this.testGrayScaleFilter();
		this.testInvertFilter();
		this.testRescaleFilter();
		this.testSimpleSharpFilter();
		this.testSimpleSmoothFilter();
		this.testHistogramFilter();
		this.testResizeFilter();
		this.testGrayScaleFilterChainsaw(false);
		this.testGrayScaleFilterChainsaw(true);
		this.testGrayScaleFilterEveryImage();
		this.testInvertFilterEveryImage();
		this.testSimpleSharpFilterEveryImage();

		SecundomerList.printToOutput(System.out);
	}
}
