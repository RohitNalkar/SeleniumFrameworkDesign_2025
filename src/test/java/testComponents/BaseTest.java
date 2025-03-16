package testComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;

import pageObjectModel.CartPage;
import pageObjectModel.CommonPage;
import pageObjectModel.LoginPage;
import pageObjectModel.OrderPage;
import pageObjectModel.ProductCataloguePage;

public class BaseTest {
	


	// Initialing the variable at global level
	public WebDriver driver;
	public LoginPage loginPage;
	public ProductCataloguePage proCatPage;
	public CommonPage commonPage;
	public CartPage cartPage;
	public OrderPage orderPage;
	
	
	
	public Properties prop;
	
	public WebDriver initializeDriver() {
		
		String browserName="";

		// here we are checking weather we are passing any browser values from the Maven command 
		browserName = System.getProperty("browser") != null ? System.getProperty("browser") : prop.getProperty("browser");
		
		if(browserName.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		}
		else if (browserName.equalsIgnoreCase("safari")) {
			driver = new SafariDriver();
		}
		else if (browserName.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		}
		else if (browserName.equalsIgnoreCase("internetexplorer")) {
			driver =new InternetExplorerDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
		
		return driver;
		
	}
	
	@BeforeTest(alwaysRun=true) 
	public void setUp() {
		
		prop = new Properties();
		try {
			// System.getProperty("user.dir") - this will give current project path
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/config/qa.properties");
			prop.load(fis);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	// (groups={"ErrorHandling"}) while running some scripts using groups then @before and @after test will not run 
	// so every time we have to provide the group name to all  @before and @after tests 
	// so in such case we have one more annotation is alwaysRun=true - so this will run test every time when we are running test in groups 
	@BeforeMethod (alwaysRun=true)  
	public void launchApplication() {
		
		driver = initializeDriver();
		
		loginPage= new LoginPage(driver);
		proCatPage= new ProductCataloguePage(driver);
		commonPage= new CommonPage(driver);
		cartPage= new CartPage(driver);
		orderPage = new OrderPage(driver);
		
		loginPage.goTo();
	}
	
	@AfterMethod(alwaysRun=true) 
	public void tearDown() {
		
		driver.close();
	}
	
	
	//Convert the JSON Data into HashMap 
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
		
		File file = new File(filePath);
		 
		// read JSON to string - we can use any outof below 2 
		
		//String jsonContent = Files.readFile(file);
		String jsonContent =FileUtils.readFileToString(file, StandardCharsets.UTF_8);

		// Convert String to HashMap - Jackson Databind utility we required
		
		ObjectMapper mapper = new ObjectMapper();
		
		TypeReference<List<HashMap<String, String>>> type = new TypeReference<List<HashMap<String, String>>>() {};
		
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, type);
		
		return data;
		
	}
	
	
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
	
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter fomater = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String dateTimeStamp = now.format(fomater);
		
		TakesScreenshot ts = (TakesScreenshot)driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File dest = new File(System.getProperty("user.dir") + "/testReport/" + testCaseName + dateTimeStamp + ".jpg");
		Files.copy(src, dest);
		return dest.toString();
	}
	
	
	
	
}
