/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)UserNotFoundException.java 1.0 01.04.2009
 */

package edu.mgupi.pass.db;

import java.sql.SQLException;

public class UserNotFoundException extends SQLException {

	public UserNotFoundException(String message) {
		super(message);
	}

}
