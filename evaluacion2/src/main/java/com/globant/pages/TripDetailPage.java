package com.globant.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TripDetailPage extends BasePage{

	@FindBy(id="departure-date-0")
	private WebElement departureDate;

	@FindBy(id="departure-airport-automation-label-0")
	private WebElement departureAirportGo;

	@FindBy(id="arrival-airportcode-automation-label-0")
	private WebElement arrivalAirportGo;

	@FindBy(id="departure-airport-automation-label-1")
	private WebElement departureAirportBack;

	@FindBy(id="arrival-airportcode-automation-label-1")
	private WebElement arrivalAirportBack;

	@FindBy(xpath="//span[@id='FlightUDPBookNowButton1']/button")
	private WebElement buttonContinueBooking;

	public TripDetailPage(WebDriver driver) {
		super(driver);
		sleep();
		for(String winHandle : driver.getWindowHandles()){
			driver.switchTo().window(winHandle);
		}
		driver.get(driver.getCurrentUrl());
	}

	public WebElement getDepartureAirportGo(){
		return departureAirportGo;
	}

	public WebElement getDepartureAirportBack(){
		return departureAirportBack	;	
	}

	public WebElement getArrivalAirportGo(){
		return arrivalAirportGo;
	}

	public WebElement getArrivalAirportBack(){
		return arrivalAirportBack;
	}

	public WebElement getDepartureDate(){
		return departureDate;
	}

	public YourTripPage clickButtonContinueBooking(){
		sleep();
		buttonContinueBooking.click();
		return new YourTripPage(driver);
	}

}
