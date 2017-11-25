package mainPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import utilities.Configurations_values;

public class ConnectionPool 
{
	//---------------------------------static instance variable..using because of the singleton design pattern---------------
	private static ConnectionPool INSTANCE=null;
	//-------------------------------------------------private variables------------------------------------------------- ----
	private Set<Connection> myConnections;
	private Object key = new Object();
	private boolean isSystemShuttingDown = false;

	//-------------------------------------------------getInstance method-----------------------------------------------------
	public  static synchronized ConnectionPool getInstance() 
	{
		if(INSTANCE == null)
		{
			INSTANCE = new ConnectionPool();
		}
		return INSTANCE;
	}

	private ConnectionPool() 
	{
		try 
		{
			Class.forName(Configurations_values.driver);
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO: handle exception
			System.out.println("ClassNotFoundException");
		}

		myConnections = new HashSet<>(Configurations_values.MAX_CONNECTIONS);
		while(myConnections.size()<Configurations_values.MAX_CONNECTIONS)
		{
			try 
			{
				myConnections.add(DriverManager.getConnection(Configurations_values.url, Configurations_values.username, Configurations_values.password));
			}
			catch (SQLException e) 
			{
				// TODO: handle exception
				System.out.println("SQLException");

			}
		}
	}

	//-------------------------------------------------getConnection method-----------------------------------------------------
	public synchronized Connection getConnection()  
	{
		Connection tempConn = null;
		synchronized (key) 
		{
			while(myConnections.isEmpty())
			{	
				try 
				{
					key.wait();
				}
				catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if(!isSystemShuttingDown)
			{
				tempConn = myConnections.iterator().next();
				myConnections.remove(tempConn);
			}	
		}

		if (tempConn == null)
		{
			throw new NullPointerException();
		}
		else
		{
			return tempConn;
		}
	}

	//-------------------------------------------------returnConnection method-------------------------------------------------
	public void returnConnection(Connection conn)
	{
		myConnections.add(conn);
		synchronized (key) 
		{
			key.notifyAll();
		}
	}

	//-------------------------------------------------closeAllConnections method---------------------------------------------
	public synchronized void closeAllConnections() throws SQLException 
	{
		isSystemShuttingDown=true;
		synchronized (key)
		{

			if(myConnections.size()<Configurations_values.MAX_CONNECTIONS)
			{
				try 
				{
					key.wait(Configurations_values.waitTime);
				}
				catch (InterruptedException e)
				{
					System.out.println("wait 5000");
				}
			}

		}
		Iterator<Connection> tempIterator = myConnections.iterator();

		while(tempIterator.hasNext())
		{
			Connection tempConn = tempIterator.next();
			tempConn.close();
			//myConnections.remove(tempConn);
		}
	}
}