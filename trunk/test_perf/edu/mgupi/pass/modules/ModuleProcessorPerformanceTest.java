package edu.mgupi.pass.modules;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.orm.PersistentException;
import org.orm.PersistentTransaction;

import edu.mgupi.pass.db.surfaces.PassPersistentManager;
import edu.mgupi.pass.filters.FilterChainsaw;
import edu.mgupi.pass.filters.FilterException;
import edu.mgupi.pass.filters.service.ResizeFilter;
import edu.mgupi.pass.sources.TestSourceImpl;
import edu.mgupi.pass.util.Secundomer;
import edu.mgupi.pass.util.SecundomerList;

/**
 * No difference between caching at this point.
 * 
 * Caching ON (EHCache)
 * 
 * <pre>
 * == RESULT == 
 * Saving original image AS IS. Total: 156 msec (25), avg = 6.24 msec/call, min = 0 msec, max = 16 msec
 * Filter image by common filter chain. Total: 1248 msec (25), avg = 49.92 msec/call, min = 15 msec, max = 94 msec
 * Saving filtered image as PNG. Total: 6956 msec (25), avg = 278.24 msec/call, min = 250 msec, max = 328 msec
 * Filter image by internal thumb chain. Total: 640 msec (25), avg = 25.6 msec/call, min = 15 msec, max = 32 msec
 * Filter image by internal histogram chain. Total: 594 msec (25), avg = 23.76 msec/call, min = 15 msec, max = 32 msec
 * Analyze image by registered module. Total: 7078 msec (25), avg = 283.12 msec/call, min = 250 msec, max = 343 msec
 * Filling locus data from database. Total: 188 msec (25), avg = 7.52 msec/call, min = 0 msec, max = 141 msec
 * Filling filters data from database. Total: 171 msec (25), avg = 6.84 msec/call, min = 0 msec, max = 16 msec
 * Common processing time. Total: 18093 msec (25), avg = 723.72 msec/call, min = 672 msec, max = 1062 msec *
 * </pre>
 * 
 * Caching OFF
 * 
 * <pre>
 * == RESULT == 
 * Saving original image AS IS. Total: 343 msec (25), avg = 13.72 msec/call, min = 0 msec, max = 157 msec
 * Filter image by common filter chain. Total: 1077 msec (25), avg = 43.08 msec/call, min = 16 msec, max = 94 msec
 * Saving filtered image as PNG. Total: 6942 msec (25), avg = 277.68 msec/call, min = 250 msec, max = 329 msec
 * Filter image by internal thumb chain. Total: 638 msec (25), avg = 25.52 msec/call, min = 15 msec, max = 32 msec
 * Filter image by internal histogram chain. Total: 592 msec (25), avg = 23.68 msec/call, min = 15 msec, max = 32 msec
 * Analyze image by registred module. Total: 7001 msec (25), avg = 280.04 msec/call, min = 250 msec, max = 313 msec
 * Filling locus data from database. Total: 219 msec (25), avg = 8.76 msec/call, min = 0 msec, max = 125 msec
 * Filling filters data from database. Total: 124 msec (25), avg = 4.96 msec/call, min = 0 msec, max = 16 msec
 * Common processing time. Total: 18109 msec (25), avg = 724.36 msec/call, min = 671 msec, max = 1047 msec
 * </pre>
 * 
 * @author raidan
 * 
 */
public class ModuleProcessorPerformanceTest {

	private ModuleProcessor processor;

	@Before
	public void setUp() throws Exception {
		processor = new ModuleProcessor();
	}

	@After
	public void tearDown() throws Exception {
		if (processor != null) {
			processor.close();
			processor = null;
		}
	}

	@Test
	public void testCommonWork() throws InstantiationException, IllegalAccessException, FilterException, IOException,
			ModuleException, PersistentException {
		PersistentTransaction transaction = PassPersistentManager.instance().getSession().beginTransaction();
		TestSourceImpl source = new TestSourceImpl();
		source.init();
		try {

			processor.setModule(TestModule.class);

			FilterChainsaw mainSaw = processor.getChainsaw();
			ResizeFilter resize = (ResizeFilter) mainSaw.appendFilter(ResizeFilter.class);
			resize.getWIDTH().setValue(1024);
			resize.getHEIGHT().setValue(1024);

			Secundomer sec = SecundomerList.registerSecundomer("Common processing time");

			for (int i = 0; i < 25; i++) {
				sec.start();
				processor.startProcessing(source.getSingleSource());
				processor.finishProcessing();
				sec.stop();
			}

			SecundomerList.printToOutput(System.out);
		} finally {
			System.out.println("Rollback");
			transaction.rollback();
			source.close();
		}
	}

}
