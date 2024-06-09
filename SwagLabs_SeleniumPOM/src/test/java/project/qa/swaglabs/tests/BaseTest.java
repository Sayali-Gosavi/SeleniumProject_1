package project.qa.swaglabs.tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import project.qa.swaglabs.factory.DriverFactory;
import project.qa.swaglabs.page.LoginPage;

public class BaseTest {
	
	DriverFactory df;
	WebDriver driver;
	LoginPage lp;
	
//	@BeforeTest
	@BeforeMethod
	public void setup() {
		df = new DriverFactory();
		driver = df.init_driver("chrome");
//		df.login("sayali.gosavi@dmoe.com");
		
		lp = new LoginPage(driver);
	}
	
//	@AfterTest
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
