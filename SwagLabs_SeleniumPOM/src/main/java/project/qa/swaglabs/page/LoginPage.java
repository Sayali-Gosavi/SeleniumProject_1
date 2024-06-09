package project.qa.swaglabs.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
	
//	1) declare private webdriver
	private WebDriver driver;
	
//	2) page constructor
	public LoginPage(WebDriver driver) {
		this.driver=driver;
	}
	
//	3) By locators
	private By loginPageText = By.xpath("//div[@class='login_logo']");
	
	private By username = By.id("user-name");
	private By password = By.id("password");
	private By loginButton = By.name("login-button");
	private By error = By.className("error-message-container error");
	
	public void enterUsername(String text) {
		driver.findElement(username).sendKeys(text);
	}
	
	public void enterPassword(String text) {
		driver.findElement(password).sendKeys(text);
	}
	
	public void loginClick(String user,String pass) {
		enterUsername(user);
		enterPassword(pass);
		driver.findElement(loginButton).click();
	}
	
	public String getErrorText() {
		return driver.findElement(error).getText();
	}
	
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

}
