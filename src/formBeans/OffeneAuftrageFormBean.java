package formBeans;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import model.Auftrag;
import model.Benutzer;
import model.KonstantenSession;
import dao.AuftragDAO;
import error.Meldungen;

@ManagedBean
@SessionScoped
public class OffeneAuftrageFormBean {

	private Map<String, Object> sessionMap = null;
	private boolean fehler;
	
	public OffeneAuftrageFormBean() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		sessionMap = externalContext.getSessionMap();
	}

	public String getKontostand() {
		Benutzer benutzer = (Benutzer) sessionMap.get(KonstantenSession.ANGEMELDETER_BENUTZER);
		return new DecimalFormat("#.00").format(benutzer.getKontostand());
	}

	public ArrayList<Auftrag> getAuftragsListe() {
		AuftragDAO auftragDAO = new AuftragDAO();
		return auftragDAO.selectAlleAuftraegeVonBenutzer();
	}
	
	public void auftragStornieren(int auftragsID) {
		AuftragDAO auftragDAO = new AuftragDAO();
		if(!auftragDAO.auftragStornieren(auftragsID)) {
			fehler = true;
		}
		
		ExternalContext externalContext = FacesContext.getCurrentInstance()
				.getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		sessionMap.put(KonstantenSession.MELDUNG,
				Meldungen.AUFTRAG_STORNIEREN);
	}
	
	public boolean isFehler() {
		return fehler;
	}
	
	public void setFehler(boolean fehler) {
		this.fehler = fehler;
	}
}