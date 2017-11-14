package facades;

import java.util.ArrayList;

import dao.CompanyDBDAO;
import dao.CustomerDBDAO;
import javaBeans.Coupon;

public class CustomerFacade implements CouponClientFacade
{

	@Override
	public CouponClientFacade login(String name, String password, String clientType)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	public CustomerFacade()
	{

	}
	
	public void purchaseCoupon(Coupon coupon)
	{
		
	}
	
	public ArrayList<Coupon> getAllPurchasedCoupons()
	{
		
	}
	
	public ArrayList<Coupon> getAllPurchasedCouponsByType(CouponType coupontype)
	{
	
	}
	
	
	
	
	
	

}
