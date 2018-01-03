package com.etouch.connsPages;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import org.testng.asserts.SoftAssert;

import com.etouch.common.CommonMethods;
import com.etouch.connsTests.Conns_Store_Locator_Page;
import com.etouch.taf.core.exception.PageException;

import com.etouch.taf.util.LogUtil;
import com.etouch.taf.webui.selenium.WebPage;

public class ConnsStoreLocatorPage extends Conns_Store_Locator_Page {
	
	static Log log = LogUtil.getLog(ConnsStoreLocatorPage.class);
	String testType;
	static String testBedName;
	static CommonMethods commonMethods = new CommonMethods();
	
	/**
	 * @author Name - Deepak Bhambri
	 * The method used to click element using linkText 
	 * Any structural modifications to the display of the link should be done by overriding this method.
	 * @throws InterruptedException 
	 * @throws PageException  If an input or output exception occurred
	 **/
	
	public static void closeLocationPopup(WebPage webPage, SoftAssert softAssert) throws InterruptedException{
		JavascriptExecutor js = (JavascriptExecutor)webPage.getDriver();
		log.info("Checking if location browser pop-up is present for this browser.");
		Thread.sleep(2000);
		try{
			if(webPage.getDriver().findElement(By.xpath("//*[@class='modal-footer']//button")).isDisplayed()){
				WebElement abc = webPage.getDriver().findElement(By.xpath("//*[@class='modal-footer']//button"));
				js.executeScript("arguments[0].click();", abc);
				Thread.sleep(2000);
				log.info("Location pop-up handled");
			}
		}catch(Exception e){
			log.info("Location pop-up not present for this browser.");
		}
	}
	
	
	public static List<String> getFontProperties(WebPage webPage,String locator, SoftAssert softAssert){	
		List<String> actualValueList=new ArrayList<String>();
		try {
			actualValueList = commonMethods.getFontProperties(webPage, locator, softAssert);

	}
	catch (Exception e){
		e.printStackTrace();
	}
		return actualValueList;
	}
	
	
	public static void mobile_Scroll_Down(WebPage webPage,String testType, SoftAssert softAssert) 
	{			JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
	try {
		if(testType.equalsIgnoreCase("Mobile")){
			js.executeScript("window.scrollBy(0,500)");				
		}
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	public static void mobile_Scroll_Down(WebPage webPage,String testType,int i, SoftAssert softAssert) 
	{		
	try {
		if(testType.equalsIgnoreCase("Mobile")){
			webPage.scrollDown(1);
		}
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	public static void mobile_Scroll_Bottom(WebPage webPage,String testType, SoftAssert softAssert) 
	{		
	try {
		if(testType.equalsIgnoreCase("Mobile")){
			webPage.scrollBottom();
		}
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	public static void PageNavigation_PageLoad(WebPage webPage,  String Navigation_URL,SoftAssert softAssert) 
	{	try {
		commonMethods.navigateToPage(webPage, Navigation_URL, softAssert);
		commonMethods.waitForPageLoad(webPage, softAssert);
		Thread.sleep(4000);
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	public static void waitForWebElement(WebPage webPage,  String locator,SoftAssert softAssert) 
	{	try {
		
		commonMethods.waitForPageLoad(webPage, softAssert);
		//commonMethods.waitForWebElement_1(By.xpath(locator), webPage); 
		Thread.sleep(6000);
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	
	public static String getTextbyCss(WebPage webPage, String locator, SoftAssert softAssert){
		String actualText= "";
		try {
			log.info("Getting text by using Css - "+locator);
		   	actualText = commonMethods.getTextbyCss(webPage, locator, softAssert);
		} catch (Exception e) {
			softAssert.fail("Unable to get text on element using Css : "+ locator+". Localized Message: "+e.getLocalizedMessage());
		}
		return actualText;
	}
	
	public static String getAttributebyXpath(WebPage webPage, String locator, String attribute, SoftAssert softAssert){
		String attributeValue="";
		try {
			log.info("Getting attribute value "+attribute+" for Xpath : "+locator);
			webPage.waitForWebElement(By.xpath(locator));
			attributeValue = commonMethods.getAttributebyXpath(webPage, locator,"href", softAssert);
		} catch (PageException e) {
			softAssert.fail("Unable to get Attribute Value : "+attribute+" for locator : "+locator+". Localized Message: "+e.getLocalizedMessage());
		}
		return attributeValue;
	}
	
	
	public static String getAttributebyXpathValue(WebPage webPage, String locator, String attribute, SoftAssert softAssert){
		String attributeValue="";
		try {
			log.info("Getting attribute value "+attribute+" for Xpath : "+locator);
			webPage.waitForWebElement(By.xpath(locator));
			attributeValue = commonMethods.getAttributebyXpath(webPage, locator,attribute, softAssert);
		} catch (PageException e) {
			softAssert.fail("Unable to get Attribute Value : "+attribute+" for locator : "+locator+". Localized Message: "+e.getLocalizedMessage());
		}
		return attributeValue;
	}
	
	public static String pageContentTextFunctionality(WebPage webPage, String testType,String testBedName,String [][]keyData, String subkey,String [] [] testData,String locator_2,String DataFilePath ,SoftAssert softAssert){
		String pageContentText="";
		try {
			ConnsStoreLocatorPage.waitForWebElement(webPage, locator_2, softAssert);
			if(!testBedName.equalsIgnoreCase("edge")){	
			 pageContentText = ConnsStoreLocatorPage.getTextbyXpath(webPage, subkey, softAssert);
			if(testType.equalsIgnoreCase("Mobile")){
				pageContentText=pageContentText.replace("Mon-Fri", "Store Hours\nMon-Fri");
			}
			}
		}catch (Exception e) {
			e.printStackTrace();	
			}
		return pageContentText;
	}
	
	public static void scrollingOperation(WebPage webPage,  String testType,String [][]testData,SoftAssert softAssert) 
	{	try {
		for (int i = 0; i < testData.length; i++) {
		if(testType.equalsIgnoreCase("Mobile")){
			if(i <= 8){
				webPage.scrollDown(1);
		}else {
				webPage.scrollDown(2);
		}
			
		}
	}
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	public static void clickWithChildElementby_UsingJavaScriptXpath(WebPage webPage,  String Locator_1,String Locator_2,String Locator_0,SoftAssert softAssert) 
	{	try {
		commonMethods.clickWithChildElementby_UsingJavaScriptXpath(webPage, Locator_1,Locator_2,Locator_0, softAssert);
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	public static void verifyElementisPresent(WebPage webPage,  String locator,SoftAssert softAssert) 
	{	try {
		commonMethods.waitForPageLoad(webPage, softAssert);
		CommonMethods.waitForWebElement(By.xpath(locator), webPage); 
		Thread.sleep(4000);
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	public static void clearTextBox(WebPage webPage,  String locator,SoftAssert softAssert) 
	{	try {
		commonMethods.waitForPageLoad(webPage, softAssert);
		commonMethods.clearTextBox(webPage, locator, softAssert);
		Thread.sleep(4000);
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	public static boolean verifyElementPresence(WebPage webPage, String[][] test,  String locator,SoftAssert softAssert) 
	{	boolean verifyElementPresence = false;	
		try {
			log.info("Inside Try verifyElementPresence  : ");
		    verifyElementPresence = commonMethods.verifyElementisPresent(webPage,locator, softAssert); 
		    Thread.sleep(4000);

	}
	catch (Exception e){
		e.printStackTrace();
	}
	return verifyElementPresence;
	}
	
	public static  String Verify_ChoseYourRegion_Links_afterLinkHoverFunctionality(WebPage webPage,String [][] testData,String testBedName,String testType,String browserName,SoftAssert softAssert) {
		String afterLinkHover  = "";
		try { 
			for (int i = 0; i < testData.length; i++) {
				if (testType.equalsIgnoreCase("Web") && (!browserName.equalsIgnoreCase("Safari")) || (!browserName.equalsIgnoreCase("IE") || (!browserName.equalsIgnoreCase("InternetExplorer") ))) {
					if(!testData[i][3].equalsIgnoreCase("NA")){
						commonMethods.hoverOnelementbyXpath(webPage, testData[i][1], softAssert);
						log.info("Region "+testData[i][0]+" after hover color attribute value is : "+afterLinkHover);
						afterLinkHover = commonMethods.getCssvaluebyXpath(webPage, testData[i][1], "color", softAssert);
					}
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return afterLinkHover;
	}
	
	
	public static String getCssvaluebyXpath(WebPage webPage, String locator, String cssName, SoftAssert softAssert){
		String cssValue="";
		try {
			cssValue = commonMethods.getCssvaluebyXpath(webPage, locator, "color", softAssert);
		} catch (Exception e) {
			softAssert.fail("Unable to get CSS Value : "+cssName+" for locator : "+locator+". Localized Message: "+e.getLocalizedMessage());
		}
		return cssValue;
	}
	
	
	public static  String getCssvaluebyXpath(WebPage webPage,String Locator_5,SoftAssert softAssert) {
		String errorMessageActualColor  = "";
		try { 
			errorMessageActualColor = commonMethods.getCssvaluebyXpath(webPage, Locator_5, "color", softAssert);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return errorMessageActualColor;
	}
	
	public static  String color_afterLinkHoverFunctionality(WebPage webPage,boolean testMethodName,String [][] testData,String Locator_4,String Locator_0,String Locator_2,String Locator_3,String testBedName,String testType,String browserName,SoftAssert softAssert) {
		String afterLinkHover  = "";		
		try {
		if (testMethodName == true)
		{ 					commonMethods.hoverOnelementbyXpath(webPage, Locator_2, softAssert);
							afterLinkHover = commonMethods.getCssvaluebyXpath(webPage, Locator_2, "color", softAssert);
							log.info("afterLinkHover : "+afterLinkHover);

		}else if (testMethodName == false) {
						commonMethods.hoverOnelementbyXpath(webPage, Locator_0, softAssert);
						afterLinkHover = commonMethods.getCssvaluebyXpath(webPage, Locator_0, "color", softAssert);
		}
		}catch (Exception e){
			e.printStackTrace();
		}
		return afterLinkHover;
	}
/**********************************************2nd Approach ********************************************************************/
		/*	for (int i = 0; i < testData.length; i++) {
				if (testMethodName = true)
				{
				Locator_1 = testData[i][4];
				Locator_2 = testData[i][2];
				Locator_3 = testData[i][0];
				}

			else {
				Locator_1 = testData[i][3];
				Locator_2 = testData[i][1];
				Locator_3 = testData[i][0];
			}
			}
			if (testMethodName = false){
			if (testType.equalsIgnoreCase("Web") && (!browserName.equalsIgnoreCase("Safari")) || (!browserName.equalsIgnoreCase("IE") || (!browserName.equalsIgnoreCase("InternetExplorer") ))) {
				if(!Locator_1.equalsIgnoreCase("NA")){
					commonMethods.hoverOnelementbyXpath(webPage, Locator_2, softAssert);
					log.info("Region "+Locator_3+" after hover color attribute value is : "+afterLinkHover);
					afterLinkHover = commonMethods.getCssvaluebyXpath(webPage, Locator_2, "color", softAssert);
			}
			}
			}
			else {
				if (testType.equalsIgnoreCase("Web") && (!browserName.equalsIgnoreCase("Safari"))) {
					if(!Locator_1.equalsIgnoreCase("NA")){
						commonMethods.hoverOnelementbyXpath(webPage, Locator_2, softAssert);
						log.info("Region "+Locator_3 +" after hover color attribute value is : "+afterLinkHover);
						afterLinkHover = commonMethods.getCssvaluebyXpath(webPage, Locator_2, "color", softAssert);
				}
			}
		}*/
		
/***************************************************************************************************************************************************/				

	

	
	public static void pageBasicFunctionality (WebPage webPage,String storeLocatorURL,String locator_1,String locator_0,SoftAssert softAssert) 
	{ try {
		PageNavigation_PageLoad(webPage,storeLocatorURL, softAssert);
		closeLocationPopup(webPage,softAssert);
		waitForWebElement(webPage,locator_1, softAssert);
		clearTextBox(webPage, locator_0, softAssert);	
		log.info("TextBox Cleared Successfully  : "+locator_0);

	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	public static void StoreLocatorDistancePageBasicFunctionality (WebPage webPage,String storeLocatorURL,String locator_0,String Locator_1,String Locator_0,SoftAssert softAssert) 
	{ try {
		PageNavigation_PageLoad(webPage,storeLocatorURL, softAssert);
		closeLocationPopup(webPage,softAssert);
		waitForWebElement(webPage,locator_0, softAssert);
		clearTextBox(webPage, Locator_1, softAssert);		
		commonMethods.sendKeysbyXpath(webPage, Locator_1, Locator_0, softAssert);
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	
	public static String getPageUrl(WebPage webPage, SoftAssert softAssert){
		String actualUrl="";
		try{
			actualUrl = commonMethods.getPageUrl(webPage, softAssert);
		}catch(Exception e){
			softAssert.fail("Unable to get current page url. Localized Message: "+e.getLocalizedMessage());
		}
		return actualUrl;
	}
	
	public static WebElement getWebElementbyXpath(WebPage webPage,String locator,SoftAssert softAssert) 
	{	WebElement element = null;
	try {
		commonMethods.getWebElementbyXpath(webPage,locator, softAssert);
	}
	catch (Exception e){
		e.printStackTrace();
	}
	return element;
	}
	
	public static void clickElementByJS_And_WaitForWebElement (WebPage webPage,String locator_1,String locator_2,SoftAssert softAssert) 
	{ try {
		commonMethods.Click_On_Element_JS(webPage, locator_1, softAssert);
		waitForWebElement(webPage,locator_2, softAssert);	
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	public static String clickElementbyJSAndGetURL(WebPage webPage, String locator, SoftAssert softAssert) throws InterruptedException{
		String pageUrl="";
		try {
			pageUrl = commonMethods.clickElementbyJSAndGetURL(webPage,  locator,   softAssert);
		} catch (Exception e) {
			e.printStackTrace();
			softAssert.fail("Unable to click on element using Xpath : "+ locator+". Localized Message: "+e.getLocalizedMessage());		
			}
		return pageUrl;
	}
	
	public static String clickAndGetPageURL(WebPage webPage, String locator, String linkName, SoftAssert softAssert,String waitOnelementLocator){
		String pageUrl="";
		try{
			pageUrl = commonMethods.clickAndGetPageURL(webPage,  locator,  linkName,  softAssert, waitOnelementLocator);
		}catch(Throwable e){
			softAssert.fail("Unable to click on link '"+linkName+". Localized Message: "+e.getLocalizedMessage());
		}
		return pageUrl;
	}

	
	public static void clickElementByJS_And_WaitForWebElement (WebPage webPage,String locator_1,String locator_2,String [][] testData,SoftAssert softAssert) 
	{ 	try {
		commonMethods.Click_On_Element_JS(webPage, locator_1, softAssert);
		waitForWebElement(webPage,locator_2, softAssert);	
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	public static void find_Store_Page_selectDropdownByValue_clickElementByJS_And_WaitForWebElement_Functionality (WebPage webPage,String Locator_4,String dropdownValue,String Locator_2,String Locator_B,SoftAssert softAssert) 
	{ try {
		
		selectDropdownByValue(webPage, Locator_4,dropdownValue, softAssert);
		clickElementByJS_And_WaitForWebElement(webPage, Locator_2,Locator_B, softAssert);
	
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}

	public static void Store_Locator_Page_Basic_Functionality (WebPage webPage,String storeLocatorURL,String locator,SoftAssert softAssert)
	{ try {
		PageNavigation_PageLoad(webPage,storeLocatorURL, softAssert);
		closeLocationPopup(webPage,softAssert);
		waitForWebElement(webPage,locator, softAssert);	
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	public static int  textbyXpathAssertion(WebPage webPage, String Locator,SoftAssert softAssert)
	{ String searchXXXMilesActualData = "";
	int intSearchXXXMilesActualData = 0 ;
	try {
		searchXXXMilesActualData = ConnsStoreLocatorPage.getTextbyXpath(webPage, Locator,softAssert);
		searchXXXMilesActualData = searchXXXMilesActualData.replace("mi","").replaceAll(" ", "");
		intSearchXXXMilesActualData=Double.valueOf(searchXXXMilesActualData).intValue();;	
	}
	catch (Exception e){
		e.printStackTrace();
	}
	return intSearchXXXMilesActualData;
	}
	
	public static String  getTextbyXpathAssertion(WebPage webPage, String Locator_3,SoftAssert softAssert)
	{ String searchXXXMilesActualData = "";
	try {
		searchXXXMilesActualData = ConnsStoreLocatorPage.getTextbyXpath(webPage, Locator_3,softAssert);	
		searchXXXMilesActualData = searchXXXMilesActualData.replace("mi","").replaceAll(" ", "");	
	}
	catch (Exception e){
		e.printStackTrace();
	}
	return searchXXXMilesActualData;
	}
	
	
	public static void selectDropdownByValue (WebPage webPage,String locator,String dropdownValue,SoftAssert softAssert) 
	{ try {
		commonMethods.selectDropdownByValue(webPage, locator,dropdownValue, softAssert);
		//commonMethods.selectDropdownByText(webPage, locator,dropdownValue, softAssert);
		}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	
	@SuppressWarnings("static-access")
	public static WebElement waitForWebElement(WebPage webPage,String Locator_3) throws PageException{
		WebElement element = null;
		try{
			element = commonMethods.waitForWebElement(By.xpath(Locator_3), webPage);
		}
		catch (Exception e) {
			throw new PageException("Failed to find object using given name, message : " + e.toString());
		}
		return element;
	}
	
	public static void find_Store_Page_Basic_Functionality (WebPage webPage,String storeLocatorURL,String locator_1,String Locator_1,String locator_0,String Locator_2,String Locator_3,String testBedName,SoftAssert softAssert) 
	{ try {
		PageNavigation_PageLoad(webPage,storeLocatorURL, softAssert);
		closeLocationPopup(webPage,softAssert);
		waitForWebElement(webPage,locator_1, softAssert);
		clearTextBox(webPage, Locator_1, softAssert);
		commonMethods.sendKeysbyXpath(webPage, Locator_1, locator_0, softAssert);
		commonMethods.Click_On_Element_JS(webPage, Locator_2, softAssert);
		if(testBedName.contains("iPadNative")||testBedName.contains("iPhoneNative")||testBedName.equalsIgnoreCase("Safari")||testBedName.equalsIgnoreCase("InternetExplorer")){
			commonMethods.waitForGivenTime(15, softAssert);
		}
		waitForWebElement(webPage,Locator_3, softAssert);
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	
	public static void find_Store_Page_Basic_Functionality_1 (WebPage webPage,String storeLocatorURL,String locator_1,String Locator_1,String locator_0,String Locator_2,String Locator_3,String testBedName,SoftAssert softAssert) 
	{ try {
		PageNavigation_PageLoad(webPage,storeLocatorURL, softAssert);
		closeLocationPopup(webPage,softAssert);
		waitForWebElement(webPage,locator_1, softAssert);
		clearTextBox(webPage, Locator_1, softAssert);
		commonMethods.sendKeysbyXpath(webPage, Locator_1, locator_0, softAssert);
		commonMethods.Click_On_Element_JS(webPage, Locator_2, softAssert);
		commonMethods.waitForGivenTime(5, softAssert);
		if(testBedName.contains("iPadNative")||testBedName.contains("iPhoneNative")||testBedName.equalsIgnoreCase("Safari")||testBedName.equalsIgnoreCase("InternetExplorer")){
			commonMethods.waitForGivenTime(7, softAssert);
		}
		waitForWebElement(webPage,Locator_3, softAssert);
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	public static void Store_Page_Basic_Functionality (WebPage webPage,String storeLocatorURL,String Locator_1,String Locator_2,String Locator_3,String Locator_4,SoftAssert softAssert) 
	{ try {
		PageNavigation_PageLoad(webPage,storeLocatorURL, softAssert);
		closeLocationPopup(webPage,softAssert);
		waitForWebElement(webPage,Locator_1, softAssert);
		clearTextBox(webPage, Locator_2, softAssert);
		commonMethods.sendKeysbyXpath(webPage, Locator_2, Locator_3, softAssert);
		commonMethods.Click_On_Element_JS(webPage, Locator_4, softAssert);
		commonMethods.waitForGivenTime(5, softAssert);
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	
	public static void ScrollIntoView (WebPage webPage,String Locator_6,SoftAssert softAssert) 
	{ 		JavascriptExecutor js = (JavascriptExecutor)webPage.getDriver();
		try {
			//executing this query will scroll until that element is not appeared on page.
			Thread.sleep(1000);
			commonMethods.waitForPageLoad(webPage, softAssert);
			WebElement element = commonMethods.findElementByXpath( webPage,  Locator_6,  softAssert);
			js.executeScript("arguments[0].scrollIntoView(true);",element);
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	public static void clickElementByJS(WebPage webPage,String locator,SoftAssert softAssert) 
	{	try {
		//commonMethods.clickElementbyXpath_usingJavaScript(webPage,locator,softAssert);
		commonMethods.clickElementbyXpath(webPage, locator, softAssert);
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	public static String  AlertBoxHandling(WebPage webPage,SoftAssert softAssert){
		String  alertActualText = "";
		try {
			alertActualText = commonMethods.AlertBoxHandling(webPage,softAssert);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return alertActualText;
	}
	
	public static void verifyBrokenLinksForGivenLinks(WebPage webPage,List<WebElement> linkList) 
	{	try {
		commonMethods.verifyBrokenLinksForGivenLinks(webPage, linkList);
		Thread.sleep(4000);
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	public static String href_getAttributebyXpath(WebPage webPage, String [] [] testData,String Locator_3,SoftAssert softAssert)
	{	String actual_hrefValue  = "";
	try {
		actual_hrefValue = commonMethods.getAttributebyXpath(webPage, Locator_3,"href",softAssert);
		Thread.sleep(4000);
	}
	catch (Exception e){
	e.printStackTrace();
	}
	return actual_hrefValue;
	}
	
	
	
	public static String getAttributebyXpath(WebPage webPage,String Locator_3, SoftAssert softAssert)
	{	String actual_hrefValue  = "";
	try {
		actual_hrefValue = commonMethods.getAttributebyXpath(webPage, Locator_3,"title",softAssert);
		Thread.sleep(4000);
		}
	catch (Exception e){
		e.printStackTrace();
	}
	return actual_hrefValue;
	}
	
	
	public static String getTextbyXpath(WebPage webPage, String Locator_3, SoftAssert softAssert)
	{	String textOnLink  = "";
	try {
		textOnLink = commonMethods.getTextbyXpath(webPage, Locator_3, softAssert);
		Thread.sleep(4000);
	}
	catch (Exception e){
		e.printStackTrace();
	}
	return textOnLink;
	}
	
	public static List<WebElement> findElementsByXpath(WebPage webPage,  String locator,SoftAssert softAssert) 
	{	log.info("Finding multiple elements by x-path: "+locator);
	    List<WebElement> elementList= new ArrayList<WebElement>();
	try {
		elementList = commonMethods.findElementsByXpath(webPage,locator , softAssert);
		Thread.sleep(4000);
	}
	catch (Exception e){
		e.printStackTrace();
	}
	return elementList;
	}
	
}
