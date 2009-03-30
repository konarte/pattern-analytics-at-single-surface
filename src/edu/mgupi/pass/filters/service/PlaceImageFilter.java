package edu.mgupi.pass.filters.service;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.filters.FilterException;
import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.filters.Param;
import edu.mgupi.pass.filters.Param.ParamType;

public class PlaceImageFilter implements IFilter {

	private final static Logger logger = LoggerFactory.getLogger(PlaceImageFilter.class);

	public final static String PLACE_CENTER = "center";
	public final static String PLACE_TOP_LEFT = "topleft";
	public final static String PLACE_TOP_RIGHT = "topright";
	public final static String PLACE_BOTTOM_LEFT = "bottomleft";
	public final static String PLACE_BOTTOM_RIGHT = "bottomright";

	private Collection<Param> params;
	private Param WIDTH = new Param("Width", "Ширина", ParamType.INT, 0);
	private Param HEIGHT = new Param("Height", "Высота", ParamType.INT, 0);
	private Param PLACE = new Param("Place", "Размещение", ParamType.STRING, PLACE_CENTER, new Object[] { PLACE_CENTER,
			PLACE_TOP_LEFT, PLACE_TOP_RIGHT, PLACE_BOTTOM_LEFT, PLACE_BOTTOM_RIGHT }, new String[] { "По центру",
			"Слева сверху", "Справа сверху", "Слева снизу", "Справа снизу" });
	private Param BACKGROUND = new Param("Background", "Цвет фона", ParamType.COLOR, Color.WHITE);

	public PlaceImageFilter() {
		params = new ArrayList<Param>(3);
		params.add(WIDTH);
		params.add(HEIGHT);
		params.add(PLACE);
		params = Collections.unmodifiableCollection(params);
	}

	@Override
	public String getName() {
		return "Размещение изображения";
	}

	@Override
	public Collection<Param> getParams() {
		return params;
	}

	public String toString() {
		return this.getName() + " (" + WIDTH.getValue() + "x" + HEIGHT.getValue() + " at " + PLACE.getValue() + ")";
	}

	@Override
	public BufferedImage convert(BufferedImage source) throws FilterException {

		if (source == null) {
			throw new IllegalArgumentException("Internal error: image is null.");
		}

		int newWidth = (Integer) WIDTH.getValue();
		int newHeight = (Integer) HEIGHT.getValue();
		String newPlace = (String) PLACE.getValue();
		Color backGround = (Color) BACKGROUND.getValue();

		logger.debug("Сдвиг изображения {}x{}, {} as {}.", new Object[] { newWidth, newHeight, newPlace, backGround });

		int width = source.getWidth();
		int height = source.getHeight();

		BufferedImage dest = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

		int newX = 0;
		int newY = 0;
		if (newPlace.equals("center")) {
			newX = (newWidth - width) / 2;
			newY = (newHeight - height) / 2;
		} else if (newPlace.equals("topleft")) {
			newX = 0;
			newY = 0;
		} else if (newPlace.equals("topright")) {
			newX = newWidth - width;
			newY = 0;
		} else if (newPlace.equals("bottomleft")) {
			newX = 0;
			newY = newHeight - height;
		} else if (newPlace.equals("bottomright")) {
			newX = newWidth - width;
			newY = newHeight - height;
		} else {
			throw new FilterException("Unknown value of parameter " + PLACE.getName() + ": " + newPlace);
		}

		Graphics gc = dest.getGraphics();
		if (backGround != null) {
			gc.setColor(backGround);
		}
		gc.fillRect(0, 0, dest.getWidth(), dest.getHeight());
		gc.drawImage(source, newX, newY, width, height, null);

		gc.dispose();

		return dest;
	}

	public Param getWIDTH() {
		return WIDTH;
	}

	public Param getHEIGHT() {
		return HEIGHT;
	}

	public Param getPLACE() {
		return PLACE;
	}

	public Param getBACKGROUND() {
		return BACKGROUND;
	}
}
