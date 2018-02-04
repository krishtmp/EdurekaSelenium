package com.mindtree.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SelectFlightPage {
	@FindBy(xpath="/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[5]/td/form/table[1]/tbody/tr[5]/td[1]/input")
	private WebElement departingFlight;
	
	@FindBy(xpath="/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[5]/td/form/table[2]/tbody/tr[7]/td[1]/input")
	private WebElement returningFlight;
	
	@FindBy(name="reserveFlights")
	private WebElement continueButton;
	
	public SelectFlightPage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	
	public void findFlight(){
		departingFlight.click();
		returningFlight.click();
		continueButton.click();
	}
}
