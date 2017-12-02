package tests;

import facades.AdminFacade;
import facades.CompanyFacade;
import javaBeans.Coupon;

public class removeCouponsTest 
{
	public removeCouponsTest(CompanyFacade companyFacade,AdminFacade adminFacade)
	{
		//getting coupon from the database
		Coupon coupon = companyFacade.getCoupon(4);
		
		//removing from the database
		companyFacade.removeCoupon(coupon);
		
		//get all the companies 
		new getAllCompaniesTest(adminFacade);
		System.out.println("------------------------------");
		new getAllCustomersTest(adminFacade);
						
	}
}
