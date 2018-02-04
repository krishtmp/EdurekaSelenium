package com.mindtree.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	@FindBy(xpath="//input[@name='login']")
	@CacheLookup
	public static WebElement signIn;	

	
	@FindBy(xpath="//input[@name='userName']")
	@CacheLookup
	public static WebElement userName;
	
	@CacheLookup
	@FindBy(xpath="//input[@name='password']")
	public static WebElement passWord;
	
	public LoginPage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	
	public void loginApplication(String name,String passw){
		userName.sendKeys(name);
		passWord.sendKeys(passw);
		signIn.click();
	}
}
