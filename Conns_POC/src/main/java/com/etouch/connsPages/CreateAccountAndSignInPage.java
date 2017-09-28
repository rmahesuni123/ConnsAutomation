package com.etouch.connsPages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.etouch.common.CommonMethods;
import com.etouch.common.CommonPage;
import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.driver.web.WebDriver;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.taf.webui.ITafElement;
import com.etouch.taf.webui.selenium.WebPage;

public class CreateAccountAndSignInPage extends CommonPage {

	/**
	 *
	 * @param sbPageUrl
	 *            the sb page url
	 * @param webPage
	 *            the web page
	 */

	static Log log = LogUtil.getLog(ConnsHomePage.class);
	String testType;
	String testBedName;
	static CommonMethods commonMethods = new CommonMethods();
	public CreateAccountAndSignInPage(String sbPageUrl, WebPage webPage) 
	{
		super(sbPageUrl, webPage);
		CommonUtil.sop("webDriver in eTouchWebSite Page : " + webPage.getDriver());
		//loadPage();
	}
	public static void verifyErrorMessageByXpath(WebPage webPage,SoftAssert softAssert, String errorMessageFieldName, String locator,
			String expectedErrorMessage) throws PageException {
		log.info("Verifiyikng error message for : " + errorMessageFieldName + " : Expected Message : "
				+ expectedErrorMessage);
		CommonMethods.waitForWebElement(By.xpath(locator), webPage);
		String actualErrorMessage = commonMethods.getTextbyXpath(webPage, locator, softAssert);
		softAssert.assertTrue(expectedErrorMessage.equals(actualErrorMessage), "Failed to verify error field :"
				+ errorMessageFieldName + " : Expected : " + expectedErrorMessage + " Actual : " + actualErrorMessage);
	}
	public static String getToolTipText(WebPage webPage, SoftAssert softAssert, String locator) throws PageException, InterruptedException {
		   /* Actions action = new Actions(webPage.getDriver());
		    WebElement element = webPage.getDriver().findElement(By.xpath(locator));
		    Thread.sleep(2000);
		    action.moveToElement(element).build().perform();
		    String ToolTipText = element.getAttribute("title");
		    return ToolTipText;*/
		
		String tooltipTxt = webPage.getDriver().findElement(By.xpath(locator)).getAttribute("title");
		//Assert.assertEquals(actualTooltipTxt, tooltipTxt);
		return tooltipTxt;
}
	public static String CreateNewEmailID()
	 {
		return getID()+"@gmail.com"; 
	 }
	
	public static String getID()
	 {
	   //int n=351;
	   double d=0;
	   int num=0;    
	   String Random;   
	   {                               
	    while(true)                
	    {               
	      int final_limit=100000; //Specify the maximum limit               
	      d=Math.random()*final_limit;                
	      num=(int)d;              
	      Random = "Test" + String.valueOf(num);
	      break;
	    }                   
	    return Random;     
	   }
	 }
}	