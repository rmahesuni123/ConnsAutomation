package com.etouch.connsTests;

import java.awt.AWTException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
	private ConnsHomePage ConnsHomePage;

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
			System.out.println("Test Type is : " + testType);
			try {
				testEnv = System.getenv().get("Environment");
				System.out.println("testEnv is : " + testEnv);
				path = Paths.get(TestBedManager.INSTANCE.getProfile().getXlsDataConfig().get("testData"));
				DataFilePath = path.toAbsolutePath().toString().replace("Env", testEnv);
				System.out.println("DataFilePath After is : " + DataFilePath);
				commonData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator", "storeLocatorCommonElements");
				storeLocatorURL = commonData[0][0];
				platform = testBed.getPlatform().getName().toUpperCase();
				url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL();
				synchronized (this) {
					webPage = new WebPage(context);
					ConnsHomePage = new ConnsHomePage(url, webPage);
					mainPage = new ConnsMainPage(url, webPage);
				}
			} catch (Exception e) {
				log.info("errr is " + e);
				SoftAssertor.addVerificationFailure(e.getMessage());
			}
		} catch (Exception e) {
			CommonUtil.sop("Error is for" + testBedName + " -----------" + e);
			SoftAssertor.addVerificationFailure(e.getMessage());
		}
	}

	@AfterTest
	public void releaseResources() throws IOException, AWTException {
		webPage.getDriver().quit();
	}

	/**
	 * Test Case - 001 - Verify title and URL of page Conns Home Page
	 * 
	 */
	@Test(priority = 1, enabled = true, description = "Verify HomePage title")
	public void Verify_HomePage_Title() {
		SoftAssert softAssert = new SoftAssert();
		try {
			// webPage.getDriver().get("http://connsecommdev-1365538477.us-east-1.elb.amazonaws.com/conns_rwd/");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "Verifytitle");
			String ExpectedTitle = test[0][1];
			softAssert.assertEquals(webPage.getPageTitle(), ExpectedTitle,
					"Page Title verification failed. Expected title - " + ExpectedTitle + " Actual title - "
							+ webPage.getPageTitle());
			softAssert.assertAll();
		} catch (Throwable e) {
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
			System.out.println("Dimensions" + dimension);
			// Dimension[width=600,height=792]
			if (testType.equalsIgnoreCase("Web")) {
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
			if (testType.equalsIgnoreCase("Mobile")) {
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
						if (!ExpectedFontValuesTab[i][3].equalsIgnoreCase("NA")) {
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
						}
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
			// mainPage.getScreenShotForFailure(webPage,
			// "Verify_Font_And_Size");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 003 - Verify broken images on page Conns Home Page
	 * 
	 */
	@Test(priority = 3, enabled = true, description = "Verify_Broken_Images")
	public void Verify_Broken_Images() throws ClientProtocolException, IOException {
		ConnsHomePage.verifyBrokenImage1();
	}

	/**
	 * Test Case - 004 - verify Links Above Header Conns Home Page
	 * 
	 */
	@Test(priority = 4, enabled = true, description = "Verify_LinksRedirection_Of_Above_Header_Section")
	public void Verify_LinksRedirection_Of_Above_Header_Section() {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = null;
		// webPage.getDriver().get(url);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyLinksAboveHeader");
			for (int i = 0; i < testData.length; i++) {
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][3].equalsIgnoreCase("NA")))) {
					System.out.println("Inside the first if. Value of I : " + i);
					commonMethods.clickElementbyXpath(webPage, testData[i][3], softAssert);
					Thread.sleep(1000);
				}
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][2].equalsIgnoreCase("NA")))) {
					System.out.println("Inside the 2nd if. Value of I : " + i);
					ActualURL = commonMethods.clickElementbyXpathAndGetURL(webPage, testData[i][2], softAssert);
					/*
					 * ActualURL =
					 * ConnsHomePage.clickAndGetPageURLUsingJS(webPage,
					 * testData[i][2], testData[i][0], testData[i][5],
					 * softAssert);
					 */
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
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 005 - verify Your Cart functionality by adding product in
	 * cart Conns Home Page
	 * 
	 */
	@Test(priority = 5, enabled = true, description = "Verify_Your_Cart_Functionality")
	public void Verify_Your_Cart_Functionality() {
		SoftAssert softAssert = new SoftAssert();
		// webPage.getDriver().get(url);
		try {
			if (testType.equalsIgnoreCase("Web")) {
				String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyYourCart");
				List<String> actualValues = ConnsHomePage.verifyYourCart(testData, testType);
				softAssert.assertEquals(actualValues.get(0), actualValues.get(1));
				log.info("1st Asset pass");
				softAssert.assertTrue(actualValues.get(2).contains(testData[0][11]), " failed " + "Actual URL is  :"
						+ actualValues.get(2) + " " + "Expected URL is  :" + testData[0][11]);
				log.info("2nd Asset pass");
			}
			if (testType.equalsIgnoreCase("Mobile")) {
				String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page",
						"verifyYourCartOnMobile");
				List<String> actualValues = ConnsHomePage.verifyYourCartOnMobile(testData, testType);
				log.info("actualValues.get(0) : " + actualValues.get(0));
				log.info("actualValues.get(1) : " + actualValues.get(1));
				softAssert.assertEquals(actualValues.get(0), actualValues.get(1));
			}
			webPage.getDriver().get(url);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Your_Cart_Functionality");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 006 - verify links In Header Conns Home Page
	 * 
	 */
	@Test(priority = 6, enabled = true, description = "Verify_Links_In_Header_Section")
	public void Verify_Links_In_Header_Section() {
		SoftAssert softAssert = new SoftAssert();
		// webPage.getDriver().get(url);
		String ActualURL = "";
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyLinksInHeader");
			for (int i = 0; i < testData.length; i++) {
				if (testType.equalsIgnoreCase("Mobile")) {
					ActualURL = commonMethods.clickElementbyXpathAndGetURL(webPage, testData[i][3], softAssert);
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
			mainPage.getScreenShotForFailure(webPage, "Verify_Links_In_Header_Section");
			// mainPage.getScreenShotForFailure(webPage,
			// "Verify_LinksRedirection_Of_Above_Header_Section");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 007 - verify links under Furniture & Mattresses main menu
	 * Conns Home Page
	 * 
	 */
	@Test(priority = 7, enabled = true, description = "Verify_LinksRedirection_Under_Furniture_And_Mattresses_Menu")
	public void Verify_LinksRedirection_Under_Furniture_And_Mattresses_Menu()
			throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = null;
		// webPage.getDriver().get(url);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page",
					"verifyLinksForFurnitureAndMattresses");
			for (int i = 0; i < testData.length; i++) {
				log.info("Iteration under test : " + i);
				if (testType.equalsIgnoreCase("Web")) {
					commonMethods.hoverOnelementbyXpath(webPage, testData[i][0], softAssert);
					ActualURL = ConnsHomePage.clickAndGetPageURLUsingJS(webPage, testData[i][1], testData[i][3],
							testData[i][8], softAssert);
					log.info("ActualURL: " + ActualURL);
					log.info("Expected URL Should Contain:  " + testData[i][2]);
					softAssert.assertTrue(ActualURL.toLowerCase().contains(testData[i][2].toLowerCase()),
							"Link Name  :" + testData[i][3] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][2]);
					webPage.getDriver().get(url);
				}
				if (testType.equalsIgnoreCase("Mobile") && !((testData[i][4].equalsIgnoreCase("NA")))) {
					log.info("Valur of i : " + i);
					log.info(
							"Verify_LinksRedirection_Under_Furniture_And_Mattresses_Menu: Inside for loop Mobile condition"
									+ i);
					commonMethods.clickElementbyXpath(webPage, testData[i][4], softAssert);
					commonMethods.clickElementbyXpath(webPage, testData[i][5], softAssert);
					commonMethods.clickElementbyXpath(webPage, testData[i][6], softAssert);
					ActualURL = ConnsHomePage.clickAndGetPageURLUsingJS(webPage, testData[i][7], testData[i][3],
							testData[i][8], softAssert);
					softAssert.assertTrue(ActualURL.toLowerCase().contains(testData[i][2].toLowerCase()),
							"Link Name  :" + testData[i][3] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][2]);
					webPage.getDriver().get(url);
				}
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_LinksRedirection_Under_Furniture_And_Mattresses_Menu");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 008 - verify links under Appliances main menu Conns Home Page
	 * 
	 */
	@Test(priority = 8, enabled = true, description = "Verify_LinksRedirection_Under_Appliances_Menu")
	public void Verify_LinksRedirection_Under_Appliances_Menu() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = null;
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyLinksForAppliance");
			for (int i = 0; i < testData.length; i++) {
				log.info("Iteration under test : " + i);
				if (testType.equalsIgnoreCase("Web")) {
					commonMethods.hoverOnelementbyXpath(webPage, testData[i][0], softAssert);
					ActualURL = ConnsHomePage.clickAndGetPageURLUsingJS(webPage, testData[i][1], testData[i][3],
							testData[i][8], softAssert);
					softAssert.assertTrue(ActualURL.toLowerCase().contains(testData[i][2].toLowerCase()),
							"Link Name  :" + testData[i][3] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][2]);
					webPage.getDriver().get(url);
				}
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][4].equalsIgnoreCase("NA")))) {
					commonMethods.clickElementbyXpath(webPage, testData[i][4], softAssert);
					commonMethods.clickElementbyXpath(webPage, testData[i][5], softAssert);
					commonMethods.clickElementbyXpath(webPage, testData[i][6], softAssert);
					Thread.sleep(3000);
					ActualURL = commonMethods.clickElementbyXpathAndGetURL(webPage, testData[i][7], softAssert);
					softAssert.assertTrue(ActualURL.toLowerCase().contains(testData[i][2].toLowerCase()),
							"Link Name  :" + testData[i][3] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][2]);
				}
				webPage.getDriver().get(url);
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_LinksRedirection_Under_Appliances_Menu");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 009 - verify links under TV, Audio & Electronics main menu
	 * Conns Home Page
	 * 
	 */
	@Test(priority = 9, enabled = true, description = "Verify_LinksRedirection_Under_TV_Audio_And_Electronics_Menu")
	public void Verify_LinksRedirection_Under_TV_Audio_And_Electronics_Menu()
			throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = null;
		webPage.getDriver().get(url);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page",
					"verifyLinksForTvAudioElectronics");
			for (int i = 0; i < testData.length; i++) {
				log.info("Iteration under test : " + i);
				if (testType.equalsIgnoreCase("Web")) {
					commonMethods.hoverOnelementbyXpath(webPage, testData[i][0], softAssert);
					ActualURL = ConnsHomePage.clickAndGetPageURLUsingJS(webPage, testData[i][1], testData[i][3],
							testData[i][8], softAssert);
					softAssert.assertTrue(ActualURL.toLowerCase().contains(testData[i][2].toLowerCase()),
							"Link Name  :" + testData[i][3] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][2]);
					webPage.getDriver().get(url);
				}
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][4].equalsIgnoreCase("NA")))) {
					commonMethods.clickElementbyXpath(webPage, testData[i][4], softAssert);
					commonMethods.clickElementbyXpath(webPage, testData[i][5], softAssert);
					commonMethods.clickElementbyXpath(webPage, testData[i][6], softAssert);
					Thread.sleep(3000);
					ActualURL = commonMethods.clickElementbyXpathAndGetURL(webPage, testData[i][7], softAssert);
					// System.out.println("ActualURL.contains(testData[i][4]) :
					// " + ActualURL.contains(testData[i][4]));
					softAssert.assertTrue(ActualURL.toLowerCase().contains(testData[i][2].toLowerCase()),
							"Link Name  :" + testData[i][3] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][2]);
				}
				/*
				 * if (testType.equalsIgnoreCase("Mobile") &&
				 * !((testData[i][4].equalsIgnoreCase("NA")))) {
				 * System.out.println("Valur of i : " + i);
				 * commonMethods.clickElementbyXpath(webPage, testData[i][4],
				 * softAssert); commonMethods.clickElementbyXpath(webPage,
				 * testData[i][5], softAssert);
				 * commonMethods.clickElementbyXpath(webPage, testData[i][6],
				 * softAssert);
				 * 
				 * ActualURL = ConnsHomePage.clickAndGetPageURLUsingJS(webPage,
				 * testData[i][7], testData[i][3], testData[i][8], softAssert);
				 * softAssert.assertTrue(ActualURL.contains(testData[i][2]),
				 * "Link Name  :" + testData[i][3] + " : failed " +
				 * "Actual URL is  :" + ActualURL + " " + "Expected URL is  :" +
				 * testData[i][2]); webPage.getDriver().get(url); }
				 */
				webPage.getDriver().get(url);
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_LinksRedirection_Under_TV_Audio_And_Electronics_Menu");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 010 - verify links under Computer Accessories main menu Conns
	 * Home Page
	 * 
	 */
	@Test(priority = 10, enabled = true, description = "Verify_LinksRedirection_Under_ComputerAccessories_Menu")
	public void Verify_LinksRedirection_Under_ComputerAccessories_Menu() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = null;
		webPage.getDriver().get(url);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page",
					"verifyLinksForComputerAccessories");
			for (int i = 0; i < testData.length; i++) {
				log.info("Iteration under test : " + i);
				if (testType.equalsIgnoreCase("Web")) {
					commonMethods.hoverOnelementbyXpath(webPage, testData[i][0], softAssert);
					ActualURL = ConnsHomePage.clickAndGetPageURLUsingJS(webPage, testData[i][1], testData[i][3],
							testData[i][8], softAssert);
					softAssert.assertTrue(ActualURL.toLowerCase().contains(testData[i][2].toLowerCase()),
							"Link Name  :" + testData[i][3] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][2]);
					webPage.getDriver().get(url);
				}
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][4].equalsIgnoreCase("NA")))) {
					commonMethods.clickElementbyXpath(webPage, testData[i][4], softAssert);
					commonMethods.clickElementbyXpath(webPage, testData[i][5], softAssert);
					commonMethods.clickElementbyXpath(webPage, testData[i][6], softAssert);
					Thread.sleep(3000);
					ActualURL = commonMethods.clickElementbyXpathAndGetURL(webPage, testData[i][7], softAssert);
					softAssert.assertTrue(ActualURL.toLowerCase().contains(testData[i][2].toLowerCase()),
							"Link Name  :" + testData[i][3] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][2]);
				}
				webPage.getDriver().get(url);
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_LinksRedirection_Under_ComputerAccessories_Menu");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 011 - verify links under Financing and Promotions main menu
	 * Conns Home Page
	 * 
	 */
	@Test(priority = 11, enabled = true, description = "Verify_LinksRedirection_Under_FinancingPromotions_Menu")
	public void Verify_LinksRedirection_Under_FinancingPromotions_Menu() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = null;
		webPage.getDriver().get(url);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page",
					"verifyLinksForFinancingPromotions");
			for (int i = 0; i < testData.length; i++) {
				log.info("Iteration under test : " + i);
				if (testType.equalsIgnoreCase("Web")) {
					commonMethods.hoverOnelementbyXpath(webPage, testData[i][0], softAssert);
					ActualURL = ConnsHomePage.clickAndGetPageURLUsingJS(webPage, testData[i][1], testData[i][3],
							testData[i][8], softAssert);
					softAssert.assertTrue(ActualURL.toLowerCase().contains(testData[i][2].toLowerCase()),
							"Link Name  :" + testData[i][3] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][2]);
					webPage.getDriver().get(url);
				}
				if (testType.equalsIgnoreCase("Mobile") && !((testData[i][4].equalsIgnoreCase("NA")))) {
					commonMethods.clickElementbyXpath(webPage, testData[i][4], softAssert);
					commonMethods.clickElementbyXpath(webPage, testData[i][5], softAssert);
					commonMethods.clickElementbyXpath(webPage, testData[i][6], softAssert);
					ActualURL = ConnsHomePage.clickAndGetPageURLUsingJS(webPage, testData[i][7], testData[i][3],
							testData[i][8], softAssert);
					softAssert.assertTrue(ActualURL.toLowerCase().contains(testData[i][2].toLowerCase()),
							"Link Name  :" + testData[i][3] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][2]);
					webPage.getDriver().get(url);
				}
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_LinksRedirection_Under_FinancingPromotions_Menu");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 012 - verify Yes Banner Conns Home Page
	 * 
	 */
	@Test(priority = 12, enabled = true, description = "Verify_ApplyNow_LinkRedirection_In_YesMoney_Banner")
	public void Verify_ApplyNow_LinkRedirection_In_YesMoney_Banner() {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = null;
		webPage.getDriver().get(url);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyYesMeBanner");
			for (int i = 0; i < testData.length; i++) {
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][3].equalsIgnoreCase("NA")))) {
					System.out.println("Inside the first if. Value of I : " + i);
					commonMethods.clickElementbyXpath(webPage, testData[i][3], softAssert);
					Thread.sleep(1000);
				}
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][2].equalsIgnoreCase("NA")))) {
					System.out.println("Inside the 2nd if. Value of I : " + i);
					ActualURL = commonMethods.clickElementbyXpathAndGetURL(webPage, testData[i][2], softAssert);
					softAssert.assertTrue(ActualURL.contains(testData[i][4]),
							"Link Name  :" + testData[i][0] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][4]);
				}
				if (testType.equalsIgnoreCase("Web")) {
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
			mainPage.getScreenShotForFailure(webPage, "Verify_ApplyNow_LinkRedirection_In_YesMoney_Banner");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 013 - verify NextDay Banner Conns Home Page
	 * 
	 */
	@Test(priority = 13, enabled = true, description = "Verify_MoreInfo_LinkRedirection_In_NextDayDeleivery_Banner")
	public void Verify_MoreInfo_LinkRedirection_In_NextDayDeleivery_Banner() {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = null;
		// webPage.getDriver().get(url);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page",
					"verifyNextDayDeliveryBanner");
			for (int i = 0; i < testData.length; i++) {
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][3].equalsIgnoreCase("NA")))) {
					System.out.println("Inside the first if. Value of I : " + i);
					commonMethods.clickElementbyXpath(webPage, testData[i][3], softAssert);
					Thread.sleep(1000);
				}
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][2].equalsIgnoreCase("NA")))) {
					System.out.println("Inside the 2nd if. Value of I : " + i);
					ActualURL = commonMethods.clickElementbyXpathAndGetURL(webPage, testData[i][2], softAssert);
					softAssert.assertTrue(ActualURL.contains(testData[i][4]),
							"Link Name  :" + testData[i][0] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][4]);
				}
				if (testType.equalsIgnoreCase("Web")) {
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
			mainPage.getScreenShotForFailure(webPage, "Verify_MoreInfo_LinkRedirection_In_NextDayDeleivery_Banner");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 014 - verify Save Big With Conns section Conns Home Page
	 * 
	 */
	@Test(priority = 14, enabled = true, description = "Verify_Details_Under_Save_Big_With_Conns_Latest_Deals")
	public void Verify_Details_Under_Save_Big_With_Conns_Latest_Deals() throws PageException, InterruptedException {
		webPage.getDriver().get(url);
		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifySaveBigWithConns");
		// ConnsHomePage.verifySaveBigWithConnsSection(testData);
		verifySaveBigWithConnsSection(testData);
	}

	/**
	 * Test Case - 015 - verify Top Category links Conns Home Page
	 * 
	 */
	@Test(priority = 15, enabled = true, description = "Verify_Top_Categories_LinksRedirection")
	public void Verify_Top_Categories_LinksRedirection() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = null;
		// webPage.getDriver().get(url);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyTopCategorySection");
			for (int i = 0; i < testData.length; i++) {
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][3].equalsIgnoreCase("NA")))) {
					System.out.println("Inside the first if. Value of I : " + i);
					commonMethods.clickElementbyXpath(webPage, testData[i][3], softAssert);
					Thread.sleep(1000);
				}
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][2].equalsIgnoreCase("NA")))) {
					System.out.println("Inside the 2nd if. Value of I : " + i);
					ActualURL = commonMethods.clickElementbyXpathAndGetURL(webPage, testData[i][2], softAssert);
					softAssert.assertTrue(ActualURL.contains(testData[i][4]),
							"Link Name  :" + testData[i][0] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][4]);
				}
				if (testType.equalsIgnoreCase("Web")) {
					/*
					 * ActualURL =
					 * ConnsHomePage.clickAndGetPageURLUsingJS(webPage,
					 * testData[i][1], testData[i][0], testData[i][5],
					 * softAssert);
					 */
					ActualURL = ConnsHomePage.clickAndGetPageURL_connsHome(webPage, testData[i][1], testData[i][0],
							testData[i][5], softAssert);
					softAssert.assertTrue(ActualURL.contains(testData[i][4]),
							"Link Name  :" + testData[i][0] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][4]);
				}
				webPage.getDriver().get(url);
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Top_Categories_LinksRedirection");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 016 - verify Build Your Own Financial Future Banner Conns
	 * Home Page
	 * 
	 */
	@Test(priority = 16, enabled = true, description = "Verify_Top_Categories_LinksRedirection")
	public void Verify_LearnMore_LinkRedirection_Under_Build_Your_Own_Financial_Future_Banner()
			throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = null;
		// webPage.getDriver().get(url);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page",
					"verifyBuildYourOwnFinancialFutureBanner");
			for (int i = 0; i < testData.length; i++) {
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][3].equalsIgnoreCase("NA")))) {
					System.out.println("Inside the first if. Value of I : " + i);
					commonMethods.clickElementbyXpath(webPage, testData[i][3], softAssert);
					Thread.sleep(1000);
				}
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][2].equalsIgnoreCase("NA")))) {
					System.out.println("Inside the 2nd if. Value of I : " + i);
					ActualURL = commonMethods.clickElementbyXpathAndGetURL(webPage, testData[i][2], softAssert);
					softAssert.assertTrue(ActualURL.contains(testData[i][4]),
							"Link Name  :" + testData[i][0] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][4]);
				}
				if (testType.equalsIgnoreCase("Web")) {
					/*
					 * if (testType.equalsIgnoreCase("Mobile")) { String
					 * ActualURL =
					 * ConnsHomePage.clickAndGetPageURLUsingJS(webPage,
					 * testData[i][3], testData[i][0], testData[i][5],
					 * softAssert);
					 * softAssert.assertTrue(ActualURL.contains(testData[i][4]),
					 * "Link Name  :" + testData[i][0] + " : failed " +
					 * "Actual URL is  :" + ActualURL + " " +
					 * "Expected URL is  :" + testData[i][4]); }
					 */
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
			mainPage.getScreenShotForFailure(webPage,
					"Verify_LearnMore_LinkRedirection_Under_Build_Your_Own_Financial_Future_Banner");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 017 - verify Help Children Banner Conns Home Page
	 * 
	 */
	@Test(priority = 17, enabled = true, description = "Verify_LearnMore_LinkRedirection_Under_Helping_Children_Thrive_Banner")
	public void Verify_LearnMore_LinkRedirection_Under_Helping_Children_Thrive_Banner()
			throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = null;
		// webPage.getDriver().get(url);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyHelpChildrenBanner");
			for (int i = 0; i < testData.length; i++) {
				if (testType.equalsIgnoreCase("Mobile")) {
					if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][3].equalsIgnoreCase("NA")))) {
						System.out.println("Inside the first if. Value of I : " + i);
						commonMethods.clickElementbyXpath(webPage, testData[i][3], softAssert);
						Thread.sleep(1000);
					}
					if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][2].equalsIgnoreCase("NA")))) {
						System.out.println("Inside the 2nd if. Value of I : " + i);
						ActualURL = commonMethods.clickElementbyXpathAndGetURL(webPage, testData[i][2], softAssert);
						softAssert.assertTrue(ActualURL.contains(testData[i][4]),
								"Link Name  :" + testData[i][0] + " : failed " + "Actual URL is  :" + ActualURL + " "
										+ "Expected URL is  :" + testData[i][4]);
					}
					if (testType.equalsIgnoreCase("Web")) {
						/*
						 * String ActualURL =
						 * ConnsHomePage.clickAndGetPageURLUsingJS(webPage,
						 * testData[i][3], testData[i][0], testData[i][5],
						 * softAssert);
						 * softAssert.assertTrue(ActualURL.contains(testData[i][
						 * 4]), "Link Name  :" + testData[i][0] + " : failed " +
						 * "Actual URL is  :" + ActualURL + " " +
						 * "Expected URL is  :" + testData[i][4]);
						 */
						ActualURL = ConnsHomePage.clickAndGetPageURLUsingJS(webPage, testData[i][1], testData[i][0],
								testData[i][5], softAssert);
						softAssert.assertTrue(ActualURL.contains(testData[i][4]),
								"Link Name  :" + testData[i][0] + " : failed " + "Actual URL is  :" + ActualURL + " "
										+ "Expected URL is  :" + testData[i][4]);
					}
				}
				webPage.getDriver().get(url);
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage,
					"Verify_LearnMore_LinkRedirection_Under_Helping_Children_Thrive_Banner");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 018 - verify Six Reasons Banner Conns Home Page
	 * 
	 */
	@Test(priority = 18, enabled = true, description = "Verify_LearnMore_LinkRedirection_Under_SixReasons_ToShop_Banner")
	public void Verify_LearnMore_LinkRedirection_Under_SixReasons_ToShop_Banner()
			throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = null;
		// webPage.getDriver().get(url);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifySixReasonsBanner");
			for (int i = 0; i < testData.length; i++) {
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][3].equalsIgnoreCase("NA")))) {
					System.out.println("Inside the first if. Value of I : " + i);
					commonMethods.clickElementbyXpath(webPage, testData[i][3], softAssert);
					Thread.sleep(1000);
				}
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][2].equalsIgnoreCase("NA")))) {
					System.out.println("Inside the 2nd if. Value of I : " + i);
					ActualURL = commonMethods.clickElementbyXpathAndGetURL(webPage, testData[i][2], softAssert);
					softAssert.assertTrue(ActualURL.contains(testData[i][4]),
							"Link Name  :" + testData[i][0] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][4]);
				}
				if (testType.equalsIgnoreCase("Web")) {
					/*
					 * if (testType.equalsIgnoreCase("Mobile")) { String
					 * ActualURL =
					 * ConnsHomePage.clickAndGetPageURLUsingJS(webPage,
					 * testData[i][3], testData[i][0], testData[i][5],
					 * softAssert);
					 * softAssert.assertTrue(ActualURL.contains(testData[i][4]),
					 * "Link Name  :" + testData[i][0] + " : failed " +
					 * "Actual URL is  :" + ActualURL + " " +
					 * "Expected URL is  :" + testData[i][4]); } else {
					 */
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
			mainPage.getScreenShotForFailure(webPage,
					"Verify_LearnMore_LinkRedirection_Under_SixReasons_ToShop_Banner");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 019 - verify Promotions Banner Conns Home Page
	 * 
	 */
	@Test(priority = 19, enabled = true, description = "Verify_LearnMore_LinkRedirection_Under_Promotions_Banner")
	public void Verify_LearnMore_LinkRedirection_Under_Promotions_Banner() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = null;
		// webPage.getDriver().get(url);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyPromotionsBanner");
			for (int i = 0; i < testData.length; i++) {
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][3].equalsIgnoreCase("NA")))) {
					System.out.println("Inside the first if. Value of I : " + i);
					commonMethods.clickElementbyXpath(webPage, testData[i][3], softAssert);
					Thread.sleep(1000);
				}
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][2].equalsIgnoreCase("NA")))) {
					System.out.println("Inside the 2nd if. Value of I : " + i);
					ActualURL = commonMethods.clickElementbyXpathAndGetURL(webPage, testData[i][2], softAssert);
					softAssert.assertTrue(ActualURL.contains(testData[i][4]),
							"Link Name  :" + testData[i][0] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][4]);
				}
				if (testType.equalsIgnoreCase("Web")) {
					/*
					 * if (testType.equalsIgnoreCase("Mobile")) { String
					 * ActualURL =
					 * ConnsHomePage.clickAndGetPageURLUsingJS(webPage,
					 * testData[i][3], testData[i][0], testData[i][5],
					 * softAssert);
					 * softAssert.assertTrue(ActualURL.contains(testData[i][4]),
					 * "Link Name  :" + testData[i][0] + " : failed " +
					 * "Actual URL is  :" + ActualURL + " " +
					 * "Expected URL is  :" + testData[i][4]); } else {
					 */
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
			mainPage.getScreenShotForFailure(webPage, "Verify_LearnMore_LinkRedirection_Under_Promotions_Banner");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 020 - verify Follow Us Section Conns Home Page
	 * 
	 */
	@Test(priority = 20, enabled = true, description = "Verify_LinkRedirection_Under_Follow_Us_Section")
	public void Verify_LinkRedirection_Under_Follow_Us_Section() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = null;
		// webPage.getDriver().get(url);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyFollowUsSection");
			for (int i = 0; i < testData.length; i++) {
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][3].equalsIgnoreCase("NA")))) {
					System.out.println("Inside the first if. Value of I : " + i);
					commonMethods.clickElementbyXpath(webPage, testData[i][3], softAssert);
					Thread.sleep(1000);
				}
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][2].equalsIgnoreCase("NA")))) {
					System.out.println("Inside the 2nd if. Value of I : " + i);
					ActualURL = commonMethods.clickElementbyXpathAndGetURL(webPage, testData[i][2], softAssert);
					softAssert.assertTrue(ActualURL.contains(testData[i][4]),
							"Link Name  :" + testData[i][0] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][4]);
				}
				if (testType.equalsIgnoreCase("Web")) {
					/*
					 * ActualURL =
					 * ConnsHomePage.clickAndGetPageURLUsingJS(webPage,
					 * testData[i][1], testData[i][0], testData[i][5],
					 * softAssert);
					 */
					ActualURL = ConnsHomePage.clickAndGetPageURL_connsHome(webPage, testData[i][1], testData[i][0],
							testData[i][5], softAssert);
					softAssert.assertTrue(ActualURL.contains(testData[i][4]),
							"Link Name  :" + testData[i][0] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][4]);
				}
				webPage.getDriver().get(url);
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_LinkRedirection_Under_Follow_Us_Section");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 021 - verify BBBRating Banner Conns Home Page
	 * 
	 */
	@Test(priority = 21, enabled = true, description = "Verify_LearnMore_LinkRedirection_For_BBB_Rating_Banner")
	public void Verify_LearnMore_LinkRedirection_For_BBB_Rating_Banner() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = null;
		// webPage.getDriver().get(url);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyBBBRatingBanner");
			for (int i = 0; i < testData.length; i++) {
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][3].equalsIgnoreCase("NA")))) {
					System.out.println("Inside the first if. Value of I : " + i);
					commonMethods.clickElementbyXpath(webPage, testData[i][3], softAssert);
					Thread.sleep(1000);
				}
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][2].equalsIgnoreCase("NA")))) {
					System.out.println("Inside the 2nd if. Value of I : " + i);
					ActualURL = commonMethods.clickElementbyXpathAndGetURL(webPage, testData[i][2], softAssert);
					softAssert.assertTrue(ActualURL.contains(testData[i][4]),
							"Link Name  :" + testData[i][0] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][4]);
				}
				if (testType.equalsIgnoreCase("Web")) {
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
			mainPage.getScreenShotForFailure(webPage, "Verify_LearnMore_LinkRedirection_For_BBB_Rating_Banner");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 022 - verify links under About Conns footer Conns Home Page
	 * 
	 */
	@Test(priority = 22, enabled = true, description = "Verify_LinkRedirection_Under_Footer_About_Conns_Section")
	public void Verify_LinkRedirection_Under_Footer_About_Conns_Section() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = null;
		// webPage.getDriver().get(url);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page",
					"verifyFooterAboutConnsLinks");
			for (int i = 0; i < testData.length; i++) {
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][3].equalsIgnoreCase("NA")))) {
					System.out.println("Inside the first if. Value of I : " + i);
					commonMethods.clickElementbyXpath(webPage, testData[i][3], softAssert);
					Thread.sleep(1000);
				}
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][2].equalsIgnoreCase("NA")))) {
					System.out.println("Inside the 2nd if. Value of I : " + i);
					ActualURL = commonMethods.clickElementbyXpathAndGetURL(webPage, testData[i][2], softAssert);
					softAssert.assertTrue(ActualURL.contains(testData[i][4]),
							"Link Name  :" + testData[i][0] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][4]);
				}
				if (testType.equalsIgnoreCase("Web")) {
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
			mainPage.getScreenShotForFailure(webPage, "Verify_LinkRedirection_Under_Footer_About_Conns_Section");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 023 - verify links under Customer Service footer Conns Home
	 * Page
	 * 
	 */
	@Test(priority = 23, enabled = true, description = "Verify_LinkRedirection_Under_Footer_Customer_Service_Section")
	public void Verify_LinkRedirection_Under_Footer_Customer_Service_Section()
			throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = null;
		// webPage.getDriver().get(url);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page",
					"verifyFooterCustomerServiceSectionLinks");
			for (int i = 0; i < testData.length; i++) {
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][3].equalsIgnoreCase("NA")))) {
					System.out.println("Inside the first if. Value of I : " + i);
					commonMethods.clickElementbyXpath(webPage, testData[i][3], softAssert);
					Thread.sleep(1000);
				}
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][2].equalsIgnoreCase("NA")))) {
					System.out.println("Inside the 2nd if. Value of I : " + i);
					ActualURL = commonMethods.clickElementbyXpathAndGetURL(webPage, testData[i][2], softAssert);
					// System.out.println("ActualURL.contains(testData[i][4]) :
					// " + ActualURL.contains(testData[i][4]));
					softAssert.assertTrue(ActualURL.contains(testData[i][4]),
							"Link Name  :" + testData[i][0] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][4]);
				}
				if (testType.equalsIgnoreCase("Web")) {
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
			mainPage.getScreenShotForFailure(webPage, "Verify_LinkRedirection_Under_Footer_Customer_Service_Section");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 024 - verify Footer We Accpet Conns Home Page
	 * 
	 */
	@Test(priority = 24, enabled = true, description = "Verify_Element_Visibility_Under_We_Accept_Section")
	public void Verify_Element_Visibility_Under_We_Accept_Section() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// webPage.getDriver().get(url);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyFooterWeAccpet");
			boolean statusOfWeAccept = commonMethods.verifyElementisPresent(webPage, testData[0][0], softAssert);
			softAssert.assertTrue(statusOfWeAccept, "We accept images are not present on the page");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Element_Visibility_Under_We_Accept_Section");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 025 - verify Footer Copyright Conns Home Page
	 * 
	 */
	@Test(priority = 25, enabled = true, description = "Verify_Content_Under_Footer_Copyright_Section")
	public void Verify_Content_Under_Footer_Copyright_Section() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		// webPage.getDriver().get(url);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyFooterCopyright");
			String homeplusText1 = commonMethods.getTextbyXpath(webPage, testData[0][0], softAssert);
			softAssert.assertEquals(homeplusText1, testData[0][1]);
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Content_Under_Footer_Copyright_Section");
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
				System.out.println(" " + SaveBigMenuOptionIdentifier + " " + CarouselLeft + " " + CarouselRight + " "
						+ ElementPosition1 + " " + ElementPosition2 + " " + ClickForDetails + " " + PopUp);
				String textAtPosition1 = null;
				String textAtPosition2 = null;
				String textAtPosition11 = null;
				String textAtPosition12 = null;
				if (testType.equalsIgnoreCase("Mobile")) {
					Thread.sleep(3000);
					WebElement element = webPage.findObjectByxPath(SaveBigMenuOptionIdentifierMobile).getWebElement();
					System.out.println("$$$$ Parent : " + element.getText());
					executor.executeScript("arguments[0].click();", element);
					Thread.sleep(3000);
					webPage.findObjectByxPath(CarouselLeft).click();
					Thread.sleep(3000);
					log.info("Clicked on element1");
					textAtPosition1 = webPage.findObjectByxPath(ElementPosition1).getText();
					System.out.println("Expected Left: " + textAtPosition1);
					for (int j = 0; j < RotationCountWeb; j++) {
						webPage.findObjectByxPath(CarouselLeft).click();
						Thread.sleep(1000);
					}
					textAtPosition2 = webPage.findObjectByxPath(ElementPosition1).getText();
					System.out.println("Actual Left : " + textAtPosition2);
					SoftAssertor.assertEquals(textAtPosition1, textAtPosition2,
							" failed " + textAtPosition1 + " " + textAtPosition2);
					webPage.findObjectByxPath(CarouselRight).click();
					Thread.sleep(3000);
					log.info("Clicked on element2");
					textAtPosition11 = webPage.findObjectByxPath(ElementPosition2).getText();
					System.out.println("Expected Right: " + textAtPosition11);
					for (int k = 0; k < RotationCountWeb; k++) {
						webPage.findObjectByxPath(CarouselRight).click();
					}
					textAtPosition12 = webPage.findObjectByxPath(ElementPosition2).getText();
					System.out.println("Actual Right: " + textAtPosition12);
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
					System.out.println("$$$$ Parent : " + element.getText());
					executor.executeScript("arguments[0].click();", element);
					Thread.sleep(3000);
					webPage.findObjectByxPath(CarouselLeft).click();
					Thread.sleep(3000);
					log.info("Clicked on element1");
					textAtPosition1 = webPage.findObjectByxPath(ElementPosition1).getText();
					System.out.println("Expected Left: " + textAtPosition1);
					for (int j = 0; j < RotationCountWeb; j++) {
						webPage.findObjectByxPath(CarouselLeft).click();
						Thread.sleep(1000);
					}
					textAtPosition2 = webPage.findObjectByxPath(ElementPosition1).getText();
					System.out.println("Actual Left : " + textAtPosition2);
					SoftAssertor.assertEquals(textAtPosition1, textAtPosition2,
							" failed " + textAtPosition1 + " " + textAtPosition2);
					webPage.findObjectByxPath(CarouselRight).click();
					Thread.sleep(3000);
					log.info("Clicked on element2");
					textAtPosition11 = webPage.findObjectByxPath(ElementPosition2).getText();
					System.out.println("Expected Right: " + textAtPosition11);
					for (int k = 0; k < RotationCountWeb; k++) {
						webPage.findObjectByxPath(CarouselRight).click();
					}
					textAtPosition12 = webPage.findObjectByxPath(ElementPosition2).getText();
					System.out.println("Actual Right: " + textAtPosition12);
					SoftAssertor.assertEquals(textAtPosition11, textAtPosition12,
							" failed " + textAtPosition11 + " " + textAtPosition12);
				}
				softAssert.assertAll();
			} catch (Throwable e) {
				errors.add(e.getLocalizedMessage());
				log.error(e.getMessage());
			}
		}
	}
}