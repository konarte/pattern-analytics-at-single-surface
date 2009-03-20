package edu.mgupi.pass.filters.service;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.filters.FilterException;
import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.filters.Param;

/**
 * Filter for create histograms. Support color and grey scale images.
 * 
 * Histogram always 256x256 px size.
 * 
 * @author raidan
 * 
 */
public class HistogramFilter implements IFilter {

	private final static Logger logger = LoggerFactory.getLogger(HistogramFilter.class);

	public String getName() {
		return "Гистограмма 256x256";
	}

	public Collection<Param> getParams() {
		return null;
	}

	public String toString() {
		return this.getName();
	}

	private static int HEIGHT = 256;

	public BufferedImage convert(BufferedImage source) throws FilterException {

		if (source == null) {
			throw new IllegalArgumentException("Internal error: image is null.");
		}

		logger.debug("Building histogram 256x{}", HEIGHT);

		BufferedImage dest = new BufferedImage(256, HEIGHT, BufferedImage.TYPE_INT_RGB);

		int height = source.getHeight();
		int width = source.getWidth();

		final int bytesPerPixes = source.getColorModel().getPixelSize() / 8;
		// boolean gray = source.getType() == BufferedImage.TYPE_BYTE_GRAY;
		histogram = new int[bytesPerPixes][256];

		// Loading distribution of dots
		Raster raster = source.getRaster();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				for (int z = 0; z < bytesPerPixes; z++) {
					histogram[z][raster.getSample(i, j, z)]++;
					histogram[z][raster.getSample(i, j, z)]++;
					histogram[z][raster.getSample(i, j, z)]++;
				}
			}
		}
		// Normalization
		for (int i = 0; i < histogram[0].length; i++) {
			for (int z = 0; z < bytesPerPixes; z++) {
				histogram[z][i / (width * height)]++;
				histogram[z][i / (width * height)]++;
				histogram[z][i / (width * height)]++;
			}

		}

		// Searching max
		int max = 0;
		for (int k = 0; k < bytesPerPixes; k++) {
			for (int value : histogram[k]) {
				if (value > max) {
					max = value;
				}
			}
		}

		// Coeff for compress
		boolean useDiv = false;
		float div = 1.0f;
		if (max >= HEIGHT) {
			useDiv = true;
			div = (float) max / HEIGHT;
		}

		// Drawning result
		Graphics gc = dest.getGraphics();

		gc.setColor(Color.WHITE);
		gc.fillRect(0, 0, 256, HEIGHT);

		gc.setColor(bytesPerPixes == 1 ? Color.BLACK : Color.RED);
		for (int i = 0; i < histogram[0].length; i++) {
			gc.drawLine(i, HEIGHT, i, (int) (HEIGHT - (useDiv ? histogram[0][i] / div : histogram[0][i])));
		}
		if (bytesPerPixes > 1) {
			gc.setColor(Color.GREEN);
			for (int i = 0; i < histogram[1].length; i++) {
				gc.drawLine(i, HEIGHT, i, (int) (HEIGHT - (useDiv ? histogram[1][i] / div : histogram[1][i])));
			}

			gc.setColor(Color.BLUE);
			for (int i = 0; i < histogram[2].length; i++) {
				gc.drawLine(i, HEIGHT, i, (int) (HEIGHT - (useDiv ? histogram[2][i] / div : histogram[2][i])));
			}
		}

		gc.dispose();

		return dest;
	}

	private int[][] histogram;

	public int[] getLastHistogramChannel() {
		if (histogram == null) {
			return null;
		}
		return histogram[0];
	}

}
