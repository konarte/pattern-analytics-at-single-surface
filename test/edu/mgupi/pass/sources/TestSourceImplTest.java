package edu.mgupi.pass.sources;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestSourceImplTest {

	private TestSourceImpl source = null;

	@Before
	public void setUp() throws Exception {
		source = new TestSourceImpl();
	}

	@After
	public void tearDown() throws Exception {
		source = null;
	}

	@Test
	public void testInitAndDone() {
		source.init();
		try {
			source.init();
			fail("No IllegalStateException!");
		} catch (IllegalStateException ise) {
			System.out.println("Received expected exception: " + ise);
		}
		source.close();
		try {
			source.close();
			fail("No IllegalStateException!");
		} catch (IllegalStateException ise) {
			System.out.println("Received expected exception: " + ise);
		}
	}

	@Test
	public void testGetSingleSource() throws IOException {

		try {
			source.init();
			SourceStore store = source.getSingleSource();
			System.out.println("Image " + store.getName() + " (" + store.getSourceImage().toString() + ")");
		} finally {
			source.close();
		}
	}
}
