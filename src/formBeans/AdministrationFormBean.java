package formBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class AdministrationFormBean {

	private boolean fehler = false; 

	public boolean isFehler() {
		return fehler;
	}
	
	public void setFehler(boolean fehler) {
		this.fehler = fehler;
	}
	
	public String aktieErfassen() {	return "aktieErfassen.xhtml"; }
	public String benutzerErfassen() {	return "benutzerErfassen.xhtml"; }
	public String dividendeAusschuetten() {
		return null;	//TODO
	}
}
