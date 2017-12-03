package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;

import exceptions.DuplicateEntryException;
import exceptions.NullConnectionException;
import exceptions.UnAvailableCouponException;
import javaBeans.Coupon;
import javaBeans.CouponType;

/**
 * this is a interface to configure all the functions and operations that coupon needs
 * @author monder
 * @version 1.0
 */
public interface CouponDAO 
{
	//------------------------------------------interface base functions----------------------------------------- 
	
	/**
	 * Receives a coupon instance and register it in the database
	 * @param coupon a coupon instance
	 * @throws SQLException thrown when the sql query is wrong
	 * @throws ClassNotFoundException thrown when the coupon class is not available
	 * @throws InterruptedException thrown when the thread is interrupted - might be because the system is shutting down
	 * @throws DuplicateEntryException thrown when trying to register a coupon that already exist
	 * @throws NullConnectionException thrown when the connection is null
	 */
	public void createCoupon(Coupon coupon) throws SQLException, ClassNotFoundException, InterruptedException, DuplicateEntryException, NullConnectionException;

	/**
	 * Receives a coupon instance and removes it's entries from the database
	 * @param coupon a coupon instance
	 * @throws SQLException thrown when the sql query is wrong
	 * @throws ClassNotFoundException thrown when the coupon class is not available
	 * @throws InterruptedException thrown when the thread is interrupted - might be because the system is shutting down
	 * @throws NullConnectionException thrown when the connection is null
	 */
	public void removeCoupon(Coupon coupon) throws SQLException, ClassNotFoundException, InterruptedException, DuplicateEntryException, NullConnectionException,UnAvailableCouponException,NullPointerException;

	/**
	 * Receives a coupon instance and update it's entries in the database
	 * @param coupon a coupon instance
	 * @throws ClassNotFoundException thrown when the coupon class is not available
	 * @throws InterruptedException thrown when the thread is interrupted - might be because the system is shutting down
	 * @throws SQLException thrown when the sql query is wrong
	 * @throws ParseException thrown when the date is not in the correct format
	 * @throws NullConnectionException thrown when the connection is null
	 * @throws UnAvailableCouponException 
	 */
	public void updateCoupon(Coupon coupon) throws ClassNotFoundException, InterruptedException, SQLException, ParseException, NullConnectionException, UnAvailableCouponException;

	/**
	 * Retrieves a coupon instance from the database by it's id
	 * @param id the desired coupon's id
	 * @return a coupon instance
	 * @throws ClassNotFoundException thrown when the coupon class is not available
	 * @throws InterruptedException thrown when the thread is interrupted - might be because the system is shutting down
	 * @throws SQLException thrown when the sql query is wrong
	 * @throws ParseException thrown when the date is not in the correct format
	 * @throws NullConnectionException thrown when the connection is null
	 * @throws UnAvailableCouponException 
	 */
	public Coupon getCoupon(long id) throws SQLException, ClassNotFoundException, InterruptedException, ParseException, NullConnectionException, UnAvailableCouponException;

	/**
	 * returns an ArrayList of all the coupons in the database
	 * @return an ArrayList of all the coupons in the database
	 * @throws ClassNotFoundException thrown when the coupon class is not available
	 * @throws InterruptedException thrown when the thread is interrupted - might be because the system is shutting down
	 * @throws SQLException thrown when the sql query is wrong
	 * @throws ParseException thrown when the date is not in the correct format
	 * @throws NullConnectionException thrown when the connection is null
	 * @throws UnAvailableCouponException 
	 */
	public Collection<Coupon> getAllCoupons() throws SQLException, ClassNotFoundException, InterruptedException, ParseException, NullConnectionException, UnAvailableCouponException;

	/**
	 * returns an ArrayList of all the coupons in the database of a given type
	 * @param couponType a type of a coupon
	 * @return an ArrayList of all the coupons in the database of a given type
	 * @throws ClassNotFoundException thrown when the coupon class is not available
	 * @throws InterruptedException thrown when the thread is interrupted - might be because the system is shutting down
	 * @throws SQLException thrown when the sql query is wrong
	 * @throws ParseException thrown when the date is not in the correct format
	 * @throws NullConnectionException thrown when the connection is null
	 */
	public Collection<Coupon> getCouponByType(CouponType couponType) throws SQLException, ClassNotFoundException, InterruptedException, ParseException, NullConnectionException;
	
	//------------------------------------------interface plus functions----------------------------------------- 
	
	/**
	 * checking if coupon exist in the database
	 * @param coupon the coupon object
	 * @throws SQLException SQL exception
	 * @throws Exception
	 * @return return true if coupon exist in the database , else false
	 */
	public boolean isCouponExist(Coupon coupon,Connection tempConn) throws SQLException,NullPointerException ;
	
	/**
	 * insert coupon to database
	 * @param coupon the coupon object
	 * @throws SQLException SQL exception
	 * @throws Exception
	 */
	public void insertCouponToDatabase(Coupon coupon,Connection tempConn) throws SQLException ;
	
	/**
	 * returns an ArrayList of all the coupons in the database that out of date 
	 * @param couponType a type of a coupon
	 * @return an ArrayList of all the coupons in the database of a given type
	 * @throws ClassNotFoundException thrown when the coupon class is not available
	 * @throws InterruptedException thrown when the thread is interrupted - might be because the system is shutting down
	 * @throws SQLException thrown when the sql query is wrong
	 * @throws ParseException thrown when the date is not in the correct format
	 * @throws NullConnectionException thrown when the connection is null
	 */
	public Collection<Coupon> getCouponOutOfDate() throws ClassNotFoundException, InterruptedException, SQLException, ParseException, NullConnectionException;

}