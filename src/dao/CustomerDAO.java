package dao;

import java.sql.SQLException;
import java.util.Collection;

import javaBeans.Coupon;
import javaBeans.CouponType;
import javaBeans.Customer;

/**
 * this is a interface to configure all the functions and operations that customer needs
 * @author monder
 * @version 1.0
 */
public interface CustomerDAO 
{
	//------------------------------------------interface base functions----------------------------------------- 
	public void createCustomer(Customer customer) throws SQLException, Exception;

	public void removeCustomer(Customer customer) throws SQLException;

	public void updateCustomer(Customer customer) throws SQLException;

	public Customer getCustomer(long id) throws SQLException;

	public Collection<Customer> getAllCustomer() throws SQLException;
	
	public Collection<Coupon>  getCoupons() throws SQLException;;

	public boolean login(String custName, String password) throws SQLException, Exception;
	
	//------------------------------------------interface plus functions----------------------------------------- 

	public boolean isCustomerExist(Customer customer) throws SQLException , Exception;
	
	public void insertCustomerToDatabase(Customer customer) throws SQLException , Exception;
	
	public Collection<Coupon> getCouponsByCustomerId(long id) throws SQLException;

	public Collection<Coupon>  getCouponsByType(CouponType couponType) throws SQLException;
	
	public Collection<Coupon>  getCouponsByPrice(double price) throws SQLException;
	
	public void addCouponToCustomer(int customerId , int couponId) throws SQLException, Exception;

	public void purchaseCoupon(Coupon coupon) throws SQLException, Exception;
	
	public boolean isValidAmount(Coupon coupon) throws SQLException, Exception;
	
	public boolean isValidDate(Coupon coupon) throws SQLException, Exception;

	public boolean isFirstBuy(Coupon coupon) throws SQLException, Exception;

}
