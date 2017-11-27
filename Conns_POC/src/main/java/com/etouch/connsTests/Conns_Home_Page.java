package com.etouch.connsTests;

import java.awt.AWTException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.http.client.ClientProtocolException;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
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
import com.etouch.connsPages.ConnsHomePage;
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
import com.gargoylesoftware.htmlunit.javascript.configuration.BrowserName;

@IExcelDataFiles(excelDataFiles = { "ConnsHomePageData=testData" })
public class Conns_Home_Page extends BaseTest {
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
	String storeLocatorURL = "";
	String[][] commonData;
	ConnsHomePage ConnsHomePage;
	protected static LinkedHashMap<Long, WebPage> webPageMap = new LinkedHashMap<Long, WebPage>();

	@BeforeClass(alwaysRun = true)
	public void setUp(ITestContext context) throws InterruptedException, FileNotFoundException, IOException {
		try {
			testBedName = context.getCurrentXmlTest().getAllParameters().get("testBedName");
			CommonUtil.sop("Test bed Name is " + testBedName);
			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			connsStoreLocatorPage = new ConnsStoreLocatorPage();
			commonMethods = new CommonMethods();
			browserName = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getBrowser().getName();
			log.info("Test Type is : " + testType);
			try {
				testEnv = System.getenv().get("Environment");
				log.info("testEnv is : " + testEnv);
				path = Paths.get(TestBedManager.INSTANCE.getProfile().getXlsDataConfig().get("testData"));
				DataFilePath = path.toAbsolutePath().toString().replace("Env", testEnv);
				log.info("DataFilePath After is : " + DataFilePath);
				commonData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator", "storeLocatorCommonElements");
				storeLocatorURL = commonData[0][0];
				platform = testBed.getPlatform().getName().toUpperCase();
				url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL();
				synchronized (this) {
					webPage = new WebPage(context);
					mainPage = new ConnsMainPage(url, webPage);
					ConnsHomePage = new ConnsHomePage(url, webPage);
					webPageMap.put(Thread.currentThread().getId(), webPage);
				}
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
				log.info("errr is " + e);
				SoftAssertor.addVerificationFailure(e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			CommonUtil.sop("Error is for" + testBedName + " -----------" + e);
			SoftAssertor.addVerificationFailure(e.getMessage());
		}
	}

	/*@AfterTest
	public void releaseResources() throws IOException, AWTException {
		webPage.getDriver().quit();
	}*/
	
	
	@AfterTest(alwaysRun = true)
	public void releaseResources() throws IOException, AWTException {
		// SpecializedScreenRecorder.stopVideoRecording();
		//webPage.getDriver().quit();
		webPageMap.get(Thread.currentThread().getId()).getDriver().quit();
	}

	/**
	 * Test Case - 001 - Verify title and URL of page Conns Home Page
	 * 
	 */
	@Test(priority = 1, enabled = true, description = "Verify HomePage title")
	public void Verify_HomePage_Title() {
		SoftAssert softAssert = new SoftAssert();
		try {
			commonMethods.waitForPageLoad(webPage, softAssert);
			// webPage.getDriver().get("http://connsecommdev-1365538477.us-east-1.elb.amazonaws.com/conns_rwd/");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "Verifytitle");
			String ExpectedTitle = test[0][1];
			softAssert.assertEquals(webPage.getPageTitle(), ExpectedTitle,
					"Page Title verification failed. Expected title - " + ExpectedTitle + " Actual title - "
							+ webPage.getPageTitle());
			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_HomePage_Title");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 002 - Verify Font Size and Style of specified on element on
	 * Conns Home Page
	 * 
	 */
	@Test(priority = 2, enabled = true, description = "Verify_Font_And_Size")
	public void Verify_Font_And_Size() {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] ExpectedFontValuesWeb = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page",
					"VerifyFontandSizeWeb");
			String[][] ExpectedFontValuesMobile = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page",
					"VerifyFontandSizeMobile");
			String[][] ExpectedFontValuesTab = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page",
					"VerifyFontandSizeTab");
			JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
			int width = ((Long) js.executeScript("return window.innerWidth || document.body.clientWidth")).intValue();
			log.info("width value calculated is :" + width);
			int height = ((Long) js.executeScript("return window.innerHeight || document.body.clientHeight"))
					.intValue();
			log.info("height value calculated is :" + height);
			Dimension dimension = new Dimension(width, height);
			log.info("Dimensions" + dimension);
			// Dimension[width=600,height=792]
			  if (testType.equalsIgnoreCase("Web") && (!(testBedName.equalsIgnoreCase("Edge") ))) {

				for (int i = 0; i < ExpectedFontValuesWeb.length; i++) {
					List<String> actualCssValues = commonMethods.getFontProperties(webPage, ExpectedFontValuesWeb[i][1],
							softAssert);
					if (!ExpectedFontValuesWeb[i][2].equalsIgnoreCase("NA")) {
						softAssert.assertTrue(actualCssValues.get(0).contains(ExpectedFontValuesWeb[i][2]),
								"CSS value verification failed for link " + ExpectedFontValuesWeb[i][0]
										+ ". Expected font Size: " + ExpectedFontValuesWeb[i][2] + " Actual Font Size: "
										+ actualCssValues.get(0));
					}
					if (!ExpectedFontValuesWeb[i][3].equalsIgnoreCase("NA")) {
						softAssert.assertTrue(actualCssValues.get(1).contains(ExpectedFontValuesWeb[i][3]),
								"CSS value verification failed for link " + ExpectedFontValuesWeb[i][0]
										+ ". Expected font color: " + ExpectedFontValuesWeb[i][3]
												+ " Actual font color: " + actualCssValues.get(1));
					}
					if (!ExpectedFontValuesWeb[i][4].equalsIgnoreCase("NA")) {
						softAssert.assertTrue(
								actualCssValues.get(2).toLowerCase()
								.contains((ExpectedFontValuesWeb[i][4]).toLowerCase()),
								"CSS value verification failed for link " + ExpectedFontValuesWeb[i][0]
										+ ". Expected font family: " + ExpectedFontValuesWeb[i][4]
												+ " Actual font family: " + actualCssValues.get(2));
					}
					if (!ExpectedFontValuesWeb[i][5].equalsIgnoreCase("NA")) {
						softAssert.assertTrue(
								actualCssValues.get(3).toLowerCase()
								.contains((ExpectedFontValuesWeb[i][5]).toLowerCase()),
								"CSS value verification failed for link " + ExpectedFontValuesWeb[i][0]
										+ ". Expected font weight: " + ExpectedFontValuesWeb[i][5]
												+ " Actual font weight: " + actualCssValues.get(3));
					}
					if (!ExpectedFontValuesWeb[i][6].equalsIgnoreCase("NA")) {
						softAssert.assertTrue(
								actualCssValues.get(4).toLowerCase()
								.contains((ExpectedFontValuesWeb[i][6]).toLowerCase()),
								"CSS value verification failed for link " + ExpectedFontValuesWeb[i][0]
										+ ". Expected font background color: " + ExpectedFontValuesWeb[i][6]
												+ " Actual font background color: " + actualCssValues.get(4));
					}
				}
			}
			if (testType.equalsIgnoreCase("Mobile") || (testType.equalsIgnoreCase("Web") && (browserName.equalsIgnoreCase("Edge")))) {
				if (width > 599 || width < 800) {
					// if(deviceName.contains("Tab")){
					for (int i = 0; i < ExpectedFontValuesTab.length; i++) {
						List<String> actualCssValues = commonMethods.getFontProperties(webPage,
								ExpectedFontValuesTab[i][1], softAssert);
						if (!ExpectedFontValuesTab[i][2].equalsIgnoreCase("NA")) {
							softAssert.assertTrue(actualCssValues.get(0).contains(ExpectedFontValuesTab[i][2]),
									"CSS value verification failed for link " + ExpectedFontValuesTab[i][0]
											+ ". Expected font Size: " + ExpectedFontValuesTab[i][2]
													+ " Actual Font Size: " + actualCssValues.get(0));
						}
						/*if (!ExpectedFontValuesTab[i][3].equalsIgnoreCase("NA")) {
							softAssert.assertTrue(actualCssValues.get(1).contains(ExpectedFontValuesTab[i][3]),
									"CSS value verification failed for link " + ExpectedFontValuesTab[i][0]
											+ ". Expected font color: " + ExpectedFontValuesTab[i][3]
											+ " Actual font color: " + actualCssValues.get(1));
						}
						if (!ExpectedFontValuesTab[i][4].equalsIgnoreCase("NA")) {
							softAssert.assertTrue(
									actualCssValues.get(2).toLowerCase()
											.contains((ExpectedFontValuesTab[i][4]).toLowerCase()),
									"CSS value verification failed for link " + ExpectedFontValuesTab[i][0]
											+ ". Expected font family: " + ExpectedFontValuesTab[i][4]
											+ " Actual font family: " + actualCssValues.get(2));
						}
						if (!ExpectedFontValuesTab[i][5].equalsIgnoreCase("NA")) {
							softAssert.assertTrue(
									actualCssValues.get(3).toLowerCase()
											.contains((ExpectedFontValuesTab[i][5]).toLowerCase()),
									"CSS value verification failed for link " + ExpectedFontValuesTab[i][0]
											+ ". Expected font weight: " + ExpectedFontValuesTab[i][5]
											+ " Actual font weight: " + actualCssValues.get(3));
						}
						if (!ExpectedFontValuesTab[i][6].equalsIgnoreCase("NA")) {
							softAssert.assertTrue(
									actualCssValues.get(4).toLowerCase()
											.contains((ExpectedFontValuesTab[i][6]).toLowerCase()),
									"CSS value verification failed for link " + ExpectedFontValuesTab[i][0]
											+ ". Expected font background color: " + ExpectedFontValuesTab[i][6]
											+ " Actual font background color: " + actualCssValues.get(4));
						}
						if (!ExpectedFontValuesTab[i][7].equalsIgnoreCase("NA")) {
							softAssert.assertTrue(
									actualCssValues.get(5).toLowerCase()
											.contains((ExpectedFontValuesTab[i][7]).toLowerCase()),
									"CSS value verification failed for link " + ExpectedFontValuesTab[i][0]
											+ ". Expected Text Align: " + ExpectedFontValuesTab[i][7]
											+ " Actual Text Align: " + actualCssValues.get(5));
						}
						if (!ExpectedFontValuesTab[i][8].equalsIgnoreCase("NA")) {
							softAssert.assertTrue(
									actualCssValues.get(6).toLowerCase()
											.contains((ExpectedFontValuesTab[i][8]).toLowerCase()),
									"CSS value verification failed for link " + ExpectedFontValuesTab[i][0]
											+ ". Expected Text Transform: " + ExpectedFontValuesTab[i][8]
											+ " Actual Text Transform: " + actualCssValues.get(6));
						}*/
					}
				} else {
					for (int i = 0; i < ExpectedFontValuesMobile.length; i++) {
						List<String> actualCssValues = commonMethods.getFontProperties(webPage,
								ExpectedFontValuesMobile[i][1], softAssert);
						if (!ExpectedFontValuesMobile[i][2].equalsIgnoreCase("NA")) {
							softAssert.assertTrue(actualCssValues.get(0).contains(ExpectedFontValuesMobile[i][2]),
									"CSS value verification failed for link " + ExpectedFontValuesMobile[i][0]
											+ ". Expected font Size: " + ExpectedFontValuesMobile[i][2]
													+ " Actual Font Size: " + actualCssValues.get(0));
						}
						if (!ExpectedFontValuesMobile[i][3].equalsIgnoreCase("NA")) {
							softAssert.assertTrue(actualCssValues.get(1).contains(ExpectedFontValuesMobile[i][3]),
									"CSS value verification failed for link " + ExpectedFontValuesMobile[i][0]
											+ ". Expected font color: " + ExpectedFontValuesMobile[i][3]
													+ " Actual font color: " + actualCssValues.get(1));
						}
						if (!ExpectedFontValuesMobile[i][4].equalsIgnoreCase("NA")) {
							softAssert.assertTrue(
									actualCssValues.get(2).toLowerCase()
									.contains((ExpectedFontValuesMobile[i][4]).toLowerCase()),
									"CSS value verification failed for link " + ExpectedFontValuesMobile[i][0]
											+ ". Expected font family: " + ExpectedFontValuesMobile[i][4]
													+ " Actual font family: " + actualCssValues.get(2));
						}
						if (!ExpectedFontValuesMobile[i][5].equalsIgnoreCase("NA")) {
							softAssert.assertTrue(
									actualCssValues.get(3).toLowerCase()
									.contains((ExpectedFontValuesMobile[i][5]).toLowerCase()),
									"CSS value verification failed for link " + ExpectedFontValuesMobile[i][0]
											+ ". Expected font weight: " + ExpectedFontValuesMobile[i][5]
													+ " Actual font weight: " + actualCssValues.get(3));
						}
						if (!ExpectedFontValuesMobile[i][6].equalsIgnoreCase("NA")) {
							softAssert.assertTrue(
									actualCssValues.get(4).toLowerCase()
									.contains((ExpectedFontValuesMobile[i][6]).toLowerCase()),
									"CSS value verification failed for link " + ExpectedFontValuesMobile[i][0]
											+ ". Expected font background color: " + ExpectedFontValuesMobile[i][6]
													+ " Actual font background color: " + actualCssValues.get(4));
						}
						if (!ExpectedFontValuesMobile[i][7].equalsIgnoreCase("NA")) {
							softAssert.assertTrue(
									actualCssValues.get(5).toLowerCase()
									.contains((ExpectedFontValuesMobile[i][7]).toLowerCase()),
									"CSS value verification failed for link " + ExpectedFontValuesMobile[i][0]
											+ ". Expected Text Align: " + ExpectedFontValuesMobile[i][7]
													+ " Actual Text Align: " + actualCssValues.get(5));
						}
						if (!ExpectedFontValuesMobile[i][8].equalsIgnoreCase("NA")) {
							softAssert.assertTrue(
									actualCssValues.get(6).toLowerCase()
									.contains((ExpectedFontValuesMobile[i][8]).toLowerCase()),
									"CSS value verification failed for link " + ExpectedFontValuesMobile[i][0]
											+ ". Expected Text Transform: " + ExpectedFontValuesMobile[i][8]
													+ " Actual Text Transform: " + actualCssValues.get(6));
						}
					}
				}
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			// mainPage.getScreenShotForFailure(webPage,
			// "Verify_Font_And_Size");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 003 - Verify broken images on Conns Home Page
	 * 
	 */
	@Test(priority = 3, enabled = true, description = "Verify_Broken_Images")
	public void Verify_Broken_Images() throws ClientProtocolException, IOException {
		commonMethods.verifyBrokenImage(webPage);
	}
	/**
	 * Test Case - 04 - verify Footer We Accpet Conns Home Page
	 * 
	 */
	@Test(priority = 4, enabled = true, description = "Verify_Element_Visibility_Under_We_Accept_Section")
	public void Verify_Element_Visibility_Under_We_Accept_Section() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyFooterWeAccpet");
			//boolean statusOfWeAccept = commonMethods.verifyElementisPresent(webPage, testData[0][0], softAssert);
			/***commented above code to use isElementPresentCheckUsingJavaScriptExecutor***/	
			boolean statusOfWeAccept = commonMethods.isElementPresentCheckUsingJavaScriptExecutor(webPage, testData[0][0], softAssert);
			softAssert.assertSame(statusOfWeAccept, true);
			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_Element_Visibility_Under_We_Accept_Section");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 05 - verify Footer Copyright section
	 * 
	 */
	@Test(priority = 5, enabled = true, description = "Verify Footer Copyright section")
	public void Verify_Content_Under_Footer_Copyright_Section() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyFooterCopyright");
			String homeplusText1 = commonMethods.getTextbyXpath(webPage, testData[0][0], softAssert);
			softAssert.assertEquals(homeplusText1, testData[0][1]);
		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_Content_Under_Footer_Copyright_Section");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	/**
	 * Test Case - 006 - verify Your Cart functionality by adding product in
	 * cart Conns Home Page
	 * @throws InterruptedException 
	 * 
	 */
	@Test(priority = 6, enabled = true, description = "Verify Your Cart Functionality")
	public void Verify_Your_Cart_Functionality() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		Thread.sleep(2000);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyYourCart");
			String cartText = commonMethods.getTextbyXpath(webPage, testData[0][0], softAssert);
			softAssert.assertTrue(cartText.toLowerCase().contains(testData[0][1].toLowerCase()),cartText+"does not contain"+testData[0][1]);
			//	commonMethods.clickElementbyXpath(webPage, testData[0][0], softAssert);
			//	commonMethods.waitForGivenTime(2, softAssert);
			//	commonMethods.clickElementbyXpath(webPage, testData[0][0], softAssert);
			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_Your_Cart_Functionality");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	/**
	 * Test Case - 007 - verify Links Above Header Conns Home Page
	 * 
	 */
	@Test(priority = 7, enabled = true, description = "Verify Links Available Above Header Section")
	public void Verify_LinksRedirection_Of_Above_Header_Section() {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = null;
		webPage.getDriver().get(url);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyLinksAboveHeader");
			for (int i = 0; i < testData.length; i++) {
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][3].equalsIgnoreCase("NA")))) {
					log.info("Inside the first if. Value of I : " + i);
					commonMethods.clickElementbyXpath(webPage, testData[i][3], softAssert);
					Thread.sleep(1000);
				}
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][2].equalsIgnoreCase("NA")))) {
					log.info("Inside the 2nd if. Value of I : " + i);
					ActualURL = commonMethods.clickElementbyXpathAndGetURL(webPage, testData[i][2], softAssert);
					softAssert.assertTrue(ActualURL.contains(testData[i][4]),
							"Link Name  :" + testData[i][0] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][4]);
				}
				if (testType.equalsIgnoreCase("Web")) {
					// replacing "clickAndGetPageURLUsingJS" method with
					// "clickAndGetPageURLUsingJS"
					ActualURL = ConnsHomePage.clickAndGetPageURLUsingJS(webPage, testData[i][1], testData[i][0],
							testData[i][5], softAssert);
					softAssert.assertTrue(ActualURL.contains(testData[i][4]),
							"Link Name  :" + testData[i][0] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][4]);
				}
				webPage.getDriver().get(url);
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	/**
	 * Test Case - 008 - verify links In Header Conns Home Page
	 * 
	 */
	@Test(priority = 8, enabled = true, description = "Verify Links In Header Section")
	public void Verify_Links_In_Header_Section() {
		SoftAssert softAssert = new SoftAssert();
		webPage.getDriver().get(url);
		String ActualURL = "";
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyLinksInHeader");
			for (int i = 0; i < testData.length; i++) {
				if (testType.equalsIgnoreCase("Mobile")) {
					ActualURL = commonMethods.clickAndGetPageURL(webPage, testData[i][1],testData[i][0], softAssert);
				} else {
					ActualURL = ConnsHomePage.clickAndGetPageURLUsingJS(webPage, testData[i][1], testData[i][0],
							testData[i][5], softAssert);
				}
				softAssert.assertTrue(ActualURL.contains(testData[i][4]), "Link Name  :" + testData[i][0] + " : failed "
						+ "Actual URL is  :" + ActualURL + " " + "Expected URL is  :" + testData[i][4]);
				webPage.getDriver().get(url);
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_Links_In_Header_Section");
			// mainPage.getScreenShotForFailure(webPage,
			// "Verify_LinksRedirection_Of_Above_Header_Section");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	/**
	 * Test Case - 009 - verify links under Furniture & Mattresses main menu
	 * Verify link status code and href value
	 * Conns Home Page
	 * 
	 */
	@Test(priority = 9, enabled = true, description = "Verify LinksRedirection Under Furniture And Mattresses Menu")
	public void Verify_LinksRedirection_Under_Furniture_And_Mattresses_Menu()
			throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		//String thirdParentXpath =  null;
		webPage.getDriver().get(url);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyLinksForFurnitureAndMattresses");
			String elementXpath=null;
			Thread.sleep(2000);

			//if (testType.equalsIgnoreCase("Web")) {
			//  if (testType.equalsIgnoreCase("Web") && (!(browserName.equalsIgnoreCase("Edge") ))) {
			 if (testType.equalsIgnoreCase("Web") && (!(testBedName.equalsIgnoreCase("Edge") ))) {
				  log.info("TestType is  : " +testType +"************ testBedName **************" +testBedName );
				for (int i = 0; i < testData.length; i++) {
					log.info("Iteration under test : " + i);
					commonMethods.hoverOnelementbyXpath(webPage, testData[0][0], softAssert);
					commonMethods.verifyLinkStatusCodeAndHrefValue(webPage, testData[i][2], testData[i][1], testData[i][2], softAssert);
				}
			}
		//	if (testType.equalsIgnoreCase("Mobile")) {
			  if (testType.equalsIgnoreCase("Mobile") || (testType.equalsIgnoreCase("Web") && (testBedName.equalsIgnoreCase("Edge")))) {
				  log.info("TestType is  : " +testType +"************ testBedName **************" +browserName );
				  for (int i = 0; i < testData.length; i++) {
					log.info("Iteration under test : " + i);
					if(!(testData[i][4].equalsIgnoreCase("NA"))){
						// verify if element was visited in previous iteration, if yes then it will skip navigation steps
						if(elementXpath==null || !elementXpath.equals(testData[i][6]))
						{
							//click on Main Menu
							commonMethods.clickElementbyXpath(webPage, testData[i][4], softAssert);
							commonMethods.Click_On_Element_JS(webPage, testData[i][5], softAssert);
							if(i>=30){
								webPage.scrollToElement(webPage.findObjectByxPath("(//*[@id='li-primary-pronav-furniture---mattresses']//a)[33]"));
							}
							//if element does not have child then use element as child
							elementXpath=testData[i][6];
							if(!testData[i][6].equals("NA"))
							{
								//if element has child
								commonMethods.clickElementbyXpath(webPage, testData[i][6], softAssert);
								//	elementXpath=testData[i][6];
								if(i>=0){
								}
							}
						}
						commonMethods.verifyLinkStatusCodeAndHrefValue(webPage, testData[i][2], testData[i][7], testData[i][2], softAssert);
					}
					else{
						if(i>0)
						{
							commonMethods.clickElementbyXpath(webPage, testData[i-1][4], softAssert);
							elementXpath=null;
						}
					}
				}
			} 
			softAssert.assertAll();
		}catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_LinksRedirection_Under_Furniture_And_Mattresses_Menu");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	/**
	 * Test Case - 010 - verify links under Appliances main menu Conns Home Page
	 * Verify link status code and href value
	 */
	@Test(priority = 10, enabled = true, description = "Verify LinksRedirection Under Appliances Menu")
	public void Verify_LinksRedirection_Under_Appliances_Menu() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
		//String thirdParentXpath =  null;
		webPage.getDriver().get(url);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyLinksForAppliance");
			String elementXpath=null;
			Thread.sleep(2000);

			//if (testType.equalsIgnoreCase("Web")) {
			//  if (testType.equalsIgnoreCase("Web") && (!(browserName.equalsIgnoreCase("Edge") ))) {
			 if (testType.equalsIgnoreCase("Web") && (!(testBedName.equalsIgnoreCase("Edge") ))) {

				  log.info("TestType is  : " +testType +"************ testBedName **************" +testBedName );
				for (int i = 0; i < testData.length; i++) {
					log.info("Iteration under test : " + i);
					Thread.sleep(2000);
					commonMethods.hoverOnelementbyXpath(webPage, testData[0][0], softAssert);
					/*WebElement element_2 = webPage.getDriver().findElement(By.xpath(commonData[0][0]));					
					js.executeScript("arguments[0].click();", element_2);*/
					Thread.sleep(4000);
					commonMethods.verifyLinkStatusCodeAndHrefValue(webPage, testData[i][2], testData[i][1], testData[i][2], softAssert);
				}
			}
		//	if (testType.equalsIgnoreCase("Mobile")) {
		   if (testType.equalsIgnoreCase("Mobile") || (testType.equalsIgnoreCase("Web") && (testBedName.equalsIgnoreCase("Edge")))) {
			   log.info("TestType is  : " +testType +"************ testBedName **************" +browserName );
			   for (int i = 0; i < testData.length; i++) {
					log.info("Iteration under test : " + i);
					if(!(testData[i][4].equalsIgnoreCase("NA"))){
						// verify if element was visited in previous iteration, if yes then it will skip navigation steps
						if(elementXpath==null || !elementXpath.equals(testData[i][6]))
						{
							//click on Main Menu
							commonMethods.clickElementbyXpath(webPage, testData[i][4], softAssert);

							commonMethods.Click_On_Element_JS(webPage, testData[i][5], softAssert);
							if(i>=50){
								webPage.scrollToElement(webPage.findObjectByxPath("(.//*[@id='li-primary-pronav-appliances']//a)[43]"));
							}
							//if element does not have child then use element as child

							elementXpath=testData[i][6];
							if(!testData[i][6].equals("NA"))
							{
								//if element has child

								commonMethods.clickElementbyXpath(webPage, testData[i][6], softAssert);
								//	elementXpath=testData[i][6];
								if(i>=0){
								}
							}
						}
						commonMethods.verifyLinkStatusCodeAndHrefValue(webPage, testData[i][2], testData[i][7], testData[i][2], softAssert);
					}
					else{
						if(i>0)
						{
							commonMethods.clickElementbyXpath(webPage, testData[i-1][4], softAssert);
							elementXpath=null;
						}
					}
				}
			} 
			softAssert.assertAll();
		}catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_LinksRedirection_Under_Appliances_Menu");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 011 - verify links under TV, Audio & Electronics main menu
	 * Verify link status code and href value
	 * Conns Home Page
	 * 
	 */
	@Test(priority = 11, enabled = true, description = "Verify LinksRedirection Under TV Audio And Electronics Menu")
	public void Verify_LinksRedirection_Under_TV_Audio_And_Electronics_Menu()
			throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		//String thirdParentXpath =  null;
		webPage.getDriver().get(url);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyLinksForTvAudioElectronics");
			String elementXpath=null;
			Thread.sleep(3000);
			/****** using javscript click instead of hoverOnelementbyXpath & reverted  if else conditions for Edge Browser Connection*************/

			if (testType.equalsIgnoreCase("Web")) {
			// if (testType.equalsIgnoreCase("Web") && (browserName.equalsIgnoreCase("Safari")) || (browserName.equalsIgnoreCase("IE") || (browserName.equalsIgnoreCase("InternetExplorer") ))) {
			//if (testType.equalsIgnoreCase("Web") && (!(testBedName.equalsIgnoreCase("Edge") ))) {
				log.info("TestType is  : " +testType +"************ testBedName **************" +testBedName );
			//	commonMethods.hoverOnelementbyXpath(webPage, testData[0][0], softAssert);
				/****** using javascript click instead of hoverOnelementbyXpath*************/
				commonMethods.clickElementbyXpath_usingJavaScript(webPage, testData[0][0], softAssert);	
				for (int i = 0; i < testData.length; i++) {
					log.info("Iteration under test : " + i);
					commonMethods.verifyLinkStatusCodeAndHrefValue(webPage, testData[i][2], testData[i][1], testData[i][2], softAssert);
				}
			}
			if (testType.equalsIgnoreCase("Mobile")) {
		// if (testType.equalsIgnoreCase("Mobile") || (testType.equalsIgnoreCase("Web") && (testBedName.equalsIgnoreCase("Edge")))) {
			 log.info("TestType is  : " +testType +"************ testBedName **************" +testBedName );
			 for (int i = 0; i < testData.length; i++) {
					log.info("Iteration under test : " + i);
					if(!(testData[i][4].equalsIgnoreCase("NA"))){
						// verify if element was visited in previous iteration, if yes then it will skip navigation steps
						if(elementXpath==null || !elementXpath.equals(testData[i][6]))
						{
							//click on Main Menu
							commonMethods.clickElementbyXpath(webPage, testData[i][4], softAssert);

							commonMethods.Click_On_Element_JS(webPage, testData[i][5], softAssert);
							/*if(i>=50){
							webPage.scrollToElement(webPage.findObjectByxPath("(.//*[@id='li-primary-pronav-appliances']//a)[43]"));
						}*/
							//if element does not have child then use element as child

							elementXpath=testData[i][6];
							if(!testData[i][6].equals("NA"))
							{
								//if element has child

								commonMethods.clickElementbyXpath(webPage, testData[i][6], softAssert);
								//	elementXpath=testData[i][6];
								if(i>=0){
								}
							}
						}
						commonMethods.verifyLinkStatusCodeAndHrefValue(webPage, testData[i][2], testData[i][7], testData[i][2], softAssert);
					}
					else{
						if(i>0)
						{
							commonMethods.clickElementbyXpath(webPage, testData[i-1][4], softAssert);
							elementXpath=null;
						}
					}
				}
			} 
			softAssert.assertAll();
		}catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_LinksRedirection_Under_TV_Audio_And_Electronics_Menu");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}		


	/**
	 * Test Case - 012 - verify links under Computer Accessories main menu Conns
	 * Verify link status code and href value
	 * Home Page
	 * 
	 */
	@Test(priority = 12, enabled = true, description = "Verify LinksRedirection Under ComputerAccessories Menu")
	public void Verify_LinksRedirection_Under_ComputerAccessories_Menu() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		//String thirdParentXpath =  null;
		webPage.getDriver().get(url);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyLinksForComputerAccessories");
			String elementXpath=null;
			Thread.sleep(2000);

			//if (testType.equalsIgnoreCase("Web")) {
			//   if (testType.equalsIgnoreCase("Web") && (browserName.equalsIgnoreCase("Safari")) || (browserName.equalsIgnoreCase("IE") || (browserName.equalsIgnoreCase("InternetExplorer") ))) {
			// if (testType.equalsIgnoreCase("Web") && (!(browserName.equalsIgnoreCase("Edge") ))) {
			 if (testType.equalsIgnoreCase("Web") && (!(testBedName.equalsIgnoreCase("Edge") ))) {
				 log.info("TestType is  : " +testType +"************ testBedName **************" +testBedName );
				 Thread.sleep(4000);
				commonMethods.hoverOnelementbyXpath(webPage, testData[0][0], softAssert);
				for (int i = 0; i < testData.length; i++) {
					log.info("Iteration under test : " + i);
					Thread.sleep(2000);
					commonMethods.verifyLinkStatusCodeAndHrefValue(webPage, testData[i][3], testData[i][1], testData[i][2], softAssert);
				}
			}
		//	if (testType.equalsIgnoreCase("Mobile")) {
		   if (testType.equalsIgnoreCase("Mobile") || (testType.equalsIgnoreCase("Web") && (testBedName.equalsIgnoreCase("Edge")))) {
			   log.info("TestType is  : " +testType +"************ testBedName **************" +testBedName );
			   for (int i = 0; i < testData.length; i++) {
					log.info("Iteration under test : " + i);
					if(!(testData[i][4].equalsIgnoreCase("NA"))){
						// verify if element was visited in previous iteration, if yes then it will skip navigation steps
						if(elementXpath==null || !elementXpath.equals(testData[i][6]))
						{
							//click on Main Menu
							commonMethods.clickElementbyXpath(webPage, testData[i][4], softAssert);

							commonMethods.Click_On_Element_JS(webPage, testData[i][5], softAssert);
							/*if(i>=50){
							webPage.scrollToElement(webPage.findObjectByxPath("(.//*[@id='li-primary-pronav-appliances']//a)[43]"));
						}*/
							//if element does not have child then use element as child

							elementXpath=testData[i][6];
							if(!testData[i][6].equals("NA"))
							{
								//if element has child

								commonMethods.clickElementbyXpath(webPage, testData[i][6], softAssert);
								//	elementXpath=testData[i][6];
								if(i>=0){
								}
							}
						}
						commonMethods.verifyLinkStatusCodeAndHrefValue(webPage, testData[i][2], testData[i][7], testData[i][2], softAssert);
					}
					else{
						if(i>0)
						{
							commonMethods.clickElementbyXpath(webPage, testData[i-1][4], softAssert);
							elementXpath=null;
						}
					}
				}
			} 
			softAssert.assertAll();
		}catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_LinksRedirection_Under_ComputerAccessories_Menu");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}	

	/**
	 * Test Case - 013 - verify links under Financing and Promotions main menu
	 * Verify link status code and href value
	 * Conns Home Page
	 * 
	 */
	@Test(priority = 13, enabled = true, description = "Verify LinksRedirection Under FinancingPromotions Menu")
	public void Verify_LinksRedirection_Under_FinancingPromotions_Menu() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		webPage.getDriver().get(url);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyLinksForFinancingPromotions");
			String elementXpath=null;
			Thread.sleep(2000);

			//if (testType.equalsIgnoreCase("Web")) {
			//   if (testType.equalsIgnoreCase("Web") && (browserName.equalsIgnoreCase("Safari")) || (browserName.equalsIgnoreCase("IE") || (browserName.equalsIgnoreCase("InternetExplorer") ))) {
			// if (testType.equalsIgnoreCase("Web") && (!(browserName.equalsIgnoreCase("Edge") ))) {
			 if (testType.equalsIgnoreCase("Web") && (!(testBedName.equalsIgnoreCase("Edge") ))) {

				 log.info("TestType is  : " +testType +"************ testBedName **************" +testBedName );
				 commonMethods.hoverOnelementbyXpath(webPage, testData[0][0], softAssert);
				for (int i = 0; i < testData.length; i++) {
					log.info("Iteration under test : " + i);
					commonMethods.verifyLinkStatusCodeAndHrefValue(webPage, testData[i][2], testData[i][1], testData[i][2], softAssert);
				}
			}
			//if (testType.equalsIgnoreCase("Mobile")) {
			   if (testType.equalsIgnoreCase("Mobile") || (testType.equalsIgnoreCase("Web") && (testBedName.equalsIgnoreCase("Edge")))) {
				   log.info("TestType is  : " +testType +"************ testBedName **************" +testBedName );
				   for (int i = 0; i < testData.length; i++) {
					log.info("Iteration under test : " + i);
					if(!(testData[i][4].equalsIgnoreCase("NA"))){
						// verify if element was visited in previous iteration, if yes then it will skip navigation steps
						if(elementXpath==null || !elementXpath.equals(testData[i][6]))
						{
							//click on Main Menu
							commonMethods.clickElementbyXpath(webPage, testData[i][4], softAssert);

							commonMethods.Click_On_Element_JS(webPage, testData[i][5], softAssert);
							/*if(i>=50){
							webPage.scrollToElement(webPage.findObjectByxPath("(.//*[@id='li-primary-pronav-appliances']//a)[43]"));
						}*/
							//if element does not have child then use element as child

							elementXpath=testData[i][6];
							if(!testData[i][6].equals("NA"))
							{
								//if element has child

								commonMethods.clickElementbyXpath(webPage, testData[i][6], softAssert);
								//	elementXpath=testData[i][6];
								if(i>=0){
								}
							}
						}
						commonMethods.verifyLinkStatusCodeAndHrefValue(webPage, testData[i][2], testData[i][7], testData[i][2], softAssert);
					}
					else{
						if(i>0)
						{
							commonMethods.clickElementbyXpath(webPage, testData[i-1][4], softAssert);
							elementXpath=null;
						}
					}
				}
			} 
			softAssert.assertAll();
		}catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_LinksRedirection_Under_FinancingPromotions_Menu");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}	


	/**
	 * Test Case - 014 - verify Yes Banner links and descripyion(on web) Conns Home Page
	 * @throws InterruptedException 
	 * 
	 */
	@Test(priority = 14, enabled = true, description = "Verify HomePage Banner Links")
	public void Verify_HomePage_Banner_Links() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = null;
		webPage.getDriver().get(url);
		commonMethods.waitForPageLoad(webPage, softAssert);
		Thread.sleep(2000);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyYesMeBanner");
		//	if(testType.equalsIgnoreCase("Web")){
		//	 if (testType.equalsIgnoreCase("Web") && (!(browserName.equalsIgnoreCase("Edge") ))) {
			 if (testType.equalsIgnoreCase("Web") && (!(testBedName.equalsIgnoreCase("Edge") ))) {
				 log.info("Web TestType is  : " +testType +"************ testBedName **************" +testBedName );
				for(int i= 0;i<testData.length;i++)
				{   commonMethods.waitForPageLoad(webPage, softAssert);
				    Thread.sleep(2000);
					log.info("Actual : "+commonMethods.getTextbyXpath(webPage, testData[i][6], softAssert)+" Expected : "+testData[i][7]);
					softAssert.assertTrue((commonMethods.getTextbyXpath(webPage, testData[i][6], softAssert).equals(testData[i][7])));
					ActualURL = commonMethods.clickAndGetPageURL(webPage, testData[i][1], testData[i][0], softAssert);
					softAssert.assertTrue(ActualURL.contains(testData[i][4]),"Expected url: "+testData[i][4]+" Actual url: "+ActualURL);
					commonMethods.navigateToPage(webPage, url, softAssert);
					commonMethods.waitForPageLoad(webPage, softAssert);
					Thread.sleep(2000);
				}
				/*commonMethods.navigateToPage(webPage, url, softAssert);
				  commonMethods.waitForPageLoad(webPage, softAssert);
				  Thread.sleep(2000);*/
			}
		//	else if(testType.equalsIgnoreCase("Mobile"))
			 else if (testType.equalsIgnoreCase("Mobile") || (testType.equalsIgnoreCase("Web") && (testBedName.equalsIgnoreCase("Edge")))) {
				 log.info("*********** Mobile TestType is  : " + testType +"************  testBedName **************" +testBedName );
				 for(int i= 0;i<testData.length;i++){
					 //	ActualURL = commonMethods.clickAndGetPageURL(webPage, testData[i][3], testData[i][0], softAssert);
					 /************Added clickAndGetPageURLByJS inside CommonMethod ************/
					 //ActualURL = commonMethods.clickAndGetPageURLByJS(webPage, testData[i][3], testData[i][0], softAssert);
					 /************Modified testData[i][3] to testData[i][1] for execution ************/
					 ActualURL = commonMethods.clickAndGetPageURLByJS(webPage, testData[i][1], testData[i][0], softAssert);
					 //commonMethods.navigateToPage(webPage, url, softAssert);
					 //commonMethods.waitForPageLoad(webPage, softAssert);
					 softAssert.assertTrue(ActualURL.contains(testData[i][4]),"Expected url: "+testData[i][4]+" Actual url: "+ActualURL);
					 commonMethods.navigateToPage(webPage, url, softAssert);
					 commonMethods.waitForPageLoad(webPage, softAssert);
					 Thread.sleep(2000);
				 }
			}

			softAssert.assertAll();

		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_HomePage_Banner_Links");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	@Test(priority = 15, enabled = true, description = "Verify_Details_Under_Save_Big_With_Conns_Latest_Deals")
	public void Verify_Details_Under_Save_Big_With_Conns_Latest_Deals() throws PageException, InterruptedException {
		webPage.getDriver().get(url);
		SoftAssert softAssert = new SoftAssert();
		commonMethods.waitForPageLoad(webPage, softAssert);
		Thread.sleep(2000);		
		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifySaveBigWithConns");
		// ConnsHomePage.verifySaveBigWithConnsSection(testData);
		verifySaveBigWithConnsSection(testData);
	}

	/**
	 * Test Case - 017 - verify Top Category links Conns Home Page
	 * 
	 */
	@Test(priority = 16, enabled = true, description = "Verify_Top_Categories_LinksRedirection")
	public void Verify_Top_Categories_LinksRedirection() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = null;
		webPage.getDriver().get(url);
		commonMethods.waitForPageLoad(webPage, softAssert);
		Thread.sleep(2000);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyTopCategorySection");
			for (int i = 0; i < testData.length; i++) {
			//	if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][3].equalsIgnoreCase("NA")))) {
				if (testType.equalsIgnoreCase("Mobile") || (testType.equalsIgnoreCase("Web") && (testBedName.equalsIgnoreCase("Edge"))) && (!(testData[i][3].equalsIgnoreCase("NA")))) {	

					commonMethods.waitForPageLoad(webPage, softAssert);
					Thread.sleep(2000);
					log.info("Inside the first if. Value of I : " + i);
					commonMethods.clickElementbyXpath(webPage, testData[i][3], softAssert);
					Thread.sleep(2000);
				}
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][2].equalsIgnoreCase("NA")))) {
					log.info("Inside the 2nd if. Value of I : " + i);
					commonMethods.waitForPageLoad(webPage, softAssert);
					Thread.sleep(2000);
					ActualURL = commonMethods.clickElementbyXpathAndGetURL(webPage, testData[i][2], softAssert);
					softAssert.assertTrue(ActualURL.contains(testData[i][4]),
							"Link Name  :" + testData[i][0] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][4]);
				}
				if (testType.equalsIgnoreCase("Web")) {
					commonMethods.waitForPageLoad(webPage, softAssert);
					Thread.sleep(2000);
					ActualURL =
							ConnsHomePage.clickAndGetPageURLUsingJS(webPage,
									testData[i][1], testData[i][0], testData[i][5],
									softAssert);

					/*ActualURL = ConnsHomePage.clickAndGetPageURL_connsHome(webPage, testData[i][1], testData[i][0],
							testData[i][5], softAssert);*/
					softAssert.assertTrue(ActualURL.contains(testData[i][4]),
							"Link Name  :" + testData[i][0] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][4]);
					Thread.sleep(2000);
				}
				webPage.getDriver().get(url);
				commonMethods.waitForPageLoad(webPage, softAssert);
				Thread.sleep(2000);
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_Top_Categories_LinksRedirection");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 022 - verify Follow Us Section Conns Home Page
	 * 
	 */
	@Test(priority = 17, enabled = true, description = "Verify_LinkRedirection_Under_Follow_Us_Section")
	public void Verify_LinkRedirection_Under_Follow_Us_Section() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = null;
		webPage.getDriver().get(url);
		commonMethods.waitForPageLoad(webPage, softAssert);
		Thread.sleep(3000);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyFollowUsSection");
			for (int i = 0; i < testData.length; i++) {
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][3].equalsIgnoreCase("NA")))) {
					log.info("Inside the first if. Value of I : " + i);
					commonMethods.clickElementbyXpath(webPage, testData[i][3], softAssert);
					Thread.sleep(1000);
				}
			//	if (testType.equalsIgnoreCase("Mobile") ||testBedName.equalsIgnoreCase("edge") ) {
				if (testType.equalsIgnoreCase("Mobile") || (testType.equalsIgnoreCase("Web") && (testBedName.equalsIgnoreCase("Edge")))) {
					log.info("TestType is  : " +testType +"************ testBedName Name **************" +testBedName );
					if(!(testData[i][2].equalsIgnoreCase("NA"))){
						log.info("Inside the 2nd if. Value of I : " + i);
						webPage.scrollBottom();
						ActualURL = commonMethods.clickAndGetPageURL(webPage, testData[i][2],testData[i][0], softAssert);
						softAssert.assertTrue(ActualURL.contains(testData[i][4]),
								"Link Name  :" + testData[i][0] + " : failed " + "Actual URL is  :" + ActualURL + " "
										+ "Expected URL is  :" + testData[i][4]);}
				}
			//	if (testType.equalsIgnoreCase("Web") && !testBedName.equalsIgnoreCase("edge")) {
				if (testType.equalsIgnoreCase("Web") && (!(testBedName.equalsIgnoreCase("Edge") ))) {
					log.info("TestType is  : " +testType +"************ testBedName **************" +browserName );
					ActualURL =
							ConnsHomePage.clickAndGetPageURLUsingJS(webPage,
									testData[i][1], testData[i][0], testData[i][5],
									softAssert);

					/*	ActualURL = ConnsHomePage.clickAndGetPageURL_connsHome(webPage, testData[i][1], testData[i][0],
							testData[i][5], softAssert);*/
					softAssert.assertTrue(ActualURL.contains(testData[i][4]),
							"Link Name  :" + testData[i][0] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][4]);
				}
				webPage.getDriver().get(url);
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_LinkRedirection_Under_Follow_Us_Section");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 023 - verify BBBRating Banner Conns Home Page
	 * 
	 */
	@Test(priority = 18, enabled = true, description = "Verify_LearnMore_LinkRedirection_For_BBB_Rating_Banner")
	public void Verify_LearnMore_LinkRedirection_For_BBB_Rating_Banner() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = null;
		webPage.getDriver().get(url);
		commonMethods.waitForPageLoad(webPage, softAssert);
		Thread.sleep(3000);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyBBBRatingBanner");
			/*if(testType.equalsIgnoreCase("Mobile")){
			//if (testType.equalsIgnoreCase("Mobile") || (testType.equalsIgnoreCase("Web") && (testBedName.equalsIgnoreCase("Edge")))) {
				log.info("TestType is  : " +testType +"************ testBedName Name **************" +testBedName );

				webPage.scrollBottom();
			}*/
			//ActualURL = commonMethods.clickAndGetPageURL(webPage, testData[0][1], testData[0][0], softAssert);
			/****************************Using clickAndGetPageURLByJS method and commented above code *************************************************************************/
			ActualURL = commonMethods.clickAndGetPageURLByJS(webPage, testData[0][1], testData[0][0], softAssert);

			softAssert.assertTrue(ActualURL.contains(testData[0][4]),"Expected url: "+testData[0][4]+" Actual url: "+ActualURL);
			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_LearnMore_LinkRedirection_For_BBB_Rating_Banner");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 024 - verify links under About Conns footer Conns Home Page
	 * 
	 */
	@Test(priority = 19, enabled = true, description = "Verify_LinkRedirection_Under_Footer_About_Conns_Section")
	public void Verify_LinkRedirection_Under_Footer_About_Conns_Section() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = null;
		webPage.getDriver().get(url);
		commonMethods.waitForPageLoad(webPage, softAssert);
		Thread.sleep(3000);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page",
					"verifyFooterAboutConnsLinks");
			for (int i = 0; i < testData.length; i++) {
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][3].equalsIgnoreCase("NA")))) {
			//	if (testType.equalsIgnoreCase("Mobile") || (testType.equalsIgnoreCase("Web") && (testBedName.equalsIgnoreCase("Edge"))) && (!(testData[i][3].equalsIgnoreCase("NA")))) {	
					log.info("TestType is  : " +testType +"************ testBedName Name **************" +testBedName );
					log.info("Inside the first if. Value of I : " + i);
					webPage.scrollBottom();
					commonMethods.clickElementbyXpath(webPage, testData[i][3], softAssert);
					webPage.scrollBottom();
					Thread.sleep(1000);
				}
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][2].equalsIgnoreCase("NA")))) {
					log.info("Inside the 2nd if. Value of I : " + i);
					ActualURL = commonMethods.clickAndGetPageURL(webPage, testData[i][2], testData[i][0], softAssert);
					softAssert.assertTrue(ActualURL.contains(testData[i][4]),
							"Link Name  :" + testData[i][0] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][4]);
				}
				if (testType.equalsIgnoreCase("Web")) {
					/************Reverting back to initial if condition  **************/
			//	if (testType.equalsIgnoreCase("Web") && (!(testBedName.equalsIgnoreCase("Edge") ))) {
					log.info("TestType is  : " +testType +"************ testBedName **************" +testBedName );
					ActualURL = ConnsHomePage.clickAndGetPageURLUsingJS(webPage, testData[i][1], testData[i][0],
							testData[i][5], softAssert);
					softAssert.assertTrue(ActualURL.contains(testData[i][4]),
							"Link Name  :" + testData[i][0] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][4]);
				}
				webPage.getDriver().get(url);
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_LinkRedirection_Under_Footer_About_Conns_Section");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 025 - verify links under Customer Service footer Conns Home
	 * Page
	 * 
	 */
	@Test(priority = 20, enabled = true, description = "Verify_LinkRedirection_Under_Footer_Customer_Service_Section")
	public void Verify_LinkRedirection_Under_Footer_Customer_Service_Section()
			throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = null;
		webPage.getDriver().get(url);
		commonMethods.waitForPageLoad(webPage, softAssert);
		Thread.sleep(3000);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page",
					"verifyFooterCustomerServiceSectionLinks");
			commonMethods.waitForPageLoad(webPage, softAssert);
			for (int i = 0; i < testData.length; i++) {
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][3].equalsIgnoreCase("NA")))) {
			//	if (testType.equalsIgnoreCase("Mobile") || (testType.equalsIgnoreCase("Web") && (testBedName.equalsIgnoreCase("Edge"))) && (!(testData[i][3].equalsIgnoreCase("NA")))) {	
					log.info("TestType is  : " +testType +"************ testBedName Name **************" +testBedName );
					webPage.scrollBottom();
					log.info("Inside the first if. Value of I : " + i);
					commonMethods.clickElementbyXpath(webPage, testData[i][3], softAssert);
					webPage.scrollBottom();
					log.info("Outside the first if. Value of I : " + i);
					commonMethods.waitForPageLoad(webPage, softAssert);
					Thread.sleep(1000);
				}
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][2].equalsIgnoreCase("NA")))) {
					log.info("Inside the 2nd if. Value of I : " + i);
					ActualURL = commonMethods.clickAndGetPageURL(webPage, testData[i][2],testData[i][0] , softAssert);
					// log.info("ActualURL.contains(testData[i][4]) :
					// " + ActualURL.contains(testData[i][4]));
					softAssert.assertTrue(ActualURL.contains(testData[i][4]),
							"Link Name  :" + testData[i][0] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][4]);
					commonMethods.waitForPageLoad(webPage, softAssert);
					Thread.sleep(1000);
				}
				if (testType.equalsIgnoreCase("Web")) {
				//if (testType.equalsIgnoreCase("Web") && (!(testBedName.equalsIgnoreCase("Edge") ))) {
					log.info("TestType is  : " +testType +"************ testBedName **************" +testBedName );
					ActualURL = ConnsHomePage.clickAndGetPageURLUsingJS(webPage, testData[i][1], testData[i][0],
							testData[i][5], softAssert);
					commonMethods.waitForPageLoad(webPage, softAssert);
					Thread.sleep(20000);
					log.info("Expected URL  : " + testData[i][4]);
					softAssert.assertTrue(ActualURL.contains(testData[i][4]),
							"Link Name  :" + testData[i][0] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][4]);
					commonMethods.waitForPageLoad(webPage, softAssert);
					Thread.sleep(1000);
				}
				webPage.getDriver().get(url);
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			log.info("In CAtch");
			mainPage.getScreenShotForFailure(webPage, "Verify_LinkRedirection_Under_Footer_Customer_Service_Section");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}



	public void verifySaveBigWithConnsSection(String[][] test) throws PageException {
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
				webPage.getDriver().get(url);
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
				JavascriptExecutor executor = (JavascriptExecutor) webPage.getDriver();
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
					executor.executeScript("arguments[0].click();", element);
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
					executor.executeScript("arguments[0].click();", element);
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
}