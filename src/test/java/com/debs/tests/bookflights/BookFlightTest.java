package com.debs.tests.bookflights;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.debs.dataproviders.DataProviders;
import com.debs.pages.bookflights.CustomerRegistrationPage;
import com.debs.pages.bookflights.FlightSearchPage;
import com.debs.pages.bookflights.FlightsConfirmationPage;
import com.debs.pages.bookflights.RegistrationConfirmationPage;
import com.debs.pages.bookflights.SelectFlightsPage;
import com.debs.records.bookflights.UserData;

public class BookFlightTest extends BaseTest {

	private static final Logger logger = LogManager.getLogger(BookFlightTest.class);
	private String projectName;
	
	@BeforeTest	
	@Parameters("project")
    public void setPageObjects(String project){
        this.projectName = project;
    }

	@Test(dataProvider = "jsonData", dataProviderClass = DataProviders.class)
	public void validUser(UserData data) {
		// System.out.println("Passengers: " + data.passengersCount());
		CustomerRegistrationPage custRegister = new CustomerRegistrationPage();
		custRegister.goTo(projectName);
		Assert.assertTrue(custRegister.verifyElement());
		logger.info("Launched the Registration URL Successfully");
		custRegister.enterUserFirstLastName(data.firstName(), data.lastName());
		custRegister.enterUserCredentials(data.email(), data.password());
		custRegister.enterUserAddress(data.street(), data.city(), data.zip(),data.state());
		
		RegistrationConfirmationPage registerConfirm= custRegister.register();
		Assert.assertTrue(registerConfirm.verifyElement());
		logger.info(registerConfirm.getConfirmationMsg());
		
		FlightSearchPage flightSearch= registerConfirm.goToFlightSearchPage();
		Assert.assertTrue(flightSearch.verifyElement());
		logger.info("Navigated to Flight Search Page");
		flightSearch.selectPassengers(data.passengersCount());
		flightSearch.selectDepartingFrom(data.departFrom());
		flightSearch.selectArriveIn(data.arriveIn());
		flightSearch.selectServiceClass(data.serviceClass());
		
		SelectFlightsPage selectFlight=flightSearch.clickFlightSelect();
		Assert.assertTrue(selectFlight.verifyElement());
		logger.info("Navigated to Select Flights page successfully");
		selectFlight.selectFlights();
		FlightsConfirmationPage flightsConfirm= selectFlight.confirmFlights();
		Assert.assertTrue(flightsConfirm.verifyElement());
		logger.info("Total Price : {} ",flightsConfirm.getPrice());
		Assert.assertEquals(flightsConfirm.getPrice(),data.expectedPrice());
		
		
		
	}

	@Test(dataProvider = "jsonData", dataProviderClass = DataProviders.class)
	public void invalidUser(UserData data) {
		// System.out.println("Passengers: " + data.passengersCount());
		CustomerRegistrationPage custRegister = new CustomerRegistrationPage();
		custRegister.goTo(projectName);
		custRegister.enterUserFirstLastName(data.firstName(), data.lastName());
		custRegister.enterUserCredentials(data.email(), data.password());
		custRegister.enterUserAddress(data.street(), data.city(), data.zip(), data.state());
		custRegister.register();
	}

}
