package com.etouch.conns.pages.test.mobile;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.etouch.taf.core.datamanager.excel.TestParameters;
import com.etouch.taf.util.ExcelUtil;

public class NewTest {
//	@Test enabled = true, description = "verify Error Msg With Blank Data")
//	public void verifyFieldValidationErrorMessageWithInValidData() {
	//	log.info("testing verifyFieldValidationErrorMessageWithInValidData started------>");
public static void main(String[] args)
{
		String[][] test= ExcelUtil.readExcelData("C:\\dummy\\Conns_POC\\src\\test\\resources\\testdata\\excel\\conns_automation_data.xls", "CreditApp", "verifyFieldValidation");
		
		for(int r=0; r<test.length; r++) {
		       for(int c=0; c<test[0].length; c++)
		           System.out.print(test[r][c] + " ");
		       System.out.println();
		    }
}		
}
