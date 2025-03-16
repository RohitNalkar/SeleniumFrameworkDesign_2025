package orderModule;


import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjectModel.CheckoutPage;
import pageObjectModel.OrderConfirmationPage;
import testComponents.BaseTest;

public class PurchaseOrderDataProviderTest extends BaseTest {
	//Variables required overall class
	String proName = "ZARA COAT 3";
	
	
	@Test (dataProvider="getloginData", groups= {"purchase"})
	public void placeThePurchaseTest(String email, String pass) throws InterruptedException {

		loginPage.loginApplication(email, pass);
		
		// add product in cart 
		proCatPage.addProductToCart(proName);
		
		//Click on cart button
		commonPage.goToCart();
		
		// verifying the product added in cart or not
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
	
	
	// verify the ordered item is displaying in order page 
	
	@Test (dataProvider= "getloginData", dependsOnMethods = {"placeThePurchaseTest"})
	public void orderHistoryTest(String email, String pass) {
		loginPage.loginApplication(email, pass );

		commonPage.goToOrdersPage();

		boolean flag = orderPage.verifyOrderProductDisplay(proName);
		Assert.assertTrue(flag, "FAILED - Product Are Missing From Order history : " + proName);
		
	}
	
	
	@DataProvider
	public Object[][] getloginData(){
		
		Object[][] data = new Object[2][2];
		data[0][0] = "rohitnalkar333@gmail.com";
		data[0][1] = "Rohit@123";
		data[1][0] = "rohitnalkar666@gmail.com";
		data[1][1] = "Rohit@123";
		
		return data;
	}
	
	
	// If we want to provide the two data then we have to combine them and then we can pass it 
	@DataProvider
	public Object[][] getCardData(){
		
		
		Object[][] data = {{"06", "18", "111", "ROHIT NALKAR", "India"},{"10", "25", "222", "GAURAV KALE", "India"}};
	
		return data;
	}

	// we can pass this to test 
	@DataProvider
	public Object[][] getCombinedData(){
	    Object[][] loginData = getloginData();
	    Object[][] cardData = getCardData();
	    
	    // Assuming both providers have the same number of rows:
	    int totalRows = loginData.length;
	    int loginCols = loginData[0].length;
	    int cardCols = cardData[0].length;
	    
	    Object[][] combinedData = new Object[totalRows][loginCols + cardCols];
	    
	    for (int i = 0; i < totalRows; i++){
	        int k = 0;
	        // copy login data
	        for (int j = 0; j < loginCols; j++){
	            combinedData[i][k++] = loginData[i][j];
	        }
	        // copy card dataz
	        for (int j = 0; j < cardCols; j++){
	            combinedData[i][k++] = cardData[i][j];
	        }
	    }
	    return combinedData;
	}

	
	
}
