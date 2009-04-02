/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)UserNotFoundException.java 1.0 01.04.2009
 */

package edu.mgupi.pass.db;

import java.sql.SQLException;

public class UserNotFoundException extends SQLException {

	private static final long serialVersionUID = -3551501247308669100L;

	public UserNotFoundException(String message) {
		super(message);
	}

}
