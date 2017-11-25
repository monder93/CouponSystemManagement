package threads;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import dao.CouponDBDAO;
import javaBeans.Coupon;
import utilities.Configurations_values;

public class DailyCouponExpirationTask implements Runnable
{

	private CouponDBDAO coupondbdao = new CouponDBDAO();
	private boolean exit = false;
	
	ArrayList<Coupon> AllCoupons = new ArrayList<>();

	@Override
	public void run() 
	{
		//getting the date of the day
		Date today = (Date) Calendar.getInstance().getTime();

		while(!exit)
		{
			try 
			{
				//getting all the coupons from the database
				AllCoupons.clear();
				AllCoupons = (ArrayList<Coupon>) coupondbdao.getAllCoupons();
			}
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//for each loop to check if the coupon is expired and removing it from the database
			for(Coupon c : AllCoupons)
			{
				if(c.getEndDate().before(today))
				{

					try
					{
						coupondbdao.removeCoupon(c);
					}
					catch (Exception e)
					{
						// TODO: handle exception
					}
				}
			}
			
			// sleep time of the thread .. for repeating 
			try
			{
				Thread.sleep(Configurations_values.sleepTime);
			}
			catch(InterruptedException e)
			{
				
			}
			
		}

	}
}
