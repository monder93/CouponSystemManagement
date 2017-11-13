package mainPackage;

import java.sql.Connection;
import java.util.Set;

public class ConnectionPool 
{
	//---------------------------------static instance variable..using because of the singleton design pattern---------------
	private static ConnectionPool INSTANCE=null;
	
	//-------------------------------------------------private final variables variables--------------------------------------
		private static final int MAX_CONNECTIONS = 5;
		private final String driver = "com.mysql.jdbc.Driver";
		//private final String url = "jdbc:mysql://localhost:3306/johnbryceproject";
		private final String url = "jdbc:mysql://localhost:3306/johnbryceproject?useSSL=false";
		private final String username = "root";
		private final String password = "12345";
		//-------------------------------------------------private variables------------------------------------------------- ----
		private Set<Connection> myConnections;
		private Object key = new Object();
		private boolean isSystemShuttingDown = false;
		
		//-------------------------------------------------getInstance method-----------------------------------------------------
		public  static ConnectionPool getInstance() 
		{
		
		}
		
		private ConnectionPool() 
		{
			
		}
		
		//-------------------------------------------------getConnection method-----------------------------------------------------
		public synchronized Connection getConnection()  
		{
			
		}
		
		//-------------------------------------------------returnConnection method-------------------------------------------------
		public void returnConnection(Connection conn)
		{
			
		}
	
		//-------------------------------------------------closeAllConnections method---------------------------------------------
		public void closeAllConnections() 
		{
			
		}
		
		
}
