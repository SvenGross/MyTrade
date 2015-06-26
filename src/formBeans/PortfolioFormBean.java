package formBeans;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import model.Aktie;
import model.Benutzer;
import model.KonstantenSession;
import dao.BenutzerDAO;


@ManagedBean
@SessionScoped
public class PortfolioFormBean {

	private double guthaben;
	private ArrayList<Aktie> aktienListe;
	
	public void verkaufen(){
	
		//sell some stocks bitch
		
	}
	
	public ArrayList<Aktie> getAuftragsListe() {
		return aktienListe;
	}

	public void setAuftragsListe(ArrayList<Aktie> auftragsListe) {
		this.aktienListe = auftragsListe;
	}

	public double getGuthaben() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		Benutzer benutzer = (Benutzer) sessionMap.get(KonstantenSession.ANGEMELDETER_BENUTZER);
		
		BenutzerDAO benutzerDAO = new BenutzerDAO();
		return benutzerDAO.getKontostand(benutzer.getBenutzerID());
	}

	public void setGuthaben(double guthaben) {
		this.guthaben = guthaben;
	}
	
}
