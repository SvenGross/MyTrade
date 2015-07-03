package dao;

import java.sql.Connection;

import model.KonstantenSession;
import connectionPool.MyTradeConnectionPool;

public class MyTradeDAO {
	
	public synchronized Connection getConnection() {
		
		MyTradeConnectionPool conPool;
		conPool = MyTradeConnectionPool.getInstance(KonstantenSession.MIN_CONNECTIONS, KonstantenSession.MAX_CONNECTIONS);
		
		Connection con = conPool.getConnection();
		return con;
		
	}
	
	public synchronized void returnConnection(Connection con) {
		MyTradeConnectionPool conPool = MyTradeConnectionPool.getInstance(KonstantenSession.MIN_CONNECTIONS, KonstantenSession.MAX_CONNECTIONS);
		conPool.putConnection(con);
	}
	
}
