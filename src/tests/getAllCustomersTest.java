package tests;

import java.util.Collection;

import facades.AdminFacade;
import javaBeans.Customer;

public class getAllCustomersTest 
{
	public getAllCustomersTest(AdminFacade adminFacade)
	{
		Collection<Customer> Array_of_customers = adminFacade.getAllCustomer();
		
		for (Customer c : Array_of_customers)
		{
			System.out.println(c);
		}
	}
}
