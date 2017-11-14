package dao;

import java.sql.SQLException;
import java.util.Collection;

import javaBeans.Coupon;
import javaBeans.Customer;

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

}
