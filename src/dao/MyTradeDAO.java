package dao;

import java.sql.Connection;

import connectionPool.MyTradeConnectionPool;

public class MyTradeDAO {
	
	public Connection getConnection() {
		
		MyTradeConnectionPool conPool;
		conPool = MyTradeConnectionPool.getInstance(1, 5);
		
		Connection con = conPool.getConnection();
		if(con != null) {
			return con;
		}
		else {
			return null;
		}
		
	}
	
}