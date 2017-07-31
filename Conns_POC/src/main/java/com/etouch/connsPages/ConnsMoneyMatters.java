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
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

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

public class ConnsMoneyMatters extends CommonPage {

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

	public ConnsMoneyMatters(String sbPageUrl, WebPage webPage) 
	{
		super(sbPageUrl, webPage);
		CommonUtil.sop("webDriver in eTouchWebSite Page : " + webPage.getDriver());
		loadPage();
		//webPage.getDriver().manage().window().maximize();
	}
	
	
/*	public void verifyBrokenImage() {
		List<WebElement> imagesList = webPage.getDriver().findElements(By.tagName("img"));
		log.info("Total number of images : " + imagesList.size());
		int imageCount = 0;
		List<Integer> brokenImageNumber = new ArrayList<Integer>();
		List<String> brokenImageSrc = new ArrayList<String>();
		for (WebElement image : imagesList) 		
		{
			try {
				imageCount++;				
				log.info("Verifying image number : " + imageCount);
				HttpClient client = HttpClientBuilder.create().build();
				HttpGet request = new HttpGet(image.getAttribute("src"));

				HttpResponse response = client.execute(request);
				//log.info("src : " + image.getAttribute("src"));
				//log.info("response.getStatusLine().getStatusCode() : " + response.getStatusLine().getStatusCode());
				if (response.getStatusLine().getStatusCode() == 200||response.getStatusLine().getStatusCode() == 451) {
					log.info("Image number " + imageCount + " is as expected "
							+ response.getStatusLine().getStatusCode());
				} else {
					brokenImageNumber.add(imageCount);
					brokenImageSrc.add(image.getAttribute("src"));
					log.info("Image number " + imageCount + " is not as expected "
							+ response.getStatusLine().getStatusCode());
					log.info("Broken Image source is : " + image.getAttribute("src"));

				}
			} catch (Exception e) {
				log.info("Image number ....." + imageCount + " is not as expected ");
				brokenImageNumber.add(imageCount);
				brokenImageSrc.add(image.getAttribute("src"));
				log.info("imageCount  : " + imageCount + " : " + brokenImageSrc);			
			}

		}
		if (brokenImageNumber.size() > 0) {

			Assert.fail("Image number of the broken images : " + Arrays.deepToString(brokenImageNumber.toArray())
			+ " -- Image source of the broken images : " + Arrays.deepToString(brokenImageSrc.toArray()));

		}

	}*/	
	
	
/*	public void verifyBrokenLinks() {
		List<WebElement> linkList = webPage.getDriver().findElements(By.tagName("a"));
		log.info("Total number of links : " + linkList.size());
		int linkCount = 0;
		List<Integer> brokenLinkNumber = new ArrayList<Integer>();
		List<String> brokenLinkHref = new ArrayList<String>();
		for (WebElement link : linkList) 		
		{
			try {
				linkCount++;				
				log.info("Verifying link number : " + linkCount);
				HttpClient client = HttpClientBuilder.create().build();
				HttpGet request = new HttpGet(link.getAttribute("href"));

				HttpResponse response = client.execute(request);
				//log.info("src : " + image.getAttribute("src"));
				//log.info("response.getStatusLine().getStatusCode() : " + response.getStatusLine().getStatusCode());
				if (response.getStatusLine().getStatusCode() == 200||response.getStatusLine().getStatusCode() == 451) {
					log.info("Link number " + linkCount + " is as expected "
							+ response.getStatusLine().getStatusCode());
				} else {
					brokenLinkNumber.add(linkCount);
					brokenLinkHref.add(link.getAttribute("src"));
					log.info("Link number " + linkCount + " is not as expected "
							+ response.getStatusLine().getStatusCode());
					log.info("Broken Link source is : " + link.getAttribute("href"));

				}
			} catch (Exception e) {
				log.info("Image number ....." + linkCount + " is not as expected ");
				brokenLinkNumber.add(linkCount);
				brokenLinkHref.add(link.getAttribute("src"));
				log.info("imageCount  : " + linkCount + " : " + brokenLinkHref);			
			}

		}
		if (brokenLinkNumber.size() > 0) {

			Assert.fail("Link number of the broken links : " + Arrays.deepToString(brokenLinkNumber.toArray())
			+ "Link href of the broken links : " + Arrays.deepToString(brokenLinkHref.toArray()));

		}

	}*/	
	
}	