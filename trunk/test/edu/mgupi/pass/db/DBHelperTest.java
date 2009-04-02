/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)DBHelperTest.java 1.0 02.04.2009
 */

package edu.mgupi.pass.db;

import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.orm.PersistentException;

public class DBHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testChangePassword() throws SQLException, PersistentException {
		try {
			DBHelper.changePassword("ad", "adesroot");
			fail("No PasswordNotEqualsException thrown!");
		} catch (PasswordNotEqualsException pne) {
			System.out.println("Received expected exception: " + pne);
		}
		DBHelper.changePassword("adesroot", "adesroot");
	}

}
