package com.debs.tests.bookflights;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import com.debs.utils.ConfigReader;
import com.debs.utils.DriverFactory;

public class BaseTest {
	
	@BeforeSuite
	public void setUpProperties()
	{
		ConfigReader.initialize();
	}
	
	@AfterMethod
	public void tearDown()
	{
		if(DriverFactory.getDriver()!=null)
		{
			DriverFactory.quitDriver();
		}
	}

}
