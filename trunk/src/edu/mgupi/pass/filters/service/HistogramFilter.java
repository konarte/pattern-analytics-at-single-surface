package edu.mgupi.pass.filters.service;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.filters.Param;
import edu.mgupi.pass.filters.ParamException;

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
		return "Гистограмма";
	}

	public Collection<Param> getParams() {
		logger.debug("HistogramFilter.getParams. Nothing to return.");
		return null;
	}

	public String toString() {
		return this.getName();
	}

	private static int HEIGHT = 256;

	public BufferedImage convert(BufferedImage source) throws ParamException {

		if (source == null) {
			throw new IllegalArgumentException("Internal error: image is null.");
		}

		logger.debug("HistogramFilter.convert, building histogram");

		BufferedImage dest = new BufferedImage(256, HEIGHT, BufferedImage.TYPE_INT_RGB);

		int height = source.getHeight();
		int width = source.getWidth();

		boolean gray = source.getType() == BufferedImage.TYPE_BYTE_GRAY;
		int[][] hist = new int[3][256];

		// Loading distribution of dots
		Raster raster = source.getRaster();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				hist[0][raster.getSample(i, j, 0)]++;
				if (!gray) {
					hist[1][raster.getSample(i, j, 1)]++;
					hist[2][raster.getSample(i, j, 2)]++;
				}
			}
		}
		// Normalization
		for (int i = 0; i < hist[0].length; i++) {
			hist[0][i / (width * height)]++;
			if (!gray) {
				hist[1][i / (width * height)]++;
				hist[2][i / (width * height)]++;
			}
		}

		// Searching max
		int max = 0;
		for (int k = 0; k < (gray ? 1 : 3); k++) {
			for (int value : hist[k]) {
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

		gc.setColor(gray ? Color.BLACK : Color.RED);
		for (int i = 0; i < hist[0].length; i++) {
			gc.drawLine(i, HEIGHT, i, (int) (HEIGHT - (useDiv ? hist[0][i] / div : hist[0][i])));
		}
		if (!gray) {
			gc.setColor(Color.GREEN);
			for (int i = 0; i < hist[1].length; i++) {
				gc.drawLine(i, HEIGHT, i, (int) (HEIGHT - (useDiv ? hist[1][i] / div : hist[1][i])));
			}

			gc.setColor(Color.BLUE);
			for (int i = 0; i < hist[2].length; i++) {
				gc.drawLine(i, HEIGHT, i, (int) (HEIGHT - (useDiv ? hist[2][i] / div : hist[2][i])));
			}
		}

		gc.dispose();

		return dest;
	}

	public void onAttachToImage(BufferedImage source) {
		logger.trace("HistogramFilter.onAttach");
		// do nothing
	}

	public void onDetachFromImage(BufferedImage source) {
		logger.trace("HistogramFilter.onDetach");
		// do nothing
	}
}
