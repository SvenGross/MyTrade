package model;

public class Aktie {

	private String name;
	private String kuerzel;
	private double preis;
	private double dividende;
	private int stueck;
		
	public Aktie(String name, String kuerzel, double preis, double dividende,
			int stueck) {
		this.name = name;
		this.kuerzel = kuerzel;
		this.preis = preis;
		this.dividende = dividende;
		this.stueck = stueck;
	}
	
	public String back() {
		return "aktieErfassen?faces-redirect=true";
	}
	
	public void save() {
		//TODO
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
}
