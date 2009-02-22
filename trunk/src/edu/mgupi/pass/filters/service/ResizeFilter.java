package edu.mgupi.pass.filters.service;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.filters.NoSuchParamException;
import edu.mgupi.pass.filters.Param;
import edu.mgupi.pass.filters.ParamHelper;
import edu.mgupi.pass.filters.Param.TYPES;

public class ResizeFilter implements IFilter {

	private final static Logger logger = LoggerFactory.getLogger(ResizeFilter.class);

	private Collection<Param> params;

	private Param WIDTH = new Param("Width", "Ширина", TYPES.PX, 0);
	private Param HEIGHT = new Param("Height", "Высота", TYPES.PX, 0);

	public ResizeFilter() {
		params = new ArrayList<Param>(2);
		params.add(WIDTH);
		params.add(HEIGHT);

	}

	public String getName() {
		return "Изменение размера";
	}

	public Collection<Param> getParams() {
		logger.debug("ResizeFilter.getParams. Nothing to return.");
		return null;
	}

	public void onAttachToImage(BufferedImage source) {
		WIDTH.setDefault_(source.getWidth());
		HEIGHT.setDefault_(source.getHeight());
	}

	public void done() {
		logger.debug("ResizeFilter.done");
	}

	public BufferedImage convert(BufferedImage source, BufferedImage dest, Map<String, Object> params)
			throws NoSuchParamException {

		if (source == null) {
			throw new IllegalArgumentException("Internal error: image is null.");
		}

		int thumbWidth = (Integer) ParamHelper.getParameter(WIDTH.getName(), params);
		int thumbHeight = (Integer) ParamHelper.getParameter(HEIGHT.getName(), params);

		logger.debug("ResizeFilter.convert, resizing image to {}x{}", thumbWidth, thumbHeight);

		double thumbRatio = (double) thumbWidth / (double) thumbHeight;
		int imageWidth = source.getWidth();
		int imageHeight = source.getHeight();
		double imageRatio = (double) imageWidth / (double) imageHeight;
		if (thumbRatio < imageRatio) {
			thumbHeight = (int) (thumbWidth / imageRatio);
		} else {
			thumbWidth = (int) (thumbHeight * imageRatio);
		}
		// draw original image to thumbnail image object and
		// scale it to the new size on-the-fly
		dest = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = dest.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		graphics2D.drawImage(source, 0, 0, thumbWidth, thumbHeight, null);

		return dest;
	}
}
