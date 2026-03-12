package com.debs.pages.bookflights;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.debs.utils.BasePage;

public class RegistrationConfirmationPage extends BasePage {
	
	
	@FindBy(css="#registration-confirmation-section>div.container>h2.text-center.text-secondary.mb-0")
	private WebElement title;
	
	@FindBy(css="div#registration-confirmation-section p.mt-3")
	private WebElement message;
	
	@FindBy(id="go-to-flights-search")
	private WebElement goToFlightSearch;
	

	@Override
	public boolean verifyElement() {
		// TODO Auto-generated method stub
		return isDisplayed_Enabled(title);
	}
	
	public RegistrationConfirmationPage()
	{
		super();
	}
	
	public String getConfirmationMsg()
	{
		return getText(message);
	}
	
	public FlightSearchPage goToFlightSearchPage()
	{
		click(goToFlightSearch);
		return new FlightSearchPage();
	}

}
