package dao;

import java.sql.Connection;

import model.KonstantenSession;
import connectionPool.MyTradeConnectionPool;

/**
 * @date 25.6.2015 - 3.7.2015
 * @author sacha weiersmueller, siro duschletta und sven gross
 */
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