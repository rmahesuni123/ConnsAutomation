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
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.etouch.common.BaseTest;
import com.etouch.common.CommonMethods;
import com.etouch.common.TafExecutor;
import com.etouch.connsPages.ConnsAccountAndSignInPage;
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

@IExcelDataFiles(excelDataFiles = { "ConnsAccountSignINData=testData" })
public class Conns_Account_And_SignIn_Page extends BaseTest {
	
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
	int j = 0;

	static Log log = LogUtil.getLog(Conns_Account_And_SignIn_Page.class);
	Logger logger = Logger.getLogger(ConnsAccountAndSignInPage.class.getName());
	private final int MAX_WAIT = 20;

	private String url;
	private String PageUrl = "";
	private WebPage webPage;
	private ConnsAccountAndSignInPage ConnsSignInPage;
	private ConnsMainPage mainPage;
	CommonMethods commonMethods = new CommonMethods();
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
	@BeforeClass(alwaysRun = false)
	public void setUp(ITestContext context) throws InterruptedException, FileNotFoundException, IOException {
		try {
			testBedName = context.getCurrentXmlTest().getAllParameters().get("testBedName");
			CommonUtil.sop("Test bed Name is " + testBedName);
			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			log.info("Test Type is : " + testType);
			try {

				platform = testBed.getPlatform().getName().toUpperCase();
				if (testType.equalsIgnoreCase("Web")) {
					log.info("videoLocation" + videoLocation);

					// SpecializedScreenRecorder.startVideoRecordingForDesktopBrowser(videoLocation);
				} else {
				}
				path = Paths.get(TestBedManager.INSTANCE.getProfile().getXlsDataConfig().get("testData"));
				DataFilePath = path.toAbsolutePath().toString();
				url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL();
				String[][] test = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "PageURL");
				url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL() + test[0][0];

				synchronized (this) {

					webPage = new WebPage(context);
					ConnsSignInPage = new ConnsAccountAndSignInPage(url, webPage);
					// connsHomepage=new ConnsHomePageNew;

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
	 * Test Case - 001 - Verify title and URL of Login page
	 * 
	 */

	@Test(priority = 1,  enabled = false)
	public void verify_Login_Page_Title() {
		log.info("************ Stated title verification of Login Page*******************");
		SoftAssert softAssert = new SoftAssert();
		try{
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyLoginPageTitle");
		//ConnsSignInPage.verifyPageTitle(testdata);
		String actualPageUrl = commonMethods.getPageUrl(webPage, softAssert);
		softAssert.assertEquals(actualPageUrl, testdata[0][0],"Page url verification failed. Expected url : "+testdata[0][0]+"Actual url : "+actualPageUrl);
		String actualPageTitle = commonMethods.getPageTitle(webPage, softAssert);
		softAssert.assertEquals(actualPageTitle, testdata[0][1],"Page title verification failed. Expected title : "+testdata[0][1]+"Actual title : "+actualPageTitle);
		softAssert.assertAll();
		}catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verifyLoginPageTitle");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 002 - Verify font on login page
	 * 
	 */

	@Test(dataProvider = "tafDataProvider", dataProviderClass = TafExcelDataProvider.class, priority = 2,  enabled = false, description = "Verify FontandSize")
	@ITafExcelDataProviderInputs(excelFile = "ConnsAccountSignINData", excelsheet = "AccountSignINPage", dataKey = "verifyFontandSizeOnLoginPage")
	public void verify_Font_and_Size_On_Login_Page(ITestContext context, TestParameters inputs)
			throws PageException, InterruptedException {
		Thread.sleep(2000);
		log.info("**************Started Font and Size of content verification on Login Page *******************************");
		SoftAssert softAssert = new SoftAssert();
		try{
		int index = 0;
		try {
			List<String> ActualValues = ConnsSignInPage.getInfo(inputs.getParamMap().get("Locator"),
					inputs.getParamMap().get("IsAltPresent"), inputs.getParamMap().get("ObjType"),
					inputs.getParamMap().get("isTitlePresent"));
			List<String> exp_pageText = ConnsSignInPage.getExpectedInfo(inputs.getParamMap().get("TextAttributes"),
					testBedName);
			ConnsSignInPage.verifyGivenValues(ActualValues, exp_pageText, "verifying font of" + index + "element");
		} catch (Exception e) {
			log.error(">--------------Test case verify font on login page failed -------------<" + e.getMessage());
			SoftAssertor.addVerificationFailure(e.getMessage());
			e.printStackTrace();
		}

		finally {
			index++;
		}
		
	
		log.info("Verification of font and size completed on Login Page");
	}
		catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verifyLoginPageTitle");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

/*	@Test(priority = 5, enabled = false)
	public void verifyContentOnLoginPage() {
		log.info("**************Started Content verification on Login Page *******************");
		SoftAssert softAssert = new SoftAssert();
		try{
			String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyContentOnLoginPage");
			for (int r = 0; r < testdata.length; r++)
			{
				String actualText=commonMethods.getTextbyXpath(webPage, testdata[r][0]);
				softAssert.assertEquals(actualText, testdata[r][1]);
			}
		}
		catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verifyLoginPageTitle");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
		//ConnsSignInPage.verifyContent(testdata);

	}
*/

/*
	@Test(priority = 5, enabled = false)
	public void verify_Content_On_Login_Page() {
		log.info("**************Started Content verification on Login Page *******************");
		SoftAssert softAssert = new SoftAssert();
		try{
			String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyContentOnLoginPage");
			ConnsSignInPage.verifyContent(testdata);
		}
		catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verify_Content_On_Login_Page");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
		
		Assert.assertEquals(ActualElementName, ExpectedElementName, "content does not Match");
	

	}*/
	

	/**
	 * Test Case - 005 - Verify content on Login Page
	 * 
	 */	
	
	@Test(priority = 5,  enabled = false)
	public void verify_Content_On_Login_Page() {
		log.info("**************Started Content verification on Login Page *******************");
		List <String> content = new ArrayList<String>();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyContentOnLoginPage");
		SoftAssert softAssert = new SoftAssert();
		try{
			for (int r = 0; r < testdata.length; r++) {
				String ActualElementName =commonMethods.getTextbyXpath(webPage, testdata[r][0], softAssert);
				String ExpectedElementName = testdata[r][1];
				softAssert.assertEquals(ActualElementName, ExpectedElementName,"Page Content verification failed. Expected Page Content Expected Element Name : "+ExpectedElementName + "Actual Page Content Actual Element Name : "+ActualElementName);	
			} 
			if (content.size() > 0) {
				Assert.fail("Content " + Arrays.deepToString(content.toArray()) + " are not working as expected");
			}
		}
		catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verify_Content_On_Login_Page");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());

		}
		

	}


	/**
	 * Test Case - 006 - Verify login functionality with Invalid Inputs
	 * 
	 */
	@Test(priority = 6,  enabled = false)
	public void verify_Login_Functionality_with_Invalid_Input() {
		log.info("******Started verification of Login functionality with invalid data ********");
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyLoginfuncInvalidInput");
		String ExpErrMsgEmail = testdata[1][3];
		String ExpErrMsgPwd = testdata[1][7];
		try{
			List<String> actualErrorMessage =ConnsSignInPage.verify_Login_Functionality_with_Invalid_Input(testdata);
			softAssert.assertEquals(actualErrorMessage.get(0), ExpErrMsgEmail,"Login Functionality with Invalid Input verification failed For Invalid Email Address. Expected Email Address Error Message : "+ExpErrMsgEmail + "Actual Email Address Error Message : "+actualErrorMessage);	
			softAssert.assertEquals(actualErrorMessage.get(0), ExpErrMsgPwd,"  Login Functionality with Invalid Input verification failed For Invalid Password . Expected Password Error Message : "+ExpErrMsgPwd + "Actual Password Error Message : "+actualErrorMessage);
			softAssert.assertAll();
		}
		catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verifyLoginPageTitle");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	
	
	/**
	 * Test Case - 007 - Verify Login functionality with Blank Input
	 * 
	 */

	@Test(priority = 7,  enabled = false)
	public void verify_Login_Functionality_with_Blank_Input() {
	log.info("******Started verification of Login functionality with blank data ********");
	SoftAssert softAssert = new SoftAssert();
	System.out.println("Error 405 Amby Valley");
	String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyLoginfuncInvalidInput");
	System.out.println("Error 406 Amby Valley");
	String ExpErrMsgEmail = testdata[1][3];
	String ExpErrMsgPwd = testdata[1][7];
	try{
		System.out.println("Error 407 Amby Valley");
	List<String> actualErrorMessage = ConnsSignInPage.verify_Login_Functionality_with_Blank_Input(testdata);
	System.out.println("Error 408 Amby Valley");
	softAssert.assertEquals(actualErrorMessage.get(0), ExpErrMsgEmail,"Login Functionality with Blank Input verification failed For Blank Email Address. Expected Email Address Error Message : "+ExpErrMsgEmail + "Actual Email Address Error Message : "+actualErrorMessage);	
	softAssert.assertEquals(actualErrorMessage.get(0), ExpErrMsgPwd,"  Login Functionality with Blank Input verification failed For Blank Password . Expected Password Error Message : "+ExpErrMsgPwd + "Actual Password Error Message : "+actualErrorMessage);
	softAssert.assertAll();
	}
	catch(Throwable e){
		mainPage.getScreenShotForFailure(webPage, "verify_Login_Functionality_with_Blank_Input");
		softAssert.assertAll();
		Assert.fail(e.getLocalizedMessage());
	}
}
	
	
	
	/**
	 * Test Case - 008 - Verify WhatsThisOverlay on login page
	 * 
	 */

	@Test(priority = 8,  enabled = false)
	public void verify_Whats_This_Overlay_Rendered() throws PageException {
		log.info("**************Started verification of What's this overlay Rendered on Login Page *******************");
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyWhatsThisOverlay");
		String Locator = testdata[0][0];
		String contentonoverlaylocator = testdata[0][1];
		String verify_Whats_This_Overlay_Rendered_Expected_Content = testdata[0][2];
		String closebutlocator = testdata[0][3];
		
		try{
			commonMethods.clickElementbyXpath(webPage, Locator, softAssert);
			String verify_Whats_This_Overlay_Rendered_Actual_Content = commonMethods.getTextbyCss(webPage,contentonoverlaylocator, softAssert);
			softAssert.assertEquals(verify_Whats_This_Overlay_Rendered_Actual_Content, verify_Whats_This_Overlay_Rendered_Expected_Content,"verify_Whats_This_Overlay_Rendered verification failed. Whats_This_Overlay_Rendered_Expected_Content : "+verify_Whats_This_Overlay_Rendered_Expected_Content+" Whats_This_Overlay_Rendered_Actual_Content : "+verify_Whats_This_Overlay_Rendered_Actual_Content);
			softAssert.assertAll();
		}catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verify_Whats_This_Overlay_Rendered");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}

	}
	
	/**
	 * Test Case - 009 - Verify WhatsThisOverlay on login page
	 * 
	 */

	@Test(priority = 9,  enabled = false)
	public void verify_Whats_This_Overlay_Close() {
		log.info("**************Started verification of What's this overlay Rendered on Login Page *******************");
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyWhatsThisOverlay");
		String Locator = testdata[0][0];
		String contentonoverlaylocator = testdata[0][1];
		String verify_Whats_This_Overlay_Close_Expected_Content = testdata[0][2];
		String closebutlocator = testdata[0][5];
		String Navigate_To_Account_Login_Form_URL = testdata[0][6];
		try{
			webPage.getDriver().navigate().to(Navigate_To_Account_Login_Form_URL);			
			Thread.sleep(3000);
			commonMethods.clickElementbyXpath(webPage, Locator, softAssert);
			String verify_Whats_This_Overlay_Close_Actual_Content = commonMethods.getTextbyCss(webPage,contentonoverlaylocator, softAssert);
			softAssert.assertEquals(verify_Whats_This_Overlay_Close_Actual_Content, verify_Whats_This_Overlay_Close_Expected_Content,"verify_Whats_This_Overlay_Close verification failed.  Whats_This_Overlay_Close_Expected_Content : "+verify_Whats_This_Overlay_Close_Expected_Content+" Whats_This_Overlay_Rendered_Actual_Content : "+verify_Whats_This_Overlay_Close_Actual_Content);
			Thread.sleep(3000);
			commonMethods.clickElementbyXpath(webPage, closebutlocator, softAssert);
			softAssert.assertAll();
		}catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verify_Whats_This_Overlay_Close");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
		
	}
	
	/**
	 * Test Case - 0010 - Verify Remember Me Checkbox functionality on Login Page
	 * 
	 */

	@Test(priority = 10,  enabled = false)
	public void verify_Redirectional_links() {
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifylinksOnLoginPage");
		List<String> brokenLinks = new ArrayList<String>();
		log.info("******Started verification of Redirectional_links functionality with valid data ********");
		SoftAssert softAssert = new SoftAssert();
		try{
			for (int r = 0; r < testdata.length; r++) {
				log.info("Verifying " + testdata[r][0]);
				String ParentElementLocator = testdata[r][1];
				String ChildElementLocator = testdata[r][2];
				String Expected_URL = testdata[r][3];
				String Expected_Element_Name = testdata[r][4];
				String Expected_Page_Title = testdata[r][5];
				log.info("Parent Locator is ..." + ParentElementLocator);

				/*if (!(ParentElementLocator.equalsIgnoreCase("NA"))) {
					webPage.hoverOnElement(By.cssSelector(testdata[r][0]));
					}*/
				String Actual_Element_Name = commonMethods.getTextbyCss(webPage,ChildElementLocator, softAssert);				
				commonMethods.clickElementbyCss(webPage, ChildElementLocator, softAssert);
				String Actual_Page_Url = commonMethods.getPageUrl(webPage, softAssert);
				String Actual_Page_Title = commonMethods.getPageTitle(webPage, softAssert);
				softAssert.assertEquals(Actual_Page_Url, Expected_URL,"Redirectional_links Functionality with Invalid Input verification failed For Redirectional_URL. Expected Redirectional_URL  : "+Expected_URL + "Actual Redirectional_URL  : "+Actual_Page_Url);	
				softAssert.assertEquals(Actual_Element_Name, Expected_Element_Name,"  Redirectional_links Functionality with Invalid Input verification failed For Redirectional_Parent_Element_Name . Expected Redirectional Parent_Element_Name : "+Expected_Element_Name + "Actual Redirectional_Parent_Element_Name : "+Actual_Element_Name);
				softAssert.assertEquals(Actual_Page_Title,Expected_Page_Title,"  Redirectional_links Functionality with Invalid Input verification failed For Redirectional_Page_Title . Expected Redirectional_Page_Title  : "+Expected_Page_Title + "Actual Redirectional_Page_Title  : "+Actual_Page_Title);
				webPage.getDriver().navigate().back();
				webPage.getDriver().navigate().refresh();
				softAssert.assertAll();
			}
		}catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verify_Redirectional_links");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
		if (brokenLinks.size() > 0) {
			Assert.fail("Link " + Arrays.deepToString(brokenLinks.toArray()) + " are not working as expected");
		}
	}
	
	
	
	
	
	/**
	 * Test Case - 011 - Verify Remember Me Checkbox functionality on Login Page
	 * 
	 */

	@Test(priority = 11,  enabled = false)
	public void verify_Remember_Me_CheckBox() {
		log.info("******Started verification of RememberMeCheckBox functionality with valid data ********");
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyRememberMeFunctionality");
			String Remember_me_CheckBox = testdata[0][0];
			String Expected_ToolTip_Text = testdata[0][1];
			ConnsSignInPage.verify_Remember_Me_Functionality(testdata);
			commonMethods.clickElementbyCss(webPage, Remember_me_CheckBox, softAssert);
			String Actual_ToolTip_Text = commonMethods.getAttributebyCss(webPage, Remember_me_CheckBox, "title", softAssert);
			SoftAssertor.assertEquals(Actual_ToolTip_Text, Expected_ToolTip_Text, "ToolTip_Text of the mouse pointer does not match");
			softAssert.assertAll();
		}catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verify_Remember_Me_CheckBox");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	

	/**
	 * Test Case - 012 - Verify Login functionality with Valid Input
	 * 
	 */

	@Test(priority = 12,  enabled = false)
	public void verify_Login_Functionality_Registered_User(ITestContext context) {
		log.info("******Started verification of Login functionality with valid data ********");
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyLoginFunctionality_Valid_Input");
		SoftAssert softAssert = new SoftAssert();
		String ChildElementLocator = null;
		String Expected_Page_URL = null;
		String Expected_Element_Name = null;
		String Actual_Page_Url = null;
		String Actual_Element_Name = null;
		String EmailAddressLocator = testdata[0][0];
		String EmailAddress = testdata[0][1];
		String PasswordLocator = testdata[0][2];
		String Password = testdata[0][3];
		String LogInButtonLocator = testdata[0][4];
		String Expected_Page_Title = testdata[0][5];
		Expected_Element_Name = testdata[0][6];
		Expected_Page_URL = testdata[0][7];
		ChildElementLocator = testdata[0][8];
		//ConnsSignInPage.verifyLoginFunctionality_Registered_User(testdata);
		try {
			commonMethods.sendKeysbyXpath(webPage, EmailAddressLocator, EmailAddress, softAssert);
			commonMethods.sendKeysbyXpath(webPage, PasswordLocator, Password, softAssert);
			commonMethods.clickElementbyXpath(webPage, LogInButtonLocator, softAssert);
			Actual_Element_Name =commonMethods.getTextbyXpath(webPage, ChildElementLocator, softAssert);
			SoftAssertor.assertEquals(Actual_Element_Name, Expected_Element_Name, "Element name does not match");
			String Actual_Page_Title = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(Actual_Page_Title,Expected_Page_Title,"  Redirectional_links Functionality with valid Input verification failed For Login_Functionality_Registered_User . Expected Login_Functionality_Registered_User_Page_Title  : "+Expected_Page_Title + "Actual Login_Functionality_Registered_User_Page_Title  : "+Actual_Page_Title);
			Actual_Page_Url = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertEquals(Actual_Page_Url, Expected_Page_URL,"verify_Login_Functionality_Registered_User  with valid Input verification failed For Login_Functionality_Registered_User. Expected Login_Functionality_Registered_User_Page_URL  : "+Expected_Page_URL + "Actual Login_Functionality_Registered_User_Page_URL  : "+Actual_Page_Url);	


			testBedName = context.getCurrentXmlTest().getAllParameters().get("testBedName");
			CommonUtil.sop("Test bed Name is " + testBedName);
			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			log.info("Test Type is : " + testType);
			

				platform = testBed.getPlatform().getName().toUpperCase();
				if (testType.equalsIgnoreCase("Mobile")) {
					commonMethods.clickElementbyXpath(webPage, ".//*[@id='slide-nav']/div/div[1]/div/div[3]/div[1]/button", softAssert);
					commonMethods.clickElementbyXpath(webPage, ".//*[@id='li-primary-pronav-signout']/a", softAssert);
					commonMethods.clickElementbyXpath(webPage, ".//*[@id='slide-nav']/div/div[1]/div/div[3]/div[1]/button", softAssert);
					//commonMethods.clickElementbyXpath(webPage, ".//*[@id='li-primary-pronav-signout']/a");
					commonMethods.clickElementbyXpath(webPage, ".//*[@id='li-primary-pronav-signin']/a", softAssert);
					
				}
			
			
			/*webPage.getDriver().navigate().back();
			webPage.getDriver().navigate().refresh();*/
			Thread.sleep(7000);
			softAssert.assertAll();
		}catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verify_Login_Functionality_Registered_User");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	
/***************************************************** UC 01 FINISHES **********************************************************************************************************************************************/
	
/***************************************************** UC 02 STARTS **********************************************************************************************************************************************/


	
	/**
	 * Test Case - 013 - Verify forgot password page title
	 * @throws PageException 
	 * 
	 * 
	 */

	@Test(priority = 13,  enabled = false)
	public void verify_Forgot_Password_Page_Title() throws PageException {
	log.info("******Started verification of title on Forgot Password Page ********");
	String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "ForgotPasswordPageTitle");
	String Expected_Forgot_Password_Page_URL= testdata[0][0];
	String Expected_Forgot_Password_Page_Title= testdata[0][1];
	String Forgot_Password_Link = testdata[0][2];
	SoftAssert softAssert = new SoftAssert();
	try{
	//ConnsSignInPage.verifyPageTitle(testdata);
	commonMethods.clickElementbyXpath(webPage, Forgot_Password_Link, softAssert);
	String Actual_Forgot_Password_Page_URL = commonMethods.getPageUrl(webPage, softAssert);
	softAssert.assertEquals(Actual_Forgot_Password_Page_URL, Expected_Forgot_Password_Page_URL,"Page url verification failed. Expected url : "+Expected_Forgot_Password_Page_URL+"Actual url : "+Actual_Forgot_Password_Page_URL);
	String Actual_Forgot_Password_Page_Title = commonMethods.getPageTitle(webPage, softAssert);
	softAssert.assertEquals(Actual_Forgot_Password_Page_Title, Expected_Forgot_Password_Page_Title,"Page title verification failed. Expected title : "+Expected_Forgot_Password_Page_Title+"Actual title : "+Actual_Forgot_Password_Page_Title);
	softAssert.assertAll();
	}catch(Throwable e){
		mainPage.getScreenShotForFailure(webPage, "verify_Forgot_Password_Page_Title");
		softAssert.assertAll();
		Assert.fail(e.getLocalizedMessage());
	}
	}
	
	/**
	 * Test Case - 014 - Verify Fonts and Size on Forgot Password Page 
	 * 
	 */
	
	 @Test(priority = 14, enabled = false, description = "Verify_Font_And_Size")
	 public void Verify_Font_And_Size(){
	  SoftAssert softAssert = new SoftAssert();
	  try
	  {
	   String[][] ExpectedFontValues = ExcelUtil.readExcelData(DataFilePath, "ConnsHomePageDev","VerifyFontandSize");
	   for(int i=0;i<ExpectedFontValues.length;i++)
	   {
	   // List<String> actualCssValues= commonMethods.getFontProperties(webPage, ExpectedFontValues[i][1]);

	 //   softAssert.assertTrue(actualCssValues.get(0).contains(ExpectedFontValues[i][2]), "CSS value verification failed for link " + ExpectedFontValues[i][0] + "Expected font Size : "+ ExpectedFontValues[i][2] + " Actual Font Size : " + actualCssValues.get(0));
	//    softAssert.assertTrue(actualCssValues.get(1).contains(ExpectedFontValues[i][3]), "CSS value verification failed for link " + ExpectedFontValues[i][0] + "Expected font family : "+ ExpectedFontValues[i][3] + " Actual font family : " + actualCssValues.get(1));
	   }
	   softAssert.assertAll();
	  }
	  catch(Throwable e){
	   //mainPage.getScreenShotForFailure(webPage, "Verify_Font_And_Size");
	   softAssert.assertAll();
	   Assert.fail(e.getLocalizedMessage());
	  } 
	 }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*	@Test(dataProvider = "tafDataProvider", dataProviderClass = TafExcelDataProvider.class, priority = 14, enabled = false, description = "Verify FontandSize")
	@ITafExcelDataProviderInputs(excelFile = "ConnsAccountSignINData", excelsheet = "AccountSignINPage", dataKey = "verifyFontandSizeOnForgotPasswordPage")
	public void verify_Font_and_Size_On_Forgot_Password_Page(ITestContext context, TestParameters inputs)
			throws PageException, InterruptedException {
		Thread.sleep(2000);
		log.info("**************Started Font and Size of content verification on Forgot Password Page *******************************");
		int index = 0;
		try {
			List<String> ActualValues = ConnsSignInPage.getInfo(inputs.getParamMap().get("Locator"),
					inputs.getParamMap().get("IsAltPresent"), inputs.getParamMap().get("ObjType"),
					inputs.getParamMap().get("isTitlePresent"));
			List<String> exp_pageText = ConnsSignInPage.getExpectedInfo(inputs.getParamMap().get("TextAttributes"),
					testBedName);
			ConnsSignInPage.verifyGivenValues(ActualValues, exp_pageText, "verifying font of" + index + "element");
		} catch (Exception e) {
			log.error(">--------------Test case verify font on login page failed -------------<" + e.getMessage());
			SoftAssertor.addVerificationFailure(e.getMessage());
			e.printStackTrace();
		}

		finally {
			index++;
		}
		log.info("Verification of font and size completed on Login Page");
	}*/

	
	/**
	 * Test Case - 017 - Verify ForgotPwdPage Content
	 * 
	 */

	@Test(priority = 17,  enabled = false)
	public void verify_Forgot_Password_Page_Content() throws PageException {
		log.info("******Started verification of content on Forgot Password Page *********");
		List <String> content = new ArrayList<String>();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifycontentonforgotPwdPage");
		String Forgot_Password_Link = testdata[0][2];
		SoftAssert softAssert = new SoftAssert();
		try{
			commonMethods.clickElementbyXpath(webPage, Forgot_Password_Link, softAssert);
			for (int r = 0; r < testdata.length; r++) {
				String Actual_Element_Name =commonMethods.getTextbyXpath(webPage, testdata[r][0], softAssert);
				String Expected_Element_Name = testdata[r][1];
				softAssert.assertEquals(Actual_Element_Name, Expected_Element_Name,"  Forgot_Password_Page_Content Functionality verification failed For Forgot_Password_Page_Content_Parent_Element_Name . Expected_Forgot_Password_Page_Content_Element_Namee : "+Expected_Element_Name + "Actual_Forgot_Password_Page_Content_Element_Name : "+Actual_Element_Name);
			} 
			if (content.size() > 0) {
				Assert.fail("Content " + Arrays.deepToString(content.toArray()) + " are not working as expected");
			}
		}
		catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verify_Forgot_Password_Page_Content");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());

		}
	}
		
/*		try{
			for (int r = 0; r < testdata.length; r++) {
				String ActualElementName =commonMethods.getTextbyXpath(webPage, testdata[r][0]);
				String ExpectedElementName = testdata[r][1];
				softAssert.assertEquals(ActualElementName, ExpectedElementName,"Page Content verification failed. Expected Page Content Expected Element Name : "+ExpectedElementName + "Actual Page Content Actual Element Name : "+ActualElementName);	
			} 
			if (content.size() > 0) {
				Assert.fail("Content " + Arrays.deepToString(content.toArray()) + " are not working as expected");
			}
		}
		catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verify_Forgot_Password_Page_Content");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());

		}*/
	
	
	/**
	 * Test Case - 018 - Verify ForgotPwdPage_Submit-Button functionality with Blank Input
	 * 
	 */
	
	@Test(priority = 18,  enabled = false)
	public void verify_Forgot_Password_Page_Submit_Button_Blank_Input() throws PageException {
	log.info("******Started verification of Error Message content on Forgot Password Page Submit Button with Blank Input *********");
	SoftAssert softAssert = new SoftAssert();
	String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "forgotPasswordFucwithInvaliddata");
	//ConnsSignInPage.verifyContent(inputdata);
	String Forgot_Password_Link = testdata[0][5];
	String Forgot_Passowrd_Submit_Button_Locator = testdata[0][3];
	String Forgot_Password_Error_Message_Locator = testdata[0][2];
	String Expected_Forgot_Password_Error_Message = testdata[0][4];
	try{
		//List<String> actualErrorMessage = ConnsSignInPage.verify_Login_Functionality_with_Blank_Input(testdata);
		webPage.getDriver().navigate().back();
		webPage.getDriver().navigate().refresh();
		commonMethods.clickElementbyXpath(webPage, Forgot_Password_Link, softAssert);
		commonMethods.clickElementbyXpath(webPage, Forgot_Passowrd_Submit_Button_Locator, softAssert);
		String Actual_Forgot_Password_Error_Message =commonMethods.getTextbyXpath(webPage, Forgot_Password_Error_Message_Locator, softAssert);
		softAssert.assertEquals(Actual_Forgot_Password_Error_Message, Expected_Forgot_Password_Error_Message,"Forgot Password Blank Input Error Message Functionality Failed For Blank Email Address Input. Expected_Forgot_Password_Error_Message  : "+Expected_Forgot_Password_Error_Message + "Actual_Forgot_Password_Error_Message : "+Actual_Forgot_Password_Error_Message);	
		softAssert.assertAll();
		}
		catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verify_Forgot_Password_Page_Submit_Button_Blank_Input");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	
	
	/**
	 * Test Case - 019 - Verify Forgot_Password_Page_GoBack_Link functionality 
	 * 
	 */
	
	@Test(priority = 19, enabled = false)
	public void verify_Forgot_Password_Page_Go_Back_Link() throws PageException {
		log.info("******Started verification of Go_Back_Link on Forgot Password Page ********");
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyLinksOnforgotPwdPage");
		//ConnsSignInPage.verifyLinks(data);
		List<String> brokenLinks = new ArrayList<String>();

		log.info("******Started verification of Go_Back_Link functionality with valid data ********");

		SoftAssert softAssert = new SoftAssert();
		try{

			log.info("Verifying " + testdata[0][0]);
			String ParentElementLocator = testdata[0][1];
			String Go_Back_Link = testdata[0][2];
			String Expected_Forgot_Password_Page_Go_Back_Link_Page_URL = testdata[0][3];
			String Expected_Forgot_Password_Page_Go_Back_Link_Element_Name = testdata[0][4];
			String Expected_Forgot_Password_Page_Go_Back_Link_Page_Title = testdata[0][5];
			String Forgot_Password_Link = testdata[1][2];
			String Login_Page_Element = testdata[2][2];
			String Expected_Login_Page_URL = testdata[2][3];
			String Expected_Login_Page_Element_Name= testdata[2][4];
			String Expected_Login_Page_Title= testdata[2][5];
			String Account_Login_Page_Forgot_Password_Page_Link = testdata[0][6];
			log.info("Parent Locator is ..." + ParentElementLocator);

			/*if (!(ParentElementLocator.equalsIgnoreCase("NA"))) {
				webPage.hoverOnElement(By.cssSelector(testdata[r][0]));
				}*/
			
			webPage.getDriver().navigate().to(Account_Login_Page_Forgot_Password_Page_Link);
			commonMethods.clickElementbyCss(webPage, Forgot_Password_Link, softAssert);
			String Actual_Forgot_Password_Page_Go_Back_Link_Element_Name = commonMethods.getTextbyCss(webPage,Go_Back_Link, softAssert);				
			String Actual_Forgot_Password_Page_Go_Back_Link_Page_URL = commonMethods.getPageUrl(webPage, softAssert);
			String Actual_Forgot_Password_Page_Go_Back_Link_Page_Title = commonMethods.getPageTitle(webPage, softAssert);
			
			softAssert.assertEquals(Actual_Forgot_Password_Page_Go_Back_Link_Page_URL, Expected_Forgot_Password_Page_Go_Back_Link_Page_URL,"Forgot_Password_Page_Go_Back_Link Functionality with verification failed For Forgot_Password_Page_Go_Back_Link_Page_URL. Expected_Forgot_Password_Page_Go_Back_Link_Page_URL  : "+Expected_Forgot_Password_Page_Go_Back_Link_Page_URL + "Actual_Forgot_Password_Page_Go_Back_Link_Page_URL  : "+Actual_Forgot_Password_Page_Go_Back_Link_Page_URL);	
			softAssert.assertEquals(Actual_Forgot_Password_Page_Go_Back_Link_Element_Name, Expected_Forgot_Password_Page_Go_Back_Link_Element_Name,"  Forgot_Password_Page_Go_Back_Link Functionality with verification failed For Forgot_Password_Page_Go_Back_Link_Element_Name . Expected_Forgot_Password_Page_Go_Back_Link_Element_Name : "+Expected_Forgot_Password_Page_Go_Back_Link_Element_Name + "Actual_Forgot_Password_Page_Go_Back_Link_Element_Name : "+Actual_Forgot_Password_Page_Go_Back_Link_Element_Name);
			softAssert.assertEquals(Actual_Forgot_Password_Page_Go_Back_Link_Page_Title,Expected_Forgot_Password_Page_Go_Back_Link_Page_Title,"  Forgot_Password_Page_Go_Back_Link Functionality with verification failed For Forgot_Password_Page_Go_Back_Link_Page_Title . Expected_Forgot_Password_Page_Go_Back_Link_Page_Title  : "+Expected_Forgot_Password_Page_Go_Back_Link_Page_Title + "Actual_Forgot_Password_Page_Go_Back_Link_Page_Title  : "+Actual_Forgot_Password_Page_Go_Back_Link_Page_Title);
			commonMethods.clickElementbyCss(webPage, Go_Back_Link, softAssert);
			String Actual_Login_Page_Element_Name = commonMethods.getTextbyXpath(webPage,Login_Page_Element, softAssert);				
			String Actual_Login_Page_Url = commonMethods.getPageUrl(webPage, softAssert);
			String Actual_Login_Page_Title = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(Actual_Login_Page_Url, Expected_Login_Page_URL,"Login_Page_URL Functionality with verification failed For Login_Page_URL. Expected_Login_Page_URL  : "+Expected_Login_Page_URL + "Actual_Login_Page_Url  : "+Actual_Login_Page_Url);	
			softAssert.assertEquals(Actual_Login_Page_Element_Name, Expected_Login_Page_Element_Name,"  Login_Page_Element Functionality with verification failed For Expected_Login_Page_Element_Name . Expected_Login_Page_Element_Name : "+Expected_Login_Page_Element_Name + "Actual_Login_Page_Element_Name : "+Actual_Login_Page_Element_Name);
			softAssert.assertEquals(Actual_Login_Page_Title,Expected_Login_Page_Title,"  Login_Page_Title Functionality with verification failed For Login_Page_Title . Expected_Login_Page_Title  : "+Expected_Login_Page_Title + "Actual_Login_Page_Title  : "+Actual_Login_Page_Title);
			softAssert.assertAll();

		}catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verify_Forgot_Password_Page_Go_Back_Link");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
			e.printStackTrace();
		}
		if (brokenLinks.size() > 0) {
			Assert.fail("Link " + Arrays.deepToString(brokenLinks.toArray()) + " are not working as expected");
		}
	}

	
	
	
	/**
	 * Test Case - 020 - Verify Forgot Password Page functionality with Invalid Input
	 * 
	 */
	
	@Test(priority = 20,  enabled = false)
	public void verify_Forgot_Password_Function_with_Invalid_Email_ID() throws PageException {
	log.info("******Started verification of Forgot Password functionality with Invalid and valid data ********");
	SoftAssert softAssert = new SoftAssert();
	String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "forgotPasswordFucwithInvaliddata");
	String Forgot_Password_Link = testdata[0][5];
	String Forgot_Password_Email_Address_Locator = testdata[1][0];
	String Forgot_Password_Email_Address_Data = testdata[1][1];
	String Forgot_Password_Email_Address_Error_Message_Locator = testdata[1][2];
	String Forgot_Passowrd_Submit_Button_Locator = testdata[1][3];
	String Expected_Forgot_Password_Error_Message = testdata[1][4];
	String Account_Login_Page_Forgot_Password_Page_Link = testdata[0][6];
	
	try{
		//List<String> actualErrorMessage = ConnsSignInPage.verify_Login_Functionality_with_Blank_Input(testdata);
	/*	webPage.getDriver().navigate().back();
		webPage.getDriver().navigate().refresh();*/
		webPage.getDriver().navigate().to(Account_Login_Page_Forgot_Password_Page_Link);
		//commonMethods.clickElementbyCss(webPage, Forgot_Password_Link, softAssert);
		commonMethods.clickElementbyXpath(webPage, Forgot_Password_Link, softAssert);
		commonMethods.sendKeysbyXpath(webPage, Forgot_Password_Email_Address_Locator, Forgot_Password_Email_Address_Data, softAssert);
		commonMethods.clickElementbyXpath(webPage, Forgot_Passowrd_Submit_Button_Locator, softAssert);
		String Actual_Forgot_Password_Email_Address_Error_Message_Locator =commonMethods.getTextbyXpath(webPage, Forgot_Password_Email_Address_Error_Message_Locator, softAssert);
		softAssert.assertEquals(Actual_Forgot_Password_Email_Address_Error_Message_Locator, Expected_Forgot_Password_Error_Message,"Forgot_Password_Function_with_Invalid_Data Functionality Failed For Invalid Email Address Input. Expected_Forgot_Password_Email_Address_Error_Message  : "+Expected_Forgot_Password_Error_Message + "Actual_Forgot_Password_Error_Message : "+Actual_Forgot_Password_Email_Address_Error_Message_Locator);	
		softAssert.assertAll();
		
		}
		catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verify_Forgot_Password_Function_with_Invalid_Email_ID");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	/**
	 * Test Case - 021- Verify Forgot Password Page functionality with Valid Input
	 * 
	 */
	
	@Test(priority = 21,  enabled = false)
	public void verify_Forgot_Password_Function_with_Valid_Email_ID() throws PageException {
	log.info("******Started verification of Forgot Password functionality with Invalid and valid data ********");
	SoftAssert softAssert = new SoftAssert();
	String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "forgotPasswordFucwithInvaliddata");
		
	String Forgot_Password_Link = testdata[0][5];
	String Forgot_Password_Email_Address_Locator = testdata[2][0];
	String Forgot_Password_Email_Address_Data = testdata[2][1];
	String Forgot_Password_Email_Address_Error_Message_Locator = testdata[2][2];
	String Forgot_Passowrd_Submit_Button_Locator = testdata[2][3];
	String Expected_Forgot_Password_Valid_Email_Address_Confirmation_Message = testdata[2][4];
	String Account_Login_Page_Forgot_Password_Page_Link = testdata[0][6];
	try{
		//List<String> actualErrorMessage = ConnsSignInPage.verify_Login_Functionality_with_Blank_Input(testdata);
		/*webPage.getDriver().navigate().back();
		webPage.getDriver().navigate().refresh();*/
		webPage.getDriver().navigate().to(Account_Login_Page_Forgot_Password_Page_Link);
		//commonMethods.clickElementbyCss(webPage, Forgot_Password_Link, softAssert);
		commonMethods.clickElementbyXpath(webPage, Forgot_Password_Link, softAssert);
		commonMethods.sendKeysbyXpath(webPage, Forgot_Password_Email_Address_Locator, Forgot_Password_Email_Address_Data, softAssert);
		commonMethods.clickElementbyXpath(webPage, Forgot_Passowrd_Submit_Button_Locator, softAssert);
		String Actual_Forgot_Password_Valid_Email_Address_Confirmation_Message =commonMethods.getTextbyXpath(webPage, Forgot_Password_Email_Address_Error_Message_Locator, softAssert);
		softAssert.assertEquals(Actual_Forgot_Password_Valid_Email_Address_Confirmation_Message, Expected_Forgot_Password_Valid_Email_Address_Confirmation_Message,"Forgot Password Blank Input Error Message Functionality Failed For Blank Email Address Input. Expected_Forgot_Password_Error_Message  : "+Expected_Forgot_Password_Valid_Email_Address_Confirmation_Message + "Actual_Forgot_Password_Valid_Email_Address_Confirmation_Message : "+Actual_Forgot_Password_Valid_Email_Address_Confirmation_Message);
		softAssert.assertAll();
		}
		catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verify_Forgot_Password_Function_with_Valid_Email_ID");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	
	
	/**
	 * Test Case - 022 - Verify Page Title in Account Information section
	 * 
	 */

	@Test(priority = 22, enabled = false)
	public void verify_Account_DashBoard_Page_Title() {
		log.info("******Started verification of content on Account Dashborad tab after login ********");
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageTitle");
		String Expected_Account_Dashboard_Page_URL = testdata[0][0];
		String Expected_Account_Dashboard_Page_Title = testdata[0][1];
		String EmailAddressLocator = testdata[0][2];
		String EmailAddress = testdata[0][3];
		String PasswordLocator =testdata[0][4];
		String Password = testdata[0][5];
		String LogInButtonLocator = testdata[0][6];
		String Navigate_To_OPT_URL = testdata[0][7];
		String Navigate_To_RWD_URL = testdata[0][8];
		SoftAssert softAssert = new SoftAssert();

		webPage.getDriver().navigate().to(Navigate_To_RWD_URL);
		commonMethods.sendKeysbyXpath(webPage, EmailAddressLocator, EmailAddress, softAssert);
		commonMethods.sendKeysbyXpath(webPage, PasswordLocator, Password, softAssert);
		commonMethods.clickElementbyXpath(webPage, LogInButtonLocator, softAssert);

		try{
			
			//String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyLoginPageTitle");
			//ConnsSignInPage.verifyPageTitle(testdata);
			String Actual_Account_Dashboard_Page_URL = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertEquals(Actual_Account_Dashboard_Page_URL, Expected_Account_Dashboard_Page_URL,"Page url verification failed. Expected url : "+Expected_Account_Dashboard_Page_URL +           "Actual url :                  "+Actual_Account_Dashboard_Page_URL);
			String Actual_Account_Dashboard_Page_Title = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(Actual_Account_Dashboard_Page_Title, Expected_Account_Dashboard_Page_Title,"Page title verification failed. Expected title : "+Expected_Account_Dashboard_Page_Title + "Actual title :                "+Actual_Account_Dashboard_Page_Title);
			softAssert.assertAll();
			}catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verify_Account_DashBoard_Page_Title");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	
	
	/**
	 * Test Case - 023 - Verify content in Account Information section
	 * 
	 */

	@Test(priority = 23, enabled = false)
	public void verify_Account_DashBoard_Content() {
		log.info("******Started verification of content on Account Dashborad tab after login ********");
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageTitle");
		ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);
		String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountPageContent");
		try{
			//String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyLoginPageTitle");
			//ConnsSignInPage.verifyPageTitle(testdata);
			//Assert.assertEquals(ActualElementName, ExpectedElementName, "content does not Match");
			List <String> content = new ArrayList<String>();
			for (int r = 0; r < test_data.length; r++) {
				try {
					String Actual_Element_Name_On_Account_Page_For_Page_Content_Verification =commonMethods.getTextbyXpath(webPage, testdata[r][0], softAssert);
					String Expected_Element_Name_On_Account_Page_For_Page_Content_Verification = testdata[r][1];
					softAssert.assertEquals(Actual_Element_Name_On_Account_Page_For_Page_Content_Verification, Expected_Element_Name_On_Account_Page_For_Page_Content_Verification,"Page Element Content verification failed. Expected_Element_Name_On_Account_Page_For_Page_Content_Verification : "+Expected_Element_Name_On_Account_Page_For_Page_Content_Verification +           "Actual_Element_Name_On_Account_Page_For_Page_Content_Verification :                  "+Actual_Element_Name_On_Account_Page_For_Page_Content_Verification);
					softAssert.assertAll();
					} catch (Throwable e) {
					content.add(testdata[r][1] + " " + e.getLocalizedMessage());
				}
			}
		}catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verify_Account_DashBoard_Page_Content");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	/**
	 * Test Case - 024 - Verify links in Account Information section
	 * 
	 */

	@Test(priority = 24, enabled = false)
	public void verify_Links_On_Account_DashBoard_Tab() {
		log.info("******Started verification of Links in Account Dashborad tab after login ********");
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageTitle");
		ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);
		String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyLinksOnAccountInformationSec");
		for(int r=0;r < test_data.length;r++)
		{	
		String Page_Expected_URL = test_data[r][3];
		String Page_Expected_Element_Name = test_data[r][4];
		String Page_Expected_Title = test_data[r][4];
		try{
	//	SoftAssertor.assertEquals(ActualElementName, ExpectedElementName, "Element name does not match");
		List<String> Page_URL_Title_Data = ConnsSignInPage.verify_Links_Account_Tab(test_data);
		softAssert.assertEquals(Page_URL_Title_Data.get(0), Page_Expected_URL,"Login Functionality with Blank Input verification failed For Blank Email Address. Expected Email Address Error Message : "+Page_Expected_URL + "Actual Email Address Error Message : "+Page_URL_Title_Data);	
		//softAssert.assertEquals(Page_URL_Title_Data.get(1), Page_Expected_Element_Name,"  Login Functionality with Blank Input verification failed For Blank Password . Expected Password Error Message : "+Page_Expected_Element_Name + "Actual Password Error Message : "+Page_URL_Title_Data);
		softAssert.assertEquals(Page_URL_Title_Data.get(2), Page_Expected_Title,"  Login Functionality with Blank Input verification failed For Blank Password . Expected Password Error Message : "+Page_Expected_Title + "Actual Password Error Message : "+Page_URL_Title_Data);
		softAssert.assertAll();
		//ConnsSignInPage.verifyLinks(testdata);

	}catch(Throwable e){
		mainPage.getScreenShotForFailure(webPage, "verify_Account_DashBoard_Page_Content");
		softAssert.assertAll();
		Assert.fail(e.getLocalizedMessage());
	}
		}
}
	

	/**
	 * Test Case - 025 - Verify content in Credit Application section on Account Dashboard tab
	 * 
	 */

	@Test(priority = 25, enabled = true)
	public void Verify_Credit_Application_Sec() {
		log.info("******Started verification of credit Application sec on Account Dashborad tab after login ********");
		SoftAssert softAssert = new SoftAssert();
		String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageTitle");
		ConnsSignInPage.verify_Account_DashBoard_Login(test_data,softAssert);
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "Verify_Credit_Application_Section");
		//ConnsSignInPage.verifyContent(testdata);
		List <String> content = new ArrayList<String>();
		for (int r = 0; r < testdata.length; r++) {
			try {
				String Actual_Content_On_Account_Information_Credit_Application_Section_For_Credit_Status_Content_Verification =commonMethods.getTextbyXpath(webPage, testdata[r][0], null);
				String Expected_Content_On_Account_Information_Credit_Application_Section_For_Credit_Status_Content_Verification = testdata[r][1];
				softAssert.assertEquals(Actual_Content_On_Account_Information_Credit_Application_Section_For_Credit_Status_Content_Verification, Expected_Content_On_Account_Information_Credit_Application_Section_For_Credit_Status_Content_Verification,"Page Element Content verification failed. Expected_Content_On_Account_Information_Credit_Application_Section_For_Credit_Status_Content_Verification : "+Expected_Content_On_Account_Information_Credit_Application_Section_For_Credit_Status_Content_Verification +           "Actual_Content_On_Account_Information_Credit_Application_Section_For_Credit_Status_Content_Verification :                  "+Actual_Content_On_Account_Information_Credit_Application_Section_For_Credit_Status_Content_Verification);
				softAssert.assertAll();
				//Assert.assertEquals(ActualElementName, ExpectedElementName, "content does not Match");
			} catch(Throwable e){
			//	mainPage.getScreenShotForFailure(webPage, "verify_Account_DashBoard_Page_Content");
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
				}
		}

	
	/**
	 * Test Case - 026 - Verify AccountInformationTab and Change Password functionality
	 * @throws PageException 
	 * 
	 */
	
	
	
	@Test(priority = 26, enabled = true)
	public void verify_Account_Information_Tab_Verify_Validation_Messages() throws PageException{
		SoftAssert softAssert = new SoftAssert();
		String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageTitle");
		ConnsSignInPage.verify_Account_DashBoard_Login(test_data,softAssert);
		log.info("verification of Mandatory field validation message started");
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","VerifyAccountInformationForm");
		String Navigate_To_Account_Information_Tab_Form_URL = testdata[0][12];
		String Expected_Email_ID_Error_Msg = testdata[0][10];
		String Expected_Email_ID_Error_Msg_Invalid_Email = testdata[1][10];
		String Expected_Customer_Already_Exists_Error_Message = testdata[2][14];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		try{
		for (int r = 0; r < testdata.length; r++) {
			String FNInput = testdata[r][2];
			String LNInput = testdata[r][5];
			String EmailInput = testdata[r][8];
			if (FNInput.equalsIgnoreCase("NA") && LNInput.equalsIgnoreCase("NA") && EmailInput.equalsIgnoreCase("NA")) {
					List<String> actualErrorMessage = ConnsSignInPage.verify_Account_Information_First_Name_Last_Name_Login_Validation(testdata,softAssert);
					softAssert.assertEquals(actualErrorMessage.get(0), Expected_Email_ID_Error_Msg,"Test Failed");
			}
			 if (FNInput.equalsIgnoreCase(testdata[1][2]) && LNInput.equalsIgnoreCase(testdata[1][5]) && EmailInput.equalsIgnoreCase(testdata[1][8])) {
				List<String> actualErrorMessage = ConnsSignInPage.verify_Account_Information_First_Name_Last_Name_Login_Invalid_Input_Validation(testdata,softAssert);
				softAssert.assertEquals(actualErrorMessage.get(0), Expected_Email_ID_Error_Msg_Invalid_Email,"Page Element Content verification failed. Expected_Email_ID_Error_Msg : "+Expected_Email_ID_Error_Msg_Invalid_Email +           "actualErrorMessage.get(0) :                  "+actualErrorMessage.get(0));
			 }
				 if (FNInput.equalsIgnoreCase(testdata[2][2]) && LNInput.equalsIgnoreCase(testdata[2][5]) && EmailInput.equalsIgnoreCase(testdata[2][8])) {
				List<String> actualErrorMessage = ConnsSignInPage.verify_Account_Information_First_Name_Last_Name_Login_Valid_Input_Validation(testdata,softAssert);
				softAssert.assertEquals(actualErrorMessage.get(0), Expected_Customer_Already_Exists_Error_Message,"Page Element Content verification failed. Expected_Email_ID_Error_Msg : "+Expected_Customer_Already_Exists_Error_Message + "actualErrorMessage.get(0) : "+actualErrorMessage.get(0));
			}
		}
	
		softAssert.assertAll();
		}
	catch(Throwable e){
		//mainPage.getScreenShotForFailure(webPage, "verify_Account_Information_Tab_Verify_Validation_Messages");
		softAssert.assertAll();
		Assert.fail(e.getLocalizedMessage());
	}
	}





	
	
	
	
	/**
	 * Test Case - 027 - Verify AccountInformationTab and Change Password functionality
	 * @throws PageException 
	 * 
	 */

	@Test(priority = 27, enabled = true)
	public void verify_Account_Information_Tab_Change_Password_Functionality() throws PageException{


		List<String> brokenItems = new ArrayList<String>();
		SoftAssert softAssert = new SoftAssert();
		String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageTitle");
		ConnsSignInPage.verify_Account_DashBoard_Login(test_data,softAssert);
		log.info("verification of Mandatory field validation message started");
		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","VerifyChangePasswordfun");
		String Navigate_To_Account_Information_Tab_Form_URL = inputdata[0][13];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		String Change_Password_Link_Locator =inputdata[0][0];
		commonMethods.clickElementbyXpath(webPage, Change_Password_Link_Locator, softAssert);
		try{
			log.info("in else part of change password method......");
			List<String> actualErrorMessage =ConnsSignInPage.verify_Account_Information_Tab_Change_Password_Functionality(inputdata);
			softAssert.assertEquals(actualErrorMessage.get(0), inputdata[1][10],"Change Password Functionality with Short Password Input verification failed For NA Password Input. Expected Password Error Message : "+inputdata[1][10] + "Actual Email Address Error Message : "+actualErrorMessage);	
			softAssert.assertEquals(actualErrorMessage.get(1), inputdata[2][10],"Change Password Functionality with Different Value New Password Input verification failed For NA Password Input. Expected Password Error Message : "+inputdata[2][10] + "Actual Email Address Error Message : "+actualErrorMessage);	
			softAssert.assertEquals(actualErrorMessage.get(2), inputdata[3][10],"Change Password Functionality with Different Value Confirm Password verification failed For NA Password Input. Expected Password Error Message : "+inputdata[2][10] + "Actual Email Address Error Message : "+actualErrorMessage);	
			softAssert.assertEquals(actualErrorMessage.get(3), inputdata[3][10],"Change Password Functionality with Invalid Input verification failed For NA Password Input. Expected Password Error Message : "+inputdata[3][10] + "Actual Email Address Error Message : "+actualErrorMessage);	
			softAssert.assertEquals(actualErrorMessage.get(4), inputdata[4][10],"Change Password Functionality with Valid Input verification failed For NA Password Input. Expected Password Error Message : "+inputdata[4][10] + "Actual Email Address Error Message : "+actualErrorMessage);	

		}
		catch(Throwable e){
			//mainPage.getScreenShotForFailure(webPage, "verify_Account_Information_Tab_Change_Password_Functionality");
			softAssert.assertAll();
			if (brokenItems.size() > 0) {
				Assert.fail("Link " + Arrays.deepToString(brokenItems.toArray()) + " are not working as expected");
			}
			Assert.fail(e.getLocalizedMessage());
		}
	}

	
	
	/**
	 * Test Case - 028 - Verify AccountInformationTab and Change Password functionality
	 * @throws PageException 
	 * 
	 */

	@Test(priority = 28, enabled = true)
	public void verify_Account_Information_Tab_Change_Password_Form_Go_Back_Link() throws PageException{


		List<String> brokenItems = new ArrayList<String>();
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageLogin");
		ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);
		log.info("verification of Mandatory field validation message started");
		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","VerifyChangePasswordfun");
		String Navigate_To_Account_Information_Tab_Form_URL = inputdata[0][13];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		String GO_BACK_Link_Locator =inputdata[0][14];
		try{
			String actualPageUrl = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertEquals(actualPageUrl, inputdata[0][13],"Page url verification failed. Expected url : "+inputdata[0][13]+"Actual url : "+actualPageUrl);
			String actualPageTitle = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(actualPageTitle, inputdata[0][15],"Page title verification failed. Expected title : "+inputdata[0][15]+"Actual title : "+actualPageTitle);
			commonMethods.clickElementbyXpath(webPage, GO_BACK_Link_Locator, softAssert);
			softAssert.assertAll();
		}catch(Throwable e){
		//	mainPage.getScreenShotForFailure(webPage, "verify_Account_Information_Tab_Change_Password_Form_Go_Back_Link");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
		
		
/***********************************************************************UC005 STARTS*******************************************************************************************************************************************************************************************/
	
	
	/**
	 * Test Case - 029 - Verify "Contact Information" form input validations by tapping on edit & submitting blank form
	 * 
	 */
	
	@Test(priority = 29, enabled = true)
	public void verify_Contact_Information_Tab_Edit_Link_Blank_Input() throws PageException{
		List<String> brokenItems = new ArrayList<String>();
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageLogin");
		ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);
		log.info("verification of Mandatory field validation message started");
		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","ContactInformationFunctionality");
		String Navigate_To_Account_Information_Tab_Form_URL = inputdata[0][0];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		String Contact_Information_Edit_Link_Locator =inputdata[0][1];
		commonMethods.clickElementbyXpath(webPage, Contact_Information_Edit_Link_Locator, softAssert);
		for (int r = 0; r < inputdata.length; r++) {
			String FNInput = inputdata[r][3];
			String LNInput = inputdata[r][6];
			String EmailInput = inputdata[r][9];
			String Expected_Username_Password_Error_Message = inputdata[r][11];
			String Expected_Email_ID_Error_Message = inputdata[r][11];
			String ButtonLocator = inputdata[r][13];
			try{
				if (FNInput.equalsIgnoreCase("NA") && LNInput.equalsIgnoreCase("NA") && EmailInput.equalsIgnoreCase("NA")) {
					List<String> actualErrorMessage = ConnsSignInPage.verify_Account_DashBoard_First_Name_Last_Name_Login(inputdata);
					SoftAssertor.assertEquals(actualErrorMessage.get(0), Expected_Username_Password_Error_Message);
					log.info("First_Name_Error_Message_Account_Information_Page : " +actualErrorMessage.get(0));
					log.info("ExpectedValMsg : " +Expected_Username_Password_Error_Message);
					SoftAssertor.assertEquals(actualErrorMessage.get(1), Expected_Username_Password_Error_Message);
					log.info("Last_Name_Error_Message_Account_Information_Page : " +actualErrorMessage.get(1));
					log.info("ExpectedValMsg : " +Expected_Username_Password_Error_Message);
					SoftAssertor.assertEquals(actualErrorMessage.get(2), Expected_Email_ID_Error_Message);
					log.info("Email_Address_Error_Message_Account_Information_Page : " +actualErrorMessage.get(2));
					log.info("ExpectedValMsg : " +Expected_Email_ID_Error_Message);
					softAssert.assertAll();
				}
			}
			catch(Throwable e){
				mainPage.getScreenShotForFailure(webPage, "verify_Contact_Information_Tab_Edit_Link_Blank_Input");
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
		}
	}
		
	
	
	
	/**
	 * Test Case - 030 - Verify "Contact Information" form input validations by tapping on edit & submitting blank form
	 * 
	 */
	
	@Test(priority = 30, enabled = true)
	public void verify_Contact_Information_Tab_Edit_Link_Valid_Input() throws PageException{
		List<String> brokenItems = new ArrayList<String>();
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageLogin");
		ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);
		log.info("verification of Mandatory field validation message started");
		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","ContactInformationFunctionality_Valid_Input");
		String Navigate_To_Account_Information_Tab_Form_URL = inputdata[0][0];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		String Contact_Information_Edit_Link_Locator =inputdata[0][1];
		commonMethods.clickElementbyXpath(webPage, Contact_Information_Edit_Link_Locator, softAssert);
		for (int r = 0; r < inputdata.length; r++) {
			String FNInput = inputdata[r][3];
			String LNInput = inputdata[r][6];
			String EmailInput = inputdata[r][9];
			String ExpectedValMsg = inputdata[r][11];
				try{
					if (!(FNInput.equalsIgnoreCase("NA") && LNInput.equalsIgnoreCase("NA") && EmailInput.equalsIgnoreCase("NA"))) {
						//System.out.println("On the Go");
						List<String> information_Saved_Successfully_Message = ConnsSignInPage.verify_Account_DashBoard_First_Name_Last_Name_Login(inputdata);
						/*String First_Name_Error_Message_Account_Information_Page = commonMethods.getTextbyXpath(webPage,FNErrLocator, softAssert);
						String Last_Name_Error_Message_Account_Information_Page = commonMethods.getTextbyXpath(webPage,LNErrLocator, softAssert);
						String Email_Address_Error_Message_Account_Information_Page = commonMethods.getTextbyXpath(webPage,EmailErrLocator, softAssert);
*/						SoftAssertor.assertEquals(information_Saved_Successfully_Message.get(0), ExpectedValMsg);
						log.info("First_Name_Error_Message_Account_Information_Page : " +information_Saved_Successfully_Message.get(0));
						log.info("ExpectedValMsg : " +ExpectedValMsg);
						softAssert.assertAll();
					}
				}
					catch(Throwable e){
						//mainPage.getScreenShotForFailure(webPage, "verify_Contact_Information_Tab_Edit_Link_Blank_Input");
						softAssert.assertAll();
						Assert.fail(e.getLocalizedMessage());
					}
				}
				}
		
	/**
	 * Test Case - 031 - Verify "Contact Information" form input validations by tapping on edit & submitting blank form
	 * 
	 */
	
	@Test(priority = 31, enabled = false)
	public void verify_Contact_Information_Tab_Default_Billing_Shipping_Address_Link() throws PageException{
		JavascriptExecutor jse = (JavascriptExecutor)webPage.getDriver();
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageLogin");
		ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);
		log.info("verification of Mandatory field validation message started");
		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","Address_Book_Manage_Addresses_Functionality");
		String Navigate_To_Account_Information_Tab_Form_URL = inputdata[0][0];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		String Address_Book_Manage_Addresses_Edit_Link_Locator =inputdata[0][1];
		String Change_Billing_Address_Edit_Link_Locator= inputdata[0][2];
		String Editing_Zip_Code_Link_Locator=inputdata[0][3];
		String Input_Zip_Code_Link_Locator=inputdata[0][4];
		String Save_Address_Button_Locator=inputdata[0][5];
		String Change_Shipping_Address_Edit_Link_Locator=inputdata[0][6];
		String Address_Book_Expected_Page_URL=inputdata[0][7];
		String Address_Book_Expected_Page_Title=inputdata[0][8];		
		String Address_Book_Expected_Page_URL_Index=inputdata[0][12];
		try{
		commonMethods.clickElementbyXpath(webPage, Address_Book_Manage_Addresses_Edit_Link_Locator, softAssert);
		commonMethods.clickElementbyXpath(webPage, Change_Billing_Address_Edit_Link_Locator, softAssert);		
		jse.executeScript("scroll(0, 250);");
		commonMethods.clickElementbyXpath(webPage, Editing_Zip_Code_Link_Locator, softAssert);		
		commonMethods.clearElementbyXpath(webPage, Editing_Zip_Code_Link_Locator, softAssert);		
		commonMethods.sendKeysbyXpath(webPage, Editing_Zip_Code_Link_Locator, Input_Zip_Code_Link_Locator, softAssert);
		commonMethods.clickElementbyXpath(webPage, Save_Address_Button_Locator, softAssert);
		String actualPageUrl = commonMethods.getPageUrl(webPage, softAssert);
		softAssert.assertEquals(actualPageUrl, Address_Book_Expected_Page_URL_Index,"Page url verification failed. Expected url : "+Address_Book_Expected_Page_URL_Index+"Actual url : "+actualPageUrl);
		String actualPageTitle = commonMethods.getPageTitle(webPage, softAssert);
		softAssert.assertEquals(actualPageTitle, Address_Book_Expected_Page_Title,"Page title verification failed. Expected title : "+Address_Book_Expected_Page_Title+"Actual title : "+actualPageTitle);
		webPage.getDriver().navigate().refresh();
		commonMethods.clickElementbyXpath(webPage, Change_Shipping_Address_Edit_Link_Locator, softAssert);		
		jse.executeScript("scroll(0, 250);");
		commonMethods.clickElementbyXpath(webPage, Editing_Zip_Code_Link_Locator, softAssert);		
		commonMethods.clearElementbyXpath(webPage, Editing_Zip_Code_Link_Locator, softAssert);		
		commonMethods.sendKeysbyXpath(webPage, Editing_Zip_Code_Link_Locator, Input_Zip_Code_Link_Locator, softAssert);
		commonMethods.clickElementbyXpath(webPage, Save_Address_Button_Locator, softAssert);
		softAssert.assertAll();
		}
	catch(Throwable e){
		mainPage.getScreenShotForFailure(webPage, "verify_Contact_Information_Tab_Default_Billing_Shipping_Address_Link");
		softAssert.assertAll();
		Assert.fail(e.getLocalizedMessage());
	}
}

	
	/**
	 * Test Case - 032 - Verify "Contact Information" form input validations by tapping on edit & submitting blank form
	 * 
	 */
	
	@Test(priority = 32, enabled = false)
	public void verify_Contact_Information_Tab_Change_Billing_Address_Link() throws PageException{
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageLogin");
		ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);
		log.info("verification of Mandatory field validation message started");
		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","Address_Book_Manage_Addresses_Functionality");
		String Navigate_To_Account_Information_Tab_Form_URL = inputdata[0][0];
		String Address_Book_Manage_Addresses_Edit_Link_Locator =inputdata[0][1];
		String Change_Billing_Address_Edit_Link_Locator= inputdata[0][2];
		String Contact_Information_Page_Address_Book_Go_Back_Link=inputdata[0][9];
		String Expected_Contact_Information_Page_Title=inputdata[0][10];
		String Expected_Contact_Information_Page_URL=inputdata[0][11];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		try{
		commonMethods.clickElementbyXpath(webPage, Address_Book_Manage_Addresses_Edit_Link_Locator, softAssert);
		commonMethods.clickElementbyXpath(webPage, Change_Billing_Address_Edit_Link_Locator, softAssert);		
		String actualPageUrl = commonMethods.getPageUrl(webPage, softAssert);
		softAssert.assertEquals(actualPageUrl, Expected_Contact_Information_Page_URL,"Page url verification failed. Expected url : "+Expected_Contact_Information_Page_URL+"Actual url : "+actualPageUrl);
		String actualPageTitle = commonMethods.getPageTitle(webPage, softAssert);
		softAssert.assertEquals(actualPageTitle, Expected_Contact_Information_Page_Title,"Page title verification failed. Expected title : "+Expected_Contact_Information_Page_Title+"Actual title : "+actualPageTitle);
		commonMethods.clickElementbyXpath(webPage, Contact_Information_Page_Address_Book_Go_Back_Link, softAssert);
		softAssert.assertAll();
		}
	catch(Throwable e){
		mainPage.getScreenShotForFailure(webPage, "verify_Contact_Information_Tab_Change_Billing_Address_Link");
		softAssert.assertAll();
		Assert.fail(e.getLocalizedMessage());
	}
}
	
	
	/**
	 * Test Case - 033 - Verify "Contact Information" form input validations by tapping on edit & submitting blank form
	 * 
	 */
	
	@Test(priority = 33, enabled = false)
	public void verify_Contact_Information_Tab_Change_Shipping_Address_Link() throws PageException{
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageLogin");
		ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);
		log.info("verification of Mandatory field validation message started");
		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","Address_Book_Manage_Addresses_Functionality");
		String Navigate_To_Account_Information_Tab_Form_URL = inputdata[0][0];
		String Address_Book_Manage_Addresses_Edit_Link_Locator =inputdata[0][1];
		String Change_Shipping_Address_Edit_Link_Locator=inputdata[0][6];
		String Contact_Information_Page_Address_Book_Go_Back_Link=inputdata[0][9];
		String Expected_Contact_Information_Page_Title=inputdata[0][10];
		String Expected_Contact_Information_Page_URL=inputdata[0][11];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		try{
		commonMethods.clickElementbyXpath(webPage, Address_Book_Manage_Addresses_Edit_Link_Locator, softAssert);
		commonMethods.clickElementbyXpath(webPage, Change_Shipping_Address_Edit_Link_Locator, softAssert);		
		String actualPageUrl = commonMethods.getPageUrl(webPage, softAssert);
		softAssert.assertEquals(actualPageUrl, Expected_Contact_Information_Page_URL,"Page url verification failed. Expected url : "+Expected_Contact_Information_Page_URL+"Actual url : "+actualPageUrl);
		String actualPageTitle = commonMethods.getPageTitle(webPage, softAssert);
		softAssert.assertEquals(actualPageTitle, Expected_Contact_Information_Page_Title,"Page title verification failed. Expected title : "+Expected_Contact_Information_Page_Title+"Actual title : "+actualPageTitle);
		commonMethods.clickElementbyXpath(webPage, Contact_Information_Page_Address_Book_Go_Back_Link, softAssert);
		softAssert.assertAll();
		}
	catch(Throwable e){
		mainPage.getScreenShotForFailure(webPage, "verify_Contact_Information_Tab_Change_Shipping_Address_Link");
		softAssert.assertAll();
		Assert.fail(e.getLocalizedMessage());
	}
}
	
	/**
	 * Test Case - 034 - Verify "Contact Information" form input validations by tapping on edit & submitting blank form
	 * 
	 */
	
	@Test(priority = 34, enabled = false)
	public void verify_Contact_Information_Tab_Go_Back_Link() throws PageException{
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageLogin");
		ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);
		log.info("verification of Mandatory field validation message started");
		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","Address_Book_Manage_Addresses_Functionality");
		String Navigate_To_Account_Information_Tab_Form_URL = inputdata[0][0];
		String Address_Book_Manage_Addresses_Edit_Link_Locator =inputdata[0][1];
		String Change_Billing_Address_Edit_Link_Locator= inputdata[0][2];
		String Change_Shipping_Address_Edit_Link_Locator=inputdata[0][6];
		String Address_Book_Expected_Page_URL=inputdata[0][7];
		String Address_Book_Expected_Page_Title=inputdata[0][8];
		String Contact_Information_Page_Address_Book_Go_Back_Link=inputdata[0][9];
		String Expected_Contact_Information_Page_Title=inputdata[0][10];
		String Expected_Contact_Information_Page_URL=inputdata[0][11];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		try{
			commonMethods.clickElementbyXpath(webPage, Address_Book_Manage_Addresses_Edit_Link_Locator, softAssert);
			commonMethods.clickElementbyXpath(webPage, Change_Billing_Address_Edit_Link_Locator, softAssert);		
			String Actual_Contact_Information_Change_Billing_Address_Page_URL = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertEquals(Actual_Contact_Information_Change_Billing_Address_Page_URL, Expected_Contact_Information_Page_URL,"Page url verification failed. Expected url : "+Expected_Contact_Information_Page_URL+"Actual url : "+Actual_Contact_Information_Change_Billing_Address_Page_URL);
			String Actual_Contact_Information_Change_Billing_Address_Page_Title = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(Actual_Contact_Information_Change_Billing_Address_Page_Title, Expected_Contact_Information_Page_Title,"Page title verification failed. Expected title : "+Expected_Contact_Information_Page_Title+"Actual title : "+Actual_Contact_Information_Change_Billing_Address_Page_Title);
			commonMethods.clickElementbyXpath(webPage, Contact_Information_Page_Address_Book_Go_Back_Link, softAssert);
			String actualAddressBookPageUrl = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertEquals(actualAddressBookPageUrl, Address_Book_Expected_Page_URL,"Page url verification failed. Expected url : "+Address_Book_Expected_Page_URL+"Actual url : "+actualAddressBookPageUrl);
			String actualAddressBookPageTitle = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(actualAddressBookPageTitle, Address_Book_Expected_Page_Title,"Page title verification failed. Expected title : "+Address_Book_Expected_Page_Title+"Actual title : "+actualAddressBookPageTitle);
			webPage.getDriver().navigate().refresh();
			commonMethods.clickElementbyXpath(webPage, Change_Shipping_Address_Edit_Link_Locator, softAssert);		
			String Actual_Contact_Information_Change_Shipping_Address_Page_URL = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertEquals(Actual_Contact_Information_Change_Shipping_Address_Page_URL, Expected_Contact_Information_Page_URL,"Page url verification failed. Expected url : "+Expected_Contact_Information_Page_URL+"Actual url : "+Actual_Contact_Information_Change_Shipping_Address_Page_URL);
			String Actual_Contact_Information_Change_Shipping_Address_Page_Title = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(Actual_Contact_Information_Change_Shipping_Address_Page_Title, Expected_Contact_Information_Page_Title,"Page title verification failed. Expected title : "+Expected_Contact_Information_Page_Title+"Actual title : "+Actual_Contact_Information_Change_Shipping_Address_Page_Title);
			commonMethods.clickElementbyXpath(webPage, Contact_Information_Page_Address_Book_Go_Back_Link, softAssert);
			String actualAddressPageUrl = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertEquals(actualAddressPageUrl, Address_Book_Expected_Page_URL,"Page url verification failed. Expected url : "+Address_Book_Expected_Page_URL+"Actual url : "+actualAddressPageUrl);
			String actualAddressPageTitle = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(actualAddressPageTitle, Address_Book_Expected_Page_Title,"Page title verification failed. Expected title : "+Address_Book_Expected_Page_Title+"Actual title : "+actualAddressPageTitle);
			softAssert.assertAll();
		}
		catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verify_Contact_Information_Tab_Go_Back_Link");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	
	/**
	 * Test Case - 035 - Verify "Contact Information" form input validations by tapping on edit & submitting blank form
	 * 
	 */
	
	@Test(priority = 35, enabled = false)
	public void verify_Contact_Information_Tab_Address_Book_Page_Additional_Address_Entries() throws PageException{
		List<String> brokenItems = new ArrayList<String>();
		SoftAssert softAssert = new SoftAssert();
		JavascriptExecutor jse = (JavascriptExecutor)webPage.getDriver();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageLogin");
		ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);
		log.info("verification of Mandatory field validation message started");
		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","Account_Information_Address_Book_Additional_Address_Valid_Input");
		//	String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","Address_Book_Manage_Addresses_Functionality");
		String Navigate_To_Account_Information_Tab_Form_URL = inputdata[0][0];
		String Account_Information_Address_Book_Manage_Addresses_Edit_Link_Locator =inputdata[0][1];
		String Account_Information_Address_Book_Additional_Address_Entries_Delete_Address_Locator= inputdata[0][2];
		String Account_Information_Address_Book_Add_New_Address_Locator=inputdata[0][3];
		String Expected_Additonal_Address_Successfully_Saved_Locator=inputdata[0][27];
		String Expected_Additonal_Address_Successfully_Saved_Message=inputdata[0][28];
		String Expected_Address_Book_Page_URL=inputdata[0][29];
		String Expected_Address_Book_Page_Title=inputdata[0][30];
		
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);		
		commonMethods.clickElementbyXpath(webPage, Account_Information_Address_Book_Manage_Addresses_Edit_Link_Locator, softAssert);
		try{
			
			/*if(	webPage.getDriver().findElement(By.xpath(inputdata[0][2])).isDisplayed())*/
			WebElement Address_Book_Additional_Address_Entries_Delete_Address_Locator = webPage.getDriver().findElement(By.xpath(inputdata[0][2]));
			if ((Address_Book_Additional_Address_Entries_Delete_Address_Locator).isDisplayed())
			{
				commonMethods.clickElementbyXpath(webPage, Account_Information_Address_Book_Additional_Address_Entries_Delete_Address_Locator, softAssert);
				Alert confirmationAlert = webPage.getDriver().switchTo().alert();
				String alertText = confirmationAlert.getText();
				log.info("Alert text is " + alertText);
				confirmationAlert.accept();
				commonMethods.clickElementbyXpath(webPage, Account_Information_Address_Book_Add_New_Address_Locator, softAssert);
			}
			ConnsSignInPage.verify_Contact_Information_Tab_Address_Book_Page_Additional_Address_Entries(inputdata);
			String actualAddressBookPageURL = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertEquals(actualAddressBookPageURL, Expected_Address_Book_Page_URL,"Page url verification failed. Expected url : "+Expected_Address_Book_Page_URL+"Actual url : "+actualAddressBookPageURL);
			String actualAddressBookPageTitle = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(actualAddressBookPageTitle, Expected_Address_Book_Page_Title,"Page title verification failed. Expected title : "+Expected_Address_Book_Page_Title+"Actual title : "+actualAddressBookPageTitle);
			String ActualElementName =commonMethods.getTextbyXpath(webPage, Expected_Additonal_Address_Successfully_Saved_Locator, softAssert);
			softAssert.assertEquals(ActualElementName, Expected_Additonal_Address_Successfully_Saved_Message,"Page Content verification failed. Expected Page Content Expected Element Name : "+Expected_Additonal_Address_Successfully_Saved_Message + "Actual Page Content Actual Element Name : "+ActualElementName);	
			softAssert.assertAll();
			}
		catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verify_Contact_Information_Tab_Address_Book_Page_Additional_Address_Entries");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
				
		
	



	
	
	
	
	
	

	/***********************************************************************UC005 ENDS*******************************************************************************************************************************************************************************************/	
	/***********************************************************************UC006 STARTS*******************************************************************************************************************************************************************************************/	

	
	/**
	 * Test Case - 036 - Verify "Contact Information" form input validations by tapping on edit & submitting blank form
	 * 
	 */
	
	@Test(priority = 36, enabled = false)
	public void verify_Account_Information_Tab_Newsletters_Link() throws PageException{
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageLogin");
		ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);
		log.info("verification of Mandatory field validation message started");
		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","Address_Book_Newsletters_Functionality");
		String Navigate_To_Account_Information_Tab_Form_URL = inputdata[0][0];
		
		String Newsletters_Edit_Link_Locator =inputdata[0][1];
		String Expected_Newsletters_Page_URL=inputdata[0][2];
		String Expected_Newsletters_Page_Title=inputdata[0][3];
		String Newsletters_Page_Go_Back_Link=inputdata[0][4];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		try{
			commonMethods.clickElementbyXpath(webPage, Newsletters_Edit_Link_Locator, softAssert);
			//commonMethods.clickElementbyXpath(webPage, Change_Billing_Address_Edit_Link_Locator, softAssert);		
			String Actual_Newsletters_Page_URL = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertEquals(Actual_Newsletters_Page_URL, Expected_Newsletters_Page_URL,"Page url verification failed. Expected url : "+Expected_Newsletters_Page_URL +"Actual url : "+Actual_Newsletters_Page_URL);
			String Actual_Newsletters_Page_Title = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(Actual_Newsletters_Page_Title, Expected_Newsletters_Page_Title,"Page title verification failed. Expected title : "+Expected_Newsletters_Page_Title+"Actual title : "+Actual_Newsletters_Page_Title);
			commonMethods.clickElementbyXpath(webPage, Newsletters_Page_Go_Back_Link, softAssert);
			softAssert.assertAll();
		}
		catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verify_Account_Information_Tab_Newsletter_Link");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}


	/**
	 * Test Case - 037 - Verify "Contact Information" form input validations by tapping on edit & submitting blank form
	 * 
	 */

	@Test(priority = 37, enabled = false)
	public void verify_Account_Information_Tab_Newsletters_General_Subscription_CheckBox() throws PageException{
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageLogin");
		ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);
		log.info("verification of Mandatory field validation message started");
		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","Address_Book_Newsletters_Functionality");
		String Navigate_To_Account_Information_Tab_Form_URL = inputdata[0][0];
		String Newsletters_Edit_Link_Locator =inputdata[0][1];
		String Newsletters_Page_Go_Back_Link=inputdata[0][4];
		String News_Letters_Subscription_CheckBox=inputdata[0][5];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		try{
			commonMethods.clickElementbyXpath(webPage, Newsletters_Edit_Link_Locator, softAssert);
			Thread.sleep(3000);
			WebElement Newsletters_Subscription_CheckBox = webPage.getDriver().findElement(By.xpath(inputdata[0][5]));
			if ((!(Newsletters_Subscription_CheckBox).isSelected()) && ((Newsletters_Subscription_CheckBox)).isEnabled())
			{
				commonMethods.clickElementbyXpath(webPage, News_Letters_Subscription_CheckBox, softAssert);
			}
			else{
				commonMethods.clickElementbyXpath(webPage, News_Letters_Subscription_CheckBox, softAssert);
				Thread.sleep(3000);
				commonMethods.clickElementbyXpath(webPage, News_Letters_Subscription_CheckBox, softAssert);
			}
			commonMethods.clickElementbyXpath(webPage, Newsletters_Page_Go_Back_Link, softAssert);
			softAssert.assertAll();
		}
		catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verify_Account_Information_Tab_Newsletters_General_Subscription_CheckBox");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	
	
	/**
	 * Test Case - 038 - Verify "Contact Information" form input validations by tapping on edit & submitting blank form
	 * 
	 */

	@Test(priority = 38, enabled = false)
	public void verify_Account_Information_Tab_Newsletters_Go_Back_Link() throws PageException{
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageLogin");
		ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);
		log.info("verification of Mandatory field validation message started");
		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","Address_Book_Newsletters_Functionality");
		String Navigate_To_Account_Information_Tab_Form_URL = inputdata[0][0];
		String Newsletters_Edit_Link_Locator =inputdata[0][1];
		String Expected_Newsletters_Page_URL=inputdata[0][2];
		String Expected_Newsletters_Page_Title=inputdata[0][3];
		String Newsletters_Page_Go_Back_Link=inputdata[0][4];
		String Expected_Account_Information_Page_URL=inputdata[0][6];
		String Expected_Account_Information_Page_Title=inputdata[0][7];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		try{
			commonMethods.clickElementbyXpath(webPage, Newsletters_Edit_Link_Locator, softAssert);
			String Actual_Newsletters_Page_URL = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertEquals(Actual_Newsletters_Page_URL, Expected_Newsletters_Page_URL,"Page url verification failed. Expected url : "+Expected_Newsletters_Page_URL+"Actual url : "+Actual_Newsletters_Page_URL);
			String Actual_Newsletters_Page_Title = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(Actual_Newsletters_Page_Title, Expected_Newsletters_Page_Title,"Page title verification failed. Expected title : "+Expected_Newsletters_Page_Title+"Actual title : "+Actual_Newsletters_Page_Title);
			commonMethods.clickElementbyXpath(webPage, Newsletters_Page_Go_Back_Link, softAssert);
			String actualAccountInformationPageUrl = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertEquals(actualAccountInformationPageUrl, Expected_Account_Information_Page_URL,"Page url verification failed. Expected url : "+Expected_Account_Information_Page_URL+"Actual url : "+actualAccountInformationPageUrl);
			String actualAccountInformationPageTitle = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(actualAccountInformationPageTitle, Expected_Account_Information_Page_Title,"Page title verification failed. Expected title : "+Expected_Account_Information_Page_Title+"Actual title : "+actualAccountInformationPageTitle);
			softAssert.assertAll();
		}
		catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verify_Account_Information_Tab_Newsletters_Go_Back_Link");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	
	/**
	 * Test Case - 039 - Verify "Contact Information" form input validations by tapping on edit & submitting blank form
	 * 
	 */

	@Test(priority = 39, enabled = false)
	public void verify_Account_Information_Tab_Newsletters_Save_Button() throws PageException{
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageLogin");
		ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);
		log.info("verification of Mandatory field validation message started");
		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","Address_Book_Newsletters_Functionality");
		String Navigate_To_Account_Information_Tab_Form_URL = inputdata[0][0];
		String Newsletters_Edit_Link_Locator =inputdata[0][1];
		String Newsletters_Page_Go_Back_Link=inputdata[0][4];
		String News_Letters_Subscription_CheckBox=inputdata[0][5];
		String Expected_Account_Information_Page_URL=inputdata[0][6];
		String Expected_Account_Information_Page_Title=inputdata[0][7];
		String Newsletters_Save_Button=inputdata[0][8];
		String Expected_Newsletters_Subscription_Successfully_Saved_Locator=inputdata[0][9];
		String Expected_Newsletters_Subscription_Successfully_Saved_Message=inputdata[0][10];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		try{
			commonMethods.clickElementbyXpath(webPage, Newsletters_Edit_Link_Locator, softAssert);
			WebElement Newsletters_Subscription_CheckBox = webPage.getDriver().findElement(By.xpath(inputdata[0][5]));
			if ((!(Newsletters_Subscription_CheckBox).isSelected()) && ((Newsletters_Subscription_CheckBox)).isEnabled())
			{
				commonMethods.clickElementbyXpath(webPage, News_Letters_Subscription_CheckBox, softAssert);
			}
			else{
				commonMethods.doubleClickElementbyXpath(webPage, News_Letters_Subscription_CheckBox, softAssert);
				}
			commonMethods.clickElementbyXpath(webPage, Newsletters_Save_Button, softAssert);
			String actualAccountInformationPageURL = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertEquals(actualAccountInformationPageURL, Expected_Account_Information_Page_URL,"Page url verification failed. Expected url : "+Expected_Account_Information_Page_URL+"Actual url : "+actualAccountInformationPageURL);
			String actualAccountInformationPageTitle = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(actualAccountInformationPageTitle, Expected_Account_Information_Page_Title,"Page title verification failed. Expected title : "+Expected_Account_Information_Page_Title+"Actual title : "+actualAccountInformationPageTitle);
			String ActualElementName =commonMethods.getTextbyXpath(webPage, Expected_Newsletters_Subscription_Successfully_Saved_Locator, softAssert);
			softAssert.assertEquals(ActualElementName, Expected_Newsletters_Subscription_Successfully_Saved_Message,"Page Content verification failed. Expected Page Content Expected Element Name : "+Expected_Newsletters_Subscription_Successfully_Saved_Message + "Actual Page Content Actual Element Name : "+ActualElementName);	
			softAssert.assertAll();
		}
		catch(Throwable e){
			mainPage.getScreenShotForFailure(webPage, "verify_Account_Information_Tab_Newsletters_Save_Button");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	
	/***********************************************************************UC006 ENDS*******************************************************************************************************************************************************************************************/	
	

	

	
	
/***********************************************************************************************************************************************************************************************/
	
	/**
	 * Test Case - 004 - Verify links on Login Page
	 * 
	 */

	@Test(priority = 4, enabled = false)
	public void verifylinksOnLoginPage() {
	log.info("**************Started verification of Links on Login Page *******************");
	String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifylinksOnLoginPage");
	ConnsSignInPage.verifyLinks(testdata);
	}

	

	/**
	 * Test Case - 08 - Verify Login functionality with Valid Input
	 * 
	 */

	@Test(priority = 8, enabled = false)
	public void verifyLoginFunctionality() {
	log.info("******Started verification of Login functionality with valid data ********");
	String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyLoginFunctionality");
	ConnsSignInPage.verifyLoginFunctionality(testdata);
	}
	
	

	
	
	/**
	 * Test Case - 021 - Verify Address Book Tab functionality
	 * 
	 */

	@Test(priority = 21, enabled = false)
	public void verifyAddressBookTab() throws PageException {
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyAddingNewAddress");

		log.info("************* Started verification of validation message on New Address Page after login************ ");
		ConnsSignInPage.verifyValidationMessageforNewAddressform(testdata);

		log.info("************* Started verification of New Address Page form with valid data after login************ ");
		String[][] data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyValidAddressform");
		ConnsSignInPage.verifyValidformSubmission(data);

		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifylinksonAddresspage");
		log.info("************* Clikcing on Save Address button on Add New Address form ************ ");
		ConnsSignInPage.verifyLinks(inputdata);
		log.info("************* Verifying Success Message once New addess has been added ************ ");
		String[][] testingdata = ExcelUtil.readExcelData(DataFilePath,"AccountSignINPage", "verifySuccessMessage");
		//ConnsSignInPage.verifyContent(testingdata);
		log.info("************* Verifying links on Address Book ************ ");
		String[][] QAdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifylinksonAddressBookPage");
		//ConnsSignInPage.verifyLinks(QAdata);

	}

	
	
	
	
	/**
	 * Test Case - 026 - Verify AccountInformationTab and Change Password functionality
	 * @throws PageException 
	 * 
	 */

	@Test(priority = 26, enabled = false)
	public void VerifyAccountInformationTab() throws PageException{
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage",
				"VerifyAccountInformationForm");
		log.info("*************Clicking on Account Information tab after login************ ");
		ConnsSignInPage.ClickonButton(testdata);
		log.info("************* Started Account Information form verification with valid and invalid data after login************ ");
		ConnsSignInPage.VerifyValidationMsg(testdata);
		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "VerifyChangePasswordfun");
		log.info("************* Started Change Password forn verification with valid and invalid data after login************ ");
		ConnsSignInPage.VerifyChangePasswordfun(inputdata);
		String[][] data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "LinkonAccountInformation");
		log.info("************* Started links verification in Account Infromation tab after login************ ");
		ConnsSignInPage.verifyLinks(data);
	}
	
	

	
	
	
	
	@Test(priority = 26, enabled = false)
	public void verify_Account_Information_Tab_Verify_Validation_Messages_Obsolete() throws PageException{

		List<String> brokenItems = new ArrayList<String>();
		SoftAssert softAssert = new SoftAssert();
		String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageTitle");
		ConnsSignInPage.verify_Account_DashBoard_Login(test_data,softAssert);
		log.info("verification of Mandatory field validation message started");
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","VerifyAccountInformationForm");
		String Navigate_To_Account_Information_Tab_Form_URL = testdata[0][12];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		String Expected_User_Name_Error_Message = testdata[0][10];
		String Expected_Password_Error_Message = testdata[1][10];
		String Expected_Email_ID_Error_Message = testdata[2][10];
		for (int r = 0; r < testdata.length; r++) {

			String FNLocator = testdata[r][1];
			String FNInput = testdata[r][2];
			String FNErrLocator = testdata[r][3];
			String LNLocator = testdata[r][4];
			String LNInput = testdata[r][5];
			String LNErrLocator = testdata[r][6];
			String EmailLocator = testdata[r][7];
			String EmailInput = testdata[r][8];
			String EmailErrLocator = testdata[r][9];
			String ExpectedValMsg = testdata[r][10];
			String ButtonLocator = testdata[r][11];
			
			
			/*String FNLocator = testdata[r][2];
			String FNInput = testdata[r][3];
			String FNErrLocator = testdata[r][4];
			String LNLocator = testdata[r][5];
			String LNInput = testdata[r][6];
			String LNErrLocator = testdata[r][7];
			String EmailLocator = testdata[r][8];
			String EmailInput = testdata[r][9];
			String Save_Confirmation_Msg_Locator = testdata[r][10];
			String ExpectedValMsg = testdata[r][11];
			String ButtonLocator = testdata[r][12];*/

			
			
			boolean runflag = true;
			if (r == 2) {
				runflag = false;

			}
			try {
				try{
					if (FNInput.equalsIgnoreCase("NA") && LNInput.equalsIgnoreCase("NA") && EmailInput.equalsIgnoreCase("NA")) {
						
						List<String> actualErrorMessage = ConnsSignInPage.verify_Account_Information_First_Name_Last_Name_Login_Validation(testdata,softAssert);
						SoftAssertor.assertEquals(actualErrorMessage.get(0), ExpectedValMsg);
						log.info("First_Name_Error_Message_Account_Information_Page : " +actualErrorMessage.get(0));
						log.info("ExpectedValMsg : " +ExpectedValMsg);
						SoftAssertor.assertEquals(actualErrorMessage.get(1), ExpectedValMsg);
						log.info("Last_Name_Error_Message_Account_Information_Page : " +actualErrorMessage.get(1));
						log.info("ExpectedValMsg : " +ExpectedValMsg);
						SoftAssertor.assertEquals(actualErrorMessage.get(2), ExpectedValMsg);
						log.info("Email_Address_Error_Message_Account_Information_Page : " +actualErrorMessage.get(2));
						log.info("ExpectedValMsg : " +ExpectedValMsg);
						
						
						
						
						String First_Name_Error_Message_Account_Information_Page = commonMethods.getTextbyXpath(webPage,FNErrLocator, softAssert);
						String Last_Name_Error_Message_Account_Information_Page = commonMethods.getTextbyXpath(webPage,LNErrLocator, softAssert);
						String Email_Address_Error_Message_Account_Information_Page = commonMethods.getTextbyXpath(webPage,EmailErrLocator, softAssert);
						SoftAssertor.assertEquals(First_Name_Error_Message_Account_Information_Page, ExpectedValMsg);
						log.info("First_Name_Error_Message_Account_Information_Page : " +First_Name_Error_Message_Account_Information_Page);
						log.info("ExpectedValMsg : " +ExpectedValMsg);
						
						SoftAssertor.assertEquals(Last_Name_Error_Message_Account_Information_Page, ExpectedValMsg);
						log.info("Last_Name_Error_Message_Account_Information_Page : " +Last_Name_Error_Message_Account_Information_Page);
						log.info("ExpectedValMsg : " +ExpectedValMsg);
						SoftAssertor.assertEquals(Email_Address_Error_Message_Account_Information_Page, ExpectedValMsg);
						log.info("Email_Address_Error_Message_Account_Information_Page : " +Email_Address_Error_Message_Account_Information_Page);
						log.info("ExpectedValMsg : " +ExpectedValMsg);
						System.out.println("First_Name_Error_Message_Account_Information_Page :"+First_Name_Error_Message_Account_Information_Page);
						System.out.println("Last_Name_Error_Message_Account_Information_Page :"+Last_Name_Error_Message_Account_Information_Page);
						System.out.println("Email_Address_Error_Message_Account_Information_Page :"+Email_Address_Error_Message_Account_Information_Page);
						System.out.println("ExpectedValMsg :"+ExpectedValMsg);
						//softAssert.assertAll();
					} else {
						List<String> actualErrorMessage = ConnsSignInPage.verify_Account_Information_First_Name_Last_Name_Login_Validation(testdata,softAssert);
		
						if (runflag) {
							
							List<String> actualErrorMessage1 = ConnsSignInPage.verify_Account_Information_First_Name_Last_Name_Login_Validation(testdata,softAssert);
							SoftAssertor.assertEquals(actualErrorMessage1.get(0), ExpectedValMsg);
							log.info("First_Name_Error_Message_Account_Information_Page : " +actualErrorMessage1.get(0));
							log.info("ExpectedValMsg : " +ExpectedValMsg);
							SoftAssertor.assertEquals(actualErrorMessage1.get(1), ExpectedValMsg);
							log.info("Last_Name_Error_Message_Account_Information_Page : " +actualErrorMessage1.get(1));
							log.info("ExpectedValMsg : " +ExpectedValMsg);
							SoftAssertor.assertEquals(actualErrorMessage1.get(2), ExpectedValMsg);
							log.info("Email_Address_Error_Message_Account_Information_Page : " +actualErrorMessage1.get(2));
							log.info("ExpectedValMsg : " +ExpectedValMsg);
							
							commonMethods.sendKeysbyXpath(webPage, EmailLocator, EmailInput, softAssert);
							commonMethods.clickElementbyXpath(webPage, ButtonLocator, softAssert);
							String Email_Address_Error_Message_Account_Information_Page = commonMethods.getTextbyXpath(webPage,EmailErrLocator, softAssert);
							log.info("Email_Address_Error_Message_Account_Information_Page : " +Email_Address_Error_Message_Account_Information_Page);
							log.info("ExpectedValMsg : " +ExpectedValMsg);
							SoftAssertor.assertEquals(Email_Address_Error_Message_Account_Information_Page, ExpectedValMsg);
							System.out.println("Email_Address_Error_Message_Account_Information_Page :"+Email_Address_Error_Message_Account_Information_Page);
							System.out.println("ExpectedValMsg :"+ExpectedValMsg);
						
						}
						softAssert.assertAll();
					}
				} catch (Exception e) {
					brokenItems.add(FNLocator + " " + e.getLocalizedMessage());
				}

				if (brokenItems.size() > 0) {
					Assert.fail("Link " + Arrays.deepToString(brokenItems.toArray()) + " are not working as expected");
				}

			}
			catch(Throwable e){
				mainPage.getScreenShotForFailure(webPage, "verify_Account_Information_Tab_Verify_Validation_Messages");
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}

		}
	}

	
	
/***********************************************************************************************************************************************************************************************/
	
	
	


}