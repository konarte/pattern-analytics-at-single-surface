package edu.mgupi.pass.util;

import java.util.ResourceBundle;

/**
 * Common constants
 * 
 * @author raidan
 * 
 */
public class Const {
	public final static int THUMB_WIDTH = 256;
	public final static int THUMB_HEIGHT = 256;

	public final static int MAIN_IMAGE_WIDTH = 1024;
	public final static int MAIN_IMAGE_HEIGHT = 1024;

	public final static int MAIN_WINDOW_MINIMUM_WIDTH = 400;
	public final static int MAIN_WINDOW_MINIMUM_HEIGHT = 300;

	public final static String PROGRAM_NAME = "Pattern Analytics at Single Surface";
	public final static String VERSION = "0.1.R1";
	public final static String BUILD = ResourceBundle.getBundle("mybuild").getString("build.number");

	public final static String PROGRAM_NAME_LAST = " v." + Const.VERSION + " dev.b." + Const.BUILD;
	public final static String PROGRAM_NAME_FULL = PROGRAM_NAME + PROGRAM_NAME_LAST;

	public final static String WEB_PROJECT_PAGE = "http://code.google.com/p/pattern-analytics-at-single-surface/";
	public final static String WEB_HELP_PAGE = "http://code.google.com/p/pattern-analytics-at-single-surface/w/list";
	public final static String USED_LIBRARIES = ResourceBundle.getBundle("used-apps").getString("libraries");

}
