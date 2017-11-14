package utilities;

public class CustomerCouponSqlQueries 
{
	public static String DELETE_COUPON_CUST_ID = "DELETE FROM customer_coupon WHERE COUPON_ID = '%1s'";
	public static String DELET_BY_CUST_ID = "DELETE FROM customer_coupon WHERE CUST_ID = '%1s'";
	public static String COUPON_ID_BY_CUST_ID = "SELECT COUPON_ID FROM customer_coupon WHERE CUST_ID = '%1s'";


}
