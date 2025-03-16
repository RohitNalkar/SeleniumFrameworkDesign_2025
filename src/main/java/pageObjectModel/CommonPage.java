package pageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utility.Base;

public class CommonPage extends Base{

	WebDriver driver;
	
	public CommonPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}

	
	@FindBy (xpath = "//button[@routerlink='/dashboard/cart']")
	WebElement cartPageEle;
	
	@FindBy (xpath = "//button[@routerlink='/dashboard/myorders']")
	WebElement ordersPageEle;
	

	By myCartBy = By.xpath("//h1[text()='My Cart']");
	By yourOrdersBy = By.xpath("//h1[text()='Your Orders']");
	
	public void goToCart() {
		waitForWebElementToAppear(cartPageEle);
		cartPageEle.click();
		waitForElementToAppear(myCartBy);
	}
	
	public void goToOrdersPage() {
		ordersPageEle.click();
		waitForElementToAppear(yourOrdersBy);
	}

	
}
