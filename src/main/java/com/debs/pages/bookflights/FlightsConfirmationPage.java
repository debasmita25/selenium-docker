package com.debs.pages.bookflights;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.debs.utils.BasePage;

public class FlightsConfirmationPage extends BasePage  {
	

	private String jsScriptForConfirmationNum ="return [...document.querySelectorAll('*')].find(e1=>e1.textContent.trim()==='Flight Confirmation #').nextElementSibling.innerText;";
	private String jsScriptForTax ="return [...document.querySelectorAll('*')].find(e1=>e1.textContent.trim()==='Tax').nextElementSibling.innerText;";
	private String jsScriptForTotalPrice ="return [...document.querySelectorAll('*')].find(e1=>e1.textContent.trim()==='Total Price').nextElementSibling.innerText;";
	
	
	@FindBy(xpath="//h2[text()='Flights Confirmation']/following-sibling::div[@class='row justify-content-center']//b")
	private WebElement bookingMsg;
	
	public FlightsConfirmationPage()
	{
		super();
	}

	@Override
	public boolean verifyElement() {
		// TODO Auto-generated method stub
		return isDisplayed_Enabled(bookingMsg);
	}
	
	public String getConfirmationNum()
	{
		return executeJavascript(jsScriptForConfirmationNum);
	}
	
	public String getTax()
	{
		return executeJavascript(jsScriptForTax);
	}
	
	public String getPrice()
	{
		return executeJavascript(jsScriptForTotalPrice);
	}

}
