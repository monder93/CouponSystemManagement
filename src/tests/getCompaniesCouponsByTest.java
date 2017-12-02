package tests;

import java.util.Collection;
import java.util.Date;

import facades.CompanyFacade;
import javaBeans.*;

public class getCompaniesCouponsByTest
{
	public getCompaniesCouponsByTest(CompanyFacade companyFacade ,Date date ,int price , CouponType couponType)
	{
		Collection<Coupon> Array_of_coupons;
		System.out.println("----------------------------- company coupons by Date -----------------------------");
		Array_of_coupons = companyFacade.getCouponByDate(date);
		for(Coupon c : Array_of_coupons)
		{
			System.out.println(c);
		}
		
		
		System.out.println("----------------------------- company coupons by Price -----------------------------");
		Array_of_coupons = companyFacade.getCouponByPrice(price);
		for(Coupon c : Array_of_coupons)
		{
			System.out.println(c);
		}
		
		System.out.println("----------------------------- company coupons by Type -----------------------------");
		Array_of_coupons = companyFacade.getCouponByType(couponType);
		for(Coupon c : Array_of_coupons)
		{
			System.out.println(c);
		}

	}
}
