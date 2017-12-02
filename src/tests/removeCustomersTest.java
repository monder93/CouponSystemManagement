package tests;

import facades.AdminFacade;
import javaBeans.Customer;

public class removeCustomersTest 
{
	public removeCustomersTest(AdminFacade adminFacade)
	{
		//getting customer from the database
		Customer customer = adminFacade.getCustomer(3);
		
		//removing from the database
		adminFacade.removeCustomer(customer);
		
		//get all the customers 
		new getAllCustomersTest(adminFacade);
		
		
	}
}
