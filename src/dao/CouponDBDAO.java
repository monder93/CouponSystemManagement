package dao;

import java.sql.SQLException;
import java.util.Collection;
import javaBeans.Coupon;
import javaBeans.CouponType;
import mainPackage.ConnectionPool;

public class CouponDBDAO implements CouponDAO
{
	private ConnectionPool pool;
	
	
	@Override
	public void createCoupon(Coupon coupon) throws SQLException 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeCoupon(Coupon coupon) throws SQLException 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCoupon(Coupon coupon) throws SQLException 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Coupon getCoupon(long id) throws SQLException 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Coupon> getAllCoupons() throws SQLException 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Coupon> getCouponByType(CouponType couponType) throws SQLException 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCouponExist(Coupon coupon) throws SQLException, Exception 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void insertCouponToDatabase(Coupon coupon) throws SQLException, Exception 
	{
		// TODO Auto-generated method stub
		
	}
}
