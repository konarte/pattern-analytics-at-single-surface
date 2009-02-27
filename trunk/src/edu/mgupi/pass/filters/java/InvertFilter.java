package edu.mgupi.pass.filters.java;

import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.LookupOp;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.filters.Param;
import edu.mgupi.pass.filters.FilterException;

public class InvertFilter implements IFilter {
	private final static Logger logger = LoggerFactory.getLogger(InvertFilter.class);

	public String getName() {
		return "Инвертирование изображения";
	}

	public Collection<Param> getParams() {
		logger.debug("InvertFilter.getParams. Nothing to return.");
		return null;
	}

	public String toString() {
		return this.getName();
	}

	public BufferedImage convert(BufferedImage source) throws FilterException {
		if (source == null) {
			throw new IllegalArgumentException("Internal error: image is null.");
		}

		logger.debug("InvertFilter.convert, inverting image");

		// if (dest == null) {
		// dest = new BufferedImage(source.getWidth(), source.getHeight(),
		// source.getType());
		// }

		// invert
		byte reScale[] = new byte[256];
		for (int j = 0; j < 256; j++) {
			reScale[j] = (byte) (256 - j);
		}
		ByteLookupTable blut = new ByteLookupTable(0, reScale);
		LookupOp lop = new LookupOp(blut, null);

		return lop.filter(source, null);

	}

	public void onAttachToImage(BufferedImage source) {
		logger.trace("InvertFilter.onAttach");
		// do nothing
	}

	public void onDetachFromImage(BufferedImage source) {
		logger.trace("InvertFilter.onDetach");
		// do nothing
	}

}
