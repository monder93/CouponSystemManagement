package mainPackage;

import java.sql.SQLException;

import exceptionsHandlers.GeneralExceptionHandler;
import facades.*;
import javaBeans.ClientType;
import threads.DailyCouponExpirationTask;


/**
 * 
 * The CouponSystem class is a singleton class that is used for logging in to the CouponSystem.
 * It is in charge of returning the right facade according to the user type, and it holds the
 * method that shuts down the CouponSystem.
 *
 */
public class CouponSystem 
{
	private static CouponSystem INSTANCE=null;
	DailyCouponExpirationTask tempDailyCouponExpirationTask = new DailyCouponExpirationTask();
	Thread cleanSystemCouponsThread = new Thread(tempDailyCouponExpirationTask,"clean");

	//-----------------------------------------------------------------------------------------------
	/**
	 * getInstace makes sure that only one instance of CouponSystem can be generated
	 * @return instance of the coupon system
	 */
	public static synchronized CouponSystem getInstance()
	{
		if (INSTANCE==null)
		{
			INSTANCE = new CouponSystem();
		}
		return INSTANCE;
	}
	//-----------------------------------------------------------------------------------------------
	/**
	 *the constructor initialize the connectionPool and starts the DailyCouponExpirationTask
	 *and sets the daily thread as daemon so the system can exit while it is running. 
	 */
	private CouponSystem()
	{
		ConnectionPool.getInstance();
		cleanSystemCouponsThread.setDaemon(true);
		cleanSystemCouponsThread.start();	
	}
	//-----------------------------------------------------------------------------------------------

	/**
	 * a factory pattern that returns the correct facade according to the login input
	 * 
	 * 
	 * @param name the name of the client
	 * @param password the password of the client
	 * @param clientType the type of client
	 * @return a facade according to the login input if true, if false returns null
	 */
	public CouponClientFacade login(String name,String password,ClientType clientType)
	{
		CouponClientFacade login = null;
		switch(clientType)
		{
		case CUSTOMER :
			CustomerFacade customerfacade = new CustomerFacade();
			login = customerfacade.login(name, password, clientType);
			if (login!=null)
			{
				System.out.println("welcome \nname : " + name + "\ntype : customer");
				System.out.println("-------------");
				return customerfacade;
			}
			break;

		case COMPANY : 
			CompanyFacade companyfacade = new CompanyFacade();
			login = companyfacade.login(name, password, clientType);
			if (login!=null)
			{
				System.out.println("welcome \nname : " + name + "\ntype : company");
				System.out.println("-------------");
				companyfacade.setUserId();
				return companyfacade;
			}
			break;

		case ADMIN : 

			AdminFacade adminfacade = new AdminFacade();
			login = adminfacade.login(name, password, clientType);
			if (login!=null)
			{
				System.out.println("welcome admin");
				System.out.println("-------------");
				return adminfacade;
			}
		}

		return null;
	}

	/**
	 * this method is called when the system is shutting down.
	 * <br/>
	 * it stops the daily task from running and closing all of the connections
	 * 
	 */
	public void shutDown()
	{
		tempDailyCouponExpirationTask.stopTask();
		ConnectionPool.getInstance().shuttingDown();
		try
		{
			ConnectionPool.getInstance().closeAllConnections();
		}
		catch (SQLException e)
		{
			GeneralExceptionHandler.handle(e);
		}
		ConnectionPool.getInstance().shuttingDown();
	}
}
