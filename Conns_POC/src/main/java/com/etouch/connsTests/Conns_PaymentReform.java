package com.etouch.connsTests;

import java.awt.AWTException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.etouch.common.BaseTest;
import com.etouch.common.CommonMethods;
import com.etouch.common.TafExecutor;
import com.etouch.connsPages.ConnsHomePage;
import com.etouch.connsPages.ConnsMainPage;
import com.etouch.connsPages.ConnsStoreLocatorPage;
import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.datamanager.excel.annotations.IExcelDataFiles;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.taf.webui.selenium.MobileView;
import com.etouch.taf.webui.selenium.WebPage;

@IExcelDataFiles(excelDataFiles = { "ConnsHomePageData=testData" })
public class Conns_PaymentReform extends BaseTest {
	static String platform;
	static Log log = LogUtil.getLog(Conns_Store_Locator_Page.class);
	static String AbsolutePath = TafExecutor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	private String url = null;
	private WebPage webPage;
	private ConnsMainPage mainPage;
	String testBedName;
	TestBed testBed;
	MobileView mobileView;
	Path path;
	String DataFilePath;
	String testType;
	static protected String browserName;
	String testEnv;
	CommonMethods commonMethods;
	ConnsStoreLocatorPage connsStoreLocatorPage;
	String storeLocatorURL = "";
	String[][] commonData;
	ConnsHomePage ConnsHomePage;

	@BeforeClass(alwaysRun = true)
	public void setUp(ITestContext context) throws InterruptedException, FileNotFoundException, IOException {
		try {
			testBedName = context.getCurrentXmlTest().getAllParameters().get("testBedName");
			CommonUtil.sop("Test bed Name is " + testBedName);
			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			connsStoreLocatorPage = new ConnsStoreLocatorPage();
			commonMethods = new CommonMethods();
			browserName = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getBrowser().getName();
			log.info("Test Type is : " + testType);
			try {
				testEnv = System.getenv().get("Environment");
				log.info("testEnv is : " + testEnv);
				path = Paths.get(TestBedManager.INSTANCE.getProfile().getXlsDataConfig().get("testData"));
				DataFilePath = path.toAbsolutePath().toString().replace("Env", testEnv);
				log.info("DataFilePath After is : " + DataFilePath);
			//	commonData = ExcelUtil.readExcelData(DataFilePath, "StoreLocator", "storeLocatorCommonElements");
			//	storeLocatorURL = commonData[0][0];
				platform = testBed.getPlatform().getName().toUpperCase();
				url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL();
				synchronized (this) {
					webPage = new WebPage(context);
				//	mainPage = new ConnsMainPage(url, webPage);
				//	ConnsHomePage = new ConnsHomePage(url, webPage);
				}
				Thread.sleep(5000);
			} catch (Exception e) {
				log.info("errr is " + e);
				SoftAssertor.addVerificationFailure(e.getMessage());
			}
		} catch (Exception e) {
			CommonUtil.sop("Error is for" + testBedName + " -----------" + e);
			SoftAssertor.addVerificationFailure(e.getMessage());
		}
	}
	@BeforeMethod
	public void SignIn() throws InterruptedException, PageException  {
	    String SignIn_Url = "http://connsecommdev-1365538477.us-east-1.elb.amazonaws.com/conns_rwd/customer/account/login/";
		
		Thread.sleep(5000);
		System.out.println("Url Opened");
		//webPage.navigateToUrl(SignIn_Url);
		webPage.getDriver().get(SignIn_Url);
		webPage.findObjectById("email").sendKeys("atul.malhotra@etouch.net");
		webPage.findObjectById("pass").sendKeys("atul123");
		webPage.findObjectById("send2").click();
		
	}
	@AfterTest
	public void releaseResources() throws IOException, AWTException {
		webPage.getDriver().quit();
	}

	/**
	 * Test Case - 002 - Verify Font Size and Style of specified on element on
	 * Conns Home Page
	 * 
	 */
	@Test(priority = 1, enabled = true, description = "Verify_Font_And_Size")
	public void Verify_Font_And_Size() {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[] WebURL = {"http://connsecommdev-1365538477.us-east-1.elb.amazonaws.com/conns_rwd/billpayments/mypayments/"};
			String[] pageName = { "Page1" };
			for (int j = 0; j < WebURL.length; j++) {
				webPage.getDriver().get(WebURL[j]);
				Thread.sleep(5000);
				System.out.println("current url===>" + webPage.getDriver().getCurrentUrl());
				String[][] ExpectedFontValuesWeb = ExcelUtil.readExcelData(DataFilePath, "Conns_PaymentReform",
						"VerifyFontandSizeWeb" + pageName[j]);
				log.info("Reading file :");
				String[][] ExpectedFontValuesMobile = ExcelUtil.readExcelData(DataFilePath, "Conns_PaymentReform",
						"VerifyFontandSizeMobile" + pageName[j]);
				String[][] ExpectedFontValuesTab = ExcelUtil.readExcelData(DataFilePath, "Conns_PaymentReform",
						"VerifyFontandSizeTab" + pageName[j]);
				JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
				int width = ((Long) js.executeScript("return window.innerWidth || document.body.clientWidth"))
						.intValue();
				log.info("width value calculated is :" + width);
				int height = ((Long) js.executeScript("return window.innerHeight || document.body.clientHeight"))
						.intValue();
				log.info("height value calculated is :" + height);
				Dimension dimension = new Dimension(width, height);
				log.info("Dimensions" + dimension);
				// Dimension[width=600,height=792]
				if (testType.equalsIgnoreCase("Web")) {
					for (int i = 0; i < ExpectedFontValuesWeb.length; i++) {
						List<String> actualCssValues = commonMethods.getFontProperties(webPage,
								ExpectedFontValuesWeb[i][1], softAssert);
						log.info("Exp Value :"+ExpectedFontValuesWeb[i][0]);
						log.info("Exp Value :"+ExpectedFontValuesWeb[i][2]);
						
						if (!ExpectedFontValuesWeb[i][2].equalsIgnoreCase("NA")) {
							softAssert.assertTrue(actualCssValues.get(0).contains(ExpectedFontValuesWeb[i][2]),
									"CSS value verification failed for link " + ExpectedFontValuesWeb[i][0]
											+ ". Expected font Size: " + ExpectedFontValuesWeb[i][2]
											+ " Actual Font Size: " + actualCssValues.get(0));
						}
						if (!ExpectedFontValuesWeb[i][3].equalsIgnoreCase("NA")) {
							softAssert.assertTrue(actualCssValues.get(1).contains(ExpectedFontValuesWeb[i][3]),
									"CSS value verification failed for link " + ExpectedFontValuesWeb[i][0]
											+ ". Expected font color: " + ExpectedFontValuesWeb[i][3]
											+ " Actual font color: " + actualCssValues.get(1));
						}
						if (!ExpectedFontValuesWeb[i][4].equalsIgnoreCase("NA")) {
							softAssert.assertTrue(
									actualCssValues.get(2).toLowerCase()
											.contains((ExpectedFontValuesWeb[i][4]).toLowerCase()),
									"CSS value verification failed for link " + ExpectedFontValuesWeb[i][0]
											+ ". Expected font family: " + ExpectedFontValuesWeb[i][4]
											+ " Actual font family: " + actualCssValues.get(2));
						}
						if (!ExpectedFontValuesWeb[i][5].equalsIgnoreCase("NA")) {
							softAssert.assertTrue(
									actualCssValues.get(3).toLowerCase()
											.contains((ExpectedFontValuesWeb[i][5]).toLowerCase()),
									"CSS value verification failed for link " + ExpectedFontValuesWeb[i][0]
											+ ". Expected font weight: " + ExpectedFontValuesWeb[i][5]
											+ " Actual font weight: " + actualCssValues.get(3));
						}
						if (!ExpectedFontValuesWeb[i][6].equalsIgnoreCase("NA")) {
							softAssert.assertTrue(
									actualCssValues.get(4).toLowerCase()
											.contains((ExpectedFontValuesWeb[i][6]).toLowerCase()),
									"CSS value verification failed for link " + ExpectedFontValuesWeb[i][0]
											+ ". Expected font background color: " + ExpectedFontValuesWeb[i][6]
											+ " Actual font background color: " + actualCssValues.get(4));
						}
						if (!ExpectedFontValuesWeb[i][7].equalsIgnoreCase("NA")) {
							softAssert.assertTrue(
									actualCssValues.get(7).toLowerCase()
											.contains((ExpectedFontValuesWeb[i][7]).toLowerCase()),
									"CSS value verification failed for link " + ExpectedFontValuesWeb[i][0]
											+ ". Expected font background Image color: " + ExpectedFontValuesWeb[i][7]
											+ " Actual font background Image color: " + actualCssValues.get(7));
						}
					}
				}
				if (testType.equalsIgnoreCase("Mobile")) {
					if (width > 599 || width < 800) {
						// if(deviceName.contains("Tab")){
						for (int i = 0; i < ExpectedFontValuesTab.length; i++) {
							List<String> actualCssValues = commonMethods.getFontProperties(webPage,
									ExpectedFontValuesTab[i][1], softAssert);
							if (!ExpectedFontValuesTab[i][2].equalsIgnoreCase("NA")) {
								softAssert.assertTrue(actualCssValues.get(0).contains(ExpectedFontValuesTab[i][2]),
										"CSS value verification failed for link " + ExpectedFontValuesTab[i][0]
												+ ". Expected font Size: " + ExpectedFontValuesTab[i][2]
												+ " Actual Font Size: " + actualCssValues.get(0));
							}
						}
					} else {
						for (int i = 0; i < ExpectedFontValuesMobile.length; i++) {
							List<String> actualCssValues = commonMethods.getFontProperties(webPage,
									ExpectedFontValuesMobile[i][1], softAssert);
							if (!ExpectedFontValuesMobile[i][2].equalsIgnoreCase("NA")) {
								softAssert.assertTrue(actualCssValues.get(0).contains(ExpectedFontValuesMobile[i][2]),
										"CSS value verification failed for link " + ExpectedFontValuesMobile[i][0]
												+ ". Expected font Size: " + ExpectedFontValuesMobile[i][2]
												+ " Actual Font Size: " + actualCssValues.get(0));
							}
							if (!ExpectedFontValuesMobile[i][3].equalsIgnoreCase("NA")) {
								softAssert.assertTrue(actualCssValues.get(1).contains(ExpectedFontValuesMobile[i][3]),
										"CSS value verification failed for link " + ExpectedFontValuesMobile[i][0]
												+ ". Expected font color: " + ExpectedFontValuesMobile[i][3]
												+ " Actual font color: " + actualCssValues.get(1));
							}
							if (!ExpectedFontValuesMobile[i][4].equalsIgnoreCase("NA")) {
								softAssert.assertTrue(
										actualCssValues.get(2).toLowerCase()
												.contains((ExpectedFontValuesMobile[i][4]).toLowerCase()),
										"CSS value verification failed for link " + ExpectedFontValuesMobile[i][0]
												+ ". Expected font family: " + ExpectedFontValuesMobile[i][4]
												+ " Actual font family: " + actualCssValues.get(2));
							}
							if (!ExpectedFontValuesMobile[i][5].equalsIgnoreCase("NA")) {
								softAssert.assertTrue(
										actualCssValues.get(3).toLowerCase()
												.contains((ExpectedFontValuesMobile[i][5]).toLowerCase()),
										"CSS value verification failed for link " + ExpectedFontValuesMobile[i][0]
												+ ". Expected font weight: " + ExpectedFontValuesMobile[i][5]
												+ " Actual font weight: " + actualCssValues.get(3));
							}
							if (!ExpectedFontValuesMobile[i][6].equalsIgnoreCase("NA")) {
								softAssert.assertTrue(
										actualCssValues.get(4).toLowerCase()
												.contains((ExpectedFontValuesMobile[i][6]).toLowerCase()),
										"CSS value verification failed for link " + ExpectedFontValuesMobile[i][0]
												+ ". Expected font background color: " + ExpectedFontValuesMobile[i][6]
												+ " Actual font background color: " + actualCssValues.get(4));
							}
							if (!ExpectedFontValuesMobile[i][7].equalsIgnoreCase("NA")) {
								softAssert.assertTrue(
										actualCssValues.get(5).toLowerCase()
												.contains((ExpectedFontValuesMobile[i][7]).toLowerCase()),
										"CSS value verification failed for link " + ExpectedFontValuesMobile[i][0]
												+ ". Expected Text Align: " + ExpectedFontValuesMobile[i][7]
												+ " Actual Text Align: " + actualCssValues.get(5));
							}
							if (!ExpectedFontValuesMobile[i][8].equalsIgnoreCase("NA")) {
								softAssert.assertTrue(
										actualCssValues.get(6).toLowerCase()
												.contains((ExpectedFontValuesMobile[i][8]).toLowerCase()),
										"CSS value verification failed for link " + ExpectedFontValuesMobile[i][0]
												+ ". Expected Text Transform: " + ExpectedFontValuesMobile[i][8]
												+ " Actual Text Transform: " + actualCssValues.get(6));
							}
						}
					}
				}
				softAssert.assertAll();
			}
		}	 catch (Throwable e) {
			// mainPage.getScreenShotForFailure(webPage,
			// "Verify_Font_And_Size");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

}