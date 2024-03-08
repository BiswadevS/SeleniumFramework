package com.pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.abstractComponents.AbstractComponents;

public class CartPage extends AbstractComponents{

	WebDriver driver;
	
	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//*[@class='cartSection']/h3")
	List<WebElement> cartProducts;
	
	@FindBy(xpath="//button[normalize-space()='Checkout']")
	WebElement checkOutEle;
	
	public boolean verifyProductDisplay(String productName) {
	return cartProducts.stream().anyMatch(cart ->cart.getText().equalsIgnoreCase(productName));
	}
	
	public CheckoutPage goToCheckout() {
		
		checkOutEle.click();
		return new CheckoutPage(driver);
	}

}
