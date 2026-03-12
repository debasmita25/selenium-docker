package com.debs.utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private static final Logger logger = LogManager.getLogger(RetryAnalyzer.class);
    private int retryCount = 0;
    private int maxRetryCount = Integer.parseInt(ConfigReader.getProperty(Constants.RETRY_COUNT));

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            logger.info("Retrying test: " + result.getName() + 
                       " for the " + retryCount + " time");
            return true;
        }
        return false;
    }
}
