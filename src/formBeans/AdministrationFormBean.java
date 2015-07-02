package formBeans;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import berechnungen.Dividendenaenderung;
import model.Aktie;
import model.Benutzer;
import model.KonstantenSession;
import dao.AktieDAO;
import dao.BenutzerDAO;
import error.Meldungen;

@ManagedBean
@SessionScoped
public class AdministrationFormBean {

	public String aktieErfassen() {
		return "aktieErfassen.xhtml";
	}

	public String benutzerErfassen() {
		return "benutzerErfassen.xhtml";
	}

	public void dividendeAusschuetten() {

		BenutzerDAO benutzerDao = new BenutzerDAO();
		AktieDAO aktieDao = new AktieDAO();

		ArrayList<Benutzer> alleBenutzer = benutzerDao.getAllUsers();

		//zuerst alle aktien
		
		// dann benutzer
								
						//Dividende berechnen
						int neueDividende = Dividendenaenderung.neueDividende(aktie.getDividende(), Dividendenaenderung.MITTLERE_STREEUNG, 10, 100);
						
						//Dividende dem Kontostand hinzufügen
						
						
						
						//Dividende speichern

		ExternalContext externalContext = FacesContext.getCurrentInstance()
				.getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		sessionMap.put(KonstantenSession.FEHLER_MELDUNG,
				Meldungen.DIVIDENDE_AUSSCHUETTEN);
	}
}