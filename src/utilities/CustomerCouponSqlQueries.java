package utilities;

/**
 * a list of constants that holds all of the customer_coupon table  SQL  Queries
 * @author monder
 * @version 1.0
 */
public class CustomerCouponSqlQueries 
{
	//SELECT Queries
	public static String COUPON_ID_BY_CUST_ID = "SELECT COUPON_ID FROM customer_coupon WHERE CUST_ID = '%1s'";
	public static String SELECT_ALL_BY_COUPON_ID = "SELECT * FROM customer_coupon WHERE COUPON_ID = '%1s'";

	//SELECT Queries with in condition
	public static String SELECT_COUPON_CUSTOMER_BY_DATE = "SELECT * FROM coupon WHERE ID in (SELECT COUPON_ID FROM customer_coupon where CUST_ID= '%1s' ) AND END_DATE<'%1s' ";
	public static String SELECT_COUPON_CUSTOMER_BY_PRICE = "SELECT * FROM coupon WHERE ID in (SELECT COUPON_ID FROM customer_coupon where CUST_ID= '%1s' ) AND PRICE<'%1s' ";
	public static String SELECT_COUPON_CUSTOMER_BY_TYPE = "SELECT * FROM coupon WHERE ID in (SELECT COUPON_ID FROM customer_coupon where CUST_ID= '%1s' ) AND TYPE='%1s' ";

	//DELETE Queries
	public static String DELETE_COUPON_CUST_ID = "DELETE FROM customer_coupon WHERE COUPON_ID = '%1s'";
	public static String DELET_BY_CUST_ID = "DELETE FROM customer_coupon WHERE CUST_ID = '%1s'";

	//INSERT Queries
	public static String INSERT_COUPON_TO_CUSTOMER = " insert into customer_coupon (CUST_ID, COUPON_ID)" + " values (?, ?)";
}
