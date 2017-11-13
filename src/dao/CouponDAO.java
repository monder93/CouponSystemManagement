package dao;

import java.sql.SQLException;
import java.util.Collection;
import javaBeans.Coupon;
import javaBeans.CouponType;

public interface CouponDAO 
{
	
	public void createCoupon(Coupon coupon) throws SQLException;

	public void removeCoupon(Coupon coupon) throws SQLException;

	public void updateCoupon(Coupon coupon) throws SQLException;

	public Coupon getCoupon(long id) throws SQLException;

	public Collection<Coupon> getAllCoupons() throws SQLException;

	public Collection<Coupon> getCouponByType(CouponType couponType) throws SQLException;
	
	public boolean isCouponExist(Coupon coupon) throws SQLException , Exception;
	
	public void insertCouponToDatabase(Coupon coupon) throws SQLException , Exception;
	

}
