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

public class ErrorValidationTest extends BaseTest {

	@Test (groups={"ErrorHandling"})
	public void loginErrorValidation() {

		loginPage.loginApplication(prop.getProperty("userNameFault"), "Invalid@123"); // Invalid Pass
		
		String errorMsg = loginPage.getErrorMsg();
		Assert.assertEquals(errorMsg, "Incorrect email or password.", "FAILED - Error msg toster is not present");
		
	}
	
	
	@Test
	public void productErrorValidation() {

		loginPage.loginApplication(prop.getProperty("userNameFault"), prop.getProperty("userPasswordFault") );
		
		// Way 1st to sort and add product in cart 
		String[] productsToAdd = {"ADIDAS ORIGINAL" , "IPHONE 13 PRO"};
		proCatPage.addProductToCart(productsToAdd);
		
		// way 2nd to sort and add product in cart 
		String proName = "ZARA COAT 3";
		//proCatPage.addProductToCart(proName); // this product we are not adding into cart and validating the error msg 

		//Click on cart button
		commonPage.goToCart();
			
		// verifying the product added in cart or not for 1Way
		for(String pro : productsToAdd) {
			boolean found = cartPage.verifyProductDisplay(pro);
			Assert.assertTrue(found, "FAILED - Product Are Missing From Cart : " + pro);
		}
				
		// verifying the product added in cart or not for 2Way
		boolean match = cartPage.verifyProductDisplay(proName);
		Assert.assertFalse(match); // product should not be there in cart
		

	}
	
	
}
