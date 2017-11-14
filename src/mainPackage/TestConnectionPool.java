package mainPackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestConnectionPool 
{

	public static void main(String[] args) throws SQLException
	{
		
		Connection c1 = ConnectionPool.getInstance().getConnection();
		Connection c2 = ConnectionPool.getInstance().getConnection();
		Connection c3 = ConnectionPool.getInstance().getConnection();
		//ConnectionPool.getInstance().closeAllConnections();
		Connection c4 = ConnectionPool.getInstance().getConnection();
		Connection c5 = ConnectionPool.getInstance().getConnection();
		
		System.out.println("done");
		System.out.println(c1);
		System.out.println(c2);
		System.out.println(c3);
		System.out.println(c4);
		System.out.println(c5);
		ConnectionPool.getInstance().returnConnection(c1);
		Connection c6 = ConnectionPool.getInstance().getConnection();
		System.out.println(c6);
		
		try 
		{
			
			Statement stmt =  c1.createStatement();
			ResultSet rs;
			//a mysql statement that checks if the customer exist in the database
			rs = stmt.executeQuery("select * from company");

			rs.next();
			System.out.println(rs.getString(1));
			
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
		

	}
}

