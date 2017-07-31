package com.etouch.connsPages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.asserts.SoftAssert;

import com.etouch.common.CommonMethods;
import com.etouch.common.CommonPage;
import com.etouch.taf.core.datamanager.excel.TestParameters;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.taf.webui.selenium.WebPage;

public class ConnsAccountAndSignInPage extends CommonPage {

	/**
	 *
	 * @param sbPageUrl
	 *            the sb page url
	 * @param webPage
	 *            the web page
	 */

	
	static Log log = LogUtil.getLog(ConnsAccountAndSignInPage.class);
	String testType;
	String testBedName;
	CommonMethods commonMethods = new CommonMethods();

	public ConnsAccountAndSignInPage(String sbPageUrl, WebPage webPage) {
		super(sbPageUrl, webPage);
		log.info("webDriver in Conns Page : " + webPage.getDriver());
		loadPage();		
	}

	public void verifyPageTitle(String[][] testdata) {
		String expurl = testdata[0][0];
		String expTitle = testdata[0][1];

		try {
			log.info("Actual URL of the page is : " + webPage.getCurrentUrl());
			log.info("Actual Title of the page is : " + webPage.getPageTitle());

			SoftAssertor.assertTrue(webPage.getCurrentUrl().contains(expurl));
			SoftAssertor.assertEquals(expTitle, webPage.getPageTitle());

		} catch (Exception e) {
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("verifyPageTitle failed");
			log.error(e.getMessage());
		}
	}
	
	
	
	
	public void verify_Remember_Me_Functionality(String[][] testdata) {
		String RememberMeCheckBox = testdata[0][0];
		String Expected_ToolTip_Text = testdata[0][1];
		log.info("Checkbox verification starts :" + RememberMeCheckBox);
		log.info("Checkbox verification starts : "+ Expected_ToolTip_Text );
		try { log.info("Checkbox verification starts");
			JavascriptExecutor jse = (JavascriptExecutor)webPage.getDriver();
			jse.executeScript("window.scrollTo(0,100)");
		//	WebElement RemembermeCheckBox = webPage.getDriver().findElement(By.cssSelector("#remember-me-box>div>input"));
			WebElement RemembermeCheckBox = webPage.getDriver().findElement(By.cssSelector("Remember_me_CheckBox"));
			 //if ((!(webPage.getDriver().findElement(By.cssSelector(RemembermeCheckBox)).isSelected())) && (webPage.getDriver().findElement(By.cssSelector(RemembermeCheckBox)).isDisplayed() && (webPage.getDriver().findElement(By.cssSelector(RemembermeCheckBox)).isEnabled())))
			if ((!(RemembermeCheckBox).isSelected()) && ((RemembermeCheckBox)).isEnabled())
								
			 { log.info("Checkbox verification begins");
				 boolean checkstatus;
				 checkstatus=RemembermeCheckBox.isSelected();
				 if (checkstatus==false){
					 log.info("Checkbox is already unchecked");
					 RemembermeCheckBox.click();
					 log.info("Checked the checkbox");
				     RemembermeCheckBox.click();
					 log.info("Unchecked the checkbox");
				 }
				
		}  
			else if (((RemembermeCheckBox).isSelected()) && ((RemembermeCheckBox)).isEnabled())
			{ log.info("Checkbox verification begins for when Checkbox is selected");
			boolean checkstatus;
			checkstatus=RemembermeCheckBox.isSelected();
			if (checkstatus==true) {
				RemembermeCheckBox.click();
				log.info("Checkbox is unchecked");
			    RemembermeCheckBox.click();
				log.info("Checkbox is checked");
			    RemembermeCheckBox.click();
				log.info("Checkbox is unchecked");
			}
			else
			{
			log.info("Checkbox is already unchecked");	
			}
		}
			}
			catch (Throwable e) {
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Login failed");
			log.error(e.getMessage());

		}

	}
	
	

	public void verifyLoginFunctionality(String[][] testdata) {
		String EmailAddressLocator = testdata[0][0];
		String EmailAddress = testdata[0][1];
		String PasswordLocator = testdata[0][2];
		String Password = testdata[0][3];
		String LogInButtonLocator = testdata[0][4];
		String TitleOfPage = testdata[0][5];
		try {
			webPage.findObjectByxPath(EmailAddressLocator).sendKeys(EmailAddress);
			webPage.findObjectByxPath(PasswordLocator).sendKeys(Password);
			webPage.findObjectByxPath(LogInButtonLocator).click();

			SoftAssertor.assertEquals(webPage.getPageTitle(), TitleOfPage, "Title of the page does not match");

		} catch (Throwable e) {
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Login failed");
			log.error(e.getMessage());

		}

	}
	
	public void verify_Account_DashBoard_Login(String[][] testdata,SoftAssert softAssert) {
	
		String EmailAddressLocator = testdata[0][2];
		String EmailAddress = testdata[0][3];
		String PasswordLocator =testdata[0][4];
		String Password = testdata[0][5];
		String LogInButtonLocator = testdata[0][6];
		String Navigate_To_OPT_URL = testdata[0][7];
		String Navigate_To_RWD_URL = testdata[0][8];
		//SoftAssert softAssert = new SoftAssert();
		try {	
			webPage.getDriver().navigate().to(Navigate_To_RWD_URL);
			commonMethods.sendKeysbyXpath(webPage, EmailAddressLocator, EmailAddress, softAssert);
			commonMethods.sendKeysbyXpath(webPage, PasswordLocator, Password, softAssert);	
			commonMethods.clickElementbyXpath(webPage, LogInButtonLocator, softAssert);

			//webPage.getDriver().navigate().to(Navigate_To_RWD_URL);
			/*if(testBedName.equalsIgnoreCase("Firefox"))
			{
				commonMethods.sendKeys_usingJavaScriptExecutor(webPage, EmailAddressLocator, EmailAddress, softAssert);
			}
			else
			{
				commonMethods.sendKeysbyXpath(webPage, EmailAddressLocator, EmailAddress, softAssert);
			}
			
			if(testBedName.equalsIgnoreCase("Firefox"))
			{
				commonMethods.sendKeys_usingJavaScriptExecutor(webPage, PasswordLocator, Password, softAssert);	
			}
			else
			{
				commonMethods.sendKeysbyXpath(webPage, PasswordLocator, Password, softAssert);	
			}
			
					
			if(testBedName.equalsIgnoreCase("Firefox"))
			{
				commonMethods.clickElementbyXpath_usingJavaScript(webPage, LogInButtonLocator, softAssert);
			}
			else
			{
				commonMethods.clickElementbyXpath(webPage, LogInButtonLocator, softAssert);
			}

*/
			//commonMethods.clickElementbyXpath(webPage, LogInButtonLocator, softAssert);

		} catch (Throwable e) {
			log.info("Catch block 111");
			//SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Login failed");
			log.error(e.getMessage());

		}

	}
	
	public void verifyLoginFunctionality_Registered_User(String[][] testdata) {
		log.info("Enter");

		String ChildElementLocator = null;
		String ExpectedURL = null;
		String ExpectedElementName = null;
		String actualUrl = null;
		String ActualElementName = null;
		String EmailAddressLocator = testdata[0][0];
		String EmailAddress = testdata[0][1];
		String PasswordLocator = testdata[0][2];
		String Password = testdata[0][3];
		String LogInButtonLocator = testdata[0][4];
		String TitleOfPage = testdata[0][5];
		ExpectedElementName = testdata[0][6];
		ExpectedURL = testdata[0][7];
		ChildElementLocator = testdata[0][8];
	try {
			webPage.findObjectByxPath(EmailAddressLocator).sendKeys(EmailAddress);
			webPage.findObjectByxPath(PasswordLocator).sendKeys(Password);
			webPage.findObjectByxPath(LogInButtonLocator).click();
			ActualElementName = webPage.findObjectByxPath(ChildElementLocator).getText();
			SoftAssertor.assertEquals(ActualElementName, ExpectedElementName, "Element name does not match");
			actualUrl = webPage.getCurrentUrl();
			log.info("Expected URL : " + ExpectedURL);
			log.info("Actual URL : " + actualUrl);
			SoftAssertor.assertTrue(actualUrl.contains(ExpectedURL), "URL of the page does not match");
			SoftAssertor.assertEquals(webPage.getPageTitle(), TitleOfPage, "Title of the page does not match");
			//SoftAssertor.assertEquals(webPage.getPageTitle(), ElementTitle, "Title of the page does not match");
			webPage.getDriver().navigate().back();
			webPage.getDriver().navigate().refresh();
			Thread.sleep(7000);
		} catch (Throwable e) {
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Login failed");
			log.error(e.getMessage());

		}

	}
	
	
	public List<String> verify_Login_Functionality_with_Invalid_Input(String[][] testdata) {
			List<String> errorMessage = new ArrayList<String>();
			String ActualEmailErrMsg="";
			String ActualPwdErrMsg="";
		
			String EmailAddlocator = testdata[1][0];
			String EmailErrlocator = testdata[1][1];
			String EmailAddInput = testdata[1][2];
			String Pwdlocator = testdata[1][4];
			String PwdErrlocator = testdata[1][5];
			String PwdInput = testdata[1][6];
			
			String LoginButtLocator = testdata[1][8];
		try {
		
		if (!(EmailAddInput.equalsIgnoreCase("NA") && PwdInput.equalsIgnoreCase("NA"))) {
		
		webPage.findObjectByxPath(EmailAddlocator).clear();
		webPage.findObjectByxPath(Pwdlocator).clear();
		webPage.findObjectByxPath(EmailAddlocator).sendKeys(EmailAddInput);
		webPage.findObjectByxPath(Pwdlocator).sendKeys(PwdInput);
		webPage.findObjectByxPath(LoginButtLocator).click();
		ActualEmailErrMsg = webPage.findObjectByxPath(EmailErrlocator).getText();
		ActualPwdErrMsg = webPage.findObjectByxPath(PwdErrlocator).getText();
		errorMessage.add(ActualEmailErrMsg);
		errorMessage.add(ActualPwdErrMsg);
		//SoftAssertor.assertEquals(ActualPwdErrMsg, ExpErrMsgPwd, "Password Error msg does not match");
		//SoftAssertor.assertEquals(ActualEmailErrMsg, ExpErrMsgEmail, "Email address error msg does not match");
	}

	}
		catch (Throwable e) {
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Login failed");
			log.error(e.getMessage());

		}
		return errorMessage;

	}
	
	
	
	public List<String> verify_Login_Functionality_with_Blank_Input(String[][] testdata) {
		List<String> errorMessage = new ArrayList<String>();
		String ActualEmailErrMsg="";
		String ActualPwdErrMsg="";
		String EmailAddlocator = testdata[1][0];
		String EmailErrlocator = testdata[1][1];
		String EmailAddInput = testdata[1][2];
		String ExpErrMsgEmail = testdata[1][3];
		String Pwdlocator = testdata[1][4];
		String PwdErrlocator = testdata[1][5];
		String PwdInput = testdata[1][6];
		String LoginButtLocator = testdata[1][8];
		try {
			if (!(EmailAddInput.equalsIgnoreCase("NA") && PwdInput.equalsIgnoreCase("NA"))) {
				webPage.findObjectByxPath(EmailAddlocator).clear();
				webPage.findObjectByxPath(Pwdlocator).clear();
				webPage.findObjectByxPath(EmailAddlocator).sendKeys(EmailAddInput);
				webPage.findObjectByxPath(Pwdlocator).sendKeys(PwdInput);
				webPage.findObjectByxPath(LoginButtLocator).click();
				ActualEmailErrMsg = webPage.findObjectByxPath(EmailErrlocator).getText();
				ActualPwdErrMsg = webPage.findObjectByxPath(PwdErrlocator).getText();
				errorMessage.add(ActualEmailErrMsg);
				errorMessage.add(ActualPwdErrMsg);
				/*	SoftAssertor.assertEquals(ActualEmailErrMsg, ExpErrMsgEmail, "Email address error msg does not match");
	SoftAssertor.assertEquals(ActualPwdErrMsg, ExpErrMsgPwd, "Password Error msg does not match");*/
			}
		}
			catch (Throwable e) {
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Login failed");
			log.error(e.getMessage());
		}
		log.info("Exit the dragon :" +errorMessage.get(0) + errorMessage.get(1));
		return errorMessage;
	}	
	
	static int r = 0;
	public List<String> verify_Links_Account_Tab(String[][] testdata) {
		List<String> brokenLinks = new ArrayList<String>();
		String ParentElementLocator = null;
		String ChildElementLocator = null;
		String Expected_Page_URL = null;
		String Expected_Page_Element_Name = null;
		String Expected_Page_Element_Title = null;
		List<String> Page_URL_Title_Element_Data = new ArrayList<String>();
		String Actual_Page_URL="";
		String Actual_Page_Title="";
		String Actual_Page_Element_Name="";
			try {
				log.info("Verifying " + testdata[r][0]);
				ParentElementLocator = testdata[r][1];
				ChildElementLocator = testdata[r][2];
				Expected_Page_URL = testdata[r][3];
				Expected_Page_Element_Name = testdata[r][4];
				Expected_Page_Element_Title = testdata[r][5];
				log.info("Parent Locator is ..." + ParentElementLocator);
				
				if (!(ParentElementLocator.equalsIgnoreCase("NA"))) {
					webPage.hoverOnElement(By.cssSelector(testdata[r][0]));
				}
				log.info("********** Before Execution ******************");
				Actual_Page_Element_Name = webPage.findObjectByxPath(ChildElementLocator).getText();
				Page_URL_Title_Element_Data.add(Actual_Page_Element_Name);
				//SoftAssertor.assertEquals(Actual_Page_ELement_Name, Expected_Page_Element_Name, "Element name does not match");
				webPage.findObjectByxPath(ChildElementLocator).click();
				String existingWindow = null;
				String newWindow = null;
				existingWindow = webPage.getDriver().getWindowHandle();
				Set<String> windows = webPage.getDriver().getWindowHandles();
				if (windows.size() >= 2) {
					windows.remove(existingWindow);
					newWindow = windows.iterator().next();
					log.info("Existing window id is" + existingWindow);
					log.info("New window id is" + newWindow);
					webPage.getDriver().switchTo().window(newWindow);
					Thread.sleep(3000);
					Actual_Page_URL = webPage.getCurrentUrl();
					Actual_Page_Title = webPage.getPageTitle();
					Page_URL_Title_Element_Data.add(Actual_Page_URL);					
					Page_URL_Title_Element_Data.add(Actual_Page_Title);
					Page_URL_Title_Element_Data.add(Actual_Page_Element_Name);
					
					webPage.getDriver().close();
					webPage.getDriver().switchTo().window(existingWindow);
					log.info("Actual Element Name" + Actual_Page_Element_Name);
					log.info("Expected Element Name" + Expected_Page_Element_Name);
					log.info("Actual Page Title" + Actual_Page_Title);
					log.info("Expected Element Title" + Expected_Page_Element_Title);
					log.info("Expected URL" + Expected_Page_URL);
					log.info("Actual URL" + Actual_Page_URL);
					
				} else {
					log.info("******************* Else Execution***************");
					Actual_Page_URL = webPage.getCurrentUrl();
					Actual_Page_Title = webPage.getPageTitle();
					Page_URL_Title_Element_Data.add(Actual_Page_Element_Name);
					Page_URL_Title_Element_Data.add(Actual_Page_URL);					
					Page_URL_Title_Element_Data.add(Actual_Page_Title);
					log.info("Actual Element Name : " + Actual_Page_Element_Name);
					log.info("Expected Element Name : " + Expected_Page_Element_Name);
					log.info("Actual Page Title : " + Actual_Page_Title);
					log.info("Expected Element Title : " + Expected_Page_Element_Title);
					log.info("Actual URL : " + Actual_Page_URL);
					log.info("Expected URL : " + Expected_Page_URL);
					try {
						if (!Expected_Page_Element_Name.equalsIgnoreCase("« Go back")
								&& !Expected_Page_Element_Name.equalsIgnoreCase("SAVE ADDRESS")
								&& !Expected_Page_Element_Name.equalsIgnoreCase("Newsletter Subscription")) {
							webPage.getDriver().navigate().back();
						}
							} catch (Exception e) {
						webPage.getDriver().navigate().back();
					}
				}

			} catch (Exception e) {
				webPage.getDriver().navigate().back();
				brokenLinks.add(Expected_Page_Element_Name + " " + e.getLocalizedMessage());
				log.info("getLocalizedMessage :"); 
				e.printStackTrace();
			}
			r++;

		if (brokenLinks.size() > 0) {
			Assert.fail("Link " + Arrays.deepToString(brokenLinks.toArray()) + " are not working as expected");
		}
		return Page_URL_Title_Element_Data;
	}


/*	public void verifyFontandSizeOnLoginPage(String [][] test_data)
			throws PageException, InterruptedException {
		Thread.sleep(2000);
		
		
		List <String> content = new ArrayList<String>();
		for (int r = 0; r < test_data.length; r++) {
			try {

				String ActualElementName = webPage.findObjectByxPath(test_data[r][0]).getText();
				String ExpectedElementName = test_data[r][1];
				Assert.assertEquals(ActualElementName, ExpectedElementName, "content does not Match");
				log.info("Expected Content :" + test_data[r][1]);
				log.info("Actual Content :" + ActualElementName);
			} catch (Throwable e) {
	
	int index = 0;
	try {
		List<String> ActualValues = getInfo(inputs.getParamMap().get("Locator"),
				inputs.getParamMap().get("IsAltPresent"), inputs.getParamMap().get("ObjType"),
				.getParamMap().get("isTitlePresent"));
		List<String> exp_pageText = getExpectedInfo(inputs.getParamMap().get("TextAttributes"),
				testBedName);
		Assert.assertEquals(ActualValues, exp_pageText, "content does not Match","verifying font of" + index + "element");
		//verifyGivenValues(ActualValues, exp_pageText, "verifying font of" + index + "element");
	} catch (Exception e) {
		log.error(">--------------Test case verify font on login page failed -------------<" + e.getMessage());
		SoftAssertor.addVerificationFailure(e.getMessage());
		e.printStackTrace();
	}

	finally {
		index++;
	}
	log.info("Verification of font and size completed on Login Page");
	String actualText = webPage.findObjectByxPath(testdata[r][0]).getText();
	}*/
	

	public void verifyContent(String[][] testdata) {
		List <String> content = new ArrayList<String>();
		
		for (int r = 0; r < testdata.length; r++) {
			try {
				String ActualElementName =commonMethods.getTextbyXpath(webPage, testdata[r][0], null);
				String ExpectedElementName = testdata[r][1];
				Assert.assertEquals(ActualElementName, ExpectedElementName, "content does not Match");
			} catch (Throwable e) {
				content.add(testdata[r][1] + " " + e.getLocalizedMessage());
			
			}
		}
		if (content.size() > 0) {
			Assert.fail("Content " + Arrays.deepToString(content.toArray()) + " are not working as expected");
		}
	}
	
	
/*	public List<String> verify_Links_Login_Page(String[][] testdata) {
		List<String> Page_URL_ElementName_Title = new ArrayList<String>();
		List<String> brokenLinks = new ArrayList<String>();
		String ParentElementLocator = null;
		String ChildElementLocator = null;
		String ExpectedURL = null;
		String ExpectedElementName = null;
		String ElementTitle = null;
		String actualUrl = null;
		String ActualElementName = null;
		
		log.info("verifyLinks_LoginPage : "  );

		for (int r = 0; r < testdata.length; r++) {
			try {
				log.info("Verifying " + testdata[r][0]);
				ParentElementLocator = testdata[r][1];
				ChildElementLocator = testdata[r][2];
				ExpectedURL = testdata[r][3];
				ExpectedElementName = testdata[r][4];
				ElementTitle = testdata[r][5];
				log.info("Parent Locator is ..." + ParentElementLocator);
				
				if (!(ParentElementLocator.equalsIgnoreCase("NA"))) {
					webPage.hoverOnElement(By.cssSelector(testdata[r][0]));

				}
				ActualElementName = webPage.findObjectByCss(ChildElementLocator).getText();
				log.info("Assert ActualElementName : " + ActualElementName  );
				log.info("Assert ExpectedElementName : " + ExpectedElementName  );
				
				SoftAssertor.assertEquals(ActualElementName, ExpectedElementName, "Element name does not match");
				
				//webPage.findObjectByCss(ChildElementLocator).click();
				
			//	log.info("ChildElementLocator got clicked : " + ChildElementLocator  );
				Thread.sleep(3000);
				String actualPageUrl = commonMethods.getPageUrl(webPage);
				String actualPageTitle = commonMethods.getPageTitle(webPage);
				
				SoftAssertor.assertTrue(actualPageUrl.contains(ExpectedURL));
				SoftAssertor.assertEquals(actualPageTitle, ElementTitle, "Title of the page does not match");
				
				log.info("Expected URL : " + ExpectedURL);
				log.info("Actual URL : " + actualUrl);
				log.info("Actual PageTitle : " + webPage.getPageTitle()  );
				log.info("Expected PageTitle : "  + ElementTitle  );
				
				actualUrl = webPage.getCurrentUrl();
				 SoftAssertor.assertTrue(actualUrl.contains(ExpectedURL));
				 SoftAssertor.assertEquals(webPage.getPageTitle(), ElementTitle, "Title of the page does not match");
				webPage.getDriver().navigate().back();
				webPage.getDriver().navigate().refresh();
				Thread.sleep(7000);
				
				Page_URL_ElementName_Title.add(ActualElementName);
				Page_URL_ElementName_Title.add(actualPageUrl);
				Page_URL_ElementName_Title.add(actualPageTitle);
			 
				actualUrl = webPage.getCurrentUrl();
				log.info("Expected URL" + ExpectedURL);
				log.info("Actual URL" + actualUrl);
				try {
					SoftAssertor.assertTrue(actualUrl.contains(ExpectedURL));
					SoftAssertor.assertEquals(webPage.getPageTitle(), ElementTitle,
							"Title of the page does not match");
					
					log.info("Actual PageTitle : " + webPage.getPageTitle()  );
					log.info("Expected PageTitle : "  + ElementTitle  );
					webPage.getDriver().navigate().back();
					
					if (!ExpectedElementName.equalsIgnoreCase("« Go back")
							&& !ExpectedElementName.equalsIgnoreCase("SAVE ADDRESS")
							&& !ExpectedElementName.equalsIgnoreCase("Newsletter Subscription")) {
						webPage.getDriver().navigate().back();
					}
					
				} catch (Exception e) {
					webPage.getDriver().navigate().back();
				}
			}
				
			
			
		 catch (Exception e) {
			webPage.getDriver().navigate().back();
		}
			
			
		}
		if (brokenLinks.size() > 0) {
			Assert.fail("Link " + Arrays.deepToString(brokenLinks.toArray()) + " are not working as expected");
		}
		return Page_URL_ElementName_Title;
		} 
*/
		

	public void verifyLinks(String[][] testdata) {

		List<String> brokenLinks = new ArrayList<String>();
		String ParentElementLocator = null;
		String ChildElementLocator = null;
		String ExpectedURL = null;
		String ExpectedElementName = null;
		String ElementTitle = null;
		String actualUrl = null;
		String ActualElementName = null;

		for (int r = 0; r < testdata.length; r++) {
			try {
				log.info("Verifying " + testdata[r][0]);
				ParentElementLocator = testdata[r][1];
				ChildElementLocator = testdata[r][2];
				ExpectedURL = testdata[r][3];
				ExpectedElementName = testdata[r][4];
				ElementTitle = testdata[r][5];
				log.info("Parent Locator is ..." + ParentElementLocator);
				
				if (!(ParentElementLocator.equalsIgnoreCase("NA"))) {
					webPage.hoverOnElement(By.cssSelector(testdata[r][0]));

				}
				ActualElementName = webPage.findObjectByCss(ChildElementLocator).getText();
				SoftAssertor.assertEquals(ActualElementName, ExpectedElementName, "Element name does not match");
				webPage.findObjectByCss(ChildElementLocator).click();
				String existingWindow = null;
				String newWindow = null;
				existingWindow = webPage.getDriver().getWindowHandle();
				Set<String> windows = webPage.getDriver().getWindowHandles();
				if (windows.size() >= 2) {
					windows.remove(existingWindow);
					newWindow = windows.iterator().next();
					log.info("Existing window id is" + existingWindow);
					log.info("New window id is" + newWindow);

					webPage.getDriver().switchTo().window(newWindow);
					Thread.sleep(3000);
					actualUrl = webPage.getCurrentUrl();
					webPage.getDriver().close();
					webPage.getDriver().switchTo().window(existingWindow);
					log.info("Expected URL" + ExpectedURL);
					log.info("Actual URL" + actualUrl);
					SoftAssertor.assertTrue(actualUrl.contains(ExpectedURL));
					SoftAssertor.assertEquals(webPage.getPageTitle(), ElementTitle, "Title of the page does not match");

				} else {
					actualUrl = webPage.getCurrentUrl();
					log.info("Expected URL" + ExpectedURL);
					log.info("Actual URL" + actualUrl);
					try {
						SoftAssertor.assertTrue(actualUrl.contains(ExpectedURL));
						SoftAssertor.assertEquals(webPage.getPageTitle(), ElementTitle,
								"Title of the page does not match");
						if (!ExpectedElementName.equalsIgnoreCase("« Go back")
								&& !ExpectedElementName.equalsIgnoreCase("SAVE ADDRESS")
								&& !ExpectedElementName.equalsIgnoreCase("Newsletter Subscription")) {
							webPage.getDriver().navigate().back();
						}
						
					} catch (Exception e) {
						webPage.getDriver().navigate().back();
					}
				}

			} catch (Exception e) {
				webPage.getDriver().navigate().back();
				brokenLinks.add(ExpectedElementName + " " + e.getLocalizedMessage());
				

			}

		}
		if (brokenLinks.size() > 0) {
			Assert.fail("Link " + Arrays.deepToString(brokenLinks.toArray()) + " are not working as expected");
		}

	}

	public void ClickonButton(String[][] testdata) throws PageException {

		webPage.findObjectByxPath(testdata[0][0]).click();
	}

	public void VerifyValidationMsg(String[][] testdata) {
		List<String> brokenItems = new ArrayList<String>();
		log.info("verification of Mandatory field validation message started");

		for (int r = 0; r < testdata.length; r++) {

			String FNLocator = testdata[r][1];
			String FNInput = testdata[r][2];
			String FNErrLocator = testdata[r][3];
			String LNLocator = testdata[r][4];
			String LNInput = testdata[r][5];
			String LNErrLocator = testdata[r][6];
			String EmailLocator = testdata[r][7];
			String EmailInput = testdata[r][8];
			String EmailErrLocator = testdata[r][9];
			String ExpectedValMsg = testdata[r][10];
			String ButtonLocator = testdata[r][11];
			boolean runflag = true;
			if (r == 2) {
				runflag = false;

			}

			try {
				if (FNInput.equalsIgnoreCase("NA") && LNInput.equalsIgnoreCase("NA")
						&& EmailInput.equalsIgnoreCase("NA")) {
					webPage.findObjectByxPath(FNLocator).click();
					webPage.findObjectByxPath(FNLocator).clear();
					webPage.findObjectByxPath(LNLocator).click();
					webPage.findObjectByxPath(LNLocator).clear();
					webPage.findObjectByxPath(EmailLocator).click();
					webPage.findObjectByxPath(EmailLocator).clear();
					webPage.findObjectByxPath(ButtonLocator).click();
					SoftAssertor.assertEquals(webPage.findObjectByxPath(FNErrLocator).getText(), ExpectedValMsg);
					SoftAssertor.assertEquals(webPage.findObjectByxPath(LNErrLocator).getText(), ExpectedValMsg);
					SoftAssertor.assertEquals(webPage.findObjectByxPath(EmailErrLocator).getText(), ExpectedValMsg);
				} else {
					webPage.findObjectByxPath(FNLocator).clear();
					webPage.findObjectByxPath(FNLocator).sendKeys(FNInput);
					webPage.findObjectByxPath(LNLocator).clear();
					webPage.findObjectByxPath(LNLocator).sendKeys(LNInput);
					webPage.findObjectByxPath(EmailLocator).clear();
					webPage.findObjectByxPath(EmailLocator).sendKeys(EmailInput);
					if (runflag) {
						webPage.findObjectByxPath(ButtonLocator).click();
					}
					log.info("in else part of validation msg method......");
					SoftAssertor.assertEquals(webPage.findObjectByxPath(EmailErrLocator).getText(), ExpectedValMsg);

				}

			} catch (Exception e) {
				brokenItems.add(FNLocator + " " + e.getLocalizedMessage());

			}
		}
		if (brokenItems.size() > 0) {
			Assert.fail("Link " + Arrays.deepToString(brokenItems.toArray()) + " are not working as expected");
		}

	}

	public void VerifyChangePasswordfun(String[][] inputdata) throws PageException {

		List<String> brokenItems = new ArrayList<String>();
		webPage.findObjectByxPath(inputdata[0][0]).click();

		for (int r = 0; r < inputdata.length; r++) {

			String CurrPwdLocator = inputdata[r][1];
			String CurrPwdinput = inputdata[r][2];
			String CurrPwdErrMsgLocator = inputdata[r][3];
			String NewPwdLocator = inputdata[r][4];
			String NewPwdinput = inputdata[r][5];
			String NewPwdErrMsgLocator = inputdata[r][6];
			String ConfPwdLocator = inputdata[r][7];
			String ConfPwdinput = inputdata[r][8];
			String ConfPwdErrMsgLocator = inputdata[r][9];
			String ExpectedValMsg = inputdata[r][10];
			String ButtonLocator = inputdata[r][11];
			String NameofTestCase = inputdata[r][12];
			try {
				if (CurrPwdinput.equalsIgnoreCase("NA") && NewPwdinput.equalsIgnoreCase("NA")
						&& ConfPwdinput.equalsIgnoreCase("NA")) {
					webPage.findObjectByxPath(ButtonLocator).click();
					SoftAssertor.assertEquals(webPage.findObjectByxPath(CurrPwdErrMsgLocator).getText(),
							ExpectedValMsg);
					SoftAssertor.assertEquals(webPage.findObjectByxPath(NewPwdErrMsgLocator).getText(), ExpectedValMsg);
					SoftAssertor.assertEquals(webPage.findObjectByxPath(ConfPwdErrMsgLocator).getText(),
							ExpectedValMsg);
				} else {
					log.info("in else part of change password method......");
					
					JavascriptExecutor jse = (JavascriptExecutor)webPage.getDriver();
					jse.executeScript("scroll(0, 250);");
					webPage.findObjectByxPath(CurrPwdLocator).click();
					webPage.findObjectByxPath(CurrPwdLocator).clear();
					webPage.findObjectByxPath(CurrPwdLocator).sendKeys(CurrPwdinput);
					webPage.findObjectByxPath(NewPwdLocator).click();
					webPage.findObjectByxPath(NewPwdLocator).clear();
					webPage.findObjectByxPath(NewPwdLocator).sendKeys(NewPwdinput);
					webPage.findObjectByxPath(ConfPwdLocator).click();
					webPage.findObjectByxPath(ConfPwdLocator).clear();
					webPage.findObjectByxPath(ConfPwdLocator).sendKeys(ConfPwdinput);
					webPage.findObjectByxPath(ButtonLocator).click();
					Thread.sleep(3000);
					if (NameofTestCase.equalsIgnoreCase("ShortPassword")) {
						SoftAssertor.assertEquals(webPage.findObjectByxPath(NewPwdErrMsgLocator).getText(),
								ExpectedValMsg);
					} else if (NameofTestCase.equalsIgnoreCase("diffvalues")) {
						SoftAssertor.assertEquals(webPage.findObjectByxPath(NewPwdErrMsgLocator).getText(),
								ExpectedValMsg);
						SoftAssertor.assertEquals(webPage.findObjectByxPath(ConfPwdErrMsgLocator).getText(),
								ExpectedValMsg);

					} else if (NameofTestCase.equalsIgnoreCase("invalidCrrpwd")) {
						SoftAssertor.assertEquals(webPage.findObjectByxPath(ConfPwdErrMsgLocator).getText(),
								ExpectedValMsg);

					} else {
						SoftAssertor.assertEquals(webPage.findObjectByxPath(ConfPwdErrMsgLocator).getText(),
								ExpectedValMsg);
					}
				}

			} catch (Exception e) {
				brokenItems.add(NameofTestCase + " " + e.getLocalizedMessage());

			}
		}
		if (brokenItems.size() > 0) {
			Assert.fail("Link " + Arrays.deepToString(brokenItems.toArray()) + " are not working as expected");
		}

	}
	
	
	
	
	public void verify_Contact_Information_Tab_Address_Book_Page_Additional_Address_Entries (String[][] inputdata) throws PageException {
		SoftAssert softAssert = new SoftAssert();
		for (int r = 0; r < inputdata.length; r++) {

			String First_Name_Locator = inputdata[r][4];
			String Last_Name_Locator = inputdata[r][5];
			String Company_Name_Locator = inputdata[r][6];
			String Telephone_Number_Locator = inputdata[r][7];
			String Fax_Number_Locator = inputdata[r][8];
			String Street_Address_1_Locator = inputdata[r][9];
			String Street_Address_2_Locator = inputdata[r][10];
			String City_Name_Locator = inputdata[r][11];
			String Zip_Postal_Code_Locator = inputdata[r][12];
			String State_Province_Dropdown_Locator = inputdata[r][13];
			String Country_Dropdown_Locator = inputdata[r][14];
			String First_Name_Input = inputdata[r][15];
			String Last_Name_Input = inputdata[r][16];
			String Company_Name_Input = inputdata[r][17];			
			String Telephone_Number_Input = inputdata[r][18];			
			String Fax_Number_Input = inputdata[r][19];
			String Street_Address_1_Input = inputdata[r][20];
			String Street_Address_2_Input = inputdata[r][21];
			String City_Name_Input = inputdata[r][22];
			String Zip_Postal_Code_Input = inputdata[r][23];
			String State_Province_Dropdown_Input = inputdata[r][24];
			String Country_Drop_Down_Input = inputdata[r][25];
			String Save_Button_Locator = inputdata[r][26];
			

			
			commonMethods.clickElementbyXpath(webPage, First_Name_Locator, softAssert);
			commonMethods.clearElementbyXpath(webPage, First_Name_Locator, softAssert);
			commonMethods.sendKeysbyXpath(webPage, First_Name_Locator, First_Name_Input, softAssert);
			
			commonMethods.clickElementbyXpath(webPage, Last_Name_Locator, softAssert);
			commonMethods.clearElementbyXpath(webPage, Last_Name_Locator, softAssert);
			commonMethods.sendKeysbyXpath(webPage, Last_Name_Locator, Last_Name_Input, softAssert);
			
			commonMethods.clickElementbyXpath(webPage, Company_Name_Locator, softAssert);
			commonMethods.clearElementbyXpath(webPage, Company_Name_Locator, softAssert);
			commonMethods.sendKeysbyXpath(webPage, Company_Name_Locator, Company_Name_Input, softAssert);
			
			commonMethods.clickElementbyXpath(webPage, Telephone_Number_Locator, softAssert);
			commonMethods.clearElementbyXpath(webPage, Telephone_Number_Locator, softAssert);
			commonMethods.sendKeysbyXpath(webPage, Telephone_Number_Locator, Telephone_Number_Input, softAssert);
			
			commonMethods.clickElementbyXpath(webPage, Fax_Number_Locator, softAssert);
			commonMethods.clearElementbyXpath(webPage, Fax_Number_Locator, softAssert);
			commonMethods.sendKeysbyXpath(webPage, Fax_Number_Locator, Fax_Number_Input, softAssert);
			
			commonMethods.clickElementbyXpath(webPage, Street_Address_1_Locator, softAssert);
			commonMethods.clearElementbyXpath(webPage, Street_Address_1_Locator, softAssert);
			commonMethods.sendKeysbyXpath(webPage, Street_Address_1_Locator, Street_Address_1_Input, softAssert);
			
			commonMethods.clickElementbyXpath(webPage, Street_Address_2_Locator, softAssert);
			commonMethods.clearElementbyXpath(webPage, Street_Address_2_Locator, softAssert);
			commonMethods.sendKeysbyXpath(webPage, Street_Address_2_Locator, Street_Address_2_Input, softAssert);
			
			commonMethods.clickElementbyXpath(webPage, City_Name_Locator, softAssert);
			commonMethods.clearElementbyXpath(webPage, City_Name_Locator, softAssert);
			commonMethods.sendKeysbyXpath(webPage, City_Name_Locator, City_Name_Input, softAssert);
			
			commonMethods.clickElementbyXpath(webPage, Zip_Postal_Code_Locator, softAssert);
			commonMethods.clearElementbyXpath(webPage, Zip_Postal_Code_Locator, softAssert);
			commonMethods.sendKeysbyXpath(webPage, Zip_Postal_Code_Locator, Zip_Postal_Code_Input, softAssert);
			
			Select State_Provice_Drop_Down = new Select(webPage.getDriver().findElement(By.xpath(State_Province_Dropdown_Locator)));
			State_Provice_Drop_Down.selectByVisibleText(State_Province_Dropdown_Input);
			
			Select Country_Drop_Down = new Select(webPage.getDriver().findElement(By.xpath(Country_Dropdown_Locator)));
			Country_Drop_Down.selectByVisibleText(Country_Drop_Down_Input);
			
			
			commonMethods.clickElementbyXpath(webPage, Save_Button_Locator, softAssert);

			/*commonMethods.clickElementbyXpath(webPage, LNLocator, softAssert);
			commonMethods.clearElementbyXpath(webPage, LNLocator, softAssert);
			commonMethods.clickElementbyXpath(webPage, EmailLocator, softAssert);
			commonMethods.clearElementbyXpath(webPage, EmailLocator, softAssert);
			commonMethods.clearElementbyXpath(webPage, ButtonLocator, softAssert);*/
			
			/*webPage.findObjectByxPath(FNLocator).clear();
			webPage.findObjectByxPath(FNLocator).sendKeys(FNInput);
			webPage.findObjectByxPath(LNLocator).clear();
			webPage.findObjectByxPath(LNLocator).sendKeys(LNInput);
			webPage.findObjectByxPath(EmailLocator).clear();
			webPage.findObjectByxPath(EmailLocator).sendKeys(EmailInput);*/

			/*if (runflag) {
				
				commonMethods.sendKeysbyXpath(webPage, EmailLocator, EmailInput, softAssert);
				commonMethods.clickElementbyXpath(webPage, ButtonLocator, softAssert);
				//webPage.findObjectByxPath(ButtonLocator).click();
			}*/


		}
	}
	
	
	
	
	
	public List<String> verify_Account_DashBoard_First_Name_Last_Name_Login (String[][] testdata,SoftAssert softAssert) throws PageException {
		List<String> brokenLinks = new ArrayList<String>();
		List<String> information_Saved_Successfully_Message = new ArrayList<String>();
		String Information_Saved_Successfully_Message_Account_Information_Page="";
		
		/*try {*/
			
			
		for (int r = 0; r < testdata.length; r++) {
		String FNLocator = testdata[r][2];
		String FNInput = testdata[r][3];
		String FNErrLocator = testdata[r][4];
		String LNLocator = testdata[r][5];
		String LNInput = testdata[r][6];
		String LNErrLocator = testdata[r][7];
		String EmailLocator = testdata[r][8];
		String EmailInput = testdata[r][9];
		String Email_Error_Msg_Locator = testdata[r][10];
		String ExpectedValMsg = testdata[r][11];
		String ButtonLocator = testdata[r][13];

		
		
		log.info("Entered for Clicking Operation : ");
		/*commonMethods.clickElementbyXpath(webPage, FNLocator, softAssert);
		commonMethods.clearElementbyXpath(webPage, FNLocator, softAssert);
		commonMethods.clickElementbyXpath(webPage, LNLocator, softAssert);
		commonMethods.clearElementbyXpath(webPage, LNLocator, softAssert);
		commonMethods.clickElementbyXpath(webPage, EmailLocator, softAssert);
		commonMethods.clearElementbyXpath(webPage, EmailLocator, softAssert);*/
		
		
		commonMethods.clickElementbyXpath(webPage, FNLocator, softAssert);
		commonMethods.clearElementbyXpath(webPage, FNLocator, softAssert);
		commonMethods.sendKeysbyXpath(webPage, FNLocator, FNInput, softAssert);
		commonMethods.clickElementbyXpath(webPage, LNLocator, softAssert);
		commonMethods.clearElementbyXpath(webPage, LNLocator, softAssert);
		commonMethods.sendKeysbyXpath(webPage, LNLocator, LNInput, softAssert);
		commonMethods.clickElementbyXpath(webPage, EmailLocator, softAssert);
		commonMethods.clearElementbyXpath(webPage, EmailLocator, softAssert);
		commonMethods.sendKeysbyXpath(webPage, EmailLocator, EmailInput, softAssert);
		commonMethods.clickElementbyXpath(webPage, ButtonLocator, softAssert);
		log.info("Executed Clicking Operation : ");
		
		Information_Saved_Successfully_Message_Account_Information_Page = commonMethods.getTextbyXpath(webPage,Email_Error_Msg_Locator, softAssert);
		information_Saved_Successfully_Message.add(Information_Saved_Successfully_Message_Account_Information_Page);
		
		}
		//}
		
		
	/*	catch (Exception e) {
			webPage.getDriver().navigate().back();
			//brokenLinks.add(Expected_Page_Element_Name + " " + e.getLocalizedMessage());
			log.info("getLocalizedMessage :"); 
			e.printStackTrace();
		}
		*/
		
		
		/*Last_Name_Error_Message_Account_Information_Page = commonMethods.getTextbyXpath(webPage,LNErrLocator, softAssert);
		Email_Address_Error_Message_Account_Information_Page = commonMethods.getTextbyXpath(webPage,EmailErrLocator, softAssert);

		
		errorMessage.add(Last_Name_Error_Message_Account_Information_Page);
		errorMessage.add(Email_Address_Error_Message_Account_Information_Page);*/
		
		
		/*webPage.findObjectByxPath(FNLocator).click();
		webPage.findObjectByxPath(FNLocator).clear();
		webPage.findObjectByxPath(LNLocator).click();
		webPage.findObjectByxPath(LNLocator).clear();
		webPage.findObjectByxPath(EmailLocator).click();
		webPage.findObjectByxPath(EmailLocator).clear();
		webPage.findObjectByxPath(ButtonLocator).click();*/
		
		
		
		//}
		return information_Saved_Successfully_Message;
	}
	
	
	public List<String> verify_Account_Information_First_Name_Last_Name_Login_Validation (String[][] testdata,SoftAssert softAssert) throws PageException, InterruptedException {
		List<String> Account_Information_Login_Validation_Error_Message = new ArrayList<String>();
		String FNLocator = testdata[0][1];
		String FNInput = testdata[0][2];
		String LNLocator = testdata[0][4];
		String LNInput = testdata[0][5];
		String EmailLocator = testdata[0][7];
		String EmailInput = testdata[0][8];
		String EmailErrLocator = testdata[0][9];
		String ButtonLocator = testdata[0][11];
		commonMethods.clickElementbyXpath(webPage, FNLocator, softAssert);
		commonMethods.clearElementbyXpath(webPage, FNLocator, softAssert);
		commonMethods.sendKeysbyXpath(webPage, FNLocator, FNInput, softAssert);
		commonMethods.clickElementbyXpath(webPage, LNLocator, softAssert);
		commonMethods.clearElementbyXpath(webPage, LNLocator, softAssert);
		commonMethods.sendKeysbyXpath(webPage, LNLocator, LNInput, softAssert);
		commonMethods.clickElementbyXpath(webPage, EmailLocator, softAssert);
		commonMethods.clearElementbyXpath(webPage, EmailLocator, softAssert);
		commonMethods.sendKeysbyXpath(webPage, EmailLocator, EmailInput, softAssert);
		commonMethods.clickElementbyXpath(webPage, ButtonLocator, softAssert);
		Thread.sleep(1000);
		String Email_Address_Error_Message_Account_Information_Page = commonMethods.getTextbyXpath(webPage,EmailErrLocator, softAssert);
		Account_Information_Login_Validation_Error_Message.add(Email_Address_Error_Message_Account_Information_Page);
		return Account_Information_Login_Validation_Error_Message;
	}
	
	
	public List<String> verify_Account_Information_First_Name_Last_Name_Login_Validation_Valid_Input (String[][] testdata,SoftAssert softAssert) throws PageException, InterruptedException {
		List<String> Account_Information_Login_Validation_Error_Message = new ArrayList<String>();
		String FNLocator = testdata[0][2];
		String FNInput = testdata[0][3];
		String LNLocator = testdata[0][5];
		String LNInput = testdata[0][6];
		String EmailLocator = testdata[0][8];
		String EmailInput = testdata[0][9];
		String EmailErrLocator = testdata[0][10];
		String ButtonLocator = testdata[0][12];
		commonMethods.clickElementbyXpath(webPage, FNLocator, softAssert);
		commonMethods.clearElementbyXpath(webPage, FNLocator, softAssert);
		commonMethods.sendKeysbyXpath(webPage, FNLocator, FNInput, softAssert);
		commonMethods.clickElementbyXpath(webPage, LNLocator, softAssert);
		commonMethods.clearElementbyXpath(webPage, LNLocator, softAssert);
		commonMethods.sendKeysbyXpath(webPage, LNLocator, LNInput, softAssert);
		commonMethods.clickElementbyXpath(webPage, EmailLocator, softAssert);
		commonMethods.clearElementbyXpath(webPage, EmailLocator, softAssert);
		commonMethods.sendKeysbyXpath(webPage, EmailLocator, EmailInput, softAssert);
		commonMethods.clickElementbyXpath(webPage, ButtonLocator, softAssert);
		Thread.sleep(1000);
		String Email_Address_Error_Message_Account_Information_Page = commonMethods.getTextbyXpath(webPage,EmailErrLocator, softAssert);
		Account_Information_Login_Validation_Error_Message.add(Email_Address_Error_Message_Account_Information_Page);
		return Account_Information_Login_Validation_Error_Message;
	}
	
	public List<String> verify_Account_Information_First_Name_Last_Name_Login_Invalid_Input_Validation (String[][] testdata,SoftAssert softAssert) throws PageException, InterruptedException {
		List<String> Account_Information_Login_Validation_Error_Message = new ArrayList<String>();
		String FNLocator = testdata[1][1];
		String FNInput = testdata[1][2];
		String LNLocator = testdata[1][4];
		String LNInput = testdata[1][5];
		String EmailLocator = testdata[1][7];
		String EmailInput = testdata[1][8];
		String EmailErrLocator = testdata[1][9];
		String ButtonLocator = testdata[1][11];
		//commonMethods.clickElementbyXpath(webPage, FNLocator, softAssert);
		commonMethods.clearElementbyXpath(webPage, FNLocator, softAssert);
		commonMethods.sendKeysbyXpath(webPage, FNLocator, FNInput, softAssert);
		log.info("****************** FNLocator : " +FNLocator  + "************************************** FNInput : " +FNInput );
		//commonMethods.clickElementbyXpath(webPage, LNLocator, softAssert);
		commonMethods.clearElementbyXpath(webPage, LNLocator, softAssert);
		commonMethods.sendKeysbyXpath(webPage, LNLocator, LNInput, softAssert);
		//commonMethods.clickElementbyXpath(webPage, EmailLocator, softAssert);
		commonMethods.clearElementbyXpath(webPage, EmailLocator, softAssert);
		commonMethods.sendKeysbyXpath(webPage, EmailLocator, EmailInput, softAssert);
		commonMethods.clickElementbyXpath(webPage, ButtonLocator, softAssert);
		String Email_Address_Error_Message_Account_Information_Page = commonMethods.getTextbyXpath(webPage,EmailErrLocator, softAssert);
		Account_Information_Login_Validation_Error_Message.add(Email_Address_Error_Message_Account_Information_Page);
		return Account_Information_Login_Validation_Error_Message;
	}
	
	public List<String> verify_Account_Information_First_Name_Last_Name_Login_Valid_Input_Validation (String[][] testdata,SoftAssert softAssert) throws PageException, InterruptedException {
		List<String> Account_Information_Login_Validation_Error_Message = new ArrayList<String>();
		String FNLocator = testdata[2][1];
		String FNInput = testdata[2][2];
		String LNLocator = testdata[2][4];
		String LNInput = testdata[2][5];
		String EmailLocator = testdata[2][7];
		String EmailInput = testdata[2][8];
		String ButtonLocator = testdata[2][11];
		String Customer_Already_Exists_Locator = testdata[2][13];
		String Customer_Already_Exists_Sucessful_Message_Locator = testdata[2][17];
		
		log.info("########################################verify_Account_Information_First_Name_Last_Name_Login_Valid_Input_Validation : $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		
		//commonMethods.clickElementbyXpath(webPage, FNLocator, softAssert);
		commonMethods.clearElementbyXpath(webPage, FNLocator, softAssert);
		commonMethods.sendKeysbyXpath(webPage, FNLocator, FNInput, softAssert);
		//commonMethods.clickElementbyXpath(webPage, LNLocator, softAssert);
		commonMethods.clearElementbyXpath(webPage, LNLocator, softAssert);
		commonMethods.sendKeysbyXpath(webPage, LNLocator, LNInput, softAssert);
		//commonMethods.clickElementbyXpath(webPage, EmailLocator, softAssert);
		commonMethods.clearElementbyXpath(webPage, EmailLocator, softAssert);
		commonMethods.sendKeysbyXpath(webPage, EmailLocator, EmailInput, softAssert);
		log.info("****************** FNLocator : " +FNLocator  + "************************************** FNInput : " +FNInput );
		log.info("****************** LNLocator : " +LNLocator  + "************************************** LNInput : " +LNInput );
		log.info("****************** EmailLocator : " +EmailLocator  + "************************************** EmailInput : " +EmailInput );
		
		commonMethods.clickElementbyXpath(webPage, ButtonLocator, softAssert);
		log.info("****************** Customer_Already_Exists_Locator : " +Customer_Already_Exists_Locator);
		String Customer_Already_Exist_Message_Account_Information_Page = commonMethods.getTextbyXpath(webPage,Customer_Already_Exists_Locator, softAssert);
		log.info("****************** Customer_Already_Exist_Message_Account_Information_Page : " +Customer_Already_Exist_Message_Account_Information_Page);
		Account_Information_Login_Validation_Error_Message.add(Customer_Already_Exist_Message_Account_Information_Page);
		log.info("****************** Account_Information_Login_Validation_Error_Message : " +Account_Information_Login_Validation_Error_Message );
		return Account_Information_Login_Validation_Error_Message;
	}
	
	
	
	
	public void verify_Account_DashBoard_Login_Again (String[][] testdata) throws PageException {
		SoftAssert softAssert = new SoftAssert();
		for (int r = 0; r < testdata.length; r++) {
			String FNLocator = testdata[r][1];
			String FNInput = testdata[r][2];
			String FNErrLocator = testdata[r][3];
			String LNLocator = testdata[r][4];
			String LNInput = testdata[r][5];
			String LNErrLocator = testdata[r][6];
			String EmailLocator = testdata[r][7];
			String EmailInput = testdata[r][8];
			String EmailErrLocator = testdata[r][9];
			String ExpectedValMsg = testdata[r][10];
			String ButtonLocator = testdata[r][11];
			boolean runflag = true;
			if (r == 2) {
				runflag = false;

			}
			
		
			commonMethods.clearElementbyXpath(webPage, FNLocator, softAssert);
			commonMethods.sendKeysbyXpath(webPage, FNLocator, FNInput, softAssert);
			commonMethods.clearElementbyXpath(webPage, LNLocator, softAssert);
			commonMethods.sendKeysbyXpath(webPage, LNLocator, LNInput, softAssert);
			commonMethods.clearElementbyXpath(webPage, EmailLocator, softAssert);
			commonMethods.sendKeysbyXpath(webPage, EmailLocator, EmailInput, softAssert);
			/*commonMethods.clickElementbyXpath(webPage, LNLocator, softAssert);
			commonMethods.clearElementbyXpath(webPage, LNLocator, softAssert);
			commonMethods.clickElementbyXpath(webPage, EmailLocator, softAssert);
			commonMethods.clearElementbyXpath(webPage, EmailLocator, softAssert);
			commonMethods.clearElementbyXpath(webPage, ButtonLocator, softAssert);*/
			
			/*webPage.findObjectByxPath(FNLocator).clear();
			webPage.findObjectByxPath(FNLocator).sendKeys(FNInput);
			webPage.findObjectByxPath(LNLocator).clear();
			webPage.findObjectByxPath(LNLocator).sendKeys(LNInput);
			webPage.findObjectByxPath(EmailLocator).clear();
			webPage.findObjectByxPath(EmailLocator).sendKeys(EmailInput);*/

			/*if (runflag) {
				
				commonMethods.sendKeysbyXpath(webPage, EmailLocator, EmailInput, softAssert);
				commonMethods.clickElementbyXpath(webPage, ButtonLocator, softAssert);
				//webPage.findObjectByxPath(ButtonLocator).click();
			}*/


		}
	}
	
	
	public List<String> verify_Account_Information_Tab_Change_Password_Functionality (String[][] inputdata) throws PageException, InterruptedException, AWTException {
			List<String> errorMessage = new ArrayList<String>();
			String Short_Password_Error_Message_Locator_Account_Information_Page="";
			String New_Different_Value_Password_Error_Message_Locator_Account_Information_Page="";
			String Confirm_Different_Value_Password_Error_Message_Locator_Account_Information_Page = "";
			String Invalid_Correct_Value_Password_Error_Message_Locator_Account_Information_Page = "";
			String Valid_Correct_Password_Notification_Message_Locator_Account_Information_Page = "";
			SoftAssert softAssert = new SoftAssert();
			for (int r = 0; r < inputdata.length; r++) 
			{
				log.info("*********************************************** ::::Inside_verify_Account_Information_Tab_Change_Password_Functionality_Method ::::::" );
			

				String CurrPwdLocator = inputdata[r][1];
				String CurrPwdinput = inputdata[r][2];
				String CurrPwdErrMsgLocator = inputdata[r][3];
				String NewPwdLocator = inputdata[r][4];
				String NewPwdinput = inputdata[r][5];
				String NewPwdErrMsgLocator = inputdata[r][6];
				String ConfPwdLocator = inputdata[r][7];
				String ConfPwdinput = inputdata[r][8];
				String ConfPwdErrMsgLocator = inputdata[r][9];
				String ExpectedValMsg = inputdata[r][10];
				String ButtonLocator = inputdata[r][11];
				String NameofTestCase = inputdata[r][12];
				String Change_Password_Locator = inputdata[0][0];
				
				JavascriptExecutor jse = (JavascriptExecutor)webPage.getDriver();
				jse.executeScript("scroll(0, 250);");
				log.info("******************************************** :::: verify_Account_Information_Tab_Change_Password_Functionality_Method_Call_Starts :::: *************************************************** ");
				commonMethods.clickElementbyXpath(webPage, CurrPwdLocator, softAssert);
				commonMethods.clearElementbyXpath(webPage, CurrPwdLocator, softAssert);
				commonMethods.sendKeysbyXpath(webPage, CurrPwdLocator, CurrPwdinput, softAssert);
				commonMethods.clickElementbyXpath(webPage, NewPwdLocator, softAssert);
				commonMethods.clearElementbyXpath(webPage, NewPwdLocator, softAssert);
				commonMethods.sendKeysbyXpath(webPage, NewPwdLocator, NewPwdinput, softAssert);
				commonMethods.clickElementbyXpath(webPage, ConfPwdLocator, softAssert);
				commonMethods.clearElementbyXpath(webPage, ConfPwdLocator, softAssert);
				commonMethods.sendKeysbyXpath(webPage, ConfPwdLocator, ConfPwdinput, softAssert);
				log.info("********************************************CurrPwdLocator : *************************************************** " +CurrPwdLocator);
				log.info("********************************************NewPwdLocator : *************************************************** " +CurrPwdLocator);
				log.info("********************************************NewPwdinput : *************************************************** " +NewPwdinput);
				log.info("********************************************ConfPwdLocator : *************************************************** " +ConfPwdLocator);
				log.info("********************************************ConfPwdinput : *************************************************** " +ConfPwdinput);
				log.info("********************************************ButtonLocator : *************************************************** " +ButtonLocator);
				commonMethods.clickElementbyXpath(webPage, ButtonLocator, softAssert);
				log.info("******************************************** |||||||||||||||||||||||||||||||||||||||||||||||  ButtonLocator Clicked Successfully ||||||||||||||||||||||||||||||||||||||||||||  : *************************************************** " +ButtonLocator);
				if (NameofTestCase.equalsIgnoreCase("ShortPassword")) {
					log.info("********************************************Short_Password_Error_Message_Locator_Account_Information_Page : *************************************************** " );

					Short_Password_Error_Message_Locator_Account_Information_Page = commonMethods.getTextbyXpath(webPage,NewPwdErrMsgLocator, softAssert);
					log.info("********************************************Short_Password_Error_Message_Locator_Account_Information_Page : *************************************************** " +Short_Password_Error_Message_Locator_Account_Information_Page);
					log.info("********************************************NewPwdErrMsgLocator : *************************************************** " +NewPwdErrMsgLocator);

					
					//SoftAssertor.assertEquals(webPage.findObjectByxPath(NewPwdErrMsgLocator).getText(),ExpectedValMsg);
				} else if (NameofTestCase.equalsIgnoreCase("diffvalues")) {	
					log.info("********************************************New_Different_Value_Password_Error_Message_Locator_Account_Information_Page : *************************************************** " );
					log.info("********************************************Confirm_Different_Value_Password_Error_Message_Locator_Account_Information_Page : *************************************************** " );

						New_Different_Value_Password_Error_Message_Locator_Account_Information_Page = commonMethods.getTextbyXpath(webPage,NewPwdErrMsgLocator, softAssert);
					    Confirm_Different_Value_Password_Error_Message_Locator_Account_Information_Page = commonMethods.getTextbyXpath(webPage,ConfPwdErrMsgLocator, softAssert);
					    log.info("********************************************New_Different_Value_Password_Error_Message_Locator_Account_Information_Page : *************************************************** " +New_Different_Value_Password_Error_Message_Locator_Account_Information_Page);
					    log.info("********************************************Confirm_Different_Value_Password_Error_Message_Locator_Account_Information_Page : *************************************************** " +Confirm_Different_Value_Password_Error_Message_Locator_Account_Information_Page);
						log.info("********************************************NewPwdErrMsgLocator : *************************************************** " +NewPwdErrMsgLocator);
						log.info("********************************************ConfPwdErrMsgLocator : *************************************************** " +ConfPwdErrMsgLocator);

						//webPage.getDriver().navigate().refresh();
					    
					    /*SoftAssertor.assertEquals(webPage.findObjectByxPath(NewPwdErrMsgLocator).getText(),ExpectedValMsg);
					SoftAssertor.assertEquals(webPage.findObjectByxPath(ConfPwdErrMsgLocator).getText(),ExpectedValMsg);*/
					
				} 
				else if (NameofTestCase.equalsIgnoreCase("invalidCrrpwd")) {
					log.info("********************************************Invalid_Correct_Value_Password_Error_Message_Locator_Account_Information_Page : *************************************************** " );
					
					//webPage.getDriver().navigate().refresh();
					//commonMethods.clickElementbyXpath(webPage, Change_Password_Locator, softAssert);
					
					//Thread.sleep(5000);
					Invalid_Correct_Value_Password_Error_Message_Locator_Account_Information_Page = commonMethods.getTextbyXpath(webPage,ConfPwdErrMsgLocator, softAssert);
					log.info("********************************************Invalid_Correct_Value_Password_Error_Message_Locator_Account_Information_Page : *************************************************** " +Invalid_Correct_Value_Password_Error_Message_Locator_Account_Information_Page);
					log.info("********************************************ConfPwdErrMsgLocator : *************************************************** " +ConfPwdErrMsgLocator);
					Thread.sleep(2000);
					SoftAssertor.assertEquals(webPage.findObjectByxPath(ConfPwdErrMsgLocator).getText(),ExpectedValMsg);

			}
				
			}
			errorMessage.add(Short_Password_Error_Message_Locator_Account_Information_Page);
			errorMessage.add(New_Different_Value_Password_Error_Message_Locator_Account_Information_Page);
			errorMessage.add(Confirm_Different_Value_Password_Error_Message_Locator_Account_Information_Page);
			errorMessage.add(Invalid_Correct_Value_Password_Error_Message_Locator_Account_Information_Page);
			//errorMessage.add(Valid_Correct_Password_Notification_Message_Locator_Account_Information_Page);
		//	log.info(":::::::::::::::::::::::::::::::::::::::::*********************************************    Short_Password_Error_Message_Locator_Account_Information_Page *************************************************** ::::::::::::::::::::::: " +Short_Password_Error_Message_Locator_Account_Information_Page);
		//	log.info(":::::::::::::::::::::::::::::::::::::::::*********************************************    New_Different_Value_Password_Error_Message_Locator_Account_Information_Page *************************************************** ::::::::::::::::::::::: " +New_Different_Value_Password_Error_Message_Locator_Account_Information_Page);
		//	log.info(":::::::::::::::::::::::::::::::::::::::::*********************************************    Confirm_Different_Value_Password_Error_Message_Locator_Account_Information_Page *************************************************** ::::::::::::::::::::::: " +Confirm_Different_Value_Password_Error_Message_Locator_Account_Information_Page);
		// log.info(":::::::::::::::::::::::::::::::::::::::::*********************************************    Invalid_Correct_Value_Password_Error_Message_Locator_Account_Information_Page *************************************************** ::::::::::::::::::::::: " +Invalid_Correct_Value_Password_Error_Message_Locator_Account_Information_Page);
		//	log.info(":::::::::::::::::::::::::::::::::::::::::*********************************************    Valid_Correct_Password_Notification_Message_Locator_Account_Information_Page  *************************************************** ::::::::::::::::::::::: " +Valid_Correct_Password_Notification_Message_Locator_Account_Information_Page);
		//	log.info(":::::::::::::::::::::::::::::::::::::::::*********************************************    ##################### errorMessage %%%%%%%%%%%%%%%%%%%%%%%%%% *************************************************** ::::::::::::::::::::::: " +errorMessage);
			
			return errorMessage;					
				
			
		}	
				/*else if (NameofTestCase.equalsIgnoreCase("invalidCrrpwd")) {
						log.info("********************************************Invalid_Correct_Value_Password_Error_Message_Locator_Account_Information_Page : *************************************************** " );
						
						webPage.getDriver().navigate().refresh();
						commonMethods.clickElementbyXpath(webPage, Change_Password_Locator, softAssert);
						
						Thread.sleep(5000);
						Invalid_Correct_Value_Password_Error_Message_Locator_Account_Information_Page = commonMethods.getTextbyCss(webPage,ConfPwdErrMsgLocator, softAssert);
						log.info("********************************************Invalid_Correct_Value_Password_Error_Message_Locator_Account_Information_Page : *************************************************** " +Invalid_Correct_Value_Password_Error_Message_Locator_Account_Information_Page);
						log.info("********************************************ConfPwdErrMsgLocator : *************************************************** " +ConfPwdErrMsgLocator);
						Thread.sleep(2000);
						SoftAssertor.assertEquals(webPage.findObjectByxPath(ConfPwdErrMsgLocator).getText(),ExpectedValMsg);

				} else if (NameofTestCase.equalsIgnoreCase("changepassword")) { 
					log.info("********************************************Valid_Correct_Password_Notification_Message_Locator_Account_Information_Page : *************************************************** " );

					Valid_Correct_Password_Notification_Message_Locator_Account_Information_Page = commonMethods.getTextbyXpath(webPage,ConfPwdErrMsgLocator, softAssert);
					log.info("********************************************Valid_Correct_Password_Notification_Message_Locator_Account_Information_Page : *************************************************** " +Valid_Correct_Password_Notification_Message_Locator_Account_Information_Page);
					log.info("********************************************ConfPwdErrMsgLocator : *************************************************** " +ConfPwdErrMsgLocator);
					//SoftAssertor.assertEquals(webPage.findObjectByxPath(ConfPwdErrMsgLocator).getText(),ExpectedValMsg);
				}
*/			
			
			//}
			
			
/*			String Change_Password_Locator1 = valid_data[0][0];
			String NameofTestCase1 = valid_data[0][12];
			String ConfPwdErrMsgLocator1 = valid_data[0][9];
			String ExpectedValMsg1 = valid_data[0][10];
			String ButtonLocator1 = valid_data[0][11];
			String Navigate_To_Account_Information_Tab_Form_Change_Password_URL = valid_data[0][16];
			webPage.getDriver().navigate().refresh();
			webPage.getDriver().navigate().to(Navigate_To_Account_Information_Tab_Form_Change_Password_URL);
			webPage.getDriver().navigate().refresh();
			commonMethods.clickElementbyXpath(webPage, inputdata[0][1], softAssert);
			commonMethods.clearElementbyXpath(webPage, inputdata[0][1], softAssert);
			commonMethods.sendKeysbyXpath(webPage, valid_data[0][1], valid_data[0][2], softAssert);
			commonMethods.clickElementbyXpath(webPage, valid_data[0][4], softAssert);
			commonMethods.clearElementbyXpath(webPage, valid_data[0][4], softAssert);
			commonMethods.sendKeysbyXpath(webPage, valid_data[0][4], valid_data[0][5], softAssert);
			commonMethods.clickElementbyXpath(webPage, inputdata[0][7], softAssert);
			commonMethods.clearElementbyXpath(webPage, inputdata[0][7], softAssert);
			commonMethods.sendKeysbyXpath(webPage, valid_data[0][7], valid_data[0][8], softAssert);
			log.info("**************#############******************************CurrPwdLocator :  " +valid_data[0][1]);
			log.info("*************#############*******************************NewPwdLocator  :  " +valid_data[0][2]);
			log.info("*************#############*******************************NewPwdinput    :  " + valid_data[0][4]);
			log.info("**************#############******************************ConfPwdLocator :  " + valid_data[0][5]);
			log.info("**************#############******************************ConfPwdinput   :  " +valid_data[0][7]);
			log.info("***************#############*****************************ButtonLocator  :  " +ButtonLocator1);
			commonMethods.clickElementbyXpath(webPage, ButtonLocator1, softAssert);
			log.info("******************************************** |||||||||||||||||||||||||||||||||||||||||||||||  ButtonLocator Clicked Successfully ||||||||||||||||||||||||||||||||||||||||||||  : *************************************************** " +ButtonLocator1);
			if (NameofTestCase1.equalsIgnoreCase("invalidCrrpwd")) {
				log.info("********************************************Invalid_Correct_Value_Password_Error_Message_Locator_Account_Information_Page : *************************************************** " );
				
				Invalid_Correct_Value_Password_Error_Message_Locator_Account_Information_Page = commonMethods.getTextbyXpath(webPage,ConfPwdErrMsgLocator1, softAssert);
				log.info("********************************************Invalid_Correct_Value_Password_Error_Message_Locator_Account_Information_Page : *************************************************** " +Invalid_Correct_Value_Password_Error_Message_Locator_Account_Information_Page);
				log.info("********************************************ConfPwdErrMsgLocator : *************************************************** " +ConfPwdErrMsgLocator1);
				
				SoftAssertor.assertEquals(webPage.findObjectByxPath(ConfPwdErrMsgLocator).getText(),ExpectedValMsg);

		}*/  /*if (NameofTestCase.equalsIgnoreCase("changepassword")) { 
			log.info("********************************************Valid_Correct_Password_Notification_Message_Locator_Account_Information_Page : *************************************************** " );

			Valid_Correct_Password_Notification_Message_Locator_Account_Information_Page = commonMethods.getTextbyXpath(webPage,ConfPwdErrMsgLocator, softAssert);
			log.info("********************************************Valid_Correct_Password_Notification_Message_Locator_Account_Information_Page : *************************************************** " +Valid_Correct_Password_Notification_Message_Locator_Account_Information_Page);
			log.info("********************************************ConfPwdErrMsgLocator : *************************************************** " +ConfPwdErrMsgLocator);
			//SoftAssertor.assertEquals(webPage.findObjectByxPath(ConfPwdErrMsgLocator).getText(),ExpectedValMsg);
		}
*/
			
			//************************************************************************************************************************************//
				
				
				/*webPage.findObjectByxPath(CurrPwdLocator).click();
				webPage.findObjectByxPath(CurrPwdLocator).clear();
				webPage.findObjectByxPath(CurrPwdLocator).sendKeys(CurrPwdinput);
				webPage.findObjectByxPath(NewPwdLocator).click();
				webPage.findObjectByxPath(NewPwdLocator).clear();
				webPage.findObjectByxPath(NewPwdLocator).sendKeys(NewPwdinput);
				webPage.findObjectByxPath(ConfPwdLocator).click();
				webPage.findObjectByxPath(ConfPwdLocator).clear();
				webPage.findObjectByxPath(ConfPwdLocator).sendKeys(ConfPwdinput);
				webPage.findObjectByxPath(ButtonLocator).click()
				
	
	webPage.getDriver().navigate().refresh();
						
						commonMethods.clickElementbyXpath(webPage, inputdata[3][1], softAssert);
						commonMethods.clearElementbyXpath(webPage, inputdata[3][1], softAssert);
						commonMethods.sendKeysbyXpath(webPage, inputdata[3][1], inputdata[3][2], softAssert);
						commonMethods.clickElementbyXpath(webPage, inputdata[3][4], softAssert);
						commonMethods.clearElementbyXpath(webPage, inputdata[3][4], softAssert);
						commonMethods.sendKeysbyXpath(webPage, inputdata[3][4], inputdata[3][5], softAssert);
						commonMethods.clickElementbyXpath(webPage, inputdata[3][7], softAssert);
						commonMethods.clearElementbyXpath(webPage, inputdata[3][7], softAssert);
						commonMethods.sendKeysbyXpath(webPage, inputdata[3][7], inputdata[3][8], softAssert);
						
						
						
						Robot robot=new Robot();
						// Press Enter
					    robot.keyPress(KeyEvent.VK_ENTER);
					    // Release Enter
					    robot.keyRelease(KeyEvent.VK_ENTER);
	
	
	
				*/
		


	
	
	
	
	
	
	public void verifyValidationMessageforNewAddressform(String[][] testdata) throws PageException {

		for (int i = 0; i < 2; i++) {
			try {
				webPage.findObjectByxPath(testdata[0][i]).click();
			} catch (Throwable e) {
				log.info(e.getLocalizedMessage());
			}
		}
		for (int i = 2; i < 4; i++) {
			try {
				webPage.findObjectByxPath(testdata[0][i]).clear();
			} catch (Throwable e) {
				log.info(e.getLocalizedMessage());
			}

		}
		webPage.findObjectByxPath(testdata[0][13]).click();
		for (int i = 4; i < 10; i++) {
			try {
				SoftAssertor.assertEquals(webPage.findObjectByxPath(testdata[0][i]).getText(), testdata[0][11],
						"Error message does not match for element");
			} catch (Throwable e) {
				log.info(e.getLocalizedMessage());
			}

		}
		SoftAssertor.assertEquals(webPage.findObjectByxPath(testdata[0][10]).getText(), testdata[0][12],
				"Error message does not match for element");

	}

	public void verifyValidformSubmission(String[][] data) throws PageException {
		for (int i = 0; i < 16; i++) {
			try {
				String locator = data[0][i];
				if (!(i == 10 || i == 14)) {
					webPage.findObjectByxPath(locator).click();
					webPage.findObjectByxPath(locator).clear();
				}
				i = i + 1;
				webPage.findObjectByxPath(locator).sendKeys(data[0][i]);
			} catch (Throwable e) {
				log.info(e.getLocalizedMessage());
			}

		}

	}

	public void LoginfuncInvalidInput(String[][] testdata) {

		for (int r = 0; r < testdata.length; r++) {

			String EmailAddlocator = testdata[r][0];
			String EmailErrlocator = testdata[r][1];
			String EmailAddInput = testdata[r][2];
			String ExpErrMsgEmail = testdata[r][3];
			String Pwdlocator = testdata[r][4];
			String PwdErrlocator = testdata[r][5];
			String PwdInput = testdata[r][6];
			String ExpErrMsgPwd = testdata[r][7];
			String LoginButtLocator = testdata[r][8];
			try {
				
			/*	log.info(" EmailAddInput : " +EmailAddInput);
				log.info(" PwdInput : " +PwdInput);
				webPage.findObjectByxPath(EmailAddlocator).sendKeys(EmailAddInput);
				webPage.findObjectByxPath(Pwdlocator).sendKeys(PwdInput);*/
				/*
				if (!(EmailAddInput.equalsIgnoreCase("NA") && PwdInput.equalsIgnoreCase("NA"))) {
					log.info(" EmailAddInput : " +EmailAddInput);
					log.info(" Pwdlocator : " +Pwdlocator);
					webPage.findObjectByxPath(EmailAddlocator).sendKeys(EmailAddInput);
					webPage.findObjectByxPath(Pwdlocator).sendKeys(PwdInput);

				}
				else if ((EmailAddInput.equalsIgnoreCase("NA") && PwdInput.equalsIgnoreCase("NA"))) {
					webPage.findObjectByxPath(EmailAddlocator).sendKeys(EmailAddInput);
					webPage.findObjectByxPath(Pwdlocator).sendKeys(PwdInput);
				}*/
				
				if (!(EmailAddInput.equalsIgnoreCase("NA") && PwdInput.equalsIgnoreCase("NA"))) {
				
				webPage.findObjectByxPath(EmailAddlocator).clear();
				webPage.findObjectByxPath(Pwdlocator).clear();
				webPage.findObjectByxPath(EmailAddlocator).sendKeys(EmailAddInput);
				webPage.findObjectByxPath(Pwdlocator).sendKeys(PwdInput);
				webPage.findObjectByxPath(LoginButtLocator).click();
				String ActualEmailErrMsg = webPage.findObjectByxPath(EmailErrlocator).getText();
				SoftAssertor.assertEquals(ActualEmailErrMsg, ExpErrMsgEmail, "Email address error msg does not match");
				String ActualPwdErrMsg = webPage.findObjectByxPath(PwdErrlocator).getText();
				SoftAssertor.assertEquals(ActualPwdErrMsg, ExpErrMsgPwd, "Password Error msg does not match");
			
			}
				
				
				
				if ((EmailAddInput.equalsIgnoreCase("NA") && PwdInput.equalsIgnoreCase("NA"))) {
					
					
					webPage.findObjectByxPath(EmailAddlocator).clear();
					webPage.findObjectByxPath(Pwdlocator).clear();
					webPage.findObjectByxPath(EmailAddlocator).sendKeys(EmailAddInput);
					webPage.findObjectByxPath(Pwdlocator).sendKeys(PwdInput);
					webPage.findObjectByxPath(LoginButtLocator).click();
					String ActualEmailErrMsg = webPage.findObjectByxPath(EmailErrlocator).getText();
					SoftAssertor.assertEquals(ActualEmailErrMsg, ExpErrMsgEmail, "Email address error msg does not match");
					String ActualPwdErrMsg = webPage.findObjectByxPath(PwdErrlocator).getText();
					SoftAssertor.assertEquals(ActualPwdErrMsg, ExpErrMsgPwd, "Password Error msg does not match");
				}
				
			}

			catch (Throwable e) {
				SoftAssertor.addVerificationFailure(e.getMessage());
				log.error("Login funcationlity for Invalid Input Failed");
				log.error(e.getMessage());
			}
		}
	}
	
	
	public void WhatsThisOverlay_Rendered(String[][] testdata) {

		String Locator = testdata[0][0];
		String contentonoverlaylocator = testdata[0][1];
		String expectedCotent = testdata[0][2];
		String closebutlocator = testdata[0][3];
		try {
			log.info("Enter");
			webPage.findObjectByCss(Locator).click();
			log.info("Clicked");
			
			String ActualContent = webPage.findObjectByCss(contentonoverlaylocator).getText();
			SoftAssertor.assertEquals(ActualContent, expectedCotent, "content on overlay does not match");

		} catch (Throwable e) {
			SoftAssertor.addVerificationFailure(e.getMessage());
			
			
			log.error("What's This Overlay failed");
			log.error(e.getMessage());
		}
		finally
		{
			try {
				webPage.findObjectByCss(closebutlocator).click();
			} catch (PageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void WhatsThisOverlay_Close(String[][] testdata) {

		String Locator = testdata[0][0];
		String contentonoverlaylocator = testdata[0][1];
		String expectedCotent = testdata[0][2];
		String closebutlocator = testdata[0][3];
		try {
			log.info("Enter");
			webPage.findObjectByxPath(Locator).click();
			log.info("Clicked");
			//webPage.findObjectByxPath(Locator).click();
			String ActualContent = webPage.findObjectByCss(contentonoverlaylocator).getText();
			SoftAssertor.assertEquals(ActualContent, expectedCotent, "content on overlay does not match");
			log.info("ActualContent : " +ActualContent);
			log.info("expectedCotent : " +expectedCotent);
			webPage.findObjectByCss(closebutlocator).click();
		} catch (Throwable e) {
			SoftAssertor.addVerificationFailure(e.getMessage());
			
			log.error("What's This Overlay failed");
			log.error(e.getMessage());
		}
		finally
		{
			try {
				webPage.findObjectByxPath(closebutlocator).click();
			} catch (PageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void ForgotPasswordPageTitle(String[][] testdata) {

		try {
			String ForgotPwdlink = testdata[0][2];
			webPage.findObjectByxPath(ForgotPwdlink).click();
			verifyPageTitle(testdata);
		} catch (Throwable e) {
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Forgot Password Page Title verification failed");
			log.error(e.getMessage());
		}
	}

	public void verifyforgotPwdPage_verify_Forgot_Password_Function_with_Valid_Invalid_Data(String[][] testingdata) throws PageException {
		String ForgotPwdlink = testingdata[0][5];
		webPage.findObjectByxPath(ForgotPwdlink).click();
		

		for (int r = 0; r < testingdata.length; r++) {
		
		String EmailAddlocator = testingdata[r][0];
		String EmailAddInput = testingdata[r][1];
		String EmailAddError = testingdata[r][2];
		String SubmitButt = testingdata[r][3];
		String ExpErrMsg = testingdata[r][4];
		
		

		try {
			/*if (!(EmailAddInput.equalsIgnoreCase("NA"))) {
				webPage.findObjectByxPath(EmailAddlocator).sendKeys(EmailAddInput);

			}
			webPage.findObjectByxPath(SubmitButt).click();
			String ActualErr = webPage.findObjectByxPath(EmailAddError).getText();
			SoftAssertor.assertEquals(ActualErr, ExpErrMsg, "Error message does not match");*/
			
			if (!(EmailAddInput.equalsIgnoreCase("NA"))) {
				log.info("When Email ID Address Input is not NA");
				
				webPage.findObjectByxPath(EmailAddlocator).clear();
				webPage.findObjectByxPath(EmailAddlocator).sendKeys(EmailAddInput);
				
				webPage.findObjectByxPath(SubmitButt).click();
				String ActualErr = webPage.findObjectByxPath(EmailAddError).getText();
				SoftAssertor.assertEquals(ActualErr, ExpErrMsg, "Error message does not match");

			}
			
			
			else if ((EmailAddInput.equalsIgnoreCase("NA"))) {
				
				log.info("When Email ID Address Input is NA");
			/*	webPage.findObjectByxPath(EmailAddlocator).sendKeys(EmailAddInput);
				webPage.findObjectByxPath(EmailAddlocator).clear();*/
				
				webPage.findObjectByxPath(SubmitButt).click();
				String ActualErr = webPage.findObjectByxPath(EmailAddError).getText();
				SoftAssertor.assertEquals(ActualErr, ExpErrMsg, "Error message does not match");

				}
				
			
		
			} catch (Throwable e) {
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("Forgot Password Page Title verification failed");
			log.error(e.getMessage());
		}

	}
}

	public void verify_Account_Information_Tab_Change_Password_Functionality_Validation(String[][] inputdata) throws PageException {
		// TODO Auto-generated method stub
		for (int r = 0; r < inputdata.length; r++) {

			String CurrPwdLocator = inputdata[r][1];
			String CurrPwdinput = inputdata[r][2];
			String CurrPwdErrMsgLocator = inputdata[r][3];
			String NewPwdLocator = inputdata[r][4];
			String NewPwdinput = inputdata[r][5];
			String NewPwdErrMsgLocator = inputdata[r][6];
			String ConfPwdLocator = inputdata[r][7];
			String ConfPwdinput = inputdata[r][8];
			String ConfPwdErrMsgLocator = inputdata[r][9];
			String ExpectedValMsg = inputdata[r][10];
			String ButtonLocator = inputdata[r][11];
			String NameofTestCase = inputdata[r][12];
		JavascriptExecutor jse = (JavascriptExecutor)webPage.getDriver();
		jse.executeScript("scroll(0, 250);");
		webPage.findObjectByxPath(CurrPwdLocator).click();

		webPage.findObjectByxPath(CurrPwdLocator).clear();
		webPage.findObjectByxPath(CurrPwdLocator).sendKeys(CurrPwdinput);
		webPage.findObjectByxPath(NewPwdLocator).click();
		webPage.findObjectByxPath(NewPwdLocator).clear();
		webPage.findObjectByxPath(NewPwdLocator).sendKeys(NewPwdinput);
		webPage.findObjectByxPath(ConfPwdLocator).click();
		webPage.findObjectByxPath(ConfPwdLocator).clear();
		webPage.findObjectByxPath(ConfPwdLocator).sendKeys(ConfPwdinput);
		webPage.findObjectByxPath(ButtonLocator).click();
		
	}
	}

}