package com.globant.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;



public class CruiseResultsPage extends BasePage{

	private String cruise="//div[@id='search-results']//span[@class='cruise-topline top-card-date-link']";

	private String itineraryDay="//table[@class='itinerary-table']//tbody//tr";

	@FindBy(className="itinerary-table")
	private WebElement listItinerary;

	@FindBy(id="update-button-length")
	private WebElement buttonUpdateLength;

	@FindBy(id="menu-length")
	private WebElement menuLength;

	@FindBy(xpath="//div[@id='menu-selection-destination']//div")
	private WebElement labelDestination; 

	@FindBy(xpath="//div[@id='menu-selection-length']//div")
	private WebElement labelLength; 

	@FindBy(xpath="//div[@id='menu-selection-departure-month']//div")
	private WebElement labelDeparture; 

	@FindBy(xpath="//div[@id='menu-content-length']//label[@for='10|14-length']")
	private WebElement button1014Length;

	@FindBy(id="search-results")
	private WebElement listCruises;

	public CruiseResultsPage(WebDriver driver) {
		super(driver);
		driver.get(driver.getCurrentUrl());
	}

	public WebElement getLabelDestination(){
		return labelDestination;
	}

	public WebElement getLabelDeparture(){
		return labelDeparture;
	}

	public CruiseResultsPage clickLength() {
		buttonClick(menuLength);
		return this;	
	}
	public CruiseResultsPage selectLength(){
		buttonClick(button1014Length);
		return this;
	}

	public CruiseResultsPage updateLengthClick(){
		buttonClick(buttonUpdateLength);
		return this;
	}

	public CruiseResultsPage updateLength(){
		clickLength()
		.selectLength()
		.updateLengthClick();
		return this;
	}

	public WebElement getLabelLength(){
		return labelLength;
	}

	public void selectCruise(){
		listCruises.findElements(By.xpath(cruise)).get(0).click();
	}

	public List<WebElement> listItinerary() {
		return listItinerary.findElements(By.xpath(itineraryDay));
	}


}
