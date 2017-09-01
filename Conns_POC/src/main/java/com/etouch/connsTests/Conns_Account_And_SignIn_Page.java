package com.etouch.connsTests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
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
import com.etouch.taf.core.datamanager.excel.annotations.IExcelDataFiles;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.taf.webui.selenium.WebPage;


@IExcelDataFiles(excelDataFiles = { "ConnsAccountSignINData=testData" })
public class Conns_Account_And_SignIn_Page extends BaseTest {
	private String testBedName;
	TestBed testBed;
	Path path;
	String DataFilePath;
	String testType;
	String currentTestBedName;
	static Log log = LogUtil.getLog(Conns_Account_And_SignIn_Page.class);
	Logger logger = Logger.getLogger(ConnsAccountAndSignInPage.class.getName());
	private String url, testEnv;
	private WebPage webPage;
	private ConnsAccountAndSignInPage ConnsSignInPage;
	private ConnsMainPage mainPage;
	CommonMethods commonMethods = new CommonMethods();
	static String platform;
	static String AbsolutePath = TafExecutor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	static String videoLocation = AbsolutePath.substring(0, AbsolutePath.indexOf("/target/classes/")).substring(1).concat("/src/test/resources/testdata/videos");

	/*** Prepare before class @throws Exception         the exception */
	@BeforeClass(alwaysRun = true)
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
					} else {
				}
				testEnv = System.getenv().get("Environment");
				log.info("testEnv is : " + System.getenv().get("Environment"));
				path = Paths.get(TestBedManager.INSTANCE.getProfile().getXlsDataConfig().get("testData"));
				DataFilePath = path.toAbsolutePath().toString().replace("Env", testEnv);
				log.info("DataFilePath After is : " + DataFilePath);
				platform = testBed.getPlatform().getName().toUpperCase();
				if (testType.equalsIgnoreCase("Web")) {
					log.info("videoLocation" + videoLocation.toString().replace("Env", testEnv));
				}
				url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL();
				String[][] test = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "PageURL");
				url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL() + test[0][0];
				synchronized (this) {
					webPage = new WebPage(context);
					ConnsSignInPage = new ConnsAccountAndSignInPage(url, webPage);
					mainPage = new ConnsMainPage(url, webPage);
					log.info(mainPage);
				}
				if (testType.equalsIgnoreCase("Web")) {
					log.info("Maximize Window in case of Desktop Browsers Only : ");
					webPage.getDriver().manage().window().maximize();
				}
			} catch (Exception e) {
				log.info("errr is " + e);
				SoftAssertor.addVerificationFailure(e.getMessage());
			}
		} catch (Exception e) {
			CommonUtil.sop("errr is for" + testBedName + " -----------" + e);
			SoftAssertor.addVerificationFailure(e.getMessage());
		}
	}

	/*** Test Case - 001 - Verify title and URL of Login page Verify Page Title : Page title should "Customer Login" : Page should load without any error Navigate to https://www.conns.com/customer/account/login/ & Mobile view :Tap on Hamberger Menu -> 'SIGN IN' */
	@Test(priority = 301, enabled = true)
	public void verify_Login_Page_Title() {
		log.info("************ Stated title verification of Login Page*******************");
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyLoginPageTitle");
			/*String Sign_In_Home_Page_Header_Link = testdata[0][3];
			commonMethods.clickElementbyXpath(webPage, Sign_In_Home_Page_Header_Link, softAssert);*/
			webPage.getCurrentUrl();// For Safari
			String actualPageUrl = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertTrue(actualPageUrl.contains(testdata[0][0]),"Page url verification failed. Expected url : " + testdata[0][0] + "Actual url   :   " + actualPageUrl);
			String actualPageTitle = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(actualPageTitle, testdata[0][1], "Page title verification failed. Expected title : " + testdata[0][1] + "Actual title :   " + actualPageTitle);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verifyLoginPageTitle");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/*** Test Case - 002 - Verify font on login page Verify Font Size & Style : Page font size & style should shown as per specification : Page should load without any error Navigate to https://www.conns.com/customer/account/login/ & Mobile view :Tap on Hamberger Menu -> 'SIGN IN'*/
	@Test(priority = 302, enabled = true, description = "Verify_Font_And_Size")
	public void verify_Font_And_Size_Login_Page() {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyLoginPageTitle");
			/*String Sign_In_Home_Page_Header_Link = testdata[0][3];
			commonMethods.clickElementbyXpath(webPage, Sign_In_Home_Page_Header_Link, softAssert);*/
			webPage.getCurrentUrl();// For Safari
			String[][] ExpectedFontValues = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","Verify_Font_And_Size_Login_Page");
			for (int i = 0; i < ExpectedFontValues.length; i++) {
				List<String> actualCssValues = commonMethods.getFontProperties(webPage, ExpectedFontValues[i][1],softAssert);
				if (testType.equalsIgnoreCase("Mobile")) {
					softAssert.assertTrue(actualCssValues.get(0).contains(ExpectedFontValues[i][5]),"CSS value verification failed for link " + ExpectedFontValues[i][0] + "Expected font Size  : " + ExpectedFontValues[i][5] + " Actual Font Size  : " + actualCssValues.get(0));
					softAssert.assertTrue(actualCssValues.get(1).contains(ExpectedFontValues[i][6]),"CSS value verification failed for link " + ExpectedFontValues[i][0] + "Expected font color : " + ExpectedFontValues[i][6] + " Actual font color : " + actualCssValues.get(1));
					softAssert.assertTrue(actualCssValues.get(2).toLowerCase().replaceAll("'", "").replaceAll("\"", "").contains((ExpectedFontValues[i][4]).toLowerCase()), "CSS value verification failed for link " + ExpectedFontValues[i][0]	+ "Expected font family : " + ExpectedFontValues[i][4] + " Actual font family : " + actualCssValues.get(2));
				} else {
					softAssert.assertTrue(actualCssValues.get(0).contains(ExpectedFontValues[i][2]), "CSS value verification failed for link " + ExpectedFontValues[i][0] + "Expected font Size  : " + ExpectedFontValues[i][2] +  " Actual Font Size   : " + actualCssValues.get(0));
					softAssert.assertTrue(actualCssValues.get(1).contains(ExpectedFontValues[i][3]), "CSS value verification failed for link " + ExpectedFontValues[i][0] + "Expected font color : " + ExpectedFontValues[i][3] +  " Actual font family : "	+ actualCssValues.get(1));
					softAssert.assertTrue(actualCssValues.get(2).toLowerCase().replaceAll("'", "").replaceAll("\"", "").contains((ExpectedFontValues[i][4]).toLowerCase()), "CSS value verification failed for link " + ExpectedFontValues[i][0] + "Expected font family : " + ExpectedFontValues[i][4] + " Actual font family : " + actualCssValues.get(2));
				}
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Font_And_Size_Login_Page");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	
	/*** Test Case - 005 - Verify content on Login Page Verify Page content : Page should load without any error : Page content & should shown as per specification Content Body section should contain two sections : "Registered Customers" section and "New Customers" section Navigate to https://www.conns.com/customer/account/login/ & Mobile view :Tap on Hamberger Menu -> 'SIGN IN'	 * @throws InterruptedException */
	@Test(priority = 303, enabled = true)
	public void verify_Content_On_Login_Page() throws InterruptedException {
		log.info("**************Started Content verification on Login Page *******************");
		List<String> content = new ArrayList<String>();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyContentOnLoginPage");
		String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyLoginPageTitle");
		SoftAssert softAssert = new SoftAssert();
		/*String Sign_In_Home_Page_Header_Link = test_data[0][3];
	      commonMethods.clickElementbyXpath(webPage, Sign_In_Home_Page_Header_Link, softAssert);*/
		webPage.getCurrentUrl();// For Safari
		try {
			for (int r = 0; r < testdata.length; r++) {
				String ActualElementName = commonMethods.getTextbyXpath(webPage, testdata[r][0], softAssert);
				String ExpectedElementName = testdata[r][1];
				softAssert.assertEquals(ActualElementName, ExpectedElementName,"Page Content verification failed. Expected Page Content Expected Element Name : " + ExpectedElementName + "Actual Page Content Actual Element Name : " + ActualElementName);
			}
			if (content.size() > 0) {
				Assert.fail("Content " + Arrays.deepToString(content.toArray()) + " are not working as expected");
			}
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Content_On_Login_Page");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/*** Test Case - 006 - Verify login functionality with Invalid Inputs Verify email address and password with invalid input Input invalid email address and Password / Mobile view :Tap on Hamberger Menu -> 'SIGN IN' Error message "Please enter a valid email address. For example johndoe@domain.com." should rendered and "Forgot Your Password" link should change to Red in color. Above error message is not displayed instead "Invalid login or Password" is displayed & "Forgot Your Password" link is remians in blue color.	 * @throws InterruptedException */
	@Test(priority = 304, enabled = true)
	public void verify_Login_Functionality_with_Invalid_Input() throws InterruptedException {
		log.info("******Started verification of Login functionality with invalid data ********");
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyLoginfuncInvalidInput");
		String ExpErrMsgEmail = testdata[0][3];
		String ExpErrMsgPwd = testdata[0][7];
		String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyLoginPageTitle");
		String Navigate_To_Account_Login_Form_URL = test_data[0][2];
		/*String Sign_In_Home_Page_Header_Link = test_data[0][3];
	      commonMethods.clickElementbyXpath(webPage, Sign_In_Home_Page_Header_Link, softAssert);*/
		webPage.getDriver().navigate().to(Navigate_To_Account_Login_Form_URL);
		webPage.getCurrentUrl();// For Safari
		try {
			List<String> actualErrorMessage = ConnsSignInPage.verify_Login_Functionality_with_Invalid_Input(testdata);
			softAssert.assertEquals(actualErrorMessage.get(0), ExpErrMsgEmail, "Login Functionality with Invalid Input verification failed For Invalid Email Address. Expected Email Address Error Message : "	+ ExpErrMsgEmail + "Actual Email Address Error Message : " + actualErrorMessage.get(0));
			softAssert.assertEquals(actualErrorMessage.get(1), ExpErrMsgPwd,   "Login Functionality with Invalid Input verification failed For Invalid Password . Expected Password Error Message : " + ExpErrMsgPwd + "Actual Password Error Message : " + actualErrorMessage.get(1));
			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "verifyLoginPageTitle");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/*** Test Case - 007 - Verify Login functionality with Blank Input Verify error message is rendered on both email address and password field when Login button is clicked with no inputs Click on Login button / Mobile view :Tap on Hamberger Menu -> 'SIGN IN' Error message "This is a required field." should rendered on both the fields @throws InterruptedException	 */
	@Test(priority = 305, enabled = true)
	public void verify_Login_Functionality_with_Blank_Input() throws InterruptedException {
		log.info("******Started verification of Login functionality with blank data ********");
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyLoginfuncInvalidInput");
		String ExpErrMsgEmail = testdata[1][3];
		String ExpErrMsgPwd = testdata[1][7];
		String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyLoginPageTitle");
		/*String Sign_In_Home_Page_Header_Link = test_data[0][3];
	      commonMethods.clickElementbyXpath(webPage, Sign_In_Home_Page_Header_Link, softAssert);*/
		webPage.getCurrentUrl();// For Safari
		try {
			List<String> actualErrorMessage = ConnsSignInPage.verify_Login_Functionality_with_Blank_Input(testdata);
			softAssert.assertEquals(actualErrorMessage.get(0), ExpErrMsgEmail,	"Login Functionality with Blank Input verification failed For Blank Email Address. Expected Email Address Error Message : "		+ ExpErrMsgEmail + "Actual Email Address Error Message : " + actualErrorMessage.get(0));
			softAssert.assertEquals(actualErrorMessage.get(1), ExpErrMsgPwd, "  Login Functionality with Blank Input verification failed For Blank Password . Expected Password Error Message : "	+ ExpErrMsgPwd + "Actual Password Error Message : " + actualErrorMessage.get(1));
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Login_Functionality_with_Blank_Input");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/*** Test Case - 008 - Verify WhatsThisOverlay on login page Verify question mark link associated with Remember Me is functional : Click on question mark (What's this) link / Mobile view :Tap on Hamberger Menu -> 'SIGN IN' "What is this " overlay should render  @throws InterruptedException	 */
	@Test(priority = 306, enabled = true)
	public void verify_Whats_This_Overlay_Rendered() throws PageException, InterruptedException {
		log.info("**************Started verification of What's this overlay Rendered on Login Page *******************");
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyWhatsThisOverlay");
		String Locator = testdata[0][0];
		String contentonoverlaylocator = testdata[0][1];
		String verify_Whats_This_Overlay_Rendered_Expected_Content = testdata[0][2];
		String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyLoginPageTitle");
		/*String Sign_In_Home_Page_Header_Link = test_data[0][3];
	      commonMethods.clickElementbyXpath(webPage, Sign_In_Home_Page_Header_Link, softAssert);*/
		webPage.getCurrentUrl();// For Safari
		try {
			Thread.sleep(5000);
			commonMethods.clickElementbyXpath(webPage, Locator, softAssert);
			String verify_Whats_This_Overlay_Rendered_Actual_Content = commonMethods.getTextbyCss(webPage,contentonoverlaylocator, softAssert);
			softAssert.assertEquals(verify_Whats_This_Overlay_Rendered_Actual_Content,verify_Whats_This_Overlay_Rendered_Expected_Content,"verify_Whats_This_Overlay_Rendered verification failed. Whats_This_Overlay_Rendered_Expected_Content : "		+ verify_Whats_This_Overlay_Rendered_Expected_Content	+ " Whats_This_Overlay_Rendered_Actual_Content : " + verify_Whats_This_Overlay_Rendered_Actual_Content);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Whats_This_Overlay_Rendered");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/*** Test Case - 009 - Verify WhatsThisOverlay on login page Verify "What's this " overlay is closed by clicking on Close button / Mobile view :Tap on Hamberger Menu -> 'SIGN IN' > ? Click on question mark link associated with Remember Me Click on CLOSE button What is this " overlay should render : What's this overlay should hide/close  */
	@Test(priority = 307, enabled = true)
	public void verify_Whats_This_Overlay_Close(ITestContext context) {
		log.info("**************Started verification of What's this overlay Rendered on Login Page *******************");
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyWhatsThisOverlay");
		String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyLoginPageTitle");
		String Locator = testdata[0][0];
		String contentonoverlaylocator = testdata[0][1];
		String verify_Whats_This_Overlay_Close_Expected_Content = testdata[0][2];
		String Close_Button_Locator_CSS = testdata[0][4];
		String Close_Button_Locator_Xpath = testdata[0][5];
		String Navigate_To_Account_Login_Form_URL = testdata[0][6];
		testBedName = context.getCurrentXmlTest().getAllParameters().get("testBedName");
		CommonUtil.sop("Test bed Name is " + testBedName);
		testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
		testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
		log.info("Test Type is : " + testType);
		platform = testBed.getPlatform().getName().toUpperCase();
		try {
			/*String Sign_In_Home_Page_Header_Link = test_data[0][3];
		      commonMethods.clickElementbyXpath(webPage, Sign_In_Home_Page_Header_Link, softAssert);*/
			webPage.getDriver().navigate().to(Navigate_To_Account_Login_Form_URL);
			webPage.getCurrentUrl();// For Safari
			Thread.sleep(3000);
			commonMethods.clickElementbyXpath(webPage, Locator, softAssert);
			String verify_Whats_This_Overlay_Close_Actual_Content = commonMethods.getTextbyCss(webPage,contentonoverlaylocator, softAssert);
			softAssert.assertEquals(verify_Whats_This_Overlay_Close_Actual_Content,verify_Whats_This_Overlay_Close_Expected_Content,"verify_Whats_This_Overlay_Close verification failed.  Whats_This_Overlay_Close_Expected_Content : " + verify_Whats_This_Overlay_Close_Expected_Content	+ " Whats_This_Overlay_Rendered_Actual_Content : "	+ verify_Whats_This_Overlay_Close_Actual_Content);
			Thread.sleep(2000);
			if (testBed.getTestBedName().equalsIgnoreCase("Safari")) {
				commonMethods.clickElementbyCss(webPage, Close_Button_Locator_CSS, softAssert);
			}
			else{
				Thread.sleep(2000);
			    commonMethods.clickElementbyXpath(webPage, Close_Button_Locator_Xpath, softAssert);
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "verify_Whats_This_Overlay_Close");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/*** Test Case - 0010 - Verify Remember Me Checkbox functionality on Login Page Verify Redirectional links : Click on 'Register' button / Mobile view :Tap on Hamberger Menu -> 'SIGN IN' > "REGISTER button : Page should navigate to https://www.conns.com/customer/account/create/ Verify Redirectional links : Click On "click here" link under Registered Customers section is functional / Mobile view :Tap on Hamberger Menu -> 'SIGN IN' : https://www.conns.com/pay_your_bill should load without any error Verify Redirectional links : Click on "Forgot your Password" link / Mobile view :Tap on Hamberger Menu -> 'SIGN IN' : Page should navigate to https://www.conns.com/customer/account/forgotpassword/ and should load without any error Verify Redirectional links : Click on "Click here" under New customers section / Mobile view :Tap on Hamberger Menu -> 'SIGN IN' >'Click here' under New customers section : Page should navigate to https://www.conns.com/pay_your_bill and should load without any error  @throws InterruptedException */
	@Test(priority = 308, enabled = true)
	public void verify_Redirectional_links() throws InterruptedException {
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifylinksOnLoginPage");
		List<String> brokenLinks = new ArrayList<String>();
		log.info("******Started verification of Redirectional_links functionality with valid data ********");
		SoftAssert softAssert = new SoftAssert();
		/*String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyLoginPageTitle");
		  String Sign_In_Home_Page_Header_Link = test_data[0][3];
	      commonMethods.clickElementbyXpath(webPage, Sign_In_Home_Page_Header_Link, softAssert);*/
		String Navigate_To_Account_Login_Form_URL = testdata[0][6];
		webPage.getDriver().navigate().to(Navigate_To_Account_Login_Form_URL);
		webPage.getCurrentUrl();// For Safari
		try {
			for (int r = 0; r < testdata.length; r++) {
				log.info("Verifying " + testdata[r][0]);
				String ParentElementLocator = testdata[r][1];
				String ChildElementLocator = testdata[r][2];
				String Expected_URL = testdata[r][3];
				String Expected_Element_Name = testdata[r][4];
				String Expected_Page_Title = testdata[r][5];
				log.info("Parent Locator is ..." + ParentElementLocator);
				Thread.sleep(2000);
				String Actual_Element_Name = commonMethods.getTextbyCss(webPage, ChildElementLocator, softAssert);
				Thread.sleep(2000);
				commonMethods.clickElementbyCssAndGetCurrentURL(webPage, ChildElementLocator, softAssert);
				String Actual_Page_Url = commonMethods.getPageUrl(webPage, softAssert);
				String Actual_Page_Title = commonMethods.getPageTitle(webPage, softAssert);
				softAssert.assertEquals(Actual_Page_Url, Expected_URL, "Redirectional_links Functionality with Invalid Input verification failed For Redirectional_URL. Expected Redirectional_URL  : " + Expected_URL + "Actual Redirectional_URL  : " + Actual_Page_Url);
				softAssert.assertTrue(Actual_Element_Name.equalsIgnoreCase(Expected_Element_Name),"Redirectional_links Functionality with Invalid Input verification failed For Redirectional_Parent_Element_Name . Expected Redirectional Parent_Element_Name : "	+ Expected_Element_Name + "Actual Redirectional_Parent_Element_Name : "	+ Actual_Element_Name);
				softAssert.assertEquals(Actual_Page_Title, Expected_Page_Title,"Redirectional_links Functionality with Invalid Input verification failed For Redirectional_Page_Title . Expected Redirectional_Page_Title  : " + Expected_Page_Title + "Actual Redirectional_Page_Title  : " + Actual_Page_Title);
				JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
				js.executeScript("javascript: setTimeout(\"history.go(-1)\", 2000)");// Used for Safari
				webPage.getDriver().getCurrentUrl();// Used for Safari
				softAssert.assertAll();
			}
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Redirectional_links");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
		if (brokenLinks.size() > 0) {
			Assert.fail("Link " + Arrays.deepToString(brokenLinks.toArray()) + " are not working as expected");
		}
	}

	/*** Test Case - 011 - Verify Remember Me Checkbox functionality on Login Page Verify 'Remember me' checkbox button is clickable : Click on 'Remember me' checkbox,"Click here" under New customers section,Mobile view :Tap on Hamberger Menu -> 'SIGN IN' Verify 'Remember me' checkbox button is clickable : Mobile view :Tap on Hamberger Menu -> 'SIGN IN' : 'Remember me ' should be unchecked */
	@Test(priority = 309, enabled = true)
	public void verify_Remember_Me_CheckBox() {
		log.info("******Started verification of RememberMeCheckBox functionality with valid data ********");
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyRememberMeFunctionality");
			//String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyLoginPageTitle");
			String Remember_me_CheckBox = testdata[0][0];
			String Expected_ToolTip_Text = testdata[0][1];
			String Navigate_To_Account_Login_Form_URL = testdata[0][2];
			/*String Sign_In_Home_Page_Header_Link = test_data[0][3];
		      commonMethods.clickElementbyXpath(webPage, Sign_In_Home_Page_Header_Link, softAssert);*/
			webPage.getDriver().navigate().to(Navigate_To_Account_Login_Form_URL);
			Thread.sleep(2000);
			ConnsSignInPage.verify_Remember_Me_Functionality(testdata);
			commonMethods.clickElementbyCssAndGetCurrentURL(webPage, Remember_me_CheckBox, softAssert);
			String Actual_ToolTip_Text = commonMethods.getAttributebyCss(webPage, Remember_me_CheckBox, "title",softAssert);
			SoftAssertor.assertEquals(Actual_ToolTip_Text, Expected_ToolTip_Text,"ToolTip_Text of the mouse pointer does not match");
			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "verify_Remember_Me_CheckBox");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/*** Test Case - 012 - Verify Login functionality with Valid Input Verify Registered Customers user login functionality : user should signed in and page should navigate to https://www.conns.com/customer/account/ And username should displayed on top header account section(top right) Enter registered user username and password and click on Login button & Mobile view :Tap on Hamberger Menu -> 'SIGN IN'-> Login button username -test123@mailinator.com password- Demo@1234 * @throws InterruptedException */
	@Test(priority = 310, enabled = true)
	public void verify_Login_Functionality_Registered_User(ITestContext context) throws InterruptedException {
		log.info("******Started verification of Login functionality with valid data ********");
		testBedName = context.getCurrentXmlTest().getAllParameters().get("testBedName");
		CommonUtil.sop("Test bed Name is " + testBedName);
		testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
		testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
		log.info("Test Type is : " + testType);
		platform = testBed.getPlatform().getName().toUpperCase();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyLoginFunctionality_Valid_Input");
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
		String Locator_1 = testdata[0][9];
		String Locator_2 = testdata[0][10];
		String Locator_3 = testdata[0][11];
		String Locator_4 = testdata[0][12];
		String Locator_5 = testdata[0][13];
		String Locator_6 = testdata[0][14];		
		String Expected_Mobile_Element_Name = testdata[0][16];
		String Account_Login_Page_Forgot_Password_Page_Link = testdata[0][7];
		String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyLoginPageTitle");
	    String Navigate_To_Account_Login_Form_URL = test_data[0][2];
	    /*String Sign_In_Home_Page_Header_Link = test_data[0][3];
	      commonMethods.clickElementbyXpath(webPage, Sign_In_Home_Page_Header_Link, softAssert);*/
		webPage.getCurrentUrl();// For Safari
		try {
			webPage.getDriver().navigate().to(Account_Login_Page_Forgot_Password_Page_Link);
			commonMethods.sendKeysbyXpath(webPage, EmailAddressLocator, EmailAddress, softAssert);
			commonMethods.sendKeysbyXpath(webPage, PasswordLocator, Password, softAssert);
			commonMethods.clickElementbyXpath(webPage, LogInButtonLocator, softAssert);
			CommonMethods.waitForWebElement(By.xpath(ChildElementLocator), webPage);
			Actual_Element_Name = commonMethods.getTextbyXpath(webPage, ChildElementLocator, softAssert);
			if (testType.equalsIgnoreCase("Mobile")) {
				softAssert.assertEquals(Actual_Element_Name, Expected_Mobile_Element_Name, " Redirectional_links Functionality with valid Input verification failed For Login_Functionality_Registered_User . Expected_Login_Functionality_Registered_User_Page_Element_Name  :  " + Expected_Mobile_Element_Name	+ "   Actual Login_Functionality_Registered_User_Page_Actual_Element_Name  : "	+ Actual_Element_Name);
			} else {
				softAssert.assertEquals(Actual_Element_Name, Expected_Element_Name,	" Redirectional_links Functionality with valid Input verification failed For Login_Functionality_Registered_User . Expected_Login_Functionality_Registered_User_Page_Element_Name  :  " + Expected_Element_Name 	+ " Actual Login_Functionality_Registered_User_Page_Actual_Element_Name  : " + Actual_Element_Name);
			}
			String Actual_Page_Title = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(Actual_Page_Title, Expected_Page_Title, "Redirectional_links Functionality with valid Input verification failed For Login_Functionality_Registered_User . Expected_Login_Functionality_Registered_User_Page_Title  : "	+ Expected_Page_Title	+ "  Actual Login_Functionality_Registered_User_Page_Actual_Page_Title  : "	+ Actual_Page_Title);
			Actual_Page_Url = commonMethods.getPageUrl(webPage, softAssert);
			if (testType.equalsIgnoreCase("Mobile")) {
				commonMethods.clickElementbyXpath(webPage, Locator_1, softAssert);
				commonMethods.clickElementbyXpath(webPage, Locator_2, softAssert);
				commonMethods.clickElementbyXpath(webPage, Locator_3, softAssert);
				commonMethods.clickElementbyXpath(webPage, Locator_4, softAssert);
			} else {
				commonMethods.clickElementbyXpath(webPage, Locator_5, softAssert);
				commonMethods.clickElementbyXpath(webPage, Locator_6, softAssert);
			}
			softAssert.assertTrue(Actual_Page_Url.contains(Expected_Page_URL),"verify_Login_Functionality_Registered_User  with valid Input verification failed For Login_Functionality_Registered_User. Expected Login_Functionality_Registered_User_Page_URL  : "	+ Expected_Page_URL + "Actual Login_Functionality_Registered_User_Page_URL  : "	+ Actual_Page_Url);
			webPage.getDriver().navigate().to(Navigate_To_Account_Login_Form_URL);
			webPage.getCurrentUrl();// For Safari
			Thread.sleep(7000);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Login_Functionality_Registered_User");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	/****************************************************** UC 01 END **********************************************************************************************************************************************/

	/****************************************************** UC 02 STARTS **********************************************************************************************************************************************/
	/**Test Case - 013 - Verify forgot password page title @throws PageException  Verify Page Title : Page should load without any error : Page title should "Forgot Your Password" Navigate to  https://www.conns.com/customer/account/forgotpassword/ &   Mobile view :Tap on Hamberger Menu -> 'SIGN IN'-> Forgot Your Password? link */
	@Test(priority = 311, enabled = true)
	public void verify_Forgot_Password_Page_Title() throws PageException {
		log.info("******Started verification of title on Forgot Password Page ********");
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "ForgotPasswordPageTitle");
		SoftAssert softAssert = new SoftAssert();
		String Expected_Forgot_Password_Page_URL = testdata[0][0];
		String Expected_Forgot_Password_Page_Title = testdata[0][1];
		String Account_Login_Page_Forgot_Password_Page_Link = testdata[0][2];
		//String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyLoginPageTitle");
		/*String Sign_In_Home_Page_Header_Link = test_data[0][3];
	      commonMethods.clickElementbyXpath(webPage, Sign_In_Home_Page_Header_Link, softAssert);*/
		try {
			commonMethods.clickElementbyXpath(webPage, Account_Login_Page_Forgot_Password_Page_Link, softAssert);
  			String Actual_Forgot_Password_Page_URL = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertTrue(Actual_Forgot_Password_Page_URL.contains(Expected_Forgot_Password_Page_URL),"Page url verification failed. Expected url : " + Expected_Forgot_Password_Page_URL  + "Actual url : " + Actual_Forgot_Password_Page_URL);
			String Actual_Forgot_Password_Page_Title = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(Actual_Forgot_Password_Page_Title, Expected_Forgot_Password_Page_Title,	"Page title verification failed. Expected title : " + Expected_Forgot_Password_Page_Title  + "Actual title : " + Actual_Forgot_Password_Page_Title);
			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "verify_Forgot_Password_Page_Title");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/*** Test Case - 014 - Verify Fonts and Size on Forgot Password Page Verify Font Size & Style : Page font size & style should shown as per specification Navigate to https://www.conns.com/customer/account/forgotpassword/ & Mobile view :Tap on Hamberger Menu -> 'SIGN IN'-> Forgot Your Password? link Page should load without any error @throws InterruptedException	 */
	@Test(priority = 312, enabled = true, description = "Verify_Font_And_Size")
	public void verify_Font_and_Size_On_Forgot_Password_Page() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Font_And_Size_On_Forgot_Password_Page");
		//String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyLoginPageTitle");
		String Account_Login_Page_Forgot_Password_Page_Link = testdata[0][7];
		String Forgot_Password_Link = testdata[0][8];
		/*String Sign_In_Home_Page_Header_Link = test_data[0][3];
	      commonMethods.clickElementbyXpath(webPage, Sign_In_Home_Page_Header_Link, softAssert);*/
		webPage.getDriver().navigate().to(Account_Login_Page_Forgot_Password_Page_Link);
		webPage.getCurrentUrl();// For Safari
		try {
			commonMethods.clickElementbyXpath(webPage, Forgot_Password_Link, softAssert);
			String[][] ExpectedFontValues = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verify_Font_And_Size_On_Forgot_Password_Page");
			for (int i = 0; i < ExpectedFontValues.length; i++) {
				List<String> actualCssValues = commonMethods.getFontProperties(webPage, ExpectedFontValues[i][1],softAssert);
				if (testType.equalsIgnoreCase("Mobile")) {
					softAssert.assertTrue(actualCssValues.get(0).contains(ExpectedFontValues[i][5]), "CSS value verification failed for link " + ExpectedFontValues[i][0]  + "Expected font Size : "  + ExpectedFontValues[i][5] + " Actual Font Size : "	+ actualCssValues.get(0));
					softAssert.assertTrue(actualCssValues.get(1).contains(ExpectedFontValues[i][6]), "CSS value verification failed for link " + ExpectedFontValues[i][0]  + "Expected font color : " + ExpectedFontValues[i][6] + " Actual font family : "	+ actualCssValues.get(1));
					softAssert.assertTrue(actualCssValues.get(2).toLowerCase().replaceAll("'", "").replaceAll("\"", "").contains((ExpectedFontValues[i][4]).toLowerCase()),	 "CSS value verification failed for link " + ExpectedFontValues[i][0] + "Expected font family : " + ExpectedFontValues[i][4] + " Actual font family : " + actualCssValues.get(2));
				} else {
					softAssert.assertTrue(actualCssValues.get(0).contains(ExpectedFontValues[i][2]), "CSS value verification failed for link " + ExpectedFontValues[i][0]   + "Expected font Size : " + ExpectedFontValues[i][2] + " Actual Font Size    : "	+ actualCssValues.get(0));
					softAssert.assertTrue(actualCssValues.get(1).contains(ExpectedFontValues[i][3]), "CSS value verification failed for link " + ExpectedFontValues[i][0]   + "Expected font color : " + ExpectedFontValues[i][3] + " Actual font family : "	+ actualCssValues.get(1));
					softAssert.assertTrue(actualCssValues.get(2).toLowerCase().replaceAll("'", "").replaceAll("\"", "")	.contains((ExpectedFontValues[i][4]).toLowerCase()),  "CSS value verification failed for link " + ExpectedFontValues[i][0] + "Expected font family : " + ExpectedFontValues[i][4] + " Actual font family : " + actualCssValues.get(2));
				}
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Font_and_Size_On_Forgot_Password_Page");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/*** Test Case - 017 - Verify ForgotPwdPage Content Verify Page content	 Navigate to https://www.conns.com/customer/account/forgotpassword/ &	 * Mobile view :Tap on Hamberger Menu -> 'SIGN IN'-> Forgot Your Password? link Page should load without any error  @throws InterruptedException	 */
	@Test(priority = 315, enabled = true)
	public void verify_Forgot_Password_Page_Content() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		log.info("******Started verification of content on Forgot Password Page *********");
		List<String> content = new ArrayList<String>();
		String[][] input_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyLoginPageTitle");
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifycontentonforgotPwdPage");
		String Forgot_Password_Link = testdata[0][2];
		String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Font_And_Size_On_Forgot_Password_Page");
		/*String Sign_In_Home_Page_Header_Link = test_data[0][3];
	      commonMethods.clickElementbyXpath(webPage, Sign_In_Home_Page_Header_Link, softAssert);*/
		String Account_Login_Page_Forgot_Password_Page_Link = test_data[0][7];
		webPage.getDriver().navigate().to(Account_Login_Page_Forgot_Password_Page_Link);
		webPage.getCurrentUrl();// For Safari
		
		try {
			commonMethods.clickElementbyXpath(webPage, Forgot_Password_Link, softAssert);
			for (int r = 0; r < testdata.length; r++) {
				String Actual_Element_Name = commonMethods.getTextbyXpath(webPage, testdata[r][0], softAssert);
				String Expected_Element_Name = testdata[r][1];
				softAssert.assertEquals(Actual_Element_Name, Expected_Element_Name,	"  Forgot_Password_Page_Content Functionality verification failed For Forgot_Password_Page_Content_Parent_Element_Name . Expected_Forgot_Password_Page_Content_Element_Namee : " + Expected_Element_Name + "Actual_Forgot_Password_Page_Content_Element_Name : "	+ Actual_Element_Name);
			}
			if (content.size() > 0) {
				Assert.fail("Content " + Arrays.deepToString(content.toArray()) + " are not working as expected");
			}
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Forgot_Password_Page_Content");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/*** Test Case - 018 - Verify ForgotPwdPage_Submit_Button functionality with Blank Input Verify error message when submit button is clicked without any inputs Click on Submit button : Mobile view :Tap on Hamberger Menu -> 'SIGN IN'-> Forgot Your Password? link Error message as 'This is a required field.' should render .*/
	@Test(priority = 316, enabled = true)
	public void verify_Forgot_Password_Page_Submit_Button_Blank_Input() throws PageException {
		log.info("******Started verification of Error Message content on Forgot Password Page Submit Button with Blank Input *********");
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","forgotPasswordFucwithInvaliddata");
		String Forgot_Password_Link = testdata[0][5];
		String Forgot_Passowrd_Submit_Button_Locator = testdata[0][3];
		String Forgot_Password_Error_Message_Locator = testdata[0][2];
		String Expected_Forgot_Password_Error_Message = testdata[0][4];
		String Account_Login_Page_Forgot_Password_Page_Link = testdata[0][6];
		String[][] input_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyLoginPageTitle");
		/*String Sign_In_Home_Page_Header_Link = test_data[0][3];
	      commonMethods.clickElementbyXpath(webPage, Sign_In_Home_Page_Header_Link, softAssert);*/
		try {
			webPage.getDriver().navigate().to(Account_Login_Page_Forgot_Password_Page_Link);
			webPage.getCurrentUrl();// For Safari
			commonMethods.clickElementbyXpath(webPage, Forgot_Password_Link, softAssert);
			commonMethods.clickElementbyXpath(webPage, Forgot_Passowrd_Submit_Button_Locator, softAssert);
			String Actual_Forgot_Password_Error_Message = commonMethods.getTextbyXpath(webPage,	Forgot_Password_Error_Message_Locator, softAssert);
			softAssert.assertEquals(Actual_Forgot_Password_Error_Message, Expected_Forgot_Password_Error_Message,"Forgot Password Blank Input Error Message Functionality Failed For Blank Email Address Input. Expected_Forgot_Password_Error_Message  : "	+ Expected_Forgot_Password_Error_Message + "Actual_Forgot_Password_Error_Message : " + Actual_Forgot_Password_Error_Message);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Forgot_Password_Page_Submit_Button_Blank_Input");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/*** Test Case - 019 - Verify Forgot_Password_Page_GoBack_Link functionality Verify 'Go back' link Click on 'Go back' link : Mobile view :Tap on Hamberger Menu -> 'SIGN IN'-> Forgot Your Password?->'Go back' link Page should navigate to https://www.conns.com/customer/account/login/ */
	@Test(priority = 317, enabled = true)
	public void verify_Forgot_Password_Page_Go_Back_Link() throws PageException {
		log.info("******Started verification of Go_Back_Link on Forgot Password Page ********");
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyLinksOnforgotPwdPage");
		List<String> brokenLinks = new ArrayList<String>();
		log.info("******Started verification of Go_Back_Link functionality with valid data ********");
		SoftAssert softAssert = new SoftAssert();
		try {
			log.info("Verifying " + testdata[0][0]);
			String ParentElementLocator = testdata[0][1];
			String Go_Back_Link = testdata[0][2];
			String Expected_Forgot_Password_Page_Go_Back_Link_Page_URL = testdata[0][3];
			String Expected_Forgot_Password_Page_Go_Back_Link_Element_Name = testdata[0][4];
			String Expected_Forgot_Password_Page_Go_Back_Link_Page_Title = testdata[0][5];
			String Forgot_Password_Link = testdata[1][2];
			String Login_Page_Element = testdata[2][2];
			String Expected_Login_Page_URL = testdata[2][3];
			String Expected_Login_Page_Element_Name = testdata[2][4];
			String Expected_Login_Page_Title = testdata[2][5];
			String Account_Login_Page_Forgot_Password_Page_Link = testdata[0][6];
			log.info("Parent Locator is ..." + ParentElementLocator);
			webPage.getDriver().navigate().to(Account_Login_Page_Forgot_Password_Page_Link);
			webPage.getCurrentUrl();// For Safari
			commonMethods.clickElementbyCss(webPage, Forgot_Password_Link, softAssert);
			String Actual_Forgot_Password_Page_Go_Back_Link_Element_Name = commonMethods.getTextbyCss(webPage,Go_Back_Link, softAssert);
			String Actual_Forgot_Password_Page_Go_Back_Link_Page_URL = commonMethods.getPageUrl(webPage,softAssert);
			String Actual_Forgot_Password_Page_Go_Back_Link_Page_Title = commonMethods.getPageTitle(webPage,softAssert);
			softAssert.assertEquals(Actual_Forgot_Password_Page_Go_Back_Link_Page_URL,Expected_Forgot_Password_Page_Go_Back_Link_Page_URL,"Forgot_Password_Page_Go_Back_Link Functionality with verification failed For Forgot_Password_Page_Go_Back_Link_Page_URL. Expected_Forgot_Password_Page_Go_Back_Link_Page_URL  : "	+ Expected_Forgot_Password_Page_Go_Back_Link_Page_URL + "Actual_Forgot_Password_Page_Go_Back_Link_Page_URL  : "	+ Actual_Forgot_Password_Page_Go_Back_Link_Page_URL);
			softAssert.assertEquals(Actual_Forgot_Password_Page_Go_Back_Link_Element_Name,Expected_Forgot_Password_Page_Go_Back_Link_Element_Name, "  Forgot_Password_Page_Go_Back_Link Functionality with verification failed For Forgot_Password_Page_Go_Back_Link_Element_Name . Expected_Forgot_Password_Page_Go_Back_Link_Element_Name : "	+ Expected_Forgot_Password_Page_Go_Back_Link_Element_Name + "Actual_Forgot_Password_Page_Go_Back_Link_Element_Name : " + Actual_Forgot_Password_Page_Go_Back_Link_Element_Name);
			softAssert.assertEquals(Actual_Forgot_Password_Page_Go_Back_Link_Page_Title,Expected_Forgot_Password_Page_Go_Back_Link_Page_Title, "  Forgot_Password_Page_Go_Back_Link Functionality with verification failed For Forgot_Password_Page_Go_Back_Link_Page_Title . Expected_Forgot_Password_Page_Go_Back_Link_Page_Title  : " + Expected_Forgot_Password_Page_Go_Back_Link_Page_Title + "Actual_Forgot_Password_Page_Go_Back_Link_Page_Title  : " + Actual_Forgot_Password_Page_Go_Back_Link_Page_Title);
			//CommonMethods.waitForWebElement(By.cssSelector(Go_Back_Link), webPage);
			commonMethods.clickElementbyCss(webPage, Go_Back_Link, softAssert);
			String Actual_Login_Page_Element_Name = commonMethods.getTextbyXpath(webPage, Login_Page_Element,softAssert);
			String Actual_Login_Page_Url = commonMethods.getPageUrl(webPage, softAssert);
			String Actual_Login_Page_Title = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertTrue(Actual_Login_Page_Url.contains(Expected_Login_Page_URL)," Login_Page_URL Functionality with verification failed For Login_Page_URL. Expected_Login_Page_URL  : "	+ Expected_Login_Page_URL + "Actual_Login_Page_Url  : " + Actual_Login_Page_Url);
			softAssert.assertEquals(Actual_Login_Page_Element_Name, Expected_Login_Page_Element_Name,"  Login_Page_Element Functionality with verification failed For Expected_Login_Page_Element_Name . Expected_Login_Page_Element_Name : " + Expected_Login_Page_Element_Name +  "    Actual_Login_Page_Element_Name : "	+ Actual_Login_Page_Element_Name);
			softAssert.assertEquals(Actual_Login_Page_Title, Expected_Login_Page_Title,	"  Login_Page_Title Functionality with verification failed For Login_Page_Title . Expected_Login_Page_Title  : " + Expected_Login_Page_Title + "Actual_Login_Page_Title  : " + Actual_Login_Page_Title);
		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "verify_Forgot_Password_Page_Go_Back_Link");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
			e.printStackTrace();
		}
		if (brokenLinks.size() > 0) {
			Assert.fail("Link " + Arrays.deepToString(brokenLinks.toArray()) + " are not working as expected");
		}
	}

	/*** Test Case - 020 - Verify Forgot Password Page functionality with Invalid Input Verify email address field validation with some invalid email address Input invalid email address : Mobile view :Tap on Hamberger Menu -> 'SIGN IN'-> Forgot Your Password? link Error message 'Please enter a valid email address. For example johndoe@domain.com.' should render */
	@Test(priority = 318, enabled = true)
	public void verify_Forgot_Password_Function_with_Invalid_Email_ID() throws PageException {
		log.info("******Started verification of Forgot Password functionality with Invalid and valid data ********");
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","forgotPasswordFucwithInvaliddata");
		String Forgot_Password_Link = testdata[0][5];
		String Forgot_Password_Email_Address_Locator = testdata[1][0];
		String Forgot_Password_Email_Address_Data = testdata[1][1];
		String Forgot_Password_Email_Address_Error_Message_Locator = testdata[1][2];
		String Forgot_Passowrd_Submit_Button_Locator = testdata[1][3];
		String Expected_Forgot_Password_Error_Message = testdata[1][4];
		String Account_Login_Page_Forgot_Password_Page_Link = testdata[0][6];
		try {
			webPage.getDriver().navigate().to(Account_Login_Page_Forgot_Password_Page_Link);
			webPage.getCurrentUrl();// For Safari
			commonMethods.clickElementbyXpath(webPage, Forgot_Password_Link, softAssert);
			commonMethods.sendKeysbyXpath(webPage, Forgot_Password_Email_Address_Locator,Forgot_Password_Email_Address_Data, softAssert);
			commonMethods.clickElementbyXpath(webPage, Forgot_Passowrd_Submit_Button_Locator, softAssert);
			String Actual_Forgot_Password_Email_Address_Error_Message_Locator = commonMethods.getTextbyXpath(webPage,Forgot_Password_Email_Address_Error_Message_Locator, softAssert);
			softAssert.assertEquals(Actual_Forgot_Password_Email_Address_Error_Message_Locator,	Expected_Forgot_Password_Error_Message,	"Forgot_Password_Function_with_Invalid_Data Functionality Failed For Invalid Email Address Input. Expected_Forgot_Password_Email_Address_Error_Message  : "	+ Expected_Forgot_Password_Error_Message + "Actual_Forgot_Password_Error_Message : " + Actual_Forgot_Password_Email_Address_Error_Message_Locator);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Forgot_Password_Function_with_Invalid_Email_ID");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/*** Test Case - 021- Verify Forgot Password Page functionality with Valid Input Verify forget password functionality with valid email address : Input valid email address : Mobile view :Tap on Hamberger Menu -> 'SIGN IN'-> Forgot Your Password? link Page should navigate to https://www.conns.com/customer/account/login/ and Message 'If there is an account associated with test@gmail.com you will receive an email with a link to reset your password' should render on top of page , */
	@Test(priority = 319, enabled = true)
	public void verify_Forgot_Password_Function_with_Valid_Email_ID() throws PageException {
		log.info("******Started verification of Forgot Password functionality with Invalid and valid data ********");
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","forgotPasswordFucwithInvaliddata");
		String Forgot_Password_Link = testdata[0][5];
		String Forgot_Password_Email_Address_Locator = testdata[2][0];
		String Forgot_Password_Email_Address_Data = testdata[2][1];
		String Forgot_Password_Email_Address_Error_Message_Locator = testdata[2][2];
		String Forgot_Passowrd_Submit_Button_Locator = testdata[2][3];
		String Expected_Forgot_Password_Valid_Email_Address_Confirmation_Message = testdata[2][4];
		String Account_Login_Page_Forgot_Password_Page_Link = testdata[0][6];
		try {
			webPage.getDriver().navigate().to(Account_Login_Page_Forgot_Password_Page_Link);
			webPage.getCurrentUrl();// For Safari
			commonMethods.clickElementbyXpath(webPage, Forgot_Password_Link, softAssert);
			commonMethods.sendKeysbyXpath(webPage, Forgot_Password_Email_Address_Locator,Forgot_Password_Email_Address_Data, softAssert);
			commonMethods.clickElementbyXpath(webPage, Forgot_Passowrd_Submit_Button_Locator, softAssert);
			String Actual_Forgot_Password_Valid_Email_Address_Confirmation_Message = commonMethods.getTextbyXpath(webPage, Forgot_Password_Email_Address_Error_Message_Locator, softAssert);
			softAssert.assertEquals(Actual_Forgot_Password_Valid_Email_Address_Confirmation_Message,Expected_Forgot_Password_Valid_Email_Address_Confirmation_Message,"Forgot Password Blank Input Error Message Functionality Failed For Blank Email Address Input. Expected_Forgot_Password_Error_Message  : "+ Expected_Forgot_Password_Valid_Email_Address_Confirmation_Message	+ "Actual_Forgot_Password_Valid_Email_Address_Confirmation_Message : "	+ Actual_Forgot_Password_Valid_Email_Address_Confirmation_Message);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Forgot_Password_Function_with_Valid_Email_ID");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/*** Test Case - 024 - Verify links in Account Information section Verify page title : Page title should 'My Account' Verify content section on account dashboard page Verify "Account Information" section on page : Verify contact information,newsletter subscription, primary billing address & primary shipping address details of customer : Verify contact information,newsletters subscription, primary billing address & primary shipping address details of customer Verify "Edit" link for "Newsletters" : click on "Edit" link for "Contact Newsletters" It should redirect to account information form page : https://www.conns.com/customer/account/edit/ Verify "Change Password" link : click on "Change Password" link : It should redirect to edit account information form page : https://www.conns.com/customer/account/edit/changepass/1/ Verify "Edit Address" link for "Primary Billing Address" : click on "Edit Address" link for "Primary Billing Address" : It should redirect to contact information form : https://www.conns.com/customer/address/edit/ Verify "Edit Address" link for "Primary Shipping Address": click on "Edit Address" link for "Primary Shipping Address" :It should redirect to contact information form : https://www.conns.com/customer/address/edit/ but getting redirected to https://www.conns.com/customer/address/edit/id/6296645/ Verify "Manage Addresses" link for "address Book" section : click on "Manage Addresses" link : It should redirect to contact information form : https://www.conns.com/customer/address/edit/	 * @throws InterruptedException * @throws InterruptedException */
	@Test(priority = 320, enabled = true)
	public void verify_Links_On_Account_DashBoard_Tab() throws InterruptedException {
		log.info("******Started verification of Links in Account Dashborad tab after login ********");
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageTitle");
		ConnsSignInPage.verify_Account_DashBoard_Login(testdata, softAssert);
		String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyLinksOnAccountInformationSec");
		String Navigate_To_Account_Information_Tab_Form_URL = test_data[0][6];
		//webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		webPage.getCurrentUrl();// For Safari
		for (int r = 0; r < test_data.length; r++) {
			String Page_Expected_URL = test_data[r][3];
			String Page_Expected_Element_Name = test_data[r][4];
			String Page_Expected_Title = test_data[r][4];
			try {
				List<String> Page_URL_Title_Data = ConnsSignInPage.verify_Links_Account_Tab(test_data);
				softAssert.assertEquals(Page_URL_Title_Data.get(1), Page_Expected_Title,"Account Information DashBoard Tab verification failed For Page Elements.         Expected_Page_Title     :  " + Page_Expected_Title         +  "   Actual_Page_Element : " + Page_URL_Title_Data.get(1));
				softAssert.assertTrue(Page_URL_Title_Data.get(2).contains(Page_Expected_URL),"  Account Information DashBoard Tab verification failed For Page URL .      Expected_Page_URL       :  " + Page_Expected_URL           +  "   Actual_Page_URL: " + Page_URL_Title_Data.get(2));
				softAssert.assertEquals(Page_URL_Title_Data.get(0), Page_Expected_Element_Name,	"  Account Information DashBoard Tab verification failed For Page Title . Expected_Page_Element   :  " + Page_Expected_Element_Name  +  "   Actual_Page_Title : " + Page_URL_Title_Data.get(0));
				softAssert.assertAll();
			} catch (Throwable e) {
				e.printStackTrace();
				mainPage.getScreenShotForFailure(webPage, "verify_Account_DashBoard_Page_Content");
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
		}
	}

	/*** Test Case - 022 - Verify Page Title in Account Information section* @throws InterruptedException **/
	@Test(priority = 321, enabled = true)
	public void verify_Account_DashBoard_Page_Title() throws InterruptedException {
		log.info("******Started verification of content on Account Dashborad tab after login ********");
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageTitle");
		/* ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);*/		
		String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyLinksOnAccountInformationSec");
		String Navigate_To_Account_Information_Tab_Form_URL = test_data[0][6];
		String Expected_Account_Dashboard_Page_URL = testdata[0][0];
		String Expected_Account_Dashboard_Page_Title = testdata[0][1];
		//webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		webPage.getCurrentUrl();// For Safari
		try {
			log.info(" Actual_Account_Dashboard_Page_URL Validation Starts  :  ****************************** "	);
			String Actual_Account_Dashboard_Page_URL = commonMethods.getPageUrl(webPage, softAssert);
			//softAssert.assertTrue(actualPageUrl.contains(Expected_Contact_Information_Page_URL),"Page url verification failed. Expected url : " + Expected_Contact_Information_Page_URL		+ "Actual url   : " + actualPageUrl);
			softAssert.assertTrue(Actual_Account_Dashboard_Page_URL.contains(Expected_Account_Dashboard_Page_URL),	"Page url verification failed.        Expected url :  " + Expected_Account_Dashboard_Page_URL	            +     "  Actual url  :  " + Actual_Account_Dashboard_Page_URL);
			log.info(" Actual_Account_Dashboard_Page_URL   :****************************** "	+ Actual_Account_Dashboard_Page_URL);
			log.info(" Expected_Account_Dashboard_Page_URL :****************************** "	+ Expected_Account_Dashboard_Page_URL);
			String Actual_Account_Dashboard_Page_Title = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(Actual_Account_Dashboard_Page_Title, Expected_Account_Dashboard_Page_Title,	"Page title verification failed. Expected title :  " + Expected_Account_Dashboard_Page_Title	    +     "Actual title :        " + Actual_Account_Dashboard_Page_Title);
			log.info(" Actual_Account_Dashboard_Page_Title   :****************************** "	+ Actual_Account_Dashboard_Page_Title);
			log.info(" Expected_Account_Dashboard_Page_Title :****************************** "	+ Expected_Account_Dashboard_Page_Title);
			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "verify_Account_DashBoard_Page_Title");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/*** Test Case - 023 - Verify content in Account Information section** @throws InterruptedException  */
	@Test(priority = 322, enabled = true)
	public void verify_Account_DashBoard_Content() throws InterruptedException {
		log.info("******Started verification of content on Account Dashborad tab after login ********");
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageTitle");
		String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyAccountPageContent");
		String Navigate_To_Account_Information_Tab_Form_URL = test_data[0][18];
		// ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);
		//webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		webPage.getCurrentUrl();// For Safari
		try {
			List<String> content = new ArrayList<String>();
			for (int r = 0; r < test_data.length; r++) {
				try {
					Thread.sleep(1000);
					String Actual_Element_Name_On_Account_Page_For_Page_Content_Verification = commonMethods.getTextbyXpath(webPage, test_data[r][20], softAssert);
					String Expected_Element_Name_On_Account_Page_For_Page_Content_Verification = test_data[r][19];
					softAssert.assertEquals(Actual_Element_Name_On_Account_Page_For_Page_Content_Verification,	Expected_Element_Name_On_Account_Page_For_Page_Content_Verification,"Page Element Content verification failed. Expected_Element_Name_On_Account_Page_For_Page_Content_Verification : "		+ Expected_Element_Name_On_Account_Page_For_Page_Content_Verification	+ "Actual_Element_Name_On_Account_Page_For_Page_Content_Verification :       "		+ Actual_Element_Name_On_Account_Page_For_Page_Content_Verification);
					log.info("Actual_Element_Name_On_Account_Page_For_Page_Content_Verification :  *****************************  "				+ Actual_Element_Name_On_Account_Page_For_Page_Content_Verification);
					log.info("Expected_Element_Name_On_Account_Page_For_Page_Content_Verification :*****************************  "  			+ Expected_Element_Name_On_Account_Page_For_Page_Content_Verification);
					softAssert.assertAll();
				} catch (Throwable e) {
					content.add(testdata[r][1] + " " + e.getLocalizedMessage());
				}
			}
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Account_DashBoard_Page_Content");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/*** Test Case - 025 - Verify "Credit Applications" section on My Account page It should shows the applied credit status/details*/
	@Test(priority = 323, enabled = true)
	public void Verify_Credit_Application_Sec() {
		log.info("******Started verification of credit Application sec on Account Dashborad tab after login ********");
		SoftAssert softAssert = new SoftAssert();
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","Verify_Credit_Application_Section");
		String Navigate_To_Account_Information_Tab_Form_URL = testdata[0][2];
		/*String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageTitle");
		  ConnsSignInPage.verify_Account_DashBoard_Login(test_data,softAssert);*/
		for (int r = 0; r < testdata.length; r++) {
			try {
				//webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
				webPage.getCurrentUrl();// For Safari
				String Actual_Content_On_Account_Information_Credit_Application_Section_For_Credit_Status_Content_Verification = commonMethods.getTextbyXpath(webPage, testdata[r][0], null);
				String Expected_Content_On_Account_Information_Credit_Application_Section_For_Credit_Status_Content_Verification = testdata[r][1];
				softAssert.assertEquals(Actual_Content_On_Account_Information_Credit_Application_Section_For_Credit_Status_Content_Verification,Expected_Content_On_Account_Information_Credit_Application_Section_For_Credit_Status_Content_Verification,	"Page Element Content verification failed. Expected_Content_On_Account_Information_Credit_Application_Section_For_Credit_Status_Content_Verification : "	+ Expected_Content_On_Account_Information_Credit_Application_Section_For_Credit_Status_Content_Verification + "Actual_Content_On_Account_Information_Credit_Application_Section_For_Credit_Status_Content_Verification :  "	+ Actual_Content_On_Account_Information_Credit_Application_Section_For_Credit_Status_Content_Verification);
				log.info("Actual_Content_On_Account_Information_Credit_Application_Section_For_Credit_Status_Content_Verification    :*****************************  "	+ Actual_Content_On_Account_Information_Credit_Application_Section_For_Credit_Status_Content_Verification);
				log.info("Expected_Content_On_Account_Information_Credit_Application_Section_For_Credit_Status_Content_Verification  :*****************************  "	+ Expected_Content_On_Account_Information_Credit_Application_Section_For_Credit_Status_Content_Verification);
				softAssert.assertAll();
				} catch (Throwable e) {
				mainPage.getScreenShotForFailure(webPage, "Verify_Credit_Application_Sec");
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
		}
	}

	/*** Test Case - 026 - Verify AccountInformationTab and Change Password functionality  @throws PageException Verify "Account Information" page title,Verify  "Account Information" form input validations by submitting blank form,Verify email address field validation with some invalid email address  @throws InterruptedException	 */
	@Test(priority = 324, enabled = true)
	public void verify_Account_Information_Tab_Verify_Validation_Messages() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		/*String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageTitle");
		  ConnsSignInPage.verify_Account_DashBoard_Login(test_data,softAssert);*/
		log.info("verification of Mandatory field validation message started");
		String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","VerifyAccountInformationForm");
		String Navigate_To_Account_Information_Tab_Form = testdata[0][12];
		String Expected_Email_ID_NA_Error_Message = testdata[0][10];
		String Expected_Email_ID_Error_Message_Invalid_Email = testdata[1][10];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form);
		webPage.getCurrentUrl();// For Safari
		try {
			for (int r = 0; r < testdata.length; r++) {
				String FNInput = testdata[r][2];
				String LNInput = testdata[r][5];
				String EmailInput = testdata[r][8];
				if (FNInput.equalsIgnoreCase("NA") && LNInput.equalsIgnoreCase("NA") && EmailInput.equalsIgnoreCase("NA")) {
					List<String> actualErrorMessage = ConnsSignInPage.verify_Account_Information_First_Name_Last_Name_Login_Validation(testdata, softAssert);
					softAssert.assertEquals(actualErrorMessage.get(0), Expected_Email_ID_NA_Error_Message,	"Page Element Content verification failed. Expected_Email_ID_Error_Msg : "	+ Expected_Email_ID_NA_Error_Message + " actualErrorMessage.get(0) :    "	+ actualErrorMessage.get(0));
					log.info(" actualErrorMessage : " + actualErrorMessage);
					log.info(" actualErrorMessage.get(0) : " + actualErrorMessage.get(0));
					log.info(" Expected_Email_ID_NA_Error_Message : " + Expected_Email_ID_NA_Error_Message);
				}
				if (FNInput.equalsIgnoreCase(testdata[1][2]) && LNInput.equalsIgnoreCase(testdata[1][5])&& EmailInput.equalsIgnoreCase(testdata[1][8])) {
					List<String> actualErrorMessage = ConnsSignInPage.verify_Account_Information_First_Name_Last_Name_Login_Invalid_Input_Validation(testdata,softAssert);
					softAssert.assertEquals(actualErrorMessage.get(0), Expected_Email_ID_Error_Message_Invalid_Email,"Page Element Content verification failed. Expected_Email_ID_Error_Msg : "+ Expected_Email_ID_Error_Message_Invalid_Email + "actualErrorMessage.get(0) :  "+ actualErrorMessage.get(0));
					log.info(" actualErrorMessage : " + actualErrorMessage);
					log.info(" actualErrorMessage.get(0) : " + actualErrorMessage.get(0));
					log.info(" Expected_Email_ID_Error_Message_Invalid_Email : "+ Expected_Email_ID_Error_Message_Invalid_Email);
				}
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/*** Test Case - 027 - Verify AccountInformationTab and Change Password functionality @throws PageException            Verify "Change Password" checkbox functionality,Verify "Change Password" form input validations by submitting blank form,Verify password field validation with short password in  "New Password" field,Verify password field validation for  "Confirm New Password" field	 */
	@Test(priority = 325, enabled = true)
	public void verify_Account_Information_Tab_Change_Password_Functionality() throws PageException {
		SoftAssert softAssert = new SoftAssert();
		/*String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageTitle");
		 ConnsSignInPage.verify_Account_DashBoard_Login(test_data,softAssert);*/
		log.info("verification of Mandatory field validation message started");
		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "VerifyChangePasswordfun");
		String Navigate_To_Account_Information_Tab_Form_Change_Password_URL = inputdata[0][16];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_Change_Password_URL);
		try {
			log.info(" verify_Account_Information_Tab_Change_Password_Functionality_Starting  : ^^^^^^^^^^^^^^^^^^^^^^^^^^ ");
			List<String> actualErrorMessage = ConnsSignInPage.verify_Account_Information_Tab_Change_Password_Functionality(inputdata);
			log.info("Actual 1 : " + actualErrorMessage.get(0).trim());
			log.info("Expect 1 : " + inputdata[1][10].trim());
			log.info(actualErrorMessage.get(0).trim().contains(inputdata[1][10].trim()));
			log.info("Actual 2 : " + actualErrorMessage.get(1).trim());
			log.info("Expect 2 : " + inputdata[2][10].trim());
			log.info(actualErrorMessage.get(1).trim().contains(inputdata[2][10].trim()));
			log.info("Actual 3 : " + actualErrorMessage.get(2).trim());
			log.info("Expect 3 : " + inputdata[2][10].trim());
			log.info(actualErrorMessage.get(2).trim().contains(inputdata[2][10].trim()));
			log.info("Actual 4 : " + actualErrorMessage.get(3).trim());
			log.info("Expect 4 : " + inputdata[3][10].trim());
			log.info(actualErrorMessage.get(3).trim().contains(inputdata[3][10].trim()));
			softAssert.assertEquals(actualErrorMessage.get(0).trim(), inputdata[1][10].trim(),	"Change Password Functionality with Short Password Input verification failed For NA Password Input. Expected Password Error Message                 :   "	+ inputdata[1][10] + "Actual Email Address Error Message : " + actualErrorMessage);
			softAssert.assertEquals(actualErrorMessage.get(1).trim(), inputdata[2][10].trim(),	"Change Password Functionality with Different Value New Password Input verification failed For NA Password Input. Expected Password Error Message   :   "	+ inputdata[2][10] + "Actual Email Address Error Message : " + actualErrorMessage);
			softAssert.assertEquals(actualErrorMessage.get(2).trim(), inputdata[2][10].trim(),	"Change Password Functionality with Different Value Confirm Password verification failed For NA Password Input. Expected Password Error Message     :   "	+ inputdata[2][10] + "Actual Email Address Error Message : " + actualErrorMessage);
			softAssert.assertEquals(actualErrorMessage.get(3).trim(), inputdata[3][10].trim(),	"Change Password Functionality with Invalid Input verification failed For NA Password Input. Expected Password Error Message                        :   "	+ inputdata[3][10] + "Actual Email Address Error Message : " + actualErrorMessage);
			softAssert.assertAll();
		} catch (Throwable e) {
			log.info("IN CATCH BLOCKKKKKKKKK");
			mainPage.getScreenShotForFailure(webPage, "verify_Account_Information_Tab_Change_Password_Functionality");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/*** Test Case - 028 - Verify "Go back" link in Account Information page  @throws PageException Application should redirect to previous page : https://www.conns.com/customer/account/  @throws InterruptedException */
	@Test(priority = 326, enabled = true)
	public void verify_Account_Information_Tab_Change_Password_Form_Go_Back_Link() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		/*String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageLogin");
		ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);*/
		log.info("verification of Mandatory field validation message started");
		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "VerifyChangePasswordfun");
		String Expected_Page_URL = inputdata[0][16];
		String Expected_Page_Title = inputdata[0][17];
		String Expected_Page_Title_After_Page_Redirection = inputdata[0][15];
		String Expected_Page_URL_After_Page_Redirection = inputdata[0][13];
		String Navigate_To_Account_Information_Tab_Form_Change_Password_URL = inputdata[0][16];
		String Navigate_To_Account_Information_Tab_Form_URL = inputdata[0][13];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_Change_Password_URL);
		webPage.getCurrentUrl();// For Safari
		String GO_BACK_Link_Locator = inputdata[0][14];
		try {
			log.info("*******************************Entered Change Password Functionality alongwith Go_Back_Link Functionality will be starting ****************************** ");
			String Actual_Page_URL = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertTrue(Actual_Page_URL.contains(Expected_Page_URL),"Page url verification failed. Expected_Page_URL   : " + Expected_Page_URL	+ " Actual_Page_URL   : " + Actual_Page_URL);
			String Actual_Page_Title = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(Actual_Page_Title, Expected_Page_Title,	  "Page title verification failed. Expected_Page_Title : " + Expected_Page_Title	+ " Actual_Page_Title : " + Actual_Page_Title);
			log.info("Actual_Page_URL     : " + Actual_Page_URL);
			log.info("Expected_Page_URL   : " + Expected_Page_URL);
			log.info("Actual_Page_Title   : " + Actual_Page_Title);
			log.info("Expected_Page_Title : " + Expected_Page_Title);
			commonMethods.clickElementbyXpath(webPage, GO_BACK_Link_Locator, softAssert);
			webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
			String Actual_Page_Url_After_Page_Redirection = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertEquals(Actual_Page_Url_After_Page_Redirection, Expected_Page_URL_After_Page_Redirection,    "Page url verification failed. Expected_Page_URL_After_Page_Redirection         :  "     + Expected_Page_URL_After_Page_Redirection         +     " Actual_Page_Url_After_Page_Redirection    :  " + Actual_Page_Url_After_Page_Redirection);
			String Actual_Page_Title_After_Page_Redirection = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(Actual_Page_Title_After_Page_Redirection,Expected_Page_Title_After_Page_Redirection, "Page title verification failed. Expected_Page_Title_After_Page_Redirection  :  "	  + Expected_Page_Title_After_Page_Redirection       +     " Actual_Page_Title_After_Page_Redirection  :  " + Actual_Page_Title_After_Page_Redirection);
			log.info("Actual_Page_Url_After_Page_Redirection      : " + Actual_Page_Url_After_Page_Redirection);
			log.info("Expected_Page_URL_After_Page_Redirection    : " + Expected_Page_URL_After_Page_Redirection);
			log.info("Actual_Page_Title_After_Page_Redirection    : " + Actual_Page_Title_After_Page_Redirection);
			log.info("Expected_Page_Title_After_Page_Redirection  : " + Expected_Page_Title_After_Page_Redirection);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage,"verify_Account_Information_Tab_Change_Password_Form_Go_Back_Link");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/************************************************************************ UC005 STARTS *******************************************************************************************************************************************************************************************/
	/*** Test Case - 029 - Verify "Contact Information" form input validations by tapping on edit & submitting blank form Verify "Account Information" form input validations by submitting blank form Application should throw anerror message : "This is a required field." * @throws InterruptedException */
	@Test(priority = 327, enabled = true)
	public void verify_Contact_Information_Tab_Edit_Link_Blank_Input() throws PageException, InterruptedException {
		 SoftAssert softAssert = new SoftAssert();
		/*String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageLogin");
		  ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);*/
		log.info("verification of Mandatory field validation message started");
		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","ContactInformationFunctionality");
		String Navigate_To_Account_Information_Tab_Form_URL = inputdata[0][0];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		webPage.getCurrentUrl();// For Safari
		String Contact_Information_Edit_Link_Locator = inputdata[0][1];
		commonMethods.clickElementbyXpath(webPage, Contact_Information_Edit_Link_Locator, softAssert);
		for (int r = 0; r < inputdata.length; r++) {
			String Expected_Email_ID_Error_Message = inputdata[r][11];
			try {
				List<String> actualErrorMessage = ConnsSignInPage.verify_Account_DashBoard_First_Name_Last_Name_Login(inputdata, softAssert);
				SoftAssertor.assertEquals(actualErrorMessage.get(0), Expected_Email_ID_Error_Message);
				log.info("Email_Address_Error_Message_Account_Information_Page : " + actualErrorMessage.get(0));
				log.info("ExpectedValMsg : " + Expected_Email_ID_Error_Message);
				softAssert.assertAll();
			} catch (Throwable e) {
				mainPage.getScreenShotForFailure(webPage, "verify_Contact_Information_Tab_Edit_Link_Blank_Input");
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
		}
	}

	/*** Test Case - 030 - Verify "Contact Information" functionality by submitting valid form Enter all valid input and click on Save Address Application should redirect to https://www.conns.com/customer/address/index/ and New address should be added under Additional Address Entries @throws InterruptedException */
	@Test(priority = 328, enabled = true)
	public void verify_Contact_Information_Tab_Edit_Link_Valid_Input() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		/*String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageLogin");
		  ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);*/
		log.info("verification of Mandatory field validation message started");
		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","ContactInformationFunctionality_Valid_Input");
		String Navigate_To_Account_Information_Tab_Form_URL = inputdata[0][0];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		webPage.getCurrentUrl();// For Safari
		String Contact_Information_Edit_Link_Locator = inputdata[0][1];
		commonMethods.clickElementbyXpath(webPage, Contact_Information_Edit_Link_Locator, softAssert);
		String FNInput = inputdata[0][3];
		String LNInput = inputdata[0][6];
		String EmailInput = inputdata[0][9];
		String ExpectedValMsg = inputdata[0][11];
		try {
			if (!(FNInput.equalsIgnoreCase("NA") && LNInput.equalsIgnoreCase("NA") && EmailInput.equalsIgnoreCase("NA"))) {
				List<String> information_Saved_Successfully_Message = ConnsSignInPage.verify_Account_Information_First_Name_Last_Name_Login_Validation_Valid_Input(inputdata,softAssert);
				SoftAssertor.assertEquals(information_Saved_Successfully_Message.get(0), ExpectedValMsg);
				log.info("First_Name_Error_Message_Account_Information_Page : "	+ information_Saved_Successfully_Message.get(0));
				log.info("ExpectedValMsg : " + ExpectedValMsg);
				softAssert.assertAll();
			}
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Contact_Information_Tab_Edit_Link_Blank_Input");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/*** Test Case - 031 - Verify default billing & shipping address by clicking on manage addresses Billing & shipping address must be editable default billing & shipping address' should be editable when tapped on Manage addresses On clicking on 'Manage Addresses' user is redirected to non-editable address book section. On clicking on 'change billing address' editable form is displayed.  @throws InterruptedException	 */
	@Test(priority = 329, enabled = true)
	public void verify_Contact_Information_Tab_Default_Billing_Shipping_Address_Link() throws PageException, InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) webPage.getDriver();
		SoftAssert softAssert = new SoftAssert();
		/*String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageLogin");
		  ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);*/
		log.info("verification of Mandatory field validation message started");
		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","Address_Book_Manage_Addresses_Functionality");
		String Navigate_To_Account_Information_Tab_Form_URL = inputdata[0][0];
		//webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		webPage.getCurrentUrl();// For Safari
		String Address_Book_Manage_Addresses_Edit_Link_Locator = inputdata[0][1];
		String Change_Billing_Address_Edit_Link_Locator = inputdata[0][2];
		String Editing_Zip_Code_Link_Locator = inputdata[0][3];
		String Input_Zip_Code_Link_Locator = inputdata[0][4];
		String Save_Address_Button_Locator = inputdata[0][5];
		String Change_Shipping_Address_Edit_Link_Locator = inputdata[0][6];
		String Address_Book_Expected_Page_Title = inputdata[0][8];
		String Address_Book_Expected_Page_URL_Index = inputdata[0][12];
		try {
			commonMethods.clickElementbyXpath(webPage, Address_Book_Manage_Addresses_Edit_Link_Locator, softAssert);
			log.info("Change_Shipping_Address_Edit_Link_Locator :::::" + Change_Shipping_Address_Edit_Link_Locator);
			commonMethods.clickElementbyXpath(webPage, Change_Billing_Address_Edit_Link_Locator, softAssert);
			log.info("Change_Billing_Address_Edit_Link_Locator:::::" + Change_Billing_Address_Edit_Link_Locator);
			jse.executeScript("scroll(0, 250);");
			commonMethods.clickElementbyXpath(webPage, Editing_Zip_Code_Link_Locator, softAssert);
			log.info("Editing_Zip_Code_Link_Locator:::::" + Editing_Zip_Code_Link_Locator);
			commonMethods.clearElementbyXpath(webPage, Editing_Zip_Code_Link_Locator, softAssert);
			log.info("Editing_Zip_Code_Link_Locator::::" + Editing_Zip_Code_Link_Locator);
			commonMethods.sendKeysbyXpath(webPage, Editing_Zip_Code_Link_Locator, Input_Zip_Code_Link_Locator,softAssert);
			log.info("Editing_Zip_Code_Link_Locator::::" + Editing_Zip_Code_Link_Locator);
			commonMethods.clickElementbyXpath(webPage, Save_Address_Button_Locator, softAssert);
			log.info("Save_Address_Button_Locator::::" + Save_Address_Button_Locator);
			String actualPageUrl = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertEquals(actualPageUrl, Address_Book_Expected_Page_URL_Index,"Page url verification failed.    Expected  url   : " + Address_Book_Expected_Page_URL_Index	+ " Actual url :   "   + actualPageUrl);
			String actualPageTitle = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(actualPageTitle, Address_Book_Expected_Page_Title,	"Page title verification failed.  Expected  title : " + Address_Book_Expected_Page_Title		+ " Actual title : "  + actualPageTitle);
			webPage.getDriver().navigate().refresh();// for other browser
			webPage.getCurrentUrl();// Rajesh ...For Safari
			webPage.getDriver().navigate().refresh();
			Thread.sleep(10000); // Rajesh
			commonMethods.clickElementbyXpath(webPage, Change_Shipping_Address_Edit_Link_Locator, softAssert);
			log.info("Change_Shipping_Address_Edit_Link_Locator before javascript::::"	+ Change_Shipping_Address_Edit_Link_Locator);
			jse.executeScript("scroll(0, 250);");
			commonMethods.clickElementbyXpath(webPage, Editing_Zip_Code_Link_Locator, softAssert);
			log.info("Editing_Zip_Code_Link_Locator1:::::::::::::::" + Editing_Zip_Code_Link_Locator);
			commonMethods.clearElementbyXpath(webPage, Editing_Zip_Code_Link_Locator, softAssert);
			log.info("Editing_Zip_Code_Link_Locator2:::::::::::::::" + Editing_Zip_Code_Link_Locator);
			commonMethods.sendKeysbyXpath(webPage, Editing_Zip_Code_Link_Locator, Input_Zip_Code_Link_Locator,softAssert);
			log.info("Editing_Zip_Code_Link_Locator3:::::::::::::::" + Editing_Zip_Code_Link_Locator);
			commonMethods.clickElementbyXpath(webPage, Save_Address_Button_Locator, softAssert);
			log.info("Save_Address_Button_Locator:::::" + Save_Address_Button_Locator);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage,"verify_Contact_Information_Tab_Default_Billing_Shipping_Address_Link");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/*** Test Case - 032 - Verify "Change Billing Address" link Application should redirect to "Contact Information" form  @throws InterruptedException */
	@Test(priority = 330, enabled = true)
	public void verify_Contact_Information_Tab_Change_Billing_Address_Link() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		/*String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageLogin");
		  ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);*/
		log.info("verification of Mandatory field validation message started");
		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","Address_Book_Manage_Addresses_Functionality");
		String Navigate_To_Account_Information_Tab_Form_URL = inputdata[0][0];
		String Address_Book_Manage_Addresses_Edit_Link_Locator = inputdata[0][1];
		String Change_Billing_Address_Edit_Link_Locator = inputdata[0][2];
		String Contact_Information_Page_Address_Book_Go_Back_Link = inputdata[0][9];
		String Expected_Contact_Information_Page_Title = inputdata[0][10];
		String Expected_Contact_Information_Page_URL = inputdata[0][11];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		webPage.getCurrentUrl();// For Safari
		try {
			commonMethods.clickElementbyXpath(webPage, Address_Book_Manage_Addresses_Edit_Link_Locator, softAssert);
			commonMethods.clickElementbyXpath(webPage, Change_Billing_Address_Edit_Link_Locator, softAssert);
			String actualPageUrl = commonMethods.getPageUrl(webPage, softAssert);
			log.info(" **************************** Assert contains url*******************************************************************************************************************************************************");
			softAssert.assertTrue(actualPageUrl.contains(Expected_Contact_Information_Page_URL),"Page url verification failed. Expected url : " + Expected_Contact_Information_Page_URL		+ "Actual url   : " + actualPageUrl);
			String actualPageTitle = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(actualPageTitle, Expected_Contact_Information_Page_Title,"Page title verification failed. Expected title : " + Expected_Contact_Information_Page_Title	+ "Actual title : " + actualPageTitle);
			commonMethods.clickElementbyXpath(webPage, Contact_Information_Page_Address_Book_Go_Back_Link, softAssert);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Contact_Information_Tab_Change_Billing_Address_Link");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/*** Test Case - 033 - Verify "Change Shipping Address" link Application should redirect to "Contact Information" form  @throws InterruptedException	 */
	@Test(priority = 331, enabled = true)
	public void verify_Contact_Information_Tab_Change_Shipping_Address_Link() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		/*String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageLogin");
		 ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);*/
		log.info("verification of Mandatory field validation message started");
		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","Address_Book_Manage_Addresses_Functionality");
		String Navigate_To_Account_Information_Tab_Form_URL = inputdata[0][0];
		String Address_Book_Manage_Addresses_Edit_Link_Locator = inputdata[0][1];
		String Change_Shipping_Address_Edit_Link_Locator = inputdata[0][6];
		String Contact_Information_Page_Address_Book_Go_Back_Link = inputdata[0][9];
		String Expected_Contact_Information_Page_Title = inputdata[0][10];
		String Expected_Contact_Information_Page_URL = inputdata[0][11];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		webPage.getCurrentUrl();// For Safari
		try {
			commonMethods.clickElementbyXpath(webPage, Address_Book_Manage_Addresses_Edit_Link_Locator, softAssert);
			commonMethods.clickElementbyXpath(webPage, Change_Shipping_Address_Edit_Link_Locator, softAssert);
			String actualPageUrl = commonMethods.getPageUrl(webPage, softAssert);
			log.info(" **************************** Assert contains url*******************************************************************************************************************************************************");
			softAssert.assertTrue(actualPageUrl.contains(Expected_Contact_Information_Page_URL),"Page url verification failed. Expected url  : " + Expected_Contact_Information_Page_URL	+ " Actual url   : " + actualPageUrl);
			String actualPageTitle = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(actualPageTitle, Expected_Contact_Information_Page_Title,"Page title verification failed. Expected title : " + Expected_Contact_Information_Page_Title	+ " Actual title : " + actualPageTitle);
			commonMethods.clickElementbyXpath(webPage, Contact_Information_Page_Address_Book_Go_Back_Link, softAssert);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Contact_Information_Tab_Change_Shipping_Address_Link");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/*** Test Case - 034 - Verify "Go back" link in "Contact Information" form input all mandatory fields with valid data and Click on Save button Success message should render "The address has been saved." : https://www.conns.com/customer/address/index/  @throws InterruptedException	 */
	@Test(priority = 332, enabled = true)
	public void verify_Contact_Information_Tab_Go_Back_Link() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		/*String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageLogin");
		  ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);*/
		log.info("verification of Mandatory field validation message started");
		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","Address_Book_Manage_Addresses_Functionality");
		String Navigate_To_Account_Information_Tab_Form_URL = inputdata[0][0];
		String Address_Book_Manage_Addresses_Edit_Link_Locator = inputdata[0][1];
		String Change_Billing_Address_Edit_Link_Locator = inputdata[0][2];
		String Change_Shipping_Address_Edit_Link_Locator = inputdata[0][6];
		String Address_Book_Expected_Page_URL = inputdata[0][7];
		String Address_Book_Expected_Page_Title = inputdata[0][8];
		String Contact_Information_Page_Address_Book_Go_Back_Link = inputdata[0][9];
		String Expected_Contact_Information_Page_Title = inputdata[0][10];
		String Expected_Contact_Information_Page_URL = inputdata[0][11];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		webPage.getCurrentUrl();// For Safari
		try {
			commonMethods.clickElementbyXpath(webPage, Address_Book_Manage_Addresses_Edit_Link_Locator, softAssert);
			commonMethods.clickElementbyXpath(webPage, Change_Billing_Address_Edit_Link_Locator, softAssert);
			String Actual_Contact_Information_Change_Billing_Address_Page_URL = commonMethods.getPageUrl(webPage,softAssert);
			log.info(" **************************** Assert contains url*******************************************************************************************************************************************************");
			softAssert.assertTrue(Actual_Contact_Information_Change_Billing_Address_Page_URL.contains(Expected_Contact_Information_Page_URL),"Page url verification failed. Expected_Contact_Information_Page_URL : "	+ Expected_Contact_Information_Page_URL		+ "Actual_Contact_Information_Change_Shipping_Address_Page_URL : "	+ Actual_Contact_Information_Change_Billing_Address_Page_URL);
			String Actual_Contact_Information_Change_Billing_Address_Page_Title = commonMethods.getPageTitle(webPage,softAssert);
			softAssert.assertEquals(Actual_Contact_Information_Change_Billing_Address_Page_Title,Expected_Contact_Information_Page_Title,"Page title verification failed. Expected title : " + Expected_Contact_Information_Page_Title		+ "Actual title : " + Actual_Contact_Information_Change_Billing_Address_Page_Title);
			commonMethods.clickElementbyXpath(webPage, Contact_Information_Page_Address_Book_Go_Back_Link, softAssert);
			String actualAddressBookPageUrl = commonMethods.getPageUrl(webPage, softAssert);
			log.info(" **************************** Assert contains url*******************************************************************************************************************************************************");
			softAssert.assertEquals(actualAddressBookPageUrl, Address_Book_Expected_Page_URL,"Page url verification failed. Expected url : " + Address_Book_Expected_Page_URL + "Actual url : "	+ actualAddressBookPageUrl);
			String actualAddressBookPageTitle = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(actualAddressBookPageTitle, Address_Book_Expected_Page_Title,"Page title verification failed. Expected title : " + Address_Book_Expected_Page_Title	+ "Actual title : " + actualAddressBookPageTitle);
			webPage.getCurrentUrl();// For Safari
			commonMethods.clickElementbyXpath(webPage, Change_Shipping_Address_Edit_Link_Locator, softAssert);
			String Actual_Contact_Information_Change_Shipping_Address_Page_URL = commonMethods.getPageUrl(webPage,softAssert);
			log.info(" **************************** Assert contains url*******************************************************************************************************************************************************");
			softAssert.assertTrue(Actual_Contact_Information_Change_Shipping_Address_Page_URL.contains(Expected_Contact_Information_Page_URL),"Page url verification failed. Expected_Contact_Information_Page_URL : "	+ Expected_Contact_Information_Page_URL	+ "Actual_Contact_Information_Change_Shipping_Address_Page_URL : "	+ Actual_Contact_Information_Change_Shipping_Address_Page_URL);
			String Actual_Contact_Information_Change_Shipping_Address_Page_Title = commonMethods.getPageTitle(webPage,softAssert);
			softAssert.assertEquals(Actual_Contact_Information_Change_Shipping_Address_Page_Title,Expected_Contact_Information_Page_Title,   "Page title verification failed. Expected title : " + Expected_Contact_Information_Page_Title	+ "Actual title : " + Actual_Contact_Information_Change_Shipping_Address_Page_Title);
			commonMethods.clickElementbyXpath(webPage, Contact_Information_Page_Address_Book_Go_Back_Link, softAssert);
			String actualAddressPageUrl = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertEquals(actualAddressPageUrl, Address_Book_Expected_Page_URL,"Page url verification failed. Expected url : " + Address_Book_Expected_Page_URL + "Actual url : "	+ actualAddressPageUrl);
			String actualAddressPageTitle = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(actualAddressPageTitle, Address_Book_Expected_Page_Title,"Page title verification failed. Expected title : " + Address_Book_Expected_Page_Title	+ "Actual title : " + actualAddressPageTitle);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Contact_Information_Tab_Go_Back_Link");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/*** Test Case - 035 - Verify add new address functionality by adding valid data "Additional Address Entries" section in Address Book page input all mandatory fields with valid data and Click on Save button Success message should render "The address has been saved." : https://www.conns.com/customer/address/index/  @throws InterruptedException */
	@Test(priority = 333, enabled = true)
	public void verify_Contact_Information_Tab_Address_Book_Page_Additional_Address_Entries()	throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		/*String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageLogin");
		ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);*/
		log.info("verification of Mandatory field validation message started");
		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","Account_Information_Address_Book_Additional_Address_Valid_Input");
		String Navigate_To_Account_Information_Tab_Form_URL = inputdata[0][0];
		String Account_Information_Address_Book_Manage_Addresses_Edit_Link_Locator = inputdata[0][1];
		String Account_Information_Address_Book_Additional_Address_Entries_Delete_Address_Locator = inputdata[0][2];
		String Account_Information_Address_Book_Add_New_Address_Locator = inputdata[0][3];
		String Expected_Additonal_Address_Successfully_Saved_Locator = inputdata[0][27];
		String Expected_Additonal_Address_Successfully_Saved_Message = inputdata[0][28];
		String Expected_Address_Book_Page_URL = inputdata[0][29];
		String Expected_Address_Book_Page_Title = inputdata[0][30];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		//webPage.getCurrentUrl();// For Safari
		Thread.sleep(3000);
		commonMethods.clickElementbyXpath(webPage, Account_Information_Address_Book_Manage_Addresses_Edit_Link_Locator,	softAssert);
		webPage.getCurrentUrl();// For Safari
		log.info("Clicked on xpath Account_Information_Address_Book_Manage_Addresses_Edit_Link_Locator");
		try {
			webPage.getDriver().navigate().refresh();
			log.info("Address_Book_Additional_Address_Entries_Delete_Address_Locator Page Refresh : " );
			webPage.getCurrentUrl();// For Safari 
			WebElement Address_Book_Additional_Address_Entries_Delete_Address_Locator = webPage.getDriver().findElement(By.xpath(Account_Information_Address_Book_Additional_Address_Entries_Delete_Address_Locator));
			log.info("Address_Book_Additional_Address_Entries_Delete_Address_Locator is displayed : ");
			if ((Address_Book_Additional_Address_Entries_Delete_Address_Locator).isDisplayed()) {
				log.info("Inside IF loop");
				((JavascriptExecutor) webPage.getDriver()).executeScript("confirm = function(message){return true;};");
				Thread.sleep(5000);
				commonMethods.clickElementbyXpath(webPage,Account_Information_Address_Book_Additional_Address_Entries_Delete_Address_Locator, softAssert);
				log.info("Added for modal dialog in safari");
				commonMethods.clickElementbyXpath(webPage, Account_Information_Address_Book_Add_New_Address_Locator,softAssert);
			}
			else{
				log.info("************************ Add New Address if Delete Address Link is not visible*****************");
				commonMethods.clickElementbyXpath(webPage, Account_Information_Address_Book_Add_New_Address_Locator,softAssert);
			}
			ConnsSignInPage.verify_Contact_Information_Tab_Address_Book_Page_Additional_Address_Entries(inputdata);
			String actualAddressBookPageURL = commonMethods.getPageUrl(webPage, softAssert);
			log.info(" **************************** Assert contains url*******************************************************************************************************************************************************");
			softAssert.assertTrue(actualAddressBookPageURL.contains(Expected_Address_Book_Page_URL),"Page url verification failed. Expected_Address_Book_Page_URL : " + Expected_Address_Book_Page_URL	+ "actualAddressBookPageURL : " + actualAddressBookPageURL);
			String actualAddressBookPageTitle = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(actualAddressBookPageTitle, Expected_Address_Book_Page_Title,	"Page title verification failed. Expected title : " + Expected_Address_Book_Page_Title				+ "Actual Title : "             + actualAddressBookPageTitle);
			String ActualElementName = commonMethods.getTextbyXpath(webPage,Expected_Additonal_Address_Successfully_Saved_Locator, softAssert);
			softAssert.assertEquals(ActualElementName, Expected_Additonal_Address_Successfully_Saved_Message,"Page Content verification failed. Expected Page Content Expected Element Name : "			+ Expected_Additonal_Address_Successfully_Saved_Message	+ "Actual Page Content Actual Element Name : " + ActualElementName);
			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage,"verify_Contact_Information_Tab_Address_Book_Page_Additional_Address_Entries");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	/************************************************************************ UC005 ENDS   *******************************************************************************************************************************************************************************************/
	/************************************************************************ UC006 STARTS *******************************************************************************************************************************************************************************************/

	/*** Test Case - 036 - Verify editing "Newsletters" link Application should redirect to "Newsletter subscription" form * @throws InterruptedException*/
	@Test(priority = 334, enabled = true)
	public void verify_Account_Information_Tab_Newsletters_Link() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		/*String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageLogin");
		ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);*/
		log.info("verification of Mandatory field validation message started");
		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","Address_Book_Newsletters_Functionality");
		String Navigate_To_Account_Information_Tab_Form_URL = inputdata[0][0];
		String Newsletters_Edit_Link_Locator = inputdata[0][1];
		String Expected_Newsletters_Page_URL = inputdata[0][2];
		String Expected_Newsletters_Page_Title = inputdata[0][3];
		String Newsletters_Page_Go_Back_Link = inputdata[0][4];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		//webPage.getCurrentUrl();// For Safari
		try {
			commonMethods.clickElementbyXpath(webPage, Newsletters_Edit_Link_Locator, softAssert);
			String Actual_Newsletters_Page_URL = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertEquals(Actual_Newsletters_Page_URL, Expected_Newsletters_Page_URL,	    "Page url verification failed. Expected url     :  "      + Expected_Newsletters_Page_URL    + " Actual url   : "	+ Actual_Newsletters_Page_URL);
			String Actual_Newsletters_Page_Title = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(Actual_Newsletters_Page_Title, Expected_Newsletters_Page_Title,	"Page title verification failed. Expected title :  "      + Expected_Newsletters_Page_Title	 + " Actual title : "   + Actual_Newsletters_Page_Title);
			commonMethods.clickElementbyXpath(webPage, Newsletters_Page_Go_Back_Link, softAssert);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Account_Information_Tab_Newsletter_Link");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/*** Test Case - 037 - Verify "General subscription" checkbox can be checked The checkbox should be checked when tapped on it  @throws InterruptedException */
	@Test(priority = 335, enabled = true)
	public void verify_Account_Information_Tab_Newsletters_General_Subscription_CheckBox()	throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		/*String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageLogin");
		ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);*/
		log.info("verification of Mandatory field validation message started");
		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","Address_Book_Newsletters_Functionality");
		String Navigate_To_Account_Information_Tab_Form_URL = inputdata[0][0];
		String Newsletters_Edit_Link_Locator = inputdata[0][1];
		String Newsletters_Page_Go_Back_Link = inputdata[0][4];
		String News_Letters_Subscription_CheckBox = inputdata[0][5];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		//webPage.getCurrentUrl();// For Safari
		try {
			Thread.sleep(3000);
			commonMethods.clickElementbyXpath(webPage, Newsletters_Edit_Link_Locator, softAssert);
			Thread.sleep(3000);
			WebElement Newsletters_Subscription_CheckBox = webPage.getDriver().findElement(By.xpath(inputdata[0][5]));
			if ((!(Newsletters_Subscription_CheckBox).isSelected())	&& ((Newsletters_Subscription_CheckBox)).isEnabled()) {
				commonMethods.clickElementbyXpath(webPage, News_Letters_Subscription_CheckBox, softAssert);
			} else {
				commonMethods.clickElementbyXpath(webPage, News_Letters_Subscription_CheckBox, softAssert);
				Thread.sleep(3000);
				commonMethods.clickElementbyXpath(webPage, News_Letters_Subscription_CheckBox, softAssert);
			}
			commonMethods.clickElementbyXpath(webPage, Newsletters_Page_Go_Back_Link, softAssert);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage,"verify_Account_Information_Tab_Newsletters_General_Subscription_CheckBox");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/*** Test Case - 038 - Verify "Go back" link on Newsletter subscription form Application should redirect to previous page : https://www.conns.com/customer/account/  @throws InterruptedException */
	@Test(priority = 336, enabled = true)
	public void verify_Account_Information_Tab_Newsletters_Go_Back_Link() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		/*String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageLogin");
		ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);*/
		log.info("verification of Mandatory field validation message started");
		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","Address_Book_Newsletters_Functionality");
		String Navigate_To_Account_Information_Tab_Form_URL = inputdata[0][0];
		String Newsletters_Edit_Link_Locator = inputdata[0][1];
		String Expected_Newsletters_Page_URL = inputdata[0][2];
		String Expected_Newsletters_Page_Title = inputdata[0][3];
		String Newsletters_Page_Go_Back_Link = inputdata[0][4];
		String Expected_Account_Information_Page_URL = inputdata[0][6];
		String Expected_Account_Information_Page_Title = inputdata[0][7];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		//webPage.getCurrentUrl();// For Safari
		try {
			commonMethods.clickElementbyXpath(webPage, Newsletters_Edit_Link_Locator, softAssert);
			String Actual_Newsletters_Page_URL = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertEquals(Actual_Newsletters_Page_URL, Expected_Newsletters_Page_URL,	   "Page url verification failed.    Expected url   : " + Expected_Newsletters_Page_URL    +  " Actual url  :  "  + Actual_Newsletters_Page_URL);
			String Actual_Newsletters_Page_Title = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(Actual_Newsletters_Page_Title, Expected_Newsletters_Page_Title,	"Page title verification failed. Expected title : " + Expected_Newsletters_Page_Title  +  " Actual title : "  + Actual_Newsletters_Page_Title);
			commonMethods.clickElementbyXpath(webPage, Newsletters_Page_Go_Back_Link, softAssert);
			String actualAccountInformationPageUrl = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertEquals(actualAccountInformationPageUrl, Expected_Account_Information_Page_URL,	     "Page url verification failed.  Expected url   : " + Expected_Account_Information_Page_URL	    + " Actual url   : " + actualAccountInformationPageUrl);
			String actualAccountInformationPageTitle = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(actualAccountInformationPageTitle, Expected_Account_Information_Page_Title,	"Page title verification failed. Expected title : " + Expected_Account_Information_Page_Title   + " Actual title : " + actualAccountInformationPageTitle);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Account_Information_Tab_Newsletters_Go_Back_Link");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**	 * Test Case - 039 - Verify "Save" link on Newsletter subscription form. User should be redirected to previous page: https://www.conns.com/customer/account/ & "The Subscription has been saved" message should appear in green box on top of page  @throws InterruptedException */
	@Test(priority = 337, enabled = true)
	public void verify_Account_Information_Tab_Newsletters_Save_Button() throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		/*String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageLogin");
		ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);*/
		log.info("verification of Mandatory field validation message started");
		String[][] inputdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","Address_Book_Newsletters_Functionality");
		String Navigate_To_Account_Information_Tab_Form_URL = inputdata[0][0];
		String Newsletters_Edit_Link_Locator = inputdata[0][1];
		String Newsletters_Page_Go_Back_Link = inputdata[0][4];
		String News_Letters_Subscription_CheckBox = inputdata[0][5];
		String Expected_Account_Information_Page_URL = inputdata[0][6];
		String Expected_Account_Information_Page_Title = inputdata[0][7];
		String Newsletters_Save_Button = inputdata[0][8];
		String Expected_Newsletters_Subscription_Successfully_Saved_Locator = inputdata[0][9];
		String Expected_Newsletters_Subscription_Successfully_Saved_Message = inputdata[0][10];
		String Wait_On_Element = inputdata[0][11];
		String Navigate_To_Newsletters_Subscription_Form_CheckBox_Save_Button_URL = inputdata[0][13];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		//webPage.getCurrentUrl();// For Safari
		try {
			commonMethods.clickElementbyXpath(webPage, Newsletters_Edit_Link_Locator, softAssert);
			webPage.getDriver().navigate().to(Navigate_To_Newsletters_Subscription_Form_CheckBox_Save_Button_URL);
			webPage.getDriver().navigate().refresh();
			WebElement Newsletters_Subscription_CheckBox = webPage.getDriver().findElement(By.xpath(inputdata[0][5]));
			log.info(" ******************************************** verify_Account_Information_Tab_Newsletters_Save_Button : Newsletters_Subscription_CheckBox Found ******************************************** ");
			if ((!(Newsletters_Subscription_CheckBox).isSelected())) {
				log.info(" ******************************************** verify_Account_Information_Tab_Newsletters_Save_Button : Newsletters_Subscription_CheckBox is  Selected  ******************************************** ");
				commonMethods.clickElementbyXpath(webPage, News_Letters_Subscription_CheckBox, softAssert);
			} else {
				commonMethods.clickElementbyXpath(webPage, News_Letters_Subscription_CheckBox, softAssert);
				commonMethods.clickElementbyXpath(webPage, News_Letters_Subscription_CheckBox, softAssert);
			}
			if (webPage.getDriver().findElement(By.xpath(Newsletters_Save_Button)).isDisplayed())
			{
			WebElement Save_Button = webPage.getDriver().findElement(By.xpath(Newsletters_Save_Button));
			CommonMethods.waitForWebElement(By.xpath(Newsletters_Save_Button), webPage);
			Save_Button.submit();
			} else {
				commonMethods.clickElementbyXpath(webPage, Newsletters_Page_Go_Back_Link, softAssert);
				webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
				WebElement Save_Button = webPage.getDriver().findElement(By.xpath(Newsletters_Save_Button));
				CommonMethods.waitForWebElement(By.xpath(Newsletters_Save_Button), webPage);
				Save_Button.submit();
			}
			CommonMethods.waitForWebElement(By.xpath(Wait_On_Element), webPage); 
			String actualAccountInformationPageURL = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertTrue(actualAccountInformationPageURL.contains(Expected_Account_Information_Page_URL) , "Page url verification failed. Expected url : " + Expected_Account_Information_Page_URL	+ "Actual url : " + actualAccountInformationPageURL);
			String actualAccountInformationPageTitle = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(actualAccountInformationPageTitle, Expected_Account_Information_Page_Title,	"Page title verification failed. Expected title : " + Expected_Account_Information_Page_Title	+ "Actual title : " + actualAccountInformationPageTitle);
			String ActualElementName = commonMethods.getTextbyXpath(webPage,Expected_Newsletters_Subscription_Successfully_Saved_Locator, softAssert);
			softAssert.assertEquals(ActualElementName, Expected_Newsletters_Subscription_Successfully_Saved_Message,"Page Content verification failed. Expected Page Content Expected Element Name : "	+ Expected_Newsletters_Subscription_Successfully_Saved_Message + "Actual Page Content Actual Element Name : " + ActualElementName);
			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "verify_Account_Information_Tab_Newsletters_Save_Button");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 024 - Verify links in Account Information section
	 * Verify page title : Page title should 'My Account'
	 * Verify content section on account dashboard page 
	 * Verify "Account Information" section on page : Verify contact information,newsletter subscription, primary billing address & primary shipping address details of customer : Verify contact information,newsletters subscription, primary billing address & primary shipping address details of customer
	 * Verify "Edit" link for "Newsletters" : click on "Edit" link for "Contact Newsletters" It should redirect to account information form page : https://www.conns.com/customer/account/edit/
	 * Verify "Change Password" link : click on "Change Password" link : It should redirect to edit account information form page : https://www.conns.com/customer/account/edit/changepass/1/
	 * Verify "Edit Address" link for "Primary Billing Address" : click on "Edit Address" link for "Primary Billing Address" : It should redirect to contact information form : https://www.conns.com/customer/address/edit/
	 * Verify "Edit Address" link for "Primary Shipping Address": click on "Edit Address" link for "Primary Shipping Address" :It should redirect to contact information form : https://www.conns.com/customer/address/edit/ but getting redirected to https://www.conns.com/customer/address/edit/id/6296645/
	 * Verify "Manage Addresses" link for "address Book" section : click on "Manage Addresses" link : It should redirect to contact information form : https://www.conns.com/customer/address/edit/
	 */
	static int count = 0;
	@Test(priority = 338, enabled = true)
	public void verify_Links_On_Account_DashBoard_Tab_Resizeable_Menu_Links_Section() {
		SoftAssert softAssert = new SoftAssert();
		try{
			log.info("******Started verification of Links in Account Dashborad tab after login ********");
			/*String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageTitle");
			ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);*/
			String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Mobile_Links_On_Account_DashBoard_Tab_Resizeable_Menu_Links_Section");
			String[][] demo_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Web_Links_On_Account_DashBoard_Tab_Resizeable_Menu_Links_Section");
			String[][] safari_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Safari_Links_On_Account_DashBoard_Tab_Resizeable_Menu_Links_Section");
			String[][] edge_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Edge_Links_On_Account_DashBoard_Tab_Resizeable_Menu_Links_Section");
			String Navigate_To_Account_Information_Tab_Form_URL = test_data[0][7];
			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			log.info("Test Bed is : " + testBed);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			log.info("Test Type is : " + testType);
			currentTestBedName = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestBedName();
			log.info("currentTestBedName is : " + currentTestBedName);
			platform = testBed.getPlatform().getName().toUpperCase();
			String[][] input_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Links_On_Account_DashBoard_Tab_Resizeable_Menu_Links_Section_Mobile");
			String Resizeable_Account_DashBoard_Menu_Mobile_Drop_Down = input_data[0][2];
			for(int r= 0; r < test_data.length;r++)
			{	
				String Page_Expected_URL = test_data[r][4];

				String Page_Expected_Element_Name = test_data[r][5];
				String Page_Expected_Title = test_data[r][6];
				if (testType.equalsIgnoreCase("Mobile")) {
					log.info("***************************************** Account Dashboard Drop Down For Mobile Starts********************************");
					commonMethods.clickElementbyXpath(webPage, Resizeable_Account_DashBoard_Menu_Mobile_Drop_Down, softAssert);
					List<String> Page_URL_Title_Data = ConnsSignInPage.verify_Links_Resizeable_Account_Tab(test_data);
					softAssert.assertTrue(Page_URL_Title_Data.get(0).equalsIgnoreCase(Page_Expected_Title),"Account Information DashBoard Tab verification failed For Page Title. Expected_Page_Title  : "+Page_Expected_Title   +  "   Actual_Expected_Title : "+Page_URL_Title_Data.get(0));	
					softAssert.assertTrue(Page_URL_Title_Data.get(1).contains(Page_Expected_URL),"  Account Information DashBoard Tab verification failed For Page URL . Expected_Page_URL : "+Page_Expected_URL + "  Actual_Page_URL : "+Page_URL_Title_Data.get(1));
					softAssert.assertTrue(Page_URL_Title_Data.get(2).equalsIgnoreCase(Page_Expected_Element_Name) ,"  Account Information DashBoard Tab verification failed For Page Title . Expected_Page_Element   : "+ Page_Expected_Element_Name  +       "   Actual_Page_Element_Name : "+Page_URL_Title_Data.get(2));
					}
				/*else if ((currentTestBedName.equalsIgnoreCase("Safari"))&& (testType.equalsIgnoreCase("Web"))) {
					log.info("***************************************** Account Dashboard Drop Down For Web Starts********************************");
					List<String> Page_URL_Title_Data = ConnsSignInPage.verify_Links_Resizeable_Account_Tab(safari_data);
					softAssert.assertTrue(Page_URL_Title_Data.get(0).equalsIgnoreCase(Page_Expected_Title),"Account Information DashBoard Tab verification failed For Page Title. Expected_Page_Title  : "+Page_Expected_Title   +  "   Actual_Expected_Title : "+Page_URL_Title_Data.get(0));	
					softAssert.assertTrue(Page_URL_Title_Data.get(1).contains(Page_Expected_URL),"  Account Information DashBoard Tab verification failed For Page URL . Expected_Page_URL : "+Page_Expected_URL + "  Actual_Page_URL : "+Page_URL_Title_Data.get(1));
					softAssert.assertTrue(Page_URL_Title_Data.get(2).equalsIgnoreCase(Page_Expected_Element_Name) ,"  Account Information DashBoard Tab verification failed For Page Title . Expected_Page_Element   : "+ Page_Expected_Element_Name  +       "   Actual_Page_Element_Name : "+Page_URL_Title_Data.get(2));
				} */
				else if ((currentTestBedName.equalsIgnoreCase("edge")) || (currentTestBedName.equalsIgnoreCase("Safari")) && (testType.equalsIgnoreCase("Web"))) {
					log.info("***************************************** Account Dashboard Drop Down For Web Starts********************************");
					List<String> Page_URL_Title_Data = ConnsSignInPage.verify_Links_Resizeable_Account_Tab(edge_data);
					softAssert.assertTrue(Page_URL_Title_Data.get(0).equalsIgnoreCase(Page_Expected_Title),"Account Information DashBoard Tab verification failed For Page Title. Expected_Page_Title  : "+Page_Expected_Title   +  "   Actual_Expected_Title : "+Page_URL_Title_Data.get(0));	
					softAssert.assertTrue(Page_URL_Title_Data.get(1).contains(Page_Expected_URL),"  Account Information DashBoard Tab verification failed For Page URL . Expected_Page_URL : "+Page_Expected_URL + "  Actual_Page_URL : "+Page_URL_Title_Data.get(1));
					softAssert.assertTrue(Page_URL_Title_Data.get(2).equalsIgnoreCase(Page_Expected_Element_Name) ,"  Account Information DashBoard Tab verification failed For Page Title . Expected_Page_Element   : "+ Page_Expected_Element_Name  +       "   Actual_Page_Element_Name : "+Page_URL_Title_Data.get(2));
				}
				else if (testType.equalsIgnoreCase("Web")) {
					log.info("***************************************** Account Dashboard Drop Down For Web Starts********************************");
					List<String> Page_URL_Title_Data = ConnsSignInPage.verify_Links_Resizeable_Account_Tab(demo_data);
					softAssert.assertTrue(Page_URL_Title_Data.get(0).equalsIgnoreCase(Page_Expected_Title),"Account Information DashBoard Tab verification failed For Page Title. Expected_Page_Title  : "+Page_Expected_Title   +  "   Actual_Expected_Title : "+Page_URL_Title_Data.get(0));	
					softAssert.assertTrue(Page_URL_Title_Data.get(1).contains(Page_Expected_URL),"  Account Information DashBoard Tab verification failed For Page URL . Expected_Page_URL : "+Page_Expected_URL + "  Actual_Page_URL : "+Page_URL_Title_Data.get(1));
					softAssert.assertTrue(Page_URL_Title_Data.get(2).equalsIgnoreCase(Page_Expected_Element_Name) ,"  Account Information DashBoard Tab verification failed For Page Title . Expected_Page_Element   : "+ Page_Expected_Element_Name  +       "   Actual_Page_Element_Name : "+Page_URL_Title_Data.get(2));
				}

				 log.info("***************************************** Account Dashboard Drop Down For Web Count ******************************** : " +count);
			}
			softAssert.assertAll();
		}
		catch(Throwable e){
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "verify_Links_On_Account_DashBoard_Tab_Resizeable_Menu_Links_Section");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	@Test(priority = 339, enabled = false)
	public void verify_Links_On_Account_DashBoard_Tab_Pay_Bill_Links_Section() throws Exception {
		log.info("******Started verification of Links in Account Dashborad tab after login ********");
		SoftAssert softAssert = new SoftAssert();
		/*String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageTitle");
		ConnsSignInPage.verify_Account_DashBoard_Login(testdata, softAssert);*/
		String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Mobile_Pay_Your_Bill_Link_On_Account_Information_Resizeable_Menu_Section");
		String[][] demo_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Web_Pay_Your_Bill_Link_On_Account_Information_Resizeable_Menu_Section");
		//String Pay_Your_Bill_Link_Resizeable_Link = test_data[0][8];
		String Navigate_To_Account_Information_Tab_Form_URL = test_data[0][6];
		String Resizeable_Account_DashBoard_Menu_Mobile_Drop_Down_Option = test_data [0][8];
		String Resizeable_Account_DashBoard_Menu_Web_Drop_Down_Option = demo_data[0][8];
		webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
		log.info("***************************************** Account Dashboard Drop Down For Mobile Starts********************************");
		String[][] input_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Links_On_Account_DashBoard_Tab_Resizeable_Menu_Links_Section_Mobile");
		String Resizeable_Account_DashBoard_Menu_Mobile_Drop_Down = input_data[0][2];
		//commonMethods.clickElementbyXpath(webPage, Pay_Your_Bill_Link_Resizeable_Link, softAssert);
		log.info("******Started verification of Links in Pay Your Bill Page after Navigation ********" +webPage.getCurrentUrl() +"\t" +"\n"  + webPage.getPageTitle());
		for (int r = 0; r < test_data.length; r++) {
			String Page_Expected_URL = test_data[r][3];
			String Page_Expected_Element_Name = test_data[r][5];
			String Page_Expected_Title = test_data[r][7];
			try {
				if (testType.equalsIgnoreCase("Mobile")) {
				commonMethods.clickElementbyXpath(webPage, Resizeable_Account_DashBoard_Menu_Mobile_Drop_Down, softAssert);
				commonMethods.clickElementbyXpath(webPage, Resizeable_Account_DashBoard_Menu_Mobile_Drop_Down_Option, softAssert);
				List<String> Page_URL_Title_Data = ConnsSignInPage.verify_Pay_Your_Bill_Link_Account_Dashboard(test_data);
				softAssert.assertTrue(Page_URL_Title_Data.get(0).equalsIgnoreCase(Page_Expected_Title),"Account Information DashBoard Tab verification failed For Page Title. Expected_Page_Title  : "+Page_Expected_Title   +  "   Actual_Expected_Title : "+Page_URL_Title_Data.get(0));	
				softAssert.assertTrue(Page_URL_Title_Data.get(1).contains(Page_Expected_URL),"  Account Information DashBoard Tab verification failed For Page URL . Expected_Page_URL : "+Page_Expected_URL + "  Actual_Page_URL : "+Page_URL_Title_Data.get(1));
				softAssert.assertTrue(Page_URL_Title_Data.get(2).equalsIgnoreCase(Page_Expected_Element_Name) ,"  Account Information DashBoard Tab verification failed For Page Title . Expected_Page_Element   : "+ Page_Expected_Element_Name  +       "   Actual_Page_Element_Name : "+Page_URL_Title_Data.get(2));
				softAssert.assertAll();
				}else{
					commonMethods.clickElementbyXpath(webPage, Resizeable_Account_DashBoard_Menu_Web_Drop_Down_Option, softAssert);
					List<String> Page_URL_Title_Data = ConnsSignInPage.verify_Pay_Your_Bill_Link_Account_Dashboard(demo_data);
					softAssert.assertTrue(Page_URL_Title_Data.get(0).equalsIgnoreCase(Page_Expected_Title),"Account Information DashBoard Tab verification failed For Page Title. Expected_Page_Title  : "+Page_Expected_Title   +  "   Actual_Expected_Title : "+Page_URL_Title_Data.get(0));	
					softAssert.assertTrue(Page_URL_Title_Data.get(1).contains(Page_Expected_URL),"  Account Information DashBoard Tab verification failed For Page URL . Expected_Page_URL : "+Page_Expected_URL + "  Actual_Page_URL : "+Page_URL_Title_Data.get(1));
					softAssert.assertTrue(Page_URL_Title_Data.get(2).equalsIgnoreCase(Page_Expected_Element_Name) ,"  Account Information DashBoard Tab verification failed For Page Title . Expected_Page_Element   : "+ Page_Expected_Element_Name  +       "   Actual_Page_Element_Name : "+Page_URL_Title_Data.get(2));
					softAssert.assertAll();
				}
			} catch (Throwable e) {
				e.printStackTrace();
				mainPage.getScreenShotForFailure(webPage, "verify_Links_On_Account_DashBoard_Tab_Pay_Bill_Links_Section");
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
		}
	}

	@Test(priority = 340, enabled = true)
	public void verify_My_Orders_Link_On_Account_Information_Resizeable_Menu_Section() throws Exception {
		log.info("******Started verification of Links in Account Dashborad tab after login ********");
		SoftAssert softAssert = new SoftAssert();
		try{
			log.info("******Started verification of Links in Account Dashborad tab after login ********");
			/*String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageTitle");
			ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);	*/		
			String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_My_Orders_Mobile_Links_On_Account_DashBoard_Tab_Resizeable_Menu_Links_Section");
			String[][] demo_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_My_Orders_Web_Links_On_Account_DashBoard_Tab_Resizeable_Menu_Links_Section");
			String Navigate_To_Account_Information_Tab_Form_URL = test_data[0][7];
			webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
			//webPage.getCurrentUrl(); //For Safari
			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			log.info("Test Bed is : " + testBed);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			log.info("Test Type is : " + testType);
			platform = testBed.getPlatform().getName().toUpperCase();
			log.info("***************************************** Account Dashboard Drop Down For Mobile Starts********************************");
			String[][] input_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Links_On_Account_DashBoard_Tab_Resizeable_Menu_Links_Section_Mobile");
			String Resizeable_Account_DashBoard_Menu_Mobile_Drop_Down = input_data[0][2];
			for(int r= 0; r < test_data.length;r++)
			{	String Page_Expected_URL = test_data[r][4];
				String Page_Expected_Element_Name = test_data[r][5];
				String Page_Expected_Title = test_data[r][6];
				String My_Order_Expected_Page_Element_Name = test_data[r][9]; 
				if (testType.equalsIgnoreCase("Mobile")) {
					commonMethods.clickElementbyXpath(webPage, Resizeable_Account_DashBoard_Menu_Mobile_Drop_Down, softAssert);
					List<String> Page_URL_Title_Data = ConnsSignInPage.verify_My_Orders_Links_Resizeable_Account_Tab(test_data);
					softAssert.assertTrue(Page_URL_Title_Data.get(0).equalsIgnoreCase(Page_Expected_Title),"Account Information DashBoard Tab verification failed For Page Title. Expected_Page_Title  : "+Page_Expected_Title   +  "   Actual_Expected_Title : "+Page_URL_Title_Data.get(0));	
					softAssert.assertTrue(Page_URL_Title_Data.get(1).contains(Page_Expected_URL),"  Account Information DashBoard Tab verification failed For Page URL . Expected_Page_URL : "+Page_Expected_URL + "  Actual_Page_URL : "+Page_URL_Title_Data.get(1));
					softAssert.assertTrue(Page_URL_Title_Data.get(2).equalsIgnoreCase(Page_Expected_Element_Name) ,"  Account Information DashBoard Tab verification failed For Page Title . Expected_Page_Element   : "+ Page_Expected_Element_Name  +       "   Actual_Page_Element_Name : "+Page_URL_Title_Data.get(2));
					softAssert.assertTrue(Page_URL_Title_Data.get(3).equalsIgnoreCase(My_Order_Expected_Page_Element_Name) ,"  Account Information DashBoard Tab verification failed For Page Title . My_Order_Page_Expected_Element_Name   : "+ My_Order_Expected_Page_Element_Name  +       "   My_Order_Actual_Page_Element_Name : "+Page_URL_Title_Data.get(3));
					}
				else {
					//commonMethods.clickElementbyXpath(webPage, Resizeable_Account_DashBoard_Menu_Mobile_Drop_Down, softAssert);
					List<String> Page_URL_Title_Data = ConnsSignInPage.verify_My_Orders_Links_Resizeable_Account_Tab(demo_data);
					softAssert.assertTrue(Page_URL_Title_Data.get(0).equalsIgnoreCase(Page_Expected_Title),"Account Information DashBoard Tab verification failed For Page Title. Expected_Page_Title  : "+Page_Expected_Title   +  "   Actual_Expected_Title : "+Page_URL_Title_Data.get(0));	
					softAssert.assertTrue(Page_URL_Title_Data.get(1).contains(Page_Expected_URL),"  Account Information DashBoard Tab verification failed For Page URL . Expected_Page_URL : "+Page_Expected_URL + "  Actual_Page_URL : "+Page_URL_Title_Data.get(1));
					softAssert.assertTrue(Page_URL_Title_Data.get(2).equalsIgnoreCase(Page_Expected_Element_Name) ,"  Account Information DashBoard Tab verification failed For Page Title . Expected_Page_Element   : "+ Page_Expected_Element_Name  +       "   Actual_Page_Element_Name : "+Page_URL_Title_Data.get(2));
					softAssert.assertTrue(Page_URL_Title_Data.get(3).equalsIgnoreCase(My_Order_Expected_Page_Element_Name) ,"  Account Information DashBoard Tab verification failed For Page Title . My_Order_Page_Expected_Element_Name   : "+ My_Order_Expected_Page_Element_Name  +       "   My_Order_Actual_Page_Element_Name : "+Page_URL_Title_Data.get(3));
				}
				log.info("***************************************** Account Dashboard Drop Down For Web Count ******************************** : " +count);
			}
			softAssert.assertAll();
		}
		catch(Throwable e){
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "verify_My_Orders_Link_On_Account_Information_Resizeable_Menu_Section");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	
	
	
	@Test(priority = 341, enabled = true)
	public void verify_My_Wish_List_Link_On_Account_Information_Resizeable_Menu_Section() throws Exception {
		log.info("******Started verification of Links in Account Dashborad tab after login ********");
		SoftAssert softAssert = new SoftAssert();
		JavascriptExecutor jse = (JavascriptExecutor)webPage.getDriver();
		try{
			log.info("******Started verification of Links in Account Dashborad tab after login ********");
			/*String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageTitle");
			ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);*/
			String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_My_Wish_List_Mobile_Links_On_Account_DashBoard_Tab_Resizeable_Menu_Links_Section");
			String[][] demo_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_My_Wish_List_Web_Links_On_Account_DashBoard_Tab_Resizeable_Menu_Links_Section");
			String [][] mobile_validation_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_My_Wish_List_Page_Mobile_Links_Section");
			String [][] web_validation_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_My_Wish_List_Page_Web_Links_Section");
			String Navigate_To_Account_Information_Tab_Form_URL = test_data[0][7];
			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			log.info("Test Bed is : " + testBed);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			log.info("Test Type is : " + testType);
			platform = testBed.getPlatform().getName().toUpperCase();
			log.info("***************************************** Account Dashboard Drop Down For Mobile Starts********************************");
			String[][] input_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Links_On_Account_DashBoard_Tab_Resizeable_Menu_Links_Section_Mobile");
			String Resizeable_Account_DashBoard_Menu_Mobile_Drop_Down = input_data[0][2];
			String Resizeable_Account_DashBoard_Menu_Mobile_Drop_Down_Wish_List_Option = mobile_validation_data[0][2];
			//String NameofTestCase = mobile_validation_data[3][0];
			//webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
			//webPage.getCurrentUrl(); //For Safari
			for(int r= 0; r < test_data.length;r++)
			{	String Page_Expected_URL = test_data[r][4];
				String Page_Expected_Element_Name = test_data[r][5];
				String Page_Expected_Title = test_data[r][6];
				String Expected_My_Wishlist_Product_Details_And_Comment_Element_Text = test_data[r][9]; 
				String Expected_My_Wishlist_Add_to_Cart_Element_Text = test_data[r][11]; 
				String Expected_My_Wishlist_Mango_Dining_Element_Text = test_data[r][13];
				String Expected_My_Wishlist_Mango_Dining_Price_Element_Text = test_data[r][15];
				String Expected_My_Wishlist_Mango_Dining_Regular_Price_Element_Text = test_data[r][17];
				//String Expected_My_Wishlist_Mango_Dining_Special_Price_Element_Text = test_data[r][19];
				String Expected_My_Wishlist_Mango_Dining_wishliststyle_Element_Text = test_data[r][21];
				String Expected_My_Wishlist_Mango_Dining_Click_Here_For_Details_Element_Text = test_data[r][23];
				String Expected_My_Wishlist_Mango_Dining_Availability_In_Stock_Element_Text = test_data[r][25];
				String Expected_My_Wishlist_Mango_Dining_Availability_In_Store_Element_Text = test_data[r][27];
				String Expected_My_Wishlist_Mango_Dining_Add_To_Cart_Alt_Quantity_Element_Text = test_data[r][29];
				String Expected_My_Wishlist_Mango_Dining_Text_Area_Element_Text = test_data[r][31];
				String Expected_My_Wishlist_Mango_Dining_Edit_Hyper_Link_Element_Text = test_data[r][33];
				String Expected_My_Wishlist_Mango_Dining_Remove_Hyper_Link_Element_Text = test_data[r][35];
				String Expected_My_Wishlist_Mango_Dining_UPDATE_WISHLIST_BUTTON_Element_Text = test_data[r][37];
				String Expected_My_Wishlist_Mango_Dining_SHARE_WISHLIST_BUTTON_Element_Text = test_data[r][39];
				String Expected_My_Wishlist_Mango_Dining_GO_BACK_HYPERLINK_Element_Text =  test_data[r][41];
				if (testType.equalsIgnoreCase("Mobile")) {
					commonMethods.clickElementbyXpath(webPage, Resizeable_Account_DashBoard_Menu_Mobile_Drop_Down, softAssert);
					List<String> Page_URL_Title_Data = ConnsSignInPage.verify_My_Wishlist_Links_Resizeable_Account_Tab(test_data);
					softAssert.assertTrue(Page_URL_Title_Data.get(0).equalsIgnoreCase(Page_Expected_Title),                                                        "  Account Information DashBoard Tab verification failed For Page Title.  Expected_Page_Title                                             			    : "+ Page_Expected_Title                                            	    	+       "   Actual_Expected_Title                                       				    : "+ Page_URL_Title_Data.get(0));	
					softAssert.assertTrue(Page_URL_Title_Data.get(1).contains(Page_Expected_URL),                                                                  "  Account Information DashBoard Tab verification failed For Page URL .   Expected_Page_URL                                                              : "+ Page_Expected_URL                                             		     	+       "   Actual_Page_URL                                             					: "+ Page_URL_Title_Data.get(1));
					softAssert.assertTrue(Page_URL_Title_Data.get(2).equalsIgnoreCase(Page_Expected_Element_Name) ,                                                "  Account Information DashBoard Tab verification failed For Page Title . Expected_Page_Element                                         				    : "+ Page_Expected_Element_Name                                   		     	+       "   Actual_Page_Element_Name                                    					: "+ Page_URL_Title_Data.get(2));
					softAssert.assertTrue(Page_URL_Title_Data.get(3).equalsIgnoreCase(Expected_My_Wishlist_Product_Details_And_Comment_Element_Text) ,             "  Account Information DashBoard Tab verification failed For Page Title . Expected_My_Wishlist_Product_Details_And_Comment_Element_Text  				: "+ Expected_My_Wishlist_Product_Details_And_Comment_Element_Text 		        +       "   Actual_My_Wishlist_Product_Details_And_Comment_Element_Text 					: "+ Page_URL_Title_Data.get(3));
					softAssert.assertTrue(Page_URL_Title_Data.get(4).equalsIgnoreCase(Expected_My_Wishlist_Add_to_Cart_Element_Text) ,                             "  Account Information DashBoard Tab verification failed For Page Title . Expected_My_Wishlist_Add_to_Cart_Element_Text                 				    : "+ Expected_My_Wishlist_Add_to_Cart_Element_Text               		     	+       "   Actual_My_Wishlist_Add_to_Cart_Element_Text                 					: "+ Page_URL_Title_Data.get(4));
					softAssert.assertTrue(Page_URL_Title_Data.get(5).equalsIgnoreCase(Expected_My_Wishlist_Mango_Dining_Element_Text) ,                            "  Account Information DashBoard Tab verification failed For Page Title . Expected_My_Wishlist_Mango_Dining_Element_Text                  			    : "+ Expected_My_Wishlist_Mango_Dining_Element_Text               		     	+       "   Actual_My_Wishlist_Mango_Dining_Element_Text               						: "+ Page_URL_Title_Data.get(5));
					softAssert.assertTrue(Page_URL_Title_Data.get(6).contains(Expected_My_Wishlist_Mango_Dining_Price_Element_Text) ,                      		   "  Account Information DashBoard Tab verification failed For Page Title . Expected_My_Wishlist_Mango_Dining_Price_Element_Text                           : "+ Expected_My_Wishlist_Mango_Dining_Price_Element_Text         		     	+       "   Actual_My_Wishlist_Mango_Dining_Price_Element_Text                              : "+ Page_URL_Title_Data.get(6));
					softAssert.assertTrue(Page_URL_Title_Data.get(7).contains(Expected_My_Wishlist_Mango_Dining_Regular_Price_Element_Text) ,                      "  Account Information DashBoard Tab verification failed For Page Title . Expected_My_Wishlist_Mango_Dining_Regular_Price_Element_Text                   : "+ Expected_My_Wishlist_Mango_Dining_Regular_Price_Element_Text   	     	+       "   Actual_My_Wishlist_Mango_Dining_Regular_Price_Element_Text                      : "+ Page_URL_Title_Data.get(7));
					//softAssert.assertTrue(Page_URL_Title_Data.get(8).equalsIgnoreCase(Expected_My_Wishlist_Mango_Dining_Special_Price_Element_Text) ,            "  Account Information DashBoard Tab verification failed For Page Title . Expected_My_Wishlist_Mango_Dining_Special_Price_Element_Text                   : "+ Expected_My_Wishlist_Mango_Dining_Special_Price_Element_Text               +       "   Actual_My_Wishlist_Mango_Dining_Special_Price_Element_Text                      : "+ Page_URL_Title_Data.get(8));
					softAssert.assertTrue(Page_URL_Title_Data.get(9).equalsIgnoreCase(Expected_My_Wishlist_Mango_Dining_wishliststyle_Element_Text) ,              "  Account Information DashBoard Tab verification failed For Page Title . Expected_My_Wishlist_Mango_Dining_wishliststyle_Element_Text                   : "+ Expected_My_Wishlist_Mango_Dining_wishliststyle_Element_Text               +       "   Actual_My_Wishlist_Mango_Dining_wishliststyle_Element_Text                      : "+ Page_URL_Title_Data.get(9));
					softAssert.assertTrue(Page_URL_Title_Data.get(10).equalsIgnoreCase(Expected_My_Wishlist_Mango_Dining_Click_Here_For_Details_Element_Text) ,    "  Account Information DashBoard Tab verification failed For Page Title . Expected_My_Wishlist_Mango_Dining_Click_Here_For_Details_Element_Text          : "+ Expected_My_Wishlist_Mango_Dining_Click_Here_For_Details_Element_Text      +       "   Actual_My_Wishlist_Mango_Dining_Click_Here_For_Details_Element_Text             : "+ Page_URL_Title_Data.get(10));
					//softAssert.assertTrue(Page_URL_Title_Data.get(11).equalsIgnoreCase(Expected_My_Wishlist_Mango_Dining_Availability_In_Stock_Element_Text) ,     "  Account Information DashBoard Tab verification failed For Page Title . Expected_My_Wishlist_Mango_Dining_Availability_In_Stock_Element_Text           : "+ Expected_My_Wishlist_Mango_Dining_Availability_In_Stock_Element_Text       +       "   Actual_My_Wishlist_Mango_Dining_Availability_In_Stock_Element_Text              : "+ Page_URL_Title_Data.get(11));
					softAssert.assertTrue(Page_URL_Title_Data.get(12).equalsIgnoreCase(Expected_My_Wishlist_Mango_Dining_Availability_In_Store_Element_Text) ,     "  Account Information DashBoard Tab verification failed For Page Title . Expected_My_Wishlist_Mango_Dining_Availability_In_Store_Element_Text           : "+ Expected_My_Wishlist_Mango_Dining_Availability_In_Store_Element_Text       +       "   Actual_My_Wishlist_Mango_Dining_Availability_In_Store_Element_Text              : "+ Page_URL_Title_Data.get(12));
					softAssert.assertTrue(Page_URL_Title_Data.get(13).equalsIgnoreCase(Expected_My_Wishlist_Mango_Dining_Add_To_Cart_Alt_Quantity_Element_Text) ,  "  Account Information DashBoard Tab verification failed For Page Title . Expected_My_Wishlist_Mango_Dining_Add_To_Cart_Alt_Quantity_Element_Text        : "+ Expected_My_Wishlist_Mango_Dining_Add_To_Cart_Alt_Quantity_Element_Text    +       "   Actual_My_Wishlist_Mango_Dining_Add_To_Cart_Alt_Quantity_Element_Text           : "+ Page_URL_Title_Data.get(13));
					//softAssert.assertTrue(Page_URL_Title_Data.get(14).equalsIgnoreCase(Expected_My_Wishlist_Mango_Dining_Text_Area_Element_Text) ,                 "  Account Information DashBoard Tab verification failed For Page Title . Expected_My_Wishlist_Mango_Dining_Text_Area_Element_Text                       : "+ Expected_My_Wishlist_Mango_Dining_Text_Area_Element_Text 			        +       "   Actual_My_Wishlist_Mango_Dining_Text_Area_Element_Text                          : "+ Page_URL_Title_Data.get(14));
					softAssert.assertTrue(Page_URL_Title_Data.get(15).equalsIgnoreCase(Expected_My_Wishlist_Mango_Dining_Edit_Hyper_Link_Element_Text) ,           "  Account Information DashBoard Tab verification failed For Page Title . Expected_My_Wishlist_Mango_Dining_Edit_Hyper_Link_Element_Text                 : "+ Expected_My_Wishlist_Mango_Dining_Edit_Hyper_Link_Element_Text 		 	+       "   Actual_My_Wishlist_Mango_Dining_Edit_Hyper_Link_Element_Text                    : "+ Page_URL_Title_Data.get(15));
					softAssert.assertTrue(Page_URL_Title_Data.get(16).equalsIgnoreCase(Expected_My_Wishlist_Mango_Dining_Remove_Hyper_Link_Element_Text) ,         "  Account Information DashBoard Tab verification failed For Page Title . Expected_My_Wishlist_Mango_Dining_Remove_Hyper_Link_Element_Text               : "+ Expected_My_Wishlist_Mango_Dining_Remove_Hyper_Link_Element_Text 		 	+       "   Actual_My_Wishlist_Mango_Dining_Remove_Hyper_Link_Element_Text                  : "+ Page_URL_Title_Data.get(16));
					softAssert.assertTrue(Page_URL_Title_Data.get(17).equalsIgnoreCase(Expected_My_Wishlist_Mango_Dining_UPDATE_WISHLIST_BUTTON_Element_Text) ,    "  Account Information DashBoard Tab verification failed For Page Title . Expected_My_Wishlist_Mango_Dining_UPDATE_WISHLIST_BUTTON_Element_Text          : "+ Expected_My_Wishlist_Mango_Dining_UPDATE_WISHLIST_BUTTON_Element_Text   	+       "   Actual_My_Wishlist_Mango_Dining_UPDATE_WISHLIST_BUTTON_Element_Text             : "+ Page_URL_Title_Data.get(17));
					softAssert.assertTrue(Page_URL_Title_Data.get(18).equalsIgnoreCase(Expected_My_Wishlist_Mango_Dining_SHARE_WISHLIST_BUTTON_Element_Text) ,     "  Account Information DashBoard Tab verification failed For Page Title . Expected_My_Wishlist_Mango_Dining_SHARE_WISHLIST_BUTTON_Element_Text           : "+ Expected_My_Wishlist_Mango_Dining_SHARE_WISHLIST_BUTTON_Element_Text  	 	+       "   Actual_My_Wishlist_Mango_Dining_SHARE_WISHLIST_BUTTON_Element_Text              : "+ Page_URL_Title_Data.get(18));
					softAssert.assertTrue(Page_URL_Title_Data.get(19).equalsIgnoreCase(Expected_My_Wishlist_Mango_Dining_GO_BACK_HYPERLINK_Element_Text) ,         "  Account Information DashBoard Tab verification failed For Page Title . Expected_My_Wishlist_Mango_Dining_GO_BACK_HYPERLINK_Element_Text               : "+ Expected_My_Wishlist_Mango_Dining_GO_BACK_HYPERLINK_Element_Text 		 	+       "   Actual_My_Wishlist_Mango_Dining_GO_BACK_HYPERLINK_Element_Text                  : "+ Page_URL_Title_Data.get(19));
				}
				else {
					List<String> Page_URL_Title_Data = ConnsSignInPage.verify_My_Wishlist_Links_Resizeable_Account_Tab(demo_data);
					softAssert.assertTrue(Page_URL_Title_Data.get(0).contains(Page_Expected_Title),                                                               "  Account Information DashBoard Tab verification failed For Expected_Page_Title.  Expected_Page_Title                                             			                                                     : "+Page_Expected_Title                                            		            +       "   Actual_Expected_Title                                       			: "+Page_URL_Title_Data.get(0));	
					softAssert.assertTrue(Page_URL_Title_Data.get(1).contains(Page_Expected_URL),                                                                 "  Account Information DashBoard Tab verification failed For Expected_Page_URL .   Expected_Page_URL                                                                                                               : "+Page_Expected_URL                                             			            +       "   Actual_Page_URL                                             			: "+Page_URL_Title_Data.get(1));
					softAssert.assertTrue(Page_URL_Title_Data.get(2).contains(Page_Expected_Element_Name),                                                        "  Account Information DashBoard Tab verification failed For Expected_Page_Element . Expected_Page_Element                                         				                                                 : "+ Page_Expected_Element_Name                                   			            +       "   Actual_Page_Element_Name                                    		    : "+Page_URL_Title_Data.get(2));
					softAssert.assertTrue(Page_URL_Title_Data.get(3).contains(Expected_My_Wishlist_Product_Details_And_Comment_Element_Text) ,                    "  Account Information DashBoard Tab verification failed For Expected_My_Wishlist_Product_Details_And_Comment_Element_Text . Expected_My_Wishlist_Product_Details_And_Comment_Element_Text  			             : "+ Expected_My_Wishlist_Product_Details_And_Comment_Element_Text 		            +       "   Actual_My_Wishlist_Product_Details_And_Comment_Element_Text 		    : "+Page_URL_Title_Data.get(3));
					softAssert.assertTrue(Page_URL_Title_Data.get(4).contains(Expected_My_Wishlist_Add_to_Cart_Element_Text),                                     "  Account Information DashBoard Tab verification failed For Expected_My_Wishlist_Add_to_Cart_Element_Text . Expected_My_Wishlist_Add_to_Cart_Element_Text                 				                         : "+ Expected_My_Wishlist_Add_to_Cart_Element_Text               			            +       "   Actual_My_Wishlist_Add_to_Cart_Element_Text                 		    : "+Page_URL_Title_Data.get(4));
					softAssert.assertTrue(Page_URL_Title_Data.get(5).contains(Expected_My_Wishlist_Mango_Dining_Element_Text),                                    "  Account Information DashBoard Tab verification failed For Expected_My_Wishlist_Mango_Dining_Element_Text . Expected_My_Wishlist_Mango_Dining_Element_Text                  			                         : "+ Expected_My_Wishlist_Mango_Dining_Element_Text               			            +       "   Actual_My_Wishlist_Mango_Dining_Element_Text               			    : "+Page_URL_Title_Data.get(5));
					softAssert.assertTrue(Page_URL_Title_Data.get(6).contains(Expected_My_Wishlist_Mango_Dining_Price_Element_Text),                              "  Account Information DashBoard Tab verification failed For Expected_My_Wishlist_Mango_Dining_Price_Element_Text . Expected_My_Wishlist_Mango_Dining_Price_Element_Text                                           : "+ Expected_My_Wishlist_Mango_Dining_Price_Element_Text         			            +       "   Actual_My_Wishlist_Mango_Dining_Price_Element_Text                      : "+Page_URL_Title_Data.get(6));
					softAssert.assertTrue(Page_URL_Title_Data.get(7).contains(Expected_My_Wishlist_Mango_Dining_Regular_Price_Element_Text),                      "  Account Information DashBoard Tab verification failed For Expected_My_Wishlist_Mango_Dining_Regular_Price_Element_Text . Expected_My_Wishlist_Mango_Dining_Regular_Price_Element_Text                           : "+ Expected_My_Wishlist_Mango_Dining_Regular_Price_Element_Text   		            +       "   Actual_My_Wishlist_Mango_Dining_Regular_Price_Element_Text              : "+Page_URL_Title_Data.get(7));
					//softAssert.assertTrue(Page_URL_Title_Data.get(8).contains(Expected_My_Wishlist_Mango_Dining_Special_Price_Element_Text),                      "  Account Information DashBoard Tab verification failed For Expected_My_Wishlist_Mango_Dining_Special_Price_Element_Text . Expected_My_Wishlist_Mango_Dining_Special_Price_Element_Text                           : "+ Expected_My_Wishlist_Mango_Dining_Special_Price_Element_Text           		    +       "   Actual_My_Wishlist_Mango_Dining_Special_Price_Element_Text              : "+Page_URL_Title_Data.get(8));
					softAssert.assertTrue(Page_URL_Title_Data.get(9).equalsIgnoreCase(Expected_My_Wishlist_Mango_Dining_wishliststyle_Element_Text),              "  Account Information DashBoard Tab verification failed For Expected_My_Wishlist_Mango_Dining_wishliststyle_Element_Text . Expected_My_Wishlist_Mango_Dining_wishliststyle_Element_Text                           : "+ Expected_My_Wishlist_Mango_Dining_wishliststyle_Element_Text            			+       "   Actual_My_Wishlist_Mango_Dining_wishliststyle_Element_Text              : "+Page_URL_Title_Data.get(9));
					softAssert.assertTrue(Page_URL_Title_Data.get(10).equalsIgnoreCase(Expected_My_Wishlist_Mango_Dining_Click_Here_For_Details_Element_Text),    "  Account Information DashBoard Tab verification failed For Expected_My_Wishlist_Mango_Dining_Click_Here_For_Details_Element_Text . Expected_My_Wishlist_Mango_Dining_Click_Here_For_Details_Element_Text         : "+ Expected_My_Wishlist_Mango_Dining_Click_Here_For_Details_Element_Text  		 	+       "   Actual_My_Wishlist_Mango_Dining_Click_Here_For_Details_Element_Text     : "+Page_URL_Title_Data.get(10));
					//softAssert.assertTrue(Page_URL_Title_Data.get(11).equalsIgnoreCase(Expected_My_Wishlist_Mango_Dining_Availability_In_Stock_Element_Text),     "  Account Information DashBoard Tab verification failed For Expected_My_Wishlist_Mango_Dining_Availability_In_Stock_Element_Text . Expected_My_Wishlist_Mango_Dining_Availability_In_Stock_Element_Text           : "+ Expected_My_Wishlist_Mango_Dining_Availability_In_Stock_Element_Text            	+       "   Actual_My_Wishlist_Mango_Dining_Availability_In_Stock_Element_Text      : "+Page_URL_Title_Data.get(11));
					softAssert.assertTrue(Page_URL_Title_Data.get(12).equalsIgnoreCase(Expected_My_Wishlist_Mango_Dining_Availability_In_Store_Element_Text),     "  Account Information DashBoard Tab verification failed For Expected_My_Wishlist_Mango_Dining_Availability_In_Store_Element_Text . Expected_My_Wishlist_Mango_Dining_Availability_In_Store_Element_Text           : "+ Expected_My_Wishlist_Mango_Dining_Availability_In_Store_Element_Text           	+       "   Actual_My_Wishlist_Mango_Dining_Availability_In_Store_Element_Text      : "+Page_URL_Title_Data.get(12));
					softAssert.assertTrue(Page_URL_Title_Data.get(13).equalsIgnoreCase(Expected_My_Wishlist_Mango_Dining_Add_To_Cart_Alt_Quantity_Element_Text),  "  Account Information DashBoard Tab verification failed For Expected_My_Wishlist_Mango_Dining_Add_To_Cart_Alt_Quantity_Element_Text . Expected_My_Wishlist_Mango_Dining_Add_To_Cart_Alt_Quantity_Element_Text     : "+ Expected_My_Wishlist_Mango_Dining_Add_To_Cart_Alt_Quantity_Element_Text   		+       "   Actual_My_Wishlist_Mango_Dining_Add_To_Cart_Alt_Quantity_Element_Text   : "+Page_URL_Title_Data.get(13));
					//softAssert.assertTrue(Page_URL_Title_Data.get(14).equalsIgnoreCase(Expected_My_Wishlist_Mango_Dining_Text_Area_Element_Text),                 "  Account Information DashBoard Tab verification failed For Expected_My_Wishlist_Mango_Dining_Text_Area_Element_Text . Expected_My_Wishlist_Mango_Dining_Text_Area_Element_Text                                   : "+ Expected_My_Wishlist_Mango_Dining_Text_Area_Element_Text 			                +       "   Actual_My_Wishlist_Mango_Dining_Text_Area_Element_Text                  : "+Page_URL_Title_Data.get(14));
					softAssert.assertTrue(Page_URL_Title_Data.get(15).equalsIgnoreCase(Expected_My_Wishlist_Mango_Dining_Edit_Hyper_Link_Element_Text),           "  Account Information DashBoard Tab verification failed For Expected_My_Wishlist_Mango_Dining_Edit_Hyper_Link_Element_Text . Expected_My_Wishlist_Mango_Dining_Edit_Hyper_Link_Element_Text                       : "+ Expected_My_Wishlist_Mango_Dining_Edit_Hyper_Link_Element_Text 			        +       "   Actual_My_Wishlist_Mango_Dining_Edit_Hyper_Link_Element_Text            : "+Page_URL_Title_Data.get(15));
					softAssert.assertTrue(Page_URL_Title_Data.get(16).equalsIgnoreCase(Expected_My_Wishlist_Mango_Dining_Remove_Hyper_Link_Element_Text),         "  Account Information DashBoard Tab verification failed For Expected_My_Wishlist_Mango_Dining_Remove_Hyper_Link_Element_Text . Expected_My_Wishlist_Mango_Dining_Remove_Hyper_Link_Element_Text                   : "+ Expected_My_Wishlist_Mango_Dining_Remove_Hyper_Link_Element_Text 			        +       "   Actual_My_Wishlist_Mango_Dining_Remove_Hyper_Link_Element_Text          : "+Page_URL_Title_Data.get(16));
					softAssert.assertTrue(Page_URL_Title_Data.get(17).equalsIgnoreCase(Expected_My_Wishlist_Mango_Dining_UPDATE_WISHLIST_BUTTON_Element_Text),    "  Account Information DashBoard Tab verification failed For Expected_My_Wishlist_Mango_Dining_UPDATE_WISHLIST_BUTTON_Element_Text . Expected_My_Wishlist_Mango_Dining_UPDATE_WISHLIST_BUTTON_Element_Text         : "+ Expected_My_Wishlist_Mango_Dining_UPDATE_WISHLIST_BUTTON_Element_Text  		    +       "   Actual_My_Wishlist_Mango_Dining_UPDATE_WISHLIST_BUTTON_Element_Text     : "+Page_URL_Title_Data.get(17));
					softAssert.assertTrue(Page_URL_Title_Data.get(18).equalsIgnoreCase(Expected_My_Wishlist_Mango_Dining_SHARE_WISHLIST_BUTTON_Element_Text),     "  Account Information DashBoard Tab verification failed For Expected_My_Wishlist_Mango_Dining_SHARE_WISHLIST_BUTTON_Element_Text . Expected_My_Wishlist_Mango_Dining_SHARE_WISHLIST_BUTTON_Element_Text           : "+ Expected_My_Wishlist_Mango_Dining_SHARE_WISHLIST_BUTTON_Element_Text  		    +       "   Actual_My_Wishlist_Mango_Dining_SHARE_WISHLIST_BUTTON_Element_Text      : "+Page_URL_Title_Data.get(18));
					softAssert.assertTrue(Page_URL_Title_Data.get(19).equalsIgnoreCase(Expected_My_Wishlist_Mango_Dining_GO_BACK_HYPERLINK_Element_Text),         "  Account Information DashBoard Tab verification failed For Expected_My_Wishlist_Mango_Dining_GO_BACK_HYPERLINK_Element_Text . Expected_My_Wishlist_Mango_Dining_GO_BACK_HYPERLINK_Element_Text                   : "+ Expected_My_Wishlist_Mango_Dining_GO_BACK_HYPERLINK_Element_Text 			        +       "   Actual_My_Wishlist_Mango_Dining_GO_BACK_HYPERLINK_Element_Text          : "+Page_URL_Title_Data.get(19));
				}
			}
				if (testType.equalsIgnoreCase("Mobile")) {
					for(int r1 = 0; r1 < mobile_validation_data.length;r1++)
				{	String My_Wish_List_Mobile_Page_Expected_URL = mobile_validation_data[r1][4];
					String My_Wish_List_Mobile_Page_Expected_Element_Name = mobile_validation_data[r1][5];
					String My_Wish_List_Mobile_Page_Expected_Title = mobile_validation_data[r1][6];
					String My_Wish_List_Mobile_Page_Element_Locator_Text =  mobile_validation_data[r1][9];
					String NameofTestCase = mobile_validation_data[r1][0];
					List<String> Page_URL_Title_Data_My_Wish_List_Links  = ConnsSignInPage.verify_My_Wish_List_Page_Links_Mobile(mobile_validation_data);
					softAssert.assertTrue(Page_URL_Title_Data_My_Wish_List_Links.get(0).equalsIgnoreCase(My_Wish_List_Mobile_Page_Expected_Title),                 " Account Information DashBoard Tab verification failed For Page Title.  My_Wish_List_Page_Expected_Title                                              : "+ My_Wish_List_Mobile_Page_Expected_Title                                     +       "   My_Wish_List_Page_Actual_Title                                       			: "+ Page_URL_Title_Data_My_Wish_List_Links.get(0));	
					softAssert.assertTrue(Page_URL_Title_Data_My_Wish_List_Links.get(1).contains(My_Wish_List_Mobile_Page_Expected_URL),                           " Account Information DashBoard Tab verification failed For Page URL .   My_Wish_List_Page_Expected_URL                                                : "+ My_Wish_List_Mobile_Page_Expected_URL                                       +       "   My_Wish_List_Page_Actual_URL                                             		: "+ Page_URL_Title_Data_My_Wish_List_Links.get(1));
					softAssert.assertTrue(Page_URL_Title_Data_My_Wish_List_Links.get(2).equalsIgnoreCase(My_Wish_List_Mobile_Page_Expected_Element_Name),          " Account Information DashBoard Tab verification failed For Page Title . My_Wish_List_Page_Expected_Element_Name                                       : "+ My_Wish_List_Mobile_Page_Expected_Element_Name                              +       "   My_Wish_List_Page_Actual_Element_Name                                    		: "+ Page_URL_Title_Data_My_Wish_List_Links.get(2));
					softAssert.assertTrue(Page_URL_Title_Data_My_Wish_List_Links.get(3).equalsIgnoreCase(My_Wish_List_Mobile_Page_Element_Locator_Text),           " Account Information DashBoard Tab verification failed For Page Title . My_Wish_List_Mobile_Page_Element_Locator_Text  				                  : "+ My_Wish_List_Mobile_Page_Element_Locator_Text 		                       +       "   Actual_My_Wish_List_Mobile_Page_Element_Locator_Text 					        : "+ Page_URL_Title_Data_My_Wish_List_Links.get(3));
				}
				}else
				{ for(int r1 = 0; r1 < web_validation_data.length;r1++)
				{	String My_Wish_List_Web_Page_Expected_URL = web_validation_data[r1][4];
					String My_Wish_List_Web_Page_Expected_Element_Name = web_validation_data[r1][5];
					String My_Wish_List_Web_Page_Expected_Title = web_validation_data[r1][6];
					String My_Wish_List_Web_Page_Element_Locator_Text =  web_validation_data[r1][9];
					String Account_DashBoard_Mobile_Drop_Down_Link = web_validation_data[r1][2];
					commonMethods.clickElementbyXpath(webPage, Resizeable_Account_DashBoard_Menu_Mobile_Drop_Down_Wish_List_Option, softAssert);
					List<String> Page_URL_Title_Data_My_Wish_List_Links  = ConnsSignInPage.verify_My_Wish_List_Page_Links_Web(web_validation_data);
					softAssert.assertTrue(Page_URL_Title_Data_My_Wish_List_Links.get(0).equalsIgnoreCase(My_Wish_List_Web_Page_Expected_Title),                    "  Account Information DashBoard Tab verification failed For Page Title.  My_Wish_List_Web_Page_Expected_Title                                     : "+ My_Wish_List_Web_Page_Expected_Title                                            	       +       "   My_Wish_List_Web_Page_Actual_Title                                       		: "+ Page_URL_Title_Data_My_Wish_List_Links.get(0));	
					softAssert.assertTrue(Page_URL_Title_Data_My_Wish_List_Links.get(1).contains(My_Wish_List_Web_Page_Expected_URL),                              "  Account Information DashBoard Tab verification failed For Page URL .   My_Wish_List_Web_Page_Expected_URL                                       : "+ My_Wish_List_Web_Page_Expected_URL                                             		   +       "   My_Wish_List_Web_Page_Actual_URL                                             	: "+ Page_URL_Title_Data_My_Wish_List_Links.get(1));
					softAssert.assertTrue(Page_URL_Title_Data_My_Wish_List_Links.get(2).equalsIgnoreCase(My_Wish_List_Web_Page_Expected_Element_Name) ,            "  Account Information DashBoard Tab verification failed For Page Title . My_Wish_List_Web_Page_Expected_Element_Name                              : "+ My_Wish_List_Web_Page_Expected_Element_Name                                   		   +       "   My_Wish_List_Web_Page_Actual_Element_Name                                   	    : "+ Page_URL_Title_Data_My_Wish_List_Links.get(2));
					softAssert.assertTrue(Page_URL_Title_Data_My_Wish_List_Links.get(3).equalsIgnoreCase(My_Wish_List_Web_Page_Element_Locator_Text) ,             "  Account Information DashBoard Tab verification failed For Page Title . My_Wish_List_Web_Page_Element_Locator_Text  			                  : "+ My_Wish_List_Web_Page_Element_Locator_Text 		                                       +       "   Actual_My_Wish_List_Web_Page_Element_Locator_Text 					            : "+ Page_URL_Title_Data_My_Wish_List_Links.get(3));
					log.info("Page_URL_Title_Data_My_Wish_List_Links.get(0) : " +Page_URL_Title_Data_My_Wish_List_Links.get(0));
					log.info("Page_URL_Title_Data_My_Wish_List_Links.get(1) : " +Page_URL_Title_Data_My_Wish_List_Links.get(1));
					log.info("Page_URL_Title_Data_My_Wish_List_Links.get(2) : " +Page_URL_Title_Data_My_Wish_List_Links.get(2));
					log.info("Page_URL_Title_Data_My_Wish_List_Links.get(3) : " +Page_URL_Title_Data_My_Wish_List_Links.get(3));
					}
				}
				log.info("***************************************** Account Dashboard Drop Down For Web Count ******************************** : " +count);
			softAssert.assertAll();
		}
		catch(Throwable e){
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "verify_My_Wish_List_Link_On_Account_Information_Resizeable_Menu_Section");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	
	@Test(priority = 342, enabled = true)
	public void verify_Register_Link_Redirection_From_Home_Page() throws Exception {
		log.info("******Started verification of Links in Account Dashborad tab after login ********");
		SoftAssert softAssert = new SoftAssert();
		try{
			log.info("******Started verification of Links in Account Dashborad tab after login ********");
			String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Mobile_Register_Link_Redirection_From_Home_Page");
			String[][] web_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Web_Register_Link_Redirection_From_Home_Page");
			String Navigate_To_Account_Information_Tab_Form_URL = test_data[0][8];
			webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);			
			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			log.info("Test Bed is : " + testBed);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			log.info("Test Type is : " + testType);
			platform = testBed.getPlatform().getName().toUpperCase();
			log.info("***************************************** Account Dashboard Drop Down For Mobile Starts********************************");
			String Ham_Burger_Icon_Locator = test_data[0][2];
			String Ham_Burger_Icon_Sign_In_Button_Locator = test_data[0][3];
			String Mobile_Register_Button_Child_Element_Locator = test_data[0][4];
			String Web_Register_Button_Child_Element_Locator = web_data[0][4];
			String Mobile_Ham_Burger_Icon_Sign_Out_Locator = test_data[0][11];
			String Web_Welcome_Message_Locator = test_data[0][12];
			String Web_Log_Out_Drop_Down_Locator = test_data[0][13];
			for(int r= 0; r < test_data.length;r++)
			{	
				String Page_Expected_URL = test_data[r][5];
				String Page_Expected_Element_Name = test_data[r][6];
				String Page_Expected_Title = test_data[r][7];
				String Create_An_Account_Expected_Page_Element_Name = test_data[r][10]; 
				if (testType.equalsIgnoreCase("Mobile")) {
					commonMethods.clickElementbyXpath(webPage, Ham_Burger_Icon_Locator, softAssert);
					commonMethods.clickElementbyXpath(webPage, Mobile_Ham_Burger_Icon_Sign_Out_Locator, softAssert);					
					webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
					commonMethods.clickElementbyXpath(webPage, Mobile_Register_Button_Child_Element_Locator, softAssert);
					commonMethods.clickElementbyXpath(webPage, Ham_Burger_Icon_Locator, softAssert);
					commonMethods.clickElementbyXpath(webPage, Ham_Burger_Icon_Sign_In_Button_Locator, softAssert);
					commonMethods.clickElementbyXpath(webPage, Mobile_Register_Button_Child_Element_Locator, softAssert);
					List<String> Page_URL_Title_Data = ConnsSignInPage.verify_Register_Link_Redirection_From_Home_Page(test_data);
					softAssert.assertTrue(Page_URL_Title_Data.get(0).equalsIgnoreCase(Page_Expected_Title),"Account Information DashBoard Tab verification failed For Expected_Page_Title. Expected_Page_Title  : "+Page_Expected_Title   +  "   Actual_Expected_Title : "+Page_URL_Title_Data.get(0));	
					softAssert.assertTrue(Page_URL_Title_Data.get(1).contains(Page_Expected_URL),"  Account Information DashBoard Tab verification failed For Expected_Page_URL . Expected_Page_URL : "+Page_Expected_URL + "  Actual_Page_URL : "+Page_URL_Title_Data.get(1));
					softAssert.assertTrue(Page_URL_Title_Data.get(2).equalsIgnoreCase(Page_Expected_Element_Name) ,"  Account Information DashBoard Tab verification failed For Page_Expected_Element_Name . Expected_Page_Element   : "+ Page_Expected_Element_Name  +       "   Actual_Page_Element_Name : "+Page_URL_Title_Data.get(2));
					softAssert.assertTrue(Page_URL_Title_Data.get(3).equalsIgnoreCase(Create_An_Account_Expected_Page_Element_Name) ,"  Account Information DashBoard Tab verification failed For Create_An_Account_Expected_Page_Element_Name . Create_An_Account_Expected_Page_Element_Name   : "+ Create_An_Account_Expected_Page_Element_Name  +       "   Create_An_Account_Actual_Page_Element_Name : "+Page_URL_Title_Data.get(3));
					}
				else {
					commonMethods.clickElementbyXpath(webPage, Web_Welcome_Message_Locator, softAssert);
					commonMethods.clickElementbyXpath(webPage, Web_Log_Out_Drop_Down_Locator, softAssert);
					webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
					commonMethods.clickElementbyXpath(webPage, Web_Register_Button_Child_Element_Locator, softAssert);
					List<String> Page_URL_Title_Data = ConnsSignInPage.verify_Register_Link_Redirection_From_Home_Page(web_data);
					softAssert.assertTrue(Page_URL_Title_Data.get(0).equalsIgnoreCase(Page_Expected_Title),"Account Information DashBoard Tab verification failed For Page Title. Expected_Page_Title  : "+Page_Expected_Title   +  "   Actual_Expected_Title : "+Page_URL_Title_Data.get(0));	
					softAssert.assertTrue(Page_URL_Title_Data.get(1).contains(Page_Expected_URL),"  Account Information DashBoard Tab verification failed For Page URL . Expected_Page_URL : "+Page_Expected_URL + "  Actual_Page_URL : "+Page_URL_Title_Data.get(1));
					softAssert.assertTrue(Page_URL_Title_Data.get(2).equalsIgnoreCase(Page_Expected_Element_Name) ,"  Account Information DashBoard Tab verification failed For Expected_Page_Element . Expected_Page_Element   : "+ Page_Expected_Element_Name  +       "   Actual_Page_Element_Name : "+Page_URL_Title_Data.get(2));
					softAssert.assertTrue(Page_URL_Title_Data.get(3).equalsIgnoreCase(Create_An_Account_Expected_Page_Element_Name) ,"  Account Information DashBoard Tab verification failed For Create_An_Account_Expected_Page_Element_Name . Create_An_Account_Expected_Page_Element_Name   : "+ Create_An_Account_Expected_Page_Element_Name  +       "   Create_An_Account_Actual_Page_Element_Name : "+Page_URL_Title_Data.get(3));
				}
				log.info("***************************************** Account Dashboard Drop Down For Web Count ******************************** : " +count);
			}
			softAssert.assertAll();
		}
		catch(Throwable e){
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "verify_Register_Link_Redirection_From_Home_Page");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	
	@Test(priority = 343, enabled = true)
	public void verify_Register_Create_New_Customer_Functionality_with_Blank_Input() throws InterruptedException {
		log.info("******Started verification of Register functionality with blank input data ********");
		SoftAssert softAssert = new SoftAssert();
		try{
			log.info("******Started verification of Register Link in Create Account Sign-In or Register Page ********");
			/*String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageTitle");
			ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);*/
			String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Mobile_Register_Create_New_Customer_Functionality_with_Blank_Input");
			String[][] web_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Web_Register_Create_New_Customer_Functionality_with_Blank_Input");
			String Navigate_To_Account_Information_Tab_Form_URL = test_data[0][22];
			webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
			//webPage.getCurrentUrl();// For Safari
			log.info("***************************************** Account Dashboard Drop Down For Mobile Starts********************************");

			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			log.info("Test Bed is : " + testBed);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			log.info("Test Type is : " + testType);
			platform = testBed.getPlatform().getName().toUpperCase();
			//String[][] input_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Register_Create_New_Customer_Functionality_with_Blank_Input");
			String Ham_Burger_Icon_Locator = test_data[0][2];
			String Ham_Burger_Icon_Sign_In_Button_Locator = test_data[0][3];
			String Mobile_Register_Button_Child_Element_Locator = test_data[0][4];
			String Web_Register_Button_Child_Element_Locator = web_data[0][4];
			String Generic_Error_Message_Text = test_data[0][21];
			log.info("***************************************** Account Dashboard Drop Down For Mobile will be starting********************************");

				if (testType.equalsIgnoreCase("Mobile")) {
					log.info("***************************************** Account Dashboard Drop Down For Mobile Starts********************************");
					commonMethods.clickElementbyXpath(webPage, Ham_Burger_Icon_Locator, softAssert);
					commonMethods.clickElementbyXpath(webPage, Ham_Burger_Icon_Sign_In_Button_Locator, softAssert);
					commonMethods.clickElementbyXpath(webPage, Mobile_Register_Button_Child_Element_Locator, softAssert);
					List<String> actualErrorMessage = ConnsSignInPage.verify_Register_New_User_Create_An_Account_Functionality_with_Blank_Input(test_data);
					softAssert.assertEquals(actualErrorMessage.get(0), Generic_Error_Message_Text,   "Login Functionality with Invalid Input verification failed For Blank_First_Name_Locator.       Expected_First_Name_Error_Message        : " + Generic_Error_Message_Text + " Actual_First_Name_Error_Message       : " + actualErrorMessage.get(0));
					softAssert.assertEquals(actualErrorMessage.get(1), Generic_Error_Message_Text,   "Login Functionality with Invalid Input verification failed For Blank_Last_Name_Locator .       Expected_Last_Name_Error_Message         : " + Generic_Error_Message_Text + " Actual_Last_Name_Error_Message        : " + actualErrorMessage.get(1));
					softAssert.assertEquals(actualErrorMessage.get(2), Generic_Error_Message_Text,   "Login Functionality with Invalid Input verification failed For Blank_Email_Address_Locator .   Expected_Email_Address_Error_Message     : " + Generic_Error_Message_Text + " Actual_Email_Address_Error_Message    : " + actualErrorMessage.get(2));
					softAssert.assertEquals(actualErrorMessage.get(3), Generic_Error_Message_Text,   "Login Functionality with Invalid Input verification failed For Blank_Password_Locator .        Expected_Password_Error_Message          : " + Generic_Error_Message_Text + " Actual_Password_Error_Message         : " + actualErrorMessage.get(3));
					softAssert.assertEquals(actualErrorMessage.get(4), Generic_Error_Message_Text,   "Login Functionality with Invalid Input verification failed For Blank_Confirm_Password_Locator .Expected_Confirm_Password_Error_Message  : " + Generic_Error_Message_Text + " Actual_Confirm_Password_Error_Message : " + actualErrorMessage.get(4));
					} 
				else {
					log.info("***************************************** Account Dashboard Drop Down For Web Starts********************************");
					commonMethods.clickElementbyXpath(webPage, Web_Register_Button_Child_Element_Locator, softAssert);
					List<String> actualErrorMessage = ConnsSignInPage.verify_Register_New_User_Create_An_Account_Functionality_with_Blank_Input(web_data); 
					softAssert.assertEquals(actualErrorMessage.get(0), Generic_Error_Message_Text,   "Login Functionality with Invalid Input verification failed For Blank_First_Name_Locator.       Expected_First_Name_Error_Message        : " + Generic_Error_Message_Text + " Actual_First_Name_Error_Message       : " + actualErrorMessage.get(0));
					softAssert.assertEquals(actualErrorMessage.get(1), Generic_Error_Message_Text,   "Login Functionality with Invalid Input verification failed For Blank_Last_Name_Locator .       Expected_Last_Name_Error_Message         : " + Generic_Error_Message_Text + " Actual_Last_Name_Error_Message        : " + actualErrorMessage.get(1));
					softAssert.assertEquals(actualErrorMessage.get(2), Generic_Error_Message_Text,   "Login Functionality with Invalid Input verification failed For Blank_Email_Address_Locator .   Expected_Email_Address_Error_Message     : " + Generic_Error_Message_Text + " Actual_Email_Address_Error_Message    : " + actualErrorMessage.get(2));
					softAssert.assertEquals(actualErrorMessage.get(3), Generic_Error_Message_Text,   "Login Functionality with Invalid Input verification failed For Blank_Password_Locator .        Expected_Password_Error_Message          : " + Generic_Error_Message_Text + " Actual_Password_Error_Message         : " + actualErrorMessage.get(3));
					softAssert.assertEquals(actualErrorMessage.get(4), Generic_Error_Message_Text,   "Login Functionality with Invalid Input verification failed For Blank_Confirm_Password_Locator .Expected_Confirm_Password_Error_Message  : " + Generic_Error_Message_Text + " Actual_Confirm_Password_Error_Message : " + actualErrorMessage.get(4));
					} 
				log.info("***************************************** Account Dashboard Drop Down For Web Count ******************************** : " +count);
				softAssert.assertAll();
		}
		catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "verify_Register_Create_New_Customer_Functionality_with_Blank_Input");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	
	@Test(priority = 344, enabled = true)
	public void verify_Register_Create_New_Customer_Functionality_with_Invalid_Input() throws InterruptedException {
		log.info("******Started verification of Register functionality with blank input data ********");
		SoftAssert softAssert = new SoftAssert();
		try{
			log.info("******Started verification of Register Link in Create Account Sign-In or Register Page ********");
			/*String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageTitle");
			ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);*/
			String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Mobile_Register_Create_New_Customer_Functionality_with_Invalid_Input");
			String[][] web_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Web_Register_Create_New_Customer_Functionality_with_Invalid_Input");
			String Navigate_To_Account_Information_Tab_Form_URL = test_data[0][29];
			webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
			//webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
			//webPage.getCurrentUrl();// For Safari
			log.info("***************************************** Account Dashboard Drop Down For Mobile Starts********************************");
			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			log.info("Test Bed is : " + testBed);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			log.info("Test Type is : " + testType);
			platform = testBed.getPlatform().getName().toUpperCase();
			String Ham_Burger_Icon_Locator = test_data[0][2];
			String Ham_Burger_Icon_Sign_In_Button_Locator = test_data[0][3];
			String Mobile_Register_Button_Child_Element_Locator = test_data[0][4];
			String Web_Register_Button_Child_Element_Locator = web_data[0][4];
			String Invalid_Email_Address_Expected_Error_Message_Text = test_data[0][25];
			String Invalid_Password_Expected_Error_Message_Text = test_data[0][26];
			String Invalid_Confirm_Password_Expected_Error_Message_Text = test_data[0][27];
			log.info("***************************************** Account Dashboard Drop Down For Mobile will be starting********************************");

				if (testType.equalsIgnoreCase("Mobile")) {
					log.info("***************************************** Account Dashboard Drop Down For Mobile Starts********************************");
					commonMethods.clickElementbyXpath(webPage, Ham_Burger_Icon_Locator, softAssert);
					commonMethods.clickElementbyXpath(webPage, Ham_Burger_Icon_Sign_In_Button_Locator, softAssert);
					commonMethods.clickElementbyXpath(webPage, Mobile_Register_Button_Child_Element_Locator, softAssert);
					List<String> actualErrorMessage = ConnsSignInPage.verify_Register_New_User_Create_An_Account_Functionality_with_Invalid_Input(test_data);
					//softAssert.assertEquals(actualErrorMessage.get(0), Generic_Error_Message_Text,                     "Login Functionality with Invalid Input verification failed For Invalid_First_Name_Locator.       Expected_First_Name_Error_Message        : " + Generic_Error_Message_Text                        + " Actual_First_Name_Error_Message       : " + actualErrorMessage);
					//softAssert.assertEquals(actualErrorMessage.get(1), Generic_Error_Message_Text,                     "Login Functionality with Invalid Input verification failed For Invalid_Last_Name_Locator .       Expected_Last_Name_Error_Message         : " + Generic_Error_Message_Text                        + " Actual_Last_Name_Error_Message        : " + actualErrorMessage);
					softAssert.assertEquals(actualErrorMessage.get(0), Invalid_Email_Address_Expected_Error_Message_Text,   "Login Functionality with Invalid Input verification failed For Invalid_Email_Address_Expected_Error_Message_Text    . Invalid_Email_Address_Expected_Error_Message_Text    : " + Invalid_Email_Address_Expected_Error_Message_Text      + " Actual_Email_Address_Error_Message    : " + actualErrorMessage.get(0));
					softAssert.assertEquals(actualErrorMessage.get(1), Invalid_Password_Expected_Error_Message_Text,        "Login Functionality with Invalid Input verification failed For Invalid_Password_Expected_Error_Message_Text         . Invalid_Password_Expected_Error_Message_Text         : " + Invalid_Confirm_Password_Expected_Error_Message_Text   + " Actual_Password_Error_Message         : " + actualErrorMessage.get(1));
					softAssert.assertEquals(actualErrorMessage.get(2), Invalid_Confirm_Password_Expected_Error_Message_Text,"Login Functionality with Invalid Input verification failed For Invalid_Confirm_Password_Expected_Error_Message_Text . Invalid_Confirm_Password_Expected_Error_Message_Text : " + Invalid_Confirm_Password_Expected_Error_Message_Text   + " Actual_Confirm_Password_Error_Message : " + actualErrorMessage.get(2));
					softAssert.assertAll();
					} 
				else {
					log.info("***************************************** Account Dashboard Drop Down For Web Starts********************************");
					commonMethods.clickElementbyXpath(webPage, Web_Register_Button_Child_Element_Locator, softAssert);
					log.info("***************************************** Web_Register_Button_Child_Element_Locator_Clicked********************************");
					List<String> actualErrorMessage = ConnsSignInPage.verify_Register_New_User_Create_An_Account_Functionality_with_Invalid_Input(web_data); 
					//softAssert.assertEquals(actualErrorMessage.get(0), Generic_Error_Message_Text,                     "Login Functionality with Invalid Input verification failed For Invalid_First_Name_Locator.       Expected_First_Name_Error_Message        : " + Generic_Error_Message_Text                        + " Actual_First_Name_Error_Message       : " + actualErrorMessage);
					//softAssert.assertEquals(actualErrorMessage.get(1), Generic_Error_Message_Text,                     "Login Functionality with Invalid Input verification failed For Invalid_Last_Name_Locator .       Expected_Last_Name_Error_Message         : " + Generic_Error_Message_Text                        + " Actual_Last_Name_Error_Message        : " + actualErrorMessage);
					softAssert.assertEquals(actualErrorMessage.get(0), Invalid_Email_Address_Expected_Error_Message_Text,   "Login Functionality with Invalid Input verification failed For Invalid_Email_Address_Expected_Error_Message_Text    . Invalid_Email_Address_Expected_Error_Message_Text    : " + Invalid_Email_Address_Expected_Error_Message_Text      + " Actual_Email_Address_Error_Message    : " + actualErrorMessage.get(0));
					softAssert.assertEquals(actualErrorMessage.get(1), Invalid_Password_Expected_Error_Message_Text,        "Login Functionality with Invalid Input verification failed For Invalid_Password_Expected_Error_Message_Text         . Invalid_Password_Expected_Error_Message_Text         : " + Invalid_Confirm_Password_Expected_Error_Message_Text   + " Actual_Password_Error_Message         : " + actualErrorMessage.get(1));
					softAssert.assertEquals(actualErrorMessage.get(2), Invalid_Confirm_Password_Expected_Error_Message_Text,"Login Functionality with Invalid Input verification failed For Invalid_Confirm_Password_Expected_Error_Message_Text . Invalid_Confirm_Password_Expected_Error_Message_Text : " + Invalid_Confirm_Password_Expected_Error_Message_Text   + " Actual_Confirm_Password_Error_Message : " + actualErrorMessage.get(2));
					softAssert.assertAll();
					} 
				log.info("***************************************** Account Dashboard Drop Down For Web Count ******************************** : " +count);
				softAssert.assertAll();
		}
		catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "verify_Register_Create_New_Customer_Functionality_with_Invalid_Input");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	
	@Test(priority = 345, enabled = true)
	public void verify_Register_Create_An_Account_Remember_Me_CheckBox() {
		log.info("******Started verification of RememberMeCheckBox functionality with valid data ********");
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Mobile_Register_Create_An_Account_Remember_Me_Functionality");
			String[][] web_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Web_Register_Create_An_Account_Remember_Me_Functionality");
			String Remember_me_CheckBox = test_data[0][5];
			String Expected_ToolTip_Text = test_data[0][6];
			String Navigate_To_Account_Information_Tab_Form_URL = test_data[0][7];			
			webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
			//webPage.getCurrentUrl();// For Safari
			log.info("***************************************** Account Dashboard Drop Down For Mobile Starts********************************");
			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			log.info("Test Bed is : " + testBed);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			log.info("Test Type is : " + testType);
			platform = testBed.getPlatform().getName().toUpperCase();
			String Ham_Burger_Icon_Locator = test_data[0][2];
			String Ham_Burger_Icon_Sign_In_Button_Locator = test_data[0][3];
			String Mobile_Register_Button_Child_Element_Locator = test_data[0][4];
			String Web_Register_Button_Child_Element_Locator = web_data[0][4];
			log.info("***************************************** Account Dashboard Drop Down For Mobile will be starting********************************");
				if (testType.equalsIgnoreCase("Mobile")) {
					log.info("***************************************** Account Dashboard Drop Down For Mobile Starts********************************");
					commonMethods.clickElementbyXpath(webPage, Ham_Burger_Icon_Locator, softAssert);
					commonMethods.clickElementbyXpath(webPage, Ham_Burger_Icon_Sign_In_Button_Locator, softAssert);
					commonMethods.clickElementbyXpath(webPage, Mobile_Register_Button_Child_Element_Locator, softAssert);
					ConnsSignInPage.verify_Remember_Me_Functionality(test_data);
					commonMethods.clickElementbyCssAndGetCurrentURL(webPage, Remember_me_CheckBox, softAssert);
					String Actual_ToolTip_Text = commonMethods.getAttributebyCss(webPage, Remember_me_CheckBox, "title",softAssert);
					SoftAssertor.assertEquals(Actual_ToolTip_Text, Expected_ToolTip_Text,"ToolTip_Text of the mouse pointer does not match");
					softAssert.assertAll();
				} 
				else {
					log.info("***************************************** Account Dashboard Drop Down For Web Starts********************************");
					commonMethods.clickElementbyXpath(webPage, Web_Register_Button_Child_Element_Locator, softAssert);
					ConnsSignInPage.verify_Remember_Me_Functionality(web_data);
					commonMethods.clickElementbyCssAndGetCurrentURL(webPage, Remember_me_CheckBox, softAssert);
					String Actual_ToolTip_Text = commonMethods.getAttributebyCss(webPage, Remember_me_CheckBox, "title",softAssert);
					SoftAssertor.assertEquals(Actual_ToolTip_Text, Expected_ToolTip_Text,"ToolTip_Text of the mouse pointer does not match");
					softAssert.assertAll();
				}
		}catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "verify_Register_Create_An_Account_Remember_Me_CheckBox");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	@Test(priority = 346, enabled = true)
	public void verify_Register_Create_An_Account_Whats_This_Overlay_Rendered() throws PageException, InterruptedException {
		log.info("**************Started verification of What's this overlay Rendered on Login Page *******************");
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Mobile_Register_Create_An_Account_Whats_This_Overlay");
			String[][] web_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Web_Register_Create_An_Account_Whats_This_Overlay");
			String Locator = test_data[0][5];
			String contentonoverlaylocator = test_data[0][6];
			String verify_Whats_This_Overlay_Rendered_Expected_Content = test_data[0][7];
			String Navigate_To_Account_Information_Tab_Form_URL = test_data[0][11];			
			webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
			//webPage.getCurrentUrl();// For Safari
			log.info("***************************************** Account Dashboard Drop Down For Mobile Starts********************************");
			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			log.info("Test Bed is : " + testBed);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			log.info("Test Type is : " + testType);
			platform = testBed.getPlatform().getName().toUpperCase();
			String Ham_Burger_Icon_Locator = test_data[0][2];
			String Ham_Burger_Icon_Sign_In_Button_Locator = test_data[0][3];
			String Mobile_Register_Button_Child_Element_Locator = test_data[0][4];
			String Web_Register_Button_Child_Element_Locator = web_data[0][4];
			log.info("***************************************** Account Dashboard Drop Down For Mobile will be starting********************************");
			if (testType.equalsIgnoreCase("Mobile")) {
				log.info("***************************************** Account Dashboard Drop Down For Mobile Starts********************************");
				commonMethods.clickElementbyXpath(webPage, Ham_Burger_Icon_Locator, softAssert);
				commonMethods.clickElementbyXpath(webPage, Ham_Burger_Icon_Sign_In_Button_Locator, softAssert);
				commonMethods.clickElementbyXpath(webPage, Mobile_Register_Button_Child_Element_Locator, softAssert);
				commonMethods.clickElementbyXpath(webPage, Locator, softAssert);
				String verify_Whats_This_Overlay_Rendered_Actual_Content = commonMethods.getTextbyCss(webPage,contentonoverlaylocator, softAssert);
				softAssert.assertEquals(verify_Whats_This_Overlay_Rendered_Actual_Content,verify_Whats_This_Overlay_Rendered_Expected_Content,"verify_Whats_This_Overlay_Rendered verification failed. Whats_This_Overlay_Rendered_Expected_Content : "		+ verify_Whats_This_Overlay_Rendered_Expected_Content	+ " Whats_This_Overlay_Rendered_Actual_Content : " + verify_Whats_This_Overlay_Rendered_Actual_Content);
				softAssert.assertAll();
			} 
			else {
				log.info("***************************************** Account Dashboard Drop Down For Web Starts********************************");
				commonMethods.clickElementbyXpath(webPage, Web_Register_Button_Child_Element_Locator, softAssert);
				commonMethods.clickElementbyXpath(webPage, Locator, softAssert);
				String verify_Whats_This_Overlay_Rendered_Actual_Content = commonMethods.getTextbyCss(webPage,contentonoverlaylocator, softAssert);
				softAssert.assertEquals(verify_Whats_This_Overlay_Rendered_Actual_Content,verify_Whats_This_Overlay_Rendered_Expected_Content,"verify_Whats_This_Overlay_Rendered verification failed. Whats_This_Overlay_Rendered_Expected_Content : "		+ verify_Whats_This_Overlay_Rendered_Expected_Content	+ " Whats_This_Overlay_Rendered_Actual_Content : " + verify_Whats_This_Overlay_Rendered_Actual_Content);
				softAssert.assertAll();
			}
		}
		catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "verify_Register_Create_An_Account_Whats_This_Overlay_Rendered");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	
	
	@Test(priority = 347, enabled = true)
	public void verify_Register_Create_An_Account_Whats_This_Overlay_Close(ITestContext context) {
		log.info("**************Started verification of What's this overlay Rendered on Login Page *******************");
		SoftAssert softAssert = new SoftAssert();
		JavascriptExecutor jse = (JavascriptExecutor) webPage.getDriver();
		try {
			String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Mobile_Register_Create_An_Account_Whats_This_Overlay");
			String[][] web_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Web_Register_Create_An_Account_Whats_This_Overlay");
			//String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage", "verifyWhatsThisOverlay");
			String Locator = test_data[0][5];
			String contentonoverlaylocator = test_data[0][6];
			//String verify_Whats_This_Overlay_Rendered_Expected_Content = test_data[0][7];
			String verify_Whats_This_Overlay_Close_Expected_Content = test_data[0][7];
			String Top_Close_Button_Locator_CSS = test_data[0][8];
			String Close_Button_Locator_CSS = test_data[0][9];
			String Close_Button_Locator_Xpath = test_data[0][10];
			String Navigate_To_Account_Information_Tab_Form_URL = test_data[0][11];			
			webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
			//webPage.getCurrentUrl();// For Safari
			log.info("***************************************** Account Dashboard Drop Down Starts********************************");
			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			log.info("Test Bed is : " + testBed);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			log.info("Test Type is : " + testType);
			platform = testBed.getPlatform().getName().toUpperCase();
			String Ham_Burger_Icon_Locator = test_data[0][2];
			String Ham_Burger_Icon_Sign_In_Button_Locator = test_data[0][3];
			String Mobile_Register_Button_Child_Element_Locator = test_data[0][4];

			String Web_Register_Button_Child_Element_Locator = web_data[0][4];
			log.info("***************************************** Account Dashboard Drop Down For Mobile will be starting********************************");
			if (testType.equalsIgnoreCase("Mobile")) {
				log.info("***************************************** Account Dashboard Drop Down For Mobile Starts********************************");
				commonMethods.clickElementbyXpath(webPage, Ham_Burger_Icon_Locator, softAssert);
				commonMethods.clickElementbyXpath(webPage, Ham_Burger_Icon_Sign_In_Button_Locator, softAssert);
				commonMethods.clickElementbyXpath(webPage, Mobile_Register_Button_Child_Element_Locator, softAssert);
				jse.executeScript("scroll(0, 250);");
				commonMethods.clickElementbyXpath(webPage, Locator, softAssert);
				String verify_Whats_This_Overlay_Close_Actual_Content = commonMethods.getTextbyCss(webPage,contentonoverlaylocator, softAssert);
				softAssert.assertEquals(verify_Whats_This_Overlay_Close_Actual_Content,verify_Whats_This_Overlay_Close_Expected_Content,"verify_Whats_This_Overlay_Close verification failed.  Whats_This_Overlay_Close_Expected_Content : " + verify_Whats_This_Overlay_Close_Expected_Content	+ " Whats_This_Overlay_Rendered_Actual_Content : "	+ verify_Whats_This_Overlay_Close_Actual_Content);
				Thread.sleep(7000);
				if (testBed.getTestBedName().equalsIgnoreCase("Safari")) {
					commonMethods.clickElementbyCss(webPage, Close_Button_Locator_CSS, softAssert);
				}
				else{
					commonMethods.clickElementbyCss(webPage, Top_Close_Button_Locator_CSS, softAssert);
				}
				softAssert.assertAll();
				} else{
				commonMethods.clickElementbyXpath(webPage, Web_Register_Button_Child_Element_Locator, softAssert);
				jse.executeScript("scroll(0, 250);");
				commonMethods.clickElementbyXpath(webPage, Locator, softAssert);
				String verify_Whats_This_Overlay_Close_Actual_Content = commonMethods.getTextbyCss(webPage,contentonoverlaylocator, softAssert);
				softAssert.assertEquals(verify_Whats_This_Overlay_Close_Actual_Content,verify_Whats_This_Overlay_Close_Expected_Content,"verify_Whats_This_Overlay_Close verification failed.  Whats_This_Overlay_Close_Expected_Content : " + verify_Whats_This_Overlay_Close_Expected_Content	+ " Whats_This_Overlay_Rendered_Actual_Content : "	+ verify_Whats_This_Overlay_Close_Actual_Content);
				Thread.sleep(7000);
				if (testBed.getTestBedName().equalsIgnoreCase("Safari")) {
					commonMethods.clickElementbyCss(webPage, Close_Button_Locator_CSS, softAssert);
				}
				else{
					commonMethods.clickElementbyXpath(webPage, Close_Button_Locator_Xpath, softAssert);
				}
				softAssert.assertAll();
				}
				}catch (Throwable e) {
					e.printStackTrace();
					mainPage.getScreenShotForFailure(webPage, "verify_Whats_This_Overlay_Close");
					softAssert.assertAll();
					Assert.fail(e.getLocalizedMessage());
		}
	}
	
	
	@Test(priority = 348, enabled = true)
	public void verify_Register_Create_An_Account_Newsletters_General_Subscription_CheckBox()	throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Mobile_Register_Create_New_Customer_Newsletters_Functionality");
			String[][] web_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Web_Register_Create_New_Customer_Newsletters_Functionality");
			String Navigate_To_Account_Information_Tab_Form_URL = test_data[0][5];
			String Newsletter_Subscription_Register_Create_An_Account_Check_Box_Locator_CssPath = test_data[0][21];
			String Newsletter_Subscription_Register_Create_An_Account_Check_Box_Xpath_Locator = test_data[0][22];
			String Newsletter_Subscription_Register_Create_An_Account_Check_Box_CssPath_Locator = test_data[0][23];
			String Newsletter_Subscription_Expected_Content_Text = test_data[0][19];
			String Newsletter_Subscription_Expected_ToolTip_Text = test_data[0][20];
			webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
			//webPage.getCurrentUrl();// For Safari
			log.info("***************************************** Account Dashboard Drop Down For Mobile Starts********************************");
			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			log.info("Test Bed is : " + testBed);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			log.info("Test Type is : " + testType);
			platform = testBed.getPlatform().getName().toUpperCase();
			String Ham_Burger_Icon_Locator = test_data[0][2];
			String Ham_Burger_Icon_Sign_In_Button_Locator = test_data[0][3];
			String Mobile_Register_Button_Child_Element_Locator = test_data[0][4];
			String Web_Register_Button_Child_Element_Locator = web_data[0][4];
			log.info("***************************************** Account Dashboard Drop Down For Mobile will be starting********************************");
			if (testType.equalsIgnoreCase("Mobile")) {
				log.info("***************************************** Account Dashboard Drop Down For Mobile Starts********************************");
				commonMethods.clickElementbyXpath(webPage, Ham_Burger_Icon_Locator, softAssert);
				commonMethods.clickElementbyXpath(webPage, Ham_Burger_Icon_Sign_In_Button_Locator, softAssert);
				commonMethods.clickElementbyXpath(webPage, Mobile_Register_Button_Child_Element_Locator, softAssert);
				
				Thread.sleep(4000);
				WebElement Newsletters_Subscription_CheckBox = webPage.getDriver().findElement(By.xpath(test_data[0][22]));
				if ((!(Newsletters_Subscription_CheckBox).isSelected())	&& ((Newsletters_Subscription_CheckBox)).isEnabled()) {
					commonMethods.clickElementbyXpath(webPage, Newsletter_Subscription_Register_Create_An_Account_Check_Box_Xpath_Locator, softAssert);
				} else {
					commonMethods.clickElementbyXpath(webPage, Newsletter_Subscription_Register_Create_An_Account_Check_Box_Xpath_Locator, softAssert);
					Thread.sleep(4000);
					commonMethods.clickElementbyXpath(webPage, Newsletter_Subscription_Register_Create_An_Account_Check_Box_Xpath_Locator, softAssert);
				}
				String Newsletter_Subscription_Actual_Content_Text = commonMethods.getTextbyCss(webPage,Newsletter_Subscription_Register_Create_An_Account_Check_Box_CssPath_Locator, softAssert);
				softAssert.assertEquals(Newsletter_Subscription_Actual_Content_Text,Newsletter_Subscription_Expected_Content_Text,"verify_Register_Create_An_Account_Newsletters_General_Subscription_CheckBox verification failed.  Newsletter_Subscription_Expected_Content_Text : " + Newsletter_Subscription_Expected_Content_Text	+ " Newsletter_Subscription_Actual_Content_Text : "	+ Newsletter_Subscription_Actual_Content_Text);
				String Newsletter_Subscription_Actual_ToolTip_Text = commonMethods.getAttributebyCss(webPage, Newsletter_Subscription_Register_Create_An_Account_Check_Box_Locator_CssPath, "title",softAssert);
				SoftAssertor.assertEquals(Newsletter_Subscription_Actual_ToolTip_Text, Newsletter_Subscription_Expected_ToolTip_Text,"ToolTip_Text of the mouse pointer does not match");softAssert.assertAll();
			} 
			else{
				log.info("***************************************** Account Dashboard Drop Down For Web Starts********************************");
				commonMethods.clickElementbyXpath(webPage, Web_Register_Button_Child_Element_Locator, softAssert);
				WebElement Newsletters_Subscription_CheckBox = webPage.getDriver().findElement(By.xpath(test_data[0][22]));
				if ((!(Newsletters_Subscription_CheckBox).isSelected())	&& ((Newsletters_Subscription_CheckBox)).isEnabled()) {
					commonMethods.clickElementbyXpath(webPage, Newsletter_Subscription_Register_Create_An_Account_Check_Box_Xpath_Locator, softAssert);
				} else {
					commonMethods.clickElementbyXpath(webPage, Newsletter_Subscription_Register_Create_An_Account_Check_Box_Xpath_Locator, softAssert);
					Thread.sleep(4000);
					commonMethods.clickElementbyXpath(webPage, Newsletter_Subscription_Register_Create_An_Account_Check_Box_Xpath_Locator, softAssert);
				}
				String Newsletter_Subscription_Actual_Content_Text = commonMethods.getTextbyCss(webPage,Newsletter_Subscription_Register_Create_An_Account_Check_Box_CssPath_Locator, softAssert);
				softAssert.assertEquals(Newsletter_Subscription_Actual_Content_Text,Newsletter_Subscription_Expected_Content_Text,"verify_Register_Create_An_Account_Newsletters_General_Subscription_CheckBox verification failed.  Newsletter_Subscription_Expected_Content_Text : " + Newsletter_Subscription_Expected_Content_Text	+ " Newsletter_Subscription_Actual_Content_Text : "	+ Newsletter_Subscription_Actual_Content_Text);
				String Newsletter_Subscription_Actual_ToolTip_Text = commonMethods.getAttributebyCss(webPage, Newsletter_Subscription_Register_Create_An_Account_Check_Box_Locator_CssPath, "title",softAssert);
				SoftAssertor.assertEquals(Newsletter_Subscription_Actual_ToolTip_Text, Newsletter_Subscription_Expected_ToolTip_Text,"ToolTip_Text of the mouse pointer does not match");
				softAssert.assertAll();
			} 
		}catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage,"verify_Account_Information_Tab_Newsletters_General_Subscription_CheckBox");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	

	@Test(priority = 349, enabled = true)
	public void verify_Register_Create_New_Customer_Functionality_with_Valid_Input() throws InterruptedException {
		log.info("******Started verification of Register functionality with blank input data ********");
		SoftAssert softAssert = new SoftAssert();
		try{
			log.info("******Started verification of Register Link in Create Account Sign-In or Register Page ********");
			/*String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verifyAccountDashBoardPageTitle");
			ConnsSignInPage.verify_Account_DashBoard_Login(testdata,softAssert);*/
			String[][] test_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Mobile_Register_Create_New_Customer_Functionality_with_Valid_Input");
			String[][] web_data = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage","verify_Web_Register_Create_New_Customer_Functionality_with_Valid_Input");
			String Navigate_To_Account_Information_Tab_Form_URL = test_data[0][29];
			Thread.sleep(1000);
			webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_URL);
			//webPage.getDriver().navigate().refresh();
			//webPage.getCurrentUrl();// For Safari
			Thread.sleep(2000);
			log.info("***************************************** Account Dashboard Drop Down For Mobile Starts********************************");
			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			log.info("Test Bed is : " + testBed);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			log.info("Test Type is : " + testType);
			platform = testBed.getPlatform().getName().toUpperCase();
			String Ham_Burger_Icon_Locator = test_data[0][2];
			String Ham_Burger_Icon_Sign_In_Button_Locator = test_data[0][3];
			String Mobile_Register_Button_Child_Element_Locator = test_data[0][4];
			String Web_Register_Button_Child_Element_Locator = web_data[0][4];
			String Email_Address_Expected_Success_Message_Locator_Text = test_data[0][31];
			String Newly_Created_User_Name_DashBoard_Header_Title_Locator_Text = test_data[0][33];
			log.info("***************************************** Account Dashboard Drop Down For Mobile will be starting********************************");
				if (testType.equalsIgnoreCase("Mobile")) {
					log.info("***************************************** Account Dashboard Drop Down For Mobile Starts********************************");
					commonMethods.clickElementbyXpath(webPage, Ham_Burger_Icon_Locator, softAssert);
					commonMethods.clickElementbyXpath(webPage, Ham_Burger_Icon_Sign_In_Button_Locator, softAssert);
					commonMethods.clickElementbyXpath(webPage, Mobile_Register_Button_Child_Element_Locator, softAssert);
					List<String> actualErrorMessage = ConnsSignInPage.verify_Register_New_User_Create_An_Account_Functionality_with_Valid_Input(test_data);
					softAssert.assertEquals(actualErrorMessage.get(0), Email_Address_Expected_Success_Message_Locator_Text,"Login Functionality with Valid Input verification failed For Email_Address_Expected_Success_Message_Locator_Text . Email_Address_Expected_Success_Message_Locator_Text : " + Email_Address_Expected_Success_Message_Locator_Text   + " Email_Address_Actual_Success_Message_Locator_Text : " + actualErrorMessage.get(0));
					softAssert.assertEquals(actualErrorMessage.get(1), Newly_Created_User_Name_DashBoard_Header_Title_Locator_Text,"Login Functionality with Valid Input verification failed For Newly_Created_User_Name_DashBoard_Header_Title_Locator_Text . Newly_Created_User_Name_DashBoard_Header_Title_Locator_Text : " + Newly_Created_User_Name_DashBoard_Header_Title_Locator_Text   + " Email_Address_Actual_Success_Message_Locator_Text : " + actualErrorMessage.get(1));
					softAssert.assertAll();
					} 
				else {
					log.info("***************************************** Account Dashboard Drop Down For Web Starts********************************");
					Thread.sleep(1000);
					commonMethods.clickElementbyXpath(webPage, Web_Register_Button_Child_Element_Locator, softAssert);
					Thread.sleep(2000);
					List<String> actualErrorMessage = ConnsSignInPage.verify_Register_New_User_Create_An_Account_Functionality_with_Valid_Input(web_data); 
					softAssert.assertEquals(actualErrorMessage.get(0), Email_Address_Expected_Success_Message_Locator_Text,"Login Functionality with Valid Input verification failed For Email_Address_Expected_Success_Message_Locator_Text . Email_Address_Expected_Success_Message_Locator_Text : " + Email_Address_Expected_Success_Message_Locator_Text   + " Email_Address_Actual_Success_Message_Locator_Text : " + actualErrorMessage.get(0));
					softAssert.assertEquals(actualErrorMessage.get(1), Newly_Created_User_Name_DashBoard_Header_Title_Locator_Text,"Login Functionality with Valid Input verification failed For Newly_Created_User_Name_DashBoard_Header_Title_Locator_Text . Newly_Created_User_Name_DashBoard_Header_Title_Locator_Text : " + Newly_Created_User_Name_DashBoard_Header_Title_Locator_Text   + " Email_Address_Actual_Success_Message_Locator_Text : " + actualErrorMessage.get(1));
					softAssert.assertAll();
					} 
				log.info("***************************************** Account Dashboard Drop Down For Web Count ******************************** : " +count);
				softAssert.assertAll();
		}
		catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "verify_Register_Create_New_Customer_Functionality_with_Valid_Input");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	

	/************************************************************************ GARBAGE ENDS*******************************************************************************************************************************************************************************************/
}
/************************************************************************ UC001-UC006 ENDS*******************************************************************************************************************************************************************************************/
