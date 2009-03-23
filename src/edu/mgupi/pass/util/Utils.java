package edu.mgupi.pass.util;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Common utils class. In common, this class is not required to test.
 * 
 * @author raidan
 * 
 */

public class Utils {

	public static boolean equals(Object o1, Object o2) {
		if ((o1 == null && o2 != null) || (o1 != null && o2 == null)) {
			return false;
		}
		return o1.equals(o2);
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
	public final static StringBuilder replaceAll(StringBuilder buffer, String replaceWhat, String replaceTo) {
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
					throw new IllegalStateException("Critical error. Replacing '" + replaceWhat + "' to '" + replaceTo
							+ "' going to infinite loop.");
				}
				idx = buffer.indexOf(replaceWhat, idx + len_to);
			}
		}
		return buffer;
	}
}
