package pageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utility.Base;

public class LoginPage extends Base {
	
	WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		super(driver);
		// we are writing this code here in constructor bcz it will execute at first only before go into class
		this.driver = driver;
		
		// it required the 2 par 1.driver and 2.current class instance
		PageFactory.initElements(driver, this);
		
	}
	
	// how this FindBy knows about driver so we need to initialize this element using pageFactory class
	@FindBy(id = "userEmail") // internally construct the driver.findElement(By.id("userEmail"))
	WebElement userEmailEle;     // In this variable that WebElement get stored 
	
	
	@FindBy(id = "userPassword")
	WebElement userPasswordEle;
	
	@FindBy(id = "login")
	WebElement loginButtonEle;
	
//	@FindBy(css = ".ng-animating")
//	WebElement spinnerEle;
	
	@FindBy(css = "[class*='flyInOut']" )
	WebElement errorMsgTosterEle;
	
	
	
	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client/");
	}
	
	public void loginApplication(String userEmail, String userPassword) {
		
		userEmailEle.sendKeys(userEmail);
		userPasswordEle.sendKeys(userPassword);
		loginButtonEle.click();
//		waitForElementToDisappear(spinnerEle);
	}
	
	
	public String getErrorMsg() {
		waitForWebElementToAppear(errorMsgTosterEle);
		String error = errorMsgTosterEle.getText();
		return error;
	}
	
	
	
}
