package com.etouch.connsPages;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.etouch.common.CommonPage;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.taf.webui.selenium.WebPage;

/**
 * The Class ConnsMainPage.
 */
public class ConnsMainPage extends CommonPage {
	/**
	 * Instantiates a new conns main page.
	 *
	 * @param sbPageUrl
	 *            the sb page url
	 * @param webPage
	 *            the web page
	 */

	public ConnsMainPage(String sbPageUrl, WebPage webPage) {
		super(sbPageUrl, webPage);

		// webDriver = webPage.getDriver();
		CommonUtil.sop("webDriver in Conns Main Page " + webPage.getDriver());

		// startRecording();
		// if(TestBedManager.INSTANCE.getCurrentTestBed().getDevice().getName()
		// != null){

		//loadPage();

		// }
	}

	public void contentVerification(String[][] contentData, String url) throws PageException {
		// try {
		for (int i = 0; i < contentData.length; i++) {
			System.out.println("Actual:  " + webPage.findObjectByxPath(contentData[i][0]).getText() + "   Expected: "
					+ contentData[i][1]);
			SoftAssertor.assertTrue(webPage.findObjectByxPath(contentData[i][0]).getText().contains(contentData[i][1]),
					"expectedContent Failed to Match Actual");
		}
		/*
		 * } catch (Throwable e) { webPage.navigateToUrl(url);
		 * Assert.fail(e.getLocalizedMessage()); }
		 */
	}

	/**
	 * Gets the page url.
	 *
	 * @return the page url
	 * @throws InterruptedException
	 */
	public String getPageUrl() throws InterruptedException {
		return webPage.getCurrentUrl();
	}

	public void getScreenShotForFailure(WebPage webPage, String methodName) {
		try {
			File scrFile = ((TakesScreenshot) webPage.getDriver()).getScreenshotAs(OutputType.FILE);
			String testEnv = System.getenv().get("Environment");
			File targetFile = new File("ConnsTestData/Output/" + testEnv + "/FailureImage/" + methodName + ".png");
			FileUtils.copyFile(scrFile, targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void catchBlock(Throwable e, WebDriver driver, String methodName) throws IOException {
		try {
			e.printStackTrace();
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String testEnv = System.getenv().get("Environment");
			File targetFile = new File("ConnsTestData/Output/" + testEnv + "/FailureImage/" + methodName + ".png");
			FileUtils.copyFile(scrFile, targetFile);
			Assert.fail("Test Case ::: " + methodName + " failed as :::" + e.getLocalizedMessage());
			driver.close();
		} catch (Exception et) {
			et.printStackTrace();
		}
	}

	public boolean isSortedFloat(List<WebElement> list) {
		boolean sorted = true;
		String str1;
		String str2;
		str1=list.get(0).getText().replace("$", "").replace(",", "");
		for (int i = 1; i < list.size(); i++) {
			String actualString=list.get(i).getText();
			str2=actualString.replace("$", "").replace(",", "");
			Float a=Float.parseFloat(str1);
			Float b=Float.parseFloat(str2);
			System.out.println(
					"Comparing:" + a + "  With " + b);
			if (a>b) {
				System.out.println(
						"Failed for comparison among:" + a + "  And " + b);
				sorted = false;
				break;
			}
			str1=str2;
		}
		return sorted;
	}
	public boolean isSortedDescFloat(List<WebElement> list) {
		boolean sorted = true;
		String str1;
		String str2;
		str1=list.get(0).getText().replace("$", "").replace(",", "");
		for (int i = 1; i < list.size(); i++) {
			String actualString=list.get(i).getText();
			str2=actualString.replace("$", "").replace(",", "");
			Float a=Float.parseFloat(str1);
			Float b=Float.parseFloat(str2);
			System.out.println(
					"Comparing:" + a + "  With " + b);
			if (b>a) {
				System.out.println(
						"Failed for comparison among:" + a + "  And " + b);
				sorted = false;
				break;
			}
			str1=str2;
		}
		return sorted;
	}
	public boolean isSortedByName(List<WebElement> list) {
		boolean sorted = true;
		String str1;
		String str2;
		str1=list.get(0).getText();
		for (int i = 1; i < list.size(); i++) {
		    str2=list.get(i).getText();
			System.out.println(
					"Comparing:" + str1 + "  With " + str2);
			if (str1.substring(0, 2).compareTo(str2.substring(0, 2))>0) {
				System.out.println(
						"Failed for comparison among:" + str1 + "  And " + str2);
				sorted = false;
				break;
			}
			str1=str2;	
		}
		return sorted;
	}
	public boolean isSortedByNameDesc(List<WebElement> list) {
		boolean sorted = true;
		String str1;
		String str2;
		str1=list.get(0).getText();
		for (int i = 1; i < list.size(); i++) {
			str2=list.get(i).getText();
			System.out.println(
					"Comparing:" + str1 + "  With " + str2);
			if (str1.substring(0, 2).compareTo(str2.substring(0, 2))<0) {
				System.out.println(
						"Failed for comparison among:" + str1 + "  And " + str2);
				sorted = false;
				break;
			}
			str1=str2;		
		}
		return sorted;
	}
}
