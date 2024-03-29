package edu.mgupi.pass.modules;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.db.locuses.LocusAppliedModule;
import edu.mgupi.pass.db.locuses.LocusAppliedModuleFactory;
import edu.mgupi.pass.db.locuses.LocusSources;
import edu.mgupi.pass.db.locuses.LocusSourcesFactory;
import edu.mgupi.pass.db.locuses.Locuses;
import edu.mgupi.pass.db.locuses.LocusesFactory;
import edu.mgupi.pass.filters.FilterException;
import edu.mgupi.pass.filters.Param;
import edu.mgupi.pass.filters.Param.ParamType;
import edu.mgupi.pass.filters.java.GrayScaleFilter;
import edu.mgupi.pass.inputs.InputStore;
import edu.mgupi.pass.inputs.TestInputImpl;

public class TestModuleTest {

	private TestModule module;

	@Before
	public void setUp() throws Exception {
		module = new TestModule();
		module.init();
	}

	@After
	public void tearDown() throws Exception {
		if (module != null) {
			module.close();
			module = null;
		}
	}

	@Test
	public void testInitAndDone() {
		try {
			module.init();
			fail("No IllegalStateException!");
		} catch (IllegalStateException ise) {
			System.out.println("Received expected exception: " + ise);
		}

		module.close();
		try {
			module.close();
			fail("No IllegalStateException!");
		} catch (IllegalStateException ise) {
			System.out.println("Received expected exception: " + ise);
		}
		module = new TestModule();
		module.init();
	}

	@Test
	public void testForModifyAnalyze() throws Exception {
		Collection<Param> params = module.getParams();
		try {
			params.add(new Param("1", "2", ParamType.INT, 0));
			fail("No exception throws!");
		} catch (UnsupportedOperationException e) {
			System.out.println("Received expected exception: " + e);
		}

	}

	@Test
	public void testAnalyze() throws IOException, ClassNotFoundException, ModuleException,
			FilterException {

		TestInputImpl source = new TestInputImpl();
		source.init();
		try {

			InputStore sourceStore = source.getSingleSource();

			Locuses locus = LocusesFactory.createLocuses();
			LocusSources locusSource = LocusSourcesFactory.createLocusSources();
			locusSource.setFilename(sourceStore.getName());
			locusSource.setSourceImage(sourceStore.getFileData());

			locus.setLocusSource(locusSource);
			LocusAppliedModule appmodule = LocusAppliedModuleFactory.createLocusAppliedModule();
			locus.setModule(appmodule);

			module.analyze(sourceStore.getSourceImage(), locus);

			BufferedImage moduleImage = ModuleHelper.getTemporaryModuleImage(locus);
			locus.setProcessed(true);

			assertNotNull(moduleImage);

			FileOutputStream fileStream;
			ObjectOutputStream out;

			new File("tmp").mkdir();

			ModuleHelper.finalyzeParams(locus);

			fileStream = new FileOutputStream("tmp/locus-serialized.data");
			out = new ObjectOutputStream(fileStream);
			out.writeObject(locus);
			fileStream.close();

			ImageIO.write(moduleImage, "PNG", new File("tmp/locus-module-return.png"));

			FileInputStream input = new FileInputStream("tmp/locus-serialized.data");
			ObjectInputStream in = new ObjectInputStream(input);
			locus = (Locuses) in.readObject();
			input.close();

			assertTrue(module.compare(locus, locus) == 1);
			assertNotNull(ModuleHelper.getTemporaryModuleImage(locus));
			ImageIO.write(ModuleHelper.getTemporaryModuleImage(locus), "PNG", new File(
					"tmp/locus-module-return-restored.png"));

			Locuses locus2 = LocusesFactory.createLocuses();
			LocusSources locusSource2 = LocusSourcesFactory.createLocusSources();
			locusSource2.setFilename(sourceStore.getName());
			locusSource2.setSourceImage(sourceStore.getFileData());

			locus2.setLocusSource(locusSource2);
			LocusAppliedModule appmodule2 = LocusAppliedModuleFactory.createLocusAppliedModule();
			locus2.setModule(appmodule2);

			module.getTEST_PARAM1().setValue(5);
			module.getTEST_PARAM2().setValue("Super");
			module.analyze(sourceStore.getSourceImage(), locus2);
			moduleImage = ModuleHelper.getTemporaryModuleImage(locus2);
			locus2.setProcessed(true);

			ImageIO.write(moduleImage, "PNG", new File("tmp/locus-module-return2.png"));
			ModuleHelper.finalyzeParams(locus2);
			assertTrue(module.compare(locus, locus2) == 1);
			assertNotNull(moduleImage);

			Locuses locus3 = LocusesFactory.createLocuses();
			LocusSources locusSource3 = LocusSourcesFactory.createLocusSources();
			locusSource3.setFilename(sourceStore.getName());
			locusSource3.setSourceImage(sourceStore.getFileData());

			locus3.setLocusSource(locusSource3);
			LocusAppliedModule appmodule3 = LocusAppliedModuleFactory.createLocusAppliedModule();
			locus3.setModule(appmodule3);

			module.analyze(new GrayScaleFilter().convert(sourceStore.getSourceImage()), locus3);
			moduleImage = ModuleHelper.getTemporaryModuleImage(locus3);
			locus3.setProcessed(true);

			ModuleHelper.finalyzeParams(locus3);
			assertFalse(module.compare(locus, locus3) == 1);
			assertNotNull(moduleImage);

			ImageIO.write(moduleImage, "PNG", new File("tmp/locus-module-return3-gray.png"));

			// Locuses locus2 = LocusesFactory.createLocuses();

			// fileStream = new FileOutputStream("loc2.data");
			// out = new ObjectOutputStream(fileStream);
			// out.writeObject(locus2);
			// fileStream.close();

		} finally {
			source.close();
		}

	}
	// @Test
	// public void testCompare() {
	// fail("Not yet implemented");
	// }

}
