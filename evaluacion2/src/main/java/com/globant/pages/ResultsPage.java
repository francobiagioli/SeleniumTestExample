package com.globant.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class ResultsPage extends BasePage{

	private String nameComboSort="sort";
	private String xPathListFligthsDuration = "//ul[@id='flightModuleList']//div[@class='primary duration-emphasis']";
	private String xPathListFligthsButtons ="//ul[@id='flightModuleList']//div[@class='price-button-wrapper']/button";

	@FindBy(id="departureAirport")
	private WebElement fieldDepartureAirport;

	@FindBy(id="returnAirport")
	private WebElement fieldReturnAirport;

	@FindBy(id="departDate")
	private WebElement fieldDepartDate;

	@FindBy(id="returnDate")
	private WebElement fieldReturnDate;

	@FindBy(id="flight-type-round-trip")
	private WebElement checkRoundTrip;

	@FindBy(id="flightModuleList")
	private WebElement flightList;

	@FindBy(id="forcedChoiceNoThanks")
	private WebElement noThanks;

	public ResultsPage(WebDriver driver) {
		super(driver);
		driver.get(driver.getCurrentUrl());
	}

	public WebElement getFieldReturnAirpot(){
		return fieldReturnAirport;
	}
	public WebElement getFieldDepartureAirpot(){
		return fieldDepartureAirport;
	}
	public WebElement getFieldDepartDate(){
		return fieldDepartDate;
	}
	public WebElement getFieldReturnDate(){
		return fieldReturnDate;
	}
	public WebElement getRadioRoundTrip(){
		return checkRoundTrip;
	}

	public void sortBy(String sort){
		sleep();
		Select selectBox = new Select(driver.findElement(By.name(nameComboSort)));
		selectBox.selectByValue(sort);

	}
	public List<WebElement> getFligthList()	{
		sleep();
		return  flightList.findElements(By.xpath(xPathListFligthsDuration));
	}

	public TripDetailPage selectFligths(Integer firstIndexFligth,Integer secondIndexFligth){
		sleep();
		return clickFligth(firstIndexFligth)
				.clickFligth(secondIndexFligth)
				.clickNoThanks();						
	}

	public ResultsPage clickFligth(Integer index){
		sleep();
		flightList.findElements(By.xpath(xPathListFligthsButtons)).get(index-1).click();
		return this;
	}

	public TripDetailPage clickNoThanks(){
		sleep();
		noThanks.click();
		return new TripDetailPage(driver);
	}
}
