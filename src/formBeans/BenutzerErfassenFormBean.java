package formBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.Benutzer;
import dao.BenutzerDAO;

@ManagedBean
@SessionScoped
public class BenutzerErfassenFormBean {

	private String name;
	private String vorname;
	private String login;
	private String passwort;
	private String rolle;
	private boolean administrator;
	private double kontostand = 10000;

	public String save() {

		setAdministrator();
		Benutzer benutzer = new Benutzer(name, vorname, login, passwort, administrator, kontostand);
		BenutzerDAO benutzerDAO = new BenutzerDAO();
		if (benutzerDAO.benutzerSpeichern(benutzer)){
			//gib message successful
		} else{
			//same same but different
		}
		
		return "administration?faces-redirect=true";

	}

	public void setAdministrator(){
		
		if (rolle.equals("Administrator")){
			administrator = true;
		} else {
			administrator = false;
		}
	}
	
	public String back() {
		
		return "administration?faces-redirect=true";
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public String getRolle() {
		return rolle;
	}

	public void setRolle(String rolle) {
		this.rolle = rolle;
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
