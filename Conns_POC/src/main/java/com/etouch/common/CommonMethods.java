package com.etouch.common;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.testng.Assert;

import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.taf.webui.selenium.WebPage;

public class CommonMethods {

	static Log log = LogUtil.getLog(CommonMethods.class);

	public boolean verifyTextPresent(WebPage webPage, String locator, String expectedText){
		boolean isTextEqual=false;
		try{
			log.info("Verifying expected text - "+expectedText);
			String actualText = webPage.findObjectByxPath(locator).getText();
			log.info("Actual text - "+actualText);
			if(actualText.equalsIgnoreCase(expectedText)){
				isTextEqual= true;
			}
		}catch(Throwable e){
			log.info("Verification failed for text - "+expectedText+" "+e.getLocalizedMessage());
		}
		return isTextEqual;
	}
	
	public boolean verifyElementisPresent(WebPage webPage, String locator){
		Boolean isElementPresent=false;
		try{
			log.info("Verifying if element is present by locator - "+locator);
			isElementPresent = webPage.findObjectByxPath(locator).isDisplayed();
		}catch(Throwable e){
			log.info("Element not visible using locator - "+locator+" "+e.getLocalizedMessage());
		}
		return isElementPresent;
	}
	
	public String clickAndGetPageURL(WebPage webPage, String locator, String linkName){
		String pageUrl="";
		try{
			log.info("Clicking on link : "+linkName);
			webPage.findObjectByxPath(locator).click();
			pageUrl= webPage.getDriver().getCurrentUrl();
			log.info("Actual URL : "+pageUrl);
		}catch(Throwable e){
			log.info("Unable to click on link '"+linkName+"' "+e.getLocalizedMessage());
		}
		return pageUrl;
	}
	
	
	public void clickElementbyXpath(WebPage webPage, String locator){
		try {
			log.info("Clicking on element using xpath - "+locator);
			webPage.findObjectByxPath(locator).click();
		} catch (PageException e) {
			log.info("Unable to click on element using Xpath : "+ locator+". ");
			e.printStackTrace();
		}
		
	}
	
	public void clickElementbyXpath(WebPage webPage,String parentlocator, String locator, String linkName){
		try{
			if(!parentlocator.equalsIgnoreCase("NA")){
				log.info("Clicking on parent locator : "+parentlocator);
				webPage.findObjectByxPath(parentlocator).click();	
			}
			log.info("Clicking on link : "+linkName);
			webPage.findObjectByxPath(locator).click();
		}catch(Throwable e){
			log.info("Unable to click on link '"+linkName+"' "+e.getLocalizedMessage());
		}
	}
	
	public String getTextbyXpath(WebPage webPage, String locator){
		String actualText= "";
		try {
			log.info("Getting text by using xpath - "+locator);
			actualText = webPage.findObjectByxPath(locator).getText();
		} catch (PageException e) {
			log.info("Unable to get text on element using Xpath : "+ locator+". ");
			e.printStackTrace();
		}
		return actualText;
	}
	
	public void sendKeysbyXpath(WebPage webPage, String locator, String text){
		try{
			log.info("Entering keys "+text+" ");
			webPage.findObjectByxPath(locator).sendKeys(text);
		}catch(Exception e){
			log.info("Unable to enter keys : "+text+" using locator : "+locator);
		}
	}
	
	public String getCssvaluebyXpath(WebPage webPage, String locator, String cssName){
		String cssValue="";
		try {
			log.info("Getting CSS value "+cssName+" for Xpath : "+locator);
			cssValue = webPage.findObjectByxPath(locator).getCssValue(cssName);
			if(cssName.equalsIgnoreCase("color")){
				cssValue=Color.fromString(cssValue).asHex();
			}
		} catch (PageException e) {
			log.info("Unable to get CSS Value : "+cssName+" for locator : "+locator);
			e.printStackTrace();
		}
		return cssValue;
	}
	
	public String getAttributebyXpath(WebPage webPage, String locator, String attribute){
		String attributeValue="";
		try {
			log.info("Getting attribute value "+attribute+" for Xpath : "+locator);
			attributeValue = webPage.findObjectByxPath(locator).getAttribute(attribute);
		} catch (PageException e) {
			log.info("Unable to get Attribute Value : "+attribute+" for locator : "+locator);
			e.printStackTrace();
		}
		return attributeValue;
	}
	
	public void hoverOnelementbyXpath(WebPage webPage, String locator){
		try {
			log.info("Hovering on element using locator : "+locator);
			WebElement element = webPage.getDriver().findElement(By.xpath(locator));
			if(!element.isDisplayed()){
				webPage.scrollDown(4);
			}
			webPage.hoverOnElement(By.xpath(locator));
		} catch (Exception e) {
			log.info("Unable to hove on element using Xpath : "+locator);
			e.printStackTrace();
		}
	}
	
	public void closeChromePopup(WebPage webPage){
		try{
			log.info("Checking if Chrome browser pop-up is present.");
			Thread.sleep(2000);
			if(webPage.getDriver().findElement(By.xpath("//*[@class='modal-footer']//button")).isDisplayed()){
				JavascriptExecutor executor = (JavascriptExecutor)webPage.getDriver();
				WebElement abc = webPage.getDriver().findElement(By.xpath("//*[@class='modal-footer']//button"));
				executor.executeScript("arguments[0].click();", abc);
				Thread.sleep(2000);
				log.info("Chrome browser pop-up handled.");
			}
		}catch(Exception e){
			log.info("Chrome browser pop-up not present.");
		}
	}
	
	public WebElement getWebElementbyXpath(WebPage webPage, String locator){
		WebElement element = null;
		try{
			log.info("Finding element using xpath :"+locator);
			element=webPage.getDriver().findElement(By.xpath(locator));
		}catch(Exception e){
			log.info("Unable to find element using Xpath : "+locator );
		}
		return element;
	}
	
	
	public List<String> getFontProperties(WebPage webPage,String locator){
		List<String> actualValueList=new ArrayList<String>();
		try{
			actualValueList.add(getCssvaluebyXpath(webPage,locator,"color"));
			actualValueList.add(getCssvaluebyXpath(webPage,locator,"font-size"));
			actualValueList.add(getCssvaluebyXpath(webPage,locator,"font-family"));
		}catch(Exception e){
			log.info("Unable to get font properties for locator : "+locator);
		}
		return actualValueList;
	}
}
