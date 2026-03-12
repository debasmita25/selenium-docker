package com.debs.tests.vendorapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.debs.dataproviders.DataProviders;
import com.debs.pages.vendorapp.DashboardPage;
import com.debs.pages.vendorapp.LoginPage;
import com.debs.records.vendorapp.VendorPortalTestData;
import com.debs.utils.RetryAnalyzer;

public class VendorAppTest extends BaseTest {

	private static final Logger logger = LogManager.getLogger(VendorAppTest.class);
	private String projectName;

	@BeforeTest
	@Parameters("project")
	public void setPageObjects(String project) {
		this.projectName = project;
	}

	@Test(dataProvider = "jsonData", dataProviderClass = DataProviders.class,retryAnalyzer = RetryAnalyzer.class)
	public void vendorData(VendorPortalTestData data) {

		LoginPage lp = new LoginPage();
		lp.goTo(projectName);
		Assert.assertTrue(lp.verifyElement());
		// System.out.println(data.username());
		logger.info("User is at Sign-in page");
		DashboardPage dashboardPage = lp.login(data.username(),data.password());
		Assert.assertTrue(dashboardPage.verifyElement());
		logger.info("User signed into the application successfully");
		// finance metrics
		Assert.assertEquals(dashboardPage.getMonthlyEarning(), data.monthlyEarning());
		Assert.assertEquals(dashboardPage.getAnnualEarning(), data.annualEarning());
		Assert.assertEquals(dashboardPage.getProfitMargin(), data.profitMargin());
		Assert.assertEquals(dashboardPage.getAvailableInventory(), data.availableInventory());
		logger.info("Finance metrics are validated successfully");
		// order history search
		dashboardPage.searchOrderHistoryBy(data.searchKeyword());
//		System.out.println("dashboardPage.getSearchResultsCount()--------------------------"+dashboardPage.getSearchResultsCount());
		Assert.assertEquals(dashboardPage.getSearchResultsCount(), data.searchResultsCount());
		logger.info("Order history search count is validated successfully");
		dashboardPage.logout();
        Assert.assertTrue(lp.verifyElement());
        logger.info("User has logged out successfully");

	}

}
