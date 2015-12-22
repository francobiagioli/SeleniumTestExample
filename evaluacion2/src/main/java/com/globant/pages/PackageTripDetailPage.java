package com.globant.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PackageTripDetailPage extends BasePage {

	@FindBy(id="departure-date-0")
	private WebElement departureDate;

	private String departureAirport = "//div[@class='flightDetail']//span[@id='departureairport']"; 

	@FindBy(id="departureairport")
	private WebElement departureAirportList;

	private String arrivalAirport = "//div[@class='flightDetail']//span[@id='arrivalairport']";

	@FindBy(id="arrivalairport")
	private WebElement arrivalAirportList;

	@FindBy(className="RBtn")
	private WebElement buttonContinueBooking;

	@FindBy(className="pageHeading")
	private WebElement pageTitle;

	public PackageTripDetailPage(WebDriver driver) {
		super(driver);
		driver.get(driver.getCurrentUrl());
	}



	public WebElement getDepartureAirportGo(){
		return departureAirportList.findElements(By.xpath(departureAirport)).get(0);
	}

	public WebElement getDepartureAirportBack(){
		return departureAirportList.findElements(By.xpath(departureAirport)).get(1);
	}

	public WebElement getArrivalAirportGo(){
		return arrivalAirportList.findElements(By.xpath(arrivalAirport)).get(0);
	}

	public WebElement getArrivalAirportBack(){
		return arrivalAirportList.findElements(By.xpath(arrivalAirport)).get(1);
	}

	public WebElement getDepartureDate(){
		return departureDate;
	}

	public PackageYourTripPage ContinueBooking(){
		sleep();
		buttonClick(buttonContinueBooking);
		return new PackageYourTripPage(driver);
	}

	public WebElement getPageTitle() {
		return pageTitle;
	}


}
