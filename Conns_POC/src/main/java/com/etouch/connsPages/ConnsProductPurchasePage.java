package com.etouch.connsPages;

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
	private WebPage webPage;
	CommonMethods commonMethods = new CommonMethods();
	Path path;

	public void Click_On_French_Door_Link(WebPage webPage, String test, SoftAssert softAssert) throws InterruptedException {
		try {
			try{
				webPage.getDriver().manage().deleteAllCookies();
				log.info("browser history cleared");
			}catch(Exception e){
				log.info("Unable to delete cookies for current browser.");
			}
			WebElement element = webPage.findObjectByxPath(test).getWebElement();
			JavascriptExecutor executor = (JavascriptExecutor) webPage.getDriver();
			executor.executeScript("arguments[0].click();", element);
			commonMethods.waitForPageLoad(webPage, softAssert);
			Thread.sleep(3000);
			log.info("clicked on :" + test);
		} catch (PageException e) {
			log.error(e.getMessage());
			softAssert.fail(e.getLocalizedMessage());
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

	public void Checkout_Guest(WebPage webPage, String[][] checkoutGuest, SoftAssert softAssert) {
		try {
			log.info("Checking out as guest user");
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
		}
		//Changes by deepak
		catch (Exception e) {
			return false;
		}
	}


	public void Submit_Billing_Information(WebPage webPage, String[][] billingInfoDetails, SoftAssert softAssert) {
		try {
			checkoutSendKeysbyXpath(webPage, billingInfoDetails[0][1], billingInfoDetails[0][3], softAssert);
			checkoutSendKeysbyXpath(webPage, billingInfoDetails[1][1], billingInfoDetails[1][3], softAssert);
			checkoutSendKeysbyXpath(webPage, billingInfoDetails[2][1], billingInfoDetails[2][3], softAssert);
			checkoutSendKeysbyXpath(webPage, billingInfoDetails[3][1], billingInfoDetails[3][3], softAssert);
			checkoutSendKeysbyXpath(webPage, billingInfoDetails[4][1], billingInfoDetails[4][3], softAssert);
			checkoutSendKeysbyXpath(webPage, billingInfoDetails[5][1], billingInfoDetails[5][3], softAssert);
			
			commonMethods.selectDropdownByValue(webPage, billingInfoDetails[7][1], billingInfoDetails[7][3], softAssert);
			checkoutSendKeysbyXpath(webPage, billingInfoDetails[6][1], billingInfoDetails[6][3], softAssert);
			//dummy click
			commonMethods.Click_On_Element_JS(webPage, billingInfoDetails[5][1], softAssert);
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
			if(browserName.equalsIgnoreCase("Safari")||browserName.contains("iPad")||browserName.contains("iPhone")){
				Thread.sleep(5000);
			}
			log.info("Clicking on Proceed to checkout");
			commonMethods.clickElementbyXpath(webPage, test[0][1], softAssert);
			Thread.sleep(5000);
		} catch (Throwable e) {
			log.error("Proceed to checkout failed");
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void clickOnMobileMenuOption(WebPage webPage, String[][] mobileMenuData, SoftAssert softAssert) {
		log.info("Clicking on mobile menu for applicances ");
		try {
			try{
				webPage.getDriver().manage().deleteAllCookies();	
			}catch(Exception e){
				log.info("Unable to delete cookies for the browser");
			}
			Thread.sleep(5000);
			commonMethods.clickElementbyXpath(webPage, mobileMenuData[0][2], softAssert);
			commonMethods.clickElementbyXpath(webPage, mobileMenuData[1][2], softAssert);
			commonMethods.clickElementbyXpath(webPage, mobileMenuData[2][2], softAssert);
			commonMethods.clickElementbyXpath(webPage, mobileMenuData[3][2], softAssert);
			log.info("Loading french door page on mobile");
			Thread.sleep(5000);
		} catch (Exception e) {
			softAssert.fail("Failed to click on French Door menu in mobile.");
		}
	}

	public void checkoutSendKeysbyXpath(WebPage webPage, String locator, String text, SoftAssert softAssert){
		try{
			log.info("Entering keys "+text+" ");
			webPage.findObjectByxPath(locator).clear();
			webPage.findObjectByxPath(locator).sendKeys(text);
		}catch(Exception e){
			softAssert.fail("Unable to Enter Keys : "+text+" using locator : "+locator+". Localized Message: "+e.getLocalizedMessage());
			Assert.fail();
		}
	}

	public String checkoutgetTextbyXpath(WebPage webPage, String locator, SoftAssert softAssert) throws PageException{
		String actualText= "";
		try {
			log.info("Getting text by using xpath - "+locator);
			actualText = webPage.findObjectByxPath(locator).getText();
			log.info("Actual text - "+actualText);
		} catch (PageException e) {
			throw new PageException("Failed to find object using given xpath, message : " + e.toString());
			//softAssert.fail("Unable to Get Text on element using Xpath : "+ locator+". Localized Message: "+e.getLocalizedMessage());
		}
		return actualText;
	}

	public void numberOfProductDisplaySelectDropdownByValue(WebPage webPage, String locator,String dropdownvalue ,SoftAssert softAssert) {
		try {
			log.info("Selecting dropdown value - "+dropdownvalue);
			if(!(browserName.equalsIgnoreCase("Safari")||browserName.equalsIgnoreCase("edge"))){	
				Thread.sleep(3000);
				CommonMethods.waitForWebElement(By.xpath(locator), webPage);
				WebElement web=webPage.getDriver().findElement(By.xpath(locator));
				Select select=new Select(web);
				select.selectByVisibleText(dropdownvalue);
			}else{
				commonMethods.clickElementbyXpath_usingJavaScript(webPage, commonData[10][1], softAssert);
			}
			Thread.sleep(3000);
		} catch (Throwable e) {
			softAssert.fail("Unable to change number of products displayed: "+ locator+". Localized Message: "+e.getLocalizedMessage());
		}
	}

	public void removeProductsFromCart(WebPage webPage, SoftAssert softAssert) throws InterruptedException{
		List<WebElement> removeIteList = webPage.getDriver().findElements(By.xpath(commonData[9][1]));
		if(removeIteList.size()>1){
			log.info("Removing products from cart");
			for(WebElement element:removeIteList){
				element.click();
				Thread.sleep(1000);
			}
		}else{
			log.info("More than 1 item not present in cart");
		}
	}

	/*
	 * -This method will open over-laybox for in-stock or pickup only product depending on data passed to the method.
	 */
	public String Open_OverlayBox_ForDataGiven(WebPage webPage, String[][] openOverlayData,SoftAssert softAssert) {
		String productText = null;
		try {
			log.info("Opening OverlayBox for: "+openOverlayData[1][2]);
			List<WebElement> listOfProducts = commonMethods.getWebElementsbyXpath(webPage,commonData[1][1], softAssert);
			WebElement product;
			for (int i = 1; i <= listOfProducts.size(); i++) {
				log.info("Searching product "+i+" for text "+openOverlayData[1][2]);
				product = webPage.getDriver().findElement(By.xpath(commonData[0][1] + i + "]"));
				if ((product.getText().toUpperCase().contains(openOverlayData[0][2]))&& (product.getText().toUpperCase().contains(openOverlayData[1][2]))) {
					log.info("Found product number "+i+" with text "+openOverlayData[1][2]);
					productText = product.getText();
					log.info("clicking on product:::" + i);
					commonMethods.clickElementbyXpath(webPage, commonData[5][1] + i + "]",softAssert);
					boolean isOverlayDisplayed = commonMethods.verifyElementisPresent(webPage,commonData[4][1], softAssert);
					if(!isOverlayDisplayed){
						try{
							commonMethods.clickElementbyXpath(webPage, commonData[3][1], softAssert);
						}catch(Exception e){
							log.info("Clicking Add to Cart button not required as Overlay box is already open");
						}
					}
					break;
				}
			}
		} catch (Throwable e) {
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
		return productText;
	}

	public void openOverlayBox(WebPage webPage,SoftAssert softAssert)
	{
		boolean isOverlayDisplayed = commonMethods.verifyElementisPresent(webPage,commonData[4][1], softAssert);
		if(!isOverlayDisplayed){
			try{
				log.info("Clicking on Add To Cart button on product page");
				commonMethods.Click_On_Element_JS(webPage, commonData[3][1], softAssert);
				//commonMethods.clickElementbyXpath(webPage, commonData[3][1], softAssert);
			}catch(Exception e){
				log.info("Clicking Add to Cart button not required as Overlay box is already open");
			}
		}

	}

	/*
	 * -This method will verify elements present on overlay box using assert.
	 */

	public void verifyOverlayBoxText(WebPage webPage, String[][] overlayData, SoftAssert softAssert){
		log.info("Verifying text present on Overlay box");
		try{
			List<String> actualTextValues = new ArrayList<String>();
			actualTextValues.add(commonMethods.getTextbyXpath(webPage, overlayData[4][1], softAssert));
			actualTextValues.add(commonMethods.getTextbyXpath(webPage, overlayData[5][1], softAssert));
			actualTextValues.add(commonMethods.getTextbyXpath(webPage, overlayData[6][1], softAssert));
			actualTextValues.add(commonMethods.getTextbyXpath(webPage, overlayData[7][1], softAssert));
			actualTextValues.add(commonMethods.getTextbyXpath(webPage, overlayData[8][1], softAssert));

			for(int i=0;i<actualTextValues.size();i++){
				softAssert.assertTrue(actualTextValues.get(i).contains(overlayData[i+4][2]),"Expected Text: "+overlayData[i+4][2]+" Actual Text: "+actualTextValues.get(i));
			}

		}catch(Exception e){
			softAssert.assertAll();
		}
	}

	/*
	 * - This method will add product with status InStock to cart if product delivery is available for given location.
	 */

	public List<String> addInstockProductForAvailableLocation(WebPage webPage, String[][] inStockProduct,SoftAssert softAssert){
		String errorMessage = null;
		//String productText = null;
		List<String> actualTextValues= new ArrayList<String>();
		String instockProductAvailableMessage= null;
		String productName= null;
		String productShippingAreaText = null;
		String productPrice = null;
		try {
			log.info("Adding In-Stock product to cart");
			List<WebElement> listOfProducts = commonMethods.getWebElementsbyXpath(webPage,commonData[1][1], softAssert);
			WebElement product;
			for (int i = 1; i <= listOfProducts.size(); i++) {
				log.info("Searching product "+i+" for text "+inStockProduct[0][3]);
				product = webPage.getDriver().findElement(By.xpath(commonData[0][1] + i + "]"));
				if ((product.getText().toUpperCase().contains(inStockProduct[0][3]))&& (product.getText().toUpperCase().contains(inStockProduct[0][2]))) {
					log.info("Found product number "+i+" with text "+inStockProduct[0][3]);
					log.info("Clicking on product:::" + i);
					commonMethods.clickElementbyXpath(webPage, commonData[5][1] + i + "]",softAssert);
					productName = commonMethods.getTextbyXpath(webPage, inStockProduct[10][1], softAssert);
					productShippingAreaText = commonMethods.getTextbyXpath(webPage, inStockProduct[11][1], softAssert);
					productPrice = commonMethods.getTextbyXpath(webPage, inStockProduct[12][1], softAssert);
					boolean isOverlayDisplayed = commonMethods.verifyElementisPresent(webPage,commonData[4][1], softAssert);
					if(!isOverlayDisplayed){
						try{
							commonMethods.clickElementbyXpath(webPage, commonData[3][1], softAssert);
						}catch(Exception e){
							log.info("Clicking Add to Cart button not required as Overlay box is already open");
						}
					}

					isOverlayDisplayed = commonMethods.verifyElementisPresent(webPage,commonData[4][1], softAssert);
					log.info("isOverlayDisplayed:" + isOverlayDisplayed);

					commonMethods.clearElementbyXpath(webPage, inStockProduct[5][1], softAssert);
					CommonMethods.waitForWebElement(By.xpath(inStockProduct[5][1]), webPage);
					commonMethods.sendKeysbyXpath(webPage, inStockProduct[5][1], inStockProduct[5][3],softAssert);
					Thread.sleep(2000);
					try{
						CommonMethods.waitForWebElement(By.xpath(inStockProduct[6][1]), webPage);
					}catch(Exception e){
						e.getLocalizedMessage();
					}
					commonMethods.clickElementbyXpath(webPage, inStockProduct[6][1], softAssert);
					Thread.sleep(10000);
					boolean isPresent = webPage.getDriver().findElements(By.xpath(inStockProduct[8][1])).size() > 0;
					if (!isPresent) {
						log.info("Product found with given delivery location");
						instockProductAvailableMessage = commonMethods.getTextbyXpath(webPage, inStockProduct[9][1], softAssert);
						actualTextValues.add(instockProductAvailableMessage);
						actualTextValues.add(productName);
						actualTextValues.add(productShippingAreaText);
						actualTextValues.add(productPrice);
						commonMethods.clickElementbyXpath(webPage, inStockProduct[7][1], softAssert);
						log.info("Clicked on ADD TO CART button in Overlay Box");
						commonMethods.waitForPageLoad(webPage, softAssert);
						if (webPage.getDriver().getPageSource().contains("Shopping Cart is Empty")) {
							log.info("Cart is Empty message displayed after clicking on Add to Cart button. Test will not be able to complete successfully because of this.");
							boolean isShoppingCartEmpty = webPage.getDriver().getPageSource().contains("Shopping Cart is Empty");
							log.info("isShoppingCartEmpty:" + isShoppingCartEmpty);
							Assert.assertFalse(isShoppingCartEmpty,
									"Cart is Empty message shown even after adding valid product. Test cannot be completed as product did not get added to cart.");
							break;
						}
						log.info("Clicked on Add To Cart button for Instock product successfully");
						break;
					} else {
						errorMessage = commonMethods.getTextbyXpath(webPage, inStockProduct[8][1], softAssert);
						log.info(errorMessage);
						log.info("Delivery for product not available for given location, trying next In-Stock product");
						webPage.getDriver().findElement(By.xpath(commonData[4][1])).click();

						if (testType.equalsIgnoreCase("Web")) {
							Click_On_French_Door_Link(webPage, commonData[7][1], softAssert);
							numberOfProductDisplaySelectDropdownByValue(webPage, commonData[8][1], "28", softAssert);
						} else {
							clickOnMobileMenuOption(webPage, mobileMenuData, softAssert);
							if(!((testBedName.contains("iPhone"))||(testBedName.contains("iPad")))){
								commonMethods.clickElementbyXpath_usingJavaScript(webPage, mobileMenuData[4][2], softAssert);	
							}
						}
					}
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Method addInstockProductForAvailableLocation failed");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
		return actualTextValues;
	}

	/*
	 * - This method will add product with status PickupOnly to cart if product delivery is available for given location.
	 */

	public void addPickupOnlyProductForAvailableLocation(WebPage webPage, String[][] pickupOnlyProduct,SoftAssert softAssert){
		String errorMessage = null;

		try {
			log.info("Adding Pickup Only product to cart");
			commonMethods.navigateToPage(webPage, pickupOnlyProduct[14][3], softAssert);
			boolean isOverlayDisplayed = commonMethods.verifyElementisPresent(webPage,commonData[4][1], softAssert);
			if(!isOverlayDisplayed){
				try{
					commonMethods.clickElementbyXpath(webPage, commonData[3][1], softAssert);
				}catch(Exception e){
					log.info("Clicking Add to Cart button not required as Overlay box is already open");
				}

				isOverlayDisplayed = commonMethods.verifyElementisPresent(webPage,commonData[4][1], softAssert);
				log.info("isOverlayDisplayed:" + isOverlayDisplayed);

				commonMethods.clearElementbyXpath(webPage, pickupOnlyProduct[5][1], softAssert);
				CommonMethods.waitForWebElement(By.xpath(pickupOnlyProduct[5][1]), webPage);
				commonMethods.sendKeysbyXpath(webPage, pickupOnlyProduct[5][1], pickupOnlyProduct[5][3],softAssert);
				Thread.sleep(2000);
				try{
					CommonMethods.waitForWebElement(By.xpath(pickupOnlyProduct[6][1]), webPage);
				}catch(Exception e){
					e.getLocalizedMessage();
				}
				commonMethods.clickElementbyXpath(webPage, pickupOnlyProduct[6][1], softAssert);
				Thread.sleep(10000);
				boolean isPresent = webPage.getDriver().findElements(By.xpath(pickupOnlyProduct[8][1])).size() > 0;
				if (!isPresent) {
					log.info("Product found with given delivery location");
					commonMethods.clickElementbyXpath(webPage, pickupOnlyProduct[7][1], softAssert);
					log.info("Clicked on Add To Cart button in Overlay Box");
					Thread.sleep(5000);
					if (isAlertPresent())
					{
						log.info("Alert present after clicking on Add to Cart button, this can be because of slow network");
						Alert alert = webPage.getDriver().switchTo().alert();
						alert.accept();
						Thread.sleep(7000);
						commonMethods.clickElementbyXpath(webPage, pickupOnlyProduct[7][1], softAssert);
					}
					if (webPage.getDriver().getPageSource().contains("Shopping Cart is Empty")) {
						log.info("Cart is Empty message displayed after clicking on Add to Cart button. Test will not be able to complete successfully because of this.");
						boolean isShoppingCartEmpty = webPage.getDriver().getPageSource().contains("Shopping Cart is Empty");
						log.info("isShoppingCartEmpty:" + isShoppingCartEmpty);
						Assert.assertFalse(isShoppingCartEmpty,
								"Cart is Empty message shown even after adding valid product. Test cannot be completed as product did not get added to cart.");
					}
					log.info("Clicked on Add To Cart button for Pickup Only product successfully");
				} else {
					errorMessage = commonMethods.getTextbyXpath(webPage, pickupOnlyProduct[8][1], softAssert);
					log.info(errorMessage);
					log.info("Delivery for product not available for given location, trying next Pickup Only product");
					webPage.getDriver().findElement(By.xpath(commonData[4][1])).click();

					if (testType.equalsIgnoreCase("Web")) {
						Click_On_French_Door_Link(webPage, commonData[7][1], softAssert);
						numberOfProductDisplaySelectDropdownByValue(webPage, commonData[8][1], "28", softAssert);
					} else {
						clickOnMobileMenuOption(webPage, mobileMenuData, softAssert);
						if(!((testBedName.contains("iPhone"))||(testBedName.contains("iPad")))){
							commonMethods.clickElementbyXpath_usingJavaScript(webPage, mobileMenuData[4][2], softAssert);	
						}
					}
				}
			}
		} catch (Throwable e) {
			log.error("Method addPickupOnlyProductForAvailableLocation failed");
			Assert.fail(e.getLocalizedMessage());
			softAssert.assertAll();
		}
	}

	/*
	 * - This method will add product with status InStocky to cart if product delivery is available for given location.
	 * - This method will mark check-box pickup before adding the product to cart
	 */

	public void addInstockPickupProductForAvailableLocation(WebPage webPage, String[][] inStockProduct,SoftAssert softAssert){
		String errorMessage = null;

		try {
			log.info("Adding In-Stock Pickup product to cart");
			List<WebElement> listOfProducts = commonMethods.getWebElementsbyXpath(webPage,commonData[1][1], softAssert);
			WebElement product;
			for (int i = 1; i <= listOfProducts.size(); i++) {
				log.info("Searching product "+i+" for text "+inStockProduct[0][3]);
				product = webPage.getDriver().findElement(By.xpath(commonData[0][1] + i + "]"));
				if ((product.getText().toUpperCase().contains(inStockProduct[0][3]))&& (product.getText().toUpperCase().contains(inStockProduct[0][2]))) {
					log.info("Found product number "+i+" with text "+inStockProduct[0][3]);
					log.info("Clicking on product:::" + i);
					commonMethods.clickElementbyXpath(webPage, commonData[5][1] + i + "]",softAssert);

					boolean isOverlayDisplayed = commonMethods.verifyElementisPresent(webPage,commonData[4][1], softAssert);
					if(isOverlayDisplayed){
						try{
							log.info("Overlay box open by default, closing it to mark pick-up checkbox");
							commonMethods.clickElementbyXpath(webPage, commonData[4][1], softAssert);
							Thread.sleep(2000);
						}catch(Exception e){
							log.info("Unable to close overlay box");
						}
					}
					log.info("Clicking on pickup checkbox");
					commonMethods.clickElementbyXpath(webPage, commonData[6][1], softAssert);
					boolean isPickupChecked = webPage.getDriver().findElement(By.xpath(commonData[6][1])).isSelected();
					log.info("isPickupChecked"+isPickupChecked);
					while(!isPickupChecked){
						log.info("Re-clicking on pickup checkbox");
						commonMethods.clickElementbyXpath(webPage, commonData[6][1], softAssert);
						log.info("isPickupChecked"+isPickupChecked);
					}
					log.info("Opening overlay box");
					commonMethods.clickElementbyXpath(webPage, commonData[3][1], softAssert);
					Thread.sleep(3000);
					isOverlayDisplayed = commonMethods.verifyElementisPresent(webPage,commonData[4][1], softAssert);
					log.info("isOverlayDisplayed:" + isOverlayDisplayed);

					commonMethods.clearElementbyXpath(webPage, inStockProduct[5][1], softAssert);
					CommonMethods.waitForWebElement(By.xpath(inStockProduct[5][1]), webPage);
					commonMethods.sendKeysbyXpath(webPage, inStockProduct[5][1], inStockProduct[5][3],softAssert);
					Thread.sleep(2000);
					try{
						CommonMethods.waitForWebElement(By.xpath(inStockProduct[6][1]), webPage);
					}catch(Exception e){
						e.getLocalizedMessage();
					}
					commonMethods.clickElementbyXpath(webPage, inStockProduct[6][1], softAssert);
					Thread.sleep(10000);
					boolean isPresent = webPage.getDriver().findElements(By.xpath(inStockProduct[8][1])).size() > 0;
					if (!isPresent) {
						log.info("Product found with given delivery location");
						commonMethods.clickElementbyXpath(webPage, inStockProduct[7][1], softAssert);
						log.info("Clicked on ADD TO CART button in Overlay Box");
						Thread.sleep(5000);
						if (isAlertPresent())
						{
							log.info("Alert present after clicking on Add to Cart button, this can be because of slow network");
							Alert alert = webPage.getDriver().switchTo().alert();
							alert.accept();
							Thread.sleep(7000);
							commonMethods.clickElementbyXpath(webPage, inStockProduct[7][1], softAssert);
						}
						if (webPage.getDriver().getPageSource().contains("Shopping Cart is Empty")) {
							log.info("Cart is Empty message displayed after clicking on Add to Cart button. Test will not be able to complete successfully because of this.");
							boolean isShoppingCartEmpty = webPage.getDriver().getPageSource().contains("Shopping Cart is Empty");
							log.info("isShoppingCartEmpty:" + isShoppingCartEmpty);
							Assert.assertFalse(isShoppingCartEmpty,
									"Cart is Empty message shown even after adding valid product. Test cannot be completed as product did not get added to cart.");
							break;
						}
						log.info("Clicked on Add To Cart button for Instock Pickup product successfully");
						break;
					} else {
						errorMessage = commonMethods.getTextbyXpath(webPage, inStockProduct[8][1], softAssert);
						log.info(errorMessage);
						log.info("Delivery for product not available for given location, trying next In-Stock product");
						webPage.getDriver().findElement(By.xpath(commonData[4][1])).click();

						if (testType.equalsIgnoreCase("Web")) {
							Click_On_French_Door_Link(webPage, commonData[7][1], softAssert);
							numberOfProductDisplaySelectDropdownByValue(webPage, commonData[8][1], "28", softAssert);
						} else {
							clickOnMobileMenuOption(webPage, mobileMenuData, softAssert);
							if(!((testBedName.contains("iPhone"))||(testBedName.contains("iPad")))){
								commonMethods.clickElementbyXpath_usingJavaScript(webPage, mobileMenuData[4][2], softAssert);	
							}
						}
					}
				}
			}
		} catch (Throwable e) {
			log.error("Method addInstockPickupProductForAvailableLocation failed");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/*
	 * Below methods are not used in product purchase but used in product search and product listing
	 */
	public static void page_Is_Shopping_Cart_Empty(WebPage webPage) throws InterruptedException {
		Thread.sleep(3000);
		boolean isShoppingCartEmpty = webPage.getDriver().getPageSource().contains("Shopping Cart is Empty");
		log.info("isShoppingCartEmpty:" + isShoppingCartEmpty);
		Assert.assertFalse(isShoppingCartEmpty,
				"--------- Functionality Failure ::: Actual:Shopping cart is empty  Expected: product should be added to cart-------");
	}

	public static void Click_On_French_Door_Link(WebPage webPage, String test) {
		try {
			//webPage.getDriver().manage().deleteAllCookies();
			WebElement element = webPage.findObjectByxPath(test).getWebElement();
			JavascriptExecutor executor = (JavascriptExecutor) webPage.getDriver();
			executor.executeScript("arguments[0].click();", element);
			log.info("clicked on :" + test);
		} catch (PageException e) {
			log.error(e.getMessage());
		}
	}

	public void proceedBySelectingConnsHomePlusPaymentMethod(WebPage webPage, String[][] connsHomePlusCard_Billing_data,SoftAssert softAssert){
		log.info("Entering Conns homeplus card number");
		commonMethods.clearTextBoxByXpath(webPage, connsHomePlusCard_Billing_data[8][1], softAssert);
		commonMethods.sendKeysbyXpath(webPage, connsHomePlusCard_Billing_data[8][1], connsHomePlusCard_Billing_data[8][3], softAssert);
	}

	public void hhregInputInOrderReviewSection(WebPage webPage, String[][] connsHomePlusCard_OrderReviewData,SoftAssert softAssert) throws InterruptedException{
		log.info("Entering hhreg inputs in order review section");
		commonMethods.sendKeysbyXpath(webPage, connsHomePlusCard_OrderReviewData[0][1], connsHomePlusCard_OrderReviewData[0][2], softAssert);
		commonMethods.sendKeysbyXpath(webPage, connsHomePlusCard_OrderReviewData[2][1], connsHomePlusCard_OrderReviewData[2][2], softAssert);
		commonMethods.selectDropdownByValue(webPage, connsHomePlusCard_OrderReviewData[3][1], connsHomePlusCard_OrderReviewData[3][2], softAssert);
		commonMethods.clickElementbyXpath(webPage, connsHomePlusCard_OrderReviewData[4][1], softAssert);
		commonMethods.clickElementbyXpath(webPage, connsHomePlusCard_OrderReviewData[5][1], softAssert);
		Thread.sleep(15000);
	}

	/*
	 * - This method will add product with status PickupOnly to cart if product delivery is available for given location.
	 */
	public void addPickupOnlyAndPromotionalProductForAvailableLocation(WebPage webPage, String[][] pickupOnlyProduct,SoftAssert softAssert,String productType){
		String errorMessage = null;
		try {
			log.info("Adding Pickup Only product to cart");
			List<WebElement> listOfProducts = commonMethods.getWebElementsbyXpath(webPage,commonData[1][1], softAssert);
			WebElement product;
			boolean isOverlayDisplayed= false;
			boolean run = false;
			String url = webPage.getCurrentUrl();
			for (int i = 1; i <= listOfProducts.size(); i++) {
				product = webPage.getDriver().findElement(By.xpath(commonData[0][1] + i + "]"));//*[@class="promotions-box"]/p[1]
				if(productType.equalsIgnoreCase("promotional"))
				{
					log.info("Searching for Promotional product");
					run = false;
					log.info("Searching product "+i+" for text "+pickupOnlyProduct[0][3]+" & "+pickupOnlyProduct[0][2]+" & "+pickupOnlyProduct[13][2]+" & "+pickupOnlyProduct[14][2]);
					if ((product.getText().toUpperCase().contains(pickupOnlyProduct[0][3]))&&
							(product.getText().toUpperCase().contains(pickupOnlyProduct[0][2])))
					{
						if((product.getText().toUpperCase().contains(pickupOnlyProduct[13][2]))||
								(product.getText().toUpperCase().contains(pickupOnlyProduct[14][2])))
						{
							run = true;
							log.info("Found product number "+i+" with text "+pickupOnlyProduct[0][3]+" & "+pickupOnlyProduct[0][2]
									+" & "+pickupOnlyProduct[13][2]+" or "+pickupOnlyProduct[14][2]);
							log.info("Clicking on product:::" + i);
							commonMethods.clickElementbyXpath(webPage, commonData[5][1] + i + "]",softAssert);
							isOverlayDisplayed = commonMethods.verifyElementisPresent(webPage,commonData[4][1], softAssert);
							if(!isOverlayDisplayed){
								try{
									commonMethods.clickElementbyXpath(webPage, commonData[3][1], softAssert);
								}catch(Exception e){
									log.info("Clicking Add to Cart button not required as Overlay box is already open");
								}
							}
						}
					}
				}
				else if(productType.equalsIgnoreCase("nonpromotional")){
					log.info("Searching for Non-Promotional product");
					run = false;
					log.info("Searching Non-Promotional product "+i+" for text "+pickupOnlyProduct[0][3]+" & "+pickupOnlyProduct[0][2]);
					if ((product.getText().toUpperCase().contains(pickupOnlyProduct[0][3]))&& (product.getText().toUpperCase().contains(pickupOnlyProduct[0][2]))
							) {
						if((!(product.getText().toUpperCase().contains(pickupOnlyProduct[13][2])))&&
								(!(product.getText().toUpperCase().contains(pickupOnlyProduct[14][2]))))
						{
							run = true;
							log.info("Found product number "+i+" with text "+pickupOnlyProduct[0][3]);
							log.info("Clicking on product:::" + i);
							commonMethods.clickElementbyXpath(webPage, commonData[5][1] + i + "]",softAssert);
							isOverlayDisplayed = commonMethods.verifyElementisPresent(webPage,commonData[4][1], softAssert);
							if(!isOverlayDisplayed){
								try{
									commonMethods.clickElementbyXpath(webPage, commonData[3][1], softAssert);
								}catch(Exception e){
									log.info("Clicking Add to Cart button not required as Overlay box is already open");
								}
							}
						}
					}
				}
				else{
					run = false;
					if ((product.getText().toUpperCase().contains(pickupOnlyProduct[0][3]))&& (product.getText().toUpperCase().contains(pickupOnlyProduct[0][2]))) {
						log.info("Found product number "+i+" with text "+pickupOnlyProduct[0][3]);
						log.info("Clicking on product:::" + i);
						commonMethods.clickElementbyXpath(webPage, commonData[5][1] + i + "]",softAssert);
						run = true;
						isOverlayDisplayed = commonMethods.verifyElementisPresent(webPage,commonData[4][1], softAssert);
						if(!isOverlayDisplayed){
							try{
								commonMethods.clickElementbyXpath(webPage, commonData[3][1], softAssert);
							}catch(Exception e){
								log.info("Clicking Add to Cart button not required as Overlay box is already open");
							}
						}
					}
				}
				if(run)
				{
					isOverlayDisplayed = commonMethods.verifyElementisPresent(webPage,commonData[4][1], softAssert);
					log.info("isOverlayDisplayed:" + isOverlayDisplayed);
					//Enter zipcode
					commonMethods.clearElementbyXpath(webPage, pickupOnlyProduct[5][1], softAssert);
					CommonMethods.waitForWebElement(By.xpath(pickupOnlyProduct[5][1]), webPage);
					commonMethods.sendKeysbyXpath(webPage, pickupOnlyProduct[5][1], pickupOnlyProduct[5][3],softAssert);
					Thread.sleep(2000);
					try{
						CommonMethods.waitForWebElement(By.xpath(pickupOnlyProduct[6][1]), webPage);
					}catch(Exception e){
						e.getLocalizedMessage();
					}
					//click update button
					commonMethods.clickElementbyXpath(webPage, pickupOnlyProduct[6][1], softAssert);
					Thread.sleep(10000);
					// verifying if error message is displayed
					boolean isPresent = webPage.getDriver().findElements(By.xpath(pickupOnlyProduct[8][1])).size() > 0;
					if (!isPresent) {
						log.info("Product found with given delivery location");
						commonMethods.clickElementbyXpath(webPage, pickupOnlyProduct[7][1], softAssert);
						log.info("Clicked on Add To Cart button in Overlay Box");
						Thread.sleep(5000);
						if (isAlertPresent())
						{
							log.info("Alert present after clicking on Add to Cart button, this can be because of slow network");
							Alert alert = webPage.getDriver().switchTo().alert();
							alert.accept();
							Thread.sleep(7000);
							commonMethods.clickElementbyXpath(webPage, pickupOnlyProduct[7][1], softAssert);
						}
						if (webPage.getDriver().getPageSource().contains("Shopping Cart is Empty")) {
							log.info("Cart is Empty message displayed after clicking on Add to Cart button. Test will not be able to complete successfully because of this.");
							boolean isShoppingCartEmpty = webPage.getDriver().getPageSource().contains("Shopping Cart is Empty");
							log.info("isShoppingCartEmpty:" + isShoppingCartEmpty);
							Assert.assertFalse(isShoppingCartEmpty,
									"Cart is Empty message shown even after adding valid product. Test cannot be completed as product did not get added to cart.");
							break;
						}
						log.info("Clicked on Add To Cart button for Pickup Only product successfully");
						break;
					} else {
						errorMessage = commonMethods.getTextbyXpath(webPage, pickupOnlyProduct[8][1], softAssert);
						log.info(errorMessage);
						log.info("Delivery for product not available for given location, trying next Pickup Only product");
						// closing zipcode box
						webPage.getDriver().findElement(By.xpath(commonData[4][1])).click();
						Thread.sleep(3000);
						if (testType.equalsIgnoreCase("Web")) {
							Click_On_French_Door_Link(webPage, commonData[7][1], softAssert);
							//chnge items displayed per page to 28
							//    numberOfProductDisplaySelectDropdownByValue(webPage, commonData[8][1], "28", softAssert);
						} else {
							clickOnMobileMenuOption(webPage, mobileMenuData, softAssert);
							//      commonMethods.clickElementbyXpath_usingJavaScript(webPage, mobileMenuData[4][2], softAssert);
						}
					}
				}
				else{
					if (testType.equalsIgnoreCase("Web")) {
						if(!url.equals(webPage.getCurrentUrl()))
						{
							Click_On_French_Door_Link(webPage, commonData[7][1], softAssert);
							numberOfProductDisplaySelectDropdownByValue(webPage, commonData[8][1], "28", softAssert);
						}
						//chnge items displayed per page to 28
					} else {
						if(!url.equals(webPage.getCurrentUrl()))
						{
							clickOnMobileMenuOption(webPage, mobileMenuData, softAssert);
							commonMethods.clickElementbyXpath_usingJavaScript(webPage, mobileMenuData[4][2], softAssert);
						}
					}
				}
			}
		} catch (Throwable e) {
			log.error("Method addPickupOnlyProductForAvailableLocation failed");
			Assert.fail(e.getLocalizedMessage());
			softAssert.assertAll();
		}
	}

	/*
	 * - This method will add product given product to cart. Please make sure product that is passed has available delivery
	 */
	public List<String> addGivenProductToCart(WebPage webPage, String productUrl, String[][] addProductData,SoftAssert softAssert, boolean getProductDetails){
		List<String> actualTextValues= new ArrayList<String>();
		String instockProductAvailableMessage= null;
		String productName= null;
		String productShippingAreaText = null;
		String productPrice = null;
		try {

			log.info("Adding given product to cart");
			commonMethods.navigateToPage(webPage, productUrl, softAssert);
			Thread.sleep(2000);
			openOverlayBox(webPage, softAssert);

			if(getProductDetails){	

				productName = commonMethods.getTextbyXpath(webPage, addProductData[10][1], softAssert);
				productShippingAreaText = commonMethods.getTextbyXpath(webPage, addProductData[11][1], softAssert);
				productPrice = commonMethods.getTextbyXpath(webPage, addProductData[12][1], softAssert);

				actualTextValues.add(productName);
				actualTextValues.add(productShippingAreaText);
				actualTextValues.add(productPrice);
			}

			commonMethods.clearElementbyXpath(webPage, addProductData[5][1], softAssert);
			CommonMethods.waitForWebElement(By.xpath(addProductData[5][1]), webPage);
			commonMethods.sendKeysbyXpath(webPage, addProductData[5][1], addProductData[5][3],softAssert);
			Thread.sleep(2000);

			try{
				//wait for update button in overlay box
				CommonMethods.waitForWebElement(By.xpath(addProductData[6][1]), webPage);
			}catch(Exception e){
				e.getLocalizedMessage();
			}
			//click update button
			commonMethods.clickElementbyXpath(webPage, addProductData[6][1], softAssert);
			Thread.sleep(5000);

			//verify is error message shown for given zip code
			boolean isPresent = webPage.getDriver().findElements(By.xpath(addProductData[8][1])).size() > 0;

			if (!isPresent) {
				log.info("Product found with given delivery location");
				//getting valid message from overlay box for later verification
				if(getProductDetails){	
					instockProductAvailableMessage = commonMethods.getTextbyXpath(webPage, addProductData[9][1], softAssert);
					actualTextValues.add(instockProductAvailableMessage);
				}

				log.info("Clicking on Add To Cart button in Overlay Box");
				commonMethods.clickElementbyXpath(webPage, addProductData[7][1], softAssert);
				Thread.sleep(8000);
				//verify if shopping cart is empty after selecting add to cart button
				if (webPage.getDriver().getPageSource().contains("Shopping Cart is Empty")) {
					log.info("Cart is Empty message displayed after clicking on Add to Cart button. Test will not be able to complete successfully because of this.");
					boolean isShoppingCartEmpty = webPage.getDriver().getPageSource().contains("Shopping Cart is Empty");
					log.info("isShoppingCartEmpty:" + isShoppingCartEmpty);
					Assert.assertFalse(isShoppingCartEmpty,
							"Cart is Empty message shown even after adding valid product. Test cannot be completed as product did not get added to cart.");
				}
				log.info("Clicked on Add To Cart button for Instock product successfully");
			}else{
				log.info("Product not available for given zipcode");
			}

		} catch (Throwable e) {
			log.error("Method addInstockProductForAvailableLocation failed");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
		return actualTextValues;
	}

	public boolean deleteCookies()
	{
		try{
			webPage.getDriver().manage().deleteAllCookies();
			log.info("browser history cleared");
			return true;
		}catch(Throwable e){
			log.info("Unable to delete cookies for current browser.");
			return false;
		}
	}

	public void navigateToProductListingPage(WebPage webpage,String productListingPageLink, SoftAssert softAssert) throws InterruptedException {

		if (testType.equalsIgnoreCase("Web")) {
			Click_On_French_Door_Link(webPage, productListingPageLink, softAssert);
			numberOfProductDisplaySelectDropdownByValue(webPage, commonData[8][1], "28", softAssert);
		} else {
			clickOnMobileMenuOption(webPage, mobileMenuData, softAssert);
			if(!((testBedName.contains("iPhone"))||(testBedName.contains("iPad")))){
				commonMethods.clickElementbyXpath_usingJavaScript(webPage, mobileMenuData[4][2], softAssert);	
			}
		}
	}

	/**
	 * Method ot verify if shipping method option is displayed
	 * If displayed method will select white gloves option and click on continue button
	 * If white gloves option is not displayed then it will  directly click on Continue button 
	 * @param webpage
	 * @param softAssert
	 * @throws InterruptedException 
	 */
	public void verifyShippingMethodOptionIsDisplayed(WebPage webpage, SoftAssert softAssert) throws InterruptedException {
		if(commonMethods.verifyElementisPresent(webpage, commonData[29][1], softAssert))
		{
			log.info("Shipping Method Option is displayed");
			if(commonMethods.verifyElementisPresent(webpage, commonData[39][1], softAssert))
			{
				log.info("Selecting White Glover Shipping Method");
				commonMethods.clickElementbyXpath(webpage, commonData[39][1], softAssert);
			}
			log.info("Clicking on Shipping Info Continue Button");
			commonMethods.clickElementbyXpath(webpage, commonData[29][1], softAssert);
		}


	}

	public void emptyShoppingCart(WebPage webPage, SoftAssert softAssert) throws InterruptedException
	{
		try{
			Thread.sleep(5000);
			String[][] checkoutPageData = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Checkout_Page_Elements");
			String[][] cartPageData = ExcelUtil.readExcelData(DataFilePath, "ProductPurchase","Cart_Page_Data");
			log.info("Deleting Cart items from user account");
			log.info("Clicking on Cart Header");
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[43][1], softAssert);
			log.info("Clicking on Shopping Cart Link");
			commonMethods.clickElementbyXpath(webPage, checkoutPageData[44][1], softAssert);
			Thread.sleep(3000);
			if(commonMethods.verifyElementisPresent(webPage, cartPageData[6][1], softAssert))
			{
				List<WebElement> removeItemButtons = commonMethods.findElementsByXpath(webPage, cartPageData[6][1], softAssert);
				log.info("Number of items present in cart: "+removeItemButtons.size());
				int counter =0;
				while(removeItemButtons.size()>0){
					removeItemButtons.get(0).click();
					commonMethods.waitForGivenTime(10, softAssert);
					commonMethods.waitForPageLoad(webPage, softAssert);
					try{
						removeItemButtons = webPage.getDriver().findElements(By.xpath(cartPageData[6][1]));	
						log.info("Number of items present in cart: "+removeItemButtons.size());
						counter++;
						if(counter>5){
							break;
						}
					}catch(Exception e){
						log.info("No more items pending in cart");
						break;
					}
				}
			}else{log.info("Shopping Cart is Empty");}
		}catch(Throwable t){
			t.printStackTrace();
		}

	}
}