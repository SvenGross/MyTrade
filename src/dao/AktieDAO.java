package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import model.Aktie;
import model.Benutzer;
import model.KonstantenSession;

public class AktieDAO extends MyTradeDAO {

	private Connection con = null;
	private Statement stmt;
	private ResultSet rs = null;
	private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	private Map<String, Object> sessionMap = externalContext.getSessionMap();

	public boolean addAktie(Aktie aktie) {

		try {

			String aktienName = aktie.getName();
			String aktienSymbol = aktie.getSymbol();
			Double aktienDividende = aktie.getDividende();
			Double aktienPreis = aktie.getPreis();
			Integer aktienAnzahl = aktie.getStueck();
			int stockIDForForeignKey = checkIfStockTypeAlreadyExists(aktienSymbol);

			if (stockIDForForeignKey == 0) {

				String sqlAktieZuPoolHinzufügen = "INSERT INTO stock_data"
						+ "(symbol, name, nominal_price, last_dividend) VALUES"
						+ "(?,?,?,?)";

				con = getConnection();
				PreparedStatement preparedStatement = con.prepareStatement(sqlAktieZuPoolHinzufügen);

				preparedStatement.setString(1, aktienSymbol);
				preparedStatement.setString(2, aktienName);
				preparedStatement.setDouble(3, aktienPreis);
				preparedStatement.setDouble(4, aktienDividende);
				
				preparedStatement.executeUpdate();
				
				preparedStatement.close();
				returnConnection(con);

				try {
					stmt = con.createStatement();
					rs = stmt.executeQuery("SELECT stockID FROM stock_data WHERE symbol = '" + aktienSymbol + "'");

						while (rs.next()) {
							stockIDForForeignKey = rs.getInt("stockID");
						}

						rs.close();
						stmt.close();
						
				} catch (SQLException e) {
					e.printStackTrace();
					returnConnection(con);
				} finally{
					returnConnection(con);
				}
				
			}

			int count = 0;

			String sqlAktienEinzelnHinzufügen = "INSERT INTO stock_pool"
					+ "(stockFK, price, ownerFK) VALUES"
					+ "(?,?,?)";

			con = getConnection();

			PreparedStatement preparedStatement = con.prepareStatement(sqlAktienEinzelnHinzufügen);

			while (count < aktienAnzahl) {

				preparedStatement.setInt(1, stockIDForForeignKey);
				preparedStatement.setDouble(2, aktienPreis);
				preparedStatement.setInt(3, ((Benutzer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("angemeldeterBenutzer")).getBenutzerIDAsInt());

				preparedStatement.executeUpdate();
				
				count = count + 1;
			}
			preparedStatement.close();
			returnConnection(con);

			return true;

		} catch (Exception e) {
			System.err.println("FEHLER:     dao.AktieDAO     Es ist ein Fehler in der Methode 'addAktie' aufgetreten.");
			e.printStackTrace();
			returnConnection(con);
			return false;
		}
	}

	private int checkIfStockTypeAlreadyExists(String symbol) {

		int stockID = 0;
		con = getConnection();

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT stockID FROM stock_data WHERE symbol = '" + symbol + "'");

			if (rs != null) {

				while (rs.next()) {
					stockID = rs.getInt("stockID");
				}

				rs.close();
				stmt.close();
				
			} else {

				stockID = 0;
				
				rs.close();
				stmt.close();

			}
		} catch (SQLException e) {
			e.printStackTrace();
			returnConnection(con);
		}
		
		returnConnection(con);
		
		return stockID;

	}

	public ArrayList<Aktie> selectAlleAktienVonBenutzer(Integer benutzerID) {
		
		if(benutzerID == null) {
			benutzerID = ((Benutzer) sessionMap.get(KonstantenSession.ANGEMELDETER_BENUTZER)).getBenutzerIDAsInt();
		}
		ArrayList<Aktie> alleAktien = new ArrayList<Aktie>();
		
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT stock_data.stockID, stock_data.symbol, stock_data.name, stock_data.nominal_price, "
							+ "stock_data.last_dividend, COUNT(*) AS stock_count "
							+ "FROM stock_data "
							+ "INNER JOIN stock_pool ON stock_pool.stockFK = stock_data.stockID "
							+ "WHERE stock_pool.ownerFK = '" + benutzerID + "' "
							+ "GROUP BY stock_data.stockID");

			while (rs.next()) {
				int aktienID = rs.getInt("stockID");
				String aktienName = rs.getString("name");
				String aktienSymbol = rs.getString("symbol");
				double aktienPreis = rs.getDouble("nominal_price");
				double aktienDividende = rs.getDouble("last_dividend");
				int aktienAnzahl = rs.getInt("stock_count");

				Aktie aktie = new Aktie(aktienID, aktienName, aktienSymbol, aktienPreis, aktienDividende, aktienAnzahl);
				alleAktien.add(aktie);
			}

			rs.close();
			stmt.close();
			returnConnection(con);
			return alleAktien;

		} catch (Exception e) {
			System.err.println("FEHLER:     dao.AktieDAO     Es ist ein Fehler in der Methode 'selectAlleAktienVonBenutzer' aufgetreten.");
			e.printStackTrace();
			returnConnection(con);
			return null;
		}
	}
	
	public Aktie selectAktie(int aktienID) {

		Aktie aktie = null;
		
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT stock_data.symbol, stock_data.name, stock_data.last_dividend "
							+ "FROM stock_data "
							+ "WHERE stock_data.stockID = '" + aktienID + "'");

			while (rs.next()) {
				String aktienName = rs.getString("name");
				String aktienSymbol = rs.getString("symbol");
				double aktienDividende = rs.getDouble("last_dividend");
				
				aktie = new Aktie(aktienID, aktienName, aktienSymbol, 0, aktienDividende, 0);
			}
			
			if(aktie != null) {
				stmt = con.createStatement();
				rs = stmt.executeQuery("SELECT AVG(orders.price_per_share) as price "
								+ "FROM orders "
								+ "WHERE orders.stockFK = '" + aktienID + "'");

				while (rs.next()) {
					double aktienPreis = rs.getDouble("price");
					aktie.setPreis(aktienPreis);
				}
			}

			rs.close();
			stmt.close();
			returnConnection(con);
			return aktie;

		} catch (Exception e) {
			System.err.println("FEHLER:     dao.AktieDAO     Es ist ein Fehler in der Methode 'selectAktie' aufgetreten.");
			e.printStackTrace();
			returnConnection(con);
			return null;
		}
	}

	
	
	public int selectAnzahlAktienDesBenutzers(int aktienID) {
		
		int anzahlAktien = 0;
		int benutzerID = ((Benutzer) sessionMap.get(KonstantenSession.ANGEMELDETER_BENUTZER)).getBenutzerIDAsInt();
		
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT COUNT(*) as anzahl "
							+ "FROM stock_pool "
							+ "WHERE stock_pool.stockFK = '" + aktienID + "' AND "
							+ "stock_pool.ownerFK = '" + benutzerID + "'");

			while (rs.next()) {
				anzahlAktien = rs.getInt("anzahl");
			}

			rs.close();
			stmt.close();
			returnConnection(con);
			return anzahlAktien;

		} catch (Exception e) {
			System.err.println("FEHLER:     dao.AktieDAO     Es ist ein Fehler in der Methode 'selectAktie' aufgetreten.");
			e.printStackTrace();
			returnConnection(con);
			return anzahlAktien;
		}
	}
	
public ArrayList<Aktie> selectAlleAktien() {
		
		ArrayList<Aktie> alleAktien = new ArrayList<Aktie>();
		
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM stock_data");

			while (rs.next()) {
				int aktienID = rs.getInt("stockID");
				String aktienName = rs.getString("name");
				String aktienSymbol = rs.getString("symbol");
				double aktienPreis = rs.getDouble("nominal_price");
				double aktienDividende = rs.getDouble("last_dividend");

				Aktie aktie = new Aktie(aktienID, aktienName, aktienSymbol, aktienPreis, aktienDividende, 1);
				alleAktien.add(aktie);
			}

			rs.close();
			stmt.close();
			returnConnection(con);
			return alleAktien;

		} catch (Exception e) {
			System.err.println("FEHLER:     dao.AktieDAO     Es ist ein Fehler in der Methode 'selectAlleAktien' aufgetreten.");
			e.printStackTrace();
			returnConnection(con);
			return null;
		}
	}
	
public boolean dividendeAktualisieren(int neueDividende, int aktieID) {
		
		try {
			con = getConnection();
			stmt = con.createStatement();
			
			if(stmt.executeUpdate("UPDATE stock_data SET last_dividend = '" + neueDividende + "' WHERE stockID = '" + aktieID + "'") == 1) {
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
			System.err.println("FEHLER:     dao.AktieDAO     Es ist ein Fehler in der Methode 'dividendeAktualisieren' aufgetreten.");
			e.printStackTrace();
			returnConnection(con);
			return false;
		}
	}
}
