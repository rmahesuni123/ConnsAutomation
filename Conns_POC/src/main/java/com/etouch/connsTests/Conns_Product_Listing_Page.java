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
	protected static CommonMethods commonMethods = new CommonMethods();
	String[][] commonData;
	static String[][] Review_Data= null;	

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
				/*commonData = ExcelUtil.readExcelData(DataFilePath, "ProductListingPage",
						"ProductListingPageCommonElements");*/
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
		SoftAssert softAssert = new SoftAssert();
		JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
		try {
			String[][] contentData = null;
			String[][] test = ExcelUtil.readExcelData(DataFilePath,
					"ProductListingPage",
					"Verify_For_Pagination_And_Product_Details");
			CommonMethods.navigateToPage(webPage, test[0][11]);
			ConnsProductPurchasePage.Click_On_French_Door_Link(webPage,
					test[0][0]);
			if(testType.equalsIgnoreCase("Web"))
			{
				//verify pagination link 2
				WebElement paginationNumberElement = commonMethods.getWebElementbyXpath(webPage, test[0][2], softAssert);
				softAssert.assertEquals(paginationNumberElement.getText(),"2","Pagination failed for page link : 2 ");
				log.info("Clicking on Pagination Link : 2 ");
				paginationNumberElement.click();

				//verify pagination link 1
				paginationNumberElement = commonMethods.getWebElementbyXpath(webPage, test[0][3], softAssert);
				softAssert.assertEquals(paginationNumberElement.getText(),"1","Pagination failed for page link : 1 ");
				log.info("Clicking on Pagination Link : 1 ");
				paginationNumberElement.click();

				//verify next and back button
				WebElement paginationNextElement = commonMethods.getWebElementbyXpath(webPage, test[0][4], softAssert);
				softAssert.assertEquals(paginationNextElement.getText(),"Next","Pagination failed for page link : Next ");
				log.info("Clicking on Pagination Link : Next ");
				paginationNextElement.click();


				WebElement paginationBackElement = commonMethods.getWebElementbyXpath(webPage, test[0][5], softAssert);
				softAssert.assertEquals(paginationBackElement.getText(),"Back","Pagination failed for page link : Back ");
				log.info("Clicking on Pagination Link : Back");
				paginationBackElement.click();
			}//if testType is mobile
			else{
				//verify next and back button for mobile device
				WebElement paginationMobileNextElement = commonMethods.getWebElementbyXpath(webPage, test[0][6], softAssert);
				softAssert.assertEquals(paginationMobileNextElement.getText(),"NEXT","Pagination failed for page link : NEXT ");
				log.info("Clicking on Pagination Link : NEXT ");
				paginationMobileNextElement.click();

				WebElement paginationMobileBackElement = commonMethods.getWebElementbyXpath(webPage, test[0][7], softAssert);
				softAssert.assertEquals(paginationMobileBackElement.getText(),"BACK","Pagination failed for page link : BACK ");
				log.info("Clicking on Pagination Link : BACK ");
				paginationMobileBackElement.click();

			}

			//selecting product with Add To Cart Button
			commonMethods.clickElementbyXpath(webPage, test[0][8], softAssert);

			if (testType.equalsIgnoreCase("Mobile") || testBedName.equalsIgnoreCase("edge")){
				contentData = ExcelUtil.readExcelData(DataFilePath,
						"ProductListingPage", "verifyProductDetailsForMobile");
			}
			else if (testType.equalsIgnoreCase("Web")) {
				contentData = ExcelUtil.readExcelData(DataFilePath,
						"ProductListingPage", "verifyProductDetails");
			}
			// closing delivery address popup
			if(commonMethods.verifyElementisPresent(webPage, test[0][10], softAssert))
			{
				commonMethods.clickElementbyXpath(webPage, test[0][10], softAssert);
			}
			commonMethods.waitForGivenTime(5, softAssert);
			commonMethods.waitForPageLoad(webPage, softAssert);
			log.info("Verifying Product details");
			for (int i = 0; i < contentData.length; i++) {
				String actualContent = webPage.findObjectByxPath(
						contentData[i][0]).getText();

				log.info("Actual:  " + actualContent + "   Expected: "
						+ contentData[i][1]);
				Assert.assertTrue(
						actualContent.equalsIgnoreCase(contentData[i][1]),
						"expectedContent: " + contentData[i][1]
								+ "  Failed to Match Actual:" + actualContent);
			}

			/************************************* Verify Product Review ***************************************/


			Review_Data = ExcelUtil.readExcelData(DataFilePath,"ProductListingPage", "Verify_Product_Review");
			System.out.println("Review_Data[25][0] :" +Review_Data[25][0]);

			//navigating to a particular product page to insert comment
			CommonMethods.navigateToPage(webPage, Review_Data[25][0]);

			//webPage.getDriver().navigate().refresh();
			// closing delivery address popup
			if(commonMethods.verifyElementisPresent(webPage, test[0][10], softAssert))
			{
				commonMethods.clickElementbyXpath(webPage, test[0][10], softAssert);
			}

			//verify if rating stars are displayed
			if (CommonMethods.verifyElementisPresent(webPage, test[0][9])) {
				commonMethods.clickElementbyXpath(webPage,test[0][9],softAssert);
			}
			//webPage.findObjectByxPath(Review_Data[0][0]).click();

			//verify if review section is displayed
			if ((CommonMethods.verifyElementisPresent(webPage, Review_Data[1][0]))) {	
				System.out.println("In If");
				if (testType.equalsIgnoreCase("Web")) {
					
					//clicking on review tab
					commonMethods.clickElementbyXpath(webPage, Review_Data[1][0], softAssert);
					
					//click on wirte the first review
					commonMethods.clickElementbyXpath(webPage,Review_Data[2][0],softAssert);
					CommonMethods.waitForGivenTime(1);

				}else {
					System.out.println("In else");
					
					commonMethods.scrollToElement(webPage, Review_Data[2][0], softAssert);
					//webPage.findObjectByxPath(Review_Data[1][0]).click();
					WebElement element = webPage.getDriver().findElement(By.xpath(Review_Data[2][0]));					
					js.executeScript("arguments[0].click();", element);
					//webPage.findObjectByxPath(Review_Data[2][0]).click();
					CommonMethods.waitForGivenTime(2);
				}
				Thread.sleep(5000);
				submitReview(webPage,softAssert, ExcelUtil.readExcelData(DataFilePath, "ProductListingPage","submitReview"), true);
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage,"Verify_For_Pagination_And_Product_Details");
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Error in Verify_For_Pagination_And_Product_Details :"+ e.getMessage());
			e.printStackTrace();
		} finally {
			String errors = SoftAssertor.readErrorsForTest();
			if (errors != null && errors.length() > 0)
				SoftAssertor.displayErrors();
		}
	}

	@Test(priority = 702, enabled = true)
	public void Verify_Upto_5_Products_Compared() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor)webPage.getDriver();
		SoftAssert softAssert = new SoftAssert();
		try {
			if (testType.equalsIgnoreCase("Web")) {
				
				String[][] test = ExcelUtil
						.readExcelData(DataFilePath, "ProductListingPage",
								"Verify_Upto_5_Products_Compared");
				/*ConnsProductPurchasePage.Click_On_French_Door_Link(webPage,
						test[0][1]);*/
				/*****Asim : old code for Click_On_French_Door_Link commented & instead using javascript for clicking operation******/
				CommonMethods.navigateToPage(webPage, test[0][9]);
				WebElement element3 = webPage.getDriver().findElement(By.xpath(test[0][1]));					
				js.executeScript("arguments[0].click();", element3);
				log.info("Clicked on French Door");
				List<WebElement> productList = webPage.getDriver().findElements(By.xpath("//div[@class='category-products category-products-list']/ul[contains(@class,'products-grid')]/li[contains(@class,'item')]"/*test[0][8]*/));
				
				List<String> productSelectedList = new ArrayList<String>();
				log.info("Total Size-->" + productList.size());
				
				for (int i = 0; i < 5; i++) {
					CommonMethods.waitForGivenTime(3);
					
					if (i < 5) {
						WebElement ele = productList.get(i);
						ele.findElement(By.xpath(".//input")).click();
						productSelectedList.add(ele.findElement(By.xpath(".//h2/a")).getText());
						System.out.println(" "+i+". "+ele.findElement(By.xpath(".//h2/a")).getText());
					}
					//else {
						if (testBedName.contains("iPadNative")
								|| testBedName.contains("iPhoneNative")
								|| testBedName.equalsIgnoreCase("Safari")) {

							js.executeScript("window.alert = function(){ return true;}");
							//compareList.get(i).click();
						//	productList.get(i).findElement(By.xpath(".//input")).click();
							CommonMethods.waitForGivenTime(2);
						} else {
							//productList.get(i).findElement(By.xpath(".//input")).click();
							//compareList.get(i).click();
							try{
							Alert alert = webPage.getDriver().switchTo()
									.alert();
							Assert.assertTrue(
									alert.getText().contains(
											"Only 5 items to compare"),
									"Alert Message does not contain: "
											+ "Only 5 items to compare");
							alert.accept();
							}
							catch(NoAlertPresentException Ex){
								log.info("Alert is not present");
							}
						}
					//}
				}
				//getting text from selection overlay 
				log.info(webPage.findObjectByxPath(test[0][3]).getText());
				log.info(webPage.findObjectByxPath(test[0][4]).getText());
				//clicking on compare now
				commonMethods.clickElementbyXpath(webPage, test[0][5], softAssert);
				commonMethods.waitForPageLoad(webPage, softAssert);
				//wait for Compare Product Title to be displayed
				CommonMethods.waitForWebElement(By.xpath(test[0][6]), webPage);
				log.info(webPage.findObjectByxPath(test[0][6]).getText());
				commonMethods.waitForPageLoad(webPage, softAssert);
				commonMethods.waitForGivenTime(5, softAssert);
				List<WebElement> newListWebElement = webPage.getDriver().findElements(By.xpath(test[0][7]));
				
				List<String> compareList = new ArrayList<String>();
				log.info(newListWebElement.get(4).getText());
				for(WebElement e : newListWebElement)
				{
					compareList.add(e.getText());	
				}
				for(String productName : compareList)
				{
					softAssert.assertTrue(productSelectedList.contains(productName)," Actual : "+productName);
				}
				softAssert.assertAll();
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
		JavascriptExecutor js = (JavascriptExecutor)webPage.getDriver();
		try {
			CommonMethods.navigateToPage(webPage, url);
			String[][] test = ExcelUtil.readExcelData(DataFilePath,
					"ProductListingPage",
					"Number_Of_Product_Displayed_From_Product_Listing_Page");
			/*ConnsProductPurchasePage.Click_On_French_Door_Link(webPage,
					test[0][0]);*/

			/*****Asim : old code for Click_On_French_Door_Link commented & instead using javascript for clicking operation******/

			WebElement element3 = webPage.getDriver().findElement(By.xpath(test[0][0]));					
			js.executeScript("arguments[0].click();", element3);

			log.info("Clicked on French Door");
			String str2[] = test[0][3].split(",");
			//Old code modified : if else condition modified 
			if (testType.equalsIgnoreCase("Mobile") || testBedName.equalsIgnoreCase("edge"))
			{     log.info("Mobile or Edge :  " +testType + " "+testBedName);
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

			else if (testType.equalsIgnoreCase("Web")) {

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
			}

		}catch (Throwable e) {
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
		JavascriptExecutor js = (JavascriptExecutor)webPage.getDriver();
		try {
			CommonMethods.navigateToPage(webPage, url);
			String[][] test = ExcelUtil.readExcelData(DataFilePath,
					"ProductListingPage",
					"Sorting_By_Product_Name_From_Product_Listing_Page");
			/*ConnsProductPurchasePage.Click_On_French_Door_Link(webPage,
					test[0][0]);*/
			/*****Asim : old code for Click_On_French_Door_Link commented & instead using javascript for clicking operation******/

			WebElement element3 = webPage.getDriver().findElement(By.xpath(test[0][0]));					
			js.executeScript("arguments[0].click();", element3);

			log.info("Clicked on French Door");
			Select s;
			if (testType.equalsIgnoreCase("Web")||(testBedName.contains("iPadNative"))) {
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
		JavascriptExecutor js = (JavascriptExecutor)webPage.getDriver();
		try {
			CommonMethods.navigateToPage(webPage, url);
			String[][] test = ExcelUtil.readExcelData(DataFilePath,
					"ProductListingPage",
					"Sorting_By_Product_Price_From_Product_Listing_Page");
			/*ConnsProductPurchasePage.Click_On_French_Door_Link(webPage,
					test[0][0]);*/
			/*****Asim : old code for Click_On_French_Door_Link commented & instead using javascript for clicking operation******/

			WebElement element3 = webPage.getDriver().findElement(By.xpath(test[0][0]));					
			js.executeScript("arguments[0].click();", element3);
			log.info("Clicked on French Door");
			Select s;
			if (testType.equalsIgnoreCase("Web")||(testBedName.contains("iPadNative"))) {
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

	public void submitReview(WebPage webPage,SoftAssert softAssert, String[][] labelsTextData, boolean validateAllFields) throws Exception
	{
		if(validateAllFields)
		{
			log.info("validateAllFields : " +validateAllFields);
			//String[][] reviewLabelsData =ExcelUtil.readExcelData(DataFilePath, "CreditApp","reviewLabelsData");
			commonMethods.verifyLabels(webPage, softAssert,ExcelUtil.readExcelData(DataFilePath, "ProductListingPage","reviewLabelsData"));
			Thread.sleep(3000);
			commonMethods.verifyLabelsErrorColor(webPage, softAssert, ExcelUtil.readExcelData(DataFilePath, "ProductListingPage","reviewDataLabelsErrorColor"));
			//webPage.navigateToUrl( webPage.getCurrentUrl());
			Thread.sleep(5000); 
		}
		commonMethods.fillFormWithOutJS(webPage, softAssert, labelsTextData);
		Thread.sleep(5000);
		commonMethods.waitForPageLoad(webPage, softAssert);
		commonMethods.verifyLabels(webPage,softAssert,ExcelUtil.readExcelData(DataFilePath, "ProductListingPage","reviewThankYouPageData"));
		commonMethods.verifyDate(webPage,softAssert,Review_Data[22][0]);
		Thread.sleep(5000);
	}
}
