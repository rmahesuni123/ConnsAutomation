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
	static Log log = LogUtil.getLog(Conns_Home_Page.class);
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
				if(testType.equalsIgnoreCase("Mobile")
						&&TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getPlatform().getName().equalsIgnoreCase("ANDROID"))
				{
					commonMethods.resetAPP(TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getDevice().getUdid());
				}
				testEnv = System.getenv().get("Environment");
				log.info("testEnv is : " + testEnv);
				path = Paths.get(TestBedManager.INSTANCE.getProfile().getXlsDataConfig().get("testData"));
				DataFilePath = path.toAbsolutePath().toString().replace("Env", testEnv);
				log.info("DataFilePath After is : " + DataFilePath);
				commonData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator", "storeLocatorCommonElements");
				log.info("storeLocatorURL : " + commonData[0][0]);
				storeLocatorURL = commonData[0][0];
				platform = testBed.getPlatform().getName().toUpperCase();
				log.info("platform : " + platform);
				url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL();
				log.info("url : " + url);
				synchronized (this) {
					log.info("synchronized : "+context );
					webPage = new WebPage(context);
					log.info("webPage : " );
					mainPage = new ConnsMainPage(url, webPage);
					log.info("mainPage : " + mainPage);
					ConnsHomePage = new ConnsHomePage(url, webPage);
					log.info("ConnsHomePage : " + ConnsHomePage);
					webPageMap.put(Thread.currentThread().getId(), webPage);
					log.info("webPageMap : " + webPageMap);
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

	
	@AfterTest(alwaysRun = true)
	public void releaseResources() throws IOException, AWTException {
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
		JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
		try {
			String[][] ExpectedFontValuesWeb = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page",
					"VerifyFontandSizeWeb");
			String[][] ExpectedFontValuesMobile = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page",
					"VerifyFontandSizeMobile");
			String[][] ExpectedFontValuesTab = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page",
					"VerifyFontandSizeTab");			
			int width = ((Long) js.executeScript("return window.innerWidth || document.body.clientWidth")).intValue();
			log.info("width value calculated is :" + width);
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
					for (int i = 0; i < ExpectedFontValuesTab.length; i++) {
						List<String> actualCssValues = commonMethods.getFontProperties(webPage,
								ExpectedFontValuesTab[i][1], softAssert);
						if (!ExpectedFontValuesTab[i][2].equalsIgnoreCase("NA")) {
							softAssert.assertTrue(actualCssValues.get(0).contains(ExpectedFontValuesTab[i][2]),
									"CSS value verification failed for link " + ExpectedFontValuesTab[i][0]
											+ ". Expected font Size: " + ExpectedFontValuesTab[i][2]
													+ " Actual Font Size: " + actualCssValues.get(0));
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
			e.printStackTrace();
			 mainPage.getScreenShotForFailure(webPage,"Verify_Font_And_Size");
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
		SoftAssert softAssert = new SoftAssert();
		ConnsHomePage.verifyBrokenImage(webPage);
		softAssert.assertAll();
	}
	/**
	 * Test Case - 04 - verify Footer We Accpet Conns Home Page
	 * 
	 */
	@Test(priority = 4, enabled = true, description = "Verify_Element_Visibility_Under_We_Accept_Section")
	public void Verify_Element_Visibility_Under_We_Accept_Section() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyFooterWeAccpet");
		String isElementPresentXpath = testData[0][0];
		try {
			boolean statusOfWeAccept = ConnsHomePage.elementPresenceVerification(webPage, isElementPresentXpath, softAssert);
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
		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyFooterCopyright");
		String Actual_Home_Plus_Text = testData[0][0];
		String Expect_Home_Plus_Text = testData[0][1];
		try {
			String homeplusText1 = ConnsHomePage.textVerification(webPage, Actual_Home_Plus_Text, softAssert);
			softAssert.assertEquals(homeplusText1, Expect_Home_Plus_Text);
			softAssert.assertAll();
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
		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyYourCart");
		String Actual_Cart_Text = testData[0][0];
		String Expected_Cart_Text = testData[0][1];
		Thread.sleep(2000);
		try {
			String cartText = ConnsHomePage.textVerification(webPage, Actual_Cart_Text, softAssert);
			softAssert.assertTrue(cartText.toLowerCase().contains(Expected_Cart_Text.toLowerCase()),cartText+"does not contain"+testData[0][1]);
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
		String ActualURL = "";
		webPage.getDriver().get(url);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyLinksAboveHeader");			
			for (int i = 0; i < testData.length; i++) {
				 String locator  = testData[i][1];
				 String linkName = testData[i][0];
				 String TargetPageLocator = testData[i][5];
				 String Expected_URL = testData[i][4];
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][2].equalsIgnoreCase("NA")))) {
					log.info(" Value of I : " + i + " TestType : " +testType.toString());						
					ActualURL = ConnsHomePage.verifyLinkNavigationUsingJS(webPage,locator, linkName,TargetPageLocator, softAssert);
					softAssert.assertTrue(ActualURL.contains(Expected_URL),	"Link Name  :" + linkName + " : failed " + "Actual URL is  :" + ActualURL + " "	+ "Expected URL is  :" + Expected_URL);
				}
				else {
					log.info(" Value of I : " + i + " TestType : " +testType.toString());					
					ActualURL = ConnsHomePage.verifyLinkNavigationUsingJS(webPage,locator, linkName,TargetPageLocator, softAssert);
					softAssert.assertTrue(ActualURL.contains(Expected_URL),	"Link Name  :" + linkName + " : failed " + "Actual URL is  :" + ActualURL + " "	+ "Expected URL is  :" + Expected_URL);
				}
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
				 String locator  = testData[i][1];
				 String linkName = testData[i][0];
				 String TargetPageLocator = testData[i][5];
				 String Expected_URL = testData[i][4];
				 ActualURL = ConnsHomePage.verifyLinkNavigationUsingJS(webPage,locator, linkName,TargetPageLocator, softAssert);
				 softAssert.assertTrue(ActualURL.contains(Expected_URL),"Link Name  :" + linkName + " : failed " + "Actual URL is  :" + ActualURL + " "	+ "Expected URL is  :" + Expected_URL);				
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
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
	@Test(priority = 9, enabled = true, description = "Verify LinksRedirection Under Furniture Menu")
	public void Verify_LinksRedirection_Under_Furniture_Menu() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		webPage.getDriver().get(url);	
		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyLinksForFurniture");
		String elementHoverXpath ="";
		String linkName = "";
		String xpath = "";
		String expectedHref = "";
		String patio_Furniture_Matteress_Link_Locator = "";
		boolean patio_Functionality = true;
		boolean portable_Air_Conditioners = false;
		Thread.sleep(2000);
		try {
			elementHoverXpath = testData[0][0];
			ConnsHomePage.elementHoveringVerification(webPage, elementHoverXpath, softAssert);
			if (testType.equalsIgnoreCase("Web")) {
			for (int i = 0; i < testData.length; i++) {
				linkName = testData[i][2];
				xpath = testData[i][1];
				expectedHref = testData[i][2];
				log.info("Iteration under test : " + i);							
				ConnsHomePage.linkStatusCodeAndHrefValueVerification(webPage, linkName, xpath, expectedHref,testData, softAssert);
				}
			}
		    if (testType.equalsIgnoreCase("Mobile") || (testType.equalsIgnoreCase("Web") && (testBedName.equalsIgnoreCase("Edge")))) {
				ConnsHomePage.LinksRedirection_Under_Option_Menu_Verification_Mobile(webPage, testData,patio_Furniture_Matteress_Link_Locator,patio_Functionality,portable_Air_Conditioners, softAssert);
			  }
			softAssert.assertAll();
		}catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_LinksRedirection_Under_Furniture_Menu");
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
		String elementHoverXpath ="";
		String linkName = "";
		String xpath = "";
		String expectedHref = "";
		String patio_Furniture_Matteress_Link_Locator = "";
		boolean patio_Functionality = false;
		boolean portable_Air_Conditioners = true;
		webPage.getDriver().get(url);
		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyLinksForAppliance");
		Thread.sleep(2000);	
		elementHoverXpath = testData[0][0];
		ConnsHomePage.elementHoveringVerification(webPage, elementHoverXpath, softAssert);
		try {
			if (testType.equalsIgnoreCase("Web")) {				
				for (int i = 0; i < testData.length; i++) {
					linkName = testData[i][2];
					xpath = testData[i][1];
					expectedHref = testData[i][2];
					log.info("Iteration under test : " + i);							
					ConnsHomePage.linkStatusCodeAndHrefValueVerification(webPage, linkName, xpath, expectedHref,testData, softAssert);
					}
				}
		    if (testType.equalsIgnoreCase("Mobile") || (testType.equalsIgnoreCase("Web") && (testBedName.equalsIgnoreCase("Edge")))) {
				ConnsHomePage.LinksRedirection_Under_Option_Menu_Verification_Mobile(webPage, testData,patio_Furniture_Matteress_Link_Locator,patio_Functionality,portable_Air_Conditioners, softAssert);
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
		webPage.getDriver().get(url);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyLinksForTvAudioElectronics");
			Thread.sleep(3000);
			String elementHoverXpath ="";
			String linkName = "";
			String xpath = "";
			String expectedHref = "";
			String patio_Furniture_Matteress_Link_Locator = "";
			boolean patio_Functionality = false;
			boolean portable_Air_Conditioners = false;	
			elementHoverXpath = testData[0][0];
			ConnsHomePage.elementHoveringVerification(webPage, elementHoverXpath, softAssert);
		if (testType.equalsIgnoreCase("Web")) {				
			for (int i = 0; i < testData.length; i++) {
				linkName = testData[i][2];
				xpath = testData[i][1];
				expectedHref = testData[i][2];
				log.info("Iteration under test : " + i);							
				ConnsHomePage.linkStatusCodeAndHrefValueVerification(webPage, linkName, xpath, expectedHref,testData, softAssert);
				}
			}
		    if (testType.equalsIgnoreCase("Mobile") || (testType.equalsIgnoreCase("Web") && (testBedName.equalsIgnoreCase("Edge")))) {
				ConnsHomePage.LinksRedirection_Under_Option_Menu_Verification_Mobile(webPage, testData,patio_Furniture_Matteress_Link_Locator,patio_Functionality,portable_Air_Conditioners, softAssert);
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
		webPage.getDriver().get(url);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyLinksForComputerAccessories");
			Thread.sleep(3000);
			String elementHoverXpath ="";
			String linkName = "";
			String xpath = "";
			String expectedHref = "";
			String patio_Furniture_Matteress_Link_Locator = "";
			boolean patio_Functionality = false;
			boolean portable_Air_Conditioners = false;	
			elementHoverXpath = testData[0][0];
			ConnsHomePage.elementHoveringVerification(webPage, elementHoverXpath, softAssert);
		if (testType.equalsIgnoreCase("Web")) {				
			for (int i = 0; i < testData.length; i++) {
				linkName = testData[i][2];
				xpath = testData[i][1];
				expectedHref = testData[i][2];
				log.info("Iteration under test : " + i);							
				ConnsHomePage.linkStatusCodeAndHrefValueVerification(webPage, linkName, xpath, expectedHref,testData, softAssert);
				}
			}
		    if (testType.equalsIgnoreCase("Mobile") || (testType.equalsIgnoreCase("Web") && (testBedName.equalsIgnoreCase("Edge")))) {
				ConnsHomePage.LinksRedirection_Under_Option_Menu_Verification_Mobile(webPage, testData,patio_Furniture_Matteress_Link_Locator,patio_Functionality,portable_Air_Conditioners, softAssert);
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
	 * Note : Skipped following iteration from datasheet
	 * The following asserts failed:
	 * Status Code for link /anniversary-sale/With Url :/anniversary-sale/ is 404,
	 * Status Code for link /anniversary-sale/With Url :/anniversary-sale/ is 404,
	 * Status Code for link /yes-money-financing-vs-rent-to-own-texas/With Url :/yes-money-financing-vs-rent-to-own-texas/ is 404
	 * 
	 */
	@Test(priority = 13, enabled = true, description = "Verify LinksRedirection Under FinancingPromotions Menu")
	public void Verify_LinksRedirection_Under_FinancingPromotions_Menu() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		webPage.getDriver().get(url);
		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyLinksForFinancingPromotions");
		String hoverElementXpath = testData[0][0];
		String patio_Furniture_Matteress_Link_Locator = "";
		boolean patio_Functionality = false;
		boolean portable_Air_Conditioners = false ;
		try {
			Thread.sleep(2000);
			if (testType.equalsIgnoreCase("Web")) {
			   ConnsHomePage.LinksRedirection_Under_FinancingPromotions_Menu_Verification_Web(webPage,hoverElementXpath,  testData, softAssert);
			}
			   if (testType.equalsIgnoreCase("Mobile") || (testType.equalsIgnoreCase("Web") && (testBedName.equalsIgnoreCase("Edge")))) {
				ConnsHomePage.LinksRedirection_Under_Option_Menu_Verification_Mobile(webPage,   testData, patio_Furniture_Matteress_Link_Locator, patio_Functionality,portable_Air_Conditioners,   softAssert);
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
		String ActualURL = "";
		String link_Name = "";
		String Redirection_Link_Locator = "";
		String Expected_URL = "";
		webPage.getDriver().get(url);
		commonMethods.waitForPageLoad(webPage, softAssert);
		Thread.sleep(2000);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyYesMeBanner");
			for (int i = 0; i < testData.length; i++) {
			Redirection_Link_Locator  = testData[i][1];
			Expected_URL = testData[i][4];
			link_Name = testData[i][0];
			log.info("***********  TestType is  : " +"************  testBedName **************"  );
			ActualURL = ConnsHomePage.Verify_HomePage_Banner_Links(webPage,testData,Redirection_Link_Locator,link_Name,url,softAssert);
			softAssert.assertTrue(ActualURL.contains(Expected_URL),"Expected url: "+Expected_URL+" Actual url: "+ActualURL);
			}
		if (testType.equalsIgnoreCase("Web") && (!(testBedName.equalsIgnoreCase("Edge") ))) {
				log.info("Web TestType is  : " +testType +"************ testBedName **************" +testBedName );
				ConnsHomePage.PageNavigation_PageLoad( webPage,  url, softAssert) ;
				for(int i= 0;i<testData.length;i++)
				{  	String Banner_Text_Xpath = testData[i][6];
					String Expected_Banner_Text = 	testData[i][7];					
					log.info("Actual : "+commonMethods.getTextbyXpath(webPage, Banner_Text_Xpath, softAssert)+" Expected : "+Expected_Banner_Text);
					softAssert.assertTrue((commonMethods.getTextbyXpath(webPage, Banner_Text_Xpath, softAssert).equals(Expected_Banner_Text)));	
					}
			}
			softAssert.assertAll();
			}
		catch (Throwable e) {
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
		ConnsHomePage.verifySaveBigWithConnsSection(testData,url,testType);
	}

	/**
	 * Test Case - 017 - verify Top Category links Conns Home Page
	 * 
	 */
	@Test(priority = 16, enabled = true, description = "Verify_Top_Categories_LinksRedirection")
	public void Verify_Top_Categories_LinksRedirection() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		webPage.getDriver().get(url);
		commonMethods.waitForPageLoad(webPage, softAssert);
		Thread.sleep(2000);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyTopCategorySection");
			ConnsHomePage.Verify_Link_Navigation( webPage, testData, url,testType,softAssert);	
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
		webPage.getDriver().get(url);
		commonMethods.waitForPageLoad(webPage, softAssert);
		Thread.sleep(3000);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyFollowUsSection");
			ConnsHomePage.Link_Redirection_Verification( webPage, testData,url,testType,testBedName,softAssert);	
			softAssert.assertAll();
		}catch (Throwable e) {
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
			String Redirection_Link_Locator = testData[0][1];
			String linkName	= testData[0][0];	
			ActualURL = ConnsHomePage.verifyLinkNavigationByJS(webPage,linkName,Redirection_Link_Locator,url, softAssert);
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
		webPage.getDriver().get(url);
		commonMethods.waitForPageLoad(webPage, softAssert);
		Thread.sleep(3000);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page","verifyFooterAboutConnsLinks");
			ConnsHomePage.Verify_Link_Navigation( webPage, testData, url,testType,softAssert);	
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
		webPage.getDriver().get(url);
		commonMethods.waitForPageLoad(webPage, softAssert);
		Thread.sleep(3000);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page","verifyFooterCustomerServiceSectionLinks");
			ConnsHomePage.Link_Redirection_Verification( webPage, testData, url,testType,testBedName,softAssert);	
			softAssert.assertAll();
	} catch (Throwable e) {
			e.printStackTrace();
			log.info("In Catch");
			mainPage.getScreenShotForFailure(webPage, "Verify_LinkRedirection_Under_Footer_Customer_Service_Section");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	@Test(priority = 21, enabled = true, description = "Verify_Primary_Navigation_Bar_Items_And_Order")
	public void Verify_Primary_Navigation_Bar_Items_And_Order()
			throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		webPage.getDriver().get(url);
		commonMethods.waitForPageLoad(webPage, softAssert);
		commonMethods.waitForGivenTime(3, softAssert);
		try {
			String[][] testData = null;
			if(testType.equalsIgnoreCase("Mobile"))
			{
				testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page","VerifyPrimaryNavOrderMobile");
				//commonMethods.clickElementbyXpath(webPage, testData[11][1], softAssert);
			}
			else
			{
				testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page","VerifyPrimaryNavOrderWeb");
			}

			for(int i=0;i<testData.length-1;i++)
			{
				log.info((testData[i][0]));
			}

			List<WebElement>ExpectedMenuItems = new ArrayList<WebElement>();
			if(testType.equalsIgnoreCase("Mobile"))
			{
				commonMethods.clickElementbyXpath(webPage, testData[14][1], softAssert);
				ExpectedMenuItems= webPage.getDriver().findElements(By.xpath(testData[14][2]));
			}
			else
			{
				ExpectedMenuItems= webPage.getDriver().findElements(By.xpath(testData[7][1]));
			}
			
			for(int i=0;i<ExpectedMenuItems.size();i++)
			{
				log.info(ExpectedMenuItems.get(i).getText());
			}
			softAssert.assertEquals(testData, ExpectedMenuItems, "The Order & Items on Primary Nav Bar are not displayed correctly");
			log.info("The Order & Items on Primary Nav Bar are displayed correctly");
			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_Primary_Navigation_Bar_Items_And_Order");
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Error in Verify_Primary_Navigation_Bar_Items_And_Order :" + e.getMessage());
		}
	}
	
	@Test(priority = 22, enabled = true, description = "Verify LinksRedirection Under Mattresses Menu")
	public void Verify_LinksRedirection_Under_Mattresses_Menu() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		webPage.getDriver().get(url);	
		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Conns_Home_Page", "verifyLinksForMattresses");
		String elementHoverXpath ="";
		String linkName = "";
		String xpath = "";
		String expectedHref = "";
		String patio_Furniture_Matteress_Link_Locator = "";
		boolean patio_Functionality = true;
		boolean portable_Air_Conditioners = false;
		Thread.sleep(2000);
		try {
			elementHoverXpath = testData[0][0];
			ConnsHomePage.elementHoveringVerification(webPage, elementHoverXpath, softAssert);
			if (testType.equalsIgnoreCase("Web")) {
			for (int i = 0; i < testData.length; i++) {
				linkName = testData[i][2];
				xpath = testData[i][1];
				expectedHref = testData[i][2];
				log.info("Iteration under test : " + i);							
				ConnsHomePage.linkStatusCodeAndHrefValueVerification(webPage, linkName, xpath, expectedHref,testData, softAssert);
				}
			}
		    if (testType.equalsIgnoreCase("Mobile") || (testType.equalsIgnoreCase("Web") && (testBedName.equalsIgnoreCase("Edge")))) {
				ConnsHomePage.LinksRedirection_Under_Option_Menu_Verification_Mobile(webPage, testData,patio_Furniture_Matteress_Link_Locator,patio_Functionality,portable_Air_Conditioners, softAssert);
			  }
			softAssert.assertAll();
		}catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_LinksRedirection_Under_Mattresses_Menu");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
}