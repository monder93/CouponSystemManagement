package facades;

import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

import dao.CompanyDBDAO;
import dao.CouponDBDAO;
import exceptions.DuplicateEntryException;
import exceptions.NullConnectionException;
import exceptions.UnAvailableCouponException;
import exceptions.WrongDataInputException;
import exceptionsHandlers.CompanyExceptionHandler;
import exceptionsHandlers.CouponExceptionHandler;
import javaBeans.ClientType;
import javaBeans.Coupon;
import javaBeans.CouponType;

/**
 * 
 * The CompanyFacade class is used by the company users of the CouponSystem.
 * It grants them access to all of the relevant methods for their uses.
 *
 */
public class CompanyFacade implements CouponClientFacade
{
	private CompanyDBDAO companydbdao;
	private CouponDBDAO coupondbdao;

	/**
	 * the CompanyFacade constructor.
	 * it initialize the companyDBDAO and the couponDBDAO
	 */
	public CompanyFacade()
	{	
		coupondbdao = new CouponDBDAO();
		companydbdao= new CompanyDBDAO();
	}
	//----------------------------------------------------------------------------------------
	/**
	 * Receives a coupon instance and register it in the database
	 * @param coupon a coupon instance
	 */
	public void createCoupon(Coupon coupon)
	{	
		try 
		{
			coupondbdao.createCoupon(coupon);
			companydbdao.addCouponToCompany(companydbdao.getCompanyId(), coupon.getId());
		}
		catch (ClassNotFoundException | SQLException | InterruptedException | DuplicateEntryException
				| NullConnectionException| ParseException e) 
		{
			CouponExceptionHandler.handle(e);
		}
	}
	//----------------------------------------------------------------------------------------
	/**
	 * Receives a coupon instance and removes its entries from the database
	 * @param coupon a coupon instance
	 */
	public void removeCoupon(Coupon coupon)
	{

		try 
		{
			coupondbdao.removeCoupon(coupon);
		}
		catch (ClassNotFoundException | SQLException | InterruptedException | DuplicateEntryException
				| NullConnectionException | UnAvailableCouponException e)
		{
			CouponExceptionHandler.handle(e);
		}

	}
	//----------------------------------------------------------------------------------------
	/**
	 * Receives a coupon instance and update its entries in the database
	 * @param coupon a coupon instance
	 */
	public void updateCoupon(Coupon coupon)
	{
		try 
		{
			coupondbdao.updateCoupon(coupon);
		}
		catch (ClassNotFoundException | InterruptedException | SQLException | ParseException
				| NullConnectionException | UnAvailableCouponException e) 
		{
			CouponExceptionHandler.handle(e);
		}
	}
	//----------------------------------------------------------------------------------------
	/**
	 * Receives a coupon' id and return an instance of that coupon from the database
	 * @param id a coupon's id
	 * @return an instance of the desired coupon from the database
	 */
	public Coupon getCoupon(long id)
	{
		Coupon tempCoupon = new Coupon();

		try 
		{
			tempCoupon = coupondbdao.getCoupon(id);
		}
		catch (SQLException | ClassNotFoundException | InterruptedException | ParseException | NullConnectionException | UnAvailableCouponException e) 
		{
			CouponExceptionHandler.handle(e);
			return null;
		}

		return tempCoupon;
	}
	//----------------------------------------------------------------------------------------
	/**
	 * getting all the coupons
	 * @return an ArrayList of all the company's coupons in the database
	 */
	public Collection<Coupon> getAllCoupon()
	{
		ArrayList<Coupon> ArrayOfCompanyCoupons = new ArrayList<>();

		try 
		{
			ArrayOfCompanyCoupons = (ArrayList<Coupon>) companydbdao.getCoupons();
		}
		catch (SQLException | ClassNotFoundException | InterruptedException | ParseException | NullConnectionException e) 
		{
			CouponExceptionHandler.handle(e);
		}
		return ArrayOfCompanyCoupons;
	}
	//----------------------------------------------------------------------------------------
	/**
	 * getting company coupons in the database by a given type
	 * @param couponType a coupon type
	 * @return an ArrayList of all the company's coupons in the database by a given type
	 */
	public Collection<Coupon> getCouponByType(CouponType couponType)
	{
		ArrayList<Coupon> ArrayOfCompanyCouponsByType = new ArrayList<>();

		try 
		{
			ArrayOfCompanyCouponsByType = (ArrayList<Coupon>) companydbdao.getCompanyCouponByType(couponType);
		}
		catch (ClassNotFoundException | InterruptedException | SQLException | NullConnectionException
				| ParseException e)
		{
			CouponExceptionHandler.handle(e);
		}

		return ArrayOfCompanyCouponsByType;
	}
	//----------------------------------------------------------------------------------------
	/**
	 * getting company coupons in the database by a given price
	 * @param price  price limit
	 * @return an ArrayList of all the company's coupons in the database by a given price
	 */
	public Collection<Coupon> getCouponByPrice(double price)
	{
		ArrayList<Coupon> ArrayOfCompanyCouponsByPrice = new ArrayList<>();

		try 
		{
			ArrayOfCompanyCouponsByPrice = (ArrayList<Coupon>) companydbdao.getCompanyCouponByPrice(price);
		} 
		catch (ClassNotFoundException | InterruptedException | SQLException | NullConnectionException
				| ParseException e) 
		{
			CouponExceptionHandler.handle(e);
		}

		return ArrayOfCompanyCouponsByPrice;
	}
	//----------------------------------------------------------------------------------------
	/**
	 * getting company coupons in the database by a given date
	 * @param date limit date
	 * @return an ArrayList of all the company's coupons in the database by a given date
	 */
	public Collection<Coupon> getCouponByDate(Date date)
	{
		ArrayList<Coupon> ArrayOfCompanyCouponsByDate = new ArrayList<>();

		try
		{
			ArrayOfCompanyCouponsByDate = (ArrayList<Coupon>) companydbdao.getCompanyCouponByDate(date);
		}
		catch (ClassNotFoundException | InterruptedException | SQLException | NullConnectionException
				| ParseException e) 
		{
			CouponExceptionHandler.handle(e);
		}
		return ArrayOfCompanyCouponsByDate;
	}
	//----------------------------------------------------------------------------------------
	/**
	 * checks the database for a company entry with the given name and the given password
	 * returns this if exist, else return false 
	 */
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType)
	{

		try 
		{
			if(companydbdao.login(name, password))
			{
				return this;
			}
		}
		catch (ClassNotFoundException | InterruptedException | SQLException | WrongDataInputException
				| NullConnectionException e)
		{
			CompanyExceptionHandler.handle(e);
		}

		return null;
	}
	//----------------------------------------------------------------------------------------
	/**
	 * sets the id of the current user in the coupon DBDAO
	 */
	public void setUserId()
	{
		this.companydbdao.setCompanyId(companydbdao.getCompanyId());
	}
}
