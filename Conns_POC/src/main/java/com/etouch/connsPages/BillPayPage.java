package com.etouch.connsPages;

import java.util.List;

import org.apache.commons.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import org.testng.asserts.SoftAssert;

import com.etouch.common.CommonMethods;
import com.etouch.connsTests.Conns_BillPay_Page_SetUp_AutoPay;

import com.etouch.taf.core.exception.PageException;

import com.etouch.taf.util.LogUtil;

import com.etouch.taf.webui.selenium.WebPage;

public class BillPayPage extends Conns_BillPay_Page_SetUp_AutoPay {
	static Log log = LogUtil.getLog(BillPayPage.class);
	String payBillUrl = null;
	// CommonMethods commonMethods = new CommonMethods();
	static CommonMethods commonMethods = new CommonMethods();
	static WebPage webPage;

	BillPayPage billPage ;/*new CreditAppPage() ;*/

	/**
	 * Verify PayYourBill abd Account page Verbiage
	 * 
	 * @param softAssert
	 * @throws Exception
	 */
	public void verifyVerbiageOfPayYourBillAndAccountPages(SoftAssert softAssert) throws Exception {
		// Verify Pay your bill page title
		softAssert.assertEquals(webPageMap.get(Thread.currentThread().getId()).getPageTitle(),
				commonData.get("payyourbillpagetitle"),
				"Pay your bill page title . Expected " + commonData.get("payyourbillpagetitle") + " Actual : "
						+ webPageMap.get(Thread.currentThread().getId()).getPageTitle());
		// Verify pay your bills and Credit account Verbiage Text
		String payYourBillText = commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()),
				commonData.get("payYourBillOnlineXpath"), softAssert);
		String creditActText = commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()),
				commonData.get("creditAccountXpath"), softAssert);

		softAssert.assertEquals(payYourBillText, commonData.get("payYourBillOnlineText"),
				"Pay YourBill Online Text . Expected " + commonData.get("payYourBillOnlineText") + " Actual : "
						+ payYourBillText);

		softAssert.assertEquals(creditActText, commonData.get("creditAccountText"),
				"Credit Account Text . Expected " + commonData.get("creditAccountText") + " Actual : " + creditActText);
		// Click on Pay Bill button
		commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()),
				commonData.get("payBillButtonXpath"), softAssert);

		// Verify New Customer and Register Customers Verbiage text
		String customersText = commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()),
				commonData.get("newCustomersXpath"), softAssert);
		String regCustomersText = commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()),
				commonData.get("registeredCustomersXpath"), softAssert);

		softAssert.assertEquals(customersText, commonData.get("newCustomersText"),
				"New Customers Text . Expected " + commonData.get("newCustomersText") + " Actual : " + customersText);

		softAssert.assertEquals(regCustomersText, commonData.get("registeredCustomersText"),
				"Registered Customers Text . Expected " + commonData.get("registeredCustomersText") + " Actual : "
						+ regCustomersText);

	}

	/**
	 * Conns login functionality
	 * 
	 * @param email
	 * @param password
	 * @param webPage
	 * @param softAssert
	 * @throws Exception
	 */
	public void connsLogin(String email, String password, WebPage webPage, SoftAssert softAssert) throws Exception {
		commonMethods.sendKeysById(webPage, commonData.get("emailId"), email, softAssert);
		commonMethods.sendKeysById(webPage, commonData.get("passwordId"), password, softAssert);
		commonMethods.clickElementById(webPageMap.get(Thread.currentThread().getId()), commonData.get("loginButtonId"),
				softAssert);
		webPage.sleep(4000);
		/*
		 * softAssert.assertEquals(webPageMap.get(Thread.currentThread().getId()).
		 * getPageTitle(),commonData.get("successLoginPageTitle"),
		 * "Success Loign page title . Expected "+commonData.get("successLoginPageTitle"
		 * )+" Actual : "+webPageMap.get(Thread.currentThread().getId()).getPageTitle())
		 * ;
		 */

	}

	/**
	 * author : Asim Singh Verify Register button
	 * 
	 * @param webPage
	 * @param softAssert
	 * @throws InterruptedException
	 */

	public static void PageTitle_PageURL_Validation(WebPage webPage, String[][] inputData, String ExpectedURL,
			String ExpectedPageTitle) throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		try {
			log.info("ExpectedPageTitle :" + ExpectedPageTitle);
			log.info("ExpectedURL :" + ExpectedURL);
			String ActualURL = webPage.getCurrentUrl();
			log.info("ActualURL :" + ActualURL);
			String ActualPageTitle = webPage.getPageTitle();
			log.info("ActualPageTitle :" + ActualPageTitle);
			/******************
			 * Actual vs Expected Validation
			 ************************************/
			softAssert.assertTrue(ActualPageTitle.equals(ExpectedPageTitle));
			softAssert.assertTrue(ActualURL.contains(ExpectedURL));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * author : Asim Singh Verify selectDropdownByValue
	 * 
	 * @param webPage
	 * @param softAssert
	 * @throws InterruptedException
	 */
	public static void selectDropdownByValue(WebPage webPage, String locator, String dropdownValue,
			SoftAssert softAssert) {
		try {
			commonMethods.selectDropdownByValue(webPage, locator, dropdownValue, softAssert);
			// commonMethods.selectDropdownByText(webPage, locator,dropdownValue,
			// softAssert);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * author : Asim Singh Verify setupAccountRetrievalFunctionality
	 * 
	 * @param webPage
	 * @param softAssert
	 * @throws InterruptedException
	 */
	public static void setupAccountRetrievalFunctionality(WebPage webPage, String[][] inputData,
			SoftAssert softAssert) {
		try {
			log.info("**************setupAccountRetrievalFunctionality starts  : ************************");
			for (int i = 34; i <= 37; i++) {
				commonMethods.waitForPageLoad(webPage, softAssert);
				commonMethods.clickElementbyXpath_usingJavaScript(webPage, inputData[i][1], softAssert);
				commonMethods.waitForPageLoad(webPage, softAssert);
			}

			WebElement SetUpAutoPayButton = webPage.getDriver().findElement(By.xpath(inputData[38][1]));
			if (SetUpAutoPayButton.isDisplayed())
				log.info("**************SetUpAutoPayButton Successfully Found  : ************************");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * author : Asim Singh Verify ClickElementPresenceByJS
	 * 
	 * @param webPage
	 * @param softAssert
	 * @throws InterruptedException
	 */
	public static void ClickElementPresenceByJS(WebPage webPage, String ElementXpath, SoftAssert softAssert) {
		JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
		try {
			log.info("**************ClickElementPresenceByJS starts  : ************************");
			WebElement PageElement = webPage.getDriver().findElement(By.xpath(ElementXpath));
			if (PageElement.isDisplayed())
				log.info("PageElement Successfully Found  : ");
			commonMethods.scrollToElement(webPage, ElementXpath, softAssert);
			js.executeScript("arguments[0].click();", PageElement);
			Thread.sleep(2000);
			log.info("**************PageElement Successfully Found & Clicked  : ************************");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * author : Asim Singh Verify AutoPayButtonHandling
	 * 
	 * @param webPage
	 * @param softAssert
	 * @throws InterruptedException
	 */
	/*public static void AutoPayButtonHandling(WebPage webPage, String ElementXpath, String[][] dataInput,
			SoftAssert softAssert) {
		try {
			log.info("************** Finding SetUpAutoPayButton   : ************************");
			if (webPage.getDriver().findElement(By.xpath(commonData.get("setupAutoPayButton"))).isDisplayed()) {
				log.info("**************setupAutoPayButton Successfully  Found  : ************************");
				commonMethods.clickElementbyXpath_usingJavaScript(webPageMap.get(Thread.currentThread().getId()),
						commonData.get("setupAutoPayButton"), softAssert);
			}
		} catch (Exception e) {
			if (webPage.getDriver().findElement(By.xpath(dataInput[34][1])).isDisplayed()) {
				log.info("**************ManageAutoPayButton Successfully  Found  : ************************");
				BillPayPage.setupAccountRetrievalFunctionality(webPage, dataInput, softAssert);
				commonMethods.clickElementbyXpath_usingJavaScript(webPageMap.get(Thread.currentThread().getId()),
						commonData.get("setupAutoPayButton"), softAssert);
			}
			e.printStackTrace();
		}
	}*/
	
	public static void AutoPayButtonHandling(WebPage webPage, String ElementXpath, String[][] dataInput,SoftAssert softAssert) {
		try {
			log.info("************** Finding SetUpAutoPayButton   : ************************");	
	boolean isSetupAutoPayDisplayed=CommonMethods.verifyElementisPresent(webPage,commonData.get("setupAutoPayButton"));
	   log.info("setup auto pay button displayed:" +isSetupAutoPayDisplayed);
	   
	   if(isSetupAutoPayDisplayed) {
	   log.info("**************ManageAutoPayButton Successfully  Found  : ************************");    
	    commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("setupAutoPayButton"),softAssert);
	   }else {
	    log.info("*************  AutoPay is setup, Cancelling AutoPay now   : ***********************");
	   //Click on Set Up Auto Pay Button
	   BillPayPage.setupAccountRetrievalFunctionality(webPage, dataInput, softAssert);
	   commonMethods.clickElementbyXpath(webPageMap.get(Thread.currentThread().getId()), commonData.get("setupAutoPayButton"),softAssert);
	   }
		}
	   catch (Exception e) {
		   e.printStackTrace();
			}
			
		}
	

	/**
	 * author : Asim Singh Verify RadioButtonValidation
	 * 
	 * @param webPage
	 * @param softAssert
	 * @throws InterruptedException
	 */
	public static void RadioButtonValidation(WebPage webPage, String[][] dataInput, SoftAssert softAssert) {
		try {
			log.info("************** Finding SetUpAutoPayButton   : ************************");
			for (int i = 10; i <= 12; i++) {
				String ActualDefaultRadioButtonStatus = webPage.getDriver().findElement(By.xpath(dataInput[i][1]))
						.getAttribute("checked");
				softAssert.assertEquals(ActualDefaultRadioButtonStatus, "true");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * author : Asim Singh Verify ErroMessageValidation
	 * 
	 * @param webPage
	 * @param softAssert
	 * @throws InterruptedException
	 */
	public static void ErroMessageValidation(WebPage webPage, String ActualCancelButtonErrorMessage,
			String CancelButtonErrorMessageXpath, String ExpectedCancelButtonErrorMessage, SoftAssert softAssert) {
		try {
			log.info("************** ErroMessageValidation starts  : ************************");
			WebElement PageElementErrorMessage = webPage.getDriver()
					.findElement(By.xpath(CancelButtonErrorMessageXpath));
			ActualCancelButtonErrorMessage = PageElementErrorMessage.getText();
			if (PageElementErrorMessage.isDisplayed())
				softAssert.assertTrue(ActualCancelButtonErrorMessage.equals(ExpectedCancelButtonErrorMessage));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * author : Asim Singh Verify currentBalanceAmount
	 * 
	 * @param webPage
	 * @param softAssert
	 * @throws InterruptedException
	 */
	public static int currentBalanceAmount(WebPage webPage, String AutoPayCurrentBalanceXpath, String currentBalance,
			String mychar, SoftAssert softAssert) {
		int currentBalanceAmount = 0;
		try {
			log.info(" currentBalanceAmount : " + currentBalanceAmount);
			currentBalance = webPage.getDriver().findElement(By.xpath(AutoPayCurrentBalanceXpath)).getText();
			mychar = currentBalance.substring(18, 24);
			log.info(" mychar : " + mychar);
			double d = Double.parseDouble(mychar);
			currentBalanceAmount = (int) d;
			log.info(" currentBalanceAmount : " + d);
			log.info(" currentBalanceAmount : " + currentBalanceAmount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currentBalanceAmount;
	}

	
	/**
	 * author : Asim Singh Verify currentBalanceAmount
	 * 
	 * @param webPage
	 * @param softAssert
	 * @throws InterruptedException
	 */
	public static double ExactCurrentBalanceAmount(WebPage webPage, String AutoPayCurrentBalanceXpath, String currentBalance,
			String mychar, SoftAssert softAssert) {
		int currentBalanceAmount = 0;
		double d = 0.0;
		try {
			log.info(" currentBalanceAmount : " + currentBalanceAmount);
			currentBalance = webPage.getDriver().findElement(By.xpath(AutoPayCurrentBalanceXpath)).getText();
			mychar = currentBalance.substring(18, 24);
			log.info(" mychar : " + mychar);
			d = Double.parseDouble(mychar);
			currentBalanceAmount = (int) d;
			log.info(" currentBalanceAmount : " + d);
			log.info(" currentBalanceAmount : " + currentBalanceAmount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}
	/**
	 * author : Asim Singh Verify currentBalanceAmount
	 * 
	 * @param webPage
	 * @param softAssert
	 * @throws InterruptedException
	 */

	public static int minDueAmount(WebPage webPage, String MinimumAmountDueXpath, String minAmountDue,
			String minAmountchar, SoftAssert softAssert) {
		int minDueAmount = 0;
		try {
			minAmountDue = webPage.getDriver().findElement(By.xpath(MinimumAmountDueXpath)).getText();
			minAmountchar = minAmountDue.substring(18, 23);
			log.info(" minAmountchar : " + minAmountchar);
			double amount = Double.parseDouble(minAmountchar);
			minDueAmount = (int) amount;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return minDueAmount;
	}

	/**
	 * author : Asim Singh Verify SelectAmountToPayRadioButtonClicking
	 * 
	 * @param webPage
	 * @param softAssert
	 * @throws InterruptedException
	 */
	public static void SelectAmountToPayRadioButtonClicking(WebPage webPage, String[][] testInput,
			SoftAssert softAssert) {
		try {
			log.info("************** SelectAmountToPayRadioButtonClicking Starts  : ************************");

				for (int i = 75; i <= 77; i++) {
					commonMethods.waitForPageLoad(webPage, softAssert);
					commonMethods.clickElementbyXpath_usingJavaScript(webPageMap.get(Thread.currentThread().getId()),
							testInput[i][1], softAssert);
					commonMethods.waitForPageLoad(webPage, softAssert);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * author : Asim Singh Verify 
	 * 
	 * MinAmountPositiveScenario
	 * 
	 * @param webPage
	 * @param softAssert
	 * @throws InterruptedException
	 */
	public static void MinAmountPositiveScenario(WebPage webPage, String[][] testInput, int minDueAmount,
			SoftAssert softAssert) {
		try {
			log.info("********  MinAmountPositiveScenario Starts  : ***************");
				for (int i = 69; i < 70; i++) {
					commonMethods.sendKeysbyXpath(webPage, testInput[i][1], "" + (minDueAmount + 0.55), softAssert);
					commonMethods.sendKeysbyXpath(webPage, testInput[i][1], "" + Keys.TAB, softAssert);
				}
				log.info("************ Minimum Amount Positive Scenario Over********");
				commonMethods.clickElementbyXpath_usingJavaScript(webPage, testInput[71][1], softAssert);
				commonMethods.clickElementbyXpath_usingJavaScript(webPage, testInput[79][1], softAssert);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * author : Asim Singh Verify currentBalanceAmountPositiveScenario
	 * 
	 * @param webPage
	 * @param softAssert
	 * @throws InterruptedException
	 */
	public static void currentBalanceAmountPositiveScenario(WebPage webPage, String[][] testInput,
			int currentBalanceAmount, SoftAssert softAssert) {
		try {
			log.info("************** currentBalanceAmountPositiveScenario Starts  : ************************");
				for (int i = 69; i < 70; i++) {
					commonMethods.sendKeysbyXpath(webPage, testInput[i][1], "" + (currentBalanceAmount - 0.55),
							softAssert);
					commonMethods.sendKeysbyXpath(webPage, testInput[i][1], "" + Keys.TAB, softAssert);
				}
				log.info(
						"************************************ Current Balance Amount Positive Scenario Over*************************************");
				commonMethods.clickElementbyXpath_usingJavaScript(webPage, testInput[78][1], softAssert);
				commonMethods.clickElementbyXpath_usingJavaScript(webPage, testInput[71][1], softAssert);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * author : Asim Singh Verify PopUpBoxTextValidation
	 * 
	 * @param webPage
	 * @param softAssert
	 * @throws InterruptedException
	 */
	public static void PopUpBoxTextValidation(WebPage webPage, String[][] dataInput, String[][] testInput,
			SoftAssert softAssert) {
		try {
			log.info("************** PopUpBoxTextValidation Scenario Starts  : ************************");
				for (int i = 12; i < 32; ++i) {
					log.info("*******Iteration value of i *********** : " + i);
					softAssert.assertTrue(
							commonMethods.getTextbyXpath(webPage, dataInput[i][1], softAssert)
									.contains(dataInput[i + 1][1]),
							"Expected Text from Dialogue box . Expected " + dataInput[i + 1][1] + " Actual : "
									+ commonMethods.getTextbyXpath(webPage, dataInput[i][1], softAssert));
					i = i + 1;
				}
				commonMethods.clickElementbyXpath_usingJavaScript(webPage, dataInput[32][1], softAssert);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * author : Asim Singh Verify BlankInputValidation
	 * 
	 * @param webPage
	 * @param softAssert
	 * @throws InterruptedException
	 */
	public static void BlankInputValidation(WebPage webPage, String SelectOtherAmountToPayRadioButtonXPath,
			String CancelButtonErrorMessageXpath, String[][] dataInput, SoftAssert softAssert) {
		try {
			log.info("************** BlankInputValidation starts  : ************************");
			commonMethods.waitForPageLoad(webPage, softAssert);
			commonMethods.clickElementbyXpath_usingJavaScript(webPage, SelectOtherAmountToPayRadioButtonXPath,
					softAssert);
			commonMethods.waitForPageLoad(webPage, softAssert);
			commonMethods.clickElementbyXpath_usingJavaScript(webPage, CancelButtonErrorMessageXpath, softAssert);
			softAssert.assertEquals(commonMethods.getTextbyXpath(webPage, dataInput[3][1], softAssert), dataInput[4][1],
					"Payment Date radio button Error Text . Expected " + dataInput[4][1] + " Actual : "
							+ commonMethods.getTextbyXpath(webPage, dataInput[3][1], softAssert));
			commonMethods.waitForPageLoad(webPage, softAssert);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * author : Asim Singh Verify MinimumAmountToPayValidation
	 * 
	 * @param webPage
	 * @param softAssert
	 * @throws InterruptedException
	 */
	public static void MinimumAmountToPayValidation(WebPage webPage, String SelectOtherAmountToPayTextBox,
			String AutoPaySubmitButtonXPath, String[][] dataInput, int minDueAmount, SoftAssert softAssert) {
		try {
			commonMethods.sendKeysbyXpath(webPage, SelectOtherAmountToPayTextBox, "" + (minDueAmount), softAssert);
			commonMethods.sendKeysbyXpath(webPage, AutoPaySubmitButtonXPath, "" + Keys.TAB, softAssert);
			log.info("**** Actual MinimumAmountToPayValidation : "+commonMethods.getTextbyXpath(webPage, dataInput[3][1],softAssert));
			log.info("**** Expected  MinimumAmountToPayValidation : "+commonMethods.getTextbyXpath(webPage, dataInput[3][1],softAssert));
			softAssert.assertEquals(commonMethods.getTextbyXpath(webPage, dataInput[3][1], softAssert), dataInput[4][1],
					"Value less than  minimum amount or payment due . Expected : " + dataInput[4][1] + " Actual : "
							+ commonMethods.getTextbyXpath(webPage, dataInput[3][1], softAssert));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * author : Asim Singh Verify CurrentAmountToPayValidation
	 * 
	 * @param webPage
	 * @param softAssert
	 * @throws InterruptedException
	 */
	public static void CurrentAmountToPayValidation(WebPage webPage, String SelectOtherAmountToPayTextBox,
			String AutoPaySubmitButtonXPath, String[][] dataInput, int currentBalanceAmount, SoftAssert softAssert) {
		try {
			/*commonMethods.sendKeysbyXpath(webPage, SelectOtherAmountToPayTextBox, "" + currentBalanceAmount,
					softAssert);
			commonMethods.sendKeysbyXpath(webPage, commonData.get("SelectOtherAmountToPayTextBox"), "" + Keys.TAB,
					softAssert);
			softAssert.assertEquals(commonMethods.getTextbyXpath(webPage, dataInput[3][1], softAssert), dataInput[4][1],
					"Value less than  minimum amount or payment due . Expected " + dataInput[4][1] + " Actual : "
							+ commonMethods.getTextbyXpath(webPage, dataInput[3][1], softAssert));*/			
			
			commonMethods.sendKeysbyXpath(webPage, SelectOtherAmountToPayTextBox, "" + (currentBalanceAmount), softAssert);
			commonMethods.sendKeysbyXpath(webPage, AutoPaySubmitButtonXPath, "" + Keys.TAB, softAssert);
			commonMethods.waitForPageLoad(webPage, softAssert);
			log.info("**** Actual currentBalance *****  : " + commonMethods.getTextbyXpath(webPage, dataInput[3][1], softAssert));
			log.info("**** Expected currentBalance *****  : " + dataInput[4][1]);

			softAssert.assertEquals(commonMethods.getTextbyXpath(webPage, dataInput[3][1], softAssert), dataInput[4][1],
					"Value less than  minimum amount or payment due . Expected : " + dataInput[4][1] + " Actual : "
							+ commonMethods.getTextbyXpath(webPage, dataInput[3][1], softAssert));
			commonMethods.waitForPageLoad(webPage, softAssert);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * author : Asim Singh 
	 * 
	 * Verify ExactCurrentAmountToPayValidation
	 * 
	 * @param webPage
	 * @param softAssert
	 * @throws InterruptedException
	 */
	public static void ExactCurrentAmountToPayValidation(WebPage webPage, String SelectOtherAmountToPayTextBox,
			String AutoPaySubmitButtonXPath, String[][] dataInput, double currentBalanceAmount, SoftAssert softAssert) {
		try {
			/*commonMethods.sendKeysbyXpath(webPage, SelectOtherAmountToPayTextBox, "" + currentBalanceAmount,
					softAssert);
			commonMethods.sendKeysbyXpath(webPage, commonData.get("SelectOtherAmountToPayTextBox"), "" + Keys.TAB,
					softAssert);
			softAssert.assertEquals(commonMethods.getTextbyXpath(webPage, dataInput[3][1], softAssert), dataInput[4][1],
					"Value less than  minimum amount or payment due . Expected " + dataInput[4][1] + " Actual : "
							+ commonMethods.getTextbyXpath(webPage, dataInput[3][1], softAssert));*/			
			
			commonMethods.sendKeysbyXpath(webPage, SelectOtherAmountToPayTextBox, "" + (currentBalanceAmount), softAssert);
			commonMethods.sendKeysbyXpath(webPage, AutoPaySubmitButtonXPath, "" + Keys.TAB, softAssert);
			commonMethods.waitForPageLoad(webPage, softAssert);
			log.info("**** Actual currentBalance *****  : " + commonMethods.getTextbyXpath(webPage, dataInput[3][1], softAssert));
			log.info("**** Expected currentBalance *****  : " + dataInput[4][1]);

			softAssert.assertEquals(commonMethods.getTextbyXpath(webPage, dataInput[3][1], softAssert), dataInput[4][1],
					"Value less than  minimum amount or payment due . Expected : " + dataInput[4][1] + " Actual : "
							+ commonMethods.getTextbyXpath(webPage, dataInput[3][1], softAssert));
			commonMethods.waitForPageLoad(webPage, softAssert);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * author : Asim Singh Verify DisclaimerTextValidation
	 * 
	 * @param webPage
	 * @param softAssert
	 * @throws InterruptedException
	 */
	public static void DisclaimerTextValidation(WebPage webPage, String disclaimersText, String disclaimersRemoveText,
			String AutoPayDisclaimerTextXPath, String SelectOtherAmountToPayTextBox, int minDueAmount,
			SoftAssert softAssert) {
		try {
			commonMethods.waitForPageLoad(webPage, softAssert);
			disclaimersText = commonMethods.getTextbyXpath(webPage, AutoPayDisclaimerTextXPath, softAssert);
			disclaimersRemoveText = disclaimersText.substring(disclaimersText.indexOf("$"),
					disclaimersText.indexOf("Further") - 1);
			disclaimersText = disclaimersText.replace(disclaimersRemoveText, "");
			log.info("***** Actual disclaimersText minDueAmount : "+disclaimersText);
			log.info("***** Actual DisclaimerTextValidation minDueAmount : "+disclaimersRemoveText);
			log.info("***** Expected DisclaimerTextValidation minDueAmount : "+minDueAmount);

			softAssert.assertTrue(disclaimersRemoveText.contains("" + (minDueAmount)),
					"Disclaimers Text . Expected :" + (minDueAmount) + " Actual : " + disclaimersRemoveText);
			commonMethods.waitForPageLoad(webPage, softAssert);
			commonMethods.clearTextBoxByXpath(webPage, SelectOtherAmountToPayTextBox, softAssert);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * author : Asim Singh 
	 * 
	 * Verify ExactDisclaimerTextValidation
	 * 
	 * @param webPage
	 * @param softAssert
	 * @throws InterruptedException
	 */
	public static void ExactDisclaimerTextValidation(WebPage webPage, String disclaimersText, String disclaimersRemoveText,
			String AutoPayDisclaimerTextXPath, String SelectOtherAmountToPayTextBox, double ExactCurrentBalanceAmount,
			SoftAssert softAssert) {
		try {
			commonMethods.waitForPageLoad(webPage, softAssert);
			disclaimersText = commonMethods.getTextbyXpath(webPage, AutoPayDisclaimerTextXPath, softAssert);
			disclaimersRemoveText = disclaimersText.substring(disclaimersText.indexOf("$"),
					disclaimersText.indexOf("Further") - 1);
			disclaimersText = disclaimersText.replace(disclaimersRemoveText, "");
			log.info("***** Actual disclaimersText minDueAmount : "+disclaimersText);
			log.info("***** Actual DisclaimerTextValidation minDueAmount : "+disclaimersRemoveText);
			log.info("***** Expected DisclaimerTextValidation minDueAmount : "+ExactCurrentBalanceAmount);

			softAssert.assertTrue(disclaimersRemoveText.contains("" + (ExactCurrentBalanceAmount)),
					"Disclaimers Text . Expected :" + (ExactCurrentBalanceAmount) + " Actual : " + disclaimersRemoveText);
			commonMethods.waitForPageLoad(webPage, softAssert);
			commonMethods.clearTextBoxByXpath(webPage, SelectOtherAmountToPayTextBox, softAssert);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * author : Asim Singh Verify DisclaimerTextValidation
	 * 
	 * @param webPage
	 * @param softAssert
	 * @throws InterruptedException
	 */
	public static void DisclaimerTextValidationForCurrentBalance(WebPage webPage, String disclaimersText, String disclaimersRemoveText,
			String AutoPayDisclaimerTextXPath, String SelectOtherAmountToPayTextBox, int currentBalanceAmount,String numberAsString,
			SoftAssert softAssert) {
		try {
			log.info("**** Verify Disclaimers Text & Clear other amount data Text box value ****");
			disclaimersText = commonMethods.getTextbyXpath(webPage, commonData.get("AutoPayDisclaimerText"), softAssert);
			disclaimersRemoveText = disclaimersText.substring(disclaimersText.indexOf("$"), disclaimersText.indexOf("Further")-1);
			disclaimersText = disclaimersText.replace(disclaimersRemoveText, "");
			numberAsString = String.valueOf(currentBalanceAmount);
			log.info("**** DisclaimerTextValidationForCurrentBalance numberAsString : ****" +numberAsString);
			log.info("**** DisclaimerTextValidationForCurrentBalance disclaimersRemoveText : ****" +disclaimersRemoveText);
			log.info("**** DisclaimerTextValidationForCurrentBalance disclaimersText : ****" +disclaimersText);



			softAssert.assertTrue(disclaimersRemoveText.contains(numberAsString),
					"Disclaimers Text . Expected "+currentBalanceAmount+" Actual : "+disclaimersRemoveText);
			commonMethods.waitForPageLoad(webPage, softAssert);

			commonMethods.clearTextBoxByXpath(webPage, commonData.get("SelectOtherAmountToPayTextBox"), softAssert);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * author : Asim Singh Verify DropDownValueErrorMessageAssertion
	 * 
	 * @param webPage
	 * @param softAssert
	 * @throws InterruptedException
	 */
	public static void DropDownValueErrorMessageAssertion(WebPage webPage, String[][] SelectPaymentDateDropDownData,
			String[][] testData, SoftAssert softAssert) {
		softAssert.assertEquals(commonMethods.getTextbyXpath(webPage, SelectPaymentDateDropDownData[3][1], softAssert),
				SelectPaymentDateDropDownData[4][1],
				"Value less than  minimum amount or payment due . Expected " + SelectPaymentDateDropDownData[4][1] + " Actual : "
						+ commonMethods.getTextbyXpath(webPage, SelectPaymentDateDropDownData[3][1], softAssert));
	}

	/**
	 * author : Asim Singh Verify isDefaultRadioButtonSelected
	 * 
	 * @param webPage
	 * @param softAssert
	 * @throws InterruptedException
	 */
	public static void isDefaultRadioButtonSelected(WebPage webPage, SoftAssert softAssert) {
		softAssert.assertTrue(
				commonMethods.verifyElementisSelected(webPage, commonData.get("shedulePaymntMinPaymntId"), softAssert),
				"Select Minimum Payment Pay Radio button. Expected " + commonMethods.verifyElementisSelected(webPage,
						commonData.get("shedulePaymntMinPaymntId"), softAssert));

	}

	/**
	 * author : Asim Singh Verify FontSizeVerification
	 * 
	 * @param webPage
	 * @param softAssert
	 * @throws InterruptedException
	 */

	public static void FontSizeVerification(WebPage webPage, String testType, String testBedName,
			String[][] ExpectedFontValuesMobile, String[][] ExpectedFontValuesTab, String[][] ExpectedFontValuesWeb,
			SoftAssert softAssert) {
		JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
		try {
			log.info("testType : " + testType);
			log.info("testBedName : " + testBedName);
			int width = ((Long) js.executeScript("return window.innerWidth || document.body.clientWidth")).intValue();
			log.info("width value calculated is :" + width);
			int height = ((Long) js.executeScript("return window.innerHeight || document.body.clientHeight"))
					.intValue();
			log.info("height value calculated is :" + height);
			Dimension dimension = new Dimension(width, height);
			System.out.println("Dimensions" + dimension);
			if ((testType.equalsIgnoreCase("Web"))) {
				log.info("********************TestType for Web started execution***************   : "
						+ testType.toString());
				log.info("********************TestBedName for Web started execution***************   "
						+ testBedName.toString());
				for (int i = 0; i < ExpectedFontValuesWeb.length; i++) {
					log.info("Iteration under test  is : " + i + " :: Item under test is : "
							+ ExpectedFontValuesWeb[i][0]);
					List<String> actualCssValues = commonMethods.getFontProperties(webPage, ExpectedFontValuesWeb[i][1],
							softAssert);
					log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][2]);
					// log.info("Actual_SuccessMessage_Text : " +ExpectedFontValuesWeb[i][0]);

					if (!ExpectedFontValuesWeb[i][2].equalsIgnoreCase("NA")) {
						log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][2]);
						log.info("actualCssValues   : " + actualCssValues.get(0));
						log.info("match status : " + actualCssValues.get(0).contains(ExpectedFontValuesWeb[i][2]));
						softAssert.assertTrue(actualCssValues.get(0).contains(ExpectedFontValuesWeb[i][2]),
								"Iteration : " + i + " --  CSS value verification failed for " + "\""
										+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
										+ ExpectedFontValuesWeb[i][2] + ", actualCssValues : " + actualCssValues.get(0)
										+ "\n");
					}

					if (!ExpectedFontValuesWeb[i][3].equalsIgnoreCase("NA")) {
						log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][3]);
						log.info("actualCssValues   : " + actualCssValues.get(1));
						log.info("match status : " + actualCssValues.get(1).contains(ExpectedFontValuesWeb[i][3]));
						softAssert.assertTrue(actualCssValues.get(1).contains(ExpectedFontValuesWeb[i][3]),
								"Iteration : " + i + " --  CSS value verification failed for " + "\""
										+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
										+ ExpectedFontValuesWeb[i][3] + ", actualCssValues : " + actualCssValues.get(1)
										+ "\n");
					}

					if (!ExpectedFontValuesWeb[i][4].equalsIgnoreCase("NA")) {
						log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][4]);
						log.info("actualCssValues   : " + actualCssValues.get(2));
						log.info("match status : " + actualCssValues.get(2).contains(ExpectedFontValuesWeb[i][4]));
						softAssert.assertTrue(actualCssValues.get(2).contains(ExpectedFontValuesWeb[i][4]),
								"Iteration : " + i + " --  CSS value verification failed for " + "\""
										+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
										+ ExpectedFontValuesWeb[i][4] + ", actualCssValues : " + actualCssValues.get(2)
										+ "\n");
					}

					if (!ExpectedFontValuesWeb[i][5].equalsIgnoreCase("NA")) {
						log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][5]);
						log.info("actualCssValues   : " + actualCssValues.get(3));
						log.info("match status : " + actualCssValues.get(3).contains(ExpectedFontValuesWeb[i][5]));
						softAssert.assertTrue(actualCssValues.get(3).contains(ExpectedFontValuesWeb[i][5]),
								"Iteration : " + i + " --  CSS value verification failed for " + "\""
										+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
										+ ExpectedFontValuesWeb[i][5] + ", actualCssValues : " + actualCssValues.get(3)
										+ "\n");
					}

					if (!ExpectedFontValuesWeb[i][6].equalsIgnoreCase("NA")) {
						log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][6]);
						log.info("actualCssValues   : " + actualCssValues.get(4));
						log.info("match status : " + actualCssValues.get(4).contains(ExpectedFontValuesWeb[i][6]));
						softAssert.assertTrue(actualCssValues.get(4).contains(ExpectedFontValuesWeb[i][6]),
								"Iteration : " + i + " --  CSS value verification failed for " + "\""
										+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
										+ ExpectedFontValuesWeb[i][6] + ", actualCssValues : " + actualCssValues.get(4)
										+ "\n");
					}

					if (!ExpectedFontValuesWeb[i][7].equalsIgnoreCase("NA")) {
						log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][7]);
						log.info("actualCssValues   : " + actualCssValues.get(5));
						log.info("match status : " + actualCssValues.get(5).contains(ExpectedFontValuesWeb[i][7]));
						softAssert.assertTrue(actualCssValues.get(5).contains(ExpectedFontValuesWeb[i][7]),
								"Iteration : " + i + " --  CSS value verification failed for " + "\""
										+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
										+ ExpectedFontValuesWeb[i][7] + ", actualCssValues : " + actualCssValues.get(5)
										+ "\n");
					}

					if (!ExpectedFontValuesWeb[i][8].equalsIgnoreCase("NA")) {
						log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][8]);
						log.info("actualCssValues   : " + actualCssValues.get(6));
						log.info("match status : " + actualCssValues.get(6).contains(ExpectedFontValuesWeb[i][8]));
						softAssert.assertTrue(actualCssValues.get(6).contains(ExpectedFontValuesWeb[i][8]),
								"Iteration : " + i + " --  CSS value verification failed for " + "\""
										+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
										+ ExpectedFontValuesWeb[i][8] + ", actualCssValues : " + actualCssValues.get(6)
										+ "\n");
					}

				}
			}

			/********************************************************************************************************************************************************/

			if (!(testType.equalsIgnoreCase("Web")) && testBedName.equalsIgnoreCase("edge")) {
				log.info("********************TestType for Edge started execution***************   : "
						+ testType.toString());
				for (int i = 0; i < ExpectedFontValuesWeb.length; i++) {
					System.out.println("Iteration under test  is : " + i + " :: Item under test is : "
							+ ExpectedFontValuesWeb[i][0]);
					List<String> actualCssValues = commonMethods.getFontProperties(webPage, ExpectedFontValuesWeb[i][1],
							softAssert);

					if (!ExpectedFontValuesWeb[i][2].equalsIgnoreCase("NA")) {
						log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][2]);
						log.info("actualCssValues   : " + actualCssValues.get(0));
						log.info("match status : " + actualCssValues.get(0).contains(ExpectedFontValuesWeb[i][2]));
						softAssert.assertTrue(actualCssValues.get(0).contains(ExpectedFontValuesWeb[i][2]),
								"Iteration : " + i + " --  CSS value verification failed for " + "\""
										+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
										+ ExpectedFontValuesWeb[i][2] + ", actualCssValues : " + actualCssValues.get(0)
										+ "\n");
					}

					if (!ExpectedFontValuesWeb[i][3].equalsIgnoreCase("NA")) {
						log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][3]);
						log.info("actualCssValues   : " + actualCssValues.get(1));
						log.info("match status : " + actualCssValues.get(1).contains(ExpectedFontValuesWeb[i][3]));
						softAssert.assertTrue(actualCssValues.get(1).contains(ExpectedFontValuesWeb[i][3]),
								"Iteration : " + i + " --  CSS value verification failed for " + "\""
										+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
										+ ExpectedFontValuesWeb[i][3] + ", actualCssValues : " + actualCssValues.get(1)
										+ "\n");
					}

					if (!ExpectedFontValuesWeb[i][4].equalsIgnoreCase("NA")) {
						log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][4]);
						log.info("actualCssValues   : " + actualCssValues.get(2));
						log.info("match status : " + actualCssValues.get(2).contains(ExpectedFontValuesWeb[i][4]));
						softAssert.assertTrue(actualCssValues.get(2).contains(ExpectedFontValuesWeb[i][4]),
								"Iteration : " + i + " --  CSS value verification failed for " + "\""
										+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
										+ ExpectedFontValuesWeb[i][4] + ", actualCssValues : " + actualCssValues.get(2)
										+ "\n");
					}

					if (!ExpectedFontValuesWeb[i][5].equalsIgnoreCase("NA")) {
						log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][5]);
						log.info("actualCssValues   : " + actualCssValues.get(3));
						log.info("match status : " + actualCssValues.get(3).contains(ExpectedFontValuesWeb[i][5]));
						softAssert.assertTrue(actualCssValues.get(3).contains(ExpectedFontValuesWeb[i][5]),
								"Iteration : " + i + " --  CSS value verification failed for " + "\""
										+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
										+ ExpectedFontValuesWeb[i][5] + ", actualCssValues : " + actualCssValues.get(3)
										+ "\n");
					}

					if (!ExpectedFontValuesWeb[i][6].equalsIgnoreCase("NA")) {
						log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][6]);
						log.info("actualCssValues   : " + actualCssValues.get(4));
						log.info("match status : " + actualCssValues.get(4).contains(ExpectedFontValuesWeb[i][6]));
						softAssert.assertTrue(actualCssValues.get(4).contains(ExpectedFontValuesWeb[i][6]),
								"Iteration : " + i + " --  CSS value verification failed for " + "\""
										+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
										+ ExpectedFontValuesWeb[i][6] + ", actualCssValues : " + actualCssValues.get(4)
										+ "\n");
					}

					if (!ExpectedFontValuesWeb[i][7].equalsIgnoreCase("NA")) {
						log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][7]);
						log.info("actualCssValues   : " + actualCssValues.get(5));
						log.info("match status : " + actualCssValues.get(5).contains(ExpectedFontValuesWeb[i][7]));
						softAssert.assertTrue(actualCssValues.get(5).contains(ExpectedFontValuesWeb[i][7]),
								"Iteration : " + i + " --  CSS value verification failed for " + "\""
										+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
										+ ExpectedFontValuesWeb[i][7] + ", actualCssValues : " + actualCssValues.get(5)
										+ "\n");
					}

					if (!ExpectedFontValuesWeb[i][8].equalsIgnoreCase("NA")) {
						log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][8]);
						log.info("actualCssValues   : " + actualCssValues.get(6));
						log.info("match status : " + actualCssValues.get(6).contains(ExpectedFontValuesWeb[i][8]));
						softAssert.assertTrue(actualCssValues.get(6).contains(ExpectedFontValuesWeb[i][8]),
								"Iteration : " + i + " --  CSS value verification failed for " + "\""
										+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
										+ ExpectedFontValuesWeb[i][8] + ", actualCssValues : " + actualCssValues.get(6)
										+ "\n");
					}
				}
			}

			if (testType.equalsIgnoreCase("Web") && testBedName.equalsIgnoreCase("edge")
					|| testType.equalsIgnoreCase("Mobile") && testBedName.equalsIgnoreCase("iPadNative")) {
				log.info("********************TestType for Edge started execution***************   : "
						+ testType.toString());
				log.info("********************TestBedName for Edge started execution***************   : "
						+ testBedName.toString());
				if (width > 599 || width < 800) {
					for (int i = 0; i < ExpectedFontValuesTab.length; i++) {

						System.out.println("Iteration under test  is : " + i + " :: Item under test is : "
								+ ExpectedFontValuesTab[i][0]);
						List<String> actualCssValues = commonMethods.getFontProperties(webPage,
								ExpectedFontValuesTab[i][1], softAssert);
						if (!ExpectedFontValuesWeb[i][2].equalsIgnoreCase("NA")) {
							log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][2]);
							log.info("actualCssValues   : " + actualCssValues.get(0));
							log.info("match status : " + actualCssValues.get(0).contains(ExpectedFontValuesWeb[i][2]));
							softAssert.assertTrue(actualCssValues.get(0).contains(ExpectedFontValuesWeb[i][2]),
									"Iteration : " + i + " --  CSS value verification failed for " + "\""
											+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
											+ ExpectedFontValuesWeb[i][2] + ", actualCssValues : "
											+ actualCssValues.get(0) + "\n");
						}

						if (!ExpectedFontValuesWeb[i][3].equalsIgnoreCase("NA")) {
							log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][3]);
							log.info("actualCssValues   : " + actualCssValues.get(1));
							log.info("match status : " + actualCssValues.get(1).contains(ExpectedFontValuesWeb[i][3]));
							softAssert.assertTrue(actualCssValues.get(1).contains(ExpectedFontValuesWeb[i][3]),
									"Iteration : " + i + " --  CSS value verification failed for " + "\""
											+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
											+ ExpectedFontValuesWeb[i][3] + ", actualCssValues : "
											+ actualCssValues.get(1) + "\n");
						}

						if (!ExpectedFontValuesWeb[i][4].equalsIgnoreCase("NA")) {
							log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][4]);
							log.info("actualCssValues   : " + actualCssValues.get(2));
							log.info("match status : " + actualCssValues.get(2).contains(ExpectedFontValuesWeb[i][4]));
							softAssert.assertTrue(actualCssValues.get(2).contains(ExpectedFontValuesWeb[i][4]),
									"Iteration : " + i + " --  CSS value verification failed for " + "\""
											+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
											+ ExpectedFontValuesWeb[i][4] + ", actualCssValues : "
											+ actualCssValues.get(2) + "\n");
						}

						if (!ExpectedFontValuesWeb[i][5].equalsIgnoreCase("NA")) {
							log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][5]);
							log.info("actualCssValues   : " + actualCssValues.get(3));
							log.info("match status : " + actualCssValues.get(3).contains(ExpectedFontValuesWeb[i][5]));
							softAssert.assertTrue(actualCssValues.get(3).contains(ExpectedFontValuesWeb[i][5]),
									"Iteration : " + i + " --  CSS value verification failed for " + "\""
											+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
											+ ExpectedFontValuesWeb[i][5] + ", actualCssValues : "
											+ actualCssValues.get(3) + "\n");
						}

						if (!ExpectedFontValuesWeb[i][6].equalsIgnoreCase("NA")) {
							log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][6]);
							log.info("actualCssValues   : " + actualCssValues.get(4));
							log.info("match status : " + actualCssValues.get(4).contains(ExpectedFontValuesWeb[i][6]));
							softAssert.assertTrue(actualCssValues.get(4).contains(ExpectedFontValuesWeb[i][6]),
									"Iteration : " + i + " --  CSS value verification failed for " + "\""
											+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
											+ ExpectedFontValuesWeb[i][6] + ", actualCssValues : "
											+ actualCssValues.get(4) + "\n");
						}

						if (!ExpectedFontValuesWeb[i][7].equalsIgnoreCase("NA")) {
							log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][7]);
							log.info("actualCssValues   : " + actualCssValues.get(5));
							log.info("match status : " + actualCssValues.get(5).contains(ExpectedFontValuesWeb[i][7]));
							softAssert.assertTrue(actualCssValues.get(5).contains(ExpectedFontValuesWeb[i][7]),
									"Iteration : " + i + " --  CSS value verification failed for " + "\""
											+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
											+ ExpectedFontValuesWeb[i][7] + ", actualCssValues : "
											+ actualCssValues.get(5) + "\n");
						}

						if (!ExpectedFontValuesWeb[i][8].equalsIgnoreCase("NA")) {
							log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][8]);
							log.info("actualCssValues   : " + actualCssValues.get(6));
							log.info("match status : " + actualCssValues.get(6).contains(ExpectedFontValuesWeb[i][8]));
							softAssert.assertTrue(actualCssValues.get(6).contains(ExpectedFontValuesWeb[i][8]),
									"Iteration : " + i + " --  CSS value verification failed for " + "\""
											+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
											+ ExpectedFontValuesWeb[i][8] + ", actualCssValues : "
											+ actualCssValues.get(6) + "\n");
						}
					}
				}
			}

			else if (testType.equalsIgnoreCase("Mobile")
					|| (testType.equalsIgnoreCase("Mobile") && testBedName.equalsIgnoreCase("IPhoneNative"))) {
				log.info("********************TestType for All_Mobile started execution***************   : "
						+ testType.toString());
				log.info("********************TestBedName for All_Mobile started execution***************   : "
						+ testBedName.toString());

				for (int i = 0; i < ExpectedFontValuesMobile.length; i++) {
					System.out.println("Iteration under test  is : " + i + " :: Item under test is : "
							+ ExpectedFontValuesMobile[i][0]);
					List<String> actualCssValues = commonMethods.getFontProperties(webPage,
							ExpectedFontValuesMobile[i][1], softAssert);
					if (!ExpectedFontValuesWeb[i][2].equalsIgnoreCase("NA")) {
						log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][2]);
						log.info("actualCssValues   : " + actualCssValues.get(0));
						log.info("match status : " + actualCssValues.get(0).contains(ExpectedFontValuesWeb[i][2]));
						softAssert.assertTrue(actualCssValues.get(0).contains(ExpectedFontValuesWeb[i][2]),
								"Iteration : " + i + " --  CSS value verification failed for " + "\""
										+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
										+ ExpectedFontValuesWeb[i][2] + ", actualCssValues : " + actualCssValues.get(0)
										+ "\n");
					}

					if (!ExpectedFontValuesWeb[i][3].equalsIgnoreCase("NA")) {
						log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][3]);
						log.info("actualCssValues   : " + actualCssValues.get(1));
						log.info("match status : " + actualCssValues.get(1).contains(ExpectedFontValuesWeb[i][3]));
						softAssert.assertTrue(actualCssValues.get(1).contains(ExpectedFontValuesWeb[i][3]),
								"Iteration : " + i + " --  CSS value verification failed for " + "\""
										+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
										+ ExpectedFontValuesWeb[i][3] + ", actualCssValues : " + actualCssValues.get(1)
										+ "\n");
					}

					if (!ExpectedFontValuesWeb[i][4].equalsIgnoreCase("NA")) {
						log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][4]);
						log.info("actualCssValues   : " + actualCssValues.get(2));
						log.info("match status : " + actualCssValues.get(2).contains(ExpectedFontValuesWeb[i][4]));
						softAssert.assertTrue(actualCssValues.get(2).contains(ExpectedFontValuesWeb[i][4]),
								"Iteration : " + i + " --  CSS value verification failed for " + "\""
										+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
										+ ExpectedFontValuesWeb[i][4] + ", actualCssValues : " + actualCssValues.get(2)
										+ "\n");
					}

					if (!ExpectedFontValuesWeb[i][5].equalsIgnoreCase("NA")) {
						log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][5]);
						log.info("actualCssValues   : " + actualCssValues.get(3));
						log.info("match status : " + actualCssValues.get(3).contains(ExpectedFontValuesWeb[i][5]));
						softAssert.assertTrue(actualCssValues.get(3).contains(ExpectedFontValuesWeb[i][5]),
								"Iteration : " + i + " --  CSS value verification failed for " + "\""
										+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
										+ ExpectedFontValuesWeb[i][5] + ", actualCssValues : " + actualCssValues.get(3)
										+ "\n");
					}

					if (!ExpectedFontValuesWeb[i][6].equalsIgnoreCase("NA")) {
						log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][6]);
						log.info("actualCssValues   : " + actualCssValues.get(4));
						log.info("match status : " + actualCssValues.get(4).contains(ExpectedFontValuesWeb[i][6]));
						softAssert.assertTrue(actualCssValues.get(4).contains(ExpectedFontValuesWeb[i][6]),
								"Iteration : " + i + " --  CSS value verification failed for " + "\""
										+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
										+ ExpectedFontValuesWeb[i][6] + ", actualCssValues : " + actualCssValues.get(4)
										+ "\n");
					}

					if (!ExpectedFontValuesWeb[i][7].equalsIgnoreCase("NA")) {
						log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][7]);
						log.info("actualCssValues   : " + actualCssValues.get(5));
						log.info("match status : " + actualCssValues.get(5).contains(ExpectedFontValuesWeb[i][7]));
						softAssert.assertTrue(actualCssValues.get(5).contains(ExpectedFontValuesWeb[i][7]),
								"Iteration : " + i + " --  CSS value verification failed for " + "\""
										+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
										+ ExpectedFontValuesWeb[i][7] + ", actualCssValues : " + actualCssValues.get(5)
										+ "\n");
					}

					if (!ExpectedFontValuesWeb[i][8].equalsIgnoreCase("NA")) {
						log.info("ExpectedFontValuesWeb : " + ExpectedFontValuesWeb[i][8]);
						log.info("actualCssValues   : " + actualCssValues.get(6));
						log.info("match status : " + actualCssValues.get(6).contains(ExpectedFontValuesWeb[i][8]));
						softAssert.assertTrue(actualCssValues.get(6).contains(ExpectedFontValuesWeb[i][8]),
								"Iteration : " + i + " --  CSS value verification failed for " + "\""
										+ ExpectedFontValuesWeb[i][0] + "\"" + "\n ExpectedFontValuesWeb "
										+ ExpectedFontValuesWeb[i][8] + ", actualCssValues : " + actualCssValues.get(6)
										+ "\n");
					}
				}
			}

		} catch (Throwable e) {
			e.printStackTrace();

		}
	}

	/**
	 * Verify Register button
	 * 
	 * @param webPage
	 * @param softAssert
	 * @throws InterruptedException
	 */
	public void verifyRegisterButton(WebPage webPage, SoftAssert softAssert) throws InterruptedException {
		commonMethods.clickElementbyXpath(webPage, commonData.get("registerBtnXpath"), softAssert);

		softAssert.assertEquals(webPageMap.get(Thread.currentThread().getId()).getPageTitle(),
				commonData.get("registerPageTitle"),
				"Register page title . Expected " + commonData.get("registerPageTitle") + " Actual : "
						+ webPageMap.get(Thread.currentThread().getId()).getPageTitle());

	}

	/**
	 * SignOut functionality
	 * 
	 * @param webPage
	 * @param softAssert
	 * @throws Exception
	 */
	public void connsSignOut(WebPage webPage, SoftAssert softAssert) throws Exception {
		commonMethods.clickElementById(webPage, commonData.get("myAccountLinkId"), softAssert);
		commonMethods.clickElementbyXpath(webPage, commonData.get("signOutLinkXpath"), softAssert);
		webPage.getDriver().manage().deleteAllCookies();

	}

	/**
	 * Get Payment values after $ sign
	 * 
	 * @param locator
	 * @param webpage
	 * @param softAssert
	 * @return
	 */
	public int getAmount(String locator, WebPage webpage, SoftAssert softAssert) {
		String currentBal = commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), locator,
				softAssert);
		int curBalance = Integer.parseInt(currentBal.substring(1));
		log.info("Current Balance Amount:" + curBalance);
		return curBalance;
	}

	/**
	 * Get payment values with Text
	 * 
	 * @param locator
	 * @param webpage
	 * @param softAssert
	 * @return
	 */
	public int getPaymentAmountByXpath(String locator, WebPage webpage, SoftAssert softAssert) {
		String balance = commonMethods.getTextbyXpath(webPageMap.get(Thread.currentThread().getId()), locator,
				softAssert);
		int curBalance = Integer.parseInt(balance.substring(balance.indexOf("$")));
		log.info("Payemnt Balance Amount:" + curBalance);
		return curBalance;
	}

}
