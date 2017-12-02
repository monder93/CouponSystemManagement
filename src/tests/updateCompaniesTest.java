package tests;

import facades.AdminFacade;
import javaBeans.Company;

public class updateCompaniesTest 
{
	public updateCompaniesTest(AdminFacade adminFacade)
	{
		Company company1 = new Company(2, "google", "456", "goog@gmail.com");
		Company company2 = new Company(3, "intel", "123", "int@intel.com");
		
		adminFacade.updateCompany(company1);
		adminFacade.updateCompany(company2);
		new getAllCompaniesTest(adminFacade);
		company2 = new Company(3, "intel", "789", "int@intel.com");
		adminFacade.updateCompany(company2);


	}
}
