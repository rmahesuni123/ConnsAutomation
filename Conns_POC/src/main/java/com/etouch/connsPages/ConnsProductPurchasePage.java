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
			webPage.getDriver().manage().deleteAllCookies();
			commonMethods.clickElementbyXpath(webPage, mobileMenuData[0][2], softAssert);
			commonMethods.clickElementbyXpath(webPage, mobileMenuData[1][2], softAssert);
			commonMethods.clickElementbyXpath(webPage, mobileMenuData[2][2], softAssert);
			commonMethods.clickElementbyXpath(webPage, mobileMenuData[3][2], softAssert);
		} catch (Exception e) {
			softAssert.fail("Failed to click on French Door menu in mobile.");
		}
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
			if(!(browserNames.get(Thread.currentThread().getId()).equalsIgnoreCase("edge")||browserNames.get(Thread.currentThread().getId()).equalsIgnoreCase("Safari"))){
				Thread.sleep(3000);
				CommonMethods.waitForWebElement(By.xpath(locator), webPage);
				WebElement web=webPage.getDriver().findElement(By.xpath(locator));
				Select select=new Select(web);
				select.selectByVisibleText(dropdownvalue);
			}else{
				commonMethods.clickElementbyXpath_usingJavaScript(webPage, mobileMenuData[4][2], softAssert);
			}
			
			Thread.sleep(3000);
		} catch (Throwable e) {
			softAssert.fail("Unable to click on element using XPath : "+ locator+". Localized Message: "+e.getLocalizedMessage());
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
				if ((product.getText().contains(openOverlayData[0][2]))&& (product.getText().contains(openOverlayData[1][2]))) {
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
				if ((product.getText().contains(inStockProduct[0][3]))&& (product.getText().contains(inStockProduct[0][2]))) {
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
							commonMethods.clickElementbyXpath_usingJavaScript(webPage, mobileMenuData[4][2], softAssert);
						}
					}
				}
			}
		} catch (Throwable e) {
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
			List<WebElement> listOfProducts = commonMethods.getWebElementsbyXpath(webPage,commonData[1][1], softAssert);
			WebElement product;
			for (int i = 1; i <= listOfProducts.size(); i++) {
				log.info("Searching product "+i+" for text "+pickupOnlyProduct[0][3]);
				product = webPage.getDriver().findElement(By.xpath(commonData[0][1] + i + "]"));
				if ((product.getText().contains(pickupOnlyProduct[0][3]))&& (product.getText().contains(pickupOnlyProduct[0][2]))) {
					log.info("Found product number "+i+" with text "+pickupOnlyProduct[0][3]);
					log.info("Clicking on product:::" + i);
					commonMethods.clickElementbyXpath(webPage, commonData[5][1] + i + "]",softAssert);
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
							break;
						}
						log.info("Clicked on Add To Cart button for Pickup Only product successfully");
						break;
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
				if ((product.getText().contains(inStockProduct[0][3]))&& (product.getText().contains(inStockProduct[0][2]))) {
					log.info("Found product number "+i+" with text "+inStockProduct[0][3]);
					log.info("Clicking on product:::" + i);
					commonMethods.clickElementbyXpath(webPage, commonData[5][1] + i + "]",softAssert);
			
					boolean isOverlayDisplayed = commonMethods.verifyElementisPresent(webPage,commonData[4][1], softAssert);
					if(isOverlayDisplayed){
						try{
							log.info("Overlay box open by default, closing it to mark pick-up checkbox");
							commonMethods.clickElementbyXpath(webPage, commonData[4][1], softAssert);
							
						}catch(Exception e){
							log.info("Clicking Add to Cart button not required as Overlay box is already open");
						}
					}
					log.info("Opening overlay box");
					commonMethods.clickElementbyXpath(webPage, commonData[6][1], softAssert);
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
							commonMethods.clickElementbyXpath_usingJavaScript(webPage, mobileMenuData[4][2], softAssert);
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
			webPage.getDriver().manage().deleteAllCookies();
			WebElement element = webPage.findObjectByxPath(test).getWebElement();
			JavascriptExecutor executor = (JavascriptExecutor) webPage.getDriver();
			executor.executeScript("arguments[0].click();", element);
			log.info("clicked on :" + test);
		} catch (PageException e) {
			log.error(e.getMessage());
		}
	}
}