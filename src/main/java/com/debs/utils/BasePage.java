package com.debs.utils;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {

	private static final Logger logger = LogManager.getLogger(BasePage.class);
	protected WebDriver driver;
	protected WebDriverWait wait;
	protected JavascriptExecutor js;

	public BasePage() {
		this.driver = DriverFactory.getDriver();
		this.wait = new WebDriverWait(driver,
				Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("explicit.wait"))));
		driver.manage().window().maximize();
		driver.manage().timeouts()
				.implicitlyWait(Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("implicit.wait"))));
		PageFactory.initElements(driver, this);
	}

	protected abstract boolean verifyElement();

	protected void click(WebElement element) {
		wait.until(ExpectedConditions.elementToBeClickable(element));
		element.click();
		logger.info("Clicked on element : {} ", element);
	}

	protected String executeJavascript(String script)
	{
		js=(JavascriptExecutor)driver;
		return (String)js.executeScript(script);
	}
	
	
	protected void clickJS(WebElement element) {
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
		logger.info("Clicked on element: {} ", element);
	}

	protected void sendKeys(WebElement element, String text) {
		wait.until(ExpectedConditions.visibilityOf(element));
		element.sendKeys(text);
		logger.info("Text entered : {} ", text);
	}

	protected void enterPassword(WebElement element, String text) {
		wait.until(ExpectedConditions.visibilityOf(element));
		element.sendKeys(text);
		logger.info("Password entered : XXXX ");
	}

	protected String getText(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
		String text = element.getText();
		logger.info("Text captured : {} ", text);
		return text;

	}

	protected boolean isDisplayed_Enabled(WebElement element) {

		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			logger.info("Element is displayed : {}", element.isDisplayed());
			logger.info("Element is enabled : {}", element.isEnabled());
			return (element.isDisplayed() || element.isEnabled()) ? true : false;
		} catch (Exception e) {
			return false;
		}
	}
	
	protected void selectOption(WebElement element,String value)
	{
		wait.until(ExpectedConditions.elementToBeClickable(element));
		Select select=new Select(element);
		select.selectByValue(value);
		logger.info("Option {} is selected ",value);
	}
	
	protected void selectOptionVisibleText(WebElement element,String value)
	{
		wait.until(ExpectedConditions.elementToBeClickable(element));
		Select select=new Select(element);
		select.selectByVisibleText(value);
		logger.info("Option {} is selected ",value);
	}
	
	protected void selectOption(WebElement element,int index)
	{
		wait.until(ExpectedConditions.elementToBeClickable(element));
		Select select=new Select(element);
		select.selectByIndex(index);
		logger.info("Option {} is selected ",index);
	}
	
	protected void deselectOption(WebElement element,String value)
	{
		wait.until(ExpectedConditions.elementToBeClickable(element));
		Select select=new Select(element);
		select.deselectByValue(value);
		logger.info("Option {} is selected ",value);
	}
	
	protected void deselectOption(WebElement element,int index)
	{
		wait.until(ExpectedConditions.elementToBeClickable(element));
		Select select=new Select(element);
		select.deselectByIndex(index);
		logger.info("Option {} is selected ",index);
	}
}
