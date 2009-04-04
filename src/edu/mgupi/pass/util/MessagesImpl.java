/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)MessagesImpl.java 1.0 04.04.2009
 */

package edu.mgupi.pass.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import edu.mgupi.pass.face.gui.AppHelper;

public class MessagesImpl {

	public final static String KEY_PREFIX = "key:";

	public static String getString(ResourceBundle resourceBundle, String key) {
		return getStringImpl(resourceBundle, key, false);
	}

	private static String getStringImpl(ResourceBundle resourceBundle, String key,
			boolean keyResource) {
		try {
			String result = resourceBundle.getString(key);
			if (result.startsWith(KEY_PREFIX) && !keyResource) {
				result = getStringImpl(resourceBundle, result.substring(KEY_PREFIX.length()), true);
			}
			return result;
		} catch (MissingResourceException e) {
			AppHelper.showErrorDialog(null, "Не найден ресурс '" + key + "'.");
			throw e;
		}
	}
}
