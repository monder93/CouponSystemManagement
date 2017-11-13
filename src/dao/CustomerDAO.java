package dao;

import java.sql.SQLException;
import java.util.Collection;
import javaBeans.Customer;

public interface CustomerDAO 
{
	
	public void createCustomer(Customer customer) throws SQLException;

	public void removeCustomer(Customer customer) throws SQLException;

	public void updateCustomer(Customer customer) throws SQLException;

	public Customer getCustomer(long id) throws SQLException;

	public Collection<Customer> getAllCustomers() throws SQLException;
	
	public Collection<Customer>  getCoupons() throws SQLException;;

	public boolean login(String custName, String password) throws SQLException, Exception;

	public boolean isCustomerExist(Customer customer) throws SQLException , Exception;
	
	public void insertCustomerToDatabase(Customer customer) throws SQLException , Exception;
}
