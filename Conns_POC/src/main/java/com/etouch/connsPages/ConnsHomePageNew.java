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
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.testng.Assert;

import com.etouch.common.CommonPage;
import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.TestBedManager;
import com.etouch.taf.core.exception.PageException;
import com.etouch.taf.util.CommonUtil;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.taf.webui.ITafElement;
import com.etouch.taf.webui.selenium.WebPage;

public class ConnsHomePageNew extends CommonPage {

	/**
	 *
	 * @param sbPageUrl
	 *            the sb page url
	 * @param webPage
	 *            the web page
	 */

	// private TestFooterPage testfooterPage;
	static Log log = LogUtil.getLog(ConnsHomePageNew.class);
	String testType;
	String testBedName;

	public ConnsHomePageNew(String sbPageUrl, WebPage webPage) {
		super(sbPageUrl, webPage);
		CommonUtil.sop("webDriver in eTouchWebSite Page : " + webPage.getDriver());
		loadPage();
	}

	public void verifyPageTitle(String expurl, String expTitle) {

		try {
			log.info("Actual URL of the page is : " + webPage.getCurrentUrl());
			log.info("Actual Title of the page is : " + webPage.getPageTitle());

			//SoftAssertor.assertEquals(expurl, webPage.getCurrentUrl());
			SoftAssertor.assertEquals(expTitle, webPage.getPageTitle());

		} catch (Exception e) {
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("verifyPageTitle failed");
			log.error(e.getMessage());
		}
	}

	public void verifyFontAndSize(String[][] testdata, String testType) {
		int count = 0;
		List<Integer> failedElements = new ArrayList<Integer>();
		for (int r = 0; r < testdata.length; r++) {

			String Locator = testdata[r][0];
			String fontAttribute = testdata[r][1];
			String fontSize = null;
			if (testType.equalsIgnoreCase("Web")) {
				fontSize = testdata[r][2];
			} else {
				fontSize = testdata[r][3];
			}
			try {
				log.info("Verifying font size and style for element no. " + (r + 1));
				ITafElement pageHeading = webPage.findObjectByCss(Locator);
				String value = pageHeading.getCssValue(fontAttribute).replaceAll("\"", "").replaceAll(" ", "")
						.toLowerCase().trim();
				Assert.assertTrue(value.contains(fontSize) || fontSize.contains(value),
						"Verify Font Size and Style failed.!!!" + "Font Attribute name " + fontAttribute + "Actual : "
								+ value + " and Expected :" + fontSize.trim());

			} catch (Throwable e) {
				count++;
				failedElements.add(count);
				SoftAssertor.addVerificationFailure(e.getMessage());
				log.error("verifyFontSizeAndStyle failed");
				log.error(e.getMessage());
			}

		}
		if (count > 0) {
			Assert.fail("Failed to verify element number : " + Arrays.deepToString(failedElements.toArray()));
		}
		log.info("Ending verifyFontSizeAndStyle");

	}

	public void verifyBrokenImage() {
		List<WebElement> imagesList = webPage.getDriver().findElements(By.tagName("img"));
		log.info("Total number of images" + imagesList.size());
		int imageCount = 0;
		List<Integer> brokenImageNumber = new ArrayList<Integer>();
		List<String> brokenImageSrc = new ArrayList<String>();
		for (WebElement image : imagesList) {

			try {
				imageCount++;
				log.info("Verifying image number : " + imageCount);
				HttpClient client = HttpClientBuilder.create().build();
				HttpGet request = new HttpGet(image.getAttribute("src"));

				HttpResponse response = client.execute(request);
				if (response.getStatusLine().getStatusCode() == 200) {
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
			}
		}
		if (brokenImageNumber.size() > 0) {
			Assert.fail("Image number of the broken images : " + Arrays.deepToString(brokenImageNumber.toArray())
					+ "Image source of the broken images : " + Arrays.deepToString(brokenImageSrc.toArray()));

		}

	}

	public void verifylinks(String[][] data, TestBed testBedName, String testType) {

		List<String> brokenLinks = new ArrayList<String>();
		String xPath = null;
		String ExpectedURL = null;
		String actualUrl = "";
		String mobileElement = null;
		String mobileParentElement = null;
		String elementName = null;

		for (int r = 0; r < data.length; r++) {
			elementName = data[r][0];
			ExpectedURL = data[r][4];
			if (testType.equalsIgnoreCase("Mobile")) {
				xPath = data[r][2];
				mobileParentElement = data[r][3];

			} else {
				xPath = data[r][1];

			}
			try {
				log.info("Verifying Link --->" + elementName);
				if (testType.equalsIgnoreCase("Mobile") && !mobileParentElement.equalsIgnoreCase("NA")) {
					webPage.findObjectByxPath(mobileParentElement).click();
					webPage.findObjectByxPath(xPath).click();
					try {
						webPage.getDriver().navigate().back();
						// webPage.getDriver().switchTo().alert().accept();

					} // try
					catch (NoAlertPresentException Ex) {
						log.info("No Alert found");
					}

				} else {

					webPage.findObjectByxPath(xPath).click();
					String existingWindow = null;
					String newWindow = null;
					existingWindow = webPage.getDriver().getWindowHandle();
					Set<String> windows = webPage.getDriver().getWindowHandles();
					if (windows.size() >= 2) {
						windows.remove(existingWindow);
						newWindow = windows.iterator().next();
						log.info("Existing window id is" + existingWindow);
						log.info("New window id is" + newWindow);

						// Set<String> windows =
						// webPage.getDriver().getWindowHandles();
						// String existing_window = (String)
						// windows.toArray()[0];..
						// String new_window = (String) windows.toArray()[1];
						webPage.getDriver().switchTo().window(newWindow);
						Thread.sleep(3000);
						actualUrl = webPage.getCurrentUrl();
						webPage.getDriver().close();
						webPage.getDriver().switchTo().window(existingWindow);
						log.info("Expected URL" + ExpectedURL);
						log.info("Actual URL" + actualUrl);
						SoftAssertor.assertTrue(actualUrl.contains(ExpectedURL),
								xPath + " failed " + actualUrl + " " + ExpectedURL);
					} else {
						actualUrl = webPage.getCurrentUrl();
						webPage.getBackToUrl();
						SoftAssertor.assertTrue(actualUrl.contains(ExpectedURL),
								xPath + " failed " + actualUrl + " " + ExpectedURL);
						log.info("testing verifyLinkNavigation completed------>");

					}

				}
				try {
					/*
					 * if(webPage.findObjectsByTag("iframe").size()>0) {
					 * webPage.getDriver().switchTo().frame(webPage.getDriver().
					 * findElement(By.xpath(".//iframe[@id='lightbox_pop']")));
					 * webPage.findObjectByxPath(".//*[@id='es']").click();;
					 * actualUrl = webPage.getCurrentUrl(); }
					 */
					// else{
					// actualUrl = webPage.getCurrentUrl();
					// }
				} catch (NoSuchElementException e) {
					brokenLinks.add(elementName + " " + e.getLocalizedMessage());
					actualUrl = webPage.getCurrentUrl();
					System.out.println(e);
				}

				// Assert.assertTrue(actualUrl.endsWith(ExpectedURL),xPath+"
				// failed "+ actualUrl+" "+ExpectedURL);

			} catch (Exception e) {
				brokenLinks.add(elementName + " " + e.getLocalizedMessage());
				log.info("Failed to Verifying Link --->" + actualUrl);
				// SoftAssertor.addVerificationFailure(e.getMessage());
				log.error("verifyLinkNavigation failed");
				log.error(e.getMessage());
			}
			if (brokenLinks.size() > 0) {
				Assert.fail("Link " + Arrays.deepToString(brokenLinks.toArray()) + " are not working as expected");
			}
			// System.out.println("Test is
			// "+webPage.findObjectByxPath(".//*[@id='slide-nav']/div/div[1]/div/div[3]/div[4]/div/p/a").getText());
		}

	}

	public void verifyYourCart(String[][] test, String testType) throws PageException, InterruptedException {

		try {
			if (testType.equalsIgnoreCase("Web")) {
				// hover on parent menu option in desktop browser
				webPage.hoverOnElement(By.xpath(test[0][0]));
				log.info("Clicking on Sub - link");
				webPage.findObjectByxPath(test[0][1]).click();
				String ExpectedProduct = webPage.findObjectByxPath(test[0][2]).getText();
				log.info("Clicking on product");
				webPage.findObjectByxPath(test[0][2]).click();
				log.info("Clicking on Add to product");
				webPage.findObjectByxPath(test[0][3]).click();

				String ZipCode = test[0][5];

				log.info("Adding Zip code");

				webPage.findObjectByxPath(test[0][4]).clear();
				webPage.findObjectByxPath(test[0][4]).sendKeys(ZipCode);
				log.info("Clicking on Update button");

				webPage.findObjectByxPath(test[0][6]).click();
				Thread.sleep(8000);

				log.info("Clicking on Add to Cart button");
				webPage.findObjectByxPath(test[0][7]).click();
				log.info("Clicking on YourCart link");
				webPage.findObjectByxPath(test[0][8]).click();

				String ExpecyedProductinCart = webPage.findObjectByxPath(test[0][9]).getText();
				SoftAssertor.assertEquals(ExpectedProduct, ExpecyedProductinCart);
				log.info("Clicking on CheckOut button");
				webPage.findObjectByxPath(test[0][10]).click();
				String ExpectedURL = test[0][11];
				String actualUrl = webPage.getCurrentUrl();
				SoftAssertor.assertTrue(actualUrl.endsWith(ExpectedURL), " failed " + actualUrl + " " + ExpectedURL);
				log.info("Verified Your Cart");

			}

		} catch (Exception e) {
			SoftAssertor.addVerificationFailure(e.getMessage());
			log.error("verifyPageTitle failed");
			log.error(e.getMessage());
		}
	}

	public void verifyNavigationLinks(String[][] testdata, String url, String testType) {

		// String MenuElement = testdata[0][0];
		String ChildElementIdentifier = null;
		String expectedURL = null;
		String elementName = null;
		String actualUrl = null;
		// String brokenLinkName = null;
		List<String> brokenLinks = new ArrayList<String>();
		boolean run = true;

		for (int i = 0; i < testdata.length; i++) {
			expectedURL = testdata[i][2];
			elementName = testdata[i][3];
			ChildElementIdentifier = testdata[i][1];
			try {
				if (testType.equalsIgnoreCase("Web")) {
					// hover on parent menu option in desktop browser
					webPage.hoverOnElement(By.xpath(testdata[i][0]));
					Thread.sleep(2000);
					log.info("Hovered on Main Menu link");
				} else {
					// Click on hamburger menu and Parent menu option when
					// executing on mobile device
					webPage.findObjectByxPath(testdata[i][4]).click();
					webPage.findObjectByxPath(testdata[i][0]).click();

					// sets run flag to false to skip validation of link
					// navigation as this links are parent links to show child
					// elements
					if (testdata[i][5].isEmpty()) {

						webPage.findObjectByxPath(testdata[i][4]).click();
						run = false;

					}
					// if Second parent on mobile device does not have any
					// child, below code will set run flag to test the second
					// parent redirection link
					// and set i++ to skip next element from sheet(child
					// element) which is present for desktop browser only
					else if (testdata[i][5].equalsIgnoreCase("Test")) {
						run = true;
						i++;
					}
					// clicks on child element on mobile device
					else {
						System.out.println("test[i][5] is    --------->  :" + testdata[i][5]);
						webPage.findObjectByxPath(testdata[i][5]).click();
						run = true;
					}
				}
				// run if element in execution is a child element
				if (run) {

					System.out.println(
							testdata[i][0] + " " + elementName + " " + ChildElementIdentifier + " " + expectedURL);
					log.info("Verifying link :" + elementName);
					if (ChildElementIdentifier.contains("//")) {
						webPage.findObjectByxPath(ChildElementIdentifier).click();
						log.info("Clicking on link");
					} else {
						webPage.findObjectByLink(ChildElementIdentifier).click();
						log.info("Clicking on link");
					}

					actualUrl = webPage.getCurrentUrl();

					webPage.getBackToUrl();
					SoftAssertor.assertTrue(actualUrl.endsWith(expectedURL),
							" failed " + actualUrl + " " + expectedURL);
				}

			} catch (Throwable e) {
				webPage.navigateToUrl(url);
				brokenLinks.add(elementName + " " + e.getLocalizedMessage());
				log.error("Failed to verify link :" + elementName);
				log.error(e.getMessage());
			}
		}
		if (brokenLinks.size() > 0) {
			Assert.fail("Link " + Arrays.deepToString(brokenLinks.toArray()) + " are not working as expected");
		}
		log.info("testing verifyLinksForFurnitureAndMattresses completed------>");

	}

	public void verifySaveBigWithConnsSection(String[][] test) {
		String SaveBigMenuOptionIdentifier = null;
		String CarouselLeft = null;
		String CarouselRight = null;
		String ElementPosition1 = null;
		String ElementPosition2 = null;
		String ClickForDetails = null;
		String PopUp = null;
		List<String> errors = new ArrayList<String>();

		for (int i = 0; i < test.length; i++) {

			try {

				SaveBigMenuOptionIdentifier = test[i][0].trim();
				CarouselLeft = test[i][1];
				CarouselRight = test[i][2];
				ElementPosition1 = test[i][3];
				ElementPosition2 = test[i][4];
				ClickForDetails = test[i][5];
				PopUp = test[i][6];

				System.out.println(" " + SaveBigMenuOptionIdentifier + " " + CarouselLeft + " " + CarouselRight + " "
						+ ElementPosition1 + " " + ElementPosition2 + " " + ClickForDetails + " " + PopUp);
				log.info("Verifying Element :" + SaveBigMenuOptionIdentifier);
				webPage.findObjectByxPath(SaveBigMenuOptionIdentifier).click();
				webPage.findObjectByxPath(CarouselLeft).click();
				String textAtPosition1 = webPage.findObjectByxPath(ElementPosition1).getText();
				System.out.println("Expected Left: " + textAtPosition1);
				for (int j = 0; j < 3; j++) {
					webPage.findObjectByxPath(CarouselLeft).click();
				}
				String textAtPosition2 = webPage.findObjectByxPath(ElementPosition2).getText();
				System.out.println("Actual Left : " + textAtPosition2);
				SoftAssertor.assertEquals(textAtPosition1, textAtPosition2,
						" failed " + textAtPosition1 + " " + textAtPosition2);

				// webPage.findObjectByxPath(CarouselRight).click();
				log.info("Clicked on element2");
				String eletextAtPosition1 = webPage.findObjectByxPath(ElementPosition2).getText();
				System.out.println("Expected Right: " + eletextAtPosition1);
				for (int k = 0; k < 3; k++) {
					webPage.findObjectByxPath(CarouselRight).click();
				}
				String eletextAtPosition2 = webPage.findObjectByxPath(ElementPosition1).getText();
				System.out.println("Actual Right: " + eletextAtPosition2);
				SoftAssertor.assertEquals(eletextAtPosition1, eletextAtPosition2,
						" failed " + eletextAtPosition1 + " " + eletextAtPosition2);

				if (!webPage.findObjectsByXpath(ClickForDetails).isEmpty()) {
					List<ITafElement> clickforprice = new ArrayList<ITafElement>();
					clickforprice = webPage.findObjectsByXpath(ClickForDetails);
					System.out.println("Size is :" + clickforprice.size());
					for (int s = 0; s < clickforprice.size(); s++) {
						if (clickforprice.get(s).getWebElement().isDisplayed()) {
							// try{
							System.out.println("Clicking ");
							clickforprice.get(s).click();
							System.out.println("Clicked");
							if (webPage.findObjectByxPath(PopUp).isDisplayed()) {
								System.out.println("PopUp Displyed");

							} else {
								errors.add("Unable to find popup after click");

							}
							s = clickforprice.size();
						}

					}

					if (webPage.findObjectByxPath(PopUp).isDisplayed()) {
						log.info("PopUp Displyed");

					} else {
						errors.add("Unable to find popup after click");

					}
				}
			} catch (Throwable e) {
				errors.add(e.getLocalizedMessage());
				log.error(e.getMessage());
			}
		}
		if (errors.size() > 0) {
			Assert.fail(Arrays.deepToString(errors.toArray()) + " are not working as expected");
		}

	}

	public void elementVisiblity(String element, String url) {
		try {

			webPage.findObjectByxPath(element).isDisplayed();

		} catch (Throwable e) {
			webPage.navigateToUrl(url);
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void contentVerification(String element, String expectedContent, String url) {
		try {
			SoftAssertor.assertEquals(webPage.findObjectByxPath(element).getText(), expectedContent,
					"Copyright Text Failed to Match");

		} catch (Throwable e) {
			webPage.navigateToUrl(url);
			Assert.fail(e.getLocalizedMessage());
		}
	}

	

}
