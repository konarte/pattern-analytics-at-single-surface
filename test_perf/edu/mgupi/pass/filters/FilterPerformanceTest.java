package edu.mgupi.pass.filters;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.Secundomer;
import edu.mgupi.pass.filters.java.ColorSpaceFilter;
import edu.mgupi.pass.filters.java.GrayScaleFilter;
import edu.mgupi.pass.filters.java.InvertFilter;
import edu.mgupi.pass.filters.java.RescaleFilter;
import edu.mgupi.pass.filters.java.SimpleSharpFilter;
import edu.mgupi.pass.filters.java.SimpleSmoothFilter;
import edu.mgupi.pass.filters.service.HistogramFilter;
import edu.mgupi.pass.filters.service.ResizeFilter;
import edu.mgupi.pass.sources.TestSourceImpl;

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

	private void testImpl(IFilter filter) throws IOException, ParamException {
		this.testImpl(filter, false);
	}

	BufferedImage image2;

	private void testImpl(IFilter filter, boolean readEveryImage) throws IOException, ParamException {
		BufferedImage image = source.getSingleSource().getImage();

		Secundomer sec = new Secundomer(filter.getClass().getName() + (readEveryImage ? " EVERYIMAGE" : ""));
		for (int i = 0; i < CNT; i++) {
			sec.start();
			if (readEveryImage) {
				image = source.getSingleSource().getImage();
			}
			image2 = filter.convert(image);
			sec.stop();
		}

		results.add(sec);
	}

	public void testColorSpaceFilter() throws IOException, ParamException {
		ColorSpaceFilter filter = new ColorSpaceFilter();
		ParamHelper.getParameterL("ColorMode", filter).setValue(ColorSpace.CS_GRAY);
		this.testImpl(filter);
	}

	public void testGrayScaleFilter() throws IOException, ParamException {
		GrayScaleFilter filter = new GrayScaleFilter();
		this.testImpl(filter);
	}

	public void testGrayScaleFilterEveryImage() throws IOException, ParamException {
		GrayScaleFilter filter = new GrayScaleFilter();
		this.testImpl(filter, true);
	}

	public void testInvertFilter() throws IOException, ParamException {
		InvertFilter filter = new InvertFilter();
		this.testImpl(filter);
	}

	public void testInvertFilterEveryImage() throws IOException, ParamException {
		InvertFilter filter = new InvertFilter();
		this.testImpl(filter, true);
	}

	public void testRescaleFilter() throws IOException, ParamException {
		RescaleFilter filter = new RescaleFilter();
		ParamHelper.getParameterL("Brightness", filter).setValue(40);
		this.testImpl(filter);
	}

	public void testSimpleSharpFilter() throws IOException, ParamException {
		SimpleSharpFilter filter = new SimpleSharpFilter();
		this.testImpl(filter);
	}

	public void testSimpleSharpFilterEveryImage() throws IOException, ParamException {
		SimpleSharpFilter filter = new SimpleSharpFilter();
		this.testImpl(filter, true);
	}

	public void testSimpleSmoothFilter() throws IOException, ParamException {
		SimpleSmoothFilter filter = new SimpleSmoothFilter();
		this.testImpl(filter);
	}

	public void testHistogramFilter() throws IOException, ParamException {
		HistogramFilter filter = new HistogramFilter();
		this.testImpl(filter);
	}

	public void testResizeFilter() throws IOException, ParamException {
		ResizeFilter filter = new ResizeFilter();
		ParamHelper.getParameterL("Width", filter).setValue(1024);
		ParamHelper.getParameterL("Height", filter).setValue(768);

		this.testImpl(filter);
	}

	public void testGrayScaleFilterChainsaw(boolean readEveryImage) throws IOException, ParamException {
		FilterChainsaw saw = new FilterChainsaw();

		GrayScaleFilter filter = new GrayScaleFilter();
		BufferedImage image = source.getSingleSource().getImage();

		saw.appendFilter(filter);

		if (!readEveryImage) {
			saw.attachImage(image);
		}

		Secundomer sec = new Secundomer(filter.getClass().getName() + " CHAINSAW"
				+ (readEveryImage ? " EVERYIMAGE" : ""));
		for (int i = 0; i < CNT; i++) {
			sec.start();
			if (readEveryImage) {
				image = source.getSingleSource().getImage();
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
	public void testAll() throws IOException, ParamException {
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
