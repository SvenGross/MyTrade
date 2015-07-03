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

/**
 * @date 25.6.2015 - 3.7.2015
 * @author sacha weiersmueller, siro duschletta und sven gross
 */
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
	
	public synchronized boolean auftragStornieren(int auftragsID) {
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
	
	public synchronized boolean auftragErfassen(int aktienID, int anzahl, double preis, int type) {
		
		int benutzerID = ((Benutzer) sessionMap.get(KonstantenSession.ANGEMELDETER_BENUTZER)).getBenutzerIDAsInt();
		
		try {
			con = getConnection();
			
			Statement stmt = con.createStatement();
			stmt.execute("INSERT INTO orders (userFK, stockFK, quantity, price_per_share, typeFK) "
					+ "VALUES('" + benutzerID + "', '" + aktienID + "', '" + anzahl + "', '" + preis + "', '" + type + "')");
			stmt.close();
			
			if(type == 2) {
				
				Statement stmt2 = con.createStatement();
				ResultSet rs2 = stmt2.executeQuery("SELECT orderID "
						+ "FROM orders "
						+ "WHERE userFK = '" + benutzerID + "' AND "
						+ "stockFK = '" + aktienID + "' AND "
						+ "quantity = '" + anzahl + "' AND "
						+ "price_per_share = '" + preis + "' AND "
						+ "typeFK = '" + type + "' "
						+ "ORDER BY creation_date DESC");
				
				Integer orderID = null;
				while (rs2.next()) {
					orderID = rs2.getInt("orderID");
				}
				
				rs2.close();
				stmt2.close();
				
				Statement stmt3 = con.createStatement();
				ResultSet rs3 = stmt3.executeQuery("SELECT stock_poolID "
						+ "FROM stock_pool "
						+ "WHERE orderFK IS NULL AND "
						+ "stockFK = '" + aktienID + "' AND "
						+ "ownerFK = '" + benutzerID + "' "
						+ "ORDER BY price ASC "
						+ "LIMIT " + anzahl);

				while (rs3.next()) {
					int stock_poolID = rs3.getInt("stock_poolID");
					con.createStatement().executeUpdate("UPDATE stock_pool SET orderFK = '" + orderID + "' WHERE stock_poolID = '" + stock_poolID + "'");
				}
				
				rs3.close();
				stmt3.close();
			}
			
			returnConnection(con);
			return true;

		} catch (Exception e) {
			System.err.println("FEHLER:     dao.AktieDAO     Es ist ein Fehler in der Methode 'auftragErfassen' aufgetreten.");
			e.printStackTrace();
			returnConnection(con);
			return false;
		}
	}
	
	public synchronized boolean auftraegeAusfuehren() {
		
		try {
			con = getConnection();
			
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT orderID, userFK, stockFK, quantity, price_per_share "
					+ "FROM orders "
					+ "WHERE  "
					+ "ORDER BY creation_date ASC");
			
			while(rs.next()) {
				int auftragsID = rs.getInt("orderID");
				int benutzerID = rs.getInt("userFK");
				int aktienID   = rs.getInt("stockFK");
				int stueck     = rs.getInt("quantity");
				double preis   = rs.getDouble("price_per_share");
			}
			
			stmt.close();
			returnConnection(con);
			return true;

		} catch (Exception e) {
			System.err.println("FEHLER:     dao.AktieDAO     Es ist ein Fehler in der Methode 'auftraegeAusfuehren' aufgetreten.");
			e.printStackTrace();
			returnConnection(con);
			return false;
		}
	}
}
