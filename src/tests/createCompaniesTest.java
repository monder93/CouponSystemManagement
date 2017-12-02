package tests;

import facades.AdminFacade;
import javaBeans.*;

public class createCompaniesTest 
{
	public createCompaniesTest(AdminFacade adminFacade)
	{
		
		Company company1 = new Company(1,"microsoft","123","microsoft@hotmail.com");
		Company company2 = new Company(2,"google","456","google@gmail.com");
		Company company3 = new Company(3,"intel","789","intel@intel.com");
		
		System.out.println("------------------------------------------------------------first time creating companies------------------------------------------------------------");
		adminFacade.createCompany(company1);
		adminFacade.createCompany(company2);
		adminFacade.createCompany(company3);
		
		System.out.println("------------------------------------------------------------creating exist company------------------------------------------------------------");
		adminFacade.createCompany(company1);
			}
}
