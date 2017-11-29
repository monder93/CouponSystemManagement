package dao;

import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;

import exceptions.*;
import javaBeans.Company;
import javaBeans.Coupon;
import javaBeans.CouponType;

/**
 * this is a interface to configure all the functions and operations that company needs
 * @author monder
 * @version 1.0
 */
public interface CompanyDAO 
{

	//------------------------------------------interface base functions----------------------------------------- 
	/**
	 * Receives a company's instance and register it in the database
	 * @param company a company's instance
	 * @throws ClassNotFoundException thrown when the company class is not available
	 * @throws InterruptedException thrown when the thread is interrupted - might be because the system is shutting down
	 * @throws SQLException thrown when the sql query is wrong
	 * @throws DuplicateEntryException thrown when trying to register a company that already exist
	 * @throws NullConnectionException thrown when the connection is null
	 * @throws ParseException 
	 */
	public void createCompany(Company company)  throws ClassNotFoundException, InterruptedException, SQLException, DuplicateEntryException, NullConnectionException, ParseException;

	/**
	 * removes a company from the database
	 * @param company receives a company's instance and removes it's entries from the database
	 * @throws ClassNotFoundException thrown when the company class is not available
	 * @throws InterruptedException thrown when the thread is interrupted - might be because the system is shutting down
	 * @throws SQLException thrown when the sql query is wrong
	 * @throws NullConnectionException thrown when the connection is null
	 */
	public void removeCompany(Company company) throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException;

	/**
	 * update a company in the database
	 * @param company receives a company's instance and updates it's entries in the database
	 * @throws ClassNotFoundException thrown when the company class is not available
	 * @throws InterruptedException thrown when the thread is interrupted - might be because the system is shutting down
	 * @throws SQLException thrown when the sql query is wrong
	 * @throws NullConnectionException thrown when the connection is null
	 */
	public void updateCompany(Company company)  throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException;

	/**
	 * Receives an id of a company and returns an instance of that company from the database
	 * @param id the id of the desired company
	 * @return an instance of a company
	 * @throws ClassNotFoundException thrown when the company class is not available
	 * @throws InterruptedException thrown when the thread is interrupted - might be because the system is shutting down
	 * @throws SQLException thrown when the sql query is wrong
	 * @throws NullConnectionException thrown when the connection is null
	 * @throws ParseException thrown when the date's are not in the correct format
	 */
	public Company getCompany(long id) throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException;

	/**
	 * returns an ArrayList of all the companys in the database
	 * @return an ArrayList of all the companys in the database
	 * @throws ClassNotFoundException thrown when the company class is not available
	 * @throws InterruptedException thrown when the thread is interrupted - might be because the system is shutting down
	 * @throws SQLException thrown when the sql query is wrong
	 * @throws NullConnectionException thrown when the connection is null
	 * @throws ParseException thrown when the date's are not in the correct format
	 */
	public Collection<Company> getAllCompanies() throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException;
	
	/**
	 * returns an ArrayList of all the coupons of the current user's company in the database
	 * @return an ArrayList of all the coupons of the current user's company in the database
	 * @throws ClassNotFoundException thrown when the coupon class is not available
	 * @throws InterruptedException thrown when the thread is interrupted - might be because the system is shutting down
	 * @throws SQLException thrown when the sql query is wrong
	 * @throws ParseException thrown when the date's are not in the correct format
	 * @throws NullConnectionException thrown when the connection is null
	 */
	public Collection<Coupon> getCoupons() throws ClassNotFoundException, InterruptedException, SQLException, ParseException, NullConnectionException;

	/**
	 * checks the database for a company with the given name and the given password
	 * @param compName the company's name
	 * @param password the company's password
	 * @return true value if there is a match' false if there is no match
	 * @throws ClassNotFoundException thrown when the company class is not available
	 * @throws InterruptedException thrown when the thread is interrupted - might be because the system is shutting down
	 * @throws SQLException thrown when the sql query is wrong
	 * @throws WrongDataInputException thrown when the input does not match any company in the database
	 * @throws NullConnectionException thrown when the connection is null
	 */
	public boolean login(String compName, String password) throws ClassNotFoundException, InterruptedException, SQLException, WrongDataInputException, NullConnectionException;
	
	//------------------------------------------interface plus functions----------------------------------------- 
	
	/**
	 * returns an ArrayList of all the coupons of specific user's company in the database
	 * @return an ArrayList of all the coupons of the current user's company in the database
	 * @throws ClassNotFoundException thrown when the coupon class is not available
	 * @throws InterruptedException thrown when the thread is interrupted - might be because the system is shutting down
	 * @throws SQLException thrown when the sql query is wrong
	 * @throws ParseException thrown when the date's are not in the correct format
	 * @throws NullConnectionException thrown when the connection is null
	 */
	public Collection<Coupon> getCouponsByCompanyId(long id) throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException;
	
	/**
	 * checking if company name is exist in the database
	 * @return an ArrayList of all the coupons of the current user's company in the database
	 * @throws ClassNotFoundException thrown when the coupon class is not available
	 * @throws InterruptedException thrown when the thread is interrupted - might be because the system is shutting down
	 * @throws SQLException thrown when the sql query is wrong
	 * @throws ParseException thrown when the date's are not in the correct format
	 * @throws NullConnectionException thrown when the connection is null
	 */
	public boolean isCompanyNameExist(Company company) throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException;
	
	/**
	 * checking if company id is exist in the database
	 * @return an ArrayList of all the coupons of the current user's company in the database
	 * @throws ClassNotFoundException thrown when the coupon class is not available
	 * @throws InterruptedException thrown when the thread is interrupted - might be because the system is shutting down
	 * @throws SQLException thrown when the sql query is wrong
	 * @throws ParseException thrown when the date's are not in the correct format
	 * @throws NullConnectionException thrown when the connection is null
	 */
	public boolean isCompanyIdExist(Company company) throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException;;
	
	/**
	 * inserting company to the database
	 * @return an ArrayList of all the coupons of the current user's company in the database
	 * @throws ClassNotFoundException thrown when the coupon class is not available
	 * @throws InterruptedException thrown when the thread is interrupted - might be because the system is shutting down
	 * @throws SQLException thrown when the sql query is wrong
	 * @throws ParseException thrown when the date's are not in the correct format
	 * @throws NullConnectionException thrown when the connection is null
	 */
	public void insertCompanyToDatabase(Company company) throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException;
	
	/**
	 * returns an ArrayList of all the coupons of the current company in the database by type
	 * @return an ArrayList of all the coupons of the current user's company in the database
	 * @throws ClassNotFoundException thrown when the coupon class is not available
	 * @throws InterruptedException thrown when the thread is interrupted - might be because the system is shutting down
	 * @throws SQLException thrown when the sql query is wrong
	 * @throws ParseException thrown when the date's are not in the correct format
	 * @throws NullConnectionException thrown when the connection is null
	 */
	public Collection<Coupon> getCompanyCouponByType(CouponType couponType) throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException;

	/**
	 * returns an ArrayList of all the coupons of the current company in the database by price
	 * @return an ArrayList of all the coupons of the current user's company in the database
	 * @throws ClassNotFoundException thrown when the coupon class is not available
	 * @throws InterruptedException thrown when the thread is interrupted - might be because the system is shutting down
	 * @throws SQLException thrown when the sql query is wrong
	 * @throws ParseException thrown when the date's are not in the correct format
	 * @throws NullConnectionException thrown when the connection is null
	 */
	public Collection<Coupon> getCompanyCouponByPrice(double price) throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException;
	
	/**
	 * returns an ArrayList of all the coupons of the current company in the database by date
	 * @return an ArrayList of all the coupons of the current user's company in the database
	 * @throws ClassNotFoundException thrown when the coupon class is not available
	 * @throws InterruptedException thrown when the thread is interrupted - might be because the system is shutting down
	 * @throws SQLException thrown when the sql query is wrong
	 * @throws ParseException thrown when the date's are not in the correct format
	 * @throws NullConnectionException thrown when the connection is null
	 */
	public Collection<Coupon> getCompanyCouponByDate(Date date) throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException;
	
	/**
	 * inserting companyId and couponId to the database ,, connecting coupon and company
	 * @return an ArrayList of all the coupons of the current user's company in the database
	 * @throws ClassNotFoundException thrown when the coupon class is not available
	 * @throws InterruptedException thrown when the thread is interrupted - might be because the system is shutting down
	 * @throws SQLException thrown when the sql query is wrong
	 * @throws ParseException thrown when the date's are not in the correct format
	 * @throws NullConnectionException thrown when the connection is null
	 */
	public void addCouponToCompany(int companyId , int couponId) throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException, DuplicateEntryException;
}
