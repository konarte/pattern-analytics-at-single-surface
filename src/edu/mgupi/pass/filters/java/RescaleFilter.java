package edu.mgupi.pass.filters.java;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.filters.NoSuchParamException;
import edu.mgupi.pass.filters.Param;
import edu.mgupi.pass.filters.Param.TYPES;

public class RescaleFilter implements IFilter {

	private final static Logger logger = LoggerFactory.getLogger(RescaleFilter.class);

	private Collection<Param> params;
	private Param BRIGHTNESS = new Param("Brightness", "Яркость", TYPES.INT, 0, -255, 255);
	private Param CONTRAST = new Param("Contrast", "Контраст", TYPES.INT, 100, 0, 255);

	public RescaleFilter() {
		params = new ArrayList<Param>(2);
		params.add(BRIGHTNESS);
		params.add(CONTRAST);

	}

	public String getName() {
		return "Изменение яркости и контраста";
	}

	public Collection<Param> getParams() {
		logger.debug("RescaleFilter.getParams return {} items", params.size());
		return params;
	}

	public String toString() {
		return this.getName() + " (B " + BRIGHTNESS.getValue() + ", C " + CONTRAST.getValue() + ")";
	}

	public BufferedImage convert(BufferedImage source) throws NoSuchParamException {
		if (source == null) {
			throw new IllegalArgumentException("Internal error: image is null.");
		}

		float brightness = (float) (Integer) BRIGHTNESS.getValue();
		float contrast = ((float) (Integer) CONTRAST.getValue()) / 100.f;

		logger.debug("RescaleFilter.convert, changing constrast index to {} and brightness value to {}", contrast,
				brightness);

		// Warning!
		// Do not try to define dest as new BufferedImage(source.getWidth(),
		// source.getHeight(), source.getType());
		// Or Java 6 (1.6.0_12) will crash!

		BufferedImage dest = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_RGB);
		RescaleOp rescale = new RescaleOp(contrast, brightness, null);
		return rescale.filter(source, dest);
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
