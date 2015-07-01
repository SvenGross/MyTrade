package model;

public class Aktie {

	private Integer aktienID = null;
	private String name;
	private String symbol;
	private double preis;
	private double dividende;
	private int stueck;
		
	public Aktie(int aktienID, String name, String symbol, double preis, double dividende, int stueck) {
		this.aktienID = aktienID;
		this.name = name;
		this.symbol = symbol;
		this.preis = preis;
		this.dividende = dividende;
		this.stueck = stueck;
	}
	
	public Aktie(String name, String symbol, double preis, double dividende, int stueck) {
		this.name = name;
		this.symbol = symbol;
		this.preis = preis;
		this.dividende = dividende;
		this.stueck = stueck;
	}
	
	public int getAktienID() {
		return aktienID;
	}
	
	public void setAktienID(int aktienID) {
		this.aktienID = aktienID;
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
}
