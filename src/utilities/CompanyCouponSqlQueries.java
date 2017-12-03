package utilities;

/**
 * a list of constants that holds all of the company_coupon table  SQL  Queries
 * @author monder
 * @version 1.0
 */
public class CompanyCouponSqlQueries
{
	//SELECT Queries
	public static String COUPON_ID_BY_COMP_ID = "SELECT COUPON_ID FROM company_coupon WHERE COMP_ID = '%1s'";
	public static String SELECT_ALL_WHERE_COUPON_ID_COMP_ID = "SELECT * FROM company_coupon WHERE COUPON_ID = '%1s' and COMP_ID = '%1s' ";

	//SELECT Queries with in condition
	public static String SELECT_COUPON_COMPANY_BY_DATE = "SELECT * FROM coupon WHERE ID in (SELECT COUPON_ID FROM company_coupon where COMP_ID= '%1s' ) AND END_DATE<'%1s' ";
	public static String SELECT_COUPON_COMPANY_BY_PRICE = "SELECT * FROM coupon WHERE ID in (SELECT COUPON_ID FROM company_coupon where COMP_ID= '%1s' ) AND PRICE<='%1s' ";
	public static String SELECT_COUPON_COMPANY_BY_TYPE = "SELECT * FROM coupon WHERE ID in (SELECT COUPON_ID FROM company_coupon where COMP_ID= '%1s' ) AND TYPE='%1s' ";

	//DELETE Queries 
	public static String DELETE_COUPON_COMP_ID = "DELETE FROM company_coupon WHERE COMP_ID = '%1s'";
	public static String DELETE_COUPON_WHERE_ID = "DELETE FROM company_coupon WHERE COUPON_ID = '%1s'";

	//INSERT Queries
	public static String INSERT_COUPON_TO_COMPANY = " insert into company_coupon (COMP_ID, COUPON_ID)" + " values (?, ?)";
}
