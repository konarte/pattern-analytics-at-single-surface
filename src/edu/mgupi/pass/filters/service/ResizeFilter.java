package edu.mgupi.pass.filters.service;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.filters.Param;
import edu.mgupi.pass.filters.ParamException;
import edu.mgupi.pass.filters.Param.TYPES;

public class ResizeFilter implements IFilter {

	private final static Logger logger = LoggerFactory.getLogger(ResizeFilter.class);

	private Collection<Param> params;

	private Param WIDTH = new Param("Width", "Ширина", TYPES.INT, 0);
	private Param HEIGHT = new Param("Height", "Высота", TYPES.INT, 0);
	private Param INTERPOLATION_METHOD = new Param("Method", "Метод конвертирования",
			RenderingHints.VALUE_INTERPOLATION_BILINEAR, new Object[] { RenderingHints.VALUE_INTERPOLATION_BICUBIC,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR },
			new String[] { "Bicubic (best)", "Bilinear (medium)", "Nearest point (worst)" });

	public ResizeFilter() {
		params = new ArrayList<Param>(2);
		params.add(WIDTH);
		params.add(HEIGHT);
		params.add(INTERPOLATION_METHOD);
	}

	public String getName() {
		return "Изменение размера";
	}

	public Collection<Param> getParams() {
		logger.debug("ResizeFilter.getParams return {} items", params.size());
		return params;
	}

	public String toString() {
		return this.getName() + " (" + WIDTH.getValue() + "x" + HEIGHT.getValue() + " by "
				+ INTERPOLATION_METHOD.getValue() + ")";
	}

	public BufferedImage convert(BufferedImage source) throws ParamException {

		if (source == null) {
			throw new IllegalArgumentException("Internal error: image is null.");
		}

		int thumbWidth = (Integer) WIDTH.getValue();
		int thumbHeight = (Integer) HEIGHT.getValue();
		Object interpolationMethod = INTERPOLATION_METHOD.getValue();

		logger.debug("ResizeFilter.convert, resizing image to {}x{}, method {}", new Object[] { thumbWidth,
				thumbHeight, interpolationMethod });

		/*
		 * Based of thumb maker by Marco Schmidt
		 */

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
		BufferedImage dest = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = dest.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, interpolationMethod);
		graphics2D.drawImage(source, 0, 0, thumbWidth, thumbHeight, null);

		graphics2D.dispose();

		return dest;
	}

	public void onAttachToImage(BufferedImage source) {
		logger.trace("ResizeFilter.onAttach. Set new default values {}x{}", source.getWidth(), source.getHeight());
		WIDTH.setDefault_(source.getWidth());
		HEIGHT.setDefault_(source.getHeight());
		// do nothing
	}

	public void onDetachFromImage(BufferedImage source) {
		logger.trace("ResizeFilter.onDetach");
		// do nothing
	}
}
