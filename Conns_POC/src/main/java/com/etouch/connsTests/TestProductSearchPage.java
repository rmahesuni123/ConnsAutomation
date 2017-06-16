package com.etouch.connsTests;

import java.awt.AWTException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.logging.Log;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.etouch.common.BaseTest;
import com.etouch.common.TafExecutor;
import com.etouch.connsPages.ConnsMainPage;
import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.datamanager.excel.TafExcelDataProvider;
import com.etouch.taf.core.datamanager.excel.TestParameters;
import com.etouch.taf.core.datamanager.excel.annotations.IExcelDataFiles;
import com.etouch.taf.core.datamanager.excel.annotations.ITafExcelDataProviderInputs;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.taf.webui.selenium.LayoutManager;
import com.etouch.taf.webui.selenium.WebPage;

//import mx4j.log.Logger;

//@Test(groups = "HomePage")
@IExcelDataFiles(excelDataFiles = { "CreditAppData=testData" })
public class TestProductSearchPage extends BaseTest {
	static String platform;
	static Log log = LogUtil.getLog(TestConnsHomePage.class);
	static String AbsolutePath = TafExecutor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	static String videoLocation = AbsolutePath.substring(0, AbsolutePath.indexOf("/target/classes/")).substring(1)
			.concat("/ConnsTestData/Output/Env/Video");
	private String url = null;
	private WebPage webPage;
	private ConnsMainPage mainPage;
	String testBedName;
	TestBed testBed;
	Path path;
	String DataFilePath;
	String testType;
	String testEnv;

	@BeforeClass(alwaysRun = true)
	public void setUp(ITestContext context) throws InterruptedException, FileNotFoundException, IOException {
		try {
			testBedName = context.getCurrentXmlTest().getAllParameters().get("testBedName");
			CommonUtil.sop("Test bed Name is " + testBedName);
			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			System.out.println("Test Type is : " + testType);
			try {
				testEnv = System.getenv().get("Environment");
				System.out.println("testEnv is : " + testEnv);
				path = Paths.get(TestBedManager.INSTANCE.getProfile().getXlsDataConfig().get("testData"));
				DataFilePath = path.toAbsolutePath().toString().replace("Env", testEnv);
				System.out.println("DataFilePath After is : " + DataFilePath);
				platform = testBed.getPlatform().getName().toUpperCase();
				if (testType.equalsIgnoreCase("Web")) {
					System.out.println("videoLocation" + videoLocation.toString().replace("Env", testEnv));
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
		} catch (Exception e) {
			CommonUtil.sop("Error is for" + testBedName + " -----------" + e);
			SoftAssertor.addVerificationFailure(e.getMessage());
		}
	}

	@AfterTest
	public void releaseResources() throws IOException, AWTException {
		// SpecializedScreenRecorder.stopVideoRecording();
	}

	@Test(priority = 1, enabled = true)
	public void verifyProductSearchUsingKeyword() {
		try {
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductSearch", "verifyProductSearchUsingKeyword");
			String Identifier = test[0][0];
			String ProductName = test[0][1];
			webPage.findObjectById(Identifier).sendKeys(ProductName);
			webPage.findObjectByClass(test[0][2]).click();
			log.info("Clicked on element " + test[0][2]);
			String productDescription = webPage.findObjectByxPath(test[0][3]).getText();
			log.info("productDescription" + productDescription);
			Assert.assertTrue(productDescription.contains(ProductName),
					"Product description: " + productDescription + " not having: " + ProductName);
			webPage.getBackToUrl();
		} catch (PageException e) {
			mainPage.getScreenShotForFailure(webPage, "verifyProductSearchUsingKeyword");
			e.printStackTrace();
		}
	}

	@Test(priority = 2, enabled = true)
	public void verifyProductSearch() {
		try {
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductSearch", "verifyProductSearch");
			String Identifier = test[0][0];
			String ProductName = test[0][1];
			webPage.findObjectById(Identifier).sendKeys(ProductName);
			webPage.findObjectByClass(test[0][2]).click();
			log.info("Clicked on element " + test[0][2]);
			String productDescription = webPage.findObjectByxPath(test[0][3]).getText();
			log.info("productDescription" + productDescription);
			Assert.assertTrue(productDescription.contains(ProductName+"1"),
					"Product description: " + productDescription + " not having: " + ProductName);
			if (testType.equalsIgnoreCase("Web")) {
				webPage.getBackToUrl();		
			}
		} catch (PageException e) {
			mainPage.getScreenShotForFailure(webPage, "verifyProductSearch");// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(dataProvider = "tafDataProvider", dataProviderClass = TafExcelDataProvider.class, priority = 5, enabled = true, description = "Verify Page Title")
	@ITafExcelDataProviderInputs(excelFile = "CreditAppData", excelsheet = "ProductSearch", dataKey = "verifyProductSearch")
	public void verifyColumnLayoutForProductSearch(ITestContext context, TestParameters inputs)
			throws PageException, InterruptedException {
		try {
			if (testType.equalsIgnoreCase("Web")) {
				LayoutManager layoutManager = new LayoutManager();
				int height[] = { 500, 500, 500 };
				int width[] = { 350, 700, 1050 };
				for (int i = 0; i < width.length; i++) {
					webPage.resize(width[i], height[i]);
					Thread.sleep(4000);
					int cols = layoutManager.getColumnLayout(width[i], height[i]);
					log.info("Column Layout " + cols);
					if (cols == 1 || cols == 2) {
						log.info("Column Layout equivalent to Mobile or Tablets for column layout = " + cols);
						Assert.assertEquals(true,
								webPage.findObjectByxPath(inputs.getParamMap().get("MobileMainMenu")).isDisplayed(),
								"Main Menu not displayed");
					} else {
						log.info("Column Layout equivalent to browser for column layout= " + cols);
						Assert.assertEquals(
								webPage.findObjectByxPath(inputs.getParamMap().get("MobileMainMenu")).isDisplayed(),
								false, "Main Menu displayed");
					}
				}
				webPage.getDriver().manage().window().maximize();
			} else {
				log.info("Column layout testing can not be done for Devices");
			}
		} catch (Exception e) {
			mainPage.getScreenShotForFailure(webPage, "verifyColumnLayoutForProductSearch");// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
