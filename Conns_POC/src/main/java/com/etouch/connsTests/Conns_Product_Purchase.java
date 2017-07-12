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
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.etouch.common.BaseTest;
import com.etouch.common.TafExecutor;
import com.etouch.connsPages.ConnsMainPage;
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
	String testBedName;
	TestBed testBed;
	Path path;
	String DataFilePath;
	String testType;
	String testEnv;
	// String testUrl =
	// "http://www.conns.com/appliances/refrigerators/french-door-refrigerators";
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

					// SpecializedScreenRecorder.startVideoRecordingForDesktopBrowser(videoLocation);
				} else {
				}
				url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL();
				// url =
				// "http://connsecommdev-1365538477.us-east-1.elb.amazonaws.com/conns_rwd/";
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

			CommonUtil.sop("errr is for" + testBedName + " -----------" + e);
			SoftAssertor.addVerificationFailure(e.getMessage());
		}
	}

	@AfterTest
	public void releaseResources() throws IOException, AWTException {
		
		webPage.getDriver().quit();
		// SpecializedScreenRecorder.stopVideoRecording();
	}

	/**
	 * Test Case 001 - Verify Navigation to Conns Home Page and Verify Page
	 * title
	 * 
	 */
	/*
	 * @Test(dataProvider = "tafDataProvider", dataProviderClass =
	 * TafExcelDataProvider.class, priority = 1, enabled = false, description =
	 * "Verify Page Title")
	 * 
	 * @ITafExcelDataProviderInputs(excelFile = "CreditAppData", excelsheet =
	 * "HomePage", dataKey = "verifyPageTitle") public void
	 * verifyPageTitle(ITestContext context, TestParameters inputs) throws
	 * PageException, InterruptedException {
	 * 
	 * log.info("testing flow verifyPageTitle");
	 * 
	 * try {
	 * 
	 * Assert.assertEquals(inputs.getParamMap().get("Title"),
	 * webPage.getPageTitle());
	 * 
	 * } catch (Exception e) {
	 * SoftAssertor.addVerificationFailure(e.getMessage()); log.error(
	 * "verifyPageTitle failed"); log.error(e.getMessage()); }
	 * 
	 * }
	 */
	public void Click_On_Refrigerators() {
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "verifyPageTitle");
		try {
			webPage.hoverOnElement(By.xpath(test[0][1]));
			webPage.findObjectByxPath(test[1][1]).click();
		} catch (PageException | AWTException e) {
			log.error(e.getMessage());

		}

	}

	public void Add_To_Cart() {
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "verifyAddToCartFunctionality");
		try {
			Robot robot = new Robot();
			// Point point = driver.findElement(by).getLocation();
			robot.mouseMove(0, 16);
			webPage.findObjectByxPath(test[0][1]).click();
		} catch (PageException | AWTException e) {
			log.error(e.getMessage());

		}

	}

	/*
	 * Test Case 001 - Verify Page title --> appliances --> Refrigerators -->
	 * French Door French Door Refrigerators : Fridges by LG, Samsung & more |
	 * Conn's
	 */
	@Test(priority = 1, enabled = false, description = "Verify Page title")
	public void Verify_Page_Title() {
		SoftAssert softAssert = new SoftAssert();
		try {
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "verifyPageTitle");
		String ExpectedTitle = test[0][3];
		System.out.println("ExpectedTitle is:::"+ExpectedTitle);
		log.info("testing flow verifyPageTitle started");
		log.error("Expected title is : " + ExpectedTitle);
		

			if (testType.equalsIgnoreCase("Web")) {
				// hover on parent menu option in desktop browser
				// commonMethods.hoverOnelementbyXpath(webPage, test[0][1]);
				Click_On_Refrigerators();
				/*
				 * webPage.hoverOnElement(By.xpath(test[0][1]));
				 * webPage.findObjectByxPath(test[1][1]).click();
				 */
				// commonMethods.clickElementbyXpath(webPage, test[1][1]);
				
				// webPage.hoverOnElement(By.xpath(test[0][1]));
				// webPage.clickOnElement(By.xpath(test[1][1]));
				System.out.println("Actual title is:::" + webPage.getPageTitle());
			} else {
				// Click on hamburger menu and Parent menu option when
				// executing on mobile device
				webPage.findObjectByxPath(test[0][4]).click();
				webPage.findObjectByxPath(test[0][0]).click();
			}
			System.out.println("before assertion");
			Assert.assertEquals(ExpectedTitle, webPage.getPageTitle());
		} catch (Exception e) {
			mainPage.getScreenShotForFailure(webPage, "verifyPageTitle");
			e.printStackTrace();
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		
		}

	}
	/*
	 * Test Case 002 - Verify ADD TO CART button functionality
	 */

	@Test(priority = 2, enabled = false, description = "verify Add To Cart Functionality")
	public void Verify_Add_To_Cart_Functionality() {
		SoftAssert softAssert = new SoftAssert();
	try{
			
		
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "verifyAddToCartFunctionality");
		log.info("testing flow verifyAddToCartFunctionality started");
		String expectedValue = null;
		if (testType.equalsIgnoreCase("Web")) {
			Click_On_Refrigerators();
			// expectedValue = test[r][2];
		} else {
			Click_On_Refrigerators();
			// expectedValue = test[r][3];
		}
		
			Robot robot = new Robot();
			// Point point = driver.findElement(by).getLocation();
			robot.mouseMove(0, 12);
			webPage.findObjectByxPath(test[0][1]).click();

			softAssert.assertTrue(webPage.findObjectByxPath(test[1][1]).isDisplayed(),
					"verification 1 failed: Over Lay Box is not displayed on clicking ADD to Cart Button" + "\n ");
			String ActualValue = webPage.findObjectByxPath(test[2][1]).getAttribute("value");
			System.out.println("ActualValue:::" + ActualValue);
			System.out.println("Expected Value:::" + test[2][3]);
			softAssert.assertTrue(webPage.findObjectByxPath(test[2][1]).isDisplayed(),
					"verification 2 failed:Zip Code Text Box is not displayed on Over Lay Box" + "\n ");
			softAssert.assertTrue(ActualValue.equalsIgnoreCase(test[2][3]), "Actual values is:::" + ActualValue
					+ "  expected values is:::" + test[2][3] + "   Enter a Zip code text is not displayed" + "\n ");

			// log.info("Verifying font size and style for element no. " + (r +
			// 1));

			// Assert.assertTrue(webPage.findObjectByxPath(test[1][1]).isDisplayed());

		} catch (Throwable e) {
			
			mainPage.getScreenShotForFailure(webPage, "verifyPageTitle");
			e.printStackTrace();
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());

			/*SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("verifyAddToCartFunctionality failed");
			log.error(e.getMessage());*/
		}

		softAssert.assertAll();

		/*
		 * if (count > 0) { Assert.fail("Failed to verify element number : " +
		 * Arrays.deepToString(failedElements.toArray())); }
		 */
		log.info("verify Add To Cart Functionality");

	}

	/**
	 * Test Case - 003 - Verify Font Size and Style of specified on element on
	 * Conns Home Page
	 * 
	 */
	@Test(priority = 3, enabled = false, description = "Verify Page Font-Size And Style")
	public void Verify_Font_Size_And_Style() {
		SoftAssert softAssert = new SoftAssert();
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "verifyFontSizeAndStyle");
		log.info("testing flow verifyFontSizeAndStyle started");
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
	 * 
	 * Verify Zip Code textbox with invalid input
	 * 
	 */

	@Test(priority = 4, enabled = true, description = "verify Zip Code Text Box Error Message")
	public void Verify_Zip_Code_Text_Box_Error_Message() {
		SoftAssert sAssert = new SoftAssert();
		try {
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "verifyZipCodeTextBoxErrorMessage");
		log.info("testing flow verifyZipCodeTextBoxErrorMessage started");
		String expectedValue = null;
		if (testType.equalsIgnoreCase("Web")) {
			Click_On_Refrigerators();
			Add_To_Cart();
			// expectedValue = test[r][2];
		} else {
			Click_On_Refrigerators();
			Add_To_Cart();
			// expectedValue = test[r][3];
		}
		
			/*
			 * Robot robot=new Robot(); //Point point =
			 * driver.findElement(by).getLocation(); robot.mouseMove(0, 12);
			 * webPage.findObjectByxPath(test[0][1]).click();
			 */

			sAssert.assertTrue(webPage.findObjectByxPath(test[0][1]).isDisplayed(),
					"verification 1 failed: Over Lay Box is not displayed on clicking ADD to Cart Button" + " \n ");
			// String
			// ActualValue=webPage.findObjectByxPath(test[2][1]).getAttribute("value");
			sAssert.assertTrue(webPage.findObjectByxPath(test[1][1]).isDisplayed(),
					"verification 2 failed:Zip Code Text Box is not displayed on Over Lay Box" + " \n ");
			System.out.println("test[1][1]:::" + test[1][1]);
			System.out.println("test[0][3]:::" + test[0][3]);
			System.out.println("test[3][1]:::" + test[3][1]);

			webPage.findObjectByxPath(test[1][1]).clear();
			webPage.wait(4000);
			webPage.getDriver().switchTo().activeElement();
			webPage.findObjectByxPath(test[1][1]).sendKeys(test[0][3]);
			webPage.wait(4000);
			webPage.findObjectByxPath(test[3][1]).click();
			
			webPage.wait(4000);
			System.out.println("actual text is:::" + webPage.findObjectByxPath(test[4][1]).getText());
			sAssert.assertTrue(webPage.findObjectByxPath(test[4][1]).getText().contains(test[0][4]));

		} catch (Throwable e) {

			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("verifyZipCodeTextBoxErrorMessage failed");
			log.error(e.getMessage());
			//sAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}

		sAssert.assertAll();

		log.info("verify Add To Cart Functionality");

	}

}
