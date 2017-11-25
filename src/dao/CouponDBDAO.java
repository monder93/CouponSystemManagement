package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import javaBeans.Coupon;
import javaBeans.CouponType;
import mainPackage.ConnectionPool;
import utilities.CompanyCouponSqlQueries;
import utilities.CouponSqlQueries;
import utilities.CustomerCouponSqlQueries;
import utilities.converter;

/**
 * this class implements a Coupon data access object to perform operations with objects and database 
 * Receives data from the database to the user || write data received from the user to the database 
 * @author monder
 * @version 1.0
 */
public class CouponDBDAO implements CouponDAO
{
	private ConnectionPool pool;

	/**
	 * this constructs a CouponDBDAO and initialize the ConnectionPool variable 
	 */
	public CouponDBDAO()
	{
		pool = ConnectionPool.getInstance();
	}
	//-----------------------------------------------------------------------------------------------------
	/**
	 * getting as a parameter coupon and adding it to the database if not exist
	 * @param coupon instance object of a coupon 
	 */
	@Override
	public void createCoupon(Coupon coupon) throws Exception 
	{
		if(isCouponExist(coupon))
		{
			//exception
			System.out.println("Coupon exist");
		}
		else
		{
			insertCouponToDatabase(coupon);
			//setting the generated id in the parameter coupon object inside insertCouponToDatebase
		}
	}
	//-----------------------------------------------------------------------------------------------------
	/**
	 * getting as a parameter coupon and removing it from the database if exist
	 * @param coupon instance object of a coupon
	 */
	@Override
	public void removeCoupon(Coupon coupon) throws SQLException , Exception
	{
		Connection tempConn = pool.getConnection();
		if(isCouponExist(coupon))
		{
			//deleting the coupon from coupon table
			Statement  tempDeleteStatement = tempConn.createStatement();
			tempDeleteStatement.execute(String.format(CouponSqlQueries.DELETE_COUPON_BY_ID,coupon.getId()));

			//deleting the coupon from company_coupon table
			tempDeleteStatement.execute(String.format(CompanyCouponSqlQueries.DELETE_COUPON_WHERE_ID,coupon.getId()));

			//deleting the coupon from customer_coupon table
			tempDeleteStatement.execute(String.format(CustomerCouponSqlQueries.DELETE_COUPON_CUST_ID,coupon.getId()));

			pool.returnConnection(tempConn);
			System.out.println("coupon removed successfully");

		}
		else
		{
			System.out.println("coupon not exist");
		}

	}
	//-----------------------------------------------------------------------------------------------------
	/**
	 * getting as a parameter coupon and updating it in the database if exist
	 * @param coupon instance object of a coupon
	 */
	@Override
	public void updateCoupon(Coupon coupon) throws Exception 
	{
		Connection tempConn = pool.getConnection();

		if(isCouponExist(coupon))
		{
			PreparedStatement preparedStmt = tempConn.prepareStatement(CouponSqlQueries.UPDATE_COUPON_WHERE_ID);
			preparedStmt.setString (1, converter.dateToString(coupon.getEndDate()));
			preparedStmt.setDouble (2, coupon.getPrice());
			preparedStmt.setLong   (3, coupon.getId());    
			// execute the java prepared statement
			preparedStmt.executeUpdate();
			//returning the connection
			pool.returnConnection(tempConn);
			System.out.println("coupon has been updated!");
		}
		else
		{
			System.out.println("coupon not exist");
		}

	}
	//-----------------------------------------------------------------------------------------------------
	/**
	 * getting as a parameter int id  and retrieve Coupon data from  the database if exist
	 * @param id id of a coupon
	 * @return returns a coupon object 
	 */
	@Override
	public Coupon getCoupon(long id) throws SQLException 
	{
		Connection tempConn = pool.getConnection();

		Statement stmt = (Statement) tempConn.createStatement();
		ResultSet rs;
		// the mysql select statement for the correct coupon
		rs = stmt.executeQuery(String.format(CouponSqlQueries.COUPON_BY_ID, id));
		//adding the data from the mysql table to the correct members in the coupon instance

		Coupon tempCoupon = new Coupon();
		while ( rs.next() )
		{
			tempCoupon.setId(rs.getInt("id"));
			tempCoupon.setTitle(rs.getString("title"));
			tempCoupon.setStartDate(converter.stringToDate(rs.getString("start_date")));
			tempCoupon.setEndDate(converter.stringToDate(rs.getString("end_date")));
			tempCoupon.setAmount(rs.getInt("amount"));
			tempCoupon.setType(CouponType.valueOf(rs.getString("type").trim()));
			tempCoupon.setMessage(rs.getString("message"));
			tempCoupon.setPrice(rs.getDouble("price"));
			tempCoupon.setImage(rs.getString("image"));
		}
		//returning the connection
		pool.returnConnection(tempConn);

		return tempCoupon;
	}
	//-----------------------------------------------------------------------------------------------------
	/**
	 * getting all the coupon from the database
	 * @return return a collection<Coupon> with all the coupons inside it 
	 */
	@Override
	public Collection<Coupon> getAllCoupons() throws SQLException 
	{
		// TODO Auto-generated method stub
		ArrayList<Coupon> tempCouponArray = new ArrayList<>();

		Connection tempConn = pool.getConnection();
		Statement  tempStatement = tempConn.createStatement();
		ResultSet  tempRs;

		tempRs = tempStatement.executeQuery(CouponSqlQueries.ALL_COUPON);
		while(tempRs.next())
		{
			Coupon tempCoupon = getCoupon(tempRs.getInt("ID"));
			tempCouponArray.add(tempCoupon);
		}
		pool.returnConnection(tempConn);
		return tempCouponArray;
	}
	//-----------------------------------------------------------------------------------------------------
	/**
	 * getting coupon from the database by type
	 * @return return a collection<Coupon> with all the coupons inside it with specific type 
	 */
	@Override
	public Collection<Coupon> getCouponByType(CouponType couponType) throws SQLException 
	{
		Connection tempConn = pool.getConnection();

		ArrayList<Coupon> tempCouponArray = new ArrayList<>();
		Coupon tempCoupon = new Coupon();

		Statement stmt = (Statement) tempConn.createStatement();
		ResultSet rs;
		// the mysql select statement for the correct coupon
		rs = stmt.executeQuery(String.format(CouponSqlQueries.COUPON_BY_TYPE, couponType.toString()));
		//adding the data from the mysql table to the correct members in the coupon instance

		while ( rs.next() )
		{
			tempCoupon.setId(rs.getInt("id"));
			tempCoupon.setTitle(rs.getString("title"));
			tempCoupon.setStartDate(converter.stringToDate(rs.getString("start_date")));
			tempCoupon.setEndDate(converter.stringToDate(rs.getString("end_date")));
			tempCoupon.setAmount(rs.getInt("amount"));
			tempCoupon.setType(CouponType.valueOf(rs.getString("type").trim()));
			tempCoupon.setMessage(rs.getString("message"));
			tempCoupon.setPrice(rs.getDouble("price"));
			tempCoupon.setImage(rs.getString("image"));

			tempCouponArray.add(tempCoupon);
		}
		//returning the connection
		pool.returnConnection(tempConn);

		return tempCouponArray;
	}
	//---------------------------------------------------------------------------------------------
	/**
	 * checks if coupon title is exist
	 * @param coupon coupon object
	 * @return return true if coupon title is exist in the database , else return false
	 */
	@Override
	public boolean isCouponExist(Coupon coupon) throws SQLException, Exception 
	{
		Connection tempConn = pool.getConnection();
		Statement  tempStatement = tempConn.createStatement();
		ResultSet  tempRs;
		try
		{
			tempRs = tempStatement.executeQuery(String.format(CouponSqlQueries.SELECT_ALL_WHERE_COUPON_TITLE,coupon.getTitle()));
			if(tempRs.next())
			{
				return true;
			}
		}
		catch (SQLException e) 
		{
			// TODO: handle exception
		}
		finally 
		{
			pool.returnConnection(tempConn);	
		}
		return false;
	}
	//---------------------------------------------------------------------------------------------
	/**
	 * inserting coupon to the database 
	 * @param coupon coupon object instance 
	 */
	@Override
	public void insertCouponToDatabase(Coupon coupon) throws SQLException, Exception 
	{
		Connection tempConn = pool.getConnection();

		//creating the preparedStatement
		PreparedStatement tempPreparedStatement = tempConn.prepareStatement(CouponSqlQueries.INSERT_COUPON,Statement.RETURN_GENERATED_KEYS);

		tempPreparedStatement.setString(1, coupon.getTitle());
		tempPreparedStatement.setString(2, converter.dateToString(coupon.getStartDate()));
		tempPreparedStatement.setString(3, converter.dateToString(coupon.getEndDate()));
		tempPreparedStatement.setInt(4, coupon.getAmount());
		tempPreparedStatement.setString(5, coupon.getMessage());
		tempPreparedStatement.setDouble(6, coupon.getPrice());
		tempPreparedStatement.setString(7, coupon.getImage());

		// execute the preparedStatement
		tempPreparedStatement.execute();

		ResultSet id;
		id = tempPreparedStatement.getGeneratedKeys();

		if(id.next())
		{
			coupon.setId(id.getInt(1));
		}

		pool.returnConnection(tempConn);

		System.out.println("coupon : " +coupon.getTitle() + " added successfully");

	}
}
