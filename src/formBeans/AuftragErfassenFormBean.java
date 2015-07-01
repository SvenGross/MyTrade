package formBeans;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import model.Aktie;
import model.KonstantenSession;

@ManagedBean
@SessionScoped
public class AuftragErfassenFormBean {

	private Map<String, Object> sessionMap = null;
	private String formTitle = "";
	private boolean fehler;

	private boolean auftragVerkauf = false;
	private ArrayList<Aktie> aktienListe;
	private Integer aktienID = null;
	private String name;
	private double preis;
	private int stueck;
	
	public AuftragErfassenFormBean() {
		init();
	}

	private void init() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		sessionMap = externalContext.getSessionMap();
		System.out.println("HERE");
		aktienID = (Integer) sessionMap.get(KonstantenSession.AUFTRAG_AKTIENID);
		if(aktienID != null) {
			setAuftragVerkauf(true);
			formTitle = "VERKAUFSAUFTRAG";
		}
		else {
			setAuftragVerkauf(false);
			formTitle = "KAUFAUFTRAG";
		}
		sessionMap.remove(KonstantenSession.AUFTRAG_AKTIENID);
	}
	
	public String back() {
		return "portfolio?faces-redirect=true";
	}

	public void save() {
		System.out.println(auftragVerkauf);
	}

	public Map<String, Object> getSessionMap() {
		return sessionMap;
	}

	public void setSessionMap(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}

	public String getFormTitle() {
		init();
		return formTitle;
	}

	public void setFormTitle(String formTitle) {
		this.formTitle = formTitle;
	}

	public boolean isFehler() {
		return fehler;
	}

	public void setFehler(boolean fehler) {
		this.fehler = fehler;
	}

	public boolean isAuftragVerkauf() {
		return auftragVerkauf;
	}

	public void setAuftragVerkauf(boolean auftragVerkauf) {
		this.auftragVerkauf = auftragVerkauf;
	}

	public ArrayList<Aktie> getAktienListe() {
		return aktienListe;
	}

	public void setAktienListe(ArrayList<Aktie> aktienListe) {
		this.aktienListe = aktienListe;
	}

	public Integer getAktienID() {
		return aktienID;
	}

	public void setAktienID(Integer aktienID) {
		this.aktienID = aktienID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPreis() {
		return preis;
	}

	public void setPreis(double preis) {
		this.preis = preis;
	}

	public int getStueck() {
		return stueck;
	}

	public void setStueck(int stueck) {
		this.stueck = stueck;
	}
}