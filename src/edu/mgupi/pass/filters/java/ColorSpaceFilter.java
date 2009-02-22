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
import edu.mgupi.pass.filters.NoSuchParamException;
import edu.mgupi.pass.filters.Param;
import edu.mgupi.pass.filters.ParamHelper;

public class ColorSpaceFilter implements IFilter {

	private final static Logger logger = LoggerFactory.getLogger(ColorSpaceFilter.class);

	public String getName() {
		return "Изменение цветовой модели";
	}

	public Collection<Param> getParams() {

		logger.debug("ColorSpaceFilter.getParams");

		Collection<Param> params = new ArrayList<Param>(1);
		params.add(new Param("ColorMode", "Режим цветности", ColorSpace.CS_GRAY,
		//
				new Integer[] { ColorSpace.CS_GRAY, ColorSpace.CS_sRGB, ColorSpace.CS_LINEAR_RGB, ColorSpace.CS_CIEXYZ,
						ColorSpace.CS_PYCC },
				//
				new String[] { "Gray scale", "sRGB", "linear RGB", "CIEXYZ", "Photo YCC" }));

		logger.debug("Return {} param(s)", params.size());
		return params;
	}

	public void done() {
		// do nothing
		logger.debug("ColorSpaceFilter.done");

	}

	public BufferedImage convert(BufferedImage source, BufferedImage dest, Map<String, Object> params)
			throws NoSuchParamException {
		if (source == null) {
			throw new IllegalArgumentException("Internal error: image is null.");
		}
		ColorSpace sourceColorSpace = source.getColorModel().getColorSpace();
		ColorSpace destColorSpace = ColorSpace.getInstance((Integer) ParamHelper.getParameter("ColorMode", params));

		logger.debug("ColorSpaceFilter.convert, converting image from color space {} to {}.", sourceColorSpace
				.getType(), destColorSpace.getType());

		// TODO Поисследовать, даст ли прирост скорости кэширование
		// ColorConvertOp-а
		return new ColorConvertOp(destColorSpace, null).filter(source, dest);
	}
}
