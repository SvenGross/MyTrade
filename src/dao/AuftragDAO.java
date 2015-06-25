package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AuftragDAO extends MyTradeDAO {

	private Connection con = null;
	Statement stmt;
	ResultSet rs = null;

	public void saveAuftrag(String name, double preis, int stueck) {

		con = getConnection();

//		try {
//
//			stmt = con.createStatement();
//			rs = stmt
//					.executeQuery("SELECT userID, firstname, lastname, username, administrator, account_balance FROM users WHERE username = '"
//							+ benutzername
//							+ "' AND password = SHA1('"
//							+ passwort + "')");
//
//			while (rs.next()) {
//
//				userData = new String[6];
//				userData[0] = rs.getString("userID");
//				userData[1] = rs.getString("firstname");
//				userData[2] = rs.getString("lastname");
//				userData[3] = rs.getString("username");
//				userData[4] = rs.getString("administrator");
//				userData[5] = rs.getString("account_balance");
//
//			}
//
//		} catch (Exception e) {
//			System.err
//					.println("dao.LoginDAO     : Die SQL-Abfrage konnte nicht ausgeführt werden.");
//			e.printStackTrace();
//		}
	}
}
