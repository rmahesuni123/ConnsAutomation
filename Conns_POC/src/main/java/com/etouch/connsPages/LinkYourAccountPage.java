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
import com.etouch.connsTests.Conns_Credit_App_Page;
import com.etouch.connsTests.Conns_LinkYour_Account_Page;
import com.etouch.connsTests.Conns_PayYourBill_Page;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.webui.ITafElement;
import com.etouch.taf.webui.selenium.WebPage;

public class LinkYourAccountPage extends Conns_LinkYour_Account_Page {
	 static Log log = LogUtil.getLog(LinkYourAccountPage.class);
	 String payBillUrl = null;
	 CommonMethods commonMethods = new CommonMethods();
	 WebPage webPage;
	 LinkYourAccountPage linkYourAccountPage;
	 
	 
	 /**
	  * Conn's Login funcationality
	  * @param emialId
	  * @param email
	  * @param passwordId
	  * @param password
	  * @param webPage
	  * @param softAssert
	  * @throws Exception
	  */
	 public void connsLogin(String emialId, String email,String passwordId, String password,WebPage webPage,SoftAssert softAssert) throws Exception{
		 commonMethods.sendKeysById(webPage,emialId, email, softAssert);
		 commonMethods.sendKeysById(webPage, commonData.get("passwordId"), password, softAssert);
		 commonMethods.clickElementById(webPageMap.get(Thread.currentThread().getId()), commonData.get("loginButtonId"),softAssert);
		 webPage.sleep(6000);
		 softAssert.assertEquals(webPageMap.get(Thread.currentThread().getId()).getPageTitle(),commonData.get("successLoginPageTitle"),
					"Success Loign page title . Expected "+commonData.get("successLoginPageTitle")+" Actual : "+webPageMap.get(Thread.currentThread().getId()).getPageTitle());
			
		 
	 }
	 
	 /**
	  * Enter Accoount no and Account SSN
	  * @param accountNoId
	  * @param accountNo
	  * @param accountSSNNoId
	  * @param accountSSNNo
	  * @param webPage
	  * @param softAssert
	  * @throws Exception
	  */
	 public void linkYourConnsCreditAccount(String accountNoId, String accountNo,String accountSSNNoId, String accountSSNNo,WebPage webPage,SoftAssert softAssert) throws Exception{
		 commonMethods.sendKeysById(webPage,accountNoId, accountNo, softAssert);
		 commonMethods.sendKeysById(webPage, accountSSNNoId, accountSSNNo, softAssert);
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
	 * Verify Link your account error page
	 * @param testData
	 * @param webPage
	 * @param softAssert
	 * @throws Exception
	 */
	public void verifyLinkYourAccountErrorPage(String[][] testData,WebPage webPage, SoftAssert softAssert) throws Exception {
		
		//Click on Link New Account Button
		commonMethods.clickElementById(webPage, testData[2][1], softAssert);
		
		//Verify Your Conn's link your account error page
		
		softAssert.assertEquals(commonMethods.getTextbyXpath(webPage, testData[5][1], softAssert),testData[6][1],
				"Link your account Error page header text . Expected "+testData[6][1]+" Actual : "+commonMethods.getTextbyXpath(webPage, testData[5][1], softAssert));
		//Click on Pay Your account error page 'Link Account' Button
		commonMethods.clickElementbyXpath(webPage, testData[10][1], softAssert);
		
	}
	

}

