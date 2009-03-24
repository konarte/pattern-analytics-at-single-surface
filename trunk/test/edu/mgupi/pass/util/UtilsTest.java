package edu.mgupi.pass.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testListFiles() {
		Collection<File> files = Utils.listFiles("bin", ".class");
		assertNotNull(files);

		for (File file : files) {
			System.out.println(file.getName());
		}
	}
	
	@Test
	public void testSplitStingBySlices() {
		String sourceString = "Hello good bye affirmative";
		assertEquals("Hello-good-bye-affirma-tive", Utils.splitStingBySlices(sourceString, 7, "-"));
		
		sourceString = "1234567890";
		assertEquals("12345-67890", Utils.splitStingBySlices(sourceString, 5, "-"));
	}

}
