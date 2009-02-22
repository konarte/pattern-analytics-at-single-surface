package edu.mgupi.pass.filters.java;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.filters.IllegalParameterValueException;
import edu.mgupi.pass.filters.Param;
import edu.mgupi.pass.filters.ParamException;
import edu.mgupi.pass.filters.ParamHelper;

public class ColorSpaceFilter implements IFilter {

	private final static Logger logger = LoggerFactory.getLogger(ColorSpaceFilter.class);

	public String getName() {
		return "Изменение цветовой модели";
	}

	private Collection<Param> params;

	// ColorSpace.CS_sRGB,
	// "sRGB",
	private Param COLOR_MODE = new Param("ColorMode", "Режим цветности", ColorSpace.CS_GRAY,//
			new Integer[] { ColorSpace.CS_GRAY, ColorSpace.CS_LINEAR_RGB, ColorSpace.CS_CIEXYZ, ColorSpace.CS_PYCC }, //
			new String[] { "Gray scale", "linear RGB", "CIEXYZ", "Photo YCC" });

	public ColorSpaceFilter() {
		params = new ArrayList<Param>(1);
		params.add(COLOR_MODE);
	}

	public Collection<Param> getParams() {
		logger.debug("ColorSpaceFilter.getParams return {} items", params.size());
		return params;
	}

	public void onAttachToImage(BufferedImage source) {
		//
	}

	public void done() {
		// do nothing
		logger.debug("ColorSpaceFilter.done");

	}

	public BufferedImage convert(BufferedImage source, BufferedImage dest, Map<String, Object> params)
			throws ParamException {
		if (source == null) {
			throw new IllegalArgumentException("Internal error: image is null.");
		}
		ColorSpace sourceColorSpace = source.getColorModel().getColorSpace();

		int destMode = (Integer) ParamHelper.getParameter(COLOR_MODE.getName(), params);
		boolean found = false;
		for (int allow : (Integer[]) COLOR_MODE.getAllowed_values()) {
			if (destMode == allow) {
				found = true;
				break;
			}
		}

		if (!found) {
			throw new IllegalParameterValueException("Unable to use ColorSpaceFilter, because parameter ColorMode has "
					+ "invalid value " + destMode + ".");
		}

		ColorSpace destColorSpace = ColorSpace.getInstance(destMode);

		logger.debug("ColorSpaceFilter.convert, converting image from color space {} to {}.", sourceColorSpace
				.getType(), destColorSpace.getType());

		// TODO Поисследовать, даст ли прирост скорости кэширование
		// ColorConvertOp-а
		return new ColorConvertOp(destColorSpace, null).filter(source, dest);
	}
}
