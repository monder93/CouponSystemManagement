package exceptions;

/**
 * 
 * An exception that provides information on an attempt to login to the coupon system with wrong user name and/or password
 *
 */
public class WrongDataInputException extends Exception 
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new exception with the specified detail message.
	 * @param message message the detail message. The detail message is saved for later retrieval by the Throwable.getMessage() method.
	 */
	public WrongDataInputException(String message)
	{
		super(message);
	}
}
