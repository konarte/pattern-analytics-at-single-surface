package edu.mgupi.pass.util;

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
		for (File file : files) {
			System.out.println(file.getName());
		}
	}

}
