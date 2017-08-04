/*
 * 
 */
package com.etouch.taf.core.driver.web;

import java.io.File;
import org.apache.commons.logging.Log;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.etouch.taf.core.TestBed;
import com.etouch.taf.core.driver.DriverBuilder;
import com.etouch.taf.core.driver.DriverConstantUtil;
import com.etouch.taf.core.driver.mobile.IOSDriver;
import com.etouch.taf.core.exception.DriverException;
import com.etouch.taf.util.ConfigUtil;
import com.etouch.taf.util.LogUtil;
import com.etouch.taf.webui.selenium.SeleniumDriver;

/**
 * The Class ChromeDriver.
 */
public class EdgeDriver extends DriverBuilder{

	/** The log. */
	private static Log log = LogUtil.getLog(EdgeDriver.class);

	/**
	 * Instantiates a new edge driver.
	 * @param testBed the test bed
	 * @throws DriverException the driver exception
	 */
	public EdgeDriver(TestBed testBed) throws DriverException {
		super(testBed);
	}
	
	/**
	 * Creates driver for Chrome.
	 * @throws DriverException the driver exception
	 */
	@Override
	public void buildDriver() throws DriverException
	{
		if(ConfigUtil.isLocalEnv(testBed.getTestBedName()))
		{
			// if it is a Selenium tool, then create selenium ChromeDriver
			if(ConfigUtil.isSelenium()){
				File edgeDriverFile=getEdgeDriverFile();
				log.debug("Found Driver file");
				driver =SeleniumDriver.buildEdgeDriver(edgeDriverFile);
			}
		} else if(ConfigUtil.isRemoteEnv(testBed.getTestBedName()))
		{
			if(ConfigUtil.isSelenium()){
				capabilities = DesiredCapabilities.edge();
				
			// Added below code to open specified browser version on browserstack	
				capabilities.setCapability("browser_version", testBed.getBrowser().getVersion());
				capabilities.setCapability("Platform_Name", testBed.getPlatform().getName());
				capabilities.setCapability("Platform_version", testBed.getPlatform().getVersion());
			//	updated code ended
				driver = SeleniumDriver.buildRemoteDriver(capabilities);
			}
		}
		else if(ConfigUtil.isBrowserStackEnv(testBed.getTestBedName()))
		{
			capabilities = DesiredCapabilities.edge();
			buildBrowserstackCapabilities();
		}
	}
	
	/**
	 * This method returns Driver file for Edge Driver
	 * If is in mentioned in config.yml then set that as system property
	 * otherwise, use the defaults Edge driver from library based on the given platform
	 * @return the edge driver file
	 * @throws DriverException 
	 */
	public File getEdgeDriverFile() throws DriverException{
		
		File file;
		if(testBed.getBrowser().getDriverLocation()!=null){			
			file =new File(testBed.getBrowser().getDriverLocation());
		}else{
			if(ConfigUtil.isWindows(testBed))
			{
				file = new File(DriverConstantUtil.EDGE_DRIVER_FILE);
			}
			else
			{
				throw new DriverException(" Not found a Edge in Windows OS");
			}
		}
		return file;
	}

	/* (non-Javadoc)
	 * @see com.etouch.taf.core.driver.DriverBuilder#getDriver()
	 */
	@Override
	public Object getDriver() throws DriverException {
		return driver;
	}
}