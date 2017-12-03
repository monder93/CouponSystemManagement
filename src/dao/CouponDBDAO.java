package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import exceptions.DuplicateEntryException;
import exceptions.NullConnectionException;
import exceptions.UnAvailableCouponException;
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
	public void createCoupon(Coupon coupon) throws SQLException, ClassNotFoundException, InterruptedException, DuplicateEntryException, NullConnectionException 
	{
		Connection tempConn = pool.getConnection();
		if(isCouponExist(coupon,tempConn))
		{
			pool.returnConnection(tempConn);
			throw new DuplicateEntryException("the user tried to create a coupon with a title that already exist in the database");
		}
		else
		{
			insertCouponToDatabase(coupon,tempConn);
			pool.returnConnection(tempConn);
			//setting the generated id in the parameter coupon object inside insertCouponToDatebase
		}
	}
	//-----------------------------------------------------------------------------------------------------
	/**
	 * getting as a parameter coupon and removing it from the database if exist
	 * @param coupon instance object of a coupon
	 */
	@Override
	public void removeCoupon(Coupon coupon) throws SQLException, ClassNotFoundException, InterruptedException, DuplicateEntryException, NullConnectionException,UnAvailableCouponException,NullPointerException
	{
		Connection tempConn = pool.getConnection();
		if(isCouponExist(coupon,tempConn))
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
			pool.returnConnection(tempConn);
			throw new UnAvailableCouponException("coupon not exist - cant remove");
		}

	}
	//-----------------------------------------------------------------------------------------------------
	/**
	 * getting as a parameter coupon and updating it in the database if exist
	 * @param coupon instance object of a coupon
	 * @throws UnAvailableCouponException 
	 */
	@Override
	public void updateCoupon(Coupon coupon) throws ClassNotFoundException, InterruptedException, SQLException, ParseException, NullConnectionException, UnAvailableCouponException 
	{
		Connection tempConn = pool.getConnection();

		if(isCouponExist(coupon,tempConn))
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
			throw new UnAvailableCouponException("coupon not exist - cant update");
		}

	}
	//-----------------------------------------------------------------------------------------------------
	/**
	 * getting as a parameter int id  and retrieve Coupon data from  the database if exist
	 * @param id id of a coupon
	 * @return returns a coupon object 
	 */
	@Override
	public Coupon getCoupon(long id) throws ClassNotFoundException, InterruptedException, SQLException, ParseException, NullConnectionException , UnAvailableCouponException
	{
		Connection tempConn = pool.getConnection();

		Statement stmt = (Statement) tempConn.createStatement();
		ResultSet rs;
		// the mysql select statement for the correct coupon
		rs = stmt.executeQuery(String.format(CouponSqlQueries.COUPON_BY_ID, id));
		//adding the data from the mysql table to the correct members in the coupon instance

		Coupon tempCoupon = new Coupon();
		if ( rs.next() )
		{
			tempCoupon.setId(rs.getInt("id"));
			tempCoupon.setTitle(rs.getString("title"));
			tempCoupon.setStartDate(rs.getDate("START_DATE"));
			tempCoupon.setEndDate(rs.getDate("END_DATE"));
			tempCoupon.setAmount(rs.getInt("amount"));
			tempCoupon.setType(CouponType.valueOf(rs.getString("type").trim()));
			tempCoupon.setMessage(rs.getString("message"));
			tempCoupon.setPrice(rs.getDouble("price"));
			tempCoupon.setImage(rs.getString("image"));
		}
		else
		{
			throw new UnAvailableCouponException("no coupon found in the database with the given id ");
		}
		//returning the connection
		pool.returnConnection(tempConn);

		return tempCoupon;
	}
	//-----------------------------------------------------------------------------------------------------
	/**
	 * getting all the coupon from the database
	 * @return return a collection<Coupon> with all the coupons inside it 
	 * @throws UnAvailableCouponException 
	 */
	@Override
	public Collection<Coupon> getAllCoupons() throws ClassNotFoundException, InterruptedException, SQLException, ParseException, NullConnectionException, UnAvailableCouponException
	{
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
	public Collection<Coupon> getCouponByType(CouponType couponType) throws ClassNotFoundException, InterruptedException, SQLException, ParseException, NullConnectionException
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
	public boolean isCouponExist(Coupon coupon,Connection tempConn) throws SQLException , NullPointerException
	{
		//Connection tempConn = pool.getConnection();
		Statement  tempStatement = tempConn.createStatement();
		ResultSet  tempRs;
		try
		{
			tempRs = tempStatement.executeQuery(String.format(CouponSqlQueries.SELECT_ALL_WHERE_COUPON_TITLE,coupon.getTitle()));
			if(tempRs.next())
			{
				//pool.returnConnection(tempConn);	
				return true;
			}
		}
		catch (Exception e)
		{
			return false;
		}
		//pool.returnConnection(tempConn);	
		return false;
	}
	//---------------------------------------------------------------------------------------------
	/**
	 * inserting coupon to the database 
	 * @param coupon coupon object instance 
	 */
	@Override
	public void insertCouponToDatabase(Coupon coupon,Connection tempConn) throws SQLException 
	{
		//Connection tempConn = pool.getConnection();

		//creating the preparedStatement
		PreparedStatement tempPreparedStatement = tempConn.prepareStatement(CouponSqlQueries.INSERT_COUPON,Statement.RETURN_GENERATED_KEYS);

		tempPreparedStatement.setString(1, coupon.getTitle());
		tempPreparedStatement.setString(2, converter.dateToString(coupon.getStartDate()));
		tempPreparedStatement.setString(3, converter.dateToString(coupon.getEndDate()));
		tempPreparedStatement.setInt(4, coupon.getAmount());
		tempPreparedStatement.setString(5, coupon.getType().toString());
		tempPreparedStatement.setString(6, coupon.getMessage());
		tempPreparedStatement.setDouble(7, coupon.getPrice());
		tempPreparedStatement.setString(8, coupon.getImage());

		// execute the preparedStatement
		tempPreparedStatement.execute();

		ResultSet id;
		id = tempPreparedStatement.getGeneratedKeys();

		if(id.next())
		{
			coupon.setId(id.getInt(1));
		}

		//pool.returnConnection(tempConn);

		System.out.println("coupon : " +coupon.getTitle() + " added successfully");

	}
	//---------------------------------------------------------------------------------------------
	/**
	 * getting all the coupons out of date
	 * @return return collection<coupon> of the out dated coupons
	 */
	@Override
	public Collection<Coupon> getCouponOutOfDate() throws ClassNotFoundException, InterruptedException, SQLException, ParseException, NullConnectionException 
	{
		Connection tempConn = pool.getConnection();

		ArrayList<Coupon> tempCouponArray = new ArrayList<>();

		Statement stmt = (Statement) tempConn.createStatement();
		ResultSet rs;
		// the mysql select statement for the correct coupon
		rs = stmt.executeQuery(CouponSqlQueries.SELECT_COUPON_OUT_OF_DATE);
		//adding the data from the mysql table to the correct members in the coupon instance

		while ( rs.next() )
		{
			Coupon tempCoupon = new Coupon();
			tempCoupon.setId(rs.getInt("id"));
			tempCoupon.setTitle(rs.getString("title"));
			//tempCoupon.setStartDate(converter.stringToDate(rs.getString("start_date")));
			tempCoupon.setStartDate((rs.getDate("start_date")));
			tempCoupon.setEndDate(rs.getDate("end_date"));
			tempCoupon.setAmount(rs.getInt("amount"));
			tempCoupon.setType(CouponType.valueOf(rs.getString("TYPE").trim()));
			tempCoupon.setMessage(rs.getString("message"));
			tempCoupon.setPrice(rs.getDouble("price"));
			tempCoupon.setImage(rs.getString("image"));

			tempCouponArray.add(tempCoupon);
		}
		//returning the connection
		pool.returnConnection(tempConn);
/*
		System.out.println("out of dated coupons:");
		Iterator<Coupon> iter = tempCouponArray.iterator();
		while (iter.hasNext()) 
		{
			Coupon c = iter.next();
			System.out.println("coupon id: "+c.getId());
		}	

		System.out.println("out of dated coupons number:  "+tempCouponArray.size());
*/
		return tempCouponArray;
	}


}
