package com.etouch.conns.pages;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.etouch.conns.common.CommonPage;
import com.etouch.taf.util.CommonUtil;
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

		loadPage();

		// }
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

	public static void getScreenShotForFailure(WebPage webPage, String methodName) throws IOException {
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
}
