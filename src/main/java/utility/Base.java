package utility;


import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Base {
	
	WebDriver driver;
	
	public Base(WebDriver driver) {
		this.driver = driver;
	}
	
	
	// Explicit Wait - visibilityOfAllElementsLocatedBy
	public void waitForElementToAppear(By findBy) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); 
		System.out.println("Waiting for Element to Appear : " + findBy);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(findBy)); // this will accept the locator of By class
	}

	
	// Explicit Wait - invisibilityOf
	public void waitForElementToDisappear(WebElement element) {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); 
		System.out.println("Waiting for Element to Disappear : " + element);
		wait.until(ExpectedConditions.invisibilityOf(element));
		
	}	
	
	// Explicit Wait - visibilityOf
	public void waitForWebElementToAppear(WebElement element) {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); 
		System.out.println("Waiting for Web Element to Appear : " + element);
		wait.until(ExpectedConditions.visibilityOf(element));
		
	}	
	
	public void selectValueFromDropdown(WebElement element, String selectBy, String value) {
		
		try {
			Select sc = new Select(element);
			
			if(selectBy.toLowerCase().contains("index")) {
				System.out.println("Selecting By Index : " + Integer.parseInt(value));
				sc.selectByIndex(Integer.parseInt(value));
			}
			else if(selectBy.toLowerCase().contains("value")) {
				System.out.println("Selecting By Value : " + value);
				sc.selectByValue(value);
			}
			else if(selectBy.toLowerCase().contains("visibletext")) {
				System.out.println("Selecting By Visible Text : " + value);
				sc.selectByVisibleText(value);
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	


}
