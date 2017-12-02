package facades;

import java.sql.SQLException;

import exceptions.NullConnectionException;
import exceptions.WrongDataInputException;
import javaBeans.ClientType;

public interface CouponClientFacade 
{
	public CouponClientFacade login(String name , String password , ClientType clientType) throws ClassNotFoundException , InterruptedException ,SQLException ,WrongDataInputException
	, NullConnectionException , NullPointerException;
}
