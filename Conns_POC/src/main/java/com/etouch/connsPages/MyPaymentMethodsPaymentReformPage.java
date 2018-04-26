package com.etouch.connsPages;



import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import org.testng.asserts.SoftAssert;

import com.etouch.common.CommonMethods;
import com.etouch.common.CommonPage;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.taf.webui.ITafElement;
import com.etouch.taf.webui.selenium.WebPage;

public class MyPaymentMethodsPaymentReformPage extends CommonPage {
	/**
	 *
	 * @param sbPageUrl
	 *            the sb page url
	 * @param webPage
	 *            the web page
	 */

	static Log log = LogUtil.getLog(MyPaymentMethodsPaymentReformPage.class);
	String testType;
	//private String testBedName;
	static CommonMethods commonMethods = new CommonMethods();
	SoftAssertor SoftAssertor;

	
	public MyPaymentMethodsPaymentReformPage(String sbPageUrl, WebPage webPage) 
	{
		super(sbPageUrl, webPage);
		CommonUtil.sop("webDriver on ConnsWebSite Page : " + webPage.getDriver());
		//loadPage();
	}
	
	public static void find_Store_Page_selectDropdownByValue_clickElementByJS_And_WaitForWebElement_Functionality (WebPage webPage,String Locator_4,String dropdownValue,String Locator_2,String Locator_B,SoftAssert softAssert) 
	{ 
		try {
		
		selectDropdownByValue(webPage, Locator_4,dropdownValue, softAssert);
		clickElementByJS_And_WaitForWebElement(webPage, Locator_2,Locator_B, softAssert);
	
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	public static void selectDropdownByValue (WebPage webPage,String locator,String dropdownValue,SoftAssert softAssert) 
	{ try {
		commonMethods.selectDropdownByValue(webPage, locator,dropdownValue, softAssert);
		//commonMethods.selectDropdownByText(webPage, locator,dropdownValue, softAssert);
		}
	catch (Exception e){
		e.printStackTrace();
	}
	}

	
	public static void HamburgerIconHandling(WebPage webPage, String testBedName,String testType,String [][]testdata, String [][] linkData,SoftAssert softAssert) {
		JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
		try {
			log.info("Started iteration------>");
			log.info(" ************* Handling Hamburger Icon Starts on My Payment Methods Main Page *********");	

			if (testType.equalsIgnoreCase("Mobile") || testBedName.equalsIgnoreCase("edge")) {
			for (int i = 3; i < 5; i++) {
				log.info("Started iteration" + i);
				
					log.info("Inside else if iPhoneNative : " + testBedName.toString());
					WebElement element_1 = webPage.getDriver().findElement(By.xpath(testdata[i][1]));
					js.executeScript("arguments[0].click();", element_1);
					CommonMethods.waitForGivenTime(20);
					/*WebElement element_2 = webPage.getDriver().findElement(By.xpath(testdata[i][1]));
					js.executeScript("arguments[0].click();", element_2);*/
					log.info(" ************* PageBrokenLinkVerification Starts on My Payment Methods Main Page *********");	
					PageBrokenLinkVerification( webPage, linkData, softAssert);
				}
			}else {
				for (int i = 4; i < 5; i++) {
					log.info("Inside else Web");
					CommonMethods.waitForGivenTime(10);
					/*WebElement element_2 = webPage.getDriver().findElement(By.xpath(testdata[i][1]));
					js.executeScript("arguments[0].click();", element_2);*/
					log.info(" ************* PageBrokenLinkVerification Starts on My Payment Methods Main Page *********");	
					PageBrokenLinkVerification( webPage, linkData, softAssert);
				}
			}
			}
		
		catch (Exception e){
			e.printStackTrace();
		}
		}
		

	
/*public static void DropdownValueAllOptions (WebPage webPage,String locator,FileInputStream fileInput ,SoftAssert softAssert) 
{ 
	try {
		//commonMethods.getAllOptions(webPage,locator);

		log.info("***************** MyPaymentMethod_CreditCard_Panel_State_CompareDropDownValues *************************");

        commonMethods.compareDropDownValues(webPage,fileInput,locator);
      
	}
        catch (Exception e){
    		e.printStackTrace();
    	}
    	}*/

public static void CompareDropdownValueAllOptions (WebPage webPage,String filePath,String locator,SoftAssert softAssert) 
{ 
	try {
		log.info("***************** MyPaymentMethod_CreditCard_Panel_CompareDropDownValues *************************");
        commonMethods.compareDropDownValues(webPage,filePath,locator);      
	}    catch (Exception e){
    		e.printStackTrace();
    	}
    	}
	
	
	public static void SaveButtonValidation (WebPage webPage,String[][] inputData,SoftAssert softAssert) 
	{ JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
	try {
		
		
		CommonMethods.waitForGivenTime(5);
		WebElement savebutton = webPage.getDriver().findElement(By.xpath(inputData[8][1]));
		js.executeScript("arguments[0].removeAttribute('disabled');", savebutton);

		if(savebutton.isDisplayed() && savebutton.isEnabled())
		commonMethods.scrollToElement(webPage, inputData[8][1], softAssert);

		js.executeScript("arguments[0].click();", savebutton);
		commonMethods.waitForPageLoad(webPage, softAssert);	
		CommonMethods.waitForGivenTime(5);
		}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	public static void MyPaymentMethod_Confirmation_Message_Page_Back_Button (WebPage webPage,String[][] inputData,SoftAssert softAssert) 
	{ JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
	try {
		
		
		CommonMethods.waitForGivenTime(5);
		WebElement backbutton = webPage.getDriver().findElement(By.xpath(inputData[12][1]));
		if(backbutton.isDisplayed() && backbutton.isEnabled())
		commonMethods.scrollToElement(webPage, inputData[12][1], softAssert);
		js.executeScript("arguments[0].click();", backbutton);
		commonMethods.waitForPageLoad(webPage, softAssert);	
		CommonMethods.waitForGivenTime(5);		
		}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	
	public static void verifyErrorMessageByXpath(WebPage webPage,SoftAssert softAssert, String errorMessageFieldName, String locator,
			String expectedErrorMessage) throws PageException {
		log.info("Verifying error message for : " + errorMessageFieldName + " : Expected Message : "
				+ expectedErrorMessage);
		CommonMethods.waitForWebElement(By.xpath(locator), webPage);
		String actualErrorMessage = commonMethods.getTextbyXpath(webPage, locator, softAssert);
		softAssert.assertTrue(expectedErrorMessage.equals(actualErrorMessage), "Failed to verify error field :"
				+ errorMessageFieldName + " : Expected : " + expectedErrorMessage + " Actual : " + actualErrorMessage);
	}

	
	public static void PageElementClick (WebPage webPage,String ElementXpath,SoftAssert softAssert) 
	{  JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
	try {
		commonMethods.waitForPageLoad(webPage, softAssert);
		WebElement element = webPage.getDriver().findElement(By.xpath(ElementXpath));
		js.executeScript("arguments[0].click();", element);
		commonMethods.waitForPageLoad(webPage, softAssert);
		CommonMethods.waitForGivenTime(4);
		}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	
	public static void ImageVerification (WebPage webPage,SoftAssert softAssert) 
	{  
	try {
		commonMethods.verifyBrokenImage(webPage);
		
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println(e.getMessage());
	}
	}
	
	
	public static void RowsTextContents (WebPage webPage,SoftAssert softAssert) 
	{  
	try {
		WebElement htmltable=webPage.getDriver().findElement(By.xpath("//*[@id='paymentmethods']/table"));
		List<WebElement> rows=htmltable.findElements(By.tagName("tr"));
		log.info("Current Number of rows under Payment Methods Table is  :"+rows.size());
		for(int i=1;i<rows.size();i++)
		{			
		log.info("Each Rows Text : " +rows.get(i).getText());
		}
		
		
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println(e.getMessage());
	}
	}
	
	public static void verifyBrokenLinksForGivenLinks(WebPage webPage,List<WebElement> linkList) 
	{	try {
		commonMethods.verifyBrokenLinksForGivenLinks(webPage, linkList);
		Thread.sleep(4000);
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	
	
	public static void Button_Xpath_Validation (WebPage webPage,String[][] inputData,String ButtonXpath,SoftAssert softAssert) 
	{ JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
	try {
		
		
		CommonMethods.waitForGivenTime(5);
		
		WebElement AddNewPaymentMethod = webPage.getDriver().findElement(By.xpath(ButtonXpath));
		if(AddNewPaymentMethod.isDisplayed() && AddNewPaymentMethod.isEnabled())
		commonMethods.scrollToElement(webPage, ButtonXpath, softAssert);
		js.executeScript("arguments[0].click();", AddNewPaymentMethod);
		commonMethods.waitForPageLoad(webPage, softAssert);	
		CommonMethods.waitForGivenTime(5);
		}
	catch (Exception e){
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
	
	public static void PageBrokenLinkVerification(WebPage webPage,String[][] inputData,SoftAssert softAssert) 
	{ 
	try {	log.info(" *********** PageBrokenLinkVerification Starts **********************");	
		
		for (int i = 0; i < inputData.length; i++) {
			{
				ITafElement link = webPage.findObjectByxPath(inputData[i][1]);
				log.info("iteration " + i + " : " + link.getAttribute("href"));
				
				HttpClient client = HttpClientBuilder.create().build();
				HttpGet request = new HttpGet(link.getAttribute("href"));
				HttpResponse response = client.execute(request);
				log.info("Status code for iteration " + i + " : " + response.getStatusLine().getStatusCode());
				softAssert.assertEquals(response.getStatusLine().getStatusCode(), 200,
						"Validation for " + inputData[i][0] + " :");
			}
		}
		}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	
	
	public static void FontSizeVerification(WebPage webPage,String testType,String testBedName,String[][] ExpectedFontValuesMobile,String[][]ExpectedFontValuesTab,String[][]ExpectedFontValuesWeb, SoftAssert softAssert) 
	{ 	JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
	try {		
		log.info("testType : "+testType);
		log.info("testBedName : "+testBedName);
		int width = ((Long) js.executeScript("return window.innerWidth || document.body.clientWidth")).intValue();
	log.info("width value calculated is :" + width);
	int height = ((Long) js.executeScript("return window.innerHeight || document.body.clientHeight"))
			.intValue();
	log.info("height value calculated is :" + height);
	Dimension dimension = new Dimension(width, height);
	System.out.println("Dimensions" + dimension);
	if ((testType.equalsIgnoreCase("Web"))) {
		log.info("********************TestType for Web started execution***************   : "
				+ testType.toString()  );
		log.info("********************TestBedName for Web started execution***************   "+testBedName.toString());
		for (int i = 0; i < ExpectedFontValuesWeb.length; i++) {
			log.info("Iteration under test  is : " + i + " :: Item under test is : "+ ExpectedFontValuesWeb[i][0]);
			List<String> actualCssValues = commonMethods.getFontProperties(webPage, ExpectedFontValuesWeb[i][1],softAssert);
			log.info("ExpectedFontValuesWeb : " +ExpectedFontValuesWeb[i][2]);
			//log.info("Actual_SuccessMessage_Text  : " +ExpectedFontValuesWeb[i][0]);

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

	
	public static void SuccessMessageValidation (WebPage webPage,String[][] inputData,String SuccessMessageXPATH,String ExpectedSuccessMessageTEXT,SoftAssert softAssert) 
	{ 
	try {
		
		/******* Actual Success Confirmation Message  vs Expected Success Confirmation Message Validation ******/
		String ActualSuccessMessageXPATH = commonMethods.getTextbyXpath(webPage, SuccessMessageXPATH, softAssert);
		log.info("ActualSuccessMessageXPATH :"+ActualSuccessMessageXPATH);
		log.info("ExpectedSuccessMessageTEXT :"+ExpectedSuccessMessageTEXT);
		softAssert.assertTrue(ActualSuccessMessageXPATH.equals(ExpectedSuccessMessageTEXT));
		}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	public static void PageTitle_PageURL_Validation_1 (String[][] inputData) throws PageException, InterruptedException 
	{ 
		SoftAssert softAssert = new SoftAssert();
		try {
		
		String ActualURL = webPage.getCurrentUrl();
		String ActualPageTitle = webPage.getPageTitle();
		String ExpectedPageTitle = inputData[14][2];			
		String ExpectedURL = inputData[14][1];
		log.info("ActualPageTitle :"+ActualPageTitle);
		log.info("ExpectedPageTitle :"+ExpectedPageTitle);
		softAssert.assertTrue(ActualPageTitle.equals(ExpectedPageTitle));
		
		/****************** Actual URL vs Expected URL Validation ************************************/
		log.info("ActualURL :"+ActualURL);
		log.info("ExpectedURL :"+ExpectedURL);
		softAssert.assertTrue(ActualURL.contains(ExpectedURL));
		//commonMethods.selectDropdownByValue(webPage, locator,dropdownValue, softAssert);
		//commonMethods.selectDropdownByText(webPage, locator,dropdownValue, softAssert);
		
		
		}
		
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	public static void PageTitle_PageURL_Validation_2 (String[][] inputData) throws PageException, InterruptedException 
	{ 
		SoftAssert softAssert = new SoftAssert();
		try {
		
		String ActualURL = webPage.getCurrentUrl();
		String ActualPageTitle = webPage.getPageTitle();
		String ExpectedPageTitle = inputData[14][2];			
		String ExpectedURL = inputData[14][1];
		log.info("ActualPageTitle :"+ActualPageTitle);
		log.info("ExpectedPageTitle :"+ExpectedPageTitle);
		softAssert.assertTrue(ActualPageTitle.equals(ExpectedPageTitle));
		
		/****************** Actual URL vs Expected URL Validation ************************************/
		log.info("ActualURL :"+ActualURL);
		log.info("ExpectedURL :"+ExpectedURL);
		softAssert.assertTrue(ActualURL.contains(ExpectedURL));
		//commonMethods.selectDropdownByValue(webPage, locator,dropdownValue, softAssert);
		//commonMethods.selectDropdownByText(webPage, locator,dropdownValue, softAssert);
		}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	
	public static Long generateRandom(int length) {
	    Random random = new Random();
	    char[] digits = new char[length];
	    digits[0] = (char) (random.nextInt(9) + '1');
	    for (int i = 1; i < length; i++) {
	        digits[i] = (char) (random.nextInt(10) + '0');
	    }
	    return Long.parseLong(new String(digits));
	}

	public static void PageTitle_PageURL_Validation (String[][] inputData,String ExpectedURL, String ExpectedPageTitle) throws PageException, InterruptedException 
	{ 
		SoftAssert softAssert = new SoftAssert();
		try {
		
		String ActualURL = webPage.getCurrentUrl();
		String ActualPageTitle = webPage.getPageTitle();		
		log.info("ActualPageTitle :"+ActualPageTitle);
		log.info("ExpectedPageTitle :"+ExpectedPageTitle);
		softAssert.assertTrue(ActualPageTitle.equals(ExpectedPageTitle));		
		/****************** Actual URL vs Expected URL Validation ************************************/
		log.info("ActualURL :"+ActualURL);
		log.info("ExpectedURL :"+ExpectedURL);
		softAssert.assertTrue(ActualURL.contains(ExpectedURL));
		}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	public static void PageTitle_PageURL_Validation_4 (String[][] inputData,String ExpectedPageTitle,String ExpectedURL) throws PageException, InterruptedException 
	{ 
		SoftAssert softAssert = new SoftAssert();
		try {		
		String ActualURL = webPage.getCurrentUrl();
		String ActualPageTitle = webPage.getPageTitle();		
		log.info("ActualPageTitle :"+ActualPageTitle);
		log.info("ExpectedPageTitle :"+ExpectedPageTitle);
		softAssert.assertTrue(ActualPageTitle.equals(ExpectedPageTitle));		
		/****************** Actual URL vs Expected URL Validation ************************************/
		log.info("ActualURL :"+ActualURL);
		log.info("ExpectedURL :"+ExpectedURL);
		softAssert.assertTrue(ActualURL.contains(ExpectedURL));
		}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	public static void ScrollIntoView (WebPage webPage,String Locator_6,SoftAssert softAssert) 
	{ 		JavascriptExecutor js = (JavascriptExecutor)webPage.getDriver();
		try {
			//executing this query will scroll until that element is not appeared on page.
			Thread.sleep(1000);
			commonMethods.waitForPageLoad(webPage, softAssert);
			WebElement element = commonMethods.findElementByXpath( webPage,  Locator_6,  softAssert);
			js.executeScript("arguments[0].scrollIntoView(true);",element);
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	
	
	public static void clickElementByJS_And_WaitForWebElement (WebPage webPage,String locator_1,String locator_2,SoftAssert softAssert) 
	{ try {
		commonMethods.Click_On_Element_JS(webPage, locator_1, softAssert);
		waitForWebElement(webPage,locator_2, softAssert);	
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
	public static void waitForWebElement(WebPage webPage,  String locator,SoftAssert softAssert) 
	{	try {
		
		commonMethods.waitForPageLoad(webPage, softAssert);
		//commonMethods.waitForWebElement_1(By.xpath(locator), webPage); 
		Thread.sleep(6000);
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
}
