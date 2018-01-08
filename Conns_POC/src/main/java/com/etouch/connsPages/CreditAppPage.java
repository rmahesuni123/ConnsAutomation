package com.etouch.connsPages;

import java.awt.AWTException;
import java.awt.Robot;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.etouch.common.CommonMethods;
import com.etouch.connsTests.Conns_Credit_App_Page;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.webui.ITafElement;
import com.etouch.taf.webui.selenium.WebPage;

public class CreditAppPage extends Conns_Credit_App_Page {
	 static Log log = LogUtil.getLog(CreditAppPage.class);
	 String CreditAppUrl = null;
	 CommonMethods commonMethods = new CommonMethods();
	 CreditAppPage creditAppPage/* = new CreditAppPage()*/;
	/**
	 * Method to navigate to Credit App Page
	 * 
	 * @author sjadhav
	 * @param webPageMap.get(Thread.currentThread().getId())
	 * @param softAssert
	 * @throws Exception
	 */
	public void navigateToCreditAppPage(SoftAssert softAssert) throws Exception {
		if (CreditAppUrl == null) {
			// webPageMap.get(Thread.currentThread().getId()).navigateToUrl(url);
			// log.info("Navigating to Home Page");
			log.info("Navigating to Credit App Page");
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("YesMoneyGetCreditTodayApplyNowLink"),
					softAssert);
			commonMethods.waitForPageLoad(webPageMap.get(Thread.currentThread().getId()), softAssert);
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("ApplyForMyYesMoney"), softAssert);
			commonMethods.waitForPageLoad(webPageMap.get(Thread.currentThread().getId()), softAssert);
			verifyPageTitle(commonData.get("CreditAppPageTitle"), softAssert);
			log.info("Navigated to Credit App Page");
			CreditAppUrl = webPageMap.get(Thread.currentThread().getId()).getCurrentUrl();
		} else {
			webPageMap.get(Thread.currentThread().getId()).navigateToUrl(CreditAppUrl);
			commonMethods.waitForPageLoad(webPageMap.get(Thread.currentThread().getId()), softAssert);
			verifyPageTitle(commonData.get("CreditAppPageTitle"), softAssert);
		}
		
	}
	
	public void navigateToYesMoneyLandingPage(SoftAssert softAssert) throws Exception {
			webPageMap.get(Thread.currentThread().getId()).navigateToUrl(url);
			// log.info("Navigating to Home Page");
			log.info("Navigating to Yes Money Landing Page");
			commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("YesMoneyGetCreditTodayApplyNowLink"),
					softAssert);
			commonMethods.waitForPageLoad(webPageMap.get(Thread.currentThread().getId()), softAssert);
			
		
	}
	
	
	/**
	 * Navigates user to conns Home Page
	 * 
	 * @author sjadhav
	 * @param softAssert
	 * @throws Exception
	 */
	/*
	 * public void navigateToHomePage(SoftAssert softAssert) throws
	 * Exception { if
	 * (webPageMap.get(Thread.currentThread().getId()).getPageTitle().equalsIgnoreCase(commonData.get("HomePageTitle"))
	 * ) { log.info("Already on HomePage");
	 * webPageMap.get(Thread.currentThread().getId()).getDriver().navigate().refresh(); } else {
	 * webPageMap.get(Thread.currentThread().getId()).navigateToUrl(url); log.info("Navigating to Home Page");
	 * verifyPageTitle(commonData.get("HomePageTitle"), softAssert); } }
	 */
	/**
	 * Verify page title
	 * 
	 * @author sjadhav
	 * @param webPageMap.get(Thread.currentThread().getId())
	 * @param expectedTitle
	 * @param softAssert
	 * @return
	 */
	public boolean verifyPageTitle(String expectedTitle, SoftAssert softAssert) {
		String actualTitle = commonMethods.getPageTitle(webPageMap.get(Thread.currentThread().getId()), softAssert);
		log.info("Verifying Page Title");
		if (actualTitle.equalsIgnoreCase(expectedTitle)) {
			log.info("Page Title successfully verified");
			return true;
		} else {
			softAssert.fail("Actual Page Title : " + actualTitle + " Expected Title : " + expectedTitle);
			return false;
		}
	}

	/**
	 * Navigates to link and verifies url and redirects back
	 * 
	 * @author sjadhav
	 * @param webPageMap.get(Thread.currentThread().getId())
	 * @param linkName
	 * @param locator
	 * @param expectedUrl
	 * @return
	 * @throws Exception
	 */
	public boolean validateLinkRedirection(String linkName, String locator, String expectedUrl,
			SoftAssert softAssert) throws Exception {
		String ActualUrl;
		ActualUrl = clickAndGetPageURL(locator, linkName, softAssert);
		commonMethods.waitForPageLoad(webPageMap.get(Thread.currentThread().getId()), softAssert);
		if (ActualUrl.contains(expectedUrl)) {
			log.info("Redirection for link " + linkName + " is successful");
			/*String browserName = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedNames.get(Thread.currentThread().getId()))
					.getBrowser().getName();
			if (browserName.equalsIgnoreCase("safari") || browserName.equalsIgnoreCase("iphone") || browserName.equalsIgnoreCase("ipad")) {
				navigateToCreditAppPage(softAssert);
			} else {
				webPageMap.get(Thread.currentThread().getId()).getBackToUrl();
			}*/
			navigateToCreditAppPage(softAssert);
		} else {
			throw new Exception("Redirection for link " + linkName
					+ " failed. Actual URL does not contain Expected partial URL : Expected = " + expectedUrl
					+ " Actual : " + ActualUrl);
		}
		return true;
	}

	/**
	 * @author Name - Deepak Bhambri The method used to click on link using
	 *         x-path and return page url Return type is String Any structural
	 *         modifications to the display of the link should be done by
	 *         overriding this method.
	 * @throws Exception
	 * @throws PageException
	 *             If an input or output exception occurred
	 **/
	public String clickAndGetPageURL(String locator, String linkName, SoftAssert softAssert) throws Exception {
		String pageUrl = "";
		try {
			log.info("Clicking on link : " + linkName);
			String mainWindow = webPageMap.get(Thread.currentThread().getId()).getDriver().getWindowHandle();
			webPageMap.get(Thread.currentThread().getId()).findObjectByxPath(locator).click();
			commonMethods.waitForPageLoad(webPageMap.get(Thread.currentThread().getId()), softAssert);
			Set<String> windowHandlesSet = webPageMap.get(Thread.currentThread().getId()).getDriver().getWindowHandles();
			if (windowHandlesSet.size() > 1) {
				for (String winHandle : windowHandlesSet) {
					webPageMap.get(Thread.currentThread().getId()).getDriver().switchTo().window(winHandle);
					if (!winHandle.equalsIgnoreCase(mainWindow)) {
						log.info("More than 1 window open after clicking on link : " + linkName);
						pageUrl = webPageMap.get(Thread.currentThread().getId()).getCurrentUrl();
						webPageMap.get(Thread.currentThread().getId()).getDriver().close();
						webPageMap.get(Thread.currentThread().getId()).getDriver().switchTo().window(mainWindow);
					}
				}
			} else {
				pageUrl = webPageMap.get(Thread.currentThread().getId()).getCurrentUrl();
			}
			log.info("Actual URL : " + pageUrl);
		} catch (Throwable e) {
			log.info("Unable to click on link '" + linkName + ". Localized Message: " + e.getLocalizedMessage());
			throw new Exception(
					"Unable to click on link '" + linkName + ". Localized Message: " + e.getLocalizedMessage());
		}
		return pageUrl;
	}

	/*
	 * public void verifyTextField(webPageMap.get(Thread.currentThread().getId()) webPageMap.get(Thread.currentThread().getId()), String[][] locator,
	 * SoftAssert softAssert) throws Exception { List<WebElement> elements = new
	 * ArrayList<WebElement>(); elements =
	 * webPageMap.get(Thread.currentThread().getId()).getDriver().findElements(By.xpath(locator));
	 * if(elements.size()==0) throw new Exception(
	 * "Unable to find TextBox with locater : "+locator); for(WebElement ele :
	 * elements) { if(!ele.getAttribute("value").isEmpty()) softAssert.fail(
	 * "TextBox With Xpath "+locator+
	 * " is not rendered empty. Value displayed is : "
	 * +ele.getAttribute("value")); ele.sendKeys("12"); } }
	 * 
	 * }
	 */
	/**
	 * String[][] textFields contains fieldName,fieldIdentifier(xPath),Expected
	 * value
	 * 
	 * @author sjadhav
	 * @param webPageMap.get(Thread.currentThread().getId())
	 * @param locators
	 * @param softAssert
	 * @throws Exception
	 */
	public void verifyTextFieldValue(String[][] textFields, SoftAssert softAssert) throws Exception {
		for (int i = 0; i < textFields.length; i++) {
			try {
				WebElement element = getWebElementbyXpath(textFields[i][1], softAssert);
				// webPageMap.get(Thread.currentThread().getId()).getDriver().findElement(By.xpath(locators[i][1]));
				if (!element.getAttribute("value").equalsIgnoreCase(textFields[i][2])) {
					log.info("Failed to verify TextBox \"" + textFields[i][0] + "\" With Xpath : " + textFields[i][1]
							+ " Expected value is : \"" + textFields[i][2] + "\" Displayed Value : \""
							+ element.getAttribute("value") + "\"");
					softAssert.fail("Failed to verify TextBox \"" + textFields[i][0] + "\" With Xpath : "
							+ textFields[i][1] + " Expected value is : \"" + textFields[i][2]
							+ "\" Displayed Value : \"" + element.getAttribute("value") + "\"");
				} else {
					log.info("TextBox \"" + textFields[i][0] + "\" is displayed with expected value : \""
							+ textFields[i][2] + "\"");
				}
			} catch (Exception e) {
				log.info("Failed to verify TextBox : " + textFields[i][0] + " With Xpath " + textFields[i][1]
						+ " Localized Message :" + e.getLocalizedMessage());
				softAssert.fail("Failed to verify TextBox : " + textFields[i][0] + " With Xpath " + textFields[i][1]
						+ " Localized Message :" + e.getLocalizedMessage());
			}
		}
	}

	/**
	 * Verifies field values for a form, String[][] fieldData contains
	 * fieldName,
	 * FieldType(textField,radio,dropDown,checkBox),FieldIdentifier(xPath),
	 * expected FieldValue
	 * 
	 * @author sjadhav
	 * @param locators
	 * @param softAssert
	 * @throws Exception
	 */
	public void verifyFieldValues(String[][] fieldData, SoftAssert softAssert) throws Exception {
		for (int i = 0; i < fieldData.length; i++) {
			try {
				if (fieldData[i][1].equalsIgnoreCase("textField")) {
					WebElement element = getWebElementbyXpath(fieldData[i][2], softAssert);
					// webPageMap.get(Thread.currentThread().getId()).getDriver().findElement(By.xpath(locators[i][1]));
					String actual = element.getAttribute("value");
					if (!actual.equalsIgnoreCase(fieldData[i][3])) {
						softAssert.fail("TextBox Expected value : " + fieldData[i][3] + " Actual value : " + actual);
					} else {
						log.info("TextBox \"" + fieldData[i][0] + "\" is displayed with expected value : \""
								+ fieldData[i][3] + "\"");
					}
				} else if (fieldData[i][1].equalsIgnoreCase("dropDown")) {
					String actual = getSelectedValueFromDropDownXpath(softAssert, fieldData[i][0], fieldData[i][2])
							.trim();
					if (!actual.equalsIgnoreCase(fieldData[i][3])) {
						softAssert.fail("DropDown Expected value : " + fieldData[i][3] + " Actual value : " + actual);
					} else {
						log.info("DropDown \"" + fieldData[i][0] + "\" is displayed with expected value : \""
								+ fieldData[i][3] + "\"");
					}
				} else {
					softAssert.fail("Unable to Identify field type Field Name : " + fieldData[i][0] + " Field Type : "
							+ fieldData[i][1] + " Xpath :" + fieldData[i][2]);
				}
				// webPageMap.get(Thread.currentThread().getId()).getDriver().findElement(By.xpath(locators[i][1]));
				/*
				 * if(!element.getAttribute("value").equalsIgnoreCase(locators[i
				 * ][2])) {log.info("Failed to verify TextBox \""+locators[i][0]
				 * + "\" With Xpath : "+locators[i][1]+
				 * " Expected value is : \""+locators[i][2]+
				 * "\" Displayed Value : \""
				 * +element.getAttribute("value")+"\""); softAssert.fail(
				 * "Failed to verify TextBox \""+locators[i][0] +
				 * "\" With Xpath : "+locators[i][1]+ " Expected value is : \""
				 * +locators[i][2]+"\" Displayed Value : \""
				 * +element.getAttribute("value")+"\""); } else{ log.info(
				 * "TextBox \""+locators[i][0] +
				 * "\" is displayed with expected value : \""
				 * +locators[i][2]+"\""); }
				 */
			} catch (Exception e) {
				log.info("Failed to verify TextBox : " + fieldData[i][0] + " With Xpath " + fieldData[i][1]
						+ " Localized Message :" + e.getLocalizedMessage());
				softAssert.fail("Failed to verify TextBox : " + fieldData[i][0] + " With Xpath " + fieldData[i][1]
						+ " Localized Message :" + e.getLocalizedMessage());
			}
		}
	}

	public String getTextBoxValueByJs(String FieldName, String xPtah, SoftAssert softAssert) {
		if (!verifyElementisPresentByXPath(webPageMap.get(Thread.currentThread().getId()), xPtah, softAssert)) {
			softAssert.fail(" Text Field \"" + FieldName + "\" is not Displayed ");
		} else {
			WebElement textField = commonMethods.getWebElementbyXpath(webPageMap.get(Thread.currentThread().getId()), xPtah, softAssert);
			JavascriptExecutor js = (JavascriptExecutor) webPageMap.get(Thread.currentThread().getId()).getDriver();
			/*
			 * if (!((String) js.executeScript("return arguments[0].getValue()",
			 * textField) == null))
			 */
			return (String) js.executeScript("return arguments[0].getValue()", textField);
		}
		return null;
	}

	/**
	 * Clicks on Submit button on Credit Application Page for registered
	 * user(Method incomplete)
	 * 
	 * @author sjadhav
	 * @param webPageMap.get(Thread.currentThread().getId())
	 * @param softAssert
	 * @throws Exception
	 */
	public void submitCreditAppForRegisteredUser(SoftAssert softAssert) throws Exception {
		commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("SubmitButton"), softAssert);
	}

	/**
	 * Submits credit app page for new user and verifies landing page
	 * 
	 * @author sjadhav
	 * @param softAssert
	 * @throws Exception
	 */
	public void submitCreditApp(SoftAssert softAssert) throws Exception {
		scrollToElement(commonData.get("SubmitButton"),softAssert);
		commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("SubmitButton"), softAssert);
		commonMethods.waitForPageLoad(webPageMap.get(Thread.currentThread().getId()), softAssert);
		String actualUrl = commonMethods.getPageUrl(webPageMap.get(Thread.currentThread().getId()), softAssert);
		if (actualUrl.contains(commonData.get("ProcessingPage"))) {
			Thread.sleep(5000);
		}
		commonMethods.waitForPageLoad(webPageMap.get(Thread.currentThread().getId()), softAssert);
		String actualSuccessUrl = commonMethods.getPageUrl(webPageMap.get(Thread.currentThread().getId()), softAssert);
		softAssert.assertTrue(actualSuccessUrl.contains(commonData.get("SuccessPage")),
				"Expected Url : " + commonData.get("SuccessPage") + " Actual Url : " + actualUrl);
	}

	/**
	 * Submits credit app page for and verifies processing page and status
	 * 
	 * @author sjadhav
	 * @param softAssert
	 * @throws Exception
	 */
	public void submitCreditAppAndVerifyStatus(SoftAssert softAssert, String expectedStatus) throws Exception {
		scrollToElement(commonData.get("SubmitButton"),softAssert);
		commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("SubmitButton"), softAssert);
		 Thread.sleep(5000);
		System.out.println("Url isd : " + commonMethods.getPageUrl(webPageMap.get(Thread.currentThread().getId()), softAssert));
		// commonMethods.waitForPageLoad(webPageMap.get(Thread.currentThread().getId()), softAssert);
		if (commonMethods.getPageUrl(webPageMap.get(Thread.currentThread().getId()), softAssert).contains(commonData.get("ProcessingPage"))) {
			log.info("Processing Page is Displayed");
			Thread.sleep(5000);
		} else {
			log.info("Unable to catch processing page");
		}
		commonMethods.waitForPageLoad(webPageMap.get(Thread.currentThread().getId()), softAssert);
		String actualUrl = commonMethods.getPageUrl(webPageMap.get(Thread.currentThread().getId()), softAssert);
		softAssert.assertTrue(actualUrl.contains(commonData.get(expectedStatus)),
				"Expected Url : " + commonData.get(expectedStatus) + " Actual Url : " + actualUrl);
		/*
		 * WebElement element = null; JavascriptExecutor js =
		 * (JavascriptExecutor) webPageMap.get(Thread.currentThread().getId()).getDriver(); System.out.println(
		 * "Getting element using js"); while(element==null){ element =
		 * (WebElement) js.executeAsyncScript(
		 * "document.evaluate(\".//*[@id='success-wireframe']//p[contains(text(),'Please wait. We are processing your application.')]\","
		 * +
		 * " document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;"
		 * ); } // String processingPageUrl = (String)
		 * js.executeAsyncScript("document.URL;"); String processingPageUrl =
		 * commonMethods.getPageUrl(webPageMap.get(Thread.currentThread().getId()), softAssert); System.out.println(
		 * "processingPageUrl is  :"+processingPageUrl);
		 * softAssert.assertTrue(processingPageUrl.contains(commonData.get(
		 * "ProcessingPage")), "Expected : " + commonData.get("ProcessingPage")
		 * + " Actual : " + processingPageUrl); log.info("Expected Url : " +
		 * commonData.get("ProcessingPage") + " Actual Url : " +
		 * processingPageUrl);
		 * while(commonMethods.verifyElementisPresent(webPageMap.get(Thread.currentThread().getId()),
		 * commonData.get("ProcessingPageHeadingXpath"), softAssert))
		 * {commonMethods.waitForGivenTime(2, softAssert);}
		 * 
		 * commonMethods.waitForPageLoad(webPageMap.get(Thread.currentThread().getId()), softAssert); String actualUrl
		 * = commonMethods.getPageUrl(webPageMap.get(Thread.currentThread().getId()), softAssert);
		 * softAssert.assertTrue(actualUrl.contains(commonData.get(
		 * expectedStatus)), "Expected : " +
		 * commonData.get("NewUserSuccessPageMessage") + " Actual : " +
		 * actualUrl);
		 */
	}

	/**
	 * Verify expected and actual error message using xPath
	 * 
	 * @author sjadhav
	 * @param webPageMap.get(Thread.currentThread().getId())
	 * @param softAssert
	 * @param errorMessageFieldName
	 * @param locator
	 * @param expectedErrorMessage
	 * @throws PageException
	 */
	public void verifyErrorMessageByXpath(SoftAssert softAssert, String errorMessageFieldName, String locator,
			String expectedErrorMessage) throws PageException {
		log.info("Verifying error message for : " + errorMessageFieldName + " : Expected Message : "
				+ expectedErrorMessage);
		commonMethods.waitForWebElement(By.xpath(locator), webPageMap.get(Thread.currentThread().getId()));
		String actualErrorMessage = commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), locator, softAssert);
		softAssert.assertTrue(expectedErrorMessage.equals(actualErrorMessage), "Failed to verify error field :"
				+ errorMessageFieldName + " : Expected : " + expectedErrorMessage + " Actual : " + actualErrorMessage);
	}

	/**
	 * Verifies error message displayed with expected error message using Id
	 * 
	 * @author sjadhav
	 * @param softAssert
	 * @param errorMessageFieldName
	 * @param locator
	 * @param expectedErrorMessage
	 * @throws InterruptedException 
	 */
	public void verifyErrorMessageById(SoftAssert softAssert, String errorMessageFieldName, String locator,
			String expectedErrorMessage) throws InterruptedException {
		try {
			Thread.sleep(9000);
			
			//  commonMethods.waitForPageLoad(webPageMap.get(Thread.currentThread().getId()), softAssert);
			//  CommonMethods.waitForWebElement(By.xpath(locator), webPageMap.get(Thread.currentThread().getId()));

		log.info("Verifying error message for : " + errorMessageFieldName + " : Expected Message : "
				+ expectedErrorMessage);
		String actualErrorMessage = commonMethods.getTextbyId(webPageMap.get(Thread.currentThread().getId()), locator, softAssert);
		//Thread.sleep(2000);

		softAssert.assertTrue(expectedErrorMessage.equals(actualErrorMessage), "Failed to verify error field :"
				+ errorMessageFieldName + " : Expected : " + expectedErrorMessage + " Actual : " + actualErrorMessage);
	}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * Verify field errors message by entering invalid data
	 * 
	 * @author sjadhav
	 * @param softAssert
	 * @param FieldName
	 * @param locator
	 * @param inputText
	 * @param errorMessageLocator
	 * @param expectedErrorMessage
	 * @throws AWTException
	 * @throws InterruptedException 
	 */
	public void verifyErrorMessageWithInvalidDataById(SoftAssert softAssert, String FieldName, String locator,
			String inputText, String errorMessageLocator, String expectedErrorMessage) throws AWTException, InterruptedException {
	//	Robot robot = new Robot();
		try {
		WebElement element = commonMethods.getWebElementbyID(webPageMap.get(Thread.currentThread().getId()), locator, softAssert);
		element.clear();
		Thread.sleep(2000);
		element.sendKeys(inputText+Keys.TAB);
		
		//element.sendKeys(inputText+Keys.TAB);
		
		
		
		//commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("FirstNameLable"), softAssert);
		// JavascriptExecutor js = (JavascriptExecutor) webPageMap.get(Thread.currentThread().getId()).getDriver();
		// js.executeScript("var key = require('selenium-webdriver').Key;"
		// + "var actionSequence =
		// require('selenium-webdriver').ActionSequence;"
		// + "new
		// actionSequence("+webPageMap.get(Thread.currentThread().getId()).getDriver()+").sendKeys(key.TAB).perform().then(function(){"
		// + "});", element);
		// if(testType.equalsIgnoreCase("Mobile"))
		// {
		// String deviceName =
		// TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getDevice().getName().toLowerCase();
		// if(deviceName.contains("iphone")||deviceName.contains("ipad")||deviceName.contains("ipad"))
		// robot.keyPress(KeyEvent.VK_ENTER);
		// }
		// else if(browserName.equalsIgnoreCase("Safari"))
		// {
		// //verifyErrorMessageForIos(softAssert, FieldName,
		// errorMessageLocator, expectedErrorMessage);
		// robot.keyPress(KeyEvent.VK_ENTER);
		// commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()),
		// commonData.get("FirstNameLable"), softAssert);
		// }
		verifyErrorMessageById(softAssert, FieldName, errorMessageLocator, expectedErrorMessage);
	}
	catch(Exception e){
		e.printStackTrace();
	}
		}

	/**
	 * Get Current selected Drop Down Value using Id
	 * 
	 * @author sjadhav
	 * @param softAssert
	 * @param FieldName
	 * @param locator
	 * @return
	 */
	public String getSelectedValueFromDropDownID(SoftAssert softAssert, String FieldName, String locator) {
		Select select = new Select(webPageMap.get(Thread.currentThread().getId()).getDriver().findElement(By.id(locator)));
		String value = select.getFirstSelectedOption().getText();
		log.info("value selected in Drop Down is : " + value);
		return value;
	}

	/**
	 * Get Current selected Drop Down Value using xPath
	 * 
	 * @author sjadhav
	 * @param softAssert
	 * @param FieldName
	 * @param locator
	 * @return
	 */
	public String getSelectedValueFromDropDownXpath(SoftAssert softAssert, String FieldName, String locator) {
		Select select = new Select(webPageMap.get(Thread.currentThread().getId()).getDriver().findElement(By.xpath(locator)));
		String value = select.getFirstSelectedOption().getText();
		log.info("value selected in Drop Down is : " + value);
		return value;
	}

	/**
	 * Verifies all values in drop down menu with expected value
	 * 
	 * @author sjadhav
	 * @param softAssert
	 * @param FieldName
	 * @param locator
	 * @param expectedValue
	 */
	public void verifyDropDownValuesById(SoftAssert softAssert, String FieldName, String locator,
			String[][] expectedValue) {
		Select select = new Select(commonMethods.getWebElementbyID(webPageMap.get(Thread.currentThread().getId()), locator, softAssert));// webPageMap.get(Thread.currentThread().getId()).getDriver().findElement(By.id(locator))
		List<WebElement> listOptions = select.getOptions();
		// List<String> listOptionsText= new ArrayList<String>();
		for (int i = 0; i < listOptions.size(); i++) {
			String actual = listOptions.get(i).getText().trim();
			String expected = expectedValue[i][1].trim();
			log.info("DropDown values : " + actual + " Expected : " + expected);
			softAssert.assertTrue(actual.equals(expected),
					"Failed to match DropDown Values : Actual : " + actual + " Expected : " + expected);
		}
	}

	/**
	 * Verifies if textField is editable using Id
	 * 
	 * @author sjadhav
	 * @param softAssert
	 * @param FieldName
	 * @param locator
	 * @param newValue
	 * @return boolean
	 */
	public void verifyTextFieldIsEditableByID(SoftAssert softAssert, String FieldName, String locator,
			String newValue) {
		if (commonMethods.getWebElementbyID(webPageMap.get(Thread.currentThread().getId()), locator, softAssert).isEnabled()) {
			log.info("TextBox is enabled");
			log.info("Setting TextBox \"" + FieldName + "\" Value to : " + newValue);
			commonMethods.clearTextBoxById(webPageMap.get(Thread.currentThread().getId()), locator, softAssert);
			commonMethods.sendKeysById(webPageMap.get(Thread.currentThread().getId()), locator, newValue, softAssert);
			String actual = commonMethods.getTextbyId(webPageMap.get(Thread.currentThread().getId()), locator, softAssert);
			softAssert.assertTrue(actual.equals(newValue),
					"Failed to Update TextBox value, New Value : " + newValue + " Existing Value : " + actual);
		} else {
			log.info("TextBox is Disabled");
			softAssert.fail(" Text Field \"" + FieldName + "\" is disabled ");
		}
	}

	public boolean verifyTextFieldIsEditableByIdJs(SoftAssert softAssert, String FieldName, String locator,
			String newValue) {
		if (!verifyElementisPresentByID(webPageMap.get(Thread.currentThread().getId()), locator, softAssert)) {
			log.info("TextBox \"" + FieldName + "\" is Not Displayed");
			softAssert.fail(" Text Field \"" + FieldName + "\" is not Displayed ");
			return false;
		} else {
			if (newValue == "" || newValue == null) {
				log.info("Value was passed as blank for textField " + FieldName);
				return true;
			}
			if (commonMethods.getWebElementbyID(webPageMap.get(Thread.currentThread().getId()), locator, softAssert).isEnabled()) {
				log.info("TextBox is enabled");
				log.info("Setting TextBox \"" + FieldName + "\" Value to : " + newValue);
				WebElement textField = commonMethods.getWebElementbyID(webPageMap.get(Thread.currentThread().getId()), locator, softAssert);
				JavascriptExecutor js = (JavascriptExecutor) webPageMap.get(Thread.currentThread().getId()).getDriver();
				if (!((String) js.executeScript("return arguments[0].getValue()", textField) == null)) {
					js.executeScript("arguments[0].value='';", textField);
					js.executeScript("arguments[0].value='" + newValue + "';", textField);
					return true;
				}
			} else {
				softAssert.fail("TextBox \"" + FieldName + "\" is Disabled ");
			}
		}
		return false;
	}

	/**
	 * Verifies if textField is editable using xPath
	 * 
	 * @author sjadhav
	 * @param softAssert
	 * @param FieldName
	 * @param locator
	 * @param newValue
	 * @return boolean
	 */
	public boolean verifyTextFieldIsEditableByXpath(SoftAssert softAssert, String FieldName, String locator,
			String newValue) {
		if (!verifyElementisPresentByXPath(webPageMap.get(Thread.currentThread().getId()), locator, softAssert)) {
			log.info("TextBox \"" + FieldName + "\" is Not Displayed");
			softAssert.fail(" Text Field \"" + FieldName + "\" is not Displayed ");
			return false;
		} else {
			if (newValue == "" || newValue == null) {
				log.info("Value was passed as blank for textField " + FieldName);
				return true;
			}
			if (commonMethods.getWebElementbyXpath(webPageMap.get(Thread.currentThread().getId()), locator, softAssert).isEnabled()) {
				log.info("TextBox is enabled");
				log.info("Setting TextBox \"" + FieldName + "\" Value to : " + newValue);
				WebElement textField = commonMethods.getWebElementbyXpath(webPageMap.get(Thread.currentThread().getId()), locator, softAssert);
				JavascriptExecutor js = (JavascriptExecutor) webPageMap.get(Thread.currentThread().getId()).getDriver();
				if (!((String) js.executeScript("return arguments[0].getValue()", textField) == null)) {
					js.executeScript("arguments[0].value='';", textField);
					js.executeScript("arguments[0].value='" + newValue + "';", textField);
					if(((String) js.executeScript("return arguments[0].getValue()", textField) == null)) {
						commonMethods.sendKeysbyXpath(webPageMap.get(Thread.currentThread().getId()), locator, newValue, softAssert);
					}
					return true;
				}
			} else {
				softAssert.fail("TextBox \"" + FieldName + "\" is Disabled ");
			}
		}
		return false;
	}

	public void getTextByJs() {
	}

	/**
	 * Verifies if dropdown Value is editable using Id
	 * 
	 * @author sjadhav
	 * @param softAssert
	 * @param FieldName
	 * @param locator
	 * @param newValue
	 */
	public void verifyDropDownFieldIsEditableById(SoftAssert softAssert, String FieldName, String locator,
			String newValue) {
		if (commonMethods.getWebElementbyID(webPageMap.get(Thread.currentThread().getId()), locator, softAssert).isEnabled()) {
			log.info("DropDown is enabled");
			log.info("Setting DropDown Value to : " + newValue);
			Select select = new Select(commonMethods.getWebElementbyID(webPageMap.get(Thread.currentThread().getId()), locator, softAssert));
			// select.selectByVisibleText(newValue.trim());
			selectValueFromDropDownByID(softAssert, FieldName, locator, newValue);
			String actual = select.getFirstSelectedOption().getText().trim();
			softAssert.assertTrue(actual.equals(newValue),
					"Failed to Update Drop Down Value, New Value : " + newValue + " Existing Value : " + actual);
		} else {
			log.info("DropDown is Disabled");
			softAssert.fail(" DropDown Field " + FieldName + " is disabled ");
		}
	}

	/**
	 * Verifies if dropdown Value is editable using xPath
	 * 
	 * @author sjadhav
	 * @param softAssert
	 * @param FieldName
	 * @param locator
	 * @param newValue
	 */
	public void verifyDropDownFieldIsEditableByXpath(SoftAssert softAssert, String FieldName, String locator,
			String newValue) {
		if (commonMethods.getWebElementbyXpath(webPageMap.get(Thread.currentThread().getId()), locator, softAssert).isEnabled()) {
			log.info("DropDown is enabled");
			log.info("Setting DropDown Value to : " + newValue);
			selectValueFromDropDownByXpath(softAssert, FieldName, locator, newValue);
		} else {
			log.info("DropDown is Disabled");
			softAssert.fail(" DropDown Field " + FieldName + " is disabled ");
		}
	}

	/**
	 * Select Check Radio Button using xpath
	 * 
	 * @author sjadhav
	 * @param softAssert
	 * @param FieldName
	 * @param locator
	 */
	public void selectRadioButtonByXpath(SoftAssert softAssert, String FieldName, String locator) {
		if (commonMethods.getWebElementbyXpath(webPageMap.get(Thread.currentThread().getId()), locator, softAssert).isEnabled()) {
			/*if(TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedNames.get(Thread.currentThread().getId())).getDevice().getName().toLowerCase().contains("nexus"))
			scrollToElement(locator,softAssert);*/
			log.info("Selecting Radio button : \"" + FieldName + "\"");
			//commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), locator, softAssert);
			JavascriptExecutor jse = (JavascriptExecutor)webPageMap.get(Thread.currentThread().getId()).getDriver();

			jse.executeScript("arguments[0].click()", commonMethods.getWebElementbyXpath(webPageMap.get(Thread.currentThread().getId()), locator, softAssert));
		} else {
			log.info("RadioButton is Disabled");
			softAssert.fail(" RadioButton Field " + FieldName + " is disabled ");
		}
	}

	/**
	 * Select Check Box using xpath
	 * 
	 * @author sjadhav
	 * @param softAssert
	 * @param FieldName
	 * @param locator
	 */
	
	public void selectCheckBoxByXpath(SoftAssert softAssert, String FieldName, String locator) {
		if (commonMethods.getWebElementbyXpath(webPageMap.get(Thread.currentThread().getId()), locator, softAssert).isEnabled()) {
			/*if(TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedNames.get(Thread.currentThread().getId())).getDevice().getName().toLowerCase().contains("nexus"))
			scrollToElement(locator,softAssert);*/
			log.info("Selecting CheckBox : \"" + FieldName + "\"");
			//commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), locator, softAssert);
			JavascriptExecutor jse = (JavascriptExecutor)webPageMap.get(Thread.currentThread().getId()).getDriver();

			jse.executeScript("arguments[0].click()", commonMethods.getWebElementbyXpath(webPageMap.get(Thread.currentThread().getId()), locator, softAssert));
		} else {
			log.info("CheckBox is Disabled");
			softAssert.fail(" CheckBox Field " + FieldName + " is disabled ");
		}
	}

	/**
	 * Select Button using xpath
	 * 
	 * @author sjadhav
	 * @param softAssert
	 * @param FieldName
	 * @param locator
	 */
	public void selectButtonByXpath(SoftAssert softAssert, String FieldName, String locator) {
		if (commonMethods.getWebElementbyXpath(webPageMap.get(Thread.currentThread().getId()), locator, softAssert).isEnabled()) {
			log.info("Clicking on Button : \"" + FieldName + "\"");
			//commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), locator, softAssert);
			
			JavascriptExecutor jse = (JavascriptExecutor)webPageMap.get(Thread.currentThread().getId()).getDriver();

			jse.executeScript("arguments[0].click()", commonMethods.getWebElementbyXpath(webPageMap.get(Thread.currentThread().getId()), locator, softAssert)); 
		} else {
			log.info("Button is Disabled");
			softAssert.fail(" Button Field " + FieldName + " is disabled ");
		}
	}

	/**
	 * Select value from drop down menu using Id
	 * 
	 * @author sjadhav
	 * @param softAssert
	 * @param FieldName
	 * @param locator
	 * @param newValue
	 */
	public void selectValueFromDropDownByID(SoftAssert softAssert, String FieldName, String locator,
			String newValue) {
		log.info("Setting DropDown \"" + FieldName + "\" Value to : " + newValue);
		Select select = new Select(commonMethods.getWebElementbyID(webPageMap.get(Thread.currentThread().getId()), locator, softAssert));
		select.selectByVisibleText(newValue.trim());
	}

	/**
	 * Select value from drop down menu using xpath
	 * 
	 * @author sjadhav
	 * @param softAssert
	 * @param FieldName
	 * @param locator
	 * @param newValue
	 */
	public void selectValueFromDropDownByXpath(SoftAssert softAssert, String FieldName, String locator,
			String newValue) {
		log.info("Setting DropDown \"" + FieldName + "\" Value to : " + newValue);
		Select select = new Select(commonMethods.getWebElementbyXpath(webPageMap.get(Thread.currentThread().getId()), locator, softAssert));
		select.selectByVisibleText(newValue.trim());
		String actual = select.getFirstSelectedOption().getText().trim();
		softAssert.assertTrue(actual.equals(newValue),
				"Failed to Update Drop Down Value, New Value : " + newValue + " Existing Value : " + actual);
	}

	/**
	 * Send text to textfields using Id, String[][] textFieldData should contain
	 * FieldName, Locator, FieldValue
	 * 
	 * @author sjadhav
	 * @param softAssert
	 * @param textFieldData
	 */
	public void sendTextToTextFieldsById(SoftAssert softAssert, String[][] textFieldData) {
		for (int i = 0; i < textFieldData.length; i++) {
			log.info("Setting TextField " + textFieldData[i][0] + " value to : " + textFieldData[i][2]);
			verifyTextFieldIsEditableByID(softAssert, textFieldData[i][0], textFieldData[i][1], textFieldData[i][2]);
		}
	}

	/**
	 * Fills form, String[][] FieldData should contains
	 * FieldName,FieldType(textField,radio,dropDown,checkBox),FieldIdentifier(
	 * xPath),FieldValue
	 * 
	 * @author sjadhav
	 * @param softAssert
	 * @param FieldData
	 */
	public void fillForm(SoftAssert softAssert, String[][] FieldData) {
		int dataLength = FieldData.length;
		String testType = TestBedManagerConfiguration.INSTANCE.getTestTypes()[0];
		for (int i = 0; i < dataLength; i++) {
			try {
				switch (FieldData[i][1]) {
				case "textField":
					if (testType.equalsIgnoreCase("Mobile")
							&& FieldData[i][2].equalsIgnoreCase(".//*[@id='applicant:middle-initial']"))
						break;
					verifyTextFieldIsEditableByXpath(softAssert, FieldData[i][0], FieldData[i][2], FieldData[i][3]);
					break;
				case "dropDown":
					verifyDropDownFieldIsEditableByXpath(softAssert, FieldData[i][0], FieldData[i][2], FieldData[i][3]);
					break;
				case "radio":
					selectRadioButtonByXpath(softAssert, FieldData[i][0], FieldData[i][2]);
					break;
				case "checkBox":
					selectCheckBoxByXpath(softAssert, FieldData[i][0], FieldData[i][2]);
					break;
				case "button":
					selectButtonByXpath(softAssert, FieldData[i][0], FieldData[i][2]);
					break;
				default:
					softAssert.fail("Invalid Data in datasheet. FieldType is not set as expected. Current value is : "
							+ FieldData[i][1]);
				}
				/*
				 * if(FieldData[i][1].equalsIgnoreCase("textField")) {
				 * //verifyTextFieldIsEditableByXpath(softAssert,
				 * FieldData[i][0], FieldData[i][2], FieldData[i][3]);
				 * commonMethods.sendKeysByXpath(webPageMap.get(Thread.currentThread().getId()), FieldData[i][2],
				 * FieldData[i][3], softAssert); } else
				 * if(FieldData[i][1].equalsIgnoreCase("dropDown")) {
				 * //verifyDropDownFieldIsEditableByXpath(softAssert,
				 * FieldData[i][0], FieldData[i][2], FieldData[i][3]);
				 * selectValueFromDropDownByXpath(softAssert, FieldData[i][0],
				 * FieldData[i][2], FieldData[i][3]); } else
				 * if(FieldData[i][1].equalsIgnoreCase("radio")) {
				 * selectRadioButtonByXpath(softAssert, FieldData[i][0],
				 * FieldData[i][2]); } else{ softAssert.fail(
				 * "Invalid Data in datasheet. FieldType is not set as expected. Current value is : "
				 * +FieldData[i][1]); }
				 */
			} catch (Throwable e) {
				softAssert.fail("Failed to set value in " + FieldData[i][1] + "  \"" + FieldData[i][0] + "\" Due to :"
						+ e.getLocalizedMessage());
			}
		}
	}

	/**
	 * Get element using xpath
	 * 
	 * @author sjadhav
	 * @param locator
	 * @param softAssert
	 * @return
	 * @throws Exception
	 */
	public WebElement getWebElementbyXpath(String locator, SoftAssert softAssert) throws Exception {
		WebElement element = null;
		try {
			log.info("Finding element using xpath :" + locator);
			element = webPageMap.get(Thread.currentThread().getId()).getDriver().findElement(By.xpath(locator));
		} catch (Exception e) {
			log.info("Unable to find element using Xpath : " + locator);
			throw new Exception("Unable to find element using Xpath : " + locator + ". Localized Message: "
					+ e.getLocalizedMessage());
		}
		return element;
	}

	/**
	 * Logins user from credit app page
	 * 
	 * @author sjadhav
	 * @param softAssert
	 * @throws Exception
	 */
	public void loginFromCreditApp(SoftAssert softAssert) throws Exception {
		// navigateToHomePage(softAssert);
		commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("SignInNowLink"), softAssert);
		login(commonData.get("Username"), commonData.get("Password"), softAssert);
		commonMethods.waitForPageLoad(webPageMap.get(Thread.currentThread().getId()), softAssert);
	}

	/**
	 * Enters username and password and clicks on login button, user should be
	 * on login page
	 * 
	 * @author sjadhav
	 * @param username
	 * @param password
	 * @param softAssert
	 * @throws InterruptedException
	 */
	public void login(String username, String password, SoftAssert softAssert) throws InterruptedException {
		commonMethods.sendKeysbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("EmailIdTextBox"), username, softAssert);
		commonMethods.sendKeysbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("PassTextBox"), password, softAssert);
		commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("LoginButton"), softAssert);
		Thread.sleep(5000);
		commonMethods.waitForPageLoad(webPageMap.get(Thread.currentThread().getId()), softAssert);
	}


	
	/**
	 * Verifies if element is present using xPath
	 * 
	 * @author sjadhav
	 * @param webPageMap.get(Thread.currentThread().getId())
	 * @param locator
	 * @param softAssert
	 * @return
	 */
	public boolean verifyElementisPresentByXPath(WebPage webPage, String locator, SoftAssert softAssert) {
		Boolean isElementPresent = false;
		try {
			log.info("Verifying if element is present by locator - " + locator);
			isElementPresent = webPageMap.get(Thread.currentThread().getId()).findObjectByxPath(locator).isDisplayed();
		} catch (Throwable e) {
		}
		return isElementPresent;
	}

	/**
	 * Verifies if element is present using xPath
	 * 
	 * @author sjadhav
	 * @param webPageMap.get(Thread.currentThread().getId())
	 * @param locator
	 * @param softAssert
	 * @return
	 */
	public boolean verifyElementisPresentByID(WebPage webPage, String locator, SoftAssert softAssert) {
		Boolean isElementPresent = false;
		try {
			log.info("Verifying if element is present by locator - " + locator);
			isElementPresent = webPageMap.get(Thread.currentThread().getId()).findObjectById(locator).isDisplayed();
		} catch (Throwable e) {
		}
		return isElementPresent;
	}

	/**
	 * Changes email address of already logged in user
	 * 
	 * @author sjadhav
	 * @param newEmailAddress
	 * @param softAssert
	 * @throws Exception
	 */
	/*
	 * public void chnageEmailAddress(String newEmailAddress,SoftAssert
	 * softAssert) throws Exception { navigateToHomePage(softAssert);
	 * commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("SignInMenu"),
	 * softAssert); commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()),
	 * commonData.get("SignInMenuMyAccountOption"), softAssert);
	 * commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()),
	 * commonData.get("ContactInformationEditOption"), softAssert);
	 * commonMethods.sendKeysbyXpath(webPageMap.get(Thread.currentThread().getId()),
	 * commonData.get("AccountInformationEmailAddress"),newEmailAddress,
	 * softAssert); commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()),
	 * commonData.get("AccountInformationSaveButton"), softAssert);
	 * navigateToCreditAppPage(softAssert); }
	 */
	/**
	 * Logout already logged in user and navigates to credit app page
	 * 
	 * @author sjadhav
	 * @param softAssert
	 * @throws InterruptedException 
	 * @throws Exception
	 */
	/*
	 * public void signOut(SoftAssert softAssert) throws Exception {
	 * navigateToHomePage(softAssert);
	 * commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("SignInMenu"),
	 * softAssert); commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()),
	 * commonData.get("SignInMenuLogoutOption"), softAssert);
	 * navigateToCreditAppPage(softAssert); }
	 */
	public void verifyErrorMessageForIos(SoftAssert softAssert, String[][] test) throws InterruptedException {
		for (int r = 0; r < test.length; r++) {
			commonMethods.sendKeysById(webPageMap.get(Thread.currentThread().getId()), test[r][1], test[r][2], softAssert);
			// verifyTextFieldIsEditableByIdJs(softAssert, test[r][0],
			// test[r][1], test[r][2]);
		}
		commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("SubmitButton"), softAssert);
		for (int i = 0; i < test.length; i++) {
			verifyErrorMessageById(softAssert, test[i][0], test[i][3], test[i][4]);
		}
	}
	
	
	public void verifyErrorMessageForSafari(SoftAssert softAssert, String[][] test) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) webPageMap.get(Thread.currentThread().getId()).getDriver();
		for (int r = 0; r < test.length; r++) {
			if(!(r >= 6)){
				WebElement element_1 = commonMethods.getWebElementbyID(webPageMap.get(Thread.currentThread().getId()), test[r][1], softAssert);
				element_1.clear();			
				element_1.sendKeys(test[r][2]);
				
				WebElement element_2 = commonMethods.getWebElementbyID(webPageMap.get(Thread.currentThread().getId()), test[r][5], softAssert);
				//js.executeScript("arguments[0].click();", element_2);
				element_2.clear();
				element_2.sendKeys(test[r][2]);
				
				log.info(" Iteration #  "+ r +"*****  Input TELEPHONE NUMBER :  " +test[r][2]); 

			verifyErrorMessageById(softAssert, test[r][0], test[r][3], test[r][4]);
			
			/*for (int i = 0; i < test.length; i++) {
				verifyErrorMessageById(softAssert, test[i][0], test[i][3], test[i][4]);
			}*/
			}else {	
				log.info(" Iteration #  "+ r +"***** Inside Else Part, Please Enter an Invalid TELEPHONE NUMBER :  " +test[r][2]); 
			}
			// verifyTextFieldIsEditableByIdJs(softAssert, test[r][0],
			// test[r][1], test[r][2]);
		}
		/*commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("SubmitButton"), softAssert);
		*/
	}

public void selectValueWithGivenDate(String mmXpath, String ddXpath, String yyyyXpath, String date) throws Exception {
		commonMethods.selectDropdownByValue(webPageMap.get(Thread.currentThread().getId()), yyyyXpath, date.substring(6, 10));
		commonMethods.selectDropdownByValue(webPageMap.get(Thread.currentThread().getId()), mmXpath, date.substring(0, 2));
		commonMethods.selectDropdownByValue(webPageMap.get(Thread.currentThread().getId()), ddXpath, date.substring(3, 5));
		Thread.sleep(2000);
	}
public void selectSpecificValuesWithGivenDate(String YesLeaseData[][], String hireDate, String lastPayDate, String nextPayDate, String accountOpenedDate) throws Exception {
	//Hire Date
	creditAppPage.selectValueWithGivenDate(YesLeaseData[10][1], YesLeaseData[11][1], YesLeaseData[12][1],
			hireDate);
	// Last Pay Date
	creditAppPage.selectValueWithGivenDate(YesLeaseData[14][1], YesLeaseData[15][1], YesLeaseData[16][1],
			lastPayDate);
	// Next Pay Date
	creditAppPage.selectValueWithGivenDate(YesLeaseData[17][1], YesLeaseData[18][1], YesLeaseData[19][1],
			nextPayDate);
	//Account Opened Date
	creditAppPage.selectValueWithGivenDate(YesLeaseData[20][1], YesLeaseData[21][1], YesLeaseData[22][1],
			accountOpenedDate);
	Thread.sleep(2000);
}

public void submitYesLeaseWithValidData(String YesLeaseData[][], SoftAssert softAssert) throws Exception {
	Calendar cal = Calendar.getInstance();
	DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	Date date = new Date();
	cal.setTime(date);
	String todays_Date = dateFormat.format(cal.getTime());
	System.out.println("Todays Date: " + todays_Date);
	cal.add(Calendar.DATE, -1);
	String yesterdays_Date = dateFormat.format(cal.getTime());
	System.out.println("yesterdays_Date : " + yesterdays_Date);
	cal.add(Calendar.MONTH, 1);
	String futureDate_1month = dateFormat.format(cal.getTime());
	System.out.println("futureDate_1month: " + futureDate_1month);
	commonMethods.selectDropdownByValue(webPageMap.get(Thread.currentThread().getId()), YesLeaseData[13][1], YesLeaseData[39][1]);
	
	selectSpecificValuesWithGivenDate(YesLeaseData,yesterdays_Date,yesterdays_Date,futureDate_1month,yesterdays_Date);
	ITafElement CardNumberField = webPageMap.get(Thread.currentThread().getId()).findObjectByxPath(YesLeaseData[26][1]);
	ITafElement routingNumberField = webPageMap.get(Thread.currentThread().getId()).findObjectByxPath(YesLeaseData[30][1]);
	ITafElement accountNumberField = webPageMap.get(Thread.currentThread().getId()).findObjectByxPath(YesLeaseData[35][1]);
	CardNumberField.sendKeys("412345");
	routingNumberField.sendKeys("021000128");
	accountNumberField.sendKeys("21000128");
	accountNumberField.sendKeys(Keys.TAB);

	commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), YesLeaseData[23][1], softAssert);
	commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), YesLeaseData[24][1], softAssert);
	commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("progressiveFormSubmit"), softAssert);
	Thread.sleep(8000);
	commonMethods.waitForPageLoad(webPageMap.get(Thread.currentThread().getId()), softAssert);
}
public String getExpectedDate(String value) throws Exception {
	Calendar cal = Calendar.getInstance();
	DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	Date date = new Date();
	cal.setTime(date);
	String todays_Date = dateFormat.format(cal.getTime());
	System.out.println("Todays Date: " + todays_Date);
	cal.add(Calendar.DATE, -1);
	String yesterdays_Date = dateFormat.format(cal.getTime());
	System.out.println("yesterdays_Date : " + yesterdays_Date);
	cal.add(Calendar.MONTH, -1);
	String PastDate_1month = dateFormat.format(cal.getTime());
	System.out.println("PastDate_1month: " + PastDate_1month);
	cal.add(Calendar.MONTH, -1);
	String PastDate_2month = dateFormat.format(cal.getTime());
	System.out.println("PastDate_2month: " + PastDate_2month);
	cal.add(Calendar.MONTH, 3);
	String futureDate_1month = dateFormat.format(cal.getTime());
	System.out.println("futureDate_1month: " + futureDate_1month);
	cal.add(Calendar.MONTH, 2);
	cal.add(Calendar.DATE, 15);
	String futureDate_4thmonth = dateFormat.format(cal.getTime());
	System.out.println("futureDate_4thmonth: " + futureDate_4thmonth);
	return futureDate_4thmonth;
}

public void scrollToElement(String xpath,SoftAssert softAssert) {
	log.info("Scrolling to element");
	org.openqa.selenium.WebElement elementToScroll = commonMethods.getWebElementbyXpath(webPageMap.get(Thread.currentThread().getId()), xpath, softAssert);
	((JavascriptExecutor) webPageMap.get(Thread.currentThread().getId()).getDriver()).executeScript("arguments[0].scrollIntoView();", elementToScroll);
	try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


public void verifyContentByXpath(SoftAssert softAssert, String MessageFieldName, String locator,
			String expectedMessage) throws PageException {
	
		log.info("Verifying Content message for : " + MessageFieldName + " : Expected Message : "
				+ expectedMessage);
		commonMethods.waitForWebElement(By.xpath(locator), webPageMap.get(Thread.currentThread().getId()));
		String actualMessage = commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), locator, softAssert);
		softAssert.assertTrue(expectedMessage.equals(actualMessage), "Failed to verify error field :"
				+ MessageFieldName + " : Expected : " + expectedMessage + " Actual : " + actualMessage);
	}


public void submitReview(WebPage webPage,SoftAssert softAssert, String[][] labelsTextData, boolean validateAllFields) throws Exception
{
	if(validateAllFields)
	{
		
		//String[][] reviewLabelsData =ExcelUtil.readExcelData(DataFilePath, "CreditApp","reviewLabelsData");
		commonMethods.verifyLabels(webPage, softAssert,ExcelUtil.readExcelData(DataFilePath, "CreditApp","reviewLabelsData"));
		 Thread.sleep(3000);
		 commonMethods.verifyLabelsErrorColor(webPage, softAssert, ExcelUtil.readExcelData(DataFilePath, "CreditApp","reviewDataLabelsErrorColor"));
		// webPageMap.get(Thread.currentThread().getId()).navigateToUrl( webPageMap.get(Thread.currentThread().getId()).getCurrentUrl());
		 Thread.sleep(5000); 
	}
		commonMethods.fillFormWithOutJS(webPage, softAssert, labelsTextData);
		Thread.sleep(5000);
		commonMethods.waitForPageLoad(webPage, softAssert);
		commonMethods.verifyLabels(webPage,softAssert,ExcelUtil.readExcelData(DataFilePath, "CreditApp","reviewThankYouPageData"));
		commonMethods.verifyDate(webPage,softAssert, commonData.get("ReviewPageDate"));
		Thread.sleep(5000);
	}










}

