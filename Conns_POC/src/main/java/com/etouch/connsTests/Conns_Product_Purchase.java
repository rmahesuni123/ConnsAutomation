package com.etouch.connsTests;

import java.awt.AWTException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.script.ScriptException;

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
import com.etouch.connsPages.ConnsHomePage;
import com.etouch.connsPages.ConnsMainPage;
import com.etouch.connsPages.ConnsProductPurchasePage;
import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.datamanager.excel.annotations.IExcelDataFiles;
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
	ConnsProductPurchasePage connsProductPurchasePage;	private ConnsHomePage ConnsHomePage;
	CommonMethods commonMethods;
	String testBedName;
	TestBed testBed;
	Path path;
	String DataFilePath;
	static protected String testType;
	static protected String browserName;
	String testEnv;
	JavascriptExecutor executor;
	String[][] frenchDoor;
	String[][] checkoutGuest;
	String[][] checkoutRegister;
	String[][] addToCart;
	String[][] submitBillingInfo;
	String[][] submitShippingInfo;
	String[][] proceedToCheckout;
	String[][] paypalInfo;
	String[][] pickupOnlyAddToCart;
	String[][] inStockOnlyAddToCart;
	String[][] addToCartCommon;
	String[][] zipCodeInValid;
	String[][] zipCodeValid;
	String[][] checkoutFlowCommonLocators;
	String[][] billingFormValidation;
	String[][] ItemLink;
	String[][] SubmitLoginCrdentials;
	String[][] SubmitRegisterDetails;
	static protected String[][] mobileMenuData;
	String[][] mobilePickupAvilable;
	String[][] getCredit;
	static protected String[][] commonData;
	String[][] PageUrlData;
	String testUrl = "";

	// String testUrl =
	// "http://connsecommdev-1365538477.us-east-1.elb.amazonaws.com/uat/";
	// String testUrl = "http://conns.com";
	@BeforeClass(alwaysRun = true)
	public void setUp(ITestContext context) throws InterruptedException, FileNotFoundException, IOException {
		try {
			testBedName = context.getCurrentXmlTest().getAllParameters().get("testBedName");
			CommonUtil.sop("Test bed Name is " + testBedName);
			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			TestBedManagerConfiguration.INSTANCE.getTestTypes();
			log.info("Test Type is : " + testType);
			try {
				testEnv = System.getenv().get("Environment");
				log.info("testEnv is : " + testEnv);
				log.info("testEnv is : " + System.getenv().get("Environment"));
				log.info("testEnv is : " + System.getProperty("ENVIRONMENT"));
				path = Paths.get(TestBedManager.INSTANCE.getProfile().getXlsDataConfig().get("testData"));
				DataFilePath = path.toAbsolutePath().toString().replace("Env", testEnv);
				log.info("DataFilePath After is : " + DataFilePath);
				frenchDoor = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Click_On_French_Door");
				checkoutGuest = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Checkout_Guest");
				checkoutRegister = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Checkout_Register");
				//addToCart = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Add_To_Cart_Button_Functionality");
				submitBillingInfo = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Submit_Billing_Information");
				submitShippingInfo = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Submit_Shipping_Info");
				proceedToCheckout = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Proceed_To_Checkout_Button");
				paypalInfo = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Submit_Paypal_Payment_Info");
				pickupOnlyAddToCart = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_PickUp_Only_Add_To_Cart_Button");
				inStockOnlyAddToCart = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_On_In_Stock_With_Delivery_Available");
				billingFormValidation = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_BillingInfo_Field_Validation_Positive_Inputs");
				ItemLink = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Remove_Item_Link_Functionality_In_Cart_Page");
				SubmitLoginCrdentials = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","SubmitLoginCrdentials");
				SubmitRegisterDetails = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","SubmitRegisterDetails");
				checkoutFlowCommonLocators = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Checkout_Flow_Common_Locators");
				mobileMenuData = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase", "Mobile_Menu_Details");
				mobilePickupAvilable = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Click_Add_To_Cart_As_Per_Avilability_Message2_Mobile");
				getCredit = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Verify_Get_Credit_Button_In_Cart_Page");
				commonData=ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Common_Data");
				PageUrlData = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Page_URL");
				testUrl = PageUrlData[0][0];
				browserName = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getBrowser().getName();
				platform = testBed.getPlatform().getName().toUpperCase();
				if (testType.equalsIgnoreCase("Web")) {
					log.info("videoLocation" + videoLocation);
				} else {
				}
				url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL();
				connsProductPurchasePage = new ConnsProductPurchasePage();
				commonMethods = new CommonMethods();
				synchronized (this) {
					webPage = new WebPage(context);
					mainPage = new ConnsMainPage(url, webPage);
					executor = (JavascriptExecutor) webPage.getDriver();
				}
			} catch (Exception e) {
				log.info("errr is " + e);
				SoftAssertor.addVerificationFailure(e.getMessage());
			}
		} catch (Exception e) {
			CommonUtil.sop("errr is for" + testBedName + " -----------" + e);
			SoftAssertor.addVerificationFailure(e.getMessage());
		}
	}

	@AfterTest
	public void releaseResources() throws IOException, AWTException {
		webPage.getDriver().quit();
	}
	/*

	
	
	/*This method will cover below scenarios
	 * - Verify page title for products under Appliances menu option. For eg- French door refrigertaor
	 */
	@Test(priority = 901, enabled = true, description = "Verify Page title")
	public void Verify_Page_Title() {
		SoftAssert softAssert = new SoftAssert();
		String actualTitle = null;
		String expectedTitle = null;
		try {

			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			if (testType.equalsIgnoreCase("Web")) {
				connsProductPurchasePage.Click_On_French_Door_Link(webPage, frenchDoor[1][1], softAssert);
			} else {
				connsProductPurchasePage.clickOnMobileMenuOption(webPage, mobileMenuData, softAssert);
			}
			actualTitle = commonMethods.getPageTitle(webPage, softAssert);
			expectedTitle = frenchDoor[0][3];
			log.info("Actual title is:::" + actualTitle);
			log.info("ExpectedTitle is:::" + expectedTitle);
			softAssert.assertEquals(actualTitle, expectedTitle, "Page tilte is not matching with expected title: "+ "Actual tilte is:" + actualTitle + "expected title is:" + expectedTitle);
			softAssert.assertAll();
		} catch (Exception e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Page_Title");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	
	
	/*This method will cover below scenarios
	 * - Verify overlay box is opening after clicking on Add to cart button, with Enter Location text field, Update, Add To Cart & Cancel buttons.
	 * - Validate Enter Location field with blank, invalid and valid inputs. Verify messages displayed for each type of input.[valid input coverd seperately]
	 */
	@Test(priority = 902, enabled = true, description = "verify Zip Code Text Box Error Message")
	public void Verify_OverlayBox_InStock_Product() {
		SoftAssert softAssert = new SoftAssert();
		String actualValueBlankInput = null;
		String actualValueInvalidInput = null;
		String expectedValueInvalidandBlank = null;
		try {
			String[][] inStockOverlayBox = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","InStock_Overlay_Box");
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			if (testType.equalsIgnoreCase("Web")) {
				connsProductPurchasePage.Click_On_French_Door_Link(webPage, frenchDoor[1][1], softAssert);
				connsProductPurchasePage.numberOfProductDisplaySelectDropdownByValue(webPage, checkoutFlowCommonLocators[17][1], "28", softAssert);
			} else {
				connsProductPurchasePage.clickOnMobileMenuOption(webPage, mobileMenuData, softAssert);
				commonMethods.clickElementbyXpath_usingJavaScript(webPage, mobileMenuData[4][2], softAssert);
			}

			connsProductPurchasePage.Open_OverlayBox_ForDataGiven(webPage,inStockOverlayBox, softAssert);
			log.info("Verifying if Overlay box is open for In-Stock Product");
			Assert.assertTrue(webPage.findObjectByxPath(inStockOverlayBox[0][1]).isDisplayed(),"Over Lay Box is not displayed on clicking ADD to Cart Button" + "\n ");
			
			connsProductPurchasePage.verifyOverlayBoxText(webPage, inStockOverlayBox, softAssert);
			
			log.info("Verifying In-Stock Product error message for blank input ");
			commonMethods.clearElementbyXpath(webPage, inStockOverlayBox[1][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, inStockOverlayBox[2][1], softAssert);
			expectedValueInvalidandBlank = inStockOverlayBox[3][2];
			log.info("Expected Text: "+expectedValueInvalidandBlank);
			actualValueBlankInput = commonMethods.getTextbyXpath(webPage, inStockOverlayBox[3][1], softAssert);
			softAssert.assertTrue(expectedValueInvalidandBlank.contains(actualValueBlankInput), "Zip code error message is not displayed for blank input:"+ "expected is:+" + expectedValueInvalidandBlank + "actual Value is:" + actualValueBlankInput);

			log.info("Verifying In-Stock Product error message for invalid input ");
			commonMethods.clearElementbyXpath(webPage, inStockOverlayBox[1][1], softAssert);
			commonMethods.sendKeysbyXpath(webPage, inStockOverlayBox[1][1], inStockOverlayBox[2][2], softAssert);
			commonMethods.clickElementbyXpath(webPage, inStockOverlayBox[2][1], softAssert);
			log.info("Expected Text: "+expectedValueInvalidandBlank);
			actualValueInvalidInput = commonMethods.getTextbyXpath(webPage, inStockOverlayBox[3][1], softAssert);
			softAssert.assertTrue(expectedValueInvalidandBlank.contains(actualValueInvalidInput), "Zip code error message is not displayed for invalid input:"+ "expected is:+" + expectedValueInvalidandBlank + "actual Value is:" + actualValueInvalidInput);			

			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_OverlayBox_InStock_Product");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
		log.info("verify Add To Cart Functionality");
	}
	
	
	/*This method will cover below scenarios
	 * - Verify overlay box is opening after clicking on Add to cart button, with Enter Location text field, Update, Add To Cart & Cancel buttons.
	 * - Validate Enter Location field with blank, invalid and valid inputs. Verify messages displayed for each type of input.[valid input coverd seperately]
	 */
	@Test(priority = 903, enabled = true, description = "verify Zip Code Text Box Error Message")
	public void Verify_OverlayBox_PickupOnly_Product() {
		SoftAssert softAssert = new SoftAssert();
		String actualValueBlankInput = null;
		String actualValueInvalidInput = null;
		String expectedValueInvalidandBlank = null;
		try {
			String[][] inPickupOnlyOverlayBox = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","PickupOnly__Overlay_Box");
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			if (testType.equalsIgnoreCase("Web")) {
				connsProductPurchasePage.Click_On_French_Door_Link(webPage, frenchDoor[1][1], softAssert);
				connsProductPurchasePage.numberOfProductDisplaySelectDropdownByValue(webPage, checkoutFlowCommonLocators[17][1], "28", softAssert);
			} else {
				connsProductPurchasePage.clickOnMobileMenuOption(webPage, mobileMenuData, softAssert);
				commonMethods.clickElementbyXpath_usingJavaScript(webPage, mobileMenuData[4][2], softAssert);
			}

			connsProductPurchasePage.Open_OverlayBox_ForDataGiven(webPage,inPickupOnlyOverlayBox, softAssert);
			log.info("Verifying if Overlay box is open for Pickup Only Product");
			Assert.assertTrue(webPage.findObjectByxPath(inPickupOnlyOverlayBox[0][1]).isDisplayed(),"verification 1 failed: Over Lay Box is not displayed on clicking ADD to Cart Button" + "\n ");
			
			connsProductPurchasePage.verifyOverlayBoxText(webPage, inPickupOnlyOverlayBox, softAssert);
			
			log.info("Verifying Pickup Only Product error message for blank input ");
			commonMethods.clearElementbyXpath(webPage, inPickupOnlyOverlayBox[1][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, inPickupOnlyOverlayBox[2][1], softAssert);
			expectedValueInvalidandBlank = inPickupOnlyOverlayBox[3][2];
			log.info("Expected Text: "+expectedValueInvalidandBlank);
			actualValueBlankInput = commonMethods.getTextbyXpath(webPage, inPickupOnlyOverlayBox[3][1], softAssert);
			softAssert.assertTrue(expectedValueInvalidandBlank.contains(actualValueBlankInput), "Zip code error message is not displayed for blank input:"+ "expected is:+" + expectedValueInvalidandBlank + "actual Value is:" + actualValueBlankInput);

			log.info("Verifying Pickup Only Product error message for invalid input ");
			commonMethods.clearElementbyXpath(webPage, inPickupOnlyOverlayBox[1][1], softAssert);
			commonMethods.sendKeysbyXpath(webPage, inPickupOnlyOverlayBox[1][1], inPickupOnlyOverlayBox[2][2], softAssert);
			commonMethods.clickElementbyXpath(webPage, inPickupOnlyOverlayBox[2][1], softAssert);
			log.info("Expected Text: "+expectedValueInvalidandBlank);
			actualValueInvalidInput = commonMethods.getTextbyXpath(webPage, inPickupOnlyOverlayBox[3][1], softAssert);
			softAssert.assertTrue(expectedValueInvalidandBlank.contains(actualValueInvalidInput), "Zip code error message is not displayed for invalid input:"+ "expected is:+" + expectedValueInvalidandBlank + "actual Value is:" + actualValueInvalidInput);			

			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_OverlayBox_PickupOnly_Product");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
		log.info("verify Add To Cart Functionality");
	}
	
	/*This method will cover below scenarios
	 * - On checkout cart page verify correct product is added to cart with correct price and quantity. 
	 * - For product with status 'In-Stock' update quantity and verify that price gets updated accordingly.
	 * - Verify 'Shopping Cart is Empty' message by removing all items from cart.
	 */

	@Test(priority = 904, enabled = true, description = "Verify the field validation with -ve inputs for shipping Information form in checkout page")
	public void Verify_Instock_Product_Add_To_Cart() {
		SoftAssert softAssert = new SoftAssert();
		String instockProductVaildDeliveryMessage = null;
		String productNameOnCartPage = null;
		String productShippingDetailsOnCartPage = null;
		String productUnitPriceIncart = null;
		String productSubTotalPriceIncart = null;
		String actualShoppingCartEmptyMessage = null;
		
		try {
			String[][] inStockAvialableProduct = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Add_Instock_Product");
			String[][] cartPageData = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Cart_Page_Data");
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			if (testType.equalsIgnoreCase("Web")) {
				connsProductPurchasePage.Click_On_French_Door_Link(webPage, frenchDoor[1][1], softAssert);
				connsProductPurchasePage.numberOfProductDisplaySelectDropdownByValue(webPage, checkoutFlowCommonLocators[17][1], "28", softAssert);
			} else {
				connsProductPurchasePage.clickOnMobileMenuOption(webPage, mobileMenuData, softAssert);
				commonMethods.clickElementbyXpath_usingJavaScript(webPage, mobileMenuData[4][2], softAssert);
			}
			
			List<String> actualProductTextDetails = connsProductPurchasePage.addInstockProductForAvailableLocation(webPage, inStockAvialableProduct,softAssert);
			instockProductVaildDeliveryMessage = actualProductTextDetails.get(0);
			log.info("Verifying product details such as product name, product unit price and product shipping areas on cart page");
			softAssert.assertTrue(instockProductVaildDeliveryMessage.contains(inStockAvialableProduct[9][4]));
			productNameOnCartPage = commonMethods.getTextbyXpath(webPage, cartPageData[0][1], softAssert);
			productShippingDetailsOnCartPage = commonMethods.getTextbyXpath(webPage, cartPageData[1][1], softAssert);
			productUnitPriceIncart = commonMethods.getTextbyXpath(webPage, cartPageData[2][1], softAssert);
			
			log.info("Verifying product details on cart page");
			softAssert.assertTrue(productNameOnCartPage.contains(actualProductTextDetails.get(1)),"Expected product name in cart page: "+productNameOnCartPage+" product name on product page: "+actualProductTextDetails.get(1));
			softAssert.assertTrue(productShippingDetailsOnCartPage.contains(actualProductTextDetails.get(2)),"Expected shipping details in cart page: "+productNameOnCartPage+" details on product page: "+actualProductTextDetails.get(1));
			softAssert.assertTrue(productUnitPriceIncart.contains(actualProductTextDetails.get(3)),"Expected product unit price in cart page: "+productNameOnCartPage+" price on product page: "+actualProductTextDetails.get(1));
			
			log.info("Updating quantity of product in cart page and verifying Subtotal amount");
			productUnitPriceIncart = productUnitPriceIncart.replaceAll(" ", "").replaceAll("[.$]", "").replaceAll(",", "");
			int productUnitPriceIncartInt = Integer.parseInt(productUnitPriceIncart);
			commonMethods.clearElementbyXpath(webPage, cartPageData[3][1], softAssert);
			commonMethods.sendKeysbyXpath(webPage, cartPageData[3][1], cartPageData[3][2], softAssert);
			commonMethods.clickElementbyXpath(webPage, cartPageData[4][1], softAssert);
			if(browserName.equalsIgnoreCase("Safari")||testType.equalsIgnoreCase("iPhoneNative")||testType.equalsIgnoreCase("iPadNative")){
				commonMethods.waitForGivenTime(10, softAssert);
			}
			productSubTotalPriceIncart = commonMethods.getTextbyXpath(webPage, cartPageData[5][1], softAssert);
			productSubTotalPriceIncart = productSubTotalPriceIncart.replaceAll("[.$]", "").replaceAll(",", "");
			int actualSubtotalAmount = Integer.parseInt(productSubTotalPriceIncart);
			int productQuantity = Integer.parseInt(cartPageData[3][2]);
			int expectedSubtotalAmount= productUnitPriceIncartInt*productQuantity;
			softAssert.assertEquals(expectedSubtotalAmount,actualSubtotalAmount ,"Expected sub-total amount: "+expectedSubtotalAmount+" Actual sub-total amount: "+actualSubtotalAmount);
			
			log.info("Removing product from cart and verifying empty shopping cart message");
			commonMethods.clickElementbyXpath(webPage, cartPageData[6][1], softAssert);
			CommonMethods.waitForWebElement(By.xpath(cartPageData[7][1]), webPage);
			actualShoppingCartEmptyMessage = commonMethods.getTextbyXpath(webPage, cartPageData[7][1], softAssert);
			softAssert.assertTrue(actualShoppingCartEmptyMessage.contains(cartPageData[7][2]),"Expectd message: "+cartPageData[7][2]+" Actual message: "+actualShoppingCartEmptyMessage);
			
			softAssert.assertAll();
		} catch (Exception e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Instock_Product_Add_To_Cart");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	/*This method will cover below scenarios
	 * - Verify Apply code functionality for invalid input.
	 * - Verify 'Get Credit' functionality from checkout cart page.
	 * - Verifying functionality for Continue Shopping link [Not in Scenario list]
	 */
	@Test(priority = 905, enabled = true, description = "Verify the field validation with -ve inputs for shipping Information form in checkout page")
	public void Verify_Pickup_Only_Product_Add_To_Cart() {
		SoftAssert softAssert = new SoftAssert();
		String actualCouponCodeErrorMessage = "";
		String expectedCouponCodeErrorMessage = "";
		String actualGetCreditHref_1 = "";
		String actualGetCreditHref_2 = "";
		String actualUrlForContinueShopping = "";
		try {
			String[][] pickupOnlyAvialableProduct = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Add_PickupOnly_Product");
			String[][] cartPageData = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Cart_Page_Data");
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
	
			if (testType.equalsIgnoreCase("Web")) {
				connsProductPurchasePage.Click_On_French_Door_Link(webPage, frenchDoor[1][1], softAssert);
				connsProductPurchasePage.numberOfProductDisplaySelectDropdownByValue(webPage, checkoutFlowCommonLocators[17][1], "28", softAssert);
			} else {
				connsProductPurchasePage.clickOnMobileMenuOption(webPage, mobileMenuData, softAssert);
				commonMethods.clickElementbyXpath_usingJavaScript(webPage, mobileMenuData[4][2], softAssert);
			}
			
			connsProductPurchasePage.addPickupOnlyProductForAvailableLocation(webPage, pickupOnlyAvialableProduct,softAssert);
			
			log.info("Verifying Discount coupoun code for invalid input");
			commonMethods.sendKeysbyXpath(webPage, cartPageData[8][1], cartPageData[8][2], softAssert);
			commonMethods.clickElementbyXpath(webPage, cartPageData[9][1], softAssert);
			if(browserName.equalsIgnoreCase("Safari")||browserName.equalsIgnoreCase("IE")||testType.equalsIgnoreCase("iPhoneNative")||testType.equalsIgnoreCase("iPadNative")){
				commonMethods.waitForGivenTime(10, softAssert);
			}
			actualCouponCodeErrorMessage = commonMethods.getTextbyXpath(webPage, cartPageData[10][1], softAssert).replaceAll(" ", "");
			expectedCouponCodeErrorMessage = cartPageData[10][2].replaceAll(" ", "");
			softAssert.assertTrue(actualCouponCodeErrorMessage.contains(expectedCouponCodeErrorMessage),"Coupon code error messages are not matching::" + " Expected:"+ expectedCouponCodeErrorMessage + " Actual: " + actualCouponCodeErrorMessage);
			
			log.info("Verifying functionality for Get Credit buttons");
			actualGetCreditHref_1 = commonMethods.getAttributebyXpath(webPage, cartPageData[11][1], "onclick", softAssert);
			actualGetCreditHref_2 = commonMethods.getAttributebyXpath(webPage, cartPageData[12][1], "onclick", softAssert);
			softAssert.assertTrue(actualGetCreditHref_1.contains(cartPageData[11][2]),"Expected Get Credit(1) Href value: "+cartPageData[11][2]+" Actual Get Credit(1) Href_1 value: "+actualGetCreditHref_1);
			softAssert.assertTrue(actualGetCreditHref_2.contains(cartPageData[12][2]),"Expected Get Credit(2) Href value: "+cartPageData[12][2]+" Actual Get Credit(2) Href_1 value: "+actualGetCreditHref_2);
			
			log.info("Verifying functionality for Continue Shopping link");
			actualUrlForContinueShopping = commonMethods.clickAndGetPageURL(webPage, cartPageData[13][1], cartPageData[13][0], softAssert, cartPageData[14][1]);
			softAssert.assertTrue(actualUrlForContinueShopping.contains(cartPageData[13][2]),"Expected Continue shopping link: "+cartPageData[13][2]+" Actual Continue shopping link: "+actualUrlForContinueShopping);
						
			softAssert.assertAll();
		} catch (Exception e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Pickup_Only_Product_Add_To_Cart");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	/*This method will cover below scenarios
	 * - Verify proceed to checkout functionality.
	 * - Verify Billing Information section for different payment options [Covered for Conn's Credit]
	 * - Verify following options on checkout page: Checkout as Guest
	 * - Verify product information is correctly displayed under Order Review section. For eg- Product Name, Price, Quantity, Grand Total etc.
	 * - Verified heading of each section of cart page [not in scenario list]
	 * - Verified billing information for valid and invalid input [not in scenario list]
	 */
	@Test(priority = 906, enabled = true, description = "Verify the field validation with -ve inputs for shipping Information form in checkout page")
	public void Verify_Proceed_To_Checkout_Page() {
		SoftAssert softAssert = new SoftAssert();
		String checkoutPageActualUrl = "";
		String productPriceOnCartPage = "";
		String productQuantityOnCartPage = "";
		String productGrandTotalOnCartPage = "";
		String emailIdFieldErrorMsg = "";
		String zipcodeIdFieldErrorMsg = "";
		String connsCreditActualUrl = "";
		String connsCreditExpectedUrl = "";
		
		List<WebElement> billingInfoErrorMessages = new ArrayList<WebElement>();
		
		try {
			String[][] pickupOnlyAvialableProduct = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Add_PickupOnly_Product");
			String[][] cartPageData = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Cart_Page_Data");
			String[][] checkoutPageData = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Checkout_Page_Elements");	
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
	
			if (testType.equalsIgnoreCase("Web")) {
				connsProductPurchasePage.Click_On_French_Door_Link(webPage, frenchDoor[1][1], softAssert);
				connsProductPurchasePage.numberOfProductDisplaySelectDropdownByValue(webPage, checkoutFlowCommonLocators[17][1], "28", softAssert);
			} else {
				connsProductPurchasePage.clickOnMobileMenuOption(webPage, mobileMenuData, softAssert);
				commonMethods.clickElementbyXpath_usingJavaScript(webPage, mobileMenuData[4][2], softAssert);
			}
			
			connsProductPurchasePage.addPickupOnlyProductForAvailableLocation(webPage, pickupOnlyAvialableProduct,softAssert);
			productPriceOnCartPage = commonMethods.getTextbyXpath(webPage, cartPageData[5][1], softAssert);
			productQuantityOnCartPage = commonMethods.getTextbyXpath(webPage, cartPageData[15][1], softAssert);
			productGrandTotalOnCartPage = commonMethods.getTextbyXpath(webPage, cartPageData[16][1], softAssert);
			
			connsProductPurchasePage.Proceed_To_Checkout_Button(webPage, proceedToCheckout, softAssert);
			
			log.info("Verifying actual url for checkout page");
			checkoutPageActualUrl =  commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertTrue(checkoutPageActualUrl.contains(checkoutPageData[0][2]),"Expected checkout page url: "+checkoutPageData[0][2]+" Actual checkout page url: "+checkoutPageActualUrl);
			
			log.info("Verifying text present for Checkout Method");
			softAssert.assertTrue(commonMethods.getTextbyXpath(webPage, checkoutPageData[1][1], softAssert).contains(checkoutPageData[1][2]));
			
			connsProductPurchasePage.Checkout_Guest(webPage, checkoutGuest, softAssert);
			
			log.info("Verifying text present for Billing Information");
			softAssert.assertTrue(commonMethods.getTextbyXpath(webPage, checkoutPageData[2][1], softAssert).contains(checkoutPageData[2][2]));
			
			log.info("Verifying invalid inputs for Billing Information fields");
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[3][1], softAssert);
			billingInfoErrorMessages = commonMethods.findElementsByXpath(webPage, checkoutPageData[4][1], softAssert);
			for(int i=0;i<billingInfoErrorMessages.size();i++){
				softAssert.assertTrue(billingInfoErrorMessages.get(i).getText().contains(checkoutPageData[4][2]));
			}
			
			commonMethods.sendKeysbyXpath(webPage, checkoutPageData[5][1], checkoutPageData[5][2], softAssert);
			WebElement billingEmailfield = commonMethods.getWebElementbyXpath(webPage, checkoutPageData[5][1], softAssert);
			billingEmailfield.sendKeys(Keys.TAB);
			emailIdFieldErrorMsg= commonMethods.getTextbyXpath(webPage, checkoutPageData[7][1], softAssert);
			softAssert.assertTrue(emailIdFieldErrorMsg.contains(checkoutPageData[7][2]));
			billingEmailfield.clear();
			
			commonMethods.sendKeysbyXpath(webPage, checkoutPageData[6][1], checkoutPageData[6][2], softAssert);
			WebElement billingZipcodefield = commonMethods.getWebElementbyXpath(webPage, checkoutPageData[6][1], softAssert);
			billingZipcodefield.sendKeys(Keys.TAB);
			zipcodeIdFieldErrorMsg= commonMethods.getTextbyXpath(webPage, checkoutPageData[8][1], softAssert);
			softAssert.assertTrue(zipcodeIdFieldErrorMsg.contains(checkoutPageData[8][2]));
			billingZipcodefield.clear();
			
			log.info("Entering valid input for Billing Information fields");
			connsProductPurchasePage.Submit_Billing_Information(webPage, submitBillingInfo, softAssert);
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[3][1], softAssert);
			CommonMethods.waitForGivenTime(5);
			if(testType.equalsIgnoreCase("Mobile")){
				webPage.scrollUp(1);
			}
			try{
				commonMethods.clickElementbyXpath(webPage, checkoutPageData[41][1], softAssert);
			}catch(Exception e){
				log.info("Unable to click on location radio box");
			}
			log.info("Clicking on continue for Pickup Location");
			CommonMethods.waitForWebElement(By.xpath(checkoutPageData[9][1]), webPage);
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[9][1], softAssert);
			if(testType.equalsIgnoreCase("Mobile")){
				webPage.scrollUp(1);
			}
			CommonMethods.waitForWebElement(By.xpath(checkoutPageData[11][1]), webPage);
			softAssert.assertTrue(commonMethods.getTextbyXpath(webPage, checkoutPageData[10][1], softAssert).contains(checkoutPageData[10][2]));
			softAssert.assertTrue(commonMethods.getTextbyXpath(webPage, checkoutPageData[11][1], softAssert).contains(checkoutPageData[11][2]));
			
			log.info("Verifying alert box after not slecting payment method");
			if(!browserName.equalsIgnoreCase("Safari")||!testType.equalsIgnoreCase("iPhoneNative")||!testType.equalsIgnoreCase("iPadNative")){
				try{
					CommonMethods.waitForGivenTime(5);
				commonMethods.clickElementbyXpath(webPage, checkoutPageData[12][1], softAssert);
				Alert alert = webPage.getDriver().switchTo().alert();
				String noPaymentMethodSelectedMethod = alert.getText();
				softAssert.assertTrue(noPaymentMethodSelectedMethod.contains(checkoutPageData[12][2]));
				alert.accept();
				}catch(Exception e){
					log.info("Alert box not present/handled");
				}
			}
			log.info("Selecting Payment Information as Conn's Credit");
			if(testType.equalsIgnoreCase("Mobile")){
				webPage.scrollUp(1);
			}
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[13][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[12][1], softAssert);
			
			log.info("Moving to Order Review Section");
			CommonMethods.waitForWebElement(By.xpath(checkoutPageData[17][1]), webPage);
			softAssert.assertTrue(commonMethods.getTextbyXpath(webPage, checkoutPageData[16][1], softAssert).contains(checkoutPageData[16][2]));
			
			String productQuantityInOrderReviewSection = commonMethods.getTextbyXpath(webPage, checkoutPageData[19][1], softAssert);
			String productPriceInOrderReviewSection = commonMethods.getTextbyXpath(webPage, checkoutPageData[19][1], softAssert);
			String productGrandTotalInOrderReviewSection = commonMethods.getTextbyXpath(webPage, checkoutPageData[19][1], softAssert);
			softAssert.assertTrue(productQuantityOnCartPage.contains(productQuantityInOrderReviewSection));
			softAssert.assertTrue(productPriceOnCartPage.contains(productPriceInOrderReviewSection));
			softAssert.assertTrue(productGrandTotalOnCartPage.contains(productGrandTotalInOrderReviewSection));
			
			connsCreditActualUrl = commonMethods.clickAndGetPageURL(webPage, checkoutPageData[17][1], checkoutPageData[17][0], softAssert, checkoutPageData[18][1]);
			connsCreditExpectedUrl = checkoutPageData[13][2];
			softAssert.assertTrue(connsCreditActualUrl.contains(connsCreditExpectedUrl));
			softAssert.assertAll();
		} catch (Exception e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Proceed_To_Checkout_Page");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	/*This method will cover below scenarios
	 * - Verify Billing Information section for different payment options
	 * - Verify different Payment methods under Payment Information section.
	 */
	@Test(priority = 907, enabled = true, description = "Verify the field validation with -ve inputs for shipping Information form in checkout page")
	public void Verify_PayPal_Checkout() {
		SoftAssert softAssert = new SoftAssert();
		String connsPayPalActualUrl = "";
		String connsPayPalExpectedUrl = "";
		
		try {
			String[][] pickupOnlyAvialableProduct = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Add_PickupOnly_Product");
			String[][] checkoutPageData = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Checkout_Page_Elements");
			
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			if (testType.equalsIgnoreCase("Web")) {
				connsProductPurchasePage.Click_On_French_Door_Link(webPage, frenchDoor[1][1], softAssert);
				connsProductPurchasePage.numberOfProductDisplaySelectDropdownByValue(webPage, checkoutFlowCommonLocators[17][1], "28", softAssert);
			} else {
				connsProductPurchasePage.clickOnMobileMenuOption(webPage, mobileMenuData, softAssert);
				commonMethods.clickElementbyXpath_usingJavaScript(webPage, mobileMenuData[4][2], softAssert);
			}
			
			connsProductPurchasePage.addPickupOnlyProductForAvailableLocation(webPage, pickupOnlyAvialableProduct,softAssert);
			connsProductPurchasePage.Proceed_To_Checkout_Button(webPage, proceedToCheckout, softAssert);
			connsProductPurchasePage.Checkout_Guest(webPage, checkoutGuest, softAssert);
			connsProductPurchasePage.Submit_Billing_Information(webPage, submitBillingInfo, softAssert);
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[3][1], softAssert);
			
			log.info("Clicking on continue for Pickup Location");
			CommonMethods.waitForGivenTime(5);
			if(testType.equalsIgnoreCase("Mobile")){
				webPage.scrollUp(1);
			}
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[9][1], softAssert);
			CommonMethods.waitForGivenTime(5);
			
			log.info("Selecting Payment Information as Paypal");
			if(testType.equalsIgnoreCase("Mobile")){
				webPage.scrollUp(1);
			}
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[14][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[12][1], softAssert);
			//CommonMethods.waitForWebElement(By.xpath(checkoutPageData[30][1]), webPage);
			CommonMethods.waitForGivenTime(5);
			connsPayPalActualUrl = commonMethods.getPageUrl(webPage, softAssert);
			connsPayPalExpectedUrl = checkoutPageData[14][2];
			softAssert.assertTrue(connsPayPalActualUrl.contains(connsPayPalExpectedUrl));
			
			softAssert.assertAll();
		} catch (Exception e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Password_Link_Functionality");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	/*This method will cover below scenarios
	 * - Verify Pickup Instore cart page functionality
	 * - Verify different Payment methods under Payment Information section.
	 * - Validate functionality of 'Place Order' button by verifying success message and order id. [Covered for Conn's Credit]
	 */
	@Test(priority = 908, enabled = true, description = "Verify the field validation with -ve inputs for shipping Information form in checkout page")
	public void Verify_InStockPickup_Product_And_Login_Options() {
		SoftAssert softAssert = new SoftAssert();
		String successFlowExpectedMessage = "";
		String successFlowActualMessage = "";
		
		try {
			String[][] inStockpickupAvialableProduct = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Add_Instock_Product");
			String[][] checkoutPageData = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Checkout_Page_Elements");

			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			if (testType.equalsIgnoreCase("Web")) {
				connsProductPurchasePage.Click_On_French_Door_Link(webPage, frenchDoor[1][1], softAssert);
				connsProductPurchasePage.numberOfProductDisplaySelectDropdownByValue(webPage, checkoutFlowCommonLocators[17][1], "28", softAssert);
			} else {
				connsProductPurchasePage.clickOnMobileMenuOption(webPage, mobileMenuData, softAssert);
				commonMethods.clickElementbyXpath_usingJavaScript(webPage, mobileMenuData[4][2], softAssert);
			}

			connsProductPurchasePage.addInstockPickupProductForAvailableLocation(webPage, inStockpickupAvialableProduct,softAssert);
			connsProductPurchasePage.Proceed_To_Checkout_Button(webPage, proceedToCheckout, softAssert);
			connsProductPurchasePage.Checkout_Guest(webPage, checkoutGuest, softAssert);
			connsProductPurchasePage.Submit_Billing_Information(webPage, submitBillingInfo, softAssert);
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[3][1], softAssert);

			log.info("Clicking on continue for Pickup Location");
			CommonMethods.waitForGivenTime(5);
			if(testType.equalsIgnoreCase("Mobile")){
				webPage.scrollUp(1);
			}
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[41][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[9][1], softAssert);
			CommonMethods.waitForGivenTime(5);

			log.info("Selecting payment method as cash on delivery and proceeding");
			if(testType.equalsIgnoreCase("Mobile")){
				webPage.scrollUp(1);
			}
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[15][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[12][1], softAssert);
			CommonMethods.waitForGivenTime(5);
			
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[17][1], softAssert);
			CommonMethods.waitForWebElement(By.xpath(checkoutPageData[42][1]), webPage);
			successFlowActualMessage = commonMethods.getTextbyXpath(webPage, checkoutPageData[42][1], softAssert);
			successFlowExpectedMessage = checkoutPageData[42][2];
			softAssert.assertTrue(successFlowActualMessage.contains(successFlowExpectedMessage),"Expected Oder Confirmation message: "+successFlowExpectedMessage+" Actual Order Confirmation message: "+successFlowActualMessage);
			
			softAssert.assertAll();
		} catch (Exception e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_InStockPickup_Product_And_Login_Options");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	/*This method will cover below scenarios
	 * - Verify 'forget password' functionality.
	 */
	
	@Test(priority = 909, enabled = true, description = "Verify the field validation with -ve inputs for shipping Information form in checkout page")
	public void Verify_Password_Link_Functionality() {
		SoftAssert softAssert = new SoftAssert();

		try {
			String[][] inStockAvialableProduct = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Add_Instock_Product");
			String[][] checkoutPageData = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Checkout_Page_Elements");
			String forgotPasswordActualUrl =  "";
			
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			if (testType.equalsIgnoreCase("Web")) {
				connsProductPurchasePage.Click_On_French_Door_Link(webPage, frenchDoor[1][1], softAssert);
				connsProductPurchasePage.numberOfProductDisplaySelectDropdownByValue(webPage, checkoutFlowCommonLocators[17][1], "28", softAssert);
			} else {
				connsProductPurchasePage.clickOnMobileMenuOption(webPage, mobileMenuData, softAssert);
				commonMethods.clickElementbyXpath_usingJavaScript(webPage, mobileMenuData[4][2], softAssert);
			}
			
			connsProductPurchasePage.addInstockProductForAvailableLocation(webPage, inStockAvialableProduct,softAssert);
			
			log.info("Verifying Forgot your password link");
			connsProductPurchasePage.Proceed_To_Checkout_Button(webPage, proceedToCheckout, softAssert);
			CommonMethods.waitForWebElement(By.xpath(checkoutPageData[22][1]), webPage);
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[22][1], softAssert);;
			forgotPasswordActualUrl = commonMethods.getPageUrl(webPage, softAssert);
			softAssert.assertTrue(forgotPasswordActualUrl.contains(checkoutPageData[22][2]));
			
			softAssert.assertAll();
		} catch (Exception e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Password_Link_Functionality");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	
	/*This method will cover below scenarios
	 * - Verify 'Get a quote' functionality
	 * - Verify Shipping Method section for the product added to cart.
	 */
	
	@Test(priority = 910, enabled = true, description = "Verify the field validation with -ve inputs for shipping Information form in checkout page")
	public void Verify_Get_Quote_Functionality() {
		SoftAssert softAssert = new SoftAssert();
		
		try {
			String[][] inStockAvialableProduct = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Add_Instock_Product");
			String[][] cartPageData = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Cart_Page_Data");
			String getQuoteActualErrMsg = "";
			String getQuoteActualValid = "";
			
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			if (testType.equalsIgnoreCase("Web")) {
				connsProductPurchasePage.Click_On_French_Door_Link(webPage, frenchDoor[1][1], softAssert);
				connsProductPurchasePage.numberOfProductDisplaySelectDropdownByValue(webPage, checkoutFlowCommonLocators[17][1], "28", softAssert);
			} else {
				connsProductPurchasePage.clickOnMobileMenuOption(webPage, mobileMenuData, softAssert);
				commonMethods.clickElementbyXpath_usingJavaScript(webPage, mobileMenuData[4][2], softAssert);
			}
			
			connsProductPurchasePage.addInstockProductForAvailableLocation(webPage, inStockAvialableProduct,softAssert);
		
			log.info("Verifying Get a Quote functionality for invalid input");
			commonMethods.sendKeysbyXpath(webPage, cartPageData[17][1], cartPageData[17][2], softAssert);
			commonMethods.clickElementbyXpath(webPage, cartPageData[18][1], softAssert);
			CommonMethods.waitForWebElement(By.xpath(cartPageData[19][1]), webPage);
			getQuoteActualErrMsg = commonMethods.getTextbyXpath(webPage, cartPageData[19][1], softAssert);
			softAssert.assertTrue(getQuoteActualErrMsg.contains(cartPageData[19][2]));
			commonMethods.clearTextBoxByXpath(webPage, cartPageData[17][1], softAssert);
			
			log.info("Verifying Get a Quote functionality for valid input");
			commonMethods.sendKeysbyXpath(webPage, cartPageData[17][1], cartPageData[18][2], softAssert);
			commonMethods.clickElementbyXpath(webPage, cartPageData[18][1], softAssert);
			CommonMethods.waitForWebElement(By.xpath(cartPageData[20][1]), webPage);
			getQuoteActualValid = commonMethods.getTextbyXpath(webPage, cartPageData[20][1], softAssert);
			softAssert.assertTrue(getQuoteActualValid.contains(cartPageData[20][2]));
			
			softAssert.assertAll();
		} catch (Exception e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Get_Quote_Functionality");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	/*This method will cover below scenarios
	 * - Verify Shipping Information section for non-pickup products, checking both options, 'Ship to Billing Information address' and 'Ship to different address'.
	 * - Verify Shipping Method section for the product added to cart.
	 */
	
	@Test(priority = 911, enabled = true, description = "Verify the field validation with -ve inputs for shipping Information form in checkout page")
	public void Verify_Ship_To_Different_Address__Functionality() {
		SoftAssert softAssert = new SoftAssert();
		String shippingInfoFirstName ="";
		
		try {
			String[][] inStockAvialableProduct = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Add_Instock_Product");
			String[][] checkoutPageData = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Checkout_Page_Elements");
			String[][] cartPageData = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Cart_Page_Data");
			
			commonMethods.navigateToPage(webPage, testUrl, softAssert);
			if (testType.equalsIgnoreCase("Web")) {
				connsProductPurchasePage.Click_On_French_Door_Link(webPage, frenchDoor[1][1], softAssert);
				connsProductPurchasePage.numberOfProductDisplaySelectDropdownByValue(webPage, checkoutFlowCommonLocators[17][1], "28", softAssert);
			} else {
				connsProductPurchasePage.clickOnMobileMenuOption(webPage, mobileMenuData, softAssert);
				commonMethods.clickElementbyXpath_usingJavaScript(webPage, mobileMenuData[4][2], softAssert);
			}
			
			connsProductPurchasePage.addInstockProductForAvailableLocation(webPage, inStockAvialableProduct,softAssert);
			connsProductPurchasePage.Proceed_To_Checkout_Button(webPage, proceedToCheckout, softAssert);
			
			log.info("Proceeding from checkout by clicking Register");
			CommonMethods.waitForWebElement(By.xpath(checkoutPageData[31][1]), webPage);
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[31][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[32][1], softAssert);
			CommonMethods.waitForWebElement(By.xpath(checkoutPageData[33][1]), webPage);
			String expectedPasswordFieldText = commonMethods.getTextbyXpath(webPage, checkoutPageData[33][1], softAssert);
			softAssert.assertTrue(expectedPasswordFieldText.contains(checkoutPageData[33][2]));
			
			log.info("Clicking on edit buttonin checkout");
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[34][1], softAssert);
			
			log.info("Proceeding as guest checkout");
			connsProductPurchasePage.Checkout_Guest(webPage, checkoutGuest, softAssert);
			connsProductPurchasePage.Submit_Billing_Information(webPage, submitBillingInfo, softAssert);
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[3][1], softAssert);
			
			log.info("Clicking on Shipping Info Edit Button");
			CommonMethods.waitForGivenTime(5);
			if(testType.equalsIgnoreCase("Mobile")){
				webPage.scrollUp(2);
			}
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[23][1], softAssert);
			shippingInfoFirstName = commonMethods.getAttributebyXpath(webPage, checkoutPageData[24][1], "value", softAssert);
			softAssert.assertTrue(shippingInfoFirstName.contains(submitBillingInfo[0][3]));
			
			log.info("Clicking on Billing Info Edit Button");
			if(testType.equalsIgnoreCase("Mobile")){
				webPage.scrollUp(2);
			}
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[25][1], softAssert);
			log.info("Clicking on Ship to different address radio button");
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[26][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[3][1], softAssert);
			CommonMethods.waitForGivenTime(5);
			
			log.info("Logging in");
			if(testType.equalsIgnoreCase("Mobile")){
				webPage.scrollUp(2);
			}
			CommonMethods.waitForGivenTime(2);
			if(testType.equalsIgnoreCase("Mobile")){
				webPage.scrollUp(2);
			}
			commonMethods.clickElementbyXpath(webPage,checkoutPageData[34][1] , softAssert);
			commonMethods.sendKeysbyXpath(webPage, checkoutPageData[35][1], checkoutPageData[35][2], softAssert);
			commonMethods.sendKeysbyXpath(webPage, checkoutPageData[36][1], checkoutPageData[36][2], softAssert);
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[37][1], softAssert);
			
			log.info("Deleting Cart items from user account");
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[43][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[44][1], softAssert);
			
			List<WebElement> removeItemButtons = commonMethods.findElementsByXpath(webPage, cartPageData[6][1], softAssert);
			for(WebElement element:removeItemButtons){
				element.click();
			}
			
			softAssert.assertAll();
		} catch (Exception e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Password_Link_Functionality");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
}
