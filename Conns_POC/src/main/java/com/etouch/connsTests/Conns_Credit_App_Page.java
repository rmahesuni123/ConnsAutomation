package com.etouch.connsTests;

import java.awt.AWTException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;

import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.etouch.common.BaseTest;
import com.etouch.common.CommonMethods;
import com.etouch.common.TafExecutor;

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
	protected static String DataFilePath;
	protected String testType, browserName;
	String currentTestBedName;
	static Log log = LogUtil.getLog(Conns_Credit_App_Page.class);
	//Logger logger = Logger.getLogger(ConnsAccountAndSignInPage.class.getName());
	private String testEnv;
	protected static String url;
	

	
	private ConnsMainPage mainPage;
	protected static LinkedHashMap<String, String> commonData;
	CommonMethods commonMethods;
	String platform;
	String AbsolutePath = TafExecutor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	boolean declinedStatus = false;
	String[][] YesLeaseData;
	Random random=new Random();
	protected static LinkedHashMap<Long, String> testBedNames = new LinkedHashMap<Long, String>();
	CreditAppPage creditAppPage;
	protected static LinkedHashMap<Long, WebPage> webPageMap = new LinkedHashMap<Long, WebPage>();
	/*** Prepare before class @throws Exception the exception */
	@BeforeClass(alwaysRun = true)
	public void setUp(ITestContext context) throws InterruptedException, FileNotFoundException, IOException {
		try {
			WebPage webPage;
			testBedName = context.getCurrentXmlTest().getAllParameters().get("testBedName");
			testBedNames.put(Thread.currentThread().getId(), testBedName);
			CommonUtil.sop("Test bed Name is " + testBedName);
			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			
			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			commonMethods = new CommonMethods();
			browserName = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getBrowser().getName()
					.toLowerCase();
			if(testType.equalsIgnoreCase("Mobile")
					&&TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getPlatform().getName().equalsIgnoreCase("ANDROID"))
			{
				commonMethods.resetAPP(TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getDevice().getUdid());
			}
			if(testType.equalsIgnoreCase("Mobile")
					&&TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getPlatform().getName().equalsIgnoreCase("iOS"))
			{
				/*log.info("Staring iOS webKit proxy : command : "+"ios_webkit_debug_proxy -c "+
			TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getDevice().getUdid()+":27753");
				Runtime.getRuntime().exec("ios_webkit_debug_proxy -c "+
			TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getDevice().getUdid()+":27753");*/
			}
			
			
			log.info("Test Type is : " + testType);
			//try {
				platform = testBed.getPlatform().getName().toUpperCase();
				System.out.println(" platform : "+platform);
				testEnv = System.getenv().get("Environment");
				log.info("testEnv is : " + System.getenv().get("Environment"));
				path = Paths.get(TestBedManager.INSTANCE.getProfile().getXlsDataConfig().get("testData"));
				DataFilePath = path.toAbsolutePath().toString().replace("Env", testEnv);
				log.info("DataFilePath After is : " + DataFilePath);
				commonData = CommonMethods.getDataInHashMap(DataFilePath, "CreditApp", "CreditAppCommonElements");
				platform = testBed.getPlatform().getName().toUpperCase();
				url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL();
				synchronized (this) {
					creditAppPage = new CreditAppPage();
					Thread.sleep(3000);
					webPage = new WebPage(context);
					webPageMap.put(Thread.currentThread().getId(), webPage);
					mainPage = new ConnsMainPage(url, webPageMap.get(Thread.currentThread().getId()));
					log.info(mainPage);
				}
				if (testType.equalsIgnoreCase("Web")) {
					log.info("Maximize Window in case of Desktop Browsers Only : ");
					webPageMap.get(Thread.currentThread().getId()).getDriver().manage().window().maximize();
				}
				CommonMethods.navigateToPage(webPageMap.get(Thread.currentThread().getId()), url);
			/*} catch (Exception e) {
				log.info("errr is " + e);
				SoftAssertor.addVerificationFailure(e.getMessage());
			}*/
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
	 * Verify Page title
	 * 
	 * @throws Exception
	 * 
	 */
	@Test(priority = 1001, enabled = true, description = "Verify Page Title")
	public void verify_Page_Title_And_Important_Notice() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			//verify Page title
			creditAppPage.navigateToCreditAppPage(softAssert);
			
			//Verify Important Notice
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifyImportantNotesLink");
			//verify Important notice link is present
			if (creditAppPage.verifyElementisPresentByXPath(webPageMap.get(Thread.currentThread().getId()), testData[0][1], softAssert))
			{
				commonMethods.clearElementbyXpath(webPageMap.get(Thread.currentThread().getId()), ".//*[@id='employed:other-income']", softAssert);
				//verify link text
				String linkText = commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[0][1], softAssert);
				softAssert.assertTrue(linkText.contains(testData[0][3]),
						"Failed to verify Important Notice Link Text. Expected "+testData[0][3]+" Actual : "+linkText);
				//Click on link
				commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[1][2], softAssert);
				log.info("Element Clicked - "+testData[1][0].toString());
				
				//Verify text for Important Notice and close popUp
				if(testType.equalsIgnoreCase("web"))
				{
					creditAppPage.verifyContentByXpath(softAssert,testData[3][0], testData[3][1],	testData[3][3]);
					commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[4][1], softAssert);
				}
				else{
					creditAppPage.verifyContentByXpath(softAssert,testData[3][0], testData[3][2],	testData[3][3]);
					commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[4][2], softAssert);
				}
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Page_Title");
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
			// creditAppPage.navigateToCreditAppPage(softAssert);
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "CreditApp", "verifyFont");
			log.info("testing flow verifyFontSizeAndStyle started");
			String pageHeadingClass = null;
			String fontAttribute = null;
			String expectedValue = null;
			int s; 
			if (testType.equalsIgnoreCase("Web")) {
				s = 3;
			}
			else if(TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getDevice().getName().contains("iPad"))
			{
				s=4;
			}
			else {
				s = 2;
			}
			for (int r = 0; r < test.length; r++) {
				pageHeadingClass = test[r][0];
				fontAttribute = test[r][1];
				expectedValue = test[r][s];
				try {
					log.info("Verifying font size and style for element no. " + r);
					ITafElement pageHeading = webPageMap.get(Thread.currentThread().getId()).findObjectByClass(pageHeadingClass);
					String value = pageHeading.getCssValue(fontAttribute).replaceAll("\"", "").replaceAll(" ", "")
							.toLowerCase().trim();
					softAssert.assertTrue(value.contains(expectedValue) || expectedValue.contains(value),
							"Verify Font Size and Style failed.!!!" + "Font Attribute name " + fontAttribute
									+ "Actual : " + value + " and Expected :" + expectedValue.trim());
				} catch (Throwable e) {
					softAssert.fail(e.getLocalizedMessage());
					creditAppPage.navigateToCreditAppPage(softAssert);
				}
			}
			log.info("Ending verify_Font_Size_And_Style");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Font_Size_And_Style");
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
		// creditAppPage.navigateToCreditAppPage(softAssert);
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
				returnValue = webPageMap.get(Thread.currentThread().getId()).findObjectByxPath(Fieldxpath).isDisplayed();
				softAssert.assertTrue(returnValue,
						"Verify Page content failed!!! " + FieldName + "Not rendered on page");
				log.info("testing verifyPageContent Completed------>");
			} catch (Exception e) {
				log.info("Failed to verifying Page Content for element no. " + r);
				softAssert.fail(e.getMessage());
				creditAppPage.navigateToCreditAppPage(softAssert);
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
			creditAppPage.navigateToCreditAppPage(softAssert);
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "CreditApp", "verifyLinksforNewUser");
			String linkName = null;
			String locator = null;
			String ExpectedURL = null;
			int length;
			if(testType.equalsIgnoreCase("mobile"))
			{length = (test.length)-1; }
			else{
				length = test.length;
			}
			for (int r = 0; r < length; r++) {
				linkName = test[r][0];
				locator = test[r][1];
				ExpectedURL = test[r][2];
				try {
					log.info("Verifying Link --->" + linkName);
					creditAppPage.validateLinkRedirection(linkName, locator, ExpectedURL, softAssert);
					log.info("testing verify_Link_Navigation completed------>");
				} catch (Throwable e) {
					softAssert.fail(e.getLocalizedMessage());
					creditAppPage.navigateToCreditAppPage(softAssert);
				}
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Link_Navigation");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1005, enabled = true, description = "verify Important Notice Text")
	public void verify_Important_Notice_Text() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			
			creditAppPage.navigateToCreditAppPage(softAssert);
			LinkedHashMap<String, String> testData = CommonMethods.getDataInHashMap(DataFilePath, "CreditApp",
					"verifyImportanceNoticeField");
			softAssert.assertTrue(commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), testData.get("ImportantNoticeMessage"), softAssert).contains(testData.get("ImportantNoticeMessage")));
			System.out.println();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Important_Notice_Text");
			softAssert.assertAll();
		}
	}
	
	@Test(priority = 1006, enabled = true, description = "Verify Suffix DropDown List")
	  public void verify_Suffix_DropDown_List() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			log.info("testing verifyYearsThereDropDownValues started------>");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "CreditApp", "verifySuffixDropDownValues");
			String[][] SuffixDropDownValues = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"SuffixDropDownValues");
			 creditAppPage.navigateToCreditAppPage(softAssert);
			creditAppPage.verifyDropDownValuesById(softAssert, test[0][0], test[0][1], SuffixDropDownValues);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Suffix_DropDown_List");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	@Test(priority = 1007, enabled = true, description = "verify EmailID Count")
	  public void verify_EmailID_Count() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		//JavascriptExecutor js = (JavascriptExecutor) webPageMap.get(Thread.currentThread().getId()).getDriver();
		creditAppPage.navigateToCreditAppPage(softAssert);
		LinkedHashMap<String, String> inputdata = CommonMethods.getDataInHashMap(DataFilePath, "CreditApp",
				"verifyEmailIdCount");	
	 if (creditAppPage.verifyElementisPresentByXPath(webPageMap.get(Thread.currentThread().getId()), inputdata.get("EmailIdentifier"), softAssert)) {
		log.info("Email ID Count Starts : " );
		/********************************************************************************************************************************/
		webPageMap.get(Thread.currentThread().getId()).getDriver().findElement(By.xpath(inputdata.get("EmailIdentifier"))).sendKeys(inputdata.get("EmailData"));
		WebElement Email_ID_Input = webPageMap.get(Thread.currentThread().getId()).getDriver().findElement(By.xpath(inputdata.get("EmailIdentifier")));
		int EmailId_Length = inputdata.get("EmailData").length();
		log.info("Email ID Count Starts 1: " + EmailId_Length);
		log.info("Email ID Input Text Starts 3: " +Email_ID_Input.getAttribute("value"));	
		log.info("Email ID Input Text Count Starts 4: " +Email_ID_Input.getAttribute("value").length());		
		if (Email_ID_Input.getAttribute("value").length() > 50) {
			log.info("Email ID Count Asserts Fail : " + EmailId_Length);	
				Assert.fail("Input Email Character is greater than 50. Email ID Character Count Fail : "
						+ inputdata.get("EmailData").length());
			log.info("Email ID Count Starts 2: " + EmailId_Length);	
		} 
	 }log.info("testing verify_EmailID_Count completed------>");
		/*********************************************************************************************************************/
	}
	
	@Test(priority = 1008, enabled = true, description = "verify form is rendered with blank fields")
	public void verify_Form_Is_Displayed_With_Blank_Field() throws Exception {
		log.info("testing verifyLinkNavigation started------>");
		SoftAssert softAssert = new SoftAssert();
		try {
			creditAppPage.navigateToCreditAppPage(softAssert);
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "CreditApp", "verifyFormIsRenderedWithBlankFields");
			creditAppPage.verifyTextFieldValue(test, softAssert);
			log.info("testing verify_Form_Is_Displayed_With_Blank_Field completed------>");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Form_Is_Displayed_With_Blank_Field");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1009, enabled = true, description = "verify Mandatory Field Validation WithoutData")
	public void verify_Mandatory_Field_Validation_Without_Data() throws Exception {
		log.info("testing verifyMandatoryFieldValidationWithoutData started------>");
		SoftAssert softAssert = new SoftAssert();
		try {
			 creditAppPage.navigateToCreditAppPage(softAssert);
			 creditAppPage.scrollToElement(commonData.get("SubmitButton"), softAssert);
			 webPageMap.get(Thread.currentThread().getId()).getDriver().findElement(By.xpath(commonData.get("SubmitButton"))).click();
			 
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifyMandatoryFieldValidationWithoutData");
			for (int i = 0; i < test.length; i++) {
				creditAppPage.verifyErrorMessageByXpath(softAssert, test[i][0], test[i][1], test[i][2]);
			}
			log.info("testing verify_Mandatory_Field_Validation_Without_Data completed------>");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Mandatory_Field_Validation_Without_Data");
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
	@Test(priority = 1010, enabled = true, description = "verify Error Msg With Blank Data")
	public void verify_Field_Validation_Error_Message_With_InValid_Data() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			creditAppPage.navigateToCreditAppPage(softAssert);
			log.info("testing verifyFieldValidationErrorMessageWithInValidData started------>");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifyFieldValidationErrorMessageWithInValidData");
			if (browserName.contains("iphone") || browserName.contains("ipad") || browserName.contains("safari")) {
				creditAppPage.verifyErrorMessageForIos(softAssert, test);
			} else {
				for (int r = 0; r < test.length; r++) {
					creditAppPage.verifyErrorMessageWithInvalidDataById(softAssert, test[r][0], test[r][1], test[r][2],
							test[r][3], test[r][4]);
				}
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Field_Validation_Error_Message_With_InValid_Data");
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
	@Test(priority = 1011, enabled = true, description = "Verify City and State Field Auto Populates")
	public void verify_City_State_Fields_Auto_Populates() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			log.info("testing verifyFieldValidationErrorMessageWithInValidData started------>");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "CreditApp", "verifyFieldAutoPopulates");
			// creditAppPage.navigateToCreditAppPage(softAssert);
			commonMethods.clearTextBoxById(webPageMap.get(Thread.currentThread().getId()), test[0][0], softAssert);
			commonMethods.sendKeysById(webPageMap.get(Thread.currentThread().getId()), test[0][0], test[0][1], softAssert);
			commonMethods.clickElementById(webPageMap.get(Thread.currentThread().getId()), test[0][2], softAssert);
			softAssert.assertEquals(test[0][3], commonMethods.getTextbyId(webPageMap.get(Thread.currentThread().getId()), test[0][2], softAssert));
			softAssert.assertEquals(test[0][5],
					creditAppPage.getSelectedValueFromDropDownID(softAssert, test[0][0], test[0][4]));
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Field_Validation_Error_Message_With_InValid_Data");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case 009 - Verify Verify Years There Drop Down Values
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1012, enabled = true, description = "verify Years There Drop Down Values")
	public void verify_Years_There_DropDown_Values() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			log.info("testing verifyYearsThereDropDownValues started------>");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "CreditApp", "verifyYearsThereDropDownValues");
			String[][] yearsThereDropDownValues = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"yearsThereDropDownValues");
			// creditAppPage.navigateToCreditAppPage(softAssert);
			creditAppPage.verifyDropDownValuesById(softAssert, test[0][0], test[0][1], yearsThereDropDownValues);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Years_There_DropDown_Values");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case 010 - verify City And State Fields Are Editable
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1013, enabled = true, description = "verify City And State Fields Are Editable")
	public void verify_City_And_State_Field_Are_Editable() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			log.info("testing verifyFieldValidationErrorMessageWithInValidData started------>");
			LinkedHashMap<String, String> testData = CommonMethods.getDataInHashMap(DataFilePath, "CreditApp",
					"verifyCityAndStateFieldAreEditable");
			// creditAppPage.navigateToCreditAppPage(softAssert);
			commonMethods.clearTextBoxById(webPageMap.get(Thread.currentThread().getId()), testData.get("ZipcodeID"), softAssert);
			commonMethods.sendKeysById(webPageMap.get(Thread.currentThread().getId()), testData.get("ZipcodeID"), testData.get("ZipcodeValue"), softAssert);
			commonMethods.clickElementById(webPageMap.get(Thread.currentThread().getId()), testData.get("CityID"), softAssert);
			creditAppPage.verifyTextFieldIsEditableByID(softAssert, "City", testData.get("CityID"),
					testData.get("CityValue"));
			creditAppPage.verifyDropDownFieldIsEditableById(softAssert, "State", testData.get("StateID"),
					testData.get("StateValue"));
			log.info("testing verify_City_And_State_Field_Are_Editable completed------>");
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_City_And_State_Field_Are_Editable");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case 011 - verify Main Source Of Income Field
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1014, enabled = true, description = "verify Main Source Of Income Field")
	public void verify_Main_Source_Of_Income_Field() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			 creditAppPage.navigateToCreditAppPage(softAssert);
			log.info("testing verifyFieldValidationErrorMessageWithInValidData started------>");
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp", "verifyMainSourceOfIncomeField");
			for (int i = 0; i < testData.length; i++) {
				creditAppPage.verifyDropDownFieldIsEditableByXpath(softAssert, testData[0][0], testData[0][1],
						testData[i][2]);
			}
			for (int j = 0; j < testData.length; j++) {
				creditAppPage.selectValueFromDropDownByXpath(softAssert, testData[0][0], testData[0][1],
						testData[j][2]);
				String[][] MonthlyIncomeTestData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
						testData[j][2] + "Data");
				creditAppPage.fillForm(softAssert, MonthlyIncomeTestData);
				// creditAppPage.sendTextToTextFieldsById(softAssert,
				// MonthlyIncomeTestData);
				// creditAppPage.navigateToCreditAppPage(softAssert);
				Thread.sleep(3000);
				
			}
			softAssert.assertAll();
			log.info("testing verify_Main_Source_Of_Income_Field completed------>");
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Main_Source_Of_Income_Field");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1015, enabled = true, description = "verify Mandatory Field Validation Without Data Main Source of Income Fields")
	public void verify_Mandatory_Field_Validation_Without_Data_Main_Source_of_Income_Fields() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			creditAppPage.navigateToCreditAppPage(softAssert);
			log.info(
					"testing verify_Mandatory_Field_Validation_Without_Data_Main_Source_of_Income_Fields started------>");
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifyMainSourceOfIncomeFieldErrorMessage");
			for (int j = 0; j < testData.length; j++) {
				creditAppPage.selectValueFromDropDownByXpath(softAssert, testData[0][0], testData[0][1],
						testData[j][2]);
				String[][] MonthlyIncomeErrorMessage = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
						testData[j][2] + "DataSet");
				creditAppPage.scrollToElement(commonData.get("SubmitButton"),softAssert);
				commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("SubmitButton"), softAssert);
				for (int k = 0; k < MonthlyIncomeErrorMessage.length; k++) {
					creditAppPage.verifyErrorMessageByXpath(softAssert, MonthlyIncomeErrorMessage[k][0],
							MonthlyIncomeErrorMessage[k][1], MonthlyIncomeErrorMessage[k][2]);
				}
				// creditAppPage.navigateToCreditAppPage(softAssert);
			}
			log.info(
					"testing verify_Mandatory_Field_Validation_Without_Data_Main_Source_of_Income_Fields completed------>");
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Main_Source_Of_Income_Field");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1016, enabled = true, description = "verify Error Message for Reference Code Field")
	public void verify_Error_Message_for_Reference_Code_Field() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			creditAppPage.navigateToCreditAppPage(softAssert);
			log.info("testing verify_Error_Message_for_Reference_Code_Field started------>");
			LinkedHashMap<String, String> testData = CommonMethods.getDataInHashMap(DataFilePath, "CreditApp",
					"verifyErrorMessageForRefCode");
			commonMethods.sendKeysbyXpath(webPageMap.get(Thread.currentThread().getId()), testData.get("RefCodeXpath"), testData.get("RefCodeValue"),
					softAssert);
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), testData.get("RefCodeSubmitXpath"), softAssert);
			CommonMethods.waitForWebElement(By.xpath(testData.get("RefCodeErrorXpath")), webPageMap.get(Thread.currentThread().getId()));
			Thread.sleep(3000);
			creditAppPage.verifyErrorMessageByXpath(softAssert, "RefCodeError", testData.get("RefCodeErrorXpath"),
					testData.get("RefCodeError"));
			log.info("testing verify_Error_Message_for_Reference_Code_Field completed------>");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Error_Message_for_Reference_Code_Field");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1017, enabled = true, description = "verify Reference Code With Valid Required Field Data")
	public void verify_Reference_Code_With_Valid_Required_Field_Data() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			creditAppPage.navigateToCreditAppPage(softAssert);
			log.info("testing verify_Reference_Code_With_Valid_Required_Field_Data started------>");
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifyMessageForRefCodeWithValidData");
			creditAppPage.fillForm(softAssert, testData);
			// Data is used from above testcase
			// verify_Error_Message_for_Reference_Code_Field
			LinkedHashMap<String, String> verifyErrorMessageForRefCodeData = CommonMethods
					.getDataInHashMap(DataFilePath, "CreditApp", "verifyErrorMessageForRefCode");
			Thread.sleep(5000);
			if(testType.equalsIgnoreCase("mobile"))
			{
				creditAppPage.verifyErrorMessageByXpath(softAssert, "RefCodeError",
						verifyErrorMessageForRefCodeData.get("RefCodeErrorXpath"),
						verifyErrorMessageForRefCodeData.get("RefCodeError"));
				log.info("Reference not accepted as valid code. As test is running on Mobile device");
			}
			else{
				creditAppPage.verifyErrorMessageByXpath(softAssert, "RefCodeError",
						verifyErrorMessageForRefCodeData.get("RefCodeErrorXpath"),
						verifyErrorMessageForRefCodeData.get("RefCodeAccpetMessage"));
			}
			
			log.info("testing verify_Reference_Code_With_Valid_Required_Field_Data completed------>");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Reference_Code_With_Valid_Required_Field_Data");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	@Test(priority = 1018, enabled = true, description = "verify Power Review In Yes Money Landing Page")
	public void verify_Power_Review_In_Yes_Money_Landing_Page() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			 creditAppPage.navigateToYesMoneyLandingPage(softAssert);
			//String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp","verifySuccessfulSubmitForRegisteredUser");
			Thread.sleep(5000);
			webPageMap.get(Thread.currentThread().getId()).findObjectByxPath(".//*[@id='PWRSummaryContainer']//div[@class='pr-rating-stars']").click();
			Thread.sleep(5000);
			webPageMap.get(Thread.currentThread().getId()).findObjectByxPath(".//*[@class='pr-snippet-write-review-link']").click();
			Thread.sleep(5000);
			commonMethods.waitForPageLoad(webPageMap.get(Thread.currentThread().getId()), softAssert);
			creditAppPage.submitReview(webPageMap.get(Thread.currentThread().getId()),softAssert, ExcelUtil.readExcelData(DataFilePath, "CreditApp","submitReviewYesMoneyLanding"), false);
			Thread.sleep(5000);
			
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verifyPowerReviewInYesMoneyLandingPage");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1019, enabled = true, description = "verify Successful Submit For Registered User")
	public void verify_Successful_Submit_Status_Approved_With_DP() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			// creditAppPage.navigateToCreditAppPage(softAssert);
			// creditAppPage.loginFromCreditApp(softAssert);
			creditAppPage.navigateToCreditAppPage(softAssert);
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifySuccessfulStatusApprovedWithDP");
			creditAppPage.fillForm(softAssert, testData);
			// creditAppPage.verifyFieldValues(testData, softAssert);
			creditAppPage.submitCreditAppAndVerifyStatus(softAssert, "ApprovedWithDownPaymentPage");
			webPageMap.get(Thread.currentThread().getId()).findObjectByxPath(".//*[@id='button-write-review']").click();
			Thread.sleep(5000);
			commonMethods.waitForPageLoad(webPageMap.get(Thread.currentThread().getId()), softAssert);
			
			webPageMap.get(Thread.currentThread().getId()).switchWindow("Conns");
			creditAppPage.submitReview(webPageMap.get(Thread.currentThread().getId()),softAssert, ExcelUtil.readExcelData(DataFilePath, "CreditApp","submitReviewYesMoneyLanding"), false);
			//webPageMap.get(Thread.currentThread().getId()).getDriver().close();
			webPageMap.get(Thread.currentThread().getId()).closeToggleWindow();
			softAssert.assertAll();
		
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Successful_Submit_Status_Approved_With_DP");
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
	@Test(priority = 1020, enabled = true, description = "verify Error Msg With Blank Data")
	public void verify_Valid_User_Successful_Submit_For_New_User_Status_Approved_Without_DP() throws Exception {
		log.info("testing verifyValidUserSuccessfulSubmitForNewUser started------>");
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifyValidUserSuccessfulSubmitForNewUser");
			creditAppPage.navigateToCreditAppPage(softAssert);
			creditAppPage.fillForm(softAssert, testData);
			creditAppPage.submitCreditAppAndVerifyStatus(softAssert, "ApprovedWithoutDownPaymentPage");
			webPageMap.get(Thread.currentThread().getId()).findObjectByxPath(".//*[@id='button-write-review']").click();
			Thread.sleep(5000);
			commonMethods.waitForPageLoad(webPageMap.get(Thread.currentThread().getId()), softAssert);
			
			webPageMap.get(Thread.currentThread().getId()).switchWindow("Conns");
			creditAppPage.submitReview(webPageMap.get(Thread.currentThread().getId()),softAssert, ExcelUtil.readExcelData(DataFilePath, "CreditApp","submitReviewYesMoneyLanding"), false);
			webPageMap.get(Thread.currentThread().getId()).closeToggleWindow();
			// Thread.sleep(10000);
			softAssert.assertAll();
			log.info(
					"testing flow verify_Valid_User_Successful_Submit_For_New_User_Status_Approved_Without_DP Completed");
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()),
					"verify_Valid_User_Successful_Submit_For_New_User_Status_Approved_Without_DP");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1021, enabled = true, description = "verify Successful Submit For Registered User")
	public void verify_Credit_App_Submit_With_Main_Source_Of_Income_As_Employed_Status_Wait() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifyCreditAppSubmitWithMainSourceOfIncomeAsEmployed");
			creditAppPage.navigateToCreditAppPage(softAssert);
			creditAppPage.fillForm(softAssert, testData);
			creditAppPage.submitCreditAppAndVerifyStatus(softAssert, "WaitPage");
			
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()),
					"verify_Credit_App_Submit_With_Main_Source_Of_Income_As_Employed_Status_Wait");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1022, enabled = true, description = "verify Successful Submit With Main Source Of Income As Social Security Statu Out Of State")
	public void verify_Credit_App_Submit_With_Main_Source_Of_Income_As_Social_Security_Status_Out_Of_State()
			throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifyCreditAppSubmitWithMainSourceOfIncomeAsSocialSecurity");
			creditAppPage.navigateToCreditAppPage(softAssert);
			creditAppPage.fillForm(softAssert, testData);
			creditAppPage.submitCreditAppAndVerifyStatus(softAssert, "OutOfStatePage");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()),
					"verify_Credit_App_Submit_With_Main_Source_Of_Income_As_Social_Security_Status_Out_Of_State");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1023, enabled = true, description = "verify Successful Submit With RefField")
	public void verify_Credit_App_Submit_With_RefField() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifyCreditAppSubmitWithRefField");
			creditAppPage.navigateToCreditAppPage(softAssert);
			creditAppPage.fillForm(softAssert, testData);
			if(testType.equalsIgnoreCase("mobile"))
			{
				//commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("SubmitButton"), softAssert);
				//commonMethods.waitForPageLoad(webPageMap.get(Thread.currentThread().getId()), softAssert);
				LinkedHashMap<String, String> verifyErrorMessageForRefCodeData = CommonMethods
						.getDataInHashMap(DataFilePath, "CreditApp", "verifyErrorMessageForRefCode");
				creditAppPage.verifyErrorMessageByXpath(softAssert, "RefCodeError",
						verifyErrorMessageForRefCodeData.get("RefCodeErrorXpath"),
						verifyErrorMessageForRefCodeData.get("RefCodeError"));
				log.info("Reference not accepted as valid code. As test is running on Mobile device");
			}
			creditAppPage.submitCreditApp(softAssert);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Credit_App_Submit_With_RefField");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1024, enabled = true, description = "verify Successful Submit For With Main source of Income as Disability Income")
	public void verify_Credit_App_Submit_With_Main_Source_Of_Income_As_Disability_Income() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifyCreditAppSubmitWithMainSourceOfIncomeAsDisabilityIncome");
			creditAppPage.navigateToCreditAppPage(softAssert);
			creditAppPage.fillForm(softAssert, testData);
			creditAppPage.submitCreditApp(softAssert);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()),
					"verify_Credit_App_Submit_With_Main_Source_Of_Income_As_Disability_Income");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1025, enabled = true, description = "verify Successful Submit With Main Source Of Income As Spous And Partner")
	public void verify_Credit_App_Submit_With_Main_Source_Of_Income_As_Spous_And_Partner() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifyCreditAppSubmitWithMainSourceOfIncomeAsSpousAndPartner");
			creditAppPage.navigateToCreditAppPage(softAssert);
			creditAppPage.fillForm(softAssert, testData);
			creditAppPage.submitCreditApp(softAssert);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()),
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
	@Test(priority = 1026, enabled = true, description = "verify Fields Are AutoPopulated For Registered User")
	public void verify_Fields_Are_AutoPopulated_For_Registered_User() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			creditAppPage.navigateToCreditAppPage(softAssert);
			creditAppPage.loginFromCreditApp(softAssert);
			creditAppPage.navigateToCreditAppPage(softAssert);
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifyFieldsAutoPopulatedForRegisteredUser");
			creditAppPage.verifyFieldValues(testData, softAssert);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Fields_Are_AutoPopulated_For_Registered_User");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1027, dependsOnMethods = "verify_Fields_Are_AutoPopulated_For_Registered_User", enabled = true, description = "verify First Name And Last Name Field Are Editable")
	public void verify_First_Name_And_Last_Name_Field_Are_Editable() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			// creditAppPage.navigateToCreditAppPage(softAssert);
			LinkedHashMap<String, String> testData = CommonMethods.getDataInHashMap(DataFilePath, "CreditApp",
					"verifyFirstNameAndLastNameFieldAreEditable");
			if (!creditAppPage.verifyTextFieldIsEditableByXpath(softAssert, "FirstName",
					testData.get("FirstNameIdentifier"), testData.get("FirstNameData")))
				softAssert.fail("TextBox FirstName is Not editable. Unable to set new value as : "
						+ testData.get("FirstNameData"));
			if (!creditAppPage.verifyTextFieldIsEditableByXpath(softAssert, "LastName",
					testData.get("LastNameIdentifier"), testData.get("LastNameData")))
				softAssert.fail("TextBox FirstName is Not editable. Unable to set new value as : "
						+ testData.get("FirstNameData"));
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_First_Name_And_Last_Name_Field_Are_Editable");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1028, dependsOnMethods = "verify_First_Name_And_Last_Name_Field_Are_Editable", enabled = true, description = "verify Email Is Not Editable For Registered User")
	public void verify_Email_Is_Not_Editable() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			// creditAppPage.navigateToCreditAppPage(softAssert);
			LinkedHashMap<String, String> testData = CommonMethods.getDataInHashMap(DataFilePath, "CreditApp",
					"verifyEmailIsNotEditable");
		
			if (!creditAppPage.verifyElementisPresentByXPath(webPageMap.get(Thread.currentThread().getId()), testData.get("EmailIdentifier"), softAssert)) {
				log.info("TextBox EmailAddress is Not Displayed");
				softAssert.fail("TextBox EmailAddress is Not Displayed");
			} 
	
			
				else {
				commonMethods.sendKeysbyXpath(webPageMap.get(Thread.currentThread().getId()), testData.get("EmailIdentifier"), testData.get("EmailData"),
						softAssert);
				String value = creditAppPage.getTextBoxValueByJs("EmailAdress", testData.get("EmailIdentifier"),
						softAssert);
			log.info("textField Value is : " + value);
				log.info("Updated value is : " + testData.get("EmailData"));
				if (value.equals(testData.get("EmailData"))) {
					softAssert.fail("Email Field is editable for Registered user , Able to set new value to " + value);
				}
			//}
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Email_Is_Not_Editable");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1029, dependsOnMethods = "verify_Email_Is_Not_Editable", enabled = true, description = "verify Sign In Link Is Not Displayed For Registered User")
	public void verify_Sign_In_Link_Is_Not_Displayed_For_Registered_User() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			// creditAppPage.navigateToCreditAppPage(softAssert);
			if (creditAppPage.verifyElementisPresentByXPath(webPageMap.get(Thread.currentThread().getId()), commonData.get("SignInNowLink"), softAssert))
				softAssert.fail("Sign In Now Link is Displayed For Registered User");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Sign_In_Link_Is_Not_Displayed_For_Registered_User");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 1030, dependsOnMethods = "verify_Sign_In_Link_Is_Not_Displayed_For_Registered_User", enabled = true, description = "verify Registered User Is Able To Fill Mandatory Fields")
	public void verify_Registered_User_Is_Able_To_Fill_Mandatory_Fields() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifyRegisteredUserIsAbleToFillMandatoryFields");
			// creditAppPage.navigateToCreditAppPage(softAssert);
			creditAppPage.fillForm(softAssert, testData);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Registered_User_Is_Able_To_Fill_Mandatory_Fields");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * logins from creditApp page and verifies if data is auto populated
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1031, enabled = true, description = "verify Successful Submit For Registered User Status Duplicate")
	public void verify_Successful_Submit_For_Registered_User_Status_Duplicate() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			 creditAppPage.navigateToCreditAppPage(softAssert);
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"verifySuccessfulSubmitForRegisteredUser");
			creditAppPage.fillForm(softAssert, testData);
			creditAppPage.submitCreditAppAndVerifyStatus(softAssert, "DuplicatePage");
			//Click on write a review
			webPageMap.get(Thread.currentThread().getId()).findObjectByxPath(".//*[@id='button-write-review']").click();
			 commonMethods.waitForPageLoad(webPageMap.get(Thread.currentThread().getId()), softAssert);
			//switch to review window
			webPageMap.get(Thread.currentThread().getId()).switchWindow("Conns");
			creditAppPage.submitReview(webPageMap.get(Thread.currentThread().getId()),softAssert, ExcelUtil.readExcelData(DataFilePath, "CreditApp","submitReviewYesMoneyLanding"), true);
			webPageMap.get(Thread.currentThread().getId()).closeToggleWindow();
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Successful_Submit_For_Registered_User_Status_Duplicate");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage()); 
		}
	}
	

	
	@Test(priority = 1032, enabled = true, description = "verify Error Msg With Phone Data")
	public void verify_Phone_Field_Validation_Error_Message_With_InValid_Data() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			creditAppPage.navigateToCreditAppPage(softAssert);
			log.info("testing verifyFieldValidationErrorMessageWithInValidData started------>");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "CreditApp","verifyPhoneNumberValidationwithInvalidData");
		//	if(testType.equalsIgnoreCase("Mobile")
			//
			if (browserName.contains("iphone") || browserName.contains("ipad") || browserName.contains("safari") ) {
				
				creditAppPage.verifyErrorMessageForSafari(softAssert, test);
			} else {
					for (int r = 0; r < test.length; r++) {
						if(!(r >= 6)){
							log.info(" Iteration #  "+ r +"*****  Input TELEPHONE NUMBER :  " +test[r][2]); 
						creditAppPage.verifyErrorMessageWithInvalidDataById(softAssert, test[r][0], test[r][1], test[r][2],
								test[r][3], test[r][4]);
				   }
					else {	
						log.info(" Iteration #  "+ r +"***** Inside Else Part, Please Enter an Invalid TELEPHONE NUMBER :  " +test[r][2]); 
					}
						softAssert.assertAll();
			}
		}
		}
				catch (Throwable e) {
					e.printStackTrace();
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Field_Validation_Error_Message_With_InValid_Data");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}


	
	
	

}
