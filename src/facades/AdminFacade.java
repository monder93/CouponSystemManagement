package facades;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import dao.CompanyDBDAO;
import dao.CustomerDBDAO;
import javaBeans.Company;
import javaBeans.Customer;

public class AdminFacade implements CouponClientFacade
{

	
	private CompanyDBDAO companydbdao;
	private CustomerDBDAO customerdbdao;

	//-----------------------------------------------------------------------------------------------------
	public AdminFacade()
	{
		this.companydbdao = new CompanyDBDAO();
		this.customerdbdao =new CustomerDBDAO();
	}
	
	//-----------------------------------------------------------------------------------------------------

	public void createCompany(Company company)
	{
		try 
		{
			companydbdao.createCompany(company);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//-----------------------------------------------------------------------------------------------------
	public void removeCompany(Company company)
	{
		try 
		{
			companydbdao.removeCompany(company);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//-----------------------------------------------------------------------------------------------------

	public void updateCompany(Company company)
	{
		try 
		{
			companydbdao.updateCompany(company);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//-----------------------------------------------------------------------------------------------------

	public Company getCompany(long id)
	{
		Company tempCompany = new Company();
		try
		{
			tempCompany =  companydbdao.getCompany(id);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tempCompany;
	}
	//-----------------------------------------------------------------------------------------------------

	public Collection<Company> getAllCompanies()
	{
		ArrayList<Company> tempArrayOfCompanies = new ArrayList<>();
		
		try 
		{
			tempArrayOfCompanies = (ArrayList<Company>) companydbdao.getAllCompanies();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tempArrayOfCompanies;
	}
	//-----------------------------------------------------------------------------------------------------
	public void createCustomer(Customer customer)
	{
		try 
		{
			customerdbdao.createCustomer(customer);
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//-----------------------------------------------------------------------------------------------------
	public void removeCustomer(Customer customer)
	{
		try {
			customerdbdao.removeCustomer(customer);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//-----------------------------------------------------------------------------------------------------

	public void updateCustomer(Customer customer)
	{
		try
		{
			customerdbdao.updateCustomer(customer);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//-----------------------------------------------------------------------------------------------------
	public Customer getCustomer(long id)
	{
		Customer tempCustomer = new Customer();
		try 
		{
			tempCustomer = customerdbdao.getCustomer(id);
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tempCustomer;
	}
	//-----------------------------------------------------------------------------------------------------

	public Collection<Customer> getAllCustomer()
	{
		ArrayList<Customer> tempArrayOfCustomers = new ArrayList<>();
				
		try 
		{
			tempArrayOfCustomers = (ArrayList<Customer>) customerdbdao.getAllCustomer();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tempArrayOfCustomers;
	}
	//-----------------------------------------------------------------------------------------------------

	@Override
	public CouponClientFacade login(String name, String password, String clientType) 
	{
		if (name.equals("admin") && password.equals("1234"))
		{
			return this;
		}

		return null;
	}
}
