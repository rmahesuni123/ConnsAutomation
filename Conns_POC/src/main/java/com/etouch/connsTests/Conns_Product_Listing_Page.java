package com.etouch.connsTests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.etouch.common.BaseTest;
import com.etouch.common.CommonMethods;
import com.etouch.common.TafExecutor;
import com.etouch.conns.listener.SoftAssertor;
import com.etouch.connsPages.ConnsMainPage;
import com.etouch.connsPages.ConnsProductPurchasePage;
import com.etouch.connsPages.ProductListingPage;
import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.datamanager.excel.annotations.IExcelDataFiles;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
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
	protected static CommonMethods commonMethods = new CommonMethods();
	String[][] commonData;
	static String[][] Review_Data = null;
	static String browserName;
	ProductListingPage productListingPage = new ProductListingPage();

	@BeforeClass(alwaysRun = true)
	public void setUp(ITestContext context) throws InterruptedException, FileNotFoundException, IOException {
		try {
			testBedName = context.getCurrentXmlTest().getAllParameters().get("testBedName");
			CommonUtil.sop("Test bed Name is " + testBedName);
			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			log.info("Test Type is : " + testType);

			browserName = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getBrowser().getName();
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
					if (testBedName.equalsIgnoreCase("edge")) {
						webPage.getDriver().manage().window().maximize();
					}
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
		SoftAssert softAssert = new SoftAssert();
		JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
		try {
			String[][] contentData = null;
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductListingPage",
					"Verify_For_Pagination_And_Product_Details");
			CommonMethods.navigateToPage(webPage, test[0][11]);
			ConnsProductPurchasePage.Click_On_French_Door_Link(webPage, test[0][0]);
			if (testType.equalsIgnoreCase("Web")) {
				// verify pagination link 2
				WebElement paginationNumberElement = commonMethods.getWebElementbyXpath(webPage, test[0][2],
						softAssert);
				softAssert.assertEquals(paginationNumberElement.getText().trim(), "2",
						"Pagination failed for page link : 2 ");
				log.info("Clicking on Pagination Link : 2 ");
				commonMethods.clickElementbyXpath(webPage, test[0][2], softAssert);
				// paginationNumberElement.click();

				// verify pagination link 1
				paginationNumberElement = commonMethods.getWebElementbyXpath(webPage, test[0][3], softAssert);
				softAssert.assertEquals(paginationNumberElement.getText().trim(), "1",
						"Pagination failed for page link : 1 ");
				log.info("Clicking on Pagination Link : 1 ");
				commonMethods.clickElementbyXpath(webPage, test[0][3], softAssert);
				// paginationNumberElement.click();

				// verify next and back button
				WebElement paginationNextElement = commonMethods.getWebElementbyXpath(webPage, test[0][4], softAssert);
				softAssert.assertEquals(paginationNextElement.getText().trim(), "Next",
						"Pagination failed for page link : Next ");
				log.info("Clicking on Pagination Link : Next ");
				commonMethods.clickElementbyXpath(webPage, test[0][4], softAssert);
				// paginationNextElement.click();

				WebElement paginationBackElement = commonMethods.getWebElementbyXpath(webPage, test[0][5], softAssert);
				softAssert.assertEquals(paginationBackElement.getText().trim(), "Back",
						"Pagination failed for page link : Back ");
				log.info("Clicking on Pagination Link : Back");
				commonMethods.clickElementbyXpath(webPage, test[0][5], softAssert);
				// paginationBackElement.click();
			} // if testType is mobile
			else {
				// verify next and back button for mobile device
				WebElement paginationMobileNextElement = commonMethods.getWebElementbyXpath(webPage, test[0][6],
						softAssert);
				softAssert.assertEquals(paginationMobileNextElement.getText().trim(), "NEXT",
						"Pagination failed for page link : NEXT ");
				log.info("Clicking on Pagination Link : NEXT ");
				paginationMobileNextElement.click();

				WebElement paginationMobileBackElement = commonMethods.getWebElementbyXpath(webPage, test[0][7],
						softAssert);
				softAssert.assertEquals(paginationMobileBackElement.getText().trim(), "BACK",
						"Pagination failed for page link : BACK ");
				log.info("Clicking on Pagination Link : BACK ");
				paginationMobileBackElement.click();

			}

			// selecting product with Add To Cart Button
			commonMethods.clickElementbyXpath(webPage, test[0][8], softAssert);

			if (testType.equalsIgnoreCase("Mobile") || testBedName.equalsIgnoreCase("edge")) {
				contentData = ExcelUtil.readExcelData(DataFilePath, "ProductListingPage",
						"verifyProductDetailsForMobile");
			} else if (testType.equalsIgnoreCase("Web")) {
				contentData = ExcelUtil.readExcelData(DataFilePath, "ProductListingPage", "verifyProductDetails");
			}
			// closing delivery address popup
			if (commonMethods.verifyElementisPresent(webPage, test[0][10], softAssert)) {
				commonMethods.clickElementbyXpath(webPage, test[0][10], softAssert);
			}
			commonMethods.waitForGivenTime(5, softAssert);
			commonMethods.waitForPageLoad(webPage, softAssert);
			log.info("Verifying Product details");
			for (int i = 0; i < contentData.length; i++) {
				String actualContent = webPage.findObjectByxPath(contentData[i][0]).getText();

				log.info("Actual:  " + actualContent + "   Expected: " + contentData[i][1]);
				Assert.assertTrue(actualContent.equalsIgnoreCase(contentData[i][1]),
						"expectedContent: " + contentData[i][1] + "  Failed to Match Actual:" + actualContent);
			}

			/*************************************
			 * Verify Product Review
			 ***************************************/

			Review_Data = ExcelUtil.readExcelData(DataFilePath, "ProductListingPage", "Verify_Product_Review");
			log.info("Review_Data[25][0] :" + Review_Data[25][0]);

			// navigating to a particular product page to insert comment
			CommonMethods.navigateToPage(webPage, Review_Data[25][0]);

			// webPage.getDriver().navigate().refresh();
			// closing delivery address popup
			if (commonMethods.verifyElementisPresent(webPage, test[0][10], softAssert)) {
				commonMethods.clickElementbyXpath(webPage, test[0][10], softAssert);
			}

			// verify if rating stars are displayed
			if (CommonMethods.verifyElementisPresent(webPage, test[0][9])) {
				commonMethods.clickElementbyXpath(webPage, test[0][9], softAssert);
			}
			// webPage.findObjectByxPath(Review_Data[0][0]).click();

			// verify if review section is displayed
			if ((CommonMethods.verifyElementisPresent(webPage, Review_Data[1][0]))) {
				log.info("In If");
				if (testType.equalsIgnoreCase("Web")) {

					// clicking on review tab
					commonMethods.clickElementbyXpath(webPage, Review_Data[1][0], softAssert);

					// click on wirte the first review
					commonMethods.clickElementbyXpath(webPage, Review_Data[2][0], softAssert);
					CommonMethods.waitForGivenTime(1);

				} else {
					log.info("In else");

					commonMethods.scrollToElement(webPage, Review_Data[2][0], softAssert);
					// webPage.findObjectByxPath(Review_Data[1][0]).click();
					WebElement element = webPage.getDriver().findElement(By.xpath(Review_Data[2][0]));
					js.executeScript("arguments[0].click();", element);
					// webPage.findObjectByxPath(Review_Data[2][0]).click();
					CommonMethods.waitForGivenTime(2);
				}
				Thread.sleep(5000);
				submitReview(webPage, softAssert,
						ExcelUtil.readExcelData(DataFilePath, "ProductListingPage", "submitReview"), true);
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_For_Pagination_And_Product_Details");
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Error in Verify_For_Pagination_And_Product_Details :" + e.getMessage());
			e.printStackTrace();
		} finally {
			String errors = SoftAssertor.readErrorsForTest();
			if (errors != null && errors.length() > 0)
				SoftAssertor.displayErrors();
		}
	}

	public void submitReview(WebPage webPage, SoftAssert softAssert, String[][] labelsTextData,
			boolean validateAllFields) throws Exception {
		if (validateAllFields) {
			log.info("validateAllFields : " + validateAllFields);
			// String[][] reviewLabelsData =ExcelUtil.readExcelData(DataFilePath,
			// "CreditApp","reviewLabelsData");
			commonMethods.verifyLabels(webPage, softAssert,
					ExcelUtil.readExcelData(DataFilePath, "ProductListingPage", "reviewLabelsData"));
			Thread.sleep(3000);
			commonMethods.verifyLabelsErrorColor(webPage, softAssert,
					ExcelUtil.readExcelData(DataFilePath, "ProductListingPage", "reviewDataLabelsErrorColor"));
			// webPage.navigateToUrl( webPage.getCurrentUrl());
			Thread.sleep(5000);
		}
		commonMethods.fillFormWithOutJS(webPage, softAssert, labelsTextData);
		Thread.sleep(5000);
		commonMethods.waitForPageLoad(webPage, softAssert);
		commonMethods.verifyLabels(webPage, softAssert,
				ExcelUtil.readExcelData(DataFilePath, "ProductListingPage", "reviewThankYouPageData"));
		commonMethods.verifyDate(webPage, softAssert, Review_Data[22][0]);
		Thread.sleep(5000);
	}

	@Test(priority = 702, enabled = true)
	public void Verify_Upto_5_Products_Compared() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
		SoftAssert softAssert = new SoftAssert();
		try {
			if (testType.equalsIgnoreCase("Web")) {

				String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductListingPage",
						"Verify_Upto_5_Products_Compared");
				/*
				 * ConnsProductPurchasePage.Click_On_French_Door_Link(webPage, test[0][1]);
				 */
				/*****
				 * Asim : old code for Click_On_French_Door_Link commented & instead using
				 * javascript for clicking operation
				 ******/
				CommonMethods.navigateToPage(webPage, test[0][9]);
				WebElement element3 = webPage.getDriver().findElement(By.xpath(test[0][1]));
				js.executeScript("arguments[0].click();", element3);
				log.info("Clicked on French Door");
				List<WebElement> productList = webPage.getDriver().findElements(By.xpath(
						"//div[@class='category-products category-products-list']/ul[contains(@class,'products-grid')]/li[contains(@class,'item')]"));

				List<String> productSelectedList = new ArrayList<String>();
				log.info("Total Size-->" + productList.size());

				String xpath = "(//div[@class='category-products category-products-list']/ul[contains(@class,'products-grid')]/li[contains(@class,'item')])[";
				String xpath2 = "]//input";
				String xpath3 = "]//h2/a";
				for (int i = 1; i <= 5; i++) {
					CommonMethods.waitForGivenTime(3);

					if (i <= 5) {

						commonMethods.clickElementbyXpath(webPage, xpath + i + xpath2, softAssert);
						productSelectedList.add(commonMethods.getTextbyXpath(webPage, xpath + i + xpath3, softAssert));
						/*
						 * WebElement ele = productList.get(i);
						 * ele.findElement(By.xpath(".//input")).click();
						 * productSelectedList.add(ele.findElement(By.xpath(".//h2/a")).getText());
						 */
						log.info("Product " + i + " : "
								+ commonMethods.getTextbyXpath(webPage, xpath + i + xpath3, softAssert));
					}
					// else {
					if (testBedName.contains("iPadNative") || testBedName.contains("iPhoneNative")
							|| testBedName.equalsIgnoreCase("Safari")) {

						js.executeScript("window.alert = function(){ return true;}");
						// compareList.get(i).click();
						// productList.get(i).findElement(By.xpath(".//input")).click();
						CommonMethods.waitForGivenTime(2);
					} else {
						// productList.get(i).findElement(By.xpath(".//input")).click();
						// compareList.get(i).click();
						try {
							Alert alert = webPage.getDriver().switchTo().alert();
							Assert.assertTrue(alert.getText().contains("Only 5 items to compare"),
									"Alert Message does not contain: " + "Only 5 items to compare");
							alert.accept();
						} catch (NoAlertPresentException Ex) {
							log.info("Alert is not present");
						}
					}
					// }
				}
				// getting text from selection overlay
				log.info(webPage.findObjectByxPath(test[0][3]).getText());
				log.info(webPage.findObjectByxPath(test[0][4]).getText());
				// clicking on compare now
				commonMethods.clickElementbyXpath(webPage, test[0][5], softAssert);
				commonMethods.waitForPageLoad(webPage, softAssert);
				// wait for Compare Product Title to be displayed
				CommonMethods.waitForWebElement(By.xpath(test[0][6]), webPage);
				log.info(webPage.findObjectByxPath(test[0][6]).getText());
				commonMethods.waitForPageLoad(webPage, softAssert);
				commonMethods.waitForGivenTime(5, softAssert);
				if ((browserName.equalsIgnoreCase("Safari") || browserName.contains("iPhone")
						|| browserName.contains("iPad"))) {
					CommonMethods.waitForGivenTime(20);
				}
				List<WebElement> newListWebElement = webPage.getDriver().findElements(By.xpath(test[0][7]));

				List<String> compareList = new ArrayList<String>();
				log.info(newListWebElement.get(4).getText());
				for (WebElement e : newListWebElement) {
					compareList.add(e.getText());
				}
				for (String productName : compareList) {
					softAssert.assertTrue(productSelectedList.contains(productName), " Actual : " + productName);
				}
				softAssert.assertAll();
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
		JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
		try {
			CommonMethods.navigateToPage(webPage, url);
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductListingPage",
					"Number_Of_Product_Displayed_From_Product_Listing_Page");
			/*
			 * ConnsProductPurchasePage.Click_On_French_Door_Link(webPage, test[0][0]);
			 */

			/*****
			 * Asim : old code for Click_On_French_Door_Link commented & instead using
			 * javascript for clicking operation
			 ******/

			WebElement element3 = webPage.getDriver().findElement(By.xpath(test[0][0]));
			js.executeScript("arguments[0].click();", element3);

			log.info("Clicked on French Door");
			String str2[] = test[0][3].split(",");
			// Old code modified : if else condition modified
			if (testType.equalsIgnoreCase("Mobile") || testBedName.equalsIgnoreCase("edge")) {
				log.info("Mobile or Edge :  " + testType + " " + testBedName);
				for (int i = 0; i < str2.length; i++) {
					ConnsProductPurchasePage.Click_On_Element_JS(webPage, test[0][7 + i]);
					CommonMethods.waitForGivenTime(5);
					List<WebElement> list = webPage.getDriver().findElements(By.xpath(test[0][9]));
					log.info("list.size():: " + list.size());
					log.info("Integer.parseInt(str2[i]):: " + Integer.parseInt(str2[i]));
					SoftAssertor.assertEquals(list.size() <= Integer.parseInt(str2[i]), true,
							"Number of element is not as expected---->Actual Size: " + list.size()
									+ " Should be less than: " + Integer.parseInt(str2[i]));
				}
			}

			else if (testType.equalsIgnoreCase("Web")) {

				CommonMethods.waitForGivenTime(5);
				Select s = new Select(webPage.getDriver().findElement(By.xpath((test[0][2]))));
				List<WebElement> list = s.getOptions();
				String str[] = { list.get(0).getText().toString(), list.get(1).getText().toString(),
						list.get(2).getText().toString() };
				int number;
				for (int i = 0; i < str.length; i++) {
					number = Integer.parseInt(str[i].trim());
					s.selectByVisibleText(String.valueOf(number));
					CommonMethods.waitForGivenTime(5);
					CommonMethods.waitForWebElement(By.xpath(test[0][4]), webPage);
					List<WebElement> elementList = webPage.getDriver().findElements(By.xpath(test[0][4]));
					SoftAssertor.assertEquals(elementList.size() <= number, true,
							"Number of element is not as expected---->Actual Size: " + elementList.size()
									+ " Should be less than: " + number);
					s = new Select(webPage.getDriver().findElement(By.xpath((test[0][2]))));
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
		JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
		try {
			CommonMethods.navigateToPage(webPage, url);
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductListingPage",
					"Sorting_By_Product_Name_From_Product_Listing_Page");
			/*
			 * ConnsProductPurchasePage.Click_On_French_Door_Link(webPage, test[0][0]);
			 */
			/*****
			 * Asim : old code for Click_On_French_Door_Link commented & instead using
			 * javascript for clicking operation
			 ******/

			WebElement element3 = webPage.getDriver().findElement(By.xpath(test[0][0]));
			js.executeScript("arguments[0].click();", element3);

			log.info("Clicked on French Door");
			Select s;
			if (testType.equalsIgnoreCase("Web") || (testBedName.contains("iPadNative"))) {
				CommonMethods.waitForWebElement(By.xpath(test[0][2]), webPage);
				s = new Select(webPage.getDriver().findElement(By.xpath((test[0][2]))));
			} else {
				CommonMethods.waitForWebElement(By.xpath(test[0][6]), webPage);
				s = new Select(webPage.getDriver().findElement(By.xpath((test[0][6]))));
			}
			s.selectByVisibleText(test[0][3]);
			CommonMethods.waitForGivenTime(4);
			CommonMethods.waitForWebElement(By.xpath(test[0][4]), webPage);
			if ((browserName.equalsIgnoreCase("Safari") || browserName.contains("iPhone")
					|| browserName.contains("iPad"))) {
				CommonMethods.waitForGivenTime(50);
			}
			List<WebElement> elementList = webPage.getDriver().findElements(By.xpath(test[0][4]));
			log.info("element Size-->" + elementList.size());
			boolean isSorted = mainPage.isSortedByName(elementList);

			SoftAssertor.assertEquals(isSorted, true, "Element is Not shorted by Product Name in Ascending order");
			// For Descending
			webPage.findObjectByxPath(test[0][5]).click();
			CommonMethods.waitForGivenTime(5);
			CommonMethods.waitForWebElement(By.xpath(test[0][4]), webPage);
			if ((browserName.equalsIgnoreCase("Safari") || browserName.contains("iPhone")
					|| browserName.contains("iPad"))) {
				CommonMethods.waitForGivenTime(50);
			}
			elementList = webPage.getDriver().findElements(By.xpath(test[0][4]));
			log.info("element Size-->" + elementList.size());
			boolean isSortedDesc = mainPage.isSortedByNameDesc(elementList);
			// log.info("element is shorted: " + isSortedDesc);
			SoftAssertor.assertEquals(isSortedDesc, true, "element is Not shorted by Product Name in Descending order");
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Product_Search_And_Shorting_By_Product_Name");
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Error in Verify_Product_Search_And_Shorting_By_Product_Name :" + e.getMessage());
			e.printStackTrace();
		} finally {
			String errors = SoftAssertor.readErrorsForTest();
			if (errors != null && errors.length() > 0)
				SoftAssertor.displayErrors();
		}
	}

	@Test(priority = 705, enabled = true)
	public void Verify_Sorting_By_Product_Price_From_Product_Listing_Page() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
		try {
			CommonMethods.navigateToPage(webPage, url);
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductListingPage",
					"Sorting_By_Product_Price_From_Product_Listing_Page");
			/*
			 * ConnsProductPurchasePage.Click_On_French_Door_Link(webPage, test[0][0]);
			 */
			/*****
			 * Asim : old code for Click_On_French_Door_Link commented & instead using
			 * javascript for clicking operation
			 ******/

			WebElement element3 = webPage.getDriver().findElement(By.xpath(test[0][0]));
			js.executeScript("arguments[0].click();", element3);
			log.info("Clicked on French Door");
			Select s;
			if (testType.equalsIgnoreCase("Web") || (testBedName.contains("iPadNative"))) {
				CommonMethods.waitForWebElement(By.xpath(test[0][2]), webPage);
				s = new Select(webPage.getDriver().findElement(By.xpath((test[0][2]))));
			} else {
				CommonMethods.waitForWebElement(By.xpath(test[0][6]), webPage);
				s = new Select(webPage.getDriver().findElement(By.xpath((test[0][6]))));
			}
			s.selectByVisibleText(test[0][3]);
			CommonMethods.waitForGivenTime(5);
			CommonMethods.waitForWebElement(By.xpath(test[0][4]), webPage);
			if ((browserName.equalsIgnoreCase("Safari") || browserName.contains("iPhone")
					|| browserName.contains("iPad"))) {
				CommonMethods.waitForGivenTime(70);
			}
			List<WebElement> elementPriceList = webPage.getDriver().findElements(By.xpath(test[0][4]));
			log.info("element Size-->" + elementPriceList.size());
			boolean isSorted = mainPage.isSortedFloat(elementPriceList);
			log.info("element is shorted: " + isSorted);
			SoftAssertor.assertEquals(isSorted, true, "element is Not in Ascending order by price");
			// For Descending
			webPage.findObjectByxPath(test[0][5]).click();
			CommonMethods.waitForGivenTime(5);
			CommonMethods.waitForWebElement(By.xpath(test[0][4]), webPage);
			if ((browserName.equalsIgnoreCase("Safari") || browserName.contains("iPhone")
					|| browserName.contains("iPad"))) {
				CommonMethods.waitForGivenTime(50);
			}
			elementPriceList = webPage.getDriver().findElements(By.xpath(test[0][4]));
			log.info("element Size-->" + elementPriceList.size());
			boolean isSortedInDesc = mainPage.isSortedDescFloat(elementPriceList);
			log.info("element is shorted: " + isSortedInDesc);
			SoftAssertor.assertEquals(isSortedInDesc, true, "element is Not in Descending order by price");
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

	/*
	 * This method will cover below scenarios - Verify Refine by Filter options on
	 * Product listing page. This method will be used to apply filters as per the
	 * filter criteria on the Product listing page-each filter one-by-one, clear
	 * filter & perform a check if filter is cleared before beginning next
	 * filter operation
	 */
	@Test(priority = 706, enabled = true)
	public void Verify_Refine_By_Product_Listing_page_Single_Filters() {
		SoftAssert softAssert = new SoftAssert();
		try {
			// navigate to PLP
			String[][] testdata1 = ExcelUtil.readExcelData(DataFilePath, "ProductListingPage", "Refine_by_URL");
			CommonMethods.navigateToPage(webPage, testdata1[0][1]);
			if (testType.equalsIgnoreCase("Web")) {
				webPage.getDriver().manage().window().maximize();
			}
			CommonMethods.waitForGivenTime(5);

			String[][] RefineByElements = ExcelUtil.readExcelData(DataFilePath, "ProductListingPage",
					"Refine_by_Elements");
			String[][] Refine_By_Price = ExcelUtil.readExcelData(DataFilePath, "ProductListingPage", "Refine_By_Price");
			//String[][] Refine_By_Price = ExcelUtil.readExcelDataWithDouble(DataFilePath, "ProductListingPage", "Refine_By_Price");
			String[][] Refine_By_Monthly_payment = ExcelUtil.readExcelData(DataFilePath, "ProductListingPage",
					"Refine_By_Monthly_payment");
			String[][] Refine_By_Brand = ExcelUtil.readExcelData(DataFilePath, "ProductListingPage", "Refine_By_Brand");

			log.info("Applying single filter operation first for Price, Monthly payment & Brand each one-by-one");

			// Select filter for Price & check if filter applied correctly
			if (testType.equalsIgnoreCase("Mobile")) {
				// click on Refine by dropdown on mobile for filter options to be visible
				commonMethods.findElementByXpath(webPage, RefineByElements[6][1], softAssert).click();
			}

			log.info(
					"Selecting Filter for Price from the Filter options available on page & verifying if products filtered correctly");
			productListingPage.Select_Filter_Price(Refine_By_Price, RefineByElements, softAssert);
			boolean filterApplied_Price = productListingPage.Verify_Filter_Applied_By_Price(Refine_By_Price,
					 softAssert);
			log.info("Filter is applied correctly for Price--> " + Refine_By_Price[2][0] + "-" + Refine_By_Price[3][0]
					+ ":" + filterApplied_Price);
			softAssert.assertEquals(filterApplied_Price, true,
					"Elements are Not filtered for Price Range:");

			// Clearing filter
			productListingPage.Clear_Filter(RefineByElements, softAssert);

			// Select filter for Monthly Payment & check if filter applied correctly
			if (testType.equalsIgnoreCase("Mobile")) {
				// click on Refine by dropdown on mobile for filter options to be visible
				commonMethods.findElementByXpath(webPage, RefineByElements[6][1], softAssert).click();
			}
			if ((testBedName.equalsIgnoreCase("iPhoneNative")) || (testBedName.equalsIgnoreCase("InternetExplorer"))) {
				commonMethods.scrollDown(webPage, 2);
			}
			log.info(
					"Selecting Filter for Monthly payment from the Filter options available on page & verifying if products filtered correctly");
			productListingPage.Select_Filter_Monthly_Payment(Refine_By_Monthly_payment, RefineByElements, softAssert);
			boolean filterApplied_MonthlyPayment = productListingPage
					.ApplyFilter_RefineBy_Monthly_Payment(Refine_By_Monthly_payment, softAssert);
			log.info("Filter is applied correctly for Monthly Payment-->" + Refine_By_Monthly_payment[2][0] + "-"
					+ Refine_By_Monthly_payment[3][0] + ":" + filterApplied_MonthlyPayment);
			softAssert.assertEquals(filterApplied_MonthlyPayment, true,
					"Elements are not filtered for Monthly Payment--> $25-$49 ");

			// Clearing filter
			productListingPage.Clear_Filter(RefineByElements, softAssert);

			// Select filter for Brand & check if filter applied correctly
			if (testType.equalsIgnoreCase("Mobile")) {
				// click on Refine by dropdown on mobile for filter options to be visible
				commonMethods.findElementByXpath(webPage, RefineByElements[6][1], softAssert).click();
			}
			if ((testBedName.equalsIgnoreCase("iPhoneNative")) || (testBedName.equalsIgnoreCase("InternetExplorer"))) {
				commonMethods.scrollDown(webPage, 2);
			}

			log.info(
					"Selecting Filter for Brand Name from the Filter options available on page & verifying if products filtered correctly");
			productListingPage.Select_Filter_Brand(Refine_By_Brand, RefineByElements, softAssert);
			boolean filterApplied_Brand = productListingPage.ApplyFilter_RefineBy_Brand(Refine_By_Brand,
					softAssert);
			log.info("Filter is applied correctly for Brand Name --> " + Refine_By_Brand[2][0] + "-"
					+ filterApplied_Brand);
			softAssert.assertEquals(filterApplied_Brand, true, "Elements are not filtered for Brand Name-->Sealy: ");

			// Clearing filter
			productListingPage.Clear_Filter(RefineByElements, softAssert);

			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_Refine_By_Product_Listing_page_Single_Filters");
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Verify_Refine_By_Product_Listing_page_Single_Filters :" + e.getMessage());
		}
	}

	/*
	 * This method will cover below scenarios - Verify Refine by Filter for multiple options on
	 * Product listing page. This method will be used to apply multiple filters as
	 * per the filter criteria on the Product listing page
	 */
	@Test(priority = 707, enabled = true)
	public void Verify_Refine_By_Product_Listing_page_Multiple_Filters() {
		SoftAssert softAssert = new SoftAssert();
		try {
			// navigate to PLP
			String[][] testdata1 = ExcelUtil.readExcelData(DataFilePath, "ProductListingPage", "Refine_by_URL");
			CommonMethods.navigateToPage(webPage, testdata1[0][1]);
			if (testType.equalsIgnoreCase("Web")) {
				webPage.getDriver().manage().window().maximize();
			}
			CommonMethods.waitForGivenTime(5);

			String[][] RefineByElements = ExcelUtil.readExcelData(DataFilePath, "ProductListingPage",
					"Refine_by_Elements");
			String[][] Refine_By_Price = ExcelUtil.readExcelData(DataFilePath, "ProductListingPage", "Refine_By_Price");
			//String[][] Refine_By_Price = ExcelUtil.readExcelDataWithDouble(DataFilePath, "ProductListingPage", "Refine_By_Price");
			String[][] Refine_By_Monthly_payment = ExcelUtil.readExcelData(DataFilePath, "ProductListingPage",
					"Refine_By_Monthly_payment");
			String[][] Refine_By_Brand = ExcelUtil.readExcelData(DataFilePath, "ProductListingPage", "Refine_By_Brand");

			log.info("Applying multiple filter operation for Price, Monthly payment & Brand at once");
			// Applying multiple filters
			if (testType.equalsIgnoreCase("Mobile")) {
				// click on Refine by dropdown on mobile for filter options to be visible
				commonMethods.findElementByXpath(webPage, RefineByElements[6][1], softAssert).click();
			}
			if ((testBedName.equalsIgnoreCase("iPhoneNative")) || (testBedName.equalsIgnoreCase("InternetExplorer"))) {
				commonMethods.scrollDown(webPage, 2);
			}
			productListingPage.Select_Multiple_Filters(Refine_By_Price, Refine_By_Monthly_payment, Refine_By_Brand,
					RefineByElements, softAssert);
			boolean are_Mutiple_Filters_Applied = productListingPage.ApplyFilter_RefineBy_Multiple_Filters(
					Refine_By_Price, Refine_By_Monthly_payment, Refine_By_Brand, RefineByElements, softAssert);
			log.info("Multiple Filters are applied correctly for Price, Monthly Payment & Brand name at once: "
					+ are_Mutiple_Filters_Applied);
			softAssert.assertEquals(are_Mutiple_Filters_Applied, true,
					"Elements are not filtered for Multiple conditions: ");
			softAssert.assertAll();

		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_Refine_By_Product_Listing_page_Multiple_Filters");
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Verify_Refine_By_Product_Listing_page_Multiple_Filters :" + e.getMessage());
		}
	}
}
