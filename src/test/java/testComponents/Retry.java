package testComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {

	
	// If there are some flaky test cases are there and those failed due to any unknown reason  
	// so IRetryAnalyzer will ask us to rerun those failed test cases or not and how many time you want to rerun them 
	// if test passes then test execution will not come in this class
	
	
	// we as we have given the ITestlistener in the xml file to understand the test when it failed or pass what he have to do
	// same as IRetryAnalizer we have to provide it in @Test signature as (retryAnalyzer = Retry.class (this is class name))
	
	int count = 0 ; 
	int maxTry = 1;
	
	@Override
	public boolean retry(ITestResult result) {
		
		if(count < maxTry ) {
			count ++;
			return true;
		}
		
		return false;
	}

	
	
	
}
