/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)Messages.java 1.0 30.03.2009
 */

package edu.mgupi.pass.face.gui;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import edu.mgupi.pass.util.Const;

/**
 * Multi-language support class.
 * 
 * @author raidan
 * 
 */
public class Messages {

	private static final String BUNDLE_NAME = "edu.mgupi.pass.face.gui.messages";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {

			if (!Const.PRODUCTION_MODE) {
				AppHelper.showErrorDialog(null, "Не найден ресурс '" + key + "'.");
				throw e;
			}
			return '!' + key + '!';
		}
	}

	public static String getString(String key, Object... arguments) {
		return MessageFormat.format(getString(key), arguments);
	}
}
