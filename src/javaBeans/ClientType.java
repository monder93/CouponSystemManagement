package javaBeans;

public enum ClientType 
{
	/**
	 * a customer - able to buy coupons
	 * a company - able to create and remove coupons
	 * an administrator - has max abilities, can create and remove customers/companies 
	 */
	CUSTOMER,COMPANY,ADMIN
}