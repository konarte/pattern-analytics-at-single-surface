package edu.mgupi.pass.filters;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

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

/**
 * Intel Core 2 Duo E6750 (2.66 GHz), image 425x640, internal Eclipse 3.4 JUnit
 * 4 runner, Java 1.6.0_12
 * 
 * <pre>
 * == RESULT ==
 * edu.mgupi.pass.filters.java.ColorSpaceFilter Total: 4454 msec (100), avg = 44.54 msec/call
 * edu.mgupi.pass.filters.java.GrayScaleFilter Total: 219 msec (100), avg = 2.19 msec/call
 * edu.mgupi.pass.filters.java.InvertFilter Total: 812 msec (100), avg = 8.12 msec/call
 * edu.mgupi.pass.filters.java.RescaleFilter Total: 4078 msec (100), avg = 40.78 msec/call
 * edu.mgupi.pass.filters.java.SimpleSharpFilter Total: 1640 msec (100), avg = 16.4 msec/call
 * edu.mgupi.pass.filters.java.SimpleSmoothFilter Total: 1641 msec (100), avg = 16.41 msec/call
 * edu.mgupi.pass.filters.service.HistogramFilter Total: 1265 msec (100), avg = 12.65 msec/call
 * edu.mgupi.pass.filters.service.ResizeFilter Total: 3204 msec (100), avg = 32.04 msec/call
 * edu.mgupi.pass.filters.java.GrayScaleFilter CHAINSAW Total: 156 msec (100), avg = 1.56 msec/call
 * edu.mgupi.pass.filters.java.GrayScaleFilter CHAINSAW EVERYIMAGE Total: 1859 msec (100), avg = 18.59 msec/call
 * edu.mgupi.pass.filters.java.GrayScaleFilter EVERYIMAGE Total: 1859 msec (100), avg = 18.59 msec/call
 * edu.mgupi.pass.filters.java.InvertFilter EVERYIMAGE Total: 2219 msec (100), avg = 22.19 msec/call
 * edu.mgupi.pass.filters.java.SimpleSharpFilter EVERYIMAGE Total: 3062 msec (100), avg = 30.62 msec/call *
 * </pre>
 * 
 * 
 * Intel Core 2 Duo E6750 (2.66 GHz), image 800x721, internal Eclipse 3.4 JUnit
 * 4 runner, Java 1.6.0_12
 * 
 * <pre>
 * == RESULT ==
 * edu.mgupi.pass.filters.java.ColorSpaceFilter Total: 5000 msec (100), avg = 50.0 msec/call
 * edu.mgupi.pass.filters.java.GrayScaleFilter Total: 641 msec (100), avg = 6.41 msec/call
 * edu.mgupi.pass.filters.java.InvertFilter Total: 2140 msec (100), avg = 21.4 msec/call
 * edu.mgupi.pass.filters.java.RescaleFilter Total: 6672 msec (100), avg = 66.72 msec/call
 * edu.mgupi.pass.filters.java.SimpleSharpFilter Total: 3047 msec (100), avg = 30.47 msec/call
 * edu.mgupi.pass.filters.java.SimpleSmoothFilter Total: 2985 msec (100), avg = 29.85 msec/call
 * edu.mgupi.pass.filters.service.HistogramFilter Total: 2719 msec (100), avg = 27.19 msec/call
 * edu.mgupi.pass.filters.service.ResizeFilter Total: 4062 msec (100), avg = 40.62 msec/call
 * edu.mgupi.pass.filters.java.GrayScaleFilter CHAINSAW Total: 375 msec (100), avg = 3.75 msec/call
 * edu.mgupi.pass.filters.java.GrayScaleFilter CHAINSAW EVERYIMAGE Total: 3359 msec (100), avg = 33.59 msec/call
 * edu.mgupi.pass.filters.java.GrayScaleFilter EVERYIMAGE Total: 3359 msec (100), avg = 33.59 msec/call
 * edu.mgupi.pass.filters.java.InvertFilter EVERYIMAGE Total: 4360 msec (100), avg = 43.6 msec/call
 * edu.mgupi.pass.filters.java.SimpleSharpFilter EVERYIMAGE Total: 6281 msec (100), avg = 62.81 msec/call
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

	private Collection<Secundomer> results = new ArrayList<Secundomer>();

	private void testImpl(IFilter filter) throws IOException, FilterException {
		this.testImpl(filter, false);
	}

	BufferedImage image2;

	private void testImpl(IFilter filter, boolean readEveryImage) throws IOException, FilterException {
		BufferedImage image = source.getSingleSource().getSourceImage();

		Secundomer sec = new Secundomer(filter.getClass().getName() + (readEveryImage ? " EVERYIMAGE" : ""));
		for (int i = 0; i < CNT; i++) {
			sec.start();
			if (readEveryImage) {
				image = source.getSingleSource().getSourceImage();
			}
			image2 = filter.convert(image);
			sec.stop();
		}

		results.add(sec);
	}

	public void testColorSpaceFilter() throws IOException, FilterException {
		ColorSpaceFilter filter = new ColorSpaceFilter();
		ParamHelper.getParameter("ColorMode", filter).setValue(ColorSpace.CS_GRAY);
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
		ParamHelper.getParameter("Brightness", filter).setValue(40);
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
		ParamHelper.getParameter("Width", filter).setValue(1024);
		ParamHelper.getParameter("Height", filter).setValue(768);

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

		Secundomer sec = new Secundomer(filter.getClass().getName() + " CHAINSAW"
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

		results.add(sec);
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

		System.out.println(" == RESULT ==");
		for (Secundomer sec : this.results) {
			System.out.println(sec);
		}
	}
}
