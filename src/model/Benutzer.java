package model;

import dao.BenutzerDAO;

/**
 * @date 25.6.2015 - 3.7.2015
 * @author sacha weiersmueller, siro duschletta und sven gross
 */
public class Benutzer {

	private String benutzerID = null;
	private String vorname;
	private String nachname;
	private String benutzername;
	private String passwort;
	private boolean administrator;
	private double kontostand;
	
	public Benutzer(String nachname, String vorname, String benutzername, String passwort, boolean administrator, double kontostand) {
		
		this.vorname = vorname;
		this.nachname = nachname;
		this.benutzername = benutzername;
		this.passwort = passwort;
		this.administrator = administrator;
		this.kontostand = kontostand;
		
	}
	
	public Benutzer(String benutzerID, String vorname, String nachname, String benutzername, String passwort, boolean administrator, double kontostand) {
		
		this.benutzerID = benutzerID;
		this.vorname = vorname;
		this.nachname = nachname;
		this.benutzername = benutzername;
		this.passwort = passwort;
		this.administrator = administrator;
		this.kontostand = kontostand;
		
	}

	public String getBenutzerID() {
		return benutzerID;
	}
	
	public void setBenutzerID(String benutzerID) {
		this.benutzerID = benutzerID;
	}

	public int getBenutzerIDAsInt() {
		if(benutzerID.isEmpty()) {
			return 0;
		}
		else {
			return Integer.parseInt(benutzerID);
		}
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getBenutzername() {
		return benutzername;
	}

	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public boolean isAdministrator() {
		return administrator;
	}

	public void setAdministrator(boolean administrator) {
		this.administrator = administrator;
	}

	public double getKontostand() {
		BenutzerDAO benutzerDAO = new BenutzerDAO();
		kontostand = benutzerDAO.getKontostand(getBenutzerIDAsInt());
		return kontostand;
	}

	public void setKontostand(double kontostand) {
		this.kontostand = kontostand;
	}
	
}
