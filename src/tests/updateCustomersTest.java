package tests;

import facades.AdminFacade;
import javaBeans.Customer;

public class updateCustomersTest 
{
	public updateCustomersTest(AdminFacade adminFacade)
	{
		Customer customer1 = adminFacade.getCustomer(2);
		customer1.setPassword("111");
		Customer customer2 = adminFacade.getCustomer(3); 
		customer2.setPassword("222");
		
		adminFacade.updateCustomer(customer1);
		adminFacade.updateCustomer(customer2);
		
		new getAllCustomersTest(adminFacade);
		
		customer1.setPassword("456");
		customer2.setPassword("789");
	}
}
