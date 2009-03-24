package edu.mgupi.pass.util;

import static org.junit.Assert.*;

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

		sourceString = "1234567890";
		assertEquals("1234567890", Utils.splitStingBySlices(sourceString, 99, "-"));

		String fish = "Принцип восприятия непредвзято создает паллиативный интеллект, условно. "
				+ "Концепция ментально оспособляет закон внешнего мира. "
				+ "Сомнение раскладывает на элементы неоднозначный структурализм.";

		assertEquals("Принцип восприятия непредвзято создает паллиативный\n"
				+ "интеллект, условно. Концепция ментально оспособляет закон\n"
				+ "внешнего мира. Сомнение раскладывает на элементы\nнеоднозначный структурализм.", Utils
				.splitStingBySlices(fish, 60, "\n"));

		assertEquals(null, Utils.splitStingBySlices(null, 0, "\n"));
		assertEquals("HELLO", Utils.splitStingBySlices("HELLO", 0, null));
		assertEquals("HELLO", Utils.splitStingBySlices("HELLO", 0, "-"));
		assertEquals("H-E-L-L-O", Utils.splitStingBySlices("HELLO", 1, "-"));
	}

	@Test
	public void testEquals() {
		assertTrue(Utils.equals("same", new String("same")));
		assertTrue(Utils.equals("same", "s" + "a" + "m" + "e"));
		assertTrue(Utils.equals(null, null));
		assertTrue(Utils.equals(1, new Long(Long.parseLong("1")).intValue()));
		assertFalse(Utils.equals(null, 1));
		assertFalse(Utils.equals(1, null));
		assertFalse(Utils.equals("h1", "h2"));
	}

}
