package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import javaBeans.Company;
import javaBeans.Coupon;
import javaBeans.CouponType;
import mainPackage.ConnectionPool;
import utilities.CompanyCouponSqlQueries;
import utilities.CompanySqlQueries;
import utilities.CouponSqlQueries;
import utilities.CustomerCouponSqlQueries;
import utilities.converter;

public class CompanyDBDAO implements CompanyDAO
{

	private ConnectionPool pool;
	private long companyId;

	public CompanyDBDAO()
	{
		pool = ConnectionPool.getInstance();
	}

	//-------------------------------------------------------------------------------------------------------
	@Override
	public void createCompany(Company company) throws SQLException, Exception 
	{
		if(isCompanyNameExist(company))
		{
			System.out.println("company is exist in the database");
		}
		else
		{
			insertCompanyToDatabase(company);
		}
	}

	//-------------------------------------------------------------------------------------------------------
	@Override
	public void removeCompany(Company company) throws SQLException 
	{
		Connection tempConn = pool.getConnection();

		try 
		{
			if(isCompanyIdExist(company))
			{
				//deleting the company from company table
				Statement  tempDeleteStatement = tempConn.createStatement();
				tempDeleteStatement.execute(String.format(CompanySqlQueries.DELETE_FROM_COMPANY_WHERE_ID,company.getId()));

				//getting all the coupons that company used
				Statement tempCouponsStatement = tempConn.createStatement();
				ResultSet tempCouponsResultSet;
				tempCouponsResultSet = tempCouponsStatement.executeQuery(String.format(CompanyCouponSqlQueries.COUPON_ID_BY_COMP_ID, company.getId()));

				while(tempCouponsResultSet.next())
				{
					int id = tempCouponsResultSet.getInt("coupon_id");
					Statement tempDeleteStatement2 = tempConn.createStatement();

					//delete coupon from coupon table 
					tempDeleteStatement2.execute(String.format(CouponSqlQueries.DELETE_COUPON_BY_ID, id));
					//delete coupon from coupon_customer table 
					tempDeleteStatement2.execute(String.format(CustomerCouponSqlQueries.DELETE_COUPON_CUST_ID, id));
					//delete coupon from coupon_company table 
					tempDeleteStatement2.execute(String.format(CompanyCouponSqlQueries.DELETE_COUPON_COMP_ID, company.getId()));
				}
				System.out.println("company : " + company.getCompName() + " removerd successfull");		    		
			}
			else
			{
				System.out.println("compnay : " + company.getCompName() + " not exist in the database");
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
	//-------------------------------------------------------------------------------------------------------

	@Override
	public void updateCompany(Company company) throws SQLException 
	{
		Connection tempConn = pool.getConnection();
		try
		{
			if(isCompanyIdExist(company))
			{
				PreparedStatement tempPreparedStatement =tempConn.prepareStatement(CompanySqlQueries.UPDATE_COMPANY_BY_ID);
				tempPreparedStatement.setString   (1, company.getPassword());
				tempPreparedStatement.setString   (2, company.getEmail());
				tempPreparedStatement.setLong     (3, company.getId());
				tempPreparedStatement.execute();
				System.out.println("company " + company.getCompName() + " updated successfully");
			}
			else
			{
				System.out.println("company " + company.getCompName() + " not exist in the database");
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

	//-------------------------------------------------------------------------------------------------------
	@Override
	public Company getCompany(long id) throws SQLException 
	{
		Connection tempConn = pool.getConnection();
		Statement  tempStatement = tempConn.createStatement();
		ResultSet  tempRs;
		tempRs = tempStatement.executeQuery(String.format(CompanySqlQueries.SELECT_ALL_WHERE_COMP_ID, id));

		Company tempCompany = new Company();
		if(tempRs.next())
		{
			tempCompany.setId(tempRs.getInt("ID"));
			tempCompany.setCompName(tempRs.getString("COMP_NAME"));
			tempCompany.setPassword(tempRs.getString("PASSWORD"));
			tempCompany.setEmail(tempRs.getString("EMAIL"));

			tempCompany.setCoupons(getCouponsByCompanyId(tempCompany.getId()));
		}
		else
		{
			System.out.println("there is no company with this id ");
		}

		//returning the connection 
		pool.returnConnection(tempConn);

		//returning the company object 
		return tempCompany;
	}
	//-------------------------------------------------------------------------------------------------------

	@Override
	public Collection<Company> getAllCompanies() throws SQLException 
	{
		// TODO Auto-generated method stub
		ArrayList<Company> tempCompanyArray = new ArrayList<>();
		
		Connection tempConn = pool.getConnection();
		Statement  tempStatement = tempConn.createStatement();
		ResultSet  tempRs;
		
		tempRs = tempStatement.executeQuery(CompanySqlQueries.SELECT_ID_FROM_COMPANY);
		while(tempRs.next())
		{
			Company tempCompany = getCompany(tempRs.getInt("ID"));
			tempCompanyArray.add(tempCompany);
		}
		pool.returnConnection(tempConn);
		return tempCompanyArray;
	}
	//-------------------------------------------------------------------------------------------------------

	@Override
	public Collection<Coupon> getCoupons() throws SQLException 
	{
		ArrayList<Coupon> arrayOfCompanyCoupons = new ArrayList<Coupon>(this.getCouponsByCompanyId(getCompanyId()));
		return arrayOfCompanyCoupons;
	}
	//-------------------------------------------------------------------------------------------------------

	@Override
	public boolean login(String compName, String password)  
	{
		Connection tempConn = pool.getConnection();
		ResultSet  tempRs;
		
		try 
		{
			Statement  tempStatement = tempConn.createStatement();
			tempRs = tempStatement.executeQuery(String.format(CompanySqlQueries.SELECT_ID_PASSWORD, compName,password));
			if(tempRs.next())
			{
				setCompanyId(tempRs.getLong("ID"));
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
	//-------------------------------------------------------------------------------------------------------

	@Override
	public Collection<Coupon> getCouponsByCompanyId(long id) throws SQLException 
	{
		ArrayList<Coupon> tempCouponsArray = new ArrayList<>();

		//getting connection from the pool
		Connection tempConn = pool.getConnection();
		Statement  tempStatement = tempConn.createStatement();
		ResultSet  tempResultSet;

		tempResultSet = tempStatement.executeQuery(String.format(CompanyCouponSqlQueries.COUPON_ID_BY_COMP_ID, id));
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
	//-------------------------------------------------------------------------------------------------------

	@Override
	public boolean isCompanyNameExist(Company company) throws SQLException, Exception 
	{
		Connection tempConn = pool.getConnection();
		Statement  tempStatement = tempConn.createStatement();
		ResultSet  tempRs;
		try
		{
			tempRs = tempStatement.executeQuery(String.format(CompanySqlQueries.SELECT_ALL_WHERE_COMP_NAME,company.getCompName()));
			if(tempRs.next())
			{
				pool.returnConnection(tempConn);
				return true;
			}
		}
		catch (SQLException e) 
		{
			// TODO: handle exception
		}
		pool.returnConnection(tempConn);
		return false;
	}
	//-------------------------------------------------------------------------------------------------------

	@Override
	public boolean isCompanyIdExist(Company company) throws SQLException, Exception 
	{
		Connection tempConn = pool.getConnection();
		Statement  tempStatement = tempConn.createStatement();
		ResultSet  tempRs;
		try
		{
			tempRs = tempStatement.executeQuery(String.format(CompanySqlQueries.SELECT_ALL_WHERE_COMP_ID,company.getId()));
			if(tempRs.next())
			{
				pool.returnConnection(tempConn);
				return true;
			}
		}
		catch (SQLException e) 
		{
			// TODO: handle exception
		}
		pool.returnConnection(tempConn);
		return false;
	}
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public void insertCompanyToDatabase(Company company) throws SQLException, Exception
	{
		Connection tempConn = pool.getConnection();

		try
		{
			//creating the preparedStatement
			PreparedStatement tempPreparedStatement = tempConn.prepareStatement(CompanySqlQueries.INSERT_COMPANY);

			tempPreparedStatement.setString(1, company.getCompName());
			tempPreparedStatement.setString(2, company.getPassword());
			tempPreparedStatement.setString(3, company.getEmail());

			// execute the preparedStatement
			tempPreparedStatement.execute();
		}
		catch (SQLException e)
		{
			// TODO: handle exception
		}
		System.out.println("company : " +company.getCompName() + " added successfully");
		pool.returnConnection(tempConn);
	}
	//-----------------------------------------------------------------------------------------------------------


	public long getCompanyId() 
	{
		return companyId;
	}

	public void setCompanyId(long companyId)
	{
		this.companyId = companyId;
	}
	//--------------------------------------------------------------------------------------------------------------

	@Override
	public Collection<Coupon> getCompanyCouponByType(CouponType couponType) throws SQLException, Exception
	{
		// getting all the coupons for the company 
		ArrayList<Coupon> ArrayOfCompanyCoupons = new ArrayList<>(this.getCouponsByCompanyId(getCompanyId()));
		
		ArrayList<Coupon> ArrayOfCouponsByType = new ArrayList<>();
		// for each loop to get coupons for the type
		for(Coupon c : ArrayOfCompanyCoupons)
		{
			if(c.getType().equals(couponType))
				ArrayOfCouponsByType.add(c);
		}
			return ArrayOfCouponsByType;
		
	}
	//--------------------------------------------------------------------------------------------------------------

	@Override
	public Collection<Coupon> getCompanyCouponByPrice(double price) throws SQLException, Exception 
	{
		// getting all the coupons for the company 
		ArrayList<Coupon> ArrayOfCompanyCoupons = new ArrayList<>(this.getCouponsByCompanyId(getCompanyId()));
		
		ArrayList<Coupon> ArrayOfCouponsByPrice = new ArrayList<>();
		// for each loop to get coupons for the type
		for(Coupon c : ArrayOfCompanyCoupons)
		{
			if(c.getPrice()<price)
				ArrayOfCouponsByPrice.add(c);
		}
			return ArrayOfCouponsByPrice;
	}
	//--------------------------------------------------------------------------------------------------------------

	@Override
	public Collection<Coupon> getCompanyCouponByDate(Date date) throws SQLException, Exception 
	{
		// getting all the coupons for the company 
		ArrayList<Coupon> ArrayOfCompanyCoupons = new ArrayList<>(this.getCouponsByCompanyId(getCompanyId()));
		
		ArrayList<Coupon> ArrayOfCouponsByDate = new ArrayList<>();
		// for each loop to get coupons for the type
		for(Coupon c : ArrayOfCompanyCoupons)
		{
			if(c.getEndDate().before(date))
				ArrayOfCouponsByDate.add(c);
		}
			return ArrayOfCouponsByDate;
	}
	
}
