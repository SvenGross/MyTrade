package formBeans;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import model.Aktie;
import model.Benutzer;
import model.KonstantenSession;
import dao.AktieDAO;


@ManagedBean
@SessionScoped
public class PortfolioFormBean {

	private Map<String, Object> sessionMap = null;
	private boolean fehler;
	
	public PortfolioFormBean() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		sessionMap = externalContext.getSessionMap();
	}
	
	public String verkaufen(Integer aktienID) {
		sessionMap.put(KonstantenSession.AUFTRAG_AKTIENID, aktienID);
		return "auftragErfassen?faces-redirect=true";
	}
	
	public ArrayList<Aktie> getAktienListe() {
		AktieDAO aktieDAO = new AktieDAO();
		return aktieDAO.selectAlleAktienVonBenutzer();
	}

	public String getKontostand() {
		Benutzer benutzer = (Benutzer) sessionMap.get(KonstantenSession.ANGEMELDETER_BENUTZER);
		return new DecimalFormat("#.00").format(benutzer.getKontostand());
	}
	
	public boolean isFehler() {
		return fehler;
	}
	
	public void setFehler(boolean fehler) {
		this.fehler = fehler;
	}	
}
