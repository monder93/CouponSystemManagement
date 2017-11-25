package utilities;

public class CompanyCouponSqlQueries
{
	public static String COUPON_ID_BY_COMP_ID = "SELECT COUPON_ID FROM company_coupon WHERE COMP_ID = '%1s'";
	public static String SELECT_COUPON_COMPANY_BY_DATE = "SELECT * FROM coupon WHERE ID in (SELECT COUPON_ID FROM company_coupon where COMP_ID= '%1s' ) AND END_DATE<'%1s' ";
	public static String SELECT_COUPON_COMPANY_BY_PRICE = "SELECT * FROM coupon WHERE ID in (SELECT COUPON_ID FROM company_coupon where COMP_ID= '%1s' ) AND PRICE<'%1s' ";
	public static String SELECT_COUPON_COMPANY_BY_TYPE = "SELECT * FROM coupon WHERE ID in (SELECT COUPON_ID FROM company_coupon where COMP_ID= '%1s' ) AND TYPE='%1s' ";

	public static String DELETE_COUPON_COMP_ID = "DELETE FROM company_coupon WHERE COMP_ID = '%1s'";
	public static String DELETE_COUPON_WHERE_ID = "DELETE FROM company_coupon WHERE COUPON_ID = '%1s'";

	public static String INSERT_COUPON_TO_COMPANY = " insert into company_coupon (COMP_ID, COUPON_ID)" + " values (?, ?)";

}
