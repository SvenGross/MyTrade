package connectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Connection Pool implementation.
 * MIN_CONNECTIONS are always built: Typically 1 (or 0: lazy instantiation).
 * More than MAX_CONNECTIONS will never be initialized.
 * @version 0.1 (May 27, 2015)
 * @author Philipp Gressly Freimann 
 *         (philipp.gressly@santis.ch)
 */
public class MyTradeConnectionPool {
	
	private static MyTradeConnectionPool thePool = null;
	
	private final int MIN_CONNECTIONS;
	private final int MAX_CONNECTIONS;
	private List<Connection> freePool;
	private List<Connection> busyPool;
	private String treiberName   = "com.mysql.jdbc.Driver";
	private String connectionURL = "jdbc:mysql://192.168.1.70/mytrade";
	private String username      = "mytrade";
	private String password      = "mytrade";
	
	private MyTradeConnectionPool(int minConnections, int maxConnections) throws ClassNotFoundException, SQLException {
		
		MIN_CONNECTIONS = minConnections;
		MAX_CONNECTIONS = maxConnections;
		
		Class.forName(treiberName);
		
		freePool = new LinkedList<Connection>();
		busyPool = new LinkedList<Connection>();
		for(int i = 0; i < MIN_CONNECTIONS; i++) {
			Connection con;
			con = createConnection();
			freePool.add(con);
		}
		
	}
	
	/**
	 * Singleton-Pattern
	 */
	public static synchronized MyTradeConnectionPool getInstance(int minConnections, int maxConnections) {
		
		if(null == thePool) {
			
			try {
				thePool = new MyTradeConnectionPool(minConnections, maxConnections);
			} catch (Exception e) {
				System.out.println("Exception in getInstance: " + e);
				e.printStackTrace();
				return null;
			}
			
		}
		return thePool;
	}

	/**
	 * Create a SQL-Connection object
	 */
	private Connection createConnection() throws SQLException {
		Connection con;
		con = DriverManager.getConnection(connectionURL, username, password);
		return con;
	}
	
	public synchronized Connection getConnection() {
		Connection con = null;
		if(0 < freePool.size()) {
			con = freePool.remove(0);
			busyPool.add(con);
			return con;
		}
		
		if(totalConnectionCount() >= MAX_CONNECTIONS) {
			System.out.println("No more space in the pools. MAX (" + MAX_CONNECTIONS + ") exceeded.");
			return null;
		}
		
		try {
			con = createConnection();
		} catch (SQLException e) {
			System.out.println("Error (ConnectionPoolImplementation.getConnection()): " + e);
			e.printStackTrace();
			return null;
		}
		busyPool.add(con);
		return con;
	}


	public synchronized void putConnection(Connection con) {
		if(con != null) {
			if(busyPool.remove(con)) {
				freePool.add(con);
				return;
			}
			System.out.println("Error: Connection was not `busy`.");
		}
	}


	private int totalConnectionCount() {
		return freePool.size() + busyPool.size();
	}

	/**
	 * Debug
	 */
	@Override
	public String toString() {
		return "ConnectionPooling. Free: " + freePool.size() +
		       " Busy: " + busyPool.size();
	}
	
}