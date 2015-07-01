package formBeans;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import model.KonstantenSession;

@ManagedBean
@SessionScoped
public class AdministrationFormBean {
	
	public String aktieErfassen() {	return "aktieErfassen.xhtml"; }
	public String benutzerErfassen() {	return "benutzerErfassen.xhtml"; }
	public void dividendeAusschuetten() {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance()
				.getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		sessionMap.put(KonstantenSession.FEHLER_MELDUNG, "test");
	}
}
