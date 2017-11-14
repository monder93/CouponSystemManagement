package utilities;

public class CompanyCouponSqlQueries
{
	public static String COUPON_ID_BY_COMP_ID = "SELECT COUPON_ID FROM company_coupon WHERE COMP_ID = '%1s'";
	public static String DELETE_COUPON_COMP_ID = "DELETE FROM company_coupon WHERE COMP_ID = '%1s'";


}
