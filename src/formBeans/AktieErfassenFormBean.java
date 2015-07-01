package formBeans;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import dao.AktieDAO;
import error.Meldungen;
import model.Aktie;
import model.KonstantenSession;

@ManagedBean
@SessionScoped
public class AktieErfassenFormBean {

	private boolean fehler = false; 
	private String 	name;
	private String 	symbol;
	private double 	preis;
	private double 	dividende;
	private int		stueck;
	private Aktie 	neueAktie;
	
	public String backToAdministration() {

		return "administration?faces-redirect=true";
	}

	public String vorschau() {

		neueAktie = new Aktie(name, symbol, preis, dividende, stueck);		
		
		return "neueAktieVorschau?faces-redirect=true";

	}
	
	public String backToAktieErfassen() {
		
		return "aktieErfassen?faces-redirect=true";
		
	}
	
	public String save() {
		
		AktieDAO neuesAktienDAO = new AktieDAO();
		if (neuesAktienDAO.addAktie(neueAktie)) {

			//Meldung "Aktie erfasst" Hinzufügen!!!!!!

			ExternalContext externalContext = FacesContext.getCurrentInstance()
					.getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			
			sessionMap.put(KonstantenSession.FEHLER_MELDUNG, Meldungen.AKTIE_ERSTELLEN);
			
			return "administration?faces-redirect=true";
		}else{
			
			//Meldung "Aktie konnte nicht erfasst werden oder so" Hinzufügen!!!!!!
			return "aktieErfassen?faces-redirect=true";
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public double getPreis() {
		return preis;
	}

	public void setPreis(double preis) {
		this.preis = preis;
	}

	public double getDividende() {
		return dividende;
	}

	public void setDividende(double dividende) {
		this.dividende = dividende;
	}

	public int getStueck() {
		return stueck;
	}

	public void setStueck(int stueck) {
		this.stueck = stueck;
	}

	public Aktie getNeueAktie() {
		return neueAktie;
	}

	public void setNeueAktie(Aktie neueAktie) {
		this.neueAktie = neueAktie;
	}
	
	public boolean isFehler() {
		return fehler;
	}
	
	public void setFehler(boolean fehler) {
		this.fehler = fehler;
	}
}