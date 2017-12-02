package tests;

import facades.CompanyFacade;
import facades.CustomerFacade;
import javaBeans.ClientType;
import javaBeans.Coupon;
import mainPackage.CouponSystem;

public class purchaseCouponsTest 
{
	public purchaseCouponsTest()
	{
		//login to microsoft company 
		CustomerFacade customerFacade;
		CompanyFacade companyFacade;
		companyFacade = (CompanyFacade) CouponSystem.getInstance().login("microsoft","123",ClientType.COMPANY);
		
		//creating coupons
		Coupon coupon1 = companyFacade.getCoupon(1);
		Coupon coupon2 = companyFacade.getCoupon(2);
		Coupon coupon3 = companyFacade.getCoupon(3);
		Coupon coupon4 = companyFacade.getCoupon(4);

		//log in to monder customer
		customerFacade = (CustomerFacade) CouponSystem.getInstance().login("monder","123",ClientType.CUSTOMER);
		customerFacade.purchaseCoupon(coupon1);
		customerFacade.purchaseCoupon(coupon2);
		customerFacade.purchaseCoupon(coupon3);
		customerFacade.purchaseCoupon(coupon4);

		//log in to tariq customer
		customerFacade = (CustomerFacade) CouponSystem.getInstance().login("tariq","456",ClientType.CUSTOMER);
		coupon2 = companyFacade.getCoupon(2);
		customerFacade.purchaseCoupon(coupon2);
		
		//log in to majd customer
		customerFacade = (CustomerFacade) CouponSystem.getInstance().login("majd","789",ClientType.CUSTOMER);
		coupon1 = companyFacade.getCoupon(1);
		coupon3 = companyFacade.getCoupon(3);
		customerFacade.purchaseCoupon(coupon3);
		customerFacade.purchaseCoupon(coupon1);

	}

}
