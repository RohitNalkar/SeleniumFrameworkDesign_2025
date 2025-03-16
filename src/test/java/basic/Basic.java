package basic;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Basic {

	
	public static void main(String[] args) throws InterruptedException {
		
		// WebDriverManager we can't use now as WebDriver internally downloading the driver for browsers.
		//WebDriverManager.ChromeDriver.setup();
		
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3)); 
		
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/client/");
		
		driver.findElement(By.id("userEmail")).sendKeys("rohitnalkar333@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Rohit@123");
		driver.findElement(By.id("login")).click();
		
		
		String[] productToAdd = {"ADIDAS ORIGINAL" , "IPHONE 13 PRO"};
		List<String> proToAdd = Arrays.asList(productToAdd);
		
		List<WebElement> products = driver.findElements(By.cssSelector(".col-lg-4"));
		
		// Way 1st to sort and add product in cart 
		for(int i = 0 ; i < products.size() ; i++) {
			
			String pro = products.get(i).findElement(By.cssSelector("h5 b")).getText(); // tagName tagName
			System.out.println(pro);
			
			if(proToAdd.contains(pro)) {
				
				// tagnName:last-of-type & tagName:first-of-type - it will select the first and last occurrence of tagName 
				products.get(i).findElement(By.cssSelector(".card-body button:last-of-type")).click();
				
				
				// this will increase performance using .invisibilityOf() method instead of .invisibilityOfElementLocated() method
				wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
			
			}
		}
		
		// way 2nd to sort and add product in cart 
		String proName = "ZARA COAT 3";
		WebElement prod = products.stream()
				.filter(product -> 
				product.findElement(By.cssSelector("b")).getText().equals(proName)).findFirst().orElse(null);
		
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		
		
		// Click on cart button
		driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();
		
		
		// verifying the product added in cart or not for 1Way
		List<WebElement> addedProLi = driver.findElements(By.xpath("//div[@class='cartSection']/h3"));
		
		for(WebElement pro : addedProLi) {
			
			String addedPro = pro.getText();
			if(!addedPro.equals(proName)) { // just excluding this product as we are verifying in further code 
				System.out.println("Verification for product - " + addedPro );
				Assert.assertTrue(proToAdd.contains(addedPro), "FAILED - Product Are Missing From Cart");
			}
			
		}
		
		
		// verifying the product added in cart or not for 2Way
		boolean flag = addedProLi.stream().anyMatch(product -> product.getText().equals(proName));
		Assert.assertTrue(flag);
		
		// click on checkout 
		driver.findElement(By.xpath("//li[@class='totalRow']/button")).click();
		
		
		// Adding Card Details
		
		WebElement expMonth = driver.findElement(By.xpath("(//select[@class='input ddl'])[1]"));
		WebElement expDate = driver.findElement(By.xpath("(//select[@class='input ddl'])[2]"));
		
		Select monthSelect = new Select(expMonth);
		Select dateSelect = new Select(expDate);
		
		monthSelect.selectByVisibleText("06");
		dateSelect.selectByVisibleText("14");
		
		driver.findElement(By.xpath("//div[text()='CVV Code ']/following-sibling::input")).sendKeys("123");
		
		driver.findElement(By.xpath("//div[text()='Name on Card ']/following-sibling::input")).sendKeys("ROHIT NALKAR");
		
		// Select Country
		
		driver.findElement(By.xpath("//input[@placeholder='Select Country']")).sendKeys("Ind");
		
		List<WebElement> countries = driver.findElements(By.xpath("//section[@class='ta-results list-group ng-star-inserted']/button"));
		
		for(WebElement con : countries) {
			
			if(con.getText().equals("India")) {
				con.click();
				break;  
			}
		}
		
		driver.findElement(By.cssSelector(".btnn.action__submit")).click();
		
		String confMSg = driver.findElement(By.xpath("//h1[@class='hero-primary']")).getText();
		
		Assert.assertEquals(confMSg, "THANKYOU FOR THE ORDER.", "FAILED - While Validating the order confirmation msg");
		
		Thread.sleep(3000);
//		driver.quit();
			
	}
}
