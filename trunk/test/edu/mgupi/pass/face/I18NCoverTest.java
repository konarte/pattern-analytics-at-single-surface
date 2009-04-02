/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)I18NCoverTest.java 1.0 30.03.2009
 */

package edu.mgupi.pass.face;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.face.gui.AppHelper;
import edu.mgupi.pass.util.Utils;
import edu.mgupi.pass.util.Config.SupportedLocale;

public class I18NCoverTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	private final static String ALLOWED_IDENTICALLY_VALUES[] = new String[] { "ID", "OK", "№" };

	private final static String BEGIN_FULL_DEF = "Messages.getString(\"";
	private final static String BEGIN_DEF = "getString(\"";
	private final static String END_DEF = "\"";

	@Test
	public void testI18NcoveringTest() throws Exception {
		String source = "src/";
		Collection<File> files = Utils.listFiles(source, ".java");

		Map<String, Collection<String>> checkingResources = new HashMap<String, Collection<String>>();
		//Map<String, String> bundleMap = new HashMap<String, String>();

		for (File file : files) {
			System.out.println(" ================== " + file);
			Collection<String> lines = Utils.loadFromFile(file);

			String fileName = null;
			String prevLine = null;

			Collection<String> keys = new ArrayList<String>();
			for (String line : lines) {
				//				int startKey = line.indexOf(BEGIN_DEF
				//						+ file.getName().substring(0, file.getName().indexOf(".")));
				int startKey = line.indexOf(BEGIN_DEF);
				if (startKey < 0) {
					continue; //
				}

				int startFullKey = line.indexOf(BEGIN_FULL_DEF);

				if (startFullKey < 0) {
					if (prevLine == null) {
						continue; //
					}
					String fullLine = prevLine + line;
					startFullKey = fullLine.indexOf(BEGIN_FULL_DEF);
					if (startFullKey < 0) {
						continue; //
					}
				}

				int endKey = line.indexOf(END_DEF, startKey + BEGIN_DEF.length() + 1);

				if (endKey < startKey) {
					continue; //
				}

				String key = line.substring(startKey + BEGIN_DEF.length(), endKey);

				//				System.out.println("Found link: " + key);

				String path = Utils.replaceAll(file.getAbsolutePath(), "\\", "/");
				int pathPos = path.indexOf("src/edu/mgupi/pass");

				if (pathPos < 0) {
					continue; //
				}

				fileName = path.substring(pathPos + "src/".length());
				//				String bundleName = fileName.substring(0, fileName.lastIndexOf("/")) + ".messages";

				fileName = Utils.replaceAll(fileName, "/", ".");
				fileName = fileName.substring(0, fileName.lastIndexOf("."));
				//				bundleName = Utils.replaceAll(bundleName, "/", ".");
				//				bundleMap.put(fileName, bundleName);

				System.out.println("CHECK " + key);
				keys.add(key);

				prevLine = line;

			}

			if (fileName != null) {
				checkingResources.put(fileName, keys);
			}
		}

		assertTrue(checkingResources.size() > 0);

		Map<Locale, Map<String, String>> resourceMap = new HashMap<Locale, Map<String, String>>();

		for (SupportedLocale supportedLocale : SupportedLocale.values()) {
			if (!AppHelper.setLocale(supportedLocale)) {
				System.err.println("Skipping unknown locale " + supportedLocale.name());
				continue; //
			}
			System.out.println("Processing locale: " + Locale.getDefault());

			Locale locale = Locale.getDefault();

			Map<String, String> currentValues = new HashMap<String, String>();
			for (String clazz_ : checkingResources.keySet()) {

				Class<?> clazzDef_ = Class.forName(clazz_);

				String bndl = clazzDef_.getPackage().getName() + ".messages";
				assertNotNull(bndl);
				ResourceBundle bundle = ResourceBundle.getBundle(bndl, Locale.getDefault());

				Map<String, String> tmpValues = new HashMap<String, String>();
				for (String link : checkingResources.get(clazz_)) {

					//					System.out.println("check key " + link + " for " + bndl);
					//
					try {
						String res = bundle.getString(link);
						assertNotNull(res);

						tmpValues.put(bndl + "@" + link, res);
					} catch (MissingResourceException mre) {
						fail("Unable to find resource " + link + " in bundle " + bndl
								+ " for class " + clazz_);
					}
				}

				for (Map.Entry<Locale, Map<String, String>> previousKeySet : resourceMap.entrySet()) {

					Locale checkedLocale = previousKeySet.getKey();

					boolean allEquals = true;
					for (String key : tmpValues.keySet()) {
						String found = previousKeySet.getValue().get(key);
						assertNotNull("Not found resource " + key + " in locale " + checkedLocale
								+ ", but in locale " + locale + " it exists.", found);

						String currentFound = tmpValues.get(key);
						assertNotNull(currentFound);
						if (found.equals(currentFound)) {
							boolean allow = false;
							for (String allowedIdent : ALLOWED_IDENTICALLY_VALUES) {
								if (found.equals(allowedIdent)) {
									allow = true;
									System.out.println("Found ACCEPTED equals resources: " + key
											+ ", value = " + found + ". Locales: " + locale
											+ " and " + checkedLocale);
									break;
								}
							}
							if (!allow) {
								fail("Found not accepted equals resources: " + key + ", value = "
										+ found + ". Locales: " + locale + " and " + checkedLocale);
							}
						} else {
							allEquals = false;
						}
					}

					assertFalse("All resources in bundle " + bndl + " for class " + clazz_
							+ " for locale " + locale + " identically to resources for locale "
							+ checkedLocale + ".", allEquals);
				}

				currentValues.putAll(tmpValues);
			}

			if (!resourceMap.containsKey(locale)) {
				resourceMap.put(locale, currentValues);
			}
		}
		//
		//		int pos = 0;
		//		Locale.setDefault(Const.LOCALE_RU);
		//		for (String file : checkingResources.keySet()) {
		//			String bndl = bundleMap.get(file);
		//			assertNotNull(bndl);
		//
		//			ResourceBundle bundle = ResourceBundle.getBundle(bndl);
		//			boolean allEquals = true;
		//			for (String key : checkingResources.get(file)) {
		//				String res = bundle.getString(key);
		//				assertNotNull(res);
		//
		//				String previous = loadedResources.get(pos++);
		//				assertNotNull(previous);
		//
		//				if (!res.equals(previous)) {
		//					allEquals = false;
		//				} else {
		//					System.err.println("Found equals resources: bundle " + bndl + ", class " + file
		//							+ ", key = " + key + ", value = " + res);
		//				}
		//			}
		//
		//			assertFalse("All resources in class " + file + " locale identically to each other.",
		//					allEquals);
		//		}

	}
}
