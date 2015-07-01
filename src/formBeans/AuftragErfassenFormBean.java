package formBeans;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import model.Aktie;
import model.KonstantenSession;

@ManagedBean
@RequestScoped
public class AuftragErfassenFormBean {

	private Map<String, Object> sessionMap = null;
	private Integer aktienID = null;
	private boolean auftragVerkauf = false;
	private String aktienName;
	private ArrayList<Aktie> aktienListe;
	private double preis;
	private int stueck;
	private boolean fehler;
	
	public AuftragErfassenFormBean() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		sessionMap = externalContext.getSessionMap();
		System.out.println("HERE");
		aktienID = (Integer) sessionMap.get(KonstantenSession.AUFTRAG_AKTIENID);
		if(aktienID != null) {
			setAuftragVerkauf(true);
			aktienName = "VERKAUFSAUFTRAG";
		}
		else {
			setAuftragVerkauf(false);
			aktienName = "KAUFAUFTRAG";
		}
		sessionMap.remove(KonstantenSession.AUFTRAG_AKTIENID);
	}
	
	public String back() {
		return "portfolio?faces-redirect=true";
	}

	public void save() {
		System.out.println(auftragVerkauf);
	}

	public String getAktienName() {
		return aktienName;
	}

	public void setAktienName(String aktienName) {
		this.aktienName = aktienName;
	}

	public ArrayList<Aktie> getAktienListe() {
		return aktienListe;
	}

	public void setAktienListe(ArrayList<Aktie> aktienListe) {
		this.aktienListe = aktienListe;
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
}