package testComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import utility.ExtentReportGenerator;



public class Listeners extends BaseTest implements ITestListener {

	// calling the ExtentReportGenerator utility here and storing in object
	ExtentReports extent = ExtentReportGenerator.getReportObject();
	ExtentTest test;
	
	// we have to make the test object as a thread safe so that we can run test parallel and no interruption will happen between the objects 
	
	ThreadLocal<ExtentTest> threadLocal = new ThreadLocal<ExtentTest>();
	
	@Override
	public void onTestStart(ITestResult result) {
		// we have to create the entry for the report so we can do this here 
		// Before start any test we have to createTest() report so that it will start reporting all execution 
		test = extent.createTest(result.getMethod().getMethodName()); // We have to store it in test and it will hold the entry of your test case 
		
		// so this will assign one unique thread id(for Error Validation) so that i will execute independently 
		// it will add test object as a map in the threadLocal and on that threadLocal obj we can call test obj methods 
		threadLocal.set(test); 
 		
		threadLocal.get().assignAuthor("RN"); // Assigns an author to the test
		threadLocal.get().assignDevice("M3 Mac Book Pro"); // Assigns a device to the test
		threadLocal.get().assignCategory("Regression Tests"); // Assigns a category to the test
	
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// on that test entry we can log our test as pass 
		threadLocal.get().log(Status.PASS , "Test Passed"); // if we don't write here report will treat it as a pass implicitly
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// on that test entry we can log our test as fail 
		threadLocal.get().log(Status.FAIL , "Test Failed"); // we have to mention this explicitly 
		threadLocal.get().fail(result.getThrowable()); // to get all error msg we have to call it from test result reference 
		
		// After Test Failed we have to capture the screenshot so we can capture it from here
		
		String filePath="";
		try {
			 //we have to pass driver in getScreenshot method also  so we are taking it from result 
			// get class from test - take actual test class - get driver field - get the obj (instance) of driver
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
			
			// this we are using to take screenshot -
			filePath = getScreenshot(result.getMethod().getMethodName(), driver); 
		
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
		threadLocal.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName()); // this we use to attach screenshot to report 
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		
	
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		
		
		
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
	
		
	}

	@Override
	public void onStart(ITestContext context) {
	
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// we have to Flush the report here  
		extent.flush();
	
	}

	
	
	
	
}
