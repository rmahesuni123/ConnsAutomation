package com.etouch.common;

import java.awt.AWTException;

import java.awt.Robot;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.webui.selenium.WebPage;

import io.appium.java_client.android.AndroidDriver;

public class CommonMethods {

	static Log log = LogUtil.getLog(CommonMethods.class);
	static CommonMethods commonMethods = new CommonMethods();

	/**
	 * @author Name - Deepak Bhambri
	 * The method used to navigate to page
	 * Return type is String
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public void navigateToPage(WebPage webPage,String navigatingUrl, SoftAssert softAssert){
			log.info("Navigating to URL: "+navigatingUrl);
			webPage.navigateToUrl(navigatingUrl);
	}
	
	public static void navigateToPage(WebPage webPage,String navigatingUrl){
			log.info("Navigating to URL: "+navigatingUrl);
			webPage.navigateToUrl(navigatingUrl);
			}
	/**
	 * @author Name - Deepak Bhambri
	 * The method used to verify if element is present
	 * Returns boolean value
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public boolean verifyElementisPresent(WebPage webPage, String locator, SoftAssert softAssert){
		Boolean isElementPresent=false;
		try{
			log.info("Verifying if element is present by locator - "+locator);
			isElementPresent = webPage.findObjectByxPath(locator).isDisplayed();
		}catch(Throwable e){
			log.info("Element not visible using locator: "+locator+". Localized Message: "+e.getLocalizedMessage());
		}
		return isElementPresent;
	}




	public static boolean verifyElementisPresent(WebPage webPage, String locator){
		Boolean isElementPresent=false;
		try{
			log.info("Verifying if element is present by locator - "+locator);
			isElementPresent = webPage.findObjectByxPath(locator).isDisplayed();
		}catch(Throwable e){
		}
		return isElementPresent;
	}


	/**
	 * @author Name - Asim Singh
	 * The method used to verify if element is present by javascript
	 * Returns boolean value
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/



	public boolean isElementPresentCheckUsingJavaScriptExecutor(WebPage webPage, String locator, SoftAssert softAssert){
		JavascriptExecutor js=(JavascriptExecutor) webPage.getDriver();
		try {
			Object obj = js.executeScript("return typeof(arguments[0]) != 'undefined' && arguments[0] != null;",
					locator);
			if (obj.toString().contains("true")) {
				System.out.println("isElementPresentCheckUsingJavaScriptExecutor: SUCCESS");
				
			} else {
				System.out.println("isElementPresentCheckUsingJavaScriptExecutor: FAIL");
			}

		} catch (NoSuchElementException e) {
			System.out.println("isElementPresentCheckUsingJavaScriptExecutor: FAIL");
		}
		return true;
	}



	/**
	 * @author Name - Asim Singh
	 * The method used to click on link using x-path and return page url
	 * Return type is String
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public String clickAndGetPageURLByJS(WebPage webPage, String locator, String linkName, SoftAssert softAssert){
		String pageUrl="";
		JavascriptExecutor js=(JavascriptExecutor) webPage.getDriver();
		try{
			log.info("Clicking on link : "+linkName);
			String mainWindow = webPage.getDriver().getWindowHandle();
			commonMethods.doubleClickElementbyXpath(webPage, locator, softAssert);
			//webPage.findObjectByxPath(locator).click();
			//commonMethods.scrollToElement(webPage, locator, softAssert);
			log.info("Clicking on element using xpath - "+locator);
			//WebElement element=webPage.getDriver().findElement(By.xpath(locator));
			
			
			//js.executeScript("arguments[0].click();", element);
			Set<String> windowHandlesSet = webPage.getDriver().getWindowHandles();
			if(windowHandlesSet.size()>1){
				for(String winHandle:windowHandlesSet){
					webPage.getDriver().switchTo().window(winHandle);
					if(!winHandle.equalsIgnoreCase(mainWindow)){
						log.info("More than 1 window open after clicking on link : "+linkName);
						pageUrl=webPage.getCurrentUrl();
						webPage.getDriver().close();
						webPage.getDriver().switchTo().window(mainWindow);
					}
				}
			}else{
				pageUrl= webPage.getCurrentUrl();
			}
			log.info("Actual URL : "+pageUrl);
		}catch(Throwable e){
			e.printStackTrace();
			softAssert.fail("Unable to click on link '"+linkName+". Localized Message: "+e.getLocalizedMessage());
		}
		return pageUrl;
	}




	public String clickAndGetPageURLUsingJS(WebPage webPage, String locator, String linkName, String TargetPageLocator,
			SoftAssert softAssert) throws PageException, InterruptedException {
		String mainWindow = webPage.getDriver().getWindowHandle();
		WebElement element = webPage.findObjectByxPath(locator).getWebElement();
		JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
		js.executeScript("arguments[0].click();", element);
		log.info("Clicked on Link : " + linkName);
		//waitPageToLoad();
		waitForPageLoad(webPage, softAssert);
		String pageUrl = "";
		try {
			Set<String> windowHandlesSet = webPage.getDriver().getWindowHandles();
			if (windowHandlesSet.size() > 1) {
				for (String winHandle : windowHandlesSet) {
					webPage.getDriver().switchTo().window(winHandle);
					if (!winHandle.equalsIgnoreCase(mainWindow)) {
						log.info("More than 1 window open after clicking on link : " + linkName);
						pageUrl = webPage.getCurrentUrl();
						webPage.getDriver().close();
						webPage.getDriver().switchTo().window(mainWindow);
					}
				}
			} else {
				pageUrl = webPage.getCurrentUrl();
			}
			log.info("Actual URL : " + pageUrl);
		} catch (Throwable e) {
			softAssert.fail("Unable to click on link '" + linkName + ". Localized Message: " + e.getLocalizedMessage());
		}
		return pageUrl;
	}

	
	public void HandlingResizeableDropDown(WebPage webPage,String testType, String testBedName,String LinkDropDown,String LegalDisclosureLink,String [][]testData,SoftAssert softAssert){		
		JavascriptExecutor js=(JavascriptExecutor) webPage.getDriver();
		try{
			
			for(int i = 5;i<=6;i++) {
				log.info("****** HandlingResizeableDropDown Iteration value : ********* "+i);
				commonMethods.waitForPageLoad(webPage, softAssert);
				WebElement element=webPage.getDriver().findElement(By.xpath(testData[0][i]));					
				js.executeScript("arguments[0].click();", element);
				commonMethods.waitForPageLoad(webPage, softAssert);	
				log.info("****** Datasheet Iteration value : ********* "+testData[0][i].toString());
				CommonMethods.waitForGivenTime(5);
			}
		
		}
		catch(Throwable e){
			e.printStackTrace(); 			
		}
		
	}
	
	/**
	 * @author Name - Archana
	 * The method used to click on link using x-path and return page url
	 * Return type is String
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public String clickAndGetPageURLByJSLegalDisclosure(WebPage webPage, String locator, String linkName, String Navigation_URL, String TargetpageLocator,String TargetPageHeader,String testType, String testBedName,String LinkDropDown,String LegalDisclosureLink,String [][] testData,SoftAssert softAssert){
		String pageUrl="";
		JavascriptExecutor js=(JavascriptExecutor) webPage.getDriver();
		try{
			log.info("Clicking on link : "+linkName);
			String mainWindow = webPage.getDriver().getWindowHandle();
			//webPage.findObjectByxPath(locator).click();
			log.info("****** testBedName : "+testBedName.toString());
			if (testType.equalsIgnoreCase("Mobile") || testBedName.equalsIgnoreCase("edge")  ) //|| testBedName.equalsIgnoreCase("Chrome")
			{			
			HandlingResizeableDropDown(webPage,testType,testBedName,LinkDropDown,LegalDisclosureLink,testData,softAssert);
			log.info("****** HandlingResizeableDropDown Completed successfully for **********  : "+testBedName.toString()); 
			}
		else {
				log.info("Clicking on element using xpath - "+locator);
				WebElement element=webPage.getDriver().findElement(By.xpath(locator));					
				js.executeScript("arguments[0].click();", element);
			}	
			
			log.info("****** Window Switching Starts **********  : "+testBedName.toString()); 

			Set<String> windowHandlesSet = webPage.getDriver().getWindowHandles();
			if(windowHandlesSet.size()>1){
				for(String winHandle:windowHandlesSet){
					webPage.getDriver().switchTo().window(winHandle);
					if(!winHandle.equalsIgnoreCase(mainWindow)){
						log.info("More than 1 window open after clicking on link : "+linkName);
						pageUrl=webPage.getCurrentUrl();
						
						log.info("*************Verify Header on Legal Disclosures page**************************");
						log.info("Actual : "+getTextbyXpath(webPage, TargetpageLocator, softAssert)+" Expected : "+TargetPageHeader);
						softAssert.assertTrue((getTextbyXpath(webPage, TargetpageLocator, softAssert).equals(TargetPageHeader)));	
						webPage.getDriver().close();
						webPage.getDriver().switchTo().window(mainWindow);
						
					}
				}
			}else{
				pageUrl= webPage.getCurrentUrl();
			}
			log.info("Actual URL : "+pageUrl);
		}catch(Throwable e){
			e.printStackTrace();
			softAssert.fail("Unable to click on link '"+linkName+". Localized Message: "+e.getLocalizedMessage());
		}
		return pageUrl;
	}

	

	/**
	 * @author Name - Archana
	 * The method used to click on link using x-path and return page url
	 * Return type is String
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public String clicknGetPageURLByJSLegalDisclosure(WebPage webPage, String locator, String linkName, String Navigation_URL, String TargetpageLocator,String TargetPageHeader,String testType, String testBedName,String LinkDropDown,String LegalDisclosureLink,String [][] testData,SoftAssert softAssert){
		String pageUrl="";
		JavascriptExecutor js=(JavascriptExecutor) webPage.getDriver();
		try{
			log.info("Clicking on link : "+linkName);
			String mainWindow = webPage.getDriver().getWindowHandle();
			//webPage.findObjectByxPath(locator).click();
			log.info("****** testBedName : "+testBedName.toString());
			if (testType.equalsIgnoreCase("Mobile") || testBedName.equalsIgnoreCase("edge") || testBedName.equalsIgnoreCase("Chrome") ) //
			{			
			HandlingResizeableDropDown(webPage,testType,testBedName,LinkDropDown,LegalDisclosureLink,testData,softAssert);
			log.info("****** HandlingResizeableDropDown Completed successfully for **********  : "+testBedName.toString()); 
			}
		else {
				commonMethods.scrollToElement(webPage, locator, softAssert);
				log.info("****** Successfully Scrolled to Element Iteration value : ********* "+locator);
				log.info("Clicking on element using xpath - "+locator);
				WebElement element=webPage.getDriver().findElement(By.xpath(locator));					
				js.executeScript("arguments[0].click();", element);
			}	
			
			log.info("****** Window Switching Starts **********  : "+testBedName.toString()); 

			Set<String> windowHandlesSet = webPage.getDriver().getWindowHandles();
			if(windowHandlesSet.size()>1){
				for(String winHandle:windowHandlesSet){
					webPage.getDriver().switchTo().window(winHandle);
					if(!winHandle.equalsIgnoreCase(mainWindow)){
						log.info("More than 1 window open after clicking on link : "+linkName);
						pageUrl=webPage.getCurrentUrl();
						
						log.info("*************Verify Header on Legal Disclosures page**************************");
						log.info("Actual : "+getTextbyXpath(webPage, TargetpageLocator, softAssert)+" Expected : "+TargetPageHeader);
						softAssert.assertTrue((getTextbyXpath(webPage, TargetpageLocator, softAssert).equals(TargetPageHeader)));	
						webPage.getDriver().close();
						webPage.getDriver().switchTo().window(mainWindow);
						
					}
				}
			}else{
				pageUrl= webPage.getCurrentUrl();
			}
			log.info("Actual URL : "+pageUrl);
		}catch(Throwable e){
			e.printStackTrace();
			softAssert.fail("Unable to click on link '"+linkName+". Localized Message: "+e.getLocalizedMessage());
		}
		return pageUrl;
	}

	

	/**
	 * @author Name - Deepak Bhambri
	 * The method used to click on link using x-path and return page url
	 * Return type is String
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public String clickAndGetPageURL(WebPage webPage, String locator, String linkName, SoftAssert softAssert){
		String pageUrl="";
		try{
			log.info("Clicking on link : "+linkName);
			String mainWindow = webPage.getDriver().getWindowHandle();
			webPage.findObjectByxPath(locator).click();
			Set<String> windowHandlesSet = webPage.getDriver().getWindowHandles();
			if(windowHandlesSet.size()>1){
				for(String winHandle:windowHandlesSet){
					webPage.getDriver().switchTo().window(winHandle);
					if(!winHandle.equalsIgnoreCase(mainWindow)){
						log.info("More than 1 window open after clicking on link : "+linkName);
						pageUrl=webPage.getCurrentUrl();
						webPage.getDriver().close();
						webPage.getDriver().switchTo().window(mainWindow);
					}
				}
			}else{
				pageUrl= webPage.getCurrentUrl();
			}
			log.info("Actual URL : "+pageUrl);
		}catch(Throwable e){
			softAssert.fail("Unable to click on link '"+linkName+". Localized Message: "+e.getLocalizedMessage());
		}
		return pageUrl;
	}
	public static void closeLocationPopupForProductSearch(WebPage webPage) throws InterruptedException{

		log.info("Checking if location browser pop-up is present for this browser.");
		Thread.sleep(2000);
		try{
			Alert alert=webPage.getDriver().switchTo().alert();
			alert.accept();

		}catch(Exception e){
			log.info("Location pop-up not present for this browser.");
		}
	}


	public String[][] readExcelData(String DataFilePath,String key) throws InterruptedException{
		Thread.sleep(2000);
		String [][]keyData = null ;
		try{
			keyData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator", key + "Region");

		}catch(Exception e){
			log.info("Location pop-up not present for this browser.");
		}
		return keyData;
	}

	/**
	 * @author Name - Deepak Bhambri
	 * The method used to click on link using x-path and return page url
	 * Return type is String
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public String clickAndGetPageURL(WebPage webPage, String locator, String linkName, SoftAssert softAssert,String waitOnelementLocator){
		String pageUrl="";
		try{
			log.info("Clicking on link : "+linkName);
			String mainWindow = webPage.getDriver().getWindowHandle();
			clickElementbyXpath(webPage, locator, softAssert);
			Set<String> windowHandlesSet = webPage.getDriver().getWindowHandles();
			if(windowHandlesSet.size()>1){
				for(String winHandle:windowHandlesSet){
					webPage.getDriver().switchTo().window(winHandle);
					if(!winHandle.equalsIgnoreCase(mainWindow)){
						log.info("More than 1 window open after clicking on link : "+linkName);
						CommonMethods.waitForWebElement(By.xpath(waitOnelementLocator), webPage);
						pageUrl=webPage.getCurrentUrl();
						webPage.getDriver().close();
						webPage.getDriver().switchTo().window(mainWindow);
					}
				}
			}else{
				CommonMethods.waitForWebElement(By.xpath(waitOnelementLocator), webPage);
				pageUrl= webPage.getCurrentUrl();
			}
			log.info("Actual URL : "+pageUrl);
		}catch(Throwable e){
			softAssert.fail("Unable to click on link '"+linkName+". Localized Message: "+e.getLocalizedMessage());
		}
		return pageUrl;
	}

	/**
	 * @author Name - Deepak Bhambri
	 * The method used to click element using xpath 
	 * Return type is void
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws InterruptedException 
	 * @throws PageException  If an input or output exception occurred
	 **/
	public void clickElementbyXpath(WebPage webPage, String locator, SoftAssert softAssert) throws InterruptedException{
		try {
			/*log.info("****************************************isEnabled"+webPage.findObjectByxPath(locator).isEnabled());
			log.info("****************************************isDispalyed"+webPage.findObjectByxPath(locator).isDisplayed());
			log.info("****************************************"+webPage.findObjectByxPath(locator).isElementVisible());*/
//			log.info("@@@@@@@@@@@@@@@@@@@@@Clicking on element using xpath - "+locator);
			
			webPage.findObjectByxPath(locator).click();
		} catch (Throwable e) {
			e.printStackTrace();
			softAssert.fail("Unable to click on element using Xpath : "+ locator+". Localized Message: "+e.getLocalizedMessage());
		}
	}

	/**
	 * @author Name - Deepak Bhambri
	 * The method used to click on child element using xpath
	 * Return type us void
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public void clickWithChildElementbyXpath(WebPage webPage,String parentlocator, String locator, String linkName, SoftAssert softAssert){
		try{
			if(!parentlocator.equalsIgnoreCase("NA")){
				log.info("Clicking on parent locator : "+parentlocator);
				webPage.findObjectByxPath(parentlocator).click();	
			}
			log.info("Clicking on link : "+linkName);
			webPage.findObjectByxPath(locator).click();
		}catch(Throwable e){
			softAssert.fail("Unable to click on link '"+linkName+". Localized Message: "+e.getLocalizedMessage());
		}
	}




	/**
	 * @author Name - Asim Singh
	 * The method used to click on child element using xpath
	 * Return type us void
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public void clickWithChildElementby_UsingJavaScriptXpath(WebPage webPage,String parentlocator, String locator, String linkName, SoftAssert softAssert){
		JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
		try{
			if(!parentlocator.equalsIgnoreCase("NA")){
				log.info("Clicking on parent locator : "+parentlocator);

				try {
					log.info("Clicking on element using xpath - "+parentlocator);
					WebElement element=webPage.getDriver().findElement(By.xpath(parentlocator));					
					js.executeScript("arguments[0].click();", element);

				} catch (Exception e) {
					e.printStackTrace();
					softAssert.fail("Unable to click on element using JavaScriptExecutor : "+ locator+". Localized Message: "+e.getLocalizedMessage());
				}
			}				
			log.info("Clicking on link : "+linkName);
			WebElement element=webPage.getDriver().findElement(By.xpath(locator));					
			js.executeScript("arguments[0].click();", element);			
		}catch(Throwable e){
			e.printStackTrace();
			softAssert.fail("Unable to click on link '"+linkName+". Localized Message: "+e.getLocalizedMessage());
		}
	}



	public  String AlertBoxHandling(WebPage webPage, SoftAssert softAssert){
		String alertActualText = "";
		try {		
			Alert alert = webPage.getDriver().switchTo().alert();
			alertActualText = alert.getText();
			log.info("Alert box text: "+alertActualText);
			Thread.sleep(2000);
			alert.accept();
		}catch(Throwable e){
			e.printStackTrace();
		}
		return  alertActualText;
	}


	/**
	 * @author Name - Deepak Bhambri
	 * The method used to get text using xpath
	 * Return type is String 
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public String getTextbyXpath(WebPage webPage, String locator, SoftAssert softAssert){
		String actualText= "";
		try {
			log.info("Getting text by using xpath - "+locator);
			actualText = webPage.findObjectByxPath(locator).getText();
			//log.info("Actual text - "+actualText);
		} catch (PageException e) {
			softAssert.fail("Unable to Get Text on element using Xpath : "+ locator+". Localized Message: "+e.getLocalizedMessage());
		}
		return actualText;
	}

	/**
	 * @author Name - Deepak Bhambri
	 * The method used to enter keys using xpath
	 * Return type is void
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public void sendKeysbyXpath(WebPage webPage, String locator, String text, SoftAssert softAssert){
		try{
			log.info("Entering keys "+text+" ");
			webPage.findObjectByxPath(locator).sendKeys(text);
		}catch(Exception e){
			softAssert.fail("Unable to Enter Keys : "+text+" using locator : "+locator+". Localized Message: "+e.getLocalizedMessage());
		}
	}

	/**
	 * @author Name - Deepak Bhambri
	 * The method used to get Css value using xpath
	 * Return type is String 
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public String getCssValueById(WebPage webPage, String locator, String cssName, SoftAssert softAssert){
		String cssValue="";
		try {
			log.info("Getting CSS value "+cssName+" for Id : "+locator);
			webPage.waitForWebElement(By.id(locator));
			cssValue = webPage.findObjectById(locator).getCssValue(cssName);
			if(cssName.equalsIgnoreCase("color")||cssName.equalsIgnoreCase("background-color")){
				cssValue=Color.fromString(cssValue).asHex();
			}
			if(cssName.equalsIgnoreCase("font-weight")){
				if(cssValue.equalsIgnoreCase("700")){
					cssValue = "bold";
				}
				if(cssValue.equalsIgnoreCase("400")){
					cssValue = "normal";
				}
			}
			log.info("Actual CSS value: "+cssValue);
		} catch (PageException e) {
			softAssert.fail("Unable to get CSS Value : "+cssName+" for locator : "+locator+". Localized Message: "+e.getLocalizedMessage());
		}
		return cssValue;
	}
	
	public String getCssvaluebyXpath(WebPage webPage, String locator, String cssName, SoftAssert softAssert){
		String cssValue="";
		try {
			log.info("Getting CSS value "+cssName+" for Xpath : "+locator);
			webPage.waitForWebElement(By.xpath(locator));
			cssValue = webPage.findObjectByxPath(locator).getCssValue(cssName);
			if(cssName.equalsIgnoreCase("color")||cssName.equalsIgnoreCase("background-color")){
				cssValue=Color.fromString(cssValue).asHex();
			}
			if(cssName.equalsIgnoreCase("font-weight")){
				if(cssValue.equalsIgnoreCase("700")){
					cssValue = "bold";
				}
				if(cssValue.equalsIgnoreCase("400")){
					cssValue = "normal";
				}
			}
			log.info("Actual CSS value: "+cssValue);
		} catch (PageException e) {
			softAssert.fail("Unable to get CSS Value : "+cssName+" for locator : "+locator+". Localized Message: "+e.getLocalizedMessage());
		}
		return cssValue;
	}
	/**
	 * @author Name - Deepak Bhambri
	 * The method used to get attribute by xpath
	 * Return type is String
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public String getAttributebyXpath(WebPage webPage, String locator, String attribute, SoftAssert softAssert){
		String attributeValue="";
		try {
			log.info("Getting attribute value "+attribute+" for Xpath : "+locator);
			webPage.waitForWebElement(By.xpath(locator));
			attributeValue = webPage.findObjectByxPath(locator).getAttribute(attribute);
		} catch (PageException e) {
			softAssert.fail("Unable to get Attribute Value : "+attribute+" for locator : "+locator+". Localized Message: "+e.getLocalizedMessage());
		}
		return attributeValue;
	}

	public void hoverOnelementbyXpath1(WebPage webPage, String locator, SoftAssert softAssert){
		try {
			log.info("Hovering on element using locator : "+locator);
			String javaScript = "var evObj = document.createEvent('MouseEvents');" +
					"evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
					"arguments[0].dispatchEvent(evObj);";
			((JavascriptExecutor)webPage.getDriver()).executeScript(javaScript, locator);
		} catch (Exception e) {
			softAssert.fail("Unable to Hover on element using Xpath : "+locator+". Localized Message: "+e.getLocalizedMessage());
		}
	}

	/******/

	public void hover_Parent_Locator_Click_ChildLocator_By_JS(String parentLocator,String childLocator,WebPage webPage)
	{  JavascriptExecutor js = (JavascriptExecutor)webPage.getDriver();
	try 
	{
		String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover',true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
		js.executeScript(mouseOverScript,parentLocator);
		Thread.sleep(1000);
		js.executeScript(mouseOverScript,childLocator);
		Thread.sleep(1000);
		js.executeScript("arguments[0].click();",childLocator);


	} catch (Exception e) {
		e.printStackTrace();
	}
	}

	public void hover_Parent_Locator_By_JS(WebPage webPage,String parentLocator,SoftAssert softAssert)
	{  JavascriptExecutor js = (JavascriptExecutor)webPage.getDriver();
	try 
	{
		String mouseOverScript =
				"if(document.createEvent)"
						+ "{var evObj = "
						+ "document.createEvent('MouseEvents');"
						+ "evObj.initEvent('mouseover',true, false);"
						+ " arguments[0].dispatchEvent(evObj);"
						+ "} "
						+ "else if(document.createEventObject){ arguments[0].fireEvent('onmouseover');}  ";

		js.executeScript(mouseOverScript, parentLocator);

		Thread.sleep(1000);

	} catch (Exception e) {
		e.printStackTrace();
		softAssert.fail("Unable to Hover on element using Xpath : "+parentLocator+". Localized Message: "+e.getLocalizedMessage());
	}
	}

	/**
	 * @author Name - Asim Singh
	 * The method used to hover on element using xpath
	 * Return type is void
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @param by
	 *            a locator e.g. xpath, css, name, id, class name etc
	 */
	public void mouseOverOnElementUsingRobot(WebPage webPage,String locator,SoftAssert softAssert) {
		try {
			org.openqa.selenium.Point coordinates = webPage.getDriver().findElement(By.xpath(locator)).getLocation();
			Robot robot = new Robot();
			robot.mouseMove(coordinates.getX(), coordinates.getY() + 60);
			/*
			 * WebDriver provide document coordinates, where as Robot class is
			 * based on Screen coordinates, so I have added +60 to compensate
			 * the browser header. You can even adjust if needed.
			 */

		} catch (AWTException e) {
			e.printStackTrace();
			softAssert.fail("Unable to Hover on element using Xpath : "+locator+". Localized Message: "+e.getLocalizedMessage());
			log.error("Failed to mouseover on the element '" + locator + "'. " + e);
		}
	}


	/**********************************/
	/**
	 * @author Name - Deepak Bhambri
	 * The method used to hover on element using xpath
	 * Return type is void
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public void hoverOnelementbyXpath(WebPage webPage, String locator, SoftAssert softAssert){
		try {
			log.info("Hovering on element using locator : "+locator);
			webPage.waitForWebElement(By.xpath(locator));
			WebElement element = webPage.getDriver().findElement(By.xpath(locator));
			if(!element.isDisplayed()){
				webPage.scrollDown(4);
			}
			webPage.hoverOnElement(By.xpath(locator));
		} catch (Exception e) {
			softAssert.fail("Unable to Hover on element using Xpath : "+locator+". Localized Message: "+e.getLocalizedMessage());
		}
	}

	/**
	 * @author Name - Deepak Bhambri
	 * The method used to get webelement using xpath
	 * Return type is WebElement 
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public WebElement getWebElementbyXpath(WebPage webPage, String locator, SoftAssert softAssert){
		WebElement element = null;
		try{
			log.info("Finding element using xpath :"+locator);
			element=webPage.getDriver().findElement(By.xpath(locator));
		}catch(Exception e){
			softAssert.fail("Unable to find element using Xpath : "+locator+". Localized Message: "+e.getLocalizedMessage());
		}
		return element;
	}

	/**
	 * @author Name - Deepak Bhambri
	 * The method used to get font properties
	 * Return type is list of strings
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public List<String> getFontProperties(WebPage webPage,String locator, SoftAssert softAssert){
		List<String> actualValueList=new ArrayList<String>();
		try{
			actualValueList.add(getCssvaluebyXpath(webPage,locator,"font-size",softAssert));
			actualValueList.add(getCssvaluebyXpath(webPage,locator,"color",softAssert));
			actualValueList.add(getCssvaluebyXpath(webPage,locator,"font-family",softAssert));
			actualValueList.add(getCssvaluebyXpath(webPage,locator,"font-weight",softAssert));
			actualValueList.add(getCssvaluebyXpath(webPage,locator,"background-color",softAssert));
			actualValueList.add(getCssvaluebyXpath(webPage,locator,"text-align",softAssert));
			actualValueList.add(getCssvaluebyXpath(webPage,locator,"text-transform",softAssert));
			actualValueList.add(getCssvaluebyXpath(webPage,locator,"background-image",softAssert));
		}catch(Exception e){
			e.printStackTrace();
			softAssert.fail("Unable to get font properties for locator : "+locator+". Localized Message: "+e.getLocalizedMessage());
		}
		return actualValueList;
	}

	/**
	 * @author Name - Deepak Bhambri
	 * The method used to get current page url
	 * Return type is String
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public String getPageUrl(WebPage webPage, SoftAssert softAssert){
		String actualUrl="";
		try{
			log.info("Getting current page url : "+webPage.getCurrentUrl());
			actualUrl=webPage.getCurrentUrl();
		}catch(Exception e){
			softAssert.fail("Unable to get current page url. Localized Message: "+e.getLocalizedMessage());
		}
		return actualUrl;
	}

	/**
	 * @author Name - Deepak Bhambri
	 * The method used to get current page title
	 * Return type is String
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public String getPageTitle(WebPage webPage, SoftAssert softAssert){
		String actualTitle="";
		try{
			log.info("Getting current page title : "+webPage.getPageTitle());
			actualTitle=webPage.getPageTitle();
		}catch(Exception e){
			softAssert.fail("Unable to get current page title. Localized Message: "+e.getLocalizedMessage());
		}
		return actualTitle;
	}

	/**
	 * @author Name - Madhukar Mandadi
	 * The method used to click element using linkText 
	 * Return type is void
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public void clickElementbyLinkText(WebPage webPage, String LinkName, SoftAssert softAssert){
		try {
			log.info("Clicking on element using Link Name"
					+ " - "+LinkName);
			webPage.findObjectByLink(LinkName).click();
		} catch (PageException e) {
			softAssert.fail("Unable to click on element using Link Name : "+ LinkName+". Localized Message: "+e.getLocalizedMessage());
		}

	}

	/**
	 * @author Name - Asim Singh
	 * The method used to click element using css locator
	 * Return type is void 
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public void clickElementbyCss(WebPage webPage, String locator, SoftAssert softAssert)throws InterruptedException{
		try {
			log.info("Clicking on element using Css - "+locator);
			webPage.findObjectByCss(locator).click();
		} catch (PageException e) {
			softAssert.fail("Unable to click on element using CSS : "+ locator+". Localized Message: "+e.getLocalizedMessage());
		}
	}
	public void clickElementbyCssAndGetCurrentURL(WebPage webPage, String locator, SoftAssert softAssert)throws InterruptedException{
		try {
			log.info("Clicking on element using Css - "+locator);
			webPage.findObjectByCss(locator).click();
			webPage.getCurrentUrl(); //for Safari
		} catch (PageException e) {
			softAssert.fail("Unable to click on element using CSS : "+ locator+". Localized Message: "+e.getLocalizedMessage());
		}
	}
	
	

	/**
	 * @author asim singh
	 * @param webPage
	 * @param locator
	 * @param softAssert
	 */
	public void clearTextBoxByJS(WebPage webPage, String locator,SoftAssert softAssert){
		try{
			log.info("Clearing text box with locator: "+locator);
			JavascriptExecutor js = (JavascriptExecutor)webPage.getDriver();
			js.executeScript("arguments[0].value ='';", getWebElementbyXpath(webPage, locator, softAssert));
		}catch(Exception e){
			softAssert.fail("Unable to clear text box with locator: "+locator+". Localized Message: "+e.getLocalizedMessage());
		}
	}
	
	/**
	 * @author asim singh
	 * @param webPage
	 * @param locator
	 * @param softAssert
	 */
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void compareDropDownValues(WebPage webPage,String FilePath, String Locator) {
		 SoftAssert softAssert = new SoftAssert ();
		try {

			BufferedReader in = new BufferedReader(new FileReader(FilePath));
	        String line;
	        line = in.readLine();
	        in.close();        
			String[] expectedDropDownItemsInArray = line.split(",");

	       // Create expected list :: This will contain expected drop-down values
	        ArrayList expectedDropDownItems = new ArrayList();
	        for(int i=0; i<expectedDropDownItemsInArray.length; i++)
	            expectedDropDownItems.add(expectedDropDownItemsInArray[i]);
			log.info("***************** expectedDropDownItems *************************"+expectedDropDownItems);

			//Create a webelement for the drop-down 
	        WebElement visualizationDropDownElement = webPage.getDriver().findElement(By.xpath(Locator));

	        //Instantiate Select class with the drop-down webelement "
	        Select visualizationDropDown = new Select(visualizationDropDownElement);

	        //Retrieve all drop-down values and store in actual list 
	        List<WebElement> valuesUnderVisualizationDropDown  = visualizationDropDown.getOptions();

	        List<String> actualDropDownItems = new ArrayList();

	        for(WebElement value : valuesUnderVisualizationDropDown){
	            actualDropDownItems.add(value.getText());
	        }

	        // Print expected and actual list
	        log.info("******************** expectedDropDownItems : " +expectedDropDownItems);       
	        log.info("******************** actualDropDownItems   : " +actualDropDownItems);

	        // Verify both the lists having same size
	        if(actualDropDownItems.size() != expectedDropDownItems.size())
	          log.info("Property file is NOT updated with the drop-down values");

	        // Compare expected and actual list
	        for (int i = 0; i < actualDropDownItems.size(); i++) {
	            if (!expectedDropDownItems.get(i).equals(actualDropDownItems.get(i)))
	            log.info("Drop-down values are NOT in correct order");
	           // else log.info("Drop-down values are in correct order");
	            }
		}
		catch (Exception e) {
			softAssert.fail("Drop Down Values Camparison Failure. Please recheck Drop Down Check List  Text File once again : "+". Localized Message: "+e.getLocalizedMessage());
		}
	}
	/**
	 * @author Name - Asim Singh
	 * The method used get text using css locator
	 * Return type is String
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public String getTextbyCss(WebPage webPage, String locator, SoftAssert softAssert){
		String actualText= "";
		try {
			log.info("Getting text by using Css - "+locator);
			actualText = webPage.findObjectByCss(locator).getText();
		} catch (PageException e) {
			softAssert.fail("Unable to get text on element using Css : "+ locator+". Localized Message: "+e.getLocalizedMessage());
		}
		return actualText;
	}

	public String getTextbyJS(WebPage webPage, String locator, SoftAssert softAssert) throws PageException{
		String actualText = "";
		JavascriptExecutor js = (JavascriptExecutor)webPage.getDriver();
		/*String script = "return   document.evaluate('locator').getText();";
	    actualText = ((JavascriptExecutor) webPage.getDriver()).executeScript(script).toString();*/

		WebElement element = webPage.getDriver().findElement(By.xpath(locator));
		actualText = (String)js.executeScript("return arguments[0].getText()", element);
		return actualText;
	}
	/**
	 * @author Name - Asim Singh
	 * The method used get attribute using css
	 * Return type is String
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public String getAttributebyCss(WebPage webPage, String locator, String attribute, SoftAssert softAssert){
		String attributeValue="";
		try {
			log.info("Getting attribute value "+attribute+" for Css : "+locator);
			attributeValue = webPage.findObjectByCss(locator).getAttribute(attribute);
		} catch (PageException e) {
			softAssert.fail("Unable to get Attribute Value : "+attribute+" using css using locator : "+locator+". Localized Message: "+e.getLocalizedMessage());
		}
		return attributeValue;
	}

	/**
	 * @author Name - Deepak Bhambri
	 * The method used to clear text box input
	 * Return type is void
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public void clearTextBox(WebPage webPage, String locator,SoftAssert softAssert){
		try{
			log.info("Clearing text box with locator: "+locator);
			webPage.findObjectByxPath(locator).clear();
		}catch(Exception e){
			softAssert.fail("Unable to clear text box with locator: "+locator+". Localized Message: "+e.getLocalizedMessage());
		}
	}
	
	public static WebElement waitForWebElement(By by, WebPage webPage) throws PageException{
		try{
			log.info("Waiting for web element to be present");
			WebElement element = null;
			element = (new WebDriverWait(webPage.getDriver(), 20)).until(ExpectedConditions.presenceOfElementLocated(by));
			return element;
		}
		catch (Exception e) {
			throw new PageException("Failed to find object using given name, message : " + e.toString());
		}
	}


	public static WebElement waitForWebElement_1(By by, WebPage webPage) throws PageException{
		try{
			log.info("Waiting for web element to be present");
			WebElement element = null;
			element = (new WebDriverWait(webPage.getDriver(), 20)).until(ExpectedConditions.presenceOfElementLocated(by));
			log.info("element found ");
			return element;
		}
		catch (Exception e) {
			throw new PageException("Failed to find object using given name, message : " + e.toString());
		}
	}




	/**
	 * @author Name - Shantanu Kulkarni
	 * The method used to click on link using x-path and return page url
	 * Return type is String	
	 **/
	public String clickElementbyXpathAndGetURL(WebPage webPage, String locator, SoftAssert softAssert) throws InterruptedException{
		try {
			log.info("Clicking on element using xpath - "+locator);
			webPage.findObjectByxPath(locator).click();			
		} catch (PageException e) {
			softAssert.fail("Unable to click on element using Xpath : "+ locator+". Localized Message: "+e.getLocalizedMessage());		
		}
		return webPage.getCurrentUrl();
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
	 * @author Name - Asim Singh
	 * The method used to clear element input box using xpath 
	 * Return type is void
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public void clearElementbyXpath(WebPage webPage, String locator, SoftAssert softAssert){
		try {
			log.info("Clearing object using xpath - "+locator);
			webPage.findObjectByxPath(locator).clear();
		} catch (PageException e) {
			softAssert.fail("Unable to clear on inputbox using Xpath : "+ locator+". Localized Message: "+e.getLocalizedMessage());
		}
	}



	public WebElement findElementByXpath(WebPage webPage, String locator, SoftAssert softAssert) throws InterruptedException{
		WebElement element= null;
		try {
			log.info("Clicking on element using xpath - "+locator);
			element=webPage.getDriver().findElement(By.xpath(locator));			
		} catch (Exception e) {
			e.printStackTrace();
			softAssert.fail("Unable to find  element using Xpath : "+ locator+". Localized Message: "+e.getLocalizedMessage());		
		}
		return element;
	}


	/**
	 * @author Name - Asim Singh
	 * The method used to click on link using x-path and return page url
	 * Return type is String	
	 **/
	public String clickElementbyJSAndGetURL(WebPage webPage, String locator, SoftAssert softAssert) throws InterruptedException{
		JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
		try {
			log.info("Clicking on element using xpath - "+locator);
			WebElement element=webPage.getDriver().findElement(By.xpath(locator));			
			js.executeScript("arguments[0].click();", element);	
		} catch (Exception e) {
			e.printStackTrace();
			softAssert.fail("Unable to click on element using Xpath : "+ locator+". Localized Message: "+e.getLocalizedMessage());		
		}
		return webPage.getCurrentUrl();
	}

	/**
	 * @author Name - Deepak Bhambri
	 * The method is used to wait for given time	
	 **/
	public void waitForGivenTime(int givenTimeinSec,SoftAssert softAssert) throws InterruptedException{
		try {
			log.info("Waiting for given time "+givenTimeinSec+"sec");
			Thread.sleep(givenTimeinSec*1000);			
		} catch (Exception e) {
			softAssert.fail("Unable to wait for given time "+givenTimeinSec+" sec. Localized Message: "+e.getLocalizedMessage());			
		}
	}

	public static void waitForGivenTime(int givenTimeinSec) throws InterruptedException{
		try {
			log.info("Waiting for given time "+givenTimeinSec+"sec");
			Thread.sleep(givenTimeinSec*1000);			
		} catch (Exception e) {
			//softAssert.fail("Unable to wait for given time "+givenTimeinSec+" sec. Localized Message: "+e.getLocalizedMessage());			
		}
	}
	/**
	 * @author Name - Madhukar
	 * get webelements in to a list and return the list to a calling method
	 **/
	public List<WebElement> getWebElementsbyXpath(WebPage webPage, String locator, SoftAssert softAssert){
		List<WebElement> element = null;
		try{
			log.info("Finding element using xpath :"+locator);
			element=webPage.getDriver().findElements(By.xpath(locator));

		}catch(Exception e){
			softAssert.fail("Unable to find element using Xpath : "+locator+". Localized Message: "+e.getLocalizedMessage());
		}
		return element;
	}

	/**
	 * @author Name - Madhukar
	 * selects dropdown value
	 **/
	public void selectDropdownByValue(WebPage webPage, String locator,String dropdownvalue ,SoftAssert softAssert) {
		try {
			WebElement web=webPage.getDriver().findElement(By.xpath(locator));
			Select select=new Select(web);
			select.selectByValue(dropdownvalue);
		} catch (Throwable e) {
			softAssert.fail("Unable to click on element using XPath : "+ locator+". Localized Message: "+e.getLocalizedMessage());
		}
	}


	public void selectDropdownByText(WebPage webPage, String locator,String dropdownvalue ,SoftAssert softAssert) {
		try {
			WebElement web=webPage.getDriver().findElement(By.xpath(locator));
			Select select=new Select(web);
			select.selectByVisibleText(dropdownvalue);
		} catch (Throwable e) {
			softAssert.fail("Unable to click on element using XPath : "+ locator+". Localized Message: "+e.getLocalizedMessage());
		}
	}


	public void selectDropdownByValue(WebPage webPage, String locator,String dropdownvalue) {
		try {
			log.info("Selecting dropdown value - "+dropdownvalue);
			WebElement web=webPage.getDriver().findElement(By.xpath(locator));
			Select select=new Select(web);
			select.selectByValue(dropdownvalue);
		} catch (Throwable e) {
			Assert.fail("Unable to click on element using XPath : "+ locator+". Localized Message: "+e.getLocalizedMessage());
		}
	}

	/**
	 * @author Name - Shantanu Kulkarni
	 * The method is used to get broken images	
	 **/	
	public void verifyBrokenImage(WebPage webPage) {
		 SoftAssert softAssert = new SoftAssert ();
		List<WebElement> imagesList = webPage.getDriver().findElements(By.tagName("img"));
		log.info("Total number of images : " + imagesList.size());
		int imageCount = 0;
		List<Integer> brokenImageNumber = new ArrayList<Integer>();
		List<String> brokenImageSrc = new ArrayList<String>();
		for (WebElement image : imagesList) {
			if (!image.getAttribute("src").contains("http://bat.bing.com/")) {
				try {
					imageCount++;
					log.info("Verifying image number : " + imageCount);
					HttpClient client = HttpClientBuilder.create().build();
					HttpGet request = new HttpGet(image.getAttribute("src"));
					HttpResponse response = client.execute(request);
					if (response.getStatusLine().getStatusCode() == 200
							|| response.getStatusLine().getStatusCode() == 451) {
						log.info("Image number " + imageCount + " is as expected "
								+ response.getStatusLine().getStatusCode());
					} else {
						brokenImageNumber.add(imageCount);
						brokenImageSrc.add(image.getAttribute("src"));
						log.info("Image number " + imageCount + " is not as expected "
								+ response.getStatusLine().getStatusCode());
						log.info("Broken Image source is : " + image.getAttribute("src"));
					}
				} catch (Exception e) {
					log.info("Image number ....." + imageCount + " is not as expected ");
					brokenImageNumber.add(imageCount);
					brokenImageSrc.add(image.getAttribute("src"));
					log.info("imageCount  : " + imageCount + " : " + brokenImageSrc);
				}
			}
		}
		if (brokenImageNumber.size() > 0) {
			softAssert.fail("Broken Image number : " + Arrays.deepToString(brokenImageNumber.toArray())
			+ "\nImage source of the Broken image : " + Arrays.deepToString(brokenImageSrc.toArray()));
		}
	}

	/**
	 * @author Name - Shantanu Kulkarni
	 * The method is used to get broken links	
	 **/	
	public void verifyBrokenLinks(WebPage webPage) {
		List<WebElement> linkList = webPage.getDriver().findElements(By.tagName("a"));
		log.info("Total number of links : " + linkList.size());
		int linkCount = 0;
		List<Integer> brokenLinkNumber = new ArrayList<Integer>();
		List<String> brokenLinkHref = new ArrayList<String>();
		for (WebElement link : linkList) 		
		{
			try {
				linkCount++;				
				log.info("Verifying link number : " + linkCount);
				HttpClient client = HttpClientBuilder.create().build();
				HttpGet request = new HttpGet(link.getAttribute("href"));
				HttpResponse response = client.execute(request);
				if (response.getStatusLine().getStatusCode() == 200) {
					log.info("Link number " + linkCount + " is as expected "
							+ response.getStatusLine().getStatusCode());
				} else {
					brokenLinkNumber.add(linkCount);
					brokenLinkHref.add(link.getAttribute("href"));//Nalini
					log.info("Link number " + linkCount + " is not as expected "
							+ response.getStatusLine().getStatusCode());
					log.info("Broken Link source is : " + link.getAttribute("href"));

				}
			} catch (Exception e) {
				log.info("Link number ....." + linkCount + " is not as expected ");
				brokenLinkNumber.add(linkCount);
				brokenLinkHref.add(link.getAttribute("href"));//nalini
				log.info("linkCount  : " + linkCount + " : " + brokenLinkHref);			
			}

		}
		if (brokenLinkNumber.size() > 0) {

			Assert.fail("Link number of the broken link : " + Arrays.deepToString(brokenLinkNumber.toArray())
			+ "\nLink (href) of the broken link : " + Arrays.deepToString(brokenLinkHref.toArray()));

		}

	}

	public void verifyBrokenLinksForGivenLinks(WebPage webPage,List<WebElement> linkList) {
		log.info("Total number of links : " + linkList.size());
		int linkCount = 0;
		List<Integer> brokenLinkNumber = new ArrayList<Integer>();
		List<String> brokenLinkHref = new ArrayList<String>();
		for (WebElement link : linkList) 		
		{
			try {
				linkCount++;				
				log.info("Verifying link number : " + linkCount);
				HttpClient client = HttpClientBuilder.create().build();
				HttpGet request = new HttpGet(link.getAttribute("href"));
				HttpResponse response = client.execute(request);
				if (response.getStatusLine().getStatusCode() == 200) {
					log.info("Link number " + linkCount + " is as expected "
							+ response.getStatusLine().getStatusCode());
				} else {
					brokenLinkNumber.add(linkCount);
					brokenLinkHref.add(link.getAttribute("href"));//Nalini
					log.info("Link number " + linkCount + " is not as expected "
							+ response.getStatusLine().getStatusCode());
					log.info("Broken Link source is : " + link.getAttribute("href"));

				}
			} catch (Exception e) {
				log.info("Link number ....." + linkCount + " is not as expected ");
				brokenLinkNumber.add(linkCount);
				brokenLinkHref.add(link.getAttribute("href"));//nalini
				log.info("linkCount  : " + linkCount + " : " + brokenLinkHref);			
			}

		}
		if (brokenLinkNumber.size() > 0) {

			Assert.fail("Link number of the broken link : " + Arrays.deepToString(brokenLinkNumber.toArray())
			+ "\nLink (href) of the broken link : " + Arrays.deepToString(brokenLinkHref.toArray()));

		}

	}
	/**
	 * @author Name - Rajesh Surve
	 * The method used to click element using Java script executor 
	 */
	public void clickElementbyXpath_usingJavaScript(WebPage webPage, String locator, SoftAssert softAssert){
		try {
			log.info("Clicking on element using xpath - "+locator);
			WebElement element=webPage.getDriver().findElement(By.xpath(locator));
			JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
			js.executeScript("arguments[0].click();", element);

		} catch (Exception e) {
			softAssert.fail("Unable to click on element using JavaScriptExecutor : "+ locator+". Localized Message: "+e.getLocalizedMessage());
		}
	}

	/**
	 * @author Name - Rajesh Surve
	 * The method used to click element using Java script executor 
	 */	

	public static void sendKeys_usingJS(WebPage webPage, String locator, String text){
		try {
			log.info("Clicking on element using xpath - "+locator);
			WebElement element=webPage.getDriver().findElement(By.xpath(locator));
			JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
			js.executeScript("arguments[0].setAttribute('text')", element);
		} catch (Exception e) {
			Assert.fail();
		}	

	}

	public  void sendKeys_usingJavaScriptExecutor(WebPage webPage, String locator, String text, SoftAssert softAssert){
		try {
			log.info("Clicking on element using xpath - "+locator);
			WebElement element=webPage.getDriver().findElement(By.xpath(locator));
			JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
			js.executeScript("arguments[0].setAttribute('text')", element);
		} catch (Exception e) {
			softAssert.fail("Unable to click on element using JavaScriptExecutor : "+ locator+". Localized Message: "+e.getLocalizedMessage());
		}	

	}

	/**
	 * @author Name - Shantanu Kulkarni
	 * The method is used to click on element with JS	
	 **/	
	public String Click_On_Element_JS(WebPage webPage, String xpath, SoftAssert softAssert) throws InterruptedException {
		JavascriptExecutor executor = (JavascriptExecutor) webPage.getDriver();
		try {
			//WebElement element = webPage.findObjectByxPath(test).getWebElement();
			log.info("Clicking on web element by Java Script");
			WebElement element = getWebElementbyXpath(webPage, xpath, softAssert);			
			executor.executeScript("arguments[0].click();", element);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
		return webPage.getCurrentUrl();
	}

	/**
	 * Method to wait for page load
	 * @author sjadhav
	 * @param webPage
	 * @param softAssert
	 * @throws InterruptedException
	 */
	public void waitForPageLoad(WebPage webPage, SoftAssert softAssert) throws InterruptedException
	{
		WebDriver driver = webPage.getDriver();
		int count =0;
		Thread.sleep(2000);
		log.info("Waiting for page to complete loading");
		while(count<120)
		{
			try{
				if(!((JavascriptExecutor)driver).executeScript("return document.readyState").toString().contains("complete"))
				{
					Thread.sleep(1000);
					count++;
				}
				else{
					log.info("Page load complete");
					break;
				}

			}catch(Exception NullPointerException)
			{
				Thread.sleep(1000);
				count++;
			}
		}
		if(count>=30)
		{
			softAssert.fail("Unable to complete page load, Took more than 120 sec to load page");
		}
	}

	/**
	 * @author sjadhav
	 * The method used to get text using ID
	 * Return type is String 
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public String getTextbyId(WebPage webPage, String locator, SoftAssert softAssert){
		String actualText= "";
		try {
			log.info("Getting text by using xpath - "+locator);
			actualText = webPage.findObjectById(locator).getText();
			log.info("Actual text - "+actualText);
		} catch (PageException e) {
			softAssert.fail("Unable to Get Text on element using Xpath : "+ locator+". Localized Message: "+e.getLocalizedMessage());
		}
		return actualText;
	}

	/**
	 * @author sjadhav
	 * The method used to get webelement using ID
	 * Return type is WebElement 
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public WebElement getWebElementbyID(WebPage webPage, String locator, SoftAssert softAssert){
		WebElement element = null;
		try{
			log.info("Finding element using id :"+locator);
			element=webPage.getDriver().findElement(By.id(locator));
		}catch(Exception e){
			softAssert.fail("Unable to find element using Xpath : "+locator+". Localized Message: "+e.getLocalizedMessage());
		}
		return element;
	}

	/**
	 * @author sjadhav
	 * @param webPage
	 * @param locator
	 * @param softAssert
	 */
	public void clearTextBoxById(WebPage webPage, String locator,SoftAssert softAssert){
		try{
			log.info("Clearing text box with locator: "+locator);
			webPage.findObjectById(locator).clear();
		}catch(Exception e){
			softAssert.fail("Unable to clear text box with locator: "+locator+". Localized Message: "+e.getLocalizedMessage());
		}
	}
	/**
	 * 
	 * @param webPage
	 * @param locator
	 * @param softAssert
	 */
	public void clearTextBoxByXpath(WebPage webPage, String locator,SoftAssert softAssert){
		try{
			log.info("Clearing text box with locator: "+locator);
			webPage.findObjectByxPath(locator).clear();
		}
		catch(InvalidElementStateException e){
		}catch(Exception e){
			softAssert.fail("Unable to clear text box with locator: "+locator+". Localized Message: "+e.getLocalizedMessage());
		}
	}

	/**
	 * @author Name - Deepak Bhambri
	 * The method used to enter keys using xpath
	 * Return type is void
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public void sendKeysById(WebPage webPage, String locator, String text, SoftAssert softAssert){
		try{
			log.info("Entering keys "+text+" ");
			webPage.findObjectById(locator).sendKeys(text);
		}catch(Exception e){
			softAssert.fail("Unable to Enter Keys : "+text+" using locator : "+locator+". Localized Message: "+e.getLocalizedMessage());
		}
	}
	/**
	 * Method to get data from excel sheet in a Key Value pair HashMap
	 * @param fileP
	 * @param sheetName
	 * @param dataKey
	 * @return
	 */
	public static LinkedHashMap<String, String> getDataInHashMap(String filePath,String sheetName, String dataKey) {
		LinkedHashMap<String, String> testData = new LinkedHashMap<String, String>();
		try {

			String[][] testDataArray = ExcelUtil.readExcelData(filePath, sheetName, dataKey);
			for (int i = 0; i < testDataArray.length; i++) {
				testData.put(testDataArray[i][0], testDataArray[i][1]);
			}
		} catch (Exception e) {
			log.error(
					" Failed to read excel data by data key  and store in linked hash map due to :::" + e.getMessage());
			e.printStackTrace();
		}
		return testData;
	}


	/**
	 * @author sjadhav
	 * The method used to click element using ID 
	 * Return type is void
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public void clickElementById(WebPage webPage, String locator, SoftAssert softAssert){
		try {
			log.info("Clicking on element using xpath - "+locator);
			webPage.findObjectById(locator).click();
		} catch (PageException e) {
			softAssert.fail("Unable to click on element using Xpath : "+ locator+". Localized Message: "+e.getLocalizedMessage());
		}
	}

	/**
	 * @author Name - Deepak Bhambri
	 * The method used to get list of webelement by xpath
	 * Return type is list of webelement
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public List<WebElement> findElementsByXpath(WebPage webPage, String locator,SoftAssert softAssert){
		log.info("Finding multiple elements by x-path: "+locator);
		List<WebElement> elementList= new ArrayList<WebElement>();
		try {
			waitForWebElement(By.xpath(locator), webPage);
			elementList = webPage.getDriver().findElements(By.xpath(locator));
		} catch (Exception e) {
			softAssert.fail("Unable to find multiple elements using xpath: "+locator+". Localized Message: "+e.getLocalizedMessage());
		}
		return elementList;
	}

	public void sendKeysbyXpath(WebPage webPage, String locator, String text){
		try{
			log.info("Entering keys "+text+" ");
			webPage.findObjectByxPath(locator).sendKeys(text);
		}catch(Exception e){
			Assert.fail("Unable to Enter Keys : "+text+" using locator : "+locator+". Localized Message: "+e.getLocalizedMessage());
		}
	}


	public void verifyLabels(WebPage webPage, SoftAssert softAssert, String[][] labelsData) throws PageException
	{
		for(int i = 0;i<labelsData.length;i++)
		{
			//label verification
			String actualContent = webPage.findObjectByxPath(labelsData[i][0]).getText();
			//String actualContent = commonMethods.getTextbyXpath(webPage,Review_Data[i][0], softAssert);
			log.info("Actual:  " + actualContent + " "
					+ "  Expected: " + labelsData[i][1]);
			softAssert.assertTrue(actualContent.contains(labelsData[i][1]),
					"Expected Content: " + labelsData[i][1] + "  Failed to Match Actual: " + actualContent);
		}	
	}

	public void verifyLabelsErrorColor(WebPage webPage, SoftAssert softAssert, String[][] labelsErrorColorCodeData) throws PageException
	{
		webPage.findObjectByxPath(".//*[@class='pr-footer']/div[@type='submit']").click();
		//String expectedColorCode = "#d00";
		String expectedColorCode ="221, 0, 0, 1";
		for(int i = 0;i<labelsErrorColorCodeData.length;i++)
		{
			String actualColorCode = webPage.findObjectByxPath(labelsErrorColorCodeData[i][0]).getCssValue("color")
					.replace("rgba(", "").replace(")", "").replace("rgb(", "");
			log.info("Actual Error Color Code:  " + actualColorCode + " "
					+ "  Expected Code: " +expectedColorCode);
			softAssert.assertTrue(expectedColorCode.contains(actualColorCode),
					"Expected Color Code: " + expectedColorCode + "  Failed to Match Actual: " + actualColorCode);
		}	
	}



	public void verifyDate(WebPage webPage, SoftAssert softAssert, String dateXpath) throws Exception {
		Date date = new Date();
		String DATE_FORMAT = "MM/dd/yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
		String System_Date = simpleDateFormat.format(date);
		System.out.println("Today is : " + System_Date);
		String Actual_Date = webPage.findObjectByxPath(dateXpath).getText();
		softAssert.assertTrue(Actual_Date.contains(System_Date),
				"expectedContent: " + System_Date + "  Failed to Match Actual:" + Actual_Date);

	}

	public void fillFormWithOutJS(WebPage webPage, SoftAssert softAssert, String[][] FieldData) {
		int dataLength = FieldData.length;
		String testType = TestBedManagerConfiguration.INSTANCE.getTestTypes()[0];
		for (int i = 0; i < dataLength; i++) {
			try {
				switch (FieldData[i][1]) {
				case "textField":
					if (testType.equalsIgnoreCase("Mobile")
							&& FieldData[i][2].equalsIgnoreCase(".//*[@id='applicant:middle-initial']"))
						break;
					verifyTextFieldIsEditableByXpathWithOutJS(webPage, softAssert, FieldData[i][0], FieldData[i][2], FieldData[i][3]);
					break;
				case "dropDown":
					verifyDropDownFieldIsEditableByXpath(webPage, softAssert, FieldData[i][0], FieldData[i][2], FieldData[i][3]);
					break;
				case "radio":
					selectRadioButtonByXpath(webPage, softAssert, FieldData[i][0], FieldData[i][2]);
					break;
				case "checkBox":
					selectCheckBoxByXpath(webPage, softAssert, FieldData[i][0], FieldData[i][2]);
					break;
				case "button":
					selectButtonByXpath(webPage, softAssert, FieldData[i][0], FieldData[i][2]);
					break;
				default:
					softAssert.fail("Invalid Data in datasheet. FieldType is not set as expected. Current value is : "
							+ FieldData[i][1]);
				}

			} catch (Throwable e) {
				softAssert.fail("Failed to set value in " + FieldData[i][1] + "  \"" + FieldData[i][0] + "\" Due to :"
						+ e.getLocalizedMessage());
			}
		}
	}

	public boolean verifyTextFieldIsEditableByXpathWithOutJS(WebPage webPage, SoftAssert softAssert, String FieldName, String locator,
			String newValue) {
		if (!verifyElementisPresent(webPage, locator, softAssert)) {
			log.info("TextBox \"" + FieldName + "\" is Not Displayed");
			softAssert.fail(" Text Field \"" + FieldName + "\" is not Displayed ");
			return false;
		} else {
			if (newValue == "" || newValue == null) {
				log.info("Value was passed as blank for textField " + FieldName);
				return true;
			}
			if (getWebElementbyXpath(webPage, locator, softAssert).isEnabled()) {
				sendKeysbyXpath(webPage, locator, newValue, softAssert);
			} else {
				softAssert.fail("TextBox \"" + FieldName + "\" is Disabled ");
			}
		}
		return false;
	}

	/**
	 * Verifies if dropdown Value is editable using xPath
	 * 
	 * @author sjadhav
	 * @param softAssert
	 * @param FieldName
	 * @param locator
	 * @param newValue
	 */
	public void verifyDropDownFieldIsEditableByXpath(WebPage webPage,SoftAssert softAssert, String FieldName, String locator,
			String newValue) {
		if (getWebElementbyXpath(webPage, locator, softAssert).isEnabled()) {
			log.info("DropDown is enabled");
			log.info("Setting DropDown Value to : " + newValue);
			selectValueFromDropDownByXpath(webPage,softAssert, FieldName, locator, newValue);
		} else {
			log.info("DropDown is Disabled");
			softAssert.fail(" DropDown Field " + FieldName + " is disabled ");
		}
	}


	/**
	 * Select Check Radio Button using xpath
	 * 
	 * @author sjadhav
	 * @param softAssert
	 * @param FieldName
	 * @param locator
	 */
	public void selectRadioButtonByXpath(WebPage webPage,SoftAssert softAssert, String FieldName, String locator) {
		if (getWebElementbyXpath(webPage, locator, softAssert).isEnabled()) {
			/*if(TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedNames.get(Thread.currentThread().getId())).getDevice().getName().toLowerCase().contains("nexus"))
			scrollToElement(locator,softAssert);*/
			log.info("Selecting Radio button : \"" + FieldName + "\"");
			//commonMethods.clickElementbyXpath(webPage, locator, softAssert);
			JavascriptExecutor jse = (JavascriptExecutor)webPage.getDriver();
			jse.executeScript("arguments[0].click()", getWebElementbyXpath(webPage, locator, softAssert));
		} else {
			log.info("RadioButton is Disabled");
			softAssert.fail(" RadioButton Field " + FieldName + " is disabled ");
		}
	}

	/**
	 * Select Check Box using xpath
	 * 
	 * @author sjadhav
	 * @param softAssert
	 * @param FieldName
	 * @param locator
	 */

	public void selectCheckBoxByXpath(WebPage webPage,SoftAssert softAssert, String FieldName, String locator) {
		if (getWebElementbyXpath(webPage, locator, softAssert).isEnabled()) {
			/*if(TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedNames.get(Thread.currentThread().getId())).getDevice().getName().toLowerCase().contains("nexus"))
			scrollToElement(locator,softAssert);*/
			log.info("Selecting CheckBox : \"" + FieldName + "\"");
			JavascriptExecutor jse = (JavascriptExecutor)webPage.getDriver();
			jse.executeScript("arguments[0].click()", getWebElementbyXpath(webPage, locator, softAssert));
		} else {
			log.info("CheckBox is Disabled");
			softAssert.fail(" CheckBox Field " + FieldName + " is disabled ");
		}
	}

	/**
	 * Select Button using xpath
	 * 
	 * @author sjadhav
	 * @param softAssert
	 * @param FieldName
	 * @param locator
	 */
	public void selectButtonByXpath(WebPage webPage, SoftAssert softAssert, String FieldName, String locator) {
		if (getWebElementbyXpath(webPage, locator, softAssert).isEnabled()) {
			log.info("Clicking on Button : \"" + FieldName + "\"");
			JavascriptExecutor jse = (JavascriptExecutor)webPage.getDriver();
			jse.executeScript("arguments[0].click()", getWebElementbyXpath(webPage, locator, softAssert)); 
		} else {
			log.info("Button is Disabled");
			softAssert.fail(" Button Field " + FieldName + " is disabled ");
		}
	}	
	/**
	 * Select value from drop down menu using xpath
	 * 
	 * @author sjadhav
	 * @param softAssert
	 * @param FieldName
	 * @param locator
	 * @param newValue
	 */
	public void selectValueFromDropDownByXpath(WebPage webPage,SoftAssert softAssert, String FieldName, String locator,
			String newValue) {
		log.info("Setting DropDown \"" + FieldName + "\" Value to : " + newValue);
		Select select = new Select(getWebElementbyXpath(webPage, locator, softAssert));
		select.selectByVisibleText(newValue.trim());
		String actual = select.getFirstSelectedOption().getText().trim();
		softAssert.assertTrue(actual.equals(newValue),
				"Failed to Update Drop Down Value, New Value : " + newValue + " Existing Value : " + actual);
	}	

	public String getTextFromHiddenElement(WebPage webPage,SoftAssert softAssert,String locator)
	{	String text = null;
	JavascriptExecutor executor = (JavascriptExecutor) webPage.getDriver();
	text = (String) executor.executeScript("return arguments[0].innerHTML", getWebElementbyXpath(webPage, ".//*[@id='mCSB_1_container']/p[6]", softAssert));
	return text;


	}
	public void selectCheckBox(WebPage webPage, String locator, SoftAssert softAssert) {
		WebElement element = webPage.getDriver().findElement(By.xpath(locator));

		if(!element.isSelected())
		{	
			element.click();
			System.out.println("Clicking on Radio Button");
		}
		else{
			System.out.println("Radio Button is already selected");
		}

	}
	public int verifyUrlStatusCode(String url) throws ClientProtocolException, IOException
	{
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		HttpResponse response = client.execute(request);
		int statusCode = response.getStatusLine().getStatusCode();
		return statusCode;
	}

	/**
	 * Method to scroll up the screen for android device
	 */
	public void screenScrollUp(WebPage webPage) {
		try {
			AndroidDriver driver = (AndroidDriver) webPage.getDriver();
			org.openqa.selenium.Dimension windowSize = driver.manage().window().getSize();

			driver.swipe((int) ((windowSize.width)*0.50), (int) ((windowSize.height)*0.85),
					(int) ((windowSize.width)*0.50), (int) ((windowSize.height)*0.30), 1000);
			log.info("Completed scrolling up the screen ");
			// isElementInvisible(commonElements.get("RefreshLoadingIcon"));
		} catch (Exception e) {
			log.error("Failed to scroll up the device screen due to :::" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Scrolls down the Page.
	 */
	public void scrollDown(WebPage webPage,int count) {
		JavascriptExecutor jse = (JavascriptExecutor) webPage.getDriver();
		for (int i = 0; i < count; i++)
			jse.executeScript("window.scrollBy(0,-240)", "");
	}
	
	


	public void scrollUp(WebPage webPage,int count) {
		JavascriptExecutor jse = (JavascriptExecutor) webPage.getDriver();
		for (int i = 0; i < count; i++)
			jse.executeScript("window.scrollBy(0,240)", "");
	}

	/**
	 * Method to delete cookies form web browser
	 * @param webPage
	 */

	public void deleteCookies(WebPage webPage)
	{
		try{
			webPage.getDriver().manage().deleteAllCookies();
			log.info("browser history cleared");
		}catch(Throwable e){
			log.info("Unable to delete cookies for current browser.");
		}
	}

	/**
	 * to verify http status code of a link(200,451) and href value
	 * @param webPage
	 * @param linkName
	 * @param xpath
	 * @param expectedHref
	 * @param softAssert
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void verifyLinkStatusCodeAndHrefValue(WebPage webPage,String linkName,String xpath,String expectedHref, SoftAssert softAssert) throws ClientProtocolException, IOException
	{
		WebElement element = getWebElementbyXpath(webPage, xpath, softAssert);
		String elementUrl=element.getAttribute("href");			
		log.info(linkName+" Actual : "+elementUrl+" Expected : "+expectedHref);
		softAssert.assertTrue(elementUrl.contains(expectedHref),"Expected Url : "+expectedHref+" Actual : "+elementUrl);

		log.info("Verifying Status Code for Link : " + linkName+" Url : "+elementUrl);
		int statusCode = verifyUrlStatusCode(element.getAttribute("href"));
		if (statusCode == 200) {
			log.info("Status code for Link : "+linkName+ " is "+statusCode);
		}
		else{
			softAssert.fail("Status Code for link "+ linkName + "With Url :" + expectedHref +" is "+statusCode);
		}
	}
	/**
	 * Method to sign out from Conns
	 * @param webPage
	 * @param softAssert
	 * @throws InterruptedException
	 */
	public void logOutFromConns(WebPage webPage, SoftAssert softAssert) throws InterruptedException {
		clickElementbyXpath(webPage, "(//*[@id='slide-nav']//a/img)[1]", softAssert);
		Thread.sleep(2000);
		clickElementbyXpath(webPage, "//*[@id='account-welcome']", softAssert);
		Thread.sleep(2000);
		clickElementbyXpath(webPage, "(//*[@id='account-menu']//a)[4]", softAssert);
	}

	public void scrollToElement(WebPage webPage, String xpath,SoftAssert softAssert) {
		log.info("Scrolling to element");
		org.openqa.selenium.WebElement elementToScroll = getWebElementbyXpath(webPage, xpath, softAssert);
		((JavascriptExecutor) webPage.getDriver()).executeScript("arguments[0].scrollIntoView();", elementToScroll);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void resetAPP(String deviceUDID) throws IOException {
	
		try{
			log.info("Resetting App and clearing background instance");
		//	Process p	= Runtime.getRuntime().exec("adb -s "+deviceUDID+" shell pm clear com.android.chrome");
			log.info("Done");
		}catch(Throwable t) 
		{
			log.info("Unable to reset App or Clear background instance");
		}
	}
	
	public void waitIfBrowserIsIos(SoftAssert softAssert,int duration,String browserName) throws InterruptedException
	{
		if((browserName.equalsIgnoreCase("Safari")||browserName.contains("iPhone")||browserName.contains("iPad"))){
			log.info("Sleep for "+duration+" seconds to complete page load on Safari and iOS browser");
			waitForGivenTime(duration, softAssert);
		}

	}
	
	public void waitForPageLoadWithOutJS(WebPage webPage,String currentUrl, SoftAssert softAssert) throws InterruptedException {
		int count=0;
		log.info("Current Url is : "+currentUrl);
		while(currentUrl.equals(webPage.getCurrentUrl())&&count<12)
		{
			log.info("Page not loaded completly, script will wait for some more time");
			waitForGivenTime(10, softAssert);
			count++;
			if(count>=12)
			{
				log.info("Page load took more than 120 sec to load.");
				Assert.fail("Page took more than 120 sec to load : URL :"+webPage.getCurrentUrl());
			}
		}

	}
	
	
	
	public boolean verifyElementisSelected(WebPage webPage, String locator, SoftAssert softAssert){
		Boolean isElementSelected=false;
		try{
			log.info("Verifying if element is selected by locator - "+locator);
			isElementSelected = webPage.findObjectById(locator).isSelected();
		}catch(Throwable e){
			log.info("Element not selected using locator: "+locator+". Localized Message: "+e.getLocalizedMessage());
		}
		return isElementSelected;
			
	}
	
	public void selectCheckBoxById(WebPage webPage, String locator, SoftAssert softAssert) {
		WebElement element = webPage.getDriver().findElement(By.id(locator));

		if(!element.isSelected())
		{	
			element.click();
			System.out.println("Clicking on Radio Button");
		}
		else{
			System.out.println("Radio Button is already selected");
		}

	}
}