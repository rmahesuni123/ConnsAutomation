package com.etouch.connsPages;



import org.apache.commons.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import com.etouch.common.CommonMethods;

import com.etouch.connsTests.Conns_PayYourBill_Page;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.util.LogUtil;

import com.etouch.taf.webui.selenium.WebPage;

public class PayYourBillPage extends Conns_PayYourBill_Page {
	 static Log log = LogUtil.getLog(PayYourBillPage.class);
	 String payBillUrl = null;
	 //CommonMethods commonMethods = new CommonMethods();
	 static CommonMethods commonMethods = new CommonMethods();
	 WebPage webPage;
	 PayYourBillPage payYourBillPage/* = new CreditAppPage()*/;
	 
	 /**
	  * Verify PayYourBill abd Account page Verbiage
	  * @param softAssert
	  * @throws Exception
	  */
	 public void verifyVerbiageOfPayYourBillAndAccountPages(SoftAssert softAssert) throws Exception {
		 log.info("******** Verify Pay your bill page title  ******************");//
		 softAssert.assertEquals(webPageMap.get(Thread.currentThread().getId()).getPageTitle(),commonData.get("payyourbillpagetitle"),
					"Pay your bill page title . Expected "+commonData.get("payyourbillpagetitle")+" Actual : "+webPageMap.get(Thread.currentThread().getId()).getPageTitle());
		 log.info("******** Verify pay your bills and Credit account Verbiage Text  ******************");//
			String payYourBillText = commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()),commonData.get("payYourBillOnlineXpath") , softAssert);
			String creditActText = commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("creditAccountXpath"), softAssert);
			log.info("******** payYourBillText  ******************" +payYourBillText);
			log.info("******** commonData.get(\"payYourBillOnlineText\")  ******************" +commonData.get("payYourBillOnlineText"));
			softAssert.assertEquals(payYourBillText,commonData.get("payYourBillOnlineText"),
					"Pay YourBill Online Text . Expected "+commonData.get("payYourBillOnlineText")+" Actual : "+payYourBillText);
			log.info("******** creditActText  ******************" +creditActText);
			log.info("******** commonData.get(\"creditAccountText\")  ******************" +commonData.get("creditAccountText"));
			softAssert.assertEquals(creditActText,commonData.get("creditAccountText"),
					"Credit Account Text . Expected "+commonData.get("creditAccountText")+" Actual : "+creditActText);
			log.info("******** Click on Pay Bill button  ******************" );
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("payBillButtonXpath"),softAssert);
			
			log.info("******** Verify New Customer and Register Customers Verbiage text  ******************" );//
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
	 public void loginWithGivenDetails(String email, String password,WebPage webPage,SoftAssert softAssert) throws Exception{
		 JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
		 commonMethods.sendKeysById(webPage, commonData.get("emailId"), email, softAssert);
		 commonMethods.sendKeysById(webPage, commonData.get("passwordId"), password, softAssert);
		 commonMethods.clickElementById(webPageMap.get(Thread.currentThread().getId()), commonData.get("loginButtonId"),softAssert);
		 //webPage.sleep(2000);
		 js.executeScript("javascript: setTimeout(\"history.go(0)\", 2000)");
		 log.info("webPage.getPageTitle() : "+webPage.getPageTitle());
		 log.info("commonData.get('successLoginPageTitle') : "+commonData.get("successLoginPageTitle"));
		 softAssert.assertEquals(webPage.getPageTitle(),commonData.get("successLoginPageTitle"),
					"Success Loign page title . Expected "+commonData.get("successLoginPageTitle")+" Actual : "+webPage.getPageTitle());
			
		 
	 }
	 
	 
	 /**
	  * Conns login functionality
	  * @param email
	  * @param password
	  * @param webPage
	  * @param softAssert
	  * @throws Exception
	  */
	 public void LoginWithGivenDetails(String email, String password,WebPage webPage,SoftAssert softAssert) throws Exception{
		 JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
		 commonMethods.sendKeysById(webPage, commonData.get("emailId"), email, softAssert);
		 commonMethods.sendKeysById(webPage, commonData.get("passwordId"), password, softAssert);
		 commonMethods.clickElementById(webPageMap.get(Thread.currentThread().getId()), commonData.get("loginButtonId"),softAssert);
		 //webPage.sleep(2000);
		 js.executeScript("javascript: setTimeout(\"history.go(0)\", 2000)");
		 log.info("webPage.getPageTitle() : "+webPage.getPageTitle());
		 log.info("commonData.get('successLoginPageTitle') : "+commonData.get("ConnsSuccessLoginPageTitle"));
		 softAssert.assertEquals(webPage.getPageTitle(),commonData.get("ConnsSuccessLoginPageTitle"),
					"Success Loign page title . Expected "+commonData.get("ConnsSuccessLoginPageTitle")+" Actual : "+webPage.getPageTitle());
			
		 
	 }
	 

		/**
		 * author : Asim Singh Verify ClickElementPresenceByJS
		 * 
		 * @param webPage
		 * @param softAssert
		 * @throws InterruptedException
		 */
		public void ClickElementPresenceByJS(WebPage webPage, String ElementXpath, SoftAssert softAssert) {
			JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
			try {
				log.info("**************ClickElementPresenceByJS starts  : ************************");
				WebElement PageElement = webPage.getDriver().findElement(By.xpath(ElementXpath));
				if (PageElement.isDisplayed())
					log.info("PageElement Successfully Found  : ");
				commonMethods.scrollToElement(webPage, ElementXpath, softAssert);
				js.executeScript("arguments[0].click();", PageElement);
				Thread.sleep(2000);
				log.info("**************PageElement Successfully Found & Clicked  : ************************");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		/**
		 * author : Asim Singh Verify ClickElementPresenceByJS
		 * 
		 * @param webPage
		 * @param softAssert
		 * @throws InterruptedException
		 */
		public void ScrollToElement(WebPage webPage, String ElementXpath, SoftAssert softAssert) {
			try {
				log.info("**************Finding ElementPresenceByJS starts  : ************************");
				WebElement PageElement = webPage.getDriver().findElement(By.xpath(ElementXpath));
				if (PageElement.isDisplayed())
					log.info("PageElement Successfully Found  : ");
				commonMethods.scrollToElement(webPage, ElementXpath, softAssert);
				
				Thread.sleep(2000);
				log.info("**************PageElement Successfully Found  : ************************");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * @author Name - Asim Singh
		 * The method used to click element using xpath 
		 * Return type is void
		 * Any structural modifications to the display of the link should be done by overriding this method.
		 * @throws PageException  If an input or output exception occurred
		 **/
		public void doubleClickElementbyXpath(WebPage webPage, String locator, SoftAssert softAssert){
			try {
				log.info("Clicking on element using xpath - "+locator);
				webPage.findObjectByxPath(locator).doubleClick();
			} catch (PageException e) {
				softAssert.fail("Unable to click on element using Xpath : "+ locator+". Localized Message: "+e.getLocalizedMessage());
			}
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
	

}

