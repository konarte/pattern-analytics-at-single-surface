package edu.mgupi.pass.filters.java;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.filters.Param;
import edu.mgupi.pass.filters.ParamException;

public class SimpleSharpFilter implements IFilter {

	private final static Logger logger = LoggerFactory.getLogger(SimpleSharpFilter.class);

	public String getName() {
		return "Увеличение резкости";
	}

	public Collection<Param> getParams() {
		logger.debug("SimpleSharpFilter.getParams. Nothing to return.");
		return null;
	}

	public String toString() {
		return this.getName();
	}

	private float[] elements = {
	//
			0.0f, -1.0f, 0.0f, //
			-1.0f, 5.f, -1.0f, //
			0.0f, -1.0f, 0.0f };
	private ConvolveOp op = new ConvolveOp(new Kernel(3, 3, elements));

	public BufferedImage convert(BufferedImage source) throws ParamException {
		if (source == null) {
			throw new IllegalArgumentException("Internal error: image is null.");
		}

		logger.debug("SimpleSharpFilter.convert, sharping image");

		BufferedImage dest = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());

		return op.filter(source, dest);
	}

	public void onAttachToImage(BufferedImage source) {
		logger.trace("SimpleSharpFilter.onAttach");
		// do nothing
	}

	public void onDetachFromImage(BufferedImage source) {
		logger.trace("SimpleSharpFilter.onDetach");
		// do nothing
	}
}
