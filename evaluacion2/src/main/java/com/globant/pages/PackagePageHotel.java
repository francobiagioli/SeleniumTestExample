package com.globant.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PackagePageHotel extends BasePage{

	@FindBy(xpath="//div[@id='fromBarCollapsed']//div/p")
	private WebElement labelFrom;

	@FindBy(id="acol-going-to")
	private WebElement FieldTo;

	@FindBy(id="acol-flight-check-in")
	private WebElement dateFrom;

	@FindBy(id="acol-flight-check-out")
	private WebElement dateTo;

	@FindBy(id="sbarprice")
	private WebElement sortByPrice;

	@FindBy(id="packageSearchResults")
	private WebElement packageList;

	@FindBy(className="title-variation")
	private WebElement labelChoseHotel;

	private String packageResultsListPrice="//div[@id='packageSearchResults']//span[@class='formatted_price']";
	private String packageResultsListStars="//div[@id='packageSearchResults']//div[@class='inner clearfix']//div[@class='hotel_name']//span";




	public PackagePageHotel(WebDriver driver) {
		super(driver);
		driver.get(driver.getCurrentUrl());
	}

	public WebElement getLabelChoseHotel(){
		return labelChoseHotel;
	}

	public WebElement getLabelFrom(){
		waitForJQueryProcessing(10,driver);
		return labelFrom;
	}
	public WebElement getFieldTo(){
		return FieldTo;
	}

	public WebElement getDateTo(){
		return dateTo;
	}

	public WebElement getDateFrom(){
		return dateFrom;
	}

	public PackagePageHotel sortHotelsByPrice(){
		buttonClick(sortByPrice);
		return this;
	}

	public List<WebElement> getPriceList(){
		waitForJQueryProcessing(10,driver);
		return packageList.findElements(By.xpath(packageResultsListPrice));
	}

	public PackagePageRoom selectFirstWithNrOfStars(Integer stars){
		//selects the first listed hotel, that fits a minimum of stars
		List<WebElement> list =packageList.findElements(By.xpath(packageResultsListStars));
		for(WebElement element :list){
			Integer star = Integer.parseInt(element.getText().substring(0, 1));
			if(star>=stars){
				element.click();
				return new PackagePageRoom(driver);
			}
		}
		return null;
	}
}
