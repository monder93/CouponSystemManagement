package tests;

import facades.CompanyFacade;
import javaBeans.ClientType;
import javaBeans.Coupon;
import javaBeans.CouponType;
import mainPackage.CouponSystem;
import utilities.converter;

public class createCouponsTest 
{
	public createCouponsTest()
	{
		CompanyFacade companyFacade;
		companyFacade = (CompanyFacade) CouponSystem.getInstance().login("microsoft","123",ClientType.COMPANY);
		//preparing the coupons
		Coupon coupon1 = new Coupon(1, "microsoftCoupon1", converter.stringToDate("18-01-01"), converter.stringToDate("19-01-01"), 1, CouponType.CAMPING, "coupon1", 10, "image1");
		Coupon coupon2 = new Coupon(2, "microsoftCoupon2", converter.stringToDate("18-02-02"), converter.stringToDate("19-02-02"), 2, CouponType.CAMPING, "coupon2", 20, "image2");
		Coupon coupon3 = new Coupon(3, "microsoftCoupon3", converter.stringToDate("18-03-03"), converter.stringToDate("19-03-03"), 3, CouponType.FOOD, "coupon3", 30, "image3");

		//creating the coupons
		System.out.println("------------------------------------------------------------first time creating coupons------------------------------------------------------------");
		companyFacade.createCoupon(coupon1);
		companyFacade.createCoupon(coupon2);
		companyFacade.createCoupon(coupon3);
		System.out.println("------------------------------------------------------------creating exist coupon------------------------------------------------------------");
		companyFacade.createCoupon(coupon3);
		
		//login to google company
		companyFacade = (CompanyFacade) CouponSystem.getInstance().login("google","456",ClientType.COMPANY);

		//preparing the coupons
		Coupon coupon4 = new Coupon(1, "googleCoupon1", converter.stringToDate("18-04-04"), converter.stringToDate("19-04-04"), 1, CouponType.CAMPING, "coupon4", 40, "image4");
		Coupon coupon5 = new Coupon(2, "googleCoupon2", converter.stringToDate("18-05-05"), converter.stringToDate("19-05-05"), 2, CouponType.CAMPING, "coupon5", 50, "image5");
		Coupon coupon6 = new Coupon(3, "googleCoupon3", converter.stringToDate("18-06-06"), converter.stringToDate("19-06-06"), 3, CouponType.FOOD, "coupon6", 60, "image6");

		//creating the coupons
		System.out.println("------------------------------------------------------------second time creating coupons------------------------------------------------------------");
		companyFacade.createCoupon(coupon4);
		companyFacade.createCoupon(coupon5);
		companyFacade.createCoupon(coupon6);
		System.out.println("------------------------------------------------------------creating exist coupon from microsoft company------------------------------------------------------------");
		companyFacade.createCoupon(coupon3);
		
	}
}
