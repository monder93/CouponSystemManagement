package mainPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import exceptionsHandlers.GeneralExceptionHandler;
import utilities.Configurations_values;

/**
 * 
 * A singleton class that holds all of the connections to the database and distributes them to the asking methods.
 *
 */
public class ConnectionPool 
{
	//---------------------------------static instance variable..using because of the singleton design pattern---------------
	private static ConnectionPool INSTANCE=null;
	//-------------------------------------------------private variables------------------------------------------------- ----
	private Set<Connection> myConnections;
	private Object key = new Object();
	private boolean isSystemShuttingDown = false;

	//-------------------------------------------------getInstance method-----------------------------------------------------
	/**
	 * 
	 * @return return instance of the ConnectionPool 
	 */
	public  static synchronized ConnectionPool getInstance() 
	{
		if(INSTANCE == null)
		{
			INSTANCE = new ConnectionPool();
		}
		return INSTANCE;
	}

	/**
	 * the constructor of the connection pool
	 */
	private ConnectionPool() 
	{
		try 
		{
			Class.forName(Configurations_values.driver);
		} 
		catch (ClassNotFoundException e) 
		{
			GeneralExceptionHandler.handle(e);
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
				GeneralExceptionHandler.handle(e);
			}
		}
	}

	//-------------------------------------------------getConnection method-----------------------------------------------------
	/**
	 * getConnection method giving connection for the asking methods and threads 
	 * its a synchronized method so if there is no connections free , it will hold the ask until one is free
	 * @return returns a connection 
	 */
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
					GeneralExceptionHandler.handle(e);
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
			throw new NullPointerException("connections are closed");
		}
		else
		{
			return tempConn;
		}
	}

	//-------------------------------------------------returnConnection method-------------------------------------------------
	/**
	 * returnConnection method receives a connection from a method/thread that don't need it<br/>
	 * any more and adds it to the Set.<br/>
	 * once the connection has been added, the method give a notify notice to any method/thread<br/>
	 * that are in a wait status.
	 * @param conn a connection that is being returned to the connection pool
	 */
	public void returnConnection(Connection conn)
	{
		myConnections.add(conn);
		synchronized (key) 
		{
			key.notifyAll();
		}
	}

	//-------------------------------------------------closeAllConnections method---------------------------------------------
	/**
	 *  cloasAllConnectios method runs on all the connections in the Set and closes them<br/>
	 *  one by one
	 * @throws SQLException thrown when the connection to the sql is not vaiable
	 */
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

	/**
	 * changes a boolean param to true and thus stops the method getConnection from giving<br/>
	 * connections.
	 */
	public void shuttingDown()
	{
		isSystemShuttingDown = true;
	}
	
	public int myConnectionsLength()
	{
		return myConnections.size();
	}
}