package com.etouch.connsTests;

import java.awt.AWTException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
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
import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.datamanager.excel.TafExcelDataProvider;
import com.etouch.taf.core.datamanager.excel.TestParameters;
import com.etouch.taf.core.datamanager.excel.annotations.IExcelDataFiles;
import com.etouch.taf.core.datamanager.excel.annotations.ITafExcelDataProviderInputs;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.taf.webui.ITafElement;
import com.etouch.taf.webui.selenium.MobileView;
import com.etouch.taf.webui.selenium.WebPage;

//import mx4j.log.Logger;

//@Test(groups = "HomePage")
@IExcelDataFiles(excelDataFiles = { "CreditAppData=testData" })
public class TestConnsStoreLocatorPage extends BaseTest {
	static String platform;
	static Log log = LogUtil.getLog(TestConnsStoreLocatorPage.class);
	static String AbsolutePath = TafExecutor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	static String videoLocation = AbsolutePath.substring(0, AbsolutePath.indexOf("/target/classes/")).substring(1)
			.concat("/src/test/resources/testdata/videos");
	private String url = null;
	private WebPage webPage;
	private MobileView mobileView;
	private ConnsMainPage mainPage;
	String testBedName;
	TestBed testBed;
	Path path;
	String DataFilePath;
	String testType;
	String browserName;
	String testEnv; 
	CommonMethods commonMethods = new CommonMethods();
	int a = 0;
	String storeLocatorURL = "https://www.conns.com/store-locator";
	String[][] commonData;

	@BeforeClass(alwaysRun = true)
	public void setUp(ITestContext context) throws InterruptedException, FileNotFoundException, IOException {
		try {
			testBedName = context.getCurrentXmlTest().getAllParameters().get("testBedName");
			CommonUtil.sop("Test bed Name is " + testBedName);
			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			browserName = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getBrowser().getName();
			System.out.println("Test Type is : " + testType);
			try {
				testEnv = System.getenv().get("Environment");
				System.out.println("testEnv is : " + testEnv);
				path = Paths.get(TestBedManager.INSTANCE.getProfile().getXlsDataConfig().get("testData"));
				DataFilePath = path.toAbsolutePath().toString().replace("Env", testEnv);
				System.out.println("DataFilePath After is : " + DataFilePath);
				commonData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator", "storeLocatorCommonElements");

				platform = testBed.getPlatform().getName().toUpperCase();
				if (testType.equalsIgnoreCase("Web")) {
					System.out.println("videoLocation" + videoLocation.toString().replace("Env", testEnv));
				}

				url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL();
				synchronized (this) {
					webPage = new WebPage(context);
					mainPage = new ConnsMainPage(url, webPage);
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

	@AfterTest
	public void releaseResources() throws IOException, AWTException {
		// SpecializedScreenRecorder.stopVideoRecording();
		webPage.getDriver().quit();
	}


	@Test(priority = 1, enabled = true, description = "Verify Store Locator Page title")
	public void verifyStoreLocatorPageTitle() {
		SoftAssert softAssert = new SoftAssert();
		try{
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "StoreLocator", "verifyPageTitle");
			String pageUrl = test[0][0];
			String ExpectedTitle = test[0][1];
			webPage.getDriver().get(pageUrl);
			softAssert.assertEquals(ExpectedTitle, webPage.getPageTitle(),
					"Page Title verification failed. Expected title - " + ExpectedTitle + " Actual title - "
							+ webPage.getPageTitle());
			softAssert.assertAll();
		}catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verifyStoreLocatorPageTitle");
			e.printStackTrace();
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}

	}

	@Test(priority = 2, enabled = true, description = "Verify Find Your Conn's HomePlus component")
	public void verifyHomePlusComponent() {
		SoftAssert softAssert = new SoftAssert();
		try{
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "StoreLocator", "verifyHomePlusComponent");
			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, test[0][0]),
					"Element not present using locator - " + test[0][0]);
			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, test[1][0]),
					"Element not present using locator - " + test[1][0]);
			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, test[2][0]),
					"Element not present using locator - " + test[2][0]);
			String homeplusText1 = commonMethods.getTextbyXpath(webPage, test[0][1]);
			softAssert.assertEquals(homeplusText1, test[0][2],
					"Text verification failed. Expected text : " + test[0][2] + " Actual text : " + homeplusText1);
			String homeplusText2 = commonMethods.getTextbyXpath(webPage, test[1][1]);
			softAssert.assertEquals(homeplusText2, test[1][2],
					"Text verification failed. Expected text : " + test[1][2] + " Actual text : " + homeplusText2);
			softAssert.assertAll();
		}catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verifyHomePlusComponent");
			e.printStackTrace();
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 3, enabled = true, description = "Verify Store locator Region links")
	public void verifyChoseYourRegionLinks() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try{
			String[][] regionLinksData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator",
					"verifyChoseYourRegionLinks");
			for (int i = 0; i < regionLinksData.length; i++) {
				webPage.getDriver().get(storeLocatorURL);
				if (testType.equalsIgnoreCase("Web")) {
					String beforeLinkHover = commonMethods.getCssvaluebyXpath(webPage, regionLinksData[i][1], "color");
					log.info("Region "+regionLinksData[i][0]+" before Hover : "+beforeLinkHover);
					commonMethods.hoverOnelementbyXpath(webPage, regionLinksData[i][1]);
					String afterLinkHover = commonMethods.getCssvaluebyXpath(webPage, regionLinksData[i][1], "color");
					log.info("Region "+regionLinksData[i][0]+" after Hover : "+afterLinkHover);
					softAssert.assertNotEquals(afterLinkHover, beforeLinkHover,
							"CSS value verification failed for link " + regionLinksData[i][0] + ". Value before hover : "
									+ beforeLinkHover + " , Value after hover : " + afterLinkHover);
					String actualUrl = commonMethods.clickAndGetPageURL(webPage, regionLinksData[i][1],
							regionLinksData[i][0]);
					softAssert.assertEquals(actualUrl, regionLinksData[i][2],
							"URL verification failed for link : '" + regionLinksData[i][0] + "'. Expected URL - "
									+ regionLinksData[i][2] + " Actual URL - " + actualUrl);
					softAssert.assertAll();
				}
			}
		}catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verifyChoseYourRegionLinks");
			e.printStackTrace();
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());

		}
	}

	@Test(priority = 4, enabled = true, description = "Verify Texas sub links")
	public void verifyTexasSubLinks() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try{
		String[][] TexasSubLinksData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator", "verifyTexasSubLinks");
		for (int i = 0; i < TexasSubLinksData.length; i++) {
			webPage.getDriver().get(storeLocatorURL);
			commonMethods.clickElementbyXpath(webPage, TexasSubLinksData[i][1]);
			if (testType.equalsIgnoreCase("Web")) {
				String beforeLinkHover = commonMethods.getCssvaluebyXpath(webPage, TexasSubLinksData[i][2], "color");
				commonMethods.hoverOnelementbyXpath(webPage, TexasSubLinksData[i][2]);
				String afterLinkHover = commonMethods.getCssvaluebyXpath(webPage, TexasSubLinksData[i][2], "color");
				softAssert.assertNotEquals(afterLinkHover, beforeLinkHover,
						"CSS value verification failed for link " + TexasSubLinksData[i][0] + ". Value before hover : "
								+ beforeLinkHover + " , Value after hover : " + afterLinkHover);
			}
			String textOnLink = commonMethods.getTextbyXpath(webPage, TexasSubLinksData[i][2]);
			softAssert.assertEquals(textOnLink, TexasSubLinksData[i][0],
					"Link text verification failed. Expected text : " + TexasSubLinksData[i][0] + " Actual text : "
							+ textOnLink);
			String actualUrl = commonMethods.clickAndGetPageURL(webPage, TexasSubLinksData[i][2],
					TexasSubLinksData[i][0]);
			softAssert.assertEquals(actualUrl, TexasSubLinksData[i][3],
					"URL verification failed for link : '" + TexasSubLinksData[i][0] + "'. Expected URL : "
							+ TexasSubLinksData[i][3] + " Actual URL : " + actualUrl);
		}
		softAssert.assertAll();
		}catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verifyTexasSubLinks");
			e.printStackTrace();
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 5, enabled = true, description = "Verify order of Store locator links")
	public void verifyOrderofLinks() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try{
		String[][] choseYourLinkData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator", "verifyOrderofLinks");
		webPage.getDriver().get(storeLocatorURL);
		for (int i = 0; i < choseYourLinkData.length; i++) {
			String textOnLink = commonMethods.getTextbyXpath(webPage, choseYourLinkData[i][1]);
			softAssert.assertEquals(textOnLink, choseYourLinkData[i][0],
					"Alphabetical order verification failed for links. Expected text : " + choseYourLinkData[i][0]
							+ " Actual text : " + textOnLink);
		}
		softAssert.assertAll();
		}catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verifyOrderofLinks");
			e.printStackTrace();
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 6, enabled = true, description = "Verify tool tip text on region links map")
	public void verifyRegionMapToolTip() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try{
		String[][] verifyRegionMapData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator",
				"verifyRegionMapToolTip");
		if (testType.equalsIgnoreCase("Web")) {
			webPage.getDriver().get(storeLocatorURL);
			for (int i = 0; i < verifyRegionMapData.length; i++) {
				String attributeValue = commonMethods.getAttributebyXpath(webPage, verifyRegionMapData[i][1], "title");
				softAssert.assertEquals(attributeValue, verifyRegionMapData[i][0],
						"Tooltip verification failed for link. Expected Text : " + verifyRegionMapData[i][0]
								+ " Actual text : " + attributeValue);
			}
			softAssert.assertAll();
		}
		}catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verifyRegionMapToolTip");
			e.printStackTrace();
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 7, enabled = true, description = "Verify region description for all region pages")
	public void verifyAllRegionPageDescription() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try{
		String[][] allRegionDescriptiondata = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyAllRegionPageDescription");
		for (int i = 0; i < allRegionDescriptiondata.length; i++) {
			webPage.getDriver().get(storeLocatorURL);
			commonMethods.clickElementbyXpath(webPage, allRegionDescriptiondata[i][1], allRegionDescriptiondata[i][2],
					allRegionDescriptiondata[i][0]);
			String storeDescriptionText = commonMethods.getTextbyXpath(webPage, allRegionDescriptiondata[i][3]);
			softAssert.assertTrue(storeDescriptionText.contains(allRegionDescriptiondata[i][4]),
					"Store locator description text verification failed. Expected text : "
							+ allRegionDescriptiondata[i][4] + " Actual text : " + storeDescriptionText);
			String breadCrumbsActualText = commonMethods.getTextbyXpath(webPage, allRegionDescriptiondata[i][7]);
			softAssert.assertTrue(breadCrumbsActualText.contains(allRegionDescriptiondata[i][8]),
					"Bread Crumbs verification failed. Expected text : "
							+ allRegionDescriptiondata[i][8] + " Actual text : " + breadCrumbsActualText);
			String yesmoneyLinkUrl = commonMethods.clickAndGetPageURL(webPage, allRegionDescriptiondata[i][5],
					allRegionDescriptiondata[i][0]);
			softAssert.assertEquals(yesmoneyLinkUrl, allRegionDescriptiondata[i][6],
					"URL verification failed for link : '" + allRegionDescriptiondata[i][0] + "'. Expected URL : "
							+ allRegionDescriptiondata[i][6] + " Actual URL : " + yesmoneyLinkUrl);
		}
		softAssert.assertAll();
		}catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verifyAllRegionPageDescription");
			e.printStackTrace();
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(enabled = true, priority = 8, description = "Verify content for all region pages")
	public void verifyAllRegionsPageContent() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try{
		String[][] regionPageTextdata = ExcelUtil.readExcelData(DataFilePath, "StoreLocator",
				"verifyAllRegionsPageTemplate");// verifyAllRegionsPageTemplate
		String key = "";
		for (int i = 0; i < regionPageTextdata.length; i++) {
			webPage.getDriver().get(regionPageTextdata[i][1]);
			key = regionPageTextdata[i][0];
			String[][] keyData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator", key + "Region");
			System.out.println("keyDatakeyDatakeyDatakeyData----" + keyData.length);
			for (int j = 0; j < keyData.length; j++) {
				String subkey = keyData[j][0];
				String text = webPage.getDriver().findElement(By.xpath(subkey)).getText();
				softAssert.assertTrue(text.contains(keyData[j][1]),
						"Expected Text : " + keyData[j][1] + " Actual Text : " + text);
			}
		}
		softAssert.assertAll();
		}catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verifyAllRegionsPageContent");
			e.printStackTrace();
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(enabled = true, priority = 9, description = "Verify alert box for empty Find Store field")
	public void verifyFindStoreAlertBox() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try{
		String[][] verifyFindStoreAlertBoxData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator",
				"verifyFindStoreAlertBox");

		webPage.getDriver().get("http://connsecommdev-1365538477.us-east-1.elb.amazonaws.com/conns_rwd/store-locator/");
		if (browserName.equalsIgnoreCase("Chrome")) {
			commonMethods.closeChromePopup(webPage);
		}
		commonMethods.clickElementbyXpath(webPage, verifyFindStoreAlertBoxData[0][0]);
		if (browserName.equalsIgnoreCase("Chrome")) {
			commonMethods.closeChromePopup(webPage);
		}
		commonMethods.clickElementbyXpath(webPage, verifyFindStoreAlertBoxData[1][0]);
		Alert alert = webPage.getDriver().switchTo().alert();
		String alertActualText = alert.getText();
		alert.accept();
		softAssert.assertEquals(alertActualText, verifyFindStoreAlertBoxData[0][1],
				"Expected Text : " + verifyFindStoreAlertBoxData[1][1] + " Actual Text : " + alertActualText);

		softAssert.assertAll();
		}catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verifyFindStoreAlertBox");
			e.printStackTrace();
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(enabled = true, priority = 10, description = "Verify Find Store field for Invalid input")
	public void verifyFindStoreInvalidData() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try{
		String[][] verifyFindStoreInvalidData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator",
				"verifyFindStoreInvalidData");

		webPage.getDriver().get("http://connsecommdev-1365538477.us-east-1.elb.amazonaws.com/conns_rwd/store-locator/");
		if (browserName.equalsIgnoreCase("Chrome")) {
			commonMethods.closeChromePopup(webPage);
		}
		commonMethods.clickElementbyXpath(webPage, verifyFindStoreInvalidData[0][0]);
		if (browserName.equalsIgnoreCase("Chrome")) {
			commonMethods.closeChromePopup(webPage);
		}
		commonMethods.sendKeysbyXpath(webPage, verifyFindStoreInvalidData[1][0], verifyFindStoreInvalidData[0][1]);
		commonMethods.clickElementbyXpath(webPage, verifyFindStoreInvalidData[2][0]);
		String errorMsgActualText = commonMethods.getTextbyXpath(webPage, verifyFindStoreInvalidData[3][0]);
		String errorMessageActualColor = commonMethods.getCssvaluebyXpath(webPage, verifyFindStoreInvalidData[3][0],
				"color");
		softAssert.assertEquals(errorMessageActualColor, verifyFindStoreInvalidData[2][1],
				"Color attribute verification failed. Expected Color : " + verifyFindStoreInvalidData[2][1]
						+ " Actual Color : " + errorMessageActualColor);
		String errorBoxActualColor = commonMethods.getCssvaluebyXpath(webPage, verifyFindStoreInvalidData[1][0],
				"color");
		softAssert.assertEquals(errorBoxActualColor, verifyFindStoreInvalidData[3][1],
				"Color attribute verification failed. Expected Color : " + verifyFindStoreInvalidData[3][1]
						+ " Actual Color : " + errorBoxActualColor);
		softAssert.assertEquals(errorMsgActualText, verifyFindStoreInvalidData[1][1],
				"Expected Text : " + verifyFindStoreInvalidData[1][1] + " Actual Text : " + errorMsgActualText);
		softAssert.assertAll();
		}catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verifyFindStoreInvalidData");
			e.printStackTrace();
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(enabled = true, priority = 11, description = "Verify Find Store functionality for valid input")
	public void verifyValidRegionSearch() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try{
		String[][] verifyValidRegionSearchData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator",
				"verifyValidRegionSearchData");

		webPage.getDriver().get("http://connsecommdev-1365538477.us-east-1.elb.amazonaws.com/conns_rwd/store-locator/");
		if (browserName.equalsIgnoreCase("Chrome")) {
			commonMethods.closeChromePopup(webPage);
		}
		commonMethods.clickElementbyXpath(webPage, verifyValidRegionSearchData[0][0]);
		if (browserName.equalsIgnoreCase("Chrome")) {
			commonMethods.closeChromePopup(webPage);
		}
		commonMethods.sendKeysbyXpath(webPage, verifyValidRegionSearchData[1][0], verifyValidRegionSearchData[0][1]);
		commonMethods.clickElementbyXpath(webPage, verifyValidRegionSearchData[2][0]);
		String regionPageActualData = commonMethods.getTextbyXpath(webPage, verifyValidRegionSearchData[3][0]);
		softAssert.assertTrue(regionPageActualData.contains(verifyValidRegionSearchData[1][1]),
				"Text verification failed. Expected Text : " + verifyValidRegionSearchData[1][1] + " Actual Text : "
						+ regionPageActualData);

		softAssert.assertAll();
		}catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verifyValidRegionSearch");
			e.printStackTrace();
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(enabled = true, priority = 12, description = "Verify Find Store functionality using zip code search")
	public void verifyZipCodeRegionSearch() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try{
		String[][] verifyZipCodeRegionSearchData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator",
				"verifyZipCodeRegionSearch");
		webPage.getDriver().get("http://connsecommdev-1365538477.us-east-1.elb.amazonaws.com/conns_rwd/");
		if (browserName.equalsIgnoreCase("Chrome")) {
			commonMethods.closeChromePopup(webPage);
		}
		commonMethods.clickElementbyXpath(webPage, verifyZipCodeRegionSearchData[0][0]);
		if (browserName.equalsIgnoreCase("Chrome")) {
			commonMethods.closeChromePopup(webPage);
		}
		commonMethods.sendKeysbyXpath(webPage, verifyZipCodeRegionSearchData[1][0],
				verifyZipCodeRegionSearchData[0][1]);
		commonMethods.clickElementbyXpath(webPage, verifyZipCodeRegionSearchData[2][0]);
		String regionPageActualData = commonMethods.getTextbyXpath(webPage, verifyZipCodeRegionSearchData[3][0]);
		softAssert.assertTrue(regionPageActualData.contains(verifyZipCodeRegionSearchData[1][1]),
				"Text verification failed. Expected Text : " + verifyZipCodeRegionSearchData[1][1] + " Actual Text : "
						+ regionPageActualData);
		softAssert.assertAll();
		}catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verifyZipCodeRegionSearch");
			e.printStackTrace();
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(enabled = true, priority = 13, description = "Verify Find Store functionality using zip code along with radius search")
	public void verifyZipCodeRadiusSearch() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try{
		String[][] verifyZipCodeRadiusSearchData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator",
				"verifyZipCodeRadiusSearch");
		webPage.getDriver().get("http://connsecommdev-1365538477.us-east-1.elb.amazonaws.com/conns_rwd/");
		if (browserName.equalsIgnoreCase("Chrome")) {
			commonMethods.closeChromePopup(webPage);
		}
		commonMethods.clickElementbyXpath(webPage, verifyZipCodeRadiusSearchData[0][0]);
		if (browserName.equalsIgnoreCase("Chrome")) {
			commonMethods.closeChromePopup(webPage);
		}
		commonMethods.sendKeysbyXpath(webPage, verifyZipCodeRadiusSearchData[1][0],
				verifyZipCodeRadiusSearchData[0][1]);
		commonMethods.clickElementbyXpath(webPage, verifyZipCodeRadiusSearchData[2][0]);
		String search50MilesActualData = commonMethods.getTextbyXpath(webPage, verifyZipCodeRadiusSearchData[3][0]);
		softAssert.assertTrue(search50MilesActualData.contains(verifyZipCodeRadiusSearchData[1][1]),
				"Text verification failed. Expected Text : " + verifyZipCodeRadiusSearchData[1][1] + " Actual Text : "
						+ search50MilesActualData);
		Select select = new Select(commonMethods.getWebElementbyXpath(webPage, verifyZipCodeRadiusSearchData[4][0]));
		select.selectByValue(verifyZipCodeRadiusSearchData[2][1]);
		commonMethods.clickElementbyXpath(webPage, verifyZipCodeRadiusSearchData[5][0]);
		String search75MilesActualData = commonMethods.getTextbyXpath(webPage, verifyZipCodeRadiusSearchData[6][0]);
		softAssert.assertTrue(search75MilesActualData.contains(verifyZipCodeRadiusSearchData[4][1]),
				"Text verification failed. Expected Text : " + verifyZipCodeRadiusSearchData[4][1] + " Actual Text : "
						+ search75MilesActualData);
		select = new Select(commonMethods.getWebElementbyXpath(webPage, verifyZipCodeRadiusSearchData[4][0]));
		select.selectByValue(verifyZipCodeRadiusSearchData[3][1]);
		commonMethods.clickElementbyXpath(webPage, verifyZipCodeRadiusSearchData[5][0]);
		String search125MilesActualData = commonMethods.getTextbyXpath(webPage, verifyZipCodeRadiusSearchData[7][0]);
		softAssert.assertTrue(search125MilesActualData.contains(verifyZipCodeRadiusSearchData[5][1]),
				"Text verification failed. Expected Text : " + verifyZipCodeRadiusSearchData[5][1] + " Actual Text : "
						+ search125MilesActualData);
		softAssert.assertAll();
		}catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verifyZipCodeRadiusSearch");
			e.printStackTrace();
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	@Test(enabled = true, priority = 14, description = "Verify Find Store functionality using city search")
	public void verifyCitySearch() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try{
		String[][] verifyCitySearchData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator",
				"verifyCitySearch");
		if (browserName.equalsIgnoreCase("Chrome")) {
			commonMethods.closeChromePopup(webPage);
		}
		commonMethods.clickElementbyXpath(webPage, verifyCitySearchData[0][0]);
		if (browserName.equalsIgnoreCase("Chrome")) {
			commonMethods.closeChromePopup(webPage);
		}
		commonMethods.sendKeysbyXpath(webPage, verifyCitySearchData[1][0],
				verifyCitySearchData[0][1]);
		commonMethods.clickElementbyXpath(webPage, verifyCitySearchData[2][0]);
		String cityPageActualData = commonMethods.getTextbyXpath(webPage, verifyCitySearchData[3][0]);
		softAssert.assertTrue(cityPageActualData.contains(verifyCitySearchData[1][1]),
				"Text verification failed. Expected Text : " + verifyCitySearchData[1][1] + " Actual Text : "
						+ cityPageActualData);
		softAssert.assertAll();
		}catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verifyCitySearch");
			e.printStackTrace();
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	@Test(enabled = true, priority = 15, description = "Verify Find Store functionality using zip code along with radius search")
	public void verifyCityRadiusSearch() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try{
		String[][] verifyCityRadiusSearchData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator",
				"verifyCityRadiusSearch");
		webPage.getDriver().get("http://connsecommdev-1365538477.us-east-1.elb.amazonaws.com/conns_rwd/");
		if (browserName.equalsIgnoreCase("Chrome")) {
			commonMethods.closeChromePopup(webPage);
		}
		commonMethods.clickElementbyXpath(webPage, verifyCityRadiusSearchData[0][0]);
		if (browserName.equalsIgnoreCase("Chrome")) {
			commonMethods.closeChromePopup(webPage);
		}
		commonMethods.sendKeysbyXpath(webPage, verifyCityRadiusSearchData[1][0],
				verifyCityRadiusSearchData[0][1]);
		commonMethods.clickElementbyXpath(webPage, verifyCityRadiusSearchData[2][0]);
		String search50MilesActualData = commonMethods.getTextbyXpath(webPage, verifyCityRadiusSearchData[3][0]);
		softAssert.assertTrue(search50MilesActualData.contains(verifyCityRadiusSearchData[1][1]),
				"Text verification failed. Expected Text : " + verifyCityRadiusSearchData[1][1] + " Actual Text : "
						+ search50MilesActualData);
		Select select = new Select(commonMethods.getWebElementbyXpath(webPage, verifyCityRadiusSearchData[4][0]));
		select.selectByValue(verifyCityRadiusSearchData[2][1]);
		commonMethods.clickElementbyXpath(webPage, verifyCityRadiusSearchData[5][0]);
		String search75MilesActualData = commonMethods.getTextbyXpath(webPage, verifyCityRadiusSearchData[6][0]);
		softAssert.assertTrue(search75MilesActualData.contains(verifyCityRadiusSearchData[4][1]),
				"Text verification failed. Expected Text : " + verifyCityRadiusSearchData[4][1] + " Actual Text : "
						+ search75MilesActualData);
		select = new Select(commonMethods.getWebElementbyXpath(webPage, verifyCityRadiusSearchData[4][0]));
		select.selectByValue(verifyCityRadiusSearchData[3][1]);
		commonMethods.clickElementbyXpath(webPage, verifyCityRadiusSearchData[5][0]);
		String search125MilesActualData = commonMethods.getTextbyXpath(webPage, verifyCityRadiusSearchData[7][0]);
		softAssert.assertTrue(search125MilesActualData.contains(verifyCityRadiusSearchData[5][1]),
				"Text verification failed. Expected Text : " + verifyCityRadiusSearchData[5][1] + " Actual Text : "
						+ search125MilesActualData);
		softAssert.assertAll();
		}catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verifyCityRadiusSearch");
			e.printStackTrace();
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	@Test(enabled = true, priority = 16, description = "Verify functionality of VIEW ALL link")
	public void verifyViewAllLink() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try{
			webPage.getDriver().get(commonData[0][0]);
			if (browserName.equalsIgnoreCase("Chrome")) {
				commonMethods.closeChromePopup(webPage);
			}
			String[][] verifyViewAllLinkData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyViewAllLink");
			String actualLinkText = commonMethods.getTextbyXpath(webPage, verifyViewAllLinkData[0][0]);
			softAssert.assertEquals(actualLinkText, verifyViewAllLinkData[0][1],"Text verification failed. Expected text : " + verifyViewAllLinkData[0][1] + " Actual text : " + actualLinkText);
			List<String> actualCssValues= commonMethods.getFontProperties(webPage, verifyViewAllLinkData[0][0]);
			softAssert.assertTrue(actualCssValues.get(0).contains(verifyViewAllLinkData[1][1]), "CSS value verification failed for link " + verifyViewAllLinkData[0][1] + "Expected Value : "+ verifyViewAllLinkData[1][1] + " Actual Value : " + actualCssValues.get(0));
			softAssert.assertTrue(actualCssValues.get(1).contains(verifyViewAllLinkData[2][1]),"CSS value verification failed for link " + verifyViewAllLinkData[0][1] + "Expected Value : "+ verifyViewAllLinkData[2][1] + " Actual Value : " + actualCssValues.get(1));
			if(!browserName.equalsIgnoreCase("IE")){
				softAssert.assertTrue(actualCssValues.get(2).contains(verifyViewAllLinkData[3][1]),"CSS value verification failed for link " + verifyViewAllLinkData[0][1] + "Expected Value : "+ verifyViewAllLinkData[3][1] + " Actual Value : " + actualCssValues.get(2));				
			}else{
				softAssert.assertTrue(actualCssValues.get(2).contains(verifyViewAllLinkData[4][1]),"CSS value verification failed for link " + verifyViewAllLinkData[0][1] + "Expected Value : "+ verifyViewAllLinkData[4][1] + " Actual Value : " + actualCssValues.get(2));
			}
			
			String actualUrl = commonMethods.clickAndGetPageURL(webPage, verifyViewAllLinkData[0][0], verifyViewAllLinkData[0][1]);
			softAssert.assertTrue(actualUrl.contains(verifyViewAllLinkData[5][1]),"URL verification failed for link : '" + verifyViewAllLinkData[0][1] + "'. Expected URL - "+ verifyViewAllLinkData[5][1] + " Actual URL - " + actualUrl);
			softAssert.assertAll();
		}catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verifyViewAllLink");
			e.printStackTrace();
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	@Test(enabled = true, priority = 17, description = "Verify functionality of all store locator links")
	public void verifyAllStoreLocatorLinks() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try{
			webPage.getDriver().get(commonData[0][0]);
			if (browserName.equalsIgnoreCase("Chrome")) {
				commonMethods.closeChromePopup(webPage);
			}
			String[][] verifyAllStoreLocatorData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator","verifyAllStoreLocatorLinks");
			for(int i=0;i<verifyAllStoreLocatorData.length;i++){
				webPage.getDriver().get(commonData[0][1]);
				String actualUrl = commonMethods.clickAndGetPageURL(webPage, verifyAllStoreLocatorData[i][2], verifyAllStoreLocatorData[i][1]);
				softAssert.assertTrue(actualUrl.contains(verifyAllStoreLocatorData[i][3]),"Expected - "+verifyAllStoreLocatorData[i][1]);
			}
			softAssert.assertAll();
		}catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verifyAllStoreLocatorLinks");
			e.printStackTrace();
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
}
