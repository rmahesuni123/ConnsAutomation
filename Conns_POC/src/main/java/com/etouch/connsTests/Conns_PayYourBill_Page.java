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



import org.openqa.selenium.WebElement;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.etouch.common.BaseTest;
import com.etouch.common.CommonMethods;
import com.etouch.common.TafExecutor;

import com.etouch.connsPages.ConnsMainPage;
import com.etouch.connsPages.MyPaymentMethodsPaymentReformPage;
import com.etouch.connsPages.PayYourBillPage;
import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;

import com.etouch.taf.webui.selenium.WebPage;


public class Conns_PayYourBill_Page extends BaseTest {
	private String testBedName;
	TestBed testBed;
	Path path;
	protected static String DataFilePath;
	protected String testType, browserName;
	String currentTestBedName;
	static Log log = LogUtil.getLog(Conns_PayYourBill_Page.class);
	Logger logger = Logger.getLogger(Conns_PayYourBill_Page.class.getName());
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
	PayYourBillPage payYourBillPage;
	protected static LinkedHashMap<Long, WebPage> webPageMap = new LinkedHashMap<Long, WebPage>();
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
				commonData = CommonMethods.getDataInHashMap(DataFilePath, "PayYourBill","payYourBillPageCommonElements");
				
				platform = testBed.getPlatform().getName().toUpperCase();
				url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL();
				synchronized (this) {
					payYourBillPage = new PayYourBillPage();
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
	 * TC_001 to TC_008 and TC_011
	 * @throws Exception
	 */
	@Test(priority = 1001, enabled = true, description = "Verify Pay Your Bill- Header Link Title And Verbiages")
	public void verify_PayYourBillPage_Title_And_Verbiage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			//Click on Pay your Bill header link
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("payyourbillXpath"),softAssert);
			
			payYourBillPage.verifyVerbiageOfPayYourBillAndAccountPages(softAssert);
			//Verify Register button
			payYourBillPage.verifyRegisterButton(webPage, softAssert);			
			
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_PayYourBillPage_Title_And_Verbiage");
			softAssert.assertAll();
		}
	}
	
	/**
	 * TC_001 to TC_008 and TC_011
	 * @throws Exception
	 */
	@Test(priority = 1002, enabled = true, description = "Verify Pay Your Bill Footer Link Page Title And Verbiages")
	public void verify_PayYourBill_FooterLinkPage_Title_And_Verbiage() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			
			log.info("******** Navigate to Base URL  ******************");//
			CommonMethods.navigateToPage(webPageMap.get(Thread.currentThread().getId()), url);
			log.info("******** Scroll down till the footer  ******************");//
			commonMethods.scrollDown(webPage, 800);
			log.info("******** Click on Pay your Bill footer link ******************");//
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("payyourbillFooterXpath"),softAssert);
			
			log.info("******** Verify pay your bills and Credit account Verbiage Text ******************");//
			payYourBillPage.verifyVerbiageOfPayYourBillAndAccountPages(softAssert);
			
			log.info("******** Verify Register button ******************");//
			payYourBillPage.verifyRegisterButton(webPage, softAssert);
			
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_payyourbill_verbiages");
			softAssert.assertAll();
		}
	}
	/**
	 * TC_012, TC_013
	 * @throws Exception
	 */
	@Test(priority = 1003, enabled = true, description = "Verify Account Summary page")
	public void verify_Account_Summary_Page() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			//Verify Important Notice
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "PayYourBill",
					"verifyAccountSummaryPage");
			log.info("******** Navigate to Base URL ******************");
			CommonMethods.navigateToPage(webPageMap.get(Thread.currentThread().getId()), url);
			log.info("******** Click on SignIn Link ******************");
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[0][1],softAssert);
			log.info("******** verify Page title ******************");
			payYourBillPage.LoginWithGivenDetails(testData[1][1],testData[2][1],webPage,softAssert);
			
			commonMethods.clickElementbyLinkText(webPage, testData[3][1], softAssert);
			
			softAssert.assertEquals(webPageMap.get(Thread.currentThread().getId()).getPageTitle(),testData[6][1],
					"Account Summary page title . Expected "+testData[6][1]+" Actual : "+webPageMap.get(Thread.currentThread().getId()).getPageTitle());
						
			String accountSumaryHeaderText = commonMethods.getTextbyXpath(webPage, testData[4][1], softAssert);
			
			softAssert.assertTrue(accountSumaryHeaderText.contains(testData[5][1]),
					"Account Summary page header text . Expected "+testData[5][1]+" Actual : "+accountSumaryHeaderText);
			
			commonMethods.clickElementById(webPageMap.get(Thread.currentThread().getId()), testData[7][1],softAssert);
			
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("payyourbillXpath"),softAssert);
			
			softAssert.assertEquals(webPageMap.get(Thread.currentThread().getId()).getPageTitle(),testData[6][1],
					"Account Summary page title . Expected "+testData[6][1]+" Actual : "+webPageMap.get(Thread.currentThread().getId()).getPageTitle());
			
			String accountSumaryHeaderText1 = commonMethods.getTextbyXpath(webPage, testData[4][1], softAssert);
			
			softAssert.assertTrue(accountSumaryHeaderText1.contains(testData[5][1]),
					"Account Summary page header text . Expected "+testData[5][1]+" Actual : "+accountSumaryHeaderText1);
			//LoggedOut from Conns 
			//payYourBillPage.connsSignOut(webPage,softAssert);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_payyourbill_summary");
			softAssert.assertAll();
		}
	}
	
	/**
	 * TC_014
	 * @throws Exception
	 */
	@Test(priority = 1004, enabled = true, description = "Verify Left Navigation Menus")
	public void verify_BillPay_Page_LeftMenus() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "PayYourBill","verifyBillPayLeftNavLinks");			
			List<WebElement> leftNavLinks = commonMethods.findElementsByXpath(webPage, testData[0][1], softAssert);
			String navLinks = "";
			for (WebElement webElement : leftNavLinks) {
				if(webElement.getText().length()!=0) {
				navLinks=navLinks+webElement.getText().trim()+",";
				}
			}
			softAssert.assertTrue(navLinks.contains(testData[2][1]),
					"Left Navigation Link Text . Expected "+testData[2][1]+" Actual : "+navLinks);
			
			List<WebElement> leftNavHeaderText = commonMethods.findElementsByXpath(webPage, testData[1][1], softAssert);
			String leftNavHeader = "";
			for (WebElement webElement : leftNavHeaderText) {
				if(webElement.getText().length()!=0) {
					leftNavHeader=leftNavHeader+webElement.getText().trim()+",";
				}
			}
			softAssert.assertTrue(leftNavHeader.contains(testData[3][1]),
					"Left Navigation Header Text . Expected "+testData[3][1]+" Actual : "+leftNavHeader);
			//LoggedOut from Conns 
			payYourBillPage.connsSignOut(webPage,softAssert);
			
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_navigation_menu");
			softAssert.assertAll();
		}
		
	}
	
	
	@Test(priority = 1005, enabled = true, description = "Verify Account Summary page for Inactive account")
	public void verify_Account_Summary_For_InactiveAccount() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String ElementXpath = "";
		log.info("************Started method verify_Account_Summary_For_InactiveAccount()***************");
		try {

			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "PayYourBill",
					"verifyAccountSummaryForInctiveAccount");
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[0][1],
					softAssert);
			// verify Page title
			payYourBillPage.loginWithGivenDetails(testData[1][1], testData[2][1], webPage, softAssert);
			
			ElementXpath = testData[15][1];
			payYourBillPage.ScrollToElement(webPage, ElementXpath, softAssert);
			commonMethods.clickElementbyLinkText(webPage, testData[3][1], softAssert);
			//webPage.sleep(4000);

			String ActualPageTitle = webPage.getPageTitle();
			log.info("ActualPageTitle : " + ActualPageTitle);
			log.info("ExpectedPageTitle : " + testData[6][1]);

			String ActualInActiveAccountSumaryHeaderText = commonMethods.getTextbyXpath(webPage, testData[4][1],
					softAssert);
			log.info("ActualInActiveAccountSumaryHeaderText : " + ActualInActiveAccountSumaryHeaderText);
			log.info("ExpectedInActiveAccountSumaryHeaderText : " + testData[5][1]);

			softAssert.assertTrue(ActualInActiveAccountSumaryHeaderText.contains(testData[5][1]),
					"ExpectedInActiveAccountSumaryHeaderText " + testData[5][1] + " Actual : "
							+ ActualInActiveAccountSumaryHeaderText);

			String ClosedAccountHeaderText = commonMethods.getTextbyXpath(webPage, testData[7][1], softAssert);
			log.info("ActualClosedAccountHeaderText : " + ClosedAccountHeaderText);
			log.info("ExpectedClosedAccountHeaderText : " + testData[8][1]);
			softAssert.assertTrue(ClosedAccountHeaderText.contains(testData[8][1]),
					"Expected_ClosedAccountHeaderText==>  " + testData[8][1] + " Actual : " + ClosedAccountHeaderText);

			String ActualDisclaimerText = commonMethods.getTextbyXpath(webPage, testData[9][1], softAssert);
			log.info("ActualDisclaimerText : " + ActualDisclaimerText);
			log.info("ExpectedDisclaimerText : " + testData[10][1]);
			softAssert.assertTrue(ActualDisclaimerText.contains(testData[10][1]),
					"Expected Disclaimer Text==>" + testData[10][1] + "Actual" + ActualDisclaimerText);

			String ActualLoanAccount_Cname = commonMethods.getTextbyXpath(webPage, testData[11][1], softAssert);
			log.info("ActualActualLoanAccount_Cname : " + ActualLoanAccount_Cname);
			log.info("ExpectedActualLoanAccount_Cname : " + testData[12][1]);
			softAssert.assertTrue(ActualLoanAccount_Cname.contains(testData[12][1]),
					"ExpectedActualLoanAccount_Cname==>" + testData[12][1] + "Actual" + ActualLoanAccount_Cname);

			String ActualUpdatedDate_Cname = commonMethods.getTextbyXpath(webPage, testData[13][1], softAssert);
			log.info("ActualUpdatedDate_Cname : " + ActualUpdatedDate_Cname);
			log.info("ExpectedUpdatedDate : " + testData[14][1]);
			softAssert.assertTrue(ActualUpdatedDate_Cname.contains(testData[14][1]),
					"ExpectedUpdatedDate==>" + testData[14][1] + "Actual" + ActualUpdatedDate_Cname);

			softAssert.assertAll();
			log.info("************Completed method verify_Account_Summary_For_InactiveAccount()***************");
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()),
					"Account_Summary_For_InactiveAccount");
			softAssert.assertAll();
		}
	}
	
	
	
	
	
}
