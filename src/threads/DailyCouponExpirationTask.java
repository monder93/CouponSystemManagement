package threads;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import dao.CouponDBDAO;
import javaBeans.Coupon;

public class DailyCouponExpirationTask implements Runnable
{

	private CouponDBDAO coupondbdao = new CouponDBDAO();
	private boolean quit = false;


	@Override
	public void run() 
	{
		//getting the date of the day

		Date today = (Date) Calendar.getInstance().getTime();

		while(!quit)
		{
			//getting all the coupons from the database
			ArrayList<Coupon> AllCoupons = new ArrayList<>();

			try 
			{
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
				Thread.sleep(24*60*60*1000);
			}
			catch(InterruptedException e)
			{
				
			}
			
		}

	}
}
