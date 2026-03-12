package com.debs.pages.bookflights;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.debs.utils.BasePage;

public class FlightSearchPage extends BasePage {
	
	@FindBy(css="#flight-search-section .text-center.text-secondary.mb-0")
	private WebElement title;
	
	@FindBy(id="passengers")
	private WebElement selectPassengers;
	
	@FindBy(id="depart-from")
	private WebElement selectDepartFrom;
	
	@FindBy(id="arrive-in")
	private WebElement selectArriveIn;
	
	@FindBy(css="div.card.mt-3 .form-check-input")
	private List<WebElement> serviceClassRadioBtn;
	
	@FindBy(css="div.card.mt-3 .form-check-label")
	private List<WebElement> serviceClassRadioLabel;
	
	@FindBy(id="search-flights")
	private WebElement searchFlights;
	
	public FlightSearchPage()
	{
		super();
	}

	@Override
	public boolean verifyElement() {
		// TODO Auto-generated method stub
		return isDisplayed_Enabled(title);
	}
	
	public void selectPassengers(String option)
	{
		selectOption(selectPassengers, option);
	}

	public void selectDepartingFrom(String option)
	{
		selectOption(selectDepartFrom, option);
	}
	
	public void selectArriveIn(String option)
	{
		selectOption(selectArriveIn, option);
	}
	
	public void selectServiceClass(String option)
	{
		for(int i=0;i<serviceClassRadioLabel.size();i++)
		{
			if(serviceClassRadioLabel.get(i).getText().equalsIgnoreCase(option))
			{
				if(!serviceClassRadioBtn.get(i).isSelected())
				click(serviceClassRadioBtn.get(i));
			}
		}
	}
	
	public SelectFlightsPage clickFlightSelect()
	{
		clickJS(searchFlights);
		return new SelectFlightsPage();
	}
}
