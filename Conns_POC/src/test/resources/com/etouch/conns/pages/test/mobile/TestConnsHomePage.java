package com.etouch.conns.pages.test.mobile;

import java.awt.AWTException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.etouch.conns.common.TafExecutor;
import com.etouch.conns.pages.ConnsMainPage;
import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.datamanager.excel.TafExcelDataProvider;
import com.etouch.taf.core.datamanager.excel.TestParameters;
import com.etouch.taf.core.datamanager.excel.annotations.IExcelDataFiles;
import com.etouch.taf.core.datamanager.excel.annotations.ITafExcelDataProviderInputs;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.tools.rally.SpecializedScreenRecorder;
import com.etouch.taf.tools.rally.VideoRecorder;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.taf.webui.selenium.MobileView;
import com.etouch.taf.webui.selenium.WebPage;
import com.sun.jna.platform.win32.WinBase.SYSTEM_INFO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;

@Test(groups = "HomePage")
@IExcelDataFiles(excelDataFiles = { "HomePageData=testData" })
public class TestConnsHomePage extends BaseTest {

	static String platform;
	static Log log = LogUtil.getLog(TestConnsHomePage.class);
	static String AbsolutePath= TafExecutor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	static String  videoLocation = AbsolutePath.substring(0,AbsolutePath.indexOf("/target/classes/")).substring(1).concat("/src/test/resources/testdata/videos");
	Logger logger = Logger.getLogger(TestConnsHomePage.class.getName());
	//private String url = "http://connsecommdev-1365connsecommdev-13655538477.us-east-1.elb.amazonaws.com/conns_rwd/yes-money-credit/application";
	private String url = "https://www.conns.com/";
	private WebPage webPage;
	private ConnsMainPage mainPage;
	VideoRecorder videoRecorder = null;
	AppiumDriver driver = null;
	MobileView mobileView = null;
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
	String testBedName;
	TestBed testBed;	
	
	
	@BeforeClass(alwaysRun = true)
	public void setUp(ITestContext context) throws InterruptedException, FileNotFoundException, IOException {
		try {
System.out.println("In before");
			testBedName = context.getCurrentXmlTest().getAllParameters().get("testBedName");
			CommonUtil.sop("Test bed Name is " + testBedName);
			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			
			try {
				
				platform = testBed.getPlatform().getName().toUpperCase();
				if ((platform.equals("WINDOWS")) || (platform.equals("MAC") || (platform.equals("OS X")))) {
					System.out.println("videoLocation"+videoLocation);
				//	SpecializedScreenRecorder.startVideoRecordingForDesktopBrowser(videoLocation);
				}
				else{
				}
				url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL();
				synchronized (this) {

					webPage = new WebPage(context);
					mainPage = new ConnsMainPage(url, webPage);

				}
			} catch (Exception e) {
				log.info("errr is " + e);
				SoftAssertor.addVerificationFailure(e.getMessage());
			}
		}

		catch (Exception e) {

			CommonUtil.sop("Error is for" + testBedName + " -----------" + e);
			SoftAssertor.addVerificationFailure(e.getMessage());
		}
	}

	@AfterTest
	public void releaseResources() throws IOException, AWTException {
		//SpecializedScreenRecorder.stopVideoRecording();
	}
	
	/**
	 * Test Case 001 - Verify Navigation to Yes Money Credit Application Page and Verify Page title
	 * 
	 */
	@Test(dataProvider = "tafDataProvider", dataProviderClass = TafExcelDataProvider.class, priority = 1, enabled = true, description = "Verify Page Title")
	@ITafExcelDataProviderInputs(excelFile = "HomePageData", excelsheet = "HomePage", dataKey = "verifyPageTitle")
	public void verifyPageTitle(ITestContext context, TestParameters inputs)
			throws PageException, InterruptedException {
		System.out.println("In test");
		String expectedPageTitle = inputs.getParamMap().get("Title");

		log.info("testing flow verifyPageTitle");

		try {

			String actualPageTitle = webPage.getPageTitle();
			Assert.assertEquals(expectedPageTitle, actualPageTitle);

		} catch (Exception e) {
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("verifyPageTitle failed");
			log.error(e.getMessage());
		}

		log.info("Ending verifyPageTitle");

	}
}
