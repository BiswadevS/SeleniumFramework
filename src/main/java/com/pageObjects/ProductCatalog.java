package com.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.abstractComponents.AbstractComponents;

public class ProductCatalog extends AbstractComponents{
	
WebDriver driver;
	
	public ProductCatalog(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(css = ".mb-3")
	List<WebElement> products ;
	
	By productsBy = By.cssSelector(".mb-3");
	By addToCart = By.cssSelector(".card-body button:last-of-type");
	By toast = By.cssSelector("#toast-container");
	
	@FindBy(css = ".ng-animating")
	WebElement spinner ;

	
	public List<WebElement> getProductList() {
		waitForElementToAppear(productsBy);
		return products;
		
	}
	
	public WebElement getProductByName(String productName) {
		return products.stream().filter(product->
		product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		
	}
	public void addProductToCart(String productName) {
		waitForElementToDisappear(spinner);
		WebElement prod = getProductByName(productName);
		
		prod.findElement(addToCart).click();
		waitForElementToAppear(toast);
		waitForElementToDisappear(spinner);
		
	}
}
