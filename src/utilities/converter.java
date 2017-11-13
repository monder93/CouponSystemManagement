package utilities;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class converter 
{
	
	// this class have 2 static functions to convert between Date && String 
	
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
	
	//--------------------------------------------------------------------------------------------------------
	public static String dateToString(Date date)
	{
		String tempDate = simpleDateFormat.format(date);
		return tempDate;
	}
	
	//--------------------------------------------------------------------------------------------------------
	
	public static Date stringToDate(String date)
	{
		Date tempDate=null;
		try
		{
			tempDate = (Date) simpleDateFormat.parse(date);
		}
		catch (ParseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tempDate;
	}
}
