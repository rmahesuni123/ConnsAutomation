package com.etouch.connsTests;

import java.awt.AWTException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.LinkedHashMap;

import java.util.Random;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.etouch.common.BaseTest;
import com.etouch.common.CommonMethods;
import com.etouch.common.TafExecutor;
import com.etouch.connsPages.BillPayPage;

import com.etouch.connsPages.ConnsMainPage;

import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.datamanager.excel.annotations.IExcelDataFiles;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;

import com.etouch.taf.webui.selenium.WebPage;


public class Conns_BillPay_Page_SetUp_AutoPay extends BaseTest {
	private String testBedName;
	TestBed testBed;
	Path path;
	protected static String DataFilePath;
	protected String testType, browserName;
	String currentTestBedName;
	static Log log = LogUtil.getLog(Conns_BillPay_Page_SetUp_AutoPay.class);
	Logger logger = Logger.getLogger(Conns_BillPay_Page_SetUp_AutoPay.class.getName());
	private String testEnv;
	protected static String url;
	WebPage webPage;
	private ConnsMainPage mainPage;
	protected static LinkedHashMap<String, String> commonData;
	CommonMethods commonMethods;
	String platform;
	String AbsolutePath = TafExecutor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	boolean declinedStatus = false;
	String[][] YesLeaseData;
	Random random = new Random();
	protected static LinkedHashMap<Long, String> testBedNames = new LinkedHashMap<Long, String>();
	BillPayPage billPayPage;
	protected static LinkedHashMap<Long, WebPage> webPageMap = new LinkedHashMap<Long, WebPage>();
	JavascriptExecutor js;

	/*** Prepare before class @throws Exception the exception */
	@BeforeClass(alwaysRun = true)
	public void setUp(ITestContext context) throws InterruptedException, FileNotFoundException, IOException {
		try {

			testBedName = context.getCurrentXmlTest().getAllParameters().get("testBedName");
			testBedNames.put(Thread.currentThread().getId(), testBedName);
			CommonUtil.sop("Test bed Name is " + testBedName);
			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);

			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			commonMethods = new CommonMethods();
			browserName = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getBrowser().getName()
					.toLowerCase();
			if (testType.equalsIgnoreCase("Mobile") && TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName)
					.getPlatform().getName().equalsIgnoreCase("ANDROID")) {
				commonMethods
						.resetAPP(TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getDevice().getUdid());
			}
			if (testType.equalsIgnoreCase("Mobile") && TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName)
					.getPlatform().getName().equalsIgnoreCase("iOS")) {
				/*
				 * log.info("Staring iOS webKit proxy : command : "+"ios_webkit_debug_proxy -c "
				 * + TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getDevice().
				 * getUdid()+":27753"); Runtime.getRuntime().exec("ios_webkit_debug_proxy -c "+
				 * TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getDevice().
				 * getUdid()+":27753");
				 */
			}

			log.info("Test Type is : " + testType);
			// try {
			platform = testBed.getPlatform().getName().toUpperCase();
			log.info(" platform : " + platform);
			testEnv = System.getenv().get("Environment");
			log.info("testEnv is : " + System.getenv().get("Environment"));
			path = Paths.get(TestBedManager.INSTANCE.getProfile().getXlsDataConfig().get("testData"));
			DataFilePath = path.toAbsolutePath().toString().replace("Env", testEnv);
			log.info("DataFilePath After is : " + DataFilePath);
			commonData = CommonMethods.getDataInHashMap(DataFilePath, "BillPayPage", "BillPageCommonElements");

			platform = testBed.getPlatform().getName().toUpperCase();
			url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL();
			synchronized (this) {
				billPayPage = new BillPayPage();
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
			js = (JavascriptExecutor) webPageMap.get(Thread.currentThread().getId()).getDriver();
		} catch (Exception e) {
			CommonUtil.sop("errr is for" + testBedName + " -----------" + e);
			SoftAssertor.addVerificationFailure(e.getMessage());
		}
	}

	@AfterClass
	public void releaseResources() throws IOException, AWTException {
	}

	/**
	 * TC_049 to TC_053
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1001, enabled = true, description = "verify_SetUpAutoPayFunctionality")
	public void verify_SetUpAutoPayFunctionality() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		log.info("***** verify_SetUpAutoPayFunctionality Scenarios Starts ****");
		String ExpectedPageTitle = "";
		String ExpectedURL = "";
		String ElementXpath = "";
		String CancelButtonFundingPortal = "";
		String CancelButtonErrorMessageXpath = "";
		String ExpectedCancelButtonErrorMessage = "";
		String ActualCancelButtonErrorMessage = "";
		String DropDownXpath = "";
		String invalidDropDownValue = "";
		String validDropDownValue = "";
		String signInLinkXPath = "";
		String selectAlternatePaymentDateRadioButtonXPATH = "";
		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "BillPayPage", "BillPageCommonElements");
		String[][] inputData = ExcelUtil.readExcelData(DataFilePath, "BillPayPage", "AccountSetupAutoPayPage");
		String[][] feedData = ExcelUtil.readExcelData(DataFilePath, "BillPayPage", "SchedulePaymentContentValidation");
		String[][] dataInput = ExcelUtil.readExcelData(DataFilePath, "BillPayPage", "SelectAmountPaymentScreen");
		String[][] SelectPaymentDateDropDownData = ExcelUtil.readExcelData(DataFilePath, "BillPayPage",	"SelectPaymentDateErrorMessage");

		ElementXpath = feedData[38][1];
		ExpectedPageTitle = testData[66][1];
		ExpectedURL = testData[67][1];
		CancelButtonFundingPortal = feedData[40][1];
		CancelButtonErrorMessageXpath = feedData[41][1];
		ExpectedCancelButtonErrorMessage = feedData[42][1];
		selectAlternatePaymentDateRadioButtonXPATH = SelectPaymentDateDropDownData[0][1];
		DropDownXpath = SelectPaymentDateDropDownData[1][1];
		invalidDropDownValue = SelectPaymentDateDropDownData[2][1];
		validDropDownValue = SelectPaymentDateDropDownData[5][1];
		signInLinkXPath = commonData.get("signInLinkXPath");
		try {
		
			log.info("**** Click on Sign In link ****");
			BillPayPage.ClickElementPresenceByJS(webPage, signInLinkXPath, softAssert);
		
			log.info("**** Click on Pay your Bill header link ****");
			billPayPage.connsLogin(inputData[0][1], inputData[1][1], webPage, softAssert);
			
			log.info("**** Click-on Credit Summary Link ****");
			commonMethods.clickElementbyLinkText(webPage, commonData.get("creditSummaryLinkText"), softAssert);
			
			log.info("**** SetUp AutoPayButtonHandling Starts ****");
			BillPayPage.AutoPayButtonHandling(webPage, ElementXpath, dataInput, softAssert);
			
			log.info("**** Setup Auto Pay Default Radio Button isSelected Validation ****");
			log.info("**** Identify Default Radio Button Validation :");
			BillPayPage.RadioButtonValidation(webPage, feedData, softAssert);
			
			log.info("**** AddNewPayMethod Functionality *****");
			BillPayPage.ClickElementPresenceByJS(webPage, ElementXpath, softAssert);
			
			log.info("**** Credit Card Funding Portal Page Title URL Validation  *********");
			BillPayPage.PageTitle_PageURL_Validation(webPage, testData, ExpectedURL, ExpectedPageTitle);
			
			log.info("**** Cancel Button Validation & Click on Credit Card Funding Portal ****");
			BillPayPage.ClickElementPresenceByJS(webPage, CancelButtonFundingPortal, softAssert);
			
			log.info("**** Cancel Button Error Message Validation : Unable to add New Payment Method *******");
			BillPayPage.ErroMessageValidation(webPage, ActualCancelButtonErrorMessage, CancelButtonErrorMessageXpath,ExpectedCancelButtonErrorMessage, softAssert);
			
			log.info("**** Select Minimum Payment Pay Radio button selection **** ");
			BillPayPage.isDefaultRadioButtonSelected(webPage,softAssert);
			BillPayPage.ClickElementPresenceByJS(webPage, selectAlternatePaymentDateRadioButtonXPATH, softAssert);
			
			log.info("**** SelectPaymentDate_DropDown_Menu_Fill_Up with Invalid Input & Assertion Message****"); 			
			BillPayPage.selectDropdownByValue(webPage, DropDownXpath, invalidDropDownValue, softAssert);
			BillPayPage.DropDownValueErrorMessageAssertion(webPage, SelectPaymentDateDropDownData, testData, softAssert);
			
			log.info("**** SelectPaymentDate_DropDown_Menu_Fill_Up with Valid Input ");
			BillPayPage.selectDropdownByValue(webPage, DropDownXpath, validDropDownValue, softAssert);
			
			softAssert.assertAll();
			log.info("***** verify_SetUpAutoPayFunctionality Scenarios Completed Successfully ****");

		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()),
					"verify_SetUpAutoPayFunctionality");
			softAssert.assertAll();
		}
	}

	/**
	 * TC_054 to TC_061
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1002, enabled = true, description = "verify_SetUp_AutoPay_OtherAmountErrorMessage_DisclaimerText")
	public void verify_SetUp_AutoPay_OtherAmountErrorMessage_DisclaimerText() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		log.info("***** verify_SetUp_AutoPay_OtherAmountErrorMessage_DisclaimerText Scenarios Starts ****");
		String disclaimersText = "";
		String disclaimersRemoveText = "";
		String currentBalance = "";
		String mychar = "";
		String minAmountDue = "";
		String minAmountchar = "";
		String numberAsString = "";
		String AutoPayCurrentBalanceXPath = commonData.get("AutoPayCurrentBalance");
		String SelectOtherAmountToPayRadioButtonXPath = commonData.get("SelectOtherAmountToPayRadioButton");
		String AutoPaySubmitButtonXPath = commonData.get("AutoPaySubmitButtonId");
		String MinimumAmountDueXpath = commonData.get("MinimumAmountDue");
		String SelectOtherAmountToPayTextBox = commonData.get("SelectOtherAmountToPayTextBox");
		String AutoPayDisclaimerTextXPath = commonData.get("AutoPayDisclaimerText");
		String[][] dataInput = ExcelUtil.readExcelData(DataFilePath, "BillPayPage", "SelectAmountPaymentScreen");
		int currentBalanceAmount = BillPayPage.currentBalanceAmount(webPage, AutoPayCurrentBalanceXPath, currentBalance,mychar, softAssert);
		double ExactCurrentBalanceAmount =BillPayPage.ExactCurrentBalanceAmount(webPage, AutoPayCurrentBalanceXPath, currentBalance,mychar, softAssert);
		int minDueAmount = BillPayPage.minDueAmount(webPage, MinimumAmountDueXpath, minAmountDue, minAmountchar,softAssert);
		try {
			
			log.info("*****  Negative Scenarios Starts *************");
			log.info("****** Blank Input for Other Amount text box : *****");
			log.info("****** Select Other Amount Id Radio button ********");
			log.info("****** Click on Submit button,Verify Radio button Error Text :*****");
			BillPayPage.BlankInputValidation(webPage, SelectOtherAmountToPayRadioButtonXPath, AutoPaySubmitButtonXPath,	dataInput, softAssert);
			
			log.info("**** minAmountDue *****  : " + minAmountDue );
			log.info("**** New Less than minAmountDue : " + minDueAmount);
			log.info("**** Enter Amount Equal To Minimum Amount or payment due : 85.06");
			log.info("**** Press TAB,Verify Payment Other Date radio button Error Text");			
			log.info("**** Verify Disclaimers Text & Clear other amount data Text box value & Press TAB");
			BillPayPage.MinimumAmountToPayValidation(webPage, SelectOtherAmountToPayTextBox, AutoPaySubmitButtonXPath,dataInput, minDueAmount, softAssert);
			BillPayPage.DisclaimerTextValidation(webPage, disclaimersText, disclaimersRemoveText,AutoPayDisclaimerTextXPath, SelectOtherAmountToPayTextBox, minDueAmount, softAssert);
			
			log.info("**** minAmountDue *****  : " + minDueAmount );
			log.info("**** New minAmountDue : " + --minDueAmount);
			log.info("**** Enter Amount Less Than Minimum Amount or payment due : 85.06");
			log.info("**** Press TAB,Verify Payment Other Date radio button Error Text");
			log.info("**** Verify Disclaimers Text & Clear other amount data Text box value & Press TAB");
			BillPayPage.MinimumAmountToPayValidation(webPage, SelectOtherAmountToPayTextBox, AutoPaySubmitButtonXPath,dataInput, minDueAmount, softAssert);
			BillPayPage.DisclaimerTextValidation(webPage, disclaimersText, disclaimersRemoveText,AutoPayDisclaimerTextXPath, SelectOtherAmountToPayTextBox, minDueAmount, softAssert);
			
			log.info("**** currentBalance *****  : " + currentBalanceAmount);
			log.info("**** New currentBalance **** : " + currentBalanceAmount);
			log.info("**** Enter Amount Equal To  Current Balance amount or payment due:");
			log.info("**** Press TAB,Verify Payment Other Date radio button Error Text:");			
			log.info("**** Verify Disclaimers Text & Clear other amount data Text box value : ***** ");
			BillPayPage.ExactCurrentAmountToPayValidation(webPage, SelectOtherAmountToPayTextBox, AutoPaySubmitButtonXPath,dataInput, ExactCurrentBalanceAmount, softAssert);
			BillPayPage.ExactDisclaimerTextValidation(webPage, disclaimersText, disclaimersRemoveText,AutoPayDisclaimerTextXPath, SelectOtherAmountToPayTextBox, ExactCurrentBalanceAmount, softAssert);
			
			log.info("**** Enter Amount More Than Current Balance : ****");
			log.info("**** Press Tab,Verify Payment Other Date radio button Error Text : ****");
			log.info("**** currentBalance     : **** " + currentBalanceAmount);
			log.info("**** New currentBalance : *****" + ++currentBalanceAmount);			
			BillPayPage.CurrentAmountToPayValidation(webPage, SelectOtherAmountToPayTextBox, AutoPaySubmitButtonXPath,dataInput, currentBalanceAmount, softAssert);
			
			log.info("**** Verify Disclaimers Text & Clear other amount data Text box value : ****");
			BillPayPage.DisclaimerTextValidationForCurrentBalance(webPage, disclaimersText, disclaimersRemoveText,AutoPayDisclaimerTextXPath, SelectOtherAmountToPayTextBox, currentBalanceAmount, numberAsString,softAssert);
		
			softAssert.assertAll();
			log.info("** verify_SetUp_AutoPay_OtherAmountErrorMessage_DisclaimerText Completed Successfully ***");

		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()),"verify_SetUp_AutoPay_OtherAmountErrorMessage_DisclaimerText");
			softAssert.assertAll();
		}
	}

	/**
	 * TC_062 to TC_070
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1003, enabled = true, description = "verify_Confirmation_Message_Functionality")
	public void verify_Confirmation_Message_Functionality() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		log.info("********** verify_Confirmation_Message_Functionality Starts *******");
		String currentBalance = "";
		String mychar = "";
		String minAmountDue = "";
		String minAmountchar = "";
		String numberAsString = "";
		String[][] dataInput = ExcelUtil.readExcelData(DataFilePath, "BillPayPage", "SelectAmountPaymentScreen");
		String[][] testInput = ExcelUtil.readExcelData(DataFilePath, "BillPayPage", "BillPageCommonElements");
		String[][] ExpectedFontValuesWeb = ExcelUtil.readExcelData(DataFilePath, "BillPayPage","VerifyConfirmationSuccessFontandSizeWeb");
		String[][] ExpectedFontValuesTab = ExcelUtil.readExcelData(DataFilePath, "BillPayPage","VerifyConfirmationSuccessFontandSizeTab");
		String[][] ExpectedFontValuesMobile = ExcelUtil.readExcelData(DataFilePath, "BillPayPage","VerifyConfirmationSuccessFontandSizeMobile");
		String ConfirmationSuccessMessageBackButtonXPath = dataInput[33][1];
		String AutoPayCurrentBalanceXPath = commonData.get("AutoPayCurrentBalance");
		String MinimumAmountDueXpath = commonData.get("MinimumAmountDue");
		int currentBalanceAmount = BillPayPage.currentBalanceAmount(webPage, AutoPayCurrentBalanceXPath, currentBalance, mychar, softAssert);
		int minDueAmount = BillPayPage.minDueAmount(webPage, MinimumAmountDueXpath, minAmountDue, minAmountchar,softAssert);
		try {
			log.info("*****  Positive Scenarios Starts *************");
			log.info("***** SelectAmountToPayRadioButtonClicking Scenario Starts : **********");
			BillPayPage.SelectAmountToPayRadioButtonClicking(webPage, testInput, softAssert);
			
			log.info("**** Enter Amount More Than Minimum Amount or payment due : 85.06 ");
			log.info("**** Press TAB,Verify Payment Other Date radio button Error Text ");
			log.info("**** minAmountDue *****  : " + minDueAmount + "New minAmountDue : " + minDueAmount);
			BillPayPage.MinAmountPositiveScenario(webPage, testInput, minDueAmount, softAssert);
			
			log.info("**** Current Balance Amount Positive Scenario Starts : **********");
			log.info("**** Enter Amount Less Than  Current Balance amount or payment due ");
			log.info("**** Press TAB,Verify Payment Other Date radio button Error Text");
			log.info("**** currentBalance *****  : " + currentBalanceAmount);
			log.info("**** New currentBalance :  " + currentBalanceAmount);
			BillPayPage.currentBalanceAmountPositiveScenario(webPage, testInput, currentBalanceAmount, softAssert);
			
			log.info("**** Pop Up Box Text Validation Starts :  *********** : ");
			BillPayPage.PopUpBoxTextValidation(webPage, dataInput, testInput, softAssert);
			
			log.info("**** Confirmation Success Message  FontSizeVerification  Starts :  *********** : ");
			BillPayPage.FontSizeVerification(webPage, minAmountchar, numberAsString, ExpectedFontValuesMobile,ExpectedFontValuesTab, ExpectedFontValuesWeb, softAssert);
			
			log.info("**** Clicking Confirmation Success Message Back Button Click :  *********** : ");
			BillPayPage.ClickElementPresenceByJS(webPage, ConfirmationSuccessMessageBackButtonXPath, softAssert);
			
			log.info("**** Confirmation Functionality setupAccountRetrievalFunctionality Starts :  *********** : ");
			BillPayPage.setupAccountRetrievalFunctionality(webPage, dataInput, softAssert);
			
			softAssert.assertAll();
			log.info("**** verify_Confirmation_Message_Functionality Completed Successfully ***");
			
		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()),"verify_Confirmation_Message_Functionality");
			softAssert.assertAll();
		}
	}

}
