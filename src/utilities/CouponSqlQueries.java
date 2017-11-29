package utilities;

/**
 * a list of constants that holds all of the coupon table  SQL  Queries
 */
public class CouponSqlQueries
{
	//SELECT Queries
	public static String COUPON_BY_ID = "SELECT * FROM coupon WHERE ID = '%1s'";
	public static String SELECT_ALL_WHERE_COUPON_TITLE ="SELECT * FROM coupon WHERE TITLE = '%1s'";
	public static String ALL_COUPON = "SELECT * FROM coupon";
	public static String COUPON_BY_TYPE = "SELECT * FROM coupon WHERE TYPE = '%1s'";
	public static String ALL_COUPONS_BY_ID = "SELECT * FROM coupon WHERE id = '%1s'";
	public static String SELECT_COUPON_OUT_OF_DATE = "SELECT * FROM  coupon where END_DATE <= CURDATE()";

	//DELETE Queries
	public static String DELETE_COUPON_BY_ID = "DELETE FROM coupon where ID = '%1s'";
	
	//INSERT Queries
	public static String INSERT_COUPON = " insert into coupon (TITLE, START_DATE, END_DATE, AMOUNT, TYPE, MESSAGE, PRICE, IMAGE)" + " values (?, ?, ?, ?, ?, ?, ?, ?)";
	
	//UPDATE Queries
	public static String UPDATE_COUPON_WHERE_ID = "update coupon set end_date = ?, price = ? where id = ?";
	public static String UPDATE_AMOUNT_OF_COUPON = "update coupon set AMOUNT = ? where ID = ?";
}
