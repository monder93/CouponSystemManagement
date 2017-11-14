package facades;

import java.util.ArrayList;
import java.util.Collection;

import dao.CompanyDBDAO;
import dao.CouponDBDAO;
import javaBeans.Coupon;

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
		
	}
	//----------------------------------------------------------------------------------------
	public void removeCoupon(Coupon coupon)
	{
		
	}
	//----------------------------------------------------------------------------------------
	public void updateCoupon(Coupon coupon)
	{
		
	}
	//----------------------------------------------------------------------------------------
	public Coupon getCoupon(long id)
	{
		
	}
	//----------------------------------------------------------------------------------------
	public Collection<Coupon> getAllCoupon()
	{
		
	}
	//----------------------------------------------------------------------------------------
	public Collection<Coupon> getCouponByType(CouponType couponType)
	{
		
	}
	//----------------------------------------------------------------------------------------

	@Override
	public boolean login(String name, String password, String clientType)
	{
		// TODO Auto-generated method stub
		return true;
	}
}
