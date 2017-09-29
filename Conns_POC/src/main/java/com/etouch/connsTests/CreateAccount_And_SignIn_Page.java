package com.etouch.connsTests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.etouch.common.BaseTest;
import com.etouch.common.CommonMethods;
import com.etouch.connsPages.ConnsMainPage;
import com.etouch.connsPages.CreateAccountAndSignInPage;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.datamanager.excel.annotations.IExcelDataFiles;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.taf.webui.ITafElement;
import com.etouch.taf.webui.selenium.WebPage;

@Test(groups = "CreateAccount_And_SignIn_Page")
@IExcelDataFiles(excelDataFiles = { "CreditAppData=testData" })
public class CreateAccount_And_SignIn_Page extends BaseTest {
	private String testBedName;
	Path path;
	String DataFilePath;
	protected String testType, browserName;
	String currentTestBedName;
	static Log log = LogUtil.getLog(CreateAccount_And_SignIn_Page.class);
	private String url, testEnv, registerUrl, signInURL, forgetPasswordPageURL;
	protected WebPage webPage;
	private ConnsMainPage mainPage;
	String[][] commonData;
	protected static CommonMethods commonMethods;
	boolean userLoggedIn = false;
	String[][] YesLeaseData;
	CreateAccountAndSignInPage CreateAccountAndSignInPage;

	/*** Prepare before class @throws Exception the exception */
	@BeforeClass(alwaysRun = true)
	public void setUp(ITestContext context) throws InterruptedException, FileNotFoundException, IOException {
		try {
			testBedName = context.getCurrentXmlTest().getAllParameters().get("testBedName");
			CommonUtil.sop("Test bed Name is " + testBedName);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			commonMethods = new CommonMethods();
			browserName = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getBrowser().getName()
					.toLowerCase();
			log.info("Test Type is : " + testType);
			try {
				testEnv = System.getenv().get("Environment");
				log.info("testEnv is : " + System.getenv().get("Environment"));
				path = Paths.get(TestBedManager.INSTANCE.getProfile().getXlsDataConfig().get("testData"));
				DataFilePath = path.toAbsolutePath().toString().replace("Env", testEnv);
				log.info("DataFilePath After is : " + DataFilePath);
				commonData = ExcelUtil.readExcelData(DataFilePath, "CreateAccountSignIn",
						"CreateAccountSignInCommonElements");
				url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL();
				registerUrl = commonData[1][1];
				signInURL = commonData[2][1];
				forgetPasswordPageURL = commonData[3][1];
				synchronized (this) {
					webPage = new WebPage(context);
					mainPage = new ConnsMainPage(url, webPage);
					CreateAccountAndSignInPage = new CreateAccountAndSignInPage(url, webPage);
					log.info(mainPage);
				}
			} catch (Exception e) {
				log.info("errr is " + e);
				SoftAssertor.addVerificationFailure(e.getMessage());
			}
			CommonMethods.navigateToPage(webPage, signInURL);
		} catch (Exception e) {
			CommonUtil.sop("errr is for" + testBedName + " -----------" + e);
			SoftAssertor.addVerificationFailure(e.getMessage());
		}
	}

	@Test(priority = 300, enabled = true)
	public void verify_Font_And_Size_Login_Page() {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] ExpectedFontValues = ExcelUtil.readExcelData(DataFilePath, "AccountSignINPage",
					"Verify_Font_And_Size_Login_Page");
			for (int i = 0; i < ExpectedFontValues.length; i++) {
				List<String> actualCssValues = commonMethods.getFontProperties(webPage, ExpectedFontValues[i][1],
						softAssert);
				if (testType.equalsIgnoreCase("Mobile")) {
					softAssert.assertTrue(actualCssValues.get(0).contains(ExpectedFontValues[i][5]),
							"CSS value verification failed for link " + ExpectedFontValues[i][0]
									+ "Expected font Size  : " + ExpectedFontValues[i][5] + " Actual Font Size  : "
									+ actualCssValues.get(0));
					softAssert.assertTrue(actualCssValues.get(1).contains(ExpectedFontValues[i][6]),
							"CSS value verification failed for link " + ExpectedFontValues[i][0]
									+ "Expected font color : " + ExpectedFontValues[i][6] + " Actual font color : "
									+ actualCssValues.get(1));
					softAssert.assertTrue(
							actualCssValues.get(2).toLowerCase().replaceAll("'", "").replaceAll("\"", "")
									.contains((ExpectedFontValues[i][4]).toLowerCase()),
							"CSS value verification failed for link " + ExpectedFontValues[i][0]
									+ "Expected font family : " + ExpectedFontValues[i][4] + " Actual font family : "
									+ actualCssValues.get(2));
				} else {
					softAssert.assertTrue(actualCssValues.get(0).contains(ExpectedFontValues[i][2]),
							"CSS value verification failed for link " + ExpectedFontValues[i][0]
									+ "Expected font Size  : " + ExpectedFontValues[i][2] + " Actual Font Size   : "
									+ actualCssValues.get(0));
					softAssert.assertTrue(actualCssValues.get(1).contains(ExpectedFontValues[i][3]),
							"CSS value verification failed for link " + ExpectedFontValues[i][0]
									+ "Expected font color : " + ExpectedFontValues[i][3] + " Actual font family : "
									+ actualCssValues.get(1));
					softAssert.assertTrue(
							actualCssValues.get(2).toLowerCase().replaceAll("'", "").replaceAll("\"", "")
									.contains((ExpectedFontValues[i][4]).toLowerCase()),
							"CSS value verification failed for link " + ExpectedFontValues[i][0]
									+ "Expected font family : " + ExpectedFontValues[i][4] + " Actual font family : "
									+ actualCssValues.get(2));
				}
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Font_And_Size_Login_Page");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 301, enabled = true)
	public void Verify_Broken_Links_On_SignIn_Page() throws ClientProtocolException, IOException {
		SoftAssert softAssert = new SoftAssert();
		webPage.getDriver().get(signInURL);
		try {
			String[][] linkData = ExcelUtil.readExcelData(DataFilePath, "CreateAccountSignIn",
					"verifyLinksRedirection");
			for (int i = 0; i < linkData.length; i++) {
				{
					ITafElement link = webPage.findObjectByxPath(linkData[i][1]);
					log.info("iteration " + i + " : " + link.getAttribute("href"));
					HttpClient client = HttpClientBuilder.create().build();
					HttpGet request = new HttpGet(link.getAttribute("href"));
					HttpResponse response = client.execute(request);
					log.info("Status code for iteration " + i + " : " + response.getStatusLine().getStatusCode());
					softAssert.assertEquals(response.getStatusLine().getStatusCode(), 200,
							"Validation for " + linkData[i][0] + " :");
				}
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Broken_Links");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 302, enabled = true)
	public void verify_SignInErrorMessage_with_Blank_Input() throws InterruptedException {
		log.info("******Started verification of Sign In functionality with blank input data ********");
		SoftAssert softAssert = new SoftAssert();
		webPage.getDriver().get(signInURL);
		try {
			String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "CreateAccountSignIn",
					"verify_SignIn_with_Blank_Input");
			commonMethods.clickElementbyXpath(webPage, testdata[0][1], softAssert);
			for (int i = 1; i < 3; i++) {
				CreateAccountAndSignInPage.verifyErrorMessageByXpath(webPage, softAssert, testdata[i][0],
						testdata[i][1], testdata[i][2]);
			}
			log.info("testing verify_SignInErrorMessage_with_Blank_Input completed------>");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_SignInErrorMessage_with_Blank_Input");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 303, enabled = true)
	public void verify_SignInErrorMessage_with_Invalid_Input() throws InterruptedException {
		log.info("******Started verify_SignInErrorMessage_with_Invalid_Input ********");
		SoftAssert softAssert = new SoftAssert();
		webPage.getDriver().get(signInURL);
		try {
			String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "CreateAccountSignIn",
					"verify_SignIn_With_Invalid_Input");
			for (int i = 0; i < 2; i++) {
				commonMethods.sendKeysbyXpath(webPage, testdata[i][1], testdata[i][2], softAssert);
			}
			commonMethods.clickElementbyXpath(webPage, testdata[2][1], softAssert);
			for (int i = 3; i < 5; i++) {
				CreateAccountAndSignInPage.verifyErrorMessageByXpath(webPage, softAssert, testdata[i][0],
						testdata[i][1], testdata[i][2]);
			}
			log.info("testing verify_SignInErrorMessage_with_Invalid_Input completed------>");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_SignInErrorMessage_with_Invalid_Input");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 304, enabled = true)
	public void verify_Forgot_Password_Functionality() throws InterruptedException {
		log.info("******Started verify_ForgotPassword_Functionality********");
		SoftAssert softAssert = new SoftAssert();
		webPage.getDriver().get(signInURL);
		try {
			String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "CreateAccountSignIn",
					"verify_Forgot_Password_Functionality");
			commonMethods.clickElementbyXpath(webPage, testdata[0][1], softAssert);
			String actualUrl = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertEquals(actualUrl, testdata[0][2], "ForgotPassword  Page URL:");
			// Submit with Blank
			String ForgotpasswordSubmitXpath = testdata[0][3];
			commonMethods.clickElementbyXpath(webPage, ForgotpasswordSubmitXpath, softAssert);
			softAssert.assertEquals(commonMethods.getTextbyXpath(webPage, testdata[1][3], softAssert), testdata[1][4],
					"Submit with blank email:");
			// Submit with invalid mail
			commonMethods.sendKeysbyXpath(webPage, testdata[2][1], testdata[2][2], softAssert);
			commonMethods.clickElementbyXpath(webPage, ForgotpasswordSubmitXpath, softAssert);
			softAssert.assertEquals(commonMethods.getTextbyXpath(webPage, testdata[2][3], softAssert), testdata[2][4],
					"Submit with invalid email");
			// Verifying Goback Link
			commonMethods.clickElementbyXpath(webPage, testdata[3][1], softAssert);
			softAssert.assertEquals(commonMethods.getPageUrl(webPage, softAssert), signInURL,
					"SignIn Page URL using GoBack Link:");
			webPage.getDriver().get(forgetPasswordPageURL);
			Thread.sleep(7000);
			// Submit with valid email ID
			commonMethods.sendKeysbyXpath(webPage, testdata[4][1], testdata[4][2], softAssert);
			commonMethods.clickElementbyXpath(webPage, ForgotpasswordSubmitXpath, softAssert);
			String successMessage = commonMethods.getTextbyXpath(webPage, testdata[4][3], softAssert);
			softAssert.assertTrue(successMessage.contains(testdata[4][4]),
					"Submit with valid email for Forgot Password: Actual:" + successMessage + " Should Contain: "
							+ testdata[4][4]);
			log.info("testing verify_Forgot_Password_Functionality completed------>");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Forgot_Password_Functionality");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 305, enabled = true)
	public void verify_Register_Link_Redirection_And_PageTitle() {
		log.info("************ Stated verify_Register_Link_Redirection_And_PageTitle*******************");
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "CreateAccountSignIn",
					"verifyRegisterPageTitle");
			commonMethods.clickElementbyXpath(webPage, testdata[0][1], softAssert);
			String actualPageUrl = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertTrue(actualPageUrl.contains(testdata[0][2]),
					"Page url verification failed. Expected url : " + testdata[0][2] + "Actual url   :   "
							+ actualPageUrl);
			String actualPageTitle = commonMethods.getPageTitle(webPage, softAssert);
			softAssert.assertEquals(actualPageTitle, testdata[0][3], "Page title verification failed. Expected title : "
					+ testdata[0][1] + "Actual title :   " + actualPageTitle);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Register_Link_Redirection_And_PageTitle");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 306, enabled = true)
	public void verify_ToolTip_For_NewsLetter_And_RememberMe_For_Create_New_Customer() throws InterruptedException {
		log.info(
				"******Started verification of verify_ToolTip_For_NewsLetter_And_RememberMe_For_Create_New_CustomerRegister functionality with Invalid input data ********");
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "CreateAccountSignIn",
					"verifyToolTipForCreateCustomer");
			for (int i = 0; i < 2; i++) {
				log.info("Started iteration: " + i);
				String toolTipText = CreateAccountAndSignInPage.getToolTipText(webPage, softAssert, testdata[i][1]);
				softAssert.assertEquals(toolTipText, testdata[i][2],
						"Tool Tip Text verification failed for :" + testdata[i][0]);
			}
			commonMethods.clickElementbyXpath(webPage, testdata[2][1], softAssert);
			CommonMethods.waitForWebElement(By.xpath(testdata[2][2]), webPage);
			String actualMessage = commonMethods.getTextbyXpath(webPage, testdata[2][2], softAssert);
			softAssert.assertEquals(actualMessage, testdata[2][3], "Failed to verify Overlay Text:");
			commonMethods.clickElementbyXpath(webPage, testdata[2][4], softAssert);
			log.info("testing verify_ToolTip_For_NewsLetter_And_RememberMe_For_Create_New_Customer completed------>");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage,
					"verify_ToolTip_For_NewsLetter_And_RememberMe_For_Create_New_Customer");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 307, enabled = true)
	public void verify_Create_New_Customer_with_Blank_Input() throws InterruptedException {
		log.info("******Started verification of Register functionality with blank input data ********");
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "CreateAccountSignIn",
					"verifyRegisterErrorMessageWithBlankInput");
			commonMethods.clickElementbyXpath(webPage, testdata[0][1], softAssert);
			for (int i = 1; i < 5; i++) {
				CreateAccountAndSignInPage.verifyErrorMessageByXpath(webPage, softAssert, testdata[i][0],
						testdata[i][1], testdata[i][2]);
			}
			log.info(
					"testing verify_Yes_Lease_Page_Mandatory_Field_Error_Message_Validation_With_Blank_Input_On_Submit completed------>");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Page_Title_For_Register_Link");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 308, enabled = true)
	public void verify_Create_New_Customer_with_Invalid_Input() throws InterruptedException {
		log.info("******Started verification of Register functionality with Invalid input data ********");
		SoftAssert softAssert = new SoftAssert();
		webPage.getDriver().get(registerUrl);
		try {
			String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "CreateAccountSignIn",
					"verifyRegisterErrorMessageWithInvalidInput");
			for (int i = 0; i < 5; i++) {
				commonMethods.sendKeysbyXpath(webPage, testdata[i][1], testdata[i][2], softAssert);
			}
			commonMethods.clickElementbyXpath(webPage, testdata[5][1], softAssert);
			for (int i = 6; i < 9; i++) {
				CreateAccountAndSignInPage.verifyErrorMessageByXpath(webPage, softAssert, testdata[i][0],
						testdata[i][1], testdata[i][2]);
			}
			log.info(
					"testing verify_Yes_Lease_Page_Mandatory_Field_Error_Message_Validation_With_Blank_Input_On_Submit completed------>");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Create_New_Customer_with_Invalid_Input");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 309, enabled = true)
	public void verify_Create_New_Customer_with_Valid_Input() throws InterruptedException {
		log.info("******Started verification of Register functionality with VInvalid input data ********");
		SoftAssert softAssert = new SoftAssert();
		webPage.getDriver().get(registerUrl);
		try {
			String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "CreateAccountSignIn",
					"verifyRegisterUserWithValidInput");
			for (int i = 0; i < 5; i++) {
				if (i == 2) {
					commonMethods.sendKeysbyXpath(webPage, testdata[i][1],
							CreateAccountAndSignInPage.CreateNewEmailID(), softAssert);
				} else {
					commonMethods.sendKeysbyXpath(webPage, testdata[i][1], testdata[i][2], softAssert);
				}
			}
			commonMethods.clickElementbyXpath(webPage, testdata[5][1], softAssert);
			CommonMethods.waitForGivenTime(5);
			CommonMethods.waitForWebElement(By.xpath(testdata[6][1]), webPage);
			String actualMessage = commonMethods.getTextbyXpath(webPage, testdata[6][1], softAssert);
			softAssert.assertEquals(actualMessage, testdata[6][2], "SuccessFul user creation Message:");
			userLoggedIn = true;
			log.info("testing verify_Create_New_Customer_with_Valid_Input completed------>");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Create_New_Customer_with_Valid_Input");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 310, enabled = true)
	public void Verify_MyOrders_MyWishList_PayYourBills() throws ClientProtocolException, IOException {
		log.info("testing Verify_MyOrders_MyWishList_PayYourBills completed------>");
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "CreateAccountSignIn",
					"Verify_MyOrders_MyWishList_PayYourBills");
			// String currentUrl = commonMethods.getPageUrl(webPage,
			// softAssert);
			// if (!currentUrl.equals(commonData[8][1])) {
			webPage.getDriver().get(commonData[8][1]);
			// }
			log.info("Started iteration------>");
			// new code
			// webPage.getDriver().navigate().refresh();
			for (int i = 0; i < 3; i++) {
				log.info("Started iteration" + i);
				if (testType.equalsIgnoreCase("Mobile") || testBedName.equalsIgnoreCase("edge")) {
					log.info("Inside if");
					commonMethods.clickElementbyXpath(webPage, testdata[i][7], softAssert);
					commonMethods.clickElementbyXpath(webPage, testdata[i][8], softAssert);
				} else {
					commonMethods.clickElementbyXpath(webPage, testdata[i][1], softAssert);
				}
				// End
				// commonMethods.clickElementbyXpath_usingJavaScript(webPage,
				// testData[i][1], softAssert);
				String actualUrl = commonMethods.getPageUrl(webPage, softAssert);
				softAssert.assertEquals(actualUrl, testdata[i][2], "Page URL:");
				softAssert.assertEquals(commonMethods.getTextbyXpath(webPage, testdata[i][3], softAssert).toLowerCase(),
						testdata[i][4].toLowerCase(), "Verification failed for content: " + testdata[i][0]);
				softAssert.assertEquals(commonMethods.getTextbyXpath(webPage, testdata[i][5], softAssert).toLowerCase(),
						testdata[i][6].toLowerCase(), "Verification failed for content: " + testdata[i][0]);
				if (testType.equalsIgnoreCase("Mobile") || testBedName.equalsIgnoreCase("edge")) {
					if (!(i == 2)) {
						commonMethods.clickElementbyXpath(webPage, testdata[i][9], softAssert);
					}
				}
			}
			softAssert.assertAll();
			log.info("testing Verify_MyOrders_MyWishList_PayYourBills completed------>");
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Broken_Links");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 311, enabled = true)
	public void verify_NewsLetters() throws InterruptedException {
		log.info("******Started verify_NewsLetters ********");
		SoftAssert softAssert = new SoftAssert();
		if (userLoggedIn == true) {
			try {
				// String actualUrl = commonMethods.getPageUrl(webPage,
				// softAssert);
				// if (!actualUrl.equals(commonData[8][1])) {
				 webPage.getDriver().get(commonData[8][1]);
				// }
				String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "CreateAccountSignIn",
						"verify_NewsLetter_Subscription");
				if (testType.equalsIgnoreCase("Mobile") || testBedName.equalsIgnoreCase("edge")) {
					commonMethods.clickElementbyXpath(webPage, testdata[0][8], softAssert);
					commonMethods.clickElementbyXpath(webPage, testdata[0][9], softAssert);
				} else {
					commonMethods.clickElementbyXpath(webPage, testdata[0][1], softAssert);
				}
				// CommonMethods.waitForGivenTime(5);
				commonMethods.clickElementbyXpath(webPage, testdata[0][2], softAssert);
				commonMethods.clickElementbyXpath(webPage, testdata[0][3], softAssert);
				String actualMessage = commonMethods.getTextbyXpath(webPage, testdata[0][4], softAssert);
				softAssert.assertEquals(actualMessage, testdata[0][5], "Newsletter Updation Message:");
				actualMessage = commonMethods.getTextbyXpath(webPage, testdata[0][6], softAssert);
				softAssert.assertEquals(actualMessage, testdata[0][7], "Newsletter subscription Message:");
				log.info("testing verify_NewsLetters completed------>");
				softAssert.assertAll();
			} catch (Throwable e) {
				mainPage.getScreenShotForFailure(webPage, "verify_NewsLetters");
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
		} else {
			Assert.fail("Declined state could not be generated");
		}
	}

	@Test(priority = 312, enabled = true)
	public void verify_Account_Information() throws InterruptedException {
		log.info("******Started verify_Account_Information ********");
		SoftAssert softAssert = new SoftAssert();
		if (userLoggedIn == true) {
			try {
				String actualUrl = commonMethods.getPageUrl(webPage, softAssert);
				//if (!actualUrl.equals(commonData[8][1])) {
					webPage.getDriver().get(commonData[8][1]);
				//}
				String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "CreateAccountSignIn",
						"verify_Account_Information");
				// new code
				if (testType.equalsIgnoreCase("Mobile") || testBedName.equalsIgnoreCase("edge")) {
					commonMethods.clickElementbyXpath(webPage, testdata[0][10], softAssert);
					commonMethods.clickElementbyXpath(webPage, testdata[0][11], softAssert);
				} else {
					commonMethods.clickElementbyXpath(webPage, testdata[0][1], softAssert);
				}
				// End
				// commonMethods.clickElementbyXpath_usingJavaScript(webPage,
				// testdata[0][1], softAssert);
				commonMethods.clickElementbyXpath(webPage, testdata[0][2], softAssert);
				CommonMethods.waitForGivenTime(5);
				log.info("Validation for Blank Data Starte----->");
				// Validate with blank data
				for (int i = 3; i < 6; i++) {
					commonMethods.clearTextBox(webPage, testdata[0][i], softAssert);
				}
				Thread.sleep(3000);
			//	commonMethods.clickElementbyXpath(webPage, testdata[0][9], softAssert);
				for (int i = 3; i < 9; i++) {
					softAssert.assertEquals(commonMethods.getTextbyXpath(webPage, testdata[1][i], softAssert),
							testdata[1][1], "Error Message: failed for: " + testdata[1][i]);
				}
				log.info("Validation for Blank Data Completed----->");
				log.info("Validation for Invalid Data Started----->");
				// Validate with invalid data
				for (int i = 3; i < 9; i++) {
					commonMethods.sendKeysbyXpath(webPage, testdata[0][i], testdata[2][i], softAssert);
				}
				webPage.findObjectByxPath(testdata[0][8]).sendKeys(Keys.TAB);
				softAssert.assertEquals(commonMethods.getTextbyXpath(webPage, testdata[3][5], softAssert),
						testdata[4][5], "Error Message: failed for: " + testdata[3][5]);
				softAssert.assertEquals(commonMethods.getTextbyXpath(webPage, testdata[3][7], softAssert),
						testdata[4][7], "Error Message: failed for: " + testdata[3][7]);
				softAssert.assertEquals(commonMethods.getTextbyXpath(webPage, testdata[3][8], softAssert),
						testdata[4][8], "Error Message: failed for: " + testdata[3][8]);
				log.info("Validation for Invalid Data Completed----->");
				log.info("Validation for Valid Data Started----->");
				// With Valid Data
				for (int i = 3; i < 9; i++) {
					commonMethods.clearTextBox(webPage, testdata[0][i], softAssert);
				}
				String newEmailID = "New" + CreateAccountAndSignInPage.CreateNewEmailID();
				for (int i = 3; i < 9; i++) {
					if (i == 5) {
						commonMethods.sendKeysbyXpath(webPage, testdata[0][i], newEmailID, softAssert);
					} else {
						commonMethods.sendKeysbyXpath(webPage, testdata[0][i], testdata[5][i], softAssert);
					}
				}
				commonMethods.clickElementbyXpath(webPage, testdata[0][9], softAssert);
				softAssert.assertEquals(commonMethods.getTextbyXpath(webPage, testdata[5][1], softAssert),
						testdata[5][2], "Verification failed for: " + testdata[5][1]);
				softAssert.assertTrue(
						commonMethods.getTextbyXpath(webPage, testdata[5][9], softAssert).contains(newEmailID),
						"Verification failed for Contact Information: Xpath" + testdata[5][9] + "Does not contain "
								+ testdata[5][5]);
				log.info("testing verify_Account_Information completed------>");
				softAssert.assertAll();
			} catch (Throwable e) {
				mainPage.getScreenShotForFailure(webPage, "verify_Account_Information");
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
		} else {
			Assert.fail("Declined state could not be generated");
		}
	}

	@Test(priority = 313, enabled = true)
	public void Verify_Address_Book() throws ClientProtocolException, IOException {
		SoftAssert softAssert = new SoftAssert();
		try {
			String actualUrl = commonMethods.getPageUrl(webPage, softAssert);
		//	if (!actualUrl.equals(commonData[8][1])) {
				webPage.getDriver().get(commonData[8][1]);
	//		}
			String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "CreateAccountSignIn", "Verify_Address_Book");
			// new code
			if (testType.equalsIgnoreCase("Mobile") || testBedName.equalsIgnoreCase("edge")) {
				commonMethods.clickElementbyXpath(webPage, testdata[0][14], softAssert);
				commonMethods.clickElementbyXpath(webPage, testdata[0][15], softAssert);
			} else {
				commonMethods.clickElementbyXpath(webPage, testdata[0][1], softAssert);
			}
			commonMethods.clearTextBox(webPage, testdata[0][2], softAssert);
			commonMethods.clearTextBox(webPage, testdata[0][3], softAssert);
			for (int i = 2; i < 10; i++) {
				if (i == 7 || i == 9) {
					commonMethods.selectDropdownByValue(webPage, testdata[0][i], testdata[1][i], softAssert);
				} else {
					commonMethods.sendKeysbyXpath(webPage, testdata[0][i], testdata[1][i], softAssert);
				}
			}
			commonMethods.clickElementbyXpath(webPage, testdata[0][10], softAssert);
			softAssert.assertEquals(commonMethods.getTextbyXpath(webPage, testdata[1][10], softAssert), testdata[1][11],
					"Verification failed for content: " + testdata[1][0]);
			String billingAddress = commonMethods.getTextbyXpath(webPage, testdata[1][12], softAssert);
			softAssert.assertTrue(billingAddress.contains(testdata[1][2] + " " + testdata[1][3]),
					"Verification failed for Name: ");
			softAssert.assertTrue(billingAddress.contains(testdata[1][6]), "Verification failed for Name: ");
			softAssert.assertTrue(billingAddress.contains(testdata[1][8]), "Verification failed for Name: ");
			String shippingAddress = commonMethods.getTextbyXpath(webPage, testdata[1][13], softAssert);
			softAssert.assertTrue(shippingAddress.contains(testdata[1][2] + " " + testdata[1][3]),
					"Verification failed for Name: ");
			softAssert.assertTrue(shippingAddress.contains(testdata[1][6]), "Verification failed for Name: ");
			softAssert.assertTrue(shippingAddress.contains(testdata[1][8]), "Verification failed for Name: ");
			softAssert.assertAll();
			// For Sign Out
			if (testType.equalsIgnoreCase("Mobile")) {
				commonMethods.clickElementbyXpath_usingJavaScript(webPage, commonData[11][1], softAssert);
				commonMethods.clickElementbyXpath_usingJavaScript(webPage, commonData[9][1], softAssert);
			} else {
				commonMethods.clickElementbyXpath_usingJavaScript(webPage, commonData[6][1], softAssert);
			}
			CommonMethods.waitForGivenTime(10);
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Broken_Links");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	// || testBedName.equalsIgnoreCase("edge")
	@Test(priority = 314, enabled = true)
	public void verify_AccountDashboard_After_SignIn_with_ExistingUser() throws InterruptedException {
		log.info("******Started verify_AccountDashboard_After_SignIn_with_ExistingUser ********");
		SoftAssert softAssert = new SoftAssert();
		if (testType.equalsIgnoreCase("Mobile") || testBedName.equalsIgnoreCase("edge")) {
			if (commonMethods.verifyElementisPresent(webPage, commonData[10][1], softAssert)) {
				commonMethods.clickElementbyXpath_usingJavaScript(webPage, commonData[9][1], softAssert);
			} else {
				if (!commonMethods.verifyElementisPresent(webPage, commonData[4][1], softAssert)) {
					commonMethods.clickElementbyXpath(webPage, commonData[6][1], softAssert);
					CommonMethods.waitForGivenTime(10);
				}
			}
		}
		/*
		 * if (testType.equalsIgnoreCase("Web")) { if
		 * (!commonMethods.verifyElementisPresent(webPage, commonData[4][1],
		 * softAssert)) { commonMethods.clickElementbyXpath(webPage,
		 * commonData[6][1], softAssert); CommonMethods.waitForGivenTime(10); }
		 * } else { if (commonMethods.verifyElementisPresent(webPage,
		 * commonData[10][1], softAssert)) {
		 * commonMethods.clickElementbyXpath_usingJavaScript(webPage,
		 * commonData[9][1], softAssert); } }
		 */
		webPage.getDriver().get(signInURL);
		try {
			String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "CreateAccountSignIn",
					"verify_SignIn_With_Valid_Input");
			for (int i = 0; i < 2; i++) {
				commonMethods.sendKeysbyXpath(webPage, testdata[i][1], testdata[i][2], softAssert);
			}
			commonMethods.clickElementbyXpath(webPage, testdata[2][1], softAssert);
			CommonMethods.waitForGivenTime(5);
			// CommonMethods.waitForWebElement(By.xpath(testdata[3][1]),
			// webPage);
			// String actualMessage = commonMethods.getTextbyXpath(webPage,
			// testdata[3][1], softAssert);
			// softAssert.assertTrue(actualMessage.equalsIgnoreCase(testdata[3][2]),
			// "SuccessFul user Login:");
			// Dashboard Verification
			webPage.getDriver().get(commonData[8][1]);
			String[][] linkData = ExcelUtil.readExcelData(DataFilePath, "CreateAccountSignIn",
					"verify_Account_Dashboard");
			/*
			 * if (testType.equalsIgnoreCase("Web")) {
			 * commonMethods.clickElementbyXpath(webPage, linkData[0][1],
			 * softAssert); CommonMethods.waitForGivenTime(2); String actualUrl
			 * = commonMethods.getPageUrl(webPage, softAssert);
			 * softAssert.assertTrue(actualUrl.contains(linkData[0][2]),
			 * "Page URL navigation failed for :" + linkData[0][0] + " URL:" +
			 * actualUrl + " not same as " + linkData[0][2]); }
			 */
			for (int i = 1; i < linkData.length; i++) {
				log.info("Started Iteration" + i);
				commonMethods.clickElementbyXpath(webPage, linkData[i][1], softAssert);
				CommonMethods.waitForGivenTime(2);
				String actualUrl = commonMethods.getPageUrl(webPage, softAssert);
				softAssert.assertTrue(actualUrl.contains(linkData[i][2]), "Page URL navigation failed for :"
						+ linkData[i][0] + " URL:" + actualUrl + " not same as " + linkData[i][2]);
				commonMethods.clickElementbyXpath(webPage, linkData[i][3], softAssert);
				CommonMethods.waitForGivenTime(2);
			}
			log.info("testing verify_AccountDashboard_After_SignIn_with_ExistingUser completed------>");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_AccountDashboard_After_SignIn_with_ExistingUser");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
}
