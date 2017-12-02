package tests;

import java.util.Collection;

import facades.AdminFacade;
import javaBeans.*;

public class getAllCompaniesTest
{
	public getAllCompaniesTest(AdminFacade adminFacade)
	{
		Collection<Company> Array_of_companies = adminFacade.getAllCompanies();
		
		for (Company c : Array_of_companies)
		{
			System.out.println(c);
		}
	}
}
