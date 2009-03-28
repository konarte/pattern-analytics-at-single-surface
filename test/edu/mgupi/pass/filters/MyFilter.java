package edu.mgupi.pass.filters;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.util.IInitiable;

public class MyFilter implements IFilter, IInitiable {

	private final static Logger logger = LoggerFactory.getLogger(MyFilter.class);

	protected void finalize() throws Throwable {
		if (!done) {
			System.err.println("Method 'close' does not called for " + this);
		}
	}

	private Collection<Param> model = ParamTest.fillParameters(new ArrayList<Param>());

	private Collection<Param> parameters = null;

	public MyFilter() {
		parameters = new ArrayList<Param>();
		ParamTest.fillParameters(parameters);
		Collections.unmodifiableCollection(parameters);
	}

	@Override
	public BufferedImage convert(BufferedImage source) throws FilterException {

		if (!init) {
			throw new IllegalStateException("Internal error. Called convert without init.");
		}

		if (done) {
			throw new IllegalStateException("Internal error. Called convert after done.");
		}

		logger.debug(this + " CONVERT");

		ParamTest.compareInCollections(model, parameters, true);

		return source;
	}

	@Override
	public String getName() {
		return "Тестовый фильтр";
	}

	@Override
	public Collection<Param> getParams() {
		return this.parameters;
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
		logger.debug(this + " CLOSE");
	}

	public void init() {
		if (done) {
			throw new IllegalStateException("Internal error. Reusing existable object.");
		}
		if (init) {
			throw new IllegalStateException("Internal error. Called done twice.");
		}
		init = true;
		logger.debug(this + " INIT");
	}

}
