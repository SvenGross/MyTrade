package formBeans;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import model.Benutzer;
import model.KonstantenSession;
import dao.BenutzerDAO;
import error.Meldungen;

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
	private Benutzer benutzer;

	private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	private Map<String, Object> sessionMap = externalContext.getSessionMap();
	
	public String vorschau() {

		setAdministrator();

		benutzer = new Benutzer(name, vorname, login, passwort, administrator, kontostand);
		
		return "benutzerErfassenVorschau?faces-redirect=true";

	}
	

	public String backToBenutzerErfassen(){
		
		return "benutzerErfassen?faces-redirect=true";
		
	}
	
	public String saveUser() {
		
		BenutzerDAO benutzerDAO = new BenutzerDAO();
		if (benutzerDAO.addUser(benutzer)){		
			sessionMap.put(KonstantenSession.MELDUNG, Meldungen.BENUTZER_ANLEGEN + benutzer.getBenutzername());
		}
		
		return "administration?faces-redirect=true";
		
	}

	public String backToAdministration() {
		
		return "administration?faces-redirect=true";
		
	}

	public void setAdministrator(){
		
		if (rolle.equals("Administrator")){
			administrator = true;
		} else {
			administrator = false;
		}
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
	
	public Benutzer getBenutzer() {
		return benutzer;
	}
	
	public void setBenutzer(Benutzer benutzer) {
		this.benutzer = benutzer;
	}
}