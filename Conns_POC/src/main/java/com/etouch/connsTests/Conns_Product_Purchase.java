package com.etouch.connsTests;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.etouch.common.BaseTest;
import com.etouch.common.CommonMethods;
import com.etouch.common.TafExecutor;
import com.etouch.connsPages.ConnsMainPage;
import com.etouch.connsPages.ConnsProductPurchasePage;
import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.datamanager.excel.annotations.IExcelDataFiles;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.taf.webui.ITafElement;
import com.etouch.taf.webui.selenium.WebPage;

//import mx4j.log.Logger;

//@Test(groups = "HomePage")
@IExcelDataFiles(excelDataFiles = { "CreditAppData=testData" })
public class Conns_Product_Purchase extends BaseTest {
	static String platform;
	static Log log = LogUtil.getLog(Conns_Product_Purchase.class);
	static String AbsolutePath = TafExecutor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	static String videoLocation = AbsolutePath.substring(0, AbsolutePath.indexOf("/target/classes/")).substring(1)
			.concat("/src/test/resources/testdata/videos");
	private String url = null;
	private WebPage webPage;
	private ConnsMainPage mainPage;
	ConnsProductPurchasePage connsProductPurchasePage;
	CommonMethods commonMethods;

	String testBedName;
	TestBed testBed;
	Path path;
	String DataFilePath;
	String testType;
	String testEnv;
	JavascriptExecutor executor;
	
	String testUrl = "http://connsecommdev-1365538477.us-east-1.elb.amazonaws.com/conns_rwd/";
	
	
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
				System.out.println("testEnv is : " + System.getenv().get("Environment"));
				System.out.println("testEnv is : " + System.getProperty("ENVIRONMENT"));
				path = Paths.get(TestBedManager.INSTANCE.getProfile().getXlsDataConfig().get("testData"));
				DataFilePath = path.toAbsolutePath().toString().replace("Env", testEnv);
				System.out.println("DataFilePath After is : " + DataFilePath);

				platform = testBed.getPlatform().getName().toUpperCase();
				if (testType.equalsIgnoreCase("Web")) {
					System.out.println("videoLocation" + videoLocation);

					
				} else {
				}
				url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL();
				
				
				connsProductPurchasePage =new ConnsProductPurchasePage();
				commonMethods = new CommonMethods();
				synchronized (this) {

					webPage = new WebPage(context);
					mainPage = new ConnsMainPage(url, webPage);
					executor = (JavascriptExecutor)webPage.getDriver();
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
		// SpecializedScreenRecorder.stopVideoRecording();
	}

	
	
	public void Click_On_Refrigerators() {
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Click_On_French_Door");
		SoftAssert softAssert = new SoftAssert();
		try {
			
			if(testBedName.equalsIgnoreCase("InternetExplorer")){
				System.out.println("inside IE loop");
				WebElement element  = commonMethods.getWebElementbyXpath(webPage, test[1][1], softAssert);
				System.out.println("element:"+element.getText());
				executor.executeScript("arguments[0].click()", element);
				System.out.println("executed");
			}else{
				webPage.hoverOnElement(By.xpath(test[0][1]));
				webPage.findObjectByxPath(test[1][1]).click();
			}
			
		} catch (PageException | AWTException e) {
			log.error(e.getMessage());

		}

	}

	public void Add_To_Cart() {
		SoftAssert softAssert = new SoftAssert();
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase",
				"Verify_Add_To_Cart_Button_Functionality");
		try {
			Robot robot = new Robot();
			// Point point = driver.findElement(by).getLocation();
			robot.mouseMove(0, 16);
			// webPage.findObjectByxPath(test[0][1]).click();
			commonMethods.clickElementbyXpath(webPage, test[0][1], softAssert);
		} catch (Throwable e) {
			log.error(e.getMessage());

		}

	}

	public void Checkout_Guest() {
		SoftAssert softAssert = new SoftAssert();
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Checkout_Guest");
		try {
			Robot robot = new Robot();
			// Point point = driver.findElement(by).getLocation();
			robot.mouseMove(0, 16);
			// webPage.findObjectByxPath(test[0][1]).click();
			commonMethods.clickElementbyXpath(webPage, test[0][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, test[1][1], softAssert);
			Thread.sleep(5000);

		} catch (Throwable e) {
			log.error(e.getMessage());

		}

	}

	public void Checkout_Register() {
		SoftAssert softAssert = new SoftAssert();
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Checkout_Register");
		try {
			Robot robot = new Robot();
			// Point point = driver.findElement(by).getLocation();
			robot.mouseMove(0, 16);
			// webPage.findObjectByxPath(test[0][1]).click();
			commonMethods.clickElementbyXpath(webPage, test[0][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, test[1][1], softAssert);

		} catch (Throwable e) {
			log.error(e.getMessage());

		}

	}

	/*
	 * 
	 * This method will fill all the required fields in billing information section and it will click on 
	 * billing information continue button as well as pickup location continue button
	 */
	public void Submit_Billing_Information() {
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Submit_Billing_Information");
		SoftAssert softAssert = new SoftAssert();
		try {
			commonMethods.getWebElementbyXpath(webPage, test[0][1], softAssert).clear();
			commonMethods.sendKeysbyXpath(webPage, test[0][1], test[0][3], softAssert);
			
			commonMethods.getWebElementbyXpath(webPage, test[1][1], softAssert).clear();
			commonMethods.sendKeysbyXpath(webPage, test[1][1], test[1][3], softAssert);
			
			commonMethods.getWebElementbyXpath(webPage, test[2][1], softAssert).clear();
			commonMethods.sendKeysbyXpath(webPage, test[2][1], test[2][3], softAssert);
			
			commonMethods.getWebElementbyXpath(webPage, test[3][1], softAssert).clear();
			commonMethods.sendKeysbyXpath(webPage, test[3][1], test[3][3], softAssert);
			
			commonMethods.getWebElementbyXpath(webPage, test[4][1], softAssert).clear();
			commonMethods.sendKeysbyXpath(webPage, test[4][1], test[4][3], softAssert);
			
			commonMethods.getWebElementbyXpath(webPage, test[5][1], softAssert).clear();
			commonMethods.sendKeysbyXpath(webPage, test[5][1], test[5][3], softAssert);
			
			commonMethods.getWebElementbyXpath(webPage, test[6][1], softAssert).clear();
			commonMethods.sendKeysbyXpath(webPage, test[6][1], test[6][3], softAssert);

			commonMethods.selectDropdownByValue(webPage, test[7][1], test[7][3], softAssert);
			commonMethods.selectDropdownByValue(webPage, test[8][1], test[8][3], softAssert);
			
			

		} catch (Throwable e) {

			mainPage.getScreenShotForFailure(webPage, "Click_On_In_Stock_Add_To_Cart_Button");
			log.error("Click_On_In_Stock_Add_To_Cart_Button failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());

		}

	}
	
	
	
	public void Submit_Shipping_Info() {
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Submit_Billing_Information");
		SoftAssert softAssert = new SoftAssert();
		try {
			commonMethods.getWebElementbyXpath(webPage, test[0][1], softAssert).clear();
			commonMethods.sendKeysbyXpath(webPage, test[0][1], test[0][3], softAssert);
			
			commonMethods.getWebElementbyXpath(webPage, test[1][1], softAssert).clear();
			commonMethods.sendKeysbyXpath(webPage, test[1][1], test[1][3], softAssert);
			
			commonMethods.getWebElementbyXpath(webPage, test[2][1], softAssert).clear();
			commonMethods.sendKeysbyXpath(webPage, test[2][1], test[2][3], softAssert);
			
			commonMethods.getWebElementbyXpath(webPage, test[3][1], softAssert).clear();
			commonMethods.sendKeysbyXpath(webPage, test[3][1], test[3][3], softAssert);
			
			commonMethods.getWebElementbyXpath(webPage, test[4][1], softAssert).clear();
			commonMethods.sendKeysbyXpath(webPage, test[4][1], test[4][3], softAssert);
			
			commonMethods.getWebElementbyXpath(webPage, test[5][1], softAssert).clear();
			commonMethods.sendKeysbyXpath(webPage, test[5][1], test[5][3], softAssert);
			
			commonMethods.getWebElementbyXpath(webPage, test[6][1], softAssert).clear();
			commonMethods.sendKeysbyXpath(webPage, test[6][1], test[6][3], softAssert);

			commonMethods.selectDropdownByValue(webPage, test[7][1], test[7][3], softAssert);
			commonMethods.selectDropdownByValue(webPage, test[8][1], test[8][3], softAssert);
			
			

		} catch (Throwable e) {

			mainPage.getScreenShotForFailure(webPage, "Click_On_In_Stock_Add_To_Cart_Button");
			log.error("Click_On_In_Stock_Add_To_Cart_Button failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());

		}

	}

	public void PickUp_Location_Continue_Button() {
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Submit_billing_Information");
		SoftAssert softAssert = new SoftAssert();
		try {
			commonMethods.clickElementbyXpath(webPage, test[10][1], softAssert);
			Thread.sleep(5000);

		} catch (Throwable e) {
			log.error(e.getMessage());

		}

	}

	public void Proceed_To_Checkout_Button() {
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Proceed_To_Checkout_Button");
		SoftAssert softAssert = new SoftAssert();
		try {
			commonMethods.clickElementbyXpath(webPage, test[0][1], softAssert);
			Thread.sleep(5000);
		} catch (Throwable e) {
			log.error(e.getMessage());

		}

	}

	public String Submit_Paypal_Payment_Info() {
		SoftAssert softAssert = new SoftAssert();
		String actualPaypalURL = null;
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Submit_Paypal_Payment_Info");
		try {
			Robot robot = new Robot();

			robot.mouseMove(0, 16);

			commonMethods.clickElementbyXpath(webPage, test[0][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, test[1][1], softAssert);

			System.out.println("test[1][4]:" + test[1][4]);
			actualPaypalURL = commonMethods.getPageUrl(webPage, softAssert);

		} catch (Throwable e) {
			log.error(e.getMessage());

		}
		return actualPaypalURL;

	}

	public void Enter_Zip_Code_Click_On_Add_To_Cart() {

		SoftAssert softAssert = new SoftAssert();
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase",
				"Enter_Zip_Code_Click_On_Add_To_Cart");
		try {
			commonMethods.verifyElementisPresent(webPage, test[0][1], softAssert);

			webPage.findObjectByxPath(test[1][1]).clear();
			webPage.waitOnElement(By.xpath(test[1][1]), 10);
			commonMethods.sendKeysbyXpath(webPage, test[1][1], test[0][3], softAssert);

			Thread.sleep(5000);

			commonMethods.clickElementbyXpath(webPage, test[2][1], softAssert);
			Thread.sleep(10000);
			commonMethods.clickElementbyXpath(webPage, test[3][1], softAssert);

			// webPage.waitOnElement(By.xpath(test[4][1]), 10);

		} catch (Throwable e) {
			log.error(e.getMessage());

		}

	}

	

	/*
	 * 
	 * Common Method to click on ADD TO CART Button for pick only Availability
	 * option in all available pages
	 */
	//@Test(enabled = true)
	public void Click_On_PickUp_Only_Add_To_Cart_Button() {
		String stockAvilabilityText = null;
		String errorMessage = null;
		SoftAssert softAssert = new SoftAssert();
		commonMethods.navigateToPage(webPage, testUrl, softAssert);
		Click_On_Refrigerators();
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_PickUp_Only_Add_To_Cart_Button");
		try {

			List<WebElement> listOfProducts = commonMethods.getWebElementsbyXpath(webPage,
					"(//*[contains(@class,'category-products')]//ul//li[contains(@class,'item')])", softAssert);

			WebElement product;
			int counter = 1;
			for (int i = 1; i <= listOfProducts.size(); i++) {
				
				product = webPage.getDriver().findElement(By.xpath(
						"(//*[contains(@class,'category-products')]//ul//li[contains(@class,'item')])[" + i + "]"));
				System.out.println("Web Element Details" + product.getText() + i);

				stockAvilabilityText = product.findElement(By.xpath(test[2][1])).getText();
				

				System.out.println("stockAvilabilityText:::" + stockAvilabilityText);
				/*
				 * verifying whether availability text is pickup only or not
				 */
				if (stockAvilabilityText.equalsIgnoreCase(test[0][3])) {

					System.out.println("test[0][3]:::" + test[0][3]);
					commonMethods.clickElementbyXpath(webPage, "(//button[@title='Add to Cart'])[" + counter + "]",
							softAssert);
					// (By.xpath("(//button[@title='Add to
					// Cart'])["+counter+"]")).click();
					counter++;

					commonMethods.verifyElementisPresent(webPage, test[4][1], softAssert);
					Thread.sleep(3000);

					webPage.findObjectByxPath(test[5][1]).clear();

					webPage.waitOnElement(By.xpath(test[5][1]), 10);
					commonMethods.sendKeysbyXpath(webPage, test[5][1], test[5][3], softAssert);

					Thread.sleep(5000);
					webPage.waitOnElement(By.xpath(test[6][1]), 10);
					commonMethods.clickElementbyXpath(webPage, test[6][1], softAssert);
					Thread.sleep(10000);

					// if (!commonMethods.verifyElementisPresent(webPage, "Zip
					// Code Error Message::" + test[8][1],softAssert)) {
					boolean isPresent = webPage.getDriver().findElements(By.xpath(test[8][1])).size() > 0;
					if (!isPresent) {

						System.out.println("before clicking add to cart on modal box");
						commonMethods.clickElementbyXpath(webPage, test[7][1], softAssert);
						System.out.println("after clicking add to cart on modal box");

						System.out.println("clicked pickup only on add to cart button");
						break;

					} else {
						errorMessage = commonMethods.getTextbyXpath(webPage, test[8][1], softAssert);
						System.out.println("errorMessage:::" + errorMessage);
						System.out.println("test[8][4]:::" + test[8][4]);

						if (errorMessage.contains(test[8][4])) {
							System.out.println("captures error message:::" + errorMessage);
							webPage.getDriver().findElement(By.xpath("//*[@id='fancybox-close']")).click();
							Thread.sleep(3000);
							webPage.getDriver().navigate().back();

						}
					}

				}

			}

		} catch (Throwable e) {
			log.error(e.getMessage());
			mainPage.getScreenShotForFailure(webPage, "Click_On_PickUp_Only_Add_To_Cart_Button");
			log.error("Click_On_PickUp_Only_Add_To_Cart_Button failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());

		}

	}
	
	
	
				
					
				



	/*
	 * UC_001 - Product Search Page
	 **********************************************************************************************************/

	/*
	 * @Madhukar
	 * 
	 * Test Case 001 - Verify Page title --> appliances --> Refrigerators -->
	 * French Door French Door Refrigerators : Fridges by LG, Samsung & more |
	 * Conn's
	 */
	@Test(priority = 201, enabled = true, description = "Verify Page title")
	public void Verify_Page_Title() {
		SoftAssert softAssert = new SoftAssert();
		try {
			log.info("testing flow verifyPageTitle started");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Verify_Page_Title");
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			String ExpectedTitle = test[0][3];
			
			System.out.println("ExpectedTitle is:::" + ExpectedTitle);

			if (testType.equalsIgnoreCase("Web")) {
				
				connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
				
				System.out.println("Actual title is:::" + webPage.getPageTitle());
			} else {
				
				webPage.findObjectByxPath(test[0][4]).click();
				webPage.findObjectByxPath(test[0][0]).click();
			}

			Assert.assertEquals(ExpectedTitle, webPage.getPageTitle());
		} catch (Exception e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Page_Title");
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Verify_Page_Title failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
			
		}

	}
	/*mainPage.getScreenShotForFailure(webPage, "Verify_Zip_Code_Text_Box_Error_Message");
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Verify_Zip_Code_Text_Box_Error_Message failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
	 * @Madhukar Test Case 002 - Verify ADD TO CART button functionality
	 */

	@Test(priority = 202, enabled = true, description = "verify Add To Cart Functionality")
	public void Verify_Add_To_Cart_Button_Functionality() {
		SoftAssert softAssert = new SoftAssert();
		Robot robot;
		String actualValue = null;
		String expectedValue = null;
		try {

			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Add_To_Cart_Button_Functionality");
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			String[][] addToCart = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Add_To_Cart_Button_Functionality");
			log.info("testing flow verifyAddToCartFunctionality started");
			commonMethods.navigateToPage(webPage, testUrl, softAssert);

			if (testType.equalsIgnoreCase("Web")) {
				connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
				connsProductPurchasePage.Add_To_Cart(webPage, addToCart, softAssert);

			} else {
				connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
				connsProductPurchasePage.Add_To_Cart(webPage, addToCart, softAssert);

			}

			boolean isOverLayBoxPresent = webPage.getDriver().findElements(By.xpath(test[1][1])).size() >= 1;
			System.out.println("isOverLayBoxPresent:::" + isOverLayBoxPresent);
			if (!isOverLayBoxPresent) {
				
				commonMethods.clickElementbyXpath(webPage, test[3][1], softAssert);
			}

			softAssert.assertTrue(isOverLayBoxPresent,
					"verification 1 failed: Over Lay Box is not displayed on clicking ADD to Cart Button" + "\n ");
			
			boolean isZipCodeTextBoxDisplayed=webPage.getDriver().findElements(By.xpath(test[2][1])).size() >= 1;

			softAssert.assertTrue(isZipCodeTextBoxDisplayed,
					"verification 1 failed: Over Lay Box is not displayed on clicking ADD to Cart Button" + "\n ");

		

			softAssert.assertAll();

		} catch (Throwable e) {

			mainPage.getScreenShotForFailure(webPage, "Verify_Add_To_Cart_Button_Functionality");
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Verify_Add_To_Cart_Button_Functionality failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());

		}

	}

	/** 
	 * Test Case - 003 -Verify Page font size & style 
	 * 
	 * 
	 */
	@Test(priority =203, enabled = true, description = "Verify Page Font-Size And Style")
	public void Verify_Font_Size_And_Style() {
		SoftAssert softAssert = new SoftAssert();
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Verify_Font_Size_And_Style");
		log.info("testing flow verifyFontSizeAndStyle started");
		commonMethods.navigateToPage(webPage, testUrl, softAssert);
		String pageHeadingClass = null;
		String fontAttribute = null;
		String expectedValue = null;
		int count = 0;
		List<Integer> failedElements = new ArrayList<Integer>();
		for (int r = 0; r < test.length; r++) {

			pageHeadingClass = test[r][0];
			fontAttribute = test[r][1];
			if (testType.equalsIgnoreCase("Web")) {
				expectedValue = test[r][2];
			} else {
				expectedValue = test[r][3];
			}
			try {
				log.info("Verifying font size and style for element no. " + (r + 1));
				ITafElement pageHeading = webPage.findObjectByCss(pageHeadingClass);
				String value = pageHeading.getCssValue(fontAttribute).replaceAll("\"", "").replaceAll(" ", "")
						.toLowerCase().trim();
				Assert.assertTrue(value.contains(expectedValue) || expectedValue.contains(value),
						"Verify Font Size and Style failed.!!!" + "Font Attribute name " + fontAttribute + "Actual : "
								+ value + " and Expected :" + expectedValue.trim());

			} catch (Throwable e) {
				count++;
				failedElements.add(count);
				SoftAssertor.addVerificationFailure(e.getMessage());
				log.error("verifyFontSizeAndStyle failed");
				log.error(e.getMessage());
			}

		}
		if (count > 0) {
			Assert.fail("Failed to verify element number : " + Arrays.deepToString(failedElements.toArray()));
		}
		log.info("Ending verifyFontSizeAndStyle");

	}

	/*
	 * @Madhukar - TC_003- Verify Zip Code textbox with invalid input 
	 * 
	 */

	@Test(priority =204, enabled = true, description = "verify Zip Code Text Box Error Message")
	public void Verify_Zip_Code_Text_Box_Error_Message() {
		SoftAssert softAssert = new SoftAssert();
		Robot robot;
		String actualValue = null;
		String expectedValue = null;
		try {
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Zip_Code_Text_Box_Error_Message");
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Click_On_French_Door");
			String[][] addToCart = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Add_To_Cart_Button_Functionality");
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			log.info("testing flow verifyZipCodeTextBoxErrorMessage started");
			

			if (testType.equalsIgnoreCase("Web")) {
				connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
				connsProductPurchasePage.Add_To_Cart(webPage, addToCart, softAssert);

			} else {
				connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
				connsProductPurchasePage.Add_To_Cart(webPage, addToCart, softAssert);

			}

			boolean isOverLayBoxPresent = webPage.getDriver().findElements(By.xpath(test[0][1])).size() >=1;
			System.out.println("isOverLayBoxPresent:::" + isOverLayBoxPresent);
			if (!isOverLayBoxPresent) {
				
				commonMethods.clickElementbyXpath(webPage, test[3][1], softAssert);
			}

			softAssert.assertTrue(webPage.findObjectByxPath(test[0][1]).isDisplayed(),
					"verification 1 failed: Over Lay Box is not displayed on clicking ADD to Cart Button" + "\n ");
			// webPage.waitOnElement( By.xpath(test[2][1]), 10);

			/*
			 * JavascriptExecutor executor = (JavascriptExecutor)
			 * webPage.getDriver();
			 * 
			 * executor.executeScript(
			 * "document.getElementById('warehouse-zip-code').value='9999';");
			 */

			commonMethods.sendKeysbyXpath(webPage, test[1][1], test[1][3], softAssert);

			commonMethods.clickElementbyXpath(webPage, test[2][1], softAssert);

			actualValue = commonMethods.getTextbyXpath(webPage, test[3][1], softAssert);
			expectedValue = test[3][4];

			softAssert.assertTrue(expectedValue.contains(actualValue), "Zip code error message is not displayed:"
					+ "expected is:+" + expectedValue + "actual Value is:" + actualValue);

			softAssert.assertAll();

		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Zip_Code_Text_Box_Error_Message");
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Verify_Zip_Code_Text_Box_Error_Message failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}

		log.info("verify Add To Cart Functionality");

	}

	/*
	 * @Madhukar TC_004 Verify ADD TO CART on overlay without entering input in
	 * Zip code
	 * 
	 */
	@Test(priority =205, enabled = true, description = "Verify Add To Cart On Overlay Without Zip Code")
	public void Verify_Add_To_Cart_On_Overlay_Without_Zip_Code() {
		SoftAssert softAssert = new SoftAssert();
		String expectedValue = null;
		try {
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Add_To_Cart_On_Overlay_Without_Zip_Code");
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Click_On_French_Door");
			String[][] addToCart = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Add_To_Cart_Button_Functionality");
			log.info("testing flow Verify_Add_To_Cart_On_Overlay_Without_Zip_Code started");
			commonMethods.navigateToPage(webPage, testUrl, softAssert);

			if (testType.equalsIgnoreCase("Web")) {
				connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
				//connsProductPurchasePage.Add_To_Cart(webPage, addToCart, softAssert);
				commonMethods.clickElementbyXpath(webPage, "(//button[@class='button btn-cart' and @title='Add to Cart'])[2]", softAssert);

			} else {
				connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
				connsProductPurchasePage.Add_To_Cart(webPage, addToCart, softAssert);

			}
			
			boolean isOverLayBoxPresent = webPage.getDriver().findElements(By.xpath(test[0][1])).size() >=1;
			System.out.println("isOverLayBoxPresent:::" + isOverLayBoxPresent);
			if (!isOverLayBoxPresent) {
				
				commonMethods.clickElementbyXpath(webPage, test[4][1], softAssert);
			}
			
			
			softAssert.assertTrue(isOverLayBoxPresent,
					"verification 1 failed: Over Lay Box is not displayed on clicking ADD to Cart Button" + "\n ");
			
			commonMethods.clickElementbyXpath(webPage, test[2][1], softAssert);

			String actualErrorMessage = commonMethods.getTextbyXpath(webPage, test[3][1], softAssert);

			System.out.println("actualErrorMessage:::" + actualErrorMessage);
			String expectedErrorMessage = test[3][4];
			System.out.println("expectedErrorMessage:"+expectedErrorMessage);
			softAssert.assertTrue(expectedErrorMessage.contains(actualErrorMessage),
					"Expected Text: " + expectedErrorMessage + "Actual text: " + actualErrorMessage);
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Add_To_Cart_On_Overlay_Without_Zip_Code");

			log.error("Verify_Add_To_Cart_On_Overlay_Without_Zip_Code failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}

	}

	/*
	 * @Madhukar TC_005 Verify ADD TO CART on overlay with valid input in Zip
	 * code
	 * 
	 */

	@Test(priority =206, enabled = true, description = "verify Zip Code Text Box Error Message")
	public void Verify_Add_To_Cart_On_Overlay_With_Valid_Zip_Code() {
		SoftAssert softAssert = new SoftAssert();
		String expectedURL = null;
		try {
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Add_To_Cart_On_Overlay_With_Valid_Zip_Code");
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Click_On_French_Door");
			String[][] addToCart = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Add_To_Cart_Button_Functionality");
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			log.info("testing flow Verify_Add_To_Cart_On_Overlay_With_Valid_Zip_Code started");

			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			
			if (testType.equalsIgnoreCase("Web")) {
				connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
				connsProductPurchasePage.Add_To_Cart(webPage, addToCart, softAssert);
			} else {
				connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
				connsProductPurchasePage.Add_To_Cart(webPage, addToCart, softAssert);
			}
			
			boolean isOverLayBoxPresent = webPage.getDriver().findElements(By.xpath(test[0][1])).size() >=1;
			System.out.println("isOverLayBoxPresent:::" + isOverLayBoxPresent);
			if (!isOverLayBoxPresent) {
				
				commonMethods.clickElementbyXpath(webPage, test[6][1], softAssert);
			}
			
			softAssert.assertTrue(isOverLayBoxPresent,
					"verification 1 failed: Over Lay Box is not displayed on clicking ADD to Cart Button" + " \n ");

			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, test[1][1], softAssert),
					"verification 2 failed:Zip Code Text Box is not displayed on Over Lay Box" + " \n ");
			
			webPage.findObjectByxPath(test[1][1]).clear();

			
			commonMethods.sendKeysbyXpath(webPage, test[1][1], test[1][3], softAssert);
			// Thread.sleep(10000);

			webPage.waitOnElement(By.xpath(test[2][1]), 10);
			
			commonMethods.clickElementbyXpath(webPage, test[2][1], softAssert);
			Thread.sleep(10000);

			webPage.waitOnElement(By.xpath(test[6][1]), 10);
			
			commonMethods.clickElementbyXpath(webPage, test[6][1], softAssert);

			expectedURL = test[0][4];
			String actualURL = webPage.getCurrentUrl();
			softAssert.assertTrue(actualURL.contains(expectedURL),
					" URL verification failed:Expected URL " + expectedURL + " Actual URL: " + actualURL);
			softAssert.assertAll();

		} catch (Throwable e) {

			mainPage.getScreenShotForFailure(webPage, "Verify_Add_To_Cart_On_Overlay_With_Valid_Zip_Code");
			log.error("verifyZipCodeTextBoxErrorMessage failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}

	}

	/*
	 * UC_002 - Cart Features validation
	 *******************************************************************************************/

	/*
	 * 
	 * 
	 * update functionality is not seen on cart page (Dev envi): tested on
	 * 04/07/2017
	 */
	@Test(priority =207, enabled = true, description = "Verify 'Update' functionality in cart page")
	public void Verify_Update_Functionality_Cart_Page() {
		SoftAssert softAssert = new SoftAssert();
		String expectedValue = null;
		try {
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Update_Functionality_Cart_Page");
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Click_On_French_Door");
			String[][] addToCart = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Add_To_Cart_Button_Functionality");
			log.info("testing flow Verify_Update_Functionality_Cart_Page started");
			commonMethods.navigateToPage(webPage, testUrl, softAssert);

			if (testType.equalsIgnoreCase("Web")) {
				connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
				connsProductPurchasePage.Add_To_Cart(webPage, addToCart, softAssert);
			} else {
				connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
				connsProductPurchasePage.Add_To_Cart(webPage, addToCart, softAssert);
			}
			
			
			if (!commonMethods.verifyElementisPresent(webPage, test[0][1], softAssert)) {
				
				commonMethods.clickElementbyXpath(webPage, test[10][1], softAssert);
			}
			
			
			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, test[0][1], softAssert),
					"verification 1 failed: Over Lay Box is not displayed on clicking ADD to Cart Button" + " \n ");

			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, test[1][1], softAssert),
					"verification 2 failed:Zip Code Text Box is not displayed on Over Lay Box" + " \n ");
			
			
			commonMethods.getWebElementbyXpath(webPage, test[1][1], softAssert).clear();


			JavascriptExecutor executor = (JavascriptExecutor) webPage.getDriver();

			executor.executeScript("document.getElementById('zip-code').value='77301';");

			commonMethods.clickElementbyXpath(webPage, test[3][1], softAssert);

			Thread.sleep(10000);
			// webPage.findObjectByxPath(test[3][1]).click();

			webPage.waitOnElement(By.xpath(test[4][1]), 10);
			commonMethods.clickElementbyXpath(webPage, test[4][1], softAssert);
			// webPage.findObjectByxPath(test[6][1]).click();

			if (commonMethods.verifyElementisPresent(webPage, test[6][1], softAssert)) {
				webPage.findObjectByxPath(test[8][1]).clear();
				commonMethods.sendKeysbyXpath(webPage, test[8][1], test[8][3], softAssert);
				commonMethods.clickElementbyXpath(webPage, test[9][1], softAssert);

			}
			expectedValue = test[8][3];
			// int expectedCount= Integer.parseInt(expectedValue);
			String actualValue = webPage.findObjectByxPath(test[8][1]).getAttribute("value");
			// int ActualCount= Integer.parseInt(actualValue);
			softAssert.assertEquals(expectedValue, actualValue,
					"Quantity is not updated clicking update button on Cart Page: " + "Expected Value: " + expectedValue
							+ " Actual Value: " + actualValue);

			softAssert.assertAll();

		} catch (Throwable e) {

			mainPage.getScreenShotForFailure(webPage, "Verify_Update_Functionality_Cart_Page");
			log.error("Verify_Update_Functionality_Cart_Page failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}

	}

	 @Test(priority =208, enabled = true, description = "Verify 'Remove item link functionality in cart page")
	public void Verify_Remove_Item_Link_Functionality_In_Cart_Page() {
		SoftAssert softAssert = new SoftAssert();
		
		try {
			
			String[][] test1 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_PickUp_Only_Add_To_Cart_Button");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Remove_Item_Link_Functionality_In_Cart_Page");
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Click_On_French_Door");
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
			connsProductPurchasePage.Click_On_PickUp_Only_Add_To_Cart_Button(webPage, test1, softAssert);
			
			log.info("testing flow Verify_Remove_Item_Link_Functionality_In_Cart_Page started");
			
			
			softAssert.assertAll();

		} catch (Throwable e) {

			mainPage.getScreenShotForFailure(webPage, "Verify_Remove_Item_Link_Functionality_In_Cart_Page");
			log.error("Verify_Remove_Item_Link_Functionality_In_Cart_Page failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}

	}

	

	@Test(priority =209, enabled = true, description = "Verify product AVAILABILITY status in cart page")
	public void Verify_Product_Availability_status_In_Cart_Page() {
		SoftAssert softAssert = new SoftAssert();
		String ProductAvailability = "In_Stock";
		String expectedValue = null;
		try {
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Product_Availability_status_In_Cart_Page");
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Click_On_French_Door");
			String[][] addToCart = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Verify_Add_To_Cart_Button_Functionality");
			
			
			log.info("testing flow Verify_Product_Availability_status_In_Cart_Page started");
			
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			
			if (testType.equalsIgnoreCase("Web")) {
				connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
				if (ProductAvailability.equalsIgnoreCase(test[4][3])) {
					
					commonMethods.clickElementbyXpath(webPage, "(//*[@id='desktop-pager']//a[@title='Next'])[1]", softAssert);
					
				}
				connsProductPurchasePage.Add_To_Cart(webPage, addToCart, softAssert);
				
			} else {
				connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
				connsProductPurchasePage.Add_To_Cart(webPage, addToCart, softAssert);
				
			}

			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, test[0][1], softAssert),"Overlay Box is not displayed");

			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, test[1][1], softAssert),"Zip Code text box is not displayed");
			
			webPage.findObjectByxPath(test[1][1]).clear();
			
			commonMethods.sendKeysbyXpath(webPage, test[1][1], test[0][3], softAssert);

			Thread.sleep(5000);

			commonMethods.clickElementbyXpath(webPage, test[2][1], softAssert);
			Thread.sleep(5000);
			commonMethods.clickElementbyXpath(webPage, test[3][1], softAssert);

			

			expectedValue = test[0][4];

			String ActualValue = commonMethods.getTextbyXpath(webPage, test[4][1], softAssert);
			System.out.println("ActualValue:"+ActualValue);
			System.out.println("expectedValue:"+expectedValue);
			softAssert.assertTrue(ActualValue.contains(expectedValue), " Expected Avilability Status : Expected  "
					+ expectedValue + " Actual Avilability Message: " + ActualValue);
			softAssert.assertAll();

		} catch (Throwable e) {

			mainPage.getScreenShotForFailure(webPage, "Verify_Product_Availability_status_In_Cart_Page");
			log.error("Verify_Product_Availability_status_In_Cart_Page failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}

	}

	

	@Test(priority =210, enabled = true, description = "Verify Zip Code textbox with invalid input")
	public void Verify_Apply_Code_Button_With_Invalid_Code() {
		SoftAssert softAssert = new SoftAssert();
		String expected_Coupon_Code_Error_Message;
		String actual_Coupon_Code_Error_Message;
		try {
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Apply_Code_Button_With_Invalid_Code");
			String[][] test1 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_PickUp_Only_Add_To_Cart_Button");
			
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			log.info("testing flow Verify_Apply_Code_Button_With_Invalid_Code started");
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);

			connsProductPurchasePage.Click_On_PickUp_Only_Add_To_Cart_Button(webPage, test1, softAssert);

			
			log.info("testing flow Verify_Apply_Code_Button_With_Invalid_Code started");

			webPage.waitOnElement(By.xpath(test[0][1]), 10);
			commonMethods.sendKeysbyXpath(webPage, test[0][1], test[0][3], softAssert);

			webPage.waitOnElement(By.xpath(test[1][1]), 10);
			commonMethods.clickElementbyXpath(webPage, test[1][1], softAssert);

			webPage.waitOnElement(By.xpath(test[2][1]), 10);
			actual_Coupon_Code_Error_Message = commonMethods.getTextbyXpath(webPage, test[2][1], softAssert);
			expected_Coupon_Code_Error_Message = test[0][4];

			softAssert.assertEquals(expected_Coupon_Code_Error_Message, actual_Coupon_Code_Error_Message,
					"Coupon_Code_Error_Messages are not matching::" + " Expected is:: "
							+ expected_Coupon_Code_Error_Message + " Actual is: " + actual_Coupon_Code_Error_Message);
			softAssert.assertAll();
		} catch (Throwable e) {

			mainPage.getScreenShotForFailure(webPage, "Verify_Apply_Code_Button_With_Invalid_Code");
			log.error("Verify_Apply_Code_Button_With_Invalid_Code failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}

	}
	
	/*
	 * 
	 * need test data
	 */
	@Test(priority =211, enabled = true, description = "Verify the functionality of 'PROCEED TO CHECKOUT' button in cart page")
	public void Verify_Apply_Code_Button_With_valid_Code() {
		SoftAssert softAssert = new SoftAssert();
		String expected_Coupon_Code_Error_Message;
		String actual_Coupon_Code_Error_Message;
		try {
			String[][] test1 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_PickUp_Only_Add_To_Cart_Button");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Apply_Code_Button_With_valid_Code");
			log.info("testing flow Verify_Apply_Code_Button_With_valid_Code started");
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			connsProductPurchasePage.Click_On_PickUp_Only_Add_To_Cart_Button(webPage, test1, softAssert);
			commonMethods.sendKeysbyXpath(webPage, test[0][1], test[0][3], softAssert);
			commonMethods.clickElementbyXpath(webPage, test[1][1], softAssert);
			actual_Coupon_Code_Error_Message = commonMethods.getTextbyXpath(webPage, test[2][1], softAssert);
			expected_Coupon_Code_Error_Message = test[0][4];

			softAssert.assertEquals(expected_Coupon_Code_Error_Message, actual_Coupon_Code_Error_Message,"Coupon_Code_Error_Messages are not matching::" + " Expected is:: "
							+ expected_Coupon_Code_Error_Message + " Actual is: " + actual_Coupon_Code_Error_Message);
			
			softAssert.assertAll();
		} catch (Throwable e) {

			mainPage.getScreenShotForFailure(webPage, "Verify_Apply_Code_Button_With_valid_Code");
			log.error("Verify_Apply_Code_Button_With_valid_Code failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}

	}

	@Test(priority =212, enabled = true, description = "Verify the functionality of 'PROCEED TO CHECKOUT' button in cart page")
	public void Verify_Proceed_To_Checkout_Functionality() {
		SoftAssert softAssert = new SoftAssert();
		String expectedUrl = null;
		String actualUrl = null;
		try {
			String[][] test1 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_PickUp_Only_Add_To_Cart_Button");
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Proceed_To_Checkout_Functionality");
			log.info("testing flow Verify_Proceed_To_Checkout_Functionality started");
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);

			connsProductPurchasePage.Click_On_PickUp_Only_Add_To_Cart_Button(webPage, test1, softAssert);

			
			connsProductPurchasePage.Proceed_To_Checkout_Button(webPage, test, softAssert);
			actualUrl = commonMethods.getPageUrl(webPage, softAssert);
			expectedUrl = test[0][4];
			System.out.println("actualUrl.contains(expectedUrl):"+actualUrl.contains(expectedUrl));
			softAssert.assertTrue(actualUrl.contains(expectedUrl), "Proceed to checkout functionality is failed:::"
					+ " Expected is: " + expectedUrl + "Actual is:::" + actualUrl);

			softAssert.assertAll();
			

		} catch (Throwable e) {

			mainPage.getScreenShotForFailure(webPage, "Verify_Proceed_To_Checkout_Functionality");
			log.error("Verify_Proceed_To_Checkout_Functionality failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}

	}

	@Test(priority = 213, enabled = true, description = "Verify the 'Continue Shopping' link in cart page")
	public void Verify_Continue_Shopping_Link_Functionality_In_Cart_Page() {
		SoftAssert softAssert = new SoftAssert();
		String expectedUrl = null;
		String actualUrl = null;
		try {
			
			String[][] test1 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_PickUp_Only_Add_To_Cart_Button");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Continue_Shopping_Link_Functionality_In_Cart_Page");
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			
			log.info("testing flow Verify_Continue_Shopping_Link_Functionality_In_Cart_Page started");
			
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			
			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
			
			connsProductPurchasePage.Click_On_PickUp_Only_Add_To_Cart_Button(webPage, test1, softAssert);

			commonMethods.clickElementbyXpath(webPage, test[0][1], softAssert);
			
			actualUrl = commonMethods.getPageUrl(webPage, softAssert);
			expectedUrl = test[0][4];
			System.out.println("actualUrl  :::" + actualUrl+"/n");
			System.out.println("expectedUrl:::" + expectedUrl);
			softAssert.assertEquals(expectedUrl, actualUrl, "Continue Shopping Link Functionality Is Failed:::"
					+ " Expected is: " + expectedUrl + " Actual is:::" + actualUrl);

			softAssert.assertAll();

		} catch (Throwable e) {

			mainPage.getScreenShotForFailure(webPage, "Verify_Continue_Shopping_Link_Functionality_In_Cart_Page");
			log.error("Verify_Continue_Shopping_Link_Functionality_In_Cart_Page failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}

	}

	@Test(priority =214, enabled = true, description = "Verify Get credit button in cart page")
	public void Verify_Get_Credit_Button_In_Cart_Page() {
		SoftAssert softAssert = new SoftAssert();

		try {
			String[][] test1 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_PickUp_Only_Add_To_Cart_Button");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Get_Credit_Button_In_Cart_Page");
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			log.info("testing flow Verify_Get_Credit_Button_In_Cart_Page started");
			
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			
			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);

			connsProductPurchasePage.Click_On_PickUp_Only_Add_To_Cart_Button(webPage, test1, softAssert);
			
			
			softAssert.assertTrue(commonMethods.verifyElementisPresent(webPage, test[0][1], softAssert),"Get credit button in cart page is not displayed");

			softAssert.assertAll();

		} catch (Throwable e) {

			mainPage.getScreenShotForFailure(webPage, "Verify_Get_Credit_Button_In_Cart_Page");
			log.error("Verify_Get_Credit_Button_In_Cart_Page failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}

	}

	@Test(priority = 215, enabled = true, description = "Verify 'More Detail' link in product availability section in cart page -- seen only for Pick-up only Product")
	public void Verify_More_Detail_Link_In_Cart_Page() {
		SoftAssert softAssert = new SoftAssert();

		try {
			String[][] test1 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_PickUp_Only_Add_To_Cart_Button");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_More_Detail_Link_In_Cart_Page");
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			log.info("testing flow Verify_More_Detail_Link_In_Cart_Page started");
			
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			
			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
			
			connsProductPurchasePage.Click_On_PickUp_Only_Add_To_Cart_Button(webPage, test1, softAssert);

			boolean isMoreDetailLinkDisplayed=commonMethods.verifyElementisPresent(webPage, test[4][1], softAssert);
			
			System.out.println("isMoreDetailLinkDisplayed:"+isMoreDetailLinkDisplayed);
			
			softAssert.assertTrue(isMoreDetailLinkDisplayed,"More Detail Link Is Not Displayed In Cart Page");

			softAssert.assertAll();

		} catch (Throwable e) {

			mainPage.getScreenShotForFailure(webPage, "Verify_More_Detail_Link_In_Cart_Page");
			log.error("Verify_More_Detail_Link_In_Cart_Page failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}

	}

	@Test(priority = 216, enabled = true, description = "Lock Icon & McAffe Logo should be seen in Cart at bottom")
	public void Verify_Lock_Icon_McAffe_Logo_In_Cart_At_Bottom() {
		SoftAssert softAssert = new SoftAssert();

		try {
			String[][] test1 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_PickUp_Only_Add_To_Cart_Button");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Lock_Icon_McAffe_Logo_In_Cart_At_Bottom");
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			
			log.info("testing flow Verify_Lock_Icon_McAffe_Logo_In_Cart_At_Bottom started");
			
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			
			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);

			connsProductPurchasePage.Click_On_PickUp_Only_Add_To_Cart_Button(webPage, test1, softAssert);

			
			boolean isLockIconDisplayed=commonMethods.verifyElementisPresent(webPage, test[0][1], softAssert);
			
			boolean isMcAfeeLogoDisplayed=commonMethods.verifyElementisPresent(webPage, test[1][1], softAssert);
			
			softAssert.assertTrue(isLockIconDisplayed,"Lock Icon is not displayed in cart page at bottom ");
			
			softAssert.assertTrue(isMcAfeeLogoDisplayed,"McAfee Logo is not displayed in cart page at bottom ");

			softAssert.assertAll();
			
			log.info("Verify_Lock_Icon_McAffe_Logo_In_Cart_At_Bottom ended ");

		} catch (Throwable e) {

			mainPage.getScreenShotForFailure(webPage, "Verify_Lock_Icon_McAffe_Logo_In_Cart_At_Bottom");
			log.error("Verify_Lock_Icon_McAffe_Logo_In_Cart_At_Bottom failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}

	}

	@Test(priority = 217, enabled = true, description = "Verify 'Continue' button by selecting 'Checkout as Guest' in checkout page")
	public void Verify_UI_Checkout_Page() {
		SoftAssert softAssert = new SoftAssert();

		try {
			String[][] test1 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_PickUp_Only_Add_To_Cart_Button");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Verify_UI_Checkout_Page");
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			String[][] proceedToCheckout = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Proceed_To_Checkout_Button");
			
			log.info("testing flow Verify_UI_Checkout_Page started");
			
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			
			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);

			connsProductPurchasePage.Click_On_PickUp_Only_Add_To_Cart_Button(webPage, test1, softAssert);
			
			connsProductPurchasePage.Proceed_To_Checkout_Button(webPage, proceedToCheckout, softAssert);
			
			
			boolean isCheckoutSectionIsExpandededModeByDefault = commonMethods.verifyElementisPresent(webPage, test[3][1], softAssert);
			softAssert.assertTrue(isCheckoutSectionIsExpandededModeByDefault, "By default checkout method is not in expanded mode");

			

			commonMethods.clickElementbyXpath(webPage, test[1][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, test[2][1], softAssert);

			
			boolean isCheckoutSectionCollapsed = commonMethods.verifyElementisPresent(webPage, test[4][1], softAssert);

			softAssert.assertTrue(isCheckoutSectionCollapsed, "checkout method is not in collapsed mode");

			boolean isBillingInformationSectionInExpanded = commonMethods.verifyElementisPresent(webPage, test[5][1],softAssert);

			softAssert.assertTrue(isBillingInformationSectionInExpanded,"Billing Information Section Is Not In Expanded Mode");

			softAssert.assertAll();

		} catch (Throwable e) {

			mainPage.getScreenShotForFailure(webPage, "Verify_UI_Checkout_Page");
			log.error("Verify_UI_Checkout_Page failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}

	}

	@Test(priority = 218, enabled = true, description = "Verify 'Continue' button by selecting 'Register' in checkout page")
	public void Verify_UI_After_Selecting_Register_Button() {
		SoftAssert softAssert = new SoftAssert();

		try {
			String[][] test1 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_PickUp_Only_Add_To_Cart_Button");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_UI_After_Selecting_Register_Button");
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			String[][] proceedToCheckout = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Proceed_To_Checkout_Button");
			
			log.info("testing flow Verify_UI_Checkout_Page started");
			
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			
			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);

			connsProductPurchasePage.Click_On_PickUp_Only_Add_To_Cart_Button(webPage, test1, softAssert);


			connsProductPurchasePage.Proceed_To_Checkout_Button(webPage, proceedToCheckout, softAssert);
			/*
			 * checking if checkout method is in expanded mode by default
			 */
			boolean isCheckoutSectionIsExpanded = commonMethods.verifyElementisPresent(webPage,
					"//li[@id='opc-login' and @class='section allow active']", softAssert);
			softAssert.assertTrue(isCheckoutSectionIsExpanded, "By default checkout method is not in expanded mode");

			

			commonMethods.clickElementbyXpath(webPage, test[1][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, test[2][1], softAssert);

			/*
			 * 
			 * checking if checkout method is collapsed after clicking continue
			 * button
			 */
			boolean isCheckoutSectionCollapsed = commonMethods.verifyElementisPresent(webPage,
					"//li[@id='opc-login' and @class='section allow']", softAssert);

			softAssert.assertTrue(isCheckoutSectionCollapsed, "checkout method is not in collapsed mode");

			/*
			 * checking if checkout method is collapsed after clicking continue
			 * button
			 */
			boolean isBillingInformationSectionInExpandMode = commonMethods.verifyElementisPresent(webPage,
					"//li[@id='opc-billing' and @class='section allow active']", softAssert);

			softAssert.assertTrue(isBillingInformationSectionInExpandMode,
					"Billing Information Section Is Not In Expanded Mode");

			softAssert.assertAll();

		} catch (Throwable e) {

			mainPage.getScreenShotForFailure(webPage, "Verify_UI_After_Selecting_Register_Button");
			log.error("Verify_UI_After_Selecting_Register_Button failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}

	}

	@Test(priority = 219, enabled = true, description = "Verify 'Continue' button by selecting 'Register' in checkout page")
	public void Verify_Login_Button_With_Valid_Credentials() {
		SoftAssert softAssert = new SoftAssert();

		try {
			String[][] test1 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_PickUp_Only_Add_To_Cart_Button");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Login_Button_With_Valid_Credentials");

			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			String[][] proceedToCheckout = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Proceed_To_Checkout_Button");
			
			log.info("testing flow Verify_Login_Button_With_Valid_Credentials started");
			
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			
			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);

			connsProductPurchasePage.Click_On_PickUp_Only_Add_To_Cart_Button(webPage, test1, softAssert);


			connsProductPurchasePage.Proceed_To_Checkout_Button(webPage, proceedToCheckout, softAssert);
			/*
			 * checking if checkout method is in expanded mode by default
			 */
			boolean isCheckoutSectionIsExpanded = commonMethods.verifyElementisPresent(webPage,"//li[@id='opc-login' and @class='section allow active']", softAssert);
			
			softAssert.assertTrue(isCheckoutSectionIsExpanded, "Could Not logged in as Login window is in collapced mode");

		
			commonMethods.sendKeysbyXpath(webPage, test[1][1], test[1][3], softAssert);

			commonMethods.sendKeysbyXpath(webPage, test[2][1], test[2][3], softAssert);

			commonMethods.clickElementbyXpath(webPage, test[3][1], softAssert);
			
			
			boolean isBillingInformationSectionInExpandMode = commonMethods.verifyElementisPresent(webPage,
					"//li[@id='opc-billing' and @class='section allow active']", softAssert);
			
			softAssert.assertTrue(isBillingInformationSectionInExpandMode, "Login is not successful");
			
			softAssert.assertAll();

		} catch (Throwable e) {

			mainPage.getScreenShotForFailure(webPage, "Verify_Login_Button_With_Valid_Credentials");
			log.error("Verify_Login_Button_With_Valid_Credentials failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}

	}

	@Test(priority = 220, enabled = true, description = "Verify 'Forgot Your Password?' functionality in checkout page for login section")
	public void Verify_Forgot_Your_Password_Functionality() {
		SoftAssert softAssert = new SoftAssert();

		try {
			String[][] test1 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_PickUp_Only_Add_To_Cart_Button");
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Forgot_Your_Password_Functionality");
			String[][] proceedToCheckout = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Proceed_To_Checkout_Button");
			
			log.info("Verify_Forgot_Your_Password_Functionality started");
			
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			
			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);

			connsProductPurchasePage.Click_On_PickUp_Only_Add_To_Cart_Button(webPage, test1, softAssert);
			
			
			connsProductPurchasePage.Proceed_To_Checkout_Button(webPage, proceedToCheckout, softAssert);
			
			
			boolean isForgotYourPasswordLinkDisplayed = commonMethods.verifyElementisPresent(webPage, test[1][1],softAssert);
			
			softAssert.assertTrue(isForgotYourPasswordLinkDisplayed, "forgot your password link is not displayed");
			
			if (isForgotYourPasswordLinkDisplayed) {

				commonMethods.clickElementbyXpath(webPage, test[1][1], softAssert);
				
				String ActualURL_ForgotYourPasswordPage = commonMethods.getPageUrl(webPage, softAssert);
				
				boolean isUserNavigatedToForgotYourPasswordPage = ActualURL_ForgotYourPasswordPage.contains(test[1][4]);
				
				softAssert.assertTrue(isUserNavigatedToForgotYourPasswordPage,"Forgot your password page url is not matching with expected url:" + "Expected Url is:"
								+ test[1][4] + "Actual url is:" + ActualURL_ForgotYourPasswordPage);
			} 

			softAssert.assertAll();

		} catch (Throwable e) {

			mainPage.getScreenShotForFailure(webPage, "Verify_Forgot_Your_Password_Functionality");
			log.error("Verify_Forgot_Your_Password_Functionality failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}

	}

	
	@Test(priority = 221, enabled = true, description = "Verify the 'continue' button without selecting shipping option")
	public void Verify_Continue_Button_Without_Selecting_Payment_Info() {
		SoftAssert softAssert = new SoftAssert();
		String actualText = null;
		String expectedText = null;

		try {
			
			String[][] test1 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_PickUp_Only_Add_To_Cart_Button");
			String[][] billingInfo= ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Submit_Billing_Information");

			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Continue_Button_Without_Selecting_Payment_Info");
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			String[][] proceedToCheckout = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Proceed_To_Checkout_Button");
			
			log.info("Verify_Continue_Button_Without_Selecting_Payment_Info started");
			
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			
			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
			
			
			connsProductPurchasePage.Click_On_PickUp_Only_Add_To_Cart_Button(webPage, test1, softAssert);

			connsProductPurchasePage.Proceed_To_Checkout_Button(webPage, proceedToCheckout, softAssert);

			Checkout_Guest();

			Submit_Billing_Information();
			
			//clicking on billing info continue button
			commonMethods.clickElementbyXpath(webPage, billingInfo[9][1],softAssert);
			Thread.sleep(5000);
			//clicking on pickup location continue button
			commonMethods.clickElementbyXpath(webPage, billingInfo[10][1],softAssert);
			
			Thread.sleep(10000);
			
			

			commonMethods.clickElementbyXpath(webPage, test[0][1], softAssert);

			
			Alert alert = webPage.getDriver().switchTo().alert();
			actualText = alert.getText();

			expectedText = test[1][4];
			alert.accept();
			softAssert.assertTrue(expectedText.equalsIgnoreCase(actualText), "expected alert message is not displayed: "
					+ " Expected Text is:" + expectedText + " Actual text is:" + actualText);
			

			softAssert.assertAll();

		} catch (Throwable e) {

			mainPage.getScreenShotForFailure(webPage, "Verify_Continue_Button_Without_Selecting_Payment_Info");
			log.error("Verify_Continue_Button_Without_Selecting_Payment_Info failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}

	}

	@Test(priority = 222, enabled = true, description = "Verify the checkout flow using Conn's credit")
	public void Verify_Payment_Info_ConnsCredit() {
		SoftAssert softAssert = new SoftAssert();
		String actualURL = null;
		String expectedURL = null;

		try {
			
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			String[][] test1 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_PickUp_Only_Add_To_Cart_Button");
			String[][] checkOutButton = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Proceed_To_Checkout_Button");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Payment_Info_ConnsCredit");
			
			log.info("Verify_Payment_Info_ConnsCredit started");
			
			commonMethods.navigateToPage(webPage, testUrl, softAssert);

			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);

			connsProductPurchasePage.Click_On_PickUp_Only_Add_To_Cart_Button( webPage, test1, softAssert);

			connsProductPurchasePage.Proceed_To_Checkout_Button( webPage, checkOutButton, softAssert);

			Checkout_Guest();

			Submit_Billing_Information();
			
			commonMethods.clickElementbyXpath(webPage, test[0][1], softAssert);

			Thread.sleep(5000);

			commonMethods.clickElementbyXpath(webPage, test[2][1], softAssert);
			
			actualURL = commonMethods.getPageUrl(webPage, softAssert);

			expectedURL = test[2][4];
			
			softAssert.assertTrue(actualURL.contains(expectedURL), "expected alert message is not displayed: "+ " Expected Text is:" + expectedURL + " Actual text is:" + actualURL);

			softAssert.assertAll();

		} catch (Throwable e) {

			mainPage.getScreenShotForFailure(webPage, "Verify_Payment_Info_ConnsCredit");
			log.error("Verify_Payment_Info_ConnsCredit failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}

	}

	@Test(priority = 223, enabled = true, description = "Verify the checkout flow using PayPal option in payment info section in checkout page")
	public void Verify_Checkout_Flow_Paypal() {
		SoftAssert softAssert = new SoftAssert();
		String actualPaypalURL = null;
		String expectedPaypalURL = null;

		try {
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			String[][] test1 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_PickUp_Only_Add_To_Cart_Button");
			String[][] checkOutButton = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Proceed_To_Checkout_Button");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Verify_Checkout_Flow_Paypal");
			String[][] billingInfo = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Submit_Billing_Information");
			
			log.info("Verify_Checkout_Flow_Paypal started");
			
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			
			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);

			connsProductPurchasePage.Click_On_PickUp_Only_Add_To_Cart_Button( webPage, test1, softAssert);

			connsProductPurchasePage.Proceed_To_Checkout_Button( webPage, checkOutButton, softAssert);

			Checkout_Guest();

			Submit_Billing_Information();
			
			//clicking on billing info continue button
			commonMethods.clickElementbyXpath(webPage, billingInfo[9][1],softAssert);
			
			//clicking on pickup location continue button
			commonMethods.clickElementbyXpath(webPage, billingInfo[10][1],softAssert);
			
			actualPaypalURL = Submit_Paypal_Payment_Info();

			System.out.println("actualPaypalURL:"+actualPaypalURL);
			System.out.println("expectedPaypalURL:"+expectedPaypalURL);
			
			expectedPaypalURL = test[0][4];

			softAssert.assertTrue(actualPaypalURL.contains(expectedPaypalURL),"expected alert message is not displayed: " + " Expected Text is:" + expectedPaypalURL
							+ " Actual text is:" + actualPaypalURL);

			softAssert.assertAll();

		} catch (Throwable e) {

			mainPage.getScreenShotForFailure(webPage, "Verify_Checkout_Flow_Paypal");
			log.error("Verify_Checkout_Flow_Paypal failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}

	}

	@Test(priority = 224, enabled = true, description = "Verify the checkout flow using PayPal option in payment info section in checkout page")
	public void Verify_Edit_Your_Cart_Link_Functionality_In_Order_Review_Section() {
		SoftAssert softAssert = new SoftAssert();
		String actualURL = null;
		String expectedURL = null;

		try {
			String[][] test1 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_PickUp_Only_Add_To_Cart_Button");

			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Edit_Your_Cart_Link_Functionality_In_Order_Review_Section");

			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			
			String[][] checkOutButton = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Proceed_To_Checkout_Button");
			
			log.info("Verify_Edit_Your_Cart_Link_Functionality_In_Order_Review_Section started");
			
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			
			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);

			connsProductPurchasePage.Click_On_PickUp_Only_Add_To_Cart_Button(webPage, test1, softAssert);


			connsProductPurchasePage.Proceed_To_Checkout_Button(webPage, checkOutButton, softAssert);

			Checkout_Guest();

			Submit_Billing_Information();
			
			
			commonMethods.clickElementbyXpath(webPage, test[0][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, test[1][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, test[2][1], softAssert);
			
			actualURL=commonMethods.getPageUrl(webPage, softAssert);
			expectedURL=test[2][4];
			
			softAssert.assertTrue(actualURL.contains(expectedURL), "'Edit Your Cart' link is not displayed in Order Review section: "+" expected is: "+ expectedURL +" actual is: "+ actualURL);

			softAssert.assertAll();

		} catch (Throwable e) {

			mainPage.getScreenShotForFailure(webPage, "Verify_Payment_Info_Continue_Button_By_Selecting_ConnsCredit");
			log.error("Verify_Payment_Info_Continue_Button_By_Selecting_ConnsCredit failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}

	}
	
	
	@Test(priority = 225, enabled = true, description = "Verify 'CONTINUE SHOPPING' button in order confirmation page")
	public void Verify_Continue_Shopping_Button_Order_Confirmation_Page() {
		SoftAssert softAssert = new SoftAssert();
		String actualURL = null;
		String expectedURL = null;

		try {
			String[][] test1 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_PickUp_Only_Add_To_Cart_Button");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Continue_Shopping_Button_Order_Confirmation_Page");
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			String[][] checkOutButton = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Proceed_To_Checkout_Button");
			
			log.info("Verify_Continue_Shopping_Button_Order_Confirmation_Page started");
			
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			
			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);

			connsProductPurchasePage.Click_On_PickUp_Only_Add_To_Cart_Button(webPage, test1, softAssert);


			connsProductPurchasePage.Proceed_To_Checkout_Button(webPage, checkOutButton, softAssert);

			Checkout_Guest();

			Submit_Billing_Information();
			
			commonMethods.clickElementbyXpath(webPage, test[0][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, test[1][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, test[2][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, test[3][1], softAssert);
			
			expectedURL=testUrl;
			
			System.out.println("expectedURL:"+expectedURL);
			System.out.println("actualURL:"+actualURL);
			
			actualURL=commonMethods.getPageUrl(webPage, softAssert);
			
			softAssert.assertEquals(actualURL, expectedURL,"continue shopping button functionality failed : "+"expectedURL is:"+ expectedURL +"actualURL is:"+ actualURL);

			softAssert.assertAll();

		} catch (Throwable e) {

			mainPage.getScreenShotForFailure(webPage, "Verify_Continue_Shopping_Button_Order_Confirmation_Page");
			log.error("Verify_Continue_Shopping_Button_Order_Confirmation_Page failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}

	}
	
	
	
	/*
	 * 
	 * UC - 004  - 2 test cases are clubbed in this test method  ( TC_004 , TC_006 ) 
	 */
	
	@Test(priority = 226, enabled = true, description = "Verify Zip Code Functionality for In-Stock Product and Verify ADD TO CART on overlay without entering input in Zip code")
	public void Verify_In_Stock_Product_Zip_Code_Functionality() {
		SoftAssert softAssert = new SoftAssert();
		String actualErrorMessage=null;
		String expectedErrorMessage=null;

		try {
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			String[][] testData2 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_In_Stock_Product_Zip_Code_Functionality");
			
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			
			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
			
			/*
			 * below method will get In_Stock availability product where Add to cart is displayed
			 */
			connsProductPurchasePage.Click_Add_To_Cart_As_Per_Avilability_Message(webPage, testData2, softAssert);
			
			boolean isOverLayBoxDisplayed=commonMethods.verifyElementisPresent(webPage, testData2[1][1], softAssert);
			if(!isOverLayBoxDisplayed){
				
				commonMethods.clickElementbyXpath(webPage, testData2[6][1], softAssert);
			}
			softAssert.assertTrue(isOverLayBoxDisplayed, "Fancy Box is not displayed");
			
			boolean isZipCodeTextBoxDisplayed=commonMethods.verifyElementisPresent(webPage, testData2[2][1], softAssert);
			softAssert.assertTrue(isZipCodeTextBoxDisplayed, "ZIP Code text box is not displayed");
			
			WebElement zipCodeTextBox=commonMethods.getWebElementbyXpath(webPage, testData2[2][1], softAssert);
			
			zipCodeTextBox.sendKeys(Keys.chord(Keys.CONTROL, "a"), " ");
			
			
			WebElement updateButton=commonMethods.getWebElementbyXpath(webPage, testData2[3][1], softAssert);
			updateButton.sendKeys(Keys.RETURN);
			//commonMethods.sendKeysbyXpath(webPage, testData2[2][1], testData2[2][4], softAssert);
			
			/*webPage.waitOnElement(By.xpath(testData2[3][1]), 10);
			commonMethods.clickElementbyXpath(webPage, testData2[3][1], softAssert);*/
			
			
			actualErrorMessage=commonMethods.getTextbyXpath(webPage, testData2[5][1], softAssert);
			expectedErrorMessage=testData2[5][4];
			System.out.println("actualErrorMessage:"+actualErrorMessage);
			System.out.println("expectedErrorMessage:"+testData2[5][4]);
			softAssert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error Message is not displayed:"+"expectedErrorMessage is:"+expectedErrorMessage + "actualErrorMessage is:"+actualErrorMessage);
			
			softAssert.assertAll();
			
		}
		 catch (Throwable e) {

				mainPage.getScreenShotForFailure(webPage, "Verify_Continue_Shopping_Button_Order_Confirmation_Page");
				log.error("Verify_Continue_Shopping_Button_Order_Confirmation_Page failed");
				log.error(e.getMessage());
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
		
		}
	/*
	 * 
	 * TC_005
	 */
	
	@Test(priority = 227, enabled = true, description = "Verify Zip Code textbox with invalid input")
	public void Verify_In_Stock_Product_Zip_Code_Functionality_In_Valid_Data() {
		SoftAssert softAssert = new SoftAssert();
		String actualErrorMessage=null;
		String expectedErrorMessage=null;

		try {
			String[][] testData1 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Verify_Page_Title");
			String[][] testData2 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_In_Stock_Product_Zip_Code_Functionality_In_Valid_Data");
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			connsProductPurchasePage.Click_On_Refrigerators(webPage, testData1, softAssert);
			
			
			
			/*
			 * below method will get In_Stock availability product where Add to cart is displayed
			 */
			connsProductPurchasePage.Click_Add_To_Cart_As_Per_Avilability_Message(webPage, testData2, softAssert);
			
			boolean isOverLayBoxDisplayed=commonMethods.verifyElementisPresent(webPage, testData2[1][1], softAssert);
			if(!isOverLayBoxDisplayed){
				
				commonMethods.clickElementbyXpath(webPage, testData2[6][1], softAssert);
			}
			softAssert.assertTrue(isOverLayBoxDisplayed, "Fancy Box is not displayed");
			
			
			
			boolean isZipCodeTextBoxDisplayed=commonMethods.verifyElementisPresent(webPage, testData2[2][1], softAssert);
			softAssert.assertTrue(isZipCodeTextBoxDisplayed, "ZIP Code text box is not displayed");
			
			
			WebElement zipCodeTextBox=commonMethods.getWebElementbyXpath(webPage, testData2[2][1], softAssert);
			
			zipCodeTextBox.sendKeys(Keys.chord(Keys.CONTROL, "a"), "9999");
			
			
			WebElement updateButton=commonMethods.getWebElementbyXpath(webPage, testData2[3][1], softAssert);
			updateButton.sendKeys(Keys.RETURN);
			
			
			
			
			actualErrorMessage=commonMethods.getTextbyXpath(webPage, testData2[5][1], softAssert);
			expectedErrorMessage=testData2[5][4];
			System.out.println("actualErrorMessage:"+actualErrorMessage);
			System.out.println("expectedErrorMessage:"+testData2[5][4]);
			softAssert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error Message is not displayed:"+"expectedErrorMessage is:"+expectedErrorMessage + "actualErrorMessage is:"+actualErrorMessage);
			
			softAssert.assertAll();
			
		}
		 catch (Throwable e) {

				mainPage.getScreenShotForFailure(webPage, "Verify_In_Stock_Product_Zip_Code_Functionality_In_Valid_Data");
				log.error("Verify_In_Stock_Product_Zip_Code_Functionality_In_Valid_Data failed");
				log.error(e.getMessage());
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
		
		}
	

	
	/*
	 * 
	 * TC_007
	 */
	@Test(priority = 228, enabled = true, description = "Verify ADD TO CART on overlay with valid input in Zip code")
	public void Verify_In_Stock_Product_Zip_Code_Functionality_Valid_Data() {
		SoftAssert softAssert = new SoftAssert();
		String actualURL=null;
		String expectedURL=null;

		try {
			String[][] testData1 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Verify_Page_Title");
			String[][] testData2 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_In_Stock_Product_Zip_Code_Functionality_Valid_Data");
			String[][] checkout = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Proceed_To_Checkout_Button");
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			connsProductPurchasePage.Click_On_Refrigerators(webPage, testData1, softAssert);
			
			
			
			/*
			 * below method will get In_Stock availability product where Add to cart is displayed
			 */
			connsProductPurchasePage.Click_On_In_Stock_Pickup_Only_Product(webPage, testData2, softAssert);
			connsProductPurchasePage.Proceed_To_Checkout_Button(webPage, checkout, softAssert);
			
			actualURL=commonMethods.getPageUrl(webPage, softAssert);
			expectedURL=testData2[6][4];
			System.out.println("actualURL:"+actualURL);
			System.out.println("expectedURL:"+expectedURL);
			softAssert.assertTrue(actualURL.contains(expectedURL), "expected URL is not dispalyed");
			
			softAssert.assertAll();
			
		}
		 catch (Throwable e) {

				mainPage.getScreenShotForFailure(webPage, "Verify_In_Stock_Product_Zip_Code_Functionality_In_Valid_Data");
				log.error("Verify_In_Stock_Product_Zip_Code_Functionality_In_Valid_Data failed");
				log.error(e.getMessage());
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
		
		}
	
	/*
	 * -------------------------------------   UC-005    -------------------------------------------------------------------------------
	 */
	
	/*
	 * 
	 * UC - 005  - 2 test cases are clubbed in this test method  ( TC_002 , TC_006 ) 
	 */
	
	@Test(priority = 229, enabled = true, description = "Verify Zip code functionality for Pick-up only Product")
	public void Verify_Pickup_Only_Product_Zip_Code_Functionality() {
		SoftAssert softAssert = new SoftAssert();
		String actualErrorMessage=null;
		String expectedErrorMessage=null;

		try {
			String[][] testData1 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Verify_Page_Title");
			String[][] testData2 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Pickup_Only_Product_Zip_Code_Functionality");
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			connsProductPurchasePage.Click_On_Refrigerators(webPage, testData1, softAssert);
			
			
			
			/*
			 * below method will get In_Stock availability product where Add to cart is displayed
			 */
			connsProductPurchasePage.Click_Add_To_Cart_As_Per_Avilability_Message(webPage, testData2, softAssert);
			
			/*
			 * 
			 * TC_002 - Verify Zip code functionality for Pick-up only Product - Started
			 */
			boolean isOverLayBoxDisplayed=commonMethods.verifyElementisPresent(webPage, testData2[1][1], softAssert);
			if(!isOverLayBoxDisplayed){
				
				commonMethods.clickElementbyXpath(webPage, testData2[6][1], softAssert);
			}
			softAssert.assertTrue(isOverLayBoxDisplayed, "Fancy Box is not displayed");
			
			boolean isZipCodeTextBoxDisplayed=commonMethods.verifyElementisPresent(webPage, testData2[2][1], softAssert);
			softAssert.assertTrue(isZipCodeTextBoxDisplayed, "ZIP Code text box is not displayed");
			
			/*
			 * 
			 * Verify Zip code functionality for Pick-up only Product - Ended
			 */
			
			
			/*
			 * 
			 * TC_006 - Verify ADD TO CART on overlay without entering input in Zip code - Started
			 */
			commonMethods.sendKeysbyXpath(webPage, testData2[2][1], testData2[1][4], softAssert);
			commonMethods.clickElementbyXpath(webPage, testData2[3][1], softAssert);
			actualErrorMessage=commonMethods.getTextbyXpath(webPage, testData2[5][1], softAssert);
			expectedErrorMessage=testData2[5][4];
			softAssert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error Message is not displayed:"+"expectedErrorMessage is:"+expectedErrorMessage + "actualErrorMessage is:"+actualErrorMessage);
			
			/*
			 * 
			 * Verify ADD TO CART on overlay without entering input in Zip code - Ended
			 */
			
			softAssert.assertAll();
			
		}
		 catch (Throwable e) {

				mainPage.getScreenShotForFailure(webPage, "Verify_Pickup_Only_Product_Zip_Code_Functionality");
				log.error("Verify_Pickup_Only_Product_Zip_Code_Functionality failed");
				log.error(e.getMessage());
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
		
		}
	
	/*
	 * TC_005 - Verify Zip Code textbox with invalid input
	 */
	@Test(priority = 230, enabled = true, description = "Verify Zip Code textbox with invalid input")
	public void Verify_Pickup_Only_Product_Zip_Code_Functionality_Invalid_Data() {
		
		SoftAssert softAssert = new SoftAssert();
		String actualErrorMessage=null;
		String expectedErrorMessage=null;

		try {
			String[][] testData1 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Verify_Page_Title");
			String[][] testData2 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Pickup_Only_Product_Zip_Code_Functionality_Invalid_Data");
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			connsProductPurchasePage.Click_On_Refrigerators(webPage, testData1, softAssert);
			
			
			
			/*
			 * below method will get In_Stock availability product where Add to cart is displayed
			 */
			connsProductPurchasePage.Click_Add_To_Cart_As_Per_Avilability_Message(webPage, testData2, softAssert);
			
			boolean isOverLayBoxDisplayed=commonMethods.verifyElementisPresent(webPage, testData2[1][1], softAssert);
			softAssert.assertTrue(isOverLayBoxDisplayed, "Fancy Box is not displayed");
			
			boolean isZipCodeTextBoxDisplayed=commonMethods.verifyElementisPresent(webPage, testData2[2][1], softAssert);
			softAssert.assertTrue(isZipCodeTextBoxDisplayed, "ZIP Code text box is not displayed");
			
			commonMethods.sendKeysbyXpath(webPage, testData2[2][1], testData2[2][4], softAssert);
			commonMethods.clickElementbyXpath(webPage, testData2[3][1], softAssert);
			
			
			actualErrorMessage=commonMethods.getTextbyXpath(webPage, testData2[5][1], softAssert);
			expectedErrorMessage=testData2[5][4];
			softAssert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error Message is not displayed:"+"expectedErrorMessage is:"+expectedErrorMessage + "actualErrorMessage is:"+actualErrorMessage);
			
			softAssert.assertAll();
			
		}
		 catch (Throwable e) {

				mainPage.getScreenShotForFailure(webPage, "Verify_Pickup_Only_Product_Zip_Code_Functionality_Invalid_Data");
				log.error("Verify_Pickup_Only_Product_Zip_Code_Functionality_Invalid_Data failed");
				log.error(e.getMessage());
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
		
		}
	
	
	
	@Test(priority =231, enabled = true, description = "Verify Zip Code textbox with invalid input")
	public void Verify_Pickup_Only_Product_Zip_Code_Functionality_valid_Data() {
		
		SoftAssert softAssert = new SoftAssert();
		String actualCartPageURL=null;
		String expectedCartPageURL=null;

		try {
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			String[][] clickOnAddToCart = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_PickUp_Only_Add_To_Cart_Button");
			String[][] testData2 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Pickup_Only_Product_Zip_Code_Functionality_valid_Data");
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
			
			connsProductPurchasePage.Click_On_PickUp_Only_Add_To_Cart_Button(webPage, clickOnAddToCart, softAssert);
			
			
			
			actualCartPageURL=commonMethods.getPageUrl(webPage, softAssert);
			expectedCartPageURL=testData2[5][4];
			
			softAssert.assertTrue(actualCartPageURL.contains(expectedCartPageURL), "Cart Page redirection is not as expected "+ " Expected is: "+ expectedCartPageURL +"Actual URL is: "+ actualCartPageURL);
			
			softAssert.assertAll();
			
		}
		 catch (Throwable e) {

				mainPage.getScreenShotForFailure(webPage, "Verify_Pickup_Only_Product_Zip_Code_Functionality_Invalid_Data");
				log.error("Verify_Pickup_Only_Product_Zip_Code_Functionality_Invalid_Data failed");
				log.error(e.getMessage());
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
		
		}
	
	
	/*
	 * 
	 *    ---------------------------------------------- UC - 006 ----------------------------------------------------------------------
	 */
	
	/*
	 * 
	 * TC - 002
	 */
	
	@Test(priority = 232, enabled = true, description = "Verify Zip Code textbox with invalid input")
	public void Verify_Zip_Code_Functionality_In_Stock_PickUp_Product() {
		
		SoftAssert softAssert = new SoftAssert();
		

		try {
			String[][] testData1 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Verify_Page_Title");
			String[][] clickOnAddToCart = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Add_In_Stock_Pickup_Only_Product_To_Cart");
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			connsProductPurchasePage.Click_On_Refrigerators(webPage, testData1, softAssert);
			commonMethods.selectDropdownByValue(webPage, "(//select[@class='hasCustomSelect'])[7]", "28", softAssert);
			
			connsProductPurchasePage.iS_Over_Lay_Box_Displayed_In_Stock_Pickup_Product(webPage, clickOnAddToCart, softAssert);
			
			
			boolean isOverLayBoxDisplayed=commonMethods.verifyElementisPresent(webPage, clickOnAddToCart[4][1], softAssert);
			boolean isZipCodeTextBoxDisplayed=commonMethods.verifyElementisPresent(webPage, clickOnAddToCart[5][1], softAssert);
			
			softAssert.assertTrue(isOverLayBoxDisplayed, "Overlay Box is not displayed on clicking Add to Cart button");
			softAssert.assertTrue(isZipCodeTextBoxDisplayed, "Zip code text box is not displayed on overlay box");
			
			
			softAssert.assertAll();
			
		}
		 catch (Throwable e) {

				mainPage.getScreenShotForFailure(webPage, "Verify_Pickup_Only_Product_Zip_Code_Functionality_Invalid_Data");
				log.error("Verify_Pickup_Only_Product_Zip_Code_Functionality_Invalid_Data failed");
				log.error(e.getMessage());
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
		
		}
	
	
	
	
	@Test(priority = 234, enabled = true, description = "Verify Zip Code textbox with invalid input")
	public void Verify_Add_To_Cart_Functionality_In_Stock_PickUp_Product_Valid_Data() {
		
		SoftAssert softAssert = new SoftAssert();
		String actualPageURL=null;
		String expectedPageURL=null;

		try {
			String[][] testData1 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Verify_Page_Title");
			String[][] clickOnAddToCart = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Add_In_Stock_Pickup_Only_Product_To_Cart");
			String[][] testData2 = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Add_To_Cart_Functionality_In_Stock_PickUp_Product_Valid_Data");
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			connsProductPurchasePage.Click_On_Refrigerators(webPage, testData1, softAssert);
			commonMethods.selectDropdownByValue(webPage, "(//select[@class='hasCustomSelect'])[7]", "28", softAssert);
			
			connsProductPurchasePage.Add_In_Stock_Pickup_Only_Product_To_Cart(webPage, clickOnAddToCart, softAssert);
			
			actualPageURL=commonMethods.getPageUrl(webPage, softAssert);
			expectedPageURL=testData2[0][4];
			
			softAssert.assertTrue(actualPageURL.contains(expectedPageURL), "Verify ADD TO CART on overlay with valid input in Zip code failed : "+ "Expected is: "+expectedPageURL +" Actual is: "+ actualPageURL);
			
			softAssert.assertAll();
			
		}
		 catch (Throwable e) {

				mainPage.getScreenShotForFailure(webPage, "Verify_Pickup_Only_Product_Zip_Code_Functionality_Invalid_Data");
				log.error("Verify_Pickup_Only_Product_Zip_Code_Functionality_Invalid_Data failed");
				log.error(e.getMessage());
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
		
		}
	
	/*
	 * 
	 * TC - 006 
	 */
	
	@Test(priority = 235, enabled = true, description = "Verify 'Pick Up IN-STORE'(product with Pick Up IN-STORE) button in cart page")
	public void Verify_Pickup_In_Store_Option_Cart_Page() {
		
		SoftAssert softAssert = new SoftAssert();
		

		try {
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			String[][] clickOnAddToCart = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Add_In_Stock_Pickup_Only_Product_To_Cart");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Pickup_In_Store_Option_Cart_Page");
			
			log.info("Verify_Pickup_In_Store_Option_Cart_Page started");
			
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			
			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
			
			commonMethods.selectDropdownByValue(webPage, "(//select[@class='hasCustomSelect'])[7]", "28", softAssert);
			
			connsProductPurchasePage.Add_In_Stock_Pickup_Only_Product_To_Cart(webPage, clickOnAddToCart, softAssert);
			
			boolean isPickupLinkDisplayed=commonMethods.verifyElementisPresent(webPage, test[0][1], softAssert);
			softAssert.assertTrue(isPickupLinkDisplayed, "Closest Pick-up locations window is not displayed");
			
			
			//clicking on pick up link
			commonMethods.clickElementbyXpath(webPage, test[0][1], softAssert);
			
			boolean isOverLayBoxDisplayed=commonMethods.verifyElementisPresent(webPage, test[1][1], softAssert);
			
			softAssert.assertTrue(isOverLayBoxDisplayed, "Closest Pick-up locations window is not displayed");
			
			softAssert.assertAll();
		
			log.info("Verify_Pickup_In_Store_Option_Cart_Page verification done ");
			
			
		}
		 catch (Throwable e) {

				mainPage.getScreenShotForFailure(webPage, "Verify_Pickup_Only_Product_Zip_Code_Functionality_Invalid_Data");
				log.error("Verify_Pickup_Only_Product_Zip_Code_Functionality_Invalid_Data failed");
				log.error(e.getMessage());
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
		
		}
	
	@Test(priority = 236, enabled = true, description = "Verify Zip Code textbox with invalid input")
	public void Verify_Get_A_Quote_Button_Cart_Page_Valid_Zip_Code() {
		
		SoftAssert softAssert = new SoftAssert();
		

		try {
			
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			String[][] clickOnAddToCart = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Add_In_Stock_Pickup_Only_Product_To_Cart");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Pickup_In_Store_Option_Cart_Page");
			String[][] getAQuote = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Enter_Zip_Code_Click_On_Get_Quote_Button");
			

			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			
			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
			
			commonMethods.selectDropdownByValue(webPage, "(//select[@class='hasCustomSelect'])[7]", "28", softAssert);
			
			connsProductPurchasePage.Add_In_Stock_Pickup_Only_Product_To_Cart(webPage, clickOnAddToCart, softAssert);
			
			
			boolean isPickupLinkDisplayed=commonMethods.verifyElementisPresent(webPage, test[0][1], softAssert);
			softAssert.assertTrue(isPickupLinkDisplayed, "Closest Pick-up locations window is not displayed");
			
			
			//clicking on pick up link
			commonMethods.clickElementbyXpath(webPage, test[0][1], softAssert);
			
			boolean isOverLayBoxDisplayed=commonMethods.verifyElementisPresent(webPage, test[1][1], softAssert);
			
			softAssert.assertTrue(isOverLayBoxDisplayed, "Closest Pick-up locations window is not displayed");
			
			String priceBeforeClickingUpdateTotalButton=commonMethods.getTextbyXpath(webPage, test[8][1], softAssert);
			
			System.out.println("priceBeforeClickingUpdateTotalButton:"+priceBeforeClickingUpdateTotalButton);
			if(isOverLayBoxDisplayed){
				
				/*String[] testData=getAQuote[0][3].split(",");
				System.out.println(getAQuote[0][3].split(","));*/
				
				
				connsProductPurchasePage.Enter_Zip_Code_Click_On_Get_Quote_Button(webPage, getAQuote, softAssert);
				
				
			}else{
				
				log.info("OverLay Box is not Displayed upon clicking pick up link in cart page");
			}
			
			
			boolean isShippingOptionsUpdated=commonMethods.verifyElementisPresent(webPage, "//*[@id='co-shipping-method-form']/dl", softAssert);
			
			softAssert.assertTrue(isShippingOptionsUpdated, " shipping options are not updated on clicking get a quote button");
			
			commonMethods.clickElementbyXpath(webPage, "//*[@id='co-shipping-method-form']/dl", softAssert);
			
			String priceAfterClickingUpdateTotalButton=commonMethods.getTextbyXpath(webPage, test[8][1], softAssert);
			
			softAssert.assertNotEquals(priceBeforeClickingUpdateTotalButton, priceAfterClickingUpdateTotalButton,"Pricing is not updated on clikcing Update Total Button");
			
			softAssert.assertAll();
			
		}
		 catch (Throwable e) {

				mainPage.getScreenShotForFailure(webPage, "Verify_Get_A_Quote_Button_Cart_Page_Valid_Zip_Code");
				log.error("Verify_Get_A_Quote_Button_Cart_Page_Valid_Zip_Code failed");
				log.error(e.getMessage());
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
		
		}
	
	
	
	@Test(priority = 237, enabled = true, description = "Verify Zip Code textbox with invalid input")
	public void Verify_Get_A_Quote_Button_Cart_Page_In_Valid_Zip_Code() {
		
		SoftAssert softAssert = new SoftAssert();
		

		try {
			
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			String[][] clickOnAddToCart = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Add_In_Stock_Pickup_Only_Product_To_Cart");
			String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Pickup_In_Store_Option_Cart_Page");
			String[][] getAQuote = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Enter_Zip_Code_Click_On_Get_Quote_Button");
			
			log.info("Verify_Get_A_Quote_Button_Cart_Page_In_Valid_Zip_Code started");

			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			
			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
			
			commonMethods.selectDropdownByValue(webPage, "(//select[@class='hasCustomSelect'])[7]", "28", softAssert);
			
			connsProductPurchasePage.Add_In_Stock_Pickup_Only_Product_To_Cart(webPage, clickOnAddToCart, softAssert);
			
			//clicking on pick up link
			commonMethods.clickElementbyXpath(webPage, test[0][1], softAssert);
			
			boolean isOverLayBoxDisplayed=commonMethods.verifyElementisPresent(webPage, test[1][1], softAssert);
			
			softAssert.assertTrue(isOverLayBoxDisplayed, "Closest Pick-up locations window is not displayed");
			
			
			if(isOverLayBoxDisplayed){
				
				/*String[] testData=getAQuote[0][3].split(",");
				System.out.println(getAQuote[0][3].split(","));*/
				
				
				connsProductPurchasePage.Enter_Zip_Code_Click_On_Get_Quote_Button(webPage, getAQuote, softAssert);
				
				
			}else{
				
				log.info("OverLay Box is not Displayed upon clicking pick up link in cart page");
			}
			
			
			boolean isShippingOptionsUpdated=commonMethods.verifyElementisPresent(webPage, "//*[@id='co-shipping-method-form']/dl", softAssert);
			
			softAssert.assertTrue(isShippingOptionsUpdated, " shipping options are not updated on clicking get a quote button");
			
			
			
			softAssert.assertAll();
			
		}
		 catch (Throwable e) {

				mainPage.getScreenShotForFailure(webPage, "Verify_Get_A_Quote_Button_Cart_Page_In_Valid_Zip_Code");
				log.error("Verify_Get_A_Quote_Button_Cart_Page_In_Valid_Zip_Code failed");
				log.error(e.getMessage());
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
		
		}
	
	
	
	@Test(priority = 238, enabled = true, description = "Verify Zip Code textbox with invalid input")
	public void Verify_Ship_To_This_Address_Radio_Button_Functionality_Valid_Billing_Info_Inputs() {
		
		SoftAssert softAssert = new SoftAssert();
		String billingInfoFirstName;
		String shippingInfoFirstName;
		

		try {
			
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			String[][] clickOnAddToCart = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Add_In_Stock_Pickup_Only_Product_To_Cart");
			String[][] checkout = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Proceed_To_Checkout_Button");
			String[][] billingInfo = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Submit_Billing_Information");
			
			log.info("Verify_Ship_To_This_Address_Radio_Button_Functionality_Valid_Billing_Info_Inputs started");

			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			
			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
			
			commonMethods.selectDropdownByValue(webPage, "(//select[@class='hasCustomSelect'])[7]", "28", softAssert);
			
			connsProductPurchasePage.Add_In_Stock_Pickup_Only_Product_To_Cart(webPage, clickOnAddToCart, softAssert);
			
			
			connsProductPurchasePage.Proceed_To_Checkout_Button(webPage, checkout, softAssert);
			
			Checkout_Guest();
			
			Submit_Billing_Information();
			
			//clicking on billing info continue button
			commonMethods.clickElementbyXpath(webPage, billingInfo[9][1],softAssert);
			
			commonMethods.clickElementbyXpath(webPage, "//*[@id='opc-shipping']/div[1]/h2/a", softAssert);
			
			shippingInfoFirstName=commonMethods.getTextbyXpath(webPage, "//*[@id='shipping:firstname']", softAssert);
			
			billingInfoFirstName=billingInfo[0][3];
			
			softAssert.assertEquals(shippingInfoFirstName, billingInfoFirstName,"Billing Info and Shipping info first names are not matching ");
			
			softAssert.assertAll();
			
		}
		 catch (Throwable e) {

				mainPage.getScreenShotForFailure(webPage, "Verify_Get_A_Quote_Button_Cart_Page_In_Valid_Zip_Code");
				log.error("Verify_Get_A_Quote_Button_Cart_Page_In_Valid_Zip_Code failed");
				log.error(e.getMessage());
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
		
		}
	
	@Test(priority = 239, enabled = true, description = "Verify Zip Code textbox with invalid input")
	public void Verify_Ship_To_Different_Address_Radio_Button_Functionality_Valid_Billing_Info_Inputs() {
		
		SoftAssert softAssert = new SoftAssert();
		String billingInfoFirstName;
		String shippingInfoFirstName;
		

		try {
			
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			String[][] clickOnAddToCart = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Add_In_Stock_Pickup_Only_Product_To_Cart");
			String[][] checkout = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Proceed_To_Checkout_Button");
			String[][] billingInfo = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Submit_Billing_Information");
			
			log.info("Verify_Ship_To_Different_Address_Radio_Button_Functionality_Valid_Billing_Info_Inputs started");

			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			
			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
			
			commonMethods.selectDropdownByValue(webPage, "(//select[@class='hasCustomSelect'])[7]", "28", softAssert);
			
			connsProductPurchasePage.Add_In_Stock_Pickup_Only_Product_To_Cart(webPage, clickOnAddToCart, softAssert);
			
			
			connsProductPurchasePage.Proceed_To_Checkout_Button(webPage, checkout, softAssert);
			
			Checkout_Guest();
			
			Submit_Billing_Information();
			
			WebElement shipToDifferentAddressRadioButton=commonMethods.getWebElementbyXpath(webPage, "//*[@id='billing:use_for_shipping_no']", softAssert);
			
			shipToDifferentAddressRadioButton.click();
			
			//clicking on billing info continue button
			commonMethods.clickElementbyXpath(webPage, billingInfo[9][1],softAssert);
			
			
			boolean isShippingInfoFirstNameEnabled=commonMethods.getWebElementbyXpath(webPage, "//*[@id='shipping:firstname']", softAssert).isEnabled();
			
			
			
			softAssert.assertTrue(isShippingInfoFirstNameEnabled,"Shipping Info Section is expanded after selecting ship to this address radio button");
			
			softAssert.assertAll();
			
		}
		 catch (Throwable e) {

				mainPage.getScreenShotForFailure(webPage, "Verify_Ship_To_Different_Address_Radio_Button_Functionality_Valid_Billing_Info_Inputs");
				log.error("Verify_Ship_To_Different_Address_Radio_Button_Functionality_Valid_Billing_Info_Inputs failed");
				log.error(e.getMessage());
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
		
		}
	
	
	@Test(priority = 240, enabled = true, description = "Verify Zip Code textbox with invalid input")
	public void Verify_Checkout_Flow_With_Zip_Code_Outside_Conns_Network() {
		
		SoftAssert softAssert = new SoftAssert();
		
		

		try {
			
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			String[][] clickOnAddToCart = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Add_In_Stock_Pickup_Only_Product_To_Cart");
			String[][] checkout = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Proceed_To_Checkout_Button");
			String[][] billingInfo = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Submit_Billing_Information");
			
			log.info("Verify_Checkout_Flow_With_Zip_Code_Outside_Conns_Network started");

			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			
			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
			
			commonMethods.selectDropdownByValue(webPage, "(//select[@class='hasCustomSelect'])[7]", "28", softAssert);
			
			connsProductPurchasePage.Add_In_Stock_Pickup_Only_Product_To_Cart(webPage, clickOnAddToCart, softAssert);
			
			
			connsProductPurchasePage.Proceed_To_Checkout_Button(webPage, checkout, softAssert);
			
			Checkout_Guest();
			
			Submit_Billing_Information();
			
			WebElement shipToDifferentAddressRadioButton=commonMethods.getWebElementbyXpath(webPage, "//*[@id='billing:use_for_shipping_no']", softAssert);
			
			shipToDifferentAddressRadioButton.click();
			
			//clicking on billing info continue button
			commonMethods.clickElementbyXpath(webPage, billingInfo[9][1],softAssert);
			
			
			boolean isShippingInfoFirstNameEnabled=commonMethods.getWebElementbyXpath(webPage, "//*[@id='shipping:firstname']", softAssert).isEnabled();
			
			
			
			softAssert.assertTrue(isShippingInfoFirstNameEnabled,"Shipping Info Section is expanded after selecting ship to this address radio button");
			
			softAssert.assertAll();
			
		}
		 catch (Throwable e) {

				mainPage.getScreenShotForFailure(webPage, "Verify_Ship_To_Different_Address_Radio_Button_Functionality_Valid_Billing_Info_Inputs");
				log.error("Verify_Ship_To_Different_Address_Radio_Button_Functionality_Valid_Billing_Info_Inputs failed");
				log.error(e.getMessage());
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
		
		}
	
	
	@Test(priority = 241, enabled = true, description = "Verify the 'Continue ' button by providing valid inputs in shipping information")
	public void Verify_Shipping_Info_Continue_Button_Valid_Info() {
		
		SoftAssert softAssert = new SoftAssert();
		
		try {
			
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			String[][] clickOnAddToCart = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Add_In_Stock_Pickup_Only_Product_To_Cart");
			String[][] checkout = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Proceed_To_Checkout_Button");
			String[][] billingInfo = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Submit_Billing_Information");
			String[][] shippingInfo = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Submit_Shipping_Info");

			log.info("Verify_Shipping_Info_COntinue_Button_Valid_Info started");

			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			
			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
			
			commonMethods.selectDropdownByValue(webPage, "(//select[@class='hasCustomSelect'])[7]", "28", softAssert);
			
			connsProductPurchasePage.Add_In_Stock_Pickup_Only_Product_To_Cart(webPage, clickOnAddToCart, softAssert);
			
			
			connsProductPurchasePage.Proceed_To_Checkout_Button(webPage, checkout, softAssert);
			
			Checkout_Guest();
			
			Submit_Billing_Information();
			
			WebElement shipToDifferentAddressRadioButton=commonMethods.getWebElementbyXpath(webPage, "//*[@id='billing:use_for_shipping_no']", softAssert);
			
			shipToDifferentAddressRadioButton.click();
			
			//clicking on billing info continue button
			commonMethods.clickElementbyXpath(webPage, billingInfo[9][1],softAssert);
			
			Submit_Shipping_Info();
			
			//clicking on shipping info continue button
			commonMethods.clickElementbyXpath(webPage, shippingInfo[8][1],softAssert);
			
			
			
			boolean isShippingMethodDisplayed=commonMethods.verifyElementisPresent(webPage, "//*[@id='checkout-shipping-method-load']/dl/dt", softAssert);
			
			
			
			softAssert.assertTrue(isShippingMethodDisplayed,"Shipping Info Section is expanded after selecting ship to this address radio button");
			
			softAssert.assertAll();
			
		}
		 catch (Throwable e) {

				mainPage.getScreenShotForFailure(webPage, "Verify_Shipping_Info_COntinue_Button_Valid_Info");
				log.error("Verify_Shipping_Info_COntinue_Button_Valid_Info failed");
				log.error(e.getMessage());
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
		
		}
	
	
	@Test(priority = 242, enabled = true, description = "Verify the 'Continue ' button by providing invalid inputs in shipping information")
	public void Verify_Shipping_Info_Continue_Button_In_Valid_Info() {
		
		SoftAssert softAssert = new SoftAssert();
		
		

		try {
			
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			String[][] clickOnAddToCart = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Add_In_Stock_Pickup_Only_Product_To_Cart");
			String[][] checkout = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Proceed_To_Checkout_Button");
			String[][] billingInfo = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Submit_Billing_Information");
			String[][] shippingInfo = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Submit_Shipping_Info");
			log.info("Verify_Shipping_Info_COntinue_Button_In_Valid_Info started");

			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			
			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
			
			commonMethods.selectDropdownByValue(webPage, "(//select[@class='hasCustomSelect'])[7]", "28", softAssert);
			
			connsProductPurchasePage.Add_In_Stock_Pickup_Only_Product_To_Cart(webPage, clickOnAddToCart, softAssert);
			
			
			connsProductPurchasePage.Proceed_To_Checkout_Button(webPage, checkout, softAssert);
			
			Checkout_Guest();
			
			Submit_Billing_Information();
			
			WebElement shipToDifferentAddressRadioButton=commonMethods.getWebElementbyXpath(webPage, "//*[@id='billing:use_for_shipping_no']", softAssert);
			
			shipToDifferentAddressRadioButton.click();
			
			//clicking on billing info continue button
			commonMethods.clickElementbyXpath(webPage, billingInfo[9][1],softAssert);
			
			Submit_Shipping_Info();
			
			//clicking on shipping info continue button
			commonMethods.clickElementbyXpath(webPage, shippingInfo[8][1],softAssert);
			
			
			/*
			 * 
			 * verification pending - verify conns shipping option in shipping method
			 */
			
			
			softAssert.assertAll();
			
		}
		 catch (Throwable e) {

				mainPage.getScreenShotForFailure(webPage, "Verify_Shipping_Info_COntinue_Button_In_Valid_Info");
				log.error("Verify_Shipping_Info_COntinue_Button_In_Valid_Info failed");
				log.error(e.getMessage());
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
		
		}
	
	
	@Test(priority = 243, enabled = true, description = "Verify the 'Continue ' button by providing invalid inputs in shipping information")
	public void Verify_Continue_Button_functionality_Conns_Shipping() {
		
		SoftAssert softAssert = new SoftAssert();
		
		

		try {
			
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			String[][] clickOnAddToCart = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Add_In_Stock_Pickup_Only_Product_To_Cart");
			String[][] checkout = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Proceed_To_Checkout_Button");
			String[][] billingInfo = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Submit_Billing_Information");
			String[][] shippingInfo = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Submit_Shipping_Info");
			log.info("Verify_Shipping_Info_COntinue_Button_In_Valid_Info started");

			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			
			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
			
			commonMethods.selectDropdownByValue(webPage, "(//select[@class='hasCustomSelect'])[7]", "28", softAssert);
			
			connsProductPurchasePage.Add_In_Stock_Pickup_Only_Product_To_Cart(webPage, clickOnAddToCart, softAssert);
			
			
			connsProductPurchasePage.Proceed_To_Checkout_Button(webPage, checkout, softAssert);
			
			Checkout_Guest();
			
			Submit_Billing_Information();
			
			WebElement shipToDifferentAddressRadioButton=commonMethods.getWebElementbyXpath(webPage, "//*[@id='billing:use_for_shipping_no']", softAssert);
			
			shipToDifferentAddressRadioButton.click();
			
			//clicking on billing info continue button
			commonMethods.clickElementbyXpath(webPage, billingInfo[9][1],softAssert);
			
			Submit_Shipping_Info();
			
			//clicking on shipping info continue button
			commonMethods.clickElementbyXpath(webPage, shippingInfo[8][1],softAssert);
			
			
			/*
			 * 
			 * verification pending - verify free shipping option in shipping method
			 */
			
			softAssert.assertAll();
			
		}
		 catch (Throwable e) {

				mainPage.getScreenShotForFailure(webPage, "Verify_Shipping_Info_COntinue_Button_In_Valid_Info");
				log.error("Verify_Shipping_Info_COntinue_Button_In_Valid_Info failed");
				log.error(e.getMessage());
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
		
		}
	
	@Test(priority = 244, enabled = true, description = "Verify the 'Continue ' button by providing invalid inputs in shipping information")
	public void Verify_Continue_Button_functionality_Free_Shipping() {
		
		SoftAssert softAssert = new SoftAssert();
		
		

		try {
			
			String[][] frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_French_Door");
			String[][] clickOnAddToCart = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Add_In_Stock_Pickup_Only_Product_To_Cart");
			String[][] checkout = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Proceed_To_Checkout_Button");
			String[][] billingInfo = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Submit_Billing_Information");
			String[][] shippingInfo = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Submit_Shipping_Info");
			log.info("Verify_Shipping_Info_COntinue_Button_In_Valid_Info started");

			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			
			connsProductPurchasePage.Click_On_Refrigerators(webPage, frenchDoor, softAssert);
			
			commonMethods.selectDropdownByValue(webPage, "(//select[@class='hasCustomSelect'])[7]", "28", softAssert);
			
			connsProductPurchasePage.Add_In_Stock_Pickup_Only_Product_To_Cart(webPage, clickOnAddToCart, softAssert);
			
			
			connsProductPurchasePage.Proceed_To_Checkout_Button(webPage, checkout, softAssert);
			
			Checkout_Guest();
			
			Submit_Billing_Information();
			
			WebElement shipToDifferentAddressRadioButton=commonMethods.getWebElementbyXpath(webPage, "//*[@id='billing:use_for_shipping_no']", softAssert);
			
			shipToDifferentAddressRadioButton.click();
			
			//clicking on billing info continue button
			commonMethods.clickElementbyXpath(webPage, billingInfo[9][1],softAssert);
			
			
			
			
			boolean isShippingInfoFirstNameEnabled=commonMethods.getWebElementbyXpath(webPage, "//*[@id='shipping:firstname']", softAssert).isEnabled();
			
			
			
			softAssert.assertTrue(isShippingInfoFirstNameEnabled,"Shipping Info Section is expanded after selecting ship to this address radio button");
			
			softAssert.assertAll();
			
		}
		 catch (Throwable e) {

				mainPage.getScreenShotForFailure(webPage, "Verify_Shipping_Info_COntinue_Button_In_Valid_Info");
				log.error("Verify_Shipping_Info_COntinue_Button_In_Valid_Info failed");
				log.error(e.getMessage());
				softAssert.assertAll();
				Assert.fail(e.getLocalizedMessage());
			}
		
		}
	
	
	
	
}
