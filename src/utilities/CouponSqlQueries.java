package utilities;

public class CouponSqlQueries
{
	public static String DELETE_COUPON_BY_ID = "DELETE FROM coupon where ID = ?";
	public static String COUPON_BY_ID = "SELECT * FROM coupon WHERE ID = '%1s'";


}
