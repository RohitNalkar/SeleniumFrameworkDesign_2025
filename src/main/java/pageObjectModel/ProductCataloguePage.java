package pageObjectModel;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utility.Base;

public class ProductCataloguePage extends Base {


	WebDriver driver;
	
	Base base = new Base(driver);
	
	// initializing the variables by constructor
	public ProductCataloguePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	// Locating all the Web Element 
	@FindBy(css = ".col-lg-4")
	List<WebElement> productsEle;
	
	
	@FindBy(css = ".ng-animating")
	WebElement spinnerEle;
	
	// Finding element using By class and using as per requirement 
	By productsBy = By.cssSelector(".col-lg-4");
	By addToCart = By.cssSelector(".card-body button:last-of-type");
	By bottomTosterBy = By.cssSelector("#toast-container");
	
	
	
	public List<WebElement> getProductList() {
		
		waitForElementToAppear(productsBy);
		return productsEle;
	}
	
	
	public WebElement getProductByName(String productName) {
		WebElement prod = getProductList().stream()
				.filter(product -> 
				product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		return prod;
	}
	
	
	public void addProductToCart(String productName) {
		
		WebElement prod = getProductByName(productName);
		prod.findElement(addToCart).click();
		waitForElementToAppear(bottomTosterBy);
		waitForElementToDisappear(spinnerEle);
		
		
	
	}
	
	
	public void addProductToCart(String[] productsToAdd) {
		
		List<String> proToAdd = Arrays.asList(productsToAdd);
		List<WebElement> products = getProductList();
		
		
		for(int i = 0 ; i < products.size() ; i++) {
			
			String pro = products.get(i).findElement(By.cssSelector("h5 b")).getText(); // tagName tagName
			
			if(proToAdd.contains(pro)) {
				
				// tagnName:last-of-type & tagName:first-of-type - it will select the first and last occurrence of tagName 
				products.get(i).findElement(addToCart).click();
				
				// this will increase performance using .invisibilityOf() method instead of .invisibilityOfElementLocated() method
				waitForElementToDisappear(spinnerEle);
			
			}
		}
	
	}
	

	
}
