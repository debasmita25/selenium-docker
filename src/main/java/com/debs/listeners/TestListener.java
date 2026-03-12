
package com.debs.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.debs.utils.ExtentManager;
import com.debs.utils.ScreenshotUtil;

public class TestListener implements ITestListener {

	private static final Logger logger = LogManager.getLogger(TestListener.class);

//    @Override
//    public void onTestFailure(ITestResult result) {
//        TakesScreenshot driver = (TakesScreenshot) DriverFactory.getDriver();
//        String screenshot = driver.getScreenshotAs(OutputType.BASE64);
//        String htmlImageFormat = "<img width=700px src='data:image/png;base64,%s' />";
//        String htmlImage = String.format(htmlImageFormat, screenshot);
//        
//        Reporter.log(htmlImage);
//        
//    }

	@Override
	public void onStart(ITestContext context) {
		logger.info("Test Suite Started: " + context.getName());
		ExtentManager.getInstance();
	}

	@Override
	public void onTestStart(ITestResult result) {

		logger.info("Test Started: " + result.getMethod().getMethodName());
		ExtentTest test = ExtentManager.getInstance().createTest(result.getMethod().getMethodName());
		ExtentManager.setTest(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		logger.info("Test Passed: " + result.getMethod().getMethodName());
		ExtentManager.getTest().log(Status.PASS, MarkupHelper.createLabel("Test Case PASSED", ExtentColor.GREEN));
		
		ExtentManager.removeTest();
	}

	@Override
	public void onTestFailure(ITestResult result) {
		logger.error("Test Failed: " + result.getMethod().getMethodName());

		ExtentTest test = ExtentManager.getTest();
		
		if (test == null) {
	        test = ExtentManager.getInstance()
	                .createTest(result.getMethod().getMethodName());
	        ExtentManager.setTest(test);
	    }
		
		test.log(Status.FAIL, MarkupHelper.createLabel("Test Case FAILED", ExtentColor.RED));
		test.fail(result.getThrowable());

		String screenshotPath = ScreenshotUtil.captureScreenshot(result.getMethod().getMethodName());
		if (screenshotPath != null) {
			try {
//				test.addScreenCaptureFromPath(screenshotPath);
				test.fail("Test Failed: " + result.getThrowable(),
						MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
			} catch (Exception e) {
				logger.error("Failed to attach screenshot: " + e.getMessage());
			}
		}
		
		ExtentManager.removeTest();

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		logger.warn("Test Skipped: " + result.getMethod().getMethodName());
		ExtentManager.getTest().log(Status.SKIP, MarkupHelper.createLabel("Test Case SKIPPED", ExtentColor.ORANGE));
		
		ExtentManager.removeTest();
	}

	@Override
	public void onFinish(ITestContext context) {
		logger.info("Test Suite Finished: " + context.getName());
		ExtentManager.getInstance().flush();
	}

}
