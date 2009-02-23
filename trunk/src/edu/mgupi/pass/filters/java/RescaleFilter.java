package edu.mgupi.pass.filters.java;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
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

public class RescaleFilter implements IFilter {

	private final static Logger logger = LoggerFactory.getLogger(RescaleFilter.class);

	private Collection<Param> params;

	public RescaleFilter() {
		params = new ArrayList<Param>(2);
		params.add(new Param("Brightness", "Яркость", TYPES.INT, 0, -255, 255));
		params.add(new Param("Contrast", "Контраст", TYPES.INT, 100, 0, 255));

	}

	public String getName() {
		return "Изменение яркости и контраста";
	}

	public Collection<Param> getParams() {
		logger.debug("RescaleFilter.getParams return {} items", params.size());
		return params;
	}

	public void done() {
		logger.debug("RescaleFilter.done");
	}

	public BufferedImage convert(BufferedImage source, BufferedImage dest, Map<String, Object> params)
			throws NoSuchParamException {
		if (source == null) {
			throw new IllegalArgumentException("Internal error: image is null.");
		}

		float brightness = (float) (Integer) ParamHelper.getParameterM("Brightness", params);
		float contrast = ((float) (Integer) ParamHelper.getParameterM("Contrast", params)) / 100.f;

		logger.debug("RescaleFilter.convert, changing constrast index to {} and brightness value to {}", contrast,
				brightness);

		// TODO Поисследовать, даст ли прирост скорости кэширование
		// RescaleOp-а

		if (dest == null) {
			dest = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_RGB);
		}
		RescaleOp rescale = new RescaleOp(contrast, brightness, null);
		return rescale.filter(source, dest);

		// Kernel kernel = new Kernel(1, 1, new float[] { brightness });
		// ConvolveOp op = new ConvolveOp(kernel);
		// temp = op.filter(source, temp);

		// Sharp
		// float[] elements = { 0.0f, -1.0f, 0.0f, -1.0f, 5.f, -1.0f, 0.0f,
		// -1.0f, 0.0f };
		//
		// Kernel kernel = new Kernel(3, 3, elements);
		// ConvolveOp op = new ConvolveOp(kernel);
		//
		// return op.filter(source, dest);

		// invert
		// byte reScale[] = new byte[256];
		// for (int j = 0; j < 256; j++) {
		// reScale[j] = (byte) (256 - j);
		// }
		// ByteLookupTable blut = new ByteLookupTable(0, reScale);
		// LookupOp lop = new LookupOp(blut, null);
		//		
		// return lop.filter(source, dest);

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
