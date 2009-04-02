/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)DBHelper.java 1.0 01.04.2009
 */

package edu.mgupi.pass.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.orm.PersistentException;

import edu.mgupi.pass.db.surfaces.PassPersistentManager;
import edu.mgupi.pass.util.Config;
import edu.mgupi.pass.util.Utils;

public class DBHelper {
	public static synchronized void changePassword(String oldPassword, String newPassword)
			throws SQLException, PersistentException {

		Connection conn = PassPersistentManager.instance().getSession().connection();

		PreparedStatement ps = conn
				.prepareStatement("select Password, password(?) from mysql.user where User = ?");
		Statement st = null;
		ResultSet rs = null;
		try {
			String username = Config.getInstance().getLogin();
			ps.setString(1, oldPassword);
			ps.setString(2, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				String currentPass = rs.getString(1);
				String testPass = rs.getString(2);
				if (!Utils.equals(currentPass, testPass)) {
					throw new PasswordNotEqualsException();
				}
				st = PassPersistentManager.instance().getSession().connection().createStatement();
				st.execute("set password for " + username + " = password('" + newPassword + "')");
			} else {
				throw new UserNotFoundException(Config.getInstance().getLogin());
			}
		} finally {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
			ps.close();
		}
	}
}
