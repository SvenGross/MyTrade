package error;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import model.KonstantenSession;

@ManagedBean
@SessionScoped
public class Meldungen {

	private static ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	public  Map<String, Object> sessionMap  = externalContext.getSessionMap();

	public static String AKTIE_ERSTELLEN        = "Die Aktie wurde erfolgreich gespeichert: ";
	public static String DIVIDENDE_AUSSCHUETTEN = "Die Dividende wurde erfolgreich ausgeschüttet.";
	public static String BENUTZER_ANLEGEN       = "Der Benutzer wurde erfolgreich angelegt: ";
	public static String AUFTRAG_AUSFUEHREN     = "Der Auftrag wurde erfolgreich ausgeführt: ";
	public static String AUFTRAG_ERFASSEN       = "Der Auftrag wurde erfolgreich erfasst: ";
	public static String AUFTRAG_STORNIEREN     = "Der Auftrag wurde erfolgreich storniert: ";
	
	//TODO DONE: dividende ausschuetten
	
	
	public Meldungen() {
		sessionMap.put(KonstantenSession.FEHLER_MELDUNG, "");
	}
	public String printFehler() {
		
		String fehlerMeldung = (String) sessionMap.get(KonstantenSession.FEHLER_MELDUNG);
		sessionMap.put(KonstantenSession.FEHLER_MELDUNG, "");
		return fehlerMeldung;
	}
}
