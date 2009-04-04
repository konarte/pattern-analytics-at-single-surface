/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)Messages.java 1.0 31.03.2009
 */

package edu.mgupi.pass.face.gui.forms;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import edu.mgupi.pass.util.MessagesImpl;

/**
 * Multi-language support class.
 * 
 * @author raidan
 * 
 */
public class Messages {

	private static final String BUNDLE_NAME = "edu.mgupi.pass.face.gui.forms.messages";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
	}

	public static String getString(String key) {
		return MessagesImpl.getString(RESOURCE_BUNDLE, key);
	}

	public static String getString(String key, Object... arguments) {
		return MessageFormat.format(getString(key), arguments);
	}
}
