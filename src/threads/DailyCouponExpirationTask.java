package threads;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import dao.CouponDBDAO;
import exceptions.NullConnectionException;
import exceptionsHandlers.CouponExceptionHandler;
import exceptionsHandlers.GeneralExceptionHandler;
import javaBeans.Coupon;
import utilities.Configurations_values;

/**
 *  a thread that represent a daily task for removing out of date coupons 
 * @author monder
 * @version 1.0
 */
public class DailyCouponExpirationTask implements Runnable
{

	private CouponDBDAO coupondbdao = new CouponDBDAO();
	private boolean exit = false;

	ArrayList<Coupon> AllCoupons = new ArrayList<>();

	/**
	 * retrieves from the database all the out of date coupons and then remove them 
	 * repeating time is 1 day  
	 */
	@Override
	public void run() 
	{

		while(!exit)
		{
			try 
			{
				//getting all the coupons from the database
				AllCoupons.clear();
				AllCoupons = (ArrayList<Coupon>) coupondbdao.getCouponOutOfDate();
			}
			catch (SQLException | ClassNotFoundException | InterruptedException | ParseException | NullConnectionException e) 
			{
				CouponExceptionHandler.handle(e);
			}

			//for each loop to check if the coupon is expired and removing it from the database
			for(Coupon c : AllCoupons)
			{
				try 
				{
					coupondbdao.removeCoupon(c);
				}
				catch (Exception e) 
				{
					CouponExceptionHandler.handle(e);
				}
			}

			// sleep time of the thread .. for repeating 
			try
			{
				Thread.sleep(Configurations_values.sleepTime);
			}
			catch(InterruptedException e)
			{
				GeneralExceptionHandler.handle(e);
			}
		}
	}

	/**
	 * function to stop the daily task from working 
	 * stopping the thread
	 */
	public void stopTask()
	{
		exit = true;
	}
}
