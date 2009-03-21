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

	public final static String LAST_PROGRAM_NAME = " v." + Const.VERSION + " dev.b." + Const.BUILD;
	public final static String FULL_PROGRAM_NAME = PROGRAM_NAME + LAST_PROGRAM_NAME;

}
