package dao;

import java.sql.SQLException;
import java.util.Collection;
import javaBeans.Coupon;
import javaBeans.CouponType;

public interface CouponDAO 
{
	//------------------------------------------interface base functions----------------------------------------- 
	public void createCoupon(Coupon coupon) throws Exception;

	public void removeCoupon(Coupon coupon) throws SQLException, Exception;

	public void updateCoupon(Coupon coupon) throws SQLException, Exception;

	public Coupon getCoupon(long id) throws SQLException;

	public Collection<Coupon> getAllCoupons() throws SQLException;

	public Collection<Coupon> getCouponByType(CouponType couponType) throws SQLException;
	
	//------------------------------------------interface plus functions----------------------------------------- 
	
	public boolean isCouponExist(Coupon coupon) throws SQLException , Exception;
	
	public void insertCouponToDatabase(Coupon coupon) throws SQLException , Exception;
}