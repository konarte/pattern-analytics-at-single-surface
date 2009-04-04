package edu.mgupi.pass.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.AbstractButton;
import javax.swing.JMenu;

/**
 * Common utilities class. In common, this class is not required to test.
 * 
 * @author raidan
 * 
 */

public class Utils {

	public static boolean equals(Object o1, Object o2) {
		if (o1 == null && o2 == null) {
			return true;
		}
		if (o1 != null) {
			return o1.equals(o2);
		}

		return false;
	}

	public static String splitStingBySlices(String text, int maximumCols, String delimiter) {
		if (text == null) {
			return null;
		}

		if (text.length() <= maximumCols || maximumCols <= 0) {
			return text;
		}

		StringBuilder source = new StringBuilder(text);
		StringBuilder dest = new StringBuilder();

		do {
			String slice = source.substring(0, maximumCols);

			int lastSpace = -1;
			for (int i = slice.length() - 1; i > 0; i--) {
				if (slice.charAt(i) == ' ') {
					lastSpace = i;
					break;
				}
			}

			if (lastSpace != -1) {

				dest.append(slice.substring(0, lastSpace));
				source.delete(0, lastSpace + 1);
			} else {

				dest.append(slice);
				source.delete(0, maximumCols);
			}
			if (source.length() > 0) {
				dest.append(delimiter);

				if (source.length() < maximumCols) {
					dest.append(source);
					source.delete(0, source.length());
				}
			}

		} while (source.length() > 0);

		return dest.toString();
	}

	public static boolean isWeLoadedFromJar() {
		URL url = Utils.class.getProtectionDomain().getCodeSource().getLocation();
		if (url != null && url.getPath().endsWith(".jar")) {
			return true;
		}

		return false;

	}

	/**
	 * Загружает список файлов из каталога или из JAR-ника
	 * 
	 * @param path
	 *            название ресурса (вида <code>com/raidan/dclog/core/</code>).
	 *            Обязательно со слэшем на конце!
	 * @param extension
	 * @return Коллекцию файлов, пригодных к загрузке методом loadFromJAR
	 * @throws IOException
	 */
	public static String[] listFilesFromJAR(String path, String extension) throws IOException {

		if (path == null) {
			return null;
		}

		URL url = Utils.class.getProtectionDomain().getCodeSource().getLocation();

		if (url == null) {
			return null;
		}

		if (url.getPath().endsWith(".jar")) {

			path = Utils.replaceAll(path, "\\", "/");
			if (!path.endsWith("/")) {
				path = path + "/";
			}

			ZipFile file = new ZipFile(url.getPath());
			ZipEntry entry = file.getEntry(path);

			if (entry == null) {
				throw new IOException("Unable to find '" + path + "' from JAR file " + url);
			}

			if (!entry.isDirectory()) {
				throw new IOException("Path '" + path + "' is not directory. Unable to load list.");
			}

			Collection<String> fileNames = new ArrayList<String>();

			Enumeration<? extends ZipEntry> entries = file.entries();
			while (entries.hasMoreElements()) {
				entry = entries.nextElement();
				String name = entry.getName();
				if (name.startsWith(path) && !entry.isDirectory()) {
					if (extension == null || name.endsWith(extension)) {
						fileNames.add(name);
					}
				}
			}

			return fileNames.toArray(new String[fileNames.size()]);
		}

		File dir = new File(url.getPath() + path);
		if (!dir.isDirectory()) {
			throw new IOException("Path '" + path + "' is not directory. Unable to load list.");
		}
		File files[] = dir.listFiles();
		Collection<String> fileNames = new ArrayList<String>();
		for (File file : files) {
			String fullPath = Utils.replaceAll(file.getCanonicalPath(), "\\", "/");
			if (extension == null || fullPath.endsWith(extension)) {
				fileNames.add(fullPath.substring(fullPath.lastIndexOf(path)));
			}
		}
		return fileNames.toArray(new String[fileNames.size()]);

	}

	/**
	 * <p>
	 * Загружает данные из ресурса. Под ресурсом подразумевается файл, доступный
	 * класслоадеру.
	 * 
	 * <p>
	 * <b>Note:</b> не используйте этот метод при загрузке множества файлов!
	 * Можно получить утечки памяти.
	 * 
	 * 
	 * @param filePath
	 *            название ресурса (вида
	 *            <code>com/forward/cfg/DBloader.properties</code>).
	 * @return данные
	 * @throws IOException
	 */
	public static byte[] loadFromResource(String filePath) throws IOException {
		InputStream stream = ClassLoader.getSystemResourceAsStream(filePath);
		if (stream == null) {
			throw new IOException("Unable to load file '" + filePath + "' from resources.");
		}
		int bufferSize = 100000;
		try {

			byte[] resultBuffer = new byte[0];
			byte[] buffer = new byte[bufferSize];
			int read = 0;
			while ((read = stream.read(buffer)) != -1) {
				byte[] tmpBuffer = new byte[resultBuffer.length + read];
				if (resultBuffer.length > 0) {
					System.arraycopy(resultBuffer, 0, tmpBuffer, 0, resultBuffer.length);
				}
				System.arraycopy(buffer, 0, tmpBuffer, resultBuffer.length, read);
				resultBuffer = tmpBuffer;
			}
			return resultBuffer;
		} finally {
			stream.close();
		}

	}

	public static Collection<String> loadFromFile(File file) throws IOException {
		Collection<String> lines = new ArrayList<String>();

		BufferedReader input = new BufferedReader(new FileReader(file));
		try {
			String line = null;
			while ((line = input.readLine()) != null) {
				lines.add(line);
			}
			return lines;
		} finally {
			input.close();
		}
	}

	public static List<File> listFiles(final String dir, final String ext) {
		return listFiles(dir, new FileFilter() {

			public boolean accept(File pathname) {
				return pathname.isDirectory() || (pathname.getName().endsWith(ext));
			}
		});
	}

	private static List<File> listFiles(final String dir, final FileFilter filter) {

		if (dir == null) {
			throw new IllegalArgumentException("Internal error. Received null dir.");
		}

		List<File> result = new ArrayList<File>();
		File files[] = new File(dir).listFiles(filter);
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					result.addAll(listFiles(files[i].getAbsolutePath(), filter));
				} else {
					result.add(files[i]);
				}
			}
		} else {
			System.err.println("Directory " + dir + " return no files.");
		}
		return result;
	}

	/**
	 * Fast implementation for simple replace data in strings
	 * 
	 * @param buffer
	 * @param replaceWhat
	 * @param replaceTo
	 * @return string with replaces
	 */
	public final static String replaceAll(String buffer, String replaceWhat, String replaceTo) {
		if (buffer == null || replaceWhat == null) {
			return null;
		}
		return replaceAll(new StringBuilder(buffer), replaceWhat, replaceTo).toString();
	}

	private static int MAX_REPLACEABLE_ITERATIONS = 1000000;

	/**
	 * Fast implementation for simple replace data in strings
	 * 
	 * @param buffer
	 * @param replaceWhat
	 * @param replaceTo
	 * @return string with replaces
	 */
	public final static StringBuilder replaceAll(StringBuilder buffer, String replaceWhat,
			String replaceTo) {
		if (buffer == null || replaceWhat == null) {
			return null;
		}

		int idx = buffer.indexOf(replaceWhat);
		if (idx >= 0) {
			int cnt = 0;
			int len = replaceWhat.length();
			int len_to = replaceTo.length();
			while (idx >= 0) {
				buffer.replace(idx, idx + len, replaceTo);
				cnt++;
				if (cnt > MAX_REPLACEABLE_ITERATIONS) {
					throw new IllegalStateException("Critical error. Replacing '" + replaceWhat
							+ "' to '" + replaceTo + "' going to infinite loop.");
				}
				idx = buffer.indexOf(replaceWhat, idx + len_to);
			}
		}
		return buffer;
	}

	//	private final static Logger logger = LoggerFactory.getLogger(Utils.class);

	public static void addCheckedListener(AbstractButton button, ActionListener listener) {

		if (button == null) {
			throw new IllegalArgumentException("Internal error. 'button' must be not null.");
		}

		if (listener == null) {
			throw new IllegalArgumentException("Internal error. 'listener' must be not null.");
		}

		ActionListener[] listeners = button.getActionListeners();
		if (listeners != null) {
			for (ActionListener listen : listeners) {
				if (listen == listener) {
					throw new IllegalArgumentException("Internal error. Listener " + listener
							+ " already registered for " + button);
				}
			}
		}
		button.addActionListener(listener);
	}

	/**
	 * Method (c) Ichiro Suzuki
	 * 
	 * @param parent
	 * @param name
	 * @return instance of found component or null (if component with this name
	 *         is not found)
	 */
	public static Component getChildNamed(Component parent, String name) {

		if (parent == null) {
			return null;
		}
		if (name == null) {
			return null;
		}

		if (name.equals(parent.getName())) {
			return parent;
		}

		if (parent instanceof Container) {
			Component[] children = (parent instanceof JMenu) ? ((JMenu) parent).getMenuComponents()
					: ((Container) parent).getComponents();

			for (int i = 0; i < children.length; ++i) {
				Component child = getChildNamed(children[i], name);
				if (child != null) {
					return child;
				}
			}
		}

		return null;
	}

	public final static int PLURAL_FIRST = 1;
	public final static int PLURAL_SECOND = 2;
	public final static int PLURAL_THIRD = 5;

	public static int getPluralForm(int value) {
		int pluralForm = 0;
		if (Locale.getDefault().equals(Const.LOCALE_RU)) {

			pluralForm = PLURAL_THIRD;
			if (value > 0) {
				pluralForm = Math.abs(value) % 100;
				int dec = pluralForm % 10;
				if (pluralForm >= 10 && pluralForm <= 20) {
					pluralForm = PLURAL_THIRD;
				} else if (dec > 1 && dec < 5) {
					pluralForm = PLURAL_SECOND;
				} else if (dec == 1) {
					pluralForm = PLURAL_FIRST;
				} else {
					pluralForm = PLURAL_THIRD;
				}
			}
		} else {
			pluralForm = value == 1 ? PLURAL_FIRST : PLURAL_SECOND;
		}
		return pluralForm;
	}

	public static String formatSimplePlurals(String pattern, int value) {
		return MessageFormat.format(pattern, new Object[] { value, getPluralForm(value) });
	}

}
