package tests;

import facades.AdminFacade;
import javaBeans.Company;

public class removeCompaniesTest 
{
	public removeCompaniesTest(AdminFacade adminFacade)
	{
		//getting company from the database
		Company company = adminFacade.getCompany(1);

		//removing from the database
		adminFacade.removeCompany(company);

		//get all the companies 
		new getAllCompaniesTest(adminFacade);
	}
}
