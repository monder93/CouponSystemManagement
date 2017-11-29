package dao;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

import exceptions.DuplicateEntryException;
import exceptions.NullConnectionException;
import exceptions.WrongDataInputException;
import javaBeans.Company;
import javaBeans.Coupon;
import javaBeans.CouponType;
import mainPackage.ConnectionPool;
import utilities.CompanyCouponSqlQueries;
import utilities.CompanySqlQueries;
import utilities.CouponSqlQueries;
import utilities.CustomerCouponSqlQueries;
import utilities.converter;

/**
 * this class implements a Company data access object to perform operations with objects and database 
 * Receives data from the database to the user || write data received from the user to the database 
 * @author monder
 * @version 1.0
 */
public class CompanyDBDAO implements CompanyDAO
{
	private ConnectionPool pool;
	private int companyId;

	/**
	 * this constructs a CompanyDBDAO and initialize the ConnectionPool variable 
	 */
	public CompanyDBDAO()
	{
		pool = ConnectionPool.getInstance();
	}
	//-------------------------------------------------------------------------------------------------------
	/**
	 * getting as a parameter company and adding it to the database if not exist
	 * @param company instance object of a company 
	 * @throws ParseException 
	 */
	@Override
	public void createCompany(Company company) throws ClassNotFoundException, InterruptedException, SQLException, DuplicateEntryException, NullConnectionException, ParseException
	{
		if(isCompanyNameExist(company))
		{
			throw new DuplicateEntryException("the admin tried to creat a company with a name that already exist in the datbase");
		}
		else
		{
			insertCompanyToDatabase(company);
		}
	}
	//-------------------------------------------------------------------------------------------------------
	/**
	 * getting as a parameter company and removing it from the database if exist
	 * @param company instance object of a company
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 * @throws SQLException
	 * @throws NullConnectionException
	 * @throws ParseException 
	 */
	@Override
	public void removeCompany(Company company) throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException
	{
		Connection tempConn = pool.getConnection();

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
		pool.returnConnection(tempConn);


	}
	//-------------------------------------------------------------------------------------------------------
	/**
	 * getting as a parameter company and updating it in the database if exist
	 * @param company instance object of a company
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 * @throws SQLException
	 * @throws NullConnectionException
	 * @throws ParseException
	 */
	@Override
	public void updateCompany(Company company) throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException 
	{
		Connection tempConn = pool.getConnection();

		if(isCompanyIdExist(company))
		{
			PreparedStatement tempPreparedStatement =tempConn.prepareStatement(CompanySqlQueries.UPDATE_COMPANY_BY_ID);
			tempPreparedStatement.setString   (1, company.getPassword());
			tempPreparedStatement.setString   (2, company.getEmail());
			tempPreparedStatement.setInt     (3, company.getId());
			tempPreparedStatement.execute();
			System.out.println("company " + company.getCompName() + " updated successfully");
		}
		else
		{
			System.out.println("company " + company.getCompName() + " not exist in the database");
		}
		pool.returnConnection(tempConn);			

	}
	//-------------------------------------------------------------------------------------------------------
	/**
	 * getting as a parameter int id  and retrieve company data from  the database if exist
	 * @param id id of a company
	 * @return returns a company object 
	 */
	@Override
	public Company getCompany(long id) throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException 
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
	/**
	 * getting all the companies from the database
	 * @return return a collection<company> with all the companies inside it 
	 */
	@Override
	public Collection<Company> getAllCompanies() throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException 
	{
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
	/**
	 * getting all the coupons of this company 
	 * @return returning collection<Coupon> with all the coupons of this company inside it 
	 */
	@Override
	public Collection<Coupon> getCoupons() throws ClassNotFoundException, InterruptedException, SQLException, ParseException, NullConnectionException
	{
		ArrayList<Coupon> arrayOfCompanyCoupons = new ArrayList<Coupon>(this.getCouponsByCompanyId(getCompanyId()));
		return arrayOfCompanyCoupons;
	}
	//-------------------------------------------------------------------------------------------------------
	/**
	 * login function to perform login operation
	 * @param compName company name 
	 * @param password password of the company
	 * @return returns true if compName and password is correct and they are in the database , else returns false
	 */
	@Override
	public boolean login(String compName, String password)  throws ClassNotFoundException, InterruptedException, SQLException, WrongDataInputException, NullConnectionException
	{
		Connection tempConn = pool.getConnection();
		ResultSet  tempRs;

		Statement  tempStatement = tempConn.createStatement();
		tempRs = tempStatement.executeQuery(String.format(CompanySqlQueries.SELECT_ID_PASSWORD, compName,password));
		if(tempRs.next())
		{
			setCompanyId(tempRs.getInt("ID"));
			return true;
		}
		pool.returnConnection(tempConn);

		return false;
	}
	//-------------------------------------------------------------------------------------------------------
	/**
	 * getting coupons by company ID
	 * @param id id of the company 
	 * @return returning a collection<Coupon> inside it coupons of company with the id in the parameter
	 */
	@Override
	public Collection<Coupon> getCouponsByCompanyId(long id) throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException
	{
		ArrayList<Coupon> tempCouponsArray = new ArrayList<>();

		//getting connection from the pool
		Connection tempConn = pool.getConnection();
		Statement  tempStatement = tempConn.createStatement();
		ResultSet  tempResultSet;

		tempResultSet = tempStatement.executeQuery(String.format(CompanyCouponSqlQueries.COUPON_ID_BY_COMP_ID, id));

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

		//returning the connection to the pool
		pool.returnConnection(tempConn);

		return tempCouponsArray;
	}
	//-------------------------------------------------------------------------------------------------------
	/**
	 * checks if company name is exist
	 * @param Company company object
	 * @return return true if company name is exist in the database , else return false
	 */
	@Override
	public boolean isCompanyNameExist(Company company) throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException
	{
		Connection tempConn = pool.getConnection();
		Statement  tempStatement = tempConn.createStatement();
		ResultSet  tempRs;

		tempRs = tempStatement.executeQuery(String.format(CompanySqlQueries.SELECT_ALL_WHERE_COMP_NAME,company.getCompName()));
		if(tempRs.next())
		{
			pool.returnConnection(tempConn);
			return true;
		}

		pool.returnConnection(tempConn);
		return false;
	}
	//-------------------------------------------------------------------------------------------------------
	/**
	 * checks if company Id is exist
	 * @param Company company object
	 * @return return true if company id is exist in the database , else return false
	 */
	@Override
	public boolean isCompanyIdExist(Company company) throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException
	{
		Connection tempConn = pool.getConnection();
		Statement  tempStatement = tempConn.createStatement();
		ResultSet  tempRs;

		tempRs = tempStatement.executeQuery(String.format(CompanySqlQueries.SELECT_ALL_WHERE_COMP_ID,company.getId()));
		if(tempRs.next())
		{
			pool.returnConnection(tempConn);
			return true;
		}

		pool.returnConnection(tempConn);
		return false;
	}
	//-----------------------------------------------------------------------------------------------------------
	/**
	 * inserting company to the database 
	 * @param company company object instance 
	 */
	@Override
	public void insertCompanyToDatabase(Company company)  throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException	
	{
		Connection tempConn = pool.getConnection();

		//creating the preparedStatement
		PreparedStatement tempPreparedStatement = tempConn.prepareStatement(CompanySqlQueries.INSERT_COMPANY);

		//System.out.println(company.getCompName());
		//System.out.println(company.getPassword());
		//System.out.println(company.getEmail());


		tempPreparedStatement.setString(1, company.getCompName());
		tempPreparedStatement.setString(2, company.getPassword());
		tempPreparedStatement.setString(3, company.getEmail());

		tempPreparedStatement.execute();

		System.out.println("company : " +company.getCompName() + " added successfully");
		pool.returnConnection(tempConn);
	}
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * getting company coupons by Type 
	 * @param couponType type of the coupon
	 * @return return collection<Coupon> inside it the company coupons by specific type
	 */
	@Override
	public Collection<Coupon> getCompanyCouponByType(CouponType couponType)  throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException
	{
		Connection tempConn = pool.getConnection();
		ArrayList<Coupon> ArrayOfCouponsByType = new ArrayList<>();
		Statement  tempStatement = tempConn.createStatement();
		ResultSet  tempRs;

		// getting all the COUPON_ID for the company with specific type
		tempRs = tempStatement.executeQuery(String.format(CompanyCouponSqlQueries.SELECT_COUPON_COMPANY_BY_TYPE,companyId,couponType.toString()));


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
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * getting company coupons by price limited 
	 * @param price limit price
	 * @return return collection<Coupon> inside it the company coupons by price limit
	 */
	@Override
	public Collection<Coupon> getCompanyCouponByPrice(double price)  throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException
	{
		Connection tempConn = pool.getConnection();
		ArrayList<Coupon> ArrayOfCouponsByPrice = new ArrayList<>();
		Statement  tempStatement = tempConn.createStatement();
		ResultSet  tempRs;

		// getting all the COUPON_ID for the company with date before date parameter
		tempRs = tempStatement.executeQuery(String.format(CompanyCouponSqlQueries.SELECT_COUPON_COMPANY_BY_PRICE,companyId,price));


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
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * getting company coupons by Date 
	 * @param date max date for the coupon to be active
	 * @return return collection<Coupon> inside it the company coupons by date
	 */
	@Override
	public Collection<Coupon> getCompanyCouponByDate(Date date)  throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException 
	{
		Connection tempConn = pool.getConnection();
		ArrayList<Coupon> ArrayOfCouponsByDate = new ArrayList<>();
		Statement  tempStatement = tempConn.createStatement();
		ResultSet  tempRs;

		// getting all the COUPON_ID for the company with date before date parameter
		tempRs = tempStatement.executeQuery(String.format(CompanyCouponSqlQueries.SELECT_COUPON_COMPANY_BY_DATE,companyId,converter.dateToString(date)));


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

			ArrayOfCouponsByDate.add(tempCoupon);		
		}

		pool.returnConnection(tempConn);

		return ArrayOfCouponsByDate;
	}
	//-----------------------------------------------------------------------------------------------------------
	/**
	 * connecting a coupon to a company 
	 * @param companyId id of the company
	 * @param couponId  id of the coupon
	 * @throws DuplicateEntryException 
	 */
	@Override
	public void addCouponToCompany(int companyId, int couponId)  throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException, DuplicateEntryException
	{
		Connection tempConn = pool.getConnection();

		//check if coupon exist in company_coupon table
		//COMP_ID_BY_COUPON_ID
		Statement stmt = (Statement) tempConn.createStatement();
		ResultSet rs;
		// the mysql select statement for the correct coupon
		rs = stmt.executeQuery(String.format(CompanyCouponSqlQueries.SELECT_ALL_WHERE_COUPON_ID_COMP_ID, couponId , companyId));

		if(!rs.next())
		{
			//creating the preparedStatement
			PreparedStatement tempPreparedStatement = tempConn.prepareStatement(CompanyCouponSqlQueries.INSERT_COUPON_TO_COMPANY);

			tempPreparedStatement.setInt(1, companyId);
			tempPreparedStatement.setInt(2, couponId);

			// execute the preparedStatement
			tempPreparedStatement.execute();
		}
		else
		{
			throw new DuplicateEntryException("coupon exist for this company");
			//System.out.println("coupon exist for this company");
		}
		pool.returnConnection(tempConn);
	}
	//-----------------------------------------------------------------------------------------------------------
	/**
	 * 
	 * @return company id 
	 */
	public int getCompanyId() 
	{
		return companyId;
	}
	//-----------------------------------------------------------------------------------------------------------
	/**
	 * setting id to this company
	 * @param companyId company id
	 */
	public void setCompanyId(int companyId)
	{
		this.companyId = companyId;
	}
}