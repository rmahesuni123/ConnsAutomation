package com.etouch.connsTests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.logging.Log;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
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
import com.etouch.connsPages.ConnsStoreLocatorPage;
import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.datamanager.excel.annotations.IExcelDataFiles;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.webui.selenium.LayoutManager;
import com.etouch.taf.webui.selenium.WebPage;

//@Test(groups = "Conns_Product_Search")
@IExcelDataFiles(excelDataFiles = { "CreditAppData=testData" })
public class Conns_Product_Search extends BaseTest {
	static String platform;
	static Log log = LogUtil.getLog(Conns_Product_Search.class);
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

	@Test(priority = 401, enabled = true)
	public void Verify_Search_Functionality_And_Results_Contents() {
		try {
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductSearch", "verifyProductSearchUsingKeyword");
			String Identifier = test[0][0];
			String ProductName = test[0][1];
			webPage.findObjectById(Identifier).clear();
			webPage.findObjectById(Identifier).sendKeys(ProductName);
			webPage.findObjectByClass(test[0][2]).click();
			log.info("Clicked on element " + test[0][2]);
			String productDescription = webPage.findObjectByxPath(test[0][3]).getText();
			log.info("productDescription" + productDescription);
			Assert.assertTrue(productDescription.contains(ProductName.substring(0,11)),
					"Product description: " + productDescription + " not having: " + ProductName);
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

	@Test(priority = 408, enabled = true)
	public void Verify_Product_Search_And_Number_Of_Product_Displayed() throws InterruptedException {
		try {
			if (testType.equalsIgnoreCase("Web")) {
				webPage.navigateToUrl(url);
				String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductSearch",
						"verifyProductSearchAndNumberPerPage");
				String Identifier = test[0][0];
				String ProductName = test[0][1];
				webPage.findObjectById(Identifier).clear();
				webPage.findObjectById(Identifier).sendKeys(ProductName);
				webPage.findObjectByClass(test[0][2]).click();
				log.info("Clicked on element " + test[0][2]);
				String productDescription = webPage.findObjectByxPath(test[0][3]).getText();
				log.info("productDescription" + productDescription);
				Assert.assertTrue(productDescription.contains(ProductName.substring(0,11)),
						"Product description: " + productDescription + " not having: " + ProductName);

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
					//Thread.sleep(5000);
					CommonMethods.waitForWebElement(By.xpath(test[0][7]), webPage);
					List<WebElement> elementList = webPage.getDriver().findElements(By.xpath(test[0][7]));
					log.info("Number: " + number + "    element Size-->" + elementList.size());
					SoftAssertor.assertEquals(elementList.size() <= number, true, "element is Not As Expected");
					log.info("Completed for iteration-->");
					s = new Select(webPage.getDriver().findElement(By.xpath((test[0][5]))));
				}
			}
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Product_Search_And_Number_Of_Product_Displayed");
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Error in Verify_Product_Search_And_Number_Of_Product_Displayed :" + e.getMessage());
			e.printStackTrace();
		} finally {
			String errors = SoftAssertor.readErrorsForTest();
			if (errors != null && errors.length() > 0)
				SoftAssertor.displayErrors();
		}
	}

	@Test(priority = 403, enabled = true)
	public void Verify_AutoPredict_For_Search_Functionality() {
		try {
			webPage.navigateToUrl(url);
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductSearch", "verifyAutoPredictProductSearch");
			// webPage.findObjectById(Identifier).clear();
			// CommonMethods.sendKeys_usingJS(webPage,".//*[@id='search']","French
			// Door");
			webPage.findObjectById(test[0][0]).sendKeys(test[0][1]);
			//Thread.sleep(10000);
			CommonMethods.waitForWebElement(By.xpath(test[0][2]), webPage);
			String autoSearchProductDescription = webPage.findObjectByxPath(test[0][2]).getText();
			webPage.findObjectByxPath(test[0][2]).click();
			log.info("Clicked on element ");
			String actualProductDescription = webPage.findObjectByxPath(test[0][3]).getText();
			log.info("productDescription" + actualProductDescription);
			Assert.assertTrue(actualProductDescription.contains(test[0][1]),
					"Product description: " + actualProductDescription + " not having: " + test[0][1]);
			Assert.assertEquals(autoSearchProductDescription, actualProductDescription,
					"Product" + autoSearchProductDescription + " is not same as: " + actualProductDescription);
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_AutoPredict_For_Search_Functionality");
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Error in Verify_AutoPredict_For_Search_Functionality :" + e.getMessage());
			e.printStackTrace();
		} finally {
			String errors = SoftAssertor.readErrorsForTest();
			if (errors != null && errors.length() > 0)
				SoftAssertor.displayErrors();
		}
	}
	@Test(priority = 404, enabled = true)
	public void Verify_Column_Layout_For_Product_Search() throws PageException, InterruptedException {
		try {
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductSearch",
					"verifyProductSearchAndShortByName");
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
						SoftAssertor.assertEquals(true, webPage.findObjectByxPath(test[0][4]).isDisplayed(),
								"Main Menu not displayed");
					} else {
						log.info("Column Layout equivalent to browser for column layout= " + cols);
						SoftAssertor.assertEquals(webPage.findObjectByxPath(test[0][4]).isDisplayed(), false,
								"Main Menu displayed");
					}
				}
				webPage.getDriver().manage().window().maximize();
			} else {
				log.info("Column layout testing can not be done for Devices");
			}
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Column_Layout_For_Product_Search");
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Error in Verify_Column_Layout_For_Product_Search :" + e.getMessage());
			e.printStackTrace();
		} finally {
			String errors = SoftAssertor.readErrorsForTest();
			if (errors != null && errors.length() > 0)
				SoftAssertor.displayErrors();
		}
	}

	@Test(priority = 405, enabled = true)
	public void Verify_Product_Search_And_Sorting_By_Product_Name() throws InterruptedException {
		try {
		//	if (testType.equalsIgnoreCase("Web")) {
				webPage.navigateToUrl(url);
				String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductSearch",
						"verifyProductSearchAndShortByName");
				String Identifier = test[0][0];
				String ProductName = test[0][1];
				webPage.findObjectById(Identifier).clear();
				webPage.findObjectById(Identifier).sendKeys(ProductName);
				webPage.findObjectByClass(test[0][2]).click();
				log.info("Clicked on element " + test[0][2]);
				String productDescription = webPage.findObjectByxPath(test[0][3]).getText();
				log.info("productDescription" + productDescription);
				Assert.assertTrue(productDescription.contains(ProductName.substring(0,11)),
						"Product description: " + productDescription + " not having: " + ProductName.substring(0,11));
				//Comment this code When sorting defect fixed
				/*Select s = new Select(webPage.getDriver().findElement(By.xpath((test[0][5]))));	
				s.selectByVisibleText(test[0][6]);	
				//Thread.sleep(18000);
				CommonMethods.waitForWebElement(By.xpath(test[0][5]), webPage);
				*/
				//End
				//Shorted in Ascending
				Select s = new Select(webPage.getDriver().findElement(By.xpath((test[0][5]))));
				s.selectByVisibleText(test[0][7]);
				//Thread.sleep(8000);
				CommonMethods.waitForWebElement(By.xpath(test[0][8]), webPage);
				
				List<WebElement> elementList = webPage.getDriver().findElements(By.xpath(test[0][8]));
				log.info("element Size-->" + elementList.size());
				boolean isSorted= mainPage.isSortedByName(elementList);
				log.info("element is shorted: " +isSorted);
				SoftAssertor.assertEquals(isSorted, true,
						"element is Not shorted by Product Name");
			
				//For Descending
				webPage.findObjectByxPath(test[0][9]).click();
				//Thread.sleep(5000);
				CommonMethods.waitForWebElement(By.xpath(test[0][8]), webPage);
				elementList = webPage.getDriver().findElements(By.xpath(test[0][8]));
				log.info("element Size-->" + elementList.size());
				boolean isSortedDesc= mainPage.isSortedByNameDesc(elementList);
				log.info("element is shorted: " +isSortedDesc);
				SoftAssertor.assertEquals(isSortedDesc, true,
						"element is Not shorted by Product Name in Desc");
				//webPage.getBackToUrl();
		//	}
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

	@Test(priority = 406, enabled = true)
	public void Verify_Product_Search_And_Sorting_By_Product_Price() throws InterruptedException {
		try {
			//if (testType.equalsIgnoreCase("Web")) {
				webPage.navigateToUrl(url);
				String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductSearch",
						"verifyProductSearchAndShortByPrice");
				String Identifier = test[0][0];
				String ProductName = test[0][1];
				webPage.findObjectById(Identifier).clear();
				webPage.findObjectById(Identifier).sendKeys(ProductName);
				webPage.findObjectByClass(test[0][2]).click();
				log.info("Clicked on element " + test[0][2]);
				String productDescription = webPage.findObjectByxPath(test[0][3]).getText();
				log.info("productDescription" + productDescription);
				Assert.assertTrue(productDescription.contains(ProductName.substring(0,11)),
						"Product description: " + productDescription + " not having: " + ProductName.substring(0,11));
				//Comment this code When sorting defect fixed
				/*Select s = new Select(webPage.getDriver().findElement(By.xpath((test[0][5]))));	
				s.selectByVisibleText(test[0][6]);
				//Thread.sleep(18000);
				CommonMethods.waitForWebElement(By.xpath(test[0][5]), webPage);
				//End */
				Select s = new Select(webPage.getDriver().findElement(By.xpath((test[0][5]))));
				s.selectByVisibleText(test[0][7]);
				//Thread.sleep(8000);
				CommonMethods.waitForWebElement(By.xpath(test[0][8]), webPage);
				List<WebElement> elementPriceList = webPage.getDriver().findElements(By.xpath(test[0][8]));
				log.info("element Size-->" + elementPriceList.size());
				boolean isSorted= mainPage.isSorted(elementPriceList);
				log.info("element is shorted: " +isSorted);
				SoftAssertor.assertEquals(isSorted, true, "element is Not shorted by price");
				
				//For Descending
				webPage.findObjectByxPath(test[0][9]).click();
				//Thread.sleep(5000);
				CommonMethods.waitForWebElement(By.xpath(test[0][8]), webPage);
				
				elementPriceList = webPage.getDriver().findElements(By.xpath(test[0][8]));
				log.info("element Size-->" + elementPriceList.size());
				boolean isSortedInDesc= mainPage.isSortedDesc(elementPriceList);
				log.info("element is shorted: " +isSortedInDesc);
				SoftAssertor.assertEquals(isSortedInDesc, true, "element is Not shorted by price");
				//webPage.getBackToUrl();
			//}
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Product_Search_And_Shorting_By_Product_Price");
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Error in Verify_Product_Search_And_Shorting_By_Product_Price :" + e.getMessage());
			e.printStackTrace();
		} finally {
			String errors = SoftAssertor.readErrorsForTest();
			if (errors != null && errors.length() > 0)
				SoftAssertor.displayErrors();
		}
	}

	
	@Test(priority = 407, enabled = true)
	public void Verify_Add_To_Cart_Using_Product_Search() throws InterruptedException {
		try {
			webPage.navigateToUrl(url);
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductSearch",
					"verifyAddToCartUsingProductSearch");
			String Identifier = test[0][0];
			String ProductName = test[0][1];
			// webPage.findObjectById(Identifier).clear();
			webPage.findObjectById(Identifier).sendKeys(ProductName);
			webPage.findObjectByClass(test[0][2]).click();
			log.info("Clicked on element " + test[0][2]);
			String productDescription = webPage.findObjectByxPath(test[0][3]).getText();
			log.info("productDescription" + productDescription);
			SoftAssertor.assertTrue(productDescription.contains(ProductName),
					"Product description: " + productDescription + " not having: " + ProductName);
			webPage.findObjectByxPath(test[0][4]).click();
			log.info("Entering Zip Code");
			webPage.findObjectByxPath(test[0][5]).clear();
			webPage.findObjectByxPath(test[0][5]).sendKeys(test[0][6]);
			webPage.findObjectByxPath(test[0][7]).click();
			if(CommonMethods.verifyElementisPresent(webPage,test[0][8]))
			{	
			Assert.assertEquals(webPage.findObjectByxPath(test[0][8]).getText(), test[0][9]);
			Assert.fail("Delivery not available at given location, Search for another product");
			}
			CommonMethods.closeLocationPopupForProductSearch(webPage);
			//Thread.sleep(5000);
			CommonMethods.waitForWebElement(By.xpath(test[0][10]), webPage);
			webPage.findObjectByxPath(test[0][10]).click();
			//Thread.sleep(5000);
			CommonMethods.waitForWebElement(By.xpath(test[0][11]), webPage);
			SoftAssertor.assertTrue(webPage.findObjectByxPath(test[0][11]).getText().contains(test[0][12]),
					"Shopping Cart: " + webPage.findObjectByxPath(test[0][11]).getText() + " not having: "
							+ test[0][12]);
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Add_To_Cart_Using_Product_Search");
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Error in Verify_Add_To_Cart_Using_Product_Search :" + e.getMessage());
			e.printStackTrace();
		} finally {
			String errors = SoftAssertor.readErrorsForTest();
			if (errors != null && errors.length() > 0)
				SoftAssertor.displayErrors();
		}
	}

}
