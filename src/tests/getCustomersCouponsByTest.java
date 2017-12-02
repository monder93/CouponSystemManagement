package tests;

import java.util.Collection;
import facades.CustomerFacade;
import javaBeans.Coupon;
import javaBeans.CouponType;

public class getCustomersCouponsByTest 
{
	public getCustomersCouponsByTest(CustomerFacade customerFacade ,int price , CouponType couponType)
	{

		Collection<Coupon> Array_of_coupons;
		System.out.println("----------------------------- customer coupons by Price -----------------------------");
		Array_of_coupons = customerFacade.getAllPurchasedCouponsByPrice(price);
		for(Coupon c : Array_of_coupons)
		{
			System.out.println(c);
		}
		
		System.out.println("----------------------------- customer coupons by Type -----------------------------");
		Array_of_coupons = customerFacade.getAllPurchasedCouponsByType(couponType);
		for(Coupon c : Array_of_coupons)
		{
			System.out.println(c);
		}

	
	}
}
