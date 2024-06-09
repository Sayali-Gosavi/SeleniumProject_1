package project.qa.swaglabs.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest{
	
	@Test
	public void loginTest() throws InterruptedException {
		Thread.sleep(5000);
		lp.loginClick("standard_user", "secret_sauce");
		Thread.sleep(2000);
		String currentUrl = lp.getCurrentUrl();
		Assert.assertEquals(currentUrl, "https://www.saucedemo.com/inventory.html");
	}
	
	@Test
	public void invalidUsernamePasswordErrorTest() throws InterruptedException {
		Thread.sleep(5000);
		lp.loginClick("abc","xyz");
		Thread.sleep(2000);
		String actualError = lp.getErrorText();
		Assert.assertEquals(actualError, "Epic sadface: Username and password do not match any user in this service");
	}
	
	@Test
	public void missingUsernameErrorTest() throws InterruptedException {
		Thread.sleep(5000);
		lp.loginClick(null,"xyz");
		Thread.sleep(2000);
		String actualError = lp.getErrorText();
		Assert.assertEquals(actualError, "Epic sadface: Username is required");
	}
	
	@Test
	public void missingPasswordErrorTest() throws InterruptedException {
		Thread.sleep(5000);
		lp.loginClick("standard_user",null);
		Thread.sleep(2000);
		String actualError = lp.getErrorText();
		Assert.assertEquals(actualError, "Epic sadface: Username is required");
	}
	


}
