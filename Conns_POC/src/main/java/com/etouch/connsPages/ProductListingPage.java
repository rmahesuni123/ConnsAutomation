package com.etouch.connsPages;

import java.util.List;

import org.apache.commons.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import com.etouch.common.CommonMethods;
import com.etouch.common.CommonPage;
import com.etouch.taf.util.ExcelUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.util.SoftAssertor;
import com.etouch.taf.webui.selenium.WebPage;

public class ProductListingPage extends CommonPage {
	static Log log = LogUtil.getLog(ProductListingPage.class);
	static CommonMethods commonMethods = new CommonMethods();
	SoftAssertor SoftAssertor;

	public void Select_Filter_Price(String[][] Refine_By_Price, String[][] RefineByElements, SoftAssert softAssert)
			throws InterruptedException {
		commonMethods.findElementByXpath(webPage, Refine_By_Price[0][0], softAssert).click();
		commonMethods.findElementByXpath(webPage, RefineByElements[1][1], softAssert).click();
		commonMethods.waitForGivenTime(7, softAssert);
	}

	public boolean Verify_Filter_Applied_By_Price(String[][] Refine_By_Price, SoftAssert softAssert)
			throws InterruptedException {

		List<WebElement> Price_elements = commonMethods.findElementsByXpath(webPage, Refine_By_Price[1][0], softAssert);
		log.info("The total products found matching the selected filter criteria are: " + Price_elements.size());

		boolean filterApplied_Price = false;
		String lowestPrice = Refine_By_Price[2][0], highestPrice = Refine_By_Price[3][0];
		Double lowerPrice = Double.parseDouble(lowestPrice);
		Double upperPrice = Double.parseDouble(highestPrice);
		for (int i = 0; i < Price_elements.size(); i++) {
			String comparison_price = Price_elements.get(i).getText().replace("$", "").replace(",", "");
			Double actualPrice = Double.parseDouble(comparison_price);
			if (actualPrice >= lowerPrice && actualPrice <= upperPrice) {
				filterApplied_Price = true;
			}
		}
		return filterApplied_Price;
	}

	public void Select_Filter_Monthly_Payment(String[][] Refine_By_Monthly_payment, String[][] RefineByElements,
			SoftAssert softAssert) throws InterruptedException {
		commonMethods.findElementByXpath(webPage, Refine_By_Monthly_payment[0][0], softAssert).click();
		commonMethods.findElementByXpath(webPage, RefineByElements[1][1], softAssert).click();
		commonMethods.waitForGivenTime(7, softAssert);
	}

	public boolean ApplyFilter_RefineBy_Monthly_Payment(String[][] Refine_By_Monthly_payment, SoftAssert softAssert)
			throws InterruptedException {

		List<WebElement> Monthly_payment_elements = commonMethods.findElementsByXpath(webPage,
				Refine_By_Monthly_payment[1][0], softAssert);
		log.info("The total products found matching the selected filter criteria are: "
				+ Monthly_payment_elements.size());

		boolean filterApplied_MonthlyPayment = false;
		String starting_range = Refine_By_Monthly_payment[2][0], ending_range = Refine_By_Monthly_payment[3][0];
		Integer lowerRange = Integer.parseInt(starting_range);
		Integer upperRange = Integer.parseInt(ending_range);
		for (int i = 0; i < Monthly_payment_elements.size(); i++) {
			String parse_value = Monthly_payment_elements.get(i).getText().replace("$", "").replace("/", "")
					.replace("mo", "");
			int actualValue = Integer.parseInt(parse_value);
			if (actualValue >= lowerRange && actualValue <= upperRange) {
				filterApplied_MonthlyPayment = true;
			}
		}
		return filterApplied_MonthlyPayment;
	}

	public void Select_Filter_Brand(String[][] Refine_By_Brand, String[][] RefineByElements, SoftAssert softAssert)
			throws InterruptedException {
		commonMethods.findElementByXpath(webPage, Refine_By_Brand[0][0], softAssert).click();
		commonMethods.findElementByXpath(webPage, RefineByElements[1][1], softAssert).click();
		commonMethods.waitForGivenTime(7, softAssert);
	}

	public boolean ApplyFilter_RefineBy_Brand(String[][] Refine_By_Brand, SoftAssert softAssert)
			throws InterruptedException {

		List<WebElement> Brand_name_elements = commonMethods.findElementsByXpath(webPage, Refine_By_Brand[1][0],
				softAssert);
		log.info("The total products found matching the selected filter criteria are: " + Brand_name_elements.size());

		boolean filterApplied_Brand = false;
		for (int i = 0; i < Brand_name_elements.size(); i++) {
			String actualName = Brand_name_elements.get(i).getText();
			if (actualName.contains(Refine_By_Brand[2][0])) {
				filterApplied_Brand = true;
			}
		}
		return filterApplied_Brand;
	}

	public void Select_Multiple_Filters(String[][] Refine_By_Price, String[][] Refine_By_Monthly_payment,
			String[][] Refine_By_Brand, String[][] RefineByElements, SoftAssert softAssert)
			throws InterruptedException {
		commonMethods.findElementByXpath(webPage, Refine_By_Price[0][0], softAssert).click();
		commonMethods.findElementByXpath(webPage, Refine_By_Monthly_payment[0][0], softAssert).click();
		commonMethods.findElementByXpath(webPage, Refine_By_Brand[0][0], softAssert).click();
		commonMethods.findElementByXpath(webPage, RefineByElements[1][1], softAssert).click();
		commonMethods.waitForGivenTime(7, softAssert);
	}

	public boolean ApplyFilter_RefineBy_Multiple_Filters(String[][] Refine_By_Price,
			String[][] Refine_By_Monthly_payment, String[][] Refine_By_Brand, String[][] RefineByElements,
			SoftAssert softAssert) throws InterruptedException {
		boolean are_Mutiple_Filters_Applied = false;

		boolean filterApplied_Price = Verify_Filter_Applied_By_Price(Refine_By_Price, softAssert);
		log.info("Filter is applied correctly for Price--> " + Refine_By_Price[2][0] + "-" + Refine_By_Price[3][0] + ":"
				+ filterApplied_Price);

		boolean filterApplied_MonthlyPayment = ApplyFilter_RefineBy_Monthly_Payment(Refine_By_Monthly_payment,
				softAssert);
		log.info("Filter is applied correctly for Monthly Payment-->" + Refine_By_Monthly_payment[2][0] + "-"
				+ Refine_By_Monthly_payment[3][0] + ":" + filterApplied_MonthlyPayment);

		boolean filterApplied_Brand = ApplyFilter_RefineBy_Brand(Refine_By_Brand, softAssert);
		log.info("Filter is applied correctly for Brand Name --> " + Refine_By_Brand[2][0] + "-" + filterApplied_Brand);

		if ((filterApplied_Price == true) && (filterApplied_MonthlyPayment == true) && (filterApplied_Brand == true)) {
			are_Mutiple_Filters_Applied = true;
		}

		return are_Mutiple_Filters_Applied;
	}

	public void Clear_Filter(String[][] RefineByElements, SoftAssert softAssert) {
		log.info("Clearing the filter selected before next operation");
		commonMethods.clickElementbyLinkText(webPage, RefineByElements[4][1], softAssert);
	}

	public boolean check_if_no_filter_present(String[][] RefineByElements, SoftAssert softAssert)
			throws InterruptedException {
		log.info("Checking if no filter is present before the next operation");
		boolean areAllFiltersCleared = commonMethods.findElementByXpath(webPage, RefineByElements[5][1], softAssert)
				.isDisplayed();
		return areAllFiltersCleared;
	}
}
