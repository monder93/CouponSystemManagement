package tests;

import facades.AdminFacade;
import facades.CompanyFacade;
import javaBeans.Coupon;
import utilities.converter;

public class updateCouponsTest 
{
	public updateCouponsTest(CompanyFacade companyFacade,AdminFacade adminFacade)
	{
		//getting coupon from the database
		Coupon coupon = companyFacade.getCoupon(5);
		
		//removing from the database
		coupon.setEndDate(converter.stringToDate("17-02-01"));
		coupon.setPrice(11);
		companyFacade.updateCoupon(coupon);
		
		//get all the companies 
		new getAllCompaniesTest(adminFacade);
		System.out.println("------------------------------");
		new getAllCustomersTest(adminFacade);
						
	}
}
