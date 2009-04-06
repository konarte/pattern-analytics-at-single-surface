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
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.text.ChoiceFormat;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Locale;

import javax.swing.JButton;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UtilsTest {

	@Before
	public void setUp() throws Exception {
		Locale.setDefault(Const.LOCALE_RU);
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
				+ "внешнего мира. Сомнение раскладывает на элементы\nнеоднозначный структурализм.",
				Utils.splitStingBySlices(fish, 60, "\n"));

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

	@Test
	public void testMessageFormat() {
		String fmt = "{0} x {1} {2} bpp {3,number,percent}";
		assertEquals("1 024 x 1 024 8 bpp 66%", MessageFormat.format(fmt, 1024, 1024, 8, 0.657));
	}

	@Test
	public void testFormatPluralsSample() {
		
		System.out.println(MessageFormat.format("hello is {0} {0,choice,1#row|rows}", 4));
		
		ChoiceFormat fmt = new ChoiceFormat(
				"-1#is negative| 0#is zero or fraction | 1#is one |1.0<is 1+ |2#is two |2<is more than 2.");
		System.out.println("Formatter Pattern : " + fmt.toPattern());

		System.out.println("Format with -INF : " + fmt.format(Double.NEGATIVE_INFINITY));
		System.out.println("Format with -1.0 : " + fmt.format(-1.0));
		System.out.println("Format with 0 : " + fmt.format(0));
		System.out.println("Format with 0.9 : " + fmt.format(0.9));
		System.out.println("Format with 1.0 : " + fmt.format(1));
		System.out.println("Format with 1.5 : " + fmt.format(1.5));
		System.out.println("Format with 2 : " + fmt.format(2));
		System.out.println("Format with 2.1 : " + fmt.format(2.1));
		System.out.println("Format with NaN : " + fmt.format(Double.NaN));
		System.out.println("Format with +INF : " + fmt.format(Double.POSITIVE_INFINITY));

		String choice1 = "файл";
		String choice2 = "файла";
		String choice3 = "файлов";

		StringBuilder builder = new StringBuilder();
		builder.append("0#").append(choice3);
		builder.append("|1#").append(choice1);
		builder.append("|1<").append(choice2);
		builder.append("|4<").append(choice3);

		fmt = new ChoiceFormat(builder.toString());

		for (int i = 0; i <= 10; i++) {
			System.out.println("" + i + " " + fmt.format(i));
		}

		choice1 = "file";
		choice2 = "files";
		builder = new StringBuilder();
		builder.append("0#").append(choice2);
		builder.append("|1#").append(choice1);
		builder.append("|1<").append(choice2);

		fmt = new ChoiceFormat(builder.toString());

		for (int i = 0; i <= 10; i++) {
			System.out.println("" + i + " " + fmt.format(i));
		}

		System.out.println(MessageFormat.format(
				"Удалить {0} {1,choice,0#строку|1#строку|1<строки|4<строк}", 25, 5));

		String formatString = "При удалении {0} {0,choice,1#строки|1<строк} "
				+ "{2,choice,1#возникла|2#возникло|3#возникли} {1,choice,0<|4<следующие} {1} "
				+ "{2,choice,1#ошибка|2#ошибки|5#ошибок} {1,choice,0<|10<(первые десять)}:";

		assertEquals("При удалении 1 строки возникли следующие 55 ошибок (первые десять):",
				MessageFormat.format(formatString, 1, 55, Utils.getPluralForm(55)));

		assertEquals("При удалении 2 строк возникли следующие 55 ошибок (первые десять):",
				MessageFormat.format(formatString, 2, 55, Utils.getPluralForm(55)));

		assertEquals("При удалении 5 строк возникли следующие 55 ошибок (первые десять):",
				MessageFormat.format(formatString, 5, 55, Utils.getPluralForm(55)));

		assertEquals("При удалении 2 строк возникла  1 ошибка :", MessageFormat.format(
				formatString, 2, 1, Utils.getPluralForm(1)));

		assertEquals("При удалении 2 строк возникло  2 ошибки :", MessageFormat.format(
				formatString, 2, 2, Utils.getPluralForm(2)));

		assertEquals("При удалении 18 строк возникли следующие 5 ошибок :", MessageFormat.format(
				formatString, 18, 5, Utils.getPluralForm(5)));

		assertEquals("При удалении 18 строк возникли следующие 11 ошибок (первые десять):",
				MessageFormat.format(formatString, 18, 11, Utils.getPluralForm(11)));
	}

	@Test
	public void testFormatPlurals() {
		Locale currentLocale = Locale.getDefault();
		if (!currentLocale.equals(Const.LOCALE_RU)) {
			Locale.setDefault(Const.LOCALE_RU);
		}

		String fmt = "Я хочу удалить {0} {1,choice,1#строку|2#строки|5#строк}.";

		assertEquals("Я хочу удалить 0 строк.", Utils.formatSimplePlurals(fmt, 0));
		assertEquals("Я хочу удалить 30 строк.", Utils.formatSimplePlurals(fmt, 30));
		assertEquals("Я хочу удалить 40 строк.", Utils.formatSimplePlurals(fmt, 40));
		assertEquals("Я хочу удалить 50 строк.", Utils.formatSimplePlurals(fmt, 50));
		assertEquals("Я хочу удалить 200 строк.", Utils.formatSimplePlurals(fmt, 200));

		assertEquals("Я хочу удалить 1 строку.", Utils.formatSimplePlurals(fmt, 1));
		assertEquals("Я хочу удалить 21 строку.", Utils.formatSimplePlurals(fmt, 21));
		assertEquals("Я хочу удалить 31 строку.", Utils.formatSimplePlurals(fmt, 31));
		assertEquals("Я хочу удалить 171 строку.", Utils.formatSimplePlurals(fmt, 171));

		/*
		 * How funny. Before 1 and 812 -- this is not a space!
		 */
		assertEquals("Я хочу удалить 1 821 строку.", Utils.formatSimplePlurals(fmt, 1821));

		for (int i = 2; i <= 4; i++) {
			assertEquals("Я хочу удалить " + i + " строки.", Utils.formatSimplePlurals(fmt, i));
		}
		for (int i = 22; i <= 24; i++) {
			assertEquals("Я хочу удалить " + i + " строки.", Utils.formatSimplePlurals(fmt, i));
		}
		for (int i = 32; i <= 34; i++) {
			assertEquals("Я хочу удалить " + i + " строки.", Utils.formatSimplePlurals(fmt, i));
		}
		for (int i = 232; i <= 234; i++) {
			assertEquals("Я хочу удалить " + i + " строки.", Utils.formatSimplePlurals(fmt, i));
		}

		for (int i = 5; i <= 20; i++) {
			assertEquals("Я хочу удалить " + i + " строк.", Utils.formatSimplePlurals(fmt, i));
		}
		for (int i = 25; i <= 30; i++) {
			assertEquals("Я хочу удалить " + i + " строк.", Utils.formatSimplePlurals(fmt, i));
		}
		for (int i = 35; i <= 40; i++) {
			assertEquals("Я хочу удалить " + i + " строк.", Utils.formatSimplePlurals(fmt, i));
		}
		for (int i = 125; i <= 130; i++) {
			assertEquals("Я хочу удалить " + i + " строк.", Utils.formatSimplePlurals(fmt, i));
		}

		Locale.setDefault(Locale.ENGLISH);

		fmt = "I want to remove {0} {1,choice,1#row|2#rows}.";

		assertEquals("I want to remove 0 rows.", Utils.formatSimplePlurals(fmt, 0));
		assertEquals("I want to remove 30 rows.", Utils.formatSimplePlurals(fmt, 30));
		assertEquals("I want to remove 40 rows.", Utils.formatSimplePlurals(fmt, 40));
		assertEquals("I want to remove 50 rows.", Utils.formatSimplePlurals(fmt, 50));
		assertEquals("I want to remove 200 rows.", Utils.formatSimplePlurals(fmt, 200));

		assertEquals("I want to remove 1 row.", Utils.formatSimplePlurals(fmt, 1));
		assertEquals("I want to remove 21 rows.", Utils.formatSimplePlurals(fmt, 21));
		assertEquals("I want to remove 31 rows.", Utils.formatSimplePlurals(fmt, 31));
		assertEquals("I want to remove 171 rows.", Utils.formatSimplePlurals(fmt, 171));
		assertEquals("I want to remove 1,821 rows.", Utils.formatSimplePlurals(fmt, 1821));

		for (int i = 2; i <= 4; i++) {
			assertEquals("I want to remove " + i + " rows.", Utils.formatSimplePlurals(fmt, i));
		}
		for (int i = 22; i <= 24; i++) {
			assertEquals("I want to remove " + i + " rows.", Utils.formatSimplePlurals(fmt, i));
		}
		for (int i = 32; i <= 34; i++) {
			assertEquals("I want to remove " + i + " rows.", Utils.formatSimplePlurals(fmt, i));
		}
		for (int i = 232; i <= 234; i++) {
			assertEquals("I want to remove " + i + " rows.", Utils.formatSimplePlurals(fmt, i));
		}

		for (int i = 5; i <= 20; i++) {
			assertEquals("I want to remove " + i + " rows.", Utils.formatSimplePlurals(fmt, i));
		}
		for (int i = 25; i <= 30; i++) {
			assertEquals("I want to remove " + i + " rows.", Utils.formatSimplePlurals(fmt, i));
		}
		for (int i = 35; i <= 40; i++) {
			assertEquals("I want to remove " + i + " rows.", Utils.formatSimplePlurals(fmt, i));
		}
		for (int i = 125; i <= 130; i++) {
			assertEquals("I want to remove " + i + " rows.", Utils.formatSimplePlurals(fmt, i));
		}
	}

	@Test
	public void testMemPools() {
		// kekeke
		for (MemoryPoolMXBean mem : ManagementFactory.getMemoryPoolMXBeans()) {
			System.out.println(" - " + mem.getName());
		}
		CompilationMXBean comp = ManagementFactory.getCompilationMXBean();
		System.out.println(comp.getName() + " : " + comp.getTotalCompilationTime());

		OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
		System.out.println(os.getName());

		for (GarbageCollectorMXBean garb : ManagementFactory.getGarbageCollectorMXBeans()) {
			System.out.println(" - " + garb.getName() + " : " + garb.getCollectionCount());
		}

	}
}
