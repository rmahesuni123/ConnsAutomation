package com.etouch.connsPages;

import java.awt.AWTException;
import java.awt.Robot;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.etouch.common.CommonMethods;
import com.etouch.common.TafExecutor;
import com.etouch.taf.core.datamanager.excel.annotations.IExcelDataFiles;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.webui.selenium.WebPage;

@IExcelDataFiles(excelDataFiles = { "CreditAppData=testData" })
public class ConnsProductPurchasePage {

	static String platform;
	static Log log = LogUtil.getLog(ConnsProductPurchasePage.class);
	static String AbsolutePath = TafExecutor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	static String videoLocation = AbsolutePath.substring(0, AbsolutePath.indexOf("/target/classes/")).substring(1)
			.concat("/src/test/resources/testdata/videos");
	private String url = null;
	private WebPage webPage;
	private ConnsMainPage mainPage;
	CommonMethods commonMethods = new CommonMethods();

	Path path;
	String DataFilePath;

	public void Click_On_Refrigerators(WebPage webPage, String[][] test, SoftAssert softAssert) {

		try {
			webPage.getDriver().manage().deleteAllCookies();
			System.out.println("test[0][1]):" + test[0][1]);
			webPage.hoverOnElement(By.xpath(test[0][1]));
			webPage.findObjectByxPath(test[1][1]).click();

		} catch (PageException | AWTException e) {
			log.error(e.getMessage());

		}

	}

	public void Add_To_Cart(WebPage webPage, String[][] addToCart, SoftAssert softAssert) {

		try {
			Robot robot = new Robot();
			// Point point = driver.findElement(by).getLocation();
			robot.mouseMove(0, 16);
			// webPage.findObjectByxPath(test[0][1]).click();
			commonMethods.clickElementbyXpath(webPage, addToCart[0][1], softAssert);
		} catch (Throwable e) {
			log.error(e.getMessage());

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

		}

	}

	public void Checkout_Guest(WebPage webPage, String[][] checkoutGuest, SoftAssert softAssert) {

		try {
			Robot robot = new Robot();
			// Point point = driver.findElement(by).getLocation();
			robot.mouseMove(0, 16);
			// webPage.findObjectByxPath(test[0][1]).click();
			commonMethods.clickElementbyXpath(webPage, checkoutGuest[0][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, checkoutGuest[1][1], softAssert);
			Thread.sleep(5000);

		} catch (Throwable e) {
			log.error(e.getMessage());

		}

	}

	public boolean isAlertPresent() {
		try {
			webPage.getDriver().switchTo().alert();

			return true;
		} // try
		catch (NoAlertPresentException Ex) {
			return false;
		} // catch
	} // isAlertPresent()

	public void Checkout_Register(WebPage webPage, String[][] checkoutRegister, SoftAssert softAssert) {

		try {
			Robot robot = new Robot();
			// Point point = driver.findElement(by).getLocation();
			robot.mouseMove(0, 16);
			// webPage.findObjectByxPath(test[0][1]).click();
			commonMethods.clickElementbyXpath(webPage, checkoutRegister[0][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, checkoutRegister[1][1], softAssert);

		} catch (Throwable e) {
			log.error(e.getMessage());

		}

	}

	public void Submit_Billing_Information(WebPage webPage, String[][] test, SoftAssert softAssert) {

		try {
			commonMethods.sendKeysbyXpath(webPage, test[0][1], test[0][3], softAssert);
			commonMethods.sendKeysbyXpath(webPage, test[1][1], test[1][3], softAssert);
			commonMethods.sendKeysbyXpath(webPage, test[2][1], test[2][3], softAssert);
			commonMethods.sendKeysbyXpath(webPage, test[3][1], test[3][3], softAssert);
			commonMethods.sendKeysbyXpath(webPage, test[4][1], test[4][3], softAssert);
			commonMethods.sendKeysbyXpath(webPage, test[5][1], test[5][3], softAssert);
			commonMethods.sendKeysbyXpath(webPage, test[6][1], test[6][3], softAssert);
			commonMethods.selectDropdownByValue(webPage, test[7][1], test[7][3], softAssert);
			commonMethods.selectDropdownByValue(webPage, test[8][1], test[8][3], softAssert);

		} catch (Throwable e) {

			mainPage.getScreenShotForFailure(webPage, "Submit_Billing_Information");
			log.error("Submit_Billing_Information failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());

		}

	}

	public void PickUp_Location_Continue_Button(WebPage webPage, String[][] submitBillingInfo, SoftAssert softAssert) {

		try {
			commonMethods.clickElementbyXpath(webPage, submitBillingInfo[10][1], softAssert);
			Thread.sleep(5000);

		} catch (Throwable e) {
			log.error(e.getMessage());

		}

	}

	public void Submit_Shipping_Info(WebPage webPage, String[][] submitShippingInfo, SoftAssert softAssert) {

		try {
			commonMethods.getWebElementbyXpath(webPage, submitShippingInfo[0][1], softAssert).clear();
			commonMethods.sendKeysbyXpath(webPage, submitShippingInfo[0][1], submitShippingInfo[0][3], softAssert);

			commonMethods.getWebElementbyXpath(webPage, submitShippingInfo[1][1], softAssert).clear();
			commonMethods.sendKeysbyXpath(webPage, submitShippingInfo[1][1], submitShippingInfo[1][3], softAssert);

			commonMethods.getWebElementbyXpath(webPage, submitShippingInfo[2][1], softAssert).clear();
			commonMethods.sendKeysbyXpath(webPage, submitShippingInfo[2][1], submitShippingInfo[2][3], softAssert);

			commonMethods.getWebElementbyXpath(webPage, submitShippingInfo[3][1], softAssert).clear();
			commonMethods.sendKeysbyXpath(webPage, submitShippingInfo[3][1], submitShippingInfo[3][3], softAssert);

			commonMethods.getWebElementbyXpath(webPage, submitShippingInfo[4][1], softAssert).clear();
			commonMethods.sendKeysbyXpath(webPage, submitShippingInfo[4][1], submitShippingInfo[4][3], softAssert);

			commonMethods.getWebElementbyXpath(webPage, submitShippingInfo[5][1], softAssert).clear();
			commonMethods.sendKeysbyXpath(webPage, submitShippingInfo[5][1], submitShippingInfo[5][3], softAssert);

			commonMethods.getWebElementbyXpath(webPage, submitShippingInfo[6][1], softAssert).clear();
			commonMethods.sendKeysbyXpath(webPage, submitShippingInfo[6][1], submitShippingInfo[6][3], softAssert);

			commonMethods.selectDropdownByValue(webPage, submitShippingInfo[7][1], submitShippingInfo[7][3],
					softAssert);
			commonMethods.selectDropdownByValue(webPage, submitShippingInfo[8][1], submitShippingInfo[8][3],
					softAssert);

		} catch (Throwable e) {

			mainPage.getScreenShotForFailure(webPage, "Click_On_In_Stock_Add_To_Cart_Button");
			log.error("Click_On_In_Stock_Add_To_Cart_Button failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());

		}

	}

	public void Proceed_To_Checkout_Button(WebPage webPage, String[][] test, SoftAssert softAssert) {

		try {
			commonMethods.clickElementbyXpath(webPage, test[0][1], softAssert);
			Thread.sleep(5000);
		} catch (Throwable e) {
			log.error(e.getMessage());

		}

	}

	public String Submit_Paypal_Payment_Info(WebPage webPage, String[][] test, SoftAssert softAssert) {

		try {
			commonMethods.clickElementbyXpath(webPage, test[0][1], softAssert);
			commonMethods.clickElementbyXpath(webPage, test[1][1], softAssert);
			Thread.sleep(5000);

			if (commonMethods.getPageUrl(webPage, softAssert).contains("expected paypal url" + test[2][4])) {

				commonMethods.sendKeysbyXpath(webPage, test[2][1], test[2][3], softAssert);
				commonMethods.sendKeysbyXpath(webPage, test[3][1], test[3][3], softAssert);
				commonMethods.clickElementbyXpath(webPage, test[4][1], softAssert);
				Thread.sleep(5000);
				commonMethods.clickElementbyXpath(webPage, test[5][1], softAssert);
				Thread.sleep(5000);

			} else {

				log.info("paypal.com url is not loaded");
			}

		} catch (Throwable e) {
			log.error(e.getMessage());

		}
		return DataFilePath;

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
					commonMethods.clickElementbyXpath(webPage, "(//button[@title='Add to Cart'])[" + counter + "]",softAssert);
					
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

	public void Enter_Zip_Code_Click_On_Add_To_Cart(WebPage webPage, String[][] test, SoftAssert softAssert) {

		/*
		 * String[][] test = ExcelUtil.readExcelData(DataFilePath,
		 * "ProductPurchase", "Enter_Zip_Code_Click_On_Add_To_Cart");
		 */
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

	public void Click_On_In_Stock_Product_Add_To_Cart_Button(WebPage webPage, String[][] test, SoftAssert softAssert) {
		String stockAvilabilityText = null;

		try {

			List<WebElement> listOfProducts = commonMethods.getWebElementsbyXpath(webPage, test[0][1], softAssert);
			for (WebElement product : listOfProducts) {
				stockAvilabilityText = product.findElement(By.xpath(test[2][1])).getText().trim();
				if (stockAvilabilityText.equalsIgnoreCase(test[0][3])) {

					product.findElement(By.xpath(test[3][1])).click();
					System.out.println("clicked pickup only on add to cart button");
					break;
				} else if (commonMethods.verifyElementisPresent(webPage, test[1][1], softAssert)) {

					webPage.waitOnElement(By.xpath(test[1][1]), 5);
					commonMethods.clickElementbyXpath(webPage, test[1][1], softAssert);

					stockAvilabilityText = product.findElement(By.xpath(test[2][1])).getText();
					if (stockAvilabilityText.equalsIgnoreCase(test[0][3])) {
						product.findElement(By.xpath(test[3][1])).click();
						System.out.println("clicked pickup only on add to cart button in 2nd page");
						break;

					}
				}
			}
		} catch (Throwable e) {
			log.error(e.getMessage());
			mainPage.getScreenShotForFailure(webPage, "Click_On_In_Stock_Product_Add_To_Cart_Button");
			log.error("Click_On_In_Stock_Product_Add_To_Cart_Button failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());

		}

	}

	/*
	 * 
	 * Common Method to click on ADD TO CART Button for pick only Availability
	 * option in all available pages
	 */

	public void Click_On_In_Stock_Pickup_Only_Product(WebPage webPage, String[][] test, SoftAssert softAssert) {

		String errorMessage = null;

		try {

			List<WebElement> listOfProducts = commonMethods.getWebElementsbyXpath(webPage,
					"(//*[contains(@class,'category-products')]//ul//li[contains(@class,'item')])", softAssert);

			WebElement product;

			for (int i = 1; i <= listOfProducts.size(); i++) {

				product = webPage.getDriver().findElement(By.xpath(
						"(//*[contains(@class,'category-products')]//ul//li[contains(@class,'item')])[" + i + "]"));
				
				System.out.println("Web Element Details:" + product.getText() + i);

				
				System.out.println("test[0][3]:::" + test[0][3]);


				if (product.getText().equalsIgnoreCase(test[0][3])) {

					System.out.println("clicking on element:::"+ i );
					
					commonMethods.clickElementbyXpath(webPage, "(//div[@class='rwd-category-list']/h2/a)[" + i + "]",softAssert);
					
					commonMethods.clickElementbyXpath(webPage, "//*[@id='add-to-cart-submit-button']", softAssert);

					commonMethods.verifyElementisPresent(webPage, test[4][1], softAssert);
					Thread.sleep(3000);

					webPage.findObjectByxPath(test[5][1]).clear();

					commonMethods.sendKeysbyXpath(webPage, test[5][1], test[5][3], softAssert);

					Thread.sleep(5000);

					commonMethods.clickElementbyXpath(webPage, test[6][1], softAssert);
					Thread.sleep(10000);

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

			mainPage.getScreenShotForFailure(webPage, "Click_On_In_Stock_Add_To_Cart_Button");
			log.error("Click_On_In_Stock_Add_To_Cart_Button failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());

		}

	}

	public void Click_On_In_Stock_Add_To_Cart_Button(WebPage webPage, String[][] test, SoftAssert softAssert) {
		String stockAvilabilityText = null;

		/*
		 * String[][] test = ExcelUtil.readExcelData(DataFilePath,
		 * "ProductPurchase", "Click_On_In_Stock_Add_To_Cart_Button");
		 */
		try {

			List<WebElement> listOfProducts = commonMethods.getWebElementsbyXpath(webPage, test[0][1], softAssert);
			for (WebElement product : listOfProducts) {

				stockAvilabilityText = product.findElement(By.xpath(test[2][1])).getText();
				if (stockAvilabilityText.equalsIgnoreCase(test[0][3])) {
					product.findElement(By.xpath(test[3][1])).click();
					System.out.println("clicked In_Stock only on add to cart button");
					break;

				} else if (commonMethods.verifyElementisPresent(webPage, test[1][1], softAssert)) {

					webPage.waitOnElement(By.xpath(test[1][1]), 5);
					commonMethods.clickElementbyXpath(webPage, test[1][1], softAssert);

					stockAvilabilityText = product.findElement(By.xpath(test[2][1])).getText();
					if (stockAvilabilityText.equalsIgnoreCase(test[0][3])) {
						product.findElement(By.xpath(test[3][1])).click();
						System.out.println("clicked In_Stock only on add to cart button in 2nd page");
						break;

					}
				}
			}
		} catch (Throwable e) {
			log.error(e.getMessage());
			mainPage.getScreenShotForFailure(webPage, "Click_On_In_Stock_Add_To_Cart_Button");
			log.error("Click_On_In_Stock_Add_To_Cart_Button failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());

		}

	}

	/*
	 * 
	 * common method to find whether searched product having Availability as
	 * In_Stock & Add To Cart Button
	 */

	/*public void Click_Add_To_Cart_As_Per_Avilability_Message(WebPage webPage, String[][] testData,
			SoftAssert softAssert) {

		WebElement product;
		String stockAvilabilityText;
		int counter = 1;
		try {

			List<WebElement> listOfProducts = commonMethods.getWebElementsbyXpath(webPage, testData[0][1], softAssert);

			System.out.println("list Of Products:" + listOfProducts.size());

			for (int i = 1; i <= listOfProducts.size(); i++) {

				listOfProducts = commonMethods.getWebElementsbyXpath(webPage,
						"(//*[contains(@class,'category-products')]//ul//li[contains(@class,'item')])", softAssert);

				product = webPage.getDriver().findElement(By.xpath(
						"(//*[contains(@class,'category-products')]//ul//li[contains(@class,'item')])[" + i + "]"));

				System.out.println("Web Element Details" + product.getText() + i);

				stockAvilabilityText = product.findElement(By.xpath(testData[2][1])).getText();

				System.out.println("stockAvilabilityText:::" + stockAvilabilityText);
				
				 * verifying whether availability text is pickup only or not
				 
				System.out.println("test[0][3]:::" + testData[0][3]);

				if (stockAvilabilityText.equalsIgnoreCase(testData[0][3])) {

					commonMethods.clickElementbyXpath(webPage, "(//button[@title='Add to Cart'])[" + counter + "]",
							softAssert);

					counter++;

					commonMethods.verifyElementisPresent(webPage, testData[4][1], softAssert);
					Thread.sleep(3000);

					webPage.findObjectByxPath(testData[5][1]).clear();

					webPage.waitOnElement(By.xpath(testData[5][1]), 10);
					commonMethods.sendKeysbyXpath(webPage, testData[5][1], testData[5][3], softAssert);

					Thread.sleep(5000);
					webPage.waitOnElement(By.xpath(testData[6][1]), 10);
					commonMethods.clickElementbyXpath(webPage, testData[6][1], softAssert);
					Thread.sleep(10000);

					boolean isErrorMessageDisplayed = webPage.getDriver().findElements(By.xpath(testData[8][1])).size() > 0;

					softAssert.assertTrue(isErrorMessageDisplayed,
							"Error Message is not displayed on entering invalid zip code");
				}
			}
		} catch (Throwable e) {
			log.error(e.getMessage());
			mainPage.getScreenShotForFailure(webPage, "Click_Add_To_Cart_As_Per_Avilability_Message");
			log.error("Click_Add_To_Cart_As_Per_Avilability_Message failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());

		}

	}*/

	public void Product_With_In_Stock_Add_To_Cart_Button_With_Avilability(WebPage webPage, String[][] testData,
			SoftAssert softAssert) {

		try {
			int i = 1;
			List<WebElement> listOfProducts = commonMethods.getWebElementsbyXpath(webPage, testData[0][1], softAssert);

			for (WebElement product : listOfProducts) {

				String productText = product.getText();

				if (productText.contains(testData[0][4])) {
					System.out.println("productText:" + productText);
					System.out.println("--------------------------------------------");

					product.findElement(By.xpath("//button[@title='Add to Cart']")).click();
					System.out.println("i:" + i);

					break;
				}

				i++;

			}
		} catch (Throwable e) {
			log.error(e.getMessage());
			mainPage.getScreenShotForFailure(webPage, "Product_With_In_Stock_Add_To_Cart_Button");
			log.error("Product_With_In_Stock_Add_To_Cart_Button failed");
			log.error(e.getMessage());
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());

		}

	}

	/*
	 * 
	 * working - in_stock pickup only method
	 */

	public void Add_In_Stock_Pickup_Only_Product_To_Cart(WebPage webPage, String[][] test, SoftAssert softAssert) {

		String errorMessage = null;
		try {

			List<WebElement> listOfProducts = commonMethods.getWebElementsbyXpath(webPage,
					"(//*[contains(@class,'category-products')]//ul//li[contains(@class,'item')])", softAssert);

			WebElement product;
			int counter = 1;
			for (int i = 1; i <= listOfProducts.size(); i++) {

				product = webPage.getDriver().findElement(By.xpath(
						"(//*[contains(@class,'category-products')]//ul//li[contains(@class,'item')])[" + i + "]"));
				System.out.println("Web Element Details:::" + product.getText() + i);

				/*
				 * verifying whether availability text is pickup only or not
				 */
				boolean isInStockAvailabilityDisplayed = product.getText().contains(test[0][3]);
				System.out.println("isInStockAvailabilityDisplayed:" + isInStockAvailabilityDisplayed);
				System.out.println("test[0][3]:::" + test[0][3]);

				System.out.println("i::::::::::::::::::::::::::" + i
						+ "-------------------------------------------------------------------------");
				if (isInStockAvailabilityDisplayed) {

					commonMethods.clickElementbyXpath(webPage, "(//h2[@class='product-name']/a)[" + i + "]",
							softAssert);

					boolean isPickupCheckboxDisplayed = webPage.getDriver()
							.findElements(By.xpath("//input[@id='instore']")).size() >= 1;

					System.out.println("checked isPickupCheckboxDisplayed:" + isPickupCheckboxDisplayed);

					if (isPickupCheckboxDisplayed) {

						commonMethods.clickElementbyXpath(webPage, "//input[@id='instore']", softAssert);
						commonMethods.clickElementbyXpath(webPage, "//*[@id='add-to-cart-submit-button']", softAssert);

						// counter++;

						webPage.waitOnElement(By.xpath(test[5][1]), 10);

						JavascriptExecutor executor = (JavascriptExecutor) webPage.getDriver();

						executor.executeScript("document.getElementById('warehouse-zip-code').value='';");

						executor.executeScript("document.getElementById('warehouse-zip-code').value='77701';");

						System.out.println("test[5][3]:" + test[5][3]);

						webPage.waitOnElement(By.xpath(test[6][1]), 10);
						commonMethods.clickElementbyXpath(webPage, test[6][1], softAssert);
						Thread.sleep(3000);

						boolean isPresent = webPage.getDriver().findElements(By.xpath(test[8][1])).size() > 0;
						System.out.println("isPresent:" + isPresent);
						System.out.println("webPage.getDriver().findElements(By.xpath(test[8][1])).size():"
								+ webPage.getDriver().findElements(By.xpath(test[8][1])).size());

						if (!isPresent) {

							System.out.println("before clicking add to cart on modal box");
							commonMethods.clickElementbyXpath(webPage, test[7][1], softAssert);
							System.out.println("after clicking add to cart on modal box");

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
								webPage.getDriver().navigate().refresh();

							}
						}
						/*
						 * if(isAlertPresent()){
						 * 
						 * 
						 * Alert alert = webPage.getDriver().switchTo().alert();
						 * String alertBoxErrorText=alert.getText();
						 * System.out.println("alertBoxErrorText:"+
						 * alertBoxErrorText); alert.accept();
						 * 
						 * }
						 */

					} else {
						System.out.println("in else block driver navigate back");
						counter++;
						webPage.getDriver().navigate().back();
						webPage.getDriver().navigate().refresh();
						// webPage.getDriver().navigate().to("http://connsecommdev-1365538477.us-east-1.elb.amazonaws.com/conns_rwd/appliances/refrigerators/french-door");
						Thread.sleep(10000);

					}

				} else {

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
	 * 
	 * working method
	 */

	public void iS_Over_Lay_Box_Displayed_In_Stock_Pickup_Product(WebPage webPage, String[][] test,
			SoftAssert softAssert) {

		String errorMessage = null;
		try {

			List<WebElement> listOfProducts = commonMethods.getWebElementsbyXpath(webPage,
					"(//*[contains(@class,'category-products')]//ul//li[contains(@class,'item')])", softAssert);

			WebElement product;
			int counter = 1;
			for (int i = 1; i <= listOfProducts.size(); i++) {

				product = webPage.getDriver().findElement(By.xpath(
						"(//*[contains(@class,'category-products')]//ul//li[contains(@class,'item')])[" + i + "]"));
				System.out.println("Web Element Details:::" + product.getText() + i);

				/*
				 * verifying whether availability text is pickup only or not
				 */
				boolean isInStockAvailabilityDisplayed = product.getText().contains(test[0][3]);
				System.out.println("isInStockAvailabilityDisplayed:" + isInStockAvailabilityDisplayed);
				System.out.println("test[0][3]:::" + test[0][3]);

				
				if (isInStockAvailabilityDisplayed) {

					commonMethods.clickElementbyXpath(webPage, "(//h2[@class='product-name']/a)[" + i + "]",
							softAssert);

					boolean isPickupCheckboxDisplayed = webPage.getDriver()
							.findElements(By.xpath("//input[@id='instore']")).size() >= 1;

					System.out.println("isPickupCheckboxDisplayed" + isPickupCheckboxDisplayed);

					if (isPickupCheckboxDisplayed) {

						commonMethods.clickElementbyXpath(webPage, "//input[@id='instore']", softAssert);
						commonMethods.clickElementbyXpath(webPage, "//*[@id='add-to-cart-submit-button']", softAssert);

						break;

					} else {
						System.out.println("driver navigating back to search results page");
						counter++;
						webPage.getDriver().navigate().back();
						webPage.getDriver().navigate().refresh();
						Thread.sleep(10000);

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

	public WebPage Click_On_In_Stock_With_Delivery_Available(WebPage webPage, String[][] inStockOnlyAddToCart,
			SoftAssert softAssert) {
		
		String errorMessage = null;

		try {
			

			List<WebElement> listOfProducts = commonMethods.getWebElementsbyXpath(webPage,
					"(//*[contains(@class,'category-products')]//ul//li[contains(@class,'item')])", softAssert);

			WebElement product;
			int counter = 1;
			for (int i = 1; i <= listOfProducts.size(); i++) {

				product = webPage.getDriver().findElement(By.xpath(
						"(//*[contains(@class,'category-products')]//ul//li[contains(@class,'item')])[" + i + "]"));
				System.out.println("Web Element Details" + product.getText() + i);

				//stockAvilabilityText = product.findElement(By.xpath(inStockOnlyAddToCart[2][1])).getText();

				//System.out.println("stockAvilabilityText:::" + stockAvilabilityText);
				/*
				 * verifying whether availability text is pickup only or not
				 */
				if (product.getText().contains(inStockOnlyAddToCart[0][3])) {

					System.out.println("clicking on element:::"+ i );
					
					commonMethods.clickElementbyXpath(webPage, "(//div[@class='rwd-category-list']/h2/a)[" + i + "]",softAssert);
					
					commonMethods.clickElementbyXpath(webPage, "//*[@id='add-to-cart-submit-button']", softAssert);
					

					boolean isOverlayDisplayed=commonMethods.verifyElementisPresent(webPage, inStockOnlyAddToCart[4][1], softAssert);
					//Thread.sleep(3000);
					System.out.println("isOverlayDisplayed:"+isOverlayDisplayed);
					webPage.findObjectByxPath(inStockOnlyAddToCart[5][1]).clear();
					
					boolean isZipCodeTextBoxDisplayed=commonMethods.verifyElementisPresent(webPage, inStockOnlyAddToCart[5][1], softAssert);
					
					System.out.println("isZipCodeTextBoxDisplayed:"+isZipCodeTextBoxDisplayed);
					
					webPage.waitOnElement(By.xpath(inStockOnlyAddToCart[5][1]), 10);
					commonMethods.sendKeysbyXpath(webPage, inStockOnlyAddToCart[5][1], inStockOnlyAddToCart[5][3],softAssert);

					Thread.sleep(5000);
					webPage.waitOnElement(By.xpath(inStockOnlyAddToCart[6][1]), 10);
					commonMethods.clickElementbyXpath(webPage, inStockOnlyAddToCart[6][1], softAssert);
					Thread.sleep(10000);

					boolean isPresent = webPage.getDriver().findElements(By.xpath(inStockOnlyAddToCart[8][1])).size() > 0;
					
					if (!isPresent) {

						System.out.println("before clicking add to cart on modal box");
						commonMethods.clickElementbyXpath(webPage, inStockOnlyAddToCart[7][1], softAssert);
						Thread.sleep(2000);
						System.out.println("after clicking add to cart on modal box");

						System.out.println("clicked pickup only on add to cart button");
						break;

					} else {
						errorMessage = commonMethods.getTextbyXpath(webPage, inStockOnlyAddToCart[8][1], softAssert);
						System.out.println("errorMessage:::" + errorMessage);
						System.out.println("test[8][4]:::" + inStockOnlyAddToCart[8][4]);

						if (errorMessage.contains(inStockOnlyAddToCart[8][4])) {
							System.out.println("captures error message:::" + errorMessage);
							webPage.getDriver().findElement(By.xpath("//*[@id='fancybox-close']")).click();
							Thread.sleep(3000);
							webPage.getDriver().navigate().back();
							webPage.getDriver().navigate().refresh();

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
		return webPage;

	}

	
	/*
	 * Verify Zip Code Functionality for In-Stock Product , Verify ADD TO CART on overlay without entering input in Zip code
	 */
	
	public void Click_Add_To_Cart_As_Per_Avilability_Message(WebPage webPage, String[][] test, SoftAssert softAssert) {

	
	//JavascriptExecutor executor = (JavascriptExecutor) webPage.getDriver();
	try {

		List<WebElement> listOfProducts = commonMethods.getWebElementsbyXpath(webPage,
				"(//*[contains(@class,'category-products')]//ul//li[contains(@class,'item')])", softAssert);

		WebElement product;
		
		for (int i = 1; i <= listOfProducts.size(); i++) {

			product = webPage.getDriver().findElement(By.xpath(
					"(//*[contains(@class,'category-products')]//ul//li[contains(@class,'item')])[" + i + "]"));
			
			System.out.println("Web Element Details:" + product.getText() + i);

			
			System.out.println("test[0][3]:::" + test[0][3]);
			/*
			 * verifying whether availability text is pickup only or not
			 */
			if (product.getText().contains(test[0][3])) {

				System.out.println("clicking on element:::"+ i );
				
				commonMethods.clickElementbyXpath(webPage, "(//div[@class='rwd-category-list']/h2/a)[" + i + "]",softAssert);
				
				commonMethods.clickElementbyXpath(webPage, "//*[@id='add-to-cart-submit-button']", softAssert);
				
				boolean isOverlayDisplayed=commonMethods.verifyElementisPresent(webPage, test[4][1], softAssert);
				
				System.out.println("isOverlayDisplayed:"+isOverlayDisplayed);
				
				Thread.sleep(3000);
				
				softAssert.assertTrue(isOverlayDisplayed, "Overlay box is not displayed");
				
				/*webPage.findObjectByxPath(test[5][1]).clear();*/
				
				boolean isZipCodeTextBoxDisplayed=commonMethods.verifyElementisPresent(webPage, test[5][1], softAssert);
				
				System.out.println("isZipCodeTextBoxDisplayed:"+isZipCodeTextBoxDisplayed);
				
				softAssert.assertTrue(isZipCodeTextBoxDisplayed, "zip code text box is not displayed");
				
				commonMethods.getWebElementbyXpath(webPage, test[5][1], softAssert).clear();
				
				commonMethods.sendKeysbyXpath(webPage, test[5][1], test[5][3], softAssert);
				
				
				
				/*executor.executeScript("document.getElementById('zip-code').value='';");

				executor.executeScript("document.getElementById('zip-code').value='77701';");*/
				
				Thread.sleep(1000);
				
				webPage.waitOnElement(By.xpath(test[6][1]), 10);
				
				commonMethods.clickElementbyXpath(webPage, test[6][1], softAssert);
				
				Thread.sleep(10000);

				//boolean isPresent = webPage.getDriver().findElements(By.xpath(test[8][1])).size() > 0;
				
				System.out.println("error message count:"+webPage.getDriver().findElements(By.xpath(test[8][1])).size());
				
				boolean isErrorMessageDispalyed=webPage.getDriver().findElements(By.xpath(test[8][1])).size() > 0;
				
				System.out.println("isErrorMessageDispalyed:"+isErrorMessageDispalyed);
				
				softAssert.assertTrue(isErrorMessageDispalyed, "error message is not displayed for empty zip code search");
				
				break;
				
				

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
}
