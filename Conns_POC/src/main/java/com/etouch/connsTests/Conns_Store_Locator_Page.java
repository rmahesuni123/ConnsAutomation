package com.etouch.connsTests;

import java.awt.AWTException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.etouch.common.BaseTest;
import com.etouch.common.CommonMethods;
import com.etouch.common.TafExecutor;
import com.etouch.connsPages.ConnsMainPage;
import com.etouch.connsPages.ConnsStoreLocatorPage;
import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.datamanager.excel.annotations.IExcelDataFiles;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.taf.webui.selenium.MobileView;
import com.etouch.taf.webui.selenium.WebPage;

//import mx4j.log.Logger;

//@Test(groups = "HomePage")
@IExcelDataFiles(excelDataFiles = { "CreditAppData=testData" })
public class Conns_Store_Locator_Page extends BaseTest {
	static String platform;
	static Log log = LogUtil.getLog(Conns_Store_Locator_Page.class);
	static String AbsolutePath = TafExecutor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	private String url = null;
	private WebPage webPage;
	private ConnsMainPage mainPage;
	String testBedName;
	TestBed testBed;
	MobileView mobileView;
	Path path;
	String DataFilePath;
	String testType;
	static protected String browserName;
	String testEnv; 
	CommonMethods commonMethods;
	ConnsStoreLocatorPage connsStoreLocatorPage;
	String storeLocatorURL="";
	String[][] commonData;
	protected static LinkedHashMap<Long, WebPage> webPageMap = new LinkedHashMap<Long, WebPage>();

	@BeforeClass(alwaysRun = true)
	public void setUp(ITestContext context) throws InterruptedException, FileNotFoundException, IOException {
		try {
			testBedName = context.getCurrentXmlTest().getAllParameters().get("testBedName");
			CommonUtil.sop("Test bed Name is " + testBedName);
			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			connsStoreLocatorPage= new ConnsStoreLocatorPage();
			commonMethods = new CommonMethods();
			browserName = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getBrowser().getName();
			System.out.println("Test Type is : " + testType);
			try {
				testEnv = System.getenv().get("Environment");
				System.out.println("testEnv is : " + testEnv);
				path = Paths.get(TestBedManager.INSTANCE.getProfile().getXlsDataConfig().get("testData"));
				DataFilePath = path.toAbsolutePath().toString().replace("Env", testEnv);
				System.out.println("DataFilePath After is : " + DataFilePath);
				commonData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator", "storeLocatorCommonElements");
				storeLocatorURL=commonData[0][0];
				platform = testBed.getPlatform().getName().toUpperCase();
				url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL();
				synchronized (this) {
					webPage = new WebPage(context);
					mainPage = new ConnsMainPage(url, webPage);
					webPageMap.put(Thread.currentThread().getId(), webPage);
				}
			} catch (Exception e) {
				log.info("errr is " + e);
				SoftAssertor.addVerificationFailure(e.getMessage());
			}
		}
		catch (Exception e) {
			CommonUtil.sop("Error is for" + testBedName + " -----------" + e);
			SoftAssertor.addVerificationFailure(e.getMessage());
		}
	}

	@AfterTest(alwaysRun = true)
	public void releaseResources() throws IOException, AWTException {
		// SpecializedScreenRecorder.stopVideoRecording();
		//webPage.getDriver().quit();
		webPageMap.get(Thread.currentThread().getId()).getDriver().quit();
	}


	@Test(priority = 101, enabled = true, description = "Verify_StoreLocator_PageTitle")
	public void Verify_StoreLocator_PageTitle() {
		SoftAssert softAssert = new SoftAssert();

		try{
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "StoreLocator", "verifyPageTitle");
			String ExpectedTitle = test[0][1];
			commonMethods.navigateToPage(webPage,storeLocatorURL, softAssert);
			connsStoreLocatorPage.closeLocationPopup(webPage,softAssert);
			CommonMethods.waitForWebElement(By.xpath(commonData[1][1]), webPage); //Added By Rajesh
			softAssert.assertEquals(ExpectedTitle, webPage.getPageTitle(),"Page Title verification failed. Expected title - " + ExpectedTitle + " Actual title - "+ webPage.getPageTitle());
			softAssert.assertAll();
		}catch(Throwable e){
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_StoreLocator_PageTitle");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}

	}

	@Test(priority = 102, enabled = true, description = "Verify_HomePlus_Component")
	public void Verify_HomePlus_Component() {
		SoftAssert softAssert = new SoftAssert();
		try{
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "StoreLocator", "verifyHomePlusComponent");
			CommonMethods.waitForWebElement(By.xpath(commonData[1][1]), webPage);
			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, test[0][0], softAssert),"Element not present using locator - " + test[0][0]);
			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, test[1][0], softAssert),
					"Element not present using locator - " + test[1][0]);
			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, test[2][0], softAssert),
					"Element not present using locator - " + test[2][0]);
			String homeplusText1 = commonMethods.getTextbyXpath(webPage, test[0][1], softAssert);
			softAssert.assertTrue(homeplusText1.contains(test[0][2]),"Text verification failed. Expected text : " + test[0][2] + " Actual text : " + homeplusText1);
			String homeplusText2 = commonMethods.getTextbyXpath(webPage, test[1][1], softAssert);
			softAssert.assertTrue(homeplusText2.contains(test[1][2]),"Text verification failed. Expected text : " + test[1][2] + " Actual text : " + homeplusText2);
			softAssert.assertAll();
		}catch(Throwable e){
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_HomePlus_Component");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 103, enabled = true, description = "Verify_ChoseYourRegion_Links")
	public void Verify_ChoseYourRegion_Links() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try{
			String[][] regionLinksData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyChoseYourRegionLinks");
			List<WebElement> linkList = commonMethods.findElementsByXpath(webPage,commonData[6][1] , softAssert);
			commonMethods.verifyBrokenLinksForGivenLinks(webPage, linkList);
			for (int i = 0; i < regionLinksData.length; i++) {
				if (testType.equalsIgnoreCase("Web") && (!browserName.equalsIgnoreCase("Safari"))) {
					if(!regionLinksData[i][3].equalsIgnoreCase("NA")){
						commonMethods.hoverOnelementbyXpath(webPage, regionLinksData[i][1], softAssert);
						String afterLinkHover = commonMethods.getCssvaluebyXpath(webPage, regionLinksData[i][1], "color", softAssert);
						log.info("Region "+regionLinksData[i][0]+" after hover color attribute value is : "+afterLinkHover);
						softAssert.assertEquals(afterLinkHover, regionLinksData[i][3],"Hover functionality failed for link "+regionLinksData[i][0]+" Expected color: "+regionLinksData[i][3]+" Actual color: "+afterLinkHover);	
					}
				}
				String actual_hrefValue = commonMethods.getAttributebyXpath(webPage, regionLinksData[i][1],"href",softAssert);
				softAssert.assertEquals(actual_hrefValue, regionLinksData[i][2],"href value verification failed for link: '" + regionLinksData[i][0] + "'. Expected href value: "+ regionLinksData[i][2] + " Actual URL: " + actual_hrefValue);
			}
			softAssert.assertAll();
		}catch(Throwable e){
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_ChoseYourRegion_Links");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 104, enabled = true, description = "Verify_Texas_SubLinks")
	public void Verify_Texas_SubLinks() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
		try{
			String[][] TexasSubLinksData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator", "verifyTexasSubLinks");			
			commonMethods.navigateToPage(webPage,storeLocatorURL, softAssert);
			connsStoreLocatorPage.closeLocationPopup(webPage,softAssert);
			if(testType.equalsIgnoreCase("Mobile")){
				webPage.scrollDown(1);
				Thread.sleep(1000);				
			}					
			//commonMethods.clickElementbyXpath(webPage, commonData[8][1], softAssert);			
			/** Asim Code : commented old code for clicking and using Javascript for clicking as old clicking method is not responding for the page****/	
			
			WebElement element_1 = webPage.getDriver().findElement(By.xpath(commonData[8][1]));					
			js.executeScript("arguments[0].click();", element_1);		
			List<WebElement> texasSublinkList = commonMethods.findElementsByXpath(webPage,commonData[7][1] , softAssert);
			commonMethods.verifyBrokenLinksForGivenLinks(webPage, texasSublinkList);
			for (int i = 0; i < TexasSubLinksData.length; i++) {
				if (testType.equalsIgnoreCase("Web") && (!browserName.equalsIgnoreCase("Safari"))) {
					if(!TexasSubLinksData[i][4].equalsIgnoreCase("NA")){
						commonMethods.hoverOnelementbyXpath(webPage, TexasSubLinksData[i][2], softAssert);
						String afterLinkHover = commonMethods.getCssvaluebyXpath(webPage, TexasSubLinksData[i][2], "color", softAssert);
						softAssert.assertEquals(afterLinkHover, TexasSubLinksData[i][4],"Hover functionality failed for link "+TexasSubLinksData[i][0]+" Expected color: "+TexasSubLinksData[i][4]+" Actual color: "+afterLinkHover);
					}
				}
				String textOnLink = commonMethods.getTextbyXpath(webPage, TexasSubLinksData[i][2], softAssert);
				softAssert.assertTrue(textOnLink.contains(TexasSubLinksData[i][0]),"Link text verification failed. Expected text : " + TexasSubLinksData[i][0] + " Actual text : "+ textOnLink);
				String actual_hrefValue = commonMethods.getAttributebyXpath(webPage, TexasSubLinksData[i][2],"href", softAssert);
				softAssert.assertEquals(actual_hrefValue, TexasSubLinksData[i][3],"href value verification failed for link : '" + TexasSubLinksData[i][0] + "'. Expected href value: "+ TexasSubLinksData[i][3] + " Actual href value: " + actual_hrefValue);
			}
			softAssert.assertAll();
		}catch(Throwable e){
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_Texas_SubLinks");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 105, enabled = true, description = "Verify_Order_of_Links")
	public void Verify_Order_of_Links() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try{
		String[][] choseYourLinkData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator", "verifyOrderofLinks");
		commonMethods.navigateToPage(webPage, storeLocatorURL, softAssert);
		connsStoreLocatorPage.closeLocationPopup(webPage,softAssert);
		CommonMethods.waitForWebElement(By.xpath(commonData[1][1]), webPage);
		for (int i = 0; i < choseYourLinkData.length; i++) {
			String textOnLink = commonMethods.getTextbyXpath(webPage, choseYourLinkData[i][1], softAssert);
			softAssert.assertEquals(textOnLink, choseYourLinkData[i][0],"Alphabetical order verification failed for links. Expected text : " + choseYourLinkData[i][0]+ " Actual text : " + textOnLink);
		}
		softAssert.assertAll();
		}catch(Throwable e){
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_Order_of_Links");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 106, enabled = true, description = "Verify_RegionMap_ToolTip")
	public void Verify_RegionMap_ToolTip() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try{
			String[][] verifyRegionMapData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyRegionMapToolTip");
			if (testType.equalsIgnoreCase("Web")) {
				CommonMethods.waitForWebElement(By.xpath(commonData[1][1]), webPage);
				for (int i = 0; i < verifyRegionMapData.length; i++) {
					String attributeValue = commonMethods.getAttributebyXpath(webPage, verifyRegionMapData[i][1], "title", softAssert);
					softAssert.assertEquals(attributeValue, verifyRegionMapData[i][0],
							"Tooltip verification failed for link. Expected Text : " + verifyRegionMapData[i][0]
									+ " Actual text : " + attributeValue);
				}
				softAssert.assertAll();
			}
		}catch(Throwable e){
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_RegionMap_ToolTip");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(enabled = true, priority = 107, description = "Verify_FindStore_AlertBox")
	public void Verify_FindStore_AlertBox() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
		try{
			String[][] verifyFindStoreAlertBoxData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator",
					"verifyFindStoreAlertBox");
			commonMethods.navigateToPage(webPage, storeLocatorURL, softAssert);
			connsStoreLocatorPage.closeLocationPopup(webPage,softAssert);
			CommonMethods.waitForWebElement(By.xpath(commonData[1][1]), webPage);
			commonMethods.clearTextBox(webPage, verifyFindStoreAlertBoxData[0][0], softAssert);
			if(testBedName.contains("iPadNative")||testBedName.contains("iPhoneNative")||testBedName.equalsIgnoreCase("Safari")||testBedName.equalsIgnoreCase("InternetExplorer")){				
				js.executeScript("window.alert = function(){ return true;}");
				//commonMethods.clickElementbyXpath(webPage, verifyFindStoreAlertBoxData[1][0], softAssert);
				
				/** Asim Code : commented old code for clicking and using Javascript for clicking as old clicking method is not responding for the page****/					
				WebElement element_1 = webPage.getDriver().findElement(By.xpath(verifyFindStoreAlertBoxData[1][0]));					
				js.executeScript("arguments[0].click();", element_1);					
			}else{
				/*commonMethods.clickElementbyXpath(webPage, verifyFindStoreAlertBoxData[1][0], softAssert);*/
				
				/** Asim Code : commented old code for clicking and using Javascript for clicking as old clicking method is not responding for the page****/
				WebElement element_1 = webPage.getDriver().findElement(By.xpath(verifyFindStoreAlertBoxData[1][0]));					
				js.executeScript("arguments[0].click();", element_1);
				
				Alert alert = webPage.getDriver().switchTo().alert();
				String alertActualText = alert.getText();
				log.info("Alert box text: "+alertActualText);
				alert.accept();
				softAssert.assertEquals(alertActualText, verifyFindStoreAlertBoxData[0][1],"Expected Text : " + verifyFindStoreAlertBoxData[1][1] + " Actual Text : " + alertActualText);
			}	
			softAssert.assertAll();
		}catch(Throwable e){
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_FindStore_AlertBox");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(enabled = true, priority = 108, description = "Verify_FindStore_for_InvalidData")
	public void Verify_FindStore_for_InvalidData() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try{
			String[][] verifyFindStoreInvalidData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyFindStoreInvalidData");
			commonMethods.navigateToPage(webPage, storeLocatorURL, softAssert);
			connsStoreLocatorPage.closeLocationPopup(webPage, softAssert);
			CommonMethods.waitForWebElement(By.xpath(commonData[1][1]), webPage);
			commonMethods.clearTextBox(webPage, verifyFindStoreInvalidData[1][0], softAssert);
			commonMethods.sendKeysbyXpath(webPage, verifyFindStoreInvalidData[1][0], verifyFindStoreInvalidData[0][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, verifyFindStoreInvalidData[2][0], softAssert);
			if(testBedName.contains("iPadNative")||testBedName.contains("iPhoneNative")||testBedName.equalsIgnoreCase("Safari")||testBedName.equalsIgnoreCase("InternetExplorer")){
				commonMethods.waitForGivenTime(7, softAssert);
			}
			CommonMethods.waitForWebElement(By.xpath(verifyFindStoreInvalidData[3][0]), webPage);
			String errorMsgActualText = commonMethods.getTextbyXpath(webPage, verifyFindStoreInvalidData[3][0], softAssert);
			String errorMessageActualColor = commonMethods.getCssvaluebyXpath(webPage, verifyFindStoreInvalidData[3][0],"color", softAssert);
			softAssert.assertEquals(errorMessageActualColor, verifyFindStoreInvalidData[2][1],"Color attribute verification failed. Expected Color : " + verifyFindStoreInvalidData[2][1]+ " Actual Color : " + errorMessageActualColor);
			String errorBoxActualColor = commonMethods.getCssvaluebyXpath(webPage, verifyFindStoreInvalidData[1][0],"color", softAssert);
			softAssert.assertEquals(errorBoxActualColor, verifyFindStoreInvalidData[3][1],"Color attribute verification failed. Expected Color : " + verifyFindStoreInvalidData[3][1]+ " Actual Color : " + errorBoxActualColor);
			softAssert.assertEquals(errorMsgActualText, verifyFindStoreInvalidData[1][1],"Expected Text : " + verifyFindStoreInvalidData[1][1] + " Actual Text : " + errorMsgActualText);
			softAssert.assertAll();
		}catch(Throwable e){
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_FindStore_for_InvalidData");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(enabled = true, priority = 109, description = "Verify_FindStore_for_ValidData")
	public void Verify_FindStore_for_ValidData() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try{
			String[][] verifyValidRegionSearchData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator",
					"verifyValidRegionSearchData");
			commonMethods.navigateToPage(webPage, storeLocatorURL, softAssert);
			connsStoreLocatorPage.closeLocationPopup(webPage, softAssert);
			CommonMethods.waitForWebElement(By.xpath(commonData[1][1]), webPage);
			commonMethods.clearTextBox(webPage, verifyValidRegionSearchData[1][0], softAssert);
			commonMethods.sendKeysbyXpath(webPage, verifyValidRegionSearchData[1][0], verifyValidRegionSearchData[0][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, verifyValidRegionSearchData[2][0], softAssert);
			commonMethods.waitForGivenTime(5, softAssert);
			//CommonMethods.waitForWebElement(By.xpath(verifyValidRegionSearchData[3][0]), webPage);
			String regionPageActualData = commonMethods.getTextbyXpath(webPage, verifyValidRegionSearchData[3][0], softAssert);
			softAssert.assertTrue(regionPageActualData.contains(verifyValidRegionSearchData[1][1]),
					"Text verification failed. Expected Text : " + verifyValidRegionSearchData[1][1] + " Actual Text : "
							+ regionPageActualData);

			softAssert.assertAll();
		}catch(Throwable e){
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_FindStore_for_ValidData");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(enabled = true, priority = 110, description = "Verify_FindStore_with_Zipcode")
	public void Verify_FindStore_with_Zipcode() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try{
			String[][] verifyZipCodeRegionSearchData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator",
					"verifyZipCodeRegionSearch");
			commonMethods.navigateToPage(webPage, storeLocatorURL, softAssert);
			connsStoreLocatorPage.closeLocationPopup(webPage, softAssert);
			CommonMethods.waitForWebElement(By.xpath(commonData[1][1]), webPage);
			commonMethods.clearTextBox(webPage, verifyZipCodeRegionSearchData[1][0], softAssert);
			commonMethods.sendKeysbyXpath(webPage, verifyZipCodeRegionSearchData[1][0],
					verifyZipCodeRegionSearchData[0][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, verifyZipCodeRegionSearchData[2][0], softAssert);
			CommonMethods.waitForWebElement(By.xpath(verifyZipCodeRegionSearchData[3][0]), webPage);
			String regionPageActualData = commonMethods.getTextbyXpath(webPage, verifyZipCodeRegionSearchData[3][0], softAssert);
			softAssert.assertTrue(regionPageActualData.contains(verifyZipCodeRegionSearchData[1][1]),
					"Text verification failed. Expected Text : " + verifyZipCodeRegionSearchData[1][1] + " Actual Text : "
							+ regionPageActualData);
			softAssert.assertAll();
		}catch(Throwable e){
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_FindStore_with_Zipcode");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(enabled = true, priority = 111, description = "Verify_FindStore_with_Zipcode_and_Radius")
	public void Verify_FindStore_with_Zipcode_and_Radius() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try{
			String[][] verifyZipCodeRadiusSearchData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator",
					"verifyZipCodeRadiusSearch");
			commonMethods.navigateToPage(webPage, storeLocatorURL, softAssert);
			connsStoreLocatorPage.closeLocationPopup(webPage, softAssert);
			CommonMethods.waitForWebElement(By.xpath(commonData[1][1]), webPage);
			commonMethods.clearTextBox(webPage, verifyZipCodeRadiusSearchData[1][0], softAssert);
			commonMethods.sendKeysbyXpath(webPage, verifyZipCodeRadiusSearchData[1][0],
					verifyZipCodeRadiusSearchData[0][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, verifyZipCodeRadiusSearchData[2][0], softAssert);
			CommonMethods.waitForWebElement(By.xpath(verifyZipCodeRadiusSearchData[3][0]), webPage);
			String search50MilesActualData = commonMethods.getTextbyXpath(webPage, verifyZipCodeRadiusSearchData[3][0], softAssert);
			search50MilesActualData = search50MilesActualData.replace("mi","").replaceAll(" ", "");
			int intSearch50MilesActualData=Double.valueOf(search50MilesActualData).intValue();;
			softAssert.assertTrue(intSearch50MilesActualData>=2 && intSearch50MilesActualData<=4,"Text verification failed. Store distance not in specified range. Expected distance: "+verifyZipCodeRadiusSearchData[1][1]+" Actual distance: "+intSearch50MilesActualData);
			Select select = new Select(commonMethods.getWebElementbyXpath(webPage, verifyZipCodeRadiusSearchData[4][0], softAssert));
			select.selectByValue(verifyZipCodeRadiusSearchData[2][1]);
			commonMethods.clickElementbyXpath(webPage, verifyZipCodeRadiusSearchData[5][0], softAssert);
			CommonMethods.waitForWebElement(By.xpath(verifyZipCodeRadiusSearchData[6][0]), webPage);
			String search75MilesActualData = commonMethods.getTextbyXpath(webPage, verifyZipCodeRadiusSearchData[6][0], softAssert);
			search75MilesActualData = search75MilesActualData.replace("mi","").replaceAll(" ", "");
			int intSearch75MilesActualData=Double.valueOf(search75MilesActualData).intValue();;
			softAssert.assertTrue(intSearch75MilesActualData>=70 && intSearch75MilesActualData<=73,"Text verification failed. Store distance not in specified range. Expected distance: "+verifyZipCodeRadiusSearchData[4][1]+" Actual distance: "+intSearch75MilesActualData);
			select = new Select(commonMethods.getWebElementbyXpath(webPage, verifyZipCodeRadiusSearchData[4][0], softAssert));
			select.selectByValue(verifyZipCodeRadiusSearchData[3][1]);
			commonMethods.clickElementbyXpath(webPage, verifyZipCodeRadiusSearchData[5][0], softAssert);
			CommonMethods.waitForWebElement(By.xpath(verifyZipCodeRadiusSearchData[7][0]), webPage);
			String search125MilesActualData = commonMethods.getTextbyXpath(webPage, verifyZipCodeRadiusSearchData[7][0], softAssert);
			search125MilesActualData = search125MilesActualData.replace("mi","").replaceAll(" ", "");
			int intSearch125MilesActualData=Double.valueOf(search125MilesActualData).intValue();;
			softAssert.assertTrue(intSearch125MilesActualData>=108 && intSearch125MilesActualData<=112,"Text verification failed. Store distance not in specified range. Expected distance: "+verifyZipCodeRadiusSearchData[5][1]+" Actual distance: "+intSearch125MilesActualData);
			softAssert.assertAll();
		}catch(Throwable e){
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_FindStore_with_Zipcode_and_Radius");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	@Test(enabled = true, priority = 112, description = "Verify_FindStore_with_CityName")
	public void Verify_FindStore_with_CityName() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try{
			String[][] verifyCitySearchData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyCitySearch");
			commonMethods.navigateToPage(webPage, storeLocatorURL, softAssert);
			connsStoreLocatorPage.closeLocationPopup(webPage, softAssert);
			CommonMethods.waitForWebElement(By.xpath(commonData[1][1]), webPage);
			commonMethods.clearTextBox(webPage, verifyCitySearchData[1][0], softAssert);
			commonMethods.sendKeysbyXpath(webPage, verifyCitySearchData[1][0],
					verifyCitySearchData[0][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, verifyCitySearchData[2][0], softAssert);
			CommonMethods.waitForWebElement(By.xpath(verifyCitySearchData[3][0]), webPage);
			String cityPageActualData = commonMethods.getTextbyXpath(webPage, verifyCitySearchData[3][0], softAssert);
			softAssert.assertTrue(cityPageActualData.contains(verifyCitySearchData[1][1]),
					"Text verification failed. Expected Text : " + verifyCitySearchData[1][1] + " Actual Text : "
							+ cityPageActualData);
			softAssert.assertAll();
		}catch(Throwable e){
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_FindStore_with_CityName");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	@Test(enabled = true, priority = 113, description = "Verify_FindStore_with_CityName_and_Radius")
	public void Verify_FindStore_with_CityName_and_Radius() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try{
			String[][] verifyCityRadiusSearchData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator",
					"verifyCityRadiusSearch");
			commonMethods.navigateToPage(webPage, storeLocatorURL, softAssert);
			connsStoreLocatorPage.closeLocationPopup(webPage, softAssert);
			CommonMethods.waitForWebElement(By.xpath(commonData[1][1]), webPage);
			commonMethods.clearTextBox(webPage, verifyCityRadiusSearchData[1][0], softAssert);
			commonMethods.sendKeysbyXpath(webPage, verifyCityRadiusSearchData[1][0],
					verifyCityRadiusSearchData[0][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, verifyCityRadiusSearchData[2][0], softAssert);
			CommonMethods.waitForWebElement(By.xpath(verifyCityRadiusSearchData[3][0]), webPage);
			String search50MilesActualData = commonMethods.getTextbyXpath(webPage, verifyCityRadiusSearchData[3][0], softAssert);
			search50MilesActualData = search50MilesActualData.replace("mi","").replaceAll(" ", "");
			int intSearch50MilesActualData=Double.valueOf(search50MilesActualData).intValue();;

			softAssert.assertTrue(intSearch50MilesActualData>=8 && intSearch50MilesActualData<=10,"Text verification failed. Store distance not in specified range. Expected distance: "+verifyCityRadiusSearchData[1][1]+" Actual distance: "+intSearch50MilesActualData);			
			Select select = new Select(commonMethods.getWebElementbyXpath(webPage, verifyCityRadiusSearchData[4][0], softAssert));
			select.selectByValue(verifyCityRadiusSearchData[2][1]);
			commonMethods.clickElementbyXpath(webPage, verifyCityRadiusSearchData[5][0], softAssert);
			CommonMethods.waitForWebElement(By.xpath(verifyCityRadiusSearchData[6][0]), webPage);
			String search75MilesActualData = commonMethods.getTextbyXpath(webPage, verifyCityRadiusSearchData[6][0], softAssert);
			search75MilesActualData = search75MilesActualData.replace("mi","").replaceAll(" ", "");
			int intSearch75MilesActualData=Double.valueOf(search75MilesActualData).intValue();;			
			softAssert.assertTrue(intSearch75MilesActualData>=73 && intSearch75MilesActualData<=75,"Text verification failed. Store distance not in specified range. Expected distance: "+verifyCityRadiusSearchData[4][1]+" Actual distance: "+intSearch75MilesActualData);
			select = new Select(commonMethods.getWebElementbyXpath(webPage, verifyCityRadiusSearchData[4][0], softAssert));
			select.selectByValue(verifyCityRadiusSearchData[3][1]);
			commonMethods.clickElementbyXpath(webPage, verifyCityRadiusSearchData[5][0], softAssert);
			CommonMethods.waitForWebElement(By.xpath(verifyCityRadiusSearchData[7][0]), webPage);
			String search125MilesActualData = commonMethods.getTextbyXpath(webPage, verifyCityRadiusSearchData[7][0], softAssert);
			search125MilesActualData = search125MilesActualData.replace("mi","").replaceAll(" ", "");
			int intSearch125MilesActualData=Double.valueOf(search125MilesActualData).intValue();;			
			softAssert.assertTrue(intSearch125MilesActualData>=100 && intSearch125MilesActualData<=102,"Text verification failed. Store distance not in specified range. Expected distance: "+verifyCityRadiusSearchData[5][1]+" Actual distance: "+intSearch125MilesActualData);
			softAssert.assertAll();
		}catch(Throwable e){
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_FindStore_with_CityName_and_Radius");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	@Test(enabled = true, priority = 114, description = "Verify_View_All_Link")
	public void Verify_View_All_Link() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try{
			String[][] verifyViewAllLinkData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyViewAllLink");
			commonMethods.navigateToPage(webPage, storeLocatorURL, softAssert);
			connsStoreLocatorPage.closeLocationPopup(webPage, softAssert);
			CommonMethods.waitForWebElement(By.xpath(commonData[1][1]), webPage);
			if(testType.equalsIgnoreCase("Mobile")){
				//webPage.scrollBottom();
				//webPage.scrollDown(3);
				((JavascriptExecutor)webPage.getDriver()).executeScript("window.scrollBy(0,500)");
				
			}
			String actualLinkText = commonMethods.getTextbyXpath(webPage, verifyViewAllLinkData[0][0], softAssert);
			softAssert.assertTrue(actualLinkText.contains(verifyViewAllLinkData[0][1]), "Text verification failed. Expected text : " + verifyViewAllLinkData[0][1] + " Actual text : " + actualLinkText);
			List<String> actualCssValues= commonMethods.getFontProperties(webPage, verifyViewAllLinkData[0][0], softAssert);
			if(testType.equalsIgnoreCase("Web")){
				softAssert.assertTrue(actualCssValues.get(0).contains(verifyViewAllLinkData[1][1]), "CSS value verification failed for link " + verifyViewAllLinkData[0][1] + "Expected Value : "+ verifyViewAllLinkData[1][1] + " Actual Value : " + actualCssValues.get(0));
			}else{
				softAssert.assertTrue(actualCssValues.get(0).contains(verifyViewAllLinkData[5][1]), "CSS value verification failed for link " + verifyViewAllLinkData[0][1] + "Expected Value : "+ verifyViewAllLinkData[5][1] + " Actual Value : " + actualCssValues.get(0));
			}
			softAssert.assertTrue(actualCssValues.get(1).contains(verifyViewAllLinkData[2][1]),"CSS value verification failed for link " + verifyViewAllLinkData[0][1] + "Expected Value : "+ verifyViewAllLinkData[2][1] + " Actual Value : " + actualCssValues.get(1));
			softAssert.assertTrue(actualCssValues.get(2).toLowerCase().replaceAll("'", "").replaceAll("\"", "").contains(verifyViewAllLinkData[3][1]),"CSS value verification failed for link " + verifyViewAllLinkData[0][1] + "Expected Value : "+ verifyViewAllLinkData[3][1] + " Actual Value : " + actualCssValues.get(2));				
			String actualUrl = commonMethods.clickAndGetPageURL(webPage, verifyViewAllLinkData[0][0], verifyViewAllLinkData[0][1], softAssert,commonData[2][1]);
			softAssert.assertTrue(actualUrl.contains(verifyViewAllLinkData[4][1]),"URL verification failed for link : '" + verifyViewAllLinkData[0][1] + "'. Expected URL - "+ verifyViewAllLinkData[4][1] + " Actual URL - " + actualUrl);
			softAssert.assertAll();
		}catch(Throwable e){
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_View_All_Link");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	@Test(enabled = true, priority = 115, description = "Verify_All_Store_Locator_Links")
	public void Verify_All_Store_Locator_Links() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
		try{
			String[][] verifyViewAllLinkData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyViewAllLink");
			commonMethods.navigateToPage(webPage, storeLocatorURL, softAssert);
			connsStoreLocatorPage.closeLocationPopup(webPage, softAssert);
			CommonMethods.waitForWebElement(By.xpath(commonData[1][1]), webPage);
			if(testType.equalsIgnoreCase("Mobile")){
				webPage.scrollBottom();
			}
			/*commonMethods.clickElementbyXpath(webPage, verifyViewAllLinkData[0][0], softAssert);*/
			
			/** Asim Code : commented old code for clicking and using Javascript for clicking as old clicking method is not responding for the page****/			
			WebElement element_1 = webPage.getDriver().findElement(By.xpath(verifyViewAllLinkData[0][0]));					
			js.executeScript("arguments[0].click();", element_1);	
			List<WebElement> allStorelinkList = commonMethods.findElementsByXpath(webPage,commonData[9][1] , softAssert);
			commonMethods.verifyBrokenLinksForGivenLinks(webPage, allStorelinkList);
			softAssert.assertAll();
		}catch(Throwable e){
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_All_Store_Locator_Links");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	@Test(enabled = true, priority = 116, description = "Verify_Visit_Store_Page_Link")
	public void Verify_Visit_Store_Page_Link() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
		try{
			String[][] verifyStorePageData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyVisitStorePageLink");
			String storeText="";
			for(int i=0;i<verifyStorePageData.length;i++){
				commonMethods.navigateToPage(webPage, storeLocatorURL, softAssert);
				connsStoreLocatorPage.closeLocationPopup(webPage, softAssert);
				CommonMethods.waitForWebElement(By.xpath(commonData[1][1]), webPage);
				if(testType.equalsIgnoreCase("Mobile")){
					webPage.scrollDown(1);
				}
				//commonMethods.clickElementbyXpath(webPage, verifyStorePageData[i][1],softAssert);
				
				/** Asim Code : commented old code for clicking and using Javascript for clicking as old clicking method is not responding for the page****/					
				WebElement element_1 = webPage.getDriver().findElement(By.xpath(verifyStorePageData[i][1]));					
				js.executeScript("arguments[0].click();", element_1);	
				CommonMethods.waitForWebElement(By.xpath(commonData[2][1]), webPage);
				String actualUrl = commonMethods.clickAndGetPageURL(webPage, verifyStorePageData[i][3], verifyStorePageData[i][0]+" -"+verifyStorePageData[i][2], softAssert,commonData[5][1]);
				softAssert.assertTrue(actualUrl.contains(verifyStorePageData[i][4]),"Expected - "+verifyStorePageData[i][4]+"Actual - "+actualUrl);
				if(testType.equalsIgnoreCase("Web")){
					storeText = commonMethods.getTextbyXpath(webPage, verifyStorePageData[i][5], softAssert);	
				}else{
					storeText = commonMethods.getTextbyXpath(webPage, verifyStorePageData[i][9], softAssert);
				}
				if(browserName.equalsIgnoreCase("edge")){
					storeText = storeText.replaceAll("(?m) +$", "");
				}
				softAssert.assertTrue(storeText.contains(verifyStorePageData[i][6]),"Text verification failed. Expected text: " + verifyStorePageData[i][6] + " Actual text: " + storeText);
				CommonMethods.waitForWebElement(By.xpath(verifyStorePageData[i][7]), webPage);
				String customerReviewText = commonMethods.getTextbyXpath(webPage, verifyStorePageData[i][7], softAssert);
				softAssert.assertTrue(customerReviewText.contains(verifyStorePageData[i][8]),"Text verification failed. Expected text: " + verifyStorePageData[i][8] + " Actual text: " + customerReviewText);
			}
			softAssert.assertAll();
		}catch(Throwable e){
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_Visit_Store_Page_Link");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	@Test(enabled = true, priority = 117, description = "Verify_Store_Distance_In_Miles")
	public void Verify_Store_Distance_In_Miles() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try{
			String[][] verifyStoreDistanceInMilesData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyStoreDistanceInMiles");
			commonMethods.navigateToPage(webPage, storeLocatorURL, softAssert);
			connsStoreLocatorPage.closeLocationPopup(webPage, softAssert);
			CommonMethods.waitForWebElement(By.xpath(commonData[1][1]), webPage);
			commonMethods.clearTextBox(webPage, verifyStoreDistanceInMilesData[1][0], softAssert);
			commonMethods.sendKeysbyXpath(webPage, verifyStoreDistanceInMilesData[1][0],verifyStoreDistanceInMilesData[0][1], softAssert);
			Select select = new Select(commonMethods.getWebElementbyXpath(webPage, verifyStoreDistanceInMilesData[4][0], softAssert));
			select.selectByValue(verifyStoreDistanceInMilesData[2][1]);
			commonMethods.clickElementbyXpath(webPage, verifyStoreDistanceInMilesData[2][0], softAssert);
			String actualUrl = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertTrue(actualUrl.contains(verifyStoreDistanceInMilesData[5][1]),"URL mismatch. Expected URL: "+verifyStoreDistanceInMilesData[5][1]+", Actual URL: "+actualUrl);
			CommonMethods.waitForWebElement(By.xpath(verifyStoreDistanceInMilesData[3][0]), webPage);
			String search10MilesActualData = commonMethods.getTextbyXpath(webPage, verifyStoreDistanceInMilesData[3][0], softAssert);
			search10MilesActualData = search10MilesActualData.replace("mi","").replaceAll(" ", "");
			int intSearch10MilesActualData=Double.valueOf(search10MilesActualData).intValue();;
			softAssert.assertTrue(intSearch10MilesActualData>=6 && intSearch10MilesActualData<=7,"Text verification failed. Store distance not in specified range. Expected distance: "+verifyStoreDistanceInMilesData[1][1]+" Actual distance: "+intSearch10MilesActualData);
			String search10MilesActualColor = commonMethods.getCssvaluebyXpath(webPage, verifyStoreDistanceInMilesData[3][0], "color", softAssert);
			softAssert.assertTrue(search10MilesActualColor.contains(verifyStoreDistanceInMilesData[6][1]),"CSS value verification failed for " + search10MilesActualData + ". Expected value: "+ verifyStoreDistanceInMilesData[6][1] + ", Actual value: " + search10MilesActualColor);
			select = new Select(commonMethods.getWebElementbyXpath(webPage, verifyStoreDistanceInMilesData[4][0], softAssert));
			select.selectByValue(verifyStoreDistanceInMilesData[3][1]);
			commonMethods.clickElementbyXpath(webPage, verifyStoreDistanceInMilesData[5][0], softAssert);
			CommonMethods.waitForWebElement(By.xpath(verifyStoreDistanceInMilesData[6][0]), webPage);
			String search100MilesActualData = commonMethods.getTextbyXpath(webPage, verifyStoreDistanceInMilesData[6][0], softAssert);
			search100MilesActualData = search100MilesActualData.replace("mi","").replaceAll(" ", "");
			int intSearch100MilesActualData=Double.valueOf(search100MilesActualData).intValue();;
			softAssert.assertTrue(intSearch100MilesActualData>=94 && intSearch100MilesActualData<=97,"Text verification failed. Store distance not in specified range. Expected distance: "+verifyStoreDistanceInMilesData[4][1]+" Actual distance: "+intSearch100MilesActualData);
			String search100MilesActualColor = commonMethods.getCssvaluebyXpath(webPage, verifyStoreDistanceInMilesData[6][0], "color", softAssert);
			softAssert.assertTrue(search100MilesActualColor.contains(verifyStoreDistanceInMilesData[6][1]),"CSS value verification failed for " + search100MilesActualData + ". Expected value: "+ verifyStoreDistanceInMilesData[6][1] + ", Actual value: " + search100MilesActualColor);			
			softAssert.assertAll();
		}catch(Throwable e){
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_Store_Distance_In_Miles");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	@Test(enabled = true, priority = 118, description = "Verify_All_Store_Locator_Page_Text")
	public void Verify_All_Store_Locator_Page_Text() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
		try{
			String[][] verifyAllStoreLocatorPageTextData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyAllStoreLocatorPageText");
			commonMethods.navigateToPage(webPage, storeLocatorURL, softAssert);
			connsStoreLocatorPage.closeLocationPopup(webPage, softAssert);
			CommonMethods.waitForWebElement(By.xpath(commonData[1][1]), webPage);
			if(testType.equalsIgnoreCase("Mobile")){
				webPage.scrollBottom();
			}
			//commonMethods.clickElementbyXpath(webPage, verifyAllStoreLocatorPageTextData[0][0],softAssert);
			
			/** Asim Code : commented old code for clicking and using Javascript for clicking as old clicking method is not responding for the page****/			
			WebElement element_1 = webPage.getDriver().findElement(By.xpath(verifyAllStoreLocatorPageTextData[0][0]));					
			js.executeScript("arguments[0].click();", element_1);	
			String actualUrl = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertTrue(actualUrl.contains(verifyAllStoreLocatorPageTextData[2][1]),"URL verification failed. Expected - "+verifyAllStoreLocatorPageTextData[2][1]+"Actual - "+actualUrl);
			CommonMethods.waitForWebElement(By.xpath(verifyAllStoreLocatorPageTextData[1][0]), webPage);
			String allStoreLocatorPageText = commonMethods.getTextbyXpath(webPage, verifyAllStoreLocatorPageTextData[1][0], softAssert);
			softAssert.assertEquals(allStoreLocatorPageText, verifyAllStoreLocatorPageTextData[1][1],"Text verification failed. Expected text: " + verifyAllStoreLocatorPageTextData[1][1] + " Actual text: " + allStoreLocatorPageText);
			softAssert.assertAll();
		}catch(Throwable e){
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_All_Store_Locator_Page_Text");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	@Test(priority = 119, enabled = true, description = "Verify_AllRegion_PageDescription")
	public void Verify_AllRegion_PageDescription() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try{
			String[][] allRegionDescriptiondata = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyAllRegionPageDescription");
			String key = "";
			for (int i = 0; i < allRegionDescriptiondata.length; i++) {
				commonMethods.navigateToPage(webPage, storeLocatorURL, softAssert);
				connsStoreLocatorPage.closeLocationPopup(webPage, softAssert);			
				key = allRegionDescriptiondata[i][0];
				String[][] keyData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator", key + "Region");
				try{
					CommonMethods.waitForWebElement(By.xpath(commonData[1][1]), webPage);	
				}catch(Exception e){
					e.getLocalizedMessage();
				}
				
				
				/******old code **********/
				/*if(testType.equalsIgnoreCase("Mobile")){
					webPage.scrollDown(1);
				}*/
				
				if(testType.equalsIgnoreCase("Mobile")){
					if(i <= 8){
						webPage.scrollDown(1);
				}else {
						webPage.scrollDown(2);
				}
					
				}
				//commonMethods.clickWithChildElementbyXpath(webPage, allRegionDescriptiondata[i][1], allRegionDescriptiondata[i][2],allRegionDescriptiondata[i][0], softAssert);
				/****old code commented above *********/
				commonMethods.clickWithChildElementby_UsingJavaScriptXpath(webPage, allRegionDescriptiondata[i][1], allRegionDescriptiondata[i][2],allRegionDescriptiondata[i][0], softAssert);

				try{
					CommonMethods.waitForWebElement(By.xpath(allRegionDescriptiondata[i][5]), webPage);	
					
				}catch(Exception e){
					e.printStackTrace();
					e.getLocalizedMessage();
				}
				
				Thread.sleep(1000);
				
				String storeDescriptionText = commonMethods.getTextbyXpath(webPage, allRegionDescriptiondata[i][3], softAssert);
			    softAssert.assertTrue(storeDescriptionText.contains(allRegionDescriptiondata[i][4]),
						"Store locator description text verification failed. Expected text : "
								+ allRegionDescriptiondata[i][4] + " Actual text : " + storeDescriptionText);
				
				String breadCrumbsActualText = commonMethods.getTextbyXpath(webPage, allRegionDescriptiondata[i][7], softAssert);
				
				softAssert.assertTrue(breadCrumbsActualText.contains(allRegionDescriptiondata[i][8]),
						"Bread Crumbs verification failed. Expected text : "
								+ allRegionDescriptiondata[i][8] + " Actual text : " + breadCrumbsActualText);
				String actual_hrefYesmoneyLink = commonMethods.getAttributebyXpath(webPage, allRegionDescriptiondata[i][5],"href", softAssert);
				
				softAssert.assertEquals(actual_hrefYesmoneyLink, allRegionDescriptiondata[i][6],
						"href value verification failed for link : '" + allRegionDescriptiondata[i][0] + "'. Expected href value for Yesmoney link: "
								+ allRegionDescriptiondata[i][6] + " Actual href value for Yesmoney link: " + actual_hrefYesmoneyLink);
			
				for (int j = 0; j < keyData.length; j++) {
					String subkey = keyData[j][0];
					try{
						CommonMethods.waitForWebElement(By.xpath(commonData[2][1]), webPage);	
					}catch(Exception e){
						e.printStackTrace();
						e.getLocalizedMessage();
					}
					if(!browserName.equalsIgnoreCase("edge")){
						String pageContentText = commonMethods.getTextbyXpath(webPage, subkey, softAssert);
						if(testType.equalsIgnoreCase("Mobile")){
							pageContentText=pageContentText.replace("Mon-Fri", "Store Hours\nMon-Fri");
						}
						softAssert.assertTrue(pageContentText.contains(keyData[j][1]),"Text verification failed for region "+allRegionDescriptiondata[i][0]+". Expected Text : " + keyData[j][1] + " Actual Text : " + pageContentText);
					}
					String actual_onclickValueGoogleMap = commonMethods.getAttributebyXpath(webPage, keyData[j][2], "onclick",softAssert);
					softAssert.assertTrue(actual_onclickValueGoogleMap.contains(keyData[j][3]),"onclick attribute value verification failed for region "+allRegionDescriptiondata[i][0]+". Expected onclick value: "+keyData[j][3]+". Actual onclick value: "+actual_onclickValueGoogleMap);
				}
			}
			softAssert.assertAll();
		}catch(Throwable e){
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_AllRegion_PageDescription");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	@Test(enabled = true, priority = 120, description = "Verify Power Review Section on Store Page")
	public void Verify_Power_Review_Section() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
		try{
			String[][] verifyValidRegionSearchData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyPowerReview");
			System.out.println("verifyValidRegionSearchData"+verifyValidRegionSearchData.length);
			commonMethods.navigateToPage(webPage, storeLocatorURL, softAssert);
			connsStoreLocatorPage.closeLocationPopup(webPage, softAssert);
			CommonMethods.waitForWebElement(By.xpath(commonData[1][1]), webPage);
			commonMethods.clearTextBox(webPage, verifyValidRegionSearchData[1][0], softAssert);
			commonMethods.sendKeysbyXpath(webPage, verifyValidRegionSearchData[1][0], verifyValidRegionSearchData[0][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, verifyValidRegionSearchData[2][0], softAssert);
			commonMethods.waitForGivenTime(5, softAssert);
			//CommonMethods.waitForWebElement(By.xpath(verifyValidRegionSearchData[3][0]), webPage);
			String regionPageActualData = commonMethods.getTextbyXpath(webPage, verifyValidRegionSearchData[3][0], softAssert);
			softAssert.assertTrue(regionPageActualData.contains(verifyValidRegionSearchData[1][1]),
					"Text verification failed. Expected Text : " + verifyValidRegionSearchData[1][1] + " Actual Text : "
							+ regionPageActualData);
			System.out.println(" *************************verifyValidRegionSearchData[4][0]********************************** :  "+verifyValidRegionSearchData[4][0]);			
			
			
			 /***commenting below mentioned functionality as it is no longer available across UAT,RWD and Prod environment ****/
			// Asim Code : commented old code for clicking and using Javascript for clicking as old clicking method is not responding for the page****/			

			//commonMethods.clickElementbyXpath(webPage, verifyValidRegionSearchData[4][0], softAssert);
			//commonMethods.clickElementbyXpath(webPage, "(.//*[@id='pr-category-snippets-119']/section/section[1]/div)[1]/div[1]", softAssert);
					
			/** Asim Code : commented old code xpath got changed and for clicking instead of selenium library methods,used Javascript for clicking as old clicking method is not responding for the page****/			
			WebElement element_1 = webPage.getDriver().findElement(By.xpath(verifyValidRegionSearchData[4][0]));					
			js.executeScript("arguments[0].click();", element_1);
			Thread.sleep(30000);	
			
			/*WebElement element_2 = webPage.getDriver().findElement(By.xpath(".//*[@id='pr-category-snippets-123']//section[contains(@class,'pr-category-snippet__rating')])[1]"));	
			CommonMethods.waitForWebElement(By.xpath(verifyValidRegionSearchData[3][0]), webPage);
			js.executeScript("arguments[0].click();", element_2);	
			Thread.sleep(1000);*/
			
			//executing this query will scroll until that element is not appeared on page.
			Thread.sleep(1000);
			commonMethods.waitForPageLoad(webPage, softAssert);
			 WebElement element = webPage.getDriver().findElement(By.xpath(" .//*[@id='StoreLocatorStore']/div[2]/div[2]/div[1]/h2"));			 
			js.executeScript("arguments[0].scrollIntoView(true);",element);
			String[][] reviewElements = ExcelUtil.readExcelData(DataFilePath, "StoreLocator",
					"verifyPowerReviewElements");
			for(int i=0;i<reviewElements.length;i++)
			{
				System.out.println("****************************************Verifying if element is present :*****************************************"+reviewElements[i][0]);
				softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, reviewElements[i][1], softAssert),"Element not present "+reviewElements[i][0]+" locator - " + reviewElements[i][1]);
			}
			
			softAssert.assertAll();
		}
		
		catch(Throwable e){
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_Power_Review_Section");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
}
