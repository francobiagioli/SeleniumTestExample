package com.globant.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class YourTripPage extends BasePage {

	@FindBy(className="faceoff-module-title")
	private WebElement whoTravelsTitle;

	@FindBy(className="product_summary_wrapper")
	private WebElement infoFlight;

	@FindBy(className="leg-departure-arrival-info")
	private WebElement departureArrivalInfo;

	private String depart="//div[@class='leg-departure-arrival-info']//span[@class='departure-airport-codes']";
	private String arrive="//div[@class='leg-departure-arrival-info']//span[@class='arrival-airport-codes']";

	public YourTripPage(WebDriver driver) {
		super(driver);
		driver.get(driver.getCurrentUrl());
	}

	public WebElement whoTravelsTitle(){
		return whoTravelsTitle;
	}

	public WebElement getDepartureAirportGo() {
		return departureArrivalInfo.findElements(By.xpath(depart)).get(0);
	}

	public WebElement getArrivalAirportGo() {
		return departureArrivalInfo.findElements(By.xpath(arrive)).get(0);
	}

	public WebElement getDepartureAirportBack() {
		return departureArrivalInfo.findElements(By.xpath(depart)).get(1);
	}


}
