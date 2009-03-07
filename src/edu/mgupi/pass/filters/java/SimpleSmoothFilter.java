package edu.mgupi.pass.filters.java;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.filters.FilterException;
import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.filters.Param;

public class SimpleSmoothFilter implements IFilter {

	private final static Logger logger = LoggerFactory.getLogger(SimpleSharpFilter.class);

	public String getName() {
		return "Размытие картинки";
	}

	public Collection<Param> getParams() {
		return null;
	}

	public String toString() {
		return this.getName();
	}

	private float[] elements = {
	//
			1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f, //
			1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f, //
			1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f };
	private ConvolveOp op = new ConvolveOp(new Kernel(3, 3, elements));

	public BufferedImage convert(BufferedImage source) throws FilterException {
		if (source == null) {
			throw new IllegalArgumentException("Internal error: image is null.");
		}

		logger.debug("Smoothing image");

		BufferedImage dest = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
		return op.filter(source, dest);
	}

}
