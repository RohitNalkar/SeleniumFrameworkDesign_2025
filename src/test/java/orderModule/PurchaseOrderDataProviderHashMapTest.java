package orderModule;


import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjectModel.CheckoutPage;
import pageObjectModel.OrderConfirmationPage;
import testComponents.BaseTest;

public class PurchaseOrderDataProviderHashMapTest extends BaseTest {
	//Variables required overall class
	//	String proName = "ZARA COAT 3";
	
	
	@Test (dataProvider="getData", groups= {"purchase"})
	public void placeThePurchaseTest(HashMap<String, String> input) throws InterruptedException {

		loginPage.loginApplication(input.get("email"), input.get("password"));
		
		// add product in cart 
		proCatPage.addProductToCart(input.get("product"));
		
		//Click on cart button
		commonPage.goToCart();
		
		// verifying the product added in cart or not
		boolean match = cartPage.verifyProductDisplay(input.get("product"));
		Assert.assertTrue(match, "FAILED - Product Are Missing From cart : " + input.get("product"));
		
		// click on checkout 
		CheckoutPage checkoutPage = cartPage.goToCheckout(); // so we have object here for CheckoutPage 
	
		checkoutPage.selectExpiryDate(input.get("month"), input.get("date"));
		
		checkoutPage.addCvvCode(input.get("cvv"));
		
		checkoutPage.addNameOnCard(input.get("cardName"));
		
		// Select Country
		checkoutPage.selectCountry(input.get("country"));

		OrderConfirmationPage orderConfirmationPage = checkoutPage.placeOrder();

		String confMsg = orderConfirmationPage.getOrderSuccessMsg();
		Assert.assertEquals(confMsg, "THANKYOU FOR THE ORDER.", "FAILED - While Validating the order confirmation msg");
		
	}
	
	
	// verify the ordered item is displaying in order page 
	
	@Test (dataProvider= "getData", dependsOnMethods = {"placeThePurchaseTest"})
	public void orderHistoryTest(HashMap<String, String> input) {
		loginPage.loginApplication(input.get("email"), input.get("password"));

		commonPage.goToOrdersPage();

		boolean flag = orderPage.verifyOrderProductDisplay(input.get("product"));
		Assert.assertTrue(flag, "FAILED - Product Are Missing From Order history : " + input.get("product"));
		
	}
	
	
	@DataProvider
	public Object[][] getData(){
		
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", "rohitnalkar333@gmail.com");
		map.put("password", "Rohit@123");
		map.put("product", "ZARA COAT 3");
		map.put("month", "06");
		map.put("date", "18");
		map.put("cvv", "111");
		map.put("cardName", "ROHIT NALKAR");
		map.put("country", "India");
		
		
		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("email", "rohitnalkar666@gmail.com");
		map1.put("password", "Rohit@123");
		map1.put("product", "ADIDAS ORIGINAL");
		map1.put("month", "10");
		map1.put("date", "15");
		map1.put("cvv", "222");
		map1.put("cardName", "KALPESH PATIL");
		map1.put("country", "Austria");
		
		
		Object[][] data = {{map}, {map1}};
		
		
		return data;
	}
	
	

	
	
}
