package com.debs.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverFactory {

	private static final Logger logger = LogManager.getLogger(DriverFactory.class);
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	public static WebDriver getDriver() {
		if (driver.get() == null) {
			driver.set(createDriver());
		}

		return driver.get();

	}

	private static WebDriver createDriver() {
		WebDriver driver = null;
		String browser = ConfigReader.getProperty(Constants.BROWSER).toLowerCase();
		boolean isGrid = Boolean.parseBoolean(ConfigReader.getProperty(Constants.GRID_ENABLED));
		boolean isRemote = Boolean.parseBoolean(ConfigReader.getProperty(Constants.REMOTE_ENABLED));
		logger.info("Creating driver for browser: " + browser);
		logger.info("Remote execution: " + isRemote);
		logger.info("Docker execution: " + isGrid);
		
		if (isGrid || isRemote)
			try {
				driver = createRemoteDriver(browser);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else
			driver = createLocalDriver(browser);
		
	
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(ConfigReader.getProperty(Constants.IMPLICIT_WAIT))));
		driver.manage().timeouts().pageLoadTimeout(
				Duration.ofSeconds(Integer.parseInt(ConfigReader.getProperty(Constants.PAGE_LOAD_TIMEOUT))));

		logger.info("Driver created successfully");

		return driver;

	}

	private static WebDriver createRemoteDriver(String browser) throws MalformedURLException {
		
		WebDriver webDriver;
		
		String urlFormat = ConfigReader.getProperty(Constants.GRID_URL_FORMAT);
        String hubHost = ConfigReader.getProperty(Constants.GRID_HUB_HOST);
        String url = String.format(urlFormat, hubHost);
        logger.info("grid url: {}", url);

		switch (browser) {
		case "firefox":
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			if (Boolean.parseBoolean(ConfigReader.getProperty(Constants.HEADLESS_ENABLED))) {
				firefoxOptions.addArguments("--headless");
			}
			webDriver = new RemoteWebDriver(new URL(url), firefoxOptions);
			break;

		case "edge":
			EdgeOptions edgeOptions = new EdgeOptions();
			if (Boolean.parseBoolean(ConfigReader.getProperty(Constants.HEADLESS_ENABLED))) {
				edgeOptions.addArguments("--headless");
			}
			webDriver = new RemoteWebDriver(new URL(url), edgeOptions);
			break;

		case "chrome":
		default:
			ChromeOptions chromeOptions = new ChromeOptions();
			if (Boolean.parseBoolean(ConfigReader.getProperty(Constants.HEADLESS_ENABLED))) {
				chromeOptions.addArguments("--headless");
			}
			//chromeOptions.addArguments("--headless=new");
			chromeOptions.addArguments("--no-sandbox");
			chromeOptions.addArguments("--disable-dev-shm-usage");
			chromeOptions.addArguments("--disable-gpu");

			webDriver = new RemoteWebDriver(new URL(url), chromeOptions);
			break;
		}

		return webDriver;

	}

	private static WebDriver createLocalDriver(String browser) {
		WebDriver driver;
		switch (browser) {
		case "firefox":
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			if (Boolean.parseBoolean(ConfigReader.getProperty(Constants.HEADLESS_ENABLED))) {
				firefoxOptions.addArguments("--headless");
			}
			driver = new FirefoxDriver(firefoxOptions);
			break;

		case "edge":
			EdgeOptions edgeOptions = new EdgeOptions();
			if (Boolean.parseBoolean(ConfigReader.getProperty(Constants.HEADLESS_ENABLED))) {
				edgeOptions.addArguments("--headless");
			}
			driver = new EdgeDriver(edgeOptions);
			break;

		case "chrome":
		default:
			ChromeOptions chromeOptions = new ChromeOptions();
			// Disable password manager
			Map<String, Object> prefs = new HashMap<>();

			// 🔐 Disable password manager
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);

			// 🚫 Disable breach detection
			prefs.put("profile.password_manager_leak_detection", false);

			// 🚫 Disable Safe Browsing password protection
			prefs.put("safebrowsing.enabled", false);
			prefs.put("safebrowsing.disable_download_protection", true);
			chromeOptions.addArguments("--disable-notifications");
			chromeOptions.addArguments("--disable-infobars");
			chromeOptions.addArguments("--disable-save-password-bubble");
			chromeOptions.addArguments("--disable-extensions");
			// Disable Chrome notifications
			prefs.put("profile.default_content_setting_values.notifications", 2);

			chromeOptions.setExperimentalOption("prefs", prefs);

			// Chrome security services off
			chromeOptions.addArguments("--disable-features=PasswordLeakDetection");
			chromeOptions.addArguments("--disable-features=AutofillServerCommunication");
			chromeOptions.addArguments("--disable-features=SafeBrowsingEnhancedProtection");
			chromeOptions.addArguments("--disable-features=PasswordManagerOnboarding");

			// UI suppressions
			chromeOptions.addArguments("--disable-notifications");
			chromeOptions.addArguments("--disable-infobars");
			chromeOptions.addArguments("--disable-save-password-bubble");
			chromeOptions.addArguments("--disable-extensions");
			if (Boolean.parseBoolean(ConfigReader.getProperty(Constants.HEADLESS_ENABLED))) {
				chromeOptions.addArguments("--headless");
			}
			driver = new ChromeDriver(chromeOptions);
			break;
		}

		logger.info("Driver initialized successfully");
		return driver;

	}

	public static void quitDriver() {
		if (driver.get() != null) {
			driver.get().quit();
			driver.remove();
			logger.info("Driver quit successfully");
		}
	}
}