package edu.mgupi.pass.modules;

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
	public void testAnalyze() throws IOException, ClassNotFoundException {

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
			module.compare(locus, locus);

			input.close();

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
