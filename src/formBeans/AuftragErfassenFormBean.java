package formBeans;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.Aktie;
import dao.AktieDAO;

@ManagedBean
@SessionScoped
public class AuftragErfassenFormBean {

	private String aktienName;
	private ArrayList<Aktie> aktienListe;
	private double preis;
	private int stueck;
	private boolean fehler;

	public AuftragErfassenFormBean() {
	
		AktieDAO aktieDAO = new AktieDAO();
		aktienListe = aktieDAO.selectAlleAktienVonBenutzer();
	}
	
	public String back() {
		return "administration?faces-redirect=true";
	}

	public String save() {
		// Meldung 1 anzeigen yo

		return "xxxxxxxxx?faces-redirect=true";
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
}