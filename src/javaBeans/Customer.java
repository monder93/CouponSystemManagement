package javaBeans;

import java.util.Collection;

/**
 * customer class - holds all the members and constructors of the companies.
 * @author monder
 * @version 1.0
 */
public class Customer
{
	
	private long id;
	private String custName;
	private String password;
	private Collection<Coupon> coupons;
	
	//-----------------------------------------------------------------------------------------
	public Customer()
	{
		
	}
	
	//-----------------------------------------------------------------------------------------
	/**
	 * the constructor of the customer class
	 * @param id the id of the customer in the database
	 * @param custName the name of the customer
	 * @param password the password of this customer - used for logging in
	 */
	//-----------------------------------------------------------------------------------------
	public Customer(long id, String custName, String password) 
	{
		super();
		this.id = id;
		this.custName = custName;
		this.password = password;
	}
	
	//-----------------------------------------------------------------------------------------
	/**
	 * the constructor of the customer class
	 * @param id the id of the customer in the database
	 * @param custName the name of the customer
	 * @param password the password of this customer - used for logging in
	 * @param coupons collection<coupon> of the customer
	 */
	public Customer(long id, String custName, String password, Collection<Coupon> coupons) 
	{
		this(id,custName,password);
		this.coupons = coupons;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * return the customer's id
	 * @return the customer's id
	 */
	public long getId() 
	{
		return id;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * sets the customer's id
	 * @param id the customer's id
	 */
	public void setId(long id) 
	{
		this.id = id;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * return the customer's name
	 * @return the customer's name
	 */
	public String getCustName() 
	{
		return custName;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * sets the customer's name
	 * @param custName the customer's name
	 */
	public void setCustName(String custName) 
	{
		this.custName = custName;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * return the customer's password
	 * @return the customer's password
	 */
	public String getPassword() 
	{
		return password;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * sets the customer's password
	 * @param password the customer's password, used for logging in
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * return the customer list of created coupons
	 * @return the customer's list of created coupons
	 */
	public Collection<Coupon> getCoupons() 
	{
		return coupons;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * sets the customer list of coupons
	 * @param coupons the customer list of created coupons
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
		return "Customer [id=" + id + ", custName=" + custName + ", password=" + password + ", coupons=" + coupons
				+ "]\n";
	}
	
}
