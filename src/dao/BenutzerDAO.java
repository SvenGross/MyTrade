package dao;

import java.sql.Connection;
import java.sql.ResultSet;
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
			rs = stmt.executeQuery("SELECT userID FROM users WHERE username = '" + benutzername + "' AND password = SHA1('" + passwort + "')");
			
			Integer benutzerID = null;
			int count = 0;
			
			//Überprüft ob nur genau 1 Benutzer mit den Credentials vorhanden ist, andernfalls Datenbank inkonsestent oder SQLInjection
			while (rs.next()) {
				
				count++;
				
				if (count > 1) {
					System.out.println("Es gibt mehr als einen Benutzer: " + benutzername);
					return null;
				}
				
				benutzerID = rs.getInt("userID");
				
			}
			
			return benutzerID;
			
		} catch (Exception e) {
			System.err.println("FEHLER:     dao.BenutzerDAO     Es ist ein Fehler in der Methode 'logindatenPruefen' aufgetreten.");
			e.printStackTrace();
		}
		
		returnConnection(con);
		return null;
		
	}
	
	public Benutzer getUserDataByID(int userID) {

		Benutzer benutzer = null;
		
		try {
			
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT userID, firstname, lastname, username, password, administrator, account_balance FROM users WHERE userID = '" + userID + "'");
			
			while(rs.next()) {
				
				int benutzerID        = rs.getInt("userID");
				String vorname        = rs.getString("firstname");
				String nachname       = rs.getString("lastname");
				String benutzername   = rs.getString("username");
				String passwort       = rs.getString("password");
				boolean administrator = rs.getBoolean("administrator");
				double kontostand     = rs.getDouble("account_balance");
				
				benutzer = new Benutzer(benutzerID, vorname, nachname, benutzername, passwort, administrator, kontostand);
				return benutzer;
				
			}
			
		} catch (Exception e) {
			System.err.println("FEHLER:     dao.BenutzerDAO     Es ist ein Fehler in der Methode 'getUserDataByID' aufgetreten.");
			e.printStackTrace();
		}
		
		returnConnection(con);
		return benutzer;
		
	}
	
	public boolean benutzerHinzufuegen(Benutzer benutzer) {
		
		try {
			
			String vorname        = benutzer.getVorname();
			String nachname       = benutzer.getNachname();
			String benutzername   = benutzer.getBenutzername();
			String passwort       = benutzer.getPasswort();
			boolean administrator = benutzer.isAdministrator();
			double kontostand     = benutzer.getKontostand();
			
			con = getConnection();
			stmt = con.createStatement();
			stmt.execute("INSERT INTO users (firstname, lastname, username, password, administrator, account_balance) " +
				"VALUES ('" + vorname + "', '" + nachname + "', '" + benutzername + "', SHA1('" + passwort + "'), '" + administrator + "', " + kontostand + ")");
			
			return true;
			
		} catch (Exception e) {
			System.err.println("FEHLER:     dao.BenutzerDAO     Es ist ein Fehler in der Methode 'benutzerHinzufuegen' aufgetreten.");
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<Benutzer> getAllUsers() {

		ArrayList<Benutzer> alleBenutzer = null;
		
		try {
			
			alleBenutzer = new ArrayList<Benutzer>();
			
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT userID, firstname, lastname, username, password, administrator, account_balance FROM users");
			
			while(rs.next()) {
				
				int benutzerID        = rs.getInt("userID");
				String vorname        = rs.getString("firstname");
				String nachname       = rs.getString("lastname");
				String benutzername   = rs.getString("username");
				String passwort       = rs.getString("password");
				boolean administrator = rs.getBoolean("administrator");
				double kontostand     = rs.getDouble("account_balance");
				
				Benutzer benutzer = new Benutzer(benutzerID, vorname, nachname, benutzername, passwort, administrator, kontostand);
				alleBenutzer.add(benutzer);
				
			}
			
			return alleBenutzer;
			
		} catch (Exception e) {
			System.err.println("FEHLER:     dao.BenutzerDAO     Es ist ein Fehler in der Methode 'getAllUsers' aufgetreten.");
			e.printStackTrace();
		}
		
		returnConnection(con);
		return alleBenutzer;
		
	}
	
	public double getKontostand(int userID) {

		double kontostand = 0;
		
		try {
			
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT account_balance FROM users WHERE userID = '" + userID + "'");
			
			while(rs.next()) {
				kontostand = rs.getDouble("account_balance");
			}
			
		} catch (Exception e) {
			System.err.println("FEHLER:     dao.BenutzerDAO     Es ist ein Fehler in der Methode 'getUserDataByID' aufgetreten.");
			e.printStackTrace();
		}
		
		returnConnection(con);
		return kontostand;
		
	}
	
}
