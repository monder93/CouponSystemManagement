package dao;

import java.sql.SQLException;
import java.util.Collection;
import javaBeans.Company;
import javaBeans.Coupon;

public interface CompanyDAO 
{

	public void createCompany(Company company) throws SQLException, Exception;

	public void removeCompany(Company company) throws SQLException;

	public void updateCompany(Company company) throws SQLException;

	public Company getCompany(long id) throws SQLException;

	public Collection<Company> getAllCompanies() throws SQLException;

	public boolean login(String compName, String password) throws SQLException, Exception;
	
	public boolean isCompanyNameExist(Company company) throws SQLException , Exception;
	
	public boolean isCompanyIdExist(Company company) throws SQLException , Exception;
	
	public void insertCompanyToDatabase(Company company) throws SQLException , Exception;

	public Collection<Coupon> getCouponsByCompanyId(long id) throws SQLException;
	
	public Collection<Coupon> getCoupons(Company company) throws SQLException;

	
}
