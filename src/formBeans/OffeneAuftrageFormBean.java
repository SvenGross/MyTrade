package formBeans;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.Auftrag;

@ManagedBean
@SessionScoped
public class OffeneAuftrageFormBean {

	private double guthaben;
	private ArrayList<Auftrag> auftragsListe;

	public ArrayList<Auftrag> getAuftragsListe() {
		return auftragsListe;
	}

	public void setAuftragsListe(ArrayList<Auftrag> auftragsListe) {
		this.auftragsListe = auftragsListe;
	}

	public double getGuthaben() {
		return guthaben;
	}

	public void setGuthaben(double guthaben) {
		this.guthaben = guthaben;
	}
	
}