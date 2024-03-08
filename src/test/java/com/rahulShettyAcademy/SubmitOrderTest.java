package com.rahulShettyAcademy;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pageObjects.CartPage;
import com.pageObjects.CheckoutPage;
import com.pageObjects.ConfirmationPage;
import com.pageObjects.OrderPage;
import com.pageObjects.ProductCatalog;
import com.testComponents.BaseTest;

public class SubmitOrderTest extends BaseTest{
	
	 String productName = "ZARA COAT 3";
	
	@Test(dataProvider = "getData", groups = {"Purchase"})
//	public void submitOrder(String email, String password,  String productName) throws Exception {
	public void submitOrder(HashMap<String, String> input) throws Exception {
		
		System.out.println("Test....");
		
		
		//ProductCatalog catalog =landingPage.loginApplication(email, password);
		ProductCatalog catalog =landingPage.loginApplication(input.get("email"), input.get("password"));

		List<WebElement> products = catalog.getProductList();
//		catalog.addProductToCart(productName);
		//catalog.addProductToCart("product");
		catalog.addProductToCart(input.get("product"));
		CartPage cartPage = catalog.goToCartPage();	

		boolean match = cartPage.verifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		CheckoutPage checkoutPage =  cartPage.goToCheckout();
		checkoutPage.selectCountry("India");
		ConfirmationPage confirmationPage = checkoutPage.submit();
		String confirmMessage = confirmationPage.getConfirmationMessage();
		//Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		System.out.println("Order successful");

	}

	//verify productName is displaying in order page
	@Test(dependsOnMethods = {"submitOrder"})
	public void orderHistoryTest() {
		
		ProductCatalog catalog =landingPage.loginApplication("anshika11@gmail.com", "Iamking@000");
		OrderPage orderPage = catalog.goToOrderPage();
		Assert.assertTrue(orderPage.verifyOrderDisplay(productName));
		System.out.println("Order history successful");
		
		
		
	}
	
	
	
	@DataProvider
	public Object[][] getData() throws Exception {
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("email", "anshika11@gmail.com");
//		map.put("password", "Iamking@000");
//		map.put("product", "ZARA COAT 3");
//		
//		HashMap<String, String> map1 = new HashMap<String, String>();
//		map1.put("email", "Johncena@gmail.com");
//		map1.put("password", "Iamking@000");
//		map1.put("product", "ADIDAS ORIGINAL");
		
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+ "//src//test//java//com//data/PurchaseOrder.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};

		//return new Object[][] {{"anshika11@gmail.com", "Iamking@000", "ZARA COAT 3"},{"Johncena@gmail.com", "Iamking@000", "ADIDAS ORIGINAL"},};
		//return new Object[][] {{map},{map1}};
	
	}

}
