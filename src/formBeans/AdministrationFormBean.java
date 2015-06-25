package formBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class AdministrationFormBean {

	public String aktieErfassen() {	return "aktieErfassen?faces-redirect=true"; }
	public String benutzerErfassen() {	return "benutzerErfassen?faces-redirect=true"; }
	public String dividendeAusschuetten() {
		return null;	//TODO
	}
}
