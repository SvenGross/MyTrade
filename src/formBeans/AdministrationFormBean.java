package formBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class AdministrationFormBean {

	public String aktieErfassen() {	return "aktieErfassen.xhtml"; }
	public String benutzerErfassen() {	return "benutzerErfassen.xhtml"; }
	public String dividendeAusschuetten() {
		return null;	//TODO
	}
}
