package edu.mgupi.pass.filters.java;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.filters.NoSuchParamException;
import edu.mgupi.pass.filters.Param;

public class GrayScaleFilter implements IFilter {
	private final static Logger logger = LoggerFactory.getLogger(GrayScaleFilter.class);

	public String getName() {
		return "Перевод в серый цвет";
	}

	public Collection<Param> getParams() {
		logger.debug("GrayScaleFilter.getParams. Nothing to return.");
		return null;
	}

	
	public String toString() {
		return this.getName();
	}

	public BufferedImage convert(BufferedImage source) throws NoSuchParamException {
		if (source == null) {
			throw new IllegalArgumentException("Internal error: image is null.");
		}

		logger.debug("GrayScaleFilter.convert, grayscaling image");

		BufferedImage dest = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D graphics2D = dest.createGraphics();
		graphics2D.drawImage(source, 0, 0, source.getWidth(), source.getHeight(), null);
		graphics2D.dispose();
		return dest;
	}

	public void onAttachToImage(BufferedImage source) {
		logger.trace("GrayScaleFilter.onAttach");
		// do nothing
	}

	public void onDetachFromImage(BufferedImage source) {
		logger.trace("GrayScaleFilter.onDetach");
		// do nothing
	}
}
