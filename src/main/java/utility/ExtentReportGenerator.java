package utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportGenerator {

	
	
	public static ExtentReports getReportObject() {
		
		 // Steps to Generate an Extent Report
		 // 1. Create an ExtentReports instance.
		 // 2. Attach a reporter (ExtentSparkReporter).
		 // 3. Create and log test cases.
		 // 4. Flush
		
		String path = System.getProperty("user.dir") + "/testReport/index.html";
		
		ExtentSparkReporter report = new ExtentSparkReporter(path);
		
		// ExtentSparkReporter is helper class used to make all configuration for your report and will report to the main ExtentReports class
		// Set Report Name
		report.config().setReportName("Web E Commerce Automation Result");
		
		// Set Page Title 
		report.config().setDocumentTitle("TEST RESULT");
		
		
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(report); // we have to attach the report here 
		
		// Set tester name 
		extent.setSystemInfo("Tester", "Rohit Nalkar");

		
		
		return extent;
		
	}
}
