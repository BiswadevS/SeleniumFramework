package com.testComponents;

import org.openqa.selenium.WebDriver;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.extentReport.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener{
	
	ExtentReports extentReports = ExtentReporterNG.config();
	ExtentTest test;
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>(); //Thread safe 
	
	@Override
	public void onTestStart(ITestResult result) {
		test = extentReports.createTest(result.getMethod().getMethodName());
		extentTest.set(test);
	  }

	
	@Override
	public void onTestSuccess(ITestResult result) {
	   test.log(Status.PASS, "Test passed");
	  }

	@Override
	public void onTestFailure(ITestResult result) {
	    extentTest.get().fail(result.getThrowable());
	    
	    try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    String filePath =null;
	   try {
		 filePath =  getScreenshot(result.getMethod().getMethodName(), driver);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
	  }

	@Override
	public void onTestSkipped(ITestResult result) {
	    // not implemented
	  }

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	    // not implemented
	  }

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
	    onTestFailure(result);
	  }

	@Override
	public void onStart(ITestContext context) {
	    // not implemented
	  }

	@Override
	public void onFinish(ITestContext context) {
	    extentReports.flush();
	  }
	
	
	
	
	

}
