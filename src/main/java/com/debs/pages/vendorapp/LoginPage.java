package com.debs.pages.vendorapp;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.debs.utils.BasePage;
import com.debs.utils.ConfigReader;

public class LoginPage extends BasePage {
	
	@FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "login")
    private WebElement loginButton;
	
	public LoginPage()
	{
		super();
	}

	@Override
	public boolean verifyElement() {
		// TODO Auto-generated method stub
		return isDisplayed_Enabled(loginButton);
	}
	
	public void goTo(String project)
	{
		driver.get(ConfigReader.getUrl(project));
	}
	
	 public DashboardPage login(String username, String password){
	        sendKeys(usernameInput,username);
	        enterPassword(passwordInput, password);
	        click(loginButton);
	        return new DashboardPage();
	    }

}
