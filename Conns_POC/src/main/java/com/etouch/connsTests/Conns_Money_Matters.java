package com.etouch.connsTests;

import java.awt.AWTException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.etouch.common.BaseTest;
import com.etouch.common.CommonMethods;
import com.etouch.common.TafExecutor;
import com.etouch.connsPages.ConnsHomePage;
import com.etouch.connsPages.ConnsMainPage;
import com.etouch.connsPages.ConnsMoneyMatters;
import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.datamanager.excel.annotations.IExcelDataFiles;
import com.etouch.taf.core.driver.web.WebDriver;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.tools.rally.VideoRecorder;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.taf.util.TafPassword;
import com.etouch.taf.webui.selenium.MobileView;
import com.etouch.taf.webui.selenium.WebPage;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;

@IExcelDataFiles(excelDataFiles = { "ConnsMoneyMattersData=testData" })
public class Conns_Money_Matters extends BaseTest {

	/** The url3. */
	String url3 = TestBedManager.INSTANCE.getDefectConfig().getUrl3();

	/** The issue url. */
	String issueUrl = TestBedManager.INSTANCE.getDefectConfig().getIssueUrl();

	/** The username. */
	String username = TestBedManager.INSTANCE.getDefectConfig().getUsername();

	/** The password. */
	TafPassword password = TestBedManager.INSTANCE.getDefectConfig().getPassword();

	/** The keys. */
	String keys = TestBedManager.INSTANCE.getDefectConfig().getKeys();

	// required for rally
	/** The Constant DEFECT_PROP. */

	// required for rally
	/** The Constant DEFECT_PROP. */

	/** The project id. */
	String PROJECT_ID = TestBedManager.INSTANCE.getDefectConfig().getProjectId();

	/** The defect owner. */
	String DEFECT_OWNER = TestBedManager.INSTANCE.getDefectConfig().getDefectOwner();

	/** The workspace id. */
	String WORKSPACE_ID = TestBedManager.INSTANCE.getDefectConfig().getWorkspaceId();
	VideoRecorder videoRecorder = null;
	AppiumDriver driver = null;
	MobileView mobileView = null;
	private ConnsMainPage mainPage;
	TouchAction act1;
	DesiredCapabilities capabilities;

	Properties props;
	String deviceName;
	String plateformVersion;
	String platformName;
	String udid;
	String appPackage;
	String appActivity;
	String appiumHost;
	String appiumPortNumber;
	String apkPath;
	private String testBedName;
	TestBed testBed;
	Path path;
	String DataFilePath;
	String testType;
	String testEnv;
	String browserName;
	CommonMethods commonMethods;
	ConnsMoneyMatters ConnsMoneyMatters;
	
	int j = 0;

	static Log log = LogUtil.getLog(Conns_Money_Matters.class);
	Logger logger = Logger.getLogger(Conns_Money_Matters.class.getName());
	private final int MAX_WAIT = 20;

	private String url;
	private String PageUrl = "";
	private WebPage webPage;

	static String platform;
	static String AbsolutePath = TafExecutor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	static String videoLocation = AbsolutePath.substring(0, AbsolutePath.indexOf("/target/classes/")).substring(1)
			.concat("/src/test/resources/testdata/videos");

	String moneyMattersURL="";
	String moneyMattersCommonElement_Web="";
	String[][] commonData;
	
	
	/**
	 * Prepare before class.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@BeforeClass(alwaysRun = true)
	public void setUp(ITestContext context) throws InterruptedException, FileNotFoundException, IOException {
		try {
			testBedName = context.getCurrentXmlTest().getAllParameters().get("testBedName");
			CommonUtil.sop("Test bed Name is " + testBedName);
			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			browserName = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getBrowser().getName();
			commonMethods = new CommonMethods();
			System.out.println("Test Type is : " + testType);
			try {
				testEnv = System.getenv().get("Environment");
				System.out.println("testEnv is : " + System.getenv().get("Environment"));
				path = Paths.get(TestBedManager.INSTANCE.getProfile().getXlsDataConfig().get("testData"));
				DataFilePath = path.toAbsolutePath().toString().replace("Env", testEnv);
				System.out.println("DataFilePath After is : " + DataFilePath);
				commonData = ExcelUtil.readExcelData(DataFilePath, "MoneyMatters", "MoneyMattersCommonElements");
				moneyMattersURL=commonData[0][1];
				moneyMattersCommonElement_Web=commonData[1][1];

				platform = testBed.getPlatform().getName().toUpperCase();
				if (testType.equalsIgnoreCase("Web")) {
					System.out.println("videoLocation" + videoLocation.toString().replace("Env", testEnv));
				}
				url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL();
				synchronized (this) {

					webPage = new WebPage(context);
					ConnsMoneyMatters = new ConnsMoneyMatters(url, webPage);
					System.out.println("Conns onject" + ConnsMoneyMatters);
					mainPage = new ConnsMainPage(url, webPage);
					System.out.println(mainPage);					
				}
				if (testType.equalsIgnoreCase("Web")) {				
				webPage.getDriver().manage().window().maximize();
				}				

			} catch (Exception e) {
				log.info("errr is " + e);
				SoftAssertor.addVerificationFailure(e.getMessage());
			}
		}

		catch (Exception e) {

			CommonUtil.sop("errr is for" + testBedName + " -----------" + e);
			SoftAssertor.addVerificationFailure(e.getMessage());
		}
	}

	@AfterTest
	public void releaseResources() throws IOException, AWTException {
		webPage.getDriver().quit();
	}

	/**
	 * Test Case - 601 - Verify title and URL of page
	 * 
	 */

	@SuppressWarnings("static-access")
	@Test(priority = 601, enabled = true, description = "Verify MoneyMatters Page title")
	public void Verify_MoneyMatters_PageTitle() 
	{
		
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "MoneyMatters", "Verifytitle");
			String ExpectedTitle = test[0][1];
			commonMethods.navigateToPage(webPage,moneyMattersURL, softAssert);				
			if (testType.equalsIgnoreCase("Web")) 
			{			
				commonMethods.waitForWebElement(By.xpath(moneyMattersCommonElement_Web), webPage);
			}
			
			log.info(webPage.getPageTitle());
			softAssert.assertEquals(webPage.getPageTitle(), ExpectedTitle,
					"Page Title verification failed. Expected title - " + ExpectedTitle + " Actual title - " + webPage.getPageTitle());
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_MoneyMatters_PageTitle");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	
	/**
	 * Test Case - 602 - Verify broken images on page
	 * 
	 */

	@SuppressWarnings("static-access")
	@Test(priority = 602, enabled = true, description = "Verify_Broken_Images")
	public void Verify_Broken_Images() throws ClientProtocolException, IOException 
	{		
		SoftAssert softAssert = new SoftAssert();
		try{	
			commonMethods.navigateToPage(webPage,moneyMattersURL, softAssert);	
			if (testType.equalsIgnoreCase("Web")) 
			{			
				commonMethods.waitForWebElement(By.xpath(moneyMattersCommonElement_Web), webPage);
			}	
			commonMethods.verifyBrokenImage(webPage);
			softAssert.assertAll();
		} catch (Throwable e) {
			//e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_Broken_Images");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}	
	}
	
	
	/**
	 * Test Case - 603 - Verify broken links on page
	 * 
	 */

	@SuppressWarnings("static-access")
	@Test(priority = 603, enabled = true, description = "Verify_Broken_Links")
	public void Verify_Broken_Links() throws ClientProtocolException, IOException {
		SoftAssert softAssert = new SoftAssert();
		try{
			if (testType.equalsIgnoreCase("Web")) 
			{				
				commonMethods.navigateToPage(webPage,moneyMattersURL, softAssert);			
				commonMethods.waitForWebElement(By.xpath(moneyMattersCommonElement_Web), webPage);
				commonMethods.verifyBrokenLinks(webPage);
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			//e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_Broken_Links");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	
	/**
	 * Test Case - 604 - Verify Font Size and Style of specified on element on
	 * 
	 */	
	
	@SuppressWarnings("static-access")
	@Test(priority = 604, enabled = true, description = "Verify_Font_And_Size")
	public void Verify_Font_And_Size() 
	{		
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] ExpectedFontValuesWeb = ExcelUtil.readExcelData(DataFilePath, "MoneyMatters","VerifyFontandSizeWeb");
			String[][] ExpectedFontValuesTab = ExcelUtil.readExcelData(DataFilePath, "MoneyMatters","VerifyFontandSizeTab");
			String[][] ExpectedFontValuesMobile = ExcelUtil.readExcelData(DataFilePath, "MoneyMatters","VerifyFontandSizeMobile");
			
			commonMethods.navigateToPage(webPage,moneyMattersURL, softAssert);	
			
			if (testType.equalsIgnoreCase("Web")) 
			{			
				commonMethods.waitForWebElement(By.xpath(moneyMattersCommonElement_Web), webPage);
			}				
			
			if (testType.equalsIgnoreCase("Web")) 
			{
				for (int i = 0; i < ExpectedFontValuesWeb.length; i++) 
				{
					System.out.println("Iteration under test  is : " + i + " :: Item under test is : " + ExpectedFontValuesWeb[i][0]);
					List<String> actualCssValues = commonMethods.getFontProperties(webPage, ExpectedFontValuesWeb[i][1],softAssert);
					if(!ExpectedFontValuesWeb[i][2].equalsIgnoreCase("NA")){
						log.info("expected : " + ExpectedFontValuesWeb[i][2]);
						log.info("actual   : " + actualCssValues.get(0));
						log.info("match status : " + actualCssValues.get(0).contains(ExpectedFontValuesWeb[i][2]));
						softAssert.assertTrue(actualCssValues.get(0).contains(ExpectedFontValuesWeb[i][2]),"Iteration : " + i +  " --  CSS value verification failed for " + ExpectedFontValuesWeb[i][0]+ ".. Expected font Size: " + ExpectedFontValuesWeb[i][2] + ", Actual Font Size: "+ actualCssValues.get(0) + "\n");	
					}
					if(!ExpectedFontValuesWeb[i][3].equalsIgnoreCase("NA")){
						log.info("expected : " + ExpectedFontValuesWeb[i][3]);
						log.info("actual   : " + actualCssValues.get(1));
						log.info("match status : " + actualCssValues.get(1).contains(ExpectedFontValuesWeb[i][3]));						
						softAssert.assertTrue(actualCssValues.get(1).contains(ExpectedFontValuesWeb[i][3]),"Iteration : " + i +  " --  CSS value verification failed for " + ExpectedFontValuesWeb[i][0]+ ".. Expected font Size: " + ExpectedFontValuesWeb[i][3] + ", Actual Font Color: "+ actualCssValues.get(1) + "\n");													
					}if(!ExpectedFontValuesWeb[i][4].equalsIgnoreCase("NA")){
						log.info("expected : " + ExpectedFontValuesWeb[i][4]);
						log.info("actual   : " + actualCssValues.get(2));
						log.info("match status : " + actualCssValues.get(2).contains(ExpectedFontValuesWeb[i][4]));							
						softAssert.assertTrue(actualCssValues.get(2).contains(ExpectedFontValuesWeb[i][4]),"Iteration : " + i +  " --  CSS value verification failed for " + ExpectedFontValuesWeb[i][0]+ ".. Expected font Size: " + ExpectedFontValuesWeb[i][4] + ", Actual Font Family: "+ actualCssValues.get(2) + "\n");													
					}
					if(!ExpectedFontValuesWeb[i][5].equalsIgnoreCase("NA")){
						log.info("expected : " + ExpectedFontValuesWeb[i][5]);
						log.info("actual   : " + actualCssValues.get(3));
						log.info("match status : " + actualCssValues.get(3).contains(ExpectedFontValuesWeb[i][5]));							
						softAssert.assertTrue(actualCssValues.get(3).contains(ExpectedFontValuesWeb[i][5]),"Iteration : " + i +  " --  CSS value verification failed for " + ExpectedFontValuesWeb[i][0]+ ".. Expected font Size: " + ExpectedFontValuesWeb[i][5] + ", Actual Font Weight: "+ actualCssValues.get(3) + "\n");	
					}
					if(!ExpectedFontValuesWeb[i][6].equalsIgnoreCase("NA")){
						log.info("expected : " + ExpectedFontValuesWeb[i][6]);
						log.info("actual   : " + actualCssValues.get(4));
						log.info("match status : " + actualCssValues.get(4).contains(ExpectedFontValuesWeb[i][6]));							
						softAssert.assertTrue(actualCssValues.get(4).contains(ExpectedFontValuesWeb[i][6]),"Iteration : " + i +  " --  CSS value verification failed for " + ExpectedFontValuesWeb[i][0]+ ".. Expected font Size: " + ExpectedFontValuesWeb[i][6] + ", Actual Background Color: "+ actualCssValues.get(4) + "\n");	
					}
					if(!ExpectedFontValuesWeb[i][7].equalsIgnoreCase("NA")){
						log.info("expected : " + ExpectedFontValuesWeb[i][7]);
						log.info("actual   : " + actualCssValues.get(5));
						log.info("match status : " + actualCssValues.get(5).contains(ExpectedFontValuesWeb[i][7]));							
						softAssert.assertTrue(actualCssValues.get(5).contains(ExpectedFontValuesWeb[i][7]),"Iteration : " + i +  " --  CSS value verification failed for " + ExpectedFontValuesWeb[i][0]+ ".. Expected font Size: " + ExpectedFontValuesWeb[i][7] + ", Actual Test Allign: "+ actualCssValues.get(5) + "\n");	
					}
					if(!ExpectedFontValuesWeb[i][8].equalsIgnoreCase("NA")){
						log.info("expected : " + ExpectedFontValuesWeb[i][8]);
						log.info("actual   : " + actualCssValues.get(6));
						log.info("match status : " + actualCssValues.get(6).contains(ExpectedFontValuesWeb[i][8]));							
						softAssert.assertTrue(actualCssValues.get(6).contains(ExpectedFontValuesWeb[i][8]),"Iteration : " + i +  " --  CSS value verification failed for " + ExpectedFontValuesWeb[i][0]+ ".. Expected font Size: " + ExpectedFontValuesWeb[i][8] + ", Actual Text Transform: "+ actualCssValues.get(6) + "\n");	
					}
				}
			}
			
			if (testType.equalsIgnoreCase("Mobile")) 
			{
				if(deviceName.contains("Tab"))
				{				
					for (int i = 0; i < ExpectedFontValuesTab.length; i++) 
					{
						System.out.println("Iteration under test  is : " + i + " :: Item under test is : " + ExpectedFontValuesTab[i][0]);
						List<String> actualCssValues = commonMethods.getFontProperties(webPage, ExpectedFontValuesTab[i][1],softAssert);
						if(!ExpectedFontValuesTab[i][2].equalsIgnoreCase("NA")){
							log.info("expected : " + ExpectedFontValuesTab[i][2]);
							log.info("actual   : " + actualCssValues.get(0));
							log.info("match status : " + actualCssValues.get(0).contains(ExpectedFontValuesTab[i][2]));
							softAssert.assertTrue(actualCssValues.get(0).contains(ExpectedFontValuesTab[i][2]),"Iteration : " + i +  " --  CSS value verification failed for " + ExpectedFontValuesTab[i][0]+ ".. Expected font Size: " + ExpectedFontValuesTab[i][2] + ", Actual Font Size: "+ actualCssValues.get(0) + "\n");	
						}
						if(!ExpectedFontValuesTab[i][3].equalsIgnoreCase("NA")){
							log.info("expected : " + ExpectedFontValuesTab[i][3]);
							log.info("actual   : " + actualCssValues.get(1));
							log.info("match status : " + actualCssValues.get(1).contains(ExpectedFontValuesTab[i][3]));						
							softAssert.assertTrue(actualCssValues.get(1).contains(ExpectedFontValuesTab[i][3]),"Iteration : " + i +  " --  CSS value verification failed for " + ExpectedFontValuesTab[i][0]+ ".. Expected font Size: " + ExpectedFontValuesTab[i][3] + ", Actual Font Color: "+ actualCssValues.get(1) + "\n");													
						}if(!ExpectedFontValuesTab[i][4].equalsIgnoreCase("NA")){
							log.info("expected : " + ExpectedFontValuesTab[i][4]);
							log.info("actual   : " + actualCssValues.get(2));
							log.info("match status : " + actualCssValues.get(2).contains(ExpectedFontValuesTab[i][4]));							
							softAssert.assertTrue(actualCssValues.get(2).contains(ExpectedFontValuesTab[i][4]),"Iteration : " + i +  " --  CSS value verification failed for " + ExpectedFontValuesTab[i][0]+ ".. Expected font Size: " + ExpectedFontValuesTab[i][4] + ", Actual Font Family: "+ actualCssValues.get(2) + "\n");													
						}
						if(!ExpectedFontValuesTab[i][5].equalsIgnoreCase("NA")){
							log.info("expected : " + ExpectedFontValuesTab[i][5]);
							log.info("actual   : " + actualCssValues.get(3));
							log.info("match status : " + actualCssValues.get(3).contains(ExpectedFontValuesTab[i][5]));							
							softAssert.assertTrue(actualCssValues.get(3).contains(ExpectedFontValuesTab[i][5]),"Iteration : " + i +  " --  CSS value verification failed for " + ExpectedFontValuesTab[i][0]+ ".. Expected font Size: " + ExpectedFontValuesTab[i][5] + ", Actual Font Weight: "+ actualCssValues.get(3) + "\n");	
						}
						if(!ExpectedFontValuesTab[i][6].equalsIgnoreCase("NA")){
							log.info("expected : " + ExpectedFontValuesTab[i][6]);
							log.info("actual   : " + actualCssValues.get(4));
							log.info("match status : " + actualCssValues.get(4).contains(ExpectedFontValuesTab[i][6]));							
							softAssert.assertTrue(actualCssValues.get(4).contains(ExpectedFontValuesTab[i][6]),"Iteration : " + i +  " --  CSS value verification failed for " + ExpectedFontValuesTab[i][0]+ ".. Expected font Size: " + ExpectedFontValuesTab[i][6] + ", Actual Background Color: "+ actualCssValues.get(4) + "\n");	
						}
						if(!ExpectedFontValuesTab[i][7].equalsIgnoreCase("NA")){
							log.info("expected : " + ExpectedFontValuesTab[i][7]);
							log.info("actual   : " + actualCssValues.get(5));
							log.info("match status : " + actualCssValues.get(5).contains(ExpectedFontValuesTab[i][7]));							
							softAssert.assertTrue(actualCssValues.get(5).contains(ExpectedFontValuesTab[i][7]),"Iteration : " + i +  " --  CSS value verification failed for " + ExpectedFontValuesTab[i][0]+ ".. Expected font Size: " + ExpectedFontValuesTab[i][7] + ", Actual Test Allign: "+ actualCssValues.get(5) + "\n");	
						}
						if(!ExpectedFontValuesTab[i][8].equalsIgnoreCase("NA")){
							log.info("expected : " + ExpectedFontValuesTab[i][8]);
							log.info("actual   : " + actualCssValues.get(6));
							log.info("match status : " + actualCssValues.get(6).contains(ExpectedFontValuesTab[i][8]));							
							softAssert.assertTrue(actualCssValues.get(6).contains(ExpectedFontValuesTab[i][8]),"Iteration : " + i +  " --  CSS value verification failed for " + ExpectedFontValuesTab[i][0]+ ".. Expected font Size: " + ExpectedFontValuesTab[i][8] + ", Actual Text Transform: "+ actualCssValues.get(6) + "\n");	
						}
					}
				}else
				{
					for (int i = 0; i < ExpectedFontValuesMobile.length; i++) 
					{
						System.out.println("Iteration under test  is : " + i + " :: Item under test is : " + ExpectedFontValuesMobile[i][0]);
						List<String> actualCssValues = commonMethods.getFontProperties(webPage, ExpectedFontValuesMobile[i][1],softAssert);
						if(!ExpectedFontValuesMobile[i][2].equalsIgnoreCase("NA")){
							log.info("expected : " + ExpectedFontValuesMobile[i][2]);
							log.info("actual   : " + actualCssValues.get(0));
							log.info("match status : " + actualCssValues.get(0).contains(ExpectedFontValuesMobile[i][2]));
							softAssert.assertTrue(actualCssValues.get(0).contains(ExpectedFontValuesMobile[i][2]),"Iteration : " + i +  " --  CSS value verification failed for " + ExpectedFontValuesMobile[i][0]+ ".. Expected font Size: " + ExpectedFontValuesMobile[i][2] + ", Actual Font Size: "+ actualCssValues.get(0) + "\n");	
						}
						if(!ExpectedFontValuesMobile[i][3].equalsIgnoreCase("NA")){
							log.info("expected : " + ExpectedFontValuesMobile[i][3]);
							log.info("actual   : " + actualCssValues.get(1));
							log.info("match status : " + actualCssValues.get(1).contains(ExpectedFontValuesMobile[i][3]));						
							softAssert.assertTrue(actualCssValues.get(1).contains(ExpectedFontValuesMobile[i][3]),"Iteration : " + i +  " --  CSS value verification failed for " + ExpectedFontValuesMobile[i][0]+ ".. Expected font Size: " + ExpectedFontValuesMobile[i][3] + ", Actual Font Color: "+ actualCssValues.get(1) + "\n");													
						}if(!ExpectedFontValuesMobile[i][4].equalsIgnoreCase("NA")){
							log.info("expected : " + ExpectedFontValuesMobile[i][4]);
							log.info("actual   : " + actualCssValues.get(2));
							log.info("match status : " + actualCssValues.get(2).contains(ExpectedFontValuesMobile[i][4]));							
							softAssert.assertTrue(actualCssValues.get(2).contains(ExpectedFontValuesMobile[i][4]),"Iteration : " + i +  " --  CSS value verification failed for " + ExpectedFontValuesMobile[i][0]+ ".. Expected font Size: " + ExpectedFontValuesMobile[i][4] + ", Actual Font Family: "+ actualCssValues.get(2) + "\n");													
						}
						if(!ExpectedFontValuesMobile[i][5].equalsIgnoreCase("NA")){
							log.info("expected : " + ExpectedFontValuesMobile[i][5]);
							log.info("actual   : " + actualCssValues.get(3));
							log.info("match status : " + actualCssValues.get(3).contains(ExpectedFontValuesMobile[i][5]));							
							softAssert.assertTrue(actualCssValues.get(3).contains(ExpectedFontValuesMobile[i][5]),"Iteration : " + i +  " --  CSS value verification failed for " + ExpectedFontValuesMobile[i][0]+ ".. Expected font Size: " + ExpectedFontValuesMobile[i][5] + ", Actual Font Weight: "+ actualCssValues.get(3) + "\n");	
						}
						if(!ExpectedFontValuesMobile[i][6].equalsIgnoreCase("NA")){
							log.info("expected : " + ExpectedFontValuesMobile[i][6]);
							log.info("actual   : " + actualCssValues.get(4));
							log.info("match status : " + actualCssValues.get(4).contains(ExpectedFontValuesMobile[i][6]));							
							softAssert.assertTrue(actualCssValues.get(4).contains(ExpectedFontValuesMobile[i][6]),"Iteration : " + i +  " --  CSS value verification failed for " + ExpectedFontValuesMobile[i][0]+ ".. Expected font Size: " + ExpectedFontValuesMobile[i][6] + ", Actual Background Color: "+ actualCssValues.get(4) + "\n");	
						}
						if(!ExpectedFontValuesMobile[i][7].equalsIgnoreCase("NA")){
							log.info("expected : " + ExpectedFontValuesMobile[i][7]);
							log.info("actual   : " + actualCssValues.get(5));
							log.info("match status : " + actualCssValues.get(5).contains(ExpectedFontValuesMobile[i][7]));							
							softAssert.assertTrue(actualCssValues.get(5).contains(ExpectedFontValuesMobile[i][7]),"Iteration : " + i +  " --  CSS value verification failed for " + ExpectedFontValuesMobile[i][0]+ ".. Expected font Size: " + ExpectedFontValuesMobile[i][7] + ", Actual Test Allign: "+ actualCssValues.get(5) + "\n");	
						}
						if(!ExpectedFontValuesMobile[i][8].equalsIgnoreCase("NA")){
							log.info("expected : " + ExpectedFontValuesMobile[i][8]);
							log.info("actual   : " + actualCssValues.get(6));
							log.info("match status : " + actualCssValues.get(6).contains(ExpectedFontValuesMobile[i][8]));							
							softAssert.assertTrue(actualCssValues.get(6).contains(ExpectedFontValuesMobile[i][8]),"Iteration : " + i +  " --  CSS value verification failed for " + ExpectedFontValuesMobile[i][0]+ ".. Expected font Size: " + ExpectedFontValuesMobile[i][8] + ", Actual Text Transform: "+ actualCssValues.get(6) + "\n");	
						}
					}					
				}
			}			
			
			softAssert.assertAll();

		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_Font_And_Size");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	
	
	/**
	 * Test Case - 605 - Verify Text of specified on element on
	 * 
	 */	
	
	@SuppressWarnings("static-access")
	@Test(priority = 605, enabled = true, description = "Verify_MoneyMatters_Text")
	public void Verify_MoneyMatters_Text() {
		SoftAssert softAssert = new SoftAssert();
		String actualTextValues = null;
		try {
			commonMethods.navigateToPage(webPage,moneyMattersURL, softAssert);	
			
			if (testType.equalsIgnoreCase("Web")) 
			{
				commonMethods.waitForWebElement(By.xpath(moneyMattersCommonElement_Web), webPage);
			}	
			
			String[][] ExpectedValues = ExcelUtil.readExcelData(DataFilePath, "MoneyMatters",
					"VerifyFontandSize");
			System.out.println("Length : " + ExpectedValues.length);
			for (int i = 0; i < ExpectedValues.length; i++) 
			{		
				log.info("Value of i : " + i);						
				actualTextValues = commonMethods.getTextbyXpath(webPage, ExpectedValues[i][1],
						softAssert);
				if (testType.equalsIgnoreCase("Mobile")) 
				{									
					softAssert.assertTrue(actualTextValues.toLowerCase().trim().contains((ExpectedValues[i][0]).toLowerCase().trim()),
							"Text value verification failed for element " + ExpectedValues[i][0]
									+ "Expected Text : " + ExpectedValues[i][0] + " Actual Text : "
									+ actualTextValues);
				} else 
				{
					softAssert.assertTrue(actualTextValues.toLowerCase().contains((ExpectedValues[i][0]).toLowerCase()),
							"Text value verification failed for element " + ExpectedValues[i][0]
									+ "Expected Text : " + ExpectedValues[i][0] + " Actual Text : "
									+ actualTextValues);
				}
			}		
			softAssert.assertAll();

		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_MoneyMatters_Text");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}	
	
	
	
	/**
	 * Test Case - 606 - verify Links Above Header Conns Home Page
	 * 
	 */

	@SuppressWarnings("static-access")
	@Test(priority = 606, enabled = true, description = "Verify_MoneyMatters_LinksRedirection")
	public void Verify_MoneyMatters_LinksRedirection() {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = null;
		
		try {
			commonMethods.navigateToPage(webPage,moneyMattersURL, softAssert);
			//ConnsHomePage.waitPageToLoad();
			if (testType.equalsIgnoreCase("Web")) 
			{			
				commonMethods.waitForWebElement(By.xpath(moneyMattersCommonElement_Web), webPage);
			}
			
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "MoneyMatters", "verifyLinks");	
			log.info("testData.length : " + testData.length);
			for (int i = 0; i < testData.length; i++)
			{
				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][3].equalsIgnoreCase("NA")))) {
					System.out.println("Inside the first if. Value of I : " + i);
					commonMethods.clickElementbyXpath(webPage, testData[i][3], softAssert);
					Thread.sleep(1000);
				}

				if (testType.equalsIgnoreCase("Mobile") && (!(testData[i][2].equalsIgnoreCase("NA")))) {
					System.out.println("Inside the 2nd if. Value of I : " + i);
					ActualURL=commonMethods.Click_On_Element_JS(webPage, testData[i][2], softAssert);
					softAssert.assertTrue(ActualURL.contains(testData[i][4]),
							"Link Name  :" + testData[i][0] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][4]);					
				}
				
				if (testType.equalsIgnoreCase("Web")) {
					ActualURL=commonMethods.clickElementbyXpathAndGetURL(webPage, testData[i][1], softAssert);	
					softAssert.assertTrue(ActualURL.contains(testData[i][4]),
							"Link Name  :" + testData[i][0] + " : failed " + "Actual URL is  :" + ActualURL + " "
									+ "Expected URL is  :" + testData[i][4]);
				}
				commonMethods.navigateToPage(webPage,moneyMattersURL, softAssert);	

				if (testType.equalsIgnoreCase("Web")) 
				{			
					commonMethods.waitForWebElement(By.xpath(moneyMattersCommonElement_Web), webPage);
				}
				
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());

		}
	}	
	
/*	public String Click_On_Element_JS(WebPage webPage, String test, SoftAssert softAssert) throws InterruptedException {

		try {
			WebElement element = webPage.findObjectByxPath(test).getWebElement();
			JavascriptExecutor executor = (JavascriptExecutor) webPage.getDriver();
			executor.executeScript("arguments[0].click();", element);
		} catch (PageException e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
		return webPage.getCurrentUrl();
	}*/
	
/*
	@SuppressWarnings("static-access")
	@Test(priority = 604, enabled = true, description = "Verify_Font_And_Size")
	public void Verify_Font_And_Size() 
	{
		SoftAssert softAssert = new SoftAssert();
		try {
			commonMethods.navigateToPage(webPage,moneyMattersURL, softAssert);	
			
			if (testType.equalsIgnoreCase("Web")) 
			{			
				commonMethods.waitForWebElement(By.xpath(moneyMattersCommonElement_Web), webPage);
			}		
			
			String[][] ExpectedFontValues = ExcelUtil.readExcelData(DataFilePath, "MoneyMatters",
					"VerifyFontandSize");
			System.out.println("Length : " + ExpectedFontValues.length);
			for (int i = 0; i < ExpectedFontValues.length; i++) 
			{		
				log.info("Value of i : " + i);						
				List<String> actualCssValues = commonMethods.getFontProperties(webPage, ExpectedFontValues[i][1],
						softAssert);

				if (testType.equalsIgnoreCase("Mobile")) 
				{					
					softAssert.assertTrue(actualCssValues.get(0).trim().contains(ExpectedFontValues[i][5].trim()),
							"CSS value verification failed for link " + ExpectedFontValues[i][0]
									+ "Expected font Size : " + ExpectedFontValues[i][5] + " Actual Font Size : "
									+ actualCssValues.get(0));
					
					softAssert.assertTrue(actualCssValues.get(1).trim().contains(ExpectedFontValues[i][6].trim()),
							"CSS value verification failed for link " + ExpectedFontValues[i][0]
									+ "Expected font color : " + ExpectedFontValues[i][6] + " Actual font family : "
									+ actualCssValues.get(1));

					softAssert.assertTrue(actualCssValues.get(2).trim().contains(ExpectedFontValues[i][4].trim()),
								"CSS value verification failed for link " + ExpectedFontValues[i][0]
										+ "Expected font family : " + ExpectedFontValues[i][4] + " Actual font family : "
										+ actualCssValues.get(2));
				} else 
				{
					softAssert.assertTrue(actualCssValues.get(0).contains(ExpectedFontValues[i][2]),
							"CSS value verification failed for link " + ExpectedFontValues[i][0]
									+ "Expected font Size : " + ExpectedFontValues[i][2] + " Actual Font Size : "
									+ actualCssValues.get(0));
					softAssert.assertTrue(actualCssValues.get(1).contains(ExpectedFontValues[i][3]),
							"CSS value verification failed for link " + ExpectedFontValues[i][0]
									+ "Expected font color : " + ExpectedFontValues[i][3] + " Actual font family : "
									+ actualCssValues.get(1));
					softAssert.assertTrue(actualCssValues.get(2).toLowerCase().contains((ExpectedFontValues[i][4]).toLowerCase()),
							"CSS value verification failed for link " + ExpectedFontValues[i][0]
									+ "Expected font family : " + ExpectedFontValues[i][4] + " Actual font family : "
									+ actualCssValues.get(2));	
				}
			}		
			softAssert.assertAll();

		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_Font_And_Size");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}	
 */
	
	
	
	
}	