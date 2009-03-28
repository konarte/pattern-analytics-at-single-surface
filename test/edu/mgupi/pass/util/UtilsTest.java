package edu.mgupi.pass.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

import javax.swing.JButton;

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

	@Test
	public void testAddCheckedListener() {
		JButton button = new JButton();
		JButton button2 = new JButton();

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//			
			}
		};

		ActionListener listener2 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//			
			}
		};

		Utils.addCheckedListener(button, listener);
		Utils.addCheckedListener(button, listener2);

		Utils.addCheckedListener(button2, listener);
		Utils.addCheckedListener(button2, listener2);

		try {
			Utils.addCheckedListener(button2, listener2);
			fail("No exception thrown!");
		} catch (IllegalArgumentException e) {
			System.out.println("Received expected exception: " + e);
		}

		try {
			Utils.addCheckedListener(null, null);
			fail("No exception thrown!");
		} catch (IllegalArgumentException e) {
			System.out.println("Received expected exception: " + e);
		}

		try {
			Utils.addCheckedListener(button, null);
			fail("No exception thrown!");
		} catch (IllegalArgumentException e) {
			System.out.println("Received expected exception: " + e);
		}

		try {
			Utils.addCheckedListener(null, listener);
			fail("No exception thrown!");
		} catch (IllegalArgumentException e) {
			System.out.println("Received expected exception: " + e);
		}
	}

	@Test
	public void testLoadFromResource() throws IOException {
		File file = new File("test/resources/splash/scout.gif");
		FileInputStream input = new FileInputStream(file);

		byte[] data = Utils.loadFromResource("resources/splash/scout.gif");
		assertEquals(input.getChannel().size(), data.length);
	}

	@Test
	public void testDecodeGarbagedCodepage() throws UnsupportedEncodingException {
		String mess = "Duplicate entry 'Ð¢ÐµÑ�Ñ‚Ð¾Ð²Ñ‹Ð¹ Ñ„Ð¸Ð»ÑŒÑ‚Ñ€' for key 'Name'";
		System.out.println(new String(mess.getBytes("Windows-1252"), "UTF-8"));
	}

}
