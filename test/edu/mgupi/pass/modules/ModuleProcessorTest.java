package edu.mgupi.pass.modules;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.orm.PersistentTransaction;

import edu.mgupi.pass.db.surfaces.PassPersistentManager;
import edu.mgupi.pass.filters.FilterChainsaw;
import edu.mgupi.pass.filters.service.ResizeFilter;
import edu.mgupi.pass.sources.TestSourceImpl;
import edu.mgupi.pass.util.SecundomerList;

public class ModuleProcessorTest {

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
	public void testCommonWork() throws Exception {
		PersistentTransaction transaction = PassPersistentManager.instance().getSession().beginTransaction();
		TestSourceImpl source = new TestSourceImpl();
		source.init();
		try {

			processor.registerModule(TestModule.class);

			FilterChainsaw mainSaw = new FilterChainsaw();
			ResizeFilter resize = new ResizeFilter();
			resize.getWIDTH().setValue(1024);
			resize.getHEIGHT().setValue(1024);
			mainSaw.appendFilter(resize);

			processor.setChainsaw(mainSaw);

			try {
				processor.startProcessing(source.getSingleSource());
			} finally {
				processor.finishProcessing();
			}

		} finally {
			transaction.rollback();
			source.close();
			PassPersistentManager.instance().disposePersistentManager();
		}

		SecundomerList.printToOutput(System.out);
	}

	@Test
	public void testEmptyFilters() throws Exception {
		PersistentTransaction transaction = PassPersistentManager.instance().getSession().beginTransaction();
		TestSourceImpl source = new TestSourceImpl();
		source.init();
		try {

			processor.registerModule(TestModule.class);

			try {
				processor.startProcessing(source.getSingleSource());
			} finally {
				processor.finishProcessing();
			}

		} finally {
			transaction.rollback();
			source.close();
			PassPersistentManager.instance().disposePersistentManager();
		}

		SecundomerList.printToOutput(System.out);
	}

}
