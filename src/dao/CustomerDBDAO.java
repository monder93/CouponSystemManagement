package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import javaBeans.Coupon;
import javaBeans.CouponType;
import javaBeans.Customer;
import mainPackage.ConnectionPool;
import utilities.CouponSqlQueries;
import utilities.CustomerCouponSqlQueries;
import utilities.CustomerSqlQueries;
import utilities.converter;

/**
 * this class implements a Customer data access object to perform operations with objects and database 
 * Receives data from the database to the user || write data received from the user to the database 
 * @author monder
 * @version 1.0
 */
public class CustomerDBDAO implements CustomerDAO
{
	private ConnectionPool pool;
	private int customerId;

	/**
	 * this constructs a CustomerDBDAO and initialize the ConnectionPool variable 
	 */
	public CustomerDBDAO()
	{
		this.pool = ConnectionPool.getInstance();
	}
	//-----------------------------------------------------------------------------------------------------
	/**
	 * getting as a parameter customer and adding it to the database if not exist
	 * @param customer instance object of a customer 
	 */
	@Override
	public void createCustomer(Customer customer) throws Exception 
	{
		// TODO Auto-generated method stub
		if(isCustomerExist(customer))
		{
			//Exception 
		}
		else
		{
			insertCustomerToDatabase(customer);
		}
	}
	//-----------------------------------------------------------------------------------------------------
	/**
	 * getting as a parameter customer and removing it from the database if exist
	 * @param customer instance object of a customer
	 */
	@Override
	public void removeCustomer(Customer customer) throws SQLException 
	{
		// TODO Auto-generated method stub
		Connection tempConn = pool.getConnection();

		try 
		{
			if(isCustomerExist(customer))
			{
				Statement  tempDeleteStatement = tempConn.createStatement();

				//deleting the customer from customer table
				tempDeleteStatement.execute(String.format(CustomerSqlQueries.DELET_BY_CUST_NAME,customer.getCustName()));

				//deleting customer coupons that customer used -- from table customer_coupon
				tempDeleteStatement.executeQuery(String.format(CustomerCouponSqlQueries.DELET_BY_CUST_ID, customer.getId()));

				System.out.println("customer  : " + customer.getCustName() + " removerd successfull");		    		
			}
			else
			{
				System.out.println("customer : " + customer.getCustName() + " not exist in the database");
			}
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
		}
		finally
		{
			pool.returnConnection(tempConn);
		}
	}	
	//-----------------------------------------------------------------------------------------------------
	/**
	 * getting as a parameter customer and updating it in the database if exist
	 * @param customer instance object of a customer
	 */
	@Override
	public void updateCustomer(Customer customer) throws SQLException
	{
		// TODO Auto-generated method stub
		Connection tempConn = pool.getConnection();

		try 
		{
			if(isCustomerExist(customer))
			{						
				PreparedStatement tempPreparedStatement = tempConn.prepareStatement(CustomerSqlQueries.UPDATE_CUSTOMER);
				tempPreparedStatement.setString(1, customer.getPassword());
				tempPreparedStatement.setLong(2, customer.getId());
				tempPreparedStatement.executeUpdate();
				System.out.println("customer  : " + customer.getCustName() + " updated successfull");		    		
			}
			else
			{
				System.out.println("customer : " + customer.getCustName() + " not exist in the database");
			}
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
		}
		finally
		{
			pool.returnConnection(tempConn);
		}
	}
	//----------------------------------------------------------------------------------------------------------
	/**
	 * getting as a parameter int id  and retrieve customer data from  the database if exist
	 * @param id id of a customer
	 * @return returns a customer object 
	 */
	@Override
	public Customer getCustomer(long id) throws SQLException 
	{
		Connection tempConn = pool.getConnection();
		Statement  tempStatement = tempConn.createStatement();
		ResultSet  tempRs;
		tempRs = tempStatement.executeQuery(String.format(CustomerSqlQueries.SELECT_ALL_WHERE_ID, id));

		Customer tempCustomer = new Customer();
		if(tempRs.next())
		{
			tempCustomer.setId(tempRs.getInt("ID"));
			tempCustomer.setCustName(tempRs.getString("CUST_NAME"));
			tempCustomer.setPassword(tempRs.getString("PASSWORD"));

			tempCustomer.setCoupons(getCouponsByCustomerId(tempCustomer.getId()));

		}
		else
		{
			System.out.println("there is no company with this id ");
		}

		//returning the connection 
		pool.returnConnection(tempConn);

		//returning the company object 
		return tempCustomer;
	}
	//----------------------------------------------------------------------------------------------------------
	/**
	 * getting all the customers from the database
	 * @return return a collection<Customer> with all the customers inside it 
	 */
	@Override
	public Collection<Customer> getAllCustomer() throws SQLException 
	{
		// TODO Auto-generated method stub
		ArrayList<Customer> tempCustomerArray = new ArrayList<>();

		Connection tempConn = pool.getConnection();
		Statement  tempStatement = tempConn.createStatement();
		ResultSet  tempRs;

		tempRs = tempStatement.executeQuery(CustomerSqlQueries.SELECT_ID_FROM_CUSTOMER);
		while(tempRs.next())
		{
			Customer tempCustomer = getCustomer(tempRs.getInt("ID"));
			tempCustomerArray.add(tempCustomer);
		}
		pool.returnConnection(tempConn);
		return tempCustomerArray;
	}
	//----------------------------------------------------------------------------------------------------------
	/**
	 * getting all the coupons of this customer 
	 * @return returning collection<Coupon> with all the coupons of this customer inside it 
	 */
	@Override
	public Collection<Coupon> getCoupons() throws SQLException 
	{
		ArrayList<Coupon> arrayOfCustomersCoupons = new ArrayList<Coupon>(this.getCouponsByCustomerId(getCustomerId()));
		return arrayOfCustomersCoupons;
	}
	//----------------------------------------------------------------------------------------------------------
	/**
	 * login function to perform login operation
	 * @param custName customer name 
	 * @param password password of the customer
	 * @return returns true if custName and password is correct and they are in the database , else returns false
	 */
	@Override
	public boolean login(String custName, String password) throws SQLException, Exception 
	{
		Connection tempConn = pool.getConnection();
		ResultSet  tempRs;

		try 
		{
			Statement  tempStatement = tempConn.createStatement();
			tempRs = tempStatement.executeQuery(String.format(CustomerSqlQueries.SELECT_ID_PASSWORD, custName,password));
			if(tempRs.next())
			{
				setCustomerId(tempRs.getInt("ID"));
				return true;
			}

		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			pool.returnConnection(tempConn);	
		}
		return false;
	}
	//---------------------------------------------------------------------------------------------------------
	/**
	 * checks if customer name is exist
	 * @param customer customer object
	 * @return return true if customer name is exist in the database , else return false
	 */
	@Override
	public boolean isCustomerExist(Customer customer) throws SQLException, Exception 
	{
		Connection tempConn = pool.getConnection();
		Statement  tempStatement = tempConn.createStatement();
		ResultSet  tempRs;
		try
		{
			tempRs = tempStatement.executeQuery(String.format(CustomerSqlQueries.SELECT_ALL_WHERE_CUST_NAME,customer.getCustName()));
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
	//---------------------------------------------------------------------------------------------------
	/**
	 * inserting customer to the database 
	 * @param customer customer object instance 
	 */
	@Override
	public void insertCustomerToDatabase(Customer customer) throws SQLException, Exception 
	{
		// TODO Auto-generated method stub
		Connection tempConn = pool.getConnection();

		try
		{
			//creating the preparedStatement
			PreparedStatement tempPreparedStatement = tempConn.prepareStatement(CustomerSqlQueries.INSERT_CUSTOMER);

			tempPreparedStatement.setString(1, customer.getCustName());
			tempPreparedStatement.setString(2, customer.getPassword());

			// execute the preparedStatement
			tempPreparedStatement.execute();
		}
		catch (SQLException e)
		{
			// TODO: handle exception
		}
		finally
		{
			pool.returnConnection(tempConn);	
		}
		System.out.println("company : " +customer.getCustName() + " added successfully");

	}
	//---------------------------------------------------------------------------------------------------------
	/**
	 * getting coupons by customer ID
	 * @param id id of the customer 
	 * @return returning a collection<Coupon> inside it coupons of customer with the id in the parameter
	 */
	@Override
	public Collection<Coupon> getCouponsByCustomerId(long id) throws SQLException 
	{
		ArrayList<Coupon> tempCouponsArray = new ArrayList<>();

		//getting connection from the pool
		Connection tempConn = pool.getConnection();
		Statement  tempStatement = tempConn.createStatement();
		ResultSet  tempResultSet;

		tempResultSet = tempStatement.executeQuery(String.format(CustomerCouponSqlQueries.COUPON_ID_BY_CUST_ID, id));
		try
		{
			while (tempResultSet.next())
			{
				Statement  tempStatement2 = tempConn.createStatement();
				ResultSet  tempResultSet2;
				tempResultSet2 = tempStatement2.executeQuery(String.format(CouponSqlQueries.COUPON_BY_ID, tempResultSet.getInt("COUPON_ID")));

				while (tempResultSet2.next())
				{
					Coupon tempCoupon = new Coupon();
					tempCoupon.setId(tempResultSet2.getInt("ID"));
					tempCoupon.setTitle(tempResultSet2.getString("TITLE"));

					// getting dates and converting them using converter class to format yy-MM-dd 
					tempCoupon.setStartDate(converter.stringToDate(tempResultSet2.getString("START_DATE")));
					tempCoupon.setEndDate(converter.stringToDate(tempResultSet2.getString("END_DATE")));
					tempCoupon.setAmount(tempResultSet2.getInt("AMOUNT"));
					tempCoupon.setType(CouponType.valueOf(tempResultSet2.getString("TYPE").trim()));
					tempCoupon.setMessage(tempResultSet2.getString("MESSAGE"));
					tempCoupon.setPrice(tempResultSet2.getDouble("PRICE"));
					tempCoupon.setImage(tempResultSet2.getString("IMAGE"));

					tempCouponsArray.add(tempCoupon);
				}
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
		finally 
		{
			//returning the connection to the pool
			pool.returnConnection(tempConn);
		}
		return tempCouponsArray;
	}
	//-----------------------------------------------------------------------------------------------
	/**
	 * getting customer coupons by Type 
	 * @param couponType type of the coupon
	 * @return return collection<Coupon> inside it the customer coupons by specific type
	 */
	@Override
	public Collection<Coupon> getCouponsByType(CouponType couponType) throws SQLException
	{
		Connection tempConn = pool.getConnection();
		ArrayList<Coupon> ArrayOfCouponsByType = new ArrayList<>();
		Statement  tempStatement = tempConn.createStatement();
		ResultSet  tempRs;

		// getting all the COUPON_ID for the customer with specific type
		tempRs = tempStatement.executeQuery(String.format(CustomerCouponSqlQueries.SELECT_COUPON_CUSTOMER_BY_TYPE,customerId,couponType.toString()));


		while ( tempRs.next() )
		{
			Coupon tempCoupon = new Coupon();
			tempCoupon.setId(tempRs.getInt("id"));
			tempCoupon.setTitle(tempRs.getString("title"));
			tempCoupon.setStartDate(converter.stringToDate(tempRs.getString("start_date")));
			tempCoupon.setEndDate(converter.stringToDate(tempRs.getString("end_date")));
			tempCoupon.setAmount(tempRs.getInt("amount"));
			tempCoupon.setType(CouponType.valueOf(tempRs.getString("type").trim()));
			tempCoupon.setMessage(tempRs.getString("message"));
			tempCoupon.setPrice(tempRs.getDouble("price"));
			tempCoupon.setImage(tempRs.getString("image"));

			ArrayOfCouponsByType.add(tempCoupon);		
		}

		pool.returnConnection(tempConn);

		return ArrayOfCouponsByType;
	}
	//-----------------------------------------------------------------------------------------------
	/**
	 * getting customer coupons by price limited 
	 * @param price limit price
	 * @return return collection<Coupon> inside it the customer coupons by price limit
	 */
	@Override
	public Collection<Coupon> getCouponsByPrice(double price) throws SQLException 
	{
		Connection tempConn = pool.getConnection();
		ArrayList<Coupon> ArrayOfCouponsByPrice = new ArrayList<>();
		Statement  tempStatement = tempConn.createStatement();
		ResultSet  tempRs;

		// getting all the COUPON_ID for the customer with price
		tempRs = tempStatement.executeQuery(String.format(CustomerCouponSqlQueries.SELECT_COUPON_CUSTOMER_BY_PRICE,customerId,price));


		while ( tempRs.next() )
		{
			Coupon tempCoupon = new Coupon();
			tempCoupon.setId(tempRs.getInt("id"));
			tempCoupon.setTitle(tempRs.getString("title"));
			tempCoupon.setStartDate(converter.stringToDate(tempRs.getString("start_date")));
			tempCoupon.setEndDate(converter.stringToDate(tempRs.getString("end_date")));
			tempCoupon.setAmount(tempRs.getInt("amount"));
			tempCoupon.setType(CouponType.valueOf(tempRs.getString("type").trim()));
			tempCoupon.setMessage(tempRs.getString("message"));
			tempCoupon.setPrice(tempRs.getDouble("price"));
			tempCoupon.setImage(tempRs.getString("image"));

			ArrayOfCouponsByPrice.add(tempCoupon);		
		}

		pool.returnConnection(tempConn);

		return ArrayOfCouponsByPrice;
	}
	//-----------------------------------------------------------------------------------------------
	/**
	 * connecting a coupon to a customer 
	 * @param customerId id of the customer
	 * @param couponId  id of the coupon
	 */
	@Override
	public void addCouponToCustomer(int customerId, int couponId) throws SQLException, Exception
	{
		Connection tempConn = pool.getConnection();

		//creating the preparedStatement
		PreparedStatement tempPreparedStatement = tempConn.prepareStatement(CustomerCouponSqlQueries.INSERT_COUPON_TO_CUSTOMER);

		tempPreparedStatement.setInt(1, customerId);
		tempPreparedStatement.setInt(2, couponId);

		// execute the preparedStatement
		tempPreparedStatement.execute();

		pool.returnConnection(tempConn);
	}
	//-----------------------------------------------------------------------------------------------
	/**
	 * 
	 * @return customer id 
	 */
	public int getCustomerId() 
	{
		return customerId;
	}
	//-----------------------------------------------------------------------------------------------
	/**
	 * setting id to this customer
	 * @param customerId customer id
	 */
	public void setCustomerId(int customerId) 
	{
		this.customerId = customerId;
	}
	//-----------------------------------------------------------------------------------------------
	/**
	 * buying coupon and adding it to the customer
	 * @param coupon
	 */
	@Override
	public void purchaseCoupon(Coupon coupon) throws SQLException, Exception 
	{
		Connection tempConn = pool.getConnection();
		
		boolean isValidAmount = isValidAmount(coupon);
		boolean isValidDate = isValidDate(coupon);
		boolean isFirstBuy = isFirstBuy(coupon);
		
		if(isValidAmount && isValidDate && isFirstBuy)
		{
			//updating amount of the coupon in the database
			PreparedStatement tempPreparedStatement = tempConn.prepareStatement(CouponSqlQueries.UPDATE_AMOUNT_OF_COUPON);
			tempPreparedStatement.setInt(1, coupon.getAmount() - 1 );
			tempPreparedStatement.setInt(2, coupon.getId());
			tempPreparedStatement.executeUpdate();
			
			//connecting coupon to customer in customer_coupon database
			addCouponToCustomer(getCustomerId() , coupon.getId());
			
			pool.returnConnection(tempConn);
		}
	}
	//-----------------------------------------------------------------------------------------------
	/**
	 * checking if the amount is valid and bigger than 0
	 * @param coupon the coupon customer wants to buy
	 * @return return true if amount > 0 else false
	 */
	@Override
	public boolean isValidAmount(Coupon coupon) throws SQLException, Exception 
	{
		Connection tempConn = pool.getConnection();
		int tempAmount = 0;
		Statement  tempStatement = tempConn.createStatement();
		ResultSet  tempRs;
		tempRs = tempStatement.executeQuery(String.format(CouponSqlQueries.SELECT_ALL_WHERE_COUPON_TITLE, coupon.getTitle()));
		if(tempRs.next())
		{
			tempAmount = tempRs.getInt("amount");
		}
		
		if(tempAmount>0)
			{
			pool.returnConnection(tempConn);
			return true;
			}
		pool.returnConnection(tempConn);	
		return false;
	}
	//-----------------------------------------------------------------------------------------------
	/**
	 * checking if today is before the end date of the coupon
	 * @param coupon coupon customer wants to buy 
	 * @return return true if today is before , else false
	 */
	@Override
	public boolean isValidDate(Coupon coupon) throws SQLException, Exception 
	{
		Connection tempConn = pool.getConnection();
		Date todayDate = (Date) Calendar.getInstance().getTime();
		Date endDate = null;
		Statement  tempStatement = tempConn.createStatement();
		ResultSet  tempRs;
		tempRs = tempStatement.executeQuery(String.format(CouponSqlQueries.SELECT_ALL_WHERE_COUPON_TITLE, coupon.getTitle()));
		if(tempRs.next())
		{
			endDate = converter.stringToDate(tempRs.getString("END_DATE"));
		}
		
		if(todayDate.before(endDate))
			{
			pool.returnConnection(tempConn);
			return true;
			}
		pool.returnConnection(tempConn);
		return false;
	}
	//-----------------------------------------------------------------------------------------------
	/**
	 * checking if the customer have purchased the coupon before 
	 * @param coupon coupon customer wants to buy 
	 * @return return true if this is the first time customer wants to buy , else false
	 */
	@Override
	public boolean isFirstBuy(Coupon coupon) throws SQLException, Exception 
	{
		Connection tempConn = pool.getConnection();
		Statement  tempStatement = tempConn.createStatement();
		ResultSet  tempRs;
		tempRs = tempStatement.executeQuery(String.format(CustomerCouponSqlQueries.SELECT_ALL_BY_COUPON_ID, coupon.getTitle()));
		if(tempRs.next())
		{
			pool.returnConnection(tempConn);
			return false;
		}
		pool.returnConnection(tempConn);
		return true;
	}
}
