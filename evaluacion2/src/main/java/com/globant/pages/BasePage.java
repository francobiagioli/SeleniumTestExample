package com.globant.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

	protected WebDriver driver;

	public BasePage(WebDriver driver){
		this.driver =driver;
		PageFactory.initElements(driver, this);
	}

	public Boolean waitForAjax(){
		//didnt work , so I had to use the sleep method
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.pollingEvery(200, TimeUnit.SECONDS);
		return wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				//return (Boolean) js.executeScript("return jQuery.active == 0");
				return (Boolean)js.executeScript("return typeof(document) != 'undefined' && document.readyState == 'complete' && typeof($) != 'undefined' && $.active == 0;");
			}
		});
	}
	public void sleep(){
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void buttonClick(WebElement button){
		sleep();
		button.click();
	}



}
