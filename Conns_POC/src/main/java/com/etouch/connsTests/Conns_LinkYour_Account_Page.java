package com.etouch.connsTests;

import java.awt.AWTException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

import java.util.Random;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
import com.etouch.connsPages.LinkYourAccountPage;
import com.etouch.connsPages.PayYourBillPage;
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


public class Conns_LinkYour_Account_Page extends BaseTest {
	private String testBedName;
	TestBed testBed;
	Path path;
	protected static String DataFilePath;
	protected String testType, browserName;
	String currentTestBedName;
	static Log log = LogUtil.getLog(Conns_LinkYour_Account_Page.class);
	Logger logger = Logger.getLogger(Conns_LinkYour_Account_Page.class.getName());
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
	Random random=new Random();
	protected static LinkedHashMap<Long, String> testBedNames = new LinkedHashMap<Long, String>();
	LinkYourAccountPage linkYourAccountPage;
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
				log.info(" platform : "+platform);
				testEnv = System.getenv().get("Environment");
				log.info("testEnv is : " + System.getenv().get("Environment"));
				path = Paths.get(TestBedManager.INSTANCE.getProfile().getXlsDataConfig().get("testData"));
				DataFilePath = path.toAbsolutePath().toString().replace("Env", testEnv);
				log.info("DataFilePath After is : " + DataFilePath);
				commonData = CommonMethods.getDataInHashMap(DataFilePath, "LinkYourAccount","verifyLinkYourAccountCommonElements");
				
				platform = testBed.getPlatform().getName().toUpperCase();
				url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL();
				synchronized (this) {
					linkYourAccountPage = new LinkYourAccountPage();
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
	 * TC_022
	 * @throws Exception
	 */
	
	@Test(priority = 1001, enabled = true, description = "Verify Link your Account page")
	public void verify_LinkYour_Account_Page() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			//Verify Important Notice
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "LinkYourAccount",
					"verifyLinkYourAccountPage");
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[0][1],softAssert);
			//Conns login with credentials and validate sucess login page
			linkYourAccountPage.connsLogin(commonData.get("emailId"),testData[1][1],commonData.get("passwordId"),testData[2][1],webPage,softAssert);
			
			commonMethods.clickElementbyLinkText(webPage, commonData.get("creditSummaryLinkText"), softAssert);
			
			softAssert.assertEquals(webPageMap.get(Thread.currentThread().getId()).getPageTitle(),testData[6][1],
					"Link your account page title . Expected "+testData[6][1]+" Actual : "+webPageMap.get(Thread.currentThread().getId()).getPageTitle());
						
			String linkYourAccountHeaderText = commonMethods.getTextbyXpath(webPage, testData[4][1], softAssert);
			
			softAssert.assertEquals(linkYourAccountHeaderText,testData[5][1],
					"Link your account page header text . Expected "+testData[5][1]+" Actual : "+linkYourAccountHeaderText);
			
			commonMethods.clickElementById(webPageMap.get(Thread.currentThread().getId()), testData[7][1],softAssert);
			
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[8][1],softAssert);
			
			softAssert.assertEquals(webPageMap.get(Thread.currentThread().getId()).getPageTitle(),testData[6][1],
					"Link your account page title . Expected "+testData[6][1]+" Actual : "+webPageMap.get(Thread.currentThread().getId()).getPageTitle());
			
			String linkYourAccountHeaderText1 = commonMethods.getTextbyXpath(webPage, testData[4][1], softAssert);
			
			softAssert.assertEquals(linkYourAccountHeaderText1,testData[5][1],
					"Link your account page header text . Expected "+testData[5][1]+" Actual : "+linkYourAccountHeaderText1);
			
			softAssert.assertAll();
			
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Page_Title");
			softAssert.assertAll();
		}
	}
	
	/**
	 * TC_023 
	 * @throws Exception
	 */
	@Test(priority = 1002, enabled = true, description = "Verify Link your Account page Font properties")
	public void verify_LinkYour_Account_Page_FontProperties() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			//Verify Important Notice
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "LinkYourAccount",
					"verifyFontProperties");

			for(int i=0;i<7;i++) {
				webPage.sleep(10000);
					String fontSize = commonMethods.getCssvaluebyXpath(webPage, testData[i][1], "font-size", softAssert);
					String fontColor = commonMethods.getCssvaluebyXpath(webPage, testData[i][1], "color", softAssert);
					String fontFamily = commonMethods.getCssvaluebyXpath(webPage, testData[i][1], "font-family", softAssert);
					String fontWeight = commonMethods.getCssvaluebyXpath(webPage, testData[i][1], "font-weight", softAssert);
					String backgroundColor = commonMethods.getCssvaluebyXpath(webPage, testData[i][1], "background-color", softAssert);
					
					if(!testData[i][2].equalsIgnoreCase("NA"))
					softAssert.assertEquals(fontSize,testData[i][2],"Font Size . Expected "+testData[i][2]+" Actual : "+fontSize);
					if(!testData[i][3].equalsIgnoreCase("NA"))
					softAssert.assertEquals(fontColor,testData[i][3],"Font Color . Expected "+testData[i][3]+" Actual : "+fontColor);
					if(!testData[i][4].equalsIgnoreCase("NA"))
					softAssert.assertTrue(fontFamily.contains(testData[i][4]),"Font Family . Expected "+testData[i][4]+" Actual : "+fontFamily);
					if(!testData[i][5].equalsIgnoreCase("NA"))
					softAssert.assertEquals(fontWeight,testData[i][5],"Font Weight . Expected "+testData[i][5]+" Actual : "+fontWeight);
					if(!testData[i][6].equalsIgnoreCase("NA"))
					softAssert.assertEquals(backgroundColor,testData[i][6],"Background color . Expected "+testData[i][6]+" Actual : "+backgroundColor);
					
//				}
				
			}
			
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Page_Title");
			softAssert.assertAll();
		}
	}
	
	/**
	 * TC_024 to TC_029
	 * @throws Exception
	 */
	@Test(priority = 1003, enabled = true, description = "Verify Link loan account functionality ")
	public void verify_Link_Account_Functionality() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			//Get test data and store in variable
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "LinkYourAccount",
					"verifyLinkLoanAccountFunctionality");

			softAssert.assertEquals(webPageMap.get(Thread.currentThread().getId()).getPageTitle(),testData[3][1],
					"Link your account page title . Expected "+testData[3][1]+" Actual : "+webPageMap.get(Thread.currentThread().getId()).getPageTitle());
			log.info("Verified Link Account page title");
			/**
			 Verify First Name and Last Name should be auto populated and should be non editable
			 **/
			String fname = (String)js.executeScript("return document.getElementById(\""+testData[19][1]+"\").value;");
			String lname = (String)js.executeScript("return document.getElementById(\""+testData[20][1]+"\").value;");
			softAssert.assertTrue(fname.length()!=0,
					"First Name should be auto populated . Expected - Actual : "+fname);
			
			softAssert.assertTrue(lname.length()!=0,
					"Last Name should be auto populated . Expected - Actual : "+lname);
			softAssert.assertTrue(commonMethods.getCssValueById(webPage, testData[19][1], "readonly", softAssert).length()==0,
					"First Name ReadOnly attribute value . Expected - Actual : "+commonMethods.getCssValueById(webPage, testData[19][1], "readonly", softAssert));
			
			softAssert.assertTrue(commonMethods.getCssValueById(webPage, testData[20][1], "readonly", softAssert).length()==0,
					"Last Name ReadOnly attribute value . Expected - Actual : "+commonMethods.getCssValueById(webPage, testData[20][1], "readonly", softAssert));
			/**
			End of Verify First Name and Last Name should be auto populated and should be non editable
			 **/
			//Verify the verbiage displayed on link account page when no account is associated to profile
			softAssert.assertEquals(commonMethods.getTextbyXpath(webPage, testData[17][1], softAssert),testData[18][1],
					"Link your account page title . Expected "+testData[18][1]+" Actual : "+commonMethods.getTextbyXpath(webPage, testData[17][1], softAssert));
			log.info("Verbiage displayed on link account page when no account is associated to profile ");
			
			/** Required fields validation starts **/
			//Click on Link New Account Button
			commonMethods.clickElementById(webPage, testData[6][1], softAssert);
			
			//Verify Your Conn's account number required field validation
			softAssert.assertEquals(commonMethods.getTextbyId(webPage, testData[12][1], softAssert),testData[7][1],
					"Link your account page title . Expected "+testData[7][1]+" Actual : "+commonMethods.getTextbyId(webPage, testData[12][1], softAssert));
			
			//Verify Your Conn's account SSN number required field validation
			softAssert.assertEquals(commonMethods.getTextbyId(webPage, testData[13][1], softAssert),testData[7][1],
					"Link your account page title . Expected "+testData[7][1]+" Actual : "+commonMethods.getTextbyId(webPage, testData[13][1], softAssert));
			log.info("Link Account number and SSN Required fields validation ");
			/** Required fields validation Ends **/
			
			/**Account number and SSN number should not accept char values  **/
			linkYourAccountPage.linkYourConnsCreditAccount(testData[4][1],testData[11][1],testData[5][1],testData[11][1],webPage,softAssert);
			
			//Verify Your Conn's account number enter characters field validation
			commonMethods.sendKeysById(webPage,testData[5][1],""+Keys.TAB, softAssert);
			String loanAccountNo = (String)js.executeScript("return document.getElementById(\"loan_account_number\").value;");
			softAssert.assertEquals(loanAccountNo,"",
					"Verify Account number after enter text values . Expected "+""+" Actual : "+loanAccountNo);
			
			//Verify Your Conn's account SSN number Enter characters field validation
			String loanAccountSsn = (String)js.executeScript("return document.getElementById(\"loan_account_ssn\").value;");
			softAssert.assertEquals(loanAccountSsn,"",
					"Verify SSN number after enter text values . Expected "+""+" Actual : "+loanAccountSsn);
			
			log.info("Account number and SSN number should not accept char values ");
			/**End of Account number and SSN number should not accept char values  **/
			
			/** Less the 3 digits account no and  account ssn no fields validation starts **/
			
			linkYourAccountPage.linkYourConnsCreditAccount(testData[4][1],testData[8][1],testData[5][1],testData[8][1],webPage,softAssert);
			
			commonMethods.sendKeysById(webPage,testData[5][1],""+Keys.TAB, softAssert);
			
			//Verify Your Conn's account number less than 3 digits field validation
			
			softAssert.assertEquals(commonMethods.getTextbyId(webPage, testData[14][1], softAssert),testData[9][1],
					"Link your account page title . Expected "+testData[9][1]+" Actual : "+commonMethods.getTextbyId(webPage, testData[14][1], softAssert));
			
			//Verify Your Conn's account SSN number less than 4 digits field validation
			softAssert.assertEquals(commonMethods.getTextbyId(webPage, testData[15][1], softAssert),testData[16][1],
					"Link your account page title . Expected "+testData[16][1]+" Actual : "+commonMethods.getTextbyId(webPage, testData[15][1], softAssert));
			log.info("Link Account number and SSN less than 3 digits validation ");
			/** Less the 3 digits account no and  account ssn no fields validation Ends **/
			
			/** Start Verify entering more than 12 digit account number **/
			commonMethods.clearTextBoxById(webPage,testData[4][1], softAssert);
			commonMethods.sendKeysById(webPage,testData[4][1],testData[10][1], softAssert);
			commonMethods.sendKeysById(webPage,testData[5][1],""+Keys.TAB, softAssert);
			String accountNo = (String)js.executeScript("return document.getElementById(\""+testData[4][1]+"\").value;");
			softAssert.assertEquals(accountNo,testData[10][1].substring(0, testData[10][1].length()-1),
					"Verify Account number after enter text values . Expected "+testData[10][1].substring(0, testData[10][1].length()-1)+" Actual : "+accountNo);
			/** End Verify entering more than 12 digit account number **/
			
			softAssert.assertAll();
			
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Page_Title");
			softAssert.assertAll();
		}
	}
	
	/**
	 * TC_031
	 * @throws Exception
	 */
	@Test(priority = 1004, enabled = true, description = "Verify Link your Account page Click Here Link")
	public void verify_LinkYour_Account_Page_ClickHereLink() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			//Verify Important Notice
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "LinkYourAccount",
					"verifyClickHereLink");
			
			
			//Click on 'Click Here' link
			commonMethods.clickElementbyXpath(webPage, testData[0][1], softAssert);
			
			//Verify 'Click here' link target page
			softAssert.assertEquals(webPage.getPageTitle(),testData[1][1],
					"Conn's Credit Card issued by Synchrony Financial title . Expected "+testData[1][1]+" Actual : "+webPage.getPageTitle());
			
			//Back to Link your account page
			webPage.getBackToUrl();
			
			
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Page_Title");
			softAssert.assertAll();
		}
	}
	
	/**
	 * TC_039
	 * @throws Exception
	 */
	@Test(priority = 1005, enabled = true, description = "Verify Account Number Tooptip text")
	public void verify_Account_Number_Tooltip() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			//Get the text data and stored in variable
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "LinkYourAccount",
					"verifyTooltipText");
			
			//Click on tooptip link
			commonMethods.clickElementbyXpath(webPage, testData[0][1], softAssert);
			//Verify Tooltip text
			softAssert.assertEquals(commonMethods.getTextbyXpath(webPage, testData[3][1], softAssert),testData[1][1],
					"Account Number Tooltip text. Expected "+testData[1][1]+" Actual : "+commonMethods.getTextbyXpath(webPage, testData[3][1], softAssert));
			//Click on Pay your bill
			commonMethods.clickElementbyLinkText(webPage, testData[2][1],softAssert);
			//LoggedOut from Conns 
//			linkYourAccountPage.connsSignOut(webPage,softAssert);
			
			log.info("Verified Account number tooltip text");
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Page_Title");
			softAssert.assertAll();
		}
	}
	
	/**
	 * TC_032 to TC_034
	 * @throws Exception
	 */
	@Test(priority = 1006, enabled = true, description = "Verify Link loan account form validations ")
	public void verify_Link_Account_FormValidations() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			//Get test data and store in variable
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "LinkYourAccount",
					"verifyLinkLoanAccountFormValidations");
			
			Random random = new Random();
			/** Enter valid account no and  and invalid account ssn no starts **/
			
			linkYourAccountPage.linkYourConnsCreditAccount(commonData.get("accountNoId"),testData[3][1],commonData.get("accountSSNNoId"),random.nextInt(10000)+"",webPage,softAssert);
			
			linkYourAccountPage.verifyLinkYourAccountErrorPage(testData, webPage, softAssert);
			
			/** Enter valid account no and  and invalid account ssn no Ends **/
			
			/** Enter invalid account no and  and valid account ssn no starts **/
			linkYourAccountPage.linkYourConnsCreditAccount(commonData.get("accountNoId"),random.nextInt(100000000)+"",commonData.get("accountSSNNoId"),testData[4][1],webPage,softAssert);
			
			linkYourAccountPage.verifyLinkYourAccountErrorPage(testData, webPage, softAssert);
			
			/** Enter valid account no and  and invalid account ssn no Ends **/
			
			/** Enter valid account no and  and valid account ssn no starts **/
			linkYourAccountPage.linkYourConnsCreditAccount(commonData.get("accountNoId"),testData[3][1],commonData.get("accountSSNNoId"),testData[4][1],webPage,softAssert);
			
			//Click on Link New Account Button
			commonMethods.clickElementById(webPage, commonData.get("accountSubmitBtnId"), softAssert);
			
			//Verify header text
			softAssert.assertEquals(commonMethods.getTextbyXpath(webPage, testData[8][1], softAssert),testData[9][1],
					"Account Summary Header text. Expected "+testData[9][1]+" Actual : "+commonMethods.getTextbyXpath(webPage, testData[8][1], softAssert));
			/** Enter valid account no and  and valid account ssn no Ends **/
			//LoggedOut from Conns 
			linkYourAccountPage.connsSignOut(webPage,softAssert);
			softAssert.assertAll();
			
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Page_Title");
			softAssert.assertAll();
		}
	}
	
	/**
	 * TC_035 to TC_038
	 * @throws Exception
	 */
	
	@Test(priority = 1007, enabled = true, description = "Verify already linked account ")
	public void verify_Already_Linked_Account() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			//Get test data and store in variable
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "LinkYourAccount",
					"verifyAlreadyLinkLoanAccount");
			CommonMethods.navigateToPage(webPageMap.get(Thread.currentThread().getId()), url);
		   //	Click on Sign in Link
			commonMethods.clickElementbyXpath(webPage, commonData.get("signInLinkXPath"),softAssert);
			//Conns login with credentials and validate success login page
			linkYourAccountPage.connsLogin(commonData.get("emailId"),testData[0][1],commonData.get("passwordId"),testData[1][1],webPage,softAssert);
			
			//Click on Conns Credit summary link
			commonMethods.clickElementbyLinkText(webPage, commonData.get("creditSummaryLinkText"), softAssert);
		
			//Verify header text
			softAssert.assertTrue(commonMethods.getTextbyXpath(webPage, testData[2][1], softAssert).contains(testData[3][1]),
					"Account Summary Header text. Expected "+testData[3][1]+" Actual : "+commonMethods.getTextbyXpath(webPage, testData[2][1], softAssert));
			
			softAssert.assertAll();
			//LoggedOut from Conns 
			linkYourAccountPage.connsSignOut(webPage,softAssert);
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_Page_Title");
			softAssert.assertAll();
		}
	}	

}
