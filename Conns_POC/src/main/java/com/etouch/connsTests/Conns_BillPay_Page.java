package com.etouch.connsTests;

import java.awt.AWTException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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
import com.etouch.connsPages.BillPayPage;
import com.etouch.connsPages.BillPayPageSetUpAutoPay;
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


public class Conns_BillPay_Page extends BaseTest {
	private String testBedName;
	TestBed testBed;
	Path path;
	protected static String DataFilePath;
	protected String testType, browserName;
	String currentTestBedName;
	static Log log = LogUtil.getLog(Conns_BillPay_Page.class);
	Logger logger = Logger.getLogger(Conns_BillPay_Page.class.getName());
	private String testEnv;
	protected static String url;
	WebPage webPage;
	
	protected static LinkedHashMap<String, String> commonData;
	static int shedulePaymntMinPayntXPath;
	static int shedulePaymntCurrentBalAmtXpath;
		
	private ConnsMainPage mainPage;
	
	CommonMethods commonMethods;
	String platform;
	String AbsolutePath = TafExecutor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	boolean declinedStatus = false;
	String[][] YesLeaseData;
	Random random=new Random();
	protected static LinkedHashMap<Long, String> testBedNames = new LinkedHashMap<Long, String>();
	BillPayPage billPayPage;
	//protected static LinkedHashMap<Long, webPage> webPage Map = new LinkedHashMap<Long, webPage>();
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
				commonData = CommonMethods.getDataInHashMap(DataFilePath, "BillPayPage","BillPageCommonElements"); 
				
				platform = testBed.getPlatform().getName().toUpperCase();
				url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL();
				synchronized (this) {
					billPayPage = new BillPayPage();
					Thread.sleep(3000);
					/***********************************/
					//webPageMap.get(Thread.currentThread().getId()) = new webPageMap.get(Thread.currentThread().getId())(context);
					//webPageMap.get(Thread.currentThread().getId())Map.put(Thread.currentThread().getId(), webPageMap.get(Thread.currentThread().getId()));
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
	 * TC_049 to TC_053
	 * @throws Exception
	 */
	@Test(priority = 1001, enabled = true, description = "Verify content & functionality for Overdue account")
	public void verify_ContentAndFunctionality_Overdue_Account() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "BillPayPage","ContentAndFunctionalityOverDueAccount");
			
			//
			log.info("****** Click on Sign In link *****");
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("signInLinkXPath"),softAssert);
			webPageMap.get(Thread.currentThread().getId()).sleep(10000);
			log.info("****** Click on Pay your Bill header link *****");
			log.info("******************************"+commonData.get("emailId"));
			billPayPage.connsLogin(testData[0][1], testData[1][1], webPageMap.get(Thread.currentThread().getId()), softAssert);
			
			log.info("****** Click-on Credit Summary Link *****");
			commonMethods.clickElementbyLinkText(webPageMap.get(Thread.currentThread().getId()), commonData.get("creditSummaryLinkText"), softAssert);	
			
			log.info("****** Verify the verbiage displayed in Red color *****");
			softAssert.assertEquals(testData[2][1],commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()),commonData.get("overdueVerbiageXpath"),softAssert),
					"Verbiage displayed in Red color . Expected "+commonData.get("overdueVerbiageXpath")+" Actual : "+testData[2][1]);
			
			log.info("****** Verify Account Summary header text Displayed *****");
			softAssert.assertTrue(commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[3][1], softAssert).contains(testData[4][1]),
					"Account Summary Header text. Expected "+testData[4][1]+" Actual : "+commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[3][1], softAssert));
			
			log.info("****** Click on Pay Bill Button *****");
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("payBillBtnXpath"),softAssert);
			
			log.info("****** Verify Schedule Payment header text Displayed *****");
			softAssert.assertTrue(commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[5][1], softAssert).contains(testData[6][1]),
					"Schedule Payment Header text. Expected "+testData[6][1]+" Actual : "+commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[5][1], softAssert));
			
			
			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "content_functionality_Overdue_account");
			softAssert.assertAll();
		}
	}
	
	/**
	 * TC_054 to TC_061
	 * @throws Exception
	 */
	@Test(priority = 1002, enabled = true, description = "Verify content on Schedule payment screen of overdue account")
	public void verify_Content_Schedule_Payment_Screen() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		
		try {
			
			log.info("****** Fetch verifyVerbiageRegisteredUsers data from excel  *****");
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "BillPayPage","contentSchedulePaymentScreen");
			
			shedulePaymntMinPayntXPath = billPayPage.getPaymentAmountByXpath(commonData.get("shedulePaymntMinPayntXPath"), webPageMap.get(Thread.currentThread().getId()), softAssert);
			log.info("***** shedulePaymntMinPayntXPath ********" +shedulePaymntMinPayntXPath);
			
			shedulePaymntCurrentBalAmtXpath =  billPayPage.getPaymentAmountByXpath(commonData.get("shedulePaymntCurrentBalAmtXpath"), webPageMap.get(Thread.currentThread().getId()), softAssert);
			log.info("***** shedulePaymntCurrentBalAmtXpath ********" +shedulePaymntMinPayntXPath);
			
			log.info("****** Select Minimum Payment Pay Radio button selection  *****");
			softAssert.assertTrue(commonMethods.verifyElementisSelected(webPageMap.get(Thread.currentThread().getId()), commonData.get("shedulePaymntMinPaymntId"), softAssert),
					"Select Minimum Payment Pay Radio button. Expected "+commonMethods.verifyElementisSelected(webPageMap.get(Thread.currentThread().getId()), commonData.get("shedulePaymntMinPaymntId"), softAssert));
			log.info("****** Select Payment Date Radio button selection  *****");
			softAssert.assertTrue(commonMethods.verifyElementisSelected(webPageMap.get(Thread.currentThread().getId()), commonData.get("shedulePaymntSelectDateId"), softAssert),
					"Select Payment Date Radio button. Expected "+commonMethods.verifyElementisSelected(webPageMap.get(Thread.currentThread().getId()), commonData.get("shedulePaymntSelectDateId"), softAssert));
			
			log.info("****** Click on Submit button  *****");
			commonMethods.clickElementById(webPageMap.get(Thread.currentThread().getId()), commonData.get("shedulePaymntSubmitBtnId"),softAssert);
			webPageMap.get(Thread.currentThread().getId()).sleep(10000);
			log.info("****** Verify Payment Date radio button Error Text  *****");
			softAssert.assertEquals(commonMethods.getTextbyId(webPageMap.get(Thread.currentThread().getId()), testData[0][1], softAssert),testData[1][1],
					"Payment Date radio button Error Text . Expected "+testData[1][1]+" Actual : "+commonMethods.getTextbyId(webPageMap.get(Thread.currentThread().getId()), testData[0][1], softAssert));
			log.info("****** Select Other Amount Id Radio button  *****");
			commonMethods.clickElementById(webPageMap.get(Thread.currentThread().getId()), commonData.get("shedulePaymntOtherAmountId"),softAssert);
			
			log.info("****** Enter value less than  minimum amount or payment due  *****");
			commonMethods.sendKeysById(webPageMap.get(Thread.currentThread().getId()), commonData.get("shedulePaymntOtherAmountTextBoxId"), testData[2][1], softAssert);
			
			log.info("****** Press TAB  *****");
			commonMethods.sendKeysById(webPageMap.get(Thread.currentThread().getId()),commonData.get("shedulePaymntOtherAmountTextBoxId"),""+Keys.TAB, softAssert);
			log.info("****** Verify Payment Other Date radio button Error Text  *****");
			softAssert.assertEquals(commonMethods.getTextbyId(webPageMap.get(Thread.currentThread().getId()), testData[3][1], softAssert),testData[4][1],
					"Value less than  minimum amount or payment due . Expected "+testData[4][1]+" Actual : "+commonMethods.getTextbyId(webPageMap.get(Thread.currentThread().getId()), testData[3][1], softAssert));
			
			log.info("****** Clear other data Text box value  *****");
			commonMethods.clearTextBox(webPageMap.get(Thread.currentThread().getId()), commonData.get("shedulePaymntOtherAmountTextBoxId"), softAssert);
			
			log.info("****** Enter value greater than current balance  *****");
			commonMethods.sendKeysById(webPageMap.get(Thread.currentThread().getId()), commonData.get("shedulePaymntOtherAmountTextBoxId"), ""+billPayPage.getAmount(commonData.get("shedulePaymntCurrentBalAmtXpath")+1,webPageMap.get(Thread.currentThread().getId()), softAssert), softAssert);
			log.info("****** Press TAB  *****");
			commonMethods.sendKeysById(webPageMap.get(Thread.currentThread().getId()),commonData.get("shedulePaymntOtherAmountTextBoxId"),""+Keys.TAB, softAssert);
			
			log.info("****** Verify Payment Other Date radio button Error Text greater than current balance  *****");
			softAssert.assertEquals(commonMethods.getTextbyId(webPageMap.get(Thread.currentThread().getId()), testData[3][1], softAssert),testData[4][1],
					"Value less than  Maximum amount or payment due . Expected "+testData[4][1]+" Actual : "+commonMethods.getTextbyId(webPageMap.get(Thread.currentThread().getId()), testData[3][1], softAssert));
			log.info("****** Clear other data Text box value  *****");

			commonMethods.clearTextBox(webPageMap.get(Thread.currentThread().getId()), commonData.get("shedulePaymntOtherAmountTextBoxId"), softAssert);
			
			log.info("****** Click on Submit button  *****");
			commonMethods.clickElementById(webPageMap.get(Thread.currentThread().getId()), commonData.get("shedulePaymntSubmitBtnId"),softAssert);
			
			log.info("****** Verify Payment Other Date radio button Error Text entered null value  *****");
			softAssert.assertEquals(commonMethods.getTextbyId(webPageMap.get(Thread.currentThread().getId()), testData[3][1], softAssert),testData[4][1],
					"Value amount or payment not enterted . Expected "+testData[4][1]+" Actual : "+commonMethods.getTextbyId(webPageMap.get(Thread.currentThread().getId()), testData[3][1], softAssert));
			
			
			
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "content_schedule_payment");
			softAssert.assertAll();
		}
	}
	
	/**
	 * TC_062 to TC_070
	 * @throws Exception
	 */
	@Test(priority = 1003, enabled = true, description = "Verify content / Payment Amount displayed on AccountSummary and SchedulePages")
	public void verify_PaymentAmount_Displayed_AccountSummary_Schedule_Pages() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String ElementXpath = "";
		try {
			
			
			log.info("***** Fetch verifyVerbiageRegisteredUsers data from excel  ********" );
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "BillPayPage","PaymentAmountAccountSummarySchedulePages");

			log.info("***** Click on Back button********");
			ElementXpath = commonData.get("shedulePaymntBackBtnXpath");
			BillPayPage.ClickElementPresenceByJS(webPageMap.get(Thread.currentThread().getId()), ElementXpath, softAssert);
			//commonMethods.clickElementById(webPageMap.get(Thread.currentThread().getId())Map.get(Thread.currentThread().getId()), commonData.get("shedulePaymntSubmitBtnId"),softAssert);
			
		
			/*int currentBalanceAmount = BillPayPageSetUpAutoPay.currentBalanceAmount(webPageMap.get(Thread.currentThread().getId()), AutoPayCurrentBalanceXPath, currentBalance,mychar, softAssert);
			double ExactCurrentBalanceAmount =BillPayPageSetUpAutoPay.ExactCurrentBalanceAmount(webPageMap.get(Thread.currentThread().getId()), AutoPayCurrentBalanceXPath, currentBalance,mychar, softAssert);
			int minDueAmount = BillPayPageSetUpAutoPay.minDueAmount(webPageMap.get(Thread.currentThread().getId()), MinimumAmountDueXpath, minAmountDue, minAmountchar,softAssert);*/
			/*String minAmountDue = "";
			String minAmountchar = "";
			String MinimumAmountDueXpath = commonData.get("minPayXpath");
			int accountSumaryMinPaynt = BillPayPage.minDueAmount(webPageMap.get(Thread.currentThread().getId()), MinimumAmountDueXpath, minAmountDue, minAmountchar,softAssert);*/
			
			
			log.info("***** Verify  Minimum payment amount displayed on Account summary and payment screen  ********" );
			int accountSumaryMinPaynt = billPayPage.getPaymentAmountByXpath(commonData.get("minPayXpath"), webPageMap.get(Thread.currentThread().getId()), softAssert);
			log.info("***** accountSumaryMinPaynt ********" +accountSumaryMinPaynt);
			log.info("***** shedulePaymntMinPayntXPath ********" +shedulePaymntMinPayntXPath);
			softAssert.assertEquals(accountSumaryMinPaynt,shedulePaymntMinPayntXPath,
					"Minimum payment amount displayed on Account summary and payment screen . Expected :  " +shedulePaymntMinPayntXPath +" Actual : "+accountSumaryMinPaynt);
			/*softAssert.assertEquals(accountSumaryMinPaynt,billPayPage.getAmount(commonData.get("shedulePaymntMinPayntXPath"), webPageMap.get(Thread.currentThread().getId()), softAssert),
					"Minimum payment amount displayed on Account summary and payment screen . Expected "+billPayPage.getAmount(commonData.get("shedulePaymntMinPayntXPath"), webPageMap.get(Thread.currentThread().getId()), softAssert)+" Actual : "+accountSumaryMinPaynt);*/
			
			/*softAssert.assertEquals(accountSumaryMinPaynt,BillPayPage.minDueAmount(webPageMap.get(Thread.currentThread().getId()), MinimumAmountDueXpath, minAmountDue, minAmountchar,softAssert),
					"Minimum payment amount displayed on Account summary and payment screen . Expected "+BillPayPage.minDueAmount(webPageMap.get(Thread.currentThread().getId()), MinimumAmountDueXpath, minAmountDue, minAmountchar,softAssert)+" Actual : "+accountSumaryMinPaynt);*/
			
			log.info("***** Verify   current balance  displayed on Account summary and payment screen  ********" );
			int overdueCBAmount = billPayPage.getPaymentAmountByXpath(commonData.get("overdueCBAmountXpath"), webPageMap.get(Thread.currentThread().getId()), softAssert);
			log.info("***** overdueCBAmount ********" +overdueCBAmount);
			log.info("***** shedulePaymntCurrentBalAmtXpath ********" +shedulePaymntCurrentBalAmtXpath);
			softAssert.assertEquals(overdueCBAmount,shedulePaymntCurrentBalAmtXpath,
					" current balance displayed on Account summary and payment screen . Expected : "+shedulePaymntCurrentBalAmtXpath+" Actual : "+overdueCBAmount);
			
			
			/**************************************************************************************************************/
			log.info("****** Click on Pay Bill Button *****");
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("payBillBtnXpath"),softAssert);
			/**************************************************************************************************************/
			
			log.info("***** Select Payment Due Radio button  ********" );
			commonMethods.selectCheckBoxById(webPageMap.get(Thread.currentThread().getId()), commonData.get("shedulePaymntPaymntDueId"), softAssert);
			
			log.info("***** Select Current Balance Radio button  ********" );
			commonMethods.selectCheckBoxById(webPageMap.get(Thread.currentThread().getId()), commonData.get("shedulePaymntCurrentBalId"), softAssert);
			
			String greenMsg = commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("shedulePaymntGreenMsgXpath"), softAssert);
			log.info("Green Message::"+greenMsg);
			log.info("***** Verify Current Balance Green Message  ********" );
			softAssert.assertEquals(greenMsg,testData[0][1],
					"Current Balance Green Message . Expected "+testData[0][1]+" Actual : "+greenMsg);
			
			log.info("***** 1 Verify that error msg is displaying by selecting  a date which past to loan account's monthly due date  ********" );
			String dueDate = commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("shedulePaymntDueDateXpath"), softAssert);
			log.info(" ******** dueDate ******* : "+dueDate);
		/**********************************************************************/	
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Date todayDate = new Date();
			log.info("***** todayDate  ********" +todayDate);
			String mdy = sdf.format(todayDate);
			log.info("***** mdy  ********" +mdy);			
			Date overDueDate = sdf.parse(mdy);
			log.info("***** overDueDate  ********" +overDueDate);
		 	/**********************************************************************/	
	      /*  if(todayDate.before(overDueDate)) {
	  	
				log.info("***** Click on One time payment date  ********" );
				commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("shedulePaymntOnetimePaymentDateId"), softAssert);
				
				log.info("***** Select Today Date  ********" );
				//commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("shedulePaymntSelectTodayXpath"), softAssert);
				ElementXpath = commonData.get("shedulePaymntSelectTodayXpath");				
				BillPayPage.ClickElementPresenceByJS(webPageMap.get(Thread.currentThread().getId()), ElementXpath, softAssert);
	        }*/
				
				/*log.info(" ************* Click on One time payment date *********");
				ElementXpath = commonData.get("shedulePaymntOnetimePaymentDateId");				
				BillPayPage.ClickElementPresenceByJS(webPageMap.get(Thread.currentThread().getId()), ElementXpath, softAssert);
				
				log.info("***** Select Today Date  ********" );
				ElementXpath = commonData.get("shedulePaymntSelectTodayXpath");				
				BillPayPage.ClickElementPresenceByJS(webPageMap.get(Thread.currentThread().getId()), ElementXpath, softAssert);
				//commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("shedulePaymntSelectTodayXpath"), softAssert);
				WebElement searchbox = webPageMap.get(Thread.currentThread().getId()).getDriver().findElement(By.xpath("//*[@id='onetimepayment_select_date']"));				
				js.executeScript("arguments[0].value='todayDate';", searchbox);*/
			log.info("***** Click on One time payment date  ********" );
			ElementXpath = commonData.get("shedulePaymntOnetimePaymentDateId");				
			BillPayPage.ClickElementPresenceByJS(webPageMap.get(Thread.currentThread().getId()), ElementXpath, softAssert);
			
			log.info("***** Click on DateSelector date  ********" );
			ElementXpath = commonData.get("DateSelector");				
			BillPayPage.ClickElementPresenceByJS(webPageMap.get(Thread.currentThread().getId()), ElementXpath, softAssert);			
			log.info("*****  DateSelector Date Clicked Successfully  ********" );

			
			log.info("***** Select Today Date  ********" );
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("shedulePaymntSelectTodayXpath"), softAssert);
	    	log.info("***** 2 Verify that error msg is displaying by selecting  a date which past to loan account's monthly due date  ********" );
			
	    	
	    	String redMsg = commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("shedulePaymntDateRedMsgXptah"), softAssert);
			log.info("Red Message::"+redMsg);
			log.info("***** Verify Current Balance Red Message  ********" );
			softAssert.assertEquals(redMsg,testData[2][1],
					"Current Balance Red Message . Expected "+testData[2][1]+" Actual : "+redMsg);
			
	    	
	    	/*softAssert.assertEquals(commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("shedulePaymntDateRedMsgXptah"), softAssert),testData[2][1],
					"Current Balance Red Message . Expected "+testData[2][1]+" Actual : "+commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("shedulePaymntDateRedMsgXptah"),softAssert));*/
			
			log.info("***** Verify Disclaimers Text ********" );
			String disclaimersText = commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("shedulePaymntDisclaimerXpath"), softAssert);
			String disclaimersRemoveText = disclaimersText.substring(disclaimersText.indexOf("$"), disclaimersText.indexOf("Further")-1);
			disclaimersText = disclaimersText.replace(disclaimersRemoveText, "");
			
			softAssert.assertEquals(disclaimersText,testData[1][1],
					"Disclaimers Text . Expected "+testData[1][1]+" Actual : "+disclaimersText);
			
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "content_schedule_payment");
			softAssert.assertAll();
		}
	}
	
	/**
	 * TC_071 to TC_074
	 * @throws Exception
	 */
	@Test(priority = 1004, enabled = true, description = "Verify content on conformation screen SchedulePages")
	public void verify_Content_Conformation_Screen_Schedule_Page() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String ElementXpath = "";
		try {
			
			log.info("***** Fetch verifyVerbiageRegisteredUsers data from excel  ********" );
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "BillPayPage","ContentOnConformationScreen");
			
			log.info("***** Click-on Credit Summary Link ********" );
			commonMethods.clickElementbyLinkText(webPageMap.get(Thread.currentThread().getId()), commonData.get("creditSummaryLinkText"), softAssert);
			
			log.info("***** Click on Pay Bill Button ********" );
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("payBillBtnXpath"),softAssert);
			
			log.info("***** Click on One time payment date ********" );
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("shedulePaymntOnetimePaymentDateId"), softAssert);
			
			log.info("***** Click on DateSelector date  ********" );
			ElementXpath = commonData.get("DateSelector");				
			BillPayPage.ClickElementPresenceByJS(webPageMap.get(Thread.currentThread().getId()), ElementXpath, softAssert);			
			log.info("*****  DateSelector Date Clicked Successfully  ********" );
			
			
			log.info("***** Select Today Date ********" );
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("shedulePaymntSelectTodayXpath"), softAssert);
			
			log.info("***** Get Min Payment From Schedule payment page ********" );
			/*String minPaymnt = commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("shedulePaymntMinPayntXPath"), softAssert);
			log.info("***** minPaymnt  ********" +minPaymnt);*/
			
			double minPaymnt = billPayPage.getPaymentAmountByXpath_Schedule_Page(commonData.get("shedulePaymntMinPayntXPath"),webPageMap.get(Thread.currentThread().getId()), softAssert);
			log.info("**** minPayment *** *: " +minPaymnt);
			String minPayment = String.valueOf(minPaymnt);
			
			String selDate = (String)js.executeScript("return document.getElementById(\""+commonData.get("ShedulePaymenntOnetimePaymentDateId")+"\").value;");
			log.info("***** selDate  ********" +selDate);
			
			log.info("***** Click on Submit button ********" );
			commonMethods.clickElementById(webPageMap.get(Thread.currentThread().getId()), commonData.get("shedulePaymntSubmitBtnId"),softAssert);
			
			log.info("***** Verify buttons available on Confirmation screen ********" );
			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPageMap.get(Thread.currentThread().getId()), testData[0][1], softAssert),
					"Conformation Pop up screen Confirm Button . ");
			
			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPageMap.get(Thread.currentThread().getId()), testData[1][1], softAssert),
					"Conformation Pop up screen Cancel Button . ");
			
			
			log.info("***** Verify Confirmation screen Header text ********" );
			softAssert.assertEquals(commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[2][1], softAssert),testData[3][1],
					"Confirmation screen Header text . Expected "+commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[2][1], softAssert)+" Actual : "+testData[3][1]);
			log.info("***** Verify pop up message text ********" );
			softAssert.assertEquals(commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[4][1], softAssert),testData[5][1],
					"Confirmation screen pop up message text . Actual :  "+commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[4][1], softAssert)+" Expected : "+testData[4][1]);
			
			
			log.info("***** Verify Pop up header text ********" );
			String headerLabelText = commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[6][1], softAssert);
			softAssert.assertEquals(headerLabelText,testData[7][1],
					"Confirmation screen pop up header message text . Expected "+commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[6][1], softAssert)+" Actual : "+headerLabelText);
			
			log.info("***** Verify message text above buttons ********" );
			softAssert.assertEquals(commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[12][1], softAssert),testData[13][1],
					"Confirmation screen pop up message text above buttons . Expected "+commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[12][1], softAssert)+" Actual : "+testData[13][1]);
			
			log.info("***** Verify Selected Amount ********" );
			softAssert.assertTrue((minPayment).contains(commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[8][1], softAssert)),
					"Verify Selected Amount . Expected "+minPayment+" Actual : "+commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[8][1], softAssert));
			
			log.info("***** Verify payment date ********" );
			softAssert.assertEquals(selDate,commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[10][1], softAssert),
					"Verify Payment Date . Expected "+selDate+" Actual : "+commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[10][1], softAssert));
			
			log.info("***** Click on Confirm button ********" );
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("ConfirmButtonXpath"), softAssert);
			
			log.info("***** Verify success message  ********" );
			softAssert.assertEquals(commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[14][1], softAssert), testData[15][1],
					"Verify success message . Expected "+testData[15][1]+" Actual : "+commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[14][1], softAssert));
			
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "content_schedule_payment");
			softAssert.assertAll();
		}
	}
	
	/**
	 * TC_079
	 * @throws Exception
	 */
	@Test(priority = 1005, enabled = true, description = "Verify One Time Payment Confirmation Font properties")
	public void verify_Payment_Conformation_Page_FontProperties() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			//Verify Important Notice
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "BillPayPage",
					"OneTimePaymentConfirmation");
			
			for(int i=0;i<4;i++) {
				webPageMap.get(Thread.currentThread().getId()).sleep(10000);
					String fontSize = commonMethods.getCssvaluebyXpath(webPageMap.get(Thread.currentThread().getId()), testData[i][1], "font-size", softAssert);
					String fontColor = commonMethods.getCssvaluebyXpath(webPageMap.get(Thread.currentThread().getId()), testData[i][1], "color", softAssert);
					String fontFamily = commonMethods.getCssvaluebyXpath(webPageMap.get(Thread.currentThread().getId()), testData[i][1], "font-family", softAssert);
					String fontWeight = commonMethods.getCssvaluebyXpath(webPageMap.get(Thread.currentThread().getId()), testData[i][1], "font-weight", softAssert);
					String backgroundColor = commonMethods.getCssvaluebyXpath(webPageMap.get(Thread.currentThread().getId()), testData[i][1], "background-color", softAssert);
					
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
	
	/**
	 * TC_078
	 * @throws Exception
	 */
	@Test(priority = 1006, enabled = true, description = "Verify Account summary page Font properties")
	public void verify_LinkYour_Account_Page_FontProperties() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			//Verify Important Notice
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "BillPayPage",
					"VerifyAccountSummaryFontProperties");
			
			for(int i=0;i<12;i++) {
				webPageMap.get(Thread.currentThread().getId()).sleep(10000);
					String fontSize = commonMethods.getCssvaluebyXpath(webPageMap.get(Thread.currentThread().getId()), testData[i][1], "font-size", softAssert);
					String fontColor = commonMethods.getCssvaluebyXpath(webPageMap.get(Thread.currentThread().getId()), testData[i][1], "color", softAssert);
					String fontFamily = commonMethods.getCssvaluebyXpath(webPageMap.get(Thread.currentThread().getId()), testData[i][1], "font-family", softAssert);
					String fontWeight = commonMethods.getCssvaluebyXpath(webPageMap.get(Thread.currentThread().getId()), testData[i][1], "font-weight", softAssert);
					String backgroundColor = commonMethods.getCssvaluebyXpath(webPageMap.get(Thread.currentThread().getId()), testData[i][1], "background-color", softAssert);
					
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
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_account_summary_font");
			softAssert.assertAll();
		}
	}
}
