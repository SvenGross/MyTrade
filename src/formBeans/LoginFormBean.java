package formBeans;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import model.Benutzer;
import dao.BenutzerDAO;

@ManagedBean
@SessionScoped
public class LoginFormBean {

	private String benutzername;
	private String passwort;
	private Integer benutzerID;

	public String anmelden() {

		BenutzerDAO benutzerDAO = new BenutzerDAO();
		benutzerID = benutzerDAO.logindatenPruefen(benutzername, passwort);
		
		if(benutzerID != null) {
			
			benutzerInDieSessionMap(benutzerDAO.getUserDataByID(benutzerID));
			
			if(benutzerDAO.getUserDataByID(benutzerID).isAdministrator())
			{

				System.err.println("BENUTZERDAO administration");
				return "administration?faces-redirect=true";
			}
			else 
			{

				System.err.println("BENUTZERDAO portfolio");
				return "portfolio?faces-redirect=true";
			}
		
		} else {
			
			return "login?faces-redirect=true";
		}
	}
	
	public String abmelden() {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		sessionMap.remove("angemeldeterBenutzer");
		
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "login?faces-redirect=true";
	}

	private void benutzerInDieSessionMap(Benutzer angemeldeterBenutzer) {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance()
				.getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		sessionMap.put("angemeldeterBenutzer", angemeldeterBenutzer);
	}

	public String getUser() {
		return benutzername;
	}

	public void setUser(String benutzername) {
		this.benutzername = benutzername;
	}

	public String getPassword() {
		return passwort;
	}

	public void setPassword(String passwort) {
		this.passwort = passwort;
	}
}