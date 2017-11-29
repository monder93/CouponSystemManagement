package mainPackage;

import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;

import facades.*;
import javaBeans.*;
import utilities.converter;
/**
 * 
 * The main class the simulates a UI for the project.
 *
 */
public class tester {

	public static void main(String[] args) throws ParseException, ClassNotFoundException, SQLException, InterruptedException 
	{
		
		// clearing the database
		//TestFunctions.clearDateBase();

		
		AdminFacade adminfacade;
		CompanyFacade companyfacade;
		CustomerFacade customerfacade;

		adminfacade = (AdminFacade) CouponSystem.getInstance().login("admin", "1234", ClientType.ADMIN);
		//test 1
/*		
		Company comp1 = new Company(0,"bezeq","123","www.");
		Company comp2 = new Company(1,"intel","456","www.");
		Company comp3 = new Company(2,"karnaf","789","www.");
		Company comp4 = new Company(3,"teva","741","www.");
		adminfacade.createCompany(comp1);
		adminfacade.createCompany(comp2);
		adminfacade.createCompany(comp3);
		adminfacade.createCompany(comp4);
*/
		// test 2
/*		
		Customer cust1 = new Customer(0,"jhon","963");
		Customer cust2 = new Customer(0,"nick","852");
		Customer cust3 = new Customer(0,"shelly","753");
		Customer cust4 = new Customer(0,"maya","951");
		adminfacade.createCustomer(cust1);
		adminfacade.createCustomer(cust2);
		adminfacade.createCustomer(cust3);
		adminfacade.createCustomer(cust4);
*/		

		//test 3
/*
		System.out.println(adminfacade.getAllCompanies());
		Company comp1 = new Company(1,"bezeq","321","www.aaa");
		adminfacade.updateCompany(comp1);
		System.out.println(adminfacade.getCompany(1));
*/		

		//test4
/*		
		System.out.println(adminfacade.getAllCustomer());
		Customer cust1 = new Customer(1,"jhon","369");
		adminfacade.updateCustomer(cust1);
		System.out.println(adminfacade.getCustomer(1));
*/		

		//test5
		companyfacade = (CompanyFacade) CouponSystem.getInstance().login("bezeq", "123", ClientType.COMPANY);
		companyfacade = (CompanyFacade) CouponSystem.getInstance().login("bezeq", "321", ClientType.COMPANY);
		
/*	
		Coupon coup1 = new Coupon(0, "aaa", converter.stringToDate("18-01-01"), converter.stringToDate("18-01-29"), 5,CouponType.CAMPING, "hhh", 25.7,"ttt");
		Coupon coup2 = new Coupon(0, "bbb", converter.stringToDate("18-12-01"), converter.stringToDate("18-02-20"), 5,CouponType.FOOD, "hhh", 30.6,"lll");
		Coupon coup3 = new Coupon(0, "mmm", converter.stringToDate("18-01-12"), converter.stringToDate("18-03-18"), 5,CouponType.FOOD, "hhh", 30.6,"nnn");
		companyfacade.createCoupon(coup1);
		companyfacade.createCoupon(coup2);
		companyfacade.createCoupon(coup3);
*/
		
	//	System.out.println(companyfacade.getCouponByType(CouponType.FOOD));
	//	System.out.println(companyfacade.getCouponByPrice(40));
		
		
//		Date test = converter.stringToDate("18-03-18");
//		System.out.println(companyfacade.getCouponByDate(test));
		
		//test6
/*		
		System.out.println(companyfacade.getAllCoupon());
		System.out.println("-----------------------------------------------------------------------------------------------");
		System.out.println(companyfacade.getCoupon(3));
		System.out.println("-----------------------------------------------------------------------------------------------");
		Coupon coup1 = new Coupon(3, "aaa", converter.stringToDate("17-01-01"), converter.stringToDate("17-03-30"), 5,CouponType.CAMPING, "hhh", 25.8,"ttt");
		companyfacade.updateCoupon(coup1);
		System.out.println("-----------------------------------------------------------------------------------------------");
		System.out.println(companyfacade.getCoupon(3));
		System.out.println("-----------------------------------------------------------------------------------------------");
*/

		//test7
/*		
		companyfacade = (CompanyFacade) CouponSystem.getInstance().login("intel", "456", ClientType.COMPANY);
		Coupon coup4 = new Coupon(0, "ccc",  converter.stringToDate("17-01-05"), converter.stringToDate("17-03-13"), 5,CouponType.CAMPING, "hhh", 25.7,"ttt");
		Coupon coup5 = new Coupon(0, "ddd", converter.stringToDate("16-11-20"), converter.stringToDate("17-02-20"), 5,CouponType.CAMPING, "hhh", 25.7,"ttt");
		companyfacade.createCoupon(coup4);
		companyfacade.createCoupon(coup5);
		System.out.println(companyfacade.getAllCoupon());
*/

		//test8
		customerfacade = (CustomerFacade) CouponSystem.getInstance().login("jhon","963",ClientType.CUSTOMER);
		
		customerfacade = (CustomerFacade) CouponSystem.getInstance().login("jhon","369",ClientType.CUSTOMER);
/*
		System.out.println(customerfacade.getAllPurchasedCoupons());
		Coupon coupon = companyfacade.getCoupon(1);
		System.out.println(coupon.getEndDate());
		customerfacade.purchaseCoupon(coupon);
*/
		
/*
  		System.out.println(customerfacade.getAllPurchasedCoupons());
		System.out.println(customerfacade.getAllPurchasedCouponsByType(CouponType.FOOD));
		System.out.println(customerfacade.getAllPurchasedCouponsByPrice(35));
*/
		//test 9
/*
		companyfacade = (CompanyFacade) CouponSystem.getInstance().login("bezeq", "321", ClientType.COMPANY);
		Coupon coup = companyfacade.getCoupon(3);
		companyfacade.removeCoupon(coup);
*/
		//test 10
	
		customerfacade = (CustomerFacade) CouponSystem.getInstance().login("jhon","369",ClientType.CUSTOMER);
		System.out.println(customerfacade.getAllPurchasedCoupons());
		customerfacade.purchaseCoupon(companyfacade.getCoupon(2));
		customerfacade.purchaseCoupon(companyfacade.getCoupon(5));
		
		
		adminfacade = (AdminFacade) CouponSystem.getInstance().login("admin", "1234", ClientType.ADMIN);
		//System.out.println(adminfacade.getAllCompanies());
		//System.out.println(adminfacade.getAllCustomer());
		//adminfacade.removeCompany(adminfacade.getCompany(2));
		//adminfacade.removeCustomer(adminfacade.getCustomer(2));
	}
}
