package com.debs.pages.bookflights;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.debs.utils.BasePage;
import com.debs.utils.ConfigReader;

public class CustomerRegistrationPage extends BasePage {
	
	public CustomerRegistrationPage()
	{
		super();
	}
	
	@FindBy(id="firstName")
	private WebElement firstName;
	
	@FindBy(id="lastName")
	private WebElement lastName;
	
	@FindBy(id="email")
	private WebElement email;
	
	@FindBy(id="password")
	private WebElement password;
	
	@FindBy(name="street")
	private WebElement streetName;
	
	@FindBy(name="city")
	private WebElement city;
	
	@FindBy(id="inputState")
	private WebElement stateSelect;
	
	@FindBy(name="zip")
	private WebElement zip;
	
	@FindBy(id="register-btn")
	private WebElement registerButton;
	

	@Override
	public boolean verifyElement() {
		// TODO Auto-generated method stub
		return isDisplayed_Enabled(firstName);
		
	}
	
	public void goTo(String project)
	{
		driver.get(ConfigReader.getUrl(project));
	}
	
	public void enterUserFirstLastName(String fname,String lname)
	
	{ 
		sendKeys(firstName, fname);
		sendKeys(lastName, lname); 
	}
	
	public void enterUserCredentials(String email,String password)
	{
		enterPassword(this.email, email);
		enterPassword(this.password, password);
	}
	
	public void enterUserAddress(String street, String city, String zip,String state){
		
		sendKeys(streetName, street);
		sendKeys(this.city,city);
		sendKeys(this.zip,zip);
		selectOption(stateSelect, state);
	}
	
    public RegistrationConfirmationPage register()
    {
    	click(registerButton);
    	return new RegistrationConfirmationPage();
    }
}
