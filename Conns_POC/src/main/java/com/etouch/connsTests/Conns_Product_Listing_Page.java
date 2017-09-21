package com.etouch.connsTests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.logging.Log;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
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
	static String AbsolutePath = TafExecutor.class.getProtectionDomain()
			.getCodeSource().getLocation().getPath();
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
	public void setUp(ITestContext context) throws InterruptedException,
			FileNotFoundException, IOException {
		try {
			testBedName = context.getCurrentXmlTest().getAllParameters()
					.get("testBedName");
			CommonUtil.sop("Test bed Name is " + testBedName);
			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(
					testBedName);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds()
					.get(testBedName).getTestType();
			log.info("Test Type is : " + testType);
			try {
				testEnv = System.getenv().get("Environment");
				log.info("testEnv is : " + testEnv);
				path = Paths.get(TestBedManager.INSTANCE.getProfile()
						.getXlsDataConfig().get("testData"));
				DataFilePath = path.toAbsolutePath().toString()
						.replace("Env", testEnv);
				log.info("DataFilePath After is : " + DataFilePath);
				platform = testBed.getPlatform().getName().toUpperCase();
				url = TestBedManagerConfiguration.INSTANCE.getWebConfig()
						.getURL();
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
	public void Verify_For_Pagination_And_Product_Details() {
		try {
			String[][] test = ExcelUtil.readExcelData(DataFilePath,
					"ProductListingPage",
					"Verify_For_Pagination_And_Product_Details");
			CommonMethods.navigateToPage(webPage, url);
			ConnsProductPurchasePage.Click_On_French_Door_Link(webPage,
					test[0][0]);
			log.info("Clicked on French Door");
			// Pagination using index number
			if (testType.equalsIgnoreCase("Web")) {
				String paginationNumber = webPage.findObjectByxPath(test[0][2])
						.getText();
				Assert.assertEquals(paginationNumber, test[0][3],
						"Pagination Number: ");
				webPage.findObjectByxPath(test[0][2]).click();
				CommonMethods.waitForGivenTime(2);
				paginationNumber = webPage.findObjectByxPath(test[0][8])
						.getText();
				Assert.assertEquals(paginationNumber, test[0][9],
						"Pagination Number: ");
				webPage.findObjectByxPath(test[0][8]).click();
				CommonMethods.waitForGivenTime(2);

				// Pagination using Next and Back
				String nextPagination = webPage.findObjectByxPath(test[0][4])
						.getText();
				Assert.assertEquals(nextPagination, test[0][5],
						"nextPagination: ");
				webPage.findObjectByxPath(test[0][4]).click();
				CommonMethods.waitForGivenTime(2);
				String backPagination = webPage.findObjectByxPath(test[0][6])
						.getText();
				Assert.assertEquals(backPagination, test[0][7],
						"backPagination: ");
				// Checking again Next is displayed or not
				// nextPagination =
				// webPage.findObjectByxPath(test[0][4]).getText();
				// Assert.assertEquals(nextPagination, test[0][5],
				// "nextPagination: ");
			} else {
				CommonMethods.waitForGivenTime(5);
				String[][] mobileData = ExcelUtil.readExcelData(DataFilePath,
						"ProductListingPage",
						"Verify_For_Pagination_And_Product_Details_For_Mobile");
				// Pagination using Next and Back
				String nextPagination = webPage.findObjectByxPath(
						mobileData[0][0]).getText();
				Assert.assertTrue(
						nextPagination.equalsIgnoreCase(mobileData[0][1]),
						"NextPagination: Expected:" + mobileData[0][1]
								+ " Actual: " + nextPagination);
				webPage.findObjectByxPath(mobileData[0][0]).click();
				CommonMethods.waitForGivenTime(5);

				String backPagination = webPage.findObjectByxPath(
						mobileData[0][2]).getText();
				Assert.assertTrue(
						backPagination.equalsIgnoreCase(mobileData[0][3]),
						"backPagination: Expected:" + mobileData[0][3]
								+ " Actual: " + backPagination);
				webPage.findObjectByxPath(mobileData[0][2]).click();
				CommonMethods.waitForGivenTime(5);

				// Verifying Next button is displayed
				nextPagination = webPage.findObjectByxPath(mobileData[0][0])
						.getText();
				Assert.assertTrue(
						nextPagination.equalsIgnoreCase(mobileData[0][1]),
						"NextPagination: Expected:" + mobileData[0][1]
								+ " Actual: " + nextPagination);
			}
			// webPage.findObjectByxPath(test[0][6]).click();
			// Click on any Product
			webPage.findObjectByxPath(test[0][10]).click();
			if (CommonMethods.verifyElementisPresent(webPage, test[0][11])) {
				webPage.findObjectByxPath(test[0][11]).click();
			}
			String[][] contentData;
			if (testType.equalsIgnoreCase("Web")) {
				contentData = ExcelUtil.readExcelData(DataFilePath,
						"ProductListingPage", "verifyProductDetails");
			} else {
				contentData = ExcelUtil.readExcelData(DataFilePath,
						"ProductListingPage", "verifyProductDetailsForMobile");
			}
			for (int i = 0; i < contentData.length; i++) {
				String actualContent = webPage.findObjectByxPath(
						contentData[i][0]).getText();
				log.info("Actual:  " + actualContent + "   Expected: "
						+ contentData[i][1]);
				SoftAssertor.assertTrue(
						actualContent.contains(contentData[i][1]),
						"expectedContent: " + contentData[i][1]
								+ "  Failed to Match Actual:" + actualContent);
			}
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage,
					"Verify_For_Pagination_And_Product_Details");
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Error in Verify_For_Pagination_And_Product_Details :"
					+ e.getMessage());
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
				CommonMethods.navigateToPage(webPage, url);
				String[][] test = ExcelUtil
						.readExcelData(DataFilePath, "ProductListingPage",
								"Verify_Upto_5_Products_Compared");
				ConnsProductPurchasePage.Click_On_French_Door_Link(webPage,
						test[0][1]);
				log.info("Clicked on French Door");
				List<ITafElement> compareList = webPage
						.findObjectsByXpath(test[0][2]);
				log.info("Total Size-->" + compareList.size());
				for (int i = 0; i < 6; i++) {
					CommonMethods.waitForGivenTime(2);
					if (i < 5) {
						compareList.get(i).click();
					}
					else {
						if (testBedName.contains("iPadNative")
								|| testBedName.contains("iPhoneNative")
								|| testBedName.equalsIgnoreCase("Safari")) {
							JavascriptExecutor js = (JavascriptExecutor)
									 webPage.getDriver();
									 js.executeScript("window.alert = function(){ return true;}");
						compareList.get(i).click();
						CommonMethods.waitForGivenTime(2);
						} else {
							compareList.get(i).click();
							Alert alert = webPage.getDriver().switchTo()
									.alert();
							Assert.assertTrue(
									alert.getText().contains(
											"Only 5 items to compare"),
									"Alert Message does not contain: "
											+ "Only 5 items to compare");
							alert.accept();
						}
					}
				}
				
				log.info(webPage.findObjectByxPath(test[0][3]).getText());
				log.info(webPage.findObjectByxPath(test[0][4]).getText());
				webPage.findObjectByxPath(test[0][5]).click();
				CommonMethods.waitForWebElement(By.xpath(test[0][6]), webPage);
				log.info(webPage.findObjectByxPath(test[0][6]).getText());
				List<ITafElement> newList = webPage
						.findObjectsByXpath(test[0][7]);
				log.info(newList.get(4).getText());
				compareList.contains(newList);
			}
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage,
					"Verify_Upto_5_Products_Compared");
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Error in Verify_Upto_5_Products_Compared :"
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			String errors = SoftAssertor.readErrorsForTest();
			if (errors != null && errors.length() > 0)
				SoftAssertor.displayErrors();
		}
	}

	@Test(priority = 703, enabled = true)
	public void Verify_Number_Of_Product_Displayed_From_Product_Listing_Page()
			throws InterruptedException {
		try {
			CommonMethods.navigateToPage(webPage, url);
			String[][] test = ExcelUtil.readExcelData(DataFilePath,
					"ProductListingPage",
					"Number_Of_Product_Displayed_From_Product_Listing_Page");
			ConnsProductPurchasePage.Click_On_French_Door_Link(webPage,
					test[0][0]);
			log.info("Clicked on French Door");
			String str2[] = test[0][3].split(",");
			if (testType.equalsIgnoreCase("Web")) {
				CommonMethods.waitForGivenTime(5);
				Select s = new Select(webPage.getDriver().findElement(
						By.xpath((test[0][2]))));
				List<WebElement> list = s.getOptions();
				String str[] = { list.get(0).getText().toString(),
						list.get(1).getText().toString(),
						list.get(2).getText().toString() };
				int number;
				for (int i = 0; i < str.length; i++) {
					number = Integer.parseInt(str[i].trim());
					s.selectByVisibleText(String.valueOf(number));
					CommonMethods.waitForGivenTime(5);
					CommonMethods.waitForWebElement(By.xpath(test[0][4]),
							webPage);
					List<WebElement> elementList = webPage.getDriver()
							.findElements(By.xpath(test[0][4]));
					SoftAssertor.assertEquals(elementList.size() <= number,
							true,
							"Number of element is not as expected---->Actual Size: "
									+ elementList.size()
									+ " Should be less than: " + number);
					s = new Select(webPage.getDriver().findElement(
							By.xpath((test[0][2]))));
				}
			} else {
				for (int i = 0; i < str2.length; i++) {
					ConnsProductPurchasePage.Click_On_Element_JS(webPage,
							test[0][7 + i]);
					CommonMethods.waitForGivenTime(5);
					List<WebElement> list = webPage.getDriver().findElements(
							By.xpath(test[0][9]));
					log.info("list.size():: " + list.size());
					log.info("Integer.parseInt(str2[i]):: "
							+ Integer.parseInt(str2[i]));
					SoftAssertor.assertEquals(
							list.size() <= Integer.parseInt(str2[i]), true,
							"Number of element is not as expected---->Actual Size: "
									+ list.size() + " Should be less than: "
									+ Integer.parseInt(str2[i]));
				}
			}
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage,
					"Verify_Number_Of_Product_Displayed_From_Product_Listing_Page");
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Error in Verify_Number_Of_Product_Displayed_From_Product_Listing_Page :"
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			String errors = SoftAssertor.readErrorsForTest();
			if (errors != null && errors.length() > 0)
				SoftAssertor.displayErrors();
		}
	}

	@Test(priority = 704, enabled = true)
	public void Verify_Sorting_By_Product_Name_From_Product_Listing_Page()
			throws InterruptedException {
		try {
			CommonMethods.navigateToPage(webPage, url);
			String[][] test = ExcelUtil.readExcelData(DataFilePath,
					"ProductListingPage",
					"Sorting_By_Product_Name_From_Product_Listing_Page");
			ConnsProductPurchasePage.Click_On_French_Door_Link(webPage,
					test[0][0]);
			log.info("Clicked on French Door");
			Select s;
			if (testType.equalsIgnoreCase("Web")) {
				CommonMethods.waitForWebElement(By.xpath(test[0][2]), webPage);
				s = new Select(webPage.getDriver().findElement(
						By.xpath((test[0][2]))));
			} else {
				CommonMethods.waitForWebElement(By.xpath(test[0][6]), webPage);
				s = new Select(webPage.getDriver().findElement(
						By.xpath((test[0][6]))));
			}
			s.selectByVisibleText(test[0][3]);
			CommonMethods.waitForGivenTime(4);
			CommonMethods.waitForWebElement(By.xpath(test[0][4]), webPage);
			List<WebElement> elementList = webPage.getDriver().findElements(
					By.xpath(test[0][4]));
			log.info("element Size-->" + elementList.size());
			boolean isSorted = mainPage.isSortedByName(elementList);
			log.info("element is shorted: " + isSorted);
			SoftAssertor
					.assertEquals(isSorted, true,
							"element is Not shorted by Product Name in Ascending order");
			// For Descending
			webPage.findObjectByxPath(test[0][5]).click();
			CommonMethods.waitForGivenTime(5);
			CommonMethods.waitForWebElement(By.xpath(test[0][4]), webPage);
			elementList = webPage.getDriver()
					.findElements(By.xpath(test[0][4]));
			log.info("element Size-->" + elementList.size());
			boolean isSortedDesc = mainPage.isSortedByNameDesc(elementList);
			log.info("element is shorted: " + isSortedDesc);
			SoftAssertor
					.assertEquals(isSortedDesc, true,
							"element is Not shorted by Product Name in Descending order");
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage,
					"Verify_Product_Search_And_Shorting_By_Product_Name");
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Error in Verify_Product_Search_And_Shorting_By_Product_Name :"
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			String errors = SoftAssertor.readErrorsForTest();
			if (errors != null && errors.length() > 0)
				SoftAssertor.displayErrors();
		}
	}

	@Test(priority = 705, enabled = true)
	public void Verify_Sorting_By_Product_Price_From_Product_Listing_Page()
			throws InterruptedException {
		try {
			CommonMethods.navigateToPage(webPage, url);
			String[][] test = ExcelUtil.readExcelData(DataFilePath,
					"ProductListingPage",
					"Sorting_By_Product_Price_From_Product_Listing_Page");
			ConnsProductPurchasePage.Click_On_French_Door_Link(webPage,
					test[0][0]);
			log.info("Clicked on French Door");
			Select s;
			if (testType.equalsIgnoreCase("Web")) {
				CommonMethods.waitForWebElement(By.xpath(test[0][2]), webPage);
				s = new Select(webPage.getDriver().findElement(
						By.xpath((test[0][2]))));
			} else {
				CommonMethods.waitForWebElement(By.xpath(test[0][6]), webPage);
				s = new Select(webPage.getDriver().findElement(
						By.xpath((test[0][6]))));
			}
			s.selectByVisibleText(test[0][3]);
			CommonMethods.waitForGivenTime(5);
			CommonMethods.waitForWebElement(By.xpath(test[0][4]), webPage);
			List<WebElement> elementPriceList = webPage.getDriver()
					.findElements(By.xpath(test[0][4]));
			log.info("element Size-->" + elementPriceList.size());
			boolean isSorted = mainPage.isSortedFloat(elementPriceList);
			log.info("element is shorted: " + isSorted);
			SoftAssertor.assertEquals(isSorted, true,
					"element is Not in Ascending order by price");
			// For Descending
			webPage.findObjectByxPath(test[0][5]).click();
			CommonMethods.waitForGivenTime(5);
			CommonMethods.waitForWebElement(By.xpath(test[0][4]), webPage);
			elementPriceList = webPage.getDriver().findElements(
					By.xpath(test[0][4]));
			log.info("element Size-->" + elementPriceList.size());
			boolean isSortedInDesc = mainPage
					.isSortedDescFloat(elementPriceList);
			log.info("element is shorted: " + isSortedInDesc);
			SoftAssertor.assertEquals(isSortedInDesc, true,
					"element is Not in Descending order by price");
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage,
					"Verify_Sorting_By_Product_Price_From_Product_Listing_Page");
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Error in Verify_Sorting_By_Product_Price_From_Product_Listing_Page :"
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			String errors = SoftAssertor.readErrorsForTest();
			if (errors != null && errors.length() > 0)
				SoftAssertor.displayErrors();
		}
	}
}
