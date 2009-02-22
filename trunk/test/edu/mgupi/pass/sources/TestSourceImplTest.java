package edu.mgupi.pass.sources;

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
	public void testInit() {
		source.init();
	}

	@Test
	public void testDone() {
		source.done();
	}

	@Test
	public void testGetSingleSource() throws IOException {

		try {
			source.init();
			SourceStore store = source.getSingleSource();
			System.out.println("Image " + store.getName() + " (" + store.getMainImage().toString() + ")");
		} finally {
			source.done();
		}
	}
}
