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

public class ConnsMoneyMatters extends CommonPage {

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
	CommonMethods commonMethods;
	
	public ConnsMoneyMatters(String sbPageUrl, WebPage webPage) 
	{
		super(sbPageUrl, webPage);
		CommonUtil.sop("webDriver in eTouchWebSite Page : " + webPage.getDriver());
		loadPage();
		//webPage.getDriver().manage().window().maximize();
	}
	

	public List<String> getFontProperties1(WebPage webPage,String locator, SoftAssert softAssert){
		System.out.println("Test1");
		List<String> actualValueList=new ArrayList<String>();
		try{
			System.out.println(commonMethods.getCssvaluebyXpath(webPage,locator,"font-size",softAssert));
			actualValueList.add(commonMethods.getCssvaluebyXpath(webPage,locator,"font-size",softAssert));
			
			
/*			actualValueList.add(commonMethods.getCssvaluebyXpath(webPage,locator,"color",softAssert));
			System.out.println(actualValueList.add(commonMethods.getCssvaluebyXpath(webPage,locator,"color",softAssert)));
			
			actualValueList.add(commonMethods.getCssvaluebyXpath(webPage,locator,"font-family",softAssert));
			System.out.println(actualValueList.add(commonMethods.getCssvaluebyXpath(webPage,locator,"font-family",softAssert)));
			
			actualValueList.add(commonMethods.getCssvaluebyXpath(webPage,locator,"font-weight",softAssert));
			System.out.println(actualValueList.add(commonMethods.getCssvaluebyXpath(webPage,locator,"font-weight",softAssert)));
			
			actualValueList.add(commonMethods.getCssvaluebyXpath(webPage,locator,"background-color",softAssert));
			System.out.println(actualValueList.add(commonMethods.getCssvaluebyXpath(webPage,locator,"background-color",softAssert)));
			
			actualValueList.add(commonMethods.getCssvaluebyXpath(webPage,locator,"text-align",softAssert));
			System.out.println(actualValueList.add(commonMethods.getCssvaluebyXpath(webPage,locator,"text-align",softAssert)));
			
			actualValueList.add(commonMethods.getCssvaluebyXpath(webPage,locator,"text-transform",softAssert));
			System.out.println(actualValueList.add(commonMethods.getCssvaluebyXpath(webPage,locator,"text-transform",softAssert)));
			
			actualValueList.add(commonMethods.getCssvaluebyXpath(webPage,locator,"text-decoration",softAssert));
			System.out.println(actualValueList.add(commonMethods.getCssvaluebyXpath(webPage,locator,"text-decoration",softAssert)));*/
		}catch(Exception e){
			softAssert.fail("Unable to get font properties for locator : "+locator+". Localized Message: "+e.getLocalizedMessage());
		}
		System.out.println("actualValueList.size : " + actualValueList.size());
		System.out.println("actualValueList : " + actualValueList);
		return actualValueList;
	}	
	
}	