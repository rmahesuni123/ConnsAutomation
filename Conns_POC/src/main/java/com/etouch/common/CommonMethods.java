package com.etouch.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.webui.selenium.WebPage;

public class CommonMethods {

	static Log log = LogUtil.getLog(CommonMethods.class);
	
	
	/**
	 * @author Name - Deepak Bhambri
	 * The method used to navigate to page
	 * Return type is String
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws PageException  If an input or output exception occurred
	 **/
	public void navigateToPage(WebPage webPage,String navigatingUrl, SoftAssert softAssert){
		try {
			log.info("Navigating to URL: "+navigatingUrl);
			webPage.loadPage(navigatingUrl);
		} catch (Throwable e) {
			softAssert.fail("Unable to Navigate to URL: "+navigatingUrl+". Localized Message: "+e.getLocalizedMessage());
		}
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
			softAssert.fail("Element not visible using locator: "+locator+". Localized Message: "+e.getLocalizedMessage());
		}
		return isElementPresent;
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
			/*log.info(webPage.getDriver().getPageSource());
			if(webPage.getDriver().findElement(By.xpath("//*[@class='modal-footer']//button")).isDisplayed()){
				JavascriptExecutor executor = (JavascriptExecutor)webPage.getDriver();
				WebElement abc = webPage.getDriver().findElement(By.xpath("//*[@class='modal-footer']//button"));
				executor.executeScript("arguments[0].click();", abc);
				Thread.sleep(2000);
				log.info("Location pop-up handled");
			}*/
		}catch(Exception e){
			log.info("Location pop-up not present for this browser.");
		}
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
			webPage.findObjectByxPath(locator).click();
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
	 * @throws PageException  If an input or output exception occurred
	 **/
	public void clickElementbyXpath(WebPage webPage, String locator, SoftAssert softAssert){
		try {
			log.info("Clicking on element using xpath - "+locator);
			webPage.findObjectByxPath(locator).click();
		} catch (PageException e) {
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
			log.info("Actual text - "+actualText);
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
	public String getCssvaluebyXpath(WebPage webPage, String locator, String cssName, SoftAssert softAssert){
		String cssValue="";
		try {
			log.info("Getting CSS value "+cssName+" for Xpath : "+locator);
			webPage.waitForWebElement(By.xpath(locator));
			cssValue = webPage.findObjectByxPath(locator).getCssValue(cssName);
			if(cssName.equalsIgnoreCase("color")||cssName.equalsIgnoreCase("background-color")){
				cssValue=Color.fromString(cssValue).asHex();
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
		}catch(Exception e){
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
	public void clickElementbyCss(WebPage webPage, String locator, SoftAssert softAssert){
		try {
			log.info("Clicking on element using Css - "+locator);
			webPage.findObjectByCss(locator).click();
		} catch (PageException e) {
			softAssert.fail("Unable to click on element using CSS : "+ locator+". Localized Message: "+e.getLocalizedMessage());
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
			WebElement element = null;
			element = (new WebDriverWait(webPage.getDriver(), 20)).until(ExpectedConditions.presenceOfElementLocated(by));
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
			log.info("Clicking on element using xpath - "+locator);
			webPage.findObjectByxPath(locator).clear();
		} catch (PageException e) {
			softAssert.fail("Unable to clear on inputbox using Xpath : "+ locator+". Localized Message: "+e.getLocalizedMessage());
		}
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
			log.info("Selecting dropdown value - "+dropdownvalue);

			WebElement web=webPage.getDriver().findElement(By.xpath(locator));
			Select select=new Select(web);
			select.selectByVisibleText(dropdownvalue);

		} catch (Throwable e) {
			softAssert.fail("Unable to click on element using XPath : "+ locator+". Localized Message: "+e.getLocalizedMessage());
		}
	}
	
	/**
	 * @author Name - Shantanu Kulkarni
	 * The method is used to get broken images	
	 **/	
	public void verifyBrokenImage(WebPage webPage) {
		List<WebElement> imagesList = webPage.getDriver().findElements(By.tagName("img"));
		log.info("Total number of images : " + imagesList.size());
		int imageCount = 0;
		List<Integer> brokenImageNumber = new ArrayList<Integer>();
		List<String> brokenImageSrc = new ArrayList<String>();
		for (WebElement image : imagesList) 		
		{
			try {
				imageCount++;				
				log.info("Verifying image number : " + imageCount);
				HttpClient client = HttpClientBuilder.create().build();
				HttpGet request = new HttpGet(image.getAttribute("src"));

				HttpResponse response = client.execute(request);
				//log.info("src : " + image.getAttribute("src"));
				//log.info("response.getStatusLine().getStatusCode() : " + response.getStatusLine().getStatusCode());
				if (response.getStatusLine().getStatusCode() == 200||response.getStatusLine().getStatusCode() == 451) {
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
		if (brokenImageNumber.size() > 0) {

			Assert.fail("Image number of the broken images : " + Arrays.deepToString(brokenImageNumber.toArray())
			+ " -- Image source of the broken images : " + Arrays.deepToString(brokenImageSrc.toArray()));

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
				//log.info("src : " + image.getAttribute("src"));
				//log.info("response.getStatusLine().getStatusCode() : " + response.getStatusLine().getStatusCode());
				if (response.getStatusLine().getStatusCode() == 200) {
					log.info("Link number " + linkCount + " is as expected "
							+ response.getStatusLine().getStatusCode());
				} else {
					brokenLinkNumber.add(linkCount);
					brokenLinkHref.add(link.getAttribute("src"));
					log.info("Link number " + linkCount + " is not as expected "
							+ response.getStatusLine().getStatusCode());
					log.info("Broken Link source is : " + link.getAttribute("href"));

				}
			} catch (Exception e) {
				log.info("Link number ....." + linkCount + " is not as expected ");
				brokenLinkNumber.add(linkCount);
				brokenLinkHref.add(link.getAttribute("src"));
				log.info("linkCount  : " + linkCount + " : " + brokenLinkHref);			
			}

		}
		if (brokenLinkNumber.size() > 0) {

			Assert.fail("Link number of the broken links : " + Arrays.deepToString(brokenLinkNumber.toArray())
			+ " -- Link href of the broken links : " + Arrays.deepToString(brokenLinkHref.toArray()));

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
			//driver.executeScript("arguments[0].setAttribute('value', '" + longstring +"')", inputField);
		} catch (Exception e) {
			Assert.fail();
		//	softAssert.fail("Unable to click on element using JavaScriptExecutor : "+ locator+". Localized Message: "+e.getLocalizedMessage());
		}	
		
	}
	
	public  void sendKeys_usingJavaScriptExecutor(WebPage webPage, String locator, String text, SoftAssert softAssert){
		try {
			log.info("Clicking on element using xpath - "+locator);
			WebElement element=webPage.getDriver().findElement(By.xpath(locator));
			JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
			js.executeScript("arguments[0].setAttribute('text')", element);
			//driver.executeScript("arguments[0].setAttribute('value', '" + longstring +"')", inputField);
		} catch (Exception e) {
			softAssert.fail("Unable to click on element using JavaScriptExecutor : "+ locator+". Localized Message: "+e.getLocalizedMessage());
		}	
		
	}
}
