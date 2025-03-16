package pageObjectModel;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import utility.Base;

public class CartPage extends Base {
	
	WebDriver driver;
	
	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}
	

	
	@FindBy(xpath = "//div[@class='cartSection']/h3")
	List <WebElement> cartProductsEle;
	
	@FindBy(xpath = "//li[@class='totalRow']/button")
	WebElement checkoutEle;

	By paymentMethodTxtEle = By.xpath("//div[text()=' Payment Method ']");
	
	public List<WebElement> getCartProductsList() {
		return cartProductsEle;
	}
	
	public boolean verifyProductDisplay(String productName) {
		boolean match = getCartProductsList().stream()
										   .anyMatch(product -> product.getText().equals(productName));
		return match;
	}
	
	
	// this utility just created to verify the product but not using this utility 
	// BCZ validation should be in the test case only not in POM class
	public void verifyProductDisplay(String[] productsName) {
		
		List<String> cardProducts = Arrays.asList(productsName);
		List<WebElement> products = getCartProductsList();
		
		for(WebElement pro : products) {
			
			String addedPro = pro.getText();
			if(!addedPro.equals("ZARA COAT 3")) { // just excluding this product as we are verifying in further code 
				Assert.assertTrue(cardProducts.contains(addedPro), "FAILED - Product Are Missing From Cart : " + addedPro);
			}
		}
	}

	public CheckoutPage goToCheckout() {
		checkoutEle.click();
		waitForElementToAppear(paymentMethodTxtEle);
		// as we know after clicking we are going to check out page 
		// so we can create object here its self and use it in code by returning the object 
		
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		return checkoutPage;
	}
	
	
	
	
	
}
