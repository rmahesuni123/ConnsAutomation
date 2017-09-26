package com.etouch.connsTests;

import java.awt.AWTException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.etouch.common.BaseTest;
import com.etouch.common.CommonMethods;
import com.etouch.connsPages.ConnsMainPage;
import com.etouch.connsPages.YesLeasePage;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.datamanager.excel.annotations.IExcelDataFiles;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.taf.webui.ITafElement;
import com.etouch.taf.webui.selenium.WebPage;

@Test(groups = "YesMoneyCreditApplication")
@IExcelDataFiles(excelDataFiles = { "CreditAppData=testData" })
public class Conns_YesLease_Page extends BaseTest {
	private String testBedName;
	Path path;
	String DataFilePath;
	protected String testType, browserName;
	String currentTestBedName;
	static Log log = LogUtil.getLog(Conns_YesLease_Page.class);
	private String url, testEnv;
	protected WebPage webPage;
	private ConnsMainPage mainPage;
	protected static LinkedHashMap<String, String> commonData;
	protected static CommonMethods commonMethods;
	boolean declinedStatus = false;
	String[][] YesLeaseData;
	YesLeasePage yesLeasePage = new YesLeasePage();
	Random random = new Random();

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
				commonData = CommonMethods.getDataInHashMap(DataFilePath, "CreditApp", "CreditAppCommonElements");
				url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL();
				synchronized (this) {
					webPage = new WebPage(context);
					mainPage = new ConnsMainPage(url, webPage);
					log.info(mainPage);
				}
			} catch (Exception e) {
				log.info("errr is " + e);
				SoftAssertor.addVerificationFailure(e.getMessage());
			}
			CommonMethods.navigateToPage(webPage, url);
		} catch (Exception e) {
			CommonUtil.sop("errr is for" + testBedName + " -----------" + e);
			SoftAssertor.addVerificationFailure(e.getMessage());
		}
	}

	@AfterClass
	public void releaseResources() throws IOException, AWTException {
	}

	@Test(priority = 1101, enabled = true, description = "verify_Credit_App_Submit_With_Status_Declined")
	public void verify_Credit_App_Submit_With_Main_Source_Of_Income_As_Retired_Statues_Declined() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "YesLeasePage",
					"verifyCreditAppSubmitForYesLease");
			YesLeasePage.navigateToCreditAppPage(commonData, webPage, softAssert);
			synchronized (this) {
				int ssnSerial = 1000 + random.nextInt(8999);
				testData[23][3] = String.valueOf(ssnSerial);
			}
			YesLeasePage.fillForm(webPage, softAssert, testData);
			yesLeasePage.submitCreditAppAndVerifyStatus(webPage, commonData, softAssert, "DeclinedPage");
			softAssert.assertAll();
			declinedStatus = true;
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage,
					"verify_Credit_App_Submit_With_Main_Source_Of_Income_As_Retired_Status_Declined");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 11002, enabled = true, description = "verify_Page_Content")
	public void verify_Page_Content() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		log.info("testing verifyPageContent started------>");
		if (declinedStatus == true) {
			String[][] contentData = ExcelUtil.readExcelData(DataFilePath, "YesLeasePage", "verifyPageContent");
			for (int i = 0; i < contentData.length; i++) {
				log.info("testing verifying Page Content for element no. " + i);
				String actualContent = webPage.findObjectByxPath(contentData[i][0]).getText();
				log.info("Actual:  " + actualContent + "   Expected: " + contentData[i][1]);
				softAssert.assertTrue(
						actualContent.toLowerCase().trim().contains(contentData[i][1].toLowerCase().trim()),
						"expectedContent: " + contentData[i][1] + "  Failed to Match Actual:" + actualContent);
				log.info("testing verifyPageContent Completed------>");
			}
			log.info("Ending verify_Page_Content");
		} else {
			Assert.fail("Declined state could not be generated");
		}
		softAssert.assertAll();
	}

	@Test(priority = 11003, enabled = true, description = "Verify_Broken_Links")
	public void Verify_Broken_Links() throws ClientProtocolException, IOException {
		SoftAssert softAssert = new SoftAssert();
		try {
			if (declinedStatus == true) {
				String[][] linkData = ExcelUtil.readExcelData(DataFilePath, "YesLeasePage", "verifyLinksRedirection");
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
			} else {
				Assert.fail("Declined state could not be generated");
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Broken_Links");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1104, enabled = true, description = "verify_Yes_Lease_Page_Mandatory_Field_Error_Message_Validation_With_Blank_Input_On_Submit")
	public void verify_Yes_Lease_Page_Mandatory_Field_Error_Message_Validation_With_Blank_Input_On_Submit()
			throws Exception {
		log.info(
				"testing verify_Yes_Lease_Page_Mandatory_Field_Error_Message_Validation_With_Blank_Input_On_Submit started------>");
		SoftAssert softAssert = new SoftAssert();
		if (declinedStatus == true) {
			try {
				YesLeaseData = ExcelUtil.readExcelData(DataFilePath, "YesLeasePage", "verifyYesLeasePageData");
				commonMethods.clickElementbyXpath(webPage, YesLeaseData[56][1], softAssert);
				for (int i = 0; i < 10; i++) {
					YesLeasePage.verifyErrorMessageByXpath(webPage, softAssert, YesLeaseData[i][0], YesLeaseData[i][1],
							YesLeaseData[i][2]);
				}
				log.info(
						"testing verify_Yes_Lease_Page_Mandatory_Field_Error_Message_Validation_With_Blank_Input_On_Submit completed------>");
				softAssert.assertAll();
			} catch (Throwable e) {
				mainPage.getScreenShotForFailure(webPage,
						"verify_Yes_Lease_Page_Mandatory_Field_Error_Message_Validation_With_Blank_Input_On_Submit");
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
				e.printStackTrace();
			}
		} else {
			Assert.fail("Declined state could not be generated");
		}
	}

	@Test(priority = 1105, enabled = true, description = "verify_Yes_Lease_Page_Field_Validation_With_Invalid_Input")
	public void verify_Yes_Lease_Page_Field_Validation_With_Invalid_Input() throws Exception {
		log.info("testing verify_Yes_Lease_Page_Field_Validation_With_Invalid_Input started------>");
		SoftAssert softAssert = new SoftAssert();
		if (declinedStatus == true) {
			try {
				Calendar cal = Calendar.getInstance();
				DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
				Date date = new Date();
				cal.setTime(date);
				String todays_Date = dateFormat.format(cal.getTime());
				System.out.println("Todays Date: " + todays_Date);
				cal.add(Calendar.DATE, -1);
				String yesterdays_Date = dateFormat.format(cal.getTime());
				System.out.println("yesterdays_Date : " + yesterdays_Date);
				cal.add(Calendar.DATE, 2);
				String tomorrows_Date = dateFormat.format(cal.getTime());
				System.out.println("tomorrows_Date : " + tomorrows_Date);
				cal.add(Calendar.MONTH, -1);
				String PastDate_1month = dateFormat.format(cal.getTime());
				System.out.println("PastDate_1month: " + PastDate_1month);
				cal.add(Calendar.MONTH, -1);
				String PastDate_2month = dateFormat.format(cal.getTime());
				System.out.println("PastDate_2month: " + PastDate_2month);
				cal.add(Calendar.MONTH, 3);
				String futureDate_1month = dateFormat.format(cal.getTime());
				System.out.println("futureDate_1month: " + futureDate_1month);
				cal.add(Calendar.MONTH, 2);
				cal.add(Calendar.DATE, 15);
				String futureDate_4thmonth = dateFormat.format(cal.getTime());
				System.out.println("futureDate_4thmonth: " + futureDate_4thmonth);
				String url = commonMethods.getPageUrl(webPage, softAssert);
				YesLeasePage.enterAccountSpecificDetails(webPage, YesLeaseData, 0);
				YesLeasePage.selectSpecificValuesWithGivenDate(webPage, YesLeaseData, "02-30-2017", futureDate_1month,
						PastDate_1month, futureDate_1month);
				Thread.sleep(2000);
				softAssert.assertEquals(webPage.findObjectByxPath(YesLeaseData[41][1]).getText(), YesLeaseData[0][2],
						"Hire Date with Invalid Date:");
				softAssert.assertEquals(webPage.findObjectByxPath(YesLeaseData[42][1]).getText(), YesLeaseData[2][2],
						"Last Pay Date with futureDate_1month Date:");
				softAssert.assertEquals(webPage.findObjectByxPath(YesLeaseData[43][1]).getText(), YesLeaseData[40][1],
						"Next Pay Date with past 1 month Date:");
				softAssert.assertEquals(webPage.findObjectByxPath(YesLeaseData[46][1]).getText(), YesLeaseData[7][2],
						"Account Open Date with 1 month Future Date:");
				SoftAssertor.assertEquals(webPage.findObjectByxPath(YesLeaseData[28][1]).getText(), YesLeaseData[29][1],
						"Card Error Message: ");
				SoftAssertor.assertEquals(webPage.findObjectByxPath(YesLeaseData[32][1]).getText(), YesLeaseData[33][1],
						"Bank Routing Error Message: ");
				SoftAssertor.assertEquals(webPage.findObjectByxPath(YesLeaseData[37][1]).getText(), YesLeaseData[38][1],
						"Account Number Error Message for 1st iteration: ");
				webPage.getDriver().get(url);
				CommonMethods.waitForGivenTime(5);
				YesLeasePage.enterAccountSpecificDetails(webPage, YesLeaseData, 1);
				YesLeasePage.selectSpecificValuesWithGivenDate(webPage, YesLeaseData, futureDate_1month,
						PastDate_2month, PastDate_1month, yesterdays_Date);
				Thread.sleep(2000);
				softAssert.assertEquals(webPage.findObjectByxPath(YesLeaseData[41][1]).getText(), YesLeaseData[0][2],
						"Hire Date with futureDate_1month validation:");
				softAssert.assertEquals(webPage.findObjectByxPath(YesLeaseData[47][1]).getText(), YesLeaseData[48][1],
						"Last Pay Date with PastDate_2month validation:");
				/*
				 * softAssert.assertEquals(webPage.findObjectByxPath(
				 * YesLeaseData[44][1]).getText(), YesLeaseData[45][1],
				 * "Next Pay Date with future 4th Month validation:");
				 */
				softAssert.assertFalse(CommonMethods.verifyElementisPresent(webPage, YesLeaseData[46][1]),
						"Account Open Date with yesterday's Date:");
				// softAssert.assertNotEquals(CardNumberField.getText(),
				// "700004A", "Card Number Field");
				SoftAssertor.assertEquals(webPage.findObjectByxPath(YesLeaseData[53][1]).getText(), YesLeaseData[53][2],
						"Card Error Message: ");
				SoftAssertor.assertEquals(webPage.findObjectByxPath(YesLeaseData[5][1]).getText(), YesLeaseData[5][2],
						"Bank Routing Error Message: ");
				SoftAssertor.assertEquals(webPage.findObjectByxPath(YesLeaseData[37][1]).getText(), YesLeaseData[38][1],
						"Account Number Error Message for 2nd iteration: ");
				webPage.getDriver().get(url);
				CommonMethods.waitForGivenTime(5);
				YesLeasePage.enterAccountSpecificDetails(webPage, YesLeaseData, 2);
				YesLeasePage.selectSpecificValuesWithGivenDate(webPage, YesLeaseData, PastDate_1month, yesterdays_Date,
						tomorrows_Date, PastDate_2month);
				Thread.sleep(2000);
				softAssert.assertFalse(CommonMethods.verifyElementisPresent(webPage, YesLeaseData[41][1]),
						"Hire Date with PastDate_1month validation:");
				softAssert.assertFalse(CommonMethods.verifyElementisPresent(webPage, YesLeaseData[42][1]),
						"Last Pay Date with yesterdays_Date validation:");
				softAssert.assertFalse(CommonMethods.verifyElementisPresent(webPage, YesLeaseData[43][1]),
						"Next Pay Date with tomorrows_Date:");
				softAssert.assertFalse(CommonMethods.verifyElementisPresent(webPage, YesLeaseData[46][1]),
						"Account Open Date with Past 2 month Date");
				/*
				 * softAssert.assertNotEquals(accountNumberField.getText(),
				 * "41111100011000128212334", "Account Number Field");
				 */
				SoftAssertor.assertEquals(webPage.findObjectByxPath(YesLeaseData[28][1]).getText(), YesLeaseData[29][1],
						"Card Error Message: ");
				SoftAssertor.assertEquals(webPage.findObjectByxPath(YesLeaseData[32][1]).getText(), YesLeaseData[33][1],
						"Bank Routing Error Message: ");
				if (testBedName.contains("iPadNative") || testBedName.contains("iPhoneNative")
						|| testBedName.equalsIgnoreCase("Safari")) {
					softAssert.assertTrue(CommonMethods.verifyElementisPresent(webPage, YesLeaseData[37][1]),
							"Account Number Error Message for 3rd iteration: ");
				} else {
					softAssert.assertFalse(CommonMethods.verifyElementisPresent(webPage, YesLeaseData[37][1]),
							"Account Number Error Message for 3rd iteration: ");
				}
				webPage.getDriver().get(url);
				CommonMethods.waitForGivenTime(5);
				YesLeasePage.enterAccountSpecificDetails(webPage, YesLeaseData, 3);
				YesLeasePage.selectSpecificValuesWithGivenDate(webPage, YesLeaseData, yesterdays_Date, PastDate_1month,
						futureDate_1month, PastDate_1month);
				Thread.sleep(2000);
				softAssert.assertFalse(CommonMethods.verifyElementisPresent(webPage, YesLeaseData[41][1]),
						"Hire Date with yesterdays_Date validation:");
				softAssert.assertFalse(CommonMethods.verifyElementisPresent(webPage, YesLeaseData[42][1]),
						"Last Pay Date with Past 1 month Date validation:");
				softAssert.assertFalse(CommonMethods.verifyElementisPresent(webPage, YesLeaseData[44][1]),
						"Next Pay Date with future 1 month Date:");
				softAssert.assertFalse(CommonMethods.verifyElementisPresent(webPage, YesLeaseData[46][1]),
						"Account Open Date with Past 1 month Date");
				commonMethods.selectDropdownByValue(webPage, YesLeaseData[13][1], YesLeaseData[39][1]);
				commonMethods.clickElementbyXpath(webPage, YesLeaseData[23][1], softAssert);
				commonMethods.clickElementbyXpath(webPage, YesLeaseData[54][1], softAssert);
				softAssert.assertFalse(CommonMethods.verifyElementisPresent(webPage, YesLeaseData[29][1]),
						"Card Error Message: ");
				softAssert.assertFalse(CommonMethods.verifyElementisPresent(webPage, YesLeaseData[32][1]),
						"Bank Routing Error Message: ");
				softAssert.assertFalse(CommonMethods.verifyElementisPresent(webPage, YesLeaseData[37][1]),
						"Account Number Error Message for 4th iteration: ");
				log.info("testing verify_Yes_Lease_Page_Field_Validation_With_Invalid_Input completed------>");
				softAssert.assertAll();
			} catch (Throwable e) {
				mainPage.getScreenShotForFailure(webPage, "verify_Yes_Lease_Page_Field_Validation_With_Invalid_Input");
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
				e.printStackTrace();
			}
		} else {
			Assert.fail("Declined state could not be generated");
		}
	}

	@Test(priority = 1106, enabled = true, description = "verify_YesLease_Submit_With_Valid_Data")
	public void verify_YesLease_Submit_With_Valid_Data() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			commonMethods.clickElementbyXpath(webPage, YesLeaseData[56][1], softAssert);
			CommonMethods.waitForGivenTime(20);
			System.out.println("Url is : " + commonMethods.getPageUrl(webPage, softAssert));
			if (commonMethods.getPageUrl(webPage, softAssert).contains("processing")) {
				log.info("Processing Page is Displayed");
				Thread.sleep(8000);
			} else {
				log.info("Unable to catch processing page");
			}
			commonMethods.waitForPageLoad(webPage, softAssert);
			String actualUrl = commonMethods.getPageUrl(webPage, softAssert);
			String[] statusCodeList = YesLeaseData[25][1].split(". ");
			if (actualUrl.contains(statusCodeList[0]) || (actualUrl.contains(statusCodeList[1]))
					|| (actualUrl.contains(statusCodeList[2])) || (actualUrl.contains(statusCodeList[3]))) {
				softAssert.assertEquals(webPage.findObjectByxPath(YesLeaseData[49][1]).getText(), YesLeaseData[50][1],
						"Yes Lease page Header:");
			} else {
				SoftAssertor.assertFail("Current URL is not as expected, Actual URL is: " + actualUrl);
			}
			log.info("testing verify_Yes_Lease_Page_Submition completed------>");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_YesLease_Submit_With_Unique_ID");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1107, enabled = true, description = "verify_YesLease_Submit_For_Unique_ID")
	public void verify_YesLease_Submit_For_Unique_ID() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			YesLeaseData = ExcelUtil.readExcelData(DataFilePath, "YesLeasePage", "verifyYesLeasePageData");
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "YesLeasePage",
					"verifyForYesLeaseSubmitWithUniqueID");
			YesLeasePage.navigateToCreditAppPage(commonData, webPage, softAssert);
			synchronized (this) {
				int ssnSerial = 1000 + random.nextInt(8999);
				testData[23][3] = String.valueOf(ssnSerial);
			}
			YesLeasePage.fillForm(webPage, softAssert, testData);
			yesLeasePage.submitCreditAppAndVerifyStatus(webPage, commonData, softAssert, "DeclinedPage");
			String url = webPage.getCurrentUrl() + YesLeaseData[51][1];
			webPage.getDriver().get(url);
			YesLeasePage.submitYesLeaseWithValidData(webPage, YesLeaseData, softAssert);
			String actualUrl = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertTrue(actualUrl.contains(YesLeaseData[51][2]),
					"Unique ID Yes Lease page URL:" + actualUrl + " does not conatain: " + YesLeaseData[51][2]);
			SoftAssertor.assertEquals(webPage.findObjectByxPath(YesLeaseData[52][1]).getText(), YesLeaseData[52][2],
					"Yes Lease with uniqueID header Validation:");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_YesLease_Submit_For_Unique_ID");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
}
