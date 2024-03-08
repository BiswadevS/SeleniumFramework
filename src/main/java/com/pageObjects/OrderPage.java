package com.pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.abstractComponents.AbstractComponents;

public class OrderPage extends AbstractComponents{

	WebDriver driver;
	
	public OrderPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//tr/td[2]")
	List<WebElement> productNames;
	
	@FindBy(xpath="//button[normalize-space()='Checkout']")
	WebElement checkOutEle;
	
	//tbody/tr/td[2]
	
	public boolean verifyOrderDisplay(String productName) {
	return productNames.stream().anyMatch(product ->product.getText().equalsIgnoreCase(productName));
	}
	
	public CheckoutPage goToCheckout() {
		
		checkOutEle.click();
		return new CheckoutPage(driver);
	}

}
