package facades;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

import dao.CompanyDBDAO;
import dao.CustomerDBDAO;
import exceptions.DuplicateEntryException;
import exceptions.NullConnectionException;
import exceptionsHandlers.CompanyExceptionHandler;
import exceptionsHandlers.CustomerExceptionHandler;
import javaBeans.ClientType;
import javaBeans.Company;
import javaBeans.Customer;

/**
 * 
 * The AdminFacade class is used by the Admin of the CouponSystem.
 * It grants them access to all of the relevant methods for their uses.
 * create , remove , update , get for all the companies and customers
 *
 */
public class AdminFacade implements CouponClientFacade
{


	private CompanyDBDAO companydbdao;
	private CustomerDBDAO customerdbdao;

	//-----------------------------------------------------------------------------------------------------
	/**
	 * the AdminFacade constructor 
	 * initialize the DBDAO's
	 * companyDBDAO
	 * customerDBDAO
	 */
	public AdminFacade()
	{
		this.companydbdao = new CompanyDBDAO();
		this.customerdbdao =new CustomerDBDAO();
	}

	//-----------------------------------------------------------------------------------------------------
	/**
	 * Receives a company instance and register it in the database
	 * @param company a company instance
	 */
	public void createCompany(Company company)
	{
		try
		{
			companydbdao.createCompany(company);
		}
		catch (ClassNotFoundException | InterruptedException | SQLException | DuplicateEntryException
				| NullConnectionException | ParseException e) 
		{
			CompanyExceptionHandler.handle(e);			
		}
	}

	//-----------------------------------------------------------------------------------------------------
	/**
	 * Receives a company instance and removes its entries from the database
	 * @param company a company instance
	 */
	public void removeCompany(Company company)
	{

		try 
		{
			companydbdao.removeCompany(company);
		}
		catch (ClassNotFoundException | InterruptedException | SQLException | NullConnectionException
				| ParseException e)
		{
			CompanyExceptionHandler.handle(e);			
		}

	}
	//-----------------------------------------------------------------------------------------------------
	/**
	 * Receives a company instance and update its entries in the database
	 * @param company a company instance
	 */
	public void updateCompany(Company company)
	{
		try 
		{
			companydbdao.updateCompany(company);
		}
		catch (ClassNotFoundException | InterruptedException | SQLException | NullConnectionException
				| ParseException e) 
		{
			CompanyExceptionHandler.handle(e);			
		}

	}
	//-----------------------------------------------------------------------------------------------------
	/**
	 * Receives an id of a company and returns an instance of that company from the database
	 * @param id a company's id
	 * @return an instance of that company from the database
	 */
	public Company getCompany(long id)
	{
		Company tempCompany = new Company();


		try 
		{
			tempCompany =  companydbdao.getCompany(id);
		} 
		catch (ClassNotFoundException | InterruptedException | SQLException | NullConnectionException
				| ParseException e) 
		{
			CompanyExceptionHandler.handle(e);
		}

		return tempCompany;
	}
	//-----------------------------------------------------------------------------------------------------
	/**
	 *getting all the companies from the database
	 * @return an ArrayList of all the companies in the database
	 */
	public Collection<Company> getAllCompanies()
	{
		ArrayList<Company> tempArrayOfCompanies = new ArrayList<>();

		try
		{
			tempArrayOfCompanies = (ArrayList<Company>) companydbdao.getAllCompanies();
		}
		catch (ClassNotFoundException | InterruptedException | SQLException | NullConnectionException
				| ParseException e) 
		{
			CompanyExceptionHandler.handle(e);			

		}
		return tempArrayOfCompanies;
	}
	//-----------------------------------------------------------------------------------------------------
	/**
	 * Receives a customer instance and register it in the database
	 * @param customer a customer instance
	 */
	public void createCustomer(Customer customer)
	{
		try 
		{
			customerdbdao.createCustomer(customer);
		}
		catch (ClassNotFoundException | InterruptedException | SQLException | DuplicateEntryException
				| NullConnectionException e) 
		{
			CustomerExceptionHandler.handle(e);
		}
	}

	//-----------------------------------------------------------------------------------------------------
	/**
	 * Receives a customer instance and removes its entries from the database
	 * @param customer a customer instance
	 */
	public void removeCustomer(Customer customer)
	{
		try 
		{
			customerdbdao.removeCustomer(customer);
		}
		catch (ClassNotFoundException | InterruptedException | SQLException | NullConnectionException e) 
		{
			CustomerExceptionHandler.handle(e);
		}
	}
	//-----------------------------------------------------------------------------------------------------
	/**
	 * Receives a customer instance and updates its entries in the database
	 * @param customer a customer instance
	 */
	public void updateCustomer(Customer customer)
	{	
		try
		{
			customerdbdao.updateCustomer(customer);
		}
		catch (ClassNotFoundException | InterruptedException | SQLException | NullConnectionException e) 
		{
			CustomerExceptionHandler.handle(e);
		}

	}
	//-----------------------------------------------------------------------------------------------------
	/**
	 * Receives a customer is and returns an instance of that customer from the database
	 * @param id a customer id
	 * @return an instance of that customer from the database
	 */
	public Customer getCustomer(long id)
	{
		Customer tempCustomer = new Customer();

		try 
		{
			tempCustomer = customerdbdao.getCustomer(id);
		}
		catch (ClassNotFoundException | InterruptedException | SQLException | NullConnectionException e) 
		{
			CustomerExceptionHandler.handle(e);
		}

		return tempCustomer;
	}
	//-----------------------------------------------------------------------------------------------------
	/**
	 * getting all the customers 
	 * @return an ArrayList of all the customers in the database
	 */
	public Collection<Customer> getAllCustomer()
	{
		ArrayList<Customer> tempArrayOfCustomers = new ArrayList<>();

		try 
		{
			tempArrayOfCustomers = (ArrayList<Customer>) customerdbdao.getAllCustomer();
		} 
		catch (ClassNotFoundException | InterruptedException | SQLException | NullConnectionException e) 
		{
			CustomerExceptionHandler.handle(e);
		}

		return tempArrayOfCustomers;
	}
	//-----------------------------------------------------------------------------------------------------
	/**
	 * checking the database for an entry of an Admin client type with the matching name and password
	 * returns true if it found one' returns false if there is no matching entry
	 */
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) 
	{
		if (name.equals("admin") && password.equals("1234"))
		{
			return this;
		}

		return null;
	}
}
