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
import javaBeans.Customer;
import mainPackage.ConnectionPool;
import utilities.CouponSqlQueries;
import utilities.CustomerCouponSqlQueries;
import utilities.CustomerSqlQueries;
import utilities.converter;

public class CustomerDBDAO implements CustomerDAO
{
	private ConnectionPool pool;
	private long customerId;

	public CustomerDBDAO()
	{
		this.pool = ConnectionPool.getInstance();
	}

	//-----------------------------------------------------------------------------------------------------
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

	@Override
	public Collection<Coupon> getCoupons() throws SQLException 
	{
		ArrayList<Coupon> arrayOfCustomersCoupons = new ArrayList<Coupon>(this.getCouponsByCustomerId(getCustomerId()));
		return arrayOfCustomersCoupons;
	}
	
	//----------------------------------------------------------------------------------------------------------

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
				setCustomerId(tempRs.getLong("ID"));
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
	public long getCustomerId() {
		return customerId;
	}
	//-----------------------------------------------------------------------------------------------

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
}
