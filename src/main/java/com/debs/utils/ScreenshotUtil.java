package com.debs.utils;


import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class ScreenshotUtil {
    private static final Logger logger = LogManager.getLogger(ScreenshotUtil.class);

    public static String captureScreenshot(String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotDir = ConfigReader.getProperty(Constants.SCREENSHOT_PATH);
        
        // Create directory if it doesn't exist
        File directory = new File(screenshotDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        
        String screenshotFile = Paths.get( testName + "_" + timestamp + ".png").toString();
        String screenshotPath = Paths.get(screenshotDir,screenshotFile).toString();

        try {
            File srcFile = ((TakesScreenshot) DriverFactory.getDriver())
                          .getScreenshotAs(OutputType.FILE);
            File destFile = new File(screenshotPath);
            FileUtils.copyFile(srcFile, destFile);
            logger.info("Screenshot captured: " + screenshotPath);
            String screenshotPathRelative =Paths.get("screenshots", screenshotFile).toString();
            logger.info("relative screenshot path is --------->{} ",screenshotPathRelative);
            return screenshotPathRelative;
        } catch (IOException e) {
            logger.error("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }
}