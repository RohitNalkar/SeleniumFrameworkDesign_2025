package pageObjectModel;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import utility.Base;

public class CheckoutPage extends Base {

	WebDriver driver;
	
	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}
	
	
	
	@FindBy(xpath = "(//select[@class='input ddl'])[1]")
	WebElement expMonthEle;
	
	@FindBy(xpath = "(//select[@class='input ddl'])[2]")
	WebElement expDateEle;
	
	@FindBy(xpath = "//div[text()='CVV Code ']/following-sibling::input")
	WebElement cvvCodeEle;
	
	@FindBy(xpath = "//div[text()='Name on Card ']/following-sibling::input")
	WebElement nameOncardEle;
	
	@FindBy(xpath = "//input[@placeholder='Select Country']")
	WebElement selecyCountryEle;
	
	@FindBy(xpath = "//section[@class='ta-results list-group ng-star-inserted']/button")
	List<WebElement> countrySeachesEle;
	
	@FindBy (css = ".btnn.action__submit")
	WebElement placeOrderEle;
	
	
	public void selectExpiryDate(String month, String date) {
		
		selectValueFromDropdown(expMonthEle, "selectByVisibleText", month);
		
		selectValueFromDropdown(expDateEle, "selectByVisibleText", date);

	}
	
	public void addCvvCode(String cvv) {
		cvvCodeEle.sendKeys(cvv);
	}
	
	public void addNameOnCard(String name) {
		nameOncardEle.sendKeys(name);
		
	}

	public void selectCountry(String country) {
		
		selecyCountryEle.sendKeys(country);
		
		List<WebElement> countries = countrySeachesEle;
		
		for(WebElement con : countries) {
			
			if(con.getText().equalsIgnoreCase(country)) {
				con.click();
				break;  
			}
		}
		
	}
	
	public OrderConfirmationPage placeOrder() {
		placeOrderEle.click();
		OrderConfirmationPage orderConfPage = new OrderConfirmationPage(driver);
		return orderConfPage;
	}
	
	
	
}
