package formBeans;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import model.Aktie;
import model.KonstantenSession;
import dao.AktieDAO;
import dao.AuftragDAO;
import error.Meldungen;

/**
 * @date 25.6.2015 - 3.7.2015
 * @author sacha weiersmueller, siro duschletta und sven gross
 */
@ManagedBean
@SessionScoped
public class AuftragErfassenFormBean {
	
	private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	private Map<String, Object> sessionMap = externalContext.getSessionMap();
	private String formTitle = ""; // Titel der im Facelet angezeigt wird ("Kauf" oder "Verkauf")

	private boolean auftragVerkauf = false;
	private Integer aktienID;
	private String name;
	private double preis;
	private int maxStueck;
	private int stueck;
	
	private void init() {
		aktienID = null;
		name = "";
		preis = 0;
		stueck = 1;
		
		aktienID = (Integer) sessionMap.get(KonstantenSession.AUFTRAG_AKTIENID);
		if(aktienID != null) {
			setAuftragVerkauf(true);
			formTitle = "VERKAUFSAUFTRAG";
			
			AktieDAO aktieDAO = new AktieDAO();
			Aktie aktie = aktieDAO.selectAktie(aktienID);
			name = aktie.getName();
			preis = aktie.getPreis();
			maxStueck = aktieDAO.selectAnzahlAktienDesBenutzers(aktienID);
			
			if(Double.valueOf(preis).isNaN()) {
				preis = 0;
			}
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

	public String save() {
		
		AuftragDAO auftragDAO = new AuftragDAO();
		if(auftragVerkauf) {
			auftragDAO.auftragErfassen(aktienID, stueck, preis, 2);
		}
		else {
			auftragDAO.auftragErfassen(aktienID, stueck, preis, 1);
		}
		
		sessionMap.put(KonstantenSession.MELDUNG,
				Meldungen.AUFTRAG_ERFASSEN + name);
		
		return "offeneAuftrage?faces-redirect=true";
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

	public boolean isAuftragVerkauf() {
		return auftragVerkauf;
	}

	public void setAuftragVerkauf(boolean auftragVerkauf) {
		this.auftragVerkauf = auftragVerkauf;
	}

	public ArrayList<Aktie> getAktienListe() {
		AktieDAO aktieDAO = new AktieDAO();
		return aktieDAO.selectAlleAktien();
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

	public int getMaxStueck() {
		return maxStueck;
	}

	public void setMaxStueck(int maxStueck) {
		this.maxStueck = maxStueck;
	}
}