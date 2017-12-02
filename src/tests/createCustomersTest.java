package tests;

import facades.AdminFacade;
import javaBeans.Customer;

public class createCustomersTest 
{
	public createCustomersTest(AdminFacade adminFacade)
	{		
		Customer customer1 = new Customer(1,"Monder","123");
		Customer customer2 = new Customer(2,"Tariq","456");
		Customer customer3 = new Customer(3,"Majd","789");
		
		System.out.println("------------------------------------------------------------first time creating customers------------------------------------------------------------");
		adminFacade.createCustomer(customer1);
		adminFacade.createCustomer(customer2);
		adminFacade.createCustomer(customer3);
		
		System.out.println("------------------------------------------------------------creating exist customer------------------------------------------------------------");
		adminFacade.createCustomer(customer1);
		
		System.out.println("------------------------------------------------------------finish creating customers------------------------------------------------------------");
	}

}
