package facades;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import dao.CompanyDBDAO;
import dao.CouponDBDAO;
import javaBeans.Coupon;
import javaBeans.CouponType;

public class CompanyFacade implements CouponClientFacade
{
	private CompanyDBDAO companydbdao;
	private CouponDBDAO coupondbdao;
	
	public CompanyFacade()
	{	
		coupondbdao = new CouponDBDAO();
		companydbdao= new CompanyDBDAO();
	}
	//----------------------------------------------------------------------------------------
	public void createCoupon(Coupon coupon)
	{	
		try
		{
			coupondbdao.createCoupon(coupon);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//----------------------------------------------------------------------------------------
	public void removeCoupon(Coupon coupon)
	{
		try 
		{
			coupondbdao.removeCoupon(coupon);
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//----------------------------------------------------------------------------------------
	public void updateCoupon(Coupon coupon)
	{
		try 
		{
			coupondbdao.updateCoupon(coupon);
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//----------------------------------------------------------------------------------------
	public Coupon getCoupon(long id)
	{
		Coupon tempCoupon = new Coupon();
		
		try 
		{
			tempCoupon = coupondbdao.getCoupon(id);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tempCoupon;
	}
	//----------------------------------------------------------------------------------------
	public Collection<Coupon> getAllCoupon()
	{
		ArrayList<Coupon> ArrayOfCompanyCoupons = new ArrayList<>();
		
		try 
		{
			ArrayOfCompanyCoupons = (ArrayList<Coupon>) companydbdao.getCoupons();
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ArrayOfCompanyCoupons;
	}
	//----------------------------------------------------------------------------------------
	public Collection<Coupon> getCouponByType(CouponType couponType)
	{
		ArrayList<Coupon> ArrayOfCompanyCouponsByType = new ArrayList<>();
		
		try
		{
			ArrayOfCompanyCouponsByType = (ArrayList<Coupon>) companydbdao.getCompanyCouponByType(couponType);
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ArrayOfCompanyCouponsByType;
	}
	//----------------------------------------------------------------------------------------
	public Collection<Coupon> getCouponByPrice(double price)
	{
		ArrayList<Coupon> ArrayOfCompanyCouponsByPrice = new ArrayList<>();
		
		try
		{
			ArrayOfCompanyCouponsByPrice = (ArrayList<Coupon>) companydbdao.getCompanyCouponByPrice(price);
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ArrayOfCompanyCouponsByPrice;
	}
	//----------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------
		public Collection<Coupon> getCouponByDate(Date date)
		{
			ArrayList<Coupon> ArrayOfCompanyCouponsByDate = new ArrayList<>();
			
			try
			{
				ArrayOfCompanyCouponsByDate = (ArrayList<Coupon>) companydbdao.getCompanyCouponByDate(date);
			}
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ArrayOfCompanyCouponsByDate;
		}
		//----------------------------------------------------------------------------------------
	@Override
	public boolean login(String name, String password, String clientType)
	{
		// TODO Auto-generated method stub
		return true;
	}
}
