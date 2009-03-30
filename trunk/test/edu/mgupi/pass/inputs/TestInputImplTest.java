package edu.mgupi.pass.inputs;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestInputImplTest {

	private TestInputImpl source = null;

	@Before
	public void setUp() throws Exception {
		source = new TestInputImpl();
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
		source.init();
		try {
			InputStore store = source.getSingleSource();
			System.out.println("Image " + store.getName() + " (" + store.getSourceImage().toString() + ")");
		} finally {
			source.close();
		}
	}
}
