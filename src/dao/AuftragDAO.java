package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import model.Auftrag;
import model.Benutzer;
import model.KonstantenSession;

public class AuftragDAO extends MyTradeDAO {

	private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	private Map<String, Object> sessionMap = externalContext.getSessionMap();
	private Connection con = null;
	private Statement stmt;
	private ResultSet rs = null;
	
	public ArrayList<Auftrag> selectAlleAuftraegeVonBenutzer() {

		int benutzerID = ((Benutzer) sessionMap.get(KonstantenSession.ANGEMELDETER_BENUTZER)).getBenutzerIDAsInt();
		ArrayList<Auftrag> alleAuftraege = new ArrayList<Auftrag>();

		try {

			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT orders.orderID, stock_data.name, stock_data.symbol, "
					+ "orders.quantity, orders.price_per_share, order_types.type "
					+ "FROM orders "
					+ "INNER JOIN stock_data ON stock_data.stockID = orders.stockFK "
					+ "INNER JOIN order_types ON order_types.typeID = orders.typeFK "
					+ "WHERE orders.userFK = '" + benutzerID + "' "
					+ "ORDER BY orders.creation_date DESC");

			while (rs.next()) {
				int auftragsID = rs.getInt("orderID");
				String auftragsTyp = rs.getString("type");
				String aktienName = rs.getString("name");
				String aktienSymbol = rs.getString("symbol");
				int aktienAnzahl = rs.getInt("quantity");
				double aktienPreis = rs.getDouble("price_per_share");
				
				Auftrag auftrag = new Auftrag(auftragsID, auftragsTyp, aktienName, aktienSymbol, aktienAnzahl, aktienPreis);
				alleAuftraege.add(auftrag);
			}
			
			rs.close();
			stmt.close();
			returnConnection(con);
			return alleAuftraege;

		} catch (Exception e) {
			System.err.println("FEHLER:     dao.AktieDAO     Es ist ein Fehler in der Methode 'selectAlleAuftraegeVonBenutzer' aufgetreten.");
			e.printStackTrace();
			returnConnection(con);
			return null;
		}
	}
	
	public boolean auftragStornieren(int auftragsID) {
		try {

			con = getConnection();
			stmt = con.createStatement();
			
			if(stmt.executeUpdate("DELETE FROM orders WHERE orderID = '" + auftragsID + "'") > 0) {
				stmt.executeUpdate("UPDATE stock_pool SET orderFK = NULL WHERE orderFK = '" + auftragsID + "'");
				stmt.close();
				returnConnection(con);
				return true;
			}
			else {
				stmt.close();
				returnConnection(con);
				return false;
			}

		} catch (Exception e) {
			System.err.println("FEHLER:     dao.AktieDAO     Es ist ein Fehler in der Methode 'auftragStornieren' aufgetreten.");
			e.printStackTrace();
			returnConnection(con);
			return false;
		}
	}
	
	public boolean auftragErfassen(int aktienID, int anzahl, double preis, int type) {
		
		int benutzerID = ((Benutzer) sessionMap.get(KonstantenSession.ANGEMELDETER_BENUTZER)).getBenutzerIDAsInt();
		
		try {
			con = getConnection();
			stmt = con.createStatement();
			
			stmt.execute("INSERT INTO orders (userFK, stockFK, quantity, price_per_share, typeFK) "
					+ "VALUES('" + benutzerID + "', '" + aktienID + "', '" + anzahl + "', '" + preis + "', '" + type + "')");
			stmt.close();
			returnConnection(con);
			return true;

		} catch (Exception e) {
			System.err.println("FEHLER:     dao.AktieDAO     Es ist ein Fehler in der Methode 'auftragErfassen' aufgetreten.");
			e.printStackTrace();
			returnConnection(con);
			return false;
		}
	}
}
