package model;

public class Auftrag {

	private String aktienName;
	private double preis;
	private int stueck;
	
		
	public Auftrag(String name, double preis, int stueck) {
		this.aktienName = name;
		this.preis = preis;
		this.stueck = stueck;
	}
	
	
	public String back() {
		return "aktieErfassen?faces-redirect=true";
	}
	
	public void save() {
		//TODO
	}
	
	
	public String getAktienName() {
		return aktienName;
	}

	public void setAktienName(String aktienName) {
		this.aktienName = aktienName;
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
