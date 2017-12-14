package com.etouch.connsPages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.Alert;
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
import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;



public class ConnsHomePage extends CommonPage {
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
	
	public ConnsHomePage(String sbPageUrl, WebPage webPage) {
		super(sbPageUrl, webPage);
		CommonUtil.sop("webDriver in ConnsHomePage Page : " + webPage.getDriver());
		loadPage();
	}

	
	public String verifyLinkNavigationUsingJS(WebPage webPage, String locator, String linkName, String TargetPageLocator,
			SoftAssert softAssert) throws PageException, InterruptedException {
		String pageURL = "";
		pageURL = commonMethods.clickAndGetPageURLUsingJS( webPage,  locator,  linkName,  TargetPageLocator,softAssert);		
		Thread.sleep(1000);
		return pageURL;
	
	}
	
	public String textVerification(WebPage webPage, String locator,SoftAssert softAssert) throws PageException, InterruptedException {
		String Actual_Text = "";
		Actual_Text = commonMethods.getTextbyXpath(webPage, locator, softAssert);		
		Thread.sleep(1000);
		return Actual_Text;
	
	}
	
	public boolean elementPresenceVerification(WebPage webPage, String locator,SoftAssert softAssert) throws PageException, InterruptedException {
		boolean elementPresence;
		elementPresence = commonMethods.isElementPresentCheckUsingJavaScriptExecutor(webPage, locator, softAssert);
		Thread.sleep(1000);
		return elementPresence;
	
	}


	public void elementHoveringVerification(WebPage webPage, String locator,  SoftAssert softAssert) {
		try{ 
			commonMethods.mouseOverOnElementUsingRobot(webPage, locator, softAssert);
			commonMethods.waitForPageLoad(webPage, softAssert);
			Thread.sleep(1000);
		}
		catch(Exception e)
		{
	    	e.printStackTrace();
	    }
		}
	
	public void linkStatusCodeAndHrefValueVerification(WebPage webPage, String linkName, String xpath, String expectedHref, String [][] testData, SoftAssert softAssert)  {
		try{ 
				commonMethods.verifyLinkStatusCodeAndHrefValue(webPage, linkName,xpath,expectedHref, softAssert);
				commonMethods.waitForPageLoad(webPage, softAssert);
				Thread.sleep(1000);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		}

	public void homePageNavigationFunctionality(WebPage webPage, String hamburgerIcon, String pageLink,   SoftAssert softAssert) throws PageException, InterruptedException, ClientProtocolException, IOException {
		commonMethods.Click_On_Element_JS(webPage, hamburgerIcon, softAssert);
		commonMethods.Click_On_Element_JS(webPage, pageLink, softAssert);
		Thread.sleep(1000);
	
	}

	public void patioFurnitureLinkNavigation(WebPage webPage, String patioFurniturePageLink,   SoftAssert softAssert) throws PageException, InterruptedException, ClientProtocolException, IOException {
		try {
			webPage.scrollToElement(webPage.findObjectByxPath(patioFurniturePageLink));
			Thread.sleep(1000);
		}catch (Exception e )
		{
				e.printStackTrace();
		}
		}

	
	
	public void portable_Air_Conditioners(WebPage webPage, String portable_Air_Conditioners_Link,SoftAssert softAssert)
	{
	try {
		webPage.scrollToElement(webPage.findObjectByxPath(portable_Air_Conditioners_Link));
		Thread.sleep(1000);
	}catch (Exception e )
	{
		e.printStackTrace();
	}
}

	public void Verify_Link_Redirection_Locator(WebPage webPage, String elementXpath,   SoftAssert softAssert) throws PageException, InterruptedException, ClientProtocolException, IOException {
		commonMethods.Click_On_Element_JS(webPage, elementXpath, softAssert);
		Thread.sleep(1000);
	
	}
	
	public void LinksRedirection_Under_FinancingPromotions_Menu_Verification_Web(WebPage webPage, String hoverElementXpath, String testData[][],  SoftAssert softAssert) throws PageException, InterruptedException, ClientProtocolException, IOException {
		log.info("TestType is  : ************ testBedName **************"  );
		commonMethods.mouseOverOnElementUsingRobot(webPage, hoverElementXpath, softAssert);
		Thread.sleep(4000);
		for (int i = 0; i < testData.length; i++) {
			log.info("Iteration under test : " + i);
			commonMethods.verifyLinkStatusCodeAndHrefValue(webPage, testData[i][2], testData[i][1], testData[i][2], softAssert);
		}
		}
	
	
	
	public String  Verify_HomePage_Banner_Links(WebPage webPage,  String testData[][], String link_Locator,String link_Name,String Navigation_URL,  SoftAssert softAssert) {
		String pageURL = "";
		PageNavigation_PageLoad( webPage,  Navigation_URL, softAssert) ;
		try {
			pageURL = commonMethods.clickAndGetPageURLByJS(webPage,link_Locator, link_Name,  softAssert);			

		}catch (Exception e)
			{
		e.printStackTrace();
			}
		return pageURL;
	}
	
	public void PageNavigation_PageLoad(WebPage webPage,  String Navigation_URL,SoftAssert softAssert) 
	{	try {
		commonMethods.navigateToPage(webPage, Navigation_URL, softAssert);
		commonMethods.waitForPageLoad(webPage, softAssert);
		Thread.sleep(4000);
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	
	public void waitForPageLoad(WebPage webPage,  SoftAssert softAssert) 
	{	log.info("TestType is  : ************ testBedName **************"  );	
		try {
		commonMethods.waitForPageLoad(webPage, softAssert);
		Thread.sleep(4000);
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	
	public void verifyBrokenImage(WebPage webPage)
	{
		commonMethods.verifyBrokenImage(webPage);
	}
	
	
	public void Verify_Link_Navigation(WebPage webPage,  String testData[] [], String URL,String testType,SoftAssert softAssert) 
	{  	String ActualURL = "";
		String Redirection_Link_Locator = "";
		String link_Name = "";
		String Target_Page_Locator = "";
		String Expected_URL = "";
		try {
		commonMethods.waitForPageLoad(webPage, softAssert);
		Thread.sleep(2000);
		for (int i = 0; i < testData.length; i++) {	
		log.info("Inside the if. Value of I Before : " + i);
		Expected_URL = testData[i][4]; 
		link_Name = testData[i][0];
		Target_Page_Locator = testData[i][5];
		String Redirection_Link_Locator_2 = testData[i][2];
		String Redirection_Link_Locator_1 = testData[i][3];
		if (testType.equalsIgnoreCase("Web")) {	
			Redirection_Link_Locator = testData[i][2];
		}
		if (testType.equalsIgnoreCase("Mobile") && (!(Redirection_Link_Locator_2.equalsIgnoreCase("NA")))) {
			Redirection_Link_Locator = testData[i][2];
		}
		if (testType.equalsIgnoreCase("Mobile")  && (!(Redirection_Link_Locator_1.equalsIgnoreCase("NA")))) {
			Redirection_Link_Locator = testData[i][3];
		}
		ActualURL = commonMethods.clickAndGetPageURLUsingJS(webPage, Redirection_Link_Locator,link_Name,Target_Page_Locator, softAssert);
		softAssert.assertTrue(ActualURL.contains(Expected_URL),"Link Name  :" + link_Name + " : failed " + "Actual URL is  :" + ActualURL + " "	+ "Expected URL is  :" + Expected_URL);	
		PageNavigation_PageLoad(webPage, URL, softAssert);
		Thread.sleep(2000);
		log.info("Inside the if. Value of I After : " + i);
		softAssert.assertAll();
		}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void Link_Redirection_Verification(WebPage webPage,  String testData[] [], String URL,String testType,String testBedName,SoftAssert softAssert) 
	{  	String ActualURL = "";
		String link_Name = "";
		String Target_Page_Locator = "";
		String Redirection_Link_Locator = "";
		String Expected_URL = "";
		try {
		commonMethods.waitForPageLoad(webPage, softAssert);
		Thread.sleep(2000);
		for (int i = 0; i < testData.length; i++) {	
		log.info("Inside the if. Value of I Before : " + i);
		Expected_URL = testData[i][4]; 
		link_Name = testData[i][0];
		Target_Page_Locator = testData[i][5];
		if (testType.equalsIgnoreCase("Web") && (!(testBedName.equalsIgnoreCase("Edge") ))) {
			log.info("Web TestType is  : " +testType +"************ testBedName **************" +testBedName );
			Redirection_Link_Locator = testData[i][1];
			log.info("Redirection_Link_Locator : " +Redirection_Link_Locator.toString());
		}
		if (testType.equalsIgnoreCase("Mobile") || (testType.equalsIgnoreCase("Web") && (testBedName.equalsIgnoreCase("Edge")))) {
			log.info("*********** Mobile TestType is  : " + testType +"************  testBedName **************" +testBedName );
			Redirection_Link_Locator = testData[i][2];
			log.info("Redirection_Link_Locator : " +Redirection_Link_Locator.toString());
		}
		ActualURL = commonMethods.clickAndGetPageURLUsingJS(webPage, Redirection_Link_Locator,link_Name,Target_Page_Locator, softAssert);
		softAssert.assertTrue(ActualURL.contains(Expected_URL),"Link Name  :" + link_Name + " : failed " + "Actual URL is  :" + ActualURL + " "	+ "Expected URL is  :" + Expected_URL);	
		PageNavigation_PageLoad(webPage, URL, softAssert);
		Thread.sleep(2000);
		log.info("Inside the if. Value of I After : " + i);		
		}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	

	
	public void LinksRedirection_Under_Option_Menu_Verification_Mobile(WebPage webPage,  String testData[][],String patio_Furniture_Matteress_Link_Locator,boolean patio_Functionality,boolean portable_Air_Conditioners , SoftAssert softAssert)
	{		
		log.info("TestType is  : " +testType +"************ testBedName **************" +testBedName );
		String elementXpath=null;
		
	try {
		
		   for (int i = 0; i < testData.length; i++) {
			log.info("Iteration under test : " + i);
			String Locator_1 = testData[i][4];
			String Locator_2 = testData[i][5];
			String Locator_3 = testData[i][6];			
			String Locator_4 = testData[i][6];
			String Locator_5 = testData[i][7];
			String Locator_6 = testData[i][2];
			String Locator_7 = testData[i-1][4];
			String patio_Furniture_Matteress_Link = "";
			if(!(testData[i][4].equalsIgnoreCase("NA"))){
				// verify if element was visited in previous iteration, if yes then it will skip navigation steps
				if(elementXpath==null || !elementXpath.equals(Locator_3))
				{
					//click on Main Menu
					commonMethods.Click_On_Element_JS(webPage, Locator_1, softAssert);
					commonMethods.Click_On_Element_JS(webPage, Locator_2, softAssert);
					if(i>=30 && patio_Functionality == true){
						
						patioFurnitureLinkNavigation(webPage, patio_Furniture_Matteress_Link,softAssert);
					//	webPage.scrollToElement(webPage.findObjectByxPath("(//*[@id='li-primary-pronav-furniture---mattresses']//a)[33]"));
					}

					if(i>=50 && portable_Air_Conditioners == true){
						portable_Air_Conditioners(webPage, patio_Furniture_Matteress_Link,softAssert);
						webPage.scrollToElement(webPage.findObjectByxPath("(.//*[@id='li-primary-pronav-appliances']//a)[43]"));
					}

					//if element does not have child then use element as child

					elementXpath=Locator_4;
					if(!Locator_4.equals("NA"))
					{
						//if element has child

						commonMethods.Click_On_Element_JS(webPage, Locator_4, softAssert);
						//	elementXpath=testData[i][6];
						if(i>=0){
						}
					}
				}
				commonMethods.verifyLinkStatusCodeAndHrefValue(webPage, Locator_6, Locator_5, Locator_6, softAssert);
			}
			else{
				if(i>0)
				{
					commonMethods.Click_On_Element_JS(webPage, Locator_7, softAssert);
					elementXpath=null;
				}
			}
		}
		
	}
	catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	public String verifyLinkNavigationByJS(WebPage webPage,String linkName,String locator,String URL, SoftAssert softAssert) throws PageException, InterruptedException {
		String pageURL = "";
		pageURL = commonMethods.clickAndGetPageURLByJS( webPage,linkName, locator, softAssert);	
		//PageNavigation_PageLoad(webPage,  URL, softAssert);
		Thread.sleep(1000);
		return pageURL;
	
	}


	
	public void verifySaveBigWithConnsSection(String[][] test,String URL,String testType) throws PageException {
		SoftAssert softAssert = new SoftAssert();
		String SaveBigMenuOptionIdentifier = null;
		String CarouselLeft = null;
		String CarouselRight = null;
		String ElementPosition1 = null;
		String ElementPosition2 = null;
		String ClickForDetails = null;
		String PopUp = null;
		// int RotationCountMobile = 0;
		int RotationCountWeb = 0;
		String SaveBigMenuOptionIdentifierMobile = null;
		List<String> errors = new ArrayList<String>();
		webPage.waitForWebElement(By.xpath(test[0][0]));
		for (int i = 0; i < test.length; i++) {
			try {
				log.info("Value of I : " + i);
				webPage.getDriver().get(URL);
				SaveBigMenuOptionIdentifier = test[i][0].trim();
				CarouselLeft = test[i][1];
				CarouselRight = test[i][2];
				ElementPosition1 = test[i][3];
				ElementPosition2 = test[i][4];
				ClickForDetails = test[i][5];
				PopUp = test[i][6];
				// RotationCountMobile = Integer.parseInt(test[i][8]);
				RotationCountWeb = Integer.parseInt(test[i][9]);
				SaveBigMenuOptionIdentifierMobile = test[i][10];
				JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
				log.info(" " + SaveBigMenuOptionIdentifier + " " + CarouselLeft + " " + CarouselRight + " "
						+ ElementPosition1 + " " + ElementPosition2 + " " + ClickForDetails + " " + PopUp);
				String textAtPosition1 = null;
				String textAtPosition2 = null;
				String textAtPosition11 = null;
				String textAtPosition12 = null;
				if (testType.equalsIgnoreCase("Mobile")) {
					Thread.sleep(3000);
					WebElement element = webPage.findObjectByxPath(SaveBigMenuOptionIdentifierMobile).getWebElement();
					log.info("$$$$ Parent : " + element.getText());
					js.executeScript("arguments[0].click();", element);
					Thread.sleep(3000);
				//	webPage.findObjectByxPath(CarouselLeft).click(); 
					/*****************using Javascript for clicking operation  *****************************/
					
					WebElement element_1=webPage.getDriver().findElement(By.xpath(CarouselLeft));			
					js.executeScript("arguments[0].click();", element_1);	
					Thread.sleep(3000);
					log.info("Clicked on element1");
					textAtPosition1 = webPage.findObjectByxPath(ElementPosition1).getText();
					log.info("Expected Left: " + textAtPosition1);
					for (int j = 0; j < RotationCountWeb; j++) {
						//webPage.findObjectByxPath(CarouselLeft).click();
						/*****************using Javascript for clicking operation  *****************************/
						WebElement element_2=webPage.getDriver().findElement(By.xpath(CarouselLeft));			
						js.executeScript("arguments[0].click();", element_2);	
						Thread.sleep(1000);
					}
					textAtPosition2 = webPage.findObjectByxPath(ElementPosition1).getText();
					log.info("Actual Left : " + textAtPosition2);
					SoftAssertor.assertEquals(textAtPosition1, textAtPosition2,
							" failed " + textAtPosition1 + " " + textAtPosition2);
					webPage.findObjectByxPath(CarouselRight).click();
					Thread.sleep(3000);
					log.info("Clicked on element2");
					textAtPosition11 = webPage.findObjectByxPath(ElementPosition2).getText();
					log.info("Expected Right: " + textAtPosition11);
					for (int k = 0; k < RotationCountWeb; k++) {
						webPage.findObjectByxPath(CarouselRight).click();
					}
					textAtPosition12 = webPage.findObjectByxPath(ElementPosition2).getText();
					log.info("Actual Right: " + textAtPosition12);
					SoftAssertor.assertEquals(textAtPosition11, textAtPosition12,
							" failed " + textAtPosition11 + " " + textAtPosition12);
				} else {
					if (i > 1) {
						textAtPosition1 = null;
						textAtPosition2 = null;
						textAtPosition11 = null;
						textAtPosition12 = null;
					}
					Thread.sleep(3000);
					WebElement element = webPage.findObjectByxPath(SaveBigMenuOptionIdentifier).getWebElement();
					log.info("$$$$ Parent : " + element.getText());
					js.executeScript("arguments[0].click();", element);
					Thread.sleep(3000);
					webPage.findObjectByxPath(CarouselLeft).click();
					Thread.sleep(3000);
					log.info("Clicked on element1");
					textAtPosition1 = webPage.findObjectByxPath(ElementPosition1).getText();
					log.info("Expected Left: " + textAtPosition1);
					for (int j = 0; j < RotationCountWeb; j++) {
						webPage.findObjectByxPath(CarouselLeft).click();
						Thread.sleep(1000);
					}
					textAtPosition2 = webPage.findObjectByxPath(ElementPosition1).getText();
					log.info("Actual Left : " + textAtPosition2);
					SoftAssertor.assertEquals(textAtPosition1, textAtPosition2,
							" failed " + textAtPosition1 + " " + textAtPosition2);
					webPage.findObjectByxPath(CarouselRight).click();
					Thread.sleep(3000);
					log.info("Clicked on element2");
					textAtPosition11 = webPage.findObjectByxPath(ElementPosition2).getText();
					log.info("Expected Right: " + textAtPosition11);
					for (int k = 0; k < RotationCountWeb; k++) {
						webPage.findObjectByxPath(CarouselRight).click();
					}
					textAtPosition12 = webPage.findObjectByxPath(ElementPosition2).getText();
					log.info("Actual Right: " + textAtPosition12);
					SoftAssertor.assertEquals(textAtPosition11, textAtPosition12,
							" failed " + textAtPosition11 + " " + textAtPosition12);
				}
				softAssert.assertAll();
			} catch (Throwable e) {
				e.printStackTrace();
				errors.add(e.getLocalizedMessage());
				log.error(e.getMessage());
			}
		}
	}
		// TODO Auto-generated method stub
		
	
}
