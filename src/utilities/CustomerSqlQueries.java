package utilities;

/**
 * a list of constants that holds all of the customer table  SQL  Queries
 * @author monder
 * @version 1.0
 */
public class CustomerSqlQueries
{
	//SELECT Queries
	public static String SELECT_ALL_WHERE_CUST_NAME = "SELECT * FROM customer WHERE CUST_NAME = '%1s'";
	public static String SELECT_ALL_WHERE_ID = "SELECT * FROM customer WHERE ID = '%1s'";
	public static String SELECT_ID_FROM_CUSTOMER = "SELECT ID FROM customer";
	public static String SELECT_ID_PASSWORD = "SELECT * FROM customer WHERE CUST_NAME ='%1s' and  PASSWORD ='%1s' ";

	//DELETE Queries
	public static String DELET_BY_CUST_NAME = "DELETE FROM customer WHERE CUST_NAME = '%1s'";

	//INSERT Queries
	public static String INSERT_CUSTOMER = " insert into customer (CUST_NAME, PASSWORD)" + " values (?, ?)";

	//UPDATE Queries
	public static String UPDATE_CUSTOMER = "update customer set PASSWORD = ? WHERE ID = ?";

}
