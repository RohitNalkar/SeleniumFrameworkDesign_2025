package orderModule;


import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjectModel.CartPage;
import pageObjectModel.CheckoutPage;
import pageObjectModel.CommonPage;
import pageObjectModel.LoginPage;
import pageObjectModel.OrderConfirmationPage;
import pageObjectModel.ProductCataloguePage;
import testComponents.BaseTest;

public class PlaceOrderTest extends BaseTest {
	//Variables required overall class
	String proName = "ZARA COAT 3";
	
	
	@Test (priority = 1, enabled = true)
	public void placeTheOrderTest() {

		loginPage.loginApplication(prop.getProperty("userName"), prop.getProperty("userPassword") );
		
		// Way 1st to sort and add product in cart 
		String[] productsToAdd = {"ADIDAS ORIGINAL" , "IPHONE 13 PRO"};
		proCatPage.addProductToCart(productsToAdd);
		
		// way 2nd to sort and add product in cart 
	
		proCatPage.addProductToCart(proName);

		//Click on cart button
		commonPage.goToCart();
			
		// verifying the product added in cart or not for 1Way
		for(String pro : productsToAdd) {
			boolean found = cartPage.verifyProductDisplay(pro);
			Assert.assertTrue(found, "FAILED - Product Are Missing From Cart : " + pro);
		}
				
		// verifying the product added in cart or not for 2Way
		boolean match = cartPage.verifyProductDisplay(proName);
		Assert.assertTrue(match, "FAILED - Product Are Missing From cart : " + proName);
		
		// click on checkout 
		CheckoutPage checkoutPage = cartPage.goToCheckout(); // so we have object here for CheckoutPage 
	
		checkoutPage.selectExpiryDate("06", "18");
		
		checkoutPage.addCvvCode("123");
		
		checkoutPage.addNameOnCard("ROHIT NALKAR");
		
		// Select Country
		checkoutPage.selectCountry("INDIA");

		OrderConfirmationPage orderConfirmationPage = checkoutPage.placeOrder();

		String confMsg = orderConfirmationPage.getOrderSuccessMsg();
		Assert.assertEquals(confMsg, "THANKYOU FOR THE ORDER.", "FAILED - While Validating the order confirmation msg");
		
	}
	
	
	// verify the ZARA COAT 3 is displaying in order page 
	
	@Test (dependsOnMethods = {"placeTheOrderTest"}, enabled = true)
	public void orderHistoryTest() {
		loginPage.loginApplication(prop.getProperty("userName"), prop.getProperty("userPassword") );

		commonPage.goToOrdersPage();

		boolean flag = orderPage.verifyOrderProductDisplay(proName);
		Assert.assertTrue(flag, "FAILED - Product Are Missing From Order history : " + proName);
		
	}
	
//	@Test
//	public void deleteProductsFromOrderHistory() {
//		
//	}
	
	
	
}
