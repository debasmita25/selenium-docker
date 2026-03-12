package com.debs.pages.bookflights;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.debs.utils.BasePage;

public class SelectFlightsPage extends BasePage {

	@FindBy(name = "departure-flight")
	private List<WebElement> departureFlightsOptions;

	@FindBy(name = "arrival-flight")
	private List<WebElement> arrivalFlightsOptions;

	@FindBy(id = "confirm-flights")
	private WebElement confirmFlightsButton;

	public SelectFlightsPage() {
		super();
	}

	@Override
	public boolean verifyElement() {
		// TODO Auto-generated method stub
		return isDisplayed_Enabled(confirmFlightsButton);
	}

	public void selectFlights() {
		int random = ThreadLocalRandom.current().nextInt(0, departureFlightsOptions.size());
		click(departureFlightsOptions.get(random));
		click(arrivalFlightsOptions.get(random));
	}

	public FlightsConfirmationPage confirmFlights() {
		click(confirmFlightsButton);
		return new FlightsConfirmationPage();
	}
}
