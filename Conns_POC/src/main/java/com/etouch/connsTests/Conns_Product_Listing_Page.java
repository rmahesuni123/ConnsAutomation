package com.etouch.connsTests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.logging.Log;
import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.etouch.common.BaseTest;
import com.etouch.common.TafExecutor;
import com.etouch.conns.listener.SoftAssertor;
import com.etouch.connsPages.ConnsMainPage;
import com.etouch.connsPages.ConnsProductPurchasePage;
import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.datamanager.excel.annotations.IExcelDataFiles;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.webui.ITafElement;
import com.etouch.taf.webui.selenium.WebPage;

//@Test(groups = "Conns_Product_Search")
@IExcelDataFiles(excelDataFiles = { "CreditAppData=testData" })
public class Conns_Product_Listing_Page extends BaseTest {
	static String platform;
	static Log log = LogUtil.getLog(Conns_Product_Listing_Page.class);
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
			log.info("Test Type is : " + testType);
			try {
				testEnv = System.getenv().get("Environment");
				log.info("testEnv is : " + testEnv);
				path = Paths.get(TestBedManager.INSTANCE.getProfile().getXlsDataConfig().get("testData"));
				DataFilePath = path.toAbsolutePath().toString().replace("Env", testEnv);
				log.info("DataFilePath After is : " + DataFilePath);
				platform = testBed.getPlatform().getName().toUpperCase();
				if (testType.equalsIgnoreCase("Web")) {
					log.info("videoLocation" + videoLocation.toString().replace("Env", testEnv));
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

	@Test(priority = 701, enabled = true)
	public void Verify_Details_On_Product_Listing_Page() {
		try {
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductListingPage",
					"Details_On_Product_Listing_Page");
			ConnsProductPurchasePage.Click_On_French_Door_Link(webPage, test[0][1]);
			log.info("Clicked on French Door");
			String productDescription = webPage.findObjectByxPath(test[0][3]).getText();
			log.info("productDescription" + productDescription);
			String[][] contentData;
			if (testType.equalsIgnoreCase("Web")) {
				contentData = ExcelUtil.readExcelData(DataFilePath, "ProductSearch", "verifyContent");
			} else {
				contentData = ExcelUtil.readExcelData(DataFilePath, "ProductSearch", "verifyContentForMobile");
			}
			for (int i = 0; i < contentData.length; i++) {
				log.info("Actual:  " + webPage.findObjectByxPath(contentData[i][0]).getText() + "   Expected: "
						+ contentData[i][1]);
				SoftAssertor.assertTrue(
						webPage.findObjectByxPath(contentData[i][0]).getText().contains(contentData[i][1]),
						"expectedContent: " + contentData[i][0] + "Failed to Match Actual:" + contentData[i][1]);
			}
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Search_Functionality_And_Results_Contents");
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Error in Verify_Search_Functionality_And_Results_Contents :" + e.getMessage());
			e.printStackTrace();
		} finally {
			String errors = SoftAssertor.readErrorsForTest();
			if (errors != null && errors.length() > 0)
				SoftAssertor.displayErrors();
		}
	}

	@Test(priority = 702, enabled = true)
	public void Verify_Upto_5_Products_Compared() throws InterruptedException {
		try {
			if (testType.equalsIgnoreCase("Web")) {
				webPage.navigateToUrl(url);
				String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductListingPage",
						"Verify_Upto_5_Products_Compared");
				ConnsProductPurchasePage.Click_On_French_Door_Link(webPage, test[0][1]);
				log.info("Clicked on French Door");
				List<ITafElement> compareList = webPage.findObjectsByXpath(test[0][2]);
				log.info("Total Size-->" + compareList.size());
				for (int i = 0; i < 5; i++) {
					Thread.sleep(4000);
					compareList.get(i).click();
				}
			/*	Alert alert = webPage.getDriver().switchTo().alert();
				Assert.assertTrue(alert.getText().contains("Only 5 items to compare"),
						"Alert Message does not contain: " + "Only 5 items to compare");
				alert.accept();*/
				
				log.info(webPage.findObjectByxPath(test[0][3]).getText());
				log.info(webPage.findObjectByxPath(test[0][4]).getText());
				webPage.findObjectByxPath(test[0][5]).click();
				Thread.sleep(5000);
				log.info(webPage.findObjectByxPath(test[0][6]).getText());
				List<ITafElement> newList = webPage.findObjectsByXpath(test[0][7]);
				log.info(newList.get(4).getText());
				compareList.contains(newList);
			}
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Upto_5_Products_Compared");
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Error in Verify_Upto_5_Products_Compared :" + e.getMessage());
			e.printStackTrace();
		} finally {
			String errors = SoftAssertor.readErrorsForTest();
			if (errors != null && errors.length() > 0)
				SoftAssertor.displayErrors();
		}
	}
}
