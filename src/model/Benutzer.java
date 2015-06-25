package model;

public class Benutzer {

	private int benutzerID;
	private String vorname;
	private String nachname;
	private String benutzername;
	private String passwort;
	private boolean administrator;
	private double kontostand;
	
	public Benutzer(String vorname, String nachname, String benutzername, String passwort, boolean administrator, double kontostand) {
		
		this.vorname = vorname;
		this.nachname = nachname;
		this.benutzername = benutzername;
		this.passwort = passwort;
		this.administrator = administrator;
		this.kontostand = kontostand;
		
	}
	
	public Benutzer(int benutzerID, String vorname, String nachname, String benutzername, String passwort, boolean administrator, double kontostand) {
		
		this.benutzerID = benutzerID;
		this.vorname = vorname;
		this.nachname = nachname;
		this.benutzername = benutzername;
		this.passwort = passwort;
		this.administrator = administrator;
		this.kontostand = kontostand;
		
	}

	public int getBenutzerID() {
		return benutzerID;
	}
	
	public void setBenutzerID(int userID) {
		this.benutzerID = userID;
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
		return kontostand;
	}

	public void setKontostand(double kontostand) {
		this.kontostand = kontostand;
	}
	
}
