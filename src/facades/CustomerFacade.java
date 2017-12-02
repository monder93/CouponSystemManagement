package facades;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import dao.CustomerDBDAO;
import exceptions.*;
import exceptionsHandlers.*;
import javaBeans.ClientType;
import javaBeans.Coupon;
import javaBeans.CouponType;

/**
 * 
 * The CustomerFacade class is used by the customer users of the CouponSystem.
 * It grants them access to all of the relevant methods for their uses.
 *
 */
public class CustomerFacade implements CouponClientFacade
{
	private CustomerDBDAO customerdbdao;

	/**
	 * the constructor of the CustomerFacade
	 * it initialize the CustomerDBDAO 
	 */
	public CustomerFacade()
	{
		customerdbdao = new CustomerDBDAO();
	}
	//----------------------------------------------------------------------------------------
	/**
	 * Receives a coupon instance and updates it's purchase in the database
	 * @param coupon a coupon instance
	 */
	public void purchaseCoupon(Coupon coupon)
	{
		try 
		{
			customerdbdao.purchaseCoupon(coupon);
		}
		catch (ClassNotFoundException | InterruptedException | SQLException | NullConnectionException| UnAvailableCouponException | NullPointerException e) 
		{
			CouponExceptionHandler.handle(e);
		}
	}
	//----------------------------------------------------------------------------------------
	/**
	 * getting all the purchased coupons for the current customer
	 * @return an ArrayList of all the current customer's purchased coupons
	 * @throws  
	 */
	public Collection<Coupon> getAllPurchasedCoupons() 
	{

			ArrayList<Coupon> allCustomerCoupons = new ArrayList<>();

			try 
			{
				allCustomerCoupons = (ArrayList<Coupon>) customerdbdao.getCoupons();
			} 
			catch (NullPointerException| ClassNotFoundException | InterruptedException | NullConnectionException | SQLException e)
			{
				CustomerExceptionHandler.handle(e);
			}

			return allCustomerCoupons;

	}
	//----------------------------------------------------------------------------------------
	/**
	 * getting all the purchased coupons for the current customer by type
	 * @return an ArrayList of all the current customer's purchased coupons by type
	 */
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType coupontype) 
	{
		ArrayList<Coupon> allCustomerCoupons = new ArrayList<>();

		try 
		{
			allCustomerCoupons = (ArrayList<Coupon>) customerdbdao.getCouponsByType(coupontype);
		} 
		catch (ClassNotFoundException | InterruptedException | NullConnectionException | SQLException e)
		{
			CustomerExceptionHandler.handle(e);
		}

		return allCustomerCoupons;
	}
	//----------------------------------------------------------------------------------------
	/**
	 * getting all the purchased coupons for the current customer by price
	 * @return an ArrayList of all the current customer's purchased coupons by price
	 */
	public Collection<Coupon> getAllPurchasedCouponsByPrice(double price) 
	{
		ArrayList<Coupon> allCustomerCoupons = new ArrayList<>();

		try 
		{
			allCustomerCoupons = (ArrayList<Coupon>) customerdbdao.getCouponsByPrice(price);
		}
		catch (ClassNotFoundException | InterruptedException | NullConnectionException | SQLException e) 
		{
			CustomerExceptionHandler.handle(e);
		}

		return allCustomerCoupons;
	}

	//----------------------------------------------------------------------------------------
	/**
	 * checks the database for a customer entry with the given name and the given password
	 * returns this instance if exist , else return null
	 */
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType)
	{

		try
		{
			if(customerdbdao.login(name, password))
			{
				return this;
			}
		}
		catch (ClassNotFoundException | InterruptedException | SQLException | WrongDataInputException
				| NullConnectionException | NullPointerException e) 
		{
			GeneralExceptionHandler.handle(e);
		}

		return null;
	}
}
