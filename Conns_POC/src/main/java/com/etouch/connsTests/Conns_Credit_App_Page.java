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

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.etouch.common.BaseTest;
import com.etouch.common.CommonMethods;
import com.etouch.common.TafExecutor;
import com.etouch.connsPages.ConnsAccountAndSignInPage;
import com.etouch.connsPages.ConnsMainPage;
import com.etouch.connsPages.CreditAppPage;
import com.etouch.taf.core.TestBed;
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
public class Conns_Credit_App_Page extends BaseTest {
	private String testBedName;
	TestBed testBed;
	Path path;
	String DataFilePath;
	protected static String testType, browserName;
	String currentTestBedName;
	static Log log = LogUtil.getLog(Conns_Account_And_SignIn_Page.class);
	Logger logger = Logger.getLogger(ConnsAccountAndSignInPage.class.getName());
	private String url, testEnv;
	protected static WebPage webPage;
	private ConnsAccountAndSignInPage ConnsSignInPage;
	private ConnsMainPage mainPage;
	protected static LinkedHashMap<String, String> commonData;
	protected static CommonMethods commonMethods;
	static String platform;
	static String AbsolutePath = TafExecutor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	static String videoLocation = AbsolutePath.substring(0, AbsolutePath.indexOf("/target/classes/")).substring(1)
			.concat("/src/test/resources/testdata/videos");
	boolean declinedStatus = false;
	String[][] YesLeaseData;

	/*** Prepare before class @throws Exception the exception */
	@BeforeClass(alwaysRun = true)
	public void setUp(ITestContext context) throws InterruptedException, FileNotFoundException, IOException {
		try {
			testBedName = context.getCurrentXmlTest().getAllParameters().get("testBedName");
			CommonUtil.sop("Test bed Name is " + testBedName);
			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			commonMethods = new CommonMethods();
			browserName = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getBrowser().getName()
					.toLowerCase();
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
				commonData = CommonMethods.getDataInHashMap(DataFilePath, "CreditApp", "CreditAppCommonElements");
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

	@AfterClass
	public void releaseResources() throws IOException, AWTException {
	}

	/**
	 * Test Case 001 - Verify Navigation to Yes Money Credit Application Page
	 * and Verify Page title
	 * 
	 * @throws Exception
	 * 
	 */
	@Test(priority = 1001, enabled = true, description = "Verify Page Title")
	public void verify_Page_Title() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			CreditAppPage.navigateToCreditAppPage(softAssert);
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Page_Title");
			softAssert.assertAll();
		}
	}

	/**
	 * Test Case - 002 - Verify Font Size and Style of specified on element on
	 * Yes Money Credit Application page
	 * 
	 * @throws Exception
	 * 
	 */
	@Test(priority = 1002, enabled = true, description = "verify Font Size And Style")
	public void verify_Font_Size_And_Style() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			// CreditAppPage.navigateToCreditAppPage(softAssert);
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "CreditApp", "verifyFont");
			log.info("testing flow verifyFontSizeAndStyle started");
			String pageHeadingClass = null;
			String fontAttribute = null;
			String expectedValue = null;
			int s;
			if (testType.equalsIgnoreCase("Web")) {
				s = 3;
			} else {
				s = 2;
			}
			for (int r = 0; r < test.length; r++) {
				pageHeadingClass = test[r][0];
				fontAttribute = test[r][1];
				expectedValue = test[r][s];
				try {
					log.info("Verifying font size and style for element no. " + r);
					ITafElement pageHeading = webPage.findObjectByClass(pageHeadingClass);
					String value = pageHeading.getCssValue(fontAttribute).replaceAll("\"", "").replaceAll(" ", "")
							.toLowerCase().trim();
					softAssert.assertTrue(value.contains(expectedValue) || expectedValue.contains(value),
							"Verify Font Size and Style failed.!!!" + "Font Attribute name " + fontAttribute
									+ "Actual : " + value + " and Expected :" + expectedValue.trim());
				} catch (Throwable e) {
					softAssert.fail(e.getLocalizedMessage());
					CreditAppPage.navigateToCreditAppPage(softAssert);
				}
			}
			log.info("Ending verify_Font_Size_And_Style");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Font_Size_And_Style");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 003 Verify Page Content are rendered
	 * 
	 * @throws Exception
	 * 
	 */
	@Test(priority = 1003, enabled = true, description = "Verify Page Content")
	public void verify_Page_Content() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		// CreditAppPage.navigateToCreditAppPage(softAssert);
		log.info("testing verifyPageContent started------>");
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "CreditApp", "verifyPageContent");
		boolean returnValue = false;
		String FieldName;
		String Fieldxpath;
		for (int r = 0; r < test.length; r++) {
			FieldName = test[r][0];
			Fieldxpath = test[r][1];
			try {
				log.info("testing verifying Page Content for element no. " + r);
				returnValue = webPage.findObjectByxPath(Fieldxpath).isDisplayed();
				softAssert.assertTrue(returnValue,
						"Verify Page content failed!!! " + FieldName + "Not rendered on page");
				log.info("testing verifyPageContent Completed------>");
			} catch (Exception e) {
				log.info("Failed to verifying Page Content for element no. " + r);
				softAssert.fail(e.getMessage());
				CreditAppPage.navigateToCreditAppPage(softAssert);
			}
			log.info("Ending verify_Page_Content");
		}
		softAssert.assertAll();
	}

	/**
	 * Test Case - 004 Verify all Link on Yes Money credit application page are
	 * functional
	 * 
	 * @throws Exception
	 * 
	 */
	@Test(priority = 1004, enabled = true, description = "verify Link Navigation")
	public void verify_Link_Navigation() throws Exception {
		log.info("testing verifyLinkNavigation started------>");
		SoftAssert softAssert = new SoftAssert();
		try {
			CreditAppPage.navigateToCreditAppPage(softAssert);
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "CreditApp", "verifyLinksforNewUser");
			String linkName = null;
			String locator = null;
			String ExpectedURL = null;
			for (int r = 0; r < test.length; r++) {
				linkName = test[r][0];
				locator = test[r][1];
				ExpectedURL = test[r][2];
				try {
					log.info("Verifying Link --->" + linkName);
					CreditAppPage.validateLinkRedirection(linkName, locator, ExpectedURL, softAssert);
					log.info("testing verify_Link_Navigation completed------>");
				} catch (Throwable e) {
					softAssert.fail(e.getLocalizedMessage());
					CreditAppPage.navigateToCreditAppPage(softAssert);
				}
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Link_Navigation");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1005, enabled = true, description = "verify form is rendered with blank fields")
	public void verify_Form_Is_Displayed_With_Blank_Field() throws Exception {
		// try{
		log.info("testing verifyLinkNavigation started------>");
		SoftAssert softAssert = new SoftAssert();
		try {
			// CreditAppPage.navigateToCreditAppPage(softAssert);
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "CreditApp", "verifyFormIsRenderedWithBlankFields");
			CreditAppPage.verifyTextFieldValue(test, softAssert);
			log.info("testing verify_Form_Is_Displayed_With_Blank_Field completed------>");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Form_Is_Displayed_With_Blank_Field");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1006, enabled = true, description = "verify Mandatory Field Validation WithoutData")
	public void verify_Mandatory_Field_Validation_Without_Data() throws Exception {
		log.info("testing verifyMandatoryFieldValidationWithoutData started------>");
		SoftAssert softAssert = new SoftAssert();
		try {
			// CreditAppPage.navigateToCreditAppPage(softAssert);
			commonMethods.clickElementbyXpath(webPage, commonData.get("SubmitButton"), softAssert);
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifyMandatoryFieldValidationWithoutData");
			for (int i = 0; i < test.length; i++) {
				CreditAppPage.verifyErrorMessageByXpath(softAssert, test[i][0], test[i][1], test[i][2]);
			}
			log.info("testing verify_Mandatory_Field_Validation_Without_Data completed------>");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Mandatory_Field_Validation_Without_Data");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case 007 - Verify field validation Error Message for fields with
	 * invalid data : Email,city,ZipCode,Cell Phone,Home phone,Alternate
	 * Phone,Monthly Mortage Rent,Monthly Income,Other income
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1007, enabled = true, description = "verify Error Msg With Blank Data")
	public void verify_Field_Validation_Error_Message_With_InValid_Data() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			CreditAppPage.navigateToCreditAppPage(softAssert);
			log.info("testing verifyFieldValidationErrorMessageWithInValidData started------>");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifyFieldValidationErrorMessageWithInValidData");
			if (browserName.contains("iphone") || browserName.contains("ipad") || browserName.contains("safari")) {
				CreditAppPage.verifyErrorMessageForIos(softAssert, test);
			} else {
				for (int r = 0; r < test.length; r++) {
					CreditAppPage.verifyErrorMessageWithInvalidDataById(softAssert, test[r][0], test[r][1], test[r][2],
							test[r][3], test[r][4]);
				}
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Texas_SubLinks");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case 008 - Verify City and State Auto populates after entering valid
	 * Zip Code
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1008, enabled = true, description = "Verify Field Auto Populates")
	public void verify_City_State_Fields_Auto_Populates() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			log.info("testing verifyFieldValidationErrorMessageWithInValidData started------>");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "CreditApp", "verifyFieldAutoPopulates");
			// CreditAppPage.navigateToCreditAppPage(softAssert);
			commonMethods.clearTextBoxById(webPage, test[0][0], softAssert);
			commonMethods.sendKeysById(webPage, test[0][0], test[0][1], softAssert);
			commonMethods.clickElementById(webPage, test[0][2], softAssert);
			softAssert.assertEquals(test[0][3], commonMethods.getTextbyId(webPage, test[0][2], softAssert));
			softAssert.assertEquals(test[0][5],
					CreditAppPage.getSelectedValueFromDropDownID(softAssert, test[0][0], test[0][4]));
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Field_Validation_Error_Message_With_InValid_Data");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case 009 - Verify Verify Years There Drop Down Values
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1009, enabled = true, description = "verify Years There Drop Down Values")
	public void verify_Years_There_DropDown_Values() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			log.info("testing verifyYearsThereDropDownValues started------>");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "CreditApp", "verifyYearsThereDropDownValues");
			String[][] yearsThereDropDownValues = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"yearsThereDropDownValues");
			// CreditAppPage.navigateToCreditAppPage(softAssert);
			CreditAppPage.verifyDropDownValuesById(softAssert, test[0][0], test[0][1], yearsThereDropDownValues);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Years_There_DropDown_Values");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case 010 - verify City And State Fields Are Editable
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1010, enabled = true, description = "verify City And State Fields Are Editable")
	public void verify_City_And_State_Field_Are_Editable() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			log.info("testing verifyFieldValidationErrorMessageWithInValidData started------>");
			LinkedHashMap<String, String> testData = CommonMethods.getDataInHashMap(DataFilePath, "CreditApp",
					"verifyCityAndStateFieldAreEditable");
			// CreditAppPage.navigateToCreditAppPage(softAssert);
			commonMethods.clearTextBoxById(webPage, testData.get("ZipcodeID"), softAssert);
			commonMethods.sendKeysById(webPage, testData.get("ZipcodeID"), testData.get("ZipcodeValue"), softAssert);
			commonMethods.clickElementById(webPage, testData.get("CityID"), softAssert);
			CreditAppPage.verifyTextFieldIsEditableByID(softAssert, "City", testData.get("CityID"),
					testData.get("CityValue"));
			CreditAppPage.verifyDropDownFieldIsEditableById(softAssert, "State", testData.get("StateID"),
					testData.get("StateValue"));
			log.info("testing verify_City_And_State_Field_Are_Editable completed------>");
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_City_And_State_Field_Are_Editable");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case 011 - verify Main Source Of Income Field
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1011, enabled = true, description = "verify Main Source Of Income Field")
	public void verify_Main_Source_Of_Income_Field() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			// CreditAppPage.navigateToCreditAppPage(softAssert);
			log.info("testing verifyFieldValidationErrorMessageWithInValidData started------>");
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp", "verifyMainSourceOfIncomeField");
			for (int i = 0; i < testData.length; i++) {
				CreditAppPage.verifyDropDownFieldIsEditableByXpath(softAssert, testData[0][0], testData[0][1],
						testData[i][2]);
			}
			for (int j = 0; j < testData.length; j++) {
				CreditAppPage.selectValueFromDropDownByXpath(softAssert, testData[0][0], testData[0][1],
						testData[j][2]);
				String[][] MonthlyIncomeTestData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
						testData[j][2] + "Data");
				CreditAppPage.fillForm(softAssert, MonthlyIncomeTestData);
				// CreditAppPage.sendTextToTextFieldsById(softAssert,
				// MonthlyIncomeTestData);
				// CreditAppPage.navigateToCreditAppPage(softAssert);
				Thread.sleep(3000);
			}
			log.info("testing verify_Main_Source_Of_Income_Field completed------>");
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Main_Source_Of_Income_Field");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1012, enabled = true, description = "verify_Mandatory_Field_Validation_Without_Data_Main_Source_of_Income_Fields")
	public void verify_Mandatory_Field_Validation_Without_Data_Main_Source_of_Income_Fields() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			CreditAppPage.navigateToCreditAppPage(softAssert);
			log.info(
					"testing verify_Mandatory_Field_Validation_Without_Data_Main_Source_of_Income_Fields started------>");
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifyMainSourceOfIncomeFieldErrorMessage");
			for (int j = 0; j < testData.length; j++) {
				CreditAppPage.selectValueFromDropDownByXpath(softAssert, testData[0][0], testData[0][1],
						testData[j][2]);
				String[][] MonthlyIncomeErrorMessage = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
						testData[j][2] + "DataSet");
				commonMethods.clickElementbyXpath(webPage, commonData.get("SubmitButton"), softAssert);
				for (int k = 0; k < MonthlyIncomeErrorMessage.length; k++) {
					CreditAppPage.verifyErrorMessageByXpath(softAssert, MonthlyIncomeErrorMessage[k][0],
							MonthlyIncomeErrorMessage[k][1], MonthlyIncomeErrorMessage[k][2]);
				}
				// CreditAppPage.navigateToCreditAppPage(softAssert);
			}
			log.info(
					"testing verify_Mandatory_Field_Validation_Without_Data_Main_Source_of_Income_Fields completed------>");
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Main_Source_Of_Income_Field");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1013, enabled = true, description = "verify_Error_Message_for_Reference_Code_Field")
	public void verify_Error_Message_for_Reference_Code_Field() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			CreditAppPage.navigateToCreditAppPage(softAssert);
			log.info("testing verify_Error_Message_for_Reference_Code_Field started------>");
			LinkedHashMap<String, String> testData = commonMethods.getDataInHashMap(DataFilePath, "CreditApp",
					"verifyErrorMessageForRefCode");
			commonMethods.sendKeysbyXpath(webPage, testData.get("RefCodeXpath"), testData.get("RefCodeValue"),
					softAssert);
			commonMethods.clickElementbyXpath(webPage, testData.get("RefCodeSubmitXpath"), softAssert);
			commonMethods.waitForWebElement(By.xpath(testData.get("RefCodeErrorXpath")), webPage);
			Thread.sleep(3000);
			CreditAppPage.verifyErrorMessageByXpath(softAssert, "RefCodeError", testData.get("RefCodeErrorXpath"),
					testData.get("RefCodeError"));
			log.info("testing verify_Error_Message_for_Reference_Code_Field completed------>");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Error_Message_for_Reference_Code_Field");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1014, enabled = true, description = "verify_Reference_Code_With_Valid_Required_Field_Data")
	public void verify_Reference_Code_With_Valid_Required_Field_Data() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			// CreditAppPage.navigateToCreditAppPage(softAssert);
			log.info("testing verify_Reference_Code_With_Valid_Required_Field_Data started------>");
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifyMessageForRefCodeWithValidData");
			CreditAppPage.fillForm(softAssert, testData);
			// Data is used from above testcase
			// verify_Error_Message_for_Reference_Code_Field
			LinkedHashMap<String, String> verifyErrorMessageForRefCodeData = commonMethods
					.getDataInHashMap(DataFilePath, "CreditApp", "verifyErrorMessageForRefCode");
			Thread.sleep(5000);
			CreditAppPage.verifyErrorMessageByXpath(softAssert, "RefCodeError",
					verifyErrorMessageForRefCodeData.get("RefCodeErrorXpath"),
					verifyErrorMessageForRefCodeData.get("RefCodeAccpetMessage"));
			log.info("testing verify_Reference_Code_With_Valid_Required_Field_Data completed------>");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Reference_Code_With_Valid_Required_Field_Data");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1015, enabled = true, description = "verify Successful Submit For Registered User")
	public void verify_Successful_Submit_Status_Approved_With_DP() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			// CreditAppPage.navigateToCreditAppPage(softAssert);
			// CreditAppPage.loginFromCreditApp(softAssert);
			CreditAppPage.navigateToCreditAppPage(softAssert);
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifySuccessfulStatusApprovedWithDP");
			CreditAppPage.fillForm(softAssert, testData);
			// CreditAppPage.verifyFieldValues(testData, softAssert);
			CreditAppPage.submitCreditAppAndVerifyStatus(softAssert, "ApprovedWithDownPaymentPage");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Successful_Submit_Status_Approved_With_DP");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 008 Verify user is successfully able to submit from after
	 * entering valid data in all mandatory fields
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1016, enabled = true, description = "verify Error Msg With Blank Data")
	public void verify_Valid_User_Successful_Submit_For_New_User_Status_Approved_Without_DP() throws Exception {
		log.info("testing verifyValidUserSuccessfulSubmitForNewUser started------>");
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifyValidUserSuccessfulSubmitForNewUser");
			CreditAppPage.navigateToCreditAppPage(softAssert);
			CreditAppPage.fillForm(softAssert, testData);
			CreditAppPage.submitCreditAppAndVerifyStatus(softAssert, "ApprovedWithoutDownPaymentPage");
			// Thread.sleep(10000);
			softAssert.assertAll();
			log.info(
					"testing flow verify_Valid_User_Successful_Submit_For_New_User_Status_Approved_Without_DP Completed");
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage,
					"verify_Valid_User_Successful_Submit_For_New_User_Status_Approved_Without_DP");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1017, enabled = true, description = "verify Successful Submit For Registered User")
	public void verify_Credit_App_Submit_With_Main_Source_Of_Income_As_Employed_Status_Wait() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifyCreditAppSubmitWithMainSourceOfIncomeAsEmployed");
			CreditAppPage.navigateToCreditAppPage(softAssert);
			CreditAppPage.fillForm(softAssert, testData);
			CreditAppPage.submitCreditAppAndVerifyStatus(softAssert, "WaitPage");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage,
					"verify_Credit_App_Submit_With_Main_Source_Of_Income_As_Employed_Status_Wait");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1018, enabled = true, description = "verify Successful Submit For Registered User")
	public void verify_Credit_App_Submit_With_Main_Source_Of_Income_As_Social_Security_Status_Out_Of_State()
			throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifyCreditAppSubmitWithMainSourceOfIncomeAsSocialSecurity");
			CreditAppPage.navigateToCreditAppPage(softAssert);
			CreditAppPage.fillForm(softAssert, testData);
			CreditAppPage.submitCreditAppAndVerifyStatus(softAssert, "OutOfStatePage");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage,
					"verify_Credit_App_Submit_With_Main_Source_Of_Income_As_Social_Security_Status_Out_Of_State");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1020, enabled = true, description = "verify Successful Submit For Registered User")
	public void verify_Credit_App_Submit_With_RefField() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifyCreditAppSubmitWithRefField");
			CreditAppPage.navigateToCreditAppPage(softAssert);
			CreditAppPage.fillForm(softAssert, testData);
			CreditAppPage.submitCreditApp(softAssert);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Credit_App_Submit_With_RefField");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1021, enabled = true, description = "verify Successful Submit For With Main source of Income as Disability Income")
	public void verify_Credit_App_Submit_With_Main_Source_Of_Income_As_Disability_Income() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifyCreditAppSubmitWithMainSourceOfIncomeAsDisabilityIncome");
			CreditAppPage.navigateToCreditAppPage(softAssert);
			CreditAppPage.fillForm(softAssert, testData);
			CreditAppPage.submitCreditApp(softAssert);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage,
					"verify_Credit_App_Submit_With_Main_Source_Of_Income_As_Disability_Income");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1022, enabled = true, description = "verify Successful Submit For Registered User")
	public void verify_Credit_App_Submit_With_Main_Source_Of_Income_As_Spous_And_Partner() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifyCreditAppSubmitWithMainSourceOfIncomeAsSpousAndPartner");
			CreditAppPage.navigateToCreditAppPage(softAssert);
			CreditAppPage.fillForm(softAssert, testData);
			CreditAppPage.submitCreditApp(softAssert);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage,
					"verify_Credit_App_Submit_With_Main_Source_Of_Income_As_Spous_And_Partner");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * logins from creditApp page and verifies if data is auto populated
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1023, enabled = true, description = "verify Successful Submit For Registered User")
	public void verify_Fields_Are_AutoPopulated_For_Registered_User() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			CreditAppPage.navigateToCreditAppPage(softAssert);
			CreditAppPage.loginFromCreditApp(softAssert);
			CreditAppPage.navigateToCreditAppPage(softAssert);
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifyFieldsAutoPopulatedForRegisteredUser");
			CreditAppPage.verifyFieldValues(testData, softAssert);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Fields_Are_AutoPopulated_For_Registered_User");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1024, dependsOnMethods = "verify_Fields_Are_AutoPopulated_For_Registered_User", enabled = true, description = "verify Successful Submit For Registered User")
	public void verify_First_Name_And_Last_Name_Field_Are_Editable() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			// CreditAppPage.navigateToCreditAppPage(softAssert);
			LinkedHashMap<String, String> testData = CommonMethods.getDataInHashMap(DataFilePath, "CreditApp",
					"verifyFirstNameAndLastNameFieldAreEditable");
			if (!CreditAppPage.verifyTextFieldIsEditableByXpath(softAssert, "FirstName",
					testData.get("FirstNameIdentifier"), testData.get("FirstNameData")))
				softAssert.fail("TextBox \"FirstName\" is Not editable. Unable to set new value as : "
						+ testData.get("FirstNameData"));
			if (!CreditAppPage.verifyTextFieldIsEditableByXpath(softAssert, "LastName",
					testData.get("LastNameIdentifier"), testData.get("LastNameData")))
				softAssert.fail("TextBox \"FirstName\" is Not editable. Unable to set new value as : "
						+ testData.get("FirstNameData"));
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_First_Name_And_Last_Name_Field_Are_Editable");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1025, dependsOnMethods = "verify_First_Name_And_Last_Name_Field_Are_Editable", enabled = true, description = "verify Successful Submit For Registered User")
	public void verify_Email_Is_Not_Editable() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			// CreditAppPage.navigateToCreditAppPage(softAssert);
			LinkedHashMap<String, String> testData = CommonMethods.getDataInHashMap(DataFilePath, "CreditApp",
					"verifyEmailIsNotEditable");
			if (!CreditAppPage.verifyElementisPresentByXPath(webPage, testData.get("EmailIdentifier"), softAssert)) {
				log.info("TextBox EmailAddress is Not Displayed");
				softAssert.fail("TextBox EmailAddress is Not Displayed");
			} else {
				SoftAssert softAssert1 = new SoftAssert();
				commonMethods.sendKeysbyXpath(webPage, testData.get("EmailIdentifier"), testData.get("EmailData"),
						softAssert1);
				String value = CreditAppPage.getTextBoxValueByJs("EmailAdress", testData.get("EmailIdentifier"),
						softAssert);
				log.info("textField Value is : " + value);
				log.info("Updated value is : " + testData.get("EmailData"));
				if (value.equals(testData.get("EmailData"))) {
					softAssert.fail("Email Field is editable for Registered user , Able to set new value to " + value);
				}
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Email_Is_Not_Editable");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1026, dependsOnMethods = "verify_Email_Is_Not_Editable", enabled = true, description = "verify Successful Submit For Registered User")
	public void verify_Sign_In_Link_Is_Not_Displayed_For_Registered_User() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			// CreditAppPage.navigateToCreditAppPage(softAssert);
			if (CreditAppPage.verifyElementisPresentByXPath(webPage, commonData.get("SignInNowLink"), softAssert))
				softAssert.fail("Sign In Now Link is Displayed For Registered User");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Sign_In_Link_Is_Not_Displayed_For_Registered_User");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1027, dependsOnMethods = "verify_Sign_In_Link_Is_Not_Displayed_For_Registered_User", enabled = true, description = "verify Successful Submit For Registered User")
	public void verify_Registered_User_Is_Able_To_Fill_Mandatory_Fields() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifyRegisteredUserIsAbleToFillMandatoryFields");
			// CreditAppPage.navigateToCreditAppPage(softAssert);
			CreditAppPage.fillForm(softAssert, testData);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Registered_User_Is_Able_To_Fill_Mandatory_Fields");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * logins from creditApp page and verifies if data is auto populated
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1028, dependsOnMethods = "verify_Registered_User_Is_Able_To_Fill_Mandatory_Fields", enabled = true, description = "verify Successful Submit For Registered User")
	public void verify_Successful_Submit_For_Registered_User_Status_Duplicate() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			// CreditAppPage.navigateToCreditAppPage(softAssert);
			// CreditAppPage.loginFromCreditApp(softAssert);
			// CreditAppPage.navigateToCreditAppPage(softAssert);
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifySuccessfulSubmitForRegisteredUser");
			// CreditAppPage.fillForm(softAssert, testData);
			// CreditAppPage.verifyFieldValues(testData, softAssert);
			CreditAppPage.submitCreditAppAndVerifyStatus(softAssert, "DuplicatePage");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "verify_Successful_Submit_For_Registered_User_Status_Duplicate");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1029, enabled = true, description = "verify Successful Submit With Main source of Income as Retired And App_Status as Declined")
	public void verify_Credit_App_Submit_With_Main_Source_Of_Income_As_Retired_Statue_Declined() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifyCreditAppSubmitForYesLease");
			CreditAppPage.navigateToCreditAppPage(softAssert);
			CreditAppPage.fillForm(softAssert, testData);
			CreditAppPage.submitCreditAppAndVerifyStatus(softAssert, "DeclinedPage");
			softAssert.assertAll();
			declinedStatus = true;
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage,
					"verify_Credit_App_Submit_With_Main_Source_Of_Income_As_Retired_Statue_Declined");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1030, enabled = true, description = "verify_Yes_Lease_MandatoryFiedlValidation")
	public void verify_Yes_Lease_Page_MandatoryFiedlValidation() throws Exception {
		log.info("testing verify_Yes_Lease_Page_MandatoryFiedlValidation started------>");
		SoftAssert softAssert = new SoftAssert();
		if (declinedStatus == true) {
			try {
				commonMethods.clickElementbyXpath(webPage, commonData.get("progressiveFormSubmit"), softAssert);
				YesLeaseData = ExcelUtil.readExcelData(DataFilePath, "CreditApp", "verifyYesLeasePageData");
				for (int i = 0; i < 10; i++) {
					CreditAppPage.verifyErrorMessageByXpath(softAssert, YesLeaseData[i][0], YesLeaseData[i][1],
							YesLeaseData[i][2]);
				}
				log.info("testing verify_Yes_Lease_Page_MandatoryFiedlValidation completed------>");
				softAssert.assertAll();
			} catch (Throwable e) {
				mainPage.getScreenShotForFailure(webPage, "verify_Yes_Lease_Page_MandatoryFiedlValidation");
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
				e.printStackTrace();
			}
		}
		else{
			Assert.fail("Declined state could not be generated");
			}
	}

	@Test(priority = 1031, enabled = true, description = "verify_Yes_Lease_Page_Date_Validation")
	public void verify_Yes_Lease_Page_Required_Date_Validation() throws Exception {
		log.info("testing verify_Yes_Lease_Page_Required_Date_Validation started------>");
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
				// Verify for Hire Date with 02-30-2017, next month date,
				// previous date and
				// then today's Date
				CreditAppPage.selectValueWithGivenDate(YesLeaseData[10][1], YesLeaseData[11][1], YesLeaseData[12][1],
						"02-30-2017");
				softAssert.assertEquals(webPage.findObjectByxPath(YesLeaseData[41][1]).getText(), YesLeaseData[0][2],
						"Hire Date with Invalid Date:");
				CreditAppPage.selectValueWithGivenDate(YesLeaseData[10][1], YesLeaseData[11][1], YesLeaseData[12][1],
						futureDate_1month);
				softAssert.assertEquals(webPage.findObjectByxPath(YesLeaseData[41][1]).getText(), YesLeaseData[0][2],
						"Hire Date with futureDate_1month validation:");
				CreditAppPage.selectValueWithGivenDate(YesLeaseData[10][1], YesLeaseData[11][1], YesLeaseData[12][1],
						PastDate_1month);
				softAssert.assertEquals(webPage.findObjectByxPath(YesLeaseData[41][1]).getAttribute("style"),"opacity: 0; display: none;","Hire Date with PastDate_1month validation:");
	
				CreditAppPage.selectValueWithGivenDate(YesLeaseData[10][1], YesLeaseData[11][1], YesLeaseData[12][1],
						yesterdays_Date);
				softAssert.assertEquals(webPage.findObjectByxPath(YesLeaseData[41][1]).getAttribute("style"),"opacity: 0; display: none;","Hire Date with yesterdays_Date validation:");
				
				
				// Verify for Last Pay Date with Future Date, 2 month past date,
				// Vaild-->current Date, 1 month past date (success with till
				// last 35 days)
				CreditAppPage.selectValueWithGivenDate(YesLeaseData[14][1], YesLeaseData[15][1], YesLeaseData[16][1],
						futureDate_1month);
				softAssert.assertEquals(webPage.findObjectByxPath(YesLeaseData[42][1]).getText(), YesLeaseData[2][2],
						"Last Pay Date with Invalid Date:");
				CreditAppPage.selectValueWithGivenDate(YesLeaseData[14][1], YesLeaseData[15][1], YesLeaseData[16][1],
						PastDate_2month);
				softAssert.assertEquals(webPage.findObjectByxPath(YesLeaseData[47][1]).getText(), YesLeaseData[48][1],
						"Last Pay Date with PastDate_2month validation:");
				CreditAppPage.selectValueWithGivenDate(YesLeaseData[14][1], YesLeaseData[15][1], YesLeaseData[16][1],
						yesterdays_Date);
				softAssert.assertEquals(webPage.findObjectByxPath(YesLeaseData[42][1]).getText(),"","Last Pay Date with yesterdays_Date validation:");
			
				CreditAppPage.selectValueWithGivenDate(YesLeaseData[14][1], YesLeaseData[15][1], YesLeaseData[16][1],
						PastDate_1month);
				softAssert.assertEquals(webPage.findObjectByxPath(YesLeaseData[42][1]).getAttribute("style"),"opacity: 0; display: none;","Last Pay Date with Past 1 month Date validation:");
			
				
				// Verify for Next Pay Date with past date, current date till
				// next 90 days
				CreditAppPage.selectValueWithGivenDate(YesLeaseData[17][1], YesLeaseData[18][1], YesLeaseData[19][1],
						PastDate_1month);
				softAssert.assertEquals(webPage.findObjectByxPath(YesLeaseData[43][1]).getText(), YesLeaseData[40][1],
						"Next Pay Date with past 1 month Date:");
				CreditAppPage.selectValueWithGivenDate(YesLeaseData[17][1], YesLeaseData[18][1], YesLeaseData[19][1],
						futureDate_4thmonth);
				softAssert.assertEquals(webPage.findObjectByxPath(YesLeaseData[44][1]).getText(), YesLeaseData[45][1],
						"Next Pay Date with future 4th Month validation:");
				CreditAppPage.selectValueWithGivenDate(YesLeaseData[17][1], YesLeaseData[18][1], YesLeaseData[19][1],
						todays_Date);
				softAssert.assertEquals(webPage.findObjectByxPath(YesLeaseData[43][1]).getAttribute("style"),"opacity: 0; display: none;","Next Pay Date with current Date:");
		
				CreditAppPage.selectValueWithGivenDate(YesLeaseData[17][1], YesLeaseData[18][1], YesLeaseData[19][1],
						futureDate_1month);
				softAssert.assertEquals(webPage.findObjectByxPath(YesLeaseData[44][1]).getAttribute("style"),"opacity: 0; display: none;","Next Pay Date with future 1 month Date:");
			
				// Verify for Account Open Date with Future date -Invalid, current date and past date
				CreditAppPage.selectValueWithGivenDate(YesLeaseData[20][1], YesLeaseData[21][1], YesLeaseData[22][1],
						futureDate_1month);
				softAssert.assertEquals(webPage.findObjectByxPath(YesLeaseData[46][1]).getText(), YesLeaseData[7][2],
						"Account Open Date with 1 month Future Date:");
				CreditAppPage.selectValueWithGivenDate(YesLeaseData[20][1], YesLeaseData[21][1], YesLeaseData[22][1],
						yesterdays_Date);
				softAssert.assertEquals(webPage.findObjectByxPath(YesLeaseData[46][1]).getAttribute("style"),"opacity: 0; display: none;","Account Open Date with yesterday's Date:");
			
				CreditAppPage.selectValueWithGivenDate(YesLeaseData[20][1], YesLeaseData[21][1], YesLeaseData[22][1],
						PastDate_1month);
				softAssert.assertEquals(webPage.findObjectByxPath(YesLeaseData[46][1]).getAttribute("style"),"opacity: 0; display: none;","Account Open Date with Past 1 month Date");
		
				log.info("testing verify_Yes_Lease_Page_Required_Date_Validation completed------>");
				softAssert.assertAll();
			} catch (Throwable e) {
				mainPage.getScreenShotForFailure(webPage, "verify_Yes_Lease_Page_Required_Date_Validation");
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
				e.printStackTrace();
			}
		}else{
			Assert.fail("Declined state could not be generated");
			}
	}
	@Test(priority = 1032, enabled = true, description = "verify_Yes_Lease_Page_Payment_Details_Validation")
	public void verify_Yes_Lease_Page_Payment_Details_Validation() throws Exception {
		log.info("testing verify_Yes_Lease_Page_Payment_Details_Validatio started------>");
		SoftAssert softAssert = new SoftAssert();
		if (declinedStatus == true) {
			try {
				ITafElement CardNumberField = webPage.findObjectByxPath(YesLeaseData[26][1]);
				ITafElement routingNumberField = webPage.findObjectByxPath(YesLeaseData[30][1]);	
				ITafElement accountNumberField = webPage.findObjectByxPath(YesLeaseData[35][1]);
				
				CardNumberField.sendKeys("2345");
				routingNumberField.sendKeys("0210001");
				accountNumberField.sendKeys("123");	
				accountNumberField.sendKeys(Keys.TAB);
				Thread.sleep(4000);
				// Verify for Error Messages
				SoftAssertor.assertEquals(webPage.findObjectByxPath(YesLeaseData[28][1]).getText(),
						YesLeaseData[29][1], "Card Error Message: ");
				SoftAssertor.assertEquals(webPage.findObjectByxPath(YesLeaseData[32][1]).getText(),
						YesLeaseData[33][1], "Bank Routing Error Message: ");	
				SoftAssertor.assertEquals(webPage.findObjectByxPath(YesLeaseData[37][1]).getText(),
								YesLeaseData[38][1], "Account Number Error Message: ");
				CardNumberField.clear();
				routingNumberField.clear();
				accountNumberField.clear();
				
				CardNumberField.sendKeys("412345");
				routingNumberField.sendKeys("021000128");
				accountNumberField.sendKeys("21000128");
				accountNumberField.sendKeys(Keys.TAB);
				Thread.sleep(6000);
				softAssert.assertEquals(webPage.findObjectByxPath(YesLeaseData[28][1]).getAttribute("style"),"opacity: 0; display: none;","CARD Error Message:");
				softAssert.assertEquals(webPage.findObjectByxPath(YesLeaseData[32][1]).getAttribute("style"),"opacity: 0; display: none;","ROUTING Number Error Message:");
				softAssert.assertEquals(webPage.findObjectByxPath(YesLeaseData[37][1]).getAttribute("style"),"opacity: 0; display: none;","Account Number Error Message:");
		
				log.info("testing verify_Yes_Lease_Page_Payment_Details_Validatio completed------>");
				softAssert.assertAll();
			} catch (Throwable e) {
				mainPage.getScreenShotForFailure(webPage, "verify_Yes_Lease_Page_Payment_Details_Validation");
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
				e.printStackTrace();
			}
		}else{
			Assert.fail("Declined state could not be generated");
			}
	}
/*	@Test(priority = 1032, enabled = true, description = "verify_Yes_Lease_Page_Payment_Details_Validation")
	public void verify_Yes_Lease_Page_Payment_Details_Validation() throws Exception {
		log.info("testing verify_Yes_Lease_Page_Payment_Details_Validatio started------>");
		SoftAssert softAssert = new SoftAssert();
		if (declinedStatus == true) {
			try {
				
				// Verify for Bank Routing No: 021000128 only will be accepted.
				ITafElement routingNumberField = webPage.findObjectByxPath(YesLeaseData[30][1]);
				String str2[] = YesLeaseData[31][1].split(", ");
				for (int i = 0; i < str2.length; i++) {
					routingNumberField.sendKeys(str2[i]);
					routingNumberField.sendKeys(Keys.TAB);
					if (i < str2.length - 1) {
						SoftAssertor.assertEquals(webPage.findObjectByxPath(YesLeaseData[32][1]).getText(),
								YesLeaseData[33][1], "Bank Routing Error Message: ");
						routingNumberField.clear();
					} else {
						SoftAssertor.assertEquals(webPage.findObjectByxPath(YesLeaseData[32][1]).getText(),
								YesLeaseData[34][1], "Bank Routing Error Message: ");
					}
				}
				// Verify for Checking Account Number: This mandatory field will
				// only accept numeric values no alphabets and special
				// characters and numeric value count has to be between 4 and
				// 17, not less than 4 or greater than 17
				ITafElement accountNumberField = webPage.findObjectByxPath(YesLeaseData[35][1]);
				String str3[] = YesLeaseData[36][1].split(", ");
				for (int i = 0; i < str3.length; i++) {
					accountNumberField.sendKeys(str3[i]);
					if (i < str3.length - 1) {
						SoftAssertor.assertEquals(webPage.findObjectByxPath(YesLeaseData[37][1]).getText(),
								YesLeaseData[38][1], "Account Number Error Message: ");
						accountNumberField.clear();
					} else {
						// SoftAssertor.assertEquals(webPage.findObjectByxPath(YesLeaseData[32][1]).getText(),
						// YesLeaseData[34][1], "Account Number Error Message: ");
					}
				}
				// Verify for Card number
				ITafElement CardNumberField = webPage.findObjectByxPath(YesLeaseData[26][1]);
				String str[] = YesLeaseData[27][1].split(", ");
				for (int i = 0; i < str.length; i++) {
					CardNumberField.sendKeys(str[i]);
					if (i < str.length - 1) {
						SoftAssertor.assertEquals(webPage.findObjectByxPath(YesLeaseData[28][1]).getText(),
								YesLeaseData[29][1], "Card Error Message: ");
						CardNumberField.clear();
					} else {
					}
				}
				log.info("testing verify_Yes_Lease_Page_Payment_Details_Validatio completed------>");
				softAssert.assertAll();
			} catch (Throwable e) {
				mainPage.getScreenShotForFailure(webPage, "verify_Yes_Lease_Page_Payment_Details_Validation");
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
				e.printStackTrace();
			}
		}
	}*/

	@Test(priority = 1033, enabled = true, description = "verify_Yes_Lease_Page_Submition")
	public void verify_Yes_Lease_Page_Submition() throws Exception {
		log.info("testing verify_Yes_Lease_Page_Submition started------>");
		SoftAssert softAssert = new SoftAssert();
		if (declinedStatus == true) {
			try {
				commonMethods.selectDropdownByValue(webPage, YesLeaseData[13][1], YesLeaseData[39][1]);
				commonMethods.clickElementbyXpath(webPage, YesLeaseData[23][1], softAssert);
				commonMethods.clickElementbyXpath(webPage, YesLeaseData[24][1], softAssert);
				commonMethods.clickElementbyXpath(webPage, commonData.get("progressiveFormSubmit"), softAssert);
				Thread.sleep(8000);
				commonMethods.waitForPageLoad(webPage, softAssert);
				String actualUrl = commonMethods.getPageUrl(webPage, softAssert);
				// State could be declined-001, approved,-002,
				// timeout-003,Invalid Unique ID-004, Wait-005
				if (actualUrl.contains("001") || (actualUrl.contains("002")) || (actualUrl.contains("003"))
						|| (actualUrl.contains("004")) || (actualUrl.contains("005"))) {
					if(actualUrl.contains("004"))
					{}
					else{
					softAssert.assertEquals(webPage.findObjectByxPath(YesLeaseData[49][1]).getText(),YesLeaseData[49][1],"Yes Lease page Header:");	
					}
					} else {
					SoftAssertor.assertFail("Current URL is not as expected,Actual URL: " + actualUrl);
				}
				log.info("testing verify_Yes_Lease_Page_Submition completed------>");
				softAssert.assertAll();
			} catch (Throwable e) {
				mainPage.getScreenShotForFailure(webPage, "verify_Yes_Lease_Page_Submition");
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
				e.printStackTrace();
			}	
	}else{
		Assert.fail("Declined state could not be generated");
		}
}
	@Test(priority = 1034, enabled = true, description = "verify_YesLease_Submit_With_Unique_ID")
	public void verify_YesLease_Submit_With_Unique_ID() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			YesLeaseData = ExcelUtil.readExcelData(DataFilePath, "CreditApp", "verifyYesLeasePageData");
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifyForYesLeaseSubmitWithUniqueID");
				CreditAppPage.navigateToCreditAppPage(softAssert);
			CreditAppPage.fillForm(softAssert, testData);
			CreditAppPage.submitCreditAppAndVerifyStatus(softAssert, "DeclinedPage");
			String url=webPage.getCurrentUrl()+YesLeaseData[51][1];//"?uniqueid=1A7BB00C79CF19F1B58F0004AC1";
		//	webPage.loadPage(url);
			webPage.getDriver().get(url);
		//	http://connsecommdev-1365538477.us-east-1.elb.amazonaws.com/conns_rwd/yes-money-credit/application/success/0007/?uniqueid=1A7BB00C79CF19F1B58F0004AC1
			Calendar cal = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
			Date date = new Date();
			cal.setTime(date);
			String todays_Date = dateFormat.format(cal.getTime());
			System.out.println("Todays Date: " + todays_Date);
			cal.add(Calendar.DATE, -1);
			String yesterdays_Date = dateFormat.format(cal.getTime());
			System.out.println("yesterdays_Date : " + yesterdays_Date);
			
			cal.add(Calendar.MONTH, 1);
			String futureDate_1month = dateFormat.format(cal.getTime());
			System.out.println("futureDate_1month: " + futureDate_1month);
			//Hire Date
			CreditAppPage.selectValueWithGivenDate(YesLeaseData[10][1], YesLeaseData[11][1], YesLeaseData[12][1],
					yesterdays_Date);
			commonMethods.selectDropdownByValue(webPage, YesLeaseData[13][1], YesLeaseData[39][1]);
			//Last Pay Date
			CreditAppPage.selectValueWithGivenDate(YesLeaseData[14][1], YesLeaseData[15][1], YesLeaseData[16][1],
					yesterdays_Date);
			//Next Pay Date
			CreditAppPage.selectValueWithGivenDate(YesLeaseData[17][1], YesLeaseData[18][1], YesLeaseData[19][1],
					futureDate_1month);
			
			
			ITafElement CardNumberField = webPage.findObjectByxPath(YesLeaseData[26][1]);
			ITafElement routingNumberField = webPage.findObjectByxPath(YesLeaseData[30][1]);	
			ITafElement accountNumberField = webPage.findObjectByxPath(YesLeaseData[35][1]);
			CardNumberField.sendKeys("412345");
			routingNumberField.sendKeys("021000128");
			accountNumberField.sendKeys("21000128");
			accountNumberField.sendKeys(Keys.TAB);
			//Account Opened Date
			CreditAppPage.selectValueWithGivenDate(YesLeaseData[20][1], YesLeaseData[21][1], YesLeaseData[22][1],
					yesterdays_Date);
			commonMethods.clickElementbyXpath(webPage, YesLeaseData[23][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, YesLeaseData[24][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, commonData.get("progressiveFormSubmit"), softAssert);
			
			Thread.sleep(10000);
			commonMethods.waitForPageLoad(webPage, softAssert);
			String actualUrl = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertTrue(actualUrl.contains(YesLeaseData[51][2]),"Unique ID Yes Lease page URL:" +actualUrl+" does not conatain: "+YesLeaseData[51][2]);	
			SoftAssertor.assertEquals(webPage.findObjectByxPath(YesLeaseData[52][1]).getText(),YesLeaseData[52][2],"Yes Lease with uniqueID header Validation:");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage,
					"verify_YesLease_Submit_With_Unique_ID");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	}
