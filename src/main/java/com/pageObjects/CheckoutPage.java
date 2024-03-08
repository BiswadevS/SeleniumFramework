package com.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.abstractComponents.AbstractComponents;

public class CheckoutPage extends AbstractComponents{
	
	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	WebDriver driver;
	
	By result = By.cssSelector(".ta-results");
	
	@FindBy(xpath = "//input[@placeholder='Select Country']")
	WebElement country;

	@FindBy(xpath = "(//button[contains(@class,'ta-item')])[2]")
	WebElement selectCountry;
	
	@FindBy(css = ".action__submit")
	WebElement submit;
	
	public void selectCountry(String countryName) {
		country.sendKeys(countryName);
		waitForElementToAppear(result);
		selectCountry.click();
		
	}
	
	public ConfirmationPage submit() {
		submit.click();
		return new ConfirmationPage(driver);
	}

}
