package com.etouch.connsPages;

import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.asserts.SoftAssert;

import com.etouch.connsTests.Conns_Credit_App_Page;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.webui.selenium.WebPage;

public class CreditAppPage extends Conns_Credit_App_Page {
	static Log log = LogUtil.getLog(CreditAppPage.class);
	static String CreditAppUrl = null;

	/**
	 * Method to navigate to Credit App Page
	 * 
	 * @author sjadhav
	 * @param webPage
	 * @param softAssert
	 * @throws Exception
	 */
	public static void navigateToCreditAppPage(SoftAssert softAssert) throws Exception {
		if (CreditAppUrl == null) {
			webPage.navigateToUrl(url);
			log.info("Navigating to Home Page");
			log.info("Navigating to Credit App Page");
			commonMethods.clickElementbyXpath(webPage, commonData.get("YesMoneyGetCreditTodayApplyNowLink"),
					softAssert);
			commonMethods.waitForPageLoad(webPage, softAssert);
			commonMethods.clickElementbyXpath(webPage, commonData.get("ApplyForMyYesMoney"), softAssert);
			commonMethods.waitForPageLoad(webPage, softAssert);
			verifyPageTitle(commonData.get("CreditAppPageTitle"), softAssert);
			log.info("Navigated to Credit App Page");
			CreditAppUrl = webPage.getCurrentUrl();
		} else {
			webPage.navigateToUrl(CreditAppUrl);
			commonMethods.waitForPageLoad(webPage, softAssert);
			verifyPageTitle(commonData.get("CreditAppPageTitle"), softAssert);
		}
		/*
		 * if(webPage.getPageTitle().equalsIgnoreCase(commonData.get(
		 * "CreditAppPageTitle"))) {log.info("Already on Credit App Page");
		 * webPage.getDriver().navigate().refresh(); } else{
		 * if(!webPage.getPageTitle().equalsIgnoreCase(commonData.get(
		 * "YesMoneyGetCreditTodayApplyNowLink"))) { System.out.println(
		 * "aaaaaaaaaa "+url); webPage.navigateToUrl(url); log.info(
		 * "Navigating to Home Page"); } log.info(
		 * "Navigating to Credit App Page");
		 * commonMethods.clickElementbyXpath(webPage,
		 * commonData.get("YesMoneyGetCreditTodayApplyNowLink"), softAssert);
		 * commonMethods.waitForPageLoad(webPage, softAssert);
		 * commonMethods.clickElementbyXpath(webPage,
		 * commonData.get("ApplyForMyYesMoney"), softAssert);
		 * commonMethods.waitForPageLoad(webPage, softAssert);
		 * verifyPageTitle(commonData.get("CreditAppPageTitle"),softAssert);
		 * log.info("Navigated to Credit App Page"); }
		 */
	}

	public static void navigateToHomePage(SoftAssert softAssert) throws Exception {
		if (webPage.getPageTitle().equalsIgnoreCase(commonData.get("HomePageTitle"))) {
			log.info("Already on HomePage");
			webPage.getDriver().navigate().refresh();
		} else {
			webPage.navigateToUrl(url);
			log.info("Navigating to Home Page");
			verifyPageTitle(commonData.get("HomePageTitle"), softAssert);
		}
	}

	/**
	 * Verify page title
	 * 
	 * @author sjadhav
	 * @param webPage
	 * @param expectedTitle
	 * @param softAssert
	 * @return
	 */
	public static boolean verifyPageTitle(String expectedTitle, SoftAssert softAssert) {
		String actualTitle = commonMethods.getPageTitle(webPage, softAssert);
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
	 * @param webPage
	 * @param linkName
	 * @param locator
	 * @param expectedUrl
	 * @return
	 * @throws Exception
	 */
	public static boolean validateLinkRedirection(String linkName, String locator, String expectedUrl)
			throws Exception {
		String ActualUrl;
		ActualUrl = clickAndGetPageURL(locator, linkName);
		if (ActualUrl.contains(expectedUrl)) {
			log.info("Redirection for link " + linkName + " is successful");
			webPage.getBackToUrl();
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
	public static String clickAndGetPageURL(String locator, String linkName) throws Exception {
		String pageUrl = "";
		try {
			log.info("Clicking on link : " + linkName);
			String mainWindow = webPage.getDriver().getWindowHandle();
			webPage.findObjectByxPath(locator).click();
			Set<String> windowHandlesSet = webPage.getDriver().getWindowHandles();
			if (windowHandlesSet.size() > 1) {
				for (String winHandle : windowHandlesSet) {
					webPage.getDriver().switchTo().window(winHandle);
					if (!winHandle.equalsIgnoreCase(mainWindow)) {
						log.info("More than 1 window open after clicking on link : " + linkName);
						pageUrl = webPage.getCurrentUrl();
						webPage.getDriver().close();
						webPage.getDriver().switchTo().window(mainWindow);
					}
				}
			} else {
				pageUrl = webPage.getCurrentUrl();
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
	 * public static void verifyTextField(WebPage webPage, String[][] locator,
	 * SoftAssert softAssert) throws Exception { List<WebElement> elements = new
	 * ArrayList<WebElement>(); elements =
	 * webPage.getDriver().findElements(By.xpath(locator));
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
	 * Verify if Text Field is displayed with expected Value
	 * 
	 * @author sjadhav
	 * @param webPage
	 * @param locators
	 * @param softAssert
	 * @throws Exception
	 */
	public static void verifyTextFieldValue(String[][] locators, SoftAssert softAssert) throws Exception {
		for (int i = 0; i < locators.length; i++) {
			try {
				WebElement element = getWebElementbyXpath(locators[i][1], softAssert);
				// webPage.getDriver().findElement(By.xpath(locators[i][1]));
				if (!element.getAttribute("value").equalsIgnoreCase(locators[i][2])) {
					log.info("Failed to verify TextBox \"" + locators[i][0] + "\" With Xpath : " + locators[i][1]
							+ " Expected value is : \"" + locators[i][2] + "\" Displayed Value : \""
							+ element.getAttribute("value") + "\"");
					softAssert.fail("Failed to verify TextBox \"" + locators[i][0] + "\" With Xpath : " + locators[i][1]
							+ " Expected value is : \"" + locators[i][2] + "\" Displayed Value : \""
							+ element.getAttribute("value") + "\"");
				} else {
					log.info("TextBox \"" + locators[i][0] + "\" is displayed with expected value : \"" + locators[i][2]
							+ "\"");
				}
			} catch (Exception e) {
				log.info("Failed to verify TextBox : " + locators[i][0] + " With Xpath " + locators[i][1]
						+ " Localized Message :" + e.getLocalizedMessage());
				softAssert.fail("Failed to verify TextBox : " + locators[i][0] + " With Xpath " + locators[i][1]
						+ " Localized Message :" + e.getLocalizedMessage());
			}
		}
	}

	public static void verifyFieldValues(String[][] locators, SoftAssert softAssert) throws Exception {
		for (int i = 0; i < locators.length; i++) {
			try {
				if (locators[i][1].equalsIgnoreCase("textField")) {
					WebElement element = getWebElementbyXpath(locators[i][2], softAssert);
					// webPage.getDriver().findElement(By.xpath(locators[i][1]));
					String actual = element.getAttribute("value");
					if (!actual.equalsIgnoreCase(locators[i][3])) {
						softAssert.fail("TextBox Expected value : " + locators[i][3] + " Actual value : " + actual);
					} else {
						log.info("TextBox \"" + locators[i][0] + "\" is displayed with expected value : \""
								+ locators[i][3] + "\"");
					}
				} else if (locators[i][1].equalsIgnoreCase("dropDown")) {
					String actual = getSelectedValueFromDropDownXpath(softAssert, locators[i][0], locators[i][2])
							.trim();
					if (!actual.equalsIgnoreCase(locators[i][3])) {
						softAssert.fail("DropDown Expected value : " + locators[i][3] + " Actual value : " + actual);
					} else {
						log.info("DropDown \"" + locators[i][0] + "\" is displayed with expected value : \""
								+ locators[i][3] + "\"");
					}
				} else {
					softAssert.fail("Unable to Identify field type Field Name : " + locators[i][0] + " Field Type : "
							+ locators[i][1] + " Xpath :" + locators[i][2]);
				}
				// webPage.getDriver().findElement(By.xpath(locators[i][1]));
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
				log.info("Failed to verify TextBox : " + locators[i][0] + " With Xpath " + locators[i][1]
						+ " Localized Message :" + e.getLocalizedMessage());
				softAssert.fail("Failed to verify TextBox : " + locators[i][0] + " With Xpath " + locators[i][1]
						+ " Localized Message :" + e.getLocalizedMessage());
			}
		}
	}

	/**
	 * Clicks on Submit button on Credit Application Page
	 * 
	 * @author sjadhav
	 * @param webPage
	 * @param softAssert
	 * @throws Exception
	 */
	public static void submitCreditAppForRegisteredUser(SoftAssert softAssert) throws Exception {
		commonMethods.clickElementbyXpath(webPage, commonData.get("SubmitButton"), softAssert);
	}

	public static void submitCreditAppForNewUser(SoftAssert softAssert) throws Exception {
		commonMethods.clickElementbyXpath(webPage, commonData.get("SubmitButton"), softAssert);
		commonMethods.waitForPageLoad(webPage, softAssert);
		Thread.sleep(10000);
		String actual = commonMethods.getTextbyXpath(webPage, commonData.get("NewUserSuccessPageXpath"), softAssert);
		softAssert.assertTrue(actual.equals(commonData.get("NewUserSuccessPageMessage")),
				"Expected : " + commonData.get("NewUserSuccessPageMessage") + " Actual : " + actual);
	}

	/**
	 * Verify expected and actual error message
	 * 
	 * @author sjadhav
	 * @param webPage
	 * @param softAssert
	 * @param errorMessageFieldName
	 * @param locator
	 * @param expectedErrorMessage
	 */
	public static void verifyErrorMessageByXpath(SoftAssert softAssert, String errorMessageFieldName, String locator,
			String expectedErrorMessage) {
		log.info("Verifiyikng error message for : " + errorMessageFieldName + " : Expected Message : "
				+ expectedErrorMessage);
		String actualErrorMessage = commonMethods.getTextbyXpath(webPage, locator, softAssert);
		softAssert.assertTrue(expectedErrorMessage.equals(actualErrorMessage), "Failed to verify error field :"
				+ errorMessageFieldName + " : Expected : " + expectedErrorMessage + " Actual : " + actualErrorMessage);
	}

	public static void verifyErrorMessageById(SoftAssert softAssert, String errorMessageFieldName, String locator,
			String expectedErrorMessage) {
		log.info("Verifiyikng error message for : " + errorMessageFieldName + " : Expected Message : "
				+ expectedErrorMessage);
		String actualErrorMessage = commonMethods.getTextbyId(webPage, locator, softAssert);
		softAssert.assertTrue(expectedErrorMessage.equals(actualErrorMessage), "Failed to verify error field :"
				+ errorMessageFieldName + " : Expected : " + expectedErrorMessage + " Actual : " + actualErrorMessage);
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
	 */
	public static void verifyErrorMessageWithInvalidDataById(SoftAssert softAssert, String FieldName, String locator,
			String inputText, String errorMessageLocator, String expectedErrorMessage) {
		WebElement element = commonMethods.getWebElementbyID(webPage, locator, softAssert);
		element.sendKeys(inputText + Keys.TAB);
		verifyErrorMessageById(softAssert, FieldName, errorMessageLocator, expectedErrorMessage);
	}

	public static String getSelectedValueFromDropDownID(SoftAssert softAssert, String FieldName, String locator) {
		Select select = new Select(webPage.getDriver().findElement(By.id(locator)));
		String value = select.getFirstSelectedOption().getText();
		log.info("value selected in Drop Down is : " + value);
		return value;
	}

	public static String getSelectedValueFromDropDownXpath(SoftAssert softAssert, String FieldName, String locator) {
		Select select = new Select(webPage.getDriver().findElement(By.xpath(locator)));
		String value = select.getFirstSelectedOption().getText();
		log.info("value selected in Drop Down is : " + value);
		return value;
	}

	/**
	 * Verifies all dropdown values with expected value
	 * 
	 * @author sjadhav
	 * @param softAssert
	 * @param FieldName
	 * @param locator
	 * @param expectedValue
	 */
	public static void verifyDropDownValuesById(SoftAssert softAssert, String FieldName, String locator,
			String[][] expectedValue) {
		Select select = new Select(commonMethods.getWebElementbyID(webPage, locator, softAssert));// webPage.getDriver().findElement(By.id(locator))
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

	public static void verifyTextFieldIsEditableByID(SoftAssert softAssert, String FieldName, String locator,
			String newValue) {
		if (commonMethods.getWebElementbyID(webPage, locator, softAssert).isEnabled()) {
			log.info("TextBox is enabled");
			log.info("Setting TextBox \"" + FieldName + "\" Value to : " + newValue);
			commonMethods.clearTextBoxById(webPage, locator, softAssert);
			commonMethods.sendKeysById(webPage, locator, newValue, softAssert);
			String actual = commonMethods.getTextbyId(webPage, locator, softAssert);
			softAssert.assertTrue(actual.equals(newValue),
					"Failed to Update TextBox value, New Value : " + newValue + " Existing Value : " + actual);
		} else {
			log.info("TextBox is Disabled");
			softAssert.fail(" Text Field \"" + FieldName + "\" is disabled ");
		}
	}

	public static boolean verifyTextFieldIsEditableByXpath(SoftAssert softAssert, String FieldName, String locator,
			String newValue) {
		if (commonMethods.getWebElementbyXpath(webPage, locator, softAssert).isEnabled()) {
			log.info("TextBox is enabled");
			log.info("Setting TextBox \"" + FieldName + "\" Value to : " + newValue);
			JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
			if (!((String) js.executeScript("return arguments[0].getValue()",
					commonMethods.getWebElementbyXpath(webPage, locator, softAssert)) == null))
				commonMethods.clearTextBoxByXpath(webPage, locator, softAssert);
			// WebElement element = commonMethods.getWebElementbyXpath(webPage,
			// locator, softAssert);
			commonMethods.sendKeysbyXpath(webPage, locator, newValue, softAssert);
			String actual = (String) js.executeScript("return arguments[0].getValue()",
					commonMethods.getWebElementbyXpath(webPage, locator, softAssert));
			// String actual = commonMethods.getTextbyXpath(webPage, locator,
			// softAssert);
			if (actual.equals(newValue))
				return true;
			// softAssert.assertTrue(actual.equals(newValue),"Failed to Update
			// TextBox value, New Value : "+newValue+" Existing Value :
			// "+actual);
		} else if (!verifyElementisPresent(webPage, locator, softAssert)) {
			log.info("TextBox \"" + FieldName + "\" is Not Displayed");
			softAssert.fail(" Text Field \"" + FieldName + "\" is not Displayed ");
		}
		return false;
	}

	public void getTextByJs() {
	}

	public static void verifyDropDownFieldIsEditableById(SoftAssert softAssert, String FieldName, String locator,
			String newValue) {
		if (commonMethods.getWebElementbyID(webPage, locator, softAssert).isEnabled()) {
			log.info("DropDown is enabled");
			log.info("Setting DropDown Value to : " + newValue);
			Select select = new Select(commonMethods.getWebElementbyID(webPage, locator, softAssert));
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

	public static void verifyDropDownFieldIsEditableByXpath(SoftAssert softAssert, String FieldName, String locator,
			String newValue) {
		if (commonMethods.getWebElementbyXpath(webPage, locator, softAssert).isEnabled()) {
			log.info("DropDown is enabled");
			log.info("Setting DropDown Value to : " + newValue);
			selectValueFromDropDownByXpath(softAssert, FieldName, locator, newValue);
		} else {
			log.info("DropDown is Disabled");
			softAssert.fail(" DropDown Field " + FieldName + " is disabled ");
		}
	}

	public static void selectRadioButtonByXpath(SoftAssert softAssert, String FieldName, String locator) {
		if (commonMethods.getWebElementbyXpath(webPage, locator, softAssert).isEnabled()) {
			log.info("Selecting Radio button : \"" + FieldName + "\"");
			commonMethods.clickElementbyXpath(webPage, locator, softAssert);
		} else {
			log.info("RadioButton is Disabled");
			softAssert.fail(" RadioButton Field " + FieldName + " is disabled ");
		}
	}

	public static void selectCheckBoxByXpath(SoftAssert softAssert, String FieldName, String locator) {
		if (commonMethods.getWebElementbyXpath(webPage, locator, softAssert).isEnabled()) {
			log.info("Selecting CheckBox : \"" + FieldName + "\"");
			commonMethods.clickElementbyXpath(webPage, locator, softAssert);
		} else {
			log.info("CheckBox is Disabled");
			softAssert.fail(" CheckBox Field " + FieldName + " is disabled ");
		}
	}

	public static void selectValueFromDropDownByID(SoftAssert softAssert, String FieldName, String locator,
			String newValue) {
		log.info("Setting DropDown \"" + FieldName + "\" Value to : " + newValue);
		Select select = new Select(commonMethods.getWebElementbyID(webPage, locator, softAssert));
		select.selectByVisibleText(newValue.trim());
	}

	public static void selectValueFromDropDownByXpath(SoftAssert softAssert, String FieldName, String locator,
			String newValue) {
		log.info("Setting DropDown \"" + FieldName + "\" Value to : " + newValue);
		Select select = new Select(commonMethods.getWebElementbyXpath(webPage, locator, softAssert));
		select.selectByVisibleText(newValue.trim());
		String actual = select.getFirstSelectedOption().getText().trim();
		softAssert.assertTrue(actual.equals(newValue),
				"Failed to Update Drop Down Value, New Value : " + newValue + " Existing Value : " + actual);
	}

	public static void sendTextToTextFieldsById(SoftAssert softAssert, String[][] textFieldData) {
		for (int i = 0; i < textFieldData.length; i++) {
			log.info("Setting TextField " + textFieldData[i][0] + " value to : " + textFieldData[i][2]);
			verifyTextFieldIsEditableByID(softAssert, textFieldData[i][0], textFieldData[i][1], textFieldData[i][2]);
		}
	}

	public static void fillForm(SoftAssert softAssert, String[][] FieldData) {
		int dataLength = FieldData.length;
		for (int i = 0; i < dataLength; i++) {
			try {
				switch (FieldData[i][1]) {
				case "textField":
					verifyTextFieldIsEditableByXpath(softAssert, FieldData[i][0], FieldData[i][2], FieldData[i][3]);
					// commonMethods.sendKeysByXpath(webPage, FieldData[i][2],
					// FieldData[i][3], softAssert);
					break;
				case "dropDown":
					verifyDropDownFieldIsEditableByXpath(softAssert, FieldData[i][0], FieldData[i][2], FieldData[i][3]);
					// selectValueFromDropDownByXpath(softAssert,
					// FieldData[i][0], FieldData[i][2], FieldData[i][3]);
					break;
				case "radio":
					selectRadioButtonByXpath(softAssert, FieldData[i][0], FieldData[i][2]);
					break;
				case "checkBox":
					selectCheckBoxByXpath(softAssert, FieldData[i][0], FieldData[i][2]);
					break;
				default:
					softAssert.fail("Invalid Data in datasheet. FieldType is not set as expected. Current value is : "
							+ FieldData[i][1]);
				}
				/*
				 * if(FieldData[i][1].equalsIgnoreCase("textField")) {
				 * //verifyTextFieldIsEditableByXpath(softAssert,
				 * FieldData[i][0], FieldData[i][2], FieldData[i][3]);
				 * commonMethods.sendKeysByXpath(webPage, FieldData[i][2],
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

	public static WebElement getWebElementbyXpath(String locator, SoftAssert softAssert) throws Exception {
		WebElement element = null;
		try {
			log.info("Finding element using xpath :" + locator);
			element = webPage.getDriver().findElement(By.xpath(locator));
		} catch (Exception e) {
			log.info("Unable to find element using Xpath : " + locator);
			throw new Exception("Unable to find element using Xpath : " + locator + ". Localized Message: "
					+ e.getLocalizedMessage());
		}
		return element;
	}

	public static void loginFromCreditApp(SoftAssert softAssert) throws Exception {
		// navigateToHomePage(softAssert);
		commonMethods.clickElementbyXpath(webPage, commonData.get("SignInNowLink"), softAssert);
		login(commonData.get("Username"), commonData.get("Password"), softAssert);
	}

	public static void login(String username, String password, SoftAssert softAssert) {
		commonMethods.sendKeysbyXpath(webPage, commonData.get("EmailIdTextBox"), username, softAssert);
		commonMethods.sendKeysbyXpath(webPage, commonData.get("PassTextBox"), password, softAssert);
		commonMethods.clickElementbyXpath(webPage, commonData.get("LoginButton"), softAssert);
	}

	public static boolean verifyElementisPresent(WebPage webPage, String locator, SoftAssert softAssert) {
		Boolean isElementPresent = false;
		try {
			log.info("Verifying if element is present by locator - " + locator);
			isElementPresent = webPage.findObjectByxPath(locator).isDisplayed();
		} catch (Throwable e) {
		}
		return isElementPresent;
	}
}
/*
 * public static void verifyAutoPopulateCityState() { WebElement element =
 * commonMethods.getWebElementbyID(webPage, locator, softAssert);
 * element.sendKeys(inputText+Keys.TAB);
 * 
 * }
 */
