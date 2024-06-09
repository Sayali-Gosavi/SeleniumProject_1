package project.qa.swaglabs.listeners;

//import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
//import jave.util.HashMap;
//import java.util.Map;
//import java.util.TimeZone;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
//import com.aventstack.extentreports.Status;
//import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
//import com.relevantcodes.extentreports.LogStatus;
//import com.relevantcodes.extentreports.model.Log;

import project.qa.swaglabs.factory.DriverFactory;

public class ExtentReportListener extends DriverFactory implements ITestListener{
	private static final String OUTPUT_FOLDER="./build/";
	private static final String FILE_NAME="TestExecutionReport.html";
	private static ExtentReports extent = init();
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
	private static ExtentReports extentReports;
	
	private static ExtentReports init() {
		Path path = Paths.get(OUTPUT_FOLDER);
		
		//if directory exists
		if(!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		extentReports = new ExtentReports();
		ExtentSparkReporter reporter = new ExtentSparkReporter(OUTPUT_FOLDER+FILE_NAME);
		reporter.config().setDocumentTitle("Swag Labs Automation");
		reporter.config().setTheme(Theme.STANDARD);
		reporter.config().setReportName("Swag Labs Test Automation Results");
		extentReports.attachReporter(reporter);
		extentReports.setSystemInfo("System","windows");
		extentReports.setSystemInfo("Author","Sayali");
		extentReports.setSystemInfo("Framework :- ","Hybrid Automation Framework using POM");
		extentReports.setSystemInfo("Build#1","1.1");
		
		return extentReports;
	}
	
	public synchronized void onStart(ITestContext context) {
		System.out.println("Test Suite started");
	}
	
	public synchronized void onFinish(ITestContext context) {
		System.out.println("Test Suite ending");
		extent.flush();
		test.remove();
	}
	
	public synchronized void onTestStart(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		String qualifiedName = result.getMethod().getQualifiedName();
		int last = qualifiedName.lastIndexOf(".");
		int mid = qualifiedName.substring(0,last).lastIndexOf(".");
		String className = qualifiedName.substring(mid+1,last);
		System.out.println(methodName+" started!");
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),result.getMethod().getDescription());
		extentTest.assignCategory(className);
		test.set(extentTest);
		test.get().getModel().setStartTime(getTime(result.getStartMillis()));
	}
	
	public synchronized void onTestSuccess(ITestResult result) {
		System.out.println(result.getMethod().getMethodName()+"passed");
		test.get().pass("testpassed");
		test.get().pass(result.getThrowable(),MediaEntityBuilder.createScreenCaptureFromPath(getScreenshot(result.getMethod().getMethodName())).build());
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}
	
	public synchronized void onTestFailure(ITestResult result) {
		System.out.println(result.getMethod().getMethodName()+"failed");
		test.get().fail(result.getThrowable(),MediaEntityBuilder.createScreenCaptureFromPath(getScreenshot(result.getMethod().getMethodName())).build());
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}
	
	public synchronized void onTestSkipped(ITestResult result) {
		System.out.println(result.getMethod().getMethodName()+"skipped");
		test.get().skip(result.getThrowable(),MediaEntityBuilder.createScreenCaptureFromPath(getScreenshot(result.getMethod().getMethodName())).build());
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}
	
	public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("onTestFailedButWithinSuccessPercentage for "+result.getMethod().getMethodName());
	}
	
	private Date getTime(long millis) {
		Calendar calender = Calendar.getInstance();
		calender.setTimeInMillis(millis);
		return calender.getTime();
	}
}
