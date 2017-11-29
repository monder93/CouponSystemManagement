package utilities;

/**
 * a list of constants that holds all of the company table  SQL  Queries
 * @author monder
 * @version 1.0
 */
public class CompanySqlQueries 
{
	//SELECT Queries
	public static String SELECT_ALL_WHERE_COMP_NAME = "SELECT * FROM company WHERE COMP_NAME = '%1s'";
	public static String SELECT_ALL_WHERE_COMP_ID = "SELECT * FROM company WHERE ID ='%1s'";
	public static String SELECT_ID_FROM_COMPANY = "SELECT ID FROM company";
	public static String SELECT_ID_PASSWORD = "SELECT * FROM company WHERE COMP_NAME = '%1s' and  PASSWORD = '%1s' ";

	//INSERT Queries
	public static String INSERT_COMPANY = "INSERT INTO company (COMP_NAME, PASSWORD, EMAIL) VALUES (?, ?, ?)";

	//DELETE Queries
	public static String DELETE_FROM_COMPANY_WHERE_ID = "DELETE FROM company WHERE ID = '%1s'";
	
	//UPDATE Queries
	public static String UPDATE_COMPANY_BY_ID = "update company set PASSWORD = ?, EMAIL = ? where ID = ?";
}
