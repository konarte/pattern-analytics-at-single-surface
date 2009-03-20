package edu.mgupi.pass.modules;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.orm.PersistentTransaction;

import edu.mgupi.pass.db.locuses.Locuses;
import edu.mgupi.pass.db.surfaces.PassPersistentManager;
import edu.mgupi.pass.filters.FilterChainsaw;
import edu.mgupi.pass.filters.java.GrayScaleFilter;
import edu.mgupi.pass.filters.service.ResizeFilter;
import edu.mgupi.pass.modules.basic.SimpleMatrixModule;
import edu.mgupi.pass.sources.TestSourceImpl;
import edu.mgupi.pass.util.SecundomerList;

public class ModuleProcessorTest {

	private ModuleProcessor processor;

	@Before
	public void setUp() throws Exception {
		SecundomerList.reset();
		processor = new ModuleProcessor();
	}

	@After
	public void tearDown() throws Exception {
		if (processor != null) {
			processor.close();
			assertNull(processor.getFilters());
			assertNull(processor.getPreProcessingFilters());
			processor = null;
		}

	}

	@Test
	public void testCommonWork() throws Exception {
		PersistentTransaction transaction = PassPersistentManager.instance().getSession().beginTransaction();
		TestSourceImpl source = new TestSourceImpl();
		source.init();
		try {

			processor.setModule(TestModule.class);

			FilterChainsaw mainSaw = new FilterChainsaw();
			ResizeFilter resize = (ResizeFilter) mainSaw.appendFilter(ResizeFilter.class);
			resize.getWIDTH().setValue(1024);
			resize.getHEIGHT().setValue(1024);

			processor.setChainsaw(mainSaw);

			assertNotNull(processor.getFilters());
			assertNull(processor.getPreProcessingFilters());

			try {
				Locuses myLocus = processor.startProcessing(source.getSingleSource());
				assertNotNull(myLocus);
				assertTrue(myLocus.getProcessed());
				BufferedImage image = ModuleHelper.getTemporaryModuleImage(myLocus);
				assertNotNull(image);

				IModule firstModule = processor.getModule();

				new File("tmp").mkdir();
				ImageIO.write(processor.getLastProcessedImage(), "PNG", new File("tmp/module-test-processed.png"));

				processor.setModule(SimpleMatrixModule.class);
				assertNotNull(ModuleHelper.getTemporaryModuleImage(myLocus));
				assertTrue(myLocus.getProcessed());

				assertFalse(processor.getModule() == firstModule);

				processor.setModule(TestModule.class);
				assertTrue(myLocus.getProcessed());
				assertNotNull(ModuleHelper.getTemporaryModuleImage(myLocus));

				assertTrue(processor.getModule() == firstModule);

				processor.setModule(TestModule2.class);
				assertNull(ModuleHelper.getTemporaryModuleImage(myLocus));

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
	public void testPreprocessingWork() throws Exception {
		PersistentTransaction transaction = PassPersistentManager.instance().getSession().beginTransaction();
		TestSourceImpl source = new TestSourceImpl();
		source.init();
		try {

			processor.setModule(TestModule.class);

			FilterChainsaw preprocessingSaw = new FilterChainsaw();

			ResizeFilter resize = (ResizeFilter) preprocessingSaw.appendFilter(ResizeFilter.class);
			resize.getWIDTH().setValue(1024);
			resize.getHEIGHT().setValue(1024);

			processor.setPreprocessingChainsaw(preprocessingSaw);

			FilterChainsaw mainSaw = new FilterChainsaw();
			preprocessingSaw.appendFilter(GrayScaleFilter.class);

			processor.setChainsaw(mainSaw);

			assertNotNull(processor.getFilters());
			assertNotNull(processor.getPreProcessingFilters());

			try {
				Locuses myLocus = processor.startProcessing(source.getSingleSource());
				assertNotNull(myLocus);
				assertTrue(myLocus.getProcessed());
				assertNotNull(ModuleHelper.getTemporaryModuleImage(myLocus));

				IModule firstModule = processor.getModule();

				new File("tmp").mkdir();
				ImageIO.write(processor.getLastProcessedImage(), "PNG", new File("tmp/module-test-processed-pre.png"));

				processor.setModule(SimpleMatrixModule.class);
				assertNotNull(ModuleHelper.getTemporaryModuleImage(myLocus));
				assertTrue(myLocus.getProcessed());

				IModule secondModule = processor.getModule();
				assertFalse(processor.getModule() == firstModule);

				processor.setModule(TestModule.class);
				assertNotNull(myLocus);
				assertTrue(myLocus.getProcessed());
				assertNotNull(ModuleHelper.getTemporaryModuleImage(myLocus));

				assertTrue(processor.getModule() == firstModule);

				processor.setModule(SimpleMatrixModule.class);
				assertNotNull(ModuleHelper.getTemporaryModuleImage(myLocus));
				assertTrue(myLocus.getProcessed());
				assertTrue(processor.getModule() == secondModule);
				assertFalse(processor.getModule() == firstModule);

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

			processor.setModule(TestModule.class);

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
