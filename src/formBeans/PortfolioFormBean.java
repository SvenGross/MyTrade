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

	private Map<String, Object> sessionMap = null;
	private double kontostand;
	private ArrayList<Aktie> aktienListe;
	
	public PortfolioFormBean() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		sessionMap = externalContext.getSessionMap();
	}
	
	public void verkaufen(){
	
		//sell some stocks bitch
		
	}
	
	public ArrayList<Aktie> getAuftragsListe() {
		return aktienListe;
	}

	public void setAuftragsListe(ArrayList<Aktie> auftragsListe) {
		this.aktienListe = auftragsListe;
	}

	public double getKontostand() {
		
		Benutzer benutzer = (Benutzer) sessionMap.get(KonstantenSession.ANGEMELDETER_BENUTZER);
		benutzer.getKontostand();
		
		
		
		
		
		
		
		return kontostand;
	}

	public void setKontostandn(double kontostand) {
		this.kontostand = kontostand;
	}
	
}
