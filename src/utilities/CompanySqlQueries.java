package utilities;

public class CompanySqlQueries 
{
	// select everything from company table where COMP_NAME = ? 
	public static String SELECT_ALL_WHERE_COMP_NAME = "SELECT * FROM company WHERE COMP_NAME = '%1s'";
	public static String INSERT_COMPANY = " insert into company (COMP_NAME, PASSWORD, EMAIL)" + " values (?, ?, ?)";
	public static String SELECT_ALL_WHERE_COMP_ID = "SELECT * FROM company WHERE ID ='%1s'";
	public static String DELETE_FROM_COMPANY_WHERE_ID = "DELETE FROM company WHERE ID = '%1s'";
	public static String UPDATE_COMPANY_BY_ID = "update company set PASSWORD = ?, EMAIL = ? where ID = ?";
	public static String SELECT_ID_FROM_COMPANY = "SELECT ID FROM company";
	public static String SELECT_ID_PASSWORD = "SELECT * FROM company WHERE COMP_NAME = '%1s and  PASSWORD = '%1s ";




	

}
