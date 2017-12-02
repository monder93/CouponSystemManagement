package javaBeans;

import java.util.Collection;

/**
 * company class - holds all the members and constructors of the companies.
 * @author monder
 * @version 1.0
 */
public class Company 
{

	private int id ;
	private String compName;
	private String password;
	private String email;
	private Collection<Coupon> coupons;

	//-----------------------------------------------------------------------------------------
	public Company()
	{

	}

	//-----------------------------------------------------------------------------------------
	/**
	 * 
	 * @param id unique identifier number for the company 
	 * @param compName company name 
	 * @param password password of the company
	 * @param email email of the company
	 */
	public Company(int id, String compName, String password, String email) 
	{
		super();
		this.id = id;
		this.compName = compName;
		this.password = password;
		this.email = email;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * 
	 * @param id unique identifier number for the company 
	 * @param compName company name 
	 * @param password password of the company
	 * @param email email of the company
	 * @param coupons  collect<coupon> of the company 
	 */
	public Company(int id, String compName, String password, String email, Collection<Coupon> coupons) 
	{
		this(id,compName,password,email);
		this.coupons = coupons;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 *  return the company's id
	 * @return the company's id
	 */
	public int getId() 
	{
		return id;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * sets the company's id
	 * @param id the company's id
	 */
	public void setId(int id) 
	{
		this.id = id;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * return the company's name
	 * @return the company's name
	 */
	public String getCompName() 
	{
		return compName;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * sets the company's name
	 * @param compName the company's name
	 */
	public void setCompName(String compName) 
	{
		this.compName = compName;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * return the company's password
	 * @return the company's password
	 */
	public String getPassword() 
	{
		return password;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * sets the company's password
	 * @param password the company's password
	 */
	public void setPassword(String password) 
	{
		this.password = password;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * return the company's email
	 * @return the company's email
	 */
	public String getEmail()
	{
		return email;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * sets the company's email
	 * @param email the company's email
	 */
	public void setEmail(String email) 
	{
		this.email = email;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * return the company's list of created coupons
	 * @return the company's list of created coupons
	 */
	public Collection<Coupon> getCoupons() 
	{
		return coupons;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * sets the company's list of coupons
	 * @param coupons the company's list of created coupons
	 */
	public void setCoupons(Collection<Coupon> coupons) 
	{
		this.coupons = coupons;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * a to string method for the company
	 */
	@Override
	public String toString() 
	{
		return "Company [id=" + id + ", compName=" + compName + ", password=" + password + ", email=" + email
				+ ", coupons=\n" + coupons + "]\n";
	}
}
