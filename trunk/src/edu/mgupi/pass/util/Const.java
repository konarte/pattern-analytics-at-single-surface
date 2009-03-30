package edu.mgupi.pass.util;

import java.util.Locale;
import java.util.ResourceBundle;

import edu.mgupi.pass.db.locuses.Locuses;
import edu.mgupi.pass.face.gui.SplashWindow;

/**
 * Common constants.
 * 
 * @author raidan
 * 
 */
public class Const {

	/**
	 * If this flag = true, then we'll show all errors with localization
	 * (resources we not found).
	 */
	public final static boolean PRODUCTION_MODE = false;

	/**
	 * Russian locale.
	 */
	public final static Locale LOCALE_RU = new Locale("ru", "RU");

	/**
	 * Width of all thumbnails (for source image and images in tables)
	 */
	public final static int THUMB_WIDTH = 256;
	/**
	 * Height of all thumbnails (for source image and images in tables)
	 */
	public final static int THUMB_HEIGHT = 256;

	/**
	 * Width of Locus image.
	 * 
	 * @see Locuses
	 */
	public final static int MAIN_IMAGE_WIDTH = 1024;

	/**
	 * Height of Locus image
	 * 
	 * @see Locuses
	 */
	public final static int MAIN_IMAGE_HEIGHT = 1024;

	/**
	 * Name of production packed jar.
	 */
	public final static String DEFAULT_PACKED_JAR_NAME = "pattern-analytics.jar";

	/**
	 * Path to image we'll use for splash window. <br>
	 * 
	 * If null, than we use {@value #SPLASH_IMAGE_PLACE_DIR} and show random
	 * image from that dir.
	 * 
	 * @see SplashWindow
	 */
	public final static String SPLASH_IMAGE_PATH = null;

	/**
	 * Directory for splash images. Used by {@link SplashWindow}. Use slash at
	 * the end.
	 */
	public final static String SPLASH_IMAGE_PLACE_DIR = "resources/splash/";

	/**
	 * Path to file what we'll use as application icon.
	 */
	public final static String FORM_ICON_IMAGE_PATH = "resources/icon/icon.gif";

	/**
	 * Program name.
	 */
	public final static String PROGRAM_NAME_FIRST = "Pattern Analytics at Single Surface";
	/**
	 * Program version.
	 */
	public final static String VERSION = "0.1.R3.I18N";
	/**
	 * Program build number. Build increments on every built by Eclipse by now.
	 * This is programmed in Ant script. Build number stored in file
	 * "src/build".
	 */
	public final static String BUILD = ResourceBundle.getBundle("mybuild")
			.getString("build.number");

	/**
	 * Program last name (version info), formed as " v." + {@value #VERSION} +
	 * " dev.b." + {@value #BUILD}.
	 */
	public final static String PROGRAM_NAME_LAST = " v." + Const.VERSION + " dev.b." + Const.BUILD;

	/**
	 * Full name of program, name and version: {@link #PROGRAM_NAME_FIRST} +
	 * {@link #PROGRAM_NAME_LAST}.
	 */
	public final static String PROGRAM_NAME_FULL = PROGRAM_NAME_FIRST + PROGRAM_NAME_LAST;

	/**
	 * HTTP link to project. Now it {@link #WEB_PROJECT_PAGE}.
	 */
	public final static String WEB_PROJECT_PAGE = "http://code.google.com/p/pattern-analytics-at-single-surface/";

	/**
	 * HTTP link to help page. Now it {@value #WEB_HELP_PAGE}.
	 */
	public final static String WEB_HELP_PAGE = "http://code.google.com/p/pattern-analytics-at-single-surface/w/list";

	/**
	 * List of used libraries. This is list builded by Ant script and stored in
	 * file "src/used-libs".
	 */
	public final static String USED_LIBRARIES = ResourceBundle.getBundle("used-libs").getString(
			"libraries");

}
