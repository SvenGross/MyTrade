package formBeans;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.Aktie;


@ManagedBean
@SessionScoped
public class PortfolioFormBean {

	private double guthaben;
	private ArrayList<Aktie> aktienListe;
	
	public void verkaufen(){
	
		//sell some stocks bitch
		
	}
		
	public ArrayList<Aktie> getAuftragsListe() {
		return aktienListe;
	}

	public void setAuftragsListe(ArrayList<Aktie> auftragsListe) {
		this.aktienListe = auftragsListe;
	}

	public double getGuthaben() {
		return guthaben;
	}

	public void setGuthaben(double guthaben) {
		this.guthaben = guthaben;
	}	
}