package edu.mgupi.pass.util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Special support class.
 * 
 * @author raidan
 * 
 */
public class ClassesHelper {
	private final static Logger logger = LoggerFactory.getLogger(ClassesHelper.class);

	private final static String CLASSES_PLACE = ".bin.";
	private static Collection<Class<?>> availableClasses = null;

	/**
	 * <p>
	 * Получение списка всех классов системы. «Все классы» — те, которые были
	 * загружены тем же ClassLoader-ом, что и текущий класс.
	 * 
	 * <p>
	 * С полученной коллекцией можно делать что угодно.
	 * 
	 * @return Collection
	 */
	public static synchronized Collection<Class<?>> getAvailableClasses() {

		if (availableClasses == null) {
			availableClasses = new ArrayList<Class<?>>();

			URL source = ClassesHelper.class.getProtectionDomain().getCodeSource().getLocation();

			logger.debug("ClassesHelper.getAvailableClasses processing from location {}.", source);

			try {
				ZipFile zipFile = null;
				Collection<String> names = new ArrayList<String>();
				try { // #1
					zipFile = new ZipFile(source.getPath());
				} catch (Exception ze) {
					for (File file : Utils.listFiles(source.getPath(), ".class")) {
						names.add(file.getAbsolutePath());
					}
				} // #1 try

				if (zipFile != null) { // #2
					try {
						for (Enumeration<? extends ZipEntry> e = zipFile.entries(); e
								.hasMoreElements();) {
							ZipEntry entry = (ZipEntry) e.nextElement();
							if (entry.getName().endsWith(".class")) {
								names.add(entry.getName());
							}
						}
					} finally {
						zipFile.close();
					}
				} // #2 if

				for (String name : names) {

					name = Utils.replaceAll(Utils.replaceAll(name, "\\", "."), "/", ".");
					int classesPos = name.indexOf(CLASSES_PLACE);
					if (classesPos >= 0) {
						name = name.substring(classesPos + CLASSES_PLACE.length());
					}
					if (name.endsWith(".class")) {
						name = name.substring(0, name.length() - ".class".length());
					}
					try {
						availableClasses.add(Class.forName(name));
					} catch (NoClassDefFoundError nfe) {
						//
					}
				}
			} catch (Exception e) {
				availableClasses = null;
				throw new RuntimeException("Unable to load classes: " + e.getMessage(), e);
			}
		}

		Collection<Class<?>> classes = new ArrayList<Class<?>>();
		classes.addAll(availableClasses);
		return classes;
	}

	/**
	 * <p>
	 * Получение списка классов системы, которые имплементирует (или наследуют)
	 * указанный класс. С полученной коллекцией можно делать что угодно.
	 * 
	 * <p>
	 * Если классы не будут найдены — будет возвращен пустой список.
	 * 
	 * @param instanceOf
	 *            класс или интерфейс
	 * @return список классов, которые наследуют/имплементируют переданный
	 *         <code>instanceOf</code>
	 */
	public static Collection<Class<?>> getAvailableClasses(Class<?> instanceOf) {
		return getAvailableClasses(null, instanceOf);
	}

	/**
	 * <p>
	 * Получение списка классов системы, которые хранятся в указанном пакете. С
	 * полученной коллекцией можно делать что угодно.
	 * 
	 * <p>
	 * Если классы не будут найдены — будет возвращен пустой список.
	 * 
	 * @param packageFrom
	 *            начиная с какого пакеты выбираем классы
	 * @return список классов, которые находятся в указанном пакете
	 */
	public static Collection<Class<?>> getAvailableClasses(String packageFrom) {
		return getAvailableClasses(packageFrom, null);
	}

	/**
	 * <p>
	 * Получение списка классов системы, которые хранятся начиная с указанного
	 * пакета и выше. С полученной коллекцией можно делать что угодно.
	 * 
	 * <p>
	 * Если классы не будут найдены — будет возвращен пустой список.
	 * 
	 * @param packageFrom
	 *            начиная с какого пакеты выбираем классы
	 * @param instanceOf
	 *            класс или интерфейс
	 * @return список классов, которые наследуют/имплементируют переданный
	 *         <code>instanceOf</code> и находятся в указанном пакете
	 */
	public static Collection<Class<?>> getAvailableClasses(String packageFrom, Class<?> instanceOf) {

		Collection<Class<?>> result = new ArrayList<Class<?>>();

		for (Class<?> clazz : getAvailableClasses()) {
			if (!clazz.isInterface() && !clazz.isPrimitive() && !clazz.isAnonymousClass()
					&& !clazz.isLocalClass() && !clazz.isMemberClass()
					&& (clazz.getName().indexOf("Abstract") < 0)) {
				boolean accept = true;
				if (packageFrom != null) {
					accept = clazz.getPackage().getName().startsWith(packageFrom);
				}
				if (instanceOf != null) {
					accept = accept && instanceOf.isAssignableFrom(clazz);
				}
				if (accept) {
					result.add(clazz);
				}
			}
		}
		return result;
	}

}
