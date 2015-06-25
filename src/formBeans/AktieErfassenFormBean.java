package formBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.Aktie;

@ManagedBean
@SessionScoped
public class AktieErfassenFormBean {

	private String name;
	private String kuerzel;
	private double preis;
	private double dividende;
	private int stueck;
	private Aktie neueAktie;
	
	public String back() {

		return "administration?faces-redirect=true";
	}

	public String vorschau() {
		neueAktie = new Aktie(name, kuerzel, preis, dividende, stueck);		
		return "neueAktieVorschau?faces-redirect=true";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKuerzel() {
		return kuerzel;
	}

	public void setKuerzel(String kuerzel) {
		this.kuerzel = kuerzel;
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
}