package com.etouch.connsTests;

import java.awt.AWTException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.etouch.common.BaseTest;
import com.etouch.common.CommonMethods;
import com.etouch.common.TafExecutor;
import com.etouch.connsPages.BillPayPage;
import com.etouch.connsPages.BillPayPage_Auto_Pay_Setup2;
import com.etouch.connsPages.ConnsMainPage;
import com.etouch.connsPages.ManageAutoPayPage;
import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.datamanager.excel.annotations.IExcelDataFiles;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;

import com.etouch.taf.webui.selenium.WebPage;

@Test(groups = "YesMoneyCreditApplication")
@IExcelDataFiles(excelDataFiles = { "CreditAppData=testData" })
public class Conns_Auto_Pay_Setup2 extends BaseTest {
	private String testBedName;
	TestBed testBed;
	Path path;
	protected static String DataFilePath;
	protected String testType, browserName;
	String currentTestBedName;
	static Log log = LogUtil.getLog(Conns_Auto_Pay_Setup2.class);
	Logger logger = Logger.getLogger(Conns_Auto_Pay_Setup2.class.getName());
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
	//BillPayPage billPayPage;
	BillPayPage_Auto_Pay_Setup2 billPayPage;
	ManageAutoPayPage manageAutoPayPage;
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
			//commonData = CommonMethods.getDataInHashMap(DataFilePath, "ManageAutoPayage", "ManageAutoPayCommonElements");
			platform = testBed.getPlatform().getName().toUpperCase();
			url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL();
			synchronized (this) {
				manageAutoPayPage = new ManageAutoPayPage();
				billPayPage = new BillPayPage_Auto_Pay_Setup2();
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
			billPayPage.ClickElementPresenceByJS(webPage, signInLinkXPath, softAssert);
		
			log.info("**** Click on Pay your Bill header link ****");
			billPayPage.connsLogin(inputData[0][1], inputData[1][1], webPage, softAssert);
			
			log.info("**** Click-on Credit Summary Link ****");
			commonMethods.clickElementbyLinkText(webPage, commonData.get("creditSummaryLinkText"), softAssert);
			
			log.info("**** SetUp AutoPayButtonHandling Starts ****");
			billPayPage.AutoPayButtonHandling(webPage, ElementXpath, dataInput, softAssert);
			
			log.info("**** Setup Auto Pay Default Radio Button isSelected Validation ****");
			log.info("**** Identify Default Radio Button Validation :");
			billPayPage.RadioButtonValidation(webPage, feedData, softAssert);
			
			log.info("**** AddNewPayMethod Functionality *****");
			billPayPage.ClickElementPresenceByJS(webPage, ElementXpath, softAssert);
			
			log.info("**** Credit Card Funding Portal Page Title URL Validation  *********");
			billPayPage.PageTitle_PageURL_Validation(webPage, testData, ExpectedURL, ExpectedPageTitle);
			
			log.info("**** Cancel Button Validation & Click on Credit Card Funding Portal ****");
			billPayPage.ClickElementPresenceByJS(webPage, CancelButtonFundingPortal, softAssert);
			
			log.info("**** Cancel Button Error Message Validation : Unable to add New Payment Method *******");
			billPayPage.ErroMessageValidation(webPage, ActualCancelButtonErrorMessage, CancelButtonErrorMessageXpath,ExpectedCancelButtonErrorMessage, softAssert);
			
			log.info("**** Select Minimum Payment Pay Radio button selection **** ");
			billPayPage.isDefaultRadioButtonSelected(webPage,softAssert);
			billPayPage.ClickElementPresenceByJS(webPage, selectAlternatePaymentDateRadioButtonXPATH, softAssert);
			
			log.info("**** SelectPaymentDate_DropDown_Menu_Fill_Up with Invalid Input & Assertion Message****"); 			
			billPayPage.selectDropdownByValue(webPage, DropDownXpath, invalidDropDownValue, softAssert);
			billPayPage.DropDownValueErrorMessageAssertion(webPage, SelectPaymentDateDropDownData, testData, softAssert);
			
			log.info("**** SelectPaymentDate_DropDown_Menu_Fill_Up with Valid Input ");
			billPayPage.selectDropdownByValue(webPage, DropDownXpath, validDropDownValue, softAssert);
			
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
			billPayPage.BlankInputValidation(webPage, SelectOtherAmountToPayRadioButtonXPath, AutoPaySubmitButtonXPath,	dataInput, softAssert);
			
			log.info("**** minAmountDue *****  : " + minAmountDue );
			log.info("**** New Less than minAmountDue : " + minDueAmount);
			log.info("**** Enter Amount Equal To Minimum Amount or payment due : 85.06");
			log.info("**** Press TAB,Verify Payment Other Date radio button Error Text");			
			log.info("**** Verify Disclaimers Text & Clear other amount data Text box value & Press TAB");
			billPayPage.MinimumAmountToPayValidation(webPage, SelectOtherAmountToPayTextBox, AutoPaySubmitButtonXPath,dataInput, minDueAmount, softAssert);
			billPayPage.DisclaimerTextValidation(webPage, disclaimersText, disclaimersRemoveText,AutoPayDisclaimerTextXPath, SelectOtherAmountToPayTextBox, minDueAmount, softAssert);
			
			log.info("**** minAmountDue *****  : " + minDueAmount );
			log.info("**** New minAmountDue : " + --minDueAmount);
			log.info("**** Enter Amount Less Than Minimum Amount or payment due : 85.06");
			log.info("**** Press TAB,Verify Payment Other Date radio button Error Text");
			log.info("**** Verify Disclaimers Text & Clear other amount data Text box value & Press TAB");
			billPayPage.MinimumAmountToPayValidation(webPage, SelectOtherAmountToPayTextBox, AutoPaySubmitButtonXPath,dataInput, minDueAmount, softAssert);
			billPayPage.DisclaimerTextValidation(webPage, disclaimersText, disclaimersRemoveText,AutoPayDisclaimerTextXPath, SelectOtherAmountToPayTextBox, minDueAmount, softAssert);
			
			log.info("**** currentBalance *****  : " + currentBalanceAmount);
			log.info("**** New currentBalance **** : " + currentBalanceAmount);
			log.info("**** Enter Amount Equal To  Current Balance amount or payment due:");
			log.info("**** Press TAB,Verify Payment Other Date radio button Error Text:");			
			log.info("**** Verify Disclaimers Text & Clear other amount data Text box value : ***** ");
			billPayPage.ExactCurrentAmountToPayValidation(webPage, SelectOtherAmountToPayTextBox, AutoPaySubmitButtonXPath,dataInput, ExactCurrentBalanceAmount, softAssert);
			billPayPage.ExactDisclaimerTextValidation(webPage, disclaimersText, disclaimersRemoveText,AutoPayDisclaimerTextXPath, SelectOtherAmountToPayTextBox, ExactCurrentBalanceAmount, softAssert);
			
			log.info("**** Enter Amount More Than Current Balance : ****");
			log.info("**** Press Tab,Verify Payment Other Date radio button Error Text : ****");
			log.info("**** currentBalance     : **** " + currentBalanceAmount);
			log.info("**** New currentBalance : *****" + ++currentBalanceAmount);			
			billPayPage.CurrentAmountToPayValidation(webPage, SelectOtherAmountToPayTextBox, AutoPaySubmitButtonXPath,dataInput, currentBalanceAmount, softAssert);
			
			log.info("**** Verify Disclaimers Text & Clear other amount data Text box value : ****");
			billPayPage.DisclaimerTextValidationForCurrentBalance(webPage, disclaimersText, disclaimersRemoveText,AutoPayDisclaimerTextXPath, SelectOtherAmountToPayTextBox, currentBalanceAmount, numberAsString,softAssert);
		
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
			billPayPage.SelectAmountToPayRadioButtonClicking(webPage, testInput, softAssert);
			
			log.info("**** Enter Amount More Than Minimum Amount or payment due : 85.06 ");
			log.info("**** Press TAB,Verify Payment Other Date radio button Error Text ");
			log.info("**** minAmountDue *****  : " + minDueAmount + "New minAmountDue : " + minDueAmount);
			billPayPage.MinAmountPositiveScenario(webPage, testInput, minDueAmount, softAssert);
			
			log.info("**** Current Balance Amount Positive Scenario Starts : **********");
			log.info("**** Enter Amount Less Than  Current Balance amount or payment due ");
			log.info("**** Press TAB,Verify Payment Other Date radio button Error Text");
			log.info("**** currentBalance *****  : " + currentBalanceAmount);
			log.info("**** New currentBalance :  " + currentBalanceAmount);
			billPayPage.currentBalanceAmountPositiveScenario(webPage, testInput, currentBalanceAmount, softAssert);
			
			log.info("**** Pop Up Box Text Validation Starts :  *********** : ");
			billPayPage.PopUpBoxTextValidation(webPage, dataInput, testInput, softAssert);
			
			log.info("**** Confirmation Success Message  FontSizeVerification  Starts :  *********** : ");
			billPayPage.FontSizeVerification(webPage, minAmountchar, numberAsString, ExpectedFontValuesMobile,ExpectedFontValuesTab, ExpectedFontValuesWeb, softAssert);
			
			log.info("**** Clicking Confirmation Success Message Back Button Click :  *********** : ");
			billPayPage.ClickElementPresenceByJS(webPage, ConfirmationSuccessMessageBackButtonXPath, softAssert);
			
			log.info("**** Confirmation Functionality setupAccountRetrievalFunctionality Starts :  *********** : ");
			//BillPayPage.setupAccountRetrievalFunctionality(webPage, dataInput, softAssert);
			
			softAssert.assertAll();
			log.info("**** verify_Confirmation_Message_Functionality Completed Successfully ***");
			
		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()),"verify_Confirmation_Message_Functionality");
			softAssert.assertAll();
		}
	}
//**********************************************************Manage Auto Pay********************************************************
	
	@Test(priority = 1004, enabled = true, description = "Verify Green verbiage and buttons displayed for Manage AutoPay account")
	public void Verify_GreenVerbiage_and_Buttons_for_Manage_AutoPay_Account() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "ManageAutoPayPage","FunctionalityManageAccount");
			
			
			String greenText = commonMethods.getTextbyXpath(webPage, testData[3][1], softAssert);
			
			greenText = greenText.replace(greenText.substring(greenText.indexOf("$"),greenText.indexOf("$")+7),"");
			
			greenText = greenText.replace(greenText.substring(greenText.indexOf("date")+7,greenText.length()),"");
						
			//Verify the verbiage displayed in Green color
			softAssert.assertEquals(greenText.trim(),testData[4][1], "Verbiage displayed does not match");
			
			//Verify Discalimer text
			String ActualDisclaimerText = commonMethods.getTextbyXpath(webPage, testData[6][1], softAssert);
			log.info("Actual disclaimer text is" +ActualDisclaimerText +"AND" + "Expected disclaimer text is" + testData[7][1]);
			softAssert.assertEquals(ActualDisclaimerText, testData[7][1]);
			
			//Click on Manage Auto Pay button
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[2][1],softAssert);
			
			//Verify Modify Auto pay buttons available on screen
			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, commonData.get("modifyAutoPayBtnXpath"), softAssert),
					"Modify Auto pay Button . ");
			
			//Verify Cancel Auto pay buttons available on screen
			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, commonData.get("CancelAutoPayBtnXpath"), softAssert),
					"Cancel Auto pay Button . ");
			
			//Verify Back buttons available on screen
			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, commonData.get("BackButtonXPath"), softAssert),
					"Back . ");
			
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "content_functionality_manage_autopay_account");
			softAssert.assertAll();
		}
	}
	
	
	@Test(priority = 1005, enabled = true, description = "Verify Font size, style of view payment plan page")
	public void verify_PaymentPlan_Page_FontProperties() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			//Verify Important Notice
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "ManageAutoPayPage",
					"FontPropertiesPaymentPlan");
			
			for(int i=0;i<5;i++) {
				webPage.sleep(10000);
					String fontSize = commonMethods.getCssvaluebyXpath(webPage, testData[i][1], "font-size", softAssert);
					String fontColor = commonMethods.getCssvaluebyXpath(webPage, testData[i][1], "color", softAssert);
					String fontFamily = commonMethods.getCssvaluebyXpath(webPage, testData[i][1], "font-family", softAssert);
					String fontWeight = commonMethods.getCssvaluebyXpath(webPage, testData[i][1], "font-weight", softAssert);
					String backgroundColor = commonMethods.getCssvaluebyXpath(webPage, testData[i][1], "background-color", softAssert);
					
					if(!testData[i][1].equalsIgnoreCase("NA"))
					softAssert.assertEquals(fontSize,testData[i][2],"Font Size . Expected "+testData[i][2]+" Actual : "+fontSize);
					if(!testData[i][3].equalsIgnoreCase("NA"))
					softAssert.assertEquals(fontColor,testData[i][3],"Font Color . Expected "+testData[i][3]+" Actual : "+fontColor);
					if(!testData[i][4].equalsIgnoreCase("NA"))
					softAssert.assertTrue(fontFamily.contains(testData[i][4]),"Font Family . Expected "+testData[i][4]+" Actual : "+fontFamily);
					if(!testData[i][5].equalsIgnoreCase("NA"))
					softAssert.assertEquals(fontWeight,testData[i][5],"Font Weight . Expected "+testData[i][5]+" Actual : "+fontWeight);
					if(!testData[i][6].equalsIgnoreCase("NA"))
					softAssert.assertEquals(backgroundColor,testData[i][6],"Background color . Expected "+testData[i][6]+" Actual : "+backgroundColor);
					
				
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_onetime_paynt_font");
			softAssert.assertAll();
		}
	}
	


	@Test(priority = 1006, enabled = true, description = "Verify content of Modify auto pay page")
	public void verify_Content_Modify_AutoPay_Page() throws Exception {

		log.info("************Started Verify content of Modify auto pay page*********************");
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "ManageAutoPayPage", "ContentManageAccount");

			// Verify all the values on view payment plan matching on schedule
			// payment screen

			String PaymentMethod = commonMethods.findElementByXpath(webPage, testData[16][1], softAssert).getText();
			String PaymentDay = commonMethods.findElementByXpath(webPage, testData[17][1], softAssert).getText();
			String Frequency = commonMethods.findElementByXpath(webPage, testData[18][1], softAssert).getText();
			String Status = commonMethods.findElementByXpath(webPage, testData[19][1], softAssert).getText();
			String AmountScheduled = commonMethods.findElementByXpath(webPage, testData[20][1], softAssert).getText();

			System.out.println(PaymentMethod + PaymentDay + Frequency + Status + AmountScheduled);
			// Click on modify auto pay button
			commonMethods.clickElementbyXpath(webPage, commonData.get("modifyAutoPayBtnXpath"), softAssert);

			// Verify tab name
			String TabName = commonMethods.getTextbyXpath(webPage, testData[14][1], softAssert);
			log.info("Tab Name displayed on clicking on modify auto pay button is==>" + TabName);
			softAssert.assertEquals(TabName, testData[15][1],
					"One time payment tab is displayed instead Set up auto pay");

			// Verify Amount displayed on view payment plan matching with amount
			// displayed on schedule payment screen
			for (int i = 21; i <= 23; i++) {
				billPayPage.IsRadioSelected(webPage, testData[i][1], AmountScheduled, softAssert);
			}

			// Verify Day displayed on view payment plan matching with Day
			// displayed on schedule payment screen
			for (int i = 24; i <= 25; i++) {

				billPayPage.IsRadioSelected(webPage, testData[i][1], PaymentDay, softAssert);
			}

			// Verify PaymentMethod displayed on view payment plan matching with
			// PaymentMethod displayed on schedule payment screen
			List<WebElement> PaymentMethodRadios = webPage.getDriver().findElements(By.xpath(testData[26][1]));
			int i = 1;
			for (WebElement e : PaymentMethodRadios) {
				boolean actualValue = e.isSelected();
				if (actualValue) {
					String xpath = testData[27][1];
					xpath = xpath.replace("[i]", "[" + i + "]");
					log.info("*********xpath==" + xpath + "**********");
					String actualtext = webPage.getDriver().findElement(By.xpath(xpath)).getText();
					log.info("**********actualtext==>" + actualtext);

					softAssert.assertEquals(actualtext, PaymentMethod, "Payment method does not match");
				}
				i++;
			}
			  

			// Verify Frequency displayed on VPP screen
			softAssert.assertEquals(Frequency, testData[28][1], "Displayed Frequency is wrong");
			log.info("Expected Frequency==" + testData[28][1] + "Actual Status==" + Frequency);

			// Verify Status displayed on VPP screen
			softAssert.assertEquals(Status, testData[29][1], "Displayed Status is wrong");
			log.info("Expected Status==" + testData[29][1] + "Actual Status==" + Status);

			softAssert.assertAll();
			log.info("************Completed Verify content of Modify auto pay page*********************");
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()),
					"content_content_manage_autopay_account");
			softAssert.assertAll();
		}
	}
	
	@Test(priority = 1007, enabled = false, description = "Verify One Time Payment Confirmation Font properties")
	public void verify_Payment_ManageAutoPay_Page_FontProperties() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			//Verify Important Notice
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "PayYourBill",
					"OneTimePaymentConfirmation");
			
			for(int i=0;i<4;i++) {
				webPage.sleep(10000);
					String fontSize = commonMethods.getCssvaluebyXpath(webPage, testData[i][1], "font-size", softAssert);
					String fontColor = commonMethods.getCssvaluebyXpath(webPage, testData[i][1], "color", softAssert);
					String fontFamily = commonMethods.getCssvaluebyXpath(webPage, testData[i][1], "font-family", softAssert);
					String fontWeight = commonMethods.getCssvaluebyXpath(webPage, testData[i][1], "font-weight", softAssert);
					String backgroundColor = commonMethods.getCssvaluebyXpath(webPage, testData[i][1], "background-color", softAssert);
					
					if(!testData[i][1].equalsIgnoreCase("NA"))
					softAssert.assertEquals(fontSize,testData[i][2],"Font Size . Expected "+testData[i][2]+" Actual : "+fontSize);
					if(!testData[i][3].equalsIgnoreCase("NA"))
					softAssert.assertEquals(fontColor,testData[i][3],"Font Color . Expected "+testData[i][3]+" Actual : "+fontColor);
					if(!testData[i][4].equalsIgnoreCase("NA"))
					softAssert.assertTrue(fontFamily.contains(testData[i][4]),"Font Family . Expected "+testData[i][4]+" Actual : "+fontFamily);
					if(!testData[i][5].equalsIgnoreCase("NA"))
					softAssert.assertEquals(fontWeight,testData[i][5],"Font Weight . Expected "+testData[i][5]+" Actual : "+fontWeight);
					if(!testData[i][6].equalsIgnoreCase("NA"))
					softAssert.assertEquals(backgroundColor,testData[i][6],"Background color . Expected "+testData[i][6]+" Actual : "+backgroundColor);
					
				
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "Verify_View_Paymant_Plan_FontProperties");
			softAssert.assertAll();
		}
	}
	
	/**
	 * 
	 */
	
	/**
	 * TC_131 to TC_134
	 * @throws Exception
	 */
	@Test(priority = 1008, enabled = true, description = "Verify clicking on cancel button")
	public void verify_Functionality_Cancel_Button() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "ManageAutoPayPage","CancelButtonFunctionality");
			
			//Click on Back button
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("BackButtonXPath"),softAssert);
			
			//Click on Manage auto pay link
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("manageAutoPayBtnXpath"),softAssert);
			
			//Click on Cancel auto pay button
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("CancelAutoPayBtnXpath"),softAssert);
			
			String actualpopuptext= commonMethods.getTextbyXpath(webPage, testData[10][1], softAssert);
			log.info("**actualpopuptext*************"+actualpopuptext);
			//Cancel confirmation popup should be displayed
			softAssert.assertEquals(commonMethods.getTextbyXpath(webPage, testData[10][1], softAssert),testData[11][1],
					"Cancel confirmation popup header Text. Expected "+testData[11][1]+" Actual : "+commonMethods.getTextbyXpath(webPage, testData[10][1], softAssert));
			
			softAssert.assertEquals(commonMethods.getTextbyXpath(webPage, testData[12][1], softAssert),testData[13][1],
					"Cancel confirmation popup para Text. Expected "+testData[13][1]+" Actual : "+commonMethods.getTextbyXpath(webPage, testData[12][1], softAssert));
			
			//Verify close link on Cancel confirmation popup
			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, testData[0][1], softAssert),
					"Close link on Cancel confirmation popup. ");
			
			//Click on Cancel auto pay button  on Cancel confirmation popup
			//commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[2][1],softAssert);
			commonMethods.clickElementbyXpath_usingJavaScript(webPage, testData[2][1], softAssert);
			
			//Verify Cancel auto pay success page
			softAssert.assertEquals(commonMethods.getTextbyXpath(webPage, testData[3][1], softAssert),testData[4][1],
					"Success cancel auto pay Text. Expected "+testData[4][1]+" Actual : "+commonMethods.getTextbyXpath(webPage, testData[3][1], softAssert));
			
			//Verify Back button on success cancel auto pay page
			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, commonData.get("BackButtonXPath"), softAssert),
					"Back button on success cancel auto pay page. ");
			
			//Click here link on success cancel auto pay page
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("BackButtonXPath"),softAssert);
			
			//Verify Account summary header text
			softAssert.assertTrue(commonMethods.getTextbyXpath(webPage, testData[6][1], softAssert).contains(testData[7][1]),
					"Account Summary header text. ");
			
			//Click on pay bill button
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[8][1],softAssert);
			
			//Verify One time payment tab
			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, testData[9][1], softAssert),
					"One time payment tab. ");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "content_cancel_button");
			softAssert.assertAll();
		}
	}
}

