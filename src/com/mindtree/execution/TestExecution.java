package com.mindtree.execution;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.mindtree.pom.BookAFlightPage;
import com.mindtree.pom.FlightFinderPage;
import com.mindtree.pom.LoginPage;
import com.mindtree.pom.SelectFlightPage;

@Listeners(TestReportGenerate.class)
public class TestExecution extends DependentFunctions 
{
	public TestExecution() {	}
	
	@Test(priority = 1)
	public void loginFunction() {
		try {
			
			Thread.sleep(3000);
			
			LoginPage la = new LoginPage(driver);
			la.loginApplication("krishna", "Krishna");

			WebElement signingOff = driver.findElement(By.xpath("//a[text()='SIGN-OFF']"));
			AssertJUnit.assertEquals(signingOff.getText(), "SIGN-OFF");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();

		}
	}

	@Test(priority = 2)
	private void bookingTicket() {
		try {
			FlightFinderPage ffp = new FlightFinderPage(driver);
			ffp.selectingTheFlight();
			SelectFlightPage sfp = new SelectFlightPage(driver);
			sfp.findFlight();
			BookAFlightPage bfp = new BookAFlightPage(driver);
			bfp.flightBooking("Krishnamoorthy", "Selvaraj", "0987654321");

			WebElement confirm = driver.findElement(By.xpath("//*[contains(text(),'been booked')]"));
			AssertJUnit.assertEquals(confirm.getText(), "Your itinerary has been booked!");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();

		}
	}

}
