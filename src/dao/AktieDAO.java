package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	Statement stmt;
	ResultSet rs = null;
	
	public boolean addAktie(Aktie aktie) {
		
		try {
			
			String	aktienName		= aktie.getName();
			String	aktienKuerzel	= aktie.getKuerzel();
			Double	aktienDividende	= aktie.getDividende();
			Double	aktienPreis		= aktie.getPreis();
			Integer aktienAnzahl	= aktie.getStueck();
			
			String sqlAktieHinzufügen = "INSERT INTO stock_data"
					+ "(symbol, name, nominal_price, last_dividend) VALUES"
					+ "(?,?,?,?)";

			con = getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sqlAktieHinzufügen);
			
			preparedStatement.setString(1, aktienName);
			preparedStatement.setString(2, aktienKuerzel);
			
			
			
			returnConnection(con);
			return true;
			
		} catch (Exception e) {
			System.err.println("FEHLER:     dao.AktieDAO     Es ist ein Fehler in der Methode 'addAktie' aufgetreten.");
			e.printStackTrace();
			return false;
		}
	}
	
	public ArrayList<Aktie> selectAlleAktienVonBenutzer() {

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		int benutzerID = ((Benutzer) sessionMap.get(KonstantenSession.ANGEMELDETER_BENUTZER)).getBenutzerIDAsInt();
		
		ArrayList<Aktie> alleAktien = new ArrayList<Aktie>();
		con = getConnection();

		try {

			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT stock_data.symbol, stock_data.name, stock_data.nominal_price, "
					+ "stock_data.last_dividend, COUNT(*) AS stock_count "
					+ "FROM stock_data "
					+ "INNER JOIN stock_pool ON stock_pool.stockFK = stock_data.stockID "
					+ "WHERE stock_pool.ownerFK = '" + benutzerID + "' "
					+ "GROUP BY stock_data.stockID");

			while (rs.next()) {
				String aktienName = rs.getString("name");
				String aktienSymbol = rs.getString("symbol");
				double aktienPreis = rs.getDouble("nominal_price");
				double aktienDividende = rs.getDouble("last_dividend");
				int aktienAnzahl = rs.getInt("stock_count");
				
				Aktie aktie = new Aktie(aktienName, aktienSymbol, aktienPreis, aktienDividende, aktienAnzahl);
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
}
