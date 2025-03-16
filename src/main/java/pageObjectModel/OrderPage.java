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

public class OrderPage extends Base {
	
	WebDriver driver;
	
	public OrderPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}
	
	
	@FindBy(xpath = "//tr[@class='ng-star-inserted']/td[2]")
	List <WebElement> orderProductsEle;
	

	
	public List<WebElement> getOrderProductsList() {
		System.out.println(orderProductsEle);
		return orderProductsEle;
	}
	
	public boolean verifyOrderProductDisplay(String productName) {
		System.out.println("---  Verifying the orderd product is avaliable in order history ot not ---");
		boolean match = getOrderProductsList().stream()
										   	  .anyMatch(product -> product.getText().equals(productName));
		return match;
	}

	
	
	
	
}
