package com.mindtree.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class FlightFinderPage {

	@FindBy(xpath="//input[@name ='servClass'][2]/..//input")
	private WebElement businessClassRadiobutton;

	@FindBy(name="airline")
	private WebElement flightSelectionDropdown;

	@FindBy(name="findFlights")
	private WebElement ContinueButton;

	public FlightFinderPage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}

	public void selectingTheFlight(){
		businessClassRadiobutton.click();
		Select drpDownFlight = new Select(flightSelectionDropdown);
		drpDownFlight.selectByIndex(2);
		ContinueButton.click();
	}
	
	
}
