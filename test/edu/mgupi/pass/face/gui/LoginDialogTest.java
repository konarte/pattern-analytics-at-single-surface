/*
 * Pattern Analytics at Single Surface
 * @(#)LoginDialogTest.java	1.0 02.04.2009
 */

package edu.mgupi.pass.face.gui;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginDialogTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoginDialog() throws Exception {
		LoginWindow login = new LoginWindow(null);
		
		SwingTestHelper.showMeBackground(login);
	}

}
