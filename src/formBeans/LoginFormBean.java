package formBeans;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import model.Benutzer;
import model.KonstantenSession;
import dao.BenutzerDAO;

/**
 * @date 25.6.2015 - 3.7.2015
 * @author sacha weiersmueller, siro duschletta und sven gross
 */
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

		sessionMap.remove(KonstantenSession.ANGEMELDETER_BENUTZER);
		
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "login?faces-redirect=true";
	}

	private void benutzerInDieSessionMap(Benutzer angemeldeterBenutzer) {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance()
				.getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		sessionMap.put(KonstantenSession.ANGEMELDETER_BENUTZER, angemeldeterBenutzer);
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
}
