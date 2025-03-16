package pageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utility.Base;

public class OrderConfirmationPage extends Base{

	
	WebDriver driver;
	
	public OrderConfirmationPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(xpath = "//h1[@class='hero-primary']")
	WebElement orderSucessMsgEle;
	
	
	public String getOrderSuccessMsg() {
	
		return orderSucessMsgEle.getText();
	}
	

}
