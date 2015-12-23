package com.globant.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PackagePageFlight extends BasePage {

	@FindBy(id="resultList")
	private WebElement resultFlightList;

	private String resultFligths= "//div[@id='resultList']//div[@class='flt_result ']//li[@id='selectAndContinue']/a";

	public PackagePageFlight(WebDriver driver) {
		super(driver);
		driver.get(driver.getCurrentUrl());
	}

	public PackagePageFlight selectDepartureFlight(Integer index){
		resultFlightList.findElements(By.xpath(resultFligths)).get(index-1).click();//the index starts at 0 so I subtract 1 
		return this;
	}

	public PackagePageCar selectReturnFlight(Integer index){
		waitForJQueryProcessing(10,driver);
		resultFlightList.findElements(By.xpath(resultFligths)).get(index-1).click();//the index starts at 0 so I subtract 1 
		return new PackagePageCar(driver);
	}

	public PackagePageCar selectFlight(Integer departure,Integer retur){
		return selectDepartureFlight(departure)
				.selectReturnFlight(retur);
	}

}
