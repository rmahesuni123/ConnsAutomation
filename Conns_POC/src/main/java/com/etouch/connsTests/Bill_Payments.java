package com.etouch.connsTests;

import java.awt.AWTException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
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
import com.etouch.connsPages.BillPayments;
import com.etouch.connsPages.ConnsHomePage;
import com.etouch.connsPages.ConnsMainPage;
import com.etouch.connsPages.CreateAccountAndSignInPage;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.datamanager.excel.annotations.IExcelDataFiles;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.taf.webui.ITafElement;
import com.etouch.taf.webui.selenium.WebPage;

@Test(groups = "Bill_Payments")
@IExcelDataFiles(excelDataFiles = { "CreditAppData=testData" })
public class Bill_Payments extends BaseTest {
	private String testBedName;
	Path path;
	String DataFilePath;
	protected String testType, browserName;
	String currentTestBedName;
	static Log log = LogUtil.getLog(Bill_Payments.class);
	private String url, testEnv;
	protected WebPage webPage;
	private ConnsMainPage mainPage;
	protected static CommonMethods commonMethods;
	private String registerUrl, signInURL, PayYourBillUrl, DashboardURL;
	String[][] commonData;
	boolean userLoggedIn = false;
	String[][] YesLeaseData;
	BillPayments billPaymentsPage;
	static String temp = null;
	protected static LinkedHashMap<Long, WebPage> webPageMap = new LinkedHashMap<Long, WebPage>();

	/*** Prepare before class @throws Exception the exception */
	@BeforeClass(alwaysRun = true)
	public void setUp(ITestContext context) throws InterruptedException, FileNotFoundException, IOException {
		try {
			testBedName = context.getCurrentXmlTest().getAllParameters().get("testBedName");
			CommonUtil.sop("Test bed Name is " + testBedName);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			commonMethods = new CommonMethods();
			browserName = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getBrowser().getName()
					.toLowerCase();
			log.info("Test Type is : " + testType);
			log.info("browserName is : " + browserName);
			try {
				testEnv = System.getenv().get("Environment");
				log.info("testEnv is : " + System.getenv().get("Environment"));
				path = Paths.get(TestBedManager.INSTANCE.getProfile().getXlsDataConfig().get("testData"));
				DataFilePath = path.toAbsolutePath().toString().replace("Env", testEnv);
				log.info("DataFilePath After is : " + DataFilePath);
				commonData = ExcelUtil.readExcelData(DataFilePath, "Bill_Payments",
						"CreateAccountSignInCommonElements");
				url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL();
				registerUrl = commonData[1][1];
				signInURL = commonData[2][1];
				PayYourBillUrl = commonData[3][1];
				DashboardURL = commonData[8][1];
				synchronized (this) {
					
										
					webPage = new WebPage(context);
					mainPage = new ConnsMainPage(url, webPage);
					billPaymentsPage = new BillPayments(url, webPage);
					webPageMap.put(Thread.currentThread().getId(), webPage);
					log.info(mainPage);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.info("errr is " + e);
				SoftAssertor.addVerificationFailure(e.getMessage());
			}
			//CommonMethods.navigateToPage(webPage, signInURL);
		} catch (Exception e) {
			e.printStackTrace();
			CommonUtil.sop("errr is for" + testBedName + " -----------" + e);
			SoftAssertor.addVerificationFailure(e.getMessage());
		}
	}

	@AfterTest
	public void releaseResources() throws IOException, AWTException {
		webPage.getDriver().quit();
	}

	@Test(priority = 1, enabled = true, description = "Verify_PayYourBill_AND_SignInPage_Verbiage")
	public void Verify_PayYourBill_AND_SignInPage_Verbiage() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = "";
		String link_Name = "";
		String Redirection_Link_Locator = "";
		String Expected_URL = "";
		webPage.getDriver().get(url);
		commonMethods.waitForPageLoad(webPage, softAssert);
		Thread.sleep(2000);
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Bill_Payments", "VerifySignInPageVerbiage");
			for (int i = 0; i < testData.length; i++) {
			Redirection_Link_Locator  = testData[i][1];
			Expected_URL = testData[i][2];
			link_Name = testData[i][0];
			log.info("***********  TestType is  : " +"************  testBedName **************"  );
			ActualURL = billPaymentsPage.verifyLinkNavigationUsingJS(webPage,testData,Redirection_Link_Locator,link_Name,url,softAssert);
			softAssert.assertTrue(ActualURL.contains(Expected_URL),"Expected url: "+Expected_URL+" Actual url: "+ActualURL);
		
				 	String Actual_Verbiage1Text = commonMethods.getTextbyXpath(webPage, testData[i][3], softAssert);
					log.info("Actual_Verbiage1Text : "+Actual_Verbiage1Text+" Expected_Verbiage1Text : "+testData[i][4]);
					softAssert.assertTrue(Actual_Verbiage1Text.equals(testData[i][4]));	
					
					String Actual_Verbiage2Text = (commonMethods.getTextbyXpath(webPage, testData[i][5], softAssert));
					log.info("Actual_Verbiage2Text : "+Actual_Verbiage2Text +" Expected_Verbiage2Text : "+testData[i][6]);
					softAssert.assertTrue(Actual_Verbiage2Text.equals(testData[i][6]));	
		
		
			softAssert.assertAll();
			}}
		catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "Verify_HomePage_Banner_Links");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	@Test(priority = 2, enabled = true)
	public void verify_AccountDashboard_After_SignIn_with_ExistingUser() throws InterruptedException {
		log.info("******Started verify_AccountDashboard_After_SignIn_with_ExistingUser ********");
		CommonMethods.waitForGivenTime(10);
		SoftAssert softAssert = new SoftAssert();
		JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
		webPage.getDriver().get(signInURL);
		commonMethods.waitForPageLoad(webPage, softAssert);
		CommonMethods.waitForGivenTime(5);
		try {
			String[][] testdata = ExcelUtil.readExcelData(DataFilePath, "Bill_Payments",
					"verify_SignIn_With_Valid_Input");
			for (int i = 0; i < 2; i++) {
				commonMethods.sendKeysbyXpath(webPage, testdata[i][1], testdata[i][2], softAssert);
			}

			WebElement element = webPage.getDriver().findElement(By.xpath(testdata[2][1]));
			js.executeScript("arguments[0].click();", element);
			CommonMethods.waitForGivenTime(10);
			//webPage.getDriver().get(commonData[8][1]);
			String[][] linkData = ExcelUtil.readExcelData(DataFilePath, "CreateAccountSignIn",
					"verify_Account_Dashboard");

			for (int i = 1; i < linkData.length; i++) {

				if ((testBedName.equalsIgnoreCase("edge")) || (testBedName.equalsIgnoreCase("Safari"))
						|| (testType.equalsIgnoreCase("Mobile"))) {
					log.info("Only Edge Browser Execution Starts " + testBedName.toString());
					if ((i == 5) || ((i == 6))) {
						CommonMethods.waitForGivenTime(5);
						log.info("Started Iteration : " + i);
						js.executeScript("return document.readyState").equals("complete");
						WebElement element_1 = webPage.getDriver().findElement(By.xpath(linkData[i][1]));
						js.executeScript("arguments[0].click();", element_1);
						CommonMethods.waitForGivenTime(5);
						String actualUrl = commonMethods.getPageUrl(webPage, softAssert);
						softAssert.assertTrue(actualUrl.contains(linkData[i][2]), "Page URL navigation failed for :"
								+ linkData[i][0] + " URL:" + actualUrl + " not same as " + linkData[i][2]);
						commonMethods.navigateToPage(webPage, DashboardURL, softAssert);
						CommonMethods.waitForGivenTime(5);
						log.info("Navigate Back for " + testBedName.toString());
						CommonMethods.waitForGivenTime(3);
					} else {

						log.info("Started Iteration : " + i);
						commonMethods.clickElementbyXpath(webPage, linkData[i][1], softAssert);
						CommonMethods.waitForGivenTime(5);
						String actualUrl = commonMethods.getPageUrl(webPage, softAssert);
						softAssert.assertTrue(actualUrl.contains(linkData[i][2]), "Page URL navigation failed for :"
								+ linkData[i][0] + " URL:" + actualUrl + " not same as " + linkData[i][2]);

						if (testType.equalsIgnoreCase("Mobile") || testBedName.equalsIgnoreCase("edge")
								|| testBedName.equalsIgnoreCase("Safari")) {
							WebElement element_2 = webPage.getDriver().findElement(By.xpath(linkData[i][3]));
							js.executeScript("arguments[0].click();", element_2);
						} else {
							commonMethods.clickElementbyXpath(webPage, linkData[i][3], softAssert);
						}
						CommonMethods.waitForGivenTime(5);
					}

				}
			}
			log.info("testing verify_AccountDashboard_After_SignIn_with_ExistingUser completed------>");
			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			mainPage.getScreenShotForFailure(webPage, "verify_AccountDashboard_After_SignIn_with_ExistingUser");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
		
	@Test(priority = 3, enabled = true, description = "Verify_Redirection_for_LegalDisclosures_Link")
	public void Verify_Redirection_for_LegalDisclosures_Link() {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = "";
		 String TargetpageLocator;
		 String TargetPageHeader;
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Bill_Payments", "Verify_Redirection_for_LegalDisclosures_Link");			
			
				String Redirection_Link_Locator  = testData[0][1];
				String Expected_URL = testData[0][2];
				String link_Name = testData[0][0];
				TargetpageLocator = testData[0][3];
				TargetPageHeader = 	testData[0][4];	
				
								
				log.info("***********  Verifying Target page url **************"  );
			//	ActualURL = billPaymentsPage.verifyLinkNavigationUsingJS_LegalDisclosure(webPage,testData,Redirection_Link_Locator,link_Name,url, TargetpageLocator,TargetPageHeader,softAssert);
				softAssert.assertTrue(ActualURL.contains(Expected_URL),"Expected url: "+Expected_URL+" Actual url: "+ActualURL);
								
			
			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
	@Test(priority = 4, enabled = true, description = "Verify_MyOnlinePayments_Verbiage")
	public void Verify_MyOnlinePayments_Verbiage() {
		SoftAssert softAssert = new SoftAssert();
		String ActualURL = "";
		
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Bill_Payments",
					"Verify_MyOnlinePayments_Verbiage");

		
			log.info("***********  Clicking on My Online Payments Link **************");
			ActualURL = billPaymentsPage.verifyLinkNavigationUsingJS(webPage, testData, testData[0][1],testData[0][0], url, softAssert);
			softAssert.assertTrue(ActualURL.contains(testData[0][2]),"Expected url: " + testData[0][2] + " Actual url: " + ActualURL);

			log.info("***********  Verifying Page Title **************");
			String ActualPageTitle = commonMethods.getTextbyXpath(webPage, testData[0][3], softAssert);
			log.info("ActualPageTitle : " + ActualPageTitle + " ExpectedPageTitle : "	+ testData[0][4]);
			softAssert.assertTrue(ActualPageTitle.equals(testData[0][4]), "Page title does not match");

			log.info("***********  Verifying Verbiage displayed above table **************"  );
			String ActualUpperText = commonMethods.getTextbyXpath(webPage, testData[0][3], softAssert);
			log.info("ActualUpperText : " +ActualUpperText  + " ExpectedUpperText : "+ testData[0][4]);
			softAssert.assertTrue(ActualUpperText.equals(testData[0][4]), "Upper verbiage does not match");
			
			log.info("***********  Verifying Verbiage displayed below table **************"  );
			String ActualBottomText = commonMethods.getTextbyXpath(webPage, testData[0][3], softAssert);
			log.info("ActualBottomText : " + ActualBottomText + " ExpectedBottomText : "+ testData[0][4]);
			softAssert.assertTrue(ActualBottomText.equals(testData[0][4]), "Bottom verbiage does not match");

			String[][] TableColumns = ExcelUtil.readExcelData(DataFilePath, "Bill_Payments",
					"MyOnlinePayments_Table_Contents");
			
			log.info("***********  Verifying Columns in Table **************"  );
			for (int i=0;i<5;i++){
			String ColumnNameLocator = TableColumns[i][1];
			String ColumnName = TableColumns[i][0];
			log.info("Actual : " + commonMethods.getTextbyXpath(webPage, ColumnNameLocator, softAssert) + " Expected : "
					+ ColumnName);
			softAssert.assertTrue(
					(commonMethods.getTextbyXpath(webPage, ColumnNameLocator, softAssert).equals(ColumnName)), "Column is not present");
			}
			log.info("***********  Verifying loan Acccount Num column data**************"  );
			List<WebElement> loanAcccountNum = webPage.getDriver().findElements(By.xpath(TableColumns[0][4]));
			log.info("RowCount==" + loanAcccountNum.size());

			int index = 1;
			for (WebElement webElement : loanAcccountNum) {
				System.out.println("***************" + webElement.getText() + "," + index);
				if (index == 1)
					continue;
				if (index == 2) {
					temp = webElement.getText();
					log.info("temp==" + temp);
				} else {

					softAssert.assertEquals(webElement.getText(), temp, "Loan account number not matched");

				}
				index++;

			}

			log.info("***********  Verifying Payment Method column data**************"  );
			//WebElement PaymentMethod =billPaymentsPage.getWebElementList(webPage, TableColumns[1][4], softAssert);
			List<WebElement> PaymentMethod = webPage.getDriver().findElements(By.xpath(TableColumns[1][4]));
			for (WebElement webElement : PaymentMethod) {
				if (index == 1)
					continue;

				softAssert.assertTrue(TableColumns[1][4].contains(webElement.getText()),
						"Payment Method does not match");

			}

			log.info("***********  Verifying Status column data**************"  );
			List<WebElement> Status = webPage.getDriver().findElements(By.xpath(TableColumns[4][4]));
			for (WebElement webElement : Status) {
				if (index == 1)
					continue;

				softAssert.assertTrue(TableColumns[4][2].contains(webElement.getText()),
						"Status does not match");

			}

			log.info("***********  Verifying Payment Amount column data**************"  );
			List<WebElement> PaymentAmount = webPage.getDriver().findElements(By.xpath(TableColumns[3][4]));
			for (WebElement webElement : PaymentAmount) {
				if (index == 1)
					continue;

				softAssert.assertTrue(TableColumns[3][2].contains(webElement.getText()),
						"Payment Amount does not match");

			}
			
			log.info("***********  Verifying Confirmation Number column data**************"  );
			List<WebElement> ConfirmationNum = webPage.getDriver().findElements(By.xpath(TableColumns[5][4]));
			index = 1;
			for (WebElement webElement : ConfirmationNum) {

				String pattern = "\\d{7}", myString = webElement.getText();
				log.info("******************::" + myString.matches(pattern) +"************************");
				softAssert.assertTrue(myString.matches(pattern), "Confirmation number did not matched the pattern");

				System.out.println(webElement.getText());

			}
			
			
			log.info("***********  Verifying Payment Date column data**************"  );
			 List<WebElement> PaymentDate = webPage.getDriver().findElements(By.xpath(TableColumns[2][4]));
			  for (WebElement webElement : PaymentDate) {
				 
				 if (index == 1)
						continue;
				 
				 if(webElement.getText().length()!=0)
				 softAssert.assertTrue(BillPayments.isThisDateWithin3MonthsRange(webElement.getText(), "MM/dd/yyyy"), "Date is not within specified range");
			 }
			  
			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
		
	}
	
	@Test(priority = 1005, enabled = true, description = "Verify My Recent Online Payments Font properties")
	public void Verify_My_Recent_Online_Payments_Font_properties() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			//Verify Important Notice
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Bill_Payments",
					"My Online Payments_FontProperties");
			
			for(int i=0;i<7;i++) {
				webPage.sleep(10000);
					String fontSize = commonMethods.getCssvaluebyXpath(webPage, testData[i][1], "font-size", softAssert);
					String fontColor = commonMethods.getCssvaluebyXpath(webPage, testData[i][1], "color", softAssert);
					String fontFamily = commonMethods.getCssvaluebyXpath(webPage, testData[i][1], "font-family", softAssert);
					String fontWeight = commonMethods.getCssvaluebyXpath(webPage, testData[i][1], "font-weight", softAssert);
					String backgroundColor = commonMethods.getCssvaluebyXpath(webPage, testData[i][1], "background-color", softAssert);
					
					if(!testData[i][1].equalsIgnoreCase("NA"))
						log.info("****Font Size . Expected **********"+testData[i][2]+" *****Actual *******: "+fontSize);
					softAssert.assertEquals(fontSize,testData[i][2],"Font Size . Expected "+testData[i][2]+" Actual : "+fontSize);
					if(!testData[i][3].equalsIgnoreCase("NA"))
					softAssert.assertEquals(fontColor,testData[i][3],"Font Color . Expected "+testData[i][3]+" Actual : "+fontColor);
					if(!testData[i][4].equalsIgnoreCase("NA"))
					softAssert.assertTrue(fontFamily.contains(testData[i][4]),"Font Family . Expected "+testData[i][4]+" Actual : "+fontFamily);
					if(!testData[i][5].equalsIgnoreCase("NA"))
					softAssert.assertEquals(fontWeight,testData[i][5],"Font Weight . Expected "+testData[i][5]+" Actual : "+fontWeight);
					if(!testData[i][6].equalsIgnoreCase("NA"))
					softAssert.assertEquals(backgroundColor,testData[i][6],"Background color . Expected "+testData[i][6]+" Actual : "+backgroundColor);
					
				
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPageMap.get(Thread.currentThread().getId()), "verify_onetime_paynt_font");
			softAssert.assertAll();
		}
	}

	@Test(priority = 3, enabled = false, description = "Verify_Adding_NewPaymentMethod")
	public void Verify_Adding_NewPaymentMethod() {
		SoftAssert softAssert = new SoftAssert();
		JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
		String ActualURL = "";
		
		try {
			String[][] testData = ExcelUtil.readExcelData(DataFilePath, "Bill_Payments", "Verify_Adding_NewPaymentMethod");			
			
				String Redirection_Link_Locator  = testData[0][1];
				String Expected_URL = testData[0][2];
				String link_Name = testData[0][0];
				log.info("***********  TestType is  : " +"************  testBedName **************"  );
				ActualURL = billPaymentsPage.verifyLinkNavigationUsingJS(webPage,testData,Redirection_Link_Locator,link_Name,url,softAssert);
				softAssert.assertTrue(ActualURL.contains(Expected_URL),"Expected url: "+Expected_URL+" Actual url: "+ActualURL);
				
				String TargetpageLocator = testData[0][3];
				String TargetPageHeader = 	testData[0][4];	
				String TableNameLocator = testData[0][5];
				String TableName = 	testData[0][6];	
				log.info("Actual : "+commonMethods.getTextbyXpath(webPage, TargetpageLocator, softAssert)+" Expected : "+TargetPageHeader);
				softAssert.assertTrue((commonMethods.getTextbyXpath(webPage, TargetpageLocator, softAssert).equals(TargetPageHeader)));	
				log.info("Actual : "+commonMethods.getTextbyXpath(webPage, TableNameLocator, softAssert)+" Expected : "+TableName);
				softAssert.assertTrue((commonMethods.getTextbyXpath(webPage, TableNameLocator, softAssert).equals(TableName)));	
				
				WebElement element = webPage.getDriver().findElement(By.xpath(testData[0][5]));
				js.executeScript("arguments[0].click();", element);
				CommonMethods.waitForGivenTime(10);
				
			
			softAssert.assertAll();
		} catch (Throwable e) {
			e.printStackTrace();
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}
	
}