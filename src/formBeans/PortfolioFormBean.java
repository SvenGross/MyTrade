package formBeans;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import dao.AktieDAO;
import model.Aktie;
import model.Benutzer;
import model.KonstantenSession;


@ManagedBean
@SessionScoped
public class PortfolioFormBean {

	private Map<String, Object> sessionMap = null;
	
	public PortfolioFormBean() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		sessionMap = externalContext.getSessionMap();
	}
	
	public void verkaufen(){
	
		//sell some stocks bitch
		
	}
	
	public ArrayList<Aktie> getAktienListe() {
		AktieDAO aktieDAO = new AktieDAO();
		return aktieDAO.getAlleAktienVonBenutzer();
	}

	public String getKontostand() {
		Benutzer benutzer = (Benutzer) sessionMap.get(KonstantenSession.ANGEMELDETER_BENUTZER);
		return new DecimalFormat("#.00").format(benutzer.getKontostand());
	}
	
}
