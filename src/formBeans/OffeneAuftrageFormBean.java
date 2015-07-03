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

	private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	private Map<String, Object> sessionMap = externalContext.getSessionMap();

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
		auftragDAO.auftragStornieren(auftragsID);
		sessionMap.put(KonstantenSession.MELDUNG, Meldungen.AUFTRAG_STORNIEREN);
	}
}