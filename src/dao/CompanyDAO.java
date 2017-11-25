package dao;

import java.sql.Date;
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
	public void createCompany(Company company)  throws ClassNotFoundException, InterruptedException, SQLException, DuplicateEntryException, NullConnectionException, Exception;

	public void removeCompany(Company company) throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException;

	public void updateCompany(Company company)  throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException;

	public Company getCompany(long id) throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException;

	public Collection<Company> getAllCompanies() throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException;
	
	public Collection<Coupon> getCoupons() throws ClassNotFoundException, InterruptedException, SQLException, ParseException, NullConnectionException;

	public boolean login(String compName, String password) throws ClassNotFoundException, InterruptedException, SQLException, WrongDataInputException, NullConnectionException;
	
	//------------------------------------------interface plus functions----------------------------------------- 
	public Collection<Coupon> getCouponsByCompanyId(long id) throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException;
	
	public boolean isCompanyNameExist(Company company) throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException;
	
	public boolean isCompanyIdExist(Company company) throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException;;
	
	public void insertCompanyToDatabase(Company company) throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException;
	
	public Collection<Coupon> getCompanyCouponByType(CouponType couponType) throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException;

	public Collection<Coupon> getCompanyCouponByPrice(double price) throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException;
	
	public Collection<Coupon> getCompanyCouponByDate(Date date) throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException;
	
	public void addCouponToCompany(int companyId , int couponId) throws ClassNotFoundException, InterruptedException, SQLException, NullConnectionException, ParseException;


	
}
