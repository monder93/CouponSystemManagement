package mainPackage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TestFunctions 
{
	
	public static void clearDateBase()
	{
		Connection conn = ConnectionPool.getInstance().getConnection();
		try
		{
			Statement stt = conn.createStatement();
			stt.execute("truncate company");
			stt.execute("truncate company_coupon");
			stt.execute("truncate coupon");
			stt.execute("truncate customer");
			stt.execute("truncate customer_coupon");
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
