package com.mindtree.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BookAFlightPage {
	@FindBy(name="passFirst0")
	private WebElement firstName;
	
	@FindBy(name="passLast0")
	private WebElement lastName;
	
	@FindBy(name="creditnumber")
	private WebElement creditCardInformation;
	
	@FindBy(name="buyFlights")
	private WebElement securePurchase;
	
	public BookAFlightPage (WebDriver driver){
		PageFactory.initElements(driver, this);
	}


	public void flightBooking(String fName, String lName, String cNumber){
		firstName.sendKeys(fName);
		lastName.sendKeys(lName);
		creditCardInformation.sendKeys(cNumber);
		securePurchase.click();
		
	}
}
