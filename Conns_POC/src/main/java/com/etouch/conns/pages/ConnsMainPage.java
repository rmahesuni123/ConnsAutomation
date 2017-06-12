package com.etouch.conns.pages;
import org.openqa.selenium.Keys;

import com.etouch.conns.common.CommonPage;
import com.etouch.taf.core.exception.PageException;
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
	
	
}
