package edu.mgupi.pass.modules;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.db.locuses.LocusSources;
import edu.mgupi.pass.db.locuses.LocusSourcesFactory;
import edu.mgupi.pass.db.locuses.Locuses;
import edu.mgupi.pass.db.locuses.LocusesFactory;
import edu.mgupi.pass.filters.ParamException;
import edu.mgupi.pass.filters.java.GrayScaleFilter;
import edu.mgupi.pass.sources.SourceStore;
import edu.mgupi.pass.sources.TestSourceImpl;

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
			module.done();
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

		module.done();
		try {
			module.done();
			fail("No IllegalStateException!");
		} catch (IllegalStateException ise) {
			System.out.println("Received expected exception: " + ise);
		}
		module = new TestModule();
		module.init();
	}

	@Test
	public void testAnalyze() throws IOException, ClassNotFoundException, ModuleException, ParamException {

		TestSourceImpl source = new TestSourceImpl();
		source.init();
		try {

			SourceStore sourceStore = source.getSingleSource();

			Locuses locus = LocusesFactory.createLocuses();
			LocusSources locusSource = LocusSourcesFactory.createLocusSources();
			locusSource.setFilename(sourceStore.getName());
			locusSource.setSourceImage(sourceStore.getFileData());

			locus.setLocusSource(locusSource);

			module.analyze(sourceStore.getImage(), locus);

			FileOutputStream fileStream;
			ObjectOutputStream out;

			fileStream = new FileOutputStream("tmp/locus-serialized.data");
			out = new ObjectOutputStream(fileStream);
			out.writeObject(locus);
			fileStream.close();

			FileInputStream input = new FileInputStream("tmp/locus-serialized.data");
			ObjectInputStream in = new ObjectInputStream(input);
			locus = (Locuses) in.readObject();
			input.close();
			

			assertTrue(module.compare(locus, locus));

			Locuses locus2 = LocusesFactory.createLocuses();
			LocusSources locusSource2 = LocusSourcesFactory.createLocusSources();
			locusSource2.setFilename(sourceStore.getName());
			locusSource2.setSourceImage(sourceStore.getFileData());

			locus2.setLocusSource(locusSource2);

			module.analyze(sourceStore.getImage(), locus2);

			assertTrue(module.compare(locus, locus2));

			Locuses locus3 = LocusesFactory.createLocuses();
			LocusSources locusSource3 = LocusSourcesFactory.createLocusSources();
			locusSource3.setFilename(sourceStore.getName());
			locusSource3.setSourceImage(sourceStore.getFileData());

			locus3.setLocusSource(locusSource3);

			module.analyze(new GrayScaleFilter().convert(sourceStore.getImage()), locus3);

			assertFalse(module.compare(locus, locus3));

			// Locuses locus2 = LocusesFactory.createLocuses();

			// fileStream = new FileOutputStream("loc2.data");
			// out = new ObjectOutputStream(fileStream);
			// out.writeObject(locus2);
			// fileStream.close();

		} finally {
			source.done();
		}

	}
	// @Test
	// public void testCompare() {
	// fail("Not yet implemented");
	// }

}
