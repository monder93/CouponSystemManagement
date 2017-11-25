package exceptions;

/**
 * 
 * An exception that provides information on a wrong use in the Coupon method in the CustomerDBDAO
 *
 */
public class DuplicateCouponTypeException extends Exception
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new exception with the specified detail message.
	 * @param message the detail message. The detail message is saved for later retrieval by the Throwable.getMessage() method.
	 */
	public DuplicateCouponTypeException(String message)
	{
		super(message);
	}
}
