package edu.mgupi.pass.util;

import java.util.ResourceBundle;

public class Const {
	public final static int THUMB_WIDTH = 256;
	public final static int THUMB_HEIGHT = 256;

	public final static String PROGRAM_NAME = "Pattern Analytics at Single Surface";
	public final static String PROGRAM_VERSION = "0.1";

	public final static int MAIN_IMAGE_WIDTH = 1024;
	public final static int MAIN_IMAGE_HEIGHT = 1024;

	public final static int MAIN_WINDOW_MINIMUM_WIDTH = 400;
	public final static int MAIN_WINDOW_MINIMUM_HEIGHT = 300;

	public final static String VERSION = ResourceBundle.getBundle("app").getString("version");
	public final static String BUILD = ResourceBundle.getBundle("mybuild").getString("build.number");
}
