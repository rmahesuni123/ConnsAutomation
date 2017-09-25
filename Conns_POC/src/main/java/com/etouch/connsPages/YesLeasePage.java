package com.etouch.connsPages;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

import org.apache.commons.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.asserts.SoftAssert;

import com.etouch.common.CommonMethods;
import com.etouch.common.CommonPage;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.taf.webui.ITafElement;
import com.etouch.taf.webui.selenium.WebPage;

public class YesLeasePage extends CommonPage {
	static Log log = LogUtil.getLog(YesLeasePage.class);
	static String CreditAppUrl = null;
	static CommonMethods commonMethods = new CommonMethods();

	public  void submitCreditAppAndVerifyStatus(WebPage webPage,LinkedHashMap<String, String> commonData, SoftAssert softAssert, String expectedStatus) throws Exception {
		commonMethods.clickElementbyXpath(webPage, commonData.get("SubmitButton"), softAssert);
		 Thread.sleep(12000);
		System.out.println("Url isd : " + commonMethods.getPageUrl(webPage, softAssert));
		// commonMethods.waitForPageLoad(webPage, softAssert);
		if (commonMethods.getPageUrl(webPage, softAssert).contains(commonData.get("ProcessingPage"))) {
			log.info("Processing Page is Displayed");
			Thread.sleep(5000);
		} else {
			log.info("Unable to catch processing page");
		}
		commonMethods.waitForPageLoad(webPage, softAssert);
		String actualUrl = commonMethods.getPageUrl(webPage, softAssert);
		softAssert.assertTrue(actualUrl.contains(commonData.get(expectedStatus)),
				"Expected Url : " + commonData.get(expectedStatus) + " Actual Url : " + actualUrl);
	
	}
	public static void fillForm(WebPage webPage,SoftAssert softAssert, String[][] FieldData) {
		int dataLength = FieldData.length;
		String testType = TestBedManagerConfiguration.INSTANCE.getTestTypes()[0];
		for (int i = 0; i < dataLength; i++) {
			try {
				switch (FieldData[i][1]) {
				case "textField":
					if (testType.equalsIgnoreCase("Mobile")
							&& FieldData[i][2].equalsIgnoreCase(".//*[@id='applicant:middle-initial']"))
						break;
					verifyTextFieldIsEditableByXpath(webPage,softAssert, FieldData[i][0], FieldData[i][2], FieldData[i][3]);
					break;
				case "dropDown":
					verifyDropDownFieldIsEditableByXpath(webPage,softAssert, FieldData[i][0], FieldData[i][2], FieldData[i][3]);
					break;
				case "radio":
					selectRadioButtonByXpath(webPage,softAssert, FieldData[i][0], FieldData[i][2]);
					break;
				case "checkBox":
					selectCheckBoxByXpath(webPage,softAssert, FieldData[i][0], FieldData[i][2]);
					break;
				case "button":
					selectButtonByXpath(webPage,softAssert, FieldData[i][0], FieldData[i][2]);
					break;
				case "NA":
					//selectButtonByXpath(webPage,softAssert, FieldData[i][0], FieldData[i][2]);
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
	public static boolean verifyTextFieldIsEditableByXpath(WebPage webPage,SoftAssert softAssert, String FieldName, String locator,
			String newValue) {
		if (!verifyElementisPresentByXPath(webPage, locator, softAssert)) {
			log.info("TextBox \"" + FieldName + "\" is Not Displayed");
			softAssert.fail(" Text Field \"" + FieldName + "\" is not Displayed ");
			return false;
		} else {
			if (newValue == "" || newValue == null) {
				log.info("Value was passed as blank for textField " + FieldName);
				return true;
			}
			if (commonMethods.getWebElementbyXpath(webPage, locator, softAssert).isEnabled()) {
				log.info("TextBox is enabled");
				log.info("Setting TextBox \"" + FieldName + "\" Value to : " + newValue);
				WebElement textField = commonMethods.getWebElementbyXpath(webPage, locator, softAssert);
				JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
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
	public static boolean verifyElementisPresentByXPath(WebPage webPage, String locator, SoftAssert softAssert) {
		Boolean isElementPresent = false;
		try {
			log.info("Verifying if element is present by locator - " + locator);
			isElementPresent = webPage.findObjectByxPath(locator).isDisplayed();
		} catch (Throwable e) {
		}
		return isElementPresent;
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
	public static void verifyDropDownFieldIsEditableById(WebPage webPage,SoftAssert softAssert, String FieldName, String locator,
			String newValue) {
		if (commonMethods.getWebElementbyID(webPage, locator, softAssert).isEnabled()) {
			log.info("DropDown is enabled");
			log.info("Setting DropDown Value to : " + newValue);
			Select select = new Select(commonMethods.getWebElementbyID(webPage, locator, softAssert));
			// select.selectByVisibleText(newValue.trim());
			selectValueFromDropDownByID(webPage,softAssert, FieldName, locator, newValue);
			String actual = select.getFirstSelectedOption().getText().trim();
			softAssert.assertTrue(actual.equals(newValue),
					"Failed to Update Drop Down Value, New Value : " + newValue + " Existing Value : " + actual);
		} else {
			log.info("DropDown is Disabled");
			softAssert.fail(" DropDown Field " + FieldName + " is disabled ");
		}
	}
	public static void selectValueFromDropDownByID(WebPage webPage,SoftAssert softAssert, String FieldName, String locator,
			String newValue) {
		log.info("Setting DropDown \"" + FieldName + "\" Value to : " + newValue);
		Select select = new Select(commonMethods.getWebElementbyID(webPage, locator, softAssert));
		select.selectByVisibleText(newValue.trim());
	}
	public static void selectValueFromDropDownByXpath(WebPage webPage,SoftAssert softAssert, String FieldName, String locator,
			String newValue) {
		log.info("Setting DropDown \"" + FieldName + "\" Value to : " + newValue);
		Select select = new Select(commonMethods.getWebElementbyXpath(webPage, locator, softAssert));
		select.selectByVisibleText(newValue.trim());
		String actual = select.getFirstSelectedOption().getText().trim();
		softAssert.assertTrue(actual.equals(newValue),
				"Failed to Update Drop Down Value, New Value : " + newValue + " Existing Value : " + actual);
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
	public static void verifyDropDownFieldIsEditableByXpath(WebPage webPage,SoftAssert softAssert, String FieldName, String locator,
			String newValue) {
		if (commonMethods.getWebElementbyXpath(webPage, locator, softAssert).isEnabled()) {
			log.info("DropDown is enabled");
			log.info("Setting DropDown Value to : " + newValue);
			selectValueFromDropDownByXpath(webPage,softAssert, FieldName, locator, newValue);
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
	public static void selectRadioButtonByXpath(WebPage webPage,SoftAssert softAssert, String FieldName, String locator) {
		if (commonMethods.getWebElementbyXpath(webPage, locator, softAssert).isEnabled()) {
			log.info("Selecting Radio button : \"" + FieldName + "\"");
			commonMethods.clickElementbyXpath(webPage, locator, softAssert);
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
	public static void selectCheckBoxByXpath(WebPage webPage,SoftAssert softAssert, String FieldName, String locator) {
		if (commonMethods.getWebElementbyXpath(webPage, locator, softAssert).isEnabled()) {
			log.info("Selecting CheckBox : \"" + FieldName + "\"");
			commonMethods.clickElementbyXpath(webPage, locator, softAssert);
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
	public static void selectButtonByXpath(WebPage webPage,SoftAssert softAssert, String FieldName, String locator) {
		if (commonMethods.getWebElementbyXpath(webPage, locator, softAssert).isEnabled()) {
			log.info("Clicking on Button : \"" + FieldName + "\"");
			commonMethods.clickElementbyXpath(webPage, locator, softAssert);
		} else {
			log.info("Button is Disabled");
			softAssert.fail(" Button Field " + FieldName + " is disabled ");
		}
	}
	public static void navigateToCreditAppPage(LinkedHashMap<String, String> commonData, WebPage webPage,SoftAssert softAssert) throws Exception {
		if (CreditAppUrl == null) {
			// webPage.navigateToUrl(url);
			// log.info("Navigating to Home Page");
			log.info("Navigating to Credit App Page");
			commonMethods.clickElementbyXpath(webPage, commonData.get("YesMoneyGetCreditTodayApplyNowLink"),
					softAssert);
			commonMethods.waitForPageLoad(webPage, softAssert);
			commonMethods.clickElementbyXpath(webPage, commonData.get("ApplyForMyYesMoney"), softAssert);
			commonMethods.waitForPageLoad(webPage, softAssert);
		//	verifyPageTitle(commonData.get("CreditAppPageTitle"), softAssert);
			log.info("Navigated to Credit App Page");
			CreditAppUrl = webPage.getCurrentUrl();
		} else {
			webPage.navigateToUrl(CreditAppUrl);
			commonMethods.waitForPageLoad(webPage, softAssert);
			//verifyPageTitle(commonData.get("CreditAppPageTitle"), softAssert);
		}
		
	}
	public static void selectValueWithGivenDate(WebPage webPage, String mmXpath, String ddXpath,
			String yyyyXpath, String date) throws Exception {
		commonMethods.selectDropdownByValue(webPage, yyyyXpath,
				date.substring(6, 10));
		commonMethods.selectDropdownByValue(webPage, mmXpath,
				date.substring(0, 2));
		commonMethods.selectDropdownByValue(webPage, ddXpath,
				date.substring(3, 5));
		Thread.sleep(2000);
	}
	public static void verifyErrorMessageByXpath(WebPage webPage,SoftAssert softAssert, String errorMessageFieldName, String locator,
			String expectedErrorMessage) throws PageException {
		log.info("Verifiyikng error message for : " + errorMessageFieldName + " : Expected Message : "
				+ expectedErrorMessage);
		CommonMethods.waitForWebElement(By.xpath(locator), webPage);
		String actualErrorMessage = commonMethods.getTextbyXpath(webPage, locator, softAssert);
		softAssert.assertTrue(expectedErrorMessage.equals(actualErrorMessage), "Failed to verify error field :"
				+ errorMessageFieldName + " : Expected : " + expectedErrorMessage + " Actual : " + actualErrorMessage);
	}
	public static void selectSpecificValuesWithGivenDate(WebPage webPage,
			String YesLeaseData[][], String hireDate, String lastPayDate,
			String nextPayDate, String accountOpenedDate) throws Exception {
		// Hire Date
		YesLeasePage.selectValueWithGivenDate(webPage,YesLeaseData[10][1],
				YesLeaseData[11][1], YesLeaseData[12][1], hireDate);
		// Last Pay Date
		YesLeasePage.selectValueWithGivenDate(webPage,YesLeaseData[14][1],
				YesLeaseData[15][1], YesLeaseData[16][1], lastPayDate);
		// Next Pay Date
		YesLeasePage.selectValueWithGivenDate(webPage,YesLeaseData[17][1],
				YesLeaseData[18][1], YesLeaseData[19][1], nextPayDate);
		// Account Opened Date
		YesLeasePage.selectValueWithGivenDate(webPage,YesLeaseData[20][1],
				YesLeaseData[21][1], YesLeaseData[22][1], accountOpenedDate);
		Thread.sleep(2000);
	}

	public static void submitYesLeaseWithValidData(WebPage webPage,String YesLeaseData[][],
			SoftAssert softAssert) throws Exception {
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
		commonMethods.selectDropdownByValue(webPage, YesLeaseData[13][1],
				YesLeaseData[39][1]);

		selectSpecificValuesWithGivenDate(webPage,YesLeaseData, yesterdays_Date,
				yesterdays_Date, futureDate_1month, yesterdays_Date);
		YesLeasePage.enterAccountSpecificDetails(webPage, YesLeaseData,3);
	
		commonMethods.clickElementbyXpath(webPage, YesLeaseData[24][1],
				softAssert);
		commonMethods.clickElementbyXpath(webPage, YesLeaseData[54][1],
				softAssert);
		commonMethods.clickElementbyXpath(webPage,
				YesLeaseData[55][1], softAssert);
		commonMethods.clickElementbyXpath(webPage,
				YesLeaseData[56][1], softAssert);
		Thread.sleep(8000);
		commonMethods.waitForPageLoad(webPage, softAssert);
	}
	public static void enterAccountSpecificDetails(WebPage webPage,String YesLeaseData[][], int n) throws PageException
	{
		String[] CardNumberList=YesLeaseData[27][1].split(". ");
		String[] routingNumberList=YesLeaseData[31][1].split(". ");
		String[] accountNumberList=YesLeaseData[36][1].split(". ");
		ITafElement CardNumberField = webPage.findObjectByxPath(YesLeaseData[26][1]);
		ITafElement routingNumberField = webPage.findObjectByxPath(YesLeaseData[30][1]);
		ITafElement accountNumberField = webPage.findObjectByxPath(YesLeaseData[35][1]);
		CardNumberField.sendKeys(CardNumberList[n]);
		routingNumberField.sendKeys(routingNumberList[n]);
		accountNumberField.sendKeys(accountNumberList[n]);
		/*CardNumberField.sendKeys("412345");
		routingNumberField.sendKeys("021000128");
		accountNumberField.sendKeys("21000128");*/
		//accountNumberField.sendKeys(Keys.TAB);	
		
	}
	
	public static String getExpectedDate(String value) throws Exception {
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
}
