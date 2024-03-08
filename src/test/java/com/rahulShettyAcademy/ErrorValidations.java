package com.rahulShettyAcademy;


import org.testng.annotations.Test;


import org.testng.Assert;
import java.util.List;

import org.openqa.selenium.WebElement;
import com.pageObjects.CartPage;
import com.pageObjects.ProductCatalog;
import com.testComponents.BaseTest;
import com.testComponents.Retry1;

public class ErrorValidations extends BaseTest {
	
	 String productName = "ZARA COAT 3";
		
		@Test(groups = {"ErrorHandling"}, retryAnalyzer = Retry1.class)
		public void submitOrderError() throws Exception {
			
			System.out.println("Test....");
			
			
			ProductCatalog catalog =landingPage.loginApplication("anshika11@gmail.com", "king@000");
			Assert.assertEquals("Incorrect email password.", landingPage.getErrorMessage());

	}
		
		@Test
		public void ProductErrorValidation() {
			
			System.out.println("Test....");
			
			
			ProductCatalog catalog =landingPage.loginApplication("anshika11@gmail.com", "Iamking@000");

			List<WebElement> products = catalog.getProductList();
			catalog.addProductToCart(productName);
			CartPage cartPage = catalog.goToCartPage();	
			Boolean match = cartPage.verifyProductDisplay("ZARA COAT 33");
			Assert.assertFalse(match);
			System.out.println("Error test successful");

			
		}

}
