/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)Messages.java 1.0 31.03.2009
 */

package edu.mgupi.pass.inputs.gui;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import edu.mgupi.pass.face.gui.AppHelper;

public class Messages {
	private static final String BUNDLE_NAME = "edu.mgupi.pass.inputs.gui.messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {

			AppHelper.showErrorDialog(null, "Не найден ресурс '" + key + "'.");
			throw e;
		}
	}

	public static String getString(String key, Object... arguments) {
		return MessageFormat.format(getString(key), arguments);
	}
}
