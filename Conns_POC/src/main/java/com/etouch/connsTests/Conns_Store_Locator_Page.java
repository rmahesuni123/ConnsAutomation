package com.etouch.connsTests;

import java.awt.AWTException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.openqa.selenium.WebElement;
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


@IExcelDataFiles(excelDataFiles = { "CreditAppData=testData" })
public class Conns_Store_Locator_Page extends BaseTest {
	static String platform;
	static Log log = LogUtil.getLog(Conns_Store_Locator_Page.class);
	static String AbsolutePath = TafExecutor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	private String url = null;
	private WebPage webPage;
	private ConnsMainPage mainPage;
	//String testBedName;
	TestBed testBed;
	MobileView mobileView;
	Path path;
	String DataFilePath;
	String testType;
	static protected String testBedName;
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
			testBedName = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getBrowser().getName();
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
		webPageMap.get(Thread.currentThread().getId()).getDriver().quit();
	}


	@Test(priority = 101, enabled = true, description = "Verify_StoreLocator_PageTitle")
	public void Verify_StoreLocator_PageTitle() {
		SoftAssert softAssert = new SoftAssert();
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "StoreLocator", "verifyPageTitle");
		String ExpectedTitle = test[0][1];
		String locator = commonData[1][1];
		try{
			ConnsStoreLocatorPage.Store_Locator_Page_Basic_Functionality(webPage,storeLocatorURL,locator, softAssert);
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
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "StoreLocator", "verifyHomePlusComponent");
		String locator = commonData[1][1];
		String elementPresenceLocator = "";
		String Locator_Text_1_Xpath = test[0][1];
		String Expected_Text_1 = test[0][2];
		String Locator_Text_2_Xpath = test[1][1];
		String Expected_Text_2 = test[1][2];
		String homeplusText1 = "";
		String homeplusText2 = "";		
		try{
			ConnsStoreLocatorPage.Store_Locator_Page_Basic_Functionality(webPage,storeLocatorURL,locator, softAssert);
			for (int i=0;i<test.length;i++){
				elementPresenceLocator = test[i][0];
				boolean verifyElementPresence = ConnsStoreLocatorPage.verifyElementPresence(webPage,test,elementPresenceLocator,softAssert);
				softAssert.assertTrue((verifyElementPresence),"Element not present using locator - " + test[i][0]);
			}
			homeplusText1 = ConnsStoreLocatorPage.getTextbyXpath(webPage, Locator_Text_1_Xpath,softAssert);
			softAssert.assertTrue(homeplusText1.contains(Expected_Text_1),"Text verification failed. Expected text : " + Expected_Text_1 + " Actual text : " + homeplusText1);
			homeplusText2 = ConnsStoreLocatorPage.getTextbyXpath(webPage, Locator_Text_2_Xpath,softAssert);
			softAssert.assertTrue(homeplusText2.contains(Expected_Text_2),"Text verification failed. Expected text : " + Expected_Text_2 + " Actual text : " + homeplusText2);
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
		String[][] regionLinksData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyChoseYourRegionLinks");
		String locator = commonData[6][1];
		String Locator_0 = "";
		String Locator_1 = "";
		String Locator_2 = "";
		String Locator_3 = "";
		String afterLinkHover = "";
		String actual_hrefValue = "";
		List<WebElement> linkList = null;
		boolean Verify_ChoseYourRegion_Links = false;
		try{
			ConnsStoreLocatorPage.Store_Locator_Page_Basic_Functionality(webPage,storeLocatorURL,locator, softAssert);
			linkList = ConnsStoreLocatorPage.findElementsByXpath(webPage,locator , softAssert);
			ConnsStoreLocatorPage.verifyBrokenLinksForGivenLinks(webPage, linkList);
			for (int i = 0; i < regionLinksData.length; i++) {
				 Locator_0 = regionLinksData[i][0];
				 Locator_1 = regionLinksData[i][1];
				 Locator_2 = regionLinksData[i][2];
				 Locator_3 = regionLinksData[i][3] ;
				if (testType.equalsIgnoreCase("Web") && (!testBedName.equalsIgnoreCase("Safari")) || (!testBedName.equalsIgnoreCase("IE") || (!testBedName.equalsIgnoreCase("InternetExplorer") ))) {
				if(!(Locator_3.equalsIgnoreCase("NA"))){
				afterLinkHover = ConnsStoreLocatorPage.color_afterLinkHoverFunctionality(webPage,Verify_ChoseYourRegion_Links,regionLinksData,Locator_0,Locator_1,Locator_3,Locator_2,testBedName,testType,testBedName, softAssert);
				softAssert.assertEquals(afterLinkHover, Locator_3,"Hover functionality failed for link "+Locator_0 +" Expected color: "+Locator_3 +" Actual color: "+afterLinkHover);	
				}
				}

				if (testType.equalsIgnoreCase("Web") && (!testBedName.equalsIgnoreCase("Safari"))) {
					if(!Locator_1.equalsIgnoreCase("NA")){
				actual_hrefValue = ConnsStoreLocatorPage.href_getAttributebyXpath(webPage,regionLinksData, Locator_1,softAssert);
				softAssert.assertEquals(actual_hrefValue, Locator_2,"href value verification failed for link: '" + Locator_0 + "'. Expected href value: "+ Locator_2 + " Actual URL: " + actual_hrefValue);
					}
				}
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
		String[][] TexasSubLinksData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator", "verifyTexasSubLinks");
		String locator_1 = commonData[8][1];
		String locator_2 = commonData[7][1];
		String Locator_0 = "";
		String Locator_2 = "";
		String Locator_3 = "";
		String Locator_4 = "";
		String afterLinkHover = "";
		String textOnLink = "";
		String actual_hrefValue = "";	
		boolean Verify_Texas_SubLinks = true;
		try{
			ConnsStoreLocatorPage.Store_Locator_Page_Basic_Functionality(webPage,storeLocatorURL,locator_1, softAssert);
			if(testType.equalsIgnoreCase("Mobile")){
				webPage.scrollDown(1);
				Thread.sleep(1000);				
			}					
			ConnsStoreLocatorPage.clickElementByJS(webPage,locator_1, softAssert);
			List<WebElement> texasSublinkList = commonMethods.findElementsByXpath(webPage,locator_2 , softAssert);
			ConnsStoreLocatorPage.verifyBrokenLinksForGivenLinks(webPage, texasSublinkList);
			for (int i = 0; i < TexasSubLinksData.length; i++) {
				 Locator_0 = TexasSubLinksData[i][0];
				 Locator_2 = TexasSubLinksData[i][2];
				 Locator_3 = TexasSubLinksData[i][3];
				 Locator_4 = TexasSubLinksData[i][4] ;
				 if (testType.equalsIgnoreCase("Web") && (!testBedName.equalsIgnoreCase("Safari"))) {
						if(!Locator_4.equalsIgnoreCase("NA")){
				 afterLinkHover = ConnsStoreLocatorPage.color_afterLinkHoverFunctionality(webPage,Verify_Texas_SubLinks,TexasSubLinksData,Locator_4,Locator_0,Locator_2,Locator_3,testBedName,testType,testBedName, softAssert);
				 softAssert.assertEquals(afterLinkHover, Locator_4,"Hover functionality failed for link "+Locator_0+" Expected color: "+Locator_4+" Actual color: "+afterLinkHover);
						}}
				 textOnLink = ConnsStoreLocatorPage.getTextbyXpath(webPage, Locator_2,softAssert);
				 softAssert.assertTrue(textOnLink.contains(Locator_0),"Link text verification failed. Expected text : " + Locator_0 + " Actual text : "+ textOnLink);
				 actual_hrefValue = ConnsStoreLocatorPage.href_getAttributebyXpath(webPage,TexasSubLinksData, Locator_2,softAssert);
				 softAssert.assertEquals(actual_hrefValue, Locator_3,"href value verification failed for link: '" + Locator_0 + "'. Expected href value: "+ Locator_3 + " Actual href value :  " + actual_hrefValue);
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
		String[][] choseYourLinkData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator", "verifyOrderofLinks");
		String locator = commonData[1][1];
		String Locator_1 = "";
		String Locator_2 = "";
		String textOnLink = "";
		try{
			ConnsStoreLocatorPage.Store_Locator_Page_Basic_Functionality(webPage,storeLocatorURL,locator, softAssert);
		for (int i = 0; i < choseYourLinkData.length; i++) {
			Locator_1 = choseYourLinkData[i][1];
			Locator_2 = choseYourLinkData[i][0];
			textOnLink = ConnsStoreLocatorPage.getTextbyXpath(webPage, Locator_1,softAssert);
			softAssert.assertEquals(textOnLink, Locator_2,"Alphabetical order verification failed for links. Expected text : " + Locator_2 + " Actual text : " + textOnLink);
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
		String locator_1 = commonData[1][1];
		String Locator_1 = "";
		String Locator_0 = "";
		String attributeValue = "";
		String[][] verifyRegionMapData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyRegionMapToolTip");
		try{
			if (testType.equalsIgnoreCase("Web")) {
				ConnsStoreLocatorPage.Store_Locator_Page_Basic_Functionality(webPage,storeLocatorURL,locator_1, softAssert);
				for (int i = 0; i < verifyRegionMapData.length; i++) {
					Locator_1 = verifyRegionMapData[i][1];
					Locator_0 = verifyRegionMapData[i][0];
					attributeValue = ConnsStoreLocatorPage.getAttributebyXpath(webPage,Locator_1, softAssert);
					softAssert.assertEquals(attributeValue, Locator_0,"Tooltip verification failed for link. Expected Text : " + Locator_0	+ " Actual text : " + attributeValue);				}
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
		String[][] verifyFindStoreAlertBoxData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyFindStoreAlertBox");
		String locator_1 =  commonData[1][1];
		String locator_0 = verifyFindStoreAlertBoxData[0][0];
		String Locator_0 = verifyFindStoreAlertBoxData[1][0];
		String Expected_Text =  verifyFindStoreAlertBoxData[0][1];
		String alert_Expected_Text = verifyFindStoreAlertBoxData[1][1];
		String alertActualText = "";
		try{	
			
			ConnsStoreLocatorPage.pageBasicFunctionality(webPage,storeLocatorURL,locator_1,locator_0,softAssert);
			ConnsStoreLocatorPage.clickElementByJS(webPage,Locator_0, softAssert);				

			if(testBedName.contains("iPadNative")||testBedName.contains("iPhoneNative")||testBedName.equalsIgnoreCase("Safari")||testBedName.equalsIgnoreCase("InternetExplorer")){				
				log.info("Looking For Alert Box : ");
//				ConnsStoreLocatorPage.clickElementByJS(webPage,Locator_0, softAssert);				
			}else{				
				//ConnsStoreLocatorPage.clickElementByJS(webPage,locator_3, softAssert);
				log.info("Looking For Alert Box : "+Locator_0);
				alertActualText = ConnsStoreLocatorPage.AlertBoxHandling(webPage, softAssert);					
				softAssert.assertEquals(alertActualText, Expected_Text,"Expected Text : " + alert_Expected_Text + " Actual Text : " + alertActualText);
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
		String[][] verifyFindStoreInvalidData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyFindStoreInvalidData");
		String Locator_1 = commonData[1][1];
		String Locator_2 = verifyFindStoreInvalidData[1][0];
		String Locator_3 = verifyFindStoreInvalidData[0][1];
		String Locator_4 = verifyFindStoreInvalidData[2][0]; 
		String Locator_5 = verifyFindStoreInvalidData[3][0] ;
		String errorMessageExpectedColor = verifyFindStoreInvalidData[2][1] ;
		String errorMsgExpectedText = verifyFindStoreInvalidData[1][1];
		String errorBoxExpectedColor = verifyFindStoreInvalidData[3][1];
		String errorMsgActualText = "";
		String errorMessageActualColor = "";
		String errorBoxActualColor = "";
		try{
			ConnsStoreLocatorPage.find_Store_Page_Basic_Functionality(webPage,storeLocatorURL,Locator_1,Locator_2,Locator_3,Locator_4,Locator_5,testBedName,softAssert);			
			errorMsgActualText = ConnsStoreLocatorPage.getTextbyXpath(webPage, Locator_5,softAssert);
			errorMessageActualColor = ConnsStoreLocatorPage.getCssvaluebyXpath(webPage, Locator_5,softAssert);			
			errorBoxActualColor = ConnsStoreLocatorPage.getCssvaluebyXpath(webPage, Locator_2,softAssert);
			
			softAssert.assertEquals(errorMessageActualColor, errorMessageExpectedColor,"Color attribute verification failed. Expected Color : " + errorMessageExpectedColor+ " Actual Color : " + errorMessageActualColor);
			softAssert.assertEquals(errorBoxActualColor,errorBoxExpectedColor,"Color attribute verification failed. Expected Color : " + errorBoxExpectedColor+ " Actual Color : " + errorBoxActualColor);
			softAssert.assertEquals(errorMsgActualText,errorMsgExpectedText,"Expected Text : " + errorMsgExpectedText + " Actual Text : " + errorMsgActualText);
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
		String[][] verifyValidRegionSearchData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyValidRegionSearchData");
		String Locator_1 = commonData[1][1];
		String Locator_2 = verifyValidRegionSearchData[1][0];
		String Locator_3 = verifyValidRegionSearchData[0][1];
		String Locator_4 = verifyValidRegionSearchData[2][0];
		String Locator_5 = verifyValidRegionSearchData[3][0];
		String regionPageExpectedData = verifyValidRegionSearchData[1][1];
		String regionPageActualData = "";
		try{
			ConnsStoreLocatorPage.find_Store_Page_Basic_Functionality(webPage,storeLocatorURL,Locator_1,Locator_2,Locator_3,Locator_4,Locator_5,testBedName,softAssert);
			regionPageActualData = ConnsStoreLocatorPage.getTextbyXpath(webPage, Locator_5,softAssert);			
			softAssert.assertTrue(regionPageActualData.contains(regionPageExpectedData),"Text verification failed. Expected Text : " + regionPageExpectedData + " Actual Text : "+ regionPageActualData);
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
		String[][] verifyZipCodeRegionSearchData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyZipCodeRegionSearch");
		String Locator_1 = commonData[1][1];
		String Locator_2 = verifyZipCodeRegionSearchData[1][0];
		String Locator_3 = verifyZipCodeRegionSearchData[0][1];
		String Locator_4 = verifyZipCodeRegionSearchData[2][0];
		String regionPageDataXpath = verifyZipCodeRegionSearchData[3][0];
		String regionPageExpectedData = verifyZipCodeRegionSearchData[1][1];
		String regionPageActualData = "";
		try{
			ConnsStoreLocatorPage.find_Store_Page_Basic_Functionality(webPage,storeLocatorURL,Locator_1,Locator_2,Locator_3,Locator_4,regionPageDataXpath,testBedName,softAssert);
			regionPageActualData =  ConnsStoreLocatorPage.getTextbyXpath(webPage, regionPageDataXpath,softAssert);	
			softAssert.assertTrue(regionPageActualData.contains(regionPageExpectedData),"Text verification failed. Expected Text : " + regionPageExpectedData + " Actual Text : "+ regionPageActualData);
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
		String[][] verifyZipCodeRadiusSearchData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyZipCodeRadiusSearch");
		String locator_1 = commonData[1][1];
		String locator_0 = verifyZipCodeRadiusSearchData[0][1];
		String Locator_1 = verifyZipCodeRadiusSearchData[1][0];	
		String Locator_2=  verifyZipCodeRadiusSearchData[2][0];
		String Locator_3 = verifyZipCodeRadiusSearchData[3][0];
		String Locator_4 = verifyZipCodeRadiusSearchData[4][0];
		String Locator_5 = verifyZipCodeRadiusSearchData[5][0];
		String Locator_6 = verifyZipCodeRadiusSearchData[6][0];
		String Locator_7 = verifyZipCodeRadiusSearchData[7][0];
		String Locator_8 = verifyZipCodeRadiusSearchData[2][1];
		String Locator_9 = verifyZipCodeRadiusSearchData[3][1];
		int intSearch50MilesActualData = 0;
		int intSearch75MilesActualData = 0;
		int intSearch125MilesActualData = 0;
		try{
			ConnsStoreLocatorPage.find_Store_Page_Basic_Functionality(webPage,storeLocatorURL,locator_1,Locator_1,locator_0,Locator_2,Locator_3,testBedName,softAssert);
			intSearch50MilesActualData = ConnsStoreLocatorPage.textbyXpathAssertion(webPage, Locator_3,softAssert);
			softAssert.assertTrue(intSearch50MilesActualData>=2 && intSearch50MilesActualData<=4,"Text verification failed. Store distance not in specified range. Expected distance: "+verifyZipCodeRadiusSearchData[1][1]+" Actual distance: "+intSearch50MilesActualData);
			ConnsStoreLocatorPage.find_Store_Page_selectDropdownByValue_clickElementByJS_And_WaitForWebElement_Functionality(webPage, Locator_4,Locator_8,Locator_5,Locator_6, softAssert);
			intSearch75MilesActualData = ConnsStoreLocatorPage.textbyXpathAssertion(webPage, Locator_6,softAssert);
			softAssert.assertTrue(intSearch75MilesActualData>=70 && intSearch75MilesActualData<=73,"Text verification failed. Store distance not in specified range. Expected distance: "+verifyZipCodeRadiusSearchData[4][1]+" Actual distance: "+intSearch75MilesActualData);
			
			ConnsStoreLocatorPage.find_Store_Page_selectDropdownByValue_clickElementByJS_And_WaitForWebElement_Functionality(webPage, Locator_4,Locator_9,Locator_5,Locator_7, softAssert);
			intSearch125MilesActualData = ConnsStoreLocatorPage.textbyXpathAssertion(webPage, Locator_7,softAssert);
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
		String[][] verifyCitySearchData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyCitySearch");		
		String locator_1 = commonData[1][1];
		String locator_2 = verifyCitySearchData[0][1];
		String Locator_1 = verifyCitySearchData[1][0];	
		String Locator_2= verifyCitySearchData[2][0];
		String Locator_3 = verifyCitySearchData[3][0];
		String cityPageExpectedData = verifyCitySearchData[1][1];
		String cityPageActualData = "";
		try{
			ConnsStoreLocatorPage.find_Store_Page_Basic_Functionality(webPage,storeLocatorURL,locator_1,Locator_1,locator_2,Locator_2,Locator_3,testBedName,softAssert);
			cityPageActualData = ConnsStoreLocatorPage.getTextbyXpath(webPage, Locator_3,softAssert);		
			softAssert.assertTrue(cityPageActualData.contains(cityPageExpectedData),"Text verification failed. Expected Text : " + cityPageExpectedData + " Actual Text : "	+ cityPageActualData);
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
		String[][] verifyCityRadiusSearchData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyCityRadiusSearch");
		String locator_1 = commonData[1][1];
		String locator_2 = verifyCityRadiusSearchData[0][1];
		String Locator_1 = verifyCityRadiusSearchData[1][0];	
		String Locator_2 = verifyCityRadiusSearchData[2][0];
		String Locator_3 = verifyCityRadiusSearchData[3][0];
		String Locator_4 = verifyCityRadiusSearchData[4][0];
		String Locator_5 = verifyCityRadiusSearchData[5][0];
		String Locator_6 = verifyCityRadiusSearchData[6][0];
		String Locator_7 = verifyCityRadiusSearchData[7][0];
		String Locator_8 = verifyCityRadiusSearchData[2][1];
		String Locator_9 = verifyCityRadiusSearchData[3][1];
		int intSearch50MilesActualData = 0;
		int intSearch75MilesActualData = 0;
		int intSearch125MilesActualData = 0;


		try{
			ConnsStoreLocatorPage.find_Store_Page_Basic_Functionality(webPage,storeLocatorURL,locator_1,Locator_1,locator_2,Locator_2,Locator_3,testBedName,softAssert);
			intSearch50MilesActualData = ConnsStoreLocatorPage.textbyXpathAssertion(webPage, Locator_3,softAssert);
			softAssert.assertTrue(intSearch50MilesActualData>=8 && intSearch50MilesActualData<=10,"Text verification failed. Store distance not in specified range. Expected distance: "+verifyCityRadiusSearchData[1][1]+" Actual distance: "+intSearch50MilesActualData);			
			
			ConnsStoreLocatorPage.find_Store_Page_selectDropdownByValue_clickElementByJS_And_WaitForWebElement_Functionality(webPage, Locator_4,Locator_8,Locator_5,Locator_6, softAssert);
			intSearch75MilesActualData = ConnsStoreLocatorPage.textbyXpathAssertion(webPage, Locator_6,softAssert);
			softAssert.assertTrue(intSearch75MilesActualData>=73 && intSearch75MilesActualData<=75,"Text verification failed. Store distance not in specified range. Expected distance: "+verifyCityRadiusSearchData[4][1]+" Actual distance: "+intSearch75MilesActualData);
			
			ConnsStoreLocatorPage.find_Store_Page_selectDropdownByValue_clickElementByJS_And_WaitForWebElement_Functionality(webPage, Locator_4,Locator_9,Locator_5,Locator_7, softAssert);
			intSearch125MilesActualData = ConnsStoreLocatorPage.textbyXpathAssertion(webPage, Locator_7,softAssert);
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
		String[][] verifyViewAllLinkData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyViewAllLink");
		String locator_1 = commonData[1][1];
		String locator_2 = commonData[2][1];
		String Actual_Text_Xpath = verifyViewAllLinkData[0][0];
		String Expected_Text = verifyViewAllLinkData[0][1];		
		String Locator_1 = verifyViewAllLinkData[1][1];
		String Locator_2 = verifyViewAllLinkData[2][1];
		String Locator_3 = verifyViewAllLinkData[3][1];
		String Locator_4 = verifyViewAllLinkData[4][1];
		String Locator_5 = verifyViewAllLinkData[5][1];
		String actualLinkText = "";
		List<String> actualCssValues ;

		try{
			webPage.getDriver().manage().window().maximize();
			ConnsStoreLocatorPage.Store_Locator_Page_Basic_Functionality(webPage,storeLocatorURL,locator_1, softAssert);
			ConnsStoreLocatorPage.mobile_Scroll_Down(webPage,testType, softAssert);			
			actualLinkText = ConnsStoreLocatorPage.getTextbyXpath(webPage, Actual_Text_Xpath, softAssert);
			softAssert.assertTrue(actualLinkText.contains(Expected_Text), "Text verification failed. Expected text : " + Expected_Text + " Actual text : " + actualLinkText);
			actualCssValues= ConnsStoreLocatorPage.getFontProperties(webPage, Actual_Text_Xpath, softAssert);
			if(testType.equalsIgnoreCase("Web")){
				softAssert.assertTrue(actualCssValues.get(0).contains(Locator_1), "CSS value verification failed for font size " + Expected_Text + "Expected Value : "+ Locator_1 + " Actual Value : " + actualCssValues.get(0));
			}
			else {
				softAssert.assertTrue(actualCssValues.get(0).contains(Locator_5), "CSS value verification failed for font size " + Expected_Text + "Expected Value : "+ Locator_5 + " Actual Value : " + actualCssValues.get(0));
			}

			softAssert.assertTrue(actualCssValues.get(1).contains(Locator_2),"CSS value verification failed for color " + Expected_Text + "Expected Value : "+ Locator_2 + " Actual Value : " + actualCssValues.get(1));
			softAssert.assertTrue(actualCssValues.get(2).toLowerCase().replaceAll("'", "").replaceAll("\"", "").contains(Locator_3),"CSS value verification failed for link " + Expected_Text + "Expected Value : "+ Locator_3 + " Actual Value : " + actualCssValues.get(2));				
			String actualUrl = commonMethods.clickAndGetPageURL(webPage, Actual_Text_Xpath, Expected_Text, softAssert,locator_2);
			softAssert.assertTrue(actualUrl.contains(Locator_4),"URL verification failed for link : '" + Expected_Text + "'. Expected URL - "+ Locator_4 + " Actual URL - " + actualUrl);
			softAssert.assertTrue(actualCssValues.get(2).toLowerCase().replaceAll("'", "").replaceAll("\"", "").contains(Locator_3),"CSS value verification failed for link " + Expected_Text + "Expected Value : "+ Locator_3 + " Actual Value : " + actualCssValues.get(2));				

			
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
		String[][] verifyViewAllLinkData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyViewAllLink");
		String locator_1 = commonData[1][1];
		String Locator_1 = verifyViewAllLinkData[0][0];
		String Locator_2 = commonData[9][1];
		List<WebElement> allStorelinkList;
		try{
			ConnsStoreLocatorPage.Store_Locator_Page_Basic_Functionality(webPage,storeLocatorURL,locator_1, softAssert);
			ConnsStoreLocatorPage.mobile_Scroll_Bottom(webPage,testType, softAssert);			
			ConnsStoreLocatorPage.clickElementByJS(webPage, Locator_1, softAssert);
			allStorelinkList = ConnsStoreLocatorPage.findElementsByXpath(webPage,Locator_2 , softAssert);
			ConnsStoreLocatorPage.verifyBrokenLinksForGivenLinks(webPage, allStorelinkList);
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
		String[][] verifyStorePageData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyVisitStorePageLink");
		String locator_1 = commonData[1][1];
		String Locator_0 = "";
		String Locator_1 = "";
		String Locator_2 = "";
		String Locator_3 = "";
		String Locator_4 = "";
		String Locator_5 = "";
		String Locator_6 = "";
		String Locator_7 = "";
		String Locator_8 = "";
		String Locator_9 = "";
		String actualUrl = "";
		String storeText="";
		String customerReviewText = "";
		
		try{
		
			for(int i=0;i<verifyStorePageData.length;i++){
				Locator_0 = verifyStorePageData[i][0];
				Locator_1 = verifyStorePageData[i][1];
				Locator_2 = verifyStorePageData[i][2];
				Locator_3 = verifyStorePageData[i][3];
				Locator_4 = verifyStorePageData[i][4];
				Locator_5 = verifyStorePageData[i][5];
				Locator_6 = verifyStorePageData[i][6];
				Locator_7 = verifyStorePageData[i][7];
				Locator_8 = verifyStorePageData[i][8];
				Locator_9 = verifyStorePageData[i][9];
				
				ConnsStoreLocatorPage.Store_Locator_Page_Basic_Functionality(webPage,storeLocatorURL,locator_1, softAssert);
				ConnsStoreLocatorPage.mobile_Scroll_Down(webPage,testType,1, softAssert);
				ConnsStoreLocatorPage.clickElementByJS_And_WaitForWebElement(webPage, Locator_1,Locator_2,verifyStorePageData, softAssert);
				actualUrl = ConnsStoreLocatorPage.clickAndGetPageURL(webPage, Locator_3, Locator_0 +" -"+Locator_0, softAssert,commonData[5][1]);
				softAssert.assertTrue(actualUrl.contains(Locator_4),"Expected - "+Locator_4 +"Actual - "+actualUrl);
				if(testType.equalsIgnoreCase("Web")){
					storeText = ConnsStoreLocatorPage.getTextbyXpath(webPage, Locator_5, softAssert);	
				}else{
					storeText = ConnsStoreLocatorPage.getTextbyXpath(webPage, Locator_9, softAssert);
				}
				if(testBedName.equalsIgnoreCase("edge")){
					storeText = storeText.replaceAll("(?m) +$", "");
				}
				softAssert.assertTrue(storeText.contains(Locator_6),"Text verification failed. Expected text: " + Locator_6 + " Actual text: " + storeText);
				ConnsStoreLocatorPage.waitForWebElement(webPage, Locator_7, softAssert);
				customerReviewText = ConnsStoreLocatorPage.getTextbyXpath(webPage, Locator_7, softAssert);
				softAssert.assertTrue(customerReviewText.contains(Locator_8),"Text verification failed. Expected text: " + Locator_8 + " Actual text: " + customerReviewText);
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
		String[][] verifyStoreDistanceInMilesData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyStoreDistanceInMiles");
		String locator_0 = commonData[1][1];
		String Locator_0 = verifyStoreDistanceInMilesData[0][1];
		String Locator_1 = verifyStoreDistanceInMilesData[1][0];	
		String Locator_2 = verifyStoreDistanceInMilesData[2][0];
		String Locator_3 = verifyStoreDistanceInMilesData[3][0];
		String Locator_4 = verifyStoreDistanceInMilesData[4][0];
		String Locator_5 = verifyStoreDistanceInMilesData[5][0];
		String Locator_6 = verifyStoreDistanceInMilesData[6][0];
		String Locator_8 = verifyStoreDistanceInMilesData[1][1];
		String Locator_9 = verifyStoreDistanceInMilesData[2][1];
		String Locator_10 = verifyStoreDistanceInMilesData[3][1];
		String Locator_11 = verifyStoreDistanceInMilesData[4][1];
		String Locator_12 = verifyStoreDistanceInMilesData[5][1];
		String Locator_13 = verifyStoreDistanceInMilesData[6][1];


		int intSearch10MilesActualData = 0;
		int intSearch100MilesActualData = 0;
		String actualUrl = "";
		String search10MilesActualColor = "";
		String Search10MilesActualData = "";
		String search100MilesActualData = "";
		String search100MilesActualColor = "";


		try{
			ConnsStoreLocatorPage.StoreLocatorDistancePageBasicFunctionality (webPage,storeLocatorURL,locator_0,Locator_1,Locator_0, softAssert);
			ConnsStoreLocatorPage.find_Store_Page_selectDropdownByValue_clickElementByJS_And_WaitForWebElement_Functionality(webPage,Locator_4,Locator_9,Locator_2,Locator_12, softAssert);
			actualUrl = ConnsStoreLocatorPage.getPageUrl(webPage, softAssert);
			softAssert.assertTrue(actualUrl.contains(Locator_12),"URL mismatch. Expected URL: "+Locator_12+", Actual URL: "+actualUrl);		
			ConnsStoreLocatorPage.waitForWebElement( webPage,Locator_3);
			Search10MilesActualData = ConnsStoreLocatorPage.getTextbyXpathAssertion(webPage, Locator_3,softAssert);
			intSearch10MilesActualData = ConnsStoreLocatorPage.textbyXpathAssertion(webPage, Locator_3,softAssert);
			softAssert.assertTrue(intSearch10MilesActualData>=6 && intSearch10MilesActualData<=7,"Text verification failed. Store distance not in specified range. Expected distance: "+Locator_8 +" Actual distance: "+intSearch10MilesActualData);
			search10MilesActualColor = ConnsStoreLocatorPage.getCssvaluebyXpath(webPage, Locator_3, "color", softAssert);
			softAssert.assertTrue(search10MilesActualColor.contains(Locator_13),"CSS value verification failed for " + Search10MilesActualData + ". Expected value: "+ Locator_13 + ", Actual value: " + search10MilesActualColor);
			ConnsStoreLocatorPage.find_Store_Page_selectDropdownByValue_clickElementByJS_And_WaitForWebElement_Functionality(webPage, Locator_4,Locator_10,Locator_5,Locator_6, softAssert);
			intSearch100MilesActualData = ConnsStoreLocatorPage.textbyXpathAssertion(webPage, Locator_6,softAssert);
			softAssert.assertTrue(intSearch100MilesActualData>=94 && intSearch100MilesActualData<=97,"Text verification failed. Store distance not in specified range. Expected distance: "+Locator_11+" Actual distance: "+intSearch100MilesActualData);
			search100MilesActualColor = commonMethods.getCssvaluebyXpath(webPage, Locator_6, "color", softAssert);
			softAssert.assertTrue(search100MilesActualColor.contains(Locator_13),"CSS value verification failed for " + search100MilesActualData + ". Expected value: "+ Locator_13 + ", Actual value: " + search100MilesActualColor);			
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
		String[][] verifyAllStoreLocatorPageTextData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyAllStoreLocatorPageText");
		String locator_0 = verifyAllStoreLocatorPageTextData[0][0];
		String Locator_1 = verifyAllStoreLocatorPageTextData[1][0];
		String allStoreLocatorPageExpectedText = verifyAllStoreLocatorPageTextData[1][1];
		String Locator_2 = verifyAllStoreLocatorPageTextData[2][1];
		String actualUrl = "";
		String allStoreLocatorPageText = "";
		try{
			ConnsStoreLocatorPage.Store_Locator_Page_Basic_Functionality(webPage,storeLocatorURL,locator_0, softAssert);
			ConnsStoreLocatorPage.mobile_Scroll_Down(webPage,testType,1, softAssert);
			actualUrl = ConnsStoreLocatorPage.clickElementbyJSAndGetURL(webPage, locator_0,softAssert);
			softAssert.assertTrue(actualUrl.contains(Locator_2),"URL verification failed. Expected - "+Locator_2+"Actual - "+actualUrl);
			ConnsStoreLocatorPage.waitForWebElement( webPage,Locator_1);
			allStoreLocatorPageText = ConnsStoreLocatorPage.getTextbyXpath(webPage, Locator_1, softAssert);
			softAssert.assertEquals(allStoreLocatorPageText, allStoreLocatorPageExpectedText,"Text verification failed. Expected text: " + allStoreLocatorPageExpectedText + " Actual text: " + allStoreLocatorPageText);
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
		String key = "";
		String subkey = "";
		String[][] allRegionDescriptiondata = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyAllRegionPageDescription");
		String locator_1 = commonData[1][1];
		String locator_2 = commonData[2][1];
		String Locator_0 = "";
		String Locator_1 = "";
		String Locator_2 = "";
		String Locator_3 = "";
		String Locator_4 = "";
		String Locator_5 = "";
		String Locator_6 = "";
		String Locator_7 = "";
		String Locator_8 = "";
		String storeDescriptionText = "";
		String breadCrumbsActualText  = "";
		String pageContentText = "";
		try{
			
			for (int i = 0; i < allRegionDescriptiondata.length; i++) {
				key = allRegionDescriptiondata[i][0];
				String[][] keyData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator", key + "Region");				
				Locator_0 = allRegionDescriptiondata[i][0];
				Locator_1 = allRegionDescriptiondata[i][1];
				Locator_2 = allRegionDescriptiondata[i][2];
				Locator_3 = allRegionDescriptiondata[i][3];
				Locator_4 = allRegionDescriptiondata[i][4];
				Locator_5 = allRegionDescriptiondata[i][5];
				Locator_6 = allRegionDescriptiondata[i][6];
				Locator_7 = allRegionDescriptiondata[i][7];
				Locator_8 = allRegionDescriptiondata[i][8];
				locator_2 = commonData[2][1];
				ConnsStoreLocatorPage.Store_Locator_Page_Basic_Functionality(webPage, storeLocatorURL,locator_1, softAssert);
				ConnsStoreLocatorPage.scrollingOperation(webPage,testType,allRegionDescriptiondata,softAssert);	
				ConnsStoreLocatorPage.clickWithChildElementby_UsingJavaScriptXpath(webPage, Locator_1,Locator_2,Locator_0, softAssert);
				ConnsStoreLocatorPage.waitForWebElement(webPage,  Locator_5, softAssert);

				Thread.sleep(3000);
				
				storeDescriptionText = ConnsStoreLocatorPage.getTextbyXpath(webPage, Locator_3, softAssert);
				softAssert.assertTrue(storeDescriptionText.contains(Locator_4),"Store locator description text verification failed. Expected text : "+ Locator_4 + " Actual text : " + storeDescriptionText);
  
			    breadCrumbsActualText = ConnsStoreLocatorPage.getTextbyCss(webPage, Locator_7, softAssert);
			    softAssert.assertTrue(breadCrumbsActualText.contains(Locator_8),"Bread Crumbs verification failed. Expected text : "+ Locator_8 + " Actual text : " + breadCrumbsActualText);
   
			    String actual_hrefYesmoneyLink = ConnsStoreLocatorPage.getAttributebyXpath(webPage, Locator_5,"href", softAssert);	
				softAssert.assertEquals(actual_hrefYesmoneyLink, Locator_6,	"href value verification failed for link : '" + Locator_0 + "'. Expected href value for Yesmoney link: "+ Locator_6 + " Actual href value for Yesmoney link: " + actual_hrefYesmoneyLink);
			    
					for (int j = 0; j < keyData.length; j++) {
						subkey = keyData[j][0];
						String Expected_pageContentText = keyData[j][1];
						String onclickValueGoogleMap_Xpath = keyData[j][2];
						String Expected_onclickValueGoogleMap = keyData[j][3];
						pageContentText = ConnsStoreLocatorPage.pageContentTextFunctionality(webPage, testType,testBedName,keyData,subkey,allRegionDescriptiondata,locator_2,DataFilePath, softAssert);
						softAssert.assertTrue(pageContentText.contains(Expected_pageContentText),"Text verification failed for region "+Locator_0+". Expected Text : " + Expected_pageContentText + " Actual Text : " + pageContentText);
						String actual_onclickValueGoogleMap = ConnsStoreLocatorPage.getAttributebyXpathValue(webPage, onclickValueGoogleMap_Xpath, "onclick",softAssert);
						softAssert.assertTrue(actual_onclickValueGoogleMap.contains(Expected_onclickValueGoogleMap),"onclick attribute value verification failed for region "+Locator_0+". Expected onclick value: "+Expected_onclickValueGoogleMap+". Actual onclick value: "+actual_onclickValueGoogleMap);
				
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
		String[][] verifyValidRegionSearchData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyPowerReview");
		String[][] reviewElements = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyPowerReviewElements");
		String locator_0 = commonData[1][1];
		String Locator_0 = verifyValidRegionSearchData[0][1];
		String Locator_1 = verifyValidRegionSearchData[1][0];
		String Locator_2 = verifyValidRegionSearchData[2][0];
		String Locator_3 = verifyValidRegionSearchData[3][0];
		String Locator_4 = verifyValidRegionSearchData[4][0];
		String Locator_5 = verifyValidRegionSearchData[1][1];
		String Locator_6 = ".//*[@id='StoreLocatorStore']/div[2]/div[2]/div[1]/h2";
		String Locator_7 = "";
		String Locator_8 ="";
		String regionPageActualData = "";
		try{
			ConnsStoreLocatorPage.Store_Page_Basic_Functionality(webPage,storeLocatorURL,locator_0,Locator_1,Locator_0,Locator_2,   softAssert);
			System.out.println("verifyValidRegionSearchData"+verifyValidRegionSearchData.length);
			regionPageActualData = ConnsStoreLocatorPage.getTextbyXpath(webPage, Locator_3, softAssert);
			softAssert.assertTrue(regionPageActualData.contains(Locator_5),	"Text verification failed. Expected Text : " + Locator_5 + " Actual Text : "+ regionPageActualData);
			ConnsStoreLocatorPage.clickElementByJS(webPage, Locator_4, softAssert);
			Thread.sleep(30000);	
			ConnsStoreLocatorPage.ScrollIntoView(webPage,Locator_6, softAssert);
			for(int i=0;i<reviewElements.length;i++)
			{
				Locator_7 = reviewElements[i][0];
				Locator_8 = reviewElements[i][1];
				softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, Locator_8, softAssert),"Element not present "+Locator_7+" locator - " + Locator_8);
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
