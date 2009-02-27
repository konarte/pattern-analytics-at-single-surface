package edu.mgupi.pass.modules;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.orm.PersistentException;

import edu.mgupi.pass.filters.FilterChainsaw;
import edu.mgupi.pass.filters.FilterException;
import edu.mgupi.pass.sources.TestSourceImpl;

public class ModuleProcessorTest {

	private ModuleProcessor processor;

	@Before
	public void setUp() throws Exception {
		processor = new ModuleProcessor();
	}

	@After
	public void tearDown() throws Exception {
		if (processor != null) {
			processor.done();
			processor = null;
		}
	}

	@Test
	public void testCommonWork() throws InstantiationException, IllegalAccessException, FilterException, IOException,
			ModuleException, PersistentException {
		TestSourceImpl source = new TestSourceImpl();
		source.init();
		try {

			processor.registerModule(TestModule.class);

			FilterChainsaw emptySaw = new FilterChainsaw();
			processor.setChainsaw(emptySaw);

			processor.startProcessing(source.getSingleSource());
			processor.finishProcessing();
		} finally {
			source.done();
		}
	}

}
