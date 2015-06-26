package formBeans;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import model.Auftrag;
import model.Benutzer;
import model.KonstantenSession;

@ManagedBean
@SessionScoped
public class OffeneAuftrageFormBean {

	private ArrayList<Auftrag> auftragsListe;
	
	public ArrayList<Auftrag> getAuftragsListe() {
		return auftragsListe;
	}

	public void setAuftragsListe(ArrayList<Auftrag> auftragsListe) {
		this.auftragsListe = auftragsListe;
	}

	public double getKontostand() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		Benutzer benutzer = (Benutzer) sessionMap.get(KonstantenSession.ANGEMELDETER_BENUTZER);
		return benutzer.getKontostand();
	}
	
}