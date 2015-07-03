package model;

/**
 * @date 25.6.2015 - 3.7.2015
 * @author sacha weiersmueller, siro duschletta und sven gross
 */
public class Auftrag {

	private int auftragsID;
	private String auftragsTyp;
	private String name;
	private String symbol;
	private int stueck;
	private double preis;
	
	public Auftrag(int auftragsID, String auftragsTyp, String name, String symbol, int stueck, double preis) {
		this.auftragsID = auftragsID;
		this.auftragsTyp = auftragsTyp;
		this.name = name;
		this.symbol = symbol;
		this.stueck = stueck;
		this.preis = preis;
	}

	public String back() {
		return "aktieErfassen?faces-redirect=true";
	}
	
	public void save() {
		//TODO
	}

	public int getAuftragsID() {
		return auftragsID;
	}

	public void setAuftragsID(int auftragsID) {
		this.auftragsID = auftragsID;
	}

	public String getAuftragsTyp() {
		return auftragsTyp;
	}

	public void setAuftragsTyp(String auftragsTyp) {
		this.auftragsTyp = auftragsTyp;
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

	public int getStueck() {
		return stueck;
	}

	public void setStueck(int stueck) {
		this.stueck = stueck;
	}

	public double getPreis() {
		return preis;
	}

	public void setPreis(double preis) {
		this.preis = preis;
	}
	
}
