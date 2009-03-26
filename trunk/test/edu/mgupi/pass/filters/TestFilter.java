package edu.mgupi.pass.filters;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.filters.Param.ParamType;
import edu.mgupi.pass.util.IInitiable;

public class TestFilter implements IFilter, IInitiable, IFilterAttachable {

	private final static Logger logger = LoggerFactory.getLogger(TestFilter.class);

	public String getName() {
		return "Warning. Тестовый фильтр.";
	}

	private Collection<Param> params = null;
	private Param PARAM_INT = new Param("p1_int", "Параметр 1(INT)", ParamType.INT, 1);
	private Param PARAM_STRING = new Param("p2_string", "Параметр 2(STRING)", ParamType.STRING, "Hello");
	private Param PARAM_LIST = new Param("p3_list", "Параметр 3(LIST)", ParamType.INT, 5, new Object[] { 1, 2, 3, 4, 5,
			6 }, new String[] { "value 1", "value 2", "value 3", "value 4", "value 5", "value 6" });
	private Param PARAM_COLOR = new Param("p4_color", "Параметр 4(COLOR)", ParamType.COLOR, Color.WHITE);
	private Param PARAM_FLOAT = new Param("p5_double", "Параметр 5(DOUBLE)", ParamType.DOUBLE, 14.55);
	private Param PARAM_INT_LOW_HI = new Param("p6_int_borders", "Параметр 6(INT, LOW_HIGH)", ParamType.INT, 10, 0, 255);

	public TestFilter() {
		params = new ArrayList<Param>();
		params.add(PARAM_INT);
		params.add(PARAM_STRING);
		params.add(PARAM_LIST);
		params.add(PARAM_COLOR);
		params.add(PARAM_FLOAT);
		params.add(PARAM_INT_LOW_HI);
	}

	public Collection<Param> getParams() {
		logger.debug("TestFilter.getParams return {} items", params.size());
		return params;
	}

	private boolean init;
	private boolean done;

	public boolean isDone() {
		return done;
	}

	public boolean isInit() {
		return init;
	}

	public void close() {
		if (!init) {
			throw new IllegalStateException("Internal error. Called done without init.");
		}
		if (done) {
			throw new IllegalStateException("Internal error. Called done twice.");
		}
		this.done = true;
	}

	public void init() {
		if (done) {
			throw new IllegalStateException("Internal error. Reusing existable object.");
		}
		if (init) {
			throw new IllegalStateException("Internal error. Called done twice.");
		}
		init = true;
	}

	public String toString() {
		return this.getName();
	}

	public BufferedImage convert(BufferedImage source) throws NoSuchParamException {

		if (source == null) {
			throw new IllegalArgumentException("Internal error: image is null.");
		}

		if (!init) {
			throw new IllegalStateException("Internal error. Called convert without init.");
		}

		if (done) {
			throw new IllegalStateException("Internal error. Called convert after done.");
		}

		if (!attached) {
			throw new IllegalStateException("Internal error. Called convert without onAttachToImage.");
		}

		logger.debug("TestFilter.convert, draw param data");

		BufferedImage dest = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
		Graphics2D graphics2D = dest.createGraphics();
		graphics2D.drawImage(source, 0, 0, source.getWidth(), source.getHeight(), null);

		graphics2D.setFont(new Font("Courier", Font.PLAIN, 22));
		graphics2D.setColor(Color.BLACK);

		// graphics2D.setStroke(new BasicStroke(3.0f));

		int y = 20;
		for (Param param : this.params) {

			String text = param.getTitle() + " = " + param.getValue();
			graphics2D.setColor(Color.WHITE);
			graphics2D.fillRect(20, y - graphics2D.getFontMetrics().getHeight() + 5, graphics2D.getFontMetrics()
					.charsWidth(text.toCharArray(), 0, text.length()), graphics2D.getFontMetrics().getHeight());

			graphics2D.setColor(Color.BLACK);
			graphics2D.drawString(text, 20, y);
			y += 35;
		}

		graphics2D.dispose();
		return dest;
	}

	private boolean attached = false;

	public void onAttachToImage(BufferedImage source) {
		logger.trace("TestFilter.onAttach");
		if (!attached) {
			attached = true;
		} else {
			throw new IllegalStateException("Internal error. Called onAttachToImage twice.");
		}
		// do nothing
	}

	public void onDetachFromImage(BufferedImage source) {
		logger.trace("TestFilter.onDetach");
		if (attached) {
			attached = false;
		} else {
			throw new IllegalStateException("Internal error. Called onDetachFromImage twice.");
		}
		// do nothing
	}

	protected boolean isAttached() {
		return this.attached;
	}

}
