package com.globant.pages;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

	private final String URL = "http://www.travelocity.com/";
	private final String aviableDates = "//div[@class='cal']//li/a";
	private final String calendarDisplayedMonths ="//section[@class='cal-month']//h2";	
	private final String destinations ="//select[@id='cruise-destination']//option";
	private final String departures ="//select[@id='cruise-departure-month']//option";

	@FindBy(id="hotel-checkin")
	private WebElement calendarHotelCheckIn;

	@FindBy(id="hotel-checkout")
	private WebElement calendarHotelCheckOut;

	@FindBy(id="tab-hotel-tab")
	private WebElement buttonOnlyHotel;

	@FindBy(id="primary-header-hotel")
	private WebElement buttonHotel;

	@FindBy(id ="header-account-menu")
	private WebElement headerUser;

	@FindBy(id="flight-origin")
	private WebElement fieldFlyingFrom;

	@FindBy(id="flight-destination")
	private WebElement fieldFlyingTo;

	@FindBy(id="hotel-destination")
	private WebElement fieldHotelDestiantion;

	@FindBy(id="package-origin")
	private WebElement fieldPackageFrom;

	@FindBy(id="package-destination")
	private WebElement fieldPackageTo;

	@FindBy(id="flight-adults")
	private WebElement comboNumberAdultsFlight;

	@FindBy(id="package-1-adults")
	private WebElement comboNumberAdultsPackage;

	@FindBy(id="hotel-1-adults")
	private WebElement comboNumberAdultsHotel;

	@FindBy(id="flight-departing")
	private WebElement calendarDateDeparting;

	@FindBy(id="flight-returning")
	private WebElement calendarDateReturning ;

	@FindBy(id="package-departing")
	private WebElement calendarDateDepartingPackage;

	@FindBy(id="package-returning")
	private WebElement calendarDateReturningPackage ;

	@FindBy(className="cal-month")
	private WebElement calendarActualMonth ;

	@FindBy(xpath="//section[@class='cal-month']//h2")
	private WebElement labelMonthCalendar;

	@FindBy(xpath="//button[@class='btn-paging btn-secondary next']")
	private WebElement ButtonNextMonth;

	@FindBy(id="search-button")
	private WebElement buttonSearch;

	@FindBy(xpath ="//ul[@class='tabs cf col']//a[@data-tab='flight']")
	private WebElement buttonFlight;

	@FindBy(xpath ="//ul[@class='tabs cf col']//a[@data-tab='package']")
	private WebElement buttonPackage;

	@FindBy(id="package-fhc-label")
	private WebElement buttonCarHotelFlight;

	@FindBy(id="partialHotelBooking")
	private WebElement checkPartialBooking;

	@FindBy(id="package-checkin")
	private WebElement calendarDateCheckIn;

	@FindBy(id="package-checkout")
	private WebElement calendarDateCheckOut;

	@FindBy(className="partialStayDatesOutOfRange")
	private WebElement labelErrorDateOutOfRange;

	@FindBy(id="primary-header-cruise")
	private WebElement buttonCruise;

	@FindBy(id="cruise-destination")
	private WebElement comboCruiseDestination;	

	@FindBy(id="cruise-departure-month")
	private WebElement comboCruiseDeparture;


	public HomePage(WebDriver driver) {

		super(driver);
		driver.get(URL);
	}


	/**
	 * 
	 * @param origin
	 * @param destination
	 * @param adults
	 * @param daysAfter
	 * @return
	 * @throws Exception 
	 */
	public ResultsPage searchFligth(String origin, String destination, String adults, Integer daysAfter,Integer monthsAfter) {
		//search for a flight
		String type= "flight";
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, monthsAfter);
		return buttonTypeClick(type)
				.fillFieldTo(destination,type)
				.fillFieldFrom(origin,type)
				.fillComboNumberAdults(adults,type)
				.selectCalendarDateDeparting(cal,type)
				.selectCalendarDateReturning(cal,daysAfter,type)
				.buttonSearchClick();
	}

	public PackagePageHotel searchPackage(String origin, String destination, String adults, Integer daysAfter,Integer monthsAfter) {
		//search for a package(car, hotel & flight) 
		String type= "package";
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, monthsAfter);
		return buttonTypeClick(type)
				.buttonCarHotleFligthClick()
				.fillFieldTo(destination,type)
				.fillFieldFrom(origin,type)
				.fillComboNumberAdults(adults,type)
				.selectCalendarDateDeparting(cal,type)
				.selectCalendarDateReturning(cal,daysAfter,type)
				.buttonSearchPackageClick();
	}


	public HomePage searchPackagePartial(String origin, String destination, String adults, Integer daysAfter,Integer monthsAfter) {
		//search a flight+hotel with a partial stay in the hotel
		String type= "package";
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, monthsAfter);
		return buttonTypeClick(type)
				.fillFieldTo(destination,type)
				.fillFieldFrom(origin,type)
				.fillComboNumberAdults(adults,type)
				.selectCalendarDateDeparting(cal,type)
				.selectCalendarDateReturning(cal,daysAfter, type)
				.setCheckPartialStay()
				.selectCalendarDateCheckIn(cal)
				.buttonSearchPartialClick();
	}

	private HomePage selectCalendarDateCheckIn(Calendar cal) {
		//adds dome days to the calendar , so the dates in the "partial stay" are out of range
		cal.add(Calendar.DAY_OF_MONTH,20);
		picDay(calendarDateCheckIn, cal);
		return this;
	}

	public HotelPage searchHotel(String destination,String adults,Integer daysAfter,Integer monthsAfter){
		//search for a hotel
		String type="hotel";
		Calendar cal =Calendar.getInstance();
		cal.add(Calendar.MONTH, monthsAfter);
		return buttonHotelClick()
				.buttonOnlyHotelClick()
				.fillFieldTo(destination,type)
				.fillComboNumberAdults(adults,type)
				.selectCalendarDateDeparting(cal,type)
				.selectCalendarDateReturning(cal,daysAfter, type)
				.buttonSearchHotelClick();
	}

	public HomePage setCheckPartialStay() {
		sleep();
		checkPartialBooking.click();
		return this;
	}

	public HomePage fillFieldTo(String destination,String type){

		switch(type){
		case "package":
			return sendKeys(fieldPackageTo,destination);
		case "flight":
			return sendKeys(fieldFlyingTo,destination);
		case "hotel":
			return sendKeys(fieldHotelDestiantion,destination);
		default:
			return sendKeys(fieldFlyingTo,destination);
		}
	}

	public HomePage fillFieldFrom(String origin ,String type){
		switch(type){
		case "package":
			return sendKeys(fieldPackageFrom,origin);			
		case "flight":
			return sendKeys(fieldFlyingFrom,origin);			
		default:
			return sendKeys(fieldFlyingFrom,origin);			
		}
	}

	public HomePage fillComboNumberAdults(String adults,String type){
		switch(type){
		case "package":
			return sendKeys(comboNumberAdultsPackage,adults);	
		case "flight":
			return sendKeys(comboNumberAdultsFlight,adults);	
		case "hotel":
			return sendKeys(comboNumberAdultsHotel,adults);
		default:
			return sendKeys(comboNumberAdultsFlight,adults);	
		}
	}

	public HomePage selectCalendarDateDeparting(Calendar depart, String type){//siempre es la primera fecha disponible del calendario
		switch(type){
		case "package":
			return picDay(calendarDateDepartingPackage,depart);	
		case "flight":
			return picDay(calendarDateDeparting,depart);	
		case "hotel":
			return picDay(calendarHotelCheckIn,depart);	
		default:
			return picDay(calendarDateDeparting,depart);	
		}
	}

	public HomePage selectCalendarDateReturning(Calendar calendar,Integer daysAfter,String type){
		sleep();
		calendar.add(Calendar.DAY_OF_MONTH, daysAfter);

		switch(type){
		case "package":
			return picDay(calendarDateReturningPackage,calendar);	
		case "flight":
			return picDay(calendarDateReturning,calendar);
		case "hotel":
			return picDay(calendarHotelCheckOut,calendar);
		default:
			return picDay(calendarDateReturning,calendar);	
		}
	}

	public HomePage picDay(WebElement element , Calendar calendar){
		//uses the day piker to pick a specific day , or the first avaiable day
		element.click();
		if (calendar!=null){
			return selectReturningDate(calendar);
		}else{
			calendarActualMonth.findElements(By.xpath(aviableDates)).get(0).click();
			return this;
		}
	}

	public HomePage selectReturningDate(Calendar calendar){
		//uses the day piker to reach the desired month and ten clicks on the desired day
		String ActualMonthYear=calendarActualMonth.findElements(By.xpath(calendarDisplayedMonths)).get(0).getText();
		String monthYear= (new SimpleDateFormat("MMM").format(calendar.getTime())+" "+
				new SimpleDateFormat("YYYY").format(calendar.getTime())).toUpperCase();

		while(!ActualMonthYear.equals(monthYear)){
			ButtonNextMonthClick();
			ActualMonthYear=calendarActualMonth.findElements(By.xpath(calendarDisplayedMonths)).get(0).getText();
			System.out.println("ActualMonthYear   "+ActualMonthYear);
		}

		Integer day = calendar.getTime().getDate();

		List<WebElement> list =calendarActualMonth.findElements(By.xpath(aviableDates));
		for(WebElement element :list){
			if(Integer.parseInt(element.getText())==day){
				element.click();
				break;
			}
		}
		return this;
	}

	public HomePage ButtonNextMonthClick(){
		ButtonNextMonth.click();
		return this;
	}

	public ResultsPage buttonSearchClick() {
		sleep();
		buttonSearch.click();
		return new ResultsPage(driver);
	}

	public HotelPage buttonSearchHotelClick() {
		buttonClick(buttonSearch);
		return new HotelPage(driver);
	}

	public PackagePageHotel buttonSearchPackageClick() {
		buttonClick(buttonSearch);	
		return new PackagePageHotel(driver);
	}

	public HomePage buttonSearchPartialClick() {
		buttonClick(buttonSearch);	
		return this;
	}

	public HomePage buttonFlightClick(){
		buttonClick(buttonFlight);
		return this;
	}

	public HomePage buttonPackageClick(){
		buttonClick(buttonPackage);
		return this;
	}

	public HomePage buttonTypeClick(String type){
		switch(type){
		case "package":
			return buttonPackageClick();
		case "flight":
			return buttonFlightClick();
		default :
			return buttonFlightClick();
		}
	}

	public WebElement getLoginMessage(){
		return headerUser;
	}

	public HomePage sendKeys(WebElement element,String value){
		element.sendKeys(value);
		return this;
	} 

	public HomePage buttonCarHotleFligthClick(){
		buttonCarHotelFlight.click();
		return this	;
	}
	public HomePage buttonHotelClick(){
		buttonHotel.click();
		return this;
	}
	public HomePage buttonOnlyHotelClick(){
		buttonOnlyHotel.click();
		return this;
	}

	public HotelPage searchHotelByData(String destination, String adults, Integer daysAfter,Integer monthsAfter, String searchHotel) {
		return searchHotel(destination,adults, daysAfter,monthsAfter)
				.ButtonGotItClick()
				.fillFieldSearchHotel(searchHotel)
				.ButtonGoClick();
	}

	public WebElement getlabelErrorDate(){
		return labelErrorDateOutOfRange;
	}

	public HomePage buttonCruiseClick(){
		buttonClick(buttonCruise);
		return this;
	}

	public HomePage selectComboCruiseDestination(String cruiseDestination){
		comboCruiseDestination.click();
		List<WebElement> list =comboCruiseDestination.findElements(By.xpath(destinations));
		findStringInListAndClick(cruiseDestination,list);
		return this;
	}



	public HomePage selectComboCruiseDeparture(String cruiseDeparture) {
		comboCruiseDeparture.click();
		List<WebElement> list =comboCruiseDeparture.findElements(By.xpath(departures));
		findStringInListAndClick(cruiseDeparture,list);
		return this;

	}

	public void findStringInListAndClick(String searchValue,List<WebElement> list){
		for(WebElement element :list){
			if (element.getText().equals(searchValue)){
				buttonClick(element);
				break;
			}
		}
	}

	public CruiseResultsPage buttonSearchCruiseClick() {
		buttonClick(buttonSearch);
		return new CruiseResultsPage(driver);

	}

	public CruiseResultsPage searchCruise(String cruiseDestination, String cruiseDeparture){
		return buttonCruiseClick()
				.selectComboCruiseDestination(cruiseDestination)
				.selectComboCruiseDeparture(cruiseDeparture)
				.buttonSearchCruiseClick();
	}

}
