package com.etouch.conns.listener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.etouch.taf.util.LogUtil;

public class SoftAssertor {

	private static Map<ITestResult, List<String>> verificationFailuresMap = new HashMap<ITestResult, List<String>>();

	/** The log. */
	static Log log = LogUtil.getLog(SoftAssertor.class);

	public static Boolean assertTrue(boolean condition, String errMsg) {

		try {
			Assert.assertTrue(condition);
			return true;
		} catch (Throwable e) {
			log.error("SoftAssertor.assertTrue failed !!! Error Message : "+e.getMessage());
			addVerificationFailure(errMsg + " Exception msg: " + e.getMessage());
			return false;
		}
	}

	public static Boolean assertFalse(boolean condition, String errMsg) {

		try {
			Assert.assertFalse(condition);
			return true;
		}
		catch (Throwable e) {
			log.error("SoftAssertor.assertFalse failed !!! Error Message : "+e.getMessage());
			addVerificationFailure(errMsg + " Exception msg: " + e.getMessage());
			return false;
		}
	}

	public static Boolean assertEquals(Object actual, Object expected,
			String errMsg) {

		try {
			Assert.assertEquals(actual, expected);
			return true;
		} catch (Throwable e) {
			log.error("SoftAssertor.assertEquals failed !!! Error Message : "+e.getMessage());
			addVerificationFailure(errMsg + " Exception msg: " + e.getMessage());
			return false;
		}
	}

	public static Boolean assertTrue(boolean condition) {

		try {
			Assert.assertTrue(condition);
			return true;
		} catch (Throwable e) {
			log.error("SoftAssertor.assertTrue failed !!! Error Message : "+e.getMessage());
			addVerificationFailure(" Exception msg: " + e.getMessage());
			return false;
		}
	}

	public static Boolean assertFalse(boolean condition) {

		try {
			Assert.assertFalse(condition);
			return true;
		} catch (Throwable e) {
			log.error("SoftAssertor.assertFalse failed !!! Error Message : "+e.getMessage());
			addVerificationFailure(" Exception msg: " + e.getMessage());
			return false;
		}
	}

	public static Boolean assertEquals(Object actual, Object expected) {
		try {
			Assert.assertEquals(actual, expected);
			return true;
		} catch (Throwable e) {
			log.error("SoftAssertor.assertEquals failed !!! Error Message : "+e.getMessage());
			addVerificationFailure(" Exception msg: " + e.getMessage());
			return false;
		}
	}

	public static Boolean assertNotNull(Object actual, String errMsg) {

		try {
			Assert.assertNotNull(actual);
			return true;
		} catch (Throwable e) {
			log.error("SoftAssertor.assertNotNull failed !!! Error Message : "+e.getMessage());
			addVerificationFailure(errMsg + " Exception msg: " + e.getMessage());
			return false;
		}
	}

	public static Boolean fail() {
		try {
			Assert.fail();
			return true;
		} catch (Throwable e) {
			log.error("SoftAssertor.fail failed !!! Error Message : "+e.getMessage());
			addVerificationFailure(" Exception msg: " + e.getMessage());
			return false;
		}
	}

	public static Boolean fail( String errMsg) {
		try {
			Assert.fail(errMsg);
			return true;
		} catch (Throwable e) {
			log.error("SoftAssertor.fail failed !!! Error Message : "+e.getMessage());
			addVerificationFailure(errMsg + " Exception msg: " + e.getMessage());
			return false;
		}
	}


	public static Boolean fail(String msg, Throwable ex ) {
		try {
			ex.printStackTrace();
			Assert.fail(msg ,ex );
			return true;
		} catch (Throwable e) {
			log.error("SoftAssertor.fail failed !!! Error Message : "+e.getMessage());
			addVerificationFailure(" Exception msg: " + e.getMessage());
			return false;
		}
	}


	public static List<String> getVerificationFailures() {
		List<String> verificationFailures = verificationFailuresMap
				.get(Reporter.getCurrentTestResult());
		return verificationFailures == null ? new ArrayList<String>()
				: verificationFailures;
	}

	public static void addVerificationFailure(String e) {
		List<String> verificationFailures = getVerificationFailures();
		verificationFailures.add(e);
		verificationFailuresMap.put(Reporter.getCurrentTestResult(),
				verificationFailures);
	}

	/**
	 * Display errors.
	 * This method logs all the errors and fails the test method with the errors captured.
	 */
	public static void displayErrors()
	{
		String errorsForTest= readErrorsForTest();	
		refreshVerificationFailures();
		if(errorsForTest!=null && errorsForTest.length()>0)
			Assert.fail(errorsForTest);	
	}

	/**
	 * Display errors for test.
	 *
	 * @param errors the errors
	 */
	public void displayErrorsForTest(String errors)
	{
		if(errors!=null && errors.length()>0)
			Assert.fail(errors);	
	}

	/**
	 * Read errors for test.
	 *
	 * @return the string
	 */
	public static String readErrorsForTest()
	{
		String errMsg = "";
		if(verificationFailuresMap!=null && verificationFailuresMap.size()>0 )
		{
			Set keys = verificationFailuresMap.keySet();
			java.util.Iterator<ITestResult> itr = keys.iterator();

			while(itr.hasNext())
			{
				List<String> errorList = verificationFailuresMap.get(itr.next());
				if(errorList!=null && errorList.size()>0)
				{
					for(int index=0; index<errorList.size(); index++)
					{
						log.error(errorList.get(index));	
						errMsg = errMsg +  errorList.get(index) + "\n";
					}					
				}
			}	
		}	
		return errMsg;
	}

	/**
	 * Refresh verification failures.
	 */
	private static void refreshVerificationFailures() {
		//List<String> verificationFailures = new ArrayList<String>();
		verificationFailuresMap = new HashMap<ITestResult,List<String>>();		
		//verificationFailuresMap.put(Reporter.getCurrentTestResult(), verificationFailures);
	}

}