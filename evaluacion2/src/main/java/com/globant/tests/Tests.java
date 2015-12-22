package com.globant.tests;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.globant.pages.CruiseResultsPage;
import com.globant.pages.HomePage;
import com.globant.pages.HotelPage;
import com.globant.pages.PackagePageCar;
import com.globant.pages.PackagePageFlight;
import com.globant.pages.PackagePageHotel;
import com.globant.pages.PackagePageRoom;
import com.globant.pages.PackageTripDetailPage;
import com.globant.pages.PackageYourTripPage;
import com.globant.pages.ResultsPage;
import com.globant.pages.TripDetailPage;
import com.globant.pages.YourTripPage;

public class Tests extends BaseTestsSuite {

	WebDriver driver;

	private String origin="LAS";
	private String destination="LAX";
	private String destinationFull="Los Angeles";
	private String adults="1";
	private Integer daysAfter=13;
	private Integer monthsAfter=2;
	private String sort="duration:desc";//Duration (Longest)
	private String searchHotel="alex";
	private String cruiseDestination = "Europe";
	private String cruiseDeparture ="April 2016";
	private String cruiseDepartureResult="Apr 2016";
	private String cruiseLength ="10-14 Nights";
	private Integer minDays =10;
	private Integer maxDays =14;
	private Integer stars =3;

	@BeforeMethod(alwaysRun=true)
	public void before(){
		driver= getDriver("Firefox");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
	}

	@AfterMethod(alwaysRun=true)
	public void after(){
		driver.close();
	}


	/**
	 * Case 1:	Begin the process of booking a flight till the complete credit card information page.
	 * Precondition: The user is not logged in.
	 * - Look for a flight from LAS to LAX, 1 adult. Date shall be at least two month in the future and must be selected using the calendar.
	 * - Verify result page using at least 5 validations.
	 * - Sort by duration > shorter. Verify the list was correctly sorted.
	 * - Select the first element on the list.
	 * - In the new page (Select your departure to Los Angeles), select the first result.
	 * - In the new page (Select your departure to Las Vegas), select the third result.
	 * - In the pop-up, select “no, thanks, maybe later”
	 * - Verify trip details in the new page, using at least 5 validations. Continue
	 * - Verify the trip information is correct using 5 validations. Continue
	 * - Verify the “Who’s travelling” page is opened using at least 5 validations.
	 */
	@Test
	public void testSearchFlightAndNavigateToTripInformation() {

		HomePage home = getHomePage();

		Assert.assertEquals(home.getLoginMessage().getText(), "Account", "The user is logged in");

		ResultsPage resultsPage = home.searchFligth(origin, destination, adults, daysAfter, monthsAfter);

		// TODO: Waits for AJAX did not work. Please clarify the correct approach.
		// home.waitForAjax();

		verifySubmitedDataResults(resultsPage, daysAfter, monthsAfter, origin, destination);

		resultsPage.sortBy(sort);

		verifyOrderTimeAsc(resultsPage);

		TripDetailPage tripDetailPage = resultsPage.selectFligths(1, 3);

		verifyDataTrip(tripDetailPage, origin, destination, monthsAfter);

		YourTripPage yourTripPage = tripDetailPage.clickButtonContinueBooking();

		verifyWhoTravels(yourTripPage, origin, destination);
	}


	/**
	 * Case 2: 	Begin the process of booking a flight with hotel and car.
	 * Precondition: The user is not logged in.				
	 * - Go to “Flight + Hotel” page. Verify the page is correctly opened.//-------Had to pick the option hotel+flight+car , because , if not , then the car can't be chosen
	 * - Look for a flight from LAS to LAX, 1 adult. Date shall be at least two month in the future and must be selected using the calendar. The trip must last 13 days.
	 * - Verify results page using at least 5 validations.
	 * - Sort by price. Verify the results were correctly sorted.
	 * - Select the first result with at least 3 stars. Don’t use the first promoted option (Sheraton Agoura Hills Hotel). Bear in mind that probably it will be on the second or third page.
	 * - In the new page, verify the hotel is the selected in the previous step using at least 3 validations.
	 * - Select the first room option
	 * - In the new page,(Now select your departing flight), select the first result.
	 * - In the new page (Now select your return flight), select the third result.
	 * - Select a car
	 * - Verify Trip Details using at least 5 validations. Continue
	 * - Verify the trip details are still correct. Continue
	 * - Verify the “Who’s travelling” page is opened using at least 5 validations.
	 */
	@Test
	public void testSearchFlightHotelCarAndNavigateToTripInformation(){
		HomePage home= getHomePage();

		Assert.assertEquals(home.getLoginMessage().getText(), "Account", "The user is logged in");

		PackagePageHotel packagePageHotel = home.searchPackage(origin, destination, adults, daysAfter,monthsAfter);

		verifyDataPackage(packagePageHotel,origin,destinationFull,daysAfter,monthsAfter);

		packagePageHotel.sortHotelsByPrice();

		verifyPackagesSortByPriceAsc(packagePageHotel);

		PackagePageRoom packagePageRoom =packagePageHotel.selectFirstWithNrOfStars(stars);

		verifyDataHotel(packagePageRoom,stars,destinationFull);	

		PackagePageFlight packagePageFlight = packagePageRoom.selectListedRoom(1);

		PackagePageCar packagePageCar =packagePageFlight.selectFlight(1,3);      

		PackageTripDetailPage packageTripDetailPage =packagePageCar.selectCar(1);

		verifyPackageDataTrip(packageTripDetailPage,origin,destination);

		PackageYourTripPage packageYourTripPage = packageTripDetailPage.ContinueBooking();
		//After this , the page opens up a blank page with the url:https://www.travelocity.com/pub/agent.dll?qsfr=cmps&itid=&itty=&itdx=&ecid=&tovr=-1294367296
		//After a moment on that blank page, it turns itself to the home page
	}


	/**
	 * 	Case 3: Verify that search by hotel name works properly.
	 *	Precondition: The user is not logged in.		
	 * 	- Go to Hotels page. 
	 *	- Complete “Search by hotel name” field with the word “ocean”. Search ----->Changed to "alex" so it returns a valid value to continue
	 *	- Verify the results obtained are correct.
	 */
	@Test
	public void testSearchHotelAndVerifyResults(){
		HomePage home= getHomePage();

		Assert.assertEquals(home.getLoginMessage().getText(), "Account", "The user is logged in");

		HotelPage hotelPage = home.searchHotelByData(destination,adults,daysAfter,monthsAfter, searchHotel);

		verifySearchResult(hotelPage,searchHotel);
	}

	/**
	 *	Case 4: Verify that the error message displayed when looking for a hotel in incorrect dates is correct.
	 *	Precondition: The user is not logged in.	
	 *	- Click on “Flight + Hotel” option
	 *	- Complete all fields.
	 *	- Select the checkbox “I only need a hotel for part of my stay”
	 *	- Complete the new dates fields with dates that are not included in the period of the flight dates. Search
	 *	- Verify the following error message is displayed: “Your partial check-in and check-out dates must fall within your arrival and departure dates. Please review your dates.”
	 */
	@Test
	public void testSearchHotelWithIncorrectDataAndVerifyError(){
		HomePage home= getHomePage();

		Assert.assertEquals(home.getLoginMessage().getText(), "Account", "The user is logged in");

		home.searchPackagePartial(origin, destination, adults, daysAfter,monthsAfter);

		Assert.assertEquals(home.getlabelErrorDate().getText(), "Your partial check-in and check-out dates must fall within your arrival and departure dates. Please review your dates.","The message error is not the expected");	
	}

	/**
	 *  Case 5: Cruises information is correctly displayed
	 *	Precondition: The user is not logged in.
	 *	- Go to Cruises page.
	 *	- In the Going to drop down select “Europe”
	 *	- In the “Departure month” dropdown select “April 2016”. Search
	 *	- Verify the Filter information selected before appears in the refine results section below each dropdown.
	 *	- In the “Cruise Length” filter, select “10-14 nights” (Verify this information is displayed below the dropdown)
	 *	- Select the first cruise option
	 *	- Check the itinerary for each day is displayed.
	 */
	@Test
	public void testSearchACruiseAndChecksIfTheInformationIsCorrectlyCharged(){
		HomePage home= getHomePage();

		Assert.assertEquals(home.getLoginMessage().getText(), "Account", "The user is logged in");

		CruiseResultsPage cruiseResultsPage =home.searchCruise(cruiseDestination, cruiseDeparture);

		verifyCruiseSearch(cruiseResultsPage,cruiseDepartureResult,cruiseDestination);

		cruiseResultsPage.updateLength();

		verifyCruiseLength(cruiseResultsPage,cruiseLength);

		cruiseResultsPage.selectCruise();

		verifyCruiseItinerary(cruiseResultsPage,minDays,maxDays);
	}

	private void verifyCruiseItinerary(CruiseResultsPage cruiseResultsPage, Integer minDays, Integer maxDays) {
		//Checks that the itinerary is correct with the quantity of selected days and that each day has it description
		boolean filledCruiseItinerary=true;

		List<WebElement> list=cruiseResultsPage.listItinerary();

		if((list.size()<minDays)||list.size()>maxDays){
			filledCruiseItinerary=false;
		}else{
			for(WebElement element :list){
				if(element.getText().equals("")){
					filledCruiseItinerary=false;
				}
			}
		}
		Assert.assertTrue(filledCruiseItinerary,"the cruise itinerary is not properly filled");
	}

	private void verifyCruiseSearch(CruiseResultsPage cruiseResultsPage,String departure,String destination) {
		//checks that the departure date and destination are correctly charged
		String cruiseDestination=cruiseResultsPage.getLabelDestination().getText().toUpperCase();
		Assert.assertEquals(cruiseDestination,destination.toUpperCase(),"The cruise destination is not correctly selected");

		String cruiseDeparture=cruiseResultsPage.getLabelDeparture().getText().toUpperCase();
		Assert.assertEquals(cruiseDeparture,departure.toUpperCase(),"The cruise departure is not correctly selected");

	}

	private void verifyCruiseLength(CruiseResultsPage cruiseResultsPage,String Length) {
		//checks that the trip's length is correctly charged

		String cruiseLength=cruiseResultsPage.getLabelLength().getText().toUpperCase();
		Assert.assertEquals(cruiseLength,Length.toUpperCase(),"The cruise length is not correctly displayed");
	}

	public HomePage getHomePage(){

		return new HomePage(driver);
	}

	public void verifySubmitedDataResults(ResultsPage results,Integer daysAfter,Integer monthsAfter,String origin ,String destination){

		//TODO: Replace Assert.assertTrue by Assert.assertEquals

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance(); 
		cal.add(Calendar.MONTH, monthsAfter);
		String dDate = sdf.format(cal.getTime());
		String departDate = results.getFieldDepartDate().getAttribute("value");
		Assert.assertEquals(departDate,dDate,"The depart date is not charged correctly");

		cal.add(Calendar.DAY_OF_MONTH, daysAfter);
		String rDate = sdf.format(cal.getTime());
		String returnDate = results.getFieldReturnDate().getAttribute("value");
		Assert.assertEquals(returnDate,rDate,"The return date is not charged correctly");

		Boolean departureAirpot = results.getFieldDepartureAirpot().getAttribute("value").contains(origin);
		Assert.assertTrue(departureAirpot,"The depart airport is not charged correctly");

		Boolean returnAirpot = results.getFieldReturnAirpot().getAttribute("value").contains(destination);
		Assert.assertTrue(returnAirpot,"The return airport is not charged correctly");

		Boolean checkRoundTrip =results.getRadioRoundTrip().isSelected();
		Assert.assertTrue(checkRoundTrip,"The round check validation is not charged correctly");


	}	

	public void verifyDataTrip(TripDetailPage tripDetailPage,String origin,String destination,Integer monthsAfter){

		Boolean	departureDate =verifyDepartTrip(tripDetailPage.getDepartureDate().getText(),monthsAfter);
		Assert.assertTrue(departureDate,"The departure date is not charged correctly");

		String departureAirpotGo =tripDetailPage.getDepartureAirportGo().getText();
		Assert.assertEquals(departureAirpotGo,origin,"The departure airpot go is not charged correctly");

		String arrivalAirpotGo =tripDetailPage.getArrivalAirportGo().getText();
		Assert.assertEquals(arrivalAirpotGo,destination,"The arrival airpot go is not charged correctly");

		String departureAirpotBack =tripDetailPage.getDepartureAirportBack().getText();
		Assert.assertEquals(departureAirpotBack,destination,"The departure airpot back is not charged correctly");

		String arrivalAirpotBack =tripDetailPage.getArrivalAirportBack().getText();
		Assert.assertEquals(arrivalAirpotBack,origin,"The arrival airpot back is not charged correctly");


	}

	public Boolean verifyDepartTrip(String trip,Integer mothsAfter){
		SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
		Calendar cal = Calendar.getInstance(); 
		cal.add(Calendar.MONTH,monthsAfter);
		String dDate = sdf.format(cal.getTime());
		return trip.contains(dDate);
	}

	public Integer turnsTimeToMinutes(String value){
		//turns the format "1h 12m" into its value in minutes
		String completeTime =value.replace("h", "").replace("m", "");
		String[] splited = completeTime.split("\\s+");
		
		String hours =splited[0];
		if(hours.equals("")){
			hours="0";
		}
		String minutes =splited[1];
		if(minutes.equals("")){
			minutes="0";
		}
		Integer timeInMins=Integer.parseInt(hours)*60+Integer.parseInt(minutes);
		return timeInMins;
	}

	public void verifyOrderTimeAsc(ResultsPage resultsPage){
		//Checks that the flight list is correctly order by descending time
		List<WebElement> list=resultsPage.getFligthList();     
		Integer itemBef =turnsTimeToMinutes(list.get(0).getText());

		Boolean ordered =true;

		for (WebElement item :list){
			Integer itemAct =turnsTimeToMinutes(item.getText());
			if((itemBef<itemAct)){
				ordered = false;
			}else{
				itemBef =turnsTimeToMinutes(item.getText());
			}
		}
		Assert.assertTrue(ordered,"The data is not ordered correcly");
	}

	public void verifyPackagesSortByPriceAsc(PackagePageHotel packagePageHotel){
		//checks if the list of hotels is ordered by ascending price
		List<WebElement> list =packagePageHotel.getPriceList();
		Boolean ordered=true;
		Double priceBef=Double.parseDouble(list.get(0).getText().replace("$","").replace(",",""));

		for(WebElement element : list){
			Double priceAct =Double.parseDouble(element.getText().replace("$","").replace(",",""));
			if(priceBef>priceAct){
				ordered = false;
			}else{
				priceBef=priceAct;
			}
		}
		Assert.assertTrue(ordered,"The prices aren't correctly sorted");

	}

	public void verifyWhoTravels(YourTripPage yourTripPage,String origin,String destination){

		Boolean urlCheckout = driver.getCurrentUrl().contains("FlightCheckout");
		Assert.assertTrue(urlCheckout,"The current page is not the Checkout page");

		String whoTravels = yourTripPage.whoTravelsTitle().getText();
		Assert.assertEquals(whoTravels,"Who's traveling?","The current page is not the Checkout page");

		String departureAirportGo=yourTripPage.getDepartureAirportGo().getText();
		Assert.assertEquals(departureAirportGo,origin,"The departure Airport Go field is not filled correctly");

		String arrivalAirportGo=yourTripPage.getArrivalAirportGo().getText();
		Assert.assertEquals(arrivalAirportGo,destination,"The arrival Airport Go field is not filled correctly");

		String departureAirportBack=yourTripPage.getDepartureAirportBack().getText();
		Assert.assertEquals(departureAirportBack,destination,"The departure Airport back field is not filled correctly");

	}

	public void verifyDataPackage(PackagePageHotel packagePageHotel,String origin,String destination,Integer daysAfter,Integer monthsAfter){
		//verifies that the date in the package is correctly charged
		Boolean departureAirport = packagePageHotel.getLabelFrom().getText().contains(origin);
		Assert.assertTrue(departureAirport, "The departure airport is not correctly charged");

		Boolean	arriveAirport = packagePageHotel.getFieldTo().getAttribute("value").contains(destination);
		Assert.assertTrue(arriveAirport, "The arrival airport is not correctly charged");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, monthsAfter);
		SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");

		Boolean departureDate = packagePageHotel.getDateFrom().getAttribute("value").equals(sdf.format(cal.getTime()));
		Assert.assertTrue(departureDate, "The departure date is not correctly charged");

		cal.add(Calendar.DAY_OF_MONTH, daysAfter);
		Boolean arriveDate = packagePageHotel.getDateTo().getAttribute("value").equals(sdf.format(cal.getTime()));
		Assert.assertTrue(arriveDate, "The arrival date is not correctly charged");

		String roomAdultsData = packagePageHotel.getLabelChoseHotel().getText();
		Assert.assertEquals(roomAdultsData, "Start by choosing your hotel", "The number of adults is not correctly charged");
	}

	public void verifyPackageDataTrip(PackageTripDetailPage packageTripDetailPage,String origin,String destination){

		String pageTitle =packageTripDetailPage.getPageTitle().getText();
		Assert.assertEquals(pageTitle,"Trip details","The charged page is not thwe expected");

		Boolean departureAirportGo =packageTripDetailPage.getDepartureAirportGo().getText().contains(origin);
		Assert.assertTrue(departureAirportGo,"The departure airport go is not charged correctly");

		Boolean arrivalAirportGo =packageTripDetailPage.getArrivalAirportGo().getText().contains(destination);
		Assert.assertTrue(arrivalAirportGo,"The arrival airport go is not charged correctly");

		Boolean departureAirportBack =packageTripDetailPage.getDepartureAirportBack().getText().contains(destination);
		Assert.assertTrue(departureAirportBack,"The departure airport back is not charged correctly");

		Boolean arrivalAirportBack =packageTripDetailPage.getArrivalAirportBack().getText().contains(origin);
		Assert.assertTrue(arrivalAirportBack,"The arrival airport back is not charged correctly");
	}

	public void verifyDataHotel(PackagePageRoom packagePageRoom,Integer nrStars,String destination){

		Boolean stars = Double.parseDouble(packagePageRoom.getStars().getText())>nrStars;
		Assert.assertTrue(stars,"The number of stars doesent fit the required");

		Boolean aviability = (packagePageRoom.getAviableRooms().size()!= 0); 
		Assert.assertTrue(aviability,"the selected hotel has no aviable rooms");

		Boolean location =packagePageRoom.getSearchSumary().getText().contains(destination);
		Assert.assertTrue(location,"The hotel is not located in the desired destination");

	}

	public void verifySearchResult(HotelPage hotelPage,String hotel){
		//verifies that the first result of the list corresponds to the search 
		Boolean searchCorrect = hotelPage.searchedHotel().getText().toUpperCase().contains(hotel.toUpperCase());

		Assert.assertTrue(searchCorrect,"The hotel isn't correctly selected");

	}

}
