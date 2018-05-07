package com.etouch.connsTests;

import java.awt.AWTException;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

import org.apache.commons.logging.Log;

import org.apache.http.client.ClientProtocolException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.etouch.common.BaseTest;
import com.etouch.common.CommonMethods;
import com.etouch.connsPages.ConnsMainPage;

import com.etouch.connsPages.MyPaymentMethodsPaymentReformPage;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.taf.webui.selenium.WebPage;


public class MyPayment_Methods_Payment_Reform extends BaseTest {
	private String testBedName;
	Path path;
	String DataFilePath;
	protected String testType, browserName;
	String currentTestBedName;
	static Log log = LogUtil.getLog(MyPayment_Methods_Payment_Reform.class);
	private String url, testEnv;
	protected WebPage webPage;
	private ConnsMainPage mainPage;
	protected static CommonMethods commonMethods;
	@SuppressWarnings("unused")
	private String registerUrl, MyPaymentMethodsURL, Register_Link;
	String[][] commonData;
	boolean userLoggedIn = false;
	MyPaymentMethodsPaymentReformPage myPaymentMethodsPaymentReformPage;
	protected static LinkedHashMap<Long, WebPage> webPageMap = new LinkedHashMap<Long, WebPage>();

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
				commonData = ExcelUtil.readExcelData(DataFilePath, "MyPaymentMethods",
						"MyPaymentMethodsCommonElements");
				url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL();
				registerUrl = commonData[1][1];
				MyPaymentMethodsURL = commonData[2][1];
				Register_Link = commonData[4][1];
				// DashboardURL = commonData[8][1];
				synchronized (this) {
					webPage = new WebPage(context);
					webPageMap.put(Thread.currentThread().getId(), webPage);
					mainPage = new ConnsMainPage(url, webPageMap.get(Thread.currentThread().getId()));
					// mainPage = new ConnsMainPage(url, webPageMap.get(Thread.currentThread().getId()));
					// createAccountAndSignInPage = new CreateAccountAndSignInPage(url, webPageMap.get(Thread.currentThread().getId()));
					myPaymentMethodsPaymentReformPage = new MyPaymentMethodsPaymentReformPage(url, webPage);
					log.info(mainPage);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.info("errr is " + e);
				SoftAssertor.addVerificationFailure(e.getMessage());
			}
			CommonMethods.navigateToPage(webPageMap.get(Thread.currentThread().getId()), MyPaymentMethodsURL);
		} catch (Exception e) {
			e.printStackTrace();
			CommonUtil.sop("errr is for" + testBedName + " -----------" + e);
			SoftAssertor.addVerificationFailure(e.getMessage());
		}
	}

	@AfterTest
	public void releaseResources() throws IOException, AWTException {
		webPageMap.get(Thread.currentThread().getId()).getDriver().quit();
	}

	@Test(priority = 301, enabled = true)
	public void MyPaymentMethod_Main_Page() throws ClientProtocolException, IOException, InterruptedException {
		log.info("******Started MyPaymentMethod_Main_Page ********");
		SoftAssert softAssert = new SoftAssert();
		String[][] ExpectedFontValuesWeb = ExcelUtil.readExcelData(DataFilePath, "MyPaymentMethods",
				"VerifyFontandSizeWeb");
		String[][] ExpectedFontValuesTab = ExcelUtil.readExcelData(DataFilePath, "MyPaymentMethods",
				"VerifyFontandSizeTab");
		String[][] ExpectedFontValuesMobile = ExcelUtil.readExcelData(DataFilePath, "MyPaymentMethods",
				"VerifyFontandSizeMobile");
		String[][] verify_SignIn_With_Valid_Input = ExcelUtil.readExcelData(DataFilePath, "MyPaymentMethods",
				"verify_SignIn_With_Valid_Input");
		String[][] linkData = ExcelUtil.readExcelData(DataFilePath, "MyPaymentMethods", "verifyLinksRedirection");
		commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), Register_Link, softAssert);
		commonMethods.waitForPageLoad(webPageMap.get(Thread.currentThread().getId()), softAssert);
		String ExpectedPageTitle = "";
		String ExpectedURL = "";
		String ElementXpath = "";
		try {

			log.info(" ************* Conns Customer Page Title Verification *******************");
			ExpectedPageTitle = verify_SignIn_With_Valid_Input[14][2];
			ExpectedURL = verify_SignIn_With_Valid_Input[14][1];
			MyPaymentMethodsPaymentReformPage.PageTitle_PageURL_Validation(verify_SignIn_With_Valid_Input, ExpectedURL,
					ExpectedPageTitle);

			log.info(" ********* Login Credentials for Registered User ****************");
			for (int i = 0; i < 2; i++) {
				commonMethods.sendKeysbyXpath(webPageMap.get(Thread.currentThread().getId()), verify_SignIn_With_Valid_Input[i][1],
						verify_SignIn_With_Valid_Input[i][2], softAssert);
			}

			log.info(" *********** Clicking on Submit Button on Registered Customer Login Page ********");
			ElementXpath = verify_SignIn_With_Valid_Input[2][1];
			MyPaymentMethodsPaymentReformPage.PageElementClick(webPageMap.get(Thread.currentThread().getId()), ElementXpath, softAssert);

			log.info(" *********** Handling Hamburger Icon & PageBrokenLinkVerification *********");
			MyPaymentMethodsPaymentReformPage.ResizeableDropDownHandling(webPageMap.get(Thread.currentThread().getId()), testBedName, testType,
					verify_SignIn_With_Valid_Input, linkData, softAssert);

			log.info(" *** My Payment Methods Click From Payment Method Main Page Resizeable Menu Option  Starts ****");
			ElementXpath = verify_SignIn_With_Valid_Input[4][1];
			MyPaymentMethodsPaymentReformPage.PageElementClick(webPageMap.get(Thread.currentThread().getId()), ElementXpath, softAssert);

			log.info(" ************* FontSizeVerification Starts on My Payment Methods Main Page *********");
			MyPaymentMethodsPaymentReformPage.FontSizeVerification(webPageMap.get(Thread.currentThread().getId()), testType, testBedName,
					ExpectedFontValuesMobile, ExpectedFontValuesTab, ExpectedFontValuesWeb, softAssert);

			log.info("************* ImageVerification Starts on My Payment Methods Main Page *********");
			MyPaymentMethodsPaymentReformPage.ImageVerification(webPageMap.get(Thread.currentThread().getId()), softAssert);

			log.info("************* RowsTextContents Starts on My Payment Methods Main Page *********");
			MyPaymentMethodsPaymentReformPage.RowsTextContents(webPageMap.get(Thread.currentThread().getId()), softAssert);

			log.info(" ************* Validation & Clicking of Add Button on My Payment Methods Main Page *********");
			ElementXpath = verify_SignIn_With_Valid_Input[7][1];
			MyPaymentMethodsPaymentReformPage.ClickElementPresenceByJS(webPageMap.get(Thread.currentThread().getId()), ElementXpath, softAssert);


			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "MyPaymentMethod_Page");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test(priority = 302, enabled = true)
	public void MyPaymentMethod_CreditCard_Page() throws ClientProtocolException, IOException, InterruptedException {
		log.info("******Started MyPaymentMethod_CreditCard_Page ********");
		SoftAssert softAssert = new SoftAssert();
		JavascriptExecutor js = (JavascriptExecutor) webPageMap.get(Thread.currentThread().getId()).getDriver();
		String ExpectedPageTitle = "";
		String ExpectedURL = "";
		String DropDownXpath = "";
		@SuppressWarnings("unused")
		String FilePath = "";

		try {
			String[][] VerifyCreditCardFundingPortal = ExcelUtil.readExcelData(DataFilePath, "MyPaymentMethods",
					"VerifyCreditCardFundingPortal");
			ExpectedPageTitle = VerifyCreditCardFundingPortal[14][2];
			ExpectedURL = VerifyCreditCardFundingPortal[14][1];
			// String CreditCardZipCodeXPATH = VerifyCreditCardFundingPortal[2][1];
			String SaveCreditCardButtonXpath = VerifyCreditCardFundingPortal[9][1];
			MyPaymentMethodsPaymentReformPage.PageTitle_PageURL_Validation(VerifyCreditCardFundingPortal, ExpectedURL,
					ExpectedPageTitle);
			commonMethods.waitForPageLoad(webPageMap.get(Thread.currentThread().getId()), softAssert);
			CommonMethods.waitForGivenTime(5);

			log.info("***************** MyPaymentMethod_CreditCard_Panel_Data_Form_Fill_Up *************************");
			WebElement element = webPageMap.get(Thread.currentThread().getId()).getDriver().findElement(By.xpath(VerifyCreditCardFundingPortal[0][1]));
			if (element.isDisplayed() && element.isEnabled())
				js.executeScript("arguments[0].click();", element);
			for (int i = 1; i <= 4; i++) {
				commonMethods.sendKeysbyXpath(webPageMap.get(Thread.currentThread().getId()), VerifyCreditCardFundingPortal[i][1],
						VerifyCreditCardFundingPortal[i][2], softAssert);
			}
			log.info("********** MyPaymentMethod_CreditCard_Panel_StateDropdownValueAllOptions_Compare ******");
			FilePath = "\\Conns_POC\\src\\test\\resources\\StateDropDownValues.properties";
			DropDownXpath = VerifyCreditCardFundingPortal[5][1];
			//MyPaymentMethodsPaymentReformPage.CompareDropdownValueAllOptions(webPageMap.get(Thread.currentThread().getId()), FilePath, DropDownXpath,softAssert);

			log.info("****** MyPaymentMethod_CreditCard_Panel_MonthDropdownValueAllOptions_Compare **********");
			FilePath = "\\Conns_POC\\src\\test\\resources\\MonthDropDownValues.properties";
			DropDownXpath = VerifyCreditCardFundingPortal[6][1];
			MyPaymentMethodsPaymentReformPage.CompareDropdownValueAllOptions(webPageMap.get(Thread.currentThread().getId()), FilePath, DropDownXpath,softAssert);

			log.info("****** MyPaymentMethod_CreditCard_Panel_YearDropdownValueAllOptions_Compare ******");
			FilePath = "\\Conns_POC\\src\\test\\resources\\YearDropDownValues.properties";
			DropDownXpath = VerifyCreditCardFundingPortal[7][1];
			MyPaymentMethodsPaymentReformPage.CompareDropdownValueAllOptions(webPageMap.get(Thread.currentThread().getId()), FilePath, DropDownXpath,softAssert);

			log.info("******* MyPaymentMethod_CreditCard_Panel_State_DropDown_Menu_Fill_Up ******");
			for (int i = 5; i <= 7; i++) {
				DropDownXpath = VerifyCreditCardFundingPortal[i][1];
				String dropdownvalue = VerifyCreditCardFundingPortal[i][2];
				MyPaymentMethodsPaymentReformPage.selectDropdownByValue(webPageMap.get(Thread.currentThread().getId()), DropDownXpath, dropdownvalue,softAssert);
			}

			log.info("********* MyPaymentMethod_CreditCard_Panel_CVV_Number_Data_Fill_Up ********");
			for (int i = 8; i <= 8; i++) {
				commonMethods.sendKeysbyXpath(webPageMap.get(Thread.currentThread().getId()), VerifyCreditCardFundingPortal[i][1],
						VerifyCreditCardFundingPortal[i][2], softAssert);
				/***** Click on External Form Fill Up Area in order to enable the Save Button***/
				element.sendKeys(Keys.TAB);
				element.sendKeys(Keys.ENTER);
			}

			/****** Save Button Display Validation ******/
			MyPaymentMethodsPaymentReformPage.Button_Xpath_Validation(webPageMap.get(Thread.currentThread().getId()), VerifyCreditCardFundingPortal,
					SaveCreditCardButtonXpath, softAssert);

			/****** Error Message Display ******/
			for (int i = 10; i < 13; i++) {
				String SuccessMessageXPATH = VerifyCreditCardFundingPortal[i][1];
				String ExpectedSuccessMessageTEXT = VerifyCreditCardFundingPortal[i][2];
				MyPaymentMethodsPaymentReformPage.SuccessMessageValidation(webPageMap.get(Thread.currentThread().getId()), VerifyCreditCardFundingPortal,
						SuccessMessageXPATH, ExpectedSuccessMessageTEXT, softAssert);
			}

			ExpectedPageTitle = VerifyCreditCardFundingPortal[15][2];
			ExpectedURL = VerifyCreditCardFundingPortal[15][1];
			MyPaymentMethodsPaymentReformPage.PageTitle_PageURL_Validation(VerifyCreditCardFundingPortal, ExpectedURL,
					ExpectedPageTitle);

			log.info("******* MyPaymentMethod_Page_AddNewPaymentMethod Button Display Validation *****************");
			String AddNewPaymentMethodButtonXpath = VerifyCreditCardFundingPortal[13][1];
			MyPaymentMethodsPaymentReformPage.Button_Xpath_Validation(webPageMap.get(Thread.currentThread().getId()), VerifyCreditCardFundingPortal,
					AddNewPaymentMethodButtonXpath, softAssert);

			log.info("*********** AddNewPaymentMethodButton Page URL & Page Title Validation **************");
			ExpectedPageTitle = VerifyCreditCardFundingPortal[16][2];
			ExpectedURL = VerifyCreditCardFundingPortal[16][1];
			MyPaymentMethodsPaymentReformPage.PageTitle_PageURL_Validation(VerifyCreditCardFundingPortal, ExpectedURL,
					ExpectedPageTitle);
		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "MyPaymentMethod_CreditCard_Page");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/***************************************************************************************************************************/
	@Test(priority = 303, enabled = true)
	public void MyPaymentMethod_Banking_Page() throws ClientProtocolException, IOException, InterruptedException {
		log.info("******Started MyPaymentMethod_Banking_Page ********");
		SoftAssert softAssert = new SoftAssert();
		JavascriptExecutor js = (JavascriptExecutor) webPageMap.get(Thread.currentThread().getId()).getDriver();
		String[][] ExpectedFontValuesWeb = ExcelUtil.readExcelData(DataFilePath, "MyPaymentMethods",
				"VerifyConfirmationSuccessFontandSizeWeb");
		String[][] ExpectedFontValuesTab = ExcelUtil.readExcelData(DataFilePath, "MyPaymentMethods",
				"VerifyConfirmationSuccessFontandSizeTab");
		String[][] ExpectedFontValuesMobile = ExcelUtil.readExcelData(DataFilePath, "MyPaymentMethods",
				"VerifyConfirmationSuccessFontandSizeMobile");

		String ExpectedPageTitle = "";
		String ExpectedURL = "";
		String DropDownXpath = "";
		String FilePath = "";
		String dropdownvalue = "";
		try {
			String[][] VerifyBankingFundingPortal = ExcelUtil.readExcelData(DataFilePath, "MyPaymentMethods",
					"VerifyBankingFundingPortal");
			commonMethods.waitForPageLoad(webPageMap.get(Thread.currentThread().getId()), softAssert);
			CommonMethods.waitForGivenTime(5);

			log.info(" ********** MyPaymentMethod_Banking_Panel_Page_Title_Verification *******");
			ExpectedPageTitle = VerifyBankingFundingPortal[14][2];
			ExpectedURL = VerifyBankingFundingPortal[14][1];
			MyPaymentMethodsPaymentReformPage.PageTitle_PageURL_Validation(VerifyBankingFundingPortal, ExpectedURL,
					ExpectedPageTitle);

			log.info("***************** MyPaymentMethod_Banking_Panel_Tab_Validation *************************");
			WebElement element = webPageMap.get(Thread.currentThread().getId()).getDriver().findElement(By.xpath(VerifyBankingFundingPortal[0][1]));
			if (element.isDisplayed() && element.isEnabled())
				js.executeScript("arguments[0].click();", element);

			log.info("***************** MyPaymentMethod_Banking_Panel_Data_Form_Fill_Up *************************");
			for (int i = 1; i < 5; i++) {
				commonMethods.sendKeysbyXpath(webPageMap.get(Thread.currentThread().getId()), VerifyBankingFundingPortal[i][1],
						VerifyBankingFundingPortal[i][2], softAssert);
			}

			log.info("********** MyPaymentMethod_BankAccountStateDropDownValues_Comparison ********");
			FilePath = "\\Conns_POC\\src\\test\\resources\\BankAccountStateDropDownValues.properties";
			DropDownXpath = VerifyBankingFundingPortal[6][1];
			MyPaymentMethodsPaymentReformPage.CompareDropdownValueAllOptions(webPageMap.get(Thread.currentThread().getId()), FilePath, DropDownXpath,
					softAssert);

			log.info("******* MyPaymentMethod_BankAccountTypeDropDownValues_Comparison **********");
			FilePath = "\\Conns_POC\\src\\test\\resources\\BankAccountType.properties";
			DropDownXpath = VerifyBankingFundingPortal[7][1];
			MyPaymentMethodsPaymentReformPage.CompareDropdownValueAllOptions(webPageMap.get(Thread.currentThread().getId()), FilePath, DropDownXpath,
					softAssert);

			log.info("********* MyPaymentMethod_Bank_Account_Panel_State_DropDown_Menu_Fill_Up *********");
			for (int i = 6; i <= 7; i++) {
				DropDownXpath = VerifyBankingFundingPortal[i][1];
				dropdownvalue = VerifyBankingFundingPortal[i][2];
				MyPaymentMethodsPaymentReformPage.selectDropdownByValue(webPageMap.get(Thread.currentThread().getId()), DropDownXpath, dropdownvalue,
						softAssert);
			}

			log.info("******* MyPaymentMethod_Banking_Panel_Account_Number_Field_Fill_Up **********");
			for (int i = 5; i < 6; i++) {
				long Account_Number = MyPaymentMethodsPaymentReformPage.generateRandom(12);
				String AccountNumberAsString = Long.toString(Account_Number);
				commonMethods.sendKeysbyXpath(webPageMap.get(Thread.currentThread().getId()), VerifyBankingFundingPortal[i][1], AccountNumberAsString,
						softAssert);
			}
			log.info("*********Enabling_MyPaymentMethod_Banking_Panel_Page_Save_Button ***********");

			log.info("**** MyPaymentMethod_Banking_Panel_Page_Save_Button_Validation **********");
			MyPaymentMethodsPaymentReformPage.SaveButtonValidation(webPageMap.get(Thread.currentThread().getId()), VerifyBankingFundingPortal, softAssert);

			log.info("******* MyPaymentMethod_Banking_Page_Successfull_Confirmation_Message_Validation ******");
			for (int i = 9; i < 12; i++) {
				String SuccessMessageXPATH = VerifyBankingFundingPortal[i][1];
				String ExpectedSuccessMessageTEXT = VerifyBankingFundingPortal[i][2];
				MyPaymentMethodsPaymentReformPage.SuccessMessageValidation(webPageMap.get(Thread.currentThread().getId()), VerifyBankingFundingPortal,
						SuccessMessageXPATH, ExpectedSuccessMessageTEXT, softAssert);
			}

			log.info("**************** MyPaymentMethod_Confirmation_Message_Font_Size_Validation *****");
			MyPaymentMethodsPaymentReformPage.FontSizeVerification(webPageMap.get(Thread.currentThread().getId()), testType, testBedName,
					ExpectedFontValuesMobile, ExpectedFontValuesTab, ExpectedFontValuesWeb, softAssert);

			log.info("**************** MyPaymentMethod_Confirmation_Message_Page_Back_Button_Validation *****");
			MyPaymentMethodsPaymentReformPage.MyPaymentMethod_Confirmation_Message_Page_Back_Button(webPageMap.get(Thread.currentThread().getId()),
					VerifyBankingFundingPortal, softAssert);

			log.info("********* MyPaymentMethod_Confirmation_Message_Page_Back_Button_Validation Page URL & Page Title Validation ********");
			ExpectedPageTitle = VerifyBankingFundingPortal[15][2];
			ExpectedURL = VerifyBankingFundingPortal[15][1];
			MyPaymentMethodsPaymentReformPage.PageTitle_PageURL_Validation(VerifyBankingFundingPortal, ExpectedURL,
					ExpectedPageTitle);
		}

		catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "MyPaymentMethod_Page");
			softAssert.assertAll();
			softAssert.fail(e.getLocalizedMessage());
		}
	}

}
