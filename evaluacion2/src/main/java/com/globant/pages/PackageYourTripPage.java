package com.globant.pages;

import org.openqa.selenium.WebDriver;

public class PackageYourTripPage extends BasePage {

	public PackageYourTripPage(WebDriver driver) {
		super(driver);
		driver.get(driver.getCurrentUrl());
	}


}
