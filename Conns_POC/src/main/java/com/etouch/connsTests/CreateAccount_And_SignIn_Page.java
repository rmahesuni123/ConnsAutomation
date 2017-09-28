package com.etouch.connsTests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.logging.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
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
	public void setUp(ITestContext context) throws InterruptedException,
			FileNotFoundException, IOException {
		try {
			testBedName = context.getCurrentXmlTest().getAllParameters()
					.get("testBedName");
			CommonUtil.sop("Test bed Name is " + testBedName);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds()
					.get(testBedName).getTestType();
			commonMethods = new CommonMethods();
			browserName = TestBedManager.INSTANCE.getCurrentTestBeds()
					.get(testBedName).getBrowser().getName().toLowerCase();
			log.info("Test Type is : " + testType);
			try {
				testEnv = System.getenv().get("Environment");
				log.info("testEnv is : " + System.getenv().get("Environment"));
				path = Paths.get(TestBedManager.INSTANCE.getProfile()
						.getXlsDataConfig().get("testData"));
				DataFilePath = path.toAbsolutePath().toString()
						.replace("Env", testEnv);
				log.info("DataFilePath After is : " + DataFilePath);
				commonData = ExcelUtil.readExcelData(DataFilePath,
						"CreateAccountSignIn",
						"CreateAccountSignInCommonElements");
				url = TestBedManagerConfiguration.INSTANCE.getWebConfig()
						.getURL();
				registerUrl = commonData[1][1];
				signInURL = commonData[2][1];
				forgetPasswordPageURL = commonData[3][1];

				synchronized (this) {
					webPage = new WebPage(context);
					mainPage = new ConnsMainPage(url, webPage);
					CreateAccountAndSignInPage = new CreateAccountAndSignInPage(
							url, webPage);
					log.info(mainPage);
				}
			} catch (Exception e) {
				log.info("errr is " + e);
				SoftAssertor.addVerificationFailure(e.getMessage());
			}
			CommonMethods.navigateToPage(webPage, registerUrl);
		} catch (Exception e) {
			CommonUtil.sop("errr is for" + testBedName + " -----------" + e);
			SoftAssertor.addVerificationFailure(e.getMessage());
		}
	}

	@Test(priority = 301, enabled = true)
	public void verify_Page_Title_For_Register_Link() {
		log.info("************ Stated verify_Page_Title_For_Register_Link*******************");
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testdata = ExcelUtil.readExcelData(DataFilePath,
					"CreateAccountSignIn", "verifyRegisterPageTitle");
			String actualPageUrl = commonMethods
					.getPageUrl(webPage, softAssert);
			softAssert.assertTrue(actualPageUrl.contains(testdata[0][0]),
					"Page url verification failed. Expected url : "
							+ testdata[0][0] + "Actual url   :   "
							+ actualPageUrl);
			String actualPageTitle = commonMethods.getPageTitle(webPage,
					softAssert);
			softAssert.assertEquals(actualPageTitle, testdata[0][1],
					"Page title verification failed. Expected title : "
							+ testdata[0][1] + "Actual title :   "
							+ actualPageTitle);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage,
					"verify_Page_Title_For_Register_Link");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 302, enabled = true)
	public void verify_ToolTip_For_NewsLetter_And_RememberMe_For_Create_New_Customer()
			throws InterruptedException {
		log.info("******Started verification of verify_ToolTip_For_NewsLetter_And_RememberMe_For_Create_New_CustomerRegister functionality with Invalid input data ********");
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testdata = ExcelUtil.readExcelData(DataFilePath,
					"CreateAccountSignIn", "verifyToolTipForCreateCustomer");
			for (int i = 0; i < 2; i++) {
				log.info("Started iteration: " + i);
				String toolTipText = CreateAccountAndSignInPage.getToolTipText(
						webPage, softAssert, testdata[i][1]);
				softAssert.assertEquals(toolTipText, testdata[i][2],
						"Tool Tip Text verification failed for :"
								+ testdata[i][0]);
			}
			commonMethods.clickElementbyXpath(webPage, testdata[2][1],
					softAssert);
			CommonMethods.waitForWebElement(By.xpath(testdata[2][2]), webPage);
			String actualMessage = commonMethods.getTextbyXpath(webPage,
					testdata[2][2], softAssert);
			softAssert.assertEquals(actualMessage, testdata[2][3],
					"Failed to verify Overlay Text:");
			commonMethods.clickElementbyXpath(webPage, testdata[2][4],
					softAssert);
			log.info("testing verify_ToolTip_For_NewsLetter_And_RememberMe_For_Create_New_Customer completed------>");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage,
					"verify_ToolTip_For_NewsLetter_And_RememberMe_For_Create_New_Customer");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 303, enabled = true)
	public void verify_Create_New_Customer_with_Blank_Input()
			throws InterruptedException {
		log.info("******Started verification of Register functionality with blank input data ********");
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testdata = ExcelUtil.readExcelData(DataFilePath,
					"CreateAccountSignIn",
					"verifyRegisterErrorMessageWithBlankInput");
			commonMethods.clickElementbyXpath(webPage, testdata[0][1],
					softAssert);
			for (int i = 1; i < 5; i++) {
				CreateAccountAndSignInPage.verifyErrorMessageByXpath(webPage,
						softAssert, testdata[i][0], testdata[i][1],
						testdata[i][2]);
			}
			log.info("testing verify_Yes_Lease_Page_Mandatory_Field_Error_Message_Validation_With_Blank_Input_On_Submit completed------>");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage,
					"verify_Page_Title_For_Register_Link");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 304, enabled = true)
	public void verify_Create_New_Customer_with_Invalid_Input()
			throws InterruptedException {
		log.info("******Started verification of Register functionality with Invalid input data ********");
		SoftAssert softAssert = new SoftAssert();
		webPage.getDriver().get(registerUrl);
		try {
			String[][] testdata = ExcelUtil.readExcelData(DataFilePath,
					"CreateAccountSignIn",
					"verifyRegisterErrorMessageWithInvalidInput");
			for (int i = 0; i < 5; i++) {
				commonMethods.sendKeysbyXpath(webPage, testdata[i][1],
						testdata[i][2], softAssert);
			}
			commonMethods.clickElementbyXpath(webPage, testdata[5][1],
					softAssert);
			for (int i = 6; i < 9; i++) {
				CreateAccountAndSignInPage.verifyErrorMessageByXpath(webPage,
						softAssert, testdata[i][0], testdata[i][1],
						testdata[i][2]);
			}
			log.info("testing verify_Yes_Lease_Page_Mandatory_Field_Error_Message_Validation_With_Blank_Input_On_Submit completed------>");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage,
					"verify_Create_New_Customer_with_Invalid_Input");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 305, enabled = true)
	public void verify_Create_New_Customer_with_Valid_Input()
			throws InterruptedException {
		log.info("******Started verification of Register functionality with VInvalid input data ********");
		SoftAssert softAssert = new SoftAssert();
		webPage.getDriver().get(registerUrl);
		try {
			String[][] testdata = ExcelUtil.readExcelData(DataFilePath,
					"CreateAccountSignIn", "verifyRegisterUserWithValidInput");
			for (int i = 0; i < 5; i++) {
				if (i == 2) {
					commonMethods.sendKeysbyXpath(webPage, testdata[i][1],
							CreateAccountAndSignInPage.CreateNewEmailID(),
							softAssert);
				} else {
					commonMethods.sendKeysbyXpath(webPage, testdata[i][1],
							testdata[i][2], softAssert);
				}
			}
			commonMethods.clickElementbyXpath(webPage, testdata[5][1],
					softAssert);
			CommonMethods.waitForGivenTime(5);
			CommonMethods.waitForWebElement(By.xpath(testdata[6][1]), webPage);

			String actualMessage = commonMethods.getTextbyXpath(webPage,
					testdata[6][1], softAssert);
			softAssert.assertEquals(actualMessage, testdata[6][2],
					"SuccessFul user creation Message:");
			// For Sign Out
			/*
			 * commonMethods.clickElementbyXpath_usingJavaScript(webPage,
			 * testdata[7][1], softAssert); CommonMethods.waitForGivenTime(10);
			 */
			userLoggedIn = true;
			log.info("testing verify_Create_New_Customer_with_Valid_Input completed------>");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage,
					"verify_Create_New_Customer_with_Valid_Input");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 306, enabled = true)
	public void verify_Account_Dashboard() throws InterruptedException {
		log.info("******Started verify_Account_Dashboard ********");
		SoftAssert softAssert = new SoftAssert();
		if (userLoggedIn == true) {
			try {
				String[][] linkData = ExcelUtil.readExcelData(DataFilePath,
						"CreateAccountSignIn", "verify_Account_Dashboard");
				for (int i = 0; i < linkData.length; i++) {
					commonMethods.clickElementbyXpath(webPage, linkData[i][1],
							softAssert);
					//CommonMethods.waitForGivenTime(10);
					/*CommonMethods.waitForWebElement(By.xpath(linkData[i][1]),
							webPage);*/
					String actualUrl = commonMethods.getPageUrl(webPage,
							softAssert);
					softAssert.assertTrue(actualUrl.contains(linkData[i][2]),
							"Page URL navigation failed for :" + linkData[i][0]
									+ " URL:"+actualUrl+" not same as " + linkData[i][2]);
					if(i>1){
					commonMethods.clickElementbyXpath(webPage, linkData[i][3],
							softAssert);
					//CommonMethods.waitForGivenTime(10);	
					}
				}
				log.info("testing verify_Account_Dashboard completed------>");
				softAssert.assertAll();
			} catch (Throwable e) {
				mainPage.getScreenShotForFailure(webPage,
						"verify_Account_Dashboard");
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
		} else {
			Assert.fail("Declined state could not be generated");
		}
	}

	@Test(priority = 307, enabled = true)
	public void verify_NewsLetters() throws InterruptedException {
		log.info("******Started verify_NewsLetters ********");
		SoftAssert softAssert = new SoftAssert();
		if (userLoggedIn == true) {
			try {
				String[][] testdata = ExcelUtil.readExcelData(DataFilePath,
						"CreateAccountSignIn", "verify_NewsLetter_Subscription");

				commonMethods.clickElementbyXpath(webPage, testdata[0][1],
						softAssert);

				CommonMethods.waitForGivenTime(5);
				commonMethods.clickElementbyXpath(webPage, testdata[0][2],
						softAssert);
				CommonMethods.waitForWebElement(By.xpath(testdata[0][3]),
						webPage);
	
				String actualMessage = commonMethods.getTextbyXpath(webPage,
						testdata[0][4], softAssert);
				softAssert.assertEquals(actualMessage, testdata[0][5],
						"Newsletter Updation Message:");
				actualMessage = commonMethods.getTextbyXpath(webPage,
						testdata[0][6], softAssert);
				softAssert.assertEquals(actualMessage, testdata[0][7],
						"Newsletter subscription Message:");
				log.info("testing verify_NewsLetters completed------>");
				softAssert.assertAll();
			} catch (Throwable e) {
				mainPage.getScreenShotForFailure(webPage,
						"verify_NewsLetters");
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
		} else {
			Assert.fail("Declined state could not be generated");
		}
	}

	@Test(priority = 306, enabled = true)
	public void verify_Forgot_Password_Functionality()
			throws InterruptedException {
		log.info("******Started verify_ForgotPassword_Functionality********");
		SoftAssert softAssert = new SoftAssert();
		webPage.getDriver().get(signInURL);
		try {
			String[][] testdata = ExcelUtil.readExcelData(DataFilePath,
					"CreateAccountSignIn",
					"verify_Forgot_Password_Functionality");
			commonMethods.clickElementbyXpath(webPage, testdata[0][1],
					softAssert);
			String actualUrl = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertEquals(actualUrl, testdata[0][2],
					"ForgotPassword  Page URL:");

			// Submit with Blank
			String ForgotpasswordSubmitXpath = testdata[0][3];
			commonMethods.clickElementbyXpath(webPage,
					ForgotpasswordSubmitXpath, softAssert);
			softAssert.assertEquals(commonMethods.getTextbyXpath(webPage,
					testdata[1][3], softAssert), testdata[1][4],
					"Submit with blank email:");

			// Submit with invalid mail
			commonMethods.sendKeysbyXpath(webPage, testdata[2][1],
					testdata[2][2], softAssert);
			commonMethods.clickElementbyXpath(webPage,
					ForgotpasswordSubmitXpath, softAssert);
			softAssert.assertEquals(commonMethods.getTextbyXpath(webPage,
					testdata[2][3], softAssert), testdata[2][4],
					"Submit with invalid email");

			// Verifying Goback Link
			commonMethods.clickElementbyXpath(webPage, testdata[3][1],
					softAssert);
			softAssert.assertEquals(
					commonMethods.getPageUrl(webPage, softAssert), signInURL,
					"SignIn Page URL using GoBack Link:");

			webPage.getDriver().get(forgetPasswordPageURL);
			Thread.sleep(7000);
			// Submit with valid email ID
			commonMethods.sendKeysbyXpath(webPage, testdata[4][1],
					testdata[4][2], softAssert);
			commonMethods.clickElementbyXpath(webPage,
					ForgotpasswordSubmitXpath, softAssert);
			String successMessage = commonMethods.getTextbyXpath(webPage,
					testdata[4][3], softAssert);
			softAssert.assertTrue(successMessage.contains(testdata[4][4]),
					"Submit with valid email for Forgot Password: Actual:"
							+ successMessage + " Should Contain: "
							+ testdata[4][4]);
			log.info("testing verify_Forgot_Password_Functionality completed------>");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage,
					"verify_Forgot_Password_Functionality");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 307, enabled = true)
	public void Verify_Broken_Links_On_SignIn_Page()
			throws ClientProtocolException, IOException {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] linkData = ExcelUtil.readExcelData(DataFilePath,
					"CreateAccountSignIn", "verifyLinksRedirection");
			for (int i = 0; i < linkData.length; i++) {
				{
					ITafElement link = webPage
							.findObjectByxPath(linkData[i][1]);
					log.info("iteration " + i + " : "
							+ link.getAttribute("href"));
					HttpClient client = HttpClientBuilder.create().build();
					HttpGet request = new HttpGet(link.getAttribute("href"));
					HttpResponse response = client.execute(request);
					log.info("Status code for iteration " + i + " : "
							+ response.getStatusLine().getStatusCode());
					softAssert.assertEquals(response.getStatusLine()
							.getStatusCode(), 200, "Validation for "
							+ linkData[i][0] + " :");
				}
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Broken_Links");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 308, enabled = true)
	public void verify_SignInErrorMessage_with_Blank_Input()
			throws InterruptedException {
		log.info("******Started verification of Sign In functionality with blank input data ********");
		SoftAssert softAssert = new SoftAssert();
		webPage.getDriver().get(signInURL);
		try {
			String[][] testdata = ExcelUtil.readExcelData(DataFilePath,
					"CreateAccountSignIn", "verify_SignIn_with_Blank_Input");
			commonMethods.clickElementbyXpath(webPage, testdata[0][1],
					softAssert);
			for (int i = 1; i < 3; i++) {
				CreateAccountAndSignInPage.verifyErrorMessageByXpath(webPage,
						softAssert, testdata[i][0], testdata[i][1],
						testdata[i][2]);
			}
			log.info("testing verify_SignInErrorMessage_with_Blank_Input completed------>");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage,
					"verify_SignInErrorMessage_with_Blank_Input");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 309, enabled = true)
	public void verify_SignInErrorMessage_with_Invalid_Input()
			throws InterruptedException {
		log.info("******Started verify_SignInErrorMessage_with_Invalid_Input ********");
		SoftAssert softAssert = new SoftAssert();
		webPage.getDriver().get(signInURL);
		try {
			String[][] testdata = ExcelUtil.readExcelData(DataFilePath,
					"CreateAccountSignIn", "verify_SignIn_With_Invalid_Input");
			for (int i = 0; i < 2; i++) {
				commonMethods.sendKeysbyXpath(webPage, testdata[i][1],
						testdata[i][2], softAssert);
			}
			commonMethods.clickElementbyXpath(webPage, testdata[2][1],
					softAssert);
			for (int i = 3; i < 5; i++) {
				CreateAccountAndSignInPage.verifyErrorMessageByXpath(webPage,
						softAssert, testdata[i][0], testdata[i][1],
						testdata[i][2]);
			}
			log.info("testing verify_SignInErrorMessage_with_Invalid_Input completed------>");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage,
					"verify_SignInErrorMessage_with_Invalid_Input");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 310, enabled = true)
	public void verify_SignIn_with_Valid_Input() throws InterruptedException {
		log.info("******Started verification of SignIn functionality with Valid input data ********");
		SoftAssert softAssert = new SoftAssert();
		webPage.getDriver().get(signInURL);
		try {
			String[][] testdata = ExcelUtil.readExcelData(DataFilePath,
					"CreateAccountSignIn", "verify_SignIn_With_Valid_Input");
			for (int i = 0; i < 2; i++) {
				commonMethods.sendKeysbyXpath(webPage, testdata[i][1],
						testdata[i][2], softAssert);

			}
			commonMethods.clickElementbyXpath(webPage, testdata[2][1],
					softAssert);
			CommonMethods.waitForGivenTime(5);
			CommonMethods.waitForWebElement(By.xpath(testdata[3][1]), webPage);

			String actualMessage = commonMethods.getTextbyXpath(webPage,
					testdata[3][1], softAssert);
			softAssert.assertEquals(actualMessage, testdata[3][2],
					"SuccessFul user Login:");

			// commonMethods.clickElementbyXpath_usingJavaScript(webPage,
			// testdata[4][1],softAssert);
			// CommonMethods.waitForGivenTime(10);
			log.info("testing verify_SignIn_with_Valid_Input completed------>");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage,
					"verify_SignIn_with_Valid_Input");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

}