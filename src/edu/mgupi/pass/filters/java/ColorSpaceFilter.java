package edu.mgupi.pass.filters.java;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.filters.FilterException;
import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.filters.Param;
import edu.mgupi.pass.filters.Param.ParamType;

public class ColorSpaceFilter implements IFilter {

	private final static Logger logger = LoggerFactory.getLogger(ColorSpaceFilter.class);

	public String getName() {
		return "Смена цветовой модели";
	}

	private Collection<Param> params;

	// ColorSpace.CS_sRGB,
	// "sRGB",

	// ColorSpace.CS_PYCC
	// "Photo YCC"
	private Param COLOR_MODE = new Param("ColorMode", "Режим цветности", ParamType.INT,
			ColorSpace.CS_GRAY,//
			new Integer[] { ColorSpace.CS_GRAY, ColorSpace.CS_LINEAR_RGB, ColorSpace.CS_CIEXYZ }, //
			new String[] { "Gray scale", "linear RGB", "CIEXYZ" });

	public ColorSpaceFilter() {
		params = new ArrayList<Param>(1);
		params.add(COLOR_MODE);
		params = Collections.unmodifiableCollection(params);
	}

	public Collection<Param> getParams() {
		return params;
	}

	public String toString() {
		return this.getName() + " (" + COLOR_MODE.getValue() + ")";
	}

	public BufferedImage convert(BufferedImage source) throws FilterException {
		if (source == null) {
			throw new IllegalArgumentException("Internal error: image is null.");
		}
		int destMode = (Integer) COLOR_MODE.getValue();

		ColorSpace sourceColorSpace = source.getColorModel().getColorSpace();
		ColorSpace destColorSpace = ColorSpace.getInstance(destMode);

		logger.debug("Converting image from color space {} to {}.", sourceColorSpace.getType(),
				destColorSpace.getType());

		/**
		 * No, don't give a shit :)
		 */
		return new ColorConvertOp(destColorSpace, null).filter(source, null);
	}

	public Param getCOLOR_MODE() {
		return COLOR_MODE;
	}

}
