package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Benutzer;

/**
 * @version 0.1 (May 29, 2015)
 * @author Sven Gross
 */
public class BenutzerDAO extends MyTradeDAO {

	private Connection con = null;
	Statement stmt;
	ResultSet rs = null;

	public Integer logindatenPruefen(String benutzername, String passwort) {

		try {

			con = getConnection();
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("SELECT userID FROM users WHERE username = '"
							+ benutzername + "' AND password = SHA1('"
							+ passwort + "')");

			Integer benutzerID = null;
			int count = 0;

			// Überprüft ob nur genau 1 Benutzer mit den Credentials vorhanden
			// ist, andernfalls Datenbank inkonsestent oder SQLInjection
			while (rs.next()) {

				count++;

				if (count > 1) {
					rs.close();
					stmt.close();
					returnConnection(con);
					return null;
				}

				benutzerID = rs.getInt("userID");

			}

			rs.close();
			stmt.close();
			returnConnection(con);
			return benutzerID;

		} catch (Exception e) {
			System.err
					.println("FEHLER:     dao.BenutzerDAO     Es ist ein Fehler in der Methode 'logindatenPruefen' aufgetreten.");
			e.printStackTrace();
			returnConnection(con);
			return null;
		}

	}

	public boolean addUser(Benutzer benutzer) {

		String sqlBenutzerHinzufügen = "INSERT INTO users"
				+ "(firstname, lastname, username, password, administrator, account_balance) VALUES"
				+ "(?,?,?,?)";

		Integer accountBalance = new Integer(null);
		int administrator;
		
		if (benutzer.isAdministrator() == false){
			accountBalance = 10000;
			administrator = 0;
		} else {
			accountBalance = null;
			administrator = 1;
		}
		
		con = getConnection();

		try {

			PreparedStatement preparedStatement = con.prepareStatement(sqlBenutzerHinzufügen);
			
			preparedStatement.setString(1, benutzer.getVorname());
			preparedStatement.setString(2, benutzer.getNachname());
			preparedStatement.setString(3, benutzer.getBenutzername());
			preparedStatement.setString(4, benutzer.getPasswort());
			preparedStatement.setInt(5, administrator);
			preparedStatement.setInt(6, accountBalance);

			preparedStatement.executeUpdate();

			returnConnection(con);
			preparedStatement.close();
		} catch (SQLException e) {
			System.err
			.println("FEHLER:     dao.BenutzerDAO     Es ist ein Fehler in der Methode 'addUser' aufgetreten.");
			e.printStackTrace();
		}

		return true;
	}

	public Benutzer getUserDataByID(int userID) {

		try {

			Benutzer benutzer = null;

			con = getConnection();
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("SELECT userID, firstname, lastname, username, password, administrator, account_balance FROM users WHERE userID = '"
							+ userID + "'");

			while (rs.next()) {

				String benutzerID = rs.getString("userID");
				String vorname = rs.getString("firstname");
				String nachname = rs.getString("lastname");
				String benutzername = rs.getString("username");
				String passwort = rs.getString("password");
				boolean administrator = rs.getBoolean("administrator");
				double kontostand = rs.getDouble("account_balance");

				benutzer = new Benutzer(benutzerID, vorname, nachname,
						benutzername, passwort, administrator, kontostand);
				return benutzer;

			}

			rs.close();
			stmt.close();
			returnConnection(con);
			return benutzer;

		} catch (Exception e) {
			System.err
					.println("FEHLER:     dao.BenutzerDAO     Es ist ein Fehler in der Methode 'getUserDataByID' aufgetreten.");
			e.printStackTrace();
			returnConnection(con);
			return null;
		}

	}

	public boolean benutzerHinzufuegen(Benutzer benutzer) {

		try {

			String vorname = benutzer.getVorname();
			String nachname = benutzer.getNachname();
			String benutzername = benutzer.getBenutzername();
			String passwort = benutzer.getPasswort();
			boolean administrator = benutzer.isAdministrator();
			double kontostand = benutzer.getKontostand();

			con = getConnection();
			stmt = con.createStatement();

			stmt.execute("INSERT INTO users (firstname, lastname, username, password, administrator, account_balance) "
					+ "VALUES ('"
					+ vorname
					+ "', '"
					+ nachname
					+ "', '"
					+ benutzername
					+ "', SHA1('"
					+ passwort
					+ "'), '"
					+ administrator + "', " + kontostand + ")");

			rs.close();
			stmt.close();
			returnConnection(con);
			return true;

		} catch (Exception e) {
			System.err
					.println("FEHLER:     dao.BenutzerDAO     Es ist ein Fehler in der Methode 'benutzerSpeichern' aufgetreten.");
			e.printStackTrace();

			returnConnection(con);
			return false;
		}
	}

	public ArrayList<Benutzer> getAllUsers() {

		try {

			ArrayList<Benutzer> alleBenutzer = new ArrayList<Benutzer>();

			con = getConnection();
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("SELECT userID, firstname, lastname, username, password, administrator, account_balance FROM users");

			while (rs.next()) {

				String benutzerID = rs.getString("userID");
				String vorname = rs.getString("firstname");
				String nachname = rs.getString("lastname");
				String benutzername = rs.getString("username");
				String passwort = rs.getString("password");
				boolean administrator = rs.getBoolean("administrator");
				double kontostand = rs.getDouble("account_balance");

				Benutzer benutzer = new Benutzer(benutzerID, vorname, nachname,
						benutzername, passwort, administrator, kontostand);
				alleBenutzer.add(benutzer);

			}

			rs.close();
			stmt.close();
			returnConnection(con);
			return alleBenutzer;

		} catch (Exception e) {
			System.err
					.println("FEHLER:     dao.BenutzerDAO     Es ist ein Fehler in der Methode 'getAllUsers' aufgetreten.");
			e.printStackTrace();
			returnConnection(con);
			return null;
		}

	}

	public double getKontostand(int userID) {

		try {

			double kontostand = 0;

			con = getConnection();
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("SELECT account_balance FROM users WHERE userID = '"
							+ userID + "'");

			while (rs.next()) {
				kontostand = rs.getDouble("account_balance");
			}

			rs.close();
			stmt.close();
			returnConnection(con);
			return kontostand;

		} catch (Exception e) {
			System.err
					.println("FEHLER:     dao.BenutzerDAO     Es ist ein Fehler in der Methode 'getUserDataByID' aufgetreten.");
			e.printStackTrace();
			returnConnection(con);
			return 0;
		}

	}

}
