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
	String storeLocatorURL = "http://www.conns.com/store-locator/";

	@BeforeClass(alwaysRun = true)
	public void setUp(ITestContext context) throws InterruptedException, FileNotFoundException, IOException {
		try {
			testBedName = context.getCurrentXmlTest().getAllParameters().get("testBedName");
			CommonUtil.sop("Test bed Name is " + testBedName);
			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			System.out.println("Test Type is : " + testType);
			try {
			   testEnv = System.getenv().get("Environment");
				System.out.println("testEnv is : " + testEnv);
				path = Paths.get(TestBedManager.INSTANCE.getProfile().getXlsDataConfig().get("testData"));
				DataFilePath = path.toAbsolutePath().toString().replace("Env", testEnv);
				System.out.println("DataFilePath After is : " + DataFilePath);

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

	/**
	 * Test Case 001 - Verify Navigation to Conns Home Page and Verify Page
	 * title
	 * 
	 */
	/*
	 * @Test(dataProvider = "tafDataProvider", dataProviderClass =
	 * TafExcelDataProvider.class, priority = 1, enabled = false, description =
	 * "Verify Page Title")
	 * 
	 * @ITafExcelDataProviderInputs(excelFile = "CreditAppData", excelsheet =
	 * "HomePage", dataKey = "verifyPageTitle") public void
	 * verifyPageTitle(ITestContext context, TestParameters inputs) throws
	 * PageException, InterruptedException {
	 * 
	 * log.info("testing flow verifyPageTitle");
	 * 
	 * try {
	 * 
	 * Assert.assertEquals(inputs.getParamMap().get("Title"),
	 * webPage.getPageTitle());
	 * 
	 * } catch (Exception e) {
	 * SoftAssertor.addVerificationFailure(e.getMessage()); log.error(
	 * "verifyPageTitle failed"); log.error(e.getMessage()); }
	 * 
	 * }
	 */

	@Test(priority = 1, enabled = true, description = "Verify Page title")
	public void verifyStoreLocatorPageTitle() {
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "StoreLocator", "verifyPageTitle");
		SoftAssert softAssert = new SoftAssert();
		String pageUrl = test[0][0];
		String ExpectedTitle = test[0][1];
		webPage.getDriver().get(pageUrl);
		softAssert.assertEquals(ExpectedTitle, webPage.getPageTitle(),
				"Page Title verification failed. Expected title - " + ExpectedTitle + " Actual title - "
						+ webPage.getPageTitle());
		softAssert.assertAll();
	}

	@Test(priority = 2, enabled = true, description = "Verify Find Your Conn's HomePlus component")
	public void verifyHomePlusComponent() {
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "StoreLocator", "verifyHomePlusComponent");
		SoftAssert softAssert = new SoftAssert();
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
	}

	@Test(priority = 3, enabled = true, description = "Verify Store locator region links")
	public void verifyChoseYourRegionLinks() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String[][] regionLinksData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator",
				"verifyChoseYourRegionLinks");
		for (int i = 0; i < regionLinksData.length; i++) {
			webPage.getDriver().get(storeLocatorURL);
			if (testType.equalsIgnoreCase("Web")) {
				String beforeLinkHover = commonMethods.getCssvaluebyXpath(webPage, regionLinksData[i][1], "color");
				commonMethods.hoverOnelementbyXpath(webPage, regionLinksData[i][1]);
				String afterLinkHover = commonMethods.getCssvaluebyXpath(webPage, regionLinksData[i][1], "color");
				softAssert.assertNotEquals(afterLinkHover, beforeLinkHover,
						"CSS value verification failed for link " + regionLinksData[i][0] + ". Value before hover : "
								+ beforeLinkHover + " , Value after hover : " + afterLinkHover);
				String actualUrl = commonMethods.clickAndGetPageURL(webPage, regionLinksData[i][1],
						regionLinksData[i][0]);
				softAssert.assertEquals(actualUrl, regionLinksData[i][2],
						"URL verification failed for link : '" + regionLinksData[i][0] + "'. Expected URL - "
								+ regionLinksData[i][2] + " Actual URL - " + actualUrl);
			}
		}
		softAssert.assertAll();
	}

	@Test(priority = 4, enabled = true)
	public void verifyTexasSubLinks() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
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
	}

	/*
	 * @Test(dataProvider = "tafDataProvider", dataProviderClass =
	 * TafExcelDataProvider.class, priority = 5, enabled = true, description =
	 * "Verify Page Title")
	 * 
	 * @ITafExcelDataProviderInputs(excelFile = "CreditAppData", excelsheet =
	 * "StoreLocator", dataKey = "verifyChoseYourRegionLink")
	 */
	@Test(priority = 5, enabled = true)
	public void verifyOrderofLinks() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String[][] choseYourLinkData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator", "verifyOrderofLinks");
		webPage.getDriver().get(storeLocatorURL);
		for (int i = 0; i < choseYourLinkData.length; i++) {
			String textOnLink = commonMethods.getTextbyXpath(webPage, choseYourLinkData[i][1]);
			softAssert.assertEquals(textOnLink, choseYourLinkData[i][0],
					"Alphabetical order verification failed for links. Expected text : " + choseYourLinkData[i][0]
							+ " Actual text : " + textOnLink);
		}
		softAssert.assertAll();
	}

	/*
	 * @Test(dataProvider = "tafDataProvider", dataProviderClass =
	 * TafExcelDataProvider.class, priority = 6, enabled = true, description =
	 * "Verify Page Title")
	 * 
	 * @ITafExcelDataProviderInputs(excelFile = "CreditAppData", excelsheet =
	 * "StoreLocator", dataKey = "verifyRegionMap")
	 */
	@Test(priority = 6, enabled = true)
	public void verifyRegionMapToolTip() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
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
	}

	/*
	 * @Test(dataProvider = "tafDataProvider", dataProviderClass =
	 * TafExcelDataProvider.class, priority = 7, enabled = true, description =
	 * "Verify Page Title")
	 * 
	 * @ITafExcelDataProviderInputs(excelFile = "CreditAppData", excelsheet =
	 * "StoreLocator", dataKey = "verifyAllRegionPageContent")
	 */
	@Test(priority = 7, enabled = true)
	public void verifyAllRegionPageContent() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String[][] allRegionDescriptiondata = ExcelUtil.readExcelData(DataFilePath, "StoreLocator",
				"verifyAllRegionPageContent");
		String[][] regionPageTextdata = ExcelUtil.readExcelData(DataFilePath, "StoreLocator", "verifyRegionPageText");
		for (int i = 0; i < allRegionDescriptiondata.length; i++) {
			webPage.getDriver().get(storeLocatorURL);
			commonMethods.clickElementbyXpath(webPage, allRegionDescriptiondata[i][1], allRegionDescriptiondata[i][2],
					allRegionDescriptiondata[i][0]);
			String storeDescriptionText = commonMethods.getTextbyXpath(webPage, allRegionDescriptiondata[i][3]);
			softAssert.assertTrue(storeDescriptionText.contains(allRegionDescriptiondata[i][4]),
					"Store locator description text verification failed. Expected text : "
							+ allRegionDescriptiondata[i][4] + " Actual text : " + storeDescriptionText);
			String visitStoreActualText = commonMethods.getTextbyXpath(webPage, regionPageTextdata[0][1]);
			softAssert.assertTrue(visitStoreActualText.contains(regionPageTextdata[0][0]),
					"Text verification failed for region - " + allRegionDescriptiondata[i][0] + ". Expected text : "
							+ regionPageTextdata[0][0] + " Actual text : " + visitStoreActualText);
			String getDirectionsActualText = commonMethods.getTextbyXpath(webPage, regionPageTextdata[1][1]);
			softAssert.assertTrue(getDirectionsActualText.contains(regionPageTextdata[1][0]),
					"Text verification failed for region - " + allRegionDescriptiondata[i][0] + ". Expected text : "
							+ regionPageTextdata[1][0] + " Actual text : " + getDirectionsActualText);
			String applyforcreditActualText = commonMethods.getTextbyXpath(webPage, regionPageTextdata[2][1]);
			softAssert.assertTrue(applyforcreditActualText.contains(regionPageTextdata[2][0]),
					"Text verification failed for region - " + allRegionDescriptiondata[i][0] + ". Expected text : "
							+ regionPageTextdata[2][0] + " Actual text : " + applyforcreditActualText);
			String yesmoneyLinkUrl = commonMethods.clickAndGetPageURL(webPage, allRegionDescriptiondata[i][5],
					allRegionDescriptiondata[i][0]);
			softAssert.assertEquals(yesmoneyLinkUrl, allRegionDescriptiondata[i][6],
					"URL verification failed for link : '" + allRegionDescriptiondata[i][0] + "'. Expected URL : "
							+ allRegionDescriptiondata[i][6] + " Actual URL : " + yesmoneyLinkUrl);
		}
		softAssert.assertAll();
	}

	/*
	 * @Test(dataProvider = "tafDataProvider", dataProviderClass =
	 * TafExcelDataProvider.class, priority = 6, enabled = true, description =
	 * "Verify Page Title")
	 * 
	 * @ITafExcelDataProviderInputs(excelFile = "CreditAppData", excelsheet =
	 * "StoreLocator", dataKey = "verifyAllRegionsPageTemplate")
	 */
	@Test(enabled = true, priority = 8)
	public void verifyAllRegionsPageTemplate() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
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
	}

	@Test(enabled = true, priority = 9)
	public void verifyFindStoreAlertBox() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
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
	}

	@Test(enabled = true, priority = 10)
	public void verifyFindStoreInvalidData() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
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
	}

	@Test(enabled = true, priority = 11)
	public void verifyValidRegionSearch() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
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
	}

	@Test(enabled = true, priority = 12)
	public void verifyZipCodeRegionSearch() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
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
	}

	@Test(enabled = false, priority = 13)
	public void verifyZipCodeRadiusSearch() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
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
		String regionPageActualData = commonMethods.getTextbyXpath(webPage, verifyZipCodeRadiusSearchData[3][0]);
		softAssert.assertTrue(regionPageActualData.contains(verifyZipCodeRadiusSearchData[1][1]),
				"Text verification failed. Expected Text : " + verifyZipCodeRadiusSearchData[1][1] + " Actual Text : "
						+ regionPageActualData);

		softAssert.assertAll();
	}

	/**
	 * Test Case - 002 - Verify Font Size and Style of specified on element on
	 * Conns Home Page
	 * 
	 */
	@Test(priority = 2, enabled = false, description = "Verify Page Font-Size And Style")
	public void verifyFontSizeAndStyle() {

		String[][] test = ExcelUtil.readExcelData(DataFilePath, "HomePageQA", "verifyFontSizeAndStyle");
		log.info("testing flow verifyFontSizeAndStyle started");
		String pageHeadingClass = null;
		String fontAttribute = null;
		String expectedValue = null;
		int count = 0;
		List<Integer> failedElements = new ArrayList<Integer>();
		for (int r = 0; r < test.length; r++) {

			pageHeadingClass = test[r][0];
			fontAttribute = test[r][1];
			if (testType.equalsIgnoreCase("Web")) {
				expectedValue = test[r][2];
			} else {
				expectedValue = test[r][3];
			}
			try {
				log.info("Verifying font size and style for element no. " + (r + 1));
				ITafElement pageHeading = webPage.findObjectByCss(pageHeadingClass);
				String value = pageHeading.getCssValue(fontAttribute).replaceAll("\"", "").replaceAll(" ", "")
						.toLowerCase().trim();
				Assert.assertTrue(value.contains(expectedValue) || expectedValue.contains(value),
						"Verify Font Size and Style failed.!!!" + "Font Attribute name " + fontAttribute + "Actual : "
								+ value + " and Expected :" + expectedValue.trim());

			} catch (Throwable e) {
				count++;
				failedElements.add(count);
				SoftAssertor.addVerificationFailure(e.getMessage());
				log.error("verifyFontSizeAndStyle failed");
				log.error(e.getMessage());
			}

		}
		if (count > 0) {
			Assert.fail("Failed to verify element number : " + Arrays.deepToString(failedElements.toArray()));
		}
		log.info("Ending verifyFontSizeAndStyle");

	}

	@Test(priority = 3, enabled = false, description = "")
	public void verifyPageContent() throws ClientProtocolException, IOException {
		List<WebElement> imagesList = webPage.getDriver().findElements(By.tagName("img"));
		log.info("Total number of images" + imagesList.size());
		int imageCount = 0;
		List<Integer> brokenImageNumber = new ArrayList<Integer>();
		List<String> brokenImageSrc = new ArrayList<String>();
		for (WebElement image : imagesList) {

			try {
				imageCount++;
				log.info("Verifying image number : " + imageCount);
				HttpClient client = HttpClientBuilder.create().build();
				HttpGet request = new HttpGet(image.getAttribute("src"));

				HttpResponse response = client.execute(request);
				if (response.getStatusLine().getStatusCode() == 200) {
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
			}
		}
		if (brokenImageNumber.size() > 0) {
			// Assert.fail("Image number " +
			// Arrays.deepToString(brokenImageNumber.toArray()) + " are not as
			// expected");
			Assert.fail("Image number of the broken images : " + Arrays.deepToString(brokenImageNumber.toArray())
					+ "Image source of the broken images : " + Arrays.deepToString(brokenImageSrc.toArray()));

		}
	}

	@Test(priority = 4, enabled = false, description = "")
	public void verifyLinksAboveHeader() throws PageException {

		String[][] test = ExcelUtil.readExcelData(DataFilePath, "HomePageQA", "verifyLinksAboveHeader");
		List<String> brokenLinks = new ArrayList<String>();
		String xPath = null;
		String ExpectedURL = null;
		String actualUrl = "";
		String mobileElement = null;
		String mobileParentElement = null;
		String elementName = null;

		for (int r = 0; r < test.length; r++) {
			elementName = test[r][0];
			if (testType.equalsIgnoreCase("Mobile")) {
				xPath = test[r][2];
				mobileParentElement = test[r][3];
				ExpectedURL = test[r][4];
			} else {
				xPath = test[r][1];
				ExpectedURL = test[r][4];
			}
			try {
				log.info("Verifying Link --->" + xPath);
				if (testType.equalsIgnoreCase("Mobile") && !mobileParentElement.isEmpty()) {
					webPage.findObjectByxPath(mobileParentElement).click();
					webPage.findObjectByxPath(xPath).click();
					try {
						webPage.getDriver().navigate().back();
						// webPage.getDriver().switchTo().alert().accept();

					} // try
					catch (NoAlertPresentException Ex) {
						log.info("No Alert found");
					}

				} else {
					webPage.findObjectByxPath(xPath).click();

				}
				try {
					/*
					 * if(webPage.findObjectsByTag("iframe").size()>0) {
					 * webPage.getDriver().switchTo().frame(webPage.getDriver().
					 * findElement(By.xpath(".//iframe[@id='lightbox_pop']")));
					 * webPage.findObjectByxPath(".//*[@id='es']").click();;
					 * actualUrl = webPage.getCurrentUrl(); }
					 */
					// else{
					actualUrl = webPage.getCurrentUrl();
					// }
				} catch (NoSuchElementException e) {
					brokenLinks.add(elementName + " " + e.getLocalizedMessage());
					actualUrl = webPage.getCurrentUrl();
					System.out.println(e);
				}
				webPage.getBackToUrl();
				// Assert.assertTrue(actualUrl.endsWith(ExpectedURL),xPath+"
				// failed "+ actualUrl+" "+ExpectedURL);
				Assert.assertTrue(actualUrl.contains(ExpectedURL), xPath + " failed " + actualUrl + " " + ExpectedURL);
				log.info("testing verifyLinkNavigation completed------>");

			} catch (Exception e) {
				brokenLinks.add(elementName + " " + e.getLocalizedMessage());
				log.info("Failed to Verifying Link --->" + actualUrl);
				// SoftAssertor.addVerificationFailure(e.getMessage());
				log.error("verifyLinkNavigation failed");
				log.error(e.getMessage());
			}
			if (brokenLinks.size() > 0) {
				Assert.fail("Link " + Arrays.deepToString(brokenLinks.toArray()) + " are not working as expected");
			}
			// System.out.println("Test is
			// "+webPage.findObjectByxPath(".//*[@id='slide-nav']/div/div[1]/div/div[3]/div[4]/div/p/a").getText());
		}

	}

	@Test(dataProvider = "tafDataProvider", dataProviderClass = TafExcelDataProvider.class, priority = 5, enabled = false, description = "Verify Page Title")
	@ITafExcelDataProviderInputs(excelFile = "CreditAppData", excelsheet = "HomePageQA", dataKey = "verifyLinksInHeader")
	public void verifyLinksInHeader(ITestContext context, TestParameters inputs)
			throws PageException, InterruptedException {
		if (testType.equalsIgnoreCase("Mobile")) {
			webPage.findObjectByxPath(inputs.getParamMap().get("MobileMainMenu")).click();
		}
		webPage.findObjectByxPath(inputs.getParamMap().get("LinkIdentifier")).click();
		log.info("Clicked on element " + inputs.getParamMap().get("ElementName"));
		Assert.assertTrue(webPage.getCurrentUrl().endsWith(inputs.getParamMap().get("Expected")),
				" Failed : " + webPage.getPageTitle() + " " + inputs.getParamMap().get("Expected"));
		webPage.getBackToUrl();
	}

	@Test(priority = 6, enabled = false, description = "")
	public void verifyLinksForFurnitureAndMattresses() throws PageException, InterruptedException {
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "HomePageQA", "verifyLinksForFurnitureAndMattresses");
		String MenuElement = test[0][0];
		String ChildElementIdentifier = null;
		String expectedURL = null;
		String elementName = null;
		String actualUrl = null;
		String brokenLinkName = null;
		List<String> brokenLinks = new ArrayList<String>();
		boolean run = true;

		for (int i = 0; i < test.length; i++) {
			expectedURL = test[i][2];
			elementName = test[i][3];
			ChildElementIdentifier = test[i][1];
			try {
				if (testType.equalsIgnoreCase("Web")) {
					// hover on parent menu option in desktop browser
					webPage.hoverOnElement(By.xpath(test[0][0]));
				} else {
					// Click on hamburger menu and Parent menu option when
					// executing on mobile device
					webPage.findObjectByxPath(test[0][4]).click();
					webPage.findObjectByxPath(test[0][0]).click();

					// sets run flag to false to skip validation of link
					// navigation as this links are parent links to show child
					// elements
					if (test[i][5].isEmpty()) {

						webPage.findObjectByxPath(test[0][4]).click();
						run = false;

					}
					// if Second parent on mobile device does not have any
					// child, below code will set run flag to test the second
					// parent redirection link
					// and set i++ to skip next element from sheet(child
					// element) which is present for desktop browser only
					else if (test[i][5].equalsIgnoreCase("Test")) {
						run = true;
						i++;
					}
					// clicks on child element on mobile device
					else {
						System.out.println("test[i][5] is    --------->  :" + test[i][5]);
						webPage.findObjectByxPath(test[i][5]).click();
						run = true;
					}
				}
				// run if element in execution is a child element
				if (run) {

					System.out
							.println(test[0][0] + " " + elementName + " " + ChildElementIdentifier + " " + expectedURL);
					log.error("Verifying link :" + elementName);
					if (ChildElementIdentifier.contains("//")) {
						webPage.findObjectByxPath(ChildElementIdentifier).click();
						log.info("Clicking on link");
					} else {
						webPage.findObjectByLink(ChildElementIdentifier).click();
						log.info("Clicking on link");
					}

					actualUrl = webPage.getCurrentUrl();

					webPage.getBackToUrl();
					Assert.assertTrue(actualUrl.endsWith(expectedURL), " failed " + actualUrl + " " + expectedURL);
				}

			} catch (Throwable e) {
				webPage.navigateToUrl(url);
				brokenLinks.add(elementName + " " + e.getLocalizedMessage());
				log.error("Failed to verify link :" + elementName);
				log.error(e.getMessage());
			}
		}
		if (brokenLinks.size() > 0) {
			Assert.fail("Link " + Arrays.deepToString(brokenLinks.toArray()) + " are not working as expected");
		}
		log.info("testing verifyLinksForFurnitureAndMattresses completed------>");

	}

	@Test(priority = 7, enabled = false, description = "")
	public void verifyLinksForAppliance() throws PageException, InterruptedException {

		String[][] test = ExcelUtil.readExcelData(DataFilePath, "HomePageQA", "verifyLinksForAppliance");
		String MenuElement = test[0][0];
		String ChildElementIdentifier = null;
		String expectedURL = null;
		String elementName = null;
		String actualUrl = null;
		String brokenLinkName = null;
		List<String> brokenLinks = new ArrayList<String>();
		boolean run = true;

		for (int i = 0; i < test.length; i++) {
			expectedURL = test[i][2];
			elementName = test[i][3];
			ChildElementIdentifier = test[i][1];
			try {
				if (testType.equalsIgnoreCase("Web")) {
					// hover on parent menu option in desktop browser
					webPage.hoverOnElement(By.xpath(test[0][0]));
				} else {
					// Click on hamburger menu and Parent menu option when
					// executing on mobile device
					webPage.findObjectByxPath(test[0][4]).click();
					webPage.findObjectByxPath(test[0][0]).click();

					// sets run flag to false to skip validation of link
					// navigation as this links are parent links to show child
					// elements
					if (test[i][5].isEmpty()) {

						webPage.findObjectByxPath(test[0][4]).click();
						run = false;

					}
					// if Second parent on mobile device does not have any
					// child, below code will set run flag to test the second
					// parent redirection link
					// and set i++ to skip next element from sheet(child
					// element) which is present for desktop browser only
					else if (test[i][5].equalsIgnoreCase("Test")) {
						run = true;
						i++;
					}
					// clicks on child element on mobile device
					else {
						System.out.println("test[i][5] is    --------->  :" + test[i][5]);
						webPage.findObjectByxPath(test[i][5]).click();
						run = true;
					}
				}
				// run if element in execution is a child element
				if (run) {

					System.out
							.println(test[0][0] + " " + elementName + " " + ChildElementIdentifier + " " + expectedURL);
					log.error("Verifying link :" + elementName);
					if (ChildElementIdentifier.contains("//")) {
						webPage.findObjectByxPath(ChildElementIdentifier).click();
						log.info("Clicking on link");
					} else {
						webPage.findObjectByLink(ChildElementIdentifier).click();
						log.info("Clicking on link");
					}

					actualUrl = webPage.getCurrentUrl();

					webPage.getBackToUrl();
					Assert.assertTrue(actualUrl.endsWith(expectedURL), " failed " + actualUrl + " " + expectedURL);
				}

			} catch (Throwable e) {
				webPage.navigateToUrl(url);
				brokenLinks.add(elementName + " " + e.getLocalizedMessage());
				log.error("Failed to verify link :" + elementName);
				log.error(e.getMessage());
			}
		}
		if (brokenLinks.size() > 0) {
			Assert.fail("Link " + Arrays.deepToString(brokenLinks.toArray()) + " are not working as expected");
		}
		log.info("testing verifyLinksForAppliance completed------>");

		/*
		 * String[][] test= ExcelUtil.readExcelData(DataFilePath, "HomePageQA",
		 * "verifyLinksForAppliance"); String MenuElement=test[0][0]; String
		 * ChildElementIdentifier=null; String expectedURL=null; String
		 * elementName=null; String actualUrl = null; String
		 * brokenLinkName=null; List<String> brokenLinks = new
		 * ArrayList<String>();
		 * 
		 * 
		 * for(int i=0; i<test.length; i++) {
		 * 
		 * try{ webPage.hoverOnElement(By.linkText(test[0][0]));
		 * ChildElementIdentifier=test[i][1]; expectedURL=test[i][2];
		 * elementName=test[i][3]; System.out.println(test[0][0]+" "
		 * +elementName+" "+ChildElementIdentifier+" "+expectedURL); log.error(
		 * "Verifying link :"+elementName);
		 * if(ChildElementIdentifier.contains("//")) {
		 * webPage.findObjectByxPath(ChildElementIdentifier).click(); log.info(
		 * "Clicking on link"); } else{
		 * webPage.findObjectByLink(ChildElementIdentifier).click(); log.info(
		 * "Clicking on link"); } actualUrl = webPage.getCurrentUrl();
		 * 
		 * webPage.getBackToUrl();
		 * Assert.assertTrue(actualUrl.endsWith(expectedURL)," failed "+
		 * actualUrl+" "+expectedURL);
		 * 
		 * 
		 * } catch(Throwable e) { webPage.navigateToUrl(url);
		 * brokenLinks.add(elementName); log.error("Failed to verify link :"
		 * +elementName); log.error(e.getMessage()); } } if (brokenLinks.size()
		 * > 0) { Assert.fail("Link " +
		 * Arrays.deepToString(brokenLinks.toArray()) +
		 * " are not working as expected"); } log.info(
		 * "testing verifyLinksForAppliance completed------>");
		 */

	}

	@Test(priority = 8, enabled = false, description = "")
	public void verifyLinksForTvAudioElectronics() throws PageException, InterruptedException {

		String[][] test = ExcelUtil.readExcelData(DataFilePath, "HomePageQA", "verifyLinksForTvAudioElectronics");
		String MenuElement = test[0][0];
		String ChildElementIdentifier = null;
		String expectedURL = null;
		String elementName = null;
		String actualUrl = null;
		String brokenLinkName = null;
		List<String> brokenLinks = new ArrayList<String>();
		boolean run = true;

		for (int i = 0; i < test.length; i++) {
			expectedURL = test[i][2];
			elementName = test[i][3];
			ChildElementIdentifier = test[i][1];
			try {
				if (testType.equalsIgnoreCase("Web")) {
					// hover on parent menu option in desktop browser
					webPage.hoverOnElement(By.xpath(test[0][0]));
				} else {
					// Click on hamburger menu and Parent menu option when
					// executing on mobile device
					webPage.findObjectByxPath(test[0][4]).click();
					webPage.findObjectByxPath(test[0][0]).click();

					// sets run flag to false to skip validation of link
					// navigation as this links are parent links to show child
					// elements
					if (test[i][5].isEmpty()) {

						webPage.findObjectByxPath(test[0][4]).click();
						run = false;

					}
					// if Second parent on mobile device does not have any
					// child, below code will set run flag to test the second
					// parent redirection link
					// and set i++ to skip next element from sheet(child
					// element) which is present for desktop browser only
					else if (test[i][5].equalsIgnoreCase("Test")) {
						run = true;
						i++;
					}
					// clicks on child element on mobile device
					else {
						System.out.println("test[i][5] is    --------->  :" + test[i][5]);
						webPage.findObjectByxPath(test[i][5]).click();
						run = true;
					}
				}
				// run if element in execution is a child element
				if (run) {

					System.out
							.println(test[0][0] + " " + elementName + " " + ChildElementIdentifier + " " + expectedURL);
					log.error("Verifying link :" + elementName);
					if (ChildElementIdentifier.contains("//")) {
						webPage.findObjectByxPath(ChildElementIdentifier).click();
						log.info("Clicking on link");
					} else {
						webPage.findObjectByLink(ChildElementIdentifier).click();
						log.info("Clicking on link");
					}

					actualUrl = webPage.getCurrentUrl();

					webPage.getBackToUrl();
					Assert.assertTrue(actualUrl.endsWith(expectedURL), " failed " + actualUrl + " " + expectedURL);
				}

			} catch (Throwable e) {
				webPage.navigateToUrl(url);
				brokenLinks.add(elementName + " " + e.getLocalizedMessage());
				log.error("Failed to verify link :" + elementName);
				log.error(e.getMessage());
			}
		}
		if (brokenLinks.size() > 0) {
			Assert.fail("Link " + Arrays.deepToString(brokenLinks.toArray()) + " are not working as expected");
		}
		log.info("testing verifyLinksForTvAudioElectronics completed------>");

		/*
		 * String[][] test= ExcelUtil.readExcelData(DataFilePath, "HomePageQA",
		 * "verifyLinksForTvAudioElectronics"); String MenuElement=test[0][0];
		 * String ChildElementIdentifier=null; String expectedURL=null; String
		 * elementName=null; String actualUrl = null; String
		 * brokenLinkName=null; List<String> brokenLinks = new
		 * ArrayList<String>();
		 * 
		 * 
		 * for(int i=0; i<test.length; i++) {
		 * 
		 * try{ webPage.hoverOnElement(By.linkText(test[0][0]));
		 * ChildElementIdentifier=test[i][1]; expectedURL=test[i][2];
		 * elementName=test[i][3]; System.out.println(test[0][0]+" "
		 * +elementName+" "+ChildElementIdentifier+" "+expectedURL); log.error(
		 * "Verifying link :"+elementName);
		 * if(ChildElementIdentifier.contains("//")) {
		 * webPage.findObjectByxPath(ChildElementIdentifier).click(); log.info(
		 * "Clicking on link"); } else{
		 * webPage.findObjectByLink(ChildElementIdentifier).click(); log.info(
		 * "Clicking on link"); } //Thread.sleep(5000); actualUrl =
		 * webPage.getCurrentUrl();
		 * 
		 * webPage.getBackToUrl();
		 * Assert.assertTrue(actualUrl.endsWith(expectedURL)," failed "+
		 * actualUrl+" "+expectedURL);
		 * 
		 * 
		 * } catch(Throwable e) { webPage.navigateToUrl(url);
		 * brokenLinks.add(elementName); log.error("Failed to verify link :"
		 * +elementName); log.error(e.getMessage()); } } if (brokenLinks.size()
		 * > 0) { Assert.fail("Link " +
		 * Arrays.deepToString(brokenLinks.toArray()) +
		 * " are not working as expected"); } log.info(
		 * "testing verifyLinksForTvAudioElectronics completed------>");
		 */

	}

	@Test(priority = 9, enabled = false, description = "")
	public void verifyLinksForComputerAccessories() throws PageException, InterruptedException {

		String[][] test = ExcelUtil.readExcelData(DataFilePath, "HomePageQA", "verifyLinksForComputerAccessories");
		String MenuElement = test[0][0];
		String ChildElementIdentifier = null;
		String expectedURL = null;
		String elementName = null;
		String actualUrl = null;
		String brokenLinkName = null;
		List<String> brokenLinks = new ArrayList<String>();
		boolean run = true;

		for (int i = 0; i < test.length; i++) {
			expectedURL = test[i][2];
			elementName = test[i][3];
			ChildElementIdentifier = test[i][1];
			try {
				if (testType.equalsIgnoreCase("Web")) {
					// hover on parent menu option in desktop browser
					webPage.hoverOnElement(By.xpath(test[0][0]));
				} else {
					// Click on hamburger menu and Parent menu option when
					// executing on mobile device
					webPage.findObjectByxPath(test[0][4]).click();
					webPage.findObjectByxPath(test[0][0]).click();

					// sets run flag to false to skip validation of link
					// navigation as this links are parent links to show child
					// elements
					if (test[i][5].isEmpty()) {

						webPage.findObjectByxPath(test[0][4]).click();
						run = false;

					}
					// if Second parent on mobile device does not have any
					// child, below code will set run flag to test the second
					// parent redirection link
					// and set i++ to skip next element from sheet(child
					// element) which is present for desktop browser only
					else if (test[i][5].equalsIgnoreCase("Test")) {
						run = true;
						i++;
					}
					// clicks on child element on mobile device
					else {
						System.out.println("test[i][5] is    --------->  :" + test[i][5]);
						webPage.findObjectByxPath(test[i][5]).click();
						run = true;
					}
				}
				// run if element in execution is a child element
				if (run) {

					System.out
							.println(test[0][0] + " " + elementName + " " + ChildElementIdentifier + " " + expectedURL);
					log.error("Verifying link :" + elementName);
					if (ChildElementIdentifier.contains("//")) {
						webPage.findObjectByxPath(ChildElementIdentifier).click();
						log.info("Clicking on link");
					} else {
						webPage.findObjectByLink(ChildElementIdentifier).click();
						log.info("Clicking on link");
					}

					actualUrl = webPage.getCurrentUrl();

					webPage.getBackToUrl();
					Assert.assertTrue(actualUrl.endsWith(expectedURL), " failed " + actualUrl + " " + expectedURL);
				}

			} catch (Throwable e) {
				webPage.navigateToUrl(url);
				brokenLinks.add(elementName + " " + e.getLocalizedMessage());
				log.error("Failed to verify link :" + elementName);
				log.error(e.getMessage());
			}
		}
		if (brokenLinks.size() > 0) {
			Assert.fail("Link " + Arrays.deepToString(brokenLinks.toArray()) + " are not working as expected");
		}
		log.info("testing verifyLinksForTvAudioElectronics completed------>");

		/*
		 * String[][] test= ExcelUtil.readExcelData(DataFilePath, "HomePageQA",
		 * "verifyLinksForComputerAccessories"); String
		 * MenuElementIdentifier=test[0][0].trim(); String
		 * ChildElementIdentifier=null; String expectedURL=null; String
		 * elementName=null; String actualUrl = null; String
		 * brokenLinkName=null; List<String> brokenLinks = new
		 * ArrayList<String>();
		 * 
		 * 
		 * for(int i=0; i<test.length; i++) {
		 * 
		 * try{ webPage.hoverOnElement(By.xpath(MenuElementIdentifier));
		 * ChildElementIdentifier=test[i][1]; expectedURL=test[i][2];
		 * elementName=test[i][3]; System.out.println(test[0][0]+" "
		 * +elementName+" "+ChildElementIdentifier+" "+expectedURL); log.error(
		 * "Verifying link :"+elementName);
		 * if(ChildElementIdentifier.contains("//")) {
		 * webPage.findObjectByxPath(ChildElementIdentifier).click(); log.info(
		 * "Clicking on link"); } else{
		 * webPage.findObjectByLink(ChildElementIdentifier).click(); log.info(
		 * "Clicking on link"); } //Thread.sleep(5000); actualUrl =
		 * webPage.getCurrentUrl();
		 * 
		 * webPage.getBackToUrl();
		 * Assert.assertTrue(actualUrl.endsWith(expectedURL)," failed "+
		 * actualUrl+" "+expectedURL);
		 * 
		 * 
		 * } catch(Throwable e) { webPage.navigateToUrl(url);
		 * brokenLinks.add(elementName); log.error("Failed to verify link :"
		 * +elementName); log.error(e.getMessage()); } } if (brokenLinks.size()
		 * > 0) { Assert.fail("Link " +
		 * Arrays.deepToString(brokenLinks.toArray()) +
		 * " are not working as expected"); } log.info(
		 * "testing verifyLinksForComputerAccessories completed------>");
		 */

	}

	@Test(priority = 10, enabled = false, description = "")
	public void verifyLinksForFinancingPromotions() throws PageException, InterruptedException {

		String[][] test = ExcelUtil.readExcelData(DataFilePath, "HomePageQA", "verifyLinksForFinancingPromotions");
		String MenuElement = test[0][0];
		String ChildElementIdentifier = null;
		String expectedURL = null;
		String elementName = null;
		String actualUrl = null;
		String brokenLinkName = null;
		List<String> brokenLinks = new ArrayList<String>();
		boolean run = true;

		for (int i = 0; i < test.length; i++) {
			expectedURL = test[i][2];
			elementName = test[i][3];
			ChildElementIdentifier = test[i][1];
			try {
				if (testType.equalsIgnoreCase("Web")) {
					// hover on parent menu option in desktop browser
					webPage.hoverOnElement(By.xpath(test[0][0]));
				} else {
					// Click on hamburger menu and Parent menu option when
					// executing on mobile device
					webPage.findObjectByxPath(test[0][4]).click();
					webPage.findObjectByxPath(test[0][0]).click();

					// sets run flag to false to skip validation of link
					// navigation as this links are parent links to show child
					// elements
					if (test[i][5].isEmpty()) {

						webPage.findObjectByxPath(test[0][4]).click();
						run = false;

					}
					// if Second parent on mobile device does not have any
					// child, below code will set run flag to test the second
					// parent redirection link
					// and set i++ to skip next element from sheet(child
					// element) which is present for desktop browser only
					else if (test[i][5].equalsIgnoreCase("Test")) {
						run = true;
						i++;
					}
					// clicks on child element on mobile device
					else {
						System.out.println("test[i][5] is    --------->  :" + test[i][5]);
						webPage.findObjectByxPath(test[i][5]).click();
						run = true;
					}
				}
				// run if element in execution is a child element
				if (run) {

					System.out
							.println(test[0][0] + " " + elementName + " " + ChildElementIdentifier + " " + expectedURL);
					log.error("Verifying link :" + elementName);
					if (ChildElementIdentifier.contains("//")) {
						webPage.findObjectByxPath(ChildElementIdentifier).click();
						log.info("Clicking on link");
					} else {
						webPage.findObjectByLink(ChildElementIdentifier).click();
						log.info("Clicking on link");
					}

					actualUrl = webPage.getCurrentUrl();

					webPage.getBackToUrl();
					Assert.assertTrue(actualUrl.endsWith(expectedURL), " failed " + actualUrl + " " + expectedURL);
				}

			} catch (Throwable e) {
				webPage.navigateToUrl(url);
				brokenLinks.add(elementName + " " + e.getLocalizedMessage());
				log.error("Failed to verify link :" + elementName);
				log.error(e.getMessage());
			}
		}
		if (brokenLinks.size() > 0) {
			Assert.fail("Link " + Arrays.deepToString(brokenLinks.toArray()) + " are not working as expected");
		}
		log.info("testing verifyLinksForFinancingPromotions completed------>");

		/*
		 * String[][] test= ExcelUtil.readExcelData(DataFilePath, "HomePageQA",
		 * "verifyLinksForFinancingPromotions"); String MenuElement=test[0][0];
		 * String ChildElementIdentifier=null; String expectedURL=null; String
		 * elementName=null; String actualUrl = null; String
		 * brokenLinkName=null; List<String> brokenLinks = new
		 * ArrayList<String>();
		 * 
		 * 
		 * for(int i=0; i<test.length; i++) {
		 * 
		 * try{ webPage.hoverOnElement(By.linkText(test[0][0]));
		 * ChildElementIdentifier=test[i][1].trim(); expectedURL=test[i][2];
		 * elementName=test[i][3]; System.out.println(test[0][0]+" "
		 * +elementName+" "+ChildElementIdentifier+" "+expectedURL); log.error(
		 * "Verifying link :"+elementName);
		 * if(ChildElementIdentifier.contains("//")) {
		 * webPage.findObjectByxPath(ChildElementIdentifier).click(); log.info(
		 * "Clicking on link"); } else{
		 * webPage.findObjectByLink(ChildElementIdentifier).click(); log.info(
		 * "Clicking on link"); } //Thread.sleep(5000); actualUrl =
		 * webPage.getCurrentUrl();
		 * 
		 * webPage.getBackToUrl();
		 * Assert.assertTrue(actualUrl.endsWith(expectedURL)," failed "+
		 * actualUrl+" "+expectedURL);
		 * 
		 * 
		 * } catch(Throwable e) { webPage.navigateToUrl(url);
		 * brokenLinks.add(elementName); log.error("Failed to verify link :"
		 * +elementName); log.error(e.getMessage()); } } if (brokenLinks.size()
		 * > 0) { Assert.fail("Link " +
		 * Arrays.deepToString(brokenLinks.toArray()) +
		 * " are not working as expected"); } log.info(
		 * "testing verifyLinksForFinancingPromotions completed------>");
		 */

	}

	@Test(dataProvider = "tafDataProvider", dataProviderClass = TafExcelDataProvider.class, priority = 11, enabled = false, description = "Verify Page Title")
	@ITafExcelDataProviderInputs(excelFile = "CreditAppData", excelsheet = "HomePageQA", dataKey = "verifyYesMeBanner")
	public void verifyYesMeBanner(ITestContext context, TestParameters inputs)
			throws PageException, InterruptedException {
		try {
			webPage.findObjectByxPath(inputs.getParamMap().get("LinkIdentifier")).click();
			log.info("Clicked on element " + inputs.getParamMap().get("ElementName"));
			Assert.assertTrue(webPage.getCurrentUrl().endsWith(inputs.getParamMap().get("Expected")),
					" Failed : " + webPage.getCurrentUrl() + " " + inputs.getParamMap().get("Expected"));
			webPage.getBackToUrl();

		} catch (Throwable e) {
			webPage.navigateToUrl(url);
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(dataProvider = "tafDataProvider", dataProviderClass = TafExcelDataProvider.class, priority = 12, enabled = false, description = "Verify Page Title")
	@ITafExcelDataProviderInputs(excelFile = "CreditAppData", excelsheet = "HomePageQA", dataKey = "verifyNextDayDeliveryBanner")
	public void verifyNextDayDeliveryBanner(ITestContext context, TestParameters inputs)
			throws PageException, InterruptedException {
		try {
			webPage.findObjectByxPath(inputs.getParamMap().get("LinkIdentifier")).click();
			log.info("Clicked on element " + inputs.getParamMap().get("ElementName"));
			Assert.assertTrue(webPage.getCurrentUrl().endsWith(inputs.getParamMap().get("Expected")),
					" Failed : " + webPage.getCurrentUrl() + " " + inputs.getParamMap().get("Expected"));
		} catch (Throwable e) {
			webPage.navigateToUrl(url);
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 14, enabled = false, description = "")
	public void verifySaveBigWithConns() throws PageException, InterruptedException {
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "HomePageQA", "verifySaveBigWithConns");
		String SaveBigMenuOptionIdentifier = null;
		String CarouselLeft = null;
		String CarouselRight = null;
		String ElementPosition1 = null;
		String ElementPosition2 = null;
		String ClickForDetails = null;
		String PopUp = null;
		List<String> errors = new ArrayList<String>();

		for (int i = 0; i < test.length; i++) {

			try {

				SaveBigMenuOptionIdentifier = test[i][0].trim();
				CarouselLeft = test[i][1];
				CarouselRight = test[i][2];
				ElementPosition1 = test[i][3];
				ElementPosition2 = test[i][4];
				ClickForDetails = test[i][5];
				PopUp = test[i][6];

				System.out.println(" " + SaveBigMenuOptionIdentifier + " " + CarouselLeft + " " + CarouselRight + " "
						+ ElementPosition1 + " " + ElementPosition2 + " " + ClickForDetails + " " + PopUp);
				log.error("Verifying Element :" + SaveBigMenuOptionIdentifier);
				webPage.findObjectByxPath(SaveBigMenuOptionIdentifier).click();
				webPage.findObjectByxPath(CarouselLeft).click();
				String textAtPosition1 = webPage.findObjectByxPath(ElementPosition1).getText();
				System.out.println("Expected Left: " + textAtPosition1);
				for (int j = 0; j < 3; j++) {
					webPage.findObjectByxPath(CarouselLeft).click();
				}
				String textAtPosition2 = webPage.findObjectByxPath(ElementPosition2).getText();
				System.out.println("Actual Left : " + textAtPosition2);
				Assert.assertEquals(textAtPosition1, textAtPosition2,
						" failed " + textAtPosition1 + " " + textAtPosition2);

				// webPage.findObjectByxPath(CarouselRight).click();
				log.info("Clicked on element2");
				String eletextAtPosition1 = webPage.findObjectByxPath(ElementPosition2).getText();
				System.out.println("Expected Right: " + eletextAtPosition1);
				for (int k = 0; k < 3; k++) {
					webPage.findObjectByxPath(CarouselRight).click();
				}
				String eletextAtPosition2 = webPage.findObjectByxPath(ElementPosition1).getText();
				System.out.println("Actual Right: " + eletextAtPosition2);
				Assert.assertEquals(eletextAtPosition1, eletextAtPosition2,
						" failed " + eletextAtPosition1 + " " + eletextAtPosition2);

				if (!webPage.findObjectsByXpath(ClickForDetails).isEmpty()) {
					List<ITafElement> clickforprice = new ArrayList<ITafElement>();
					clickforprice = webPage.findObjectsByXpath(ClickForDetails);
					System.out.println("Size is :" + clickforprice.size());
					for (int s = 0; s < clickforprice.size(); s++) {
						if (clickforprice.get(s).getWebElement().isDisplayed()) {
							// try{
							System.out.println("Clicking ");
							clickforprice.get(s).click();
							System.out.println("Clicked");
							if (webPage.findObjectByxPath(PopUp).isDisplayed()) {
								System.out.println("PopUp Displyed");

							} else {
								errors.add("Unable to find popup after click");

							}
							s = clickforprice.size();
						}

					}

					if (webPage.findObjectByxPath(PopUp).isDisplayed()) {
						log.info("PopUp Displyed");

					} else {
						errors.add("Unable to find popup after click");

					}
				}
			} catch (Throwable e) {
				errors.add(e.getLocalizedMessage());
				log.error(e.getMessage());
			}
		}
		if (errors.size() > 0) {
			Assert.fail(Arrays.deepToString(errors.toArray()) + " are not working as expected");
		}

	}

	@Test(priority = 14, enabled = false, description = "")
	public void verifyTopCategorySection() throws PageException, InterruptedException {
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "HomePageQA", "verifyTopCategorySection");
		String ElementIdentifier = null;
		String expectedURL = null;
		String elementName = null;
		String actualUrl = null;
		String brokenLinkName = null;
		List<String> brokenLinks = new ArrayList<String>();

		for (int i = 0; i < test.length; i++) {

			try {

				ElementIdentifier = test[i][0].trim();
				expectedURL = test[i][1];
				elementName = test[i][2];
				System.out.println(" " + elementName + " " + ElementIdentifier + " " + expectedURL);
				log.error("Verifying link :" + elementName);
				webPage.findObjectByxPath(ElementIdentifier).click();
				log.info("Clicking on link");
				actualUrl = webPage.getCurrentUrl();

				webPage.getBackToUrl();
				Assert.assertTrue(actualUrl.endsWith(expectedURL), " failed " + actualUrl + " " + expectedURL);

			} catch (Throwable e) {
				webPage.navigateToUrl(url);
				brokenLinks.add(elementName);
				log.error("Failed to verify link :" + elementName);
				log.error(e.getMessage());
			}
		}
		if (brokenLinks.size() > 0) {
			Assert.fail("Link " + Arrays.deepToString(brokenLinks.toArray()) + " are not working as expected");
		}
		log.info("testing verifyTopCategorySection completed------>");

	}

	@Test(dataProvider = "tafDataProvider", dataProviderClass = TafExcelDataProvider.class, priority = 15, enabled = false, description = "Verify Page Title")
	@ITafExcelDataProviderInputs(excelFile = "CreditAppData", excelsheet = "HomePageQA", dataKey = "verifyBuildYourOwnFinancialFutureBanner")
	public void verifyBuildYourOwnFinancialFutureBanner(ITestContext context, TestParameters inputs)
			throws PageException, InterruptedException {
		try {
			if (testType.equalsIgnoreCase("Web")) {
				webPage.findObjectByxPath(inputs.getParamMap().get("LinkIdentifier")).click();
			} else {
				webPage.findObjectByxPath(inputs.getParamMap().get("MobileLinkIdentifier")).click();

			}
			log.info("Clicked on element " + inputs.getParamMap().get("ElementName"));
			Assert.assertTrue(webPage.getCurrentUrl().endsWith(inputs.getParamMap().get("Expected")),
					" Failed : " + webPage.getCurrentUrl() + " " + inputs.getParamMap().get("Expected"));
			webPage.getBackToUrl();
		} catch (Throwable e) {
			webPage.navigateToUrl(url);
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(dataProvider = "tafDataProvider", dataProviderClass = TafExcelDataProvider.class, priority = 16, enabled = false, description = "Verify Page Title")
	@ITafExcelDataProviderInputs(excelFile = "CreditAppData", excelsheet = "HomePageQA", dataKey = "verifyHelpChildrenBanner")
	public void verifyHelpChildrenBanner(ITestContext context, TestParameters inputs)
			throws PageException, InterruptedException {
		try {
			webPage.findObjectByxPath(inputs.getParamMap().get("LinkIdentifier")).click();
			log.info("Clicked on element " + inputs.getParamMap().get("ElementName"));
			Assert.assertTrue(webPage.getCurrentUrl().endsWith(inputs.getParamMap().get("Expected")),
					" Failed : " + webPage.getCurrentUrl() + " " + inputs.getParamMap().get("Expected"));
			webPage.getBackToUrl();
		} catch (Throwable e) {
			webPage.navigateToUrl(url);
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(dataProvider = "tafDataProvider", dataProviderClass = TafExcelDataProvider.class, priority = 17, enabled = false, description = "Verify Page Title")
	@ITafExcelDataProviderInputs(excelFile = "CreditAppData", excelsheet = "HomePageQA", dataKey = "verifySixReasonsBanner")
	public void verifySixReasonsBanner(ITestContext context, TestParameters inputs)
			throws PageException, InterruptedException {
		try {
			webPage.findObjectByxPath(inputs.getParamMap().get("LinkIdentifier")).click();
			log.info("Clicked on element " + inputs.getParamMap().get("ElementName"));
			Assert.assertTrue(webPage.getCurrentUrl().endsWith(inputs.getParamMap().get("Expected")),
					" Failed : " + webPage.getCurrentUrl() + " " + inputs.getParamMap().get("Expected"));
			webPage.getBackToUrl();
		} catch (Throwable e) {
			webPage.navigateToUrl(url);
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(dataProvider = "tafDataProvider", dataProviderClass = TafExcelDataProvider.class, priority = 18, enabled = false, description = "Verify Page Title")
	@ITafExcelDataProviderInputs(excelFile = "CreditAppData", excelsheet = "HomePageQA", dataKey = "verifyPromotionsBanner")
	public void verifyPromotionsBanner(ITestContext context, TestParameters inputs)
			throws PageException, InterruptedException {
		try {
			webPage.findObjectByxPath(inputs.getParamMap().get("LinkIdentifier")).click();
			log.info("Clicked on element " + inputs.getParamMap().get("ElementName"));
			Assert.assertTrue(webPage.getCurrentUrl().endsWith(inputs.getParamMap().get("Expected")),
					" Failed : " + webPage.getCurrentUrl() + " " + inputs.getParamMap().get("Expected"));
			webPage.getBackToUrl();
		} catch (Throwable e) {
			webPage.navigateToUrl(url);
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 19, enabled = false, description = "")
	public void verifyFollowUsSection() throws PageException, InterruptedException {
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "HomePageQA", "verifyFollowUsSection");
		String ElementIdentifier = null;
		String expectedURL = null;
		String elementName = null;
		String actualUrl = null;
		String brokenLinkName = null;
		List<String> brokenLinks = new ArrayList<String>();

		for (int i = 0; i < test.length; i++) {

			try {

				ElementIdentifier = test[i][0].trim();
				expectedURL = test[i][1];
				elementName = test[i][2];
				System.out.println(" " + elementName + " " + ElementIdentifier + " " + expectedURL);
				log.error("Verifying link :" + elementName);
				webPage.findObjectByxPath(ElementIdentifier).click();
				log.info("Clicking on link");
				actualUrl = webPage.getCurrentUrl();

				webPage.getBackToUrl();
				Assert.assertTrue(actualUrl.endsWith(expectedURL), " failed " + actualUrl + " " + expectedURL);

			} catch (Throwable e) {
				webPage.navigateToUrl(url);
				brokenLinks.add(elementName);
				log.error("Failed to verify link :" + elementName);
				log.error(e.getMessage());
			}
		}
		if (brokenLinks.size() > 0) {
			Assert.fail("Link " + Arrays.deepToString(brokenLinks.toArray()) + " are not working as expected");
		}
		log.info("testing verifyTopCategorySection completed------>");

	}

	@Test(dataProvider = "tafDataProvider", dataProviderClass = TafExcelDataProvider.class, priority = 20, enabled = false, description = "")
	@ITafExcelDataProviderInputs(excelFile = "CreditAppData", excelsheet = "HomePageQA", dataKey = "verifyBBBRatingBanner")
	public void verifyBBBRatingBanner(ITestContext context, TestParameters inputs)
			throws PageException, InterruptedException {
		try {
			webPage.findObjectByxPath(inputs.getParamMap().get("LinkIdentifier")).click();
			log.info("Clicked on element " + inputs.getParamMap().get("ElementName"));
			Assert.assertEquals(webPage.getCurrentUrl(), inputs.getParamMap().get("Expected"), " Failed : Actual : "
					+ webPage.getCurrentUrl() + " Expected : " + inputs.getParamMap().get("Expected"));
			webPage.getBackToUrl();
		} catch (Throwable e) {
			webPage.navigateToUrl(url);
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 21, enabled = false, description = "")
	public void verifyFooterAboutConnsLinks() throws PageException, InterruptedException {
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "HomePageQA", "verifyFooterAboutConnsLinks");
		String ElementIdentifier = null;
		String expectedURL = null;
		String elementName = null;
		String actualUrl = null;
		String brokenLinkName = null;
		List<String> brokenLinks = new ArrayList<String>();

		for (int i = 0; i < test.length; i++) {

			try {

				ElementIdentifier = test[i][0].trim();
				expectedURL = test[i][1];
				elementName = test[i][2];
				System.out.println(" " + elementName + " " + ElementIdentifier + " " + expectedURL);
				log.error("Verifying link :" + elementName);
				webPage.findObjectByLink(ElementIdentifier).click();
				log.info("Clicking on link");
				actualUrl = webPage.getCurrentUrl();

				webPage.getBackToUrl();
				if (expectedURL.contains("http")) {
					Assert.assertEquals(actualUrl, expectedURL,
							" Failed Actual : " + actualUrl + " Expected :  " + expectedURL);
				} else {
					Assert.assertTrue(actualUrl.endsWith(expectedURL),
							" Failed Actual : " + actualUrl + " Expected :  " + expectedURL);
				}

			} catch (Throwable e) {
				webPage.navigateToUrl(url);
				brokenLinks.add(elementName);
				log.error("Failed to verify link :" + elementName);
				log.error(e.getMessage());
			}
		}
		if (brokenLinks.size() > 0) {
			Assert.fail("Link " + Arrays.deepToString(brokenLinks.toArray()) + " are not working as expected");
		}
		log.info("testing verifyFooterAboutConnsLinks completed------>");

	}

	@Test(priority = 22, enabled = false, description = "")
	public void verifyFooterCustomerServiceSectionLinks() throws PageException, InterruptedException {
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "HomePageQA",
				"verifyFooterCustomerServiceSectionLinks");
		String ElementIdentifier = null;
		String expectedURL = null;
		String elementName = null;
		String actualUrl = null;
		String brokenLinkName = null;
		List<String> brokenLinks = new ArrayList<String>();

		for (int i = 0; i < test.length; i++) {

			try {

				ElementIdentifier = test[i][0].trim();
				expectedURL = test[i][1];
				elementName = test[i][2];
				System.out.println(" " + elementName + " " + ElementIdentifier + " " + expectedURL);
				log.error("Verifying link :" + elementName);
				String title = webPage.getPageTitle();
				webPage.findObjectByxPath(ElementIdentifier).click();
				log.info("Clicking on link");
				if (elementName.equalsIgnoreCase("Weekly Ad")) {
					webPage.switchWindow("Conns Weekly Ad – low prices on furniture and more");
					actualUrl = webPage.getCurrentUrl();
					webPage.switchWindow(title);
				} else {
					actualUrl = webPage.getCurrentUrl();

					webPage.getBackToUrl();
				}
				if (expectedURL.contains("http")) {
					Assert.assertEquals(actualUrl, expectedURL,
							" Failed Actual : " + actualUrl + " Expected :  " + expectedURL);
				} else {
					Assert.assertTrue(actualUrl.endsWith(expectedURL),
							" Failed Actual : " + actualUrl + " Expected :  " + expectedURL);
				}

			} catch (Throwable e) {
				webPage.navigateToUrl(url);
				brokenLinks.add(elementName);
				log.error("Failed to verify link :" + elementName);
				log.error(e.getMessage());
			}
		}
		if (brokenLinks.size() > 0) {
			Assert.fail("Link " + Arrays.deepToString(brokenLinks.toArray()) + " are not working as expected");
		}
		log.info("testing verifyFooterCustomerServiceSectionLinks completed------>");

	}

	@Test(dataProvider = "tafDataProvider", dataProviderClass = TafExcelDataProvider.class, priority = 23, enabled = false, description = "")
	@ITafExcelDataProviderInputs(excelFile = "CreditAppData", excelsheet = "HomePageQA", dataKey = "verifyFooterWeAccpet")
	public void verifyFooterWeAccpet(ITestContext context, TestParameters inputs)
			throws PageException, InterruptedException {
		try {

			webPage.findObjectByxPath(inputs.getParamMap().get("ElementIdentifier")).isDisplayed();

		} catch (Throwable e) {
			webPage.navigateToUrl(url);
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(dataProvider = "tafDataProvider", dataProviderClass = TafExcelDataProvider.class, priority = 24, enabled = false, description = "")
	@ITafExcelDataProviderInputs(excelFile = "CreditAppData", excelsheet = "HomePageQA", dataKey = "verifyFooterCopyright")
	public void verifyFooterCopyright(ITestContext context, TestParameters inputs)
			throws PageException, InterruptedException {
		try {
			Assert.assertEquals(webPage.findObjectByxPath(inputs.getParamMap().get("ElementIdentifier")).getText(),
					inputs.getParamMap().get("ExpectedText"), "Copyright Text Failed to Match");

		} catch (Throwable e) {
			webPage.navigateToUrl(url);
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 25, enabled = false, description = "verifyYourCart")
	public void verifyYourCart() throws PageException, InterruptedException {
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "HomePageQA", "verifyYourCart");

		try {
			if (testType.equalsIgnoreCase("Web")) {
				// hover on parent menu option in desktop browser
				webPage.hoverOnElement(By.xpath(test[0][0]));
				log.info("Clicking on Sub - link");
				webPage.findObjectByxPath(test[0][1]).click();
				String ExpectedProduct = webPage.findObjectByxPath(test[0][2]).getText();
				log.info("Clicking on product");
				webPage.findObjectByxPath(test[0][2]).click();
				log.info("Clicking on Add to product");
				webPage.findObjectByxPath(test[0][3]).click();

				String ZipCode = test[0][5];

				log.info("Adding Zip code");

				webPage.findObjectByxPath(test[0][4]).clear();
				webPage.findObjectByxPath(test[0][4]).sendKeys(ZipCode);
				log.info("Clicking on Update button");

				webPage.findObjectByxPath(test[0][6]).click();
				Thread.sleep(8000);

				log.info("Clicking on Add to Cart button");
				webPage.findObjectByxPath(test[0][7]).click();
				log.info("Clicking on YourCart link");
				webPage.findObjectByxPath(test[0][8]).click();

				String ExpecyedProductinCart = webPage.findObjectByxPath(test[0][9]).getText();
				Assert.assertEquals(ExpectedProduct, ExpecyedProductinCart);
				log.info("Clicking on CheckOut button");
				webPage.findObjectByxPath(test[0][10]).click();
				String ExpectedURL = test[0][11];
				Assert.assertEquals(ExpectedURL, webPage.getCurrentUrl());
				log.info("Verified Your Cart");

			}

		} catch (Exception e) {
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("verifyPageTitle failed");
			log.error(e.getMessage());
		}

	}
}
