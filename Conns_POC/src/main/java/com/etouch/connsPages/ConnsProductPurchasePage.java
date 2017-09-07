package com.etouch.connsPages;

import java.awt.AWTException;
import java.awt.Robot;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.etouch.common.CommonMethods;
import com.etouch.common.TafExecutor;
import com.etouch.connsTests.Conns_Product_Purchase;
import com.etouch.taf.core.datamanager.excel.annotations.IExcelDataFiles;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.webui.selenium.WebPage;

@IExcelDataFiles(excelDataFiles = { "CreditAppData=testData" })
public class ConnsProductPurchasePage extends Conns_Product_Purchase {
	static String platform;
	static Log log = LogUtil.getLog(ConnsProductPurchasePage.class);
	static String AbsolutePath = TafExecutor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	static String videoLocation = AbsolutePath.substring(0, AbsolutePath.indexOf("/target/classes/")).substring(1)
			.concat("/src/test/resources/testdata/videos");
	private String url = null;
	private WebPage webPage;
	private ConnsMainPage mainPage;
	CommonMethods commonMethods = new CommonMethods();
	private ConnsHomePage ConnsHomePage;
	Path path;
	String DataFilePath;

	public void Click_On_Refrigerators(WebPage webPage, String[][] test, SoftAssert softAssert) {
		try {
			webPage.getDriver().manage().deleteAllCookies();
			log.info("test[0][1]):" + test[0][1]);
			webPage.hoverOnElement(By.xpath(test[0][1]));
			webPage.findObjectByxPath(test[1][1]).click();
		} catch (PageException | AWTException e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
	}

	public void Click_On_French_Door_Link(WebPage webPage, String test, SoftAssert softAssert) throws InterruptedException {
		try {
			try{
				webPage.getDriver().manage().deleteAllCookies();
				webPage.getDriver().manage().deleteAllCookies();
			}catch(Exception e){
				log.info("Unable to delete cookies for current browser.");
			}
			WebElement element = webPage.findObjectByxPath(test).getWebElement();
			JavascriptExecutor executor = (JavascriptExecutor) webPage.getDriver();
			executor.executeScript("arguments[0].click();", element);
			commonMethods.waitForPageLoad(webPage, softAssert);
			//commonMethods.waitForGivenTime(3, softAssert);
			log.info("clicked on :" + test);
		} catch (PageException e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
	}

	public static void Click_On_French_Door_Link(WebPage webPage, String test) {
		try {
			webPage.getDriver().manage().deleteAllCookies();
			WebElement element = webPage.findObjectByxPath(test).getWebElement();
			JavascriptExecutor executor = (JavascriptExecutor) webPage.getDriver();
			executor.executeScript("arguments[0].click();", element);
			log.info("clicked on :" + test);
		} catch (PageException e) {
			log.error(e.getMessage());
		}
	}
	public static void Click_On_Element_JS(WebPage webPage, String test) {
		try {
			WebElement element = webPage.findObjectByxPath(test).getWebElement();
			JavascriptExecutor executor = (JavascriptExecutor) webPage.getDriver();
			executor.executeScript("arguments[0].click();", element);
			log.info("clicked on :" + test);
		} catch (PageException e) {
			log.error(e.getMessage());

		}
	}
	public void Click_On_Element_JS(WebPage webPage, String test, SoftAssert softAssert) {
		try {
			WebElement element = webPage.findObjectByxPath(test).getWebElement();
			JavascriptExecutor executor = (JavascriptExecutor) webPage.getDriver();
			executor.executeScript("arguments[0].click();", element);
			log.info("clicked on :" + test);
		} catch (PageException e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
	}

	public void Add_To_Cart(WebPage webPage, String[][] addToCart, SoftAssert softAssert) {
		String ProductText = null;
		WebElement product;
		try {
			List<WebElement>listOfProducts = commonMethods.getWebElementsbyXpath(webPage,"html/body/div[2]/div/div[4]/div[2]/div/div[4]/div[5]/ul/li", softAssert);

			log.info("Size of List: "+listOfProducts.size());
			for (int j = 1; j < listOfProducts.size(); j++) {
				product = webPage.getDriver().findElement(By.xpath(commonData[0][1] + j + "]"));
				log.info(j + ":::" + "Web Element Details are:::" + product.getText());
				ProductText = product.getText();
				if ((ProductText.contains(addToCart[0][3]))&&(ProductText.contains(addToCart[3][3]))) {
					commonMethods.clickElementbyXpath(webPage,addToCart[4][1] + j + "]", softAssert);
					Thread.sleep(3000);
					boolean isOverLayBoxPresent = commonMethods.verifyElementisPresent(webPage, addToCart[1][1], softAssert);
					if(!isOverLayBoxPresent){
						try{
							commonMethods.clickElementbyXpath(webPage, commonData[3][1], softAssert);	
						}catch(Exception e){
							log.info("Overlay box is already open");
						}
					}
					CommonMethods.waitForWebElement(By.xpath(addToCart[1][1]), webPage);
					isOverLayBoxPresent = commonMethods.verifyElementisPresent(webPage, addToCart[1][1], softAssert);
					log.info("isOverLayBoxPresent:::" + isOverLayBoxPresent);
					softAssert.assertTrue(isOverLayBoxPresent,"verification 1 failed: Over Lay Box is not displayed on clicking ADD to Cart Button"+ "\n ");
					boolean isZipCodeTextBoxDisplayed = webPage.getDriver().findElements(By.xpath(addToCart[2][1])).size() >= 1;
					log.info("isZipCodeTextBoxDisplayed:::" + isZipCodeTextBoxDisplayed);
					break;
				} else if ((ProductText.contains(addToCart[1][3]))&&(ProductText.contains(addToCart[3][3]))) {
					commonMethods.clickElementbyXpath(webPage,addToCart[4][1] + j + "]", softAssert);
					Thread.sleep(3000);
					boolean isOverLayBoxPresent = commonMethods.verifyElementisPresent(webPage, addToCart[1][1], softAssert);
					if(!isOverLayBoxPresent){
						try{
							commonMethods.clickElementbyXpath(webPage, commonData[3][1], softAssert);	
						}catch(Exception e){
							log.info("Overlay box is already open");
						}
					}
					CommonMethods.waitForWebElement(By.xpath(addToCart[1][1]), webPage);
					isOverLayBoxPresent = commonMethods.verifyElementisPresent(webPage, addToCart[1][1], softAssert);
					log.info("isOverLayBoxPresent:::" + isOverLayBoxPresent);
					softAssert.assertTrue(isOverLayBoxPresent,"verification 1 failed: Over Lay Box is not displayed on clicking ADD to Cart Button"+ "\n ");
					boolean isZipCodeTextBoxDisplayed = webPage.getDriver().findElements(By.xpath(addToCart[2][2])).size() >= 1;
					log.info("isZipCodeTextBoxDisplayed:::" + isZipCodeTextBoxDisplayed);
					break;
				} else {
					log.error("No product is not displayed with Add to cart button in product list page ");
				}

			}
		} catch (Throwable e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
	}

	public void Enter_Zip_Code_Click_On_Get_Quote_Button(WebPage webPage, String[][] test, SoftAssert softAssert) {
		try {
			Robot robot = new Robot();
			robot.mouseMove(0, 16);
			commonMethods.sendKeysbyXpath(webPage, test[1][1], test[1][3], softAssert);
			commonMethods.clickElementbyXpath(webPage, test[1][1], softAssert);
		} catch (Throwable e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
	}

	public void Checkout_Guest(WebPage webPage, String[][] checkoutGuest, SoftAssert softAssert) {
		try {
			Click_On_Element_JS(webPage, checkoutGuest[0][1], softAssert);
			Click_On_Element_JS(webPage, checkoutGuest[1][1], softAssert);
			Thread.sleep(5000);
		} catch (Throwable e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
	}

	public boolean isAlertPresent() {
		try {
			webPage.getDriver().switchTo().alert();
			return true;
		} // try
		//Changes by deepak
		catch (Exception e) {
			//done
			return false;
		} // catch
	}


	public void Submit_Billing_Information(WebPage webPage, String[][] test, SoftAssert softAssert) {
		try {
			checkoutSendKeysbyXpath(webPage, test[0][1], test[0][3], softAssert);
			checkoutSendKeysbyXpath(webPage, test[1][1], test[1][3], softAssert);
			checkoutSendKeysbyXpath(webPage, test[2][1], test[2][3], softAssert);
			checkoutSendKeysbyXpath(webPage, test[3][1], test[3][3], softAssert);
			checkoutSendKeysbyXpath(webPage, test[4][1], test[4][3], softAssert);
			checkoutSendKeysbyXpath(webPage, test[5][1], test[5][3], softAssert);
			checkoutSendKeysbyXpath(webPage, test[6][1], test[6][3], softAssert);
			commonMethods.selectDropdownByValue(webPage, test[7][1], test[7][3], softAssert);
			Thread.sleep(2000);
		} catch (Throwable e) {
			log.error("Submit_Billing_Information failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}


	public void Submit_Shipping_Info(WebPage webPage, String[][] submitShippingInfo, SoftAssert softAssert) {
		try {
			commonMethods.getWebElementbyXpath(webPage, submitShippingInfo[0][1], softAssert).clear();
			checkoutSendKeysbyXpath(webPage, submitShippingInfo[0][1], submitShippingInfo[0][3], softAssert);
			commonMethods.getWebElementbyXpath(webPage, submitShippingInfo[1][1], softAssert).clear();
			checkoutSendKeysbyXpath(webPage, submitShippingInfo[1][1], submitShippingInfo[1][3], softAssert);
			commonMethods.getWebElementbyXpath(webPage, submitShippingInfo[2][1], softAssert).clear();
			checkoutSendKeysbyXpath(webPage, submitShippingInfo[2][1], submitShippingInfo[2][3], softAssert);
			commonMethods.getWebElementbyXpath(webPage, submitShippingInfo[3][1], softAssert).clear();
			checkoutSendKeysbyXpath(webPage, submitShippingInfo[3][1], submitShippingInfo[3][3], softAssert);
			commonMethods.getWebElementbyXpath(webPage, submitShippingInfo[5][1], softAssert).clear();
			checkoutSendKeysbyXpath(webPage, submitShippingInfo[5][1], submitShippingInfo[5][3], softAssert);
			commonMethods.selectDropdownByValue(webPage, submitShippingInfo[6][1], submitShippingInfo[6][3],softAssert);

		} catch (Throwable e) {
			log.error("Click_On_In_Stock_Add_To_Cart_Button failed");
			log.error(e.getMessage());
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void Proceed_To_Checkout_Button(WebPage webPage, String[][] test, SoftAssert softAssert) {
		try {
			log.info("clicking on checkout");
			commonMethods.clickElementbyXpath(webPage, test[0][1], softAssert);
			log.info("clicked on checkout");
			Thread.sleep(5000);
		} catch (Throwable e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
	}

	public void Submit_Paypal_Payment_Info(WebPage webPage, String[][] test, SoftAssert softAssert) {
		try {
			CommonMethods.waitForWebElement(By.xpath(test[0][1]), webPage);
			commonMethods.clickElementbyXpath(webPage, test[0][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, test[1][1], softAssert);
			Thread.sleep(5000);
			log.info("test[1][4]:" + test[1][4]);
		} catch (Throwable e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
	}


	public void click_Register_Radio_Button(WebPage webPage, String[][] test, SoftAssert softAssert) {
		try {
			commonMethods.clickElementbyXpath(webPage, test[0][1], softAssert);
			Thread.sleep(5000);
		} catch (Throwable e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
	}

	public void click_Register_Continue_Button(WebPage webPage, String[][] test, SoftAssert softAssert) {
		try {
			commonMethods.clickElementbyXpath(webPage, test[1][1], softAssert);
			Thread.sleep(5000);
		} catch (Throwable e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
	}

	public void click_Billing_Info_Continue_Button(WebPage webPage, String[][] test, SoftAssert softAssert) {
		try {
			commonMethods.clickElementbyXpath(webPage, test[4][1], softAssert);
			Thread.sleep(10000);
		} catch (Throwable e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
	}

	public void click_Place_Order_Button(WebPage webPage, String[][] test, SoftAssert softAssert) {
		try {
			commonMethods.clickElementbyXpath(webPage, test[15][1], softAssert);
			Thread.sleep(5000);
		} catch (Throwable e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
	}

	public void click_Pickup_Location_Continue_Button(WebPage webPage, String[][] test, SoftAssert softAssert) {
		try {
			String pickupLocationContinueButton = test[5][1];
			Click_On_Element_JS(webPage, pickupLocationContinueButton, softAssert);
			Thread.sleep(10000);
		} catch (Throwable e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
	}

	public void click_Shipping_Method_Continue_Button(WebPage webPage, String[][] test, SoftAssert softAssert) {
		try {
			String shippingMethodContinueButton = test[26][1];
			Click_On_Element_JS(webPage, shippingMethodContinueButton, softAssert);
		} catch (Throwable e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
	}

	public void click_Payment_Info_Continue_Button(WebPage webPage, String[][] test, SoftAssert softAssert) {
		try {
			String shippingMethodContinueButton = test[27][1];
			Click_On_Element_JS(webPage, shippingMethodContinueButton, softAssert);
			Thread.sleep(5000);
		} catch (Throwable e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
	}

	public String get_Pickup_Location_Product_Name(WebPage webPage, String[][] test, SoftAssert softAssert) {
		String productNamePickupLocation = null;
		try {
			String productNameInPickupLocationSection = test[6][1];
			productNamePickupLocation = commonMethods.getTextbyXpath(webPage, productNameInPickupLocationSection,softAssert);
		} catch (Throwable e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
		return productNamePickupLocation;
	}

	public void click_Conns_Credit_Radio_Button(WebPage webPage, String[][] test, SoftAssert softAssert) {
		try {
			String connsCreditRadioButton = test[7][1];
			Click_On_Element_JS(webPage, connsCreditRadioButton, softAssert);
			Thread.sleep(5000);
		} catch (Throwable e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
	}

	public void click_Paypal_Credit_Radio_Button(WebPage webPage, String[][] test, SoftAssert softAssert) {
		try {
			String paypalRadioButton = test[8][1];
			Click_On_Element_JS(webPage, paypalRadioButton, softAssert);
		} catch (Throwable e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
	}

	public void click_Cash_On_Delivery_Radio_Button(WebPage webPage, String[][] test, SoftAssert softAssert) {
		try {
			String cashOnDeliveryRadioButton = test[9][1];
			Click_On_Element_JS(webPage, cashOnDeliveryRadioButton, softAssert);
		} catch (Throwable e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
	}

	public void get_Product_Name_Order_Review_Section(WebPage webPage, String[][] test, SoftAssert softAssert) {
		try {
			String orderReviewSectionProductName = test[11][1];
			commonMethods.getTextbyXpath(webPage, orderReviewSectionProductName, softAssert);
		} catch (Throwable e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
	}

	public void get_Product_Price_Order_Review_Section(WebPage webPage, String[][] test, SoftAssert softAssert) {
		try {
			String orderReviewSectionProductPrice = test[12][1];
			commonMethods.getTextbyXpath(webPage, orderReviewSectionProductPrice, softAssert);
		} catch (Throwable e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
	}

	public void get_Product_quantity_Order_Review_Section(WebPage webPage, String[][] test, SoftAssert softAssert) {
		try {
			String orderReviewSectionProductQty = test[13][1];
			commonMethods.getTextbyXpath(webPage, orderReviewSectionProductQty, softAssert);
		} catch (Throwable e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
	}

	public void get_SubTotal_Order_Review_Section(WebPage webPage, String[][] test, SoftAssert softAssert) {
		try {
			String orderReviewSectionProductSubtotal = test[14][1];
			commonMethods.getTextbyXpath(webPage, orderReviewSectionProductSubtotal, softAssert);
		} catch (Throwable e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
	}

	/*
	 * Working - select pickup only product and check the product is avilable
	 * for delivery
	 * 
	 */
	public void Click_On_PickUp_Only_Add_To_Cart_Button(WebPage webPage, String[][] test, SoftAssert softAssert) {
		String stockAvilabilityText = null;
		String errorMessage = null;
		try {
			List<WebElement> listOfProducts = commonMethods.getWebElementsbyXpath(webPage,commonData[1][1], softAssert);
			WebElement product;
			log.info("Size of list: "+listOfProducts.size());
			// int counter = 1;
			for (int i = 1; i <= listOfProducts.size(); i++) {
				product = webPage.getDriver().findElement(By.xpath(commonData[0][1] + i + "]"));
				/*
				 * verifying whether availability text is pickup only or not
				 */
				log.info("test[0][3]:::" + test[0][3]);
				if ((product.getText().contains(test[0][3])) && (product.getText().contains(test[0][2]))) {
					commonMethods.clickElementbyXpath(webPage, commonData[2][1] + i + "]",softAssert);

					boolean isOverlayOpen = commonMethods.verifyElementisPresent(webPage, test[4][1], softAssert);
					if(!isOverlayOpen){
						try{
							commonMethods.clickElementbyXpath(webPage, commonData[3][1], softAssert);	
						}catch(Exception e){
							log.info("Overlay box is already open");
						}
					}

					// counter++;
					Thread.sleep(3000);
					webPage.findObjectByxPath(test[5][1]).clear();
					webPage.waitOnElement(By.xpath(test[5][1]), 10);
					commonMethods.clearElementbyXpath(webPage, test[5][1], softAssert);
					commonMethods.sendKeysbyXpath(webPage, test[5][1], test[5][3], softAssert);
					Thread.sleep(5000);
					webPage.waitOnElement(By.xpath(test[6][1]), 10);
					commonMethods.clickElementbyXpath(webPage, test[6][1], softAssert);
					Thread.sleep(10000);
					boolean isPresent = webPage.getDriver().findElements(By.xpath(test[8][1])).size() > 0;
					if (!isPresent) {
						log.info("before clicking add to cart on modal box");
						commonMethods.clickElementbyXpath(webPage, test[7][1], softAssert);
						log.info("after clicking add to cart on modal box");
						if (webPage.getDriver().getPageSource().contains("Shopping Cart is Empty")) {
							boolean isShoppingCartEmpty = webPage.getDriver().getPageSource()
									.contains("Shopping Cart is Empty");
							log.info("isShoppingCartEmpty:" + isShoppingCartEmpty);
							Assert.assertFalse(isShoppingCartEmpty,
									"--------- Functionality Failure ::: Actual:Shopping cart is empty  Expected: product should be added to cart-------");
							// break;
						}
						log.info("clicked pickup only on add to cart button");
						break;
					} else {
						errorMessage = commonMethods.getTextbyXpath(webPage, test[8][1], softAssert);
						log.info("errorMessage:::" + errorMessage);
						log.info("test[8][4]:::" + test[8][4]);
						if (errorMessage.contains(test[8][4])) {
							log.info("captures error message:::" + errorMessage);
							webPage.getDriver().findElement(By.xpath(commonData[4][1])).click();
							Thread.sleep(3000);
							if (testType.equalsIgnoreCase("Web")) {
								Click_On_French_Door_Link(webPage, commonData[7][1], softAssert);
								numberOfProductDisplaySelectDropdownByValue(webPage, commonData[8][1], "28", softAssert);
							} else {
								clickOnMobileMenuOption(webPage, mobileMenuData, softAssert);
								commonMethods.clickElementbyXpath_usingJavaScript(webPage, mobileMenuData[4][2], softAssert);
							}
						}
					}
				}
			}
		} catch (Throwable e) {
			log.error("Click_On_PickUp_Only_Add_To_Cart_Button failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			// Assert.fail(e.getLocalizedMessage());
		}
	}

	public void Enter_Zip_Code_Click_On_Add_To_Cart(WebPage webPage, String[][] test, SoftAssert softAssert) {
		try {
			commonMethods.verifyElementisPresent(webPage, test[0][1], softAssert);
			webPage.findObjectByxPath(test[1][1]).clear();
			webPage.waitOnElement(By.xpath(test[1][1]), 10);
			commonMethods.sendKeysbyXpath(webPage, test[1][1], test[0][3], softAssert);
			Thread.sleep(5000);
			commonMethods.clickElementbyXpath(webPage, test[2][1], softAssert);
			Thread.sleep(10000);
			commonMethods.clickElementbyXpath(webPage, test[3][1], softAssert);
		} catch (Throwable e) {
			log.error(e.getMessage());
		}
	}

	public void Click_On_In_Stock_Product_Add_To_Cart_Button(WebPage webPage, String[][] test, SoftAssert softAssert) {
		String stockAvilabilityText = null;
		try {
			List<WebElement> listOfProducts = commonMethods.getWebElementsbyXpath(webPage, test[0][1], softAssert);
			for (WebElement product : listOfProducts) {
				stockAvilabilityText = product.findElement(By.xpath(test[2][1])).getText().trim();
				if (stockAvilabilityText.equalsIgnoreCase(test[0][3])) {
					product.findElement(By.xpath(test[3][1])).click();
					log.info("clicked pickup only on add to cart button");
					break;
				} else if (commonMethods.verifyElementisPresent(webPage, test[1][1], softAssert)) {
					webPage.waitOnElement(By.xpath(test[1][1]), 5);
					commonMethods.clickElementbyXpath(webPage, test[1][1], softAssert);
					stockAvilabilityText = product.findElement(By.xpath(test[2][1])).getText();
					if (stockAvilabilityText.equalsIgnoreCase(test[0][3])) {
						product.findElement(By.xpath(test[3][1])).click();
						log.info("clicked pickup only on add to cart button in 2nd page");
						break;
					}
				}
			}
		} catch (Throwable e) {
			log.error(e.getMessage());
			log.error("Click_On_In_Stock_Product_Add_To_Cart_Button failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/*
	 * 
	 * Common Method to click on ADD TO CART Button for In_Stock Availability
	 * option in all available pages
	 */
	public void Select_Conns_Credit(WebPage webPage, String[][] test, SoftAssert softAssert) {
		try {
			commonMethods.clickElementbyXpath(webPage, test[0][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, test[2][1], softAssert);
			Thread.sleep(5000);
		} catch (Throwable e) {
			// mainPage.getScreenShotForFailure(webPage,
			// "Click_On_In_Stock_Add_To_Cart_Button");
			log.error("Click_On_In_Stock_Add_To_Cart_Button failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}


	public void Add_In_Stock_Pickup_Only_Product_To_Cart(WebPage webPage, String[][] test, SoftAssert softAssert) {
		String errorMessage = null;
		try {
			List<WebElement> listOfProducts = commonMethods.getWebElementsbyXpath(webPage,commonData[1][1], softAssert);
			WebElement product;
			int counter = 1;
			for (int i = 1; i <= listOfProducts.size(); i++) {
				product = webPage.getDriver().findElement(By.xpath(commonData[0][1] + i + "]"));
				log.info("Web Element Details:::" + product.getText() + i);
				/*
				 * verifying whether availability text is pickup only or not
				 */
				boolean isInStockAvailabilityDisplayed = (product.getText().contains(test[0][3])&& product.getText().contains(test[0][2]));
				log.info("isInStockAvailabilityDisplayed:" + isInStockAvailabilityDisplayed);
				log.info("test[0][3]:::" + test[0][3]);
				log.info("i::::::::::::::::::::::::::" + i
						+ "-------------------------------------------------------------------------");
				if (isInStockAvailabilityDisplayed) {
					commonMethods.clickElementbyXpath(webPage, commonData[2][1] + i + "]",softAssert);
					boolean isOverlayBoxDisplayed = commonMethods.verifyElementisPresent(webPage, commonData[4][1], softAssert);
					log.info("isOverlayBoxDisplayed after selecting product:" + isOverlayBoxDisplayed);
					if (!isOverlayBoxDisplayed) {
						//try/catch added by deepak to fix issue
						try{
							commonMethods.clickElementbyXpath(webPage, commonData[6][1], softAssert);
							commonMethods.clickElementbyXpath(webPage, commonData[3][1], softAssert);
						}catch(Exception e){
							log.info("Unable to open overlay box");
						}
					}
					//done
					// counter++;
					isOverlayBoxDisplayed = commonMethods.verifyElementisPresent(webPage, commonData[4][1], softAssert);
					log.info("isOverlayBoxDisplayed after clicking on subit:" + isOverlayBoxDisplayed);
					if (isOverlayBoxDisplayed) {
						webPage.waitOnElement(By.xpath(test[5][1]), 10);
						JavascriptExecutor executor = (JavascriptExecutor) webPage.getDriver();
						executor.executeScript("document.getElementById('warehouse-zip-code').value='';");
						executor.executeScript("document.getElementById('warehouse-zip-code').value='77701';");
						log.info("test[5][3]:" + test[5][3]);
						webPage.waitOnElement(By.xpath(test[6][1]), 10);
						commonMethods.clickElementbyXpath(webPage, test[6][1], softAssert);
						Thread.sleep(3000);
						boolean isPresent = webPage.getDriver().findElements(By.xpath(test[8][1])).size() > 0;
						log.info("isPresent:" + isPresent);
						log.info("webPage.getDriver().findElements(By.xpath(test[8][1])).size():"
								+ webPage.getDriver().findElements(By.xpath(test[8][1])).size());
						if (!isPresent) {
							log.info("before clicking add to cart on modal box");
							commonMethods.clickElementbyXpath(webPage, test[7][1], softAssert);
							log.info("after clicking add to cart on modal box");
							if (webPage.getDriver().getPageSource().contains("Shopping Cart is Empty")) {
								boolean isShoppingCartEmpty = webPage.getDriver().getPageSource().contains("Shopping Cart is Empty");
								log.info("isShoppingCartEmpty:" + isShoppingCartEmpty);
								Assert.assertFalse(isShoppingCartEmpty,
										"--------- Functionality Failure ::: Actual:Shopping cart is empty  Expected: product should be added to cart-------");
								// break;
							}
							break;
						} else {
							log.info("in else block driver navigate back");
							counter++;
							if (testType.equalsIgnoreCase("Web")) {
								Click_On_French_Door_Link(webPage, commonData[7][1], softAssert);
								numberOfProductDisplaySelectDropdownByValue(webPage, commonData[8][1], "28", softAssert);
							} else {
								clickOnMobileMenuOption(webPage, mobileMenuData, softAssert);
								commonMethods.clickElementbyXpath_usingJavaScript(webPage, mobileMenuData[4][2], softAssert);
							}

							Thread.sleep(10000);
						}
					} else {
						log.info("in else block driver navigate back");
						counter++;
						if (testType.equalsIgnoreCase("Web")) {
							Click_On_French_Door_Link(webPage, commonData[7][1], softAssert);
							numberOfProductDisplaySelectDropdownByValue(webPage, commonData[8][1], "28", softAssert);
						} else {
							clickOnMobileMenuOption(webPage, mobileMenuData, softAssert);
							commonMethods.clickElementbyXpath_usingJavaScript(webPage, mobileMenuData[4][2], softAssert);
						}

						Thread.sleep(10000);
					}
				}
			}

		} catch (Throwable e) {
			log.error("Click_On_PickUp_Only_Add_To_Cart_Button failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}


	//added by deepak
	public void Add_In_Stock_Pickup_Only_Product_To_Verify_Different_Address_Radio_Button(WebPage webPage, String[][] test, SoftAssert softAssert) {
		String errorMessage = null;
		try {
			List<WebElement> listOfProducts = commonMethods.getWebElementsbyXpath(webPage,
					commonData[1][1], softAssert);
			WebElement product;
			int counter = 1;
			for (int i = 1; i <= listOfProducts.size(); i++) {
				product = webPage.getDriver().findElement(By.xpath(commonData[0][1] + i + "]"));
				log.info("Web Element Details:::" + product.getText() + i);
				/*
				 * verifying whether availability text is pickup only or not
				 */
				boolean isInStockAvailabilityDisplayed = (product.getText().contains(test[0][3])&& product.getText().contains(test[0][2]));
				log.info("isInStockAvailabilityDisplayed:" + isInStockAvailabilityDisplayed);
				log.info("test[0][3]:::" + test[0][3]);
				log.info("i::::::::::::::::::::::::::" + i
						+ "-------------------------------------------------------------------------");
				if (isInStockAvailabilityDisplayed) {
					commonMethods.clickElementbyXpath(webPage, commonData[2][1] + i + "]",softAssert);
					boolean isOverlayBoxDisplayed = commonMethods.verifyElementisPresent(webPage, commonData[4][1], softAssert);
					log.info("isOverlayBoxDisplayed after selecting product:" + isOverlayBoxDisplayed);
					if (!isOverlayBoxDisplayed) {
						//try/catch added by deepak to fix issue
						try{
							commonMethods.clickElementbyXpath(webPage, commonData[3][1], softAssert);
						}catch(Exception e){
							log.info("Unable to open overlay box");
						}
					}
					//done
					// counter++;
					isOverlayBoxDisplayed = commonMethods.verifyElementisPresent(webPage, commonData[4][1], softAssert);
					log.info("isOverlayBoxDisplayed after clicking on subit:" + isOverlayBoxDisplayed);
					if (isOverlayBoxDisplayed) {
						webPage.waitOnElement(By.xpath(test[5][1]), 10);
						JavascriptExecutor executor = (JavascriptExecutor) webPage.getDriver();
						executor.executeScript("document.getElementById('warehouse-zip-code').value='';");
						executor.executeScript("document.getElementById('warehouse-zip-code').value='77701';");
						log.info("test[5][3]:" + test[5][3]);
						webPage.waitOnElement(By.xpath(test[6][1]), 10);
						commonMethods.clickElementbyXpath(webPage, test[6][1], softAssert);
						Thread.sleep(3000);
						boolean isPresent = webPage.getDriver().findElements(By.xpath(test[8][1])).size() > 0;
						log.info("isPresent:" + isPresent);
						log.info("webPage.getDriver().findElements(By.xpath(test[8][1])).size():"
								+ webPage.getDriver().findElements(By.xpath(test[8][1])).size());
						if (!isPresent) {
							log.info("before clicking add to cart on modal box");
							commonMethods.clickElementbyXpath(webPage, test[7][1], softAssert);
							log.info("after clicking add to cart on modal box");
							if (webPage.getDriver().getPageSource().contains("Shopping Cart is Empty")) {
								boolean isShoppingCartEmpty = webPage.getDriver().getPageSource()
										.contains("Shopping Cart is Empty");
								log.info("isShoppingCartEmpty:" + isShoppingCartEmpty);
								Assert.assertFalse(isShoppingCartEmpty,
										"--------- Functionality Failure ::: Actual:Shopping cart is empty  Expected: product should be added to cart-------");
								// break;
							}
							break;
						} else {
							log.info("in else block driver navigate back");
							counter++;
							if (testType.equalsIgnoreCase("Web")) {
								Click_On_French_Door_Link(webPage, commonData[7][1], softAssert);
								numberOfProductDisplaySelectDropdownByValue(webPage, commonData[8][1], "28", softAssert);
							} else {
								clickOnMobileMenuOption(webPage, mobileMenuData, softAssert);
								commonMethods.clickElementbyXpath_usingJavaScript(webPage, mobileMenuData[4][2], softAssert);
							}

							Thread.sleep(10000);
						}
					} else {
						log.info("in else block driver navigate back");
						counter++;
						if (testType.equalsIgnoreCase("Web")) {
							Click_On_French_Door_Link(webPage, commonData[7][1], softAssert);
							numberOfProductDisplaySelectDropdownByValue(webPage, commonData[8][1], "28", softAssert);
						} else {
							clickOnMobileMenuOption(webPage, mobileMenuData, softAssert);
							commonMethods.clickElementbyXpath_usingJavaScript(webPage, mobileMenuData[4][2], softAssert);
						}

						Thread.sleep(10000);
					}
				} 
			}
		}
		catch (Throwable e) {
			log.error(e.getMessage());
			log.error("Click_On_PickUp_Only_Add_To_Cart_Button failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}



	/*
	 * 
	 * working method
	 */
	public void Page_Zip_Code_Functionality_In_Stock_PickUp_Product(WebPage webPage, String[][] test,
			SoftAssert softAssert) {
		try {
			List<WebElement> listOfProducts = commonMethods.getWebElementsbyXpath(webPage,commonData[1][1], softAssert);
			WebElement product;
			int counter = 1;
			for (int i = 1; i <= listOfProducts.size(); i++) {
				product = webPage.getDriver().findElement(By.xpath(commonData[0][1] + i + "]"));
				log.info("Web Element Details:::" + product.getText() + i);
				/*
				 * verifying whether availability text is pickup only or not
				 */
				boolean isInStockAvailabilityDisplayed = (product.getText().contains(test[0][3])&& product.getText().contains(test[0][2]));
				log.info("isInStockAvailabilityDisplayed:" + isInStockAvailabilityDisplayed);
				log.info("test[0][3]:::" + test[0][3]);
				log.info("test[0][2]:::" + test[0][2]);
				if (isInStockAvailabilityDisplayed) {
					commonMethods.clickElementbyXpath(webPage, commonData[2][1] + i + "]",softAssert);
					boolean isOverLayBoxDisplayed = commonMethods.verifyElementisPresent(webPage, commonData[4][1], softAssert);
					log.info("isOverlayBoxDisplayed after selecting product:" + isOverLayBoxDisplayed);
					if (!isOverLayBoxDisplayed) {
						//added by deepak to fix overlayissue
						try{
							commonMethods.clickElementbyXpath(webPage, commonData[6][1], softAssert);
							commonMethods.clickElementbyXpath(webPage, commonData[3][1], softAssert);
						}catch(Exception e){
							log.info("Unable to open overlay box");
						}
					}

					isOverLayBoxDisplayed = commonMethods.verifyElementisPresent(webPage, commonData[4][1], softAssert);
					log.info("isOverlayBoxDisplayed after clicking on subit:" + isOverLayBoxDisplayed);
					if(isOverLayBoxDisplayed){
						isOverLayBoxDisplayed = commonMethods.verifyElementisPresent(webPage, test[4][1],softAssert);
						log.info("isOverLayBoxDisplayed:" + isOverLayBoxDisplayed);
						boolean isZipCodeTextBoxDisplayed = commonMethods.verifyElementisPresent(webPage, test[5][1],softAssert);
						log.info("isZipCodeTextBoxDisplayed:" + isZipCodeTextBoxDisplayed);
						softAssert.assertTrue(isOverLayBoxDisplayed,"Overlay Box is not displayed on clicking Add to Cart button");
						softAssert.assertTrue(isZipCodeTextBoxDisplayed,"Zip code text box is not displayed on overlay box");
						break;
					}else {
						log.info("driver navigating back to search results page");
						counter++;
						if (testType.equalsIgnoreCase("Web")) {
							Click_On_French_Door_Link(webPage, commonData[7][1], softAssert);
							numberOfProductDisplaySelectDropdownByValue(webPage, commonData[8][1], "28", softAssert);
						} else {
							clickOnMobileMenuOption(webPage, mobileMenuData, softAssert);
							commonMethods.clickElementbyXpath_usingJavaScript(webPage, mobileMenuData[4][2], softAssert);
						}
						Thread.sleep(10000);
					}

				}
			}
		} catch (Throwable e) {
			log.error(e.getMessage());
			log.error("Click_On_PickUp_Only_Add_To_Cart_Button failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
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
		} catch (Throwable e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
	}

	public String Click_On_In_Stock_With_Delivery_Available(WebPage webPage, String[][] inStockOnlyAddToCart,
			SoftAssert softAssert) {
		String errorMessage = null;
		String productText = null;
		try {
			List<WebElement> listOfProducts = commonMethods.getWebElementsbyXpath(webPage,
					commonData[1][1], softAssert);
			log.info("list items:" + listOfProducts.size());
			WebElement product;
			int counter = 1;
			for (int i = 1; i <= listOfProducts.size(); i++) {
				log.info("IN FOR: "+i);
				product = webPage.getDriver().findElement(By.xpath(commonData[0][1] + i + "]"));
				if ((product.getText().contains(inStockOnlyAddToCart[0][3]))&& (product.getText().contains(inStockOnlyAddToCart[0][2]))) {
					log.info("inside if");
					productText = product.getText();
					log.info("clicking on element:::" + i);
					log.info(i + ":::clicking on link");
					commonMethods.clickElementbyXpath(webPage, commonData[5][1] + i + "]",
							softAssert);
					log.info(i + ":::clicked on link");
					//try catch block added by deepak for failing test
					boolean isOverlayDisplayed = commonMethods.verifyElementisPresent(webPage,commonData[4][1], softAssert);
					if(!isOverlayDisplayed){
						try{
							commonMethods.clickElementbyXpath(webPage, commonData[3][1], softAssert);
						}catch(Exception e){
							log.info("Clicking Add to Cart button not required as Overlay box is already open");
						}
					}
					//
					//done
					isOverlayDisplayed = commonMethods.verifyElementisPresent(webPage,commonData[4][1], softAssert);
					// Thread.sleep(3000);
					log.info("isOverlayDisplayed:" + isOverlayDisplayed);
					webPage.findObjectByxPath(inStockOnlyAddToCart[5][1]).clear();
					boolean isZipCodeTextBoxDisplayed = commonMethods.verifyElementisPresent(webPage,
							inStockOnlyAddToCart[5][1], softAssert);
					log.info("isZipCodeTextBoxDisplayed:" + isZipCodeTextBoxDisplayed);
					webPage.waitOnElement(By.xpath(inStockOnlyAddToCart[5][1]), 10);
					commonMethods.sendKeysbyXpath(webPage, inStockOnlyAddToCart[5][1], inStockOnlyAddToCart[5][3],
							softAssert);
					Thread.sleep(5000);
					webPage.waitOnElement(By.xpath(inStockOnlyAddToCart[6][1]), 10);
					commonMethods.clickElementbyXpath(webPage, inStockOnlyAddToCart[6][1], softAssert);
					Thread.sleep(10000);
					boolean isPresent = webPage.getDriver().findElements(By.xpath(inStockOnlyAddToCart[8][1]))
							.size() > 0;
							if (!isPresent) {
								log.info("before clicking add to cart on modal box");
								commonMethods.clickElementbyXpath(webPage, inStockOnlyAddToCart[7][1], softAssert);
								Thread.sleep(5000);
								log.info("after clicking add to cart on modal box");
								//added by deepak
								if (isAlertPresent())
								{
									Alert alert = webPage.getDriver().switchTo().alert();
									alert.accept();
									Thread.sleep(7000);
									commonMethods.clickElementbyXpath(webPage, inStockOnlyAddToCart[7][1], softAssert);
								}
								//done
								if (webPage.getDriver().getPageSource().contains("Shopping Cart is Empty")) {
									boolean isShoppingCartEmpty = webPage.getDriver().getPageSource()
											.contains("Shopping Cart is Empty");
									log.info("isShoppingCartEmpty:" + isShoppingCartEmpty);
									Assert.assertFalse(isShoppingCartEmpty,
											"--------- Functionality Failure ::: Actual:Shopping cart is empty  Expected: product should be added to cart-------");
									// break;
								}
								log.info("clicked pickup only on add to cart button");
								break;
							} else {
								errorMessage = commonMethods.getTextbyXpath(webPage, inStockOnlyAddToCart[8][1], softAssert);
								log.info("errorMessage:::" + errorMessage);
								log.info("test[8][4]:::" + inStockOnlyAddToCart[8][4]);
								if (errorMessage.contains(inStockOnlyAddToCart[8][4])) {
									log.info("captures error message:::" + errorMessage);
									webPage.getDriver().findElement(By.xpath(commonData[4][1])).click();

									if (testType.equalsIgnoreCase("Web")) {
										Click_On_French_Door_Link(webPage, commonData[7][1], softAssert);
										numberOfProductDisplaySelectDropdownByValue(webPage, commonData[8][1], "28", softAssert);
									} else {
										clickOnMobileMenuOption(webPage, mobileMenuData, softAssert);
										commonMethods.clickElementbyXpath_usingJavaScript(webPage, mobileMenuData[4][2], softAssert);
									}

								}
							}
				}
			}
		} catch (Throwable e) {
			log.error("Click_On_PickUp_Only_Add_To_Cart_Button failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
		return productText;
	}

	/*
	 * Verify Zip Code Functionality for In-Stock Product , Verify ADD TO CART
	 * on overlay without entering input in Zip code
	 */
	public void Click_Add_To_Cart_As_Per_Avilability_Message(WebPage webPage, String[][] test, SoftAssert softAssert) {
		try {
			List<WebElement> listOfProducts = commonMethods.getWebElementsbyXpath(webPage,commonData[1][1], softAssert);
			WebElement product;
			for (int i = 1; i <= listOfProducts.size(); i++) {
				product = webPage.getDriver().findElement(By.xpath(commonData[0][1] + i + "]"));
				log.info("Web Element Details:" + product.getText() + i);
				log.info("test[0][3]:::" + test[0][3]);
				/*
				 * verifying whether availability text is pickup only or not
				 */
				if ((product.getText().contains(test[0][3])) && (product.getText().contains(test[0][2]))) {
					log.info("clicking on element:::" + i);
					commonMethods.clickElementbyXpath(webPage, commonData[5][1] + i + "]",
							softAssert);
					//below line is commented deepak to test on UAT
					boolean isOverlayDisplayed = commonMethods.verifyElementisPresent(webPage,commonData[4][1], softAssert);
					if(!isOverlayDisplayed){
						try{
							commonMethods.clickElementbyXpath(webPage, commonData[3][1], softAssert);
						}catch(Exception e){
							log.info("Unable to click on ADD TO CART button from product page as modal is already open.");
						}

					}
					//done
					isOverlayDisplayed = commonMethods.verifyElementisPresent(webPage, commonData[4][1], softAssert);
					log.info("isOverlayDisplayed:" + isOverlayDisplayed);
					Thread.sleep(3000);
					softAssert.assertTrue(isOverlayDisplayed, "Overlay box is not displayed");
					/* webPage.findObjectByxPath(test[5][1]).clear(); */
					boolean isZipCodeTextBoxDisplayed = commonMethods.verifyElementisPresent(webPage, test[5][1],softAssert);
					log.info("isZipCodeTextBoxDisplayed:" + isZipCodeTextBoxDisplayed);
					softAssert.assertTrue(isZipCodeTextBoxDisplayed, "zip code text box is not displayed");
					commonMethods.getWebElementbyXpath(webPage, test[5][1], softAssert).clear();
					commonMethods.sendKeysbyXpath(webPage, test[5][1], test[5][3], softAssert);
					Thread.sleep(1000);
					webPage.waitOnElement(By.xpath(test[6][1]), 10);
					commonMethods.clickElementbyXpath(webPage, test[6][1], softAssert);
					Thread.sleep(10000);
					log.info("error message count:" + webPage.getDriver().findElements(By.xpath(test[8][1])).size());
					boolean isErrorMessageDispalyed = webPage.getDriver().findElements(By.xpath(test[8][1])).size() > 0;
					log.info("isErrorMessageDispalyed:" + isErrorMessageDispalyed);
					softAssert.assertTrue(isErrorMessageDispalyed,"error message is not displayed for empty zip code search");
					break;
				}
			}
		} catch (Throwable e) {
			log.error("Click_On_PickUp_Only_Add_To_Cart_Button failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public WebPage Click_On_Any_Products_Add_To_Cart(WebPage webPage2, String[][] inStockOnlyAddToCart,
			SoftAssert softAssert) {
		String errorMessage = null;
		try {
			List<WebElement> listOfProducts = commonMethods.getWebElementsbyXpath(webPage,
					commonData[1][1], softAssert);
			WebElement product;
			int counter = 1;
			for (int i = 1; i <= listOfProducts.size(); i++) {
				product = webPage.getDriver().findElement(By.xpath(
						commonData[0][1] + i + "]"));
				log.info("Web Element Details" + product.getText() + i);
				/*
				 * checking on add to cart button if it is available
				 */
				if (product.getText().contains(inStockOnlyAddToCart[0][3])
						|| product.getText().contains(inStockOnlyAddToCart[1][3])) {
					log.info("clicking on element:::" + i);
					commonMethods.clickElementbyXpath(webPage, commonData[5][1] + i + "]",
							softAssert);
					boolean isOverlayDisplayed = commonMethods.verifyElementisPresent(webPage,commonData[4][1], softAssert);
					if(!isOverlayDisplayed){
						try{
							commonMethods.clickElementbyXpath(webPage, commonData[3][1], softAssert);
						}catch(Exception e){
							log.info("Unable to click on ADD TO CART button from product page as modal is already open.");
						}
					}
					isOverlayDisplayed = commonMethods.verifyElementisPresent(webPage,commonData[4][1], softAssert);
					// Thread.sleep(3000);
					log.info("isOverlayDisplayed:" + isOverlayDisplayed);
					webPage.findObjectByxPath(inStockOnlyAddToCart[5][1]).clear();
					boolean isZipCodeTextBoxDisplayed = commonMethods.verifyElementisPresent(webPage,
							inStockOnlyAddToCart[5][1], softAssert);
					log.info("isZipCodeTextBoxDisplayed:" + isZipCodeTextBoxDisplayed);
					webPage.waitOnElement(By.xpath(inStockOnlyAddToCart[5][1]), 10);
					commonMethods.sendKeysbyXpath(webPage, inStockOnlyAddToCart[5][1], inStockOnlyAddToCart[5][3],
							softAssert);
					Thread.sleep(5000);
					webPage.waitOnElement(By.xpath(inStockOnlyAddToCart[6][1]), 10);
					commonMethods.clickElementbyXpath(webPage, inStockOnlyAddToCart[6][1], softAssert);
					Thread.sleep(10000);
					boolean isPresent = webPage.getDriver().findElements(By.xpath(inStockOnlyAddToCart[8][1]))
							.size() > 0;
							if (!isPresent) {
								log.info("before clicking add to cart on modal box");
								commonMethods.clickElementbyXpath(webPage, inStockOnlyAddToCart[7][1], softAssert);
								Thread.sleep(2000);
								log.info("after clicking add to cart on modal box");
								if (webPage.getDriver().getPageSource().contains("Shopping Cart is Empty")) {
									boolean isShoppingCartEmpty = webPage.getDriver().getPageSource()
											.contains("Shopping Cart is Empty");
									log.info("isShoppingCartEmpty:" + isShoppingCartEmpty);
									Assert.assertFalse(isShoppingCartEmpty,
											"--------- Functionality Failure ::: Actual:Shopping cart is empty  Expected: product should be added to cart-------");
									// break;
								}
								log.info("clicked pickup only on add to cart button");
								break;
							} else {
								errorMessage = commonMethods.getTextbyXpath(webPage, inStockOnlyAddToCart[8][1], softAssert);
								log.info("errorMessage:::" + errorMessage);
								log.info("test[8][4]:::" + inStockOnlyAddToCart[8][4]);
								if (errorMessage.contains(inStockOnlyAddToCart[8][4])) {
									log.info("captures error message:::" + errorMessage);
									webPage.getDriver().findElement(By.xpath(commonData[4][1])).click();
									Thread.sleep(3000);
									if (testType.equalsIgnoreCase("Web")) {
										Click_On_French_Door_Link(webPage, commonData[7][1], softAssert);
										numberOfProductDisplaySelectDropdownByValue(webPage, commonData[8][1], "28", softAssert);
									} else {
										clickOnMobileMenuOption(webPage, mobileMenuData, softAssert);
										commonMethods.clickElementbyXpath_usingJavaScript(webPage, mobileMenuData[4][2], softAssert);
									}
								}
							}
				}
			}
		} catch (Throwable e) {
			log.error("Click_On_PickUp_Only_Add_To_Cart_Button failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
		return webPage;
	}

	public List<String> page_Verify_Product_Details_Cart(WebPage webPage, String[][] test, SoftAssert softAssert) {
		ArrayList<String> actualValue = new ArrayList<String>();
		String productPriceCartPage = null;
		String ProductNameCartPage = null;
		try {
			// product price cart page
			productPriceCartPage = commonMethods.getTextbyXpath(webPage, test[24][1], softAssert);
			log.info("productPriceCartPage:" + productPriceCartPage);
			// product name cart page
			ProductNameCartPage = commonMethods.getTextbyXpath(webPage, test[20][1], softAssert);
			log.info("productLinkTextCartPage:" + ProductNameCartPage);
			actualValue.add(ProductNameCartPage);
			actualValue.add(productPriceCartPage);
		} catch (Throwable e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
		return actualValue;
	}

	public List<String> page_Verify_Order_Review_Details(WebPage webPage2, String[][] checkoutFlowCommonLocators,
			SoftAssert softAssert) {
		ArrayList<String> actualValue = new ArrayList<String>();
		String productPrice = null;
		String productName = null;
		// String productQty=null;
		// String subTotal=null;
		try {
			// product price cart page
			productName = commonMethods.getTextbyXpath(webPage, checkoutFlowCommonLocators[11][1], softAssert);
			log.info("productName review section:" + productName);
			// product name cart page
			productPrice = commonMethods.getTextbyXpath(webPage, checkoutFlowCommonLocators[12][1], softAssert);
			log.info("productPrice review section:" + productPrice);
			actualValue.add(productName);
			actualValue.add(productPrice);
		} catch (Throwable e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
		return actualValue;
	}

	public List<String> page_Verify_Cart_Sidebar_Checkout(WebPage webPage2, String[][] checkoutFlowCommonLocators,
			SoftAssert softAssert) {
		ArrayList<String> actualValue = new ArrayList<String>();
		String productPrice = null;
		String productName = null;
		try {
			// product price cart side bar
			productName = commonMethods.getTextbyXpath(webPage, checkoutFlowCommonLocators[28][1], softAssert);
			log.info("productName cart sidebar:" + productName);
			// product name cart side bar
			productPrice = commonMethods.getTextbyXpath(webPage, checkoutFlowCommonLocators[29][1], softAssert);
			log.info("product price cart sidebar:" + productPrice);
			actualValue.add(productName);
			actualValue.add(productPrice);
		} catch (Throwable e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
		return actualValue;
	}

	public List<String> page_Verify_Cart_Header_Details(WebPage webPage, String[][] checkoutFlowCommonLocators,
			SoftAssert softAssert) {
		ArrayList<String> actualValue = new ArrayList<String>();
		String productPrice = null;
		String productName = null;
		try {
			// clicking on cart side header
			commonMethods.clickElementbyXpath(webPage, checkoutFlowCommonLocators[30][1], softAssert);
			// product price cart side header
			productName = commonMethods.getTextbyXpath(webPage, checkoutFlowCommonLocators[31][1], softAssert);
			log.info("productName cart header:" + productName);
			// product name cart side header
			productPrice = commonMethods.getTextbyXpath(webPage, checkoutFlowCommonLocators[32][1], softAssert);
			log.info("product price cart header:" + productPrice);
			actualValue.add(productName);
			actualValue.add(productPrice);
		} catch (Throwable e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
		return actualValue;
	}

	public void clickOnMobileMenuOption(WebPage webPage, String[][] mobileMenuData, SoftAssert softAssert) {
		log.info("Clicking on mobile menu for applicances ");
		try {
			webPage.getDriver().manage().deleteAllCookies();
			commonMethods.clickElementbyXpath(webPage, mobileMenuData[0][2], softAssert);
			commonMethods.clickElementbyXpath(webPage, mobileMenuData[1][2], softAssert);
			commonMethods.clickElementbyXpath(webPage, mobileMenuData[2][2], softAssert);
			commonMethods.clickElementbyXpath(webPage, mobileMenuData[3][2], softAssert);
		} catch (Exception e) {
			softAssert.fail("Failed to click on French Door menu in mobile.");
		}
	}



	public void page_Is_Shopping_Cart_Empty(WebPage webPage, SoftAssert softAssert) {
		try {
			if (webPage.getDriver().getPageSource().contains("Shopping Cart is Empty")) {
				boolean isShoppingCartEmpty = webPage.getDriver().getPageSource().contains("Shopping Cart is Empty");
				log.info("isShoppingCartEmpty:" + isShoppingCartEmpty);
				Assert.assertFalse(isShoppingCartEmpty,
						"--------- Functionality Failure ::: Actual:Shopping cart is empty  Expected: product should be added to cart-------");
				// break;
			} else {
				log.info("shopping cart empty message is not displayed");
			}
		} catch (Throwable e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
		}
	}
	public static void page_Is_Shopping_Cart_Empty(WebPage webPage) throws InterruptedException {
		Thread.sleep(3000);
		boolean isShoppingCartEmpty = webPage.getDriver().getPageSource().contains("Shopping Cart is Empty");
		log.info("isShoppingCartEmpty:" + isShoppingCartEmpty);
		Assert.assertFalse(isShoppingCartEmpty,
				"--------- Functionality Failure ::: Actual:Shopping cart is empty  Expected: product should be added to cart-------");
	}


	public void checkoutSendKeysbyXpath(WebPage webPage, String locator, String text, SoftAssert softAssert){
		try{
			log.info("Entering keys "+text+" ");
			webPage.findObjectByxPath(locator).sendKeys(text);
		}catch(Exception e){
			softAssert.fail("Unable to Enter Keys : "+text+" using locator : "+locator+". Localized Message: "+e.getLocalizedMessage());
			Assert.fail();
		}
	}

	public void numberOfProductDisplaySelectDropdownByValue(WebPage webPage, String locator,String dropdownvalue ,SoftAssert softAssert) {
		try {
			log.info("Selecting dropdown value - "+dropdownvalue);

			WebElement web=webPage.getDriver().findElement(By.xpath(locator));
			Select select=new Select(web);
			select.selectByVisibleText(dropdownvalue);
			Thread.sleep(3000);
		} catch (Throwable e) {
			softAssert.fail("Unable to click on element using XPath : "+ locator+". Localized Message: "+e.getLocalizedMessage());
		}
	}
	
	public void reducingProductquantity(WebPage webPage, String[][] checkoutFlowCommonLocators, SoftAssert softAssert){
		try{
			log.info("Verifying if product quantity is greater than or equal to 3");
			String iteQuatityString = commonMethods.getAttributebyXpath(webPage, checkoutFlowCommonLocators[18][1], "value", softAssert);
			int iteQuantityInt = Integer.parseInt(iteQuatityString);
			if(iteQuantityInt>=3){
				log.info("Product quantity is greater than or equal to 3");
				commonMethods.clearTextBox(webPage, checkoutFlowCommonLocators[18][1], softAssert);
				commonMethods.sendKeysbyXpath(webPage, checkoutFlowCommonLocators[18][1], commonData[9][1], softAssert);
				commonMethods.clickElementbyXpath(webPage, checkoutFlowCommonLocators[19][1], softAssert);
				log.info("Reduced product quantity to "+commonData[9][1]);
			}else{
				log.info("Product quantity is not greater than or equal to 3");
			}
		}catch(Exception e){
			log.error("Not able to reduce product quantity");
		}
	}
}
