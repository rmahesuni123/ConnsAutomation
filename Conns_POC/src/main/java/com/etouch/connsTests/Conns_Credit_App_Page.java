package com.etouch.connsTests;

import java.awt.AWTException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.etouch.common.BaseTest;
import com.etouch.common.CommonMethods;
import com.etouch.common.TafExecutor;
import com.etouch.connsPages.ConnsMainPage;
import com.etouch.connsPages.ConnsStoreLocatorPage;
import com.etouch.connsPages.CreditAppPage;
import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.config.TestBedManagerConfiguration;
import com.etouch.taf.core.datamanager.excel.annotations.IExcelDataFiles;
//import com.etouch.taf.tools.rally.SpecializedScreenRecorder;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.taf.webui.selenium.WebPage;

@Test(groups = "YesMoneyCreditApplication")
@IExcelDataFiles(excelDataFiles = { "CreditAppData=testData" })
public class Conns_Credit_App_Page extends BaseTest {
	static Log log = LogUtil.getLog(Conns_Credit_App_Page.class);
	static String AbsolutePath = TafExecutor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	Logger logger = Logger.getLogger(Conns_Credit_App_Page.class.getName());
	protected static String url;
	protected static WebPage webPage;
	ConnsMainPage mainPage;
	String testBedName;
	TestBed testBed;
	Path path;
	String DataFilePath;
	String testEnv;
	protected static CommonMethods commonMethods;
	ConnsStoreLocatorPage connsStoreLocatorPage;
	protected static LinkedHashMap<String, String> commonData;
	String testType, browserName;

	@BeforeClass(alwaysRun = true)
	public void setUp(ITestContext context) throws InterruptedException, FileNotFoundException, IOException {
		try {
			testBedName = context.getCurrentXmlTest().getAllParameters().get("testBedName");
			log.info("Test bed Name is " + testBedName);
			testBed = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName);
			testType = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getTestType();
			connsStoreLocatorPage = new ConnsStoreLocatorPage();
			commonMethods = new CommonMethods();
			browserName = TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getBrowser().getName();
			log.info("Test Type is : " + testType);
			try {
				testEnv = System.getenv().get("Environment");
				System.out.println("testEnv is : " + testEnv);
				path = Paths.get(TestBedManager.INSTANCE.getProfile().getXlsDataConfig().get("testData"));
				DataFilePath = path.toAbsolutePath().toString().replace("Env", testEnv);
				System.out.println("DataFilePath After is : " + DataFilePath);
				commonData = CommonMethods.getDataInHashMap(DataFilePath, "CreditApp", "CreditAppCommonElements");
				url = TestBedManagerConfiguration.INSTANCE.getWebConfig().getURL();
				synchronized (this) {
					webPage = new WebPage(context);
					mainPage = new ConnsMainPage(url, webPage);
				}
				if (testType.equalsIgnoreCase("Web")) {
					log.info("Maximizing window");
					webPage.getDriver().manage().window().maximize();
				}
			} catch (Exception e) {
				log.info("errr is " + e);
				SoftAssertor.addVerificationFailure(e.getMessage());
			}
		} catch (Exception e) {
			CommonUtil.sop("Error is for" + testBedName + " -----------" + e);
			SoftAssertor.addVerificationFailure(e.getMessage());
		}
	}

	@AfterClass
	public void releaseResources() throws IOException, AWTException {
	}

	/**
	 * Test Case 001 - Verify Navigation to Yes Money Credit Application Page
	 * and Verify Page title
	 * 
	 * @throws Exception
	 * 
	 */
	@Test(priority = 1, enabled = true, description = "Verify Page Title")
	public void verify_Page_Title() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			CreditAppPage.navigateToCreditAppPage(softAssert);
		} catch (Throwable e) {
			softAssert.fail(e.getLocalizedMessage());
			CreditAppPage.navigateToCreditAppPage(softAssert);
		}
		log.info("Ending verify_Page_Title");
		softAssert.assertAll();
	}

	/**
	 * Test Case - 002 - Verify Font Size and Style of specified on element on
	 * Yes Money Credit Application page
	 * 
	 * @throws Exception
	 * 
	 */
	@Test(priority = 2, enabled = true, description = "Verify_Font_And_Size")
	public void Verify_Font_And_Size() {
		SoftAssert softAssert = new SoftAssert();
		try {
			String[][] ExpectedFontValuesWeb = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"VerifyFontandSizeWeb");
			String[][] ExpectedFontValuesTab = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"VerifyFontandSizeTab");
			String[][] ExpectedFontValuesMobile = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					"VerifyFontandSizeMobile");
			CreditAppPage.navigateToCreditAppPage(softAssert);
			JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
			int width = ((Long) js.executeScript("return window.innerWidth || document.body.clientWidth")).intValue();
			log.info("width value calculated is :" + width);
			int height = ((Long) js.executeScript("return window.innerHeight || document.body.clientHeight"))
					.intValue();
			log.info("height value calculated is :" + height);
			Dimension dimension = new Dimension(width, height);
			System.out.println("Dimensions" + dimension);
			// Dimension[width=600,height=792]
			if (testType.equalsIgnoreCase("Web")) {
				System.out.println("@Web");
				for (int i = 0; i < ExpectedFontValuesWeb.length; i++) {
					System.out.println("Iteration under test  is : " + i + " :: Item under test is : "
							+ ExpectedFontValuesWeb[i][0]);
					List<String> actualCssValues = commonMethods.getFontProperties(webPage, ExpectedFontValuesWeb[i][1],
							softAssert);
					if (!ExpectedFontValuesWeb[i][2].equalsIgnoreCase("NA")) {
						log.info("expected : " + ExpectedFontValuesWeb[i][2]);
						log.info("actual   : " + actualCssValues.get(0));
						log.info("match status : " + actualCssValues.get(0).contains(ExpectedFontValuesWeb[i][2]));
						softAssert.assertTrue(actualCssValues.get(0).contains(ExpectedFontValuesWeb[i][2]),
								"Iteration : " + i + " --  CSS value verification failed for "
										+ ExpectedFontValuesWeb[i][0] + ".. Expected font Size: "
										+ ExpectedFontValuesWeb[i][2] + ", Actual Font Size: " + actualCssValues.get(0)
										+ "\n");
					}
					if (!ExpectedFontValuesWeb[i][3].equalsIgnoreCase("NA")) {
						log.info("expected : " + ExpectedFontValuesWeb[i][3]);
						log.info("actual   : " + actualCssValues.get(1));
						log.info("match status : " + actualCssValues.get(1).contains(ExpectedFontValuesWeb[i][3]));
						softAssert.assertTrue(actualCssValues.get(1).contains(ExpectedFontValuesWeb[i][3]),
								"Iteration : " + i + " --  CSS value verification failed for "
										+ ExpectedFontValuesWeb[i][0] + ".. Expected font Size: "
										+ ExpectedFontValuesWeb[i][3] + ", Actual Font Color: " + actualCssValues.get(1)
										+ "\n");
					}
					if (!ExpectedFontValuesWeb[i][4].equalsIgnoreCase("NA")) {
						log.info("expected : " + ExpectedFontValuesWeb[i][4]);
						log.info("actual   : " + actualCssValues.get(2));
						log.info("match status : " + actualCssValues.get(2).contains(ExpectedFontValuesWeb[i][4]));
						softAssert.assertTrue(actualCssValues.get(2).contains(ExpectedFontValuesWeb[i][4]),
								"Iteration : " + i + " --  CSS value verification failed for "
										+ ExpectedFontValuesWeb[i][0] + ".. Expected font Size: "
										+ ExpectedFontValuesWeb[i][4] + ", Actual Font Family: "
										+ actualCssValues.get(2) + "\n");
					}
					if (!ExpectedFontValuesWeb[i][5].equalsIgnoreCase("NA")) {
						log.info("expected : " + ExpectedFontValuesWeb[i][5]);
						log.info("actual   : " + actualCssValues.get(3));
						log.info("match status : " + actualCssValues.get(3).contains(ExpectedFontValuesWeb[i][5]));
						softAssert.assertTrue(actualCssValues.get(3).contains(ExpectedFontValuesWeb[i][5]),
								"Iteration : " + i + " --  CSS value verification failed for "
										+ ExpectedFontValuesWeb[i][0] + ".. Expected font Size: "
										+ ExpectedFontValuesWeb[i][5] + ", Actual Font Weight: "
										+ actualCssValues.get(3) + "\n");
					}
					if (!ExpectedFontValuesWeb[i][6].equalsIgnoreCase("NA")) {
						log.info("expected : " + ExpectedFontValuesWeb[i][6]);
						log.info("actual   : " + actualCssValues.get(4));
						log.info("match status : " + actualCssValues.get(4).contains(ExpectedFontValuesWeb[i][6]));
						softAssert.assertTrue(actualCssValues.get(4).contains(ExpectedFontValuesWeb[i][6]),
								"Iteration : " + i + " --  CSS value verification failed for "
										+ ExpectedFontValuesWeb[i][0] + ".. Expected font Size: "
										+ ExpectedFontValuesWeb[i][6] + ", Actual Background Color: "
										+ actualCssValues.get(4) + "\n");
					}
					if (!ExpectedFontValuesWeb[i][7].equalsIgnoreCase("NA")) {
						log.info("expected : " + ExpectedFontValuesWeb[i][7]);
						log.info("actual   : " + actualCssValues.get(5));
						log.info("match status : " + actualCssValues.get(5).contains(ExpectedFontValuesWeb[i][7]));
						softAssert.assertTrue(actualCssValues.get(5).contains(ExpectedFontValuesWeb[i][7]),
								"Iteration : " + i + " --  CSS value verification failed for "
										+ ExpectedFontValuesWeb[i][0] + ".. Expected font Size: "
										+ ExpectedFontValuesWeb[i][7] + ", Actual Test Allign: "
										+ actualCssValues.get(5) + "\n");
					}
					if (!ExpectedFontValuesWeb[i][8].equalsIgnoreCase("NA")) {
						log.info("expected : " + ExpectedFontValuesWeb[i][8]);
						log.info("actual   : " + actualCssValues.get(6));
						log.info("match status : " + actualCssValues.get(6).contains(ExpectedFontValuesWeb[i][8]));
						softAssert.assertTrue(actualCssValues.get(6).contains(ExpectedFontValuesWeb[i][8]),
								"Iteration : " + i + " --  CSS value verification failed for "
										+ ExpectedFontValuesWeb[i][0] + ".. Expected font Size: "
										+ ExpectedFontValuesWeb[i][8] + ", Actual Text Transform: "
										+ actualCssValues.get(6) + "\n");
					}
				}
			}
			if (testType.equalsIgnoreCase("Mobile")) {
				System.out.println("Mobileeeeeeeeeeee"
						+ TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getDevice().getName());
				if (width > 599 || width < 800)
				// if(TestBedManager.INSTANCE.getCurrentTestBeds().get(testBedName).getDevice().getName().contains("Tab"))
				{
					System.out.println("Tab Mode");
					for (int i = 0; i < ExpectedFontValuesTab.length; i++) {
						System.out.println("Iteration under test  is : " + i + " :: Item under test is : "
								+ ExpectedFontValuesTab[i][0]);
						List<String> actualCssValues = commonMethods.getFontProperties(webPage,
								ExpectedFontValuesTab[i][1], softAssert);
						if (!ExpectedFontValuesTab[i][2].equalsIgnoreCase("NA")) {
							log.info("expected : " + ExpectedFontValuesTab[i][2]);
							log.info("actual   : " + actualCssValues.get(0));
							log.info("match status : " + actualCssValues.get(0).contains(ExpectedFontValuesTab[i][2]));
							softAssert.assertTrue(actualCssValues.get(0).contains(ExpectedFontValuesTab[i][2]),
									"Iteration : " + i + " --  CSS value verification failed for "
											+ ExpectedFontValuesTab[i][0] + ".. Expected font Size: "
											+ ExpectedFontValuesTab[i][2] + ", Actual Font Size: "
											+ actualCssValues.get(0) + "\n");
						}
						if (!ExpectedFontValuesTab[i][3].equalsIgnoreCase("NA")) {
							log.info("expected : " + ExpectedFontValuesTab[i][3]);
							log.info("actual   : " + actualCssValues.get(1));
							log.info("match status : " + actualCssValues.get(1).contains(ExpectedFontValuesTab[i][3]));
							softAssert.assertTrue(actualCssValues.get(1).contains(ExpectedFontValuesTab[i][3]),
									"Iteration : " + i + " --  CSS value verification failed for "
											+ ExpectedFontValuesTab[i][0] + ".. Expected font Size: "
											+ ExpectedFontValuesTab[i][3] + ", Actual Font Color: "
											+ actualCssValues.get(1) + "\n");
						}
						if (!ExpectedFontValuesTab[i][4].equalsIgnoreCase("NA")) {
							log.info("expected : " + ExpectedFontValuesTab[i][4]);
							log.info("actual   : " + actualCssValues.get(2));
							log.info("match status : " + actualCssValues.get(2).contains(ExpectedFontValuesTab[i][4]));
							softAssert.assertTrue(actualCssValues.get(2).contains(ExpectedFontValuesTab[i][4]),
									"Iteration : " + i + " --  CSS value verification failed for "
											+ ExpectedFontValuesTab[i][0] + ".. Expected font Size: "
											+ ExpectedFontValuesTab[i][4] + ", Actual Font Family: "
											+ actualCssValues.get(2) + "\n");
						}
						if (!ExpectedFontValuesTab[i][5].equalsIgnoreCase("NA")) {
							log.info("expected : " + ExpectedFontValuesTab[i][5]);
							log.info("actual   : " + actualCssValues.get(3));
							log.info("match status : " + actualCssValues.get(3).contains(ExpectedFontValuesTab[i][5]));
							softAssert.assertTrue(actualCssValues.get(3).contains(ExpectedFontValuesTab[i][5]),
									"Iteration : " + i + " --  CSS value verification failed for "
											+ ExpectedFontValuesTab[i][0] + ".. Expected font Size: "
											+ ExpectedFontValuesTab[i][5] + ", Actual Font Weight: "
											+ actualCssValues.get(3) + "\n");
						}
						if (!ExpectedFontValuesTab[i][6].equalsIgnoreCase("NA")) {
							log.info("expected : " + ExpectedFontValuesTab[i][6]);
							log.info("actual   : " + actualCssValues.get(4));
							log.info("match status : " + actualCssValues.get(4).contains(ExpectedFontValuesTab[i][6]));
							softAssert.assertTrue(actualCssValues.get(4).contains(ExpectedFontValuesTab[i][6]),
									"Iteration : " + i + " --  CSS value verification failed for "
											+ ExpectedFontValuesTab[i][0] + ".. Expected font Size: "
											+ ExpectedFontValuesTab[i][6] + ", Actual Background Color: "
											+ actualCssValues.get(4) + "\n");
						}
						if (!ExpectedFontValuesTab[i][7].equalsIgnoreCase("NA")) {
							log.info("expected : " + ExpectedFontValuesTab[i][7]);
							log.info("actual   : " + actualCssValues.get(5));
							log.info("match status : " + actualCssValues.get(5).contains(ExpectedFontValuesTab[i][7]));
							softAssert.assertTrue(actualCssValues.get(5).contains(ExpectedFontValuesTab[i][7]),
									"Iteration : " + i + " --  CSS value verification failed for "
											+ ExpectedFontValuesTab[i][0] + ".. Expected font Size: "
											+ ExpectedFontValuesTab[i][7] + ", Actual Test Allign: "
											+ actualCssValues.get(5) + "\n");
						}
						if (!ExpectedFontValuesTab[i][8].equalsIgnoreCase("NA")) {
							log.info("expected : " + ExpectedFontValuesTab[i][8]);
							log.info("actual   : " + actualCssValues.get(6));
							log.info("match status : " + actualCssValues.get(6).contains(ExpectedFontValuesTab[i][8]));
							softAssert.assertTrue(actualCssValues.get(6).contains(ExpectedFontValuesTab[i][8]),
									"Iteration : " + i + " --  CSS value verification failed for "
											+ ExpectedFontValuesTab[i][0] + ".. Expected font Size: "
											+ ExpectedFontValuesTab[i][8] + ", Actual Text Transform: "
											+ actualCssValues.get(6) + "\n");
						}
					}
				} else {
					System.out.println("Mobile Mode");
					for (int i = 0; i < ExpectedFontValuesMobile.length; i++) {
						System.out.println("Iteration under test  is : " + i + " :: Item under test is : "
								+ ExpectedFontValuesMobile[i][0]);
						List<String> actualCssValues = commonMethods.getFontProperties(webPage,
								ExpectedFontValuesMobile[i][1], softAssert);
						if (!ExpectedFontValuesMobile[i][2].equalsIgnoreCase("NA")) {
							log.info("expected : " + ExpectedFontValuesMobile[i][2]);
							log.info("actual   : " + actualCssValues.get(0));
							log.info("match status : "
									+ actualCssValues.get(0).contains(ExpectedFontValuesMobile[i][2]));
							softAssert.assertTrue(actualCssValues.get(0).contains(ExpectedFontValuesMobile[i][2]),
									"Iteration : " + i + " --  CSS value verification failed for "
											+ ExpectedFontValuesMobile[i][0] + ".. Expected font Size: "
											+ ExpectedFontValuesMobile[i][2] + ", Actual Font Size: "
											+ actualCssValues.get(0) + "\n");
						}
						if (!ExpectedFontValuesMobile[i][3].equalsIgnoreCase("NA")) {
							log.info("expected : " + ExpectedFontValuesMobile[i][3]);
							log.info("actual   : " + actualCssValues.get(1));
							log.info("match status : "
									+ actualCssValues.get(1).contains(ExpectedFontValuesMobile[i][3]));
							softAssert.assertTrue(actualCssValues.get(1).contains(ExpectedFontValuesMobile[i][3]),
									"Iteration : " + i + " --  CSS value verification failed for "
											+ ExpectedFontValuesMobile[i][0] + ".. Expected font Size: "
											+ ExpectedFontValuesMobile[i][3] + ", Actual Font Color: "
											+ actualCssValues.get(1) + "\n");
						}
						if (!ExpectedFontValuesMobile[i][4].equalsIgnoreCase("NA")) {
							log.info("expected : " + ExpectedFontValuesMobile[i][4]);
							log.info("actual   : " + actualCssValues.get(2));
							log.info("match status : "
									+ actualCssValues.get(2).contains(ExpectedFontValuesMobile[i][4]));
							softAssert.assertTrue(actualCssValues.get(2).contains(ExpectedFontValuesMobile[i][4]),
									"Iteration : " + i + " --  CSS value verification failed for "
											+ ExpectedFontValuesMobile[i][0] + ".. Expected font Size: "
											+ ExpectedFontValuesMobile[i][4] + ", Actual Font Family: "
											+ actualCssValues.get(2) + "\n");
						}
						if (!ExpectedFontValuesMobile[i][5].equalsIgnoreCase("NA")) {
							log.info("expected : " + ExpectedFontValuesMobile[i][5]);
							log.info("actual   : " + actualCssValues.get(3));
							log.info("match status : "
									+ actualCssValues.get(3).contains(ExpectedFontValuesMobile[i][5]));
							softAssert.assertTrue(actualCssValues.get(3).contains(ExpectedFontValuesMobile[i][5]),
									"Iteration : " + i + " --  CSS value verification failed for "
											+ ExpectedFontValuesMobile[i][0] + ".. Expected font Size: "
											+ ExpectedFontValuesMobile[i][5] + ", Actual Font Weight: "
											+ actualCssValues.get(3) + "\n");
						}
						if (!ExpectedFontValuesMobile[i][6].equalsIgnoreCase("NA")) {
							log.info("expected : " + ExpectedFontValuesMobile[i][6]);
							log.info("actual   : " + actualCssValues.get(4));
							log.info("match status : "
									+ actualCssValues.get(4).contains(ExpectedFontValuesMobile[i][6]));
							softAssert.assertTrue(actualCssValues.get(4).contains(ExpectedFontValuesMobile[i][6]),
									"Iteration : " + i + " --  CSS value verification failed for "
											+ ExpectedFontValuesMobile[i][0] + ".. Expected font Size: "
											+ ExpectedFontValuesMobile[i][6] + ", Actual Background Color: "
											+ actualCssValues.get(4) + "\n");
						}
						if (!ExpectedFontValuesMobile[i][7].equalsIgnoreCase("NA")) {
							log.info("expected : " + ExpectedFontValuesMobile[i][7]);
							log.info("actual   : " + actualCssValues.get(5));
							log.info("match status : "
									+ actualCssValues.get(5).contains(ExpectedFontValuesMobile[i][7]));
							softAssert.assertTrue(actualCssValues.get(5).contains(ExpectedFontValuesMobile[i][7]),
									"Iteration : " + i + " --  CSS value verification failed for "
											+ ExpectedFontValuesMobile[i][0] + ".. Expected font Size: "
											+ ExpectedFontValuesMobile[i][7] + ", Actual Test Allign: "
											+ actualCssValues.get(5) + "\n");
						}
						if (!ExpectedFontValuesMobile[i][8].equalsIgnoreCase("NA")) {
							log.info("expected : " + ExpectedFontValuesMobile[i][8]);
							log.info("actual   : " + actualCssValues.get(6));
							log.info("match status : "
									+ actualCssValues.get(6).contains(ExpectedFontValuesMobile[i][8]));
							softAssert.assertTrue(actualCssValues.get(6).contains(ExpectedFontValuesMobile[i][8]),
									"Iteration : " + i + " --  CSS value verification failed for "
											+ ExpectedFontValuesMobile[i][0] + ".. Expected font Size: "
											+ ExpectedFontValuesMobile[i][8] + ", Actual Text Transform: "
											+ actualCssValues.get(6) + "\n");
						}
					}
				}
			}
			softAssert.assertAll();
		} catch (Throwable e) {
			mainPage.getScreenShotForFailure(webPage, "Verify_Font_And_Size");
			softAssert.assertAll();
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test Case - 003 Verify Page Content are rendered
	 * 
	 * @throws Exception
	 * 
	 */
	@Test(priority = 3, enabled = true, description = "Verify Page Content")
	public void verify_Page_Content() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		CreditAppPage.navigateToCreditAppPage(softAssert);
		log.info("testing verifyPageContent started------>");
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "CreditApp", "verifyPageContent");
		boolean returnValue = false;
		String FieldName;
		String Fieldxpath;
		for (int r = 0; r < test.length; r++) {
			FieldName = test[r][0];
			Fieldxpath = test[r][1];
			try {
				log.info("testing verifying Page Content for element no. " + r);
				returnValue = webPage.findObjectByxPath(Fieldxpath).isDisplayed();
				softAssert.assertTrue(returnValue,
						"Verify Page content failed!!! " + FieldName + "Not rendered on page");
				log.info("testing verifyPageContent Completed------>");
			} catch (Exception e) {
				log.info("Failed to verifying Page Content for element no. " + r);
				softAssert.fail(e.getMessage());
				CreditAppPage.navigateToCreditAppPage(softAssert);
			}
			log.info("Ending verify_Page_Content");
		}
		softAssert.assertAll();
	}

	/**
	 * Test Case - 004 Verify all Link on Yes Money credit application page are
	 * functional
	 * 
	 * @throws Exception
	 * 
	 */
	@Test(priority = 4, enabled = true, description = "verify Link Navigation")
	public void verify_Link_Navigation() throws Exception {
		log.info("testing verifyLinkNavigation started------>");
		SoftAssert softAssert = new SoftAssert();
		CreditAppPage.navigateToCreditAppPage(softAssert);
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "CreditApp", "verifyLinksforNewUser");
		String linkName = null;
		String locator = null;
		String ExpectedURL = null;
		for (int r = 0; r < test.length; r++) {
			linkName = test[r][0];
			locator = test[r][1];
			ExpectedURL = test[r][2];
			try {
				log.info("Verifying Link --->" + linkName);
				CreditAppPage.validateLinkRedirection(linkName, locator, ExpectedURL);
				log.info("testing verify_Link_Navigation completed------>");
			} catch (Throwable e) {
				softAssert.fail(e.getLocalizedMessage());
				CreditAppPage.navigateToCreditAppPage(softAssert);
			}
		}
		softAssert.assertAll();
	}

	@Test(priority = 5, enabled = true, description = "verify form is rendered with blank fields")
	public void verify_Form_Is_Displayed_With_Blank_Field() throws Exception {
		// try{
		log.info("testing verifyLinkNavigation started------>");
		SoftAssert softAssert = new SoftAssert();
		CreditAppPage.navigateToCreditAppPage(softAssert);
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "CreditApp", "verifyFormIsRenderedWithBlankFields");
		CreditAppPage.verifyTextFieldValue(test, softAssert);
		log.info("testing verify_Form_Is_Displayed_With_Blank_Field completed------>");
		softAssert.assertAll();
	}

	@Test(priority = 6, enabled = true, description = "verify Mandatory Field Validation WithoutData")
	public void verify_Mandatory_Field_Validation_Without_Data() throws Exception {
		log.info("testing verifyMandatoryFieldValidationWithoutData started------>");
		SoftAssert softAssert = new SoftAssert();
		CreditAppPage.navigateToCreditAppPage(softAssert);
		commonMethods.clickElementbyXpath(webPage, commonData.get("SubmitButton"), softAssert);
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
				"verifyMandatoryFieldValidationWithoutData");
		for (int i = 0; i < test.length; i++) {
			CreditAppPage.verifyErrorMessageByXpath(softAssert, test[i][0], test[i][1], test[i][2]);
		}
		log.info("testing verify_Mandatory_Field_Validation_Without_Data completed------>");
		softAssert.assertAll();
	}

	/**
	 * Test Case 007 - Verify field validation Error Message for fields with
	 * invalid data : Email,city,ZipCode,Cell Phone,Home phone,Alternate
	 * Phone,Monthly Mortage Rent,Monthly Income,Other income
	 * 
	 * @throws Exception
	 */
	@Test(priority = 7, enabled = true, description = "verify Error Msg With Blank Data")
	public void verify_Field_Validation_Error_Message_With_InValid_Data() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		log.info("testing verifyFieldValidationErrorMessageWithInValidData started------>");
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
				"verifyFieldValidationErrorMessageWithInValidData");
		CreditAppPage.navigateToCreditAppPage(softAssert);
		for (int r = 0; r < test.length; r++) {
			CreditAppPage.verifyErrorMessageWithInvalidDataById(softAssert, test[r][0], test[r][1], test[r][2],
					test[r][3], test[r][4]);
		}
		softAssert.assertAll();
	}

	/**
	 * Test Case 008 - Verify City and State Auto populates after entering valid
	 * Zip Code
	 * 
	 * @throws Exception
	 */
	@Test(priority = 8, enabled = true, description = "Verify Field Auto Populates")
	public void verify_Field_Auto_Populates() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		log.info("testing verifyFieldValidationErrorMessageWithInValidData started------>");
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "CreditApp", "verifyFieldAutoPopulates");
		CreditAppPage.navigateToCreditAppPage(softAssert);
		commonMethods.sendKeysById(webPage, test[0][0], test[0][1], softAssert);
		commonMethods.clickElementById(webPage, test[0][2], softAssert);
		softAssert.assertEquals(test[0][3], commonMethods.getTextbyId(webPage, test[0][2], softAssert));
		softAssert.assertEquals(test[0][5],
				CreditAppPage.getSelectedValueFromDropDownID(softAssert, test[0][0], test[0][4]));
	}

	/**
	 * Test Case 009 - Verify Verify Years There Drop Down Values
	 * 
	 * @throws Exception
	 */
	@Test(priority = 9, enabled = true, description = "verify Years There Drop Down Values")
	public void verify_Years_There_DropDown_Values() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		log.info("testing verifyYearsThereDropDownValues started------>");
		String[][] test = ExcelUtil.readExcelData(DataFilePath, "CreditApp", "verifyYearsThereDropDownValues");
		String[][] yearsThereDropDownValues = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
				"yearsThereDropDownValues");
		CreditAppPage.navigateToCreditAppPage(softAssert);
		CreditAppPage.verifyDropDownValuesById(softAssert, test[0][0], test[0][1], yearsThereDropDownValues);
		softAssert.assertAll();
	}

	/**
	 * Test Case 010 - verify City And State Fields Are Editable
	 * 
	 * @throws Exception
	 */
	@Test(priority = 10, enabled = true, description = "verify City And State Fields Are Editable")
	public void verify_City_And_State_Field_Are_Editable() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		log.info("testing verifyFieldValidationErrorMessageWithInValidData started------>");
		LinkedHashMap<String, String> testData = CommonMethods.getDataInHashMap(DataFilePath, "CreditApp",
				"verifyCityAndStateFieldAreEditable");
		CreditAppPage.navigateToCreditAppPage(softAssert);
		commonMethods.sendKeysById(webPage, testData.get("ZipcodeID"), testData.get("ZipcodeValue"), softAssert);
		commonMethods.clickElementById(webPage, testData.get("CityID"), softAssert);
		CreditAppPage.verifyTextFieldIsEditableByID(softAssert, "City", testData.get("CityID"),
				testData.get("CityValue"));
		CreditAppPage.verifyDropDownFieldIsEditableById(softAssert, "State", testData.get("StateID"),
				testData.get("StateValue"));
		log.info("testing verify_City_And_State_Field_Are_Editable completed------>");
	}

	/**
	 * Test Case 011 - verify Main Source Of Income Field
	 * 
	 * @throws Exception
	 */
	@Test(priority = 11, enabled = true, description = "verify Main Source Of Income Field")
	public void verify_Main_Source_Of_Income_Field() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		CreditAppPage.navigateToCreditAppPage(softAssert);
		log.info("testing verifyFieldValidationErrorMessageWithInValidData started------>");
		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp", "verifyMainSourceOfIncomeField");
		for (int i = 0; i < testData.length; i++) {
			CreditAppPage.verifyDropDownFieldIsEditableByXpath(softAssert, testData[0][0], testData[0][1],
					testData[i][2]);
		}
		for (int j = 0; j < testData.length; j++) {
			CreditAppPage.selectValueFromDropDownByXpath(softAssert, testData[0][0], testData[0][1], testData[j][2]);
			String[][] MonthlyIncomeTestData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
					testData[j][2] + "Data");
			CreditAppPage.fillForm(softAssert, MonthlyIncomeTestData);
			// CreditAppPage.sendTextToTextFieldsById(softAssert,
			// MonthlyIncomeTestData);
			CreditAppPage.navigateToCreditAppPage(softAssert);
			Thread.sleep(3000);
		}
		log.info("testing verify_Main_Source_Of_Income_Field completed------>");
	}

	/**
	 * Test Case - 008 Verify user is successfully able to submit from after
	 * entering valid data in all mandatory fields
	 * 
	 * @throws Exception
	 */
	@Test(priority = 12, enabled = true, description = "verify Error Msg With Blank Data")
	public void verify_Valid_User_Successful_Submit_For_New_User() throws Exception {
		log.info("testing verifyValidUserSuccessfulSubmitForNewUser started------>");
		SoftAssert softAssert = new SoftAssert();
		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
				"verifyValidUserSuccessfulSubmitForNewUser");
		CreditAppPage.navigateToCreditAppPage(softAssert);
		CreditAppPage.fillForm(softAssert, testData);
		// CreditAppPage.submitCreditAppForNewUser(softAssert);
		// Thread.sleep(10000);
		softAssert.assertAll();
		log.info("testing flow verify_Valid_User_Successful_Submit_For_New_User Completed");
	}

	@Test(priority = 13, enabled = true, description = "verify Successful Submit For Registered User")
	public void verify_Credit_App_Submit_With_RefField() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp", "verifyCreditAppSubmitWithRefField");
		CreditAppPage.navigateToCreditAppPage(softAssert);
		CreditAppPage.fillForm(softAssert, testData);
		softAssert.assertAll();
	}

	@Test(priority = 14, enabled = true, description = "verify Successful Submit For Registered User")
	public void verify_Credit_App_Submit_With_Main_Source_Of__Income_As_Employed() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
				"verifyCreditAppSubmitWithMainSourceOfIncomeAsEmployed");
		CreditAppPage.navigateToCreditAppPage(softAssert);
		CreditAppPage.fillForm(softAssert, testData);
		softAssert.assertAll();
	}

	@Test(priority = 15, enabled = true, description = "verify Successful Submit For Registered User")
	public void verify_Credit_App_Submit_With_Main_Source_Of_Income_As_Social_Security() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
				"verifyCreditAppSubmitWithMainSourceOfIncomeAsSocialSecurity");
		CreditAppPage.navigateToCreditAppPage(softAssert);
		CreditAppPage.fillForm(softAssert, testData);
		softAssert.assertAll();
	}

	@Test(priority = 16, enabled = true, description = "verify Successful Submit For Registered User")
	public void verify_Credit_App_Submit_With_Main_Source_Of_Income_As_Disability_Income() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
				"verifyCreditAppSubmitWithMainSourceOfIncomeAsDisabilityIncome");
		CreditAppPage.navigateToCreditAppPage(softAssert);
		CreditAppPage.fillForm(softAssert, testData);
		softAssert.assertAll();
	}

	@Test(priority = 17, enabled = true, description = "verify Successful Submit For Registered User")
	public void verify_Credit_App_Submit_With_Main_Source_Of_Income_As_Retired() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
				"verifyCreditAppSubmitWithMainSourceOfIncomeAsRetired");
		CreditAppPage.navigateToCreditAppPage(softAssert);
		CreditAppPage.fillForm(softAssert, testData);
		softAssert.assertAll();
	}

	@Test(priority = 18, enabled = true, description = "verify Successful Submit For Registered User")
	public void verify_Credit_App_Submit_With_Main_Source_Of_Income_As_Spous_And_Partner() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
				"verifyCreditAppSubmitWithMainSourceOfIncomeAsSpousAndPartner");
		CreditAppPage.navigateToCreditAppPage(softAssert);
		CreditAppPage.fillForm(softAssert, testData);
		softAssert.assertAll();
	}

	/**
	 * logins from creditApp page and verifies if data is auto populated
	 * 
	 * @throws Exception
	 */
	@Test(priority = 19, enabled = true, description = "verify Successful Submit For Registered User")
	public void verify_Successful_Submit_For_Registered_User() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		CreditAppPage.navigateToCreditAppPage(softAssert);
		CreditAppPage.loginFromCreditApp(softAssert);
		CreditAppPage.navigateToCreditAppPage(softAssert);
		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
				"verifySuccessfulSubmitForRegisteredUser");
		CreditAppPage.verifyFieldValues(testData, softAssert);
		softAssert.assertAll();
	}

	@Test(priority = 20, enabled = true, description = "verify Successful Submit For Registered User")
	public void verify_First_Name_And_Last_Name_Field_Are_Editable() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		CreditAppPage.navigateToCreditAppPage(softAssert);
		LinkedHashMap<String, String> testData = CommonMethods.getDataInHashMap(DataFilePath, "CreditApp",
				"verifyFirstNameAndLastNameFieldAreEditable");
		if (!CreditAppPage.verifyTextFieldIsEditableByXpath(softAssert, "FirstName",
				testData.get("FirstNameIdentifier"), testData.get("FirstNameData")))
			softAssert.fail("TextBox \"FirstName\" is Not editable. Unable to set new value as : "
					+ testData.get("FirstNameData"));
		if (!CreditAppPage.verifyTextFieldIsEditableByXpath(softAssert, "LastName", testData.get("LastNameIdentifier"),
				testData.get("LastNameData")))
			softAssert.fail("TextBox \"FirstName\" is Not editable. Unable to set new value as : "
					+ testData.get("FirstNameData"));
		softAssert.assertAll();
	}

	@Test(priority = 21, enabled = true, description = "verify Successful Submit For Registered User")
	public void verify_Email_Is_Not_Editable() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		CreditAppPage.navigateToCreditAppPage(softAssert);
		LinkedHashMap<String, String> testData = CommonMethods.getDataInHashMap(DataFilePath, "CreditApp",
				"verifyEmailIsNotEditable");
		if (CreditAppPage.verifyTextFieldIsEditableByXpath(softAssert, "FirstName", testData.get("EmailIdentifier"),
				testData.get("EmailData")))
			softAssert.fail(
					"TextBox \"Email Address\" is Editable. Able to set new value as : " + testData.get("EmailData"));
		softAssert.assertAll();
	}

	@Test(priority = 22, enabled = true, description = "verify Successful Submit For Registered User")
	public void verify_Sign_In_Link_Is_Not_Displayed_For_Registered_User() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		CreditAppPage.navigateToCreditAppPage(softAssert);
		if (CreditAppPage.verifyElementisPresent(webPage, commonData.get("SignInNowLink"), softAssert))
			softAssert.fail("Sign In Now Link is Displayed For Registered User");
		softAssert.assertAll();
	}

	@Test(priority = 23, enabled = true, description = "verify Successful Submit For Registered User")
	public void verify_Registered_User_Is_Able_To_Fill_Mandatory_Fields() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String[][] testData = ExcelUtil.readExcelData(DataFilePath, "CreditApp",
				"verifyRegisteredUserIsAbleToFillMandatoryFields");
		CreditAppPage.navigateToCreditAppPage(softAssert);
		CreditAppPage.fillForm(softAssert, testData);
		softAssert.assertAll();
	}
}
