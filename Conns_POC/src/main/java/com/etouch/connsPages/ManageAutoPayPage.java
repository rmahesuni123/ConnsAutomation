package com.etouch.connsPages;

import java.awt.AWTException;
import java.awt.Robot;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.etouch.common.CommonMethods;
import com.etouch.connsTests.Conns_BillPay_Page;
import com.etouch.connsTests.Conns_Credit_App_Page;
import com.etouch.connsTests.Conns_Manage_AutoPay_Page;
import com.etouch.connsTests.Conns_PayYourBill_Page;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.webui.ITafElement;
import com.etouch.taf.webui.selenium.WebPage;

public class ManageAutoPayPage extends Conns_Manage_AutoPay_Page {
	 static Log log = LogUtil.getLog(ManageAutoPayPage.class);
	 String payBillUrl = null;
	 CommonMethods commonMethods = new CommonMethods();
	 WebPage webPage;
	 ManageAutoPayPage billPage/* = new CreditAppPage()*/;
	 
	 /**
	  * Verify PayYourBill abd Account page Verbiage
	  * @param softAssert
	  * @throws Exception
	  */
	 public void verifyVerbiageOfPayYourBillAndAccountPages(SoftAssert softAssert) throws Exception {
		 //Verify Pay your bill page title
		 softAssert.assertEquals(webPageMap.get(Thread.currentThread().getId()).getPageTitle(),commonData.get("payyourbillpagetitle"),
					"Pay your bill page title . Expected "+commonData.get("payyourbillpagetitle")+" Actual : "+webPageMap.get(Thread.currentThread().getId()).getPageTitle());
		//Verify pay your bills and Credit account Verbiage Text
			String payYourBillText = commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()),commonData.get("payYourBillOnlineXpath") , softAssert);
			String creditActText = commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("creditAccountXpath"), softAssert);
			
			softAssert.assertEquals(payYourBillText,commonData.get("payYourBillOnlineText"),
					"Pay YourBill Online Text . Expected "+commonData.get("payYourBillOnlineText")+" Actual : "+payYourBillText);
			
			softAssert.assertEquals(creditActText,commonData.get("creditAccountText"),
					"Credit Account Text . Expected "+commonData.get("creditAccountText")+" Actual : "+creditActText);
			//Click on Pay Bill button
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("payBillButtonXpath"),softAssert);
			
			//Verify New Customer and Register Customers Verbiage text
			String customersText = commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("newCustomersXpath"), softAssert);
			String regCustomersText = commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("registeredCustomersXpath"), softAssert);
			
			softAssert.assertEquals(customersText,commonData.get("newCustomersText"),
					"New Customers Text . Expected "+commonData.get("newCustomersText")+" Actual : "+customersText);
			
			softAssert.assertEquals(regCustomersText,commonData.get("registeredCustomersText"),
					"Registered Customers Text . Expected "+commonData.get("registeredCustomersText")+" Actual : "+regCustomersText);	
		
			
		}
	 
	 
	 /**
	  * Conns login functionality
	  * @param email
	  * @param password
	  * @param webPage
	  * @param softAssert
	  * @throws Exception
	  */
	 public void connsLogin(String email, String password,WebPage webPage,SoftAssert softAssert) throws Exception{
		 commonMethods.sendKeysById(webPage, commonData.get("emailId"), email, softAssert);
		 commonMethods.sendKeysById(webPage, commonData.get("passwordId"), password, softAssert);
		 commonMethods.clickElementById(webPageMap.get(Thread.currentThread().getId()), commonData.get("loginButtonId"),softAssert);
		 webPage.sleep(4000);
		 softAssert.assertEquals(webPageMap.get(Thread.currentThread().getId()).getPageTitle(),commonData.get("successLoginPageTitle"),
					"Success Loign page title . Expected "+commonData.get("successLoginPageTitle")+" Actual : "+webPageMap.get(Thread.currentThread().getId()).getPageTitle());
			
		 
	 }

	 /**
	  * Verify Register button
	  * @param webPage
	  * @param softAssert
	  * @throws InterruptedException
	  */
	public void verifyRegisterButton(WebPage webPage, SoftAssert softAssert) throws InterruptedException {
		commonMethods.clickElementbyXpath(webPage, commonData.get("registerBtnXpath"), softAssert);
		
		softAssert.assertEquals(webPageMap.get(Thread.currentThread().getId()).getPageTitle(),commonData.get("registerPageTitle"),
					"Register page title . Expected "+commonData.get("registerPageTitle")+" Actual : "+webPageMap.get(Thread.currentThread().getId()).getPageTitle());
		
		
	}
	
	 /**
	  * SignOut functionality
	  * @param webPage
	  * @param softAssert
	  * @throws Exception
	  */
	public void connsSignOut(WebPage webPage, SoftAssert softAssert) throws Exception{
		commonMethods.clickElementById(webPage, commonData.get("myAccountLinkId"),softAssert);
		commonMethods.clickElementbyXpath(webPage, commonData.get("signOutLinkXpath"), softAssert);
		webPage.getDriver().manage().deleteAllCookies();
		
	}
	
	/**
	 * Get Payment values after $ sign
	 * @param locator
	 * @param webpage
	 * @param softAssert
	 * @return
	 */
	public int getAmount(String locator, WebPage webpage, SoftAssert softAssert) {
		String currentBal = commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), locator, softAssert);
		int curBalance = Integer.parseInt(currentBal.substring(1));
		log.info("Current Balance Amount:"+curBalance);
		return curBalance;
	}
	
	/**
	 * Get payment values with Text
	 * @param locator
	 * @param webpage
	 * @param softAssert
	 * @return
	 */
	public int getPaymentAmountByXpath(String locator, WebPage webpage, SoftAssert softAssert) {
		String balance = commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), locator, softAssert);
		log.info("Balance Amount:"+balance);
		log.info("Balance Amount:"+balance.substring(balance.indexOf("$")+1));
		log.info("Balance Amount:"+balance.indexOf("$")+3);
		int curBalance = Integer.parseInt(balance.substring(balance.indexOf("$")+1,balance.indexOf("$")+3));
		log.info("Payemnt Balance Amount:"+curBalance);
		return curBalance;
	}
	

}

