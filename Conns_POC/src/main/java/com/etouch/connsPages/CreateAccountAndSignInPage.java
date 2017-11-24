package com.etouch.connsPages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.etouch.common.CommonMethods;
import com.etouch.common.CommonPage;
import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.driver.web.WebDriver;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.taf.webui.ITafElement;
import com.etouch.taf.webui.selenium.WebPage;

public class CreateAccountAndSignInPage extends CommonPage {

	/**
	 *
	 * @param sbPageUrl
	 *            the sb page url
	 * @param webPage
	 *            the web page
	 */

	static Log log = LogUtil.getLog(ConnsHomePage.class);
	String testType;
	String testBedName;
	static CommonMethods commonMethods = new CommonMethods();
	public CreateAccountAndSignInPage(String sbPageUrl, WebPage webPage) 
	{
		super(sbPageUrl, webPage);
		CommonUtil.sop("webDriver in eTouchWebSite Page : " + webPage.getDriver());
		//loadPage();
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
	public static String getToolTipText(WebPage webPage, SoftAssert softAssert, String locator) throws PageException, InterruptedException {
		   /* Actions action = new Actions(webPage.getDriver());
		    WebElement element = webPage.getDriver().findElement(By.xpath(locator));
		    Thread.sleep(2000);
		    action.moveToElement(element).build().perform();
		    String ToolTipText = element.getAttribute("title");
		    return ToolTipText;*/
		
		String tooltipTxt = webPage.getDriver().findElement(By.xpath(locator)).getAttribute("title");
		//Assert.assertEquals(actualTooltipTxt, tooltipTxt);
		return tooltipTxt;
}
	
	
	
	
	public static void verify_Contact_Information_Tab_Address_Book_Page_Additional_Address_Entries (String[][] inputdata) throws PageException, InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		JavascriptExecutor js = (JavascriptExecutor) webPage.getDriver();
		for (int q = 0; q < inputdata.length; q++) {
			Thread.sleep(1000);

			String First_Name_Locator = inputdata[q][4];
			String Last_Name_Locator = inputdata[q][5];
			String Company_Name_Locator = inputdata[q][6];
			String Telephone_Number_Locator = inputdata[q][7];
			String Fax_Number_Locator = inputdata[q][8];
			String Street_Address_1_Locator = inputdata[q][9];
			String Street_Address_2_Locator = inputdata[q][10];
			String City_Name_Locator = inputdata[q][11];
			String Zip_Postal_Code_Locator = inputdata[q][12];
			String State_Province_Dropdown_Locator = inputdata[q][13];
			String Country_Dropdown_Locator = inputdata[q][14];
			String First_Name_Input = inputdata[q][15];
			String Last_Name_Input = inputdata[q][16];
			String Company_Name_Input = inputdata[q][17];			
			String Telephone_Number_Input = inputdata[q][18];			
			String Fax_Number_Input = inputdata[q][19];
			String Street_Address_1_Input = inputdata[q][20];
			String Street_Address_2_Input = inputdata[q][21];
			String City_Name_Input = inputdata[q][22];
			String Zip_Postal_Code_Input = inputdata[q][23];
			String State_Province_Dropdown_Input = inputdata[q][24];
			String Country_Drop_Down_Input = inputdata[q][25];
			String Save_Button_Locator = inputdata[q][26];
			String Expected_Address_Book_Page_URL = inputdata[0][29];
			
try {
	
			commonMethods.clickElementbyXpath(webPage, First_Name_Locator, softAssert);
			/*WebElement First_Name_Locator_Element = webPage.getDriver().findElement(By.xpath(First_Name_Locator));			
			js.executeScript("arguments[0].click();", First_Name_Locator_Element);*/
			commonMethods.clearElementbyXpath(webPage, First_Name_Locator, softAssert);
			commonMethods.sendKeysbyXpath(webPage, First_Name_Locator, First_Name_Input, softAssert);
			
			commonMethods.clickElementbyXpath(webPage, Last_Name_Locator, softAssert);
			/*WebElement Last_Name_Locator_Element = webPage.getDriver().findElement(By.xpath(Last_Name_Locator));
			js.executeScript("arguments[0].click();", Last_Name_Locator_Element);	*/		
			commonMethods.clearElementbyXpath(webPage, Last_Name_Locator, softAssert);
			commonMethods.sendKeysbyXpath(webPage, Last_Name_Locator, Last_Name_Input, softAssert);
			
			commonMethods.clickElementbyXpath(webPage, Company_Name_Locator, softAssert);
			/*WebElement Company_Name_Locator_Element = webPage.getDriver().findElement(By.xpath(Company_Name_Locator));			
			js.executeScript("arguments[0].click();", Company_Name_Locator_Element);*/
			commonMethods.clearElementbyXpath(webPage, Company_Name_Locator, softAssert);
			commonMethods.sendKeysbyXpath(webPage, Company_Name_Locator, Company_Name_Input, softAssert);
			
			commonMethods.clickElementbyXpath(webPage, Telephone_Number_Locator, softAssert);
			/*WebElement Telephone_Number_Locator_Element = webPage.getDriver().findElement(By.xpath(Telephone_Number_Locator));			
			js.executeScript("arguments[0].click();", Telephone_Number_Locator_Element);*/
			commonMethods.clearElementbyXpath(webPage, Telephone_Number_Locator, softAssert);
			commonMethods.sendKeysbyXpath(webPage, Telephone_Number_Locator, Telephone_Number_Input, softAssert);
			
			commonMethods.clickElementbyXpath(webPage, Fax_Number_Locator, softAssert);
			/*WebElement Fax_Number_Locator_Element = webPage.getDriver().findElement(By.xpath(Fax_Number_Locator));			
			js.executeScript("arguments[0].click();", Fax_Number_Locator_Element);*/
			commonMethods.clearElementbyXpath(webPage, Fax_Number_Locator, softAssert);
			commonMethods.sendKeysbyXpath(webPage, Fax_Number_Locator, Fax_Number_Input, softAssert);
			
			commonMethods.clickElementbyXpath(webPage, Street_Address_1_Locator, softAssert);
			/*WebElement Street_Address_1_Locator_Element = webPage.getDriver().findElement(By.xpath(Street_Address_1_Locator));
			js.executeScript("arguments[0].click();", Street_Address_1_Locator_Element);*/
			commonMethods.clearElementbyXpath(webPage, Street_Address_1_Locator, softAssert);
			commonMethods.sendKeysbyXpath(webPage, Street_Address_1_Locator, Street_Address_1_Input, softAssert);
			
			commonMethods.clickElementbyXpath(webPage, Street_Address_2_Locator, softAssert);
			/*WebElement Street_Address_2_Locator_Element = webPage.getDriver().findElement(By.xpath(Street_Address_2_Locator));
			js.executeScript("arguments[0].click();", Street_Address_2_Locator_Element);*/
			commonMethods.clearElementbyXpath(webPage, Street_Address_2_Locator, softAssert);
			commonMethods.sendKeysbyXpath(webPage, Street_Address_2_Locator, Street_Address_2_Input, softAssert);
			
			commonMethods.clickElementbyXpath(webPage, City_Name_Locator, softAssert);
			/*WebElement City_Name_Locator_Element = webPage.getDriver().findElement(By.xpath(City_Name_Locator));
			js.executeScript("arguments[0].click();", City_Name_Locator_Element);*/
			commonMethods.clearElementbyXpath(webPage, City_Name_Locator, softAssert);
			commonMethods.sendKeysbyXpath(webPage, City_Name_Locator, City_Name_Input, softAssert);
			
			log.info("Zip_Postal_Code_Locator Will be Clicked" );
			commonMethods.clickElementbyXpath(webPage, Zip_Postal_Code_Locator, softAssert);
			/*WebElement Zip_Postal_Code_Locator_Element = webPage.getDriver().findElement(By.xpath(Zip_Postal_Code_Locator));			
			js.executeScript("arguments[0].click();", Zip_Postal_Code_Locator_Element);*/
			commonMethods.clearElementbyXpath(webPage, Zip_Postal_Code_Locator, softAssert);
			commonMethods.sendKeysbyXpath(webPage, Zip_Postal_Code_Locator, Zip_Postal_Code_Input, softAssert);
			log.info("Zip_Postal_Code_Input Entered" );
			
			log.info("State_Province_Dropdown_Locator Will be Clicked" );
			Select State_Provice_Drop_Down = new Select(webPage.getDriver().findElement(By.xpath(State_Province_Dropdown_Locator)));
			State_Provice_Drop_Down.selectByVisibleText(State_Province_Dropdown_Input);
			log.info("State_Province_Dropdown_Input Will be Entered" );
			
			log.info("Country_Dropdown_Locator Will be Clicked" );
			Select Country_Drop_Down = new Select(webPage.getDriver().findElement(By.xpath(Country_Dropdown_Locator)));
			Country_Drop_Down.selectByVisibleText(Country_Drop_Down_Input);
			log.info("Country_Drop_Down_Input Entered" );
			
			
			
		
			
			/*CommonMethods.waitForGivenTime(5);
			commonMethods.clickElementbyXpath(webPage, Save_Button_Locator, softAssert);
			CommonMethods.waitForGivenTime(5);*/
			
}
catch (Exception e)

{
  e.printStackTrace();	
  System.out.println("****************** Unable to click Save Button ***********************" +e);
  
}

		}
	}
	
	
	
	
	public static String CreateNewEmailID() throws InterruptedException
	 {	CommonMethods.waitForGivenTime(2);
		return getID()+"@gmail.com"; 
	 }
	
	public static String getID()
	 {
	   //int n=351;
	   double d=0;
	   int num=0;    
	   String Random;   
	   {                               
	    while(true)                
	    {               
	      int final_limit=100000; //Specify the maximum limit               
	      d=Math.random()*final_limit;                
	      num=(int)d;              
	      Random = "Test" + String.valueOf(num);
	      break;
	    }                   
	    return Random;     
	   }
	 }
}	