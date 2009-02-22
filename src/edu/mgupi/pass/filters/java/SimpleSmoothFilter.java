package edu.mgupi.pass.filters.java;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.filters.NoSuchParamException;
import edu.mgupi.pass.filters.Param;

public class SimpleSmoothFilter implements IFilter {

	private final static Logger logger = LoggerFactory.getLogger(SimpleSharpFilter.class);

	public String getName() {
		return "Размытие картинки";
	}

	public Collection<Param> getParams() {
		logger.debug("SimpleSmoothFilter.getParams. Nothing to return.");
		return null;
	}

	public void onAttachToImage(BufferedImage source) {
		//
	}

	public void done() {
		logger.debug("SimpleSmoothFilter.done");
	}

	private float[] elements = {
	//
			1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f, //
			1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f, //
			1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f };
	private ConvolveOp op = new ConvolveOp(new Kernel(3, 3, elements));

	public BufferedImage convert(BufferedImage source, BufferedImage dest, Map<String, Object> params)
			throws NoSuchParamException {
		if (source == null) {
			throw new IllegalArgumentException("Internal error: image is null.");
		}

		logger.debug("SimpleSmoothFilter.convert, smoothing image");

		if (dest == null) {
			dest = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
		}

		return op.filter(source, dest);
	}
}
