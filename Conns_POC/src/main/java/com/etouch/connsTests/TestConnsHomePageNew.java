package com.etouch.connsTests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.etouch.common.BaseTest;
import com.etouch.common.TafExecutor;
import com.etouch.connsPages.ConnsHomePageNew;
import com.etouch.connsPages.ConnsMainPage;
import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.datamanager.excel.TafExcelDataProvider;
import com.etouch.taf.core.datamanager.excel.TestParameters;
import com.etouch.taf.core.datamanager.excel.annotations.IExcelDataFiles;
import com.etouch.taf.core.datamanager.excel.annotations.ITafExcelDataProviderInputs;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.tools.rally.VideoRecorder;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.taf.util.TafPassword;
import com.etouch.taf.webui.ITafElement;
import com.etouch.taf.webui.selenium.MobileView;
import com.etouch.taf.webui.selenium.WebPage;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

@IExcelDataFiles(excelDataFiles = { "ConnsHomePageData=testData" })
public class TestConnsHomePageNew extends BaseTest {

	// private String url = "http://eTouch.net";
	// private YesMoneyCreditApplication yesMoneyCreditApplication = null;
	// required for jira
	/** The url3. */
	String url3 = TestBedManager.INSTANCE.getDefectConfig().getUrl3();

	/** The issue url. */
	String issueUrl = TestBedManager.INSTANCE.getDefectConfig().getIssueUrl();

	/** The username. */
	String username = TestBedManager.INSTANCE.getDefectConfig().getUsername();

	/** The password. */
	TafPassword password = TestBedManager.INSTANCE.getDefectConfig().getPassword();

	/** The keys. */
	String keys = TestBedManager.INSTANCE.getDefectConfig().getKeys();

	// required for rally
	/** The Constant DEFECT_PROP. */
	// private static final String DEFECT_PROP = null;

	// required for rally
	/** The Constant DEFECT_PROP. */

	/** The Constant STORY_ID. */
	// private static final String STORY_ID = null;

	/** The project id. */
	String PROJECT_ID = TestBedManager.INSTANCE.getDefectConfig().getProjectId();

	/** The defect owner. */
	String DEFECT_OWNER = TestBedManager.INSTANCE.getDefectConfig().getDefectOwner();

	/** The workspace id. */
	String WORKSPACE_ID = TestBedManager.INSTANCE.getDefectConfig().getWorkspaceId();
	VideoRecorder videoRecorder = null;
	AppiumDriver driver = null;
	MobileView mobileView = null;
	TouchAction act1;
	DesiredCapabilities capabilities;

	Properties props;
	String deviceName;
	String plateformVersion;
	String platformName;
	String udid;
	String appPackage;
	String appActivity;
	String appiumHost;
	String appiumPortNumber;
	String apkPath;
	private String testBedName;
	TestBed testBed;
	Path path;
	String DataFilePath;
	String testType;
	String testEnv;
	int j = 0;

	static Log log = LogUtil.getLog(TestConnsHomePageNew.class);
	Logger logger = Logger.getLogger(ConnsHomePageNew.class.getName());
	private final int MAX_WAIT = 20;

	private String url;
	private String PageUrl = "";
	private WebPage webPage;
	private ConnsHomePageNew ConnsHomePage;
	static String platform;
	static String AbsolutePath = TafExecutor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	static String videoLocation = AbsolutePath.substring(0, AbsolutePath.indexOf("/target/classes/")).substring(1)
			.concat("/src/test/resources/testdata/videos");

	/*
	 * public static String var_ObjType; public static String var_Xpath; public
	 * static String var_Overlay_Close_Css;
	 */

	/* public static String LandingPage; */

	/**
	 * Prepare before class.
	 *
	 * @throws Exception
	 *             the exception
	 */
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
				//System.out.println("testEnv is : " + testEnv);
				System.out.println("testEnv is : " + System.getenv().get("Environment"));
				//System.out.println("testEnv is : " + System.getProperty("Environment"));
				//System.out.println("testEnv is : " + System.getProperty("ENVIRONMENT"));
				path = Paths.get(TestBedManager.INSTANCE.getProfile().getXlsDataConfig().get("testData"));
				DataFilePath = path.toAbsolutePath().toString().replace("Env", testEnv);
				System.out.println("DataFilePath After is : " + DataFilePath);
				
				platform = testBed.getPlatform().getName().toUpperCase();
				if (testType.equalsIgnoreCase("Web")) {
					System.out.println("videoLocation" + videoLocation);

					// SpecializedScreenRecorder.startVideoRecordingForDesktopBrowser(videoLocation);
				} else {
				}
				url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL();
				// url =
				// "http://connsecommdev-1365538477.us-east-1.elb.amazonaws.com/conns_rwd/";
				synchronized (this) {

					webPage = new WebPage(context);
					ConnsHomePage = new ConnsHomePageNew(url, webPage);

				}
			} catch (Exception e) {
				log.info("errr is " + e);
				SoftAssertor.addVerificationFailure(e.getMessage());
			}
		}

		catch (Exception e) {

			CommonUtil.sop("errr is for" + testBedName + " -----------" + e);
			SoftAssertor.addVerificationFailure(e.getMessage());
		}
	}
	
	/**
	 * Test Case - 001 - Verify title and URL of page
	 * Conns Home Page
	 * 
	 */

	@Test(priority = 1, enabled = true)
	public void verifyHomePageTitle() {
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "ConnsHomePageDev", "Verifytitle");
		String ExpectedURL = test[0][0];
		String ExpectedTitle = test[0][1];
		ConnsHomePage.verifyPageTitle(ExpectedURL, ExpectedTitle);

	}
	
	/**
	 * Test Case - 002 - Verify Font Size and Style of specified on element on
	 * Conns Home Page
	 * 
	 */
	
	@Test(dataProvider = "tafDataProvider", dataProviderClass = TafExcelDataProvider.class, priority = 2, enabled = true, description = "Verify FontandSize")
	@ITafExcelDataProviderInputs(excelFile = "ConnsHomePageData", excelsheet = "ConnsHomePageDev", dataKey = "VerifyFontandSize")
	public void verifyFontandSize(ITestContext context, TestParameters inputs)
			throws PageException, InterruptedException {
		Thread.sleep(2000);
		log.info("Verifying :: Font and Size of content");
		int index = 0;
		try {
			List<String> ActualValues = ConnsHomePage.getInfo(inputs.getParamMap().get("Locator"),
					inputs.getParamMap().get("IsAltPresent"), inputs.getParamMap().get("ObjType"),
					inputs.getParamMap().get("isTitlePresent"));
			List<String> exp_pageText = ConnsHomePage.getExpectedInfo(inputs.getParamMap().get("TextAttributes"),
					testBedName);
			ConnsHomePage.verifyGivenValues(ActualValues, exp_pageText, "verifying font of" + index + "element");
		} catch (Exception e) {
			log.error(">--------------Test case verify font on Homepage failed -------------<" + e.getMessage());
			SoftAssertor.addVerificationFailure(e.getMessage());
			e.printStackTrace();
		}

		finally {
			index++;
		}
		log.info("Verification of font and size completed");
	}
	
	/*@Test(priority = 2, enabled = true)
	public void verifyFontSizeAndStyle() {

		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "ConnsHomePageDev", "VerifyFontandSize");
		log.info("testing flow verifyFontSizeAndStyle started");
		ConnsHomePage.verifyFontAndSize(testdata, testType);
		
	}*/
	
	/**
	 * Test Case - 003 - Verify broken images on page
	 * Conns Home Page
	 * 
	 */

	@Test(priority = 3, enabled = true)
	public void verifyBrokenImage() throws ClientProtocolException, IOException {
		ConnsHomePage.verifyBrokenImage();
	}
	
	/**
	 * Test Case - 004 - verify Links Above Header
	 * Conns Home Page
	 * 
	 */

	@Test(priority = 4, enabled = true)
	public void verifyLinksAboveHeader() throws PageException {

		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "ConnsHomePageDev", "verifyLinksAboveHeader");
		ConnsHomePage.verifylinks(testData, testBed, testType);

	}
	
	/**
	 * Test Case - 005 - verify Your Cart functionality by adding product in cart
	 * Conns Home Page
	 * 
	 */

	@Test(priority = 5, enabled = true)
	public void verifyYourCartFunctionality() throws PageException, InterruptedException {

		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "ConnsHomePageDev", "verifyYourCart");
		ConnsHomePage.verifyYourCart(testData, testType);

	}
	
	/**
	 * Test Case - 006 - verify links In Header
	 * Conns Home Page
	 * 
	 */

	@Test(priority = 6, enabled = true)
	public void verifylinksInHeader() throws PageException, InterruptedException {

		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "ConnsHomePageDev", "verifyLinksInHeader");
		ConnsHomePage.verifylinks(testData, testBed, testType);

	}
	
	/**
	 * Test Case - 007 - verify links under Furniture & Mattresses main menu
	 * Conns Home Page
	 * 
	 */

	@Test(priority = 7, enabled = true)
	public void verifyFurnitureAndMattressesNavigation() throws PageException, InterruptedException {

		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "ConnsHomePageDev",
				"verifyLinksForFurnitureAndMattresses");
		ConnsHomePage.verifyNavigationLinks(testData, url, testType);

	}
	
	/**
	 * Test Case - 008 - verify links under Appliances main menu
	 * Conns Home Page
	 * 
	 */

	@Test(priority = 8, enabled = true)
	public void verifyAppliancesNavigation() throws PageException, InterruptedException {

		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "ConnsHomePageDev", "verifyLinksForAppliance");
		ConnsHomePage.verifyNavigationLinks(testData, url, testType);

	}
	/**
	 * Test Case - 009 - verify links under TV, Audio & Electronics main menu
	 * Conns Home Page
	 * 
	 */
	@Test(priority = 9, enabled = true)
	public void verifyTVAudioNavigation() throws PageException, InterruptedException {

		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "ConnsHomePageDev",
				"verifyLinksForTvAudioElectronics");
		ConnsHomePage.verifyNavigationLinks(testData, url, testType);

	}
	/**
	 * Test Case - 010 - verify links under Computer Accessories main menu
	 * Conns Home Page
	 * 
	 */
	@Test(priority = 10, enabled = true)
	public void verifyComputerAccessoriesNavigation() throws PageException, InterruptedException {

		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "ConnsHomePageDev",
				"verifyLinksForComputerAccessories");
		ConnsHomePage.verifyNavigationLinks(testData, url, testType);

	}
	
	/**
	 * Test Case - 011 - verify links under Financing and Promotions main menu
	 * Conns Home Page
	 * 
	 */

	@Test(priority = 11, enabled = true)
	public void verifyFinancingPromotionsNavigation() throws PageException, InterruptedException {

		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "ConnsHomePageDev",
				"verifyLinksForFinancingPromotions");
		ConnsHomePage.verifyNavigationLinks(testData, url, testType);

	}
	
	/**
	 * Test Case - 012 - verify Yes Banner
	 * Conns Home Page
	 * 
	 */

	@Test(priority = 12, enabled = true)
	public void verifyYesBanner() throws PageException, InterruptedException {

		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "ConnsHomePageDev", "verifyYesMeBanner");
		ConnsHomePage.verifylinks(testData, testBed, testType);

	}
	
	/**
	 * Test Case - 013 - verify NextDay Banner
	 * Conns Home Page
	 * 
	 */

	@Test(priority = 13, enabled = true)
	public void verifyNextDayBanner() throws PageException, InterruptedException {

		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "ConnsHomePageDev", "verifyNextDayDeliveryBanner");
		ConnsHomePage.verifylinks(testData, testBed, testType);

	}
	
	/**
	 * Test Case - 014 - verify Save Big With Conns section
	 * Conns Home Page
	 * 
	 */

	@Test(priority = 14, enabled = true)
	public void verifySaveBigWithConns() throws PageException, InterruptedException {

		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "ConnsHomePageDev", "verifySaveBigWithConns");
		ConnsHomePage.verifySaveBigWithConnsSection(testData);

	}
	
	/**
	 * Test Case - 015 - verify Top Category links
	 * Conns Home Page
	 * 
	 */

	@Test(priority = 15, enabled = true)
	public void verifyTopCategorylinks() throws PageException, InterruptedException {

		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "ConnsHomePageDev", "verifyTopCategorySection");
		ConnsHomePage.verifylinks(testData, testBed, testType);

	}
	
	/**
	 * Test Case - 016 - verify Build Your Own Financial Future Banner
	 * Conns Home Page
	 * 
	 */

	@Test(priority = 16, enabled = true)
	public void verifyBuildYourOwnFinancialFutureBanner() throws PageException, InterruptedException {

		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "ConnsHomePageDev",
				"verifyBuildYourOwnFinancialFutureBanner");
		ConnsHomePage.verifylinks(testData, testBed, testType);

	}
	
	/**
	 * Test Case - 017 - verify Help Children Banner
	 * Conns Home Page
	 * 
	 */

	@Test(priority = 17, enabled = true)
	public void verifyHelpChildrenBanner() throws PageException, InterruptedException {

		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "ConnsHomePageDev", "verifyHelpChildrenBanner");
		ConnsHomePage.verifylinks(testData, testBed, testType);

	}
	
	/**
	 * Test Case - 018 - verify Six Reasons Banner
	 * Conns Home Page
	 * 
	 */

	@Test(priority = 18, enabled = true)
	public void verifySixReasonsBanner() throws PageException, InterruptedException {

		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "ConnsHomePageDev", "verifySixReasonsBanner");
		ConnsHomePage.verifylinks(testData, testBed, testType);

	}
	/**
	 * Test Case - 019 - verify Promotions Banner
	 * Conns Home Page
	 * 
	 */

	@Test(priority = 19, enabled = true)
	public void verifyPromotionsBanner() throws PageException, InterruptedException {

		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "ConnsHomePageDev", "verifyPromotionsBanner");
		ConnsHomePage.verifylinks(testData, testBed, testType);

	}
	/**
	 * Test Case - 020 - verify Follow Us Section
	 * Conns Home Page
	 * 
	 */

	@Test(priority = 20, enabled = true)
	public void verifyFollowUsSection() throws PageException, InterruptedException {

		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "ConnsHomePageDev", "verifyFollowUsSection");
		ConnsHomePage.verifylinks(testData, testBed, testType);

	}
	/**
	 * Test Case - 021 - verify BBBRating Banner
	 * Conns Home Page
	 * 
	 */

	@Test(priority = 21, enabled = true)
	public void verifyBBBRatingBanner() throws PageException, InterruptedException {

		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "ConnsHomePageDev", "verifyBBBRatingBanner");
		ConnsHomePage.verifylinks(testData, testBed, testType);

	}
	/**
	 * Test Case - 022 - verify links under About Conns footer
	 * Conns Home Page
	 * 
	 */


	@Test(priority = 22, enabled = true)
	public void verifyFooterAboutConnsLinks() throws PageException, InterruptedException {

		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "ConnsHomePageDev", "verifyFooterAboutConnsLinks");
		ConnsHomePage.verifylinks(testData, testBed, testType);

	}
	
	/**
	 * Test Case - 023 - verify links under Customer Service footer
	 * Conns Home Page
	 * 
	 */

	@Test(priority = 23, enabled = true)
	public void verifyFooterCustomerServiceSectionLinks() throws PageException, InterruptedException {

		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "ConnsHomePageDev",
				"verifyFooterCustomerServiceSectionLinks");
		ConnsHomePage.verifylinks(testData, testBed, testType);

	}
	
	/**
	 * Test Case - 024 - verify Footer We Accpet
	 * Conns Home Page
	 * 
	 */

	@Test(dataProvider = "tafDataProvider", dataProviderClass = TafExcelDataProvider.class, priority = 24, enabled = true)
	@ITafExcelDataProviderInputs(excelFile = "ConnsHomePageData", excelsheet = "ConnsHomePageDev", dataKey = "verifyFooterWeAccpet")
	public void verifyFooterWeAccpet(ITestContext context, TestParameters inputs)
			throws PageException, InterruptedException {

		ConnsHomePage.elementVisiblity(inputs.getParamMap().get("ElementIdentifier"), url);

	}
	/**
	 * Test Case - 025 - verify Footer Copyright
	 * Conns Home Page
	 * 
	 */

	@Test(dataProvider = "tafDataProvider", dataProviderClass = TafExcelDataProvider.class, priority = 25, enabled = true)
	@ITafExcelDataProviderInputs(excelFile = "ConnsHomePageData", excelsheet = "ConnsHomePageDev", dataKey = "verifyFooterCopyright")
	public void verifyFooterCopyright(ITestContext context, TestParameters inputs)
			throws PageException, InterruptedException {
		ConnsHomePage.contentVerification(inputs.getParamMap().get("ElementIdentifier"),
				inputs.getParamMap().get("ExpectedText"), url);

	}

}