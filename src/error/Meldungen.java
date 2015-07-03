package error;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import model.KonstantenSession;

/**
 * @date 25.6.2015 - 3.7.2015
 * @author sacha weiersmueller, siro duschletta und sven gross
 */
@ManagedBean
@SessionScoped
public class Meldungen {

	private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	private Map<String, Object> sessionMap  = externalContext.getSessionMap();

	public static String AKTIE_ERSTELLEN        = "Die Aktie wurde erfolgreich gespeichert: ";
	public static String DIVIDENDE_AUSSCHUETTEN = "Die Dividende wurde erfolgreich ausgeschüttet.";
	public static String BENUTZER_ANLEGEN       = "Der Benutzer wurde erfolgreich angelegt: ";
	public static String AUFTRAG_ERFASSEN       = "Der Auftrag wurde erfolgreich erfasst: ";
	public static String AUFTRAG_STORNIEREN     = "Der Auftrag wurde erfolgreich storniert: ";
	
	//TODO DONE: dividende ausschuetten
//				 aktie erstellen
//				benutzer anlegen
//				auftrag stornieren
//				auftrag erfassen
	
	
	public Meldungen() {
		sessionMap.put(KonstantenSession.MELDUNG, "");
	}
	public String printFehler() {
		
		String fehlerMeldung = (String) sessionMap.get(KonstantenSession.MELDUNG);
		sessionMap.put(KonstantenSession.MELDUNG, "");
		return fehlerMeldung;
	}
}
