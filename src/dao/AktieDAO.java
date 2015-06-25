package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import model.Benutzer;

public class AktieDAO extends MyTradeDAO {

	private Connection con = null;
	Statement stmt;
	ResultSet rs = null;

	public void selectAllAktien() {

		con = getConnection();
	}

	public LinkedHashMap<String, String> selectAlleAktienVonBenutzer() {

		ExternalContext externalContext = FacesContext.getCurrentInstance()
				.getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		int benutzerID = ((Benutzer) sessionMap.get("angemeldeterBenutzer")).getBenutzerID();
		
		LinkedHashMap<String, String> alleAktien = new LinkedHashMap<String, String>();
		con = getConnection();

		try {

			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT name FROM stock_data "
					+ "WHERE stockID = " + "(Select stockFK From stock_pool "
					+ "Where stock_pool.ownerFK = " + benutzerID + ");");

			while (rs.next()) {
				String aktienName = rs.getString("name");
				alleAktien.put(aktienName, aktienName);
			}

		} catch (Exception e) {
			System.err
					.println("dao.AktieDAO     : Die SQL-Abfrage konnte nicht ausgeführt werden.");
			e.printStackTrace();
		}
		
		return alleAktien;
	}
}
