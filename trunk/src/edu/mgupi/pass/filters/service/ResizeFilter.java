package edu.mgupi.pass.filters.service;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.filters.FilterException;
import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.filters.IllegalParameterValueException;
import edu.mgupi.pass.filters.Param;
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
		params = new ArrayList<Param>(3);
		params.add(WIDTH);
		params.add(HEIGHT);
		params.add(INTERPOLATION_METHOD);
		params = Collections.unmodifiableCollection(params);
	}

	public String getName() {
		return "Изменение размера";
	}

	public Collection<Param> getParams() {
		return params;
	}

	public String toString() {
		return this.getName() + " (" + WIDTH.getValue() + "x" + HEIGHT.getValue() + " by "
				+ INTERPOLATION_METHOD.getValue() + ")";
	}

	public BufferedImage convert(BufferedImage source) throws FilterException {

		if (source == null) {
			throw new IllegalArgumentException("Internal error: image is null.");
		}

		Dimension thumb = new Dimension((Integer) WIDTH.getValue(), (Integer) HEIGHT.getValue());
		Object interpolationMethod = INTERPOLATION_METHOD.getValue();

		if (!RenderingHints.KEY_INTERPOLATION.isCompatibleValue(interpolationMethod)) {
			throw new IllegalParameterValueException("Unable to set value " + interpolationMethod + " from parameter "
					+ INTERPOLATION_METHOD.getName() + " to 'RenderingHints.KEY_INTERPOLATION'.");
		}

		logger.debug("Resizing image to {}x{}, method {}", new Object[] { thumb.width, thumb.height,
				interpolationMethod });

		Dimension newSize = calcThumbSize(source, thumb);

		// draw original image to thumbnail image object and
		// scale it to the new size on-the-fly
		BufferedImage dest = new BufferedImage(newSize.width, newSize.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = dest.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, interpolationMethod);
		graphics2D.drawImage(source, 0, 0, newSize.width, newSize.height, null);

		graphics2D.dispose();

		return dest;
	}

	/**
	 * 
	 * 
	 * Based of thumb maker by Marco Schmidt
	 * 
	 * 
	 * @param source
	 * @param thumb
	 * @return resizedI
	 */
	public static Dimension calcThumbSize(BufferedImage source, Dimension thumb) {
		int imageWidth = source.getWidth();
		int imageHeight = source.getHeight();

		double thumbRatio = thumb.getWidth() / thumb.getHeight();
		double imageRatio = (double) imageWidth / (double) imageHeight;

		if (thumbRatio < imageRatio) {
			thumb.height = (int) (thumb.width / imageRatio);
		} else {
			thumb.width = (int) (thumb.height * imageRatio);
		}
		return thumb;
	}

	//
	// public void onAttachToImage(BufferedImage source) {
	// logger.trace("Sets new default values {}x{}", source.getWidth(),
	// source.getHeight());
	// WIDTH.setDefault_(source.getWidth());
	// HEIGHT.setDefault_(source.getHeight());
	// // do nothing
	// }
	//
	// public void onDetachFromImage(BufferedImage source) {
	// logger.trace("No action on detach");
	// // do nothing
	// }

	public Param getWIDTH() {
		return WIDTH;
	}

	public Param getHEIGHT() {
		return HEIGHT;
	}

	public Param getINTERPOLATION_METHOD() {
		return INTERPOLATION_METHOD;
	}

}
