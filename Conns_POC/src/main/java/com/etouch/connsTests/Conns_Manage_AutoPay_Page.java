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
import org.openqa.selenium.Alert;
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
import com.etouch.connsPages.ConnsMainPage;
import com.etouch.connsPages.CreditAppPage;
import com.etouch.connsPages.ManageAutoPayPage;
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
public class Conns_Manage_AutoPay_Page extends BaseTest {
	private String testBedName;
	TestBed testBed;
	Path path;
	protected static String DataFilePath;
	protected String testType, browserName;
	String currentTestBedName;
	static Log log = LogUtil.getLog(Conns_Manage_AutoPay_Page.class);
	Logger logger = Logger.getLogger(Conns_Manage_AutoPay_Page.class.getName());
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
				commonData = CommonMethods.getDataInHashMap(DataFilePath, "ManageAutoPayPage","ManageAutoPayCommonElements");
				
				platform = testBed.getPlatform().getName().toUpperCase();
				url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL();
				synchronized (this) {
					manageAutoPayPage = new ManageAutoPayPage();
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
	 * TC_115 to TC_118
	 * @throws Exception
	 */
	@Test(priority = 1001, enabled = true, description = "Verify functionality for Manage Auto Pay account")
	public void verify_Functionality_ManageAutoPay_Account() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "ManageAutoPayPage","FunctionalityManageAccount");
			
			//Click on Sign In link
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("signInLinkXPath"),softAssert);
			
			//Click on Pay your Bill header link
			manageAutoPayPage.connsLogin(testData[0][1], testData[1][1], webPage, softAssert);
			
			//Click-on Credit Summary Link
			commonMethods.clickElementbyLinkText(webPage, commonData.get("creditSummaryLinkText"), softAssert);	
			
			String greenText = commonMethods.getTextbyXpath(webPage, testData[3][1], softAssert);
			
			greenText = greenText.replace(greenText.substring(greenText.indexOf("$"),greenText.indexOf("$")+7),"");
			
			greenText = greenText.replace(greenText.substring(greenText.indexOf("date")+7,greenText.length()),"");
						
			//Verify the verbiage displayed in Green color
			softAssert.assertEquals(greenText.trim(),testData[4][1],
					"Verbiage displayed Text . Expected "+testData[4][1]+" Actual : "+greenText.trim());
			
			//Click on Manage Auto Pay button
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[2][1],softAssert);
			
			//Verify Modify Auto pay buttons available on screen
			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, commonData.get("modifyAutoPayBtnXpath"), softAssert),
					"Modify Auto pay Button . ");
			
			//Verify Cancel Auto pay buttons available on screen
			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, commonData.get("CancelAutoPayBtnXpath"), softAssert),
					"cancel Auto pay Button . ");
			
			//Verify Modify Auto pay buttons available on screen
			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, commonData.get("BackButtonXPath"), softAssert),
					"Modify Auto pay Button . ");
			
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "content_functionality_manage_autopay_account");
			softAssert.assertAll();
		}
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1002, enabled = true, description = "Verify One Time Payment Confirmation Font properties")
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
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_onetime_paynt_font");
			softAssert.assertAll();
		}
	}
	
	/**
	 * TC_121 to TC_125
	 * @throws Exception
	 */
	@Test(priority = 1003, enabled = true, description = "Verify content of manage auto pay page")
	public void verify_Content_ManageAutoPay_Account() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "ManageAutoPayPage","ContentManageAutoPayAccount");
			//Click on Back button
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("BackButtonXPath"),softAssert);
			String paymentDue = commonMethods.getTextbyXpath(webPage, testData[0][1], softAssert);
			int minPayment = manageAutoPayPage.getPaymentAmountByXpath(testData[1][1],webPage, softAssert);
			
			//Click on Modify auto pay link
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("manageAutoPayBtnXpath"),softAssert);
			
			//Verify Modify Auto pay buttons available on screen
			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, commonData.get("modifyAutoPayBtnXpath"), softAssert),
					"Modify Auto pay Button . ");
			
			//Verify Cancel Auto pay buttons available on screen
			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, commonData.get("CancelAutoPayBtnXpath"), softAssert),
					"cancel Auto pay Button . ");
			
			//Verify Back buttons available on screen
			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, commonData.get("BackButtonXPath"), softAssert),
					"Back Auto pay Button . ");
			
			
			//Click on Modify Auto Pay button
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("modifyAutoPayBtnXpath"),softAssert);
			
			//Verify One Time payment tab  available on screen
			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, testData[14][1], softAssert),
					"One time payment Button .");
			
			String minpaymentSetupPage = commonMethods.getTextbyXpath(webPage, testData[2][1], softAssert);
			//Verify payment due on Account summary and Setup auto pay
			softAssert.assertEquals(paymentDue,minpaymentSetupPage,
					"payment due on Account summary and Setup auto pay . Expected "+minpaymentSetupPage+" Actual : "+paymentDue);
			
			//Verify min payment  on Account summary and Setup auto pay
			softAssert.assertEquals(minPayment,manageAutoPayPage.getPaymentAmountByXpath(testData[3][1],webPage, softAssert),
					"Minimum payment on Account summary and Setup auto pay . Expected "+manageAutoPayPage.getPaymentAmountByXpath(testData[3][1],webPage, softAssert)+" Actual : "+minPayment);
			
			//Click on Submit button
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[13][1],softAssert);
			
			//Verify View payment plan and modify auto pay screen
			softAssert.assertEquals(minPayment,manageAutoPayPage.getPaymentAmountByXpath(testData[6][1],webPage, softAssert),
					"View payment plan and modify auto pay screen . Expected "+manageAutoPayPage.getPaymentAmountByXpath(testData[6][1],webPage, softAssert)+" Actual : "+minpaymentSetupPage);
			
			//Verify Frequency and Due date
			String freDueDate =  commonMethods.getTextbyXpath(webPage,testData[7][1], softAssert)+","+commonMethods.getTextbyXpath(webPage,testData[8][1], softAssert);
			softAssert.assertEquals(freDueDate,testData[10][1],
					"Frequency and Due date Text. Expected "+testData[10][1]+" Actual : "+freDueDate);
			
			//Verify cancel link on Pop up / Auto pay screen
			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, testData[11][1], softAssert),
					"cancel link on Pop up / Auto pay screen . ");
			String headersText="";
			for (WebElement element : commonMethods.getWebElementsbyXpath(webPage, testData[5][1], softAssert)) {
				headersText=headersText+","+element.getText();
			}
			//Verify Auto pay screen header text
			softAssert.assertEquals(headersText,testData[12][1],
					"Auto pay screen header text. Expected "+testData[12][1]+" Actual : "+headersText);
			
			//Click on Confirm button
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[9][1],softAssert);

			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "content_content_manage_autopay_account");
			softAssert.assertAll();
		}
	}
	
	/**
	 * 
	 */
	@Test(priority = 1004, enabled = true, description = "Verify Font size, style of view payment plan page")
	public void verify_PaymentPlan_Page_FontProperties() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			//Verify Important Notice
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "PayYourBill",
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
	
	/**
	 * TC_131 to TC_134
	 * @throws Exception
	 */
	@Test(priority = 1005, enabled = true, description = "Verify clicking on cancel button")
	public void verify_Functionality_Cancel_Button() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "ManageAutoPayPage","CancelButtonFunctionality");
			
			//Click on Back button
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("BackButtonXPath"),softAssert);
			
			//Click on Modify auto pay link
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("manageAutoPayBtnXpath"),softAssert);
			
			//Click on Cancel auto pay button
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("CancelAutoPayBtnXpath"),softAssert);
			
			
			//Cancel confirmation popup should be displayed
			softAssert.assertEquals(commonMethods.getTextbyXpath(webPage, testData[10][1], softAssert),testData[1][1],
					"Cancel confirmation popup header Text. Expected "+testData[1][1]+" Actual : "+commonMethods.getTextbyXpath(webPage, testData[10][1], softAssert));
			
			softAssert.assertEquals(commonMethods.getTextbyXpath(webPage, testData[11][1], softAssert),testData[12][1],
					"Cancel confirmation popup para Text. Expected "+testData[12][1]+" Actual : "+commonMethods.getTextbyXpath(webPage, testData[11][1], softAssert));
			
			//Verify close link on Cancel confirmation popup
			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, testData[0][1], softAssert),
					"Close link on Cancel confirmation popup. ");
			
			//Click on Cancel auto pay button  on Cancel confirmation popup
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), testData[2][1],softAssert);
			
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
