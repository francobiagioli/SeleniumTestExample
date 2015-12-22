package com.globant.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseTestsSuite {

	static {
		System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
	}

	protected WebDriver getDriver(String browser) {
		switch (browser) {
		case "firefox":
			return new FirefoxDriver();
		case "chrome":
			return new ChromeDriver();
		default:
			return new FirefoxDriver();
		}
	}
}