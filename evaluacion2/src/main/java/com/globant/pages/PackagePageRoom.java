package com.globant.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PackagePageRoom extends BasePage{

	@FindBy(className="rating-number")
	private WebElement stars;

	@FindBy(id="rooms-and-rates")
	private WebElement aviableRooms;

	@FindBy(className="search-summary")
	private WebElement searchSumary; 

	private String Rooms ="//section[@id='rooms-and-rates']//article//div[@class='book-button-wrapper']";

	public PackagePageRoom(WebDriver driver) {
		// brings the result page ,when it opens in a new widow
		super(driver);
		sleep();
		for(String winHandle : driver.getWindowHandles()){
			driver.switchTo().window(winHandle);
		}
		driver.get(driver.getCurrentUrl());
	}
//10.160.2.251

	public WebElement getStars(){
		return stars;
	}

	public List<WebElement> getAviableRooms(){
		return aviableRooms.findElements(By.xpath(Rooms));
	}

	public WebElement getSearchSumary(){
		return searchSumary;
	}

	public PackagePageFlight selectListedRoom(Integer selectRoom){
		List<WebElement> list =aviableRooms.findElements(By.xpath(Rooms));
		list.get(selectRoom-1).click();//the index starts at 0 so I subtract 1 

		return new PackagePageFlight(driver);

	}
}
