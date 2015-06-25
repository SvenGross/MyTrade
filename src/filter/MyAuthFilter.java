package filter;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

/**
 * Es werden hier alle *.xhtml Seiten geprüft.
 * Einige sind offen für alle (siehe Methode istOeffentlicheSeite());
 * andere brauchen ein Login.
 * 
 * @version 0.1 (Jun 1, 2015)
 * @author Philipp Gressly Freimann 
 *         (philipp.gressly@santis.ch)
 */
@WebFilter("*.xhtml") // oder z. B. @WebFilter("/privat/*")
public class MyAuthFilter implements Filter {

	boolean debug = true;
	public static final String benutzer = "angemeldeterBenutzer";
	
	private void debugOut(String meldung) {
		if(debug) {
			System.out.println("Debug MyAuthFilter." + meldung);
		}
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		debugOut("init(): AuthFilter...");
	}

/**
 * Versuche, die Session aus dem Request zu holen.
 * Ist das nicht möglich, so gehe über den FacesContext.
 */
	HttpSession holeSessionVariable(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(null == session) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			if(null == facesContext || null == facesContext.getExternalContext()) {
				debugOut("holeSessionVariable(): No session!");
			} else {
				session = (HttpSession) facesContext.getExternalContext().getSession(true);            		
			}
		}
		return session;
	}


	String loginUrl = "login.xhtml";

	boolean istLoginURL(HttpServletRequest request) {
		String reqString = request.getRequestURI();
		debugOut("istLoginURL(): reqString: [" + reqString + "]");
		return reqString.contains(loginUrl);
	}


	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		try {
			debugOut("doFilter(): start...");

			eigenerDoHTTPFilter((HttpServletRequest)  req, 
			                    (HttpServletResponse) res, 
			                    chain);	

			debugOut("doFilter(): ... done.");
		} catch (Exception ex) {
			System.out.println("Exception im MyAuthFilter " + ex);
			ex.printStackTrace();
		}
	}


	/**
	 * Wie "doFilter", doch a) mit throws, statt try-catch und
	 *                      b) mit HttpServlet, statt Servlet
	 */
	private void eigenerDoHTTPFilter(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if(null == holeSessionVariable(request)) {
			behandleLeereSession(request, response, chain);
			return;
		}

		if(istLoginURL(request)) {
			debugOut("eigenerDoHTTPFilter(): Request is login request!");
			chain.doFilter(request, response); // hier drauf darf eingentlich jeder
			return;
		}

		Object user = holeSessionVariable(request).getAttribute(benutzer);
		if(null == user && istOeffentlicheSeite(request)) {
			debugOut("eigenerDoHTTPFilter(): Request ist freie Seite");
			chain.doFilter(request, response); // jeder, da öffentlich	
			return;
		}

		if(null == user) { // hier aber keine freie Seite
			debugOut("eigenerDoHTTPFilter(): user ist null, aber nicht freie Seite!");	
			response.sendRedirect(loginUrl);
			return;
		}

		debugOut("  Session: " + holeSessionVariable(request));
		debugOut("  User:    " + user                        );
		chain.doFilter(request, response); // darf weiterleiten, da eingeloggt
	}


	/**
	 * Prüfe, ob die Seite ein Login braucht. "true", falls die Seite 
	 * ohne "Login" sichtbar sein darf.
	 */
	private boolean istOeffentlicheSeite(HttpServletRequest req) {
//		String reqString = req.getRequestURI();
//		return reqString.contains("hierFrei.xhtml") || 
//				   reqString.contains("passwortFalsch.xhtml");
		
		return false;
	}


	/**
	 * Wie ist vorzugehen, wenn noch keine Session angelegt wurde?
	 */
	private void behandleLeereSession(HttpServletRequest  request,
	                                  HttpServletResponse response,
	                                  FilterChain         chain)
	             throws IOException, ServletException 
	{
		debugOut("behandleLeereSession(): Session ist null");
		if(istOeffentlicheSeite(request) || istLoginURL(request)) {
			chain.doFilter(request, response);	
		} else {
			response.sendRedirect(loginUrl);
		}
	}


	@Override
	public void destroy() {
		// must be overriden
	}

} // end class MyAuthFilter