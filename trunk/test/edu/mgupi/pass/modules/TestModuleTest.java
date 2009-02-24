package edu.mgupi.pass.modules;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

//	@Test
//	public void testAnalyze() {
//		
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCompare() {
//		fail("Not yet implemented");
//	}

}
