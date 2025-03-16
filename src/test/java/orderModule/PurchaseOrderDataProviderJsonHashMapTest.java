package orderModule;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjectModel.CheckoutPage;
import pageObjectModel.OrderConfirmationPage;
import testComponents.BaseTest;

public class PurchaseOrderDataProviderJsonHashMapTest extends BaseTest {
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
	public Object[][] getData() throws IOException{
		
		String jsonFilePath = System.getProperty("user.dir")+ "/testData/orderModule/PurchaseOrder.json";
		
		List<HashMap<String, String>> jsonData = getJsonDataToMap(jsonFilePath);
		
		Object[][] data = {{jsonData.get(0)}, {jsonData.get(1)}};
		
		return data;
	}
	
	

	
	
}
