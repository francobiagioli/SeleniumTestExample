package com.globant.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PackagePageCar extends BasePage {

	@FindBy(id="grid-body")
	private WebElement carResults;
	
	private String carLink="//tbody[@id='grid-body']//a";
	
	public PackagePageCar(WebDriver driver) {
		super(driver);
		driver.get(driver.getCurrentUrl());
	}
	
	public List<WebElement> carList(){
		return carResults.findElements(By.xpath(carLink));
	}
	
	public PackageTripDetailPage selectCar(Integer index){
		waitForJQueryProcessing(10,driver);
		carList().get(index-1).click();//the index starts at 0 so I subtract 1 
		
		return new PackageTripDetailPage(driver);
		
	}

}
