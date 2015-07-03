package dao;

import java.sql.Connection;

import connectionPool.MyTradeConnectionPool;

public class MyTradeDAO {
	
	public Connection getConnection() {
		
		MyTradeConnectionPool conPool;
		conPool = MyTradeConnectionPool.getInstance(1, 5);
		
		Connection con = conPool.getConnection();
		return con;
		
	}
	
	public void returnConnection(Connection con) {
		MyTradeConnectionPool conPool = MyTradeConnectionPool.getInstance(1, 5);
		conPool.putConnection(con);
	}
	
}
