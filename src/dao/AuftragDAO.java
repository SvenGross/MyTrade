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
						+ "LIMIT " + anzahl);

				while (rs3.next()) {
					int stock_poolID = rs3.getInt("stock_poolID");
					con.createStatement().executeUpdate("UPDATE stock_pool SET orderFK = '" + orderID + "' WHERE stock_poolID = '" + stock_poolID + "'");
				}
				
				rs3.close();
				stmt3.close();
			}
			
			returnConnection(con);
			auftraegeAusfuehren();
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
			
			double gesamtsumme = 0;
			int stueck = 0;
			
			int kauf_auftragsID;
			int kauf_benutzerID;
			int aktienID;
			int kauf_stueck;
			double kauf_preis;
			
			int verkauf_auftragsID;
			int verkauf_benutzerID;
			int verkauf_stueck;
			double verkauf_preis;
			
			con = getConnection();
			
			//Alle offenen Kaufaufträge selektieren
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT orderID, userFK, stockFK, quantity, price_per_share "
					+ "FROM orders "
					+ "WHERE typeFK = '1' "
					+ "ORDER BY creation_date ASC");
			
			while(rs.next()) {
				kauf_auftragsID = rs.getInt("orderID");
				kauf_benutzerID = rs.getInt("userFK");
				aktienID        = rs.getInt("stockFK");
				kauf_stueck     = rs.getInt("quantity");
				kauf_preis      = rs.getDouble("price_per_share");
				
				//Alle passenden Verkaufsaufträge zu Kaufauftrag selektieren (Preis kleiner oder gleich was Benutzer zahlen würde)
				Statement stmt2 = con.createStatement();
				ResultSet rs2 = stmt2.executeQuery("SELECT orderID, userFK, quantity, price_per_share "
					+ "FROM orders "
					+ "WHERE typeFK = '2' AND "
					+ "price_per_share <= '" + kauf_preis + "' AND "
					+ "stockFK = '" + aktienID + "' "
					+ "ORDER BY creation_date ASC");
				
				while(rs2.next() && stueck < kauf_stueck) {
					verkauf_auftragsID = rs2.getInt("orderID");
					verkauf_benutzerID = rs2.getInt("userFK");
					verkauf_stueck     = rs2.getInt("quantity");
					verkauf_preis      = rs2.getDouble("price_per_share");
					
					//Aktien zum Verkaufsauftrag selektieren
					Statement stmt3 = con.createStatement();
					ResultSet rs3 = stmt3.executeQuery("SELECT stock_poolID "
						+ "FROM stock_pool "
						+ "WHERE orderFK = '" + verkauf_auftragsID + "'");
					
					int newOrderQuantity = verkauf_stueck;
					while(rs3.next() && stueck < kauf_stueck) {
						int verkauf_akitenEintragID = rs3.getInt("stock_poolID");
						
						con.createStatement().executeUpdate("UPDATE stock_pool SET ownerFK = '" + kauf_benutzerID + "', "
							+ "orderFK = NULL "
							+ "WHERE stock_poolID = '" + verkauf_akitenEintragID + "'");
						
						newOrderQuantity = newOrderQuantity - 1;
						con.createStatement().executeUpdate("UPDATE orders SET quantity = '" + newOrderQuantity + "' "
								+ "WHERE orderID = '" + verkauf_auftragsID + "'");
						
						//Falls NICHT Administrator -> Kontostand aktualisieren
						BenutzerDAO benutzerDAO = new BenutzerDAO();
						if(!benutzerDAO.isBenutzerAdmin(verkauf_benutzerID)) {
							benutzerDAO.kontostandAktualisieren((benutzerDAO.getKontostand(verkauf_benutzerID) + verkauf_preis), verkauf_benutzerID);
						}
						
						//Gesamtsumme für Kontostandänderung Käufer berechnen
						gesamtsumme = gesamtsumme + verkauf_preis;
						stueck++;
					}
					
					stmt3.close();
					rs3.close();
				}
				
				//Kaufauftrag gekaufte Anzahl Aktien abziehen
				int newOrderQuantity = kauf_stueck - stueck;
				con.createStatement().executeUpdate("UPDATE orders SET quantity = '" + newOrderQuantity + "' "
						+ "WHERE orderID = '" + kauf_auftragsID + "'");
				
				//Kontostand des Käufers aktualisieren
				BenutzerDAO benutzerDAO = new BenutzerDAO();
				benutzerDAO.kontostandAktualisieren((benutzerDAO.getKontostand(kauf_benutzerID) - gesamtsumme), kauf_benutzerID);
				
				stmt2.close();
				rs2.close();
			}
			
			//Aufträge mit neu Anzahl Aktien 0 löschen
			ausgefuehrteAuftraegeLoeschen();
			
			rs.close();
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
	
	public synchronized void ausgefuehrteAuftraegeLoeschen() {
		try {
			con = getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM orders WHERE quantity = '0'");
			stmt.close();
			returnConnection(con);

		} catch (Exception e) {
			System.err.println("FEHLER:     dao.AktieDAO     Es ist ein Fehler in der Methode 'ausgefuehrteAuftraegeLoeschen' aufgetreten.");
			e.printStackTrace();
			returnConnection(con);
		}
	}
}
