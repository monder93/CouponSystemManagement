package facades;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import dao.CouponDBDAO;
import dao.CustomerDBDAO;
import javaBeans.Coupon;
import javaBeans.CouponType;

public class CustomerFacade implements CouponClientFacade
{
	private CouponDBDAO coupondbdao;
	private CustomerDBDAO customerdbdao;
	
	public CustomerFacade()
	{
		coupondbdao = new CouponDBDAO();
		customerdbdao = new CustomerDBDAO();
	}
	//----------------------------------------------------------------------------------------

	public void purchaseCoupon(Coupon coupon)
	{
		//coupondbdao.createCoupon(coupon);
	}
	//----------------------------------------------------------------------------------------

	public Collection<Coupon> getAllPurchasedCoupons()
	{
		ArrayList<Coupon> allCustomerCoupons = new ArrayList<>();
		
		try 
		{
			allCustomerCoupons = (ArrayList<Coupon>) customerdbdao.getCoupons();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allCustomerCoupons;
	}
	//----------------------------------------------------------------------------------------

	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType coupontype)
	{
		ArrayList<Coupon> allCustomerCoupons = new ArrayList<>();
		
		try 
		{
			allCustomerCoupons = (ArrayList<Coupon>) customerdbdao.getCouponsByType(coupontype);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allCustomerCoupons;
	}
	//----------------------------------------------------------------------------------------

	public Collection<Coupon> getAllPurchasedCouponsByPrice(double price)
	{
		ArrayList<Coupon> allCustomerCoupons = new ArrayList<>();
		
		try 
		{
			allCustomerCoupons = (ArrayList<Coupon>) customerdbdao.getCouponsByPrice(price);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allCustomerCoupons;
	}

	//----------------------------------------------------------------------------------------

	@Override
	public boolean login(String name, String password, String clientType)
	{
		// TODO Auto-generated method stub
		return true;
	}
	
	
	

}
