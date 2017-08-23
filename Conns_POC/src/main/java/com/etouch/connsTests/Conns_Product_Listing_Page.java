package com.etouch.connsTests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.etouch.common.BaseTest;
import com.etouch.common.CommonMethods;
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

	@Test(priority = 701, enabled = false)
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
					Thread.sleep(2000);
					compareList.get(i).click();
				}
				/*
				 * Alert alert = webPage.getDriver().switchTo().alert();
				 * Assert.assertTrue(alert.getText().contains(
				 * "Only 5 items to compare"),
				 * "Alert Message does not contain: " +
				 * "Only 5 items to compare"); alert.accept();
				 */
				log.info(webPage.findObjectByxPath(test[0][3]).getText());
				log.info(webPage.findObjectByxPath(test[0][4]).getText());
				webPage.findObjectByxPath(test[0][5]).click();
				// Thread.sleep(5000);
				CommonMethods.waitForWebElement(By.xpath(test[0][6]), webPage);
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

	@Test(priority = 703, enabled = true)
	public void Verify_Number_Of_Product_Displayed_From_Product_Listing_Page() throws InterruptedException {
		try {
			if (testType.equalsIgnoreCase("Web")) {
				webPage.navigateToUrl(url);
				String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductListingPage",
						"Number_Of_Product_Displayed_From_Product_Listing_Page");
				ConnsProductPurchasePage.Click_On_French_Door_Link(webPage, test[0][1]);
				log.info("Clicked on French Door");
				Thread.sleep(7000);
				Select s = new Select(webPage.getDriver().findElement(By.xpath((test[0][5]))));
				List<WebElement> list = s.getOptions();
				String str[] = { list.get(0).getText().toString(), list.get(1).getText().toString(),
						list.get(2).getText().toString() };
				String str2[] = test[0][6].split(",");
				int number;
				for (int i = 0; i < str.length; i++) {
					number = Integer.parseInt(str[i].trim());
					SoftAssertor.assertEquals(number, Integer.parseInt(str2[i]), "Number List:  ");
					log.info("Started iteration for -->" + number);
					s.selectByVisibleText(String.valueOf(number));
					// Thread.sleep(5000);
					CommonMethods.waitForWebElement(By.xpath(test[0][7]), webPage);
					List<WebElement> elementList = webPage.getDriver().findElements(By.xpath(test[0][7]));
					log.info("Number: " + number + "    element Size-->" + elementList.size());
					SoftAssertor.assertEquals(elementList.size() <= number, true, "element is Not As Expected");
					log.info("Completed for iteration-->");
					s = new Select(webPage.getDriver().findElement(By.xpath((test[0][5]))));
				}
			}
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Number_Of_Product_Displayed_From_Product_Listing_Page");
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Error in Verify_Number_Of_Product_Displayed_From_Product_Listing_Page :" + e.getMessage());
			e.printStackTrace();
		} finally {
			String errors = SoftAssertor.readErrorsForTest();
			if (errors != null && errors.length() > 0)
				SoftAssertor.displayErrors();
		}
	}

	@Test(priority = 704, enabled = true)
	public void Verify_Sorting_By_Product_Name_From_Product_Listing_Page() throws InterruptedException {
		try {
			// if (testType.equalsIgnoreCase("Web")) {
			webPage.navigateToUrl(url);
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductListingPage",
					"Sorting_By_Product_Name_From_Product_Listing_Page");
			ConnsProductPurchasePage.Click_On_French_Door_Link(webPage, test[0][1]);
			log.info("Clicked on French Door");
			// Thread.sleep(5000);
			CommonMethods.waitForWebElement(By.xpath(test[0][5]), webPage);
			/*
			 * //Comment this code When sorting defect fixed Select s = new
			 * Select(webPage.getDriver().findElement(By.xpath((test[0][5]))));
			 * s.selectByVisibleText(test[0][6]); //Thread.sleep(8000);
			 * CommonMethods.waitForWebElement(By.xpath(test[0][5]), webPage);
			 * //End //Shorted in Ascending
			 */
			Select s = new Select(webPage.getDriver().findElement(By.xpath((test[0][5]))));
			s.selectByVisibleText(test[0][7]);
			// Thread.sleep(8000);
			CommonMethods.waitForWebElement(By.xpath(test[0][8]), webPage);
			List<WebElement> elementList = webPage.getDriver().findElements(By.xpath(test[0][8]));
			log.info("element Size-->" + elementList.size());
			boolean isSorted = mainPage.isSortedByName(elementList);
			log.info("element is shorted: " + isSorted);
			SoftAssertor.assertEquals(isSorted, true, "element is Not shorted by Product Name");
			// For Descending
			webPage.findObjectByxPath(test[0][9]).click();
			// Thread.sleep(5000);
			CommonMethods.waitForWebElement(By.xpath(test[0][8]), webPage);
			elementList = webPage.getDriver().findElements(By.xpath(test[0][8]));
			log.info("element Size-->" + elementList.size());
			boolean isSortedDesc = mainPage.isSortedByNameDesc(elementList);
			log.info("element is shorted: " + isSortedDesc);
			SoftAssertor.assertEquals(isSortedDesc, true, "element is Not shorted by Product Name in Desc");
			// webPage.getBackToUrl();
			// }
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Sorting_By_Product_Name_From_Product_Listing_Page");
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Error in Verify_Sorting_By_Product_Name_From_Product_Listing_Page :" + e.getMessage());
			e.printStackTrace();
		} finally {
			String errors = SoftAssertor.readErrorsForTest();
			if (errors != null && errors.length() > 0)
				SoftAssertor.displayErrors();
		}
	}

	@Test(priority = 705, enabled = true)
	public void Verify_Sorting_By_Product_Price_From_Product_Listing_Page() throws InterruptedException {
		try {
			// if (testType.equalsIgnoreCase("Web")) {
			webPage.navigateToUrl(url);
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductListingPage",
					"Sorting_By_Product_Price_From_Product_Listing_Page");
			ConnsProductPurchasePage.Click_On_French_Door_Link(webPage, test[0][1]);
			log.info("Clicked on French Door");
			/*
			 * //Comment this code When sorting defect fixed Select s = new
			 * Select(webPage.getDriver().findElement(By.xpath((test[0][5]))));
			 * s.selectByVisibleText(test[0][6]); //Thread.sleep(7000);
			 * CommonMethods.waitForWebElement(By.xpath(test[0][5]), webPage);
			 * //End
			 */
			Select s = new Select(webPage.getDriver().findElement(By.xpath((test[0][5]))));
			s.selectByVisibleText(test[0][7]);
			// Thread.sleep(8000);
			CommonMethods.waitForWebElement(By.xpath(test[0][8]), webPage);
			List<WebElement> elementPriceList = webPage.getDriver().findElements(By.xpath(test[0][8]));
			log.info("element Size-->" + elementPriceList.size());
			boolean isSorted = mainPage.isSortedFloat(elementPriceList);
			log.info("element is shorted: " + isSorted);
			SoftAssertor.assertEquals(isSorted, true, "element is Not shorted by price");
			// For Descending
			webPage.findObjectByxPath(test[0][9]).click();
			// Thread.sleep(5000);
			CommonMethods.waitForWebElement(By.xpath(test[0][8]), webPage);
			elementPriceList = webPage.getDriver().findElements(By.xpath(test[0][8]));
			log.info("element Size-->" + elementPriceList.size());
			boolean isSortedInDesc = mainPage.isSortedDescFloat(elementPriceList);
			log.info("element is shorted: " + isSortedInDesc);
			SoftAssertor.assertEquals(isSortedInDesc, true, "element is Not shorted by price");
			// webPage.getBackToUrl();
			// }
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Sorting_By_Product_Price_From_Product_Listing_Page");
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Error in Verify_Sorting_By_Product_Price_From_Product_Listing_Page :" + e.getMessage());
			e.printStackTrace();
		} finally {
			String errors = SoftAssertor.readErrorsForTest();
			if (errors != null && errors.length() > 0)
				SoftAssertor.displayErrors();
		}
	}
}
