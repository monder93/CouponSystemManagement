package javaBeans;

import java.util.Date;

/**
 * Coupon class - holds all the members and constructors of the coupons.
 * @author monder
 * @version 1.0
 */
public class Coupon 
{
	private int id;
	private String title;
	private Date startDate;
	private Date endDate;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String image;
	
	//-----------------------------------------------------------------------------------------

	public Coupon()
	{
		
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * 
	 * the constructor of the coupon
	 * @param id the id of the coupon in the database
	 * @param title title or name  of the coupon
	 * @param startDate the date that states the start of this coupon validation
	 * @param endDate the date that states the end of this coupon validation
	 * @param amount the number of available coupons of this coupon 
	 * @param coupontype the type of this coupon
	 * @param message a message from the creator of this coupon
	 * @param price the price of this coupon
	 * @param image an image to illustrate the coupos's use
	 */
	public Coupon(int id, String title, Date startDate, Date endDate, int amount, CouponType type, String message,
			double price, String image) 
	{
		super();
		this.id = id;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.type = type;
		this.message = message;
		this.price = price;
		this.image = image;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * return the coupons id
	 * @return the coupons id
	 */
	public int getId() 
	{
		return id;
	}
	
	//-----------------------------------------------------------------------------------------
    /**
     * sets the coupon's id
     * @param id the coupon's id
     */
	public void setId(int id) 
	{
		this.id = id;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * return the coupon's title
	 * @return the coupon's title
	 */
	public String getTitle() 
	{
		return title;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * set's the title of the coupon
	 * @param title the coupon's title or name
	 */
	public void setTitle(String title) 
	{
		this.title = title;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * return the coupon's start date 
	 * @return the coupon's start date
	 */
	public Date getStartDate() 
	{
		return startDate;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * set's the coupon's start date 
	 * @param date the coupon's start date
	 */
	public void setStartDate(Date startDate) 
	{
		this.startDate = startDate;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * return the coupon's end date
	 * @return the coupon's end date 
	 */
	public Date getEndDate() 
	{
		return endDate;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * sets the coupon's end date 
	 * @param date the coupon's end date
	 */
	public void setEndDate(Date endDate) 
	{
		this.endDate = endDate;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * return the available amount of the coupon
	 * @return the available amount of the coupon
	 */
	public int getAmount() 
	{
		return amount;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * sets the available amount of the coupon
	 * @param amount the current amount of available coupon
	 */
	public void setAmount(int amount) 
	{
		this.amount = amount;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * return the type of the coupon
	 * @return the type of the coupon
	 */
	public CouponType getType() 
	{
		return type;
	}
	
	//-----------------------------------------------------------------------------------------
	/**
	 * sets the type of the coupon
	 * @param coupontype the type of the coupon
	 */
	public void setType(CouponType type) 
	{
		this.type = type;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * return the message from the coupon's creator
	 * @return the message from the coupon's creator
	 */
	public String getMessage() 
	{
		return message;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * set's the message from the coupon's creator
	 * @param message a message from the coupon's creator
	 */
	public void setMessage(String message) 
	{
		this.message = message;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * return the price of the coupon
	 * @return the price of the coupon
	 */
	public double getPrice()
	{
		return price;
	}
	
	//-----------------------------------------------------------------------------------------
	/**
	 * sets the price of the coupon
	 * @param price the price of the coupon
	 */
	public void setPrice(double price) 
	{
		this.price = price;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * return the image of the coupon
	 * @return the image of the coupon
	 */
	public String getImage() 
	{
		return image;
	}
	
	//-----------------------------------------------------------------------------------------
	/**
	 * sets the image of the coupon
	 * @param image an image to illustrate the coupon's use 
	 */
	public void setImage(String image) 
	{
		this.image = image;
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * a to string method for the company
	 */
	@Override
	public String toString() 
	{
		return "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", amount=" + amount + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
				+ image + "] \n";
	}
	
}
