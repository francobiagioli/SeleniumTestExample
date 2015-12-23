package com.globant.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HotelPage extends BasePage {

	private String hotel ="//div[@class='bColumn']//li[@class='hotelName fakeLink']//strong";

	@FindBy(id="inpHotelNameMirror")
	private WebElement fieldSearchHotel;

	@FindBy(xpath="//div[@class='cssCell']//button")
	private WebElement buttonSearch;

	@FindBy(className="gotIt")
	private WebElement buttonGotIt;

	@FindBy(id="resultsContainer")
	private WebElement listHotelsResult;

	public HotelPage(WebDriver driver) {
		super(driver);
		driver.get(driver.getCurrentUrl());
	}

	public HotelPage fillFieldSearchHotel(String search){
		fieldSearchHotel.sendKeys(search);
		return this;
	}
	public HotelPage ButtonGotItClick(){
		buttonGotIt.click();
		return this;
	}		
	public HotelPage ButtonGoClick(){
		buttonSearch.click();
		return this;
	}

	public WebElement searchedHotel(){
		waitForJQueryProcessing(10,driver);
		return listHotelsResult.findElements(By.xpath(hotel)).get(0);
	}
}
